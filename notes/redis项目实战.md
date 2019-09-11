# 15.3.1. 分布式锁实现源码

* 分布锁满足两个条件，一个是加有效时间的锁，一个是高性能解锁

* 采用redis命令setnx（set if not exist）、setex（set expire value）实现

* 【千万记住】解锁流程不能遗漏，否则导致任务执行一次就永不过期

* 将加锁代码和任务逻辑放在try，catch代码块，将解锁流程放在finally

* 通过scp方式将项目打包jar包放到集群里面的服务器

* 通过nohup启动jar包

### 15.3.3. Lua脚本实现分布式锁

**简介：手把手进行Lua脚本讲解和setnx、setex命令连用讲解**

*  Lua简介
  * 从 Redis 2.6.0 版本开始，通过内置的 Lua 解释器，可以使用 EVAL 命令对 Lua 脚本进行求值。
  * Redis 使用单个 Lua 解释器去运行所有脚本，并且， Redis 也保证脚本会以原子性(atomic)的方式执行：当某个脚本正在运行的时候，不会有其他脚本或 Redis 命令被执行。这和使用 MULTI / EXEC 包围的事务很类似。在其他别的客户端看来，脚本的效果(effect)要么是不可见的(not visible)，要么就是已完成的(already completed)。


* Lua脚本配置流程
  *  1、在resource目录下面新增一个后缀名为.lua结尾的文件
  * 2、编写lua脚本
  * 3、传入lua脚本的key和arg
  * 4、调用redisTemplate.execute方法执行脚本
* Lua脚本结合RedisTempalte实战演练
* Lua脚本其他工作场景剖析和演练
* lua eval http://doc.redisfans.com/script/eval.html


### 15.3.4. RedisConnection实现分布式锁


RedisConnection实现分布锁的方式，采用redisTemplate操作redisConnection
           实现setnx和setex两个命令连用**

- redisTemplate本身有没通过valueOperation实现分布式锁

  * 问题探索：
             Spring Data Redis提供了与Java客户端包的集成服务，比如Jedis, JRedis等 
             通过getNativeConnection的方式可以解决问题吗？

- Spring Data Redis提供了与Java客户端包的集成服务，比如Jedis, JRedis等 

  * 代码演示

  * ```java
    /**
            * 重写redisTemplate的set方法
            * <p>
            * 命令 SET resource-name anystring NX EX max-lock-time 是一种在 Redis 中实现锁的简单方法。
            * <p>
            * 客户端执行以上的命令：
            * <p>
            * 如果服务器返回 OK ，那么这个客户端获得锁。
            * 如果服务器返回 NIL ，那么客户端获取锁失败，可以在稍后再重试。
            *
            * @param key     锁的Key
            * @param value   锁里面的值
            * @param seconds 过去时间（秒）
            * @return
         */
          private String set(final String key, final String value, final long seconds) {
            Assert.isTrue(!StringUtils.isEmpty(key), "key不能为空");
            return redisTemplate.execute(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    Object nativeConnection = connection.getNativeConnection();
                    String result = null;
                    if (nativeConnection instanceof JedisCommands) {
                        result = ((JedisCommands) nativeConnection).set(key, value, NX, EX, seconds);
                    }
    
                    if (!StringUtils.isEmpty(lockKeyLog) && !StringUtils.isEmpty(result)) {
                        logger.info("获取锁{}的时间：{}", lockKeyLog, System.currentTimeMillis());
                    }
    
                    return result;
                }
            });
          }
    ```

    

- 为什么新版本的spring-data-redis会报class not can not be case错误
            

  ```
  io.lettuce.core.RedisAsyncCommandsImpl cannot be cast to redis.clients.jedis.JedisCommands
  ```

  

- 探索spring-data-redis升级

  * 官网api分析
             https://docs.spring.io/spring-data/redis/docs/1.5.0.RELEASE/api/
             https://docs.spring.io/spring-data/redis/docs/2.0.13.RELEASE/api/

  * 源码改造

    ```java
    public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
          RedisConnection redisConnection =             redisTemplate.getConnectionFactory().getConnection();
         return redisConnection.set(key.getBytes(), getHostIp().getBytes(), Expiration.seconds(expire), RedisStringCommands.SetOption.ifAbsent());
    }
    
    ```


    

# 17. redis排行榜


## 17.1. 项目中实现的功能
            
1、用sorted Set实现过排行榜项目

2、用过期key结合springboot cache实现过缓存存储

3、redis实现分布式环境seesion共享

4、用布隆过滤器解决过缓存穿透

5、redis实现分布式锁

6、redis实现订单重推系统

