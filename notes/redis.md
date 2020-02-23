<!-- TOC -->

- [1. 概述](#1-概述)
- [2. 数据类型](#2-数据类型)
    - [2.1. STRING](#21-string)
    - [2.2. LIST](#22-list)
    - [2.3. SET](#23-set)
    - [2.4. HASH](#24-hash)
    - [2.5. ZSET](#25-zset)
    - [2.6. 五种基本类型的底层实现TODO](#26-五种基本类型的底层实现todo)
- [3. 数据结构](#3-数据结构)
    - [3.1. 字典](#31-字典)
    - [3.2. 跳跃表](#32-跳跃表)
- [4. 使用场景](#4-使用场景)
    - [4.1. 计数器](#41-计数器)
    - [4.2. 缓存](#42-缓存)
    - [4.3. 查找表](#43-查找表)
    - [4.4. 消息队列](#44-消息队列)
    - [4.5. 会话缓存](#45-会话缓存)
    - [4.6. 分布式锁实现](#46-分布式锁实现)
    - [4.7. 其它](#47-其它)
- [5. Redis 与 Memcached](#5-redis-与-memcached)
    - [5.1. 数据类型](#51-数据类型)
    - [5.2. 数据持久化](#52-数据持久化)
    - [5.3. 分布式](#53-分布式)
    - [5.4. 内存管理机制](#54-内存管理机制)
    - [5.5. 缓存过期机制](#55-缓存过期机制)
- [6. 数据淘汰策略(如何保证Redis中的数据都是热点数据)](#6-数据淘汰策略如何保证redis中的数据都是热点数据)
    - [Redis 具体有 6 种淘汰策略：](#redis-具体有-6-种淘汰策略)
    - [Redis过期key清除策略](#redis过期key清除策略)
- [Redis过期细节](#redis过期细节)
    - [清除过期时间](#清除过期时间)
    - [Redis键值2种失效方式](#redis键值2种失效方式)
- [7. 持久化](#7-持久化)
    - [7.1. RDB 持久化](#71-rdb-持久化)
        - [rdb 的bgsave 很占用内存](#rdb-的bgsave-很占用内存)
        - [rdb的原理](#rdb的原理)
        - [写时复制的原理](#写时复制的原理)
    - [7.2. AOF 持久化](#72-aof-持久化)
    - [AOF 重写](#aof-重写)
    - [7.3. Redis两种持久化方案对比](#73-redis两种持久化方案对比)
- [8. 事务](#8-事务)
    - [8.1. redis事务相关命令](#81-redis事务相关命令)
    - [8.2. redis数据库事务的ACID与传统关系型事务的比较](#82-redis数据库事务的acid与传统关系型事务的比较)
        - [8.2.1. 原子性（Atomicity）](#821-原子性atomicity)
        - [8.2.2. 一致性（Consistency）](#822-一致性consistency)
        - [8.2.3. 隔离性（Isolation）](#823-隔离性isolation)
        - [8.2.4. 持久性（Durability）](#824-持久性durability)
    - [10.3. redis主从集群的搭建与docker通信知识](#103-redis主从集群的搭建与docker通信知识)
        - [10.3.1. docker搭建redis主从集群](#1031-docker搭建redis主从集群)
        - [10.3.2. docker 搭建redis-sentinel集群](#1032-docker-搭建redis-sentinel集群)
- [11. 订阅机制](#11-订阅机制)
    - [11.1. redis的消息订阅发布和mq对比？](#111-redis的消息订阅发布和mq对比)
- [12. redis作为二级缓存](#12-redis作为二级缓存)
    - [12.1. 实现](#121-实现)
    - [12.2. springboot cache自定义项](#122-springboot-cache自定义项)
    - [12.3. mysql与redis性能对比](#123-mysql与redis性能对比)
- [13. 缓存穿透与缓存雪崩](#13-缓存穿透与缓存雪崩)
    - [13.1. 缓存穿透](#131-缓存穿透)
    - [13.2. 缓存雪崩](#132-缓存雪崩)
    - [13.3. 缓存雪崩解决方案](#133-缓存雪崩解决方案)
- [14. redis实现分布式锁(解决不同客户端并发操作某个数据)](#14-redis实现分布式锁解决不同客户端并发操作某个数据)
    - [14.1. 分布式锁是什么](#141-分布式锁是什么)
    - [14.2. 分布锁实现方案分析](#142-分布锁实现方案分析)
        - [14.2.1. 分布式锁面试题](#1421-分布式锁面试题)
- [15. redis高可用集群](#15-redis高可用集群)
    - [15.1. 主从复制的原理](#151-主从复制的原理)
    - [15.2. 分布式系统高可用](#152-分布式系统高可用)
    - [数据迁移的三种方式](#数据迁移的三种方式)
    - [15.3. Redis高可用架构Sentinel(解决宕机)](#153-redis高可用架构sentinel解决宕机)
        - [15.3.1. Sentinel三大工作任务](#1531-sentinel三大工作任务)
        - [15.3.2. 冷备和热备](#1532-冷备和热备)
        - [15.3.3. Sentinel故障转移原理](#1533-sentinel故障转移原理)
        - [15.3.4. sentinel整合Springboot实战](#1534-sentinel整合springboot实战)
    - [15.4. Redis内置高可用集群](#154-redis内置高可用集群)
        - [15.4.1. Redis集群搭建](#1541-redis集群搭建)
        - [15.4.2. 一致性Hash算法](#1542-一致性hash算法)
            - [一致性hash的特性](#一致性hash的特性)
        - [15.4.3. 一致性Hash算法虚拟节点](#1543-一致性hash算法虚拟节点)
        - [15.4.4. twemproxy实现hash分片](#1544-twemproxy实现hash分片)
- [16. 布隆过滤器](#16-布隆过滤器)
        - [16.0.5. 介绍](#1605-介绍)
        - [16.0.6. 使用场景](#1606-使用场景)
    - [16.1. 过滤器原理](#161-过滤器原理)
    - [16.2. goole布隆过滤器与Redis布隆过滤器](#162-goole布隆过滤器与redis布隆过滤器)
    - [布隆过滤器扩容](#布隆过滤器扩容)
    - [16.3. Redis布隆过滤器安装](#163-redis布隆过滤器安装)
    - [16.4. Redis布隆过滤器与springboot的整合探索](#164-redis布隆过滤器与springboot的整合探索)
- [redis的并发竞争问题(修改一个值,商品超卖)](#redis的并发竞争问题修改一个值商品超卖)
    - [解决方案](#解决方案)
        - [分布式锁](#分布式锁)
            - [数据库实现方式](#数据库实现方式)
            - [zookeeper实现](#zookeeper实现)
            - [redis实现](#redis实现)
        - [利用消息队列](#利用消息队列)
        - [用事务或者lua脚本实现乐观锁](#用事务或者lua脚本实现乐观锁)
        - [其他想法](#其他想法)
- [redis与数据库的一致性(缓存入库万一mysql失败了怎么办)](#redis与数据库的一致性缓存入库万一mysql失败了怎么办)
    - [读缓存时](#读缓存时)
    - [写缓存时](#写缓存时)
        - [为什么是删除缓存，而不是更新缓存？](#为什么是删除缓存而不是更新缓存)
    - [高并发的场景下，该解决方案要注意的问题：](#高并发的场景下该解决方案要注意的问题)
- [RDB原理再理解](#rdb原理再理解)
    - [rdbSave](#rdbsave)
    - [save && bgsave](#save--bgsave)
    - [bgsave](#bgsave)
    - [服务器的dirty](#服务器的dirty)
    - [rdbLoad](#rdbload)
- [lua脚本保证操作原子性](#lua脚本保证操作原子性)
- [雪花算法](#雪花算法)
    - [SnowFlake作用](#snowflake作用)
    - [雪花中的溢出问题](#雪花中的溢出问题)
    - [雪花中的冬令时问题(时钟回拨问题)](#雪花中的冬令时问题时钟回拨问题)
- [面试题](#面试题)
    - [修改配置不重启Redis会实时生效吗？](#修改配置不重启redis会实时生效吗)
    - [Redis的内存用完了会发生什么？](#redis的内存用完了会发生什么)
    - [redis常见性能问题和解决方案：](#redis常见性能问题和解决方案)

<!-- /TOC -->
# 1. 概述

Redis 是速度非常快的非关系型（NoSQL）内存键值数据库，可以存储键和五种不同类型的值之间的映射。

Redis为单进程单线程模式，采用队列模式将并发访问变为串行访问

键的类型只能为字符串，值支持五种数据类型：字符串、列表、集合、散列表、有序集合。

Redis 支持很多特性，例如将内存中的数据持久化到硬盘中，使用复制来扩展读性能，使用分片来扩展写性能。

# 2. 数据类型

| 数据类型 | 可以存储的值 | 操作 |
| :--: | :--: | :--: |
| STRING | 字符串、整数或者浮点数 | 对整个字符串或者字符串的其中一部分执行操作</br> 对整数和浮点数执行自增或者自减操作 |
| LIST | 列表 | 从两端压入或者弹出元素 </br> 对单个或者多个元素进行修剪，</br> 只保留一个范围内的元素 |
| SET | 无序集合 | 添加、获取、移除单个元素</br> 检查一个元素是否存在于集合中</br> 计算交集、并集、差集</br> 从集合里面随机获取元素 |
| HASH(map) | 包含键值对的无序散列表 | 添加、获取、移除单个键值对</br> 获取所有键值对</br> 检查某个键是否存在|
| ZSET | 有序集合 | 添加、获取、删除元素</br> 根据分值范围或者成员来获取元素</br> 计算一个键的排名 |

> [What Redis data structures look like](https://redislabs.com/ebook/part-1-getting-started/chapter-1-getting-to-know-redis/1-2-what-redis-data-structures-look-like/)

## 2.1. STRING

<div align="center"> <img src="pics/6019b2db-bc3e-4408-b6d8-96025f4481d6.png" width="400"/> </div><br>

```html
> set hello world
OK
> get hello
"world"
> del hello
(integer) 1
> get hello
(nil)
```

## 2.2. LIST

<div align="center"> <img src="pics/fb327611-7e2b-4f2f-9f5b-38592d408f07.png" width="400"/> </div><br>

```html
> rpush list-key item
(integer) 1
> rpush list-key item2
(integer) 2
> rpush list-key item
(integer) 3

> lrange list-key 0 -1
1) "item"
2) "item2"
3) "item"

> lindex list-key 1
"item2"

> lpop list-key
"item"

> lrange list-key 0 -1
1) "item2"
2) "item"
```

## 2.3. SET

<div align="center"> <img src="pics/cd5fbcff-3f35-43a6-8ffa-082a93ce0f0e.png" width="400"/> </div><br>

```html
> sadd set-key item
(integer) 1
> sadd set-key item2
(integer) 1
> sadd set-key item3
(integer) 1
> sadd set-key item
(integer) 0

> smembers set-key
1) "item"
2) "item2"
3) "item3"

> sismember set-key item4
(integer) 0
> sismember set-key item
(integer) 1

> srem set-key item2
(integer) 1
> srem set-key item2
(integer) 0

> smembers set-key
1) "item"
2) "item3"
```

## 2.4. HASH

<div align="center"> <img src="pics/7bd202a7-93d4-4f3a-a878-af68ae25539a.png" width="400"/> </div><br>

```html
> hset hash-key sub-key1 value1
(integer) 1
> hset hash-key sub-key2 value2
(integer) 1
> hset hash-key sub-key1 value1
(integer) 0

> hgetall hash-key
1) "sub-key1"
2) "value1"
3) "sub-key2"
4) "value2"

> hdel hash-key sub-key2
(integer) 1
> hdel hash-key sub-key2
(integer) 0

> hget hash-key sub-key1
"value1"

> hgetall hash-key
1) "sub-key1"
2) "value1"
```

## 2.5. ZSET

<div align="center"> <img src="pics/1202b2d6-9469-4251-bd47-ca6034fb6116.png" width="400"/> </div><br>

```html
> zadd zset-key 728 member1
(integer) 1
> zadd zset-key 982 member0
(integer) 1
> zadd zset-key 982 member0
(integer) 0

> zrange zset-key 0 -1 withscores
1) "member1"
2) "728"
3) "member0"
4) "982"

> zrangebyscore zset-key 0 800 withscores
1) "member1"
2) "728"

> zrem zset-key member1
(integer) 1
> zrem zset-key member1
(integer) 0

> zrange zset-key 0 -1 withscores
1) "member0"
2) "982"
```


## 2.6. 五种基本类型的底层实现TODO

# 3. 数据结构

## 3.1. 字典

dictht 是一个散列表结构，使用拉链法解决哈希冲突。

```c
/* This is our hash table structure. Every dictionary has two of this as we
 * implement incremental rehashing, for the old to the new table. */
typedef struct dictht {
    dictEntry **table;
    unsigned long size;
    unsigned long sizemask;
    unsigned long used;
} dictht;
```

```c
typedef struct dictEntry {
    void *key;
    union {
        void *val;
        uint64_t u64;
        int64_t s64;
        double d;
    } v;
    struct dictEntry *next;
} dictEntry;
```

Redis 的字典 dict 中包含两个哈希表 dictht，这是为了方便进行 rehash 操作。在扩容时，将其中一个 dictht 上的键值对 rehash 到另一个 dictht 上面，完成之后释放空间并交换两个 dictht 的角色。

```c
typedef struct dict {
    dictType *type;
    void *privdata;
    dictht ht[2];
    long rehashidx; /* rehashing not in progress if rehashidx == -1 */
    unsigned long iterators; /* number of iterators currently running */
} dict;
```

rehash 操作不是一次性完成，而是采用渐进方式，这是为了避免一次性执行过多的 rehash 操作给服务器带来过大的负担。

渐进式 rehash 通过记录 dict 的 rehashidx 完成，它从 0 开始，然后每执行一次 rehash 都会递增。例如在一次 rehash 中，要把 dict[0] rehash 到 dict[1]，这一次会把 dict[0] 上 table[rehashidx] 的键值对 rehash 到 dict[1] 上，dict[0] 的 table[rehashidx] 指向 null，并令 rehashidx++。

在 rehash 期间，每次对字典执行添加、删除、查找或者更新操作时，都会执行一次渐进式 rehash。

采用渐进式 rehash 会导致字典中的数据分散在两个 dictht 上，因此对字典的查找操作也需要到对应的 dictht 去执行。

```c
/* Performs N steps of incremental rehashing. Returns 1 if there are still
 * keys to move from the old to the new hash table, otherwise 0 is returned.
 *
 * Note that a rehashing step consists in moving a bucket (that may have more
 * than one key as we use chaining) from the old to the new hash table, however
 * since part of the hash table may be composed of empty spaces, it is not
 * guaranteed that this function will rehash even a single bucket, since it
 * will visit at max N*10 empty buckets in total, otherwise the amount of
 * work it does would be unbound and the function may block for a long time. */
int dictRehash(dict *d, int n) {
    int empty_visits = n * 10; /* Max number of empty buckets to visit. */
    if (!dictIsRehashing(d)) return 0;

    while (n-- && d->ht[0].used != 0) {
        dictEntry *de, *nextde;

        /* Note that rehashidx can't overflow as we are sure there are more
         * elements because ht[0].used != 0 */
        assert(d->ht[0].size > (unsigned long) d->rehashidx);
        while (d->ht[0].table[d->rehashidx] == NULL) {
            d->rehashidx++;
            if (--empty_visits == 0) return 1;
        }
        de = d->ht[0].table[d->rehashidx];
        /* Move all the keys in this bucket from the old to the new hash HT */
        while (de) {
            uint64_t h;

            nextde = de->next;
            /* Get the index in the new hash table */
            h = dictHashKey(d, de->key) & d->ht[1].sizemask;
            de->next = d->ht[1].table[h];
            d->ht[1].table[h] = de;
            d->ht[0].used--;
            d->ht[1].used++;
            de = nextde;
        }
        d->ht[0].table[d->rehashidx] = NULL;
        d->rehashidx++;
    }

    /* Check if we already rehashed the whole table... */
    if (d->ht[0].used == 0) {
        zfree(d->ht[0].table);
        d->ht[0] = d->ht[1];
        _dictReset(&d->ht[1]);
        d->rehashidx = -1;
        return 0;
    }

    /* More to rehash... */
    return 1;
}
```

## 3.2. 跳跃表

是有序集合的底层实现之一。

跳跃表是基于多指针有序链表实现的，可以看成多个有序链表。

<div align="center"> <img src="pics/beba612e-dc5b-4fc2-869d-0b23408ac90a.png" width="600px"/> </div><br>

在查找时，从上层指针开始查找，找到对应的区间之后再到下一层去查找。下图演示了查找 22 的过程。

<div align="center"> <img src="pics/0ea37ee2-c224-4c79-b895-e131c6805c40.png" width="600px"/> </div><br>

与红黑树等平衡树相比，跳跃表具有以下优点：

- 插入速度非常快速，因为不需要进行旋转等操作来维护平衡性；
- 更容易实现；
- 支持无锁操作。

# 4. 使用场景

## 4.1. 计数器

可以对 String 进行自增自减运算，从而实现计数器功能。

Redis 这种内存型数据库的读写性能非常高，很适合存储频繁读写的计数量。

## 4.2. 缓存

将热点数据放到内存中，设置内存的最大使用量以及淘汰策略来保证缓存的命中率。

## 4.3. 查找表

例如 DNS 记录就很适合使用 Redis 进行存储。

查找表和缓存类似，也是利用了 Redis 快速的查找特性。但是查找表的内容不能失效，而缓存的内容可以失效，因为缓存不作为可靠的数据来源。

## 4.4. 消息队列

List 是一个双向链表，可以通过 lpush 和 rpop 写入和读取消息

不过最好使用 Kafka、RabbitMQ 等消息中间件。

## 4.5. 会话缓存

可以使用 Redis 来统一存储多台应用服务器的会话信息。

当应用服务器不再存储用户的会话信息，也就不再具有状态，一个用户可以请求任意一个应用服务器，从而更容易实现高可用性以及可伸缩性。

## 4.6. 分布式锁实现

在分布式场景下，无法使用单机环境下的锁来对多个节点上的进程进行同步。

可以使用 Redis 自带的 SETNX 命令实现分布式锁，除此之外，还可以使用官方提供的 RedLock 分布式锁实现。

## 4.7. 其它

Set 可以实现交集、并集等操作，从而实现共同好友等功能。

ZSet 可以实现有序性操作，从而实现排行榜等功能。

# 5. Redis 与 Memcached

两者都是非关系型内存键值数据库，主要有以下不同：

## 5.1. 数据类型

Memcached 仅支持字符串类型的[k,v]，而 Redis 支持五种不同的数据类型，可以更灵活地解决问题。

## 5.2. 数据持久化

Redis 支持两种持久化策略：RDB 快照和 AOF 日志,rdb：属于全量数据备份，备份的是数据,aof：append only if,增量持久化备份，备份的是指令[如：set key， del key]

而 Memcached 不支持持久化。

## 5.3. 分布式

Memcached 不支持分布式，只能通过在客户端使用一致性哈希来实现分布式存储，这种方式在存储和查询时都需要先在客户端计算一次数据所在的节点。

Redis Cluster 实现了分布式的支持。

## 5.4. 内存管理机制

- 在 Redis 中，并不是所有数据都一直存储在内存中，可以将一些很久没用的 value 交换到磁盘，而 Memcached 的数据则会一直在内存中。

- Memcached 将内存分割成特定长度的块来存储数据，以完全解决内存碎片的问题。但是这种方式会使得内存的利用率不高，例如块的大小为 128 bytes，只存储 100 bytes 的数据，那么剩下的 28 bytes 就浪费掉了。而redis直接申请分配内存,会产生内存碎片.

## 5.5. 缓存过期机制

缓存数据过期机制  key，在一个小时之后过期，超过一个小时查数据就会查不到  key:"1",value:"2" 

1）Memcached 在删除失效主键时也是采用的消极方法，即 Memcached 内部也不会监视主键是否失效，而是在通过 Get访问主键时才会检查其是否已经失效

2）Redis 定时、定期等多种缓存失效机制，减少内存泄漏



# 6. 数据淘汰策略(如何保证Redis中的数据都是热点数据)

可以设置内存最大使用量，当内存使用量超出时，会施行数据淘汰策略。

## Redis 具体有 6 种淘汰策略：

| 策略 | 描述 |
| :--: | :--: |
| volatile-lru | 从已设置过期时间的数据集中挑选最近最少使用的数据淘汰 |
| volatile-ttl | 从已设置过期时间的数据集中挑选将要过期的数据淘汰 |
|volatile-random | 从已设置过期时间的数据集中任意选择数据淘汰 |
| allkeys-lru | 从所有数据集中挑选最近最少使用的数据淘汰 |
| allkeys-random | 从所有数据集中任意选择数据进行淘汰 |
| noeviction | 禁止驱逐数据 |

**Redis 4.0 引入了 volatile-lfu 和 allkeys-lfu 淘汰策略，LFU 策略通过统计访问频率，将访问频率最少的键值对淘汰。**

作为内存数据库，出于对性能和内存消耗的考虑，Redis 的淘汰算法实际实现上并非针对所有 key，而是抽样一小部分并且从中选出被淘汰的 key。

使用 Redis 缓存数据时，为了提高缓存命中率，需要保证缓存数据都是热点数据。可以将内存最大使用量设置为热点数据占用的内存量，然后启用 allkeys-lru 淘汰策略，将最近最少使用的数据淘汰。






##  Redis过期key清除策略

**简介：redis过期key清除策略分析**

- Redis如何淘汰过期的keys：   set name daniel 3600

  - 惰性删除  ：

    * 概念：当一些客户端尝试访问它时，key会被发现并主动的过期

    * 放任键过期不管，但是每次从键空间中获取键时，都检查取得的键是否过期，如果过期的话，就删除该键

    * 特点：**CPU友好**，但如果一个key不再使用，那么它会一直存在于内存中，造成浪费

  - 定时删除：

    * 概念：设置键的过期时间的同时，创建一个定时器（timer），让定时器在键的过期时间来临时，立即执行对键的删除操作  


  - 定期删除：

    * 隔一段时间，程序就对数据库进行一次检查，删除里面的过期键，至于要删除多少过期键，

      以及要检查多少个数据库，则由算法决定。 即设置一个定时任务，比如10分钟删除一次过期的key；间隔小则占用CPU,间隔大则浪费内存

    * 例如Redis每秒处理：

    1. 测试随机的20个keys进行相关过期检测。
    2. 删除所有已经过期的keys。
    3. 如果有多于25%的keys过期，重复步奏1.

  

Redis服务器实际使用的是惰性删除和定期删除两种策略：通过配合使用这两种删除策略，服务器可以很好地在合理使用CPU时间和避免浪费内存空间之间取得平衡。

惰性删除策略是怎么实现？通过expireIfNeeded函数，当我们操作key的时候进行判断key是否过期

定期删除策略是怎么实现的？通过activeExpireCycle函数，serverCron函数执行时，activeExpireCycle函数就会被调用，规定的时间里面分多次遍历服务器的expires字典随机检查一部分key的过期时间，并删除其中的过期key

# Redis过期细节
## 清除过期时间
生存时间可以通过使用 DEL 命令来删除整个 key 来移除，或者被 SET 和 GETSET 命令覆盖原来的数据，

也就是说，修改key对应的value和使用另外相同的key和value来覆盖以后，当前数据的生存时间不同。

比如说，对一个 key 执行INCR命令，对一个列表进行LPUSH命令，或者对一个哈希表执行HSET命令，这类操作都不会修改 key 本身的生存时间

## Redis键值2种失效方式

被动：

当客户端尝试获取key时，发现key超时逾期了，然后删掉。
但这不够，因为有些key可能永远不会被再次访问。
这就要用到主动。

主动：

Redis每秒钟进行10次的定时检查，从要失效的key集合中随机抽取，

# 7. 持久化

Redis 是内存型数据库，为了保证数据在断电后不会丢失，需要将内存中的数据持久化到硬盘上。

## 7.1. RDB 持久化

 RDB 文件是一个压缩的**二进制文件**，通过该文件可以还原生成 RDB 文件时的数据库状态

有两个命令可以生成 RDB 文件，一个是 SAVE、另一个是 BGSAVE。

 **两者的区别**在于：前者会阻塞 Redis 服务器进程，直到 RDB 文件创建完毕为止。而在服务器进程阻塞期间，服务器是不能处理任何命令请求的。后者则不会阻塞服务器进程，因为是通过 fork 一个子进程，并让其去创建 RDB 文件，而服务器进程（父进程）继续则继续处理命令请求。

当写完数据库状态后，**新 RDB 文件就会原子地替换旧的 RDB 文件**。


对于 RDB 持久化而言，我们一般都会使用 BGSAVE 来持久化，毕竟它不会阻塞服务器进程。

在 Redis 的配置文件，有提供设置服务器每隔多久时间来执行 BGSAVE 命令。


- 在Redis中RDB持久化的触发分为两种：自己手动触发与Redis定时触发

- 而自动触发的场景主要是有以下几点

  * 根据我们的 `save m n` 配置规则自动触发
  * save 900 1 // 900 秒内，对数据库至少修改 1 次。
  * 从节点全量复制时，主节点发送rdb文件给从节点完成复制操作，主节点会触发 `bgsave`
  * 执行 `debug reload` 时
  * 执行 `shutdown`时，如果没有开启aof，也会触发

- 禁用RDB
  - 只需在save的最后一行写上：save  ""

### rdb 的bgsave 很占用内存
RDB 快照的持久化，需要注意：在进行快照的时候（save），fork 出来进行 dump 操作的子进程会占用与父进程一样的内存，真正的 copy-on-write，对性能的影响和内存的耗用都是比较大的。比如机器 8G 内存，Redis 已经使用了 6G 内存，这时 save 的话会再生成 6G，变成 12G，大于系统的 8G。这时候会发生交换；要是虚拟内存不够则会崩溃，导致数据丢失。所以在用 redis 的时候一定对系统内存做好容量规划。

### rdb的原理  
父进程fork()一个子进程来进行数据备份,

**fork() 与 exec() 的区别**  

当前进程调用fork()，会创建一个跟当前进程完全相同的子进程(除了pid)，所以子进程同样是会执行fork()之后的代码。

exec函数的作用就是：装载一个新的程序（可执行映像）覆盖当前进程内存空间中的映像，从而执行不同的任务。exec系列函数在执行时会直接替换掉当前进程的地址空间。

<div align="center"> <img src=".\pictures\redis\Snipaste_2019-09-25_19-55-38.jpg " width="600px"> </div><br>

但大多数情况下,我们需要的是fork()出一个子进程,但这个子进程跟父进程做不一样的事情,这个时候很多拷贝来的数据都是用不到的,所以就用到了写时复制的技术:

fork创建出的子进程，与父进程共享内存空间。也就是说，如果子进程不对内存空间进行写入操作的话，内存空间中的数据并不会复制给子进程，这样创建子进程的速度就很快了！(不用复制，直接引用父进程的物理空间)。  
并且如果在fork函数返回之后，子进程第一时间exec一个新的可执行映像，那么也不会浪费时间和内存空间了

### 写时复制的原理
fork()之后，kernel把父进程中所有的内存页的权限都设为read-only，然后子进程的地址空间指向父进程。当父子进程都只读内存时，相安无事。当其中某个进程写内存时，CPU硬件检测到内存页是read-only的，于是触发页异常中断（page-fault），陷入kernel的一个中断例程。中断例程中，kernel就会把触发的异常的页复制一份，于是父子进程各自持有独立的一份。


Copy On Write技术好处是什么？

COW技术可减少分配和复制大量资源时带来的瞬间延时。  
COW技术可减少不必要的资源分配。比如fork进程时，并不是所有的页面都需要复制，父进程的代码段和只读数据段都不被允许修改，所以无需复制。  

Copy On Write技术缺点是什么？

如果在fork()之后，父子进程都还需要继续进行写操作，那么会产生大量的分页错误(页异常中断page-fault)，这样就得不偿失。


## 7.2. AOF 持久化

将写命令添加到 AOF 文件（Append Only File）的末尾。

使用 AOF 持久化需要设置同步选项，从而确保写命令同步到磁盘文件上的时机。这是因为对文件进行写入并不会马上将内容同步到磁盘上，而是先存储到缓冲区，然后由操作系统决定什么时候同步到磁盘。有以下同步选项：

| 选项 | 同步频率 |
| :--: | :--: |
| always | 每个写命令都同步 |
| everysec | 每秒同步一次 |
| no | 让操作系统来决定何时同步 |

- always 选项会严重减低服务器的性能；
- everysec 选项比较合适，可以保证系统崩溃时只会丢失一秒左右的数据，并且 Redis 每秒执行一次同步对服务器性能几乎没有任何影响；
- no 选项并不能给服务器性能带来多大的提升，而且也会增加系统崩溃时数据丢失的数量。`

随着服务器写请求的增多，AOF 文件会越来越大。Redis 提供了一种将 AOF 重写的特性，能够去除 AOF 文件中的冗余写命令。


AOF 持久化功能的实现可以分为 3 个步骤：命令追加、文件写入、文件同步

命令追加很好理解，就是将写命令追加到 AOF 缓冲区的末尾。

文件写入是缓冲区内容写到 AOF 文件，文件写入是将 AOF 文件保存到磁盘。

因为服务器在处理文件事件时，可能会发生写操作，使得一些内容会被追加到 AOF 缓冲区末尾。所以，在服务器每次结束一个事件循环之前 ，都会调用 flushAppendOnlyFile 方法。

这个方法执行以下两个工作： - WRITE：根据条件，将缓冲区内容写入到 AOF 文件。 - SAVE：根据条件，调用 fsync 或 fdatasync 函数，将 AOF 文件保存到磁盘中。

两个步骤都需要根据一定的条件来执行，而这些条件由 Redis 配置文件中的 appendfsync 选项来决定的，一共有三个选择： 1. appendfsync always：每执行一个命令保存一次 2. appendfsync everysec（默认，推荐）：每一秒钟保存一次 3. appendfsync no：不保存

appendfsync always：每次执行完一个命令之后， WRITE 和 SAVE 都会被执行  
appendfsync everysec：SAVE 原则上每隔一秒钟就会执行一次。  
appendfsync no：每次执行完一个命令之后， WRITE 会执行，SAVE 都会被忽略，只会在以下任意一种情况中被执行：  
Redis 被关闭  
AOF 功能被关闭  
系统的写缓存被刷新（可能是缓存已经被写满，或者定期保存操作被执行。完成依赖 OS 的写入，一般为 30 秒左右一次）  

## AOF 重写



1. 保存数据库的状态差异,导致命令可以合并

2. 子进程重写过程中,父进程创建重写缓冲区,子进程重写完成在附加上重写缓冲区的内容,形成新文件.



通过该功能来创建一个新的 AOF 文件来代替旧文件。并且两个文件所保存的数据库状态一样，但新文件不会包含任何冗余命令，所以新文件要比旧文件小得多。

而为什么新文件不会包含任何冗余命令呢？

**那是因为这个重写功能是通过读取服务器当前的数据库状态来实现的。虽然叫做「重写」，但并没有对旧文件进行任何读取修改**。

比如旧文件保存了对某个 key 有 4 个 set 命令，经过重写之后，新文件只会记录**最后一次对该 key 的 set 命令**。因此说新文件不会包含任何冗余命令

因为重写涉及到大量 IO 操作，所以 Redis 是**用子进程来实现**这个功能的，否则将会阻塞主进程。该子进程拥有父进程的数据副本，可以避免在使用锁的情况下，保证数据的安全性。

那么这里又会存在一个问题，子进程在重写过程中，服务器还在继续处理命令请求，新命令可能会对数据库进行修改，这会导致当前数据库状态和重写后的 AOF 文件，所保存的数据库状态不一致。

为了解决这个问题，Redis 设置了一个 **AOF 重写缓冲区** 。在子进程执行 AOF 重写期间，主进程需要执行以下三个步骤： 1. 执行客户端的请求命令 2. 将执行后的写命令追加到 AOF 缓冲区 3. 将执行后的写命令追加到 AOF 重写缓冲区

当子进程结束重写后，会向主进程发送一个信号，主进程接收到之后会调用信号处理函数执行以下步骤： 1. 将 AOF 重写缓冲区内容写入新的 AOF 文件中。此时新文件所保存的数据库状态就和当前数据库状态一致了 2. 对新文件进行改名，原子地覆盖现有 AOF 文件，完成新旧文件的替换。

当函数执行完成后，主进程就继续处理客户端命令。

因此，在整个 AOF 重写过程中，只有在执行信号处理函数时才会阻塞主进程，其他时候都不会阻塞。



## 7.3. Redis两种持久化方案对比

**首先注意一点: Master 上 Snapshot 和 AOF 都不做，来保证 Master 的读写性能，而 Slave 上则同时开启 Snapshot 和 AOF 来进行持久化，保证数据的安全性。**

- Redis提供了不同的持久性选项：

  - RDB持久性以指定的时间间隔执行数据集的时间点快照。
  - AOF持久性记录服务器接收的每个写入操作，将在服务器启动时再次播放，重建原始数据集。使用与Redis协议本身相同的格式以仅追加方式记录命令。当Redis太大时，Redis能够重写日志背景。

- RDB的优缺点

  * 优点：
    * RDB最大限度地提高了Redis的性能，父进程不需要参与磁盘I/O,而是fork出一个子进程来备份
    * 与AOF相比，RDB允许使用大数据集更快地重启
  * 缺点：
    * 如果您需要在Redis停止工作时（例如断电后）将数据丢失的可能性降至最低，则RDB并不好,因为它的备份时间长,备份数量大
    * RDB经常需要fork（）才能使用子进程持久存储在磁盘上。如果数据集很大，Fork（）可能会非常耗时

- AOF的优缺点

  * 优点：

    * 性能消耗更小
    * 可以修改追加命令,比如flushall,只需要停机把aof末尾的命令删除
    * AOF的日志易于理解 

  * 缺点：

    * AOF文件通常比同一数据集的等效RDB文件大
    * 根据确切的fsync策略，AOF可能比RDB慢

- RDB 和 AOF ,我应该用哪一个？
  一般来说,如果想达到足以媲美 PostgreSQL 的数据安全性， 你应该同时使用两种持久化功能。如果你非常关心你的数据,但仍然可以承受数分钟以内的数据丢失， 那么你可以只使用 RDB 持久化。有很多用户都只使用 AOF 持久化， 但我们并不推荐这种方式： 因为定时生成 RDB 快照（snapshot）非常便于进行数据库备份， 并且 RDB 恢复数据集的速度也要比 AOF 恢复的速度要快

  
* 在线上我们到底该怎么做？

  * RDB持久化与AOF持久化同步使用
  
  * 自己制定策略定期检查Redis的情况，然后可以手动触发备份、重写数据；

  * 采用集群和主从同步


# 8. 事务

一个事务包含了多个命令，服务器在执行事务期间，不会改去执行其它客户端的命令请求。

事务中的多个命令被一次性发送给服务器，而不是一条一条发送，这种方式被称为流水线，它可以减少客户端与服务器之间的网络通信次数从而提升性能。

Redis 最简单的事务实现方式是使用 MULTI 和 EXEC 命令将事务操作包围起来。


## 8.1. redis事务相关命令
1. MULTI 与 EXEC命令
以 MULTI 开始一个事务，然后将多个命令入队到事务中， 最后由 EXEC 命令触发事务， 一并执行事务中的所有命令
2. DISCARD命令
DISCARD 命令用于取消一个事务， 它清空客户端的整个事务队列， 然后将客户端从事务状态调整回非事务状态， 最后返回字符串 OK 给客户端， 说明事务已被取消。
3. WATCH命令
WATCH 命令用于在事务开始之前监视任意数量的键： 当调用 EXEC 命令执行事务时， 如果任意一个被监视的键已经被其他客户端修改了， 那么整个事务不再执行， 直接返回失败。


## 8.2. redis数据库事务的ACID与传统关系型事务的比较     

ACID，是指数据库管理系统（DBMS）在写入或更新资料的过程中，为保证事务（transaction）是正确可靠的，所必须具备的四个特性：原子性（atomicity，或称不可分割性）、一致性（consistency）、隔离性（isolation，又称独立性）、持久性（durability）。

### 8.2.1. 原子性（Atomicity）
一个事务（transaction）中的所有操作，或者全部完成，或者全部不完成，不会结束在中间某个环节。事务在执行过程中发生错误，会被回滚（Rollback）到事务开始前的状态，就像这个事务从来没有执行过一样。即，事务不可分割、不可约简。

单个 Redis 命令的执行是原子性的，但 Redis 没有在事务上增加任何维持原子性的机制，所以 Redis 事务的执行并不是原子性的。

如果一个事务队列中的所有命令都被成功地执行，那么称这个事务执行成功。

另一方面，如果 Redis 服务器进程在执行事务的过程中被停止,或者命令执行错误，那么剩余命令会继续执行,同时不提供回滚

通过内置的 Lua 解释器，可以使用 EVAL 命令对 Lua 脚本进行求值。Redis 使用单个 Lua 解释器去运行所有脚本，并且， **Redis 也保证脚本会以原子性(atomic)的方式执行：当某个脚本正在运行的时候，不会有其他脚本或 Redis 命令被执行。这和使用 MULTI / EXEC 包围的事务很类似**。在其他别的客户端看来，脚本的效果(effect)要么是不可见的(not visible)，要么就是已完成的(already completed)。



### 8.2.2. 一致性（Consistency）
定义: 在事务开始之前和事务结束以后，数据库的完整性没有被破坏。

Redis 的一致性问题可以分为三部分来讨论：入队错误、执行错误、Redis 进程被终结。

**入队错误**  
在命令入队的过程中，如果客户端向服务器发送了错误的命令，比如命令的参数数量不对，等等， 那么服务器将向客户端返回一个出错信息， 并且将客户端的事务状态设为 REDIS_DIRTY_EXEC 。

当客户端执行 EXEC 命令时， Redis 会拒绝执行状态为 REDIS_DIRTY_EXEC 的事务， 并返回失败信息。
```linux
redis 127.0.0.1:6379> MULTI
OK

redis 127.0.0.1:6379> set key
(error) ERR wrong number of arguments for 'set' command

redis 127.0.0.1:6379> EXISTS key
QUEUED

redis 127.0.0.1:6379> EXEC
(error) EXECABORT Transaction discarded because of previous errors.
```
因此，带有不正确入队命令的事务不会被执行，也不会影响数据库的一致性。

**执行错误**  

执行错误的命令,但对其他事务没有影响

如果命令在事务执行的过程中发生错误，比如说，对一个不同类型的 key 执行了错误的操作， 那么 Redis 只会将错误包含在事务的结果中， 这不会引起事务中断或整个失败，不会影响已执行事务命令的结果，也不会影响后面要执行的事务命令， 所以它对事务的一致性也没有影响。

**Redis 进程被终结**  
如果 Redis 服务器进程在执行事务的过程中被其他进程终结，或者被管理员强制杀死，那么根据 Redis 所使用的持久化模式，可能有以下情况出现：

内存模式：如果 Redis 没有采取任何持久化机制，那么重启之后的数据库总是空白的，所以数据总是一致的。

+ RDB 模式：在执行事务时，Redis 不会中断事务去执行保存 RDB 的工作，只有在事务执行之后，保存 RDB 的工作才有可能开始。所以当 RDB 模式下的 Redis 服务器进程在事务中途被杀死时，事务内执行的命令，不管成功了多少，都不会被保存到 RDB 文件里。恢复数据库需要使用现有的 RDB 文件，而这个 RDB 文件的数据保存的是最近一次的数据库快照（snapshot），所以它的数据可能不是最新的，但只要 RDB 文件本身没有因为其他问题而出错，那么还原后的数据库就是一致的。

+ AOF 模式：因为保存 AOF 文件的工作在后台线程进行，所以即使是在事务执行的中途，保存 AOF 文件的工作也可以继续进行，因此，根据事务语句是否被写入并保存到 AOF 文件，有以下两种情况发生：

    1）如果事务语句未写入到 AOF 文件，或 AOF 未被 SYNC 调用保存到磁盘，那么当进程被杀死之后，Redis 可以根据最近一次成功保存到磁盘的 AOF 文件来还原数据库，只要 AOF 文件本身没有因为其他问题而出错，那么还原后的数据库总是一致的，但其中的数据不一定是最新的。

    2）如果事务的部分语句被写入到 AOF 文件，并且 AOF 文件被成功保存，那么不完整的事务执行信息就会遗留在 AOF 文件里，当重启 Redis 时，程序会检测到 AOF 文件并不完整，Redis 会退出，并报告错误。需要使用 redis-check-aof 工具将部分成功的事务命令移除之后，才能再次启动服务器。还原之后的数据总是一致的，而且数据也是最新的（直到事务执行之前为止）。

### 8.2.3. 隔离性（Isolation）
隔离性:数据库允许多个并发事务同时对其数据进行读写和修改的能力，隔离性可以防止多个事务并发执行时由于交叉执行而导致数据的不一致。事务隔离分为不同级别，包括未提交读（Read uncommitted）、提交读（read committed）、可重复读（repeatable read）和串行化（Serializable）。

Redis 是单进程程序，并且它保证在执行事务时，不会对事务进行中断，事务可以运行直到执行完所有事务队列中的命令为止。因此，Redis 的事务是总是带有隔离性的。

### 8.2.4. 持久性（Durability）
因为事务不过是用队列包裹起了一组 Redis 命令，并没有提供任何额外的持久性功能，所以事务的持久性由 Redis 所使用的持久化模式决定：

在单纯的内存模式下，事务肯定是不持久的。

在 RDB 模式下，服务器可能在事务执行之后、RDB 文件更新之前的这段时间失败，所以 RDB 模式下的 Redis 事务也是不持久的。

在 AOF 的“总是 SYNC ”模式下，事务的每条命令在执行成功之后，都会立即调用 fsync 或 fdatasync 将事务数据写入到 AOF 文件。但是，这种保存是由后台线程进行的，主线程不会阻塞直到保存成功，所以从命令执行成功到数据保存到硬盘之间，还是有一段非常小的间隔，所以这种模式下的事务也是不持久的。

其他 AOF 模式也和“总是 SYNC ”模式类似，所以它们都是不持久的。


## 10.3. redis主从集群的搭建与docker通信知识
### 10.3.1. docker搭建redis主从集群
[docker搭建redis主从集群](https://blog.csdn.net/qq_28804275/article/details/80907796)  
[容器之间软连接通信](https://www.9ge6.com/archives/392.html)  
[容器之间的四种通信方式](https://www.cnkirito.moe/docker-network-bridge/)   
[link的底层原理](https://www.jianshu.com/p/21d66ca6115e)    
查看docker所有容器的ip: `docker inspect -f '{{.Name}} - {{.NetworkSettings.IPAddress }}' $(docker ps -aq)`    
改进点:

+ redis.conf的protected mode 关闭,不然会导致spring boot 访问失败
+ 同一主机的docker容器间的访问(redis里面设置127.0.0.1 会识别成自己容器内的网址,而不是宿主机的网址),创建slave容器的时候应该是`docker run --link redis-master:redis-master  -it --name redis-slave -v /root/:/usr/local/etc/redis -d -p 6301:6379 redis /bin/bash`,使用--link就可以使主从容器之间  
+  redis-slave.conf 里面的slaveof masterport使用master容器内部port 6379即可,不要用绑定到外部的port  

用link实现之后,又学到了自定义桥接模式  
[自定义桥接模式参考博客](https://www.cnkirito.moe/docker-network-bridge/)  
[桥接模式下的三种访问容器方式](https://birdben.github.io/2017/05/02/Docker/Docker%E5%AE%9E%E6%88%98%EF%BC%88%E4%BA%8C%E5%8D%81%E4%B8%83%EF%BC%89Docker%E5%AE%B9%E5%99%A8%E4%B9%8B%E9%97%B4%E7%9A%84%E9%80%9A%E4%BF%A1/)

自定义桥接模式内部互相暴露端口,可以通过别名访问,也可以对宿主机暴露端口;
但自定义网络内部不能访问宿主机下的端口服务

link不如自定义network  
link: hosts     
自定义network: 在容器内部创建了dns     


### 10.3.2. docker 搭建redis-sentinel集群
[docker搭建redis-sentinel集群](https://cloud.tencent.com/developer/article/1343834)  
改进点: 
+ 运行sentinel的时候把26000端口暴露出来,以供外界访问  
docker run -it --name redis-sentienl0container `-p 26000:26000 ` -v /root/:/usr/local/etc/redis/ -d  redis:4.0 /bin/bash

+ 自定义sentinel.conf的时候, master ip不要写localhost或者127.0.0.1 ; 而是要写服务器所在局域网的ip,比如 192.168.245.4;这样spring boot 通过sentinel连接redis节点的时候,就不会出现连接127.0.0.1:6300而找不到redis节点.




# 11. 订阅机制
1. PUBLISH将信息message发送到指定的频道channe1。返回收到消息的客户端数量
2. SUBSCRIBE订阅给指定频道的信息
3. UNSUBSCRTBE取消订阅指定的频道，如果不指定，则取消订阅所有的频道。


## 11.1. redis的消息订阅发布和mq对比？

答：redis发布订阅功能比较薄弱但比较轻量级，mq消息持久化，数据可靠性比较差，无后台功能可msgId、msgKey进行查询消息


# 12. redis作为二级缓存

## 12.1. 实现
导入ibatis,导入redis,在访问数据的时候,先访问redis是否存在,存在就返回,不存在就去mysql寻找,并放入缓存.

**常用注解:**

@CacheConfig(cacheNames="userInfoCache")  在同个redis里面必须唯一

@Cacheable(查) ： 
            来划分可缓存的方法 - 即，结果存储在缓存中的方法，以便在后续调用（具有相同的参数）时，返回缓存中的值而不必实际执行该方法

@CachePut（修改、增加） ：
            当需要更新缓存而不干扰方法执行时，可以使用@CachePut注释。也就是说，始终执行该方法并将其结果放入缓存中（根据@CachePut选项）

@CacheEvict（删除） ：
            对于从缓存中删除陈旧或未使用的数据非常有用，指示缓存范围内的驱逐是否需要执行而不仅仅是一个条目驱逐# 缓存雪崩


## 12.2. springboot cache自定义项

1)自定义KeyGenerator            

2)自定义cacheManager，设置缓存过期时间

3）自定义序列化方式，Jackson 

## 12.3. mysql与redis性能对比

redis缓存和数据库的对比

```
1、apache abtest
          ab是Apache HTTP server benchmarking tool，可以用以测试HTTP请求的服务器性能

2、abtest的安装
          1）yum install -y httpd-tools
          2）ab -V检验是否安装成功
3、ab -n1000 -c10 http://localhost:8080/getByCache?id=2
   ab -n1000 -c10 http://localhost:8080/getUser?id=2
         
         1）-n:进行http请求的总个数
         2）-c:请求的client个数，也就是请求并发数
         3)统计qps：qps即每秒并发数，request per second
    统计：10个并发的情况下
        redis qps：963.85[#/sec] (mean)
        DB qps： 766.75 [#/sec] (mean)
        
        100个并发的情况下 1000个
        redis qps：1130.60 [#/sec] (mean)
        DB qps：956.15 [#/sec] (mean) 
        
        
        100个并发的情况下，进行10000个请求
        redsi qps: 2102.39 [#/sec] (mean)
        DB qps: 679.07 [#/sec] (mean)
        500个并发的情况下，进行10000个请求
        redis qps：374.91 [#/sec] (mean)
        DB qps：扛不住
```

# 13. 缓存穿透与缓存雪崩
## 13.1. 缓存穿透
什么是缓存穿透？

缓存穿透是指用户查询数据，在数据库没有，自然在缓存中也不会有。这样就导致用户查询的时候，在缓存中找不到对应key的value，每次都要去数据库再查询一遍，然后返回空(相当于进行了两次无用的查询)。这样请求就绕过缓存直接查数据库

你有什么解决方案来防止缓存穿透？

1、缓存空值
如果一个查询返回的数据为空(不管是数据不 存在，还是系统故障)我们仍然把这个空结果进行缓存，但它的过期时间会很短，最长不超过五分钟。 通过这个直接设置的默认值存放到缓存，这样第二次到缓冲中获取就有值了，而不会继续访问数据库    

2、采用布隆过滤器BloomFilter  优势占用内存空间很小，bit存储。性能特别高。   
将所有可能存在的数据哈希到一个足够大的 bitmap 中，一个一定不存在的数据会被这个bitmap 拦截掉，从而避免了对底层存储系统的查询压力


## 13.2. 缓存雪崩

如果缓存集中在一段时间内失效，发生大量的缓存穿透，所有的查询都落在数据库上，造成了缓存雪崩。

由于原有缓存失效，新缓存未到期间所有原本应该访问缓存的请求都去查询数据库了，而对数据库CPU 和内存造成巨大压力，严重的会造成数据库宕机 

## 13.3. 缓存雪崩解决方案  

1、加锁排队   
key： whiltList  
value：1000w个uid   
指定setNxwhiltList value nullValue    
mutex互斥锁解决，Redis的SETNX去set一个mutex key，  
当操作返回成功时，再进行load db的操作并回设缓存；  
否则，就重试整个get缓存的方法  

2、数据预热  
缓存预热就是系统上线后，将相关的缓存数据直接加载到缓存系统。这样就可以避免在用户请求的时候，先查询数据库，然后再将数据缓存的问题!用户直接查询事先被预热的缓存数据!  
可以通过缓存reload机制，预先去更新缓存，再即将发生大并发访问前手动触发加载缓存不同的key    
    
3、双层缓存策略  
C1为原始缓存，C2为拷贝缓存，C1失效时，可以访问C2，C1缓存失效时间设置为短期，C2设置为长期。

4、定时更新缓存策略  
失效性要求不高的缓存，容器启动初始化加载，采用定时任务更新或移除缓存

5、设置不同的过期时间，让缓存失效的时间点尽量均匀

# 14. redis实现分布式锁(解决不同客户端并发操作某个数据)

## 14.1. 分布式锁是什么

* * 分布式锁是控制分布式系统或不同系统之间共同访问共享资源的一种锁实现

  * 如果不同的系统或同一个系统的不同主机之间共享了某个资源时，往往通过互斥来防止彼此干扰。

* 分布锁设计目的

 可以保证在分布式部署的应用集群中，同一个方法在同一操作只能被一台机器上的一个线程执行。

* 设计要求
  * 这把锁要是一把可重入锁（避免死锁）
  * 这把锁有高可用的获取锁和释放锁功能
  * 这把锁获取锁和释放锁的性能要好… 

## 14.2. 分布锁实现方案分析    
  * 获取锁的时候，使用 setnx(SETNX key val:当且仅当 key 不存在时，set 一个 key 为 val 的字符串，返回 1;  
  * 若 key 存在，则什么都不做，返回 【0】加锁，锁的 value 值为当前占有锁服务器内网IP编号拼接任务标识  
  * 在释放锁的时候进行判断。并使用 expire 命令为锁添 加一个超时时间，超过该时间则自动释放锁。 
  * 返回1则成功获取锁。还设置一个获取的超时时间， 若超过这个时间则放弃获取锁。setex（key,value,expire）过期以秒为单位  
  * 释放锁的时候，判断是不是该锁（即Value为当前服务器内网IP编号拼接任务标识），若是该锁，则执行 delete 进行锁释放 


### 14.2.1. 分布式锁面试题

**简介：分析支付宝2018分布式锁面试题**

- 问题1：
      1、什么是分布式锁？

  * 首先，为了确保分布式锁可用，我们至少要确保锁的实现同时满足以下三个条件：
    * 互斥性。在任意时刻，只有一个客户端能持有锁。
    * 不会发生死锁。即使有一个客户端在持有锁的期间崩溃而没有主动解锁，也能保证后续其他客户端能加锁。
    * 解铃还须系铃人。加锁和解锁必须是同一个客户端，客户端自己不能把别人加的锁给解了

- * 从 Redis 2.6.0 版本开始，通过内置的 Lua 解释器，可以使用 EVAL 命令对 Lua 脚本进行求值。

  - Redis 使用单个 Lua 解释器去运行所有脚本，并且， Redis 也保证脚本会以原子性(atomic)的方式执行：当某个脚本正在运行的时候，不会有其他脚本或 Redis 命令被执行。这和使用 MULTI / EXEC 包围的事务很类似。在其他别的客户端看来，脚本的效果(effect)要么是不可见的(not visible)，要么就是已完成的(already completed)。

- 问题2：怎么实现分布式锁

  - 实现分布式锁的方案大概有两种
    * 采用lua脚本操作分布式锁
    * 采用setnx、setex命令连用的方式实现分布式锁

- 问题3： 解锁需要注意什么

      解铃还须系铃人。加锁和解锁必须是同一个客户端，客户端自己不能把别人加的锁给解了


# 15. redis高可用集群

## 15.1. 主从复制的原理

- 进行复制中的主从服务器双方的数据库将保存相同的数据，概念上将这种现象称作“数据库状态一致”

- redis2.8版本之前使用旧版复制功能SYNC

  * SYNC是一个非常耗费资源的操作
    * 主服务器需要执行BGSAVE命令来生成RDB文件(一种数据持久化方式,全量持久化,与aof对应)，这个生成操作会耗费主服务器大量的的CPU、内存和磁盘读写资源
    * 主服务器将RDB文件发送给从服务器，这个发送操作会耗费主从服务器大量的网络带宽和流量，并对主服务器响应命令
    * 请求的时间产生影响：接收到RDB文件的从服务器在载入文件的过程是阻塞的，无法处理命令请求

- 2.8之后使用PSYNC

  - PSYNC命令具有完整重同步（full resynchronization）和部分重同步（partial resynchronization1）两种模式

    *  部分重同步功能由以下三个部分构成：
      * 主服务的复制偏移量（replication offset）和从服务器的复制偏移量
      * 主服务器的复制积压缓冲区（replication backlog）,默认大小为1M,里面存放着命令
      * 服务器的运行ID(run ID),用于存储服务器标识，如从服务器断线重新连接，取到主服务器的运行ID与重接后的主服务器运行ID进行对比，从而判断是执行部分重同步还是执行完整重同步


## 15.2. 分布式系统高可用

- 高可用的概念？

  - 高可用HA（High Availability）是分布式系统架构设计中必须考虑的因素之一，它通常是指，通过设计减少系统不能提供服务的时间。

- 通过三大要点解释高可用:

  - 单点是系统高可用的大敌，应该尽量在系统设计的过程中避免单点

  - 保证系统高可用，架构设计的核心准则是：冗余。

  - 每次出现故障需要人工介入恢复势必会增加系统的不可服务实践,实现自动故障转移

  - 重启服务，看服务是否每次都获取锁失败   

- 分布式高可用经典架构环节分析

  - 【客户端层】到【反向代理层】的高可用，是通过反向代理层的冗余来实现的。以nginx为例：有两台nginx，一台对线上提供服务，另一台冗余以保证高可用， 常见的实践是keepalived存活探测
  - 【反向代理层】到【web应用】的高可用，是通过站点层的冗余来实现的。假设反向代理层是nginx，nginx.conf里能够配置多个web后端，并且nginx能够探测到多个后端的存活性。
    * 自动故障转移：当web-server挂了的时候，nginx能够探测到，会自动的进行故障转移，将流量自动迁移到其他的web-server，整个过程由nginx自动完成，对调用方是透明的。
  * 【服务层】到【缓存层】的高可用，是通过缓存数据的冗余来实现的。redis天然支持主从同步，redis官方也有sentinel哨兵机制，来做redis的存活性检测。
  * 【服务层】到【数据库层】的高可用，数据库层用“主从同步，读写分离”架构，所以数据库层的高可用，又分为“读库高可用”与“写库高可用”两类。
    * 读库采用冗余的方式实现高可用，写库采用keepalived存活探测   binlog进行同步


## 数据迁移的三种方式
1. rdb数据备份恢复方法
redis 127.0.0.1:6379> SAVE
OK
或者
redis-cli -h 127.0.0.1 -p 6379 -a pwd bgsave
该命令将在 redis 安装目录中创建dump.rdb文件。

查找dump.rdb文件位置
redis 127.0.0.1:6379> CONFIG GET dir
1) “dir”
2) “/usr/local/redis/bin”
以上命令 CONFIG GET dir 输出的 redis 安装目录为 /usr/local/redis/bin。
bgsave
创建 redis 备份文件也可以使用命令 BGSAVE，该命令在后台执行。
127.0.0.1:6379> BGSAVE
Background saving started

2. AOF数据备份恢复方法
另一种持久化方式AOF，在配置文件中打开[appendonly yes]。
AOF刷新日志到disk的规则：
appendfsync always #always 表示每次有写操作都进行同步，非常慢，非常安全。
appendfsync everysec #everysec表示对写操作进行累积，每秒同步一次
官方的建议的everysec，安全，就是速度不够快，如果是机器出现问题可能会丢失1秒的数据。

也可以手动执行bgrewriteaof进行AOF备份：
redis-cli -h 127.0.0.1 -p 6379 -a pwd bgrewriteaof

迁移数据恢复
迁移到另外一台恢复数据，需先检查配置文件，将按照以下优先级恢复数据到内存：
如果只配置AOF,重启时加载AOF文件恢复数据；
如果同时 配置了RBD和AOF,启动是只加载AOF文件恢复数据；
如果只配置RBD,启动是讲加载dump文件恢复数据；

dump.rdb或者AOF文件迁移到另外一台恢复数据
恢复数据，只需将备份文件 (dump.rdb或者AOF文件) 移动到 redis 安装目录并启动服务即可。

3. 挂redis从库复制数据方法
Redis提供了复制（replication）功能可以自动实现同步的过程。
配置方法
通过配置文件 从数据库的配置文件中加入slaveof master-ip master-port，主数据库无需配置
通过命令行参数 启动redis-server的时候，使用命令行参数–slaveof master-ip master port
redis-server –port 6380 –slaveof 127.0.0.1 6379
通过命令SLAVEOF master-ip master-port
redis>SLAVEOF 127.0.0.1 6379
SLAVEOF NO ONE可以是当前数据库停止接收其他数据库的同步，转成主Redis数据库，程序连接地址都改为新的redis库IP地址和端口。

## 15.3. Redis高可用架构Sentinel(解决宕机)
**sentinel(中文就是哨兵的意思)是为了主从之间的自动切换(自动高可用),cluster用于分散hash负载均衡**

**简介：互联网服务灾备故障转移，sentinel的配置**

* `Redis`主从复制可将主节点数据同步给从节点，从节点此时有两个作用：
  - 一旦主节点宕机，从节点作为主节点的备份可以随时顶上来。
  - 扩展主节点的读能力，分担主节点读压力。

  但是问题来了：

  - 一旦主节点宕机，从节点晋升成主节点，同时需要修改应用方的主节点地址，还需要命令所有从节点去复制新的主节点，整个过程需要人工干预。

- redis主节点挂掉之后应该怎么操作？命令模拟

  ```shell
   slaveof no one       # 取消主备，变更为主节点
  
   slaveof 新host  新节点  # 将其他节点设置为新主节点的备份节点
  ```

- Sentinel正是实现了这个功能

- 开启Sentinel配置       3主3从   3主6从

  ```powershell
  sentinel monitor mymaster 127.0.0.1 6379 1   
  sentinel down-after-milliseconds mymaster 10000
  sentinel failover-timeout mymaster 60000
  sentinel parallel-syncs mymaster 1
  
  ```
- 命令讲解

  * sentinel monitor mymaster 127.0.0.1 6379 1 名称为mymaster的主节点名，1表示将这个主服务器判断为失效至少需要 1个 Sentinel 同意 （只要同意 Sentinel 的数量不达标，自动故障迁移就不会执行）
  * down-after-milliseconds 选项指定了 Sentinel 认为服务器已经断线所需的毫秒数
  *  failover过期时间，当failover开始后，在此时间内仍然没有触发任何failover操作，当前sentinel  将会认为此次failoer失败
  * parallel-syncs 选项指定了在执行故障转移时， 最多可以有多少个从服务器同时对新的主服务器进行同步， 这个数字越小， 完成故障转移所需的时间就越长。

  * 如果从服务器被设置为允许使用过期数据集， 那么你可能不希望所有从服务器都在同一时间向新的主服务器发送同步请求， 因为尽管复制过程的绝大部分步骤都不会阻塞从服务器， 但从服务器在载入主服务器发来的 RDB 文件时， 仍然会造成从服务器在一段时间内不能处理命令请求： 如果全部从服务器一起对新的主服务器进行同步， 那么就可能会造成所有从服务器在短时间内全部不可用的情况出现。

- 启动所有主从上的sentinel   

  *  前提是它们各自的server已成功启动
        cd /usr/local/redis/src/redis-sentinel /etc/redis/sentinel.conf

- info Replication  查看节点信息

- shutdown主节点看服务是否正常

### 15.3.1. Sentinel三大工作任务 

  - 监控（Monitoring）： Sentinel 会不断地检查你的主服务器和从服务器是否运作正常。
  - 提醒（Notification）： 当被监控的某个 Redis 服务器出现问题时， Sentinel 可以通过 API 向管理员或者其他应用程序发送通知。      
  - 自动故障迁移（Automatic failover）： 当一个主服务器不能正常工作时， Sentinel 会开始一次自动故障迁移操作， 它会将失效主服务器的其中一个从服务器升级为新的主服务器， 并让失效主服务器的其他从服务器改为复制新的主服务器； 当客户端试图连接失效的主服务器时， 集群也会向客户端返回新主服务器的地址， 使得集群可以使用新主服务器代替失效服务器。
  - 可以当成一个反向代理服务器来用
    

### 15.3.2. 冷备和热备

  * 冷备  

    *  概念：冷备份发生在数据库已经正常关闭的情况下，当正常关闭时会提供给我们一个完整的数据库
    * 优点：
      * 是非常快速的备份方法（只需拷文件）
      * 低度维护，高度安全
    * 缺点：
      - 单独使用时，只能提供到“某一时间点上”的恢复
      - 再实施备份的全过程中，数据库必须要作备份而不能作其他工作。也就是说，在冷备份过程中，数据库必须是关闭状态

  * 热备

    *   概念：热备份是在数据库运行的情况下，采用archivelog mode方式备份数据库的方法      

    *   优点：
      * 备份的时间短
      * 备份时数据库仍可使用
      * 可达到秒级恢复
    *   缺点  
      * 若热备份不成功，所得结果不可用于时间点的恢复
      * 因难于维护，所以要非凡仔细小心

  

### 15.3.3. Sentinel故障转移原理

**简介：Sentinel是怎么工作的？**

- 主观下线：

  - 概念主观下线（Subjectively Down， 简称 SDOWN）指的是单个 Sentinel 实例对服务器做出的下线判断

  - 主观下线特点：

    * 如果一个服务器没有在 master-down-after-milliseconds 选项所指定的时间内， 对向它发送 PING 命令的 Sentinel 返回一个有效回复（valid reply）， 那么 Sentinel 就会将这个服务器标记为主观下线

    * 服务器对 PING 命令的有效回复可以是以下三种回复的其中一种：
         ```shell
         返回 +PONG 。
         返回 -LOADING 错误。
         返回 -MASTERDOWN 错误。
         ```

- 客观下线

  - 客观下线概念：

    * 指的是多个 Sentinel 实例在对同一个服务器做出 SDOWN 判断， 并且通过 SENTINEL is-master-down-by-addr 命令互相交流之后， 得出的服务器下线判断。 （一个 Sentinel 可以通过向另一个 Sentinel 发送 SENTINEL is-master-down-by-addr 命令来询问对方是否认为给定的服务器已下线。）

  - 客观下线特点：

    * 从主观下线状态切换到客观下线状态并没有使用严格的法定人数算法（strong quorum algorithm）， 而是使用了流言协议： 如果 Sentinel 在给定的时间范围内， 从其他 Sentinel 那里接收到了足够数量的主服务器下线报告， 那么 Sentinel 就会将主服务器的状态从主观下线改变为客观下线。 如果之后其他 Sentinel 不再报告主服务器已下线， 那么客观下线状态就会被移除。

  - 客观下线注意点：
    * 客观下线条件只适用于主服务器： 对于任何其他类型的 Redis 实例， Sentinel 在将它们判断为下线前不需要进行协商， 所以从服务器或者其他 Sentinel 永远不会达到客观下线条件。 只要一个 Sentinel 发现某个主服务器进入了客观下线状态， 这个sentinel 就可能会被其他 Sentinel 推选出， 并对失效的主服务器执行自动故障迁移操作。

### 15.3.4. sentinel整合Springboot实战

**简介：Redis高可用整合springboot讲解**

- 思考？redis高可用sentinel整合springboot的步骤

   图解高可用架构代理的概念

- 引入yml配置文件 

  ```
  redis:
        sentinel:
           master: redis_master_group1    #mymaster
           nodes: 172.16.244.133:26379
  ```


- pom文件

  ```java
  <dependency>
  			<groupId>org.springframework.boot</groupId>
  			<artifactId>spring-boot-starter-data-redis</artifactId>
  </dependency>
  ```
- 启动springboot服务

- 登上服务器看配置是否有效

## 15.4. Redis内置高可用集群

### 15.4.1. Redis集群搭建

[我的github秒杀项目repository](https://github.com/sx89/seckill-practice)

### 15.4.2. 一致性Hash算法

[参考博客](https://juejin.im/post/5ae1476ef265da0b8d419ef2#heading-2)

  2的32次方进行hash取模     0到2的32次方-1

- jedis分布式之 ShardedJedisPool （一致性Hash分片算法）

- 概念：

  分布式系统中负载均衡的问题时候可以使用Hash算法让固定的一部分请求落到同一台服务器上，这样每台服务器固定处理一部分请求（并维护这些请求的信息），起到负载均衡的作用

- 做法：

  * hash环上顺时针从整数0开始，一直到最大正整数，我们根据四个ip计算的hash值肯定会落到这个hash环上的某一个点，至此我们把服务器的四个ip映射到了一致性hash环
  * 当用户在客户端进行请求时候，首先根据hash(用户id)计算路由规则（hash值），然后看hash值落到了hash环的那个地方，根据hash值在hash环上的位置顺时针找距离最近的ip作为路由ip

#### 一致性hash的特性
  - hash的特性
       * 单调性(Monotonicity)，单调性是指如果已经有一些请求通过哈希分派到了相应的服务器进行处理，又有新的服务器加入到系统中时候，应保证原有的请求可以被映射到原有的或者新的服务器中去，而不会被映射到原来的其它服务器上去。 

       * 分散性(Spread)：(同一个请求落到了多个服务器上,增大网络消耗,一致性hash具有很低的分散性) 分布式环境中，客户端请求时候可能不知道所有服务器的存在，可能只知道其中一部分服务器，在客户端看来他看到的部分服务器会形成一个完整的hash环。如果多个客户端都把部分服务器作为一个完整hash环，那么可能会导致，同一个用户的请求被路由到不同的服务器进行处理。这种情况显然是应该避免的，因为它不能保证同一个用户的请求落到同一个服务器。所谓分散性是指上述情况发生的严重程度。好的哈希算法应尽量避免尽量降低分散性。 一致性hash具有很低的分散性

       * 平衡性(Balance)：平衡性也就是说负载均衡，是指客户端hash后的请求应该能够分散到不同的服务器上去。一致性hash可以做到每个服务器都进行处理请求，但是不能保证每个服务器处理的请求的数量大致相同

- 新增节点

  * 在系统中增加一台服务器Node X,此时对象Object A、B、D不受影响，只有对象C需要重定位到新的Node X 。一般的，在一致性哈希算法中，如果增加一台服务器，则受影响的数据仅仅是新服务器到其环空间中前一台服务器（即沿着逆时针方向行走遇到的第一台服务器）之间数据，其它数据也不会受到影响。


### 15.4.3. 一致性Hash算法虚拟节点

**简介：通过虚拟节点倾斜解决方案，均匀一致性hash**

- 出现问题分析：

  ​    部门hash节点下架之后，虽然剩余机器都在处理请求，但是明显每个机器的负载不均衡，这样称为一致性hash的倾斜，虚拟节点的出现就是为了解决这个问题。

- 增设虚拟节点

     当物理机器数目为A，虚拟节点为B的时候，实际hash环上节点个数为A*B，将A节点分部为A1,A2,A3;将A1、A2、A3平均分布在各个位置，使A服务的节点尽量均匀分配在各个角落

     

     当物理机器数目为3，虚拟节点为ABCD的时候，实际hash环上节点个数为12个，将A节点分部为A1,A2,A3;将A1、A2、A3平均分布在各个位置，这样第一台物理机拥有的虚拟节点是 A1 B1 C1 D1 ;这样第二台物理机拥有的虚拟节点是 A2 B2 C2 D2 ;这样第3台物理机拥有的虚拟节点是 A3 B3 C3 D3.这样某一台物理机挂了,其他两台机器可以负载均衡. 

     

     当某个节点挂了之后，其数据均衡的分布给相邻的顺时针后面的一个节点上面，故所有数据比之前所述一致性hash相对均衡

### 15.4.4. twemproxy实现hash分片

**简介：经典服务端分片twemproxy特性讲解**

codis

- 概念：  

  ​    Twemproxy，也叫nutcraker。是一个twtter开源的一个redis和memcache快速/轻量级代理服务器; Twemproxy是一个快速的单线程代理程序，支持Memcached 和Redis。Redis代理中间件twemproxy是一种利用中间件做分片的技术。twemproxy处于客户端和服务器的中间，将客户端发来的请求，进行一定的处理后（sharding），再转发给后端真正的redis服务器

- 作用：

     Twemproxy通过引入一个代理层，可以将其后端的多台Redis或Memcached实例进行统一管理与分配，使应用程序只需要在Twemproxy上进行操作，而不用关心后面具体有多少个真实的Redis或Memcached存储

- 特性：
  * 支持失败节点自动删除
    * 可以设置重新连接该节点的时间
    * 可以设置连接多少次之后删除该节点
  * 减少了客户端直接与服务器连接的连接数量
    * 自动分片到后端多个redis实例上
  * 多种哈希算法
    * md5，crc16，crc32，crc32a，fnv1_64，fnv1a_64，fnv1_32，fnv1a_32，hsieh，murmur，jenkins 
  * 多种分片算法
    * ketama（一致性hash算法的一种实现），modula，random 



# 16. 布隆过滤器



### 16.0.5. 介绍


- 概念：

  - **布隆过滤器**（英语：Bloom Filter）是1970年由布隆提出的。它实际上是一个很长的[二进制](https://zh.wikipedia.org/wiki/%E4%BA%8C%E8%BF%9B%E5%88%B6)向量和一系列随机[映射函数](https://zh.wikipedia.org/wiki/%E6%98%A0%E5%B0%84)。布隆过滤器可以用于检索一个元素是否在一个集合中。它的优点是空间效率和查询时间都远远超过一般的算法，缺点是有一定的误识别率和删除困难。
- 优点:

  - 相比于其它的数据结构，布隆过滤器在空间和时间方面都有巨大的优势。布隆过滤器存储空间和插入/查询时间都是常数（{\displaystyle O(k)}![O(k)](https://wikimedia.org/api/rest_v1/media/math/render/svg/f5ec39041121b14e8c2b1a986c9b04547b223e3c)）。另外，散列函数相互之间没有关系，方便由硬件并行实现。布隆过滤器不需要存储元素本身，在某些对保密要求非常严格的场合有优势
- 缺点
  - 但是布隆过滤器的缺点和优点一样明显。误算率是其中之一。随着存入的元素数量增加，误算率随之增加。但是如果元素数量太少，则使用散列表足矣

### 16.0.6. 使用场景


**网页爬虫对URL的去重，避免爬取相同的URL地址；**

  **反垃圾邮件，从数十亿个垃圾邮件列表中判断某邮箱是否垃圾邮箱（同理，垃圾短信）；**

  **缓存击穿，将已存在的缓存放到布隆中，当黑客访问不存在的缓存时迅速返回避免缓存及DB挂掉。**

 **抢红包项目中,抢红包分为抢和拆,抢的一步可以用布隆过滤器,拆的一步,把抢的那些用户请求过滤掉**


## 16.1. 过滤器原理

<div align="center"> <img src=" .\pictures\redis\Snipaste_2019-09-11_16-59-45.jpg" width="650px"> </div><br>

以上图为例，具体的操作流程：假设集合里面有3个元素{x, y, z}，哈希函数的个数为3。首先将位数组进行初始化，将里面每个位都设置位0。对于集合里面的每一个元素，将元素依次通过3个哈希函数进行映射，每次映射都会产生一个哈希值，这个值对应位数组上面的一个点，然后将位数组对应的位置标记为1。查询W元素是否存在集合中的时候，同样的方法将W通过哈希映射到位数组上的3个点。如果3个点的其中有一个点不为1，则可以判断该元素一定不存在集合中。反之，如果3个点都为1，则该元素可能存在集合中。注意：此处不能判断该元素是否一定存在集合中，可能存在一定的误判率。可以从图中可以看到：假设某个元素通过映射对应下标为4，5，6这3个点。虽然这3个点都为1，但是很明显这3个点是不同元素经过哈希得到的位置，因此这种情况说明元素虽然不在集合中，也可能对应的都是1，这是误判率存在的原因。

无非是一些好人也被抓，这个可以通过给这些可伶的被误伤的设置个白名单就OK。但是能屏蔽大量坏人的作用是很重要的.

## 16.2. goole布隆过滤器与Redis布隆过滤器

布隆过滤器两种实现方案的优缺点分析

- google布隆过滤器的缺陷与思考

  基于内存布隆过滤器有什么特点

  - 内存级别产物
  - 重启即失效
  - 本地内存无法用在分布式场景
  - 不支持大数据量存储

- 需求分析步骤

  - 互联网功能需求分析
    - 这是一个抽奖程序，只针对会员用户有效
  - 抽离出功能所有api
  - 制定存储方案
  - 性能优化方案分析

- Redis布隆过滤器

  * 可扩展性Bloom过滤器
    * 一旦Bloom过滤器达到容量，就会在其上创建一个新的过滤器
  * 不存在重启即失效或者定时任务维护的成本
    * 基于goole实现的布隆过滤器需要启动之后初始化布隆过滤器
  * 缺点：
    * 需要网络IO,性能比基于内存的过滤器低

- 选择:

     优先基于数据量进行考虑
     
## 布隆过滤器扩容

因为布隆过滤器的不可逆，我们没法重新建一个更大的布隆过滤器然后去把数据重新导入。这边采取的扩容的方法是，保留原有的布隆过滤器，建立一个更大的，新增数据都放在新的布隆过滤器中，去重的时候检查所有的布隆过滤器。



## 16.3. Redis布隆过滤器安装


- Redis布隆过滤器安装过程    自己构建一个bitMap
- 
  * 您应该首先下载并编译模块：

    ```powershell
     $ git clone git：//github.com/RedisLabsModules/rebloom
     $ cd rebloom
     $ make
    ```

  * 将Rebloom加载到Redis中，在redis.conf里面添加

    ```powershell
    loadmodule /path/to/rebloom.so
    ```

  * 命令实战

    ```powershell
    BF.ADD bloom redis
    BF.EXISTS bloom redis
    BF.EXISTS bloom nonxist
    ```

## 16.4. Redis布隆过滤器与springboot的整合探索



**简介：基于lua脚本实现springboot和布隆过滤器的整合**

- 通过普通命令无法实现springboot整合布隆过滤器
- 查找github开源框架的流程
- 分析开源框架的实现原理
- 通过lua脚本自己实现布隆过滤器
- 编写两个lua脚本
  * 添加数据到指定名称的布隆过滤器
  * 从指定名称的布隆过滤器获取key是否存在的脚本


# redis的并发竞争问题(修改一个值,商品超卖)

Redis是一种单线程机制的nosql数据库，基于key-value，数据可持久化落盘。由于单线程所以Redis本身并没有锁的概念，多个客户端连接并不存在竞争关系，但是利用jedis等客户端对Redis进行并发访问时会出现问题。


## 解决方案
### 分布式锁

#### 数据库实现方式

#### zookeeper实现

#### redis实现

https://www.jianshu.com/p/3582556fe6f1

在类似秒杀这样的并发场景下，为了确保同一时刻只能允许一个用户访问资源，需要利用加锁的机制控制资源的访问权。如果服务只在单台机器上运行，可以简单地用一个内存变量进行控制。而在多台机器的系统上，则需要用分布式锁的机制进行并发控制。基于redis的一些特性，利用redis可以既方便又高效地模拟锁的实现。

**简单方案**

如果lock_key不存在，那么就设置lock_key的值为1，并且设置过期时间；如果lock_key存在，说明已经有人在使用这把锁，访问失败。

```python
def acquire_lock(lock_key, expire_timeout=60):
    if redis.setnx(lock_key, 1):
        redis.expire(lock_key, expire_timeout)
        return True
    return False
```
异常情况：如果setnx设置成功，但expire由于某些原因（比如超时）操作失败，那么这把锁就永远存在了，也就是所谓的死锁，后面的人永远无法访问这个资源。

**利用时间戳取值的方案**

为了解决死锁，我们可以利用setnx的value来做文章。上例中的我们设的value是1，其实并没有派上用场。因此可以考虑将value设为当前时间加上expire_timeout，当setnx设置失败后，我们去读lock_key的value，并且和当前时间作比对，如果当前时间大于value，那么资源理当被释放。代码示例如下：

```python
def acquire_lock(lock_key, expire_timeout=60):
    expire_time = int(time.time()) + expire_timeout
    if redis.setnx(lock_key, expire_time):
        redis.expire(lock_key, expire_timeout)
        return True
    redis_value = redis.get(lock_key)
    if redis_value and int(time.time()) > int(redis_value):
        redis.delete(lock_key)
    return False
```
然而仔细推敲下这段代码仍然能发现一些问题。第一，这个方案依赖时间，如果在分布式系统中的时间没有同步，则会对方案产生一定偏差。第二，假设C1和C2都没拿到锁，它们都去读value并对比时间，在竞态条件(race condition)下可能产生如下的时序：C1删除lock_key，C1获得锁，C2删除lock_key，C2获得锁。这样C1和C2同时拿到了锁，显然是不对的。

**GETSET改进方案**

GETSET指令在set新值的同时会返回老的值，这样的话我们可以检查返回的值，如果该值和之前读出来的值相同，那么这次操作有效，反之则无效。代码示例如下：

```python
def acquire_lock(lock_key, expire_timeout=60):
    expire_time = int(time.time()) + expire_timeout
    if redis.setnx(lock_key, expire_time):
        redis.expire(lock_key, expire_timeout)
        return True
    redis_value = redis.get(lock_key)
    if redis_value and int(time.time()) > int(redis_value):
        expire_time = int(time.time()) + expire_timeout
        old_value = redis.getset(lock_key, expire_time)
        if int(old_value) == int(redis_value):
            return True
    return False
```

这个方案基本可以满足要求，除了有一个小瑕疵，由于getset会去修改value，在竞态条件下可能会被修改多次导致timeout有细微的误差，但这个对结果影响不大。

**最终方法**

从redis 2.6.12版本开始有一个更为简便的方法。我们可以使用SET指令的扩展 ** SET key value [EX seconds] [PX milliseconds] [NX|XX] **，这个指令相当于对SETNX和EXPIRES进行了合并，因而我们的算法可以简化为如下一行：

```python
def acquire_lock(lock_key, expire_timeout=60):
    ret = redis.set(lock_key, int(time.time()), nx=True, ex=expire_timeout):
    return ret
```



### 利用消息队列
在并发量过大的情况下,可以通过消息中间件进行处理,把并行读写进行串行化。

把Redis.set操作放在队列中使其串行化,必须的一个一个执行。

这种方式在一些高并发的场景中算是一种通用的解决方案。

### 用事务或者lua脚本实现乐观锁
使用乐观锁的方式进行解决（成本较低，非阻塞，性能较高）

本质上是假设不会进行冲突，使用redis的命令watch进行构造条件。伪代码如下：

复制代码
watch price

get price $price

$price = $price + 10

multi

set price $price

exec
复制代码
解释一下：

watch这里表示监控该key值，后面的事务是有条件的执行，如果从watch的exec语句执行时，watch的key对应的value值被修改了，则事务不会执行。



###  其他想法
分布式锁  + 分段优化  +  一致性hash (只是请求的转移,几个主节点存储一样的数据)把redis进行负载均衡

过滤大量请求,   用略大于商品数量的用户进来, 将秒杀分成两段,少量请求先抢redis商品数量才能下单mysql,redis原子加减分给商品数量,异步入库   有个问题是拿到号的不是真正的下单,会有少买问题,这个时候可以再把屏蔽的多余的号拿过来允许他们下单.  Mq异步入库


引入队列，然后将所有写DB操作在单队列中排队，完全串行处理。当达到库存阀值的时候就不在消费队列，并关闭购买功能。这就解决了超卖问题。



# redis与数据库的一致性(缓存入库万一mysql失败了怎么办)
## 读缓存时
读的时候，先读缓存，缓存没有的话，就读数据库，然后取出数据后放入缓存，同时返回响应。
## 写缓存时 
更新的时候，先更新数据库，然后再删除缓存。

### 为什么是删除缓存，而不是更新缓存？

原因很简单，很多时候，在复杂点的缓存场景，缓存不单单是数据库中直接取出来的值。

比如可能更新了某个表的一个字段，然后其对应的缓存，是需要查询另外两个表的数据并进行运算，才能计算出缓存最新的值的。

另外更新缓存的代价有时候是很高的。是不是说，每次修改数据库的时候，都一定要将其对应的缓存更新一份？也许有的场景是这样，但是对于比较复杂的缓存数据计算的场景，就不是这样了。如果你频繁修改一个缓存涉及的多个表，缓存也频繁更新。但是问题在于，这个缓存到底会不会被频繁访问到？

举个栗子，一个缓存涉及的表的字段，在 1 分钟内就修改了 20 次，或者是 100 次，那么缓存更新 20 次、100 次；但是这个缓存在 1 分钟内只被读取了 1 次，有大量的冷数据。实际上，如果你只是删除缓存的话，那么在 1 分钟内，这个缓存不过就重新计算一次而已，开销大幅度降低。用到缓存才去算缓存。

其实删除缓存，而不是更新缓存，就是一个 lazy 计算的思想，不要每次都重新做复杂的计算，不管它会不会用到，而是让它到需要被使用的时候再重新计算。像 mybatis，hibernate，都有懒加载思想。查询一个部门，部门带了一个员工的 list，没有必要说每次查询部门，都把里面的 1000 个员工的数据也同时查出来啊。80% 的情况，查这个部门，就只是要访问这个部门的信息就可以了。先查部门，同时要访问里面的员工，那么这个时候只有在你要访问里面的员工的时候，才会去数据库里面查询 1000 个员工。


问题：先更新数据库，再删除缓存。如果删除缓存失败了，那么会导致数据库中是新数据，缓存中是旧数据，数据就出现了不一致。


解决思路：先删除缓存，再更新数据库。如果数据库更新失败了，那么数据库中是旧数据，缓存中是空的，那么数据不会不一致。因为读的时候缓存没有，所以去读了数据库中的旧数据，然后更新到缓存中。

比较复杂的数据不一致问题分析
数据发生了变更，先删除了缓存，然后要去修改数据库，此时还没修改。一个请求过来，去读缓存，发现缓存空了，去查询数据库，查到了修改前的旧数据，放到了缓存中。随后数据变更的程序完成了数据库的修改。完了，数据库和缓存中的数据不一样了...

为什么上亿流量高并发场景下，缓存会出现这个问题？

只有在对一个数据在并发的进行读写的时候，才可能会出现这种问题。其实如果说你的并发量很低的话，特别是读并发很低，每天访问量就 1 万次，那么很少的情况下，会出现刚才描述的那种不一致的场景。但是问题是，如果每天的是上亿的流量，每秒并发读是几万，每秒只要有数据更新的请求，就可能会出现上述的数据库+缓存不一致的情况。

解决方案如下：

更新数据的时候，根据数据的唯一标识，将操作路由之后，发送到一个 jvm 内部队列中。读取数据的时候，如果发现数据不在缓存中，那么将重新执行“读取数据+更新缓存”的操作，根据唯一标识路由之后，也发送到同一个 jvm 内部队列中。

一个队列对应一个工作线程，每个工作线程串行拿到对应的操作，然后一条一条的执行。这样的话，一个数据变更的操作，先删除缓存，然后再去更新数据库，但是还没完成更新。此时如果一个读请求过来，没有读到缓存，那么可以先将缓存更新的请求发送到队列中，此时会在队列中积压，然后同步等待缓存更新完成。

这里有一个优化点，一个队列中，其实多个更新缓存请求串在一起是没意义的，因此可以做过滤，如果发现队列中已经有一个更新缓存的请求了，那么就不用再放个更新请求操作进去了，直接等待前面的更新操作请求完成即可。

待那个队列对应的工作线程完成了上一个操作的数据库的修改之后，才会去执行下一个操作，也就是缓存更新的操作，此时会从数据库中读取最新的值，然后写入缓存中。

如果请求还在等待时间范围内，不断轮询发现可以取到值了，那么就直接返回；如果请求等待的时间超过一定时长，那么这一次直接从数据库中读取当前的旧值。

## 高并发的场景下，该解决方案要注意的问题：

**读请求长时阻塞**  

由于读请求进行了非常轻度的异步化，所以一定要注意读超时的问题，每个读请求必须在超时时间范围内返回。

该解决方案，最大的风险点在于说，可能数据更新很频繁，导致队列中积压了大量更新操作在里面，然后读请求会发生大量的超时，最后导致大量的请求直接走数据库。务必通过一些模拟真实的测试，看看更新数据的频率是怎样的。


**读请求并发量过高**  

这里还必须做好压力测试，确保恰巧碰上上述情况的时候，还有一个风险，就是突然间大量读请求会在几十毫秒的延时 hang 在服务上，看服务能不能扛的住，需要多少机器才能扛住最大的极限情况的峰值。

但是因为并不是所有的数据都在同一时间更新，缓存也不会同一时间失效，所以每次可能也就是少数数据的缓存失效了，然后那些数据对应的读请求过来，并发量应该也不会特别大。

**多服务实例部署的请求路由**    

可能这个服务部署了多个实例，那么必须保证说，执行数据更新操作，以及执行缓存更新操作的请求，都通过 Nginx 服务器路由到相同的服务实例上。

比如说，对同一个商品的读写请求，全部路由到同一台机器上。可以自己去做服务间的按照某个请求参数的 hash 路由，也可以用 Nginx 的 hash 路由功能等等。

**热点商品的路由问题，导致请求的倾斜**

万一某个商品的读写请求特别高，全部打到相同的机器的相同的队列里面去了，可能会造成某台机器的压力过大。就是说，因为只有在商品数据更新的时候才会清空缓存，然后才会导致读写并发，所以其实要根据业务系统去看，如果更新频率不是太高的话，这个问题的影响并不是特别大，但是的确可能某些机器的负载会高一些

# RDB原理再理解

https://cgiirw.github.io/2018/09/13/Redis_RDB/


## rdbSave
保存是rdbSave()提供的，这个方法有点长，但并不复杂，主要功能如下：

创建临时文件，保存的过程中会先创建一个临时文件；

写入文件前的准备工作，包括初始化IO、设置校验、写入版本号等；

遍历数据库，写入临时文件；

将临时文件覆盖原RDB文件；

做好“善后工作”，比如考虑程序的鲁棒性，对各种可能情况设置好处理方法，并且保存/清除各种需要/不需要的数据，关闭打开的文件等等


在Redis中，可能调用rdbSave的命令包括flushallCommand、debugCommand、rdbSaveBackground、saveCommand、prepareForShutdown。下面就来研究直接保存和后台保存命令的实现。

## save && bgsave

首先看看save，Redis会调用saveCommand函数，它的全部代码：

```c
void saveCommand(redisClient *c) {
    // BGSAVE 已经在执行中，不能再执行 SAVE
    // 否则将产生竞争条件
    if (server.rdb_child_pid != -1) {
        addReplyError(c,"Background save already in progress");
        return;
    }
    // 执行
    if (rdbSave(server.rdb_filename) == REDIS_OK) {
        addReply(c,shared.ok);
    } else {
        addReply(c,shared.err);
    }
}
```

所以save不是任何时刻都可以执行成功，server.rdb_child_pid不等于-1说明存在执行bgsave的子进程，此时saveCommand()会直接返回。

从上面的代码可以看出，saveCommand()基本上就是简单封装了rdbSave函数。

## bgsave

再来看看bgsave，Redis会调用bgsaveCommand函数：

```c
void bgsaveCommand(redisClient *c) {
    // 不能重复执行 BGSAVE
    if (server.rdb_child_pid != -1) {
        addReplyError(c,"Background save already in progress");
    // 不能在 BGREWRITEAOF 正在运行时执行
    } else if (server.aof_child_pid != -1) {
        addReplyError(c,"Can't BGSAVE while AOF log rewriting is in progress");
    // 执行 BGSAVE
    } else if (rdbSaveBackground(server.rdb_filename) == REDIS_OK) {
        addReplyStatus(c,"Background saving started");
    } else {
        addReply(c,shared.err);
    }
}
```

不能重复执行，也不能在aof子进程存在的时候执行。具体核心代码又封装在rdbSaveBackground()中
```c
int rdbSaveBackground(char *filename) {
    pid_t childpid;
    long long start;
    if (server.rdb_child_pid != -1) return REDIS_ERR;
    // 记录后台保存前服务器状态
    server.dirty_before_bgsave = server.dirty;
    // 开始时间
    start = ustime();
    // 创建子进程
    if ((childpid = fork()) == 0) {
        int retval;
        /* Child */
        // 子进程不接收网络数据
        if (server.ipfd > 0) close(server.ipfd);
        if (server.sofd > 0) close(server.sofd);
        // 保存数据
        retval = rdbSave(filename);
        if (retval == REDIS_OK) {
            size_t private_dirty = zmalloc_get_private_dirty();
            if (private_dirty) {
                redisLog(REDIS_NOTICE,
                    "RDB: %lu MB of memory used by copy-on-write",
                    private_dirty/(1024*1024));
            }
        }
        // 退出子进程
        exitFromChild((retval == REDIS_OK) ? 0 : 1);
    } else {
        /* Parent */
        // 记录最后一次 fork 的时间
        server.stat_fork_time = ustime()-start;
        // 创建子进程失败时进行错误报告
        if (childpid == -1) {
            redisLog(REDIS_WARNING,"Can't save in background: fork: %s",
                strerror(errno));
            return REDIS_ERR;
        }
        redisLog(REDIS_NOTICE,"Background saving started by pid %d",childpid);
        // 记录保存开始的时间
        server.rdb_save_time_start = time(NULL);
        // 记录子进程的 id
        server.rdb_child_pid = childpid;
        // 在执行时关闭对数据库的 rehash
        // 避免 copy-on-write
        updateDictResizePolicy();
        return REDIS_OK;
    }
    return REDIS_OK; /* unreached */
}
```

调用fork()会创建新的进程 **(fork创建进程,是把进程完全拷贝一份)**，有三种情况：

fork()如果创建子进程成功，会返回0给子进程；  
fork()如果创建子进程成功，会返回子进程的pid号给父进程；  
fork()执行发生错误，会返回-1(此时创建新的进程失败，也就无所谓父进程子进程，返回给当前进程)；  

一旦childpid = fork()创建子进程成功，则从这个地方开始，将有父子两个进程同时向下按各自逻辑执行代码，下面考虑这种情况下的父子进程的情况：

—–子进程—–

对子进程来说，由于fork()返回了0给它，它会执行从上向下数第二个if语句的内容，断掉该进程的网络连接进而不接受新进的数据，调用rdbSave()进行存储，当它执行到exitFromChild()，内部通过_exit()就结束进程。


—–父进程—–

由于fork()会返回子进程的pid给父进程，所以父进程不会执行第二个if语句中的内容，它会向下执行else中的内容，进行一些日志记录啦，打印一些可能发生的错误报告啦等等，最后返回REDIS_OK。

Redis通过RDB进行持久化的保存功能基本解析完毕了，现在的问题是，bgsave命令执行后，后台保存是一个间断性实施的过程，这个功能如何实现的？

## 服务器的dirty

server.dirty用来记录更改过却没有被持久化的键的个数。

dbSaveBackground()在子进程创建之前执行server.dirty_before_bgsave = server.dirty;，是因为一旦子进程创建之后，由于dirty在父进程和子进程中都会被更改，而它更改后的值分别保存在各自进程拷贝的内存页中，所以要在之前记录一个dirty的锚点，方便后续使用。在之前的文章中，本人提到了操作系统的写时复制(COW)，Redis在这方面利用了操作系统的写时复制机制，下一篇文章将进一步阐述原理，这里只需要暂时知道，在dirty更改前，两个进程的dirty指针指向同一内存地址，一旦变更，那么父进程的dirty不再是子进程的dirty了。

## rdbLoad

Redis的main()会调用loadDataFromDisk()，而loadDataFromDisk()会调用rdbLoad，调用方式如下：

```c
void loadDataFromDisk(void) {
    long long start = ustime();
    // 如果开启了 AOF 功能，那么优先使用 AOF 文件来还原数据
    if (server.aof_state == REDIS_AOF_ON) {
        if (loadAppendOnlyFile(server.aof_filename) == REDIS_OK)
            redisLog(REDIS_NOTICE,"DB loaded from append only file: %.3f seconds",(float)(ustime()-start)/1000000);
    } else {
        // 在没有开启 AOF 功能时，才使用 RDB 来还原
        if (rdbLoad(server.rdb_filename) == REDIS_OK) {
            redisLog(REDIS_NOTICE,"DB loaded from disk: %.3f seconds",
                (float)(ustime()-start)/1000000);
        } else if (errno != ENOENT) {
            redisLog(REDIS_WARNING,"Fatal error loading the DB. Exiting.");
            exit(1);
        }
    }
}
```

RDB在后台保存时会利用操作系统写时复制机制，不至于在内存中完整复制一份要保存的数据，从而减少开销，


# lua脚本保证操作原子性
只满足了原子性的一个特性,执行完成的话,能保证全部执行.但不能保证在执行错误的情况下回滚.

redis的lua脚本类似于MULTI和EXEC,redis保证其中的命令的执行期间,不会插入其他的命令.

# 雪花算法


Twitter-Snowflake算法产生的背景相当简单，为了满足Twitter每秒上万条消息的请求，每条消息都必须分配一条唯一的id，这些id还需要一些大致的顺序（方便客户端排序），并且在分布式系统中不同机器产生的id必须不同。

<div align="center"> <img src=" .\pictures\redis\Snipaste_2019-09-21_12-12-20.jpg" width="600px"> </div><br>

1) 1位，不用。二进制中最高位为1的都是负数，但是我们生成的id一般都使用整数，所以这个最高位固定是0

2) 41位，用来记录时间戳（毫秒）。


3) 41位可以表示2^41−1个数字，如果只用来表示正整数（计算机中正数包含0），可以表示的数值范围是：0 至 2^41−1，减1是因为可表示的数值范围是从0开始算的，而不是1。
也就是说41位可以表示2^41−1个毫秒的值，转化成单位年则是(2^41−1)/(1000∗60∗60∗24∗365)=69年

4) 10位，用来记录工作机器id。
可以部署在2^10=1024个节点，包括5位datacenterId和5位workerId,5位（bit）可以表示的最大正整数是2^5−1=31，即可以用0、1、2、3、....31这32个数字，来表示不同的datecenterId或workerId

1) 12位，序列号，用来记录同毫秒内产生的不同id。
12位（bit）可以表示的最大正整数是2^12−1=4095，即可以用0、1、2、3、....4094这4095个数字，来表示同一机器同一时间截（毫秒)内产生的4095个ID序号

## SnowFlake作用

由于在Java中64bit的整数是long类型，所以在Java中SnowFlake算法生成的id就是long来存储的。

所有生成的id按时间趋势递增

整个分布式系统内不会产生重复id（因为有datacenterId和workerId来做区分）


代码:
```java
public class SnowFlake {

    /**
     * 起始的时间戳:这个时间戳自己随意获取，比如自己代码的时间戳
     */
    private final static long START_STMP = 1543903501000L;

    /**
     * 每一部分占用的位数
     */
    private final static long SEQUENCE_BIT = 12; //序列号占用的位数
    private final static long MACHINE_BIT = 5;  //机器标识占用的位数
    private final static long DATACENTER_BIT = 5;//数据中心占用的位数

    /**
     * 每一部分的最大值：先进行左移运算，再同-1进行异或运算；异或：相同位置相同结果为0，不同结果为1
     */
     /** 用位运算计算出最大支持的数据中心数量：31 */
    private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    
    /** 用位运算计算出最大支持的机器数量：31 */
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    
    /** 用位运算计算出12位能存储的最大正整数：4095 */
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
     
     /** 机器标志较序列号的偏移量 */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    
    /** 数据中心较机器标志的偏移量 */
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    
    /** 时间戳较数据中心的偏移量 */
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    private static long datacenterId;  //数据中心
    private static long machineId;    //机器标识
    private static long sequence = 0L; //序列号
    private static long lastStmp = -1L;//上一次时间戳

	 /** 此处无参构造私有，同时没有给出有参构造，在于避免以下两点问题：
	 	  1、私有化避免了通过new的方式进行调用，主要是解决了在for循环中通过new的方式调用产生的id不一定唯一问题问题，因为用于			 记录上一次时间戳的lastStmp永远无法得到比对；
	 	  2、没有给出有参构造在第一点的基础上考虑了一套分布式系统产生的唯一序列号应该是基于相同的参数
	  */
    private SnowFlake(){}

    /**
     * 产生下一个ID
     *
     * @return
     */
    public static synchronized long nextId() {
    	  /** 获取当前时间戳 */
        long currStmp = getNewstmp();
        
        /** 如果当前时间戳小于上次时间戳则抛出异常 */
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }
		 /** 相同毫秒内 */
        if (currStmp == lastStmp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
            
            		/** 获取下一时间的时间戳并赋值给当前时间戳 */
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }
		 /** 当前时间戳存档记录，用于下次产生id时对比是否为相同时间戳 */
        lastStmp = currStmp;


        return (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                | datacenterId << DATACENTER_LEFT      //数据中心部分
                | machineId << MACHINE_LEFT            //机器标识部分
                | sequence;                            //序列号部分
    }

    private static long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    private static long getNewstmp() {
        return System.currentTimeMillis();
    }

}
```
## 雪花中的溢出问题
当同一毫秒产生的序列号已经最大了,那就获取下一时间戳,来重新生成id;
## 雪花中的冬令时问题(时钟回拨问题)
我们所说的夏令时实际上包括两类：夏令时和冬令时

```
夏令时(1:00 -> 3:00 AM)
往后拨一个小时，直接从1点变到3点，也就是说我们要比原来提前一个小时和美国人开会。
冬令时(1:00 -> 1:00 -> 2:00 AM)
往前拨一个小时，要过两个1点，这时比平常晚一个小时。
```

由此可见夏令时从1点跳到3点在雪花算法中没有什么影响，但是在冬令时要经历两个相同的时间段并使用相同的时间戳和算法参数进行运算就要出问题了。   
解决方法:  
备份之前未使用的雪花id,分配给后来的机器用.  
https://www.jianshu.com/p/98c202f64652


# 面试题 

## 修改配置不重启Redis会实时生效吗？
针对运行实例，有许多配置选项可以通过 CONFIG SET 命令进行修改，而无需执行任何形式的重启。 从 Redis 2.2 开始，可以从 AOF 切换到 RDB 的快照持久性或其他方式而不需要重启 Redis。检索 ‘CONFIG GET *’ 命令获取更多信息。

但偶尔重新启动是必须的，如为升级 Redis 程序到新的版本，或者当你需要修改某些目前 CONFIG 命令还不支持的配置参数的时候。

## Redis的内存用完了会发生什么？

如果达到设置的上限，Redis的写命令会返回错误信息（但是读命令还可以正常返回。）或者你可以将Redis当缓存来使用配置淘汰机制，当Redis达到内存上限时会冲刷掉旧的内容。

## redis常见性能问题和解决方案：
(1) Master最好不要写内存快照，如果Master写内存快照，save命令调度rdbSave函数，会阻塞主线程的工作，当快照比较大时对性能影响是非常大的，会间断性暂停服务。

(2) 如果数据比较重要，某个Slave开启AOF备份数据，策略设置为每秒同步一次

(3) 为了主从复制的速度和连接的稳定性，Master和Slave最好在同一个局域网内

(4) 尽量避免在压力很大的主库上增加从库

(5) 主从复制不要用图状结构，用单向链表结构更为稳定，即：Master <- Slave1 <- Slave2 <- Slave3...这样的结构方便解决单点故障问题，实现Slave对Master的替换。如果Master挂了，可以立刻启用Slave1做Master，其他不变。




























































