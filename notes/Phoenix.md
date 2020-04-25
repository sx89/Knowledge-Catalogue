# Phoenix简介

## 1.1 Phoenix定义

Phoenix是HBase的开源SQL皮肤。可以使用标准JDBC API代替HBase客户端API来创建表，插入数据和查询HBase数据。

## 1.2 Phoenix特点

1) 容易集成：如Spark，Hive，Pig，Flume和Map Reduce。

2) 性能好：直接使用HBase API以及协处理器和自定义过滤器，可以为小型查询提供毫秒级的[性能](http://phoenix.apache.org/performance.html)，或者为数千万行提供数秒的性能。

3) 操作简单：DML命令以及通过DDL命令创建表和版本化增量更改。

4) 安全功能: [支持GRANT和REVOKE](https://issues.apache.org/jira/browse/PHOENIX-672) 。

5) 完美支持Hbase二级索引创建。

## Phoenix和Hive的区别

Hive是HDFS的SQL层
Phoenix是HBase的SQL层

对象不一样
使用场景区分很明显

Hive感觉更适合离线资料分析
并且无效能要求那种

Phoenix由于是搭载HBase上
所以比较适合一些real time的
当然对离线资料也是可以的

## Pheonix操作

## shell操作

### 表的操作

put ,insert,update,delete,create,select之类的,用到再查 

### 表的映射

当hbase中不存在表的时候,用pheonix的命令创建的时候,会自动关联hbase和pheonix

当hbase中存在表的时候,可以用创建视图的方式关联表,但视图只能查询,不能删改原来的数据表的信息.但视图可以删改.

