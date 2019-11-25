[TOC]




# 一、索引



## B+ Tree 原理

### 1. 数据结构

B Tree 指的是 Balance Tree，也就是平衡树。平衡树是一颗查找树，并且所有叶子节点位于同一层。

B+ Tree 是基于 B Tree 和叶子节点顺序访问指针进行实现，它具有 B Tree 的平衡性，并且通过顺序访问指针来提高区间查询的性能。

在 B+ Tree 中，一个节点中的 key 从左到右非递减排列，如果某个指针的左右相邻 key 分别是 key<sub>i</sub> 和 key<sub>i+1</sub>，且不为 null，则该指针指向节点的所有 key 大于等于 key<sub>i</sub> 且小于等于 key<sub>i+1</sub>。

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/33576849-9275-47bb-ada7-8ded5f5e7c73.png" width="350px"> </div><br>
### 2. 操作

进行查找操作时，首先在根节点进行二分查找，找到一个 key 所在的指针，然后递归地在指针所指向的节点进行查找。直到查找到叶子节点，然后在叶子节点上进行二分查找，找出 key 所对应的 data。

插入删除操作会破坏平衡树的平衡性，因此在插入删除操作之后，需要对树进行一个分裂、合并、旋转等操作来维护平衡性。

### 3. 与红黑树的比较

红黑树等平衡树也可以用来实现索引，但是文件系统及数据库系统普遍采用 B+ Tree 作为索引结构，主要有以下两个原因：

（一）更少的查找次数

平衡树查找操作的时间复杂度和树高 h 相关，O(h)=O(log<sub>d</sub>N)，其中 d 为每个节点的出度。

红黑树的出度为 2，而 B+ Tree 的出度一般都非常大，所以红黑树的树高 h 很明显比 B+ Tree 大非常多，查找的次数也就更多。

（二）利用磁盘预读特性

为了减少磁盘 I/O 操作，磁盘往往不是严格按需读取，而是每次都会预读。预读过程中，磁盘进行顺序读取，顺序读取不需要进行磁盘寻道，并且只需要很短的磁盘旋转时间，速度会非常快。

操作系统一般将内存和磁盘分割成固定大小的块，每一块称为一页，内存与磁盘以页为单位交换数据。数据库系统将索引的一个节点的大小设置为页的大小，使得一次 I/O 就能完全载入一个节点。并且可以利用预读特性，相邻的节点也能够被预先载入。
### 红黑树介绍
红黑树是一棵二叉搜索树，它在每个节点增加了一个存储位记录节点的颜色，可以是RED,也可以是BLACK；通过任意一条从根到叶子简单路径上颜色的约束，红黑树保证最长路径不超过最短路径的二倍，因而近似平衡。

具体性质如下：

每个节点颜色不是黑色，就是红色
根节点是黑色的
如果一个节点是红色，那么它的两个子节点就是黑色的（没有连续的红节点）
对于每个节点，从该节点到其后代叶节点的简单路径上，均包含相同数目的黑色节点

满足以上性质时，就能保证最长路径不超过最短路径的二倍,因为你的最短路径就是全黑节点，最长路径就是一个红节点一个黑节点，最后黑色节点相同时，最长路径刚好是最短路径的两倍

