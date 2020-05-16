

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



## Driver与Excetor

todo

![image-20200514214836700](pictures/Spark/image-20200514214836700.png)



## WordCount思路

<img src="pictures/Spark/image-20200515083032331.png" alt="image-20200515083032331" style="zoom:50%;" />



<img src="pictures/Spark/image-20200515084905898.png" alt="image-20200515084905898" style="zoom:50%;" />

<img src="pictures/Spark/image-20200515090029542.png" alt="image-20200515090029542" style="zoom:50%;" />



### 代码TODO





## Spark在Yarn下运行

## 使用yarn

<img src="pictures/Spark/image-20200515105355707.png" alt="image-20200515105355707" style="zoom:50%;" />

<img src="pictures/Spark/image-20200515105318425.png" alt="image-20200515105318425" style="zoom:50%;" />

## 原理

### Yarn的架构

<img src="pictures/Spark/image-20200515160953190.png" alt="image-20200515160953190" style="zoom:50%;" />



### Spark向Yarn提交任务的过程

提交代码

<img src="pictures/Spark/image-20200515162145384.png" alt="image-20200515162145384" style="zoom:50%;" />



<img src="pictures/Spark/image-20200515161613951.png" alt="image-20200515161613951" style="zoom:50%;" />

# RDD

## JavaIO

<img src="pictures/Spark/image-20200515163917682.png" alt="image-20200515163917682" style="zoom:50%;" />

BufferedInputStream是一个缓冲区

FileInputStream是实际读取文件者

### 字符流的读取

<img src="pictures/Spark/image-20200515164659560.png" alt="image-20200515164659560" style="zoom:50%;" />



FileInputStream是实际读取文件者

InputStreamReader（new FileInputStream（“xx”），“UTF-8”）；负责把FileInputStream的读取转换成UTF-8的格式

BufferedReader是一个字符缓冲区，把UTF-8的数据放入缓冲区，存满后发给java程序

最后的readline的时候才是真正的java读取



## RDD介绍

RDD(Resilient Distributed Dataset) 叫着 弹性分布式数据集 ，是Spark 中最基本的抽象，它代表一个不可变、可分区、里面元素可以并行计算的集合。

**不可变的意思是内部不提供改变数据的方法，类似于string类型不同改变string的方法，substring之类的函数其实是创建一个新的字符串，不可变是因为rdd的数据要给很多程序来用。**

RDD 具有数据流模型特点：自动容错、位置感知性调度和可伸缩。

RDD 允许用户在执行多个查询时，显示地将工作集缓存在内存中，后续的查询能够重用工作集，这将会极大的提升查询的效率。

我们可以认为 RDD 就是一个代理，我们操作这个代理就像操作本地集合一样，不需去关心任务调度、容错等问题。

### RDD的原理

<img src="pictures/Spark/image-20200516140153953.png" alt="image-20200516140153953" style="zoom:50%;" />

当触发collect的时候才会执行图中的逻辑。在此之前知识数据处理逻辑的封装。

RDD里面封装的是数据处理逻辑，而非数据。



## RDD 的属性

**一组分片（Partition），即数据集的基本组成单位**。 对于RDD来说，每个分片都会被一个计算任务处理，并决定并行计算的粒度。用户可以在创建RDD 的时候指定RDD的分片个数，如果没有指定，那么就会采用默认值。默认值就是程序所分配到的CPU Cores 的数目；

**对于RDD来说，每个分片都会被一个计算任务处理，并决定并行计算的粒度**。用户可以在创建RDD 的时候指定RDD的分片个数，如果没有指定，那么就会采用默认值。默认值就是程序所分配到的CPU Cores 的数目；

**RDD 之间互相存在依赖关系**。 RDD 的每次转换都会生成一个新的 RDD ,所以 RDD 之前就会形成类似于流水线一样的前后依赖关系。在部分分区数据丢失时，Spark 可以通过这个依赖关系重新计算丢失部分的分区数据，而不是对 RDD 的所有分区进行重新计算。

**一个Partitioner ，即 RDD 的分片函数** 。当前Spark 中实现了两种类型的分片函数，一个是基于哈希的 HashPartitioner ，另外一个是基于范围的 RangePartitioner。只有对于key-value的RDD ,才会有 Partitioner,非 key-value 的RDD 的 Partitioner 的值是None。Partitioner 函数不但决定了RDD 本身的分片数量，也决定了 Parent RDD Shuffle 输出时的分片数量。

**一个列表，存储存取每个Partition 的优先位置（preferred location）**。 对于一个HDFS 文件来说，这个列表保存的就是每个 Partition 所在的块位置。安装“移动数据不如移动计算”的理念，Spark 在进行任务调度的时候，会尽可能地将计算任务分配到其所要处理数据块的存储位置。

<img src="pictures/Spark/image-20200516142759356.png" alt="image-20200516142759356" style="zoom:50%;" />



## RDD的特点

RDD表示只读的分区的数据集，对RDD进行改动，只能通过RDD的转换操作，由一个RDD得到一个新的RDD，新的RDD包含了从其他RDD衍生所必需的信息。RDDs之间存在依赖，RDD的执行是按照血缘关系延时计算的。如果血缘关系较长，可以通过持久化RDD来切断血缘关系。

### 分区