## 17.2. 功能设计

    1、ZSetOperations重要api讲解（sortedSet）
        

        1、排行榜：
             排行榜功能是一个很普遍的需求。使用 Redis 中有序集合的特性来实现排行榜是又好又快的选择。
             一般排行榜都是有实效性的，比如“用户积分榜”，游戏中活跃度排行榜，游戏装备排行榜等。

          面临问题：
             数据库设计复杂，并发数较高，数据要求实时性高   

        2、redis实现排行榜api讲解



    2、浅谈mysql数据库表设计过程中几个关键要点

        简介：数据库表score_flow（积分流水表）、user_score（用户积分表总表）设计，用于：1）查top100  2）查用户的排名

        1、表设计过程中应该注意的点即数据类型 
              1）更小的通常更好 
                  控制字节长度
              
              2）使用合适的数据类型
                  如tinyint只占8个位，char(1024)与varchar(1024)的对比,char用于类似定长数据存储比varchar节省空间，如：uuid（32），可以用char(32).


              3）尽量避免NULL建议使用NOT NULL DEFAULT ''
                  NULL的列会让索引统计和值比较都更复杂。可为NULL的列会占据更多的磁盘空间，在Mysql中也需要更多复杂的处理程序


        2、索引设计过程中应该注意的点

              1）选择唯一性索引
                  唯一性索引的值是唯一的，可以更快速的通过该索引来确定某条记录,保证物理上面唯一

              2）为经常需要排序、分组和联合操作的字段建立索引
                  经常需要ORDER BY、GROUP BY、DISTINCT和UNION等操作的字段，排序操作会浪费很多时间

              3)常作为查询条件的字段建立索引
                  如果某个字段经常用来做查询条件，那么该字段的查询速度会影响整个表的查询速度        
    
              4)数据少的地方不必建立索引
      



  3、sql优化，explain查看执行计划(注意：扫描行数会影响CPU运行，占用大量内存)
        简介：sql优化以及mybatis generator反向工程讲解
 
              1） 能够用BETWEEN的就不要用IN 

              2） 能够用DISTINCT的就不用GROUP BY

              1)  避免数据类型强转

              4） 学会采用explain查看执行计划


        续：org.mybatis.generator配置讲解    

        引入：
        <dependency>
      <groupId>org.mybatis.generator</groupId>
      <artifactId>mybatis-generator-core</artifactId>
      <scope>test</scope>
      <version>1.3.2</version>
      <optional>true</optional>
    </dependency>

    <dependency>
      <groupId>commons-io</groupId>
      <artifactId>commons-io</artifactId>
      <version>2.5</version>
    </dependency>



4、排行榜三大接口讲解

        
         1、添加用户积分


         2、获取top N 排行  
             redisService新增方法reverseRangeWithScores()


         3、根据用户ID获取排行
             zset.rank(key,value)，key为set的名称，value为用户id

    5、springboot项目初始化加载讲解
         场景：将一千万用户白名单load缓存，用户请求的时候判断该用户是否是缓存里面的用户

        
         1、springboot实现初始化加载配置（实现缓存预热）

             1、采用实现springboot ApplicationRunner
                该方法仅在SpringApplication.run(…)完成之前调用

             2、采用实现InitializingBean
                InitializingBean接口为bean提供了初始化方法的方式，它只包括afterPropertiesSet()方法。
                在spring初始化bean的时候，如果bean实现了InitializingBean接口，
                在对象的所有属性被初始化后之后才会调用afterPropertiesSet()方法

         2、初始化同步redis数据


         3、初始化完成再放入请求





# 18. redis秒杀系统


### 18.0.1. 秒杀系统需求分析



**简介：采用大厂需求分析步骤对秒杀需求功能分析**

- 功能核心点

  * 经典互联网商品抢购秒杀功能

- 功能api

  * 商品秒杀接口

- 数据落地存储方案

  * 通过分布式redis减库存
  * DB存最终订单信息数据

- api性能调优

  * 性能瓶颈在高并发秒杀
  * 技术难题在于超卖问题

  

### 18.0.2. 秒杀系统功能步骤梳理



**简介：后端秒杀功能步骤梳理 **  

- 利用 Redis 缓存incr拦截流量  

  - 首先通过数据控制模块，提前将秒杀商品缓存到读写分离 Redis，并设置秒杀开始标记如下：

    ```shell
    "skuId_start": 0   //开始标记0表示秒杀开始
    "skuId_count": 10000 //总数
    "skuId_access": 12000  //接受抢购数
    ```

  - 秒杀开始前，服务集群读取 goodsId_Start 为 0，直接返回未开始。

  - **服务时间不一致可能导致流量倾斜,所以要有开始标记**

  - 数据控制模块将 goodsId_start 改为1，标志秒杀开始。

  - 当接受下单数达到 sku_count*1.2 后，继续拦截所有请求，商品剩余数量为 0

    

- 利用Redis缓存加速库存扣量

  ```shell
  "skuId_booked": 10000 //总数0开始10000  通过incr扣减库存，返回抢购成功
  ```

- 将用户订单数据写入mq

- 监听mq入库





### 18.0.3. 秒杀系统功能api实战(上)



**简介：后端秒杀网关流量拦截层功能开发 **  

- 先判断秒杀是否已经开始
  * 初始化时将key  SECKILL_START_1 value 0_1554046102存入数据库中
- 利用 Redis 缓存incr拦截流量  
  - 缓存拦截流量代码编写
  - 用incr方法原子加
  - 通过原子加判断当前skuId_access是否达到最大值
  - 思考：是否需要保证获取到值的时候和incr值两个命令的原子性
    * 保证原子性的方式，采用lua脚本
    * 采用lua脚本方式保证原子性带来缺点，性能有所下降
    * 不保证原子性缺点，放入请求量可能大于skuId_access





### 18.0.4. 秒杀系统功能api实战(中)



**简介：后端秒杀信息校验层功能开发布隆过滤器实现重复购买拦截 **  

- 订单信息校验层

  * 校验当前用户是否已经买过这个商品
    - 需要存储用户的uid
    - 存数据库效率太低
    - 存Redis value方式数据太大
    - 存布隆过滤器性能高且数据量小

- 校验完通过直接返回抢购成功

  

  

  

  

### 18.0.5. 秒杀系统功能api实战(下)



  **简介：后端秒杀信息校验层功能开发lua脚本实现库存扣除**  

  - 库存扣除成功，获取当前最新库存

  - 如果库存大于0，即马上进行库存扣除，并且访问抢购成功给用户

  - 考虑原子性问题  

    * 保证原子性的方式，采用lua脚本
    * 采用lua脚本方式保证原子性带来缺点，性能有所下降
    * 不保证原子性缺点，放入请求量可能大于预期值
    * 当前扣除库存场景必须保证原子性，否则会导致超卖

  - 返回抢购结果

    * 抢购成功
    * 库存没了 ，抢购失败

    