[红黑树参考博客](https://blog.csdn.net/tanrui519521/article/details/80980135)

# 树的介绍
## 普通二叉查找树

非平衡,查找的时间复杂度是Ｏ(log N)

## B树与B+树

### B树出现的原因
B-树就是B树，没有所谓的B减树

Ｂ树和Ｂ＋树的出现是因为磁盘ＩＯ；众所周知，ＩＯ操作的效率很低，那么，当在大量数据存储中，查询时我们不能一下子将所有数据加载到内存中，只能逐一加载磁盘页，每个磁盘页对应树的节点。造成大量磁盘ＩＯ操作（最坏情况下为树的高度）。

平衡二叉树由于树深度过大而造成磁盘IO读写过于频繁，进而导致效率低下。 
所以，我们为了减少磁盘ＩＯ的次数，就你必须降低树的深度，将“瘦高”的树变得“矮胖”。一个基本的想法就是： 
（1）、每个节点存储多个元素   
（2）、摒弃二叉树结构，采用多叉树  

### B+树的特点

<div align="center"> <img src=" .\pictures\mysql\Snipaste_2019-09-25_20-23-50.jpg" width="600px"> </div><br>
1.有k个子树的中间节点包含有k个元素（B树中是k-1个元素），每个元素不保存数据，只用来索引，所有数据都保存在叶子节点。

2.所有的叶子结点中包含了全部元素的信息，及指向含这些元素记录的指针，且叶子结点本身依关键字的大小自小而大顺序链接。

3.所有的中间节点元素都同时存在于子节点，在子节点元素中是最大（或最小）元素。

4.**每个叶子结点都存有相邻叶子结点的指针，叶子结点本身依关键字的大小自小而大顺序链接**



### B+树比B树的优点
1.多阶:单一节点存储更多的元素，使得时间复杂度的logN的底数更大；  
2.**只有叶子节点**存数据:所有查询都要查找到叶子节点，又因为层数一致,**关键字查询路径长度相同**,查询性能稳定；**一次性读取进内存的关键字也会更多;**  
3.叶子节点**顺序存储数据**:可以利用磁盘的顺序IO,减少IO读取次数,读取速度更快;   
3.所有叶子节点形成有序链表:便于范围查询。  



## MySQL 索引

索引是在存储引擎层实现的，而不是在服务器层实现的，所以不同存储引擎具有不同的索引类型和实现。

### 1. B+Tree 索引

是大多数 MySQL 存储引擎的默认索引类型。

因为不再需要进行全表扫描，只需要对树进行搜索即可，所以查找速度快很多。

因为 B+ Tree 的有序性，所以除了用于查找，还可以用于排序和分组。

可以指定多个列作为索引列，多个索引列共同组成键。

适用于全键值、键值范围和键前缀查找，其中键前缀查找只适用于最左前缀查找。如果不是按照索引列的顺序进行查找，则无法使用索引。

InnoDB 的 B+Tree 索引分为主索引和辅助索引。主索引的叶子节点 data 域记录着完整的数据记录，这种索引方式被称为聚簇索引。因为无法把数据行存放在两个不同的地方，所以一个表只能有一个聚簇索引。

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/45016e98-6879-4709-8569-262b2d6d60b9.png" width="350px"> </div><br>
辅助索引的叶子节点的 data 域记录着主键的值，因此在使用辅助索引进行查找时，需要先在辅助索引中查找到主键值，然后再到主索引中进行查找。

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/7c349b91-050b-4d72-a7f8-ec86320307ea.png" width="350px"> </div><br>
### 2. 哈希索引

哈希索引能以 O(1) 时间进行查找，但是失去了有序性：

- 无法用于排序与分组；
- 只支持精确查找，无法用于部分查找和范围查找。

InnoDB 存储引擎有一个特殊的功能叫“自适应哈希索引”，当某个索引值被使用的非常频繁时，会在 B+Tree 索引之上再创建一个哈希索引，这样就让 B+Tree 索引具有哈希索引的一些优点，比如快速的哈希查找。

### 3. 全文索引

**MyISAM** 存储引擎支持全文索引，用于查找文本中的关键词，而不是直接比较是否相等。

查找条件使用 MATCH AGAINST，而不是普通的 WHERE。

全文索引使用倒排索引实现，它记录着关键词到其所在文档的映射。

InnoDB 存储引擎在 MySQL 5.6.4 版本中也开始支持全文索引。


### 倒排索引:

倒排索引(反向索引) 与 正向索引对应,正向索引是以文档id为主键,文档里面有哪些关键单词为内容,如图:

<div align="center"> <img src=".\pictures\mysql\Snipaste_2019-09-05_14-30-26.jpg" width="350px"> </div><br>
而反向索引是以word为主键,word所在的多个docId为关键内容的索引,如图:

<div align="center"> <img src=".\pictures\mysql\Snipaste_2019-09-05_14-32-30.jpg" width="350px"> </div><br>
**实例如下**

<div align="center"> <img src=".\pictures\mysql\Snipaste_2019-09-05_14-33-41.jpg" width="350px"> </div><br>
关键单词作为索引,倒排列表中记录该单词在哪些文档中出现以及出现的位置在哪
<div align="center"> <img src="notes\pictures\mysql\Snipaste_2019-09-05_14-35-45.jpg" width="350px"> </div><br>

### 4. 空间数据索引

MyISAM 存储引擎支持空间数据索引（R-Tree），可以用于地理数据存储。空间数据索引会从所有维度来索引数据，可以有效地使用任意维度来进行组合查询。

必须使用 GIS 相关的函数来维护数据。

## 索引优化

### 1. 独立的列

在进行查询时，索引列不能是表达式的一部分，也不能是函数的参数，否则无法使用索引。

例如下面的查询不能使用 actor_id 列的索引：

```sql
SELECT actor_id FROM sakila.actor WHERE actor_id + 1 = 5;
```

### 2. 多列索引

在需要使用多个列作为条件进行查询时，使用多列索引比使用多个单列索引性能更好。例如下面的语句中，最好把 actor_id 和 film_id 设置为多列索引。

```sql
SELECT film_id, actor_ id FROM sakila.film_actor
WHERE actor_id = 1 AND film_id = 1;
```

### 3. 索引列的顺序

让选择性最强的索引列放在前面。

索引的选择性是指：不重复的索引值和记录总数的比值。最大值为 1，此时每个记录都有唯一的索引与其对应。选择性越高，每个记录的区分度越高，查询效率也越高。

例如下面显示的结果中 customer_id 的选择性比 staff_id 更高，因此最好把 customer_id 列放在多列索引的前面。

```sql
SELECT COUNT(DISTINCT staff_id)/COUNT(*) AS staff_id_selectivity,
COUNT(DISTINCT customer_id)/COUNT(*) AS customer_id_selectivity,
COUNT(*)
FROM payment;
```

```html
   staff_id_selectivity: 0.0001
customer_id_selectivity: 0.0373
               COUNT(*): 16049
```

### 4. 前缀索引

对于 BLOB、TEXT 和 VARCHAR 类型的列，必须使用前缀索引，只索引开始的部分字符。

前缀长度的选取需要根据索引选择性来确定  
索引选择性指的是distinct的个数与总数的比值,最大为1,如果选取合适prefix_length就可以保证很接近索引选择性的话,用前一段做索引就可以节省空间


实例:
<div align="center"> <img src=".\pictures\mysql\Snipaste_2019-09-05_15-15-58.jpg" width="650px"> </div><br>

### 5. 覆盖索引

覆盖索引是一种辅助索引,辅助索引包含所有需要查询的字段的值,只需要访问这个覆盖索引就可以得到要查询的字段的值,而不需要再访问主键索引(聚集索引)拿到一整行的值.

具有以下优点：

- 索引通常远小于数据行的大小,因为它只是由一行的部分字段组成的索引不包含一整行数据，只读取索引能大大减少数据访问量。
- 一些存储引擎（例如 MyISAM）在内存中只缓存索引，而数据依赖于操作系统来缓存。因此，只访问索引可以不使用系统调用（通常比较费时）。
- 对于 InnoDB 引擎，若辅助索引能够覆盖查询，则无需访问主索引。


## 聚集索引,辅助索引,组合索引,非聚集索引

聚集索引与非聚集索引  
聚集索引:  
一张表的主键存储在B+树中,同时叶子节点存放表的每一行的数据,叶子节点存储在数据页中(方便磁盘顺序读取,这也是B+树优于红黑树的一大原因)  
非聚集索引  
叶子节点存储的不是数据,而是指向某个数据块的指针.所以数据块不是按照顺序存放的


聚集索引和辅助索引

聚集索引（主键索引）  
—innodb存储引擎是索引组织表，即表中的数据按照主键顺序存放。而聚集索引就是按照每张表的主键构造一颗B+树，同时叶子节点中存放的即为整张表的记录数据
—聚集索引的叶子节点称为数据页，数据页，数据页！重要的事说三遍。聚集索引的这个特性决定了索引组织表中的数据也是索引的一部分。

辅助索引（二级索引）  
—非主键索引  
—叶子节点=键值+书签。Innodb存储引擎的书签就是相应行数据的主键索引值  


单列索引与组合索引(复合索引,联合索引)  
单列索引，顾名思义也就是只有一个字段的索引列。  
组合索引，又称复合索引，两个或更多个列上的索引被称作复合索引。对于复合索引，他们都遵循左侧原则，也是就是说一个查询可以只使用复合索引最左侧的一部份。例如索引是key index (a,b,c). 可以支持a | a,b| a,b,c 3种组合进行查找，但不支持 b,c进行查找 .当最左侧字段是常量引用时，索引就十分有效。


## 索引的优点

- 大大减少了服务器需要扫描的数据行数。

- 帮助服务器避免进行排序和分组，以及避免创建临时表（B+Tree 索引是有序的，可以用于 ORDER BY 和 GROUP BY 操作。临时表主要是在排序和分组过程中创建，不需要排序和分组，也就不需要创建临时表）。

- 将随机 I/O 变为顺序 I/O（B+Tree 索引是有序的，会将相邻的数据都存储在一起）。

## 索引的使用条件

- 对于非常小的表、大部分情况下简单的全表扫描比建立索引更高效；

- 对于中到大型的表，索引就非常有效；

- 但是对于特大型的表，建立和维护索引的代价将会随之增长。这种情况下，需要用到一种技术可以直接区分出需要查询的一组数据，而不是一条记录一条记录地匹配，例如可以使用分区技术。

# 二、查询性能优化

## 使用 Explain 进行分析

Explain 用来分析 SELECT 查询语句，开发人员可以通过分析 Explain 结果来优化查询语句。 

比较重要的字段有：

- select_type : 查询类型，有简单查询、联合查询、子查询等
- key : 使用的索引
- rows : 扫描的行数

## 优化数据访问

### 1. 减少请求的数据量

- 只返回必要的列：最好不要使用 SELECT * 语句。
- 只返回必要的行：使用 LIMIT 语句来限制返回的数据。
- 缓存重复查询的数据：使用缓存可以避免在数据库中进行查询，特别在要查询的数据经常被重复查询时，缓存带来的查询性能提升将会是非常明显的。

### 2. 减少服务器端扫描的行数

最有效的方式是使用索引来覆盖查询。

## 重构查询方式

### 1. 切分大查询

一个大查询如果一次性执行的话，可能一次锁住很多数据、占满整个事务日志、耗尽系统资源、阻塞很多小的但重要的查询。

```sql
DELETE FROM messages WHERE create < DATE_SUB(NOW(), INTERVAL 3 MONTH);
```

```sql
rows_affected = 0
do {
    rows_affected = do_query(
    "DELETE FROM messages WHERE create  < DATE_SUB(NOW(), INTERVAL 3 MONTH) LIMIT 10000")
} while rows_affected > 0
```

### 2. 分解大连接查询

将一个大连接查询分解成对每一个表进行一次单表查询，然后在应用程序中进行关联，这样做的好处有：

- 让缓存更高效。对于连接查询，如果其中一个表发生变化，那么整个查询缓存就无法使用。而分解后的多个查询，即使其中一个表发生变化，对其它表的查询缓存依然可以使用。
- 分解成多个单表查询，这些单表查询的缓存结果更可能被其它查询使用到，从而减少冗余记录的查询。
- 减少锁竞争；
- 在应用层进行连接，可以更容易对数据库进行拆分，从而更容易做到高性能和可伸缩。
- 查询本身效率也可能会有所提升。例如下面的例子中，使用 IN() 代替连接查询，可以让 MySQL 按照 ID 顺序进行查询，这可能比随机的连接要更高效。

```sql
SELECT * FROM tab
JOIN tag_post ON tag_post.tag_id=tag.id
JOIN post ON tag_post.post_id=post.id
WHERE tag.tag='mysql';
```

```sql
SELECT * FROM tag WHERE tag='mysql';
SELECT * FROM tag_post WHERE tag_id=1234;
SELECT * FROM post WHERE post.id IN (123,456,567,9098,8904);
```

# 三、存储引擎

## InnoDB

是 MySQL 默认的事务型存储引擎，只有在需要它不支持的特性时，才考虑使用其它存储引擎。

实现了四个标准的隔离级别，默认级别是可重复读（REPEATABLE READ）。在可重复读隔离级别下，通过多版本并发控制（MVCC）+ Next-Key Locking 防止幻影读。

主索引是聚簇索引，在索引中保存了数据，从而避免直接读取磁盘，因此对查询性能有很大的提升。

内部做了很多优化，包括从磁盘读取数据时采用的可预测性读、能够加快读操作并且自动创建的自适应哈希索引、能够加速插入操作的插入缓冲区等。

支持真正的在线热备份。其它存储引擎不支持在线热备份，要获取一致性视图需要停止对所有表的写入，而在读写混合场景中，停止写入可能也意味着停止读取。

## MyISAM

设计简单，数据以紧密格式存储。对于只读数据，或者表比较小、可以容忍修复操作，则依然可以使用它。

提供了大量的特性，包括压缩表、空间数据索引等。

不支持事务。

不支持行级锁，只能对整张表加锁，读取时会对需要读到的所有表加共享锁，写入时则对表加排它锁。但在表有读取操作的同时，也可以往表中插入新的记录，这被称为并发插入（CONCURRENT INSERT）。

可以手工或者自动执行检查和修复操作，但是和事务恢复以及崩溃恢复不同，可能导致一些数据丢失，而且修复操作是非常慢的。

如果指定了 DELAY_KEY_WRITE 选项，在每次修改执行完成时，不会立即将修改的索引数据写入磁盘，而是会写到内存中的键缓冲区，只有在清理键缓冲区或者关闭表的时候才会将对应的索引块写入磁盘。这种方式可以极大的提升写入性能，但是在数据库或者主机崩溃时会造成索引损坏，需要执行修复操作。

## 比较

- 事务：InnoDB 是事务型的，可以使用 Commit 和 Rollback 语句。

- 并发：MyISAM 只支持表级锁，而 InnoDB 还支持行级锁。

- 外键：InnoDB 支持外键。

- 备份：InnoDB 支持在线热备份。

- 崩溃恢复：MyISAM 崩溃后发生损坏的概率比 InnoDB 高很多，而且恢复的速度也更慢。

- 其它特性：MyISAM 支持压缩表和空间数据索引。
## 隔离级别


# 四、数据类型

## 整型

TINYINT, SMALLINT, MEDIUMINT, INT, BIGINT 分别使用 8, 16, 24, 32, 64 位存储空间，一般情况下越小的列越好。

INT(11) 中的数字只是规定了交互工具显示字符的个数，对于存储和计算来说是没有意义的。

## 浮点数

FLOAT 和 DOUBLE 为浮点类型，DECIMAL 为高精度小数类型。CPU 原生支持浮点运算，但是不支持 DECIMAl 类型的计算，因此 DECIMAL 的计算比浮点类型需要更高的代价。

FLOAT、DOUBLE 和 DECIMAL 都可以指定列宽，例如 DECIMAL(18, 9) 表示总共 18 位，取 9 位存储小数部分，剩下 9 位存储整数部分。

## 字符串

主要有 CHAR 和 VARCHAR 两种类型，一种是定长的，一种是变长的。

VARCHAR 这种变长类型能够节省空间，因为只需要存储必要的内容。但是在执行 UPDATE 时可能会使行变得比原来长，当超出一个页所能容纳的大小时，就要执行额外的操作。MyISAM 会将行拆成不同的片段存储，而 InnoDB 则需要分裂页来使行放进页内。

在进行存储和检索时，会保留 VARCHAR 末尾的空格，而会删除 CHAR 末尾的空格。

## 时间和日期

MySQL 提供了两种相似的日期时间类型：DATETIME 和 TIMESTAMP。

### 1. DATETIME

能够保存从 1000 年到 9999 年的日期和时间，精度为秒，使用 8 字节的存储空间。

它与时区无关。

默认情况下，MySQL 以一种可排序的、无歧义的格式显示 DATETIME 值，例如“2008-01-16 22<span>:</span>37<span>:</span>08”，这是 ANSI 标准定义的日期和时间表示方法。

### 2. TIMESTAMP

和 UNIX 时间戳相同，保存从 1970 年 1 月 1 日午夜（格林威治时间）以来的秒数，使用 4 个字节，只能表示从 1970 年到 2038 年。

它和时区有关，也就是说一个时间戳在不同的时区所代表的具体时间是不同的。

MySQL 提供了 FROM_UNIXTIME() 函数把 UNIX 时间戳转换为日期，并提供了 UNIX_TIMESTAMP() 函数把日期转换为 UNIX 时间戳。

默认情况下，如果插入时没有指定 TIMESTAMP 列的值，会将这个值设置为当前时间。

应该尽量使用 TIMESTAMP，因为它比 DATETIME 空间效率更高。


# 五、切分
## 分库分表中间件

简单易用的组件：  
当当sharding-jdbc  
蘑菇街TSharding  

强悍重量级的中间件：  
sharding  
TDDL Smart Client的方式（淘宝）  
Atlas(Qihoo 360)  
alibaba.cobar(是阿里巴巴（B2B）部门开发)  
MyCAT（基于阿里开源的Cobar产品而研发）  
Oceanus(58同城数据库中间件)  
OneProxy(支付宝首席架构师楼方鑫开发)  
vitess（谷歌开发的数据库中间件）  


## 水平切分

水平切分又称为 Sharding，它是将同一个表中的记录拆分到多个结构相同的表中。

当一个表的数据不断增多时，Sharding 是必然的选择，它可以将数据分布到集群的不同节点上，从而缓存单个数据库的压力。

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/63c2909f-0c5f-496f-9fe5-ee9176b31aba.jpg" width=""> </div><br>
## 垂直切分

垂直切分是将一张表按列切分成多个表，通常是按照列的关系密集程度进行切分，也可以利用垂直切分将经常被使用的列和不经常被使用的列切分到不同的表中。

在数据库的层面使用垂直切分将按数据库中表的密集程度部署到不同的库中，例如将原来的电商数据库垂直切分成商品数据库、用户数据库等。

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/e130e5b8-b19a-4f1e-b860-223040525cf6.jpg" width=""> </div><br>
## Sharding 策略


### 顺序分库分表
按照id顺序分库分表,但要保证id是有顺序自增的,而且会导致顺序分布将来的数据库访问会面临某个数据库访问过多过热的情况.

按照时间来分库,一月一存.

如果无法保证你的id是自增长的，那么你的数据就会凌乱的分散在各个数据库，这样热点确实就分散了，可是每当你增加一个数据库的时候，你就有可能进行大量的数据迁移，应对这种情况，就是尽量减少数据迁移的代价，所以这里运用一致性hash的方式进行分库分表比较好，可以尽可能的减少数据迁移，并且也能让解决热点过于集中的问题.一致性hash的分库策略可以看redis的md.

### 取模分库分表
一般的取模分库分表是就是将id mod n(一般为2的次数)，然后放入数据库中，这样能够使数据分散，不会有某个数据库过热的问题


二次分库时，为了数据迁移方便，一般是按倍数增加，比如初始4个库，二次分裂为8个，再16个。这样对于某个库的数据，一半数据移到新库，剩余不动，对比每次只增加一个库，所有数据都要大规模变动。

### 映射表：使用单独的一个数据库来存储映射关系。

性能会有所下降,因为要查两个数据库


## Sharding 存在的问题

### 1. 事务问题

使用分布式事务来解决，比如 XA 接口。

方案一：使用分布式事务  
优点：交由数据库管理，简单有效  
缺点：性能代价高，特别是shard越来越多时  

方案二：由应用程序和数据库共同控制  
原理：将一个跨多个数据库的分布式事务分拆成多个仅处 于单个数据库上面的小事务，并通过应用程序来总控 各个小事务。  
优点：性能上有优势  
缺点：需要应用程序在事务控制上做灵活设计。如果使用 了spring的事务管理，改动起来会面临一定的困难。  

### 2. 连接

可以将原来的连接分解成多个单表查询，然后在用户程序中进行连接。

### 3. ID 唯一性

- 使用全局唯一 ID（GUID）
- 为每个分片指定一个 ID 范围
- 分布式 ID 生成器 (如 Twitter 的 Snowflake 算法)

### 跨库join
只要是进行切分，跨节点Join的问题是不可避免的。但是良好的设计和切分却可以减少此类情况的发生。解决这一问题的普遍做法是分两次查询实现。在第一次查询的结果集中找出关联数据的id,根据这些id发起第二次请求得到关联数据。

### 跨节点的count,order by,group by以及聚合函数问题
这些是一类问题，因为它们都需要基于全部数据集合进行计算。多数的代理都不会自动处理合并工作。解决方案：与解决跨节点join问题的类似，分别在各个节点上得到结果后在应用程序端进行合并。和join不同的是每个结点的查询可以并行执行，因此很多时候它的速度要比单一大表快很多。但如果结果集很大，对应用程序内存的消耗是一个问题。

### 数据迁移，容量规划，扩容等问题

来自淘宝综合业务平台团队，它利用对2的倍数取余具有向前兼容的特性（如对4取余得1的数对2取余也是1）来分配数据，避免了行级别的数据迁移，但是依然需要进行表级别的迁移，同时对扩容规模和分表数量都有限制。总得来说，这些方案都不是十分的理想，多多少少都存在一些缺点，这也从一个侧面反映出了Sharding扩容的难度。


# 六、复制

## 主从复制

主要涉及三个线程：binlog 线程、I/O 线程和 SQL 线程。

-  **binlog 线程** ：负责将主服务器上的数据更改写入二进制日志（Binary log）中。
-  **I/O 线程** ：负责从主服务器上读取二进制日志，并写入从服务器的中继日志（Relay log）。
-  **SQL 线程** ：负责读取中继日志，解析出主服务器已经执行的数据更改并在从服务器中重放（Replay）。

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/master-slave.png" width=""> </div><br>

主从同步事件有3种形式:statement、row、mixed。

statement：会将对数据库操作的sql语句写入到binlog中。
row：会将每一条数据的变化写入到binlog中。
mixed：statement与row的混合。Mysql决定什么时候写statement格式的，什么时候写row格式的binlog

## 读写分离

主服务器处理写操作以及实时性要求比较高的读操作，而从服务器处理读操作。

读写分离能提高性能的原因在于：

- 主从服务器负责各自的读和写，极大程度缓解了锁的争用；
- 从服务器可以使用 MyISAM，提升查询性能以及节约系统开销；
- 增加冗余，提高可用性。

读写分离常用代理方式来实现，代理服务器接收应用层传来的读写请求，然后决定转发到哪个服务器。

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/master-slave-proxy.png" width=""> </div><br>

# 事务隔离机制

## 事务必须满足ACID
原子性（Atomicity）：事务作为一个整体被执行，包含在其中的对数据库的操作要么全部被执行，要么都不执行

一致性（Consistency）：事务应确保数据库的状态从一个一致状态转变为另一个一致状态。一致状态的含义是数据库中的数据应满足完整性约束

隔离性（Isolation）：多个事务并发执行时，一个事务的执行不应影响其他事务的执行

持久性（Durability）：已被提交的事务对数据库的修改应该永久保存在数据库中


## 事务的定义

事务（Transaction）是并发控制的基本单位。所谓的事务，它是一个操作序列，这些操作要么都执行，要么都不执行，它是一个不可分割的工作单位。例如，银行转账工作：从一个账号扣款并使另一个账号增款，这两个操作要么都执行，要么都不执行。所以，应该把它们看成一个事务。事务是数据库维护数据一致性的单位，在每个事务结束时，都能保 持数据一致性。

事务的生命周期
<div align="center"> <img src=".\pictures\mysql\Snipaste_2019-09-08_15-04-15.jpg" width=""/> </div><br>

## 并发事务会出现的问题

### 第一类丢失更新
定义：A事务撤销时，把已经提交的B事务的更新数据覆盖了。

<div align="center"> <img src=".\pictures\mysql\Snipaste_2019-09-08_15-09-50.jpg" width=""/> </div><br>
以上的示例演示了第一类丢失更新问题，事务B虽然成功了，但是它所做的更新没有被永久存储，这种并发问题是由于完全没有隔离事务造成的。

当两个事务更新相同的数据时，如果一个事务被提交，另一个事务却撤销，那么会连同第一个事务所做的更新也被撤销了。（这是绝对避免出现的事情） 事务A的开始时间和结束时间包含事务B的开始和结束时间,事务A回滚事务的同时,把B的已经提交的事务也回滚的,这是避免的,这就是第一类丢失更新.

### 第二类丢失更新
定义：A事务提交时，把已经提交的B事务的更新数据覆盖了。

<div align="center"> <img src=".\pictures\mysql\Snipaste_2019-09-08_15-11-55.jpg" width=""/> </div><br>
第二类丢失更新和第一类的区别实际上是对数据的影响是由A事务的撤销还是提交造成的，它和不可重复读(下面介绍)本质上是同一类并发问题，通常把它看做是不可重复读的一个特例。两个或多个事务查询同一数据。然后都基于自己的查询结果更新数据，这时会造成最后一个提交的更新事务，将覆盖其它已经提交的更新事务。


### 脏读：
脏读就是指当一个事务正在访问数据，并且对数据进行了修改，而这种修改还没有提交到数据库中，这时，另外一个事务也访问这个数据，然后使用了这个数据。

<div align="center"> <img src=".\pictures\mysql\Snipaste_2019-09-08_15-15-31.jpg" width=""/> </div><br>
### 不可重复读：
是指**在一个事务内，多次读同一数据,结果却不是重复的一样的值**。在这个事务还没有结束时，另外一个事务也访问该同一数据。那么，在第一个事务中的两次读数据之间，由于第二个事务的修改，那么第一个事务两次读到的的数据可能是不一样的。这样就发生了在一个事务内两次读到的数据是不一样的，因此称为是不可重复读。（即不能读到相同的数据内容）

<div align="center"> <img src=".\pictures\mysql\Snipaste_2019-09-08_15-17-11.jpg" width=""/> </div><br>

### 幻读:
定义：读到已提交插入数据，幻读与不可重复读类似，幻读是查询到了另一个事务已提交的新插入数据，而不可重复读是查询到了另一个事务已提交的更新数据。

**不可重复读和幻读的区别： 简单来说，不可重复读是由于数据修改引起的，幻读是由数据行插入或者删除引起的。**


<div align="center"> <img src=".\pictures\mysql\Snipaste_2019-09-08_15-20-07.jpg" width=""/> </div><br>

## 四种事务隔离机制
未提交读(Read Uncommitted)：允许脏读，也就是可能读取到其他会话中未提交事务修改的数据

提交读(Read Committed)：只能读取到已经提交的数据。Oracle等多数数据库默认都是该级别 (不重复读)

可重复读(Repeated Read)：可重复读。在同一个事务内的查询都是事务开始时刻一致的，InnoDB默认级别。在SQL标准中，该隔离级别消除了不可重复读，但是还存在幻象读

串行读(Serializable)：完全串行化的读，每次读都需要获得表级共享锁，读写相互都会阻塞

<div align="center"> <img src=".\pictures\mysql\Snipaste_2019-09-08_10-31-29.jpg" width=""/> </div><br>
### 读未提交

该隔离级别，所有事务都可以看到其他未提交事务的执行结果。通俗地讲就是，在一个事务中可以读取到另一个事务中新增或修改但未提交的数据。

该隔离级别可能导致的问题是脏读。因为另一个事务可能回滚，所以在第一个事务中读取到的数据很可能是无效的脏数据，造成脏读现象

```
> set tx_isolation='READ-UNCOMMITTED';
```
<div align="center"> <img src=".\pictures\mysql\Snipaste_2019-09-08_10-28-31.jpg" width=""/> </div><br>

### 读已提交

这是大多数数据库系统的默认隔离级别（但不是mysql默认的） 

一个事务只能看见已经提交事务所做的修改。换句话说，一个事务从开始直到提交之前，所做的任何修改对其他事务都是不可见的。  

该隔离级别可能导致的问题是不可重复读。因为两次执行同样的查询，可能会得到不一样的结果。  

notes\pictures\mysql\Snipaste_2019-09-08_10-29-01.jpg

<div align="center"> <img src=".\pictures\mysql\Snipaste_2019-09-08_10-29-01.jpg
" width=""/> </div><br>

### Repeatable read 可重复读

这是MySQL的默认事务隔离级别

它确保同一事务的多个实例在并发读取数据时，会看到同样的数据行。通俗来讲，可重复读在一个事务里读取数据,怎么读都不会变，除非提交了该事务才会发现其他事务修改了数据,仿佛产生幻觉(我之前查了那么多次都没发现改变啊)  。

该隔离级别存在的问题是幻读


<div align="center"> <img src=".\pictures\mysql\Snipaste_2019-09-08_10-30-05.jpg" width=""/> </div><br>
虽然Repeatable read避免了不可重复读，但还有可能出现幻读 。

<div align="center"> <img src=".\pictures\mysql\Snipaste_2019-09-08_10-30-41.jpg" width=""/> </div><br>
### Serializable 串行化
这是最高的隔离级别

它通过强制事务排序，使之不可能相互冲突，从而解决幻读问题。通俗地讲就是，假如两个事务都操作到同一数据行，那么这个数据行就会被锁定，只允许先读取/操作到数据行的事务优先操作，只有当事务提交了，数据行才会解锁，后一个事务才能成功操作这个数据行，否则只能一直等待

该隔离级别可能导致大量的超时现象和锁竞争。
## 事务隔离机制的实现原理

Read Uncommitted，读写均不使用锁，数据的一致性最差，也会出现许多逻辑错误。

Read Committed，使用写锁，但是读会出现不一致，不可重复读。

Repeatable Read, 使用读锁和写锁，解决不可重复读的问题，但会有幻读。

Serializable, 使用事务串形化调度，避免出现因为插入数据没法加锁导致的不一致的情况。



太难了,有空看
不同事务隔离级别分别会加哪些锁
可重复读与mvcc和一致性锁有关
https://www.cnblogs.com/cjsblog/p/8365921.html

## MVCC
### 定义
Multi-Version Concurrency Control,在InoDb引擎的可重复读事务机制中起重要作用,与行锁一起实现了Innodb的重复读机制,可以在大多数情况下代替行锁,提高效率.

### 实现
利用保存时间快照,经典的有乐观并发控制和悲观并发控制.

InnoDB的MVCC,是通过在每行记录后面保存两个隐藏的列来实现的,这两个列，分别保存了这个行的创建时间，一个保存的是行的删除时间。这里存储的并不是实际的时间值,而是系统版本号(可以理解为事务的ID)，每开始一个新的事务，系统版本号就会自动递增，事务开始时刻的系统版本号会作为事务的ID.下面看一下在REPEATABLE READ隔离级别下,MVCC具体是如何操作的.

#### insert
InnoDB为新插入的每一行保存当前系统版本号作为版本号.   
第一个事务ID为1；  
```sql
start transaction;
insert into yang values(NULL,'yang') ;
insert into yang values(NULL,'long');
insert into yang values(NULL,'fei');
commit;
```

<div align="center"> <img src=".\pictures\mysql\Snipaste_2019-09-09_14-27-20.jpg" width="650"/> </div><br>
#### select 

InnoDB会根据以下两个条件检查每行记录: 

a.InnoDB只会查找版本早于当前事务版本的数据行(也就是,行的系统版本号小于或等于事务的系统版本号)，这样可以确保事务读取的行，要么是在事务开始前已经存在的，要么是事务自身插入或者修改过的. 

b.行的删除版本要么未定义,要么大于当前事务版本号,这可以确保事务读取到的行，在事务开始之前未被删除. 

只有a,b同时满足的记录，才能返回作为查询结果.

#### delete
InnoDB会为删除的每一行保存当前系统的版本号(事务的ID)作为删除标识. 

**举例:**

第二个事务,ID为2;

```sql
start transaction;
select * from yang;  //(1)
select * from yang;  //(2)
commit; 
```
**假设1(验证select)**
假设在执行这个事务ID为2的过程中,刚执行到(1),这时,有另一个事务ID为3往这个表里插入了一条数据;   
第三个事务ID为3;  
```sql
start transaction;
insert into yang values(NULL,'tian');
commit;
```
这时表中的数据如下:

<div align="center"> <img src=".\pictures\mysql\Snipaste_2019-09-09_14-39-48.jpg" width="650"/> </div><br>
然后接着执行事务2中的(2),由于id=4的数据的创建时间(事务ID为3),执行当前事务的ID为2,而InnoDB只会查找事务ID小于等于当前事务ID的数据行,所以id=4的数据行并不会在执行事务2中的(2)被检索出来,在事务2中的两条select 语句检索出来的数据都只会下表:

<div align="center"> <img src=".\pictures\mysql\Snipaste_2019-09-09_14-41-37.jpg" width="650"/> </div><br>
**假设2(验证delete)**
假设在执行这个事务ID为2的过程中,刚执行到(1),假设事务执行完事务3后，接着又执行了事务4;   
第四个事务:  
```sql
start   transaction;  
delete from yang where id=1;
commit;  
```
此时数据库中的表如下:

写代码,写日志,本地debug,发到uat,uat测试通过,code review,发到pre,pre测试通过,发到pro,测试通过,删除次要日志,留下关键日志(info里也有很多关键日志),merge到master(merge交给别人来做)

接着执行事务ID为2的事务(2),根据SELECT 检索条件可以知道,它会检索创建时间(创建事务的ID)小于当前事务ID的行和删除时间(删除事务的ID)大于当前事务的行,而id=4的行上面已经说过,而id=1的行由于删除时间(删除事务的ID)大于当前事务的ID,所以事务2的(2)select * from yang也会把id=1的数据检索出来.所以,事务2中的两条select 语句检索出来的数据都如下:

<div align="center"> <img src=".\pictures\mysql\Snipaste_2019-09-09_14-47-44.jpg" width="650"/> </div><br>
#### update
InnoDB执行UPDATE，实际上是新插入了一行记录，并保存其创建时间为当前事务的ID，同时保存当前事务ID到要UPDATE的行的删除时间.

<div align="center"> <img src=".\pictures\mysql\Snipaste_2019-09-09_14-37-56.jpg" width="650"/> </div><br>

**假设3(验证update)**
假设在执行完事务2的(1)后又执行,其它用户执行了事务3,4,这时，又有一个用户对这张表执行了UPDATE操作:   
第5个事务:  

```sql
start  transaction;
update yang set name='Long' where id=2;
commit;
```
根据update的更新原则:会生成新的一行,并在原来要修改的列的删除时间列上添加本事务ID,得到表如下:
<div align="center"> <img src=".\pictures\mysql\Snipaste_2019-09-09_14-50-12.jpg" width="650"/> </div><br>
继续执行事务2的(2),根据select 语句的检索条件,得到下表:

<div align="center"> <img src=".\pictures\mysql\Snipaste_2019-09-09_14-50-36.jpg" width="650"/> </div><br>
还是和事务2中(1)select 得到相同的结果.

### mvcc解决幻读
例如：  

此时books表中有5条数据，版本号为1  

事务A，系统版本号2：select * from books；因为1<=2所以此时会读取5条数据。  
事务B，系统版本号3：insert into books ...，插入一条数据，新插入的数据版号为3，而其他的数据的版本号仍然是2，插入完成之后commit，事务结束。  

事务A，系统版本号2：再次select * from books；只能读取<=2的数据，事务B新插入的那条数据版本号为3，因此读不出来，解决了幻读的问题。    
## 数据库的锁
### 乐观锁与悲观锁

乐观锁, 顾名思义，就是很乐观，每次去拿数据的时候都认为别人不会修改，所以不会上锁，但是在更新的时候会判断一下在此期间别人有没有去更新这个数据，可以使用版本号等机制。乐观锁适用于多读的应用类型，这样可以提高吞吐量，像数据库如果提供类似于write_condition机制的其实都是提供的乐观锁。


悲观锁，顾名思义，就是很悲观，每次去拿数据的时候都认为别人会修改，所以每次在拿数据的时候都会上锁，这样别人想拿这个数据就会block直到它拿到锁。传统的关系型数据库里边就用到了很多这种锁机制，比如**行锁，表锁等，读锁，写锁等，都是在做操作之前先上锁。**

### 共享锁(读锁,S锁)
被加锁的对象可以被持锁事务读取，但是不能被修改，其他事务也可以在上面再加共享锁。

兼容性：如果数据资源上放置了共享锁，还能再放置共享锁和更新锁。

并发性能：具有良好的并发性能，当数据被放置共享锁后，还可以再放置共享锁或更新锁。所以并发性能很好。

### 排他锁(写锁,X锁)
被加锁的对象只能被持有锁的事务读取和修改，其他事务无法在该对象上加其他锁，也不能读取和修改该对象

兼容性：独占锁不能和其他锁兼容，如果数据资源上已经加了独占锁，就不能再放置其他的锁了。同样，如果数据资源上已经放置了其他锁，那么也就不能再放置独占锁了。

并发性能：最差。只允许一个事务访问锁定的数据，如果其他事务也需要访问该数据，就必须等待。

### 更新锁(意向锁)
更新锁在的初始化阶段用来锁定可能要被修改的资源，这可以避免使用共享锁造成的死锁现象。例如，对于以下的update语句：

UPDATE accounts SET balance=900 WHERE id=1

更新操作需要分两步：读取accounts表中id为1的记录 –> 执行更新操作。

如果在第一步使用共享锁，再第二步把锁升级为独占锁，就可能出现死锁现象。例如：两个事务都获取了同一数据资源的共享锁，然后都要把锁升级为独占锁，但需要等待另一个事务解除共享锁才能升级为独占锁，这就造成了死锁。

更新锁有如下特征：

加锁与解锁：当一个事务执行update语句时，数据库系统会先为事务分配一把更新锁。当读取数据完毕，执行更新操作时，会把更新锁升级为独占锁。

兼容性：更新锁与共享锁是兼容的，也就是说，一个资源可以同时放置更新锁和共享锁，但是最多放置一把更新锁。这样，当多个事务更新相同的数据时，只有一个事务能获得更新锁，然后再把更新锁升级为独占锁，其他事务必须等到前一个事务结束后，才能获取得更新锁，这就避免了死锁。

并发性能：允许多个事务同时读锁定的资源，但不允许其他事务修改它。


### 锁粒度
表级锁：开销小，加锁快；不会出现死锁；锁定粒度大，发生锁冲突的概率最高，并发度最低。  
这些存储引擎通过总是一次性同时获取所有需要的锁以及总是按相同的顺序获取表锁来避免死锁。  

表级锁更适合于以查询为主，并发用户少，只有少量按索引条件更新数据的应用，如Web 应用  

行级锁：开销大，加锁慢；会出现死锁；锁定粒度最小，发生锁冲突的概率最低，并发度也最高。  
最大程度的支持并发，同时也带来了最大的锁开销。  
在 InnoDB 中，除单个 SQL 组成的事务外，  
锁是逐步获得的，这就决定了在 InnoDB 中发生死锁是可能的。  
行级锁只在存储引擎层实现，而Mysql服务器层没有实现。 行级锁更适合于有大量按索引条件并发更新少量不同数据，同时又有并发查询的应用，如一些在线事务处理（OLTP）系统  

页面锁：开销和加锁时间界于表锁和行锁之间；会出现死锁；锁定粒度界于表锁和行锁之间，并发度一般  





# 一、基础

模式定义了数据如何存储、存储什么样的数据以及数据如何分解等信息，数据库和表都有模式。

主键的值不允许修改，也不允许复用（不能将已经删除的主键值赋给新数据行的主键）。

SQL（Structured Query Language)，标准 SQL 由 ANSI 标准委员会管理，从而称为 ANSI SQL。各个 DBMS 都有自己的实现，如 PL/SQL、Transact-SQL 等。

