
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
- [6. 键的过期时间](#6-键的过期时间)
- [7. 数据淘汰策略](#7-数据淘汰策略)
- [8. 持久化](#8-持久化)
    - [8.1. RDB 持久化](#81-rdb-持久化)
    - [8.2. AOF 持久化](#82-aof-持久化)
- [9. 事务](#9-事务)
    - [9.1. redis事务相关命令](#91-redis事务相关命令)
    - [9.2. redis数据库事务的ACID与传统关系型事务的比较](#92-redis数据库事务的acid与传统关系型事务的比较)
        - [9.2.1. 原子性（Atomicity）](#921-原子性atomicity)
        - [9.2.2. 一致性（Consistency）](#922-一致性consistency)
        - [9.2.3. 隔离性（Isolation）](#923-隔离性isolation)
        - [9.2.4. 持久性（Durability）](#924-持久性durability)
- [10. 事件](#10-事件)
    - [10.1. 文件事件](#101-文件事件)
    - [10.2. 时间事件](#102-时间事件)
    - [10.3. 事件的调度与执行](#103-事件的调度与执行)
- [11. 主从](#11-主从)
    - [11.1. 连接过程](#111-连接过程)
    - [11.2. 主从链](#112-主从链)
    - [11.3. redis主从集群的搭建与docker通信知识](#113-redis主从集群的搭建与docker通信知识)
        - [11.3.1. docker搭建redis主从集群](#1131-docker搭建redis主从集群)
        - [11.3.2. docker 搭建redis-sentinel集群](#1132-docker-搭建redis-sentinel集群)
- [12. 订阅机制](#12-订阅机制)
    - [12.1. redis的消息订阅发布和mq对比？](#121-redis的消息订阅发布和mq对比)
- [13. redis作为二级缓存](#13-redis作为二级缓存)
    - [13.1. 实现](#131-实现)
    - [13.2. springboot cache自定义项](#132-springboot-cache自定义项)
    - [13.3. mysql与redis性能对比](#133-mysql与redis性能对比)
- [14. 缓存穿透与缓存雪崩](#14-缓存穿透与缓存雪崩)
    - [14.1. 缓存穿透](#141-缓存穿透)
    - [14.2. 缓存雪崩](#142-缓存雪崩)
    - [14.3. 缓存雪崩解决方案](#143-缓存雪崩解决方案)
- [15. redis实现分布式锁](#15-redis实现分布式锁)
    - [15.1. 分布式锁是什么](#151-分布式锁是什么)
    - [15.2. 分布锁实现方案分析](#152-分布锁实现方案分析)
        - [15.3.6. 分布式锁面试题](#1536-分布式锁面试题)
- [16. redis高可用集群](#16-redis高可用集群)
    - [16.0.7. 读写分离的数据同步](#1607-读写分离的数据同步)
    - [16.0.8. 分布式系统高可用](#1608-分布式系统高可用)
    - [16.1. Redis高可用架构Sentinel](#161-redis高可用架构sentinel)
        - [16.1.1. 灾备切换Sentinel的使用](#1611-灾备切换sentinel的使用)
        - [16.1.2. 互联网高可用灾备以及Sentinel三大任务](#1612-互联网高可用灾备以及sentinel三大任务)
        - [16.1.3. Redis高可用Sentinel故障转移原理](#1613-redis高可用sentinel故障转移原理)
        - [16.1.4. sentinel整合Springboot实战](#1614-sentinel整合springboot实战)
    - [16.2. Redis内置高可用集群](#162-redis内置高可用集群)
        - [16.2.1. Redis集群搭建](#1621-redis集群搭建)
        - [16.2.2. Redis集群分片重哈希](#1622-redis集群分片重哈希)
    - [16.3. Redis高可用集群扩展](#163-redis高可用集群扩展)
        - [16.3.1. Redis集群知多少](#1631-redis集群知多少)
        - [16.3.2. 一致性Hash算法](#1632-一致性hash算法)
        - [16.3.3. 一致性Hash算法虚拟节点](#1633-一致性hash算法虚拟节点)
        - [16.3.4. twemproxy实现hash分片](#1634-twemproxy实现hash分片)
- [16.4. Redis缓存持久化策略](#164-redis缓存持久化策略)
        - [16.4.1. Redis RDB持久化原理](#1641-redis-rdb持久化原理)
        - [16.4.2. Redis AOF持久化原理](#1642-redis-aof持久化原理)
        - [16.4.3. Redis两种持久化方案对比](#1643-redis两种持久化方案对比)
        - [16.4.4. Redis过期key清除策略](#1644-redis过期key清除策略)
- [16.5. 布隆过滤器](#165-布隆过滤器)
        - [16.5.1. 寻衅滋事？先过了我这道关](#1651-寻衅滋事先过了我这道关)
        - [16.5.2. 了解布隆过滤器](#1652-了解布隆过滤器)
        - [16.5.3. 谷歌布隆过滤器实现会员转盘抽奖](#1653-谷歌布隆过滤器实现会员转盘抽奖)
        - [16.5.4. goole布隆过滤器与Redis布隆过滤器](#1654-goole布隆过滤器与redis布隆过滤器)
        - [16.5.5. Redis布隆过滤器安装](#1655-redis布隆过滤器安装)
        - [16.5.6. Redis布隆过滤器与springboot的整合探索](#1656-redis布隆过滤器与springboot的整合探索)

<!-- /TOC -->

# 1. 概述

Redis 是速度非常快的非关系型（NoSQL）内存键值数据库，可以存储键和五种不同类型的值之间的映射。

键的类型只能为字符串，值支持五种数据类型：字符串、列表、集合、散列表、有序集合。

Redis 支持很多特性，例如将内存中的数据持久化到硬盘中，使用复制来扩展读性能，使用分片来扩展写性能。

# 2. 数据类型

| 数据类型 | 可以存储的值 | 操作 |
| :--: | :--: | :--: |
| STRING | 字符串、整数或者浮点数 | 对整个字符串或者字符串的其中一部分执行操作</br> 对整数和浮点数执行自增或者自减操作 |
| LIST | 列表 | 从两端压入或者弹出元素 </br> 对单个或者多个元素进行修剪，</br> 只保留一个范围内的元素 |
| SET | 无序集合 | 添加、获取、移除单个元素</br> 检查一个元素是否存在于集合中</br> 计算交集、并集、差集</br> 从集合里面随机获取元素 |
| HASH | 包含键值对的无序散列表 | 添加、获取、移除单个键值对</br> 获取所有键值对</br> 检查某个键是否存在|
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

# 6. 键的过期时间

Redis 可以为每个键设置过期时间，当键过期时，会自动删除该键。

对于散列表这种容器，只能为整个键设置过期时间（整个散列表），而不能为键里面的单个元素设置过期时间。

# 7. 数据淘汰策略

可以设置内存最大使用量，当内存使用量超出时，会施行数据淘汰策略。

Redis 具体有 6 种淘汰策略：

| 策略 | 描述 |
| :--: | :--: |
| volatile-lru | 从已设置过期时间的数据集中挑选最近最少使用的数据淘汰 |
| volatile-ttl | 从已设置过期时间的数据集中挑选将要过期的数据淘汰 |
|volatile-random | 从已设置过期时间的数据集中任意选择数据淘汰 |
| allkeys-lru | 从所有数据集中挑选最近最少使用的数据淘汰 |
| allkeys-random | 从所有数据集中任意选择数据进行淘汰 |
| noeviction | 禁止驱逐数据 |

作为内存数据库，出于对性能和内存消耗的考虑，Redis 的淘汰算法实际实现上并非针对所有 key，而是抽样一小部分并且从中选出被淘汰的 key。

使用 Redis 缓存数据时，为了提高缓存命中率，需要保证缓存数据都是热点数据。可以将内存最大使用量设置为热点数据占用的内存量，然后启用 allkeys-lru 淘汰策略，将最近最少使用的数据淘汰。

Redis 4.0 引入了 volatile-lfu 和 allkeys-lfu 淘汰策略，LFU 策略通过统计访问频率，将访问频率最少的键值对淘汰。

# 8. 持久化

Redis 是内存型数据库，为了保证数据在断电后不会丢失，需要将内存中的数据持久化到硬盘上。

## 8.1. RDB 持久化

将某个时间点的所有数据都存放到硬盘上。

可以将快照复制到其它服务器从而创建具有相同数据的服务器副本。

如果系统发生故障，将会丢失最后一次创建快照之后的数据。

如果数据量很大，保存快照的时间会很长。

## 8.2. AOF 持久化

将写命令添加到 AOF 文件（Append Only File）的末尾。

使用 AOF 持久化需要设置同步选项，从而确保写命令同步到磁盘文件上的时机。这是因为对文件进行写入并不会马上将内容同步到磁盘上，而是先存储到缓冲区，然后由操作系统决定什么时候同步到磁盘。有以下同步选项：

| 选项 | 同步频率 |
| :--: | :--: |
| always | 每个写命令都同步 |
| everysec | 每秒同步一次 |
| no | 让操作系统来决定何时同步 |

- always 选项会严重减低服务器的性能；
- everysec 选项比较合适，可以保证系统崩溃时只会丢失一秒左右的数据，并且 Redis 每秒执行一次同步对服务器性能几乎没有任何影响；
- no 选项并不能给服务器性能带来多大的提升，而且也会增加系统崩溃时数据丢失的数量。

随着服务器写请求的增多，AOF 文件会越来越大。Redis 提供了一种将 AOF 重写的特性，能够去除 AOF 文件中的冗余写命令。

# 9. 事务

一个事务包含了多个命令，服务器在执行事务期间，不会改去执行其它客户端的命令请求。

事务中的多个命令被一次性发送给服务器，而不是一条一条发送，这种方式被称为流水线，它可以减少客户端与服务器之间的网络通信次数从而提升性能。

Redis 最简单的事务实现方式是使用 MULTI 和 EXEC 命令将事务操作包围起来。


## 9.1. redis事务相关命令
1. MULTI 与 EXEC命令
以 MULTI 开始一个事务，然后将多个命令入队到事务中， 最后由 EXEC 命令触发事务， 一并执行事务中的所有命令
2. DISCARD命令
DISCARD 命令用于取消一个事务， 它清空客户端的整个事务队列， 然后将客户端从事务状态调整回非事务状态， 最后返回字符串 OK 给客户端， 说明事务已被取消。
3. WATCH命令
WATCH 命令用于在事务开始之前监视任意数量的键： 当调用 EXEC 命令执行事务时， 如果任意一个被监视的键已经被其他客户端修改了， 那么整个事务不再执行， 直接返回失败。


## 9.2. redis数据库事务的ACID与传统关系型事务的比较     

ACID，是指数据库管理系统（DBMS）在写入或更新资料的过程中，为保证事务（transaction）是正确可靠的，所必须具备的四个特性：原子性（atomicity，或称不可分割性）、一致性（consistency）、隔离性（isolation，又称独立性）、持久性（durability）。

### 9.2.1. 原子性（Atomicity）
单个 Redis 命令的执行是原子性的，但 Redis 没有在事务上增加任何维持原子性的机制，所以 Redis 事务的执行并不是原子性的。

如果一个事务队列中的所有命令都被成功地执行，那么称这个事务执行成功。

另一方面，如果 Redis 服务器进程在执行事务的过程中被停止 —— 比如接到 KILL 信号、宿主机器停机，等等，那么事务执行失败。

当事务失败时，Redis 也不会进行任何的重试或者回滚动作。

### 9.2.2. 一致性（Consistency）
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
如果命令在事务执行的过程中发生错误，比如说，对一个不同类型的 key 执行了错误的操作， 那么 Redis 只会将错误包含在事务的结果中， 这不会引起事务中断或整个失败，不会影响已执行事务命令的结果，也不会影响后面要执行的事务命令， 所以它对事务的一致性也没有影响。


**Redis 进程被终结**  
如果 Redis 服务器进程在执行事务的过程中被其他进程终结，或者被管理员强制杀死，那么根据 Redis 所使用的持久化模式，可能有以下情况出现：

内存模式：如果 Redis 没有采取任何持久化机制，那么重启之后的数据库总是空白的，所以数据总是一致的。

+ RDB 模式：在执行事务时，Redis 不会中断事务去执行保存 RDB 的工作，只有在事务执行之后，保存 RDB 的工作才有可能开始。所以当 RDB 模式下的 Redis 服务器进程在事务中途被杀死时，事务内执行的命令，不管成功了多少，都不会被保存到 RDB 文件里。恢复数据库需要使用现有的 RDB 文件，而这个 RDB 文件的数据保存的是最近一次的数据库快照（snapshot），所以它的数据可能不是最新的，但只要 RDB 文件本身没有因为其他问题而出错，那么还原后的数据库就是一致的。

+ AOF 模式：因为保存 AOF 文件的工作在后台线程进行，所以即使是在事务执行的中途，保存 AOF 文件的工作也可以继续进行，因此，根据事务语句是否被写入并保存到 AOF 文件，有以下两种情况发生：

    1）如果事务语句未写入到 AOF 文件，或 AOF 未被 SYNC 调用保存到磁盘，那么当进程被杀死之后，Redis 可以根据最近一次成功保存到磁盘的 AOF 文件来还原数据库，只要 AOF 文件本身没有因为其他问题而出错，那么还原后的数据库总是一致的，但其中的数据不一定是最新的。

    2）如果事务的部分语句被写入到 AOF 文件，并且 AOF 文件被成功保存，那么不完整的事务执行信息就会遗留在 AOF 文件里，当重启 Redis 时，程序会检测到 AOF 文件并不完整，Redis 会退出，并报告错误。需要使用 redis-check-aof 工具将部分成功的事务命令移除之后，才能再次启动服务器。还原之后的数据总是一致的，而且数据也是最新的（直到事务执行之前为止）。

### 9.2.3. 隔离性（Isolation）
Redis 是单进程程序，并且它保证在执行事务时，不会对事务进行中断，事务可以运行直到执行完所有事务队列中的命令为止。因此，Redis 的事务是总是带有隔离性的。

### 9.2.4. 持久性（Durability）
因为事务不过是用队列包裹起了一组 Redis 命令，并没有提供任何额外的持久性功能，所以事务的持久性由 Redis 所使用的持久化模式决定：

在单纯的内存模式下，事务肯定是不持久的。

在 RDB 模式下，服务器可能在事务执行之后、RDB 文件更新之前的这段时间失败，所以 RDB 模式下的 Redis 事务也是不持久的。

在 AOF 的“总是 SYNC ”模式下，事务的每条命令在执行成功之后，都会立即调用 fsync 或 fdatasync 将事务数据写入到 AOF 文件。但是，这种保存是由后台线程进行的，主线程不会阻塞直到保存成功，所以从命令执行成功到数据保存到硬盘之间，还是有一段非常小的间隔，所以这种模式下的事务也是不持久的。

其他 AOF 模式也和“总是 SYNC ”模式类似，所以它们都是不持久的。


# 10. 事件

Redis 服务器是一个事件驱动程序。

## 10.1. 文件事件

服务器通过套接字与客户端或者其它服务器进行通信，文件事件就是对套接字操作的抽象。

Redis 基于 Reactor 模式开发了自己的网络事件处理器，使用 I/O 多路复用程序来同时监听多个套接字，并将到达的事件传送给文件事件分派器，分派器会根据套接字产生的事件类型调用相应的事件处理器。

<div align="center"> <img src="pics/9ea86eb5-000a-4281-b948-7b567bd6f1d8.png" width=""/> </div><br>

## 10.2. 时间事件

服务器有一些操作需要在给定的时间点执行，时间事件是对这类定时操作的抽象。

时间事件又分为：

- 定时事件：是让一段程序在指定的时间之内执行一次；
- 周期性事件：是让一段程序每隔指定时间就执行一次。

Redis 将所有时间事件都放在一个无序链表中，通过遍历整个链表查找出已到达的时间事件，并调用相应的事件处理器。

## 10.3. 事件的调度与执行

服务器需要不断监听文件事件的套接字才能得到待处理的文件事件，但是不能一直监听，否则时间事件无法在规定的时间内执行，因此监听时间应该根据距离现在最近的时间事件来决定。

事件调度与执行由 aeProcessEvents 函数负责，伪代码如下：

```python
def aeProcessEvents():
    # 获取到达时间离当前时间最接近的时间事件
    time_event = aeSearchNearestTimer()
    # 计算最接近的时间事件距离到达还有多少毫秒
    remaind_ms = time_event.when - unix_ts_now()
    # 如果事件已到达，那么 remaind_ms 的值可能为负数，将它设为 0
    if remaind_ms < 0:
        remaind_ms = 0
    # 根据 remaind_ms 的值，创建 timeval
    timeval = create_timeval_with_ms(remaind_ms)
    # 阻塞并等待文件事件产生，最大阻塞时间由传入的 timeval 决定
    aeApiPoll(timeval)
    # 处理所有已产生的文件事件
    procesFileEvents()
    # 处理所有已到达的时间事件
    processTimeEvents()
```

将 aeProcessEvents 函数置于一个循环里面，加上初始化和清理函数，就构成了 Redis 服务器的主函数，伪代码如下：

```python
def main():
    # 初始化服务器
    init_server()
    # 一直处理事件，直到服务器关闭为止
    while server_is_not_shutdown():
        aeProcessEvents()
    # 服务器关闭，执行清理操作
    clean_server()
```

从事件处理的角度来看，服务器运行流程如下：

<div align="center"> <img src="pics/c0a9fa91-da2e-4892-8c9f-80206a6f7047.png" width="350"/> </div><br>

# 11. 主从

通过使用 slaveof host port 命令来让一个服务器成为另一个服务器的从服务器。

一个从服务器只能有一个主服务器，并且不支持主主复制。

## 11.1. 连接过程

1. 主服务器创建快照文件，发送给从服务器，并在发送期间使用缓冲区记录执行的写命令。快照文件发送完毕之后，开始向从服务器发送存储在缓冲区中的写命令；

2. 从服务器丢弃所有旧数据，载入主服务器发来的快照文件，之后从服务器开始接受主服务器发来的写命令；

3. 主服务器每执行一次写命令，就向从服务器发送相同的写命令。

## 11.2. 主从链

随着负载不断上升，主服务器可能无法很快地更新所有从服务器，或者重新连接和重新同步从服务器将导致系统超载。为了解决这个问题，可以创建一个中间层来分担主服务器的复制工作。中间层的服务器是最上层服务器的从服务器，又是最下层服务器的主服务器。

<div align="center"> <img src="pics/395a9e83-b1a1-4a1d-b170-d081e7bb5bab.png" width="600"/> </div><br>

## 11.3. redis主从集群的搭建与docker通信知识
### 11.3.1. docker搭建redis主从集群
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


### 11.3.2. docker 搭建redis-sentinel集群
[docker搭建redis-sentinel集群](https://cloud.tencent.com/developer/article/1343834)  
改进点: 
+ 运行sentinel的时候把26000端口暴露出来,以供外界访问  
docker run -it --name redis-sentienl0container `-p 26000:26000 ` -v /root/:/usr/local/etc/redis/ -d  redis:4.0 /bin/bash

+ 自定义sentinel.conf的时候, master ip不要写localhost或者127.0.0.1 ; 而是要写服务器所在局域网的ip,比如 192.168.245.4;这样spring boot 通过sentinel连接redis节点的时候,就不会出现连接127.0.0.1:6300而找不到redis节点.




# 12. 订阅机制
1. PUBLISH将信息message发送到指定的频道channe1。返回收到消息的客户端数量
2. SUBSCRIBE订阅给指定频道的信息
3. UNSUBSCRTBE取消订阅指定的频道，如果不指定，则取消订阅所有的频道。


## 12.1. redis的消息订阅发布和mq对比？

答：redis发布订阅功能比较薄弱但比较轻量级，mq消息持久化，数据可靠性比较差，无后台功能可msgId、msgKey进行查询消息


# 13. redis作为二级缓存

## 13.1. 实现
导入ibatis,导入redis,在访问数据的时候,先访问redis是否存在,存在就返回,不存在就去mysql寻找,并放入缓存.

**常用注解:**

@CacheConfig(cacheNames="userInfoCache")  在同个redis里面必须唯一

@Cacheable(查) ： 
            来划分可缓存的方法 - 即，结果存储在缓存中的方法，以便在后续调用（具有相同的参数）时，返回缓存中的值而不必实际执行该方法

@CachePut（修改、增加） ：
            当需要更新缓存而不干扰方法执行时，可以使用@CachePut注释。也就是说，始终执行该方法并将其结果放入缓存中（根据@CachePut选项）

@CacheEvict（删除） ：
            对于从缓存中删除陈旧或未使用的数据非常有用，指示缓存范围内的驱逐是否需要执行而不仅仅是一个条目驱逐# 缓存雪崩


## 13.2. springboot cache自定义项

1)自定义KeyGenerator            

2)自定义cacheManager，设置缓存过期时间

3）自定义序列化方式，Jackson 

## 13.3. mysql与redis性能对比

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

# 14. 缓存穿透与缓存雪崩
## 14.1. 缓存穿透
什么是缓存穿透？
 
缓存穿透是指用户查询数据，在数据库没有，自然在缓存中也不会有。这样就导致用户查询的时候，在缓存中找不到对应key的value，每次都要去数据库再查询一遍，然后返回空(相当于进行了两次无用的查询)。这样请求就绕过缓存直接查数据库

你有什么解决方案来防止缓存穿透？


1、缓存空值
如果一个查询返回的数据为空(不管是数据不 存在，还是系统故障)我们仍然把这个空结果进行缓存，但它的过期时间会很短，最长不超过五分钟。 通过这个直接设置的默认值存放到缓存，这样第二次到缓冲中获取就有值了，而不会继续访问数据库    

2、采用布隆过滤器BloomFilter  优势占用内存空间很小，bit存储。性能特别高。   
将所有可能存在的数据哈希到一个足够大的 bitmap 中，一个一定不存在的数据会被这个bitmap 拦截掉，从而避免了对底层存储系统的查询压力


## 14.2. 缓存雪崩

如果缓存集中在一段时间内失效，发生大量的缓存穿透，所有的查询都落在数据库上，造成了缓存雪崩。

由于原有缓存失效，新缓存未到期间所有原本应该访问缓存的请求都去查询数据库了，而对数据库CPU 和内存造成巨大压力，严重的会造成数据库宕机 

## 14.3. 缓存雪崩解决方案  

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

# 15. redis实现分布式锁

## 15.1. 分布式锁是什么

* * 分布式锁是控制分布式系统或不同系统之间共同访问共享资源的一种锁实现

  * 如果不同的系统或同一个系统的不同主机之间共享了某个资源时，往往通过互斥来防止彼此干扰。

* 分布锁设计目的

​ 可以保证在分布式部署的应用集群中，同一个方法在同一操作只能被一台机器上的一个线程执行。

* 设计要求
  * 这把锁要是一把可重入锁（避免死锁）
  * 这把锁有高可用的获取锁和释放锁功能
  * 这把锁获取锁和释放锁的性能要好… 

## 15.2. 分布锁实现方案分析    
  * 获取锁的时候，使用 setnx(SETNX key val:当且仅当 key 不存在时，set 一个 key 为 val 的字符串，返回 1;  
  * 若 key 存在，则什么都不做，返回 【0】加锁，锁的 value 值为当前占有锁服务器内网IP编号拼接任务标识  
  * 在释放锁的时候进行判断。并使用 expire 命令为锁添 加一个超时时间，超过该时间则自动释放锁。 
  * 返回1则成功获取锁。还设置一个获取的超时时间， 若超过这个时间则放弃获取锁。setex（key,value,expire）过期以秒为单位  
  * 释放锁的时候，判断是不是该锁（即Value为当前服务器内网IP编号拼接任务标识），若是该锁，则执行 delete 进行锁释放 


### 15.3.6. 分布式锁面试题

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


# 16. redis高可用集群

## 16.0.7. 读写分离的数据同步

- 进行复制中的主从服务器双方的数据库将保存相同的数据，概念上将这种现象称作“数据库状态一致”

RDB  全量持久化   AOF append only if  增量持久化

- redis2.8版本之前使用旧版复制功能SYNC

  * SYNC是一个非常耗费资源的操作
    * 主服务器需要执行BGSAVE命令来生成RDB文件，这个生成操作会耗费主服务器大量的的CPU、内存和磁盘读写资源
    * 主服务器将RDB文件发送给从服务器，这个发送操作会耗费主从服务器大量的网络带宽和流量，并对主服务器响应命令
    * 请求的时间产生影响：接收到RDB文件的从服务器在载入文件的过程是阻塞的，无法处理命令请求

- 2.8之后使用PSYNC

  - PSYNC命令具有完整重同步（full resynchronization）和部分重同步（partial resynchronization1）两种模式

    *  部分重同步功能由以下三个部分构成：
      * 主服务的复制偏移量（replication offset）和从服务器的复制偏移量
      * 主服务器的复制积压缓冲区（replication backlog）,默认大小为1M
      * 服务器的运行ID(run ID),用于存储服务器标识，如从服务器断线重新连接，取到主服务器的运行ID与重接后的主服务器运行ID进行对比，从而判断是执行部分重同步还是执行完整重同步

## 16.0.8. 分布式系统高可用

- 高可用的概念？

  - 高可用HA（High Availability）是分布式系统架构设计中必须考虑的因素之一，它通常是指，通过设计减少系统不能提供服务的时间。

- 通过三大要点解释高可用:

  ****************

  - 单点是系统高可用的大敌，应该尽量在系统设计的过程中避免单点

  - 保证系统高可用，架构设计的核心准则是：冗余。

  - 每次出现故障需要人工介入恢复势必会增加系统的不可服务实践,实现自动故障转移

  - 重启服务，看服务是否每次都获取锁失败   
------

​     

- 分布式高可用经典架构环节分析

    

  - 【客户端层】到【反向代理层】的高可用，是通过反向代理层的冗余来实现的。以nginx为例：有两台nginx，一台对线上提供服务，另一台冗余以保证高可用， 常见的实践是keepalived存活探测
  - 【反向代理层】到【web应用】的高可用，是通过站点层的冗余来实现的。假设反向代理层是nginx，nginx.conf里能够配置多个web后端，并且nginx能够探测到多个后端的存活性。
    * 自动故障转移：当web-server挂了的时候，nginx能够探测到，会自动的进行故障转移，将流量自动迁移到其他的web-server，整个过程由nginx自动完成，对调用方是透明的。

  * 【服务层】到【缓存层】的高可用，是通过缓存数据的冗余来实现的。
              redis天然支持主从同步，redis官方也有sentinel哨兵机制，来做redis的存活性检测。
  * 【服务层】到【数据库层】的高可用，数据库层用“主从同步，读写分离”架构，所以数据库层的高可用，又分为“读库高可用”与“写库高可用”两类。
    * 读库采用冗余的方式实现高可用，写库采用keepalived存活探测   binlog进行同步

  


## 16.1. Redis高可用架构Sentinel



### 16.1.1. 灾备切换Sentinel的使用
**sentinel是为了主从之间的自动切换(自动高可用),cluster用于分散hash负载均衡**
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

  



### 16.1.2. 互联网高可用灾备以及Sentinel三大任务

**简介：互联网冷备和热备讲解，Sentinel是怎么工作的？Sentinel三大工作任务是什么？**

- Sentinel三大工作任务 

  - 监控（Monitoring）： Sentinel 会不断地检查你的主服务器和从服务器是否运作正常。
  - 提醒（Notification）： 当被监控的某个 Redis 服务器出现问题时， Sentinel 可以通过 API 向管理员或者其他应用程序发送通知。      
  - 自动故障迁移（Automatic failover）： 当一个主服务器不能正常工作时， Sentinel 会开始一次自动故障迁移操作， 它会将失效主服务器的其中一个从服务器升级为新的主服务器， 并让失效主服务器的其他从服务器改为复制新的主服务器； 当客户端试图连接失效的主服务器时， 集群也会向客户端返回新主服务器的地址， 使得集群可以使用新主服务器代替失效服务器。
  - 可以当成一个反向代理服务器来用
    

- 互联网冷备和热备讲解，冷备和热备的特点分析

  * 冷备  

    *  概念：冷备份发生在数据库已经正常关闭的情况下，当正常关闭时会提供给我们一个完整的数据库
    * 优点：
      * 是非常快速的备份方法（只需拷文件）
      *  低度维护，高度安全
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

  

### 16.1.3. Redis高可用Sentinel故障转移原理

**简介：Sentinel是怎么工作的？**

- 主观下线：

  - 概念主观下线（Subjectively Down， 简称 SDOWN）指的是单个 Sentinel 实例对服务器做出的下线判断

  - 主管下线特点：

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

    * 客观下线条件只适用于主服务器： 对于任何其他类型的 Redis 实例， Sentinel 在将它们判断为下线前不需要进行协商， 所以从服务器或者其他 Sentinel 永远不会达到客观下线条件。 只要一个 Sentinel 发现某个主服务器进入了客观下线状态， 这个 Sentinel 就可能会被其他 Sentinel 推选出， 并对失效的主服务器执行自动故障迁移操作。

      

### 16.1.4. sentinel整合Springboot实战

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









## 16.2. Redis内置高可用集群

### 16.2.1. Redis集群搭建

**简介：Redis集群搭建实战，赠送Redis图文搭建教程**

- 安装redis

  * 处理步骤

  ```shell
  cd /usr/local/
  wget http://download.redis.io/releases/redis-4.0.6.tar.gz
  tar -zxvf redis-4.0.6.tar.gz
  cd redis-4.0.6
  make && make install
  ```

  

- 新建集群文件夹

  

  - 处理步骤

    ```shell
    cd /usr/local/
    mkdir redis_cluster
    cd redis_cluster
    mkdir 7000 7001 7002 7003 7004 7005
    cp /usr/local/redis-4.0.6/redis.conf  /usr/local/redis_cluster/7000  
    ```

    



- 修改redis_cluster/7000到redis_cluster/7005文件夹下面的Redis.conf

  *  处理步骤

```shell
daemonize    yes                          //redis后台运行
port  7000                                //端口7000,7002,7003
cluster-enabled  yes                      //开启集群  把注释#去掉
cluster-config-file  nodes.conf      //集群的配置  配置文件首次启动自动生成 7000,7001,7002
cluster-node-timeout  5000                //请求超时  设置5秒够了
appendonly  yes                           //aof日志开启  有需要就开启，它会每次写操作都记录一条日志
bind 127.0.0.1 172.16.244.144(此处为自己内网的ip地址，centos7下面采用ip addr来查看，其他系统试一下ifconfig查看，ip为)

```



- 在其他节点也修改完Redis.conf

  ​     

  * 处理步骤    

  ```shell
  cp /usr/local/redis_cluster/7000/redis.conf /usr/local/redis_cluster/7001
  cp /usr/local/redis_cluster/7000/redis.conf /usr/local/redis_cluster/7002
  cp /usr/local/redis_cluster/7000/redis.conf /usr/local/redis_cluster/7003
  cp /usr/local/redis_cluster/7000/redis.conf /usr/local/redis_cluster/7004
  cp /usr/local/redis_cluster/7000/redis.conf /usr/local/redis_cluster/7005
  
  ```




- 启动所有redis节点cd redis-server所在的路径

    * 处理步骤

    ```shell
    cp /usr/local/redis-4.0.6/src/redis-server /usr/local/ redis-cluster
    
    cd /usr/local/redis_cluster/7000 ../redis-server ./redis.conf
            
    cd /usr/local/redis-cluster/7001 ../redis-server ./redis.conf
            
    cd /usr/local/redis-cluster/7002 ../redis-server ./redis.conf
     
    cd /usr/local/redis-cluster/7003 ../redis-server ./redis.conf
            
    cd /usr/local/redis-cluster/7004 ../redis-server ./redis.conf
     
    cd /usr/local/redis-cluster/7005 ../redis-server ./redis.conf
    ```

     

- 创建集群

  * 前面已经准备好了搭建集群的redis节点，接下来我们要把这些节点都串连起来搭建集群。官方提供了一个工具：redis-trib.rb(/usr/local/redis-4.0.6/src/redis-trib.rb) 看后缀就知道不能直接执 行，它是用ruby写的一个程序，所以我们还得安装ruby.

  ```shell
    yum -y install ruby ruby-devel rubygems rpm-build 
             
    gem install redis
  ```


  ​     

- 如果gem install redis发现报错

    ```shell
    curl -L get.rvm.io | bash -s stable 
    
    source /usr/local/rvm/scripts/rvm
    
    rvm list known
    
    rvm install 2.3.3
    
    rvm use 2.3.3
    
    ruby --version
    
    gem install redis
    ```


​         

- 开启集群工作 

     ```shell
     cd /usr/local/redis-4.0.6/src

     设置集群的ip要设置局域网ip而不是本机,不然springboot在连接集群的时候,如果springboot主机跟集群不在同一个局域网内,就会导致springboot去127.0.0.1找集群却找不到

    (不推荐) ./redis-trib.rb create --replicas 1 127.0.0.1:7000 127.0.0.1:7001 \
           127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005
     (推荐) ./redis-trib.rb create --replicas 1 192.168.245.5:7000 192.168.245.5:7001 \
           192.168.245.5:7002 192.168.245.5:7003 192.168.245.5:7004 192.168.245.5:7005
     ```

- 测试集群是否正常

     ```
     ./redis-cli -c -p 7000 
     ```

- 如果搭建失败，请用此命令将所有启动的redis server一个个关闭掉

     ```
     ./redis-cli -p 7000 shutdown
     ```



### 16.2.2. Redis集群分片重哈希

**简介：玩转Redis集群节点分片重哈希**

- 采用SSH连接远程服务器

  - ssh命令安装过程：https://blog.csdn.net/DanielAntony/article/details/87997574

    

- 集群重新分片

     手动处理solt节点槽重新分片

  - ./redis-trib.rb reshard 127.0.0.1:7000
  - 你想移动多少个槽( 从1 到 16384)?  all



- 添加一个新的主节点

     

  ```
  ./redis-trib.rb add-node 127.0.0.1:7006 127.0.0.1:7000
  ```

  

- 添加一个新的从节点

  ```
  ./redis-trib.rb add-node --slave 127.0.0.1:7006 127.0.0.1:7000
  ```

  

- 移除一个节点

   ./redis-trib.rb del-node 127.0.0.1:7000 `<node-id>`
    第一个参数是任意一个节点的地址,第二个节点是你想要移除的节点地址。

  * 移除主节点【先确保节点里面没有slot】
    * 使用同样的方法移除主节点,不过在移除主节点前，需要确保这个主节点是空的. 如果不是空的,需要将这个节点的数据重新分片到其他主节点上.
    * 替代移除主节点的方法是手动执行故障恢复，被移除的主节点会作为一个从节点存在，不过这种情况下不会减少集群节点的数量，也需要重新分片数据.

  * 移除从节点  直接移除成功


 
## 16.3. Redis高可用集群扩展





### 16.3.1. Redis集群知多少

**简介：一起来进行Redis集群探索**

- 图解探索【是不是只学习master cluster就行了】

- 分析cluster集群方式原理

  

  - 水平切分于垂直切分相比，相对来说稍微复杂一些。因为要将同一个表中的不同数据拆分到不同的数据库中。 分片是一种基于数据库分成若干片段的传统概念扩容技术，它将数据库分割成多个碎片并将这些碎片放置在不同的服务器上。

  - 垂直切分的最大特点就是规则简单，实施也更为方便，尤其适合各业务之间的耦合度非常低，相互影响很小，业务逻辑非常清晰的系统。按照业务维度将不同数据放入不同的表

    

- 故障转移

  

  * 通过gossip节点信息同步实现，实现slave作为master的备份节点，并实现故障剔除master节点采用slave替换，并在替换完之后将结果通知到其他节点。

    

- Twitter推特公司twemproxy服务端分片和客户端分片集群解决方案



### 16.3.2. 一致性Hash算法

**简介：一起来进行Redis集群探索**

  2的32次方进行hash取模     0到2的32次方-1

- jedis分布式之 ShardedJedisPool （一致性Hash分片算法）

- 概念：

    分布式系统中负载均衡的问题时候可以使用Hash算法让固定的一部分请求落到同一台服务器上，这样每台服务器固定处理一部分请求（并维护这些请求的信息），起到负载均衡的作用

- 做法：

  * hash环上顺时针从整数0开始，一直到最大正整数，我们根据四个ip计算的hash值肯定会落到这个hash环上的某一个点，至此我们把服务器的四个ip映射到了一致性hash环
  * 当用户在客户端进行请求时候，首先根据hash(用户id)计算路由规则（hash值），然后看hash值落到了hash环的那个地方，根据hash值在hash环上的位置顺时针找距离最近的ip作为路由ip
  * 当用户在客户端进行请求时候，首先根据hash(用户id)计算路由规则（hash值），然后看hash值落到了hash环的那个地方，根据hash值在hash环上的位置顺时针找距离最近的ip作为路由ip.

-    一致性hash的特性

  * 单调性(Monotonicity)，单调性是指如果已经有一些请求通过哈希分派到了相应的服务器进行处理，又有新的服务器加入到系统中时候，应保证原有的请求可以被映射到原有的或者新的服务器中去，而不会被映射到原来的其它服务器上去。 

  * 分散性(Spread)：分布式环境中，客户端请求时候可能不知道所有服务器的存在，可能只知道其中一部分服务器，在客户端看来他看到的部分服务器会形成一个完整的hash环。如果多个客户端都把部分服务器作为一个完整hash环，那么可能会导致，同一个用户的请求被路由到不同的服务器进行处理。这种情况显然是应该避免的，因为它不能保证同一个用户的请求落到同一个服务器。所谓分散性是指上述情况发生的严重程度。好的哈希算法应尽量避免尽量降低分散性。 一致性hash具有很低的分散性

  * 平衡性(Balance)：平衡性也就是说负载均衡，是指客户端hash后的请求应该能够分散到不同的服务器上去。一致性hash可以做到每个服务器都进行处理请求，但是不能保证每个服务器处理的请求的数量大致相同

- 虚拟节点





### 16.3.3. 一致性Hash算法虚拟节点

**简介：通过虚拟节点倾斜解决方案，均匀一致性hash**

- 出现问题分析：

  ​    部门hash节点下架之后，虽然剩余机器都在处理请求，但是明显每个机器的负载不均衡，这样称为一致性hash的倾斜，虚拟节点的出现就是为了解决这个问题。

  

- 增设虚拟节点

     当物理机器数目为A，虚拟节点为B的时候，实际hash环上节点个数为A*B，将A节点分部为A1,A2,A3;将A1、A2、A3平均分布在各个位置，使A服务的节点尽量均匀分配在各个角落

  

- 每台服务器负载相对均衡

    当某个节点挂了之后，其数据均衡的分布给相邻的顺时针后面的一个节点上面，故所有数据比之前所述一致性hash相对均衡

   

### 16.3.4. twemproxy实现hash分片

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


    

# 16.4. Redis缓存持久化策略



### 16.4.1. Redis RDB持久化原理

**简介：rdb持久化方案配置讲解，redis的开发者是怎么实现rdb的**

- rdb持久化配置

  ```shell
  # 时间策略，表示900s内如果有1条是写入命令，就触发产生一次快照，可以理解为就进行一次备份
  save 900 1
  save 300 10  # 表示300s内有10条写入，就产生快照
  save 60 10000
  # redis servercron 类似于linux的crontab，默认每隔100毫秒执行一次
  
  # 文件名称
  dbfilename dump.rdb
  
  # 如果持久化出错，主进程是否停止写入
  stop-writes-on-bgsave-error yes
  
  # 是否压缩
  rdbcompression yes
  
  # 导入时是否检查
  rdbchecksum yes
  
  # 文件保存路径
  dir /usr/local/redis-4.0.6
  ```

- save的含义

  ​    实际生产环境每个时段的读写请求肯定不是均衡的，为此redis提供一种根据key单位时间操作次数来触发一次备份到磁盘，我们可以自由定制什么情况下触发备份，此功能起到平衡性能与数据安全的作用

- 在Redis中RDB持久化的触发分为两种：自己手动触发与Redis定时触发

  * **针对RDB方式的持久化，手动触发可以使用：**
    - save：会阻塞当前Redis服务器，直到持久化完成，线上应该禁止使用。
    - bgsave：该触发方式会fork一个子进程，由子进程负责持久化过程，因此阻塞只会发生在fork子进程的时候

- 而自动触发的场景主要是有以下几点

  * 根据我们的 `save m n` 配置规则自动触发
  * 从节点全量复制时，主节点发送rdb文件给从节点完成复制操作，主节点会触发 `bgsave`
  * 执行 `debug reload` 时
  * 执行 `shutdown`时，如果没有开启aof，也会触发

- 禁用RDB

  - 只需要在save的最后一行写上：save  ""

   

### 16.4.2. Redis AOF持久化原理

**简介：rdb持久化方案配置讲解，redis的开发者是怎么实现rdb的**

- AOF持久化配置

  ```shell
  # 是否开启aof
  appendonly yes
  
  # 文件名称
  appendfilename "appendonly.aof"
  
  # 同步方式
  appendfsync everysec
  
  # aof重写期间是否同步
  no-appendfsync-on-rewrite no
  
  # 重写触发配置
  auto-aof-rewrite-percentage 100
  auto-aof-rewrite-min-size 64mb
  
  # 加载aof时如果有错如何处理
  aof-load-truncated yes # yes表示如果aof尾部文件出问题，写log记录并继续执行。no表示提示写入等待修复后写入
  
  # 文件重写策略
  aof-rewrite-incremental-fsync yes
  
  ```

  

- appendfsync  同步模式有三种模式，一般情况下都采用 **everysec** 配置，在数据和安全里面做平衡性选择，最多损失1s的数据

  - always：把每个写命令都立即同步到aof，很慢，但是很安全

  - everysec：每秒同步一次，是折中方案

  - no：redis不处理交给OS来处理，非常快，但是也最不安全

    

- AOF的整个流程大体来看可以分为两步，一步是命令的实时写入（如果是 `appendfsync everysec` 配置，会有1s损耗），第二步是对aof文件的重写。

  * 步骤：

    * 命令写入=》追加到aof_buf =》通过时间事件调用flushAppendOnlyFile函数同步到aof磁盘

  * 原因：

    * 实时写入磁盘会带来非常高的磁盘IO，影响整体性能

      

- AOF持久化的效率和安全性分析

  ```java
  always：每个时间事件循环都将AOF_BUF缓冲区的所有内容写入到AOF文件，并且同步AOF文件，这是最安全的方式，但磁盘操作和阻塞延迟，是IO开支较大。
  everysec：每秒同步一次，性能和安全都比较中庸的方式，也是redis推荐的方式。如果遇到物理服务器故障，有可能导致最近一秒内aof记录丢失(可能为部分丢失)。
  no：redis并不直接调用文件同步，而是交给操作系统来处理，操作系统可以根据buffer填充情况/通道空闲时间等择机触发同步；这是一种普通的文件操作方式。性能较好，在物理服务器故障时，数据丢失量会因OS配置有关。处于no模式下的flushAppendOnlyFile调用无须执行同步操作
  ```





### 16.4.3. Redis两种持久化方案对比

**简介：rdb和aof两种持久化方案的优缺点分析，以及如何选择**

- Redis提供了不同的持久性选项：

  - RDB持久性以指定的时间间隔执行数据集的时间点快照。
  - AOF持久性记录服务器接收的每个写入操作，将在服务器启动时再次播放，重建原始数据集。使用与Redis协议本身相同的格式以仅追加方式记录命令。当Redis太大时，Redis能够重写日志背景。

- RDB的优缺点

  * 优点：
    * RDB最大限度地提高了Redis的性能，父进程不需要参与磁盘I/O
    * 与AOF相比，RDB允许使用大数据集更快地重启
  * 缺点：
    * 如果您需要在Redis停止工作时（例如断电后）将数据丢失的可能性降至最低，则RDB并不好
    * RDB经常需要fork（）才能使用子进程持久存储在磁盘上。如果数据集很大，Fork（）可能会非常耗时

- AOF的优缺点

  * 优点：

    * 数据更加安全
    * 当Redis AOF文件太大时，Redis能够在后台自动重写AOF        ## INCRE  1  执行10万 =    INCREBY10万执行一次
    * AOF以易于理解和解析的格式一个接一个地包含所有操作的日志     # flushdb类似于rm -rf /*

  * 缺点：

    * AOF文件通常比同一数据集的等效RDB文件大

    * 根据确切的fsync策略，AOF可能比RDB慢

      

- RDB 和 AOF ,我应该用哪一个？
  一般来说,如果想达到足以媲美 PostgreSQL 的数据安全性， 你应该同时使用两种持久化功能。如果你非常关心你的数据,但仍然可以承受数分钟以内的数据丢失， 那么你可以只使用 RDB 持久化。有很多用户都只使用 AOF 持久化， 但我们并不推荐这种方式： 因为定时生成 RDB 快照（snapshot）非常便于进行数据库备份， 并且 RDB 恢复数据集的速度也要比 AOF 恢复的速度要快

  

* 在线上我们到底该怎么做？

  * RDB持久化与AOF持久化同步使用

  * 如果Redis中的数据并不是特别敏感或者可以通过其它方式重写生成数据，可以关闭持久化，如果丢失数据可以通过其它途径补回；

  * 自己制定策略定期检查Redis的情况，然后可以手动触发备份、重写数据；

  * 采用集群和主从同步

    

### 16.4.4. Redis过期key清除策略

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

 



# 16.5. 布隆过滤器



### 16.5.1. 寻衅滋事？先过了我这道关



**简介：布隆过滤器是什么，一定要用吗?**

- 黑客流量攻击：故意访问不存在的数据，导致程序不断访问DB数据库的数据
- 黑客安全阻截：当黑客访问不存在的缓存时迅速返回避免缓存及DB挂掉
- 思考：如果让你实现这个功能你会怎么做？   key：10000 10001  10002 10003  大集合，key是否在集合里面
- 温故而知新：分析java常用数据结构复习  set  map key,value  list 有序get[0]、get[1]； 
-   list.contain (key)遍历数据，进行equals()比较，性能小
-  set.contain(key) hashcode比较，性能较高，64位  1G
-   map.get(key) hashcode比较，性能还行
- 概念：

  - **布隆过滤器**（英语：Bloom Filter）是1970年由布隆提出的。它实际上是一个很长的[二进制](https://zh.wikipedia.org/wiki/%E4%BA%8C%E8%BF%9B%E5%88%B6)向量和一系列随机[映射函数](https://zh.wikipedia.org/wiki/%E6%98%A0%E5%B0%84)。布隆过滤器可以用于检索一个元素是否在一个集合中。它的优点是空间效率和查询时间都远远超过一般的算法，缺点是有一定的误识别率和删除困难。
- 优点:

  - 相比于其它的数据结构，布隆过滤器在空间和时间方面都有巨大的优势。布隆过滤器存储空间和插入/查询时间都是常数（{\displaystyle O(k)}![O(k)](https://wikimedia.org/api/rest_v1/media/math/render/svg/f5ec39041121b14e8c2b1a986c9b04547b223e3c)）。另外，散列函数相互之间没有关系，方便由硬件并行实现。布隆过滤器不需要存储元素本身，在某些对保密要求非常严格的场合有优势
- 缺点
  - 但是布隆过滤器的缺点和优点一样明显。误算率是其中之一。随着存入的元素数量增加，误算率随之增加。但是如果元素数量太少，则使用散列表足矣

   





### 16.5.2. 了解布隆过滤器



**简介：布隆过滤器是什么，一定要用吗?**

- 布隆过滤器的其他使用场景

- **网页爬虫对URL的去重，避免爬取相同的URL地址；**

  **反垃圾邮件，从数十亿个垃圾邮件列表中判断某邮箱是否垃圾邮箱（同理，垃圾短信）；**

  **缓存击穿，将已存在的缓存放到布隆中，当黑客访问不存在的缓存时迅速返回避免缓存及DB挂掉。**

- 布隆过滤器实现原理图解

  







### 16.5.3. 谷歌布隆过滤器实现会员转盘抽奖



**简介：抽奖程序功能需求分析，谷歌实现布隆过滤器，谷歌布隆过滤器的局限性**

- 需求分析步骤

  * 互联网功能需求分析
    * 这是一个抽奖程序，只针对会员用户有效
  * 抽离出功能所有api
  * 制定存储方案
  * 性能优化方案分析

  ​    

- 成型互联网产品用户量上千万，日常百万，怎么做到高性能非会员过滤

- 这是一个布隆过滤器的经典使用场景

- 通过google布隆过滤器存储会员数据实战

  * 程序启动时将数据放入内存中
  * google自动创建布隆过滤器
  * 用户ID进来之后判断是否是会员

- 代码演练

* ```sql
  CREATE TABLE `sys_user` (
    `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
    `user_name` varchar(11) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '用户名',
    `image` varchar(11) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '用户头像',
    PRIMARY KEY (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
  ```





### 16.5.4. goole布隆过滤器与Redis布隆过滤器

**简介：**布隆过滤器两种实现方案的优缺点分析

- google布隆过滤器的缺陷与思考

  - 基于内存布隆过滤器有什么特点

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

  ​    

- Redis布隆过滤器

  * 可扩展性Bloom过滤器
    * 一旦Bloom过滤器达到容量，就会在其上创建一个新的过滤器
  * 不存在重启即失效或者定时任务维护的成本
    * 基于goole实现的布隆过滤器需要启动之后初始化布隆过滤器
  * 缺点：
    * 需要网络IO,性能比基于内存的过滤器低

- 选择:

     优先基于数据量进行考虑









### 16.5.5. Redis布隆过滤器安装

**简介：**

- Redis布隆过滤器安装过程    自己构建一个bitMap

  * git在centos7下面的安装

    ```shell
    1、安装git，直接使用yum安装即可：
    
    yum -y install git
    
    2、创建git用户，git用户可以正常通过ssh使用git，但无法登录shell，因为我们为git用户指定的git-shell每次一登录就自动退出。
    
    useradd -m -d /home/git -s /usr/bin/git-shell git
    
    3、初始化git仓库
    
    mkdir -p /data/git
    
    cd /data/git
    
    git init --bare project1.git
    
    chown git.git project1.git -R
    
    4、创建免密钥
    
    cd /home/git
    
    mkdir .ssh
    
    chmod 700 .ssh
    
    touch .ssh/authorized_keys
    
    chmod 600 .ssh/authorized_keys
    
    chown git.git .ssh -R
    ```

    

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






### 16.5.6. Redis布隆过滤器与springboot的整合探索



**简介：基于lua脚本实现springboot和布隆过滤器的整合**

- 通过普通命令无法实现springboot整合布隆过滤器
- 查找github开源框架的流程
- 分析开源框架的实现原理
- 通过lua脚本自己实现布隆过滤器
- 编写两个lua脚本
  * 添加数据到指定名称的布隆过滤器
  * 从指定名称的布隆过滤器获取key是否存在的脚本