RDD逻辑上是分区的，每个分区的数据是抽象存在的，计算的时候会通过一个compute函数得到每个分区的数据。如果RDD是通过已有的文件系统构建，则compute函数是读取指定文件系统中的数据，如果RDD是通过其他RDD转换而来，则compute函数是执行转换逻辑将其他RDD的数据进行转换。

### 只读

RDD是只读的，要想改变RDD中的数据，只能在现有的RDD基础上创建新的RDD。

由一个RDD转换到另一个RDD，可以通过丰富的操作算子实现，不再像MapReduce那样只能写map和reduce了

<img src="pictures/Spark/image-20200516143416833.png" alt="image-20200516143416833" style="zoom:50%;" />



RDD的操作算子包括两类，一类叫做transformations，它是用来将RDD进行转化，构建RDD的血缘关系；另一类叫做actions，它是用来触发RDD的计算，得到RDD的相关计算结果或者将RDD保存的文件系统中。下图是RDD所支持的操作算子列表。

### 依赖

RDDs通过操作算子进行转换，转换得到的新RDD包含了从其他RDDs衍生所必需的信息，RDDs之间维护着这种血缘关系，也称之为依赖。如下图所示，依赖包括两种，一种是窄依赖，RDDs之间分区是一一对应的，另一种是宽依赖，下游RDD的每个分区与上游RDD(也称之为父RDD)的每个分区都有关，是多对多的关系。

<img src="pictures/Spark/image-20200516143503992.png" alt="image-20200516143503992" style="zoom:50%;" />                               

### 缓存

如果在应用程序中多次使用同一个RDD，可以将该RDD缓存起来，该RDD只有在第一次计算的时候会根据血缘关系得到分区的数据，在后续其他地方用到该RDD的时候，会直接从缓存处取而不用再根据血缘关系计算，这样就加速后期的重用。如下图所示，RDD-1经过一系列的转换后得到RDD-n并保存到hdfs，RDD-1在这一过程中会有个中间结果，如果将其缓存到内存，那么在随后的RDD-1转换到RDD-m这一过程中，就不会计算其之前的RDD-0了。

<img src="pictures/Spark/image-20200516143515760.png" alt="image-20200516143515760" style="zoom:50%;" /> 

### CheckPoint

虽然RDD的血缘关系天然地可以实现容错，当RDD的某个分区数据失败或丢失，可以通过血缘关系重建。但是对于长时间迭代型应用来说，随着迭代的进行，RDDs之间的血缘关系会越来越长，一旦在后续迭代过程中出错，则需要通过非常长的血缘关系去重建，势必影响性能。为此，RDD支持checkpoint将数据保存到持久化的存储中，这样就可以切断之前的血缘关系，因为checkpoint后的RDD不需要知道它的父RDDs了，它可以从checkpoint处拿到数据。



### 依赖关系

RDD 和它依赖的 父 RDD(可能有多个) 的关系有两种不同的类型，即 窄依赖（narrow dependency）和宽依赖（wide dependency）。

**窄依赖**：窄依赖指的是每一个父 RDD 的 Partition 最多被子 RDD 的一个分区使用。可以比喻为独生子女。 **宽依赖**：宽依赖是多个字 RDD 的Partition 会依赖同一个父 RDD 的 Partition



## RDD编程

### RDD创建

在Spark中创建RDD的创建方式可以分为三种：从集合中创建RDD；从外部存储创建RDD；从其他RDD创建。

#### 从集合创建

<img src="pictures/Spark/image-20200516155333225.png" alt="image-20200516155333225" style="zoom:50%;" />

#### 从外部存储系统创建

<img src="pictures/Spark/image-20200516155648611.png" alt="image-20200516155648611" style="zoom:50%;" />

**分区问题**

<img src="pictures/Spark/image-20200516161848356.png" alt="image-20200516161848356" style="zoom:50%;" />



### 分区原理

根据读入文件的字节数大小假设是13KB，分区数为2，则分区为

13/2= 6...1

所以分区为

6   6  1 

最终有三个分区

## RDD的转换

RDD整体上分为Value类型和Key-Value类型

### Value类型

#### map(func)案例

1. 作用：返回一个新的RDD，该RDD由每一个输入元素经过func函数转换后组成

2. 需求：创建一个1-10数组的RDD，将所有元素*2形成新的RDD

<img src="pictures/Spark/image-20200516164459237.png" alt="image-20200516164459237" style="zoom:50%;" />

## RDD 的持久化

### RDD 的 cache(持久化)

Spark中最重要的功能之一是跨操作在内存中持久化（或缓存）数据集。当您持久保存RDD时，每个节点都会存储它在内存中计算的任何分区，并在该数据集（或从中派生的数据集）的其他操作中重用它们。这使得未来的行动更快（通常超过10倍）。缓存是迭代算法和快速交互使用的关键工具。

您可以使用persist()或cache()方法标记要保留的RDD 。第一次在动作中计算它，它将保留在节点的内存中。Spark的缓存是容错的 - 如果丢失了RDD的任何分区，它将使用最初创建它的转换自动重新计算。

### 持久化需求

1. 要求的计算速度快
2. 集群的资源要足够大
3. 重要: cache 的数据会多次触发Action
4. 建议先进行数据过滤,然后将缩小范围后的数据再cache 到内存中.