SQL 语句不区分大小写，但是数据库表名、列名和值是否区分依赖于具体的 DBMS 以及配置。

SQL 支持以下三种注释：

```sql
# 注释
SELECT *
FROM mytable; -- 注释
/* 注释1
   注释2 */
```

数据库创建与使用：

```sql
CREATE DATABASE test;
USE test;
```

# 二、创建表

```sql
CREATE TABLE mytable (
  # int 类型，不为空，自增
  id INT NOT NULL AUTO_INCREMENT,
  # int 类型，不可为空，默认值为 1，不为空
  col1 INT NOT NULL DEFAULT 1,
  # 变长字符串类型，最长为 45 个字符，可以为空
  col2 VARCHAR(45) NULL,
  # 日期类型，可为空
  col3 DATE NULL,
  # 设置主键为 id
  PRIMARY KEY (`id`));
```

# 三、修改表

添加列

```sql
ALTER TABLE mytable
ADD col CHAR(20);
```

删除列

```sql
ALTER TABLE mytable
DROP COLUMN col;
```

删除表

```sql
DROP TABLE mytable;
```

# 四、插入

普通插入

```sql
INSERT INTO mytable(col1, col2)
VALUES(val1, val2);
```

插入检索出来的数据

```sql
INSERT INTO mytable1(col1, col2)
SELECT col1, col2
FROM mytable2;
```

