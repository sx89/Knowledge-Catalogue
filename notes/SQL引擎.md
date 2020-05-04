# 星型模型和雪花型模型比较

https://blog.csdn.net/nisjlvhudy/article/details/7889422



# HBase 上的 SQL 引擎，Kylin 和 Phoenix 的不同

https://www.cnblogs.com/bonelee/p/12442904.html

1）Kylin 和 Phoenix 虽然同为 Hadoop/HBase 上的 SQL 引擎，两者的定位不同，一个是 OLAP，另一个是 OLTP，服务于不同的场景；

2）Phoenix 更多的是适用于以往关系型数据库的相关操作，当查询语句是点查找和小范围扫描时，Phoenix 可以比较好地满足，而它不太适合大量 scan 类型的 OLAP 查询，或查询的模式较为灵活的场景；

3）Kylin 是一个只读型的分析引擎，不适合细粒度修改数据，但适合做海量数据的交互式在线分析，通常跟数据仓库以及 BI 工具结合使用，目标用户为业务分析人员。

# OLAP、OLTP的介绍和比较

https://blog.csdn.net/zhangzheng0413/article/details/8271322

数据处理大致可以分成两大类：联机事务处理OLTP（on-line transaction processing）、联机分析处理OLAP（On-Line Analytical Processing）。OLTP是传统的关系型数据库的主要应用，主要是基本的、日常的事务处理，例如银行交易。OLAP是数据仓库系统的主要应用，支持复杂的分析操作，侧重决策支持，并且提供直观易懂的查询结果。 