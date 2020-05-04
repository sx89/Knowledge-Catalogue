

# Spark基础

## spark来历



1. MapReduce有很多弊端

<img src="pictures/Spark/image-20200501160954365.png" alt="image-20200501160954365" style="zoom:50%;" />

2. yarn使得资源调度（RM）和任务调度（Driver）解耦。通过AM连接。当task在NM中运行，当task需要更多资源的时候，driver通过AM中介，来跟RM交互，拿到资源，最后RM指挥NM来拿到资源给task用



<img src="pictures/Spark/image-20200501161217154.png" alt="image-20200501161217154" style="zoom:50%;" />



3.spark基于hadoop1.X来做，当时没有yarn，yarn是在hadoop2.X问世的

<img src="pictures/Spark/image-20200501161607095.png" alt="image-20200501161607095" style="zoom:50%;" />

适合迭代式计算是因为scala是函数式编程，可以不断地调用函数。

中间结果放在内存中。

spark改进hadoop1.0中MapReduce缺点的方案是：

<img src="pictures/Spark/image-20200501162224196.png" alt="image-20200501162224196" style="zoom:50%;" />

Worker是资源管理，Executor是计算。

Worker可以被yarn的NodeManager代替（资源管理可以被替代）但是计算框架spark比MapReduce要好用，所以当hadoop2.X实现yarn的资源调度+计算可插拔之后，大数据框架组合变成了hadoopHDFS存储+yarn资源调度+spark计算



## Spark内置模块

<img src="pictures/Spark/image-20200501163222694.png" alt="image-20200501163222694" style="zoom:50%;" />

<img src="pictures/Spark/image-20200501163245570.png" alt="image-20200501163245570" style="zoom:50%;" />



## Spark特点

<img src="pictures/Spark/image-20200501163444432.png" alt="image-20200501163444432" style="zoom:50%;" />