将一个表的内容插入到一个新表

```sql
CREATE TABLE newtable AS
SELECT * FROM mytable;
```

# 五、更新

```sql
UPDATE mytable
SET col = val
WHERE id = 1;
```

# 六、删除

```sql
DELETE FROM mytable
WHERE id = 1;
```

**TRUNCATE TABLE**  可以清空表，也就是删除所有行。

```sql
TRUNCATE TABLE mytable;
```

使用更新和删除操作时一定要用 WHERE 子句，不然会把整张表的数据都破坏。可以先用 SELECT 语句进行测试，防止错误删除。

# 七、查询

## DISTINCT

相同值只会出现一次。它作用于所有列，也就是说所有列的值都相同才算相同。

```sql
SELECT DISTINCT col1, col2
FROM mytable;
```

## LIMIT

限制返回的行数。可以有两个参数，第一个参数为起始行，从 0 开始；第二个参数为返回的总行数。

返回前 5 行：

```sql
SELECT *
FROM mytable
LIMIT 5;
```

```sql
SELECT *
FROM mytable
LIMIT 0, 5;
```

返回第 3 \~ 5 行：

```sql
SELECT *
FROM mytable
LIMIT 2, 3;
```

# 八、排序

-  **ASC** ：升序（默认）
-  **DESC** ：降序

可以按多个列进行排序，并且为每个列指定不同的排序方式：

```sql
SELECT *
FROM mytable
ORDER BY col1 DESC, col2 ASC;
```

# 九、过滤

不进行过滤的数据非常大，导致通过网络传输了多余的数据，从而浪费了网络带宽。因此尽量使用 SQL 语句来过滤不必要的数据，而不是传输所有的数据到客户端中然后由客户端进行过滤。

```sql
SELECT *
FROM mytable
WHERE col IS NULL;
```

下表显示了 WHERE 子句可用的操作符

|  操作符 | 说明  |
| :---: | :---: |
| = | 等于 |
| &lt; | 小于 |
| &gt; | 大于 |
| &lt;&gt; != | 不等于 |
| &lt;= !&gt; | 小于等于 |
| &gt;= !&lt; | 大于等于 |
| BETWEEN | 在两个值之间 |
| IS NULL | 为 NULL 值 |

应该注意到，NULL 与 0、空字符串都不同。

**AND 和 OR**  用于连接多个过滤条件。优先处理 AND，当一个过滤表达式涉及到多个 AND 和 OR 时，可以使用 () 来决定优先级，使得优先级关系更清晰。

**IN**  操作符用于匹配一组值，其后也可以接一个 SELECT 子句，从而匹配子查询得到的一组值。

**NOT**  操作符用于否定一个条件。

# 十、通配符

通配符也是用在过滤语句中，但它只能用于文本字段。

-  **%**  匹配 >=0 个任意字符；

-  **\_**  匹配 ==1 个任意字符；

-  **[ ]**  可以匹配集合内的字符，例如 [ab] 将匹配字符 a 或者 b。用脱字符 ^ 可以对其进行否定，也就是不匹配集合内的字符。

使用 Like 来进行通配符匹配。

```sql
SELECT *
FROM mytable
WHERE col LIKE '[^AB]%'; -- 不以 A 和 B 开头的任意文本
```

不要滥用通配符，通配符位于开头处匹配会非常慢。

# 十一、计算字段

在数据库服务器上完成数据的转换和格式化的工作往往比客户端上快得多，并且转换和格式化后的数据量更少的话可以减少网络通信量。

计算字段通常需要使用  **AS**  来取别名，否则输出的时候字段名为计算表达式。

```sql
SELECT col1 * col2 AS alias
FROM mytable;
```

**CONCAT()**  用于连接两个字段。许多数据库会使用空格把一个值填充为列宽，因此连接的结果会出现一些不必要的空格，使用 **TRIM()** 可以去除首尾空格。

```sql
SELECT CONCAT(TRIM(col1), '(', TRIM(col2), ')') AS concat_col
FROM mytable;
```

# 十二、函数

各个 DBMS 的函数都是不相同的，因此不可移植，以下主要是 MySQL 的函数。

## 汇总

|函 数 |说 明|
| :---: | :---: |
| AVG() | 返回某列的平均值 |
| COUNT() | 返回某列的行数 |
| MAX() | 返回某列的最大值 |
| MIN() | 返回某列的最小值 |
| SUM() |返回某列值之和 |

AVG() 会忽略 NULL 行。

使用 DISTINCT 可以汇总不同的值。

```sql
SELECT AVG(DISTINCT col1) AS avg_col
FROM mytable;
```

## 文本处理

| 函数  | 说明  |
| :---: | :---: |
|  LEFT() |  左边的字符 |
| RIGHT() | 右边的字符 |
| LOWER() | 转换为小写字符 |
| UPPER() | 转换为大写字符 |
| LTRIM() | 去除左边的空格 |
| RTRIM() | 去除右边的空格 |
| LENGTH() | 长度 |
| SOUNDEX() | 转换为语音值 |

其中， **SOUNDEX()**  可以将一个字符串转换为描述其语音表示的字母数字模式。

```sql
SELECT *
FROM mytable
WHERE SOUNDEX(col1) = SOUNDEX('apple')
```

## 日期和时间处理


- 日期格式：YYYY-MM-DD
- 时间格式：HH:<zero-width space>MM:SS

|函 数 | 说 明|
| :---: | :---: |
| ADDDATE() | 增加一个日期（天、周等）|
| ADDTIME() | 增加一个时间（时、分等）|
| CURDATE() | 返回当前日期 |
| CURTIME() | 返回当前时间 |
| DATE() |返回日期时间的日期部分|
| DATEDIFF() |计算两个日期之差|
| DATE_ADD() |高度灵活的日期运算函数|
| DATE_FORMAT() |返回一个格式化的日期或时间串|
| DAY()| 返回一个日期的天数部分|
| DAYOFWEEK() |对于一个日期，返回对应的星期几|
| HOUR() |返回一个时间的小时部分|
| MINUTE() |返回一个时间的分钟部分|
| MONTH() |返回一个日期的月份部分|
| NOW() |返回当前日期和时间|
| SECOND() |返回一个时间的秒部分|
| TIME() |返回一个日期时间的时间部分|
| YEAR() |返回一个日期的年份部分|

```sql
mysql> SELECT NOW();
```

```
2018-4-14 20:25:11
```

## 数值处理

| 函数 | 说明 |
| :---: | :---: |
| SIN() | 正弦 |
| COS() | 余弦 |
| TAN() | 正切 |
| ABS() | 绝对值 |
| SQRT() | 平方根 |
| MOD() | 余数 |
| EXP() | 指数 |
| PI() | 圆周率 |
| RAND() | 随机数 |

# 十三、分组

把具有相同的数据值的行放在同一组中。

可以对同一分组数据使用汇总函数进行处理，例如求分组数据的平均值等。

指定的分组字段除了能按该字段进行分组，也会自动按该字段进行排序。

```sql
SELECT col, COUNT(*) AS num
FROM mytable
GROUP BY col;
```

GROUP BY 自动按分组字段进行排序，ORDER BY 也可以按汇总字段来进行排序。

```sql
SELECT col, COUNT(*) AS num
FROM mytable
GROUP BY col
ORDER BY num;
```

WHERE 过滤行，HAVING 过滤分组，行过滤应当先于分组过滤。

```sql
SELECT col, COUNT(*) AS num
FROM mytable
WHERE col > 2
GROUP BY col
HAVING num >= 2;
```

分组规定：

- GROUP BY 子句出现在 WHERE 子句之后，ORDER BY 子句之前；
- 除了汇总字段外，SELECT 语句中的每一字段都必须在 GROUP BY 子句中给出；
- NULL 的行会单独分为一组；
- 大多数 SQL 实现不支持 GROUP BY 列具有可变长度的数据类型。

# 十四、子查询

子查询中只能返回一个字段的数据。

可以将子查询的结果作为 WHRER 语句的过滤条件：

```sql
SELECT *
FROM mytable1
WHERE col1 IN (SELECT col2
               FROM mytable2);
```

下面的语句可以检索出客户的订单数量，子查询语句会对第一个查询检索出的每个客户执行一次：

```sql
SELECT cust_name, (SELECT COUNT(*)
                   FROM Orders
                   WHERE Orders.cust_id = Customers.cust_id)
                   AS orders_num
FROM Customers
ORDER BY cust_name;
```

# 十五、连接

连接用于连接多个表，使用 JOIN 关键字，并且条件语句使用 ON 而不是 WHERE。

连接可以替换子查询，并且比子查询的效率一般会更快。

可以用 AS 给列名、计算字段和表名取别名，给表名取别名是为了简化 SQL 语句以及连接相同表。

## 内连接

内连接又称等值连接，使用 INNER JOIN 关键字。

```sql
SELECT A.value, B.value
FROM tablea AS A INNER JOIN tableb AS B
ON A.key = B.key;
```

可以不明确使用 INNER JOIN，而使用普通查询并在 WHERE 中将两个表中要连接的列用等值方法连接起来。

```sql
SELECT A.value, B.value
FROM tablea AS A, tableb AS B
WHERE A.key = B.key;
```

## 自连接

自连接可以看成内连接的一种，只是连接的表是自身而已。

一张员工表，包含员工姓名和员工所属部门，要找出与 Jim 处在同一部门的所有员工姓名。

子查询版本

```sql
SELECT name
FROM employee
WHERE department = (
      SELECT department
      FROM employee
      WHERE name = "Jim");
```

自连接版本

```sql
SELECT e1.name
FROM employee AS e1 INNER JOIN employee AS e2
ON e1.department = e2.department
      AND e2.name = "Jim";
```

## 自然连接

自然连接是把同名列通过等值测试连接起来的，同名列可以有多个。

内连接和自然连接的区别：内连接提供连接的列，而自然连接自动连接所有同名列。

```sql
SELECT A.value, B.value
FROM tablea AS A NATURAL JOIN tableb AS B;
```

## 外连接

外连接保留了没有关联的那些行。分为左外连接，右外连接以及全外连接，左外连接就是保留左表没有关联的行。

检索所有顾客的订单信息，包括还没有订单信息的顾客。

```sql
SELECT Customers.cust_id, Orders.order_num
FROM Customers LEFT OUTER JOIN Orders
ON Customers.cust_id = Orders.cust_id;
```

customers 表：

| cust_id | cust_name |
| :---: | :---: |
| 1 | a |
| 2 | b |
| 3 | c |

orders 表：

| order_id | cust_id |
| :---: | :---: |
|1    | 1 |
|2    | 1 |
|3    | 3 |
|4    | 3 |

结果：

| cust_id | cust_name | order_id |
| :---: | :---: | :---: |
| 1 | a | 1 |
| 1 | a | 2 |
| 3 | c | 3 |
| 3 | c | 4 |
| 2 | b | Null |

# 十六、组合查询

使用  **UNION**  来组合两个查询，如果第一个查询返回 M 行，第二个查询返回 N 行，那么组合查询的结果一般为 M+N 行。

每个查询必须包含相同的列、表达式和聚集函数。

默认会去除相同行，如果需要保留相同行，使用 UNION ALL。

只能包含一个 ORDER BY 子句，并且必须位于语句的最后。

```sql
SELECT col
FROM mytable
WHERE col = 1
UNION
SELECT col
FROM mytable
WHERE col =2;
```

# 十七、视图

视图是虚拟的表，本身不包含数据，也就不能对其进行索引操作。

对视图的操作和对普通表的操作一样。

视图具有如下好处：

- 简化复杂的 SQL 操作，比如复杂的连接；
- 只使用实际表的一部分数据；
- 通过只给用户访问视图的权限，保证数据的安全性；
- 更改数据格式和表示。

```sql
CREATE VIEW myview AS
SELECT Concat(col1, col2) AS concat_col, col3*col4 AS compute_col
FROM mytable
WHERE col5 = val;
```

# 十八、存储过程

存储过程可以看成是对一系列 SQL 操作的批处理。

使用存储过程的好处：

- 代码封装，保证了一定的安全性；
- 代码复用；
- 由于是预先编译，因此具有很高的性能。

命令行中创建存储过程需要自定义分隔符，因为命令行是以 ; 为结束符，而存储过程中也包含了分号，因此会错误把这部分分号当成是结束符，造成语法错误。

包含 in、out 和 inout 三种参数。

给变量赋值都需要用 select into 语句。

每次只能给一个变量赋值，不支持集合的操作。

```sql
delimiter //

create procedure myprocedure( out ret int )
    begin
        declare y int;
        select sum(col1)
        from mytable
        into y;
        select y*y into ret;
    end //

delimiter ;
```

```sql
call myprocedure(@ret);
select @ret;
```

# 十九、游标

在存储过程中使用游标可以对一个结果集进行移动遍历。

游标主要用于交互式应用，其中用户需要对数据集中的任意行进行浏览和修改。

使用游标的四个步骤：

1. 声明游标，这个过程没有实际检索出数据；
2. 打开游标；
3. 取出数据；
4. 关闭游标；

```sql
delimiter //
create procedure myprocedure(out ret int)
    begin
        declare done boolean default 0;

        declare mycursor cursor for
        select col1 from mytable;
        # 定义了一个 continue handler，当 sqlstate '02000' 这个条件出现时，会执行 set done = 1
        declare continue handler for sqlstate '02000' set done = 1;

        open mycursor;

        repeat
            fetch mycursor into ret;
            select ret;
        until done end repeat;

        close mycursor;
    end //
 delimiter ;
```

# 二十、触发器

触发器会在某个表执行以下语句时而自动执行：DELETE、INSERT、UPDATE。

触发器必须指定在语句执行之前还是之后自动执行，之前执行使用 BEFORE 关键字，之后执行使用 AFTER 关键字。BEFORE 用于数据验证和净化，AFTER 用于审计跟踪，将修改记录到另外一张表中。

INSERT 触发器包含一个名为 NEW 的虚拟表。

```sql
CREATE TRIGGER mytrigger AFTER INSERT ON mytable
FOR EACH ROW SELECT NEW.col into @result;

SELECT @result; -- 获取结果
```

DELETE 触发器包含一个名为 OLD 的虚拟表，并且是只读的。

UPDATE 触发器包含一个名为 NEW 和一个名为 OLD 的虚拟表，其中 NEW 是可以被修改的，而 OLD 是只读的。

MySQL 不允许在触发器中使用 CALL 语句，也就是不能调用存储过程。

# 二十一、事务管理

基本术语：

- 事务（transaction）指一组 SQL 语句；
- 回退（rollback）指撤销指定 SQL 语句的过程；
- 提交（commit）指将未存储的 SQL 语句结果写入数据库表；
- 保留点（savepoint）指事务处理中设置的临时占位符（placeholder），你可以对它发布回退（与回退整个事务处理不同）。

不能回退 SELECT 语句，回退 SELECT 语句也没意义；也不能回退 CREATE 和 DROP 语句。

MySQL 的事务提交默认是隐式提交，每执行一条语句就把这条语句当成一个事务然后进行提交。当出现 START TRANSACTION 语句时，会关闭隐式提交；当 COMMIT 或 ROLLBACK 语句执行后，事务会自动关闭，重新恢复隐式提交。

设置 autocommit 为 0 可以取消自动提交；autocommit 标记是针对每个连接而不是针对服务器的。

如果没有设置保留点，ROLLBACK 会回退到 START TRANSACTION 语句处；如果设置了保留点，并且在 ROLLBACK 中指定该保留点，则会回退到该保留点。

```sql
START TRANSACTION
// ...
SAVEPOINT delete1
// ...
ROLLBACK TO delete1
// ...
COMMIT
```

# 二十二、字符集

基本术语：

- 字符集为字母和符号的集合；
- 编码为某个字符集成员的内部表示；
- 校对字符指定如何比较，主要用于排序和分组。

除了给表指定字符集和校对外，也可以给列指定：

```sql
CREATE TABLE mytable
(col VARCHAR(10) CHARACTER SET latin COLLATE latin1_general_ci )
DEFAULT CHARACTER SET hebrew COLLATE hebrew_general_ci;
```

可以在排序、分组时指定校对：

```sql
SELECT *
FROM mytable
ORDER BY col COLLATE latin1_general_ci;
```

# 二十三、权限管理

MySQL 的账户信息保存在 mysql 这个数据库中。

```sql
USE mysql;
SELECT user FROM user;
```

**创建账户** 

新创建的账户没有任何权限。

```sql
CREATE USER myuser IDENTIFIED BY 'mypassword';
```

**修改账户名** 

```sql
RENAME USER myuser TO newuser;
```

**删除账户** 

```sql
DROP USER myuser;
```

**查看权限** 

```sql
SHOW GRANTS FOR myuser;
```

**授予权限** 

账户用 username@host 的形式定义，username@% 使用的是默认主机名。

```sql
GRANT SELECT, INSERT ON mydatabase.* TO myuser;
```

**删除权限** 

GRANT 和 REVOKE 可在几个层次上控制访问权限：

- 整个服务器，使用 GRANT ALL 和 REVOKE ALL；
- 整个数据库，使用 ON database.\*；
- 特定的表，使用 ON database.table；
- 特定的列；
- 特定的存储过程。

```sql
REVOKE SELECT, INSERT ON mydatabase.* FROM myuser;
```

**更改密码** 

必须使用 Password() 函数进行加密。

```sql
SET PASSWROD FOR myuser = Password('new_password');
```

# sql执行顺序

查询语句中select from where group by having order by limit的执行顺序

## sql查询的执行顺序
书写顺序:

select
from
where
join on
group by
having
order by
limit

执行顺序:

from
join on
where
group by
having
select
distinct
order by
limit 

from:需要从哪个数据表检索数据

where:过滤表中数据的条件

group by:如何将上面过滤出的数据分组

having:对上面已经分组的数据进行过滤的条件

select:查看结果集中的哪个列，或列的计算结果

order by :按照什么样的顺序来查看返回的数据

limit :截取出目标页数据

## from 和 where 内部的解析顺序
from后面的表关联，是自右向左解析的

而where条件的解析顺序是自下而上的。

也就是说，在写SQL文的时候，尽量把数据量大的表放在最右边来进行关联，

而把能筛选出大量数据的条件放在where语句的最下面。

# mysql 数据库的备份
备份:
1. 开启binlog
   https://blog.csdn.net/king_kgh/article/details/74800513  
   https://blog.51cto.com/lookingdream/1921162
   https://learnku.com/articles/20702
2. mysqldump 全量备份
恢复
1. mysqldump全量恢复
2. binlog指定恢复  全量备份之后的部分小数据


# count(1),count(*),count(列名)

count(*) 和 count(1) 一样,会统计表中的所有的记录数，包含字段为null 的记录。

count(列名)只包括列名那一列，在统计结果的时候，会忽略列值为空（这里的空不是只空字符串或者0，而是表示null）的计数，即某个字段值为NULL时，不统计。

# mysql转义字符

## 转义字符

\0  
一个ASCII  0  (NUL)字符。  
\n  
一个新行符。  
\t  
一个定位符。  
\r  
一个回车符。  
\b  
一个退格符。  
\ '  
一个单引号(“ '”)符。  
\ "  
一个双引号(“ "”)符。  
\\  
一个反斜线(“\”)符。  
\%  
一个“%”符。它用于在正文中搜索“%”的文字实例，否则这里“%”将解释为一个通配符。  
\_  
一个“_”符。它用于在正文中搜索“_”的文字实例，否则这里“_”将解释为一个通配符。  
注意，如果你在某些正文环境中使用“\%”或“\%_”，这些将返回字符串“\%”和“\_”而不是“%”和“_”。  

举一个MySQL中使用转义字符的SQL语句的例子：

1）查找th_article表中的art_content字段，找出其中带有“\"”的记录
select * from th_article where art_content like '%\\\"%'

2）将上面找出来的记录更新，“\"”修改为“"”
update th_article set art_content = replace(art_content, '\\\"', '\"') where art_content like '%\\\"%'

## 双引号下的应用

当字符串用双引号引起来的时候,如果内部有需要转义的字符,则会用到转义字符,比如

```sql
select * from user where name like "%\"sx\"%";
意思是 匹配名为 %"sx"% 的名字
```



## 单引号下的应用

但是用单引号引起来,就代表内部是一个字符串了,不需要用转义字符

```sql
SELECT * FROM notice_73 where text = '<p style="color:white;font-size:14px">您的（标题/封面/视频）中含有违禁信息，根据相关法律法规政策，予以下架。</p>';
```


