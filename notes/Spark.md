

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

文件分区的分区数是最小分区数，内存分区的分区数是最大分区数。

**最小分区数**

根据读入文件的字节数大小假设是13KB，分区数为2，则分区为

13/2= 6...1

所以分区为

6   6  1 

最终有三个分区

**最大分区数**

13被拆分为  6  和 6+1

## RDD的转换

RDD整体上分为Value类型和Key-Value类型

### Value类型

#### map(func)案例

1. 作用：返回一个新的RDD，该RDD由每一个输入元素经过func函数转换后组成

2. 需求：创建一个1-10数组的RDD，将所有元素*2形成新的RDD

<img src="pictures/Spark/image-20200516164459237.png" alt="image-20200516164459237" style="zoom:50%;" />

####  mapPartitions(func) 案例

作用：类似于map，但独立地在RDD的每一个分片上运行，因此在类型为T的RDD上运行时，func的函数类型必须是Iterator[T] => Iterator[U]。假设有N个元素，有M个分区，那么map的函数的将被调用N次,而mapPartitions被调用M次,一个函数一次处理所有分区。

#### mapPartitionsWithIndex(func) 案例

作用：类似于mapPartitions，但func带有一个整数参数表示分片的索引值，因此在类型为T的RDD上运行时，func的函数类型必须是(Int, Interator[T]) => Iterator[U]；

### Driver与Excetor之间数据的传递

<img src="pictures/Spark/image-20200516221828499.png" alt="image-20200516221828499" style="zoom:50%;" />

Driver和Executor分别执行不同的代码：

<img src="pictures/Spark/image-20200516222040447.png" alt="image-20200516222040447" style="zoom:50%;" />

这就会涉及到如果Driver和Executor不在同一台机器，i的传播问题，如果i不能被序列化，则i无法在不同机器间传播，则会报错。



#### latMap(func) 案例

 作用：类似于map，但是每一个输入元素可以被映射为0或多个输出元素（所以func应该返回一个序列，而不是单一元素）

#### map()和mapPartition()的区别

1. map()：每次处理一条数据。

2. mapPartition()：每次处理一个分区的数据，这个分区的数据处理完后，原RDD中分区的数据才能释放，**可能导致OOM。**

3. 开发指导：当内存空间较大的时候建议使用mapPartition()，以提高处理效率。

####  glom案例

作用：将每一个分区形成一个数组，形成新的RDD类型时RDD[Array[T]]

####  groupBy(func)案例

作用：分组，按照传入函数的返回值进行分组。将相同的key对应的值放入一个迭代器。

需求：创建一个RDD，按照元素模以2的值进行分组。

（1）创建

scala> val rdd = sc.parallelize(1 to 4)

rdd: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[65] at parallelize at <console>:24

（2）按照元素模以2的值进行分组

scala> val group = rdd.groupBy(_%2)

group: org.apache.spark.rdd.RDD[(Int, Iterable[Int])] = ShuffledRDD[2] at groupBy at <console>:26

（3）打印结果

scala> group.collect

res0: Array[(Int, Iterable[Int])] = Array((0,CompactBuffer(2, 4)), (1,CompactBuffer(1, 3)))

#### filter(func) 案例

作用：过滤。返回一个新的RDD，该RDD由经过func函数计算后返回值为true的输入元素组成。

需求：创建一个RDD（由字符串组成），过滤出一个新RDD（包含”xiao”子串）

（1）创建

scala> var sourceFilter = sc.parallelize(Array("xiaoming","xiaojiang","xiaohe","dazhi"))

sourceFilter: org.apache.spark.rdd.RDD[String] = ParallelCollectionRDD[10] at parallelize at <console>:24

（2）打印

scala> sourceFilter.collect()

res9: Array[String] = Array(xiaoming, xiaojiang, xiaohe, dazhi)

（3）过滤出含” xiao”子串的形成一个新的RDD

scala> val filter = sourceFilter.filter(_.contains("xiao"))

filter: org.apache.spark.rdd.RDD[String] = MapPartitionsRDD[11] at filter at <console>:26

（4）打印新RDD

scala> filter.collect()

res10: Array[String] = Array(xiaoming, xiaojiang, xiaohe)

#### sample(withReplacement, fraction, seed) 案例

作用：以指定的随机种子随机抽样出数量为fraction的数据，withReplacement表示是抽出的数据是否放回，true为有放回的抽样，false为无放回的抽样，seed用于指定随机数生成器种子。

需求：创建一个RDD（1-10），从中选择放回和不放回抽样

（1）创建RDD

scala> val rdd = sc.parallelize(1 to 10)

rdd: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[20] at parallelize at <console>:24

（2）打印

scala> rdd.collect()

res15: Array[Int] = Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

（3）放回抽样

scala> var sample1 = rdd.sample(true,0.4,2)

sample1: org.apache.spark.rdd.RDD[Int] = PartitionwiseSampledRDD[21] at sample at <console>:26

（4）打印放回抽样结果

scala> sample1.collect()

res16: Array[Int] = Array(1, 2, 2, 7, 7, 8, 9)

（5）不放回抽样

scala> var sample2 = rdd.sample(false,0.2,3)

sample2: org.apache.spark.rdd.RDD[Int] = PartitionwiseSampledRDD[22] at sample at <console>:26

（6）打印不放回抽样结果

scala> sample2.collect()

res17: Array[Int] = Array(1, 9)

#### distinct([numTasks])) 案例

作用：对源RDD进行去重后返回一个新的RDD。默认情况下，只有8个并行任务来操作，但是可以传入一个可选的numTasks参数改变它。

需求：创建一个RDD，使用distinct()对其去重。

（1）创建一个RDD

scala> val distinctRdd = sc.parallelize(List(1,2,1,5,2,9,6,1))

distinctRdd: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[34] at parallelize at <console>:24

（2）对RDD进行去重（不指定并行度）

scala> val unionRDD = distinctRdd.distinct()

unionRDD: org.apache.spark.rdd.RDD[Int] = MapPartitionsRDD[37] at distinct at <console>:26

（3）打印去重后生成的新RDD

scala> unionRDD.collect()

res20: Array[Int] = Array(1, 9, 5, 6, 2)

（4）对RDD（指定并行度为2）

scala> val unionRDD = distinctRdd.distinct(2)

unionRDD: org.apache.spark.rdd.RDD[Int] = MapPartitionsRDD[40] at distinct at <console>:26

（5）打印去重后生成的新RDD

scala> unionRDD.collect()

res21: Array[Int] = Array(6, 2, 1, 9, 5)

#### coalesce(numPartitions) 案例

作用：缩减分区数，用于大数据集过滤后，提高小数据集的执行效率。

需求：创建一个4个分区的RDD，对其缩减分区

（1）创建一个RDD

scala> val rdd = sc.parallelize(1 to 16,4)

rdd: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[54] at parallelize at <console>:24

（2）查看RDD的分区数

scala> rdd.partitions.size

res20: Int = 4

（3）对RDD重新分区

scala> val coalesceRDD = rdd.coalesce(3)

coalesceRDD: org.apache.spark.rdd.RDD[Int] = CoalescedRDD[55] at coalesce at <console>:26

（4）查看新RDD的分区数

scala> coalesceRDD.partitions.size

res21: Int = 3

#### repartition(numPartitions) 案例

作用：根据分区数，重新通过网络随机洗牌所有数据。

需求：创建一个4个分区的RDD，对其重新分区

（1）创建一个RDD

scala> val rdd = sc.parallelize(1 to 16,4)

rdd: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[56] at parallelize at <console>:24

（2）查看RDD的分区数

scala> rdd.partitions.size

res22: Int = 4

（3）对RDD重新分区

scala> val rerdd = rdd.repartition(2)

rerdd: org.apache.spark.rdd.RDD[Int] = MapPartitionsRDD[60] at repartition at <console>:26

（4）查看新RDD的分区数

scala> rerdd.partitions.size

res23: Int = 2

####  coalesce和repartition的区别

coalesce重新分区，可以选择是否进行shuffle过程。由参数shuffle: Boolean = false/true决定。

repartition实际上是调用的coalesce，默认是进行shuffle的。源码如下：

def repartition(numPartitions: Int)(implicit ord: Ordering[T] = null): RDD[T] = withScope {
  coalesce(numPartitions, shuffle = true)
 }

#### sortBy(func,[ascending], [numTasks]) 案例

作用；使用func先对数据进行处理，按照处理后的数据比较结果排序，默认为正序。

需求：创建一个RDD，按照不同的规则进行排序

（1）创建一个RDD

scala> val rdd = sc.parallelize(List(2,1,3,4))

rdd: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[21] at parallelize at <console>:24

（2）按照自身大小排序

scala> rdd.sortBy(x => x).collect()

res11: Array[Int] = Array(1, 2, 3, 4)

（3）按照与3余数的大小排序

scala> rdd.sortBy(x => x%3).collect()

res12: Array[Int] = Array(3, 4, 1, 2)



#### pipe(command, [envVars]) 案例

作用：管道，针对每个分区，都执行一个shell脚本，返回输出的RDD。

注意：脚本需要放在Worker节点可以访问到的位置

需求：编写一个脚本，使用管道将脚本作用于RDD上。

（1）编写一个脚本

Shell脚本

\#!/bin/sh

echo "AA"

while read LINE; do

  echo ">>>"${LINE}

done

（2）创建一个只有一个分区的RDD

scala> val rdd = sc.parallelize(List("hi","Hello","how","are","you"),1)

rdd: org.apache.spark.rdd.RDD[String] = ParallelCollectionRDD[50] at parallelize at <console>:24

（3）将脚本作用该RDD并打印

scala> rdd.pipe("/opt/module/spark/pipe.sh").collect()

res18: Array[String] = Array(AA, >>>hi, >>>Hello, >>>how, >>>are, >>>you)

（4）创建一个有两个分区的RDD

scala> val rdd = sc.parallelize(List("hi","Hello","how","are","you"),2)

rdd: org.apache.spark.rdd.RDD[String] = ParallelCollectionRDD[52] at parallelize at <console>:24

（5）将脚本作用该RDD并打印

scala> rdd.pipe("/opt/module/spark/pipe.sh").collect()

res19: Array[String] = Array(AA, >>>hi, >>>Hello, AA, >>>how, >>>are, >>>you)

###  双Value类型交互

#### 2.3.2.1 union(otherDataset) 案例

\1. 作用：对源RDD和参数RDD求并集后返回一个新的RDD

\2. 需求：创建两个RDD，求并集

（1）创建第一个RDD

scala> val rdd1 = sc.parallelize(1 to 5)

rdd1: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[23] at parallelize at <console>:24

（2）创建第二个RDD

scala> val rdd2 = sc.parallelize(5 to 10)

rdd2: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[24] at parallelize at <console>:24

（3）计算两个RDD的并集

scala> val rdd3 = rdd1.union(rdd2)

rdd3: org.apache.spark.rdd.RDD[Int] = UnionRDD[25] at union at <console>:28

（4）打印并集结果

scala> rdd3.collect()

res18: Array[Int] = Array(1, 2, 3, 4, 5, 5, 6, 7, 8, 9, 10)

#### 2.3.2.2 subtract (otherDataset) 案例

\1. 作用：计算差的一种函数，去除两个RDD中相同的元素，不同的RDD将保留下来

\2. 需求：创建两个RDD，求第一个RDD与第二个RDD的差集

（1）创建第一个RDD

scala> val rdd = sc.parallelize(3 to 8)

rdd: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[70] at parallelize at <console>:24

（2）创建第二个RDD

scala> val rdd1 = sc.parallelize(1 to 5)

rdd1: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[71] at parallelize at <console>:24

（3）计算第一个RDD与第二个RDD的差集并打印

scala> rdd.subtract(rdd1).collect()

res27: Array[Int] = Array(8, 6, 7)

#### 2.3.2.3 intersection(otherDataset) 案例

\1. 作用：对源RDD和参数RDD求交集后返回一个新的RDD

\2. 需求：创建两个RDD，求两个RDD的交集

（1）创建第一个RDD

scala> val rdd1 = sc.parallelize(1 to 7)

rdd1: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[26] at parallelize at <console>:24

（2）创建第二个RDD

scala> val rdd2 = sc.parallelize(5 to 10)

rdd2: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[27] at parallelize at <console>:24

（3）计算两个RDD的交集

scala> val rdd3 = rdd1.intersection(rdd2)

rdd3: org.apache.spark.rdd.RDD[Int] = MapPartitionsRDD[33] at intersection at <console>:28

（4）打印计算结果

scala> rdd3.collect()

res19: Array[Int] = Array(5, 6, 7)

#### 2.3.2.4 cartesian(otherDataset) 案例

\1. 作用：笛卡尔积（尽量避免使用）

\2. 需求：创建两个RDD，计算两个RDD的笛卡尔积

（1）创建第一个RDD

scala> val rdd1 = sc.parallelize(1 to 3)

rdd1: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[47] at parallelize at <console>:24

（2）创建第二个RDD

scala> val rdd2 = sc.parallelize(2 to 5)

rdd2: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[48] at parallelize at <console>:24

（3）计算两个RDD的笛卡尔积并打印

scala> rdd1.cartesian(rdd2).collect()

res17: Array[(Int, Int)] = Array((1,2), (1,3), (1,4), (1,5), (2,2), (2,3), (2,4), (2,5), (3,2), (3,3), (3,4), (3,5))

#### 2.3.2.5 zip(otherDataset)案例

\1. 作用：将两个RDD组合成Key/Value形式的RDD,这里默认两个RDD的partition数量以及元素数量都相同，否则会抛出异常。

\2. 需求：创建两个RDD，并将两个RDD组合到一起形成一个(k,v)RDD

（1）创建第一个RDD

scala> val rdd1 = sc.parallelize(Array(1,2,3),3)

rdd1: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[1] at parallelize at <console>:24

（2）创建第二个RDD（与1分区数相同）

scala> val rdd2 = sc.parallelize(Array("a","b","c"),3)

rdd2: org.apache.spark.rdd.RDD[String] = ParallelCollectionRDD[2] at parallelize at <console>:24

（3）第一个RDD组合第二个RDD并打印

scala> rdd1.zip(rdd2).collect

res1: Array[(Int, String)] = Array((1,a), (2,b), (3,c))

（4）第二个RDD组合第一个RDD并打印

scala> rdd2.zip(rdd1).collect

res2: Array[(String, Int)] = Array((a,1), (b,2), (c,3))

（5）创建第三个RDD（与1,2分区数不同）

scala> val rdd3 = sc.parallelize(Array("a","b","c"),2)

rdd3: org.apache.spark.rdd.RDD[String] = ParallelCollectionRDD[5] at parallelize at <console>:24

（6）第一个RDD组合第三个RDD并打印

scala> rdd1.zip(rdd3).collect

java.lang.IllegalArgumentException: Can't zip RDDs with unequal numbers of partitions: List(3, 2)

 at org.apache.spark.rdd.ZippedPartitionsBaseRDD.getPartitions(ZippedPartitionsRDD.scala:57)

 at org.apache.spark.rdd.RDD$$anonfun$partitions$2.apply(RDD.scala:252)

 at org.apache.spark.rdd.RDD$$anonfun$partitions$2.apply(RDD.scala:250)

 at scala.Option.getOrElse(Option.scala:121)

 at org.apache.spark.rdd.RDD.partitions(RDD.scala:250)

 at org.apache.spark.SparkContext.runJob(SparkContext.scala:1965)

 at org.apache.spark.rdd.RDD$$anonfun$collect$1.apply(RDD.scala:936)

 at org.apache.spark.rdd.RDDOperationScope$.withScope(RDDOperationScope.scala:151)

 at org.apache.spark.rdd.RDDOperationScope$.withScope(RDDOperationScope.scala:112)

 at org.apache.spark.rdd.RDD.withScope(RDD.scala:362)

 at org.apache.spark.rdd.RDD.collect(RDD.scala:935)

 ... 48 elided

### 2.3.3 Key-Value类型

#### 2.3.3.1 partitionBy案例

\1. 作用：对pairRDD进行分区操作，如果原有的partionRDD和现有的partionRDD是一致的话就不进行分区， 否则会生成ShuffleRDD，即会产生shuffle过程。

\2. 需求：创建一个4个分区的RDD，对其重新分区

（1）创建一个RDD

scala> val rdd = sc.parallelize(Array((1,"aaa"),(2,"bbb"),(3,"ccc"),(4,"ddd")),4)

rdd: org.apache.spark.rdd.RDD[(Int, String)] = ParallelCollectionRDD[44] at parallelize at <console>:24

（2）查看RDD的分区数

scala> rdd.partitions.size

res24: Int = 4

（3）对RDD重新分区

scala> var rdd2 = rdd.partitionBy(new org.apache.spark.HashPartitioner(2))

rdd2: org.apache.spark.rdd.RDD[(Int, String)] = ShuffledRDD[45] at partitionBy at <console>:26

（4）查看新RDD的分区数

scala> rdd2.partitions.size

res25: Int = 2

#### 2.3.3.2 groupByKey案例

\1. 作用：groupByKey也是对每个key进行操作，但只生成一个sequence。

\2. 需求：创建一个pairRDD，将相同key对应值聚合到一个sequence中，并计算相同key对应值的相加结果。

（1）创建一个pairRDD

scala> val words = Array("one", "two", "two", "three", "three", "three")

words: Array[String] = Array(one, two, two, three, three, three)

 

scala> val wordPairsRDD = sc.parallelize(words).map(word => (word, 1))

wordPairsRDD: org.apache.spark.rdd.RDD[(String, Int)] = MapPartitionsRDD[4] at map at <console>:26

（2）将相同key对应值聚合到一个sequence中

scala> val group = wordPairsRDD.groupByKey()

group: org.apache.spark.rdd.RDD[(String, Iterable[Int])] = ShuffledRDD[5] at groupByKey at <console>:28

（3）打印结果

scala> group.collect()

res1: Array[(String, Iterable[Int])] = Array((two,CompactBuffer(1, 1)), (one,CompactBuffer(1)), (three,CompactBuffer(1, 1, 1)))

（4）计算相同key对应值的相加结果

scala> group.map(t => (t._1, t._2.sum))

res2: org.apache.spark.rdd.RDD[(String, Int)] = MapPartitionsRDD[6] at map at <console>:31

（5）打印结果

scala> res2.collect()

res3: Array[(String, Int)] = Array((two,2), (one,1), (three,3))

#### 2.3.3.3 reduceByKey(func, [numTasks]) 案例

\1. 在一个(K,V)的RDD上调用，返回一个(K,V)的RDD，使用指定的reduce函数，将相同key的值聚合到一起，reduce任务的个数可以通过第二个可选的参数来设置。

\2. 需求：创建一个pairRDD，计算相同key对应值的相加结果

（1）创建一个pairRDD

scala> val rdd = sc.parallelize(List(("female",1),("male",5),("female",5),("male",2)))

rdd: org.apache.spark.rdd.RDD[(String, Int)] = ParallelCollectionRDD[46] at parallelize at <console>:24

（2）计算相同key对应值的相加结果

scala> val reduce = rdd.reduceByKey((x,y) => x+y)

reduce: org.apache.spark.rdd.RDD[(String, Int)] = ShuffledRDD[47] at reduceByKey at <console>:26

（3）打印结果

scala> reduce.collect()

res29: Array[(String, Int)] = Array((female,6), (male,7))

#### 2.3.3.4 reduceByKey和groupByKey的区别

\1. reduceByKey：按照key进行聚合，在shuffle之前有combine（预聚合）操作，返回结果是RDD[k,v].

\2. groupByKey：按照key进行分组，直接进行shuffle。

\3. 开发指导：reduceByKey比groupByKey，建议使用。但是需要注意是否会影响业务逻辑。

#### 2.3.3.5 aggregateByKey案例

参数：(zeroValue:U,[partitioner: Partitioner]) (seqOp: (U, V) => U,combOp: (U, U) => U)

\1. 作用：在kv对的RDD中，，按key将value进行分组合并，合并时，将每个value和初始值作为seq函数的参数，进行计算，返回的结果作为一个新的kv对，然后再将结果按照key进行合并，最后将每个分组的value传递给combine函数进行计算（先将前两个value进行计算，将返回结果和下一个value传给combine函数，以此类推），将key与计算结果作为一个新的kv对输出。

\2. 参数描述：

（1）zeroValue：给每一个分区中的每一个key一个初始值；

（2）seqOp：函数用于在每一个分区中用初始值逐步迭代value；

（3）combOp：函数用于合并每个分区中的结果。

\3. 需求：创建一个pairRDD，取出每个分区相同key对应值的最大值，然后相加

\4. 需求分析

<img src="pictures/Spark/image-20200517141719395.png" alt="image-20200517141719395" style="zoom:50%;" />

（1）创建一个pairRDD

scala> val rdd = sc.parallelize(List(("a",3),("a",2),("c",4),("b",3),("c",6),("c",8)),2)

rdd: org.apache.spark.rdd.RDD[(String, Int)] = ParallelCollectionRDD[0] at parallelize at <console>:24

（2）取出每个分区相同key对应值的最大值，然后相加

scala> val agg = rdd.aggregateByKey(0)(math.max(_,_),_+_)

agg: org.apache.spark.rdd.RDD[(String, Int)] = ShuffledRDD[1] at aggregateByKey at <console>:26

（3）打印结果

scala> agg.collect()

res0: Array[(String, Int)] = Array((b,3), (a,3), (c,12))

#### 2.3.3.6 foldByKey案例

参数：(zeroValue: V)(func: (V, V) => V): RDD[(K, V)]

\1.   作用：aggregateByKey的简化操作，seqop和combop相同

\2.   需求：创建一个pairRDD，计算相同key对应值的相加结果

（1）创建一个pairRDD

scala> val rdd = sc.parallelize(List((1,3),(1,2),(1,4),(2,3),(3,6),(3,8)),3)

rdd: org.apache.spark.rdd.RDD[(Int, Int)] = ParallelCollectionRDD[91] at parallelize at <console>:24

（2）计算相同key对应值的相加结果

scala> val agg = rdd.foldByKey(0)(_+_)

agg: org.apache.spark.rdd.RDD[(Int, Int)] = ShuffledRDD[92] at foldByKey at <console>:26

（3）打印结果

scala> agg.collect()

res61: Array[(Int, Int)] = Array((3,14), (1,9), (2,3))

#### 2.3.3.7 combineByKey[C] 案例

参数：(createCombiner: V => C, mergeValue: (C, V) => C, mergeCombiners: (C, C) => C)

\1.   作用：对相同K，把V合并成一个集合。

\2.   参数描述：

（1）createCombiner: combineByKey() 会遍历分区中的所有元素，因此每个元素的键要么还没有遇到过，要么就和之前的某个元素的键相同。如果这是一个新的元素,combineByKey()会使用一个叫作createCombiner()的函数来创建那个键对应的累加器的初始值

（2）mergeValue: 如果这是一个在处理当前分区之前已经遇到的键，它会使用mergeValue()方法将该键的累加器对应的当前值与这个新的值进行合并

（3）mergeCombiners: 由于每个分区都是独立处理的， 因此对于同一个键可以有多个累加器。如果有两个或者更多的分区都有对应同一个键的累加器， 就需要使用用户提供的 mergeCombiners() 方法将各个分区的结果进行合并。

\3.   需求：创建一个pairRDD，根据key计算每种key的均值。（先计算每个key出现的次数以及可以对应值的总和，再相除得到结果）

\4.   需求分析：

<img src="pictures/Spark/image-20200517142052665.png" alt="image-20200517142052665" style="zoom:50%;" />



（1）创建一个pairRDD

scala> val input = sc.parallelize(Array(("a", 88), ("b", 95), ("a", 91), ("b", 93), ("a", 95), ("b", 98)),2)

input: org.apache.spark.rdd.RDD[(String, Int)] = ParallelCollectionRDD[52] at parallelize at <console>:26

（2）将相同key对应的值相加，同时记录该key出现的次数，放入一个二元组

scala> val combine = input.combineByKey((_,1),(acc:(Int,Int),v)=>(acc._1+v,acc._2+1),(acc1:(Int,Int),acc2:(Int,Int))=>(acc1._1+acc2._1,acc1._2+acc2._2))

combine: org.apache.spark.rdd.RDD[(String, (Int, Int))] = ShuffledRDD[5] at combineByKey at <console>:28

（3）打印合并后的结果

scala> combine.collect

res5: Array[(String, (Int, Int))] = Array((b,(286,3)), (a,(274,3)))

（4）计算平均值

scala> val result = combine.map{case (key,value) => (key,value._1/value._2.toDouble)}

result: org.apache.spark.rdd.RDD[(String, Double)] = MapPartitionsRDD[54] at map at <console>:30

（5）打印结果

scala> result.collect()

res33: Array[(String, Double)] = Array((b,95.33333333333333), (a,91.33333333333333))

#### 2.3.3.8 sortByKey([ascending], [numTasks]) 案例

\1. 作用：在一个(K,V)的RDD上调用，K必须实现Ordered接口，返回一个按照key进行排序的(K,V)的RDD

\2. 需求：创建一个pairRDD，按照key的正序和倒序进行排序

（1）创建一个pairRDD

scala> val rdd = sc.parallelize(Array((3,"aa"),(6,"cc"),(2,"bb"),(1,"dd")))

rdd: org.apache.spark.rdd.RDD[(Int, String)] = ParallelCollectionRDD[14] at parallelize at <console>:24

（2）按照key的正序

scala> rdd.sortByKey(true).collect()

res9: Array[(Int, String)] = Array((1,dd), (2,bb), (3,aa), (6,cc))

（3）按照key的倒序

scala> rdd.sortByKey(false).collect()

res10: Array[(Int, String)] = Array((6,cc), (3,aa), (2,bb), (1,dd))

#### 2.3.3.9 mapValues案例

\1. 针对于(K,V)形式的类型只对V进行操作

\2. 需求：创建一个pairRDD，并将value添加字符串"|||"

（1）创建一个pairRDD

scala> val rdd3 = sc.parallelize(Array((1,"a"),(1,"d"),(2,"b"),(3,"c")))

rdd3: org.apache.spark.rdd.RDD[(Int, String)] = ParallelCollectionRDD[67] at parallelize at <console>:24

（2）对value添加字符串"|||"

scala> rdd3.mapValues(_+"|||").collect()

res26: Array[(Int, String)] = Array((1,a|||), (1,d|||), (2,b|||), (3,c|||))

#### 2.3.3.10 join(otherDataset, [numTasks]) 案例

\1. 作用：在类型为(K,V)和(K,W)的RDD上调用，返回一个相同key对应的所有元素对在一起的(K,(V,W))的RDD

\2. 需求：创建两个pairRDD，并将key相同的数据聚合到一个元组。

（1）创建第一个pairRDD

scala> val rdd = sc.parallelize(Array((1,"a"),(2,"b"),(3,"c")))

rdd: org.apache.spark.rdd.RDD[(Int, String)] = ParallelCollectionRDD[32] at parallelize at <console>:24

（2）创建第二个pairRDD

scala> val rdd1 = sc.parallelize(Array((1,4),(2,5),(3,6)))

rdd1: org.apache.spark.rdd.RDD[(Int, Int)] = ParallelCollectionRDD[33] at parallelize at <console>:24

（3）join操作并打印结果

scala> rdd.join(rdd1).collect()

res13: Array[(Int, (String, Int))] = Array((1,(a,4)), (2,(b,5)), (3,(c,6)))

#### 2.3.3.11 cogroup(otherDataset, [numTasks]) 案例

\1. 作用：在类型为(K,V)和(K,W)的RDD上调用，返回一个(K,(Iterable<V>,Iterable<W>))类型的RDD

\2. 需求：创建两个pairRDD，并将key相同的数据聚合到一个迭代器。

（1）创建第一个pairRDD

scala> val rdd = sc.parallelize(Array((1,"a"),(2,"b"),(3,"c")))

rdd: org.apache.spark.rdd.RDD[(Int, String)] = ParallelCollectionRDD[37] at parallelize at <console>:24

（2）创建第二个pairRDD

scala> val rdd1 = sc.parallelize(Array((1,4),(2,5),(3,6)))

rdd1: org.apache.spark.rdd.RDD[(Int, Int)] = ParallelCollectionRDD[38] at parallelize at <console>:24

（3）cogroup两个RDD并打印结果

scala> rdd.cogroup(rdd1).collect()

res14: Array[(Int, (Iterable[String], Iterable[Int]))] = Array((1,(CompactBuffer(a),CompactBuffer(4))), (2,(CompactBuffer(b),CompactBuffer(5))), (3,(CompactBuffer(c),CompactBuffer(6))))

### 2.3.4 案例实操

\1. 数据结构：时间戳，省份，城市，用户，广告，中间字段使用空格分割。

​                               

样本如下： 

1516609143867 6 7 64 16

1516609143869 9 4 75 18

1516609143869 1 7 87 12

\2. 需求：统计出每一个省份广告被点击次数的TOP3

\3. 实现过程：

package com.package.practice

 

import org.apache.spark.rdd.RDD

import org.apache.spark.{SparkConf, SparkContext}

 

//需求：统计出每一个省份广告被点击次数的TOP3

object Practice {

 

 def main(args: Array[String]): Unit = {

 

  //1.初始化spark配置信息并建立与spark的连接

  val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Practice")

  val sc = new SparkContext(sparkConf)

 

  //2.读取数据生成RDD：TS，Province，City，User，AD

  val line = sc.textFile("E:\\IDEAWorkSpace\\SparkTest\\src\\main\\resources\\agent.log")

 

  //3.按照最小粒度聚合：((Province,AD),1)

  val provinceAdToOne = line.map { x =>

   val fields: Array[String] = x.split(" ")

   ((fields(1), fields(4)), 1)

  }

 

  //4.计算每个省中每个广告被点击的总数：((Province,AD),sum)

  val provinceAdToSum = provinceAdToOne.reduceByKey(_ + _)

 

  //5.将省份作为key，广告加点击数为value：(Province,(AD,sum))

  val provinceToAdSum = provinceAdToSum.map(x => (x._1._1, (x._1._2, x._2)))

 

  //6.将同一个省份的所有广告进行聚合(Province,List((AD1,sum1),(AD2,sum2)...))

  val provinceGroup = provinceToAdSum.groupByKey()

 

  //7.对同一个省份所有广告的集合进行排序并取前3条，排序规则为广告点击总数

  val provinceAdTop3 = provinceGroup.mapValues { x =>

   x.toList.sortWith((x, y) => x._2 > y._2).take(3)

  }

 

  //8.将数据拉取到Driver端并打印

  provinceAdTop3.collect().foreach(println)

 

  //9.关闭与spark的连接

  sc.stop()

 }

}

## 2.4 Action

### 2.4.1 reduce(func)案例

\1. 作用：通过func函数聚集RDD中的所有元素，先聚合分区内数据，再聚合分区间数据。

\2. 需求：创建一个RDD，将所有元素聚合得到结果。

（1）创建一个RDD[Int]

scala> val rdd1 = sc.makeRDD(1 to 10,2)

rdd1: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[85] at makeRDD at <console>:24

（2）聚合RDD[Int]所有元素

scala> rdd1.reduce(_+_)

res50: Int = 55

（3）创建一个RDD[String]

scala> val rdd2 = sc.makeRDD(Array(("a",1),("a",3),("c",3),("d",5)))

rdd2: org.apache.spark.rdd.RDD[(String, Int)] = ParallelCollectionRDD[86] at makeRDD at <console>:24

（4）聚合RDD[String]所有数据

**key与key拼凑，value与value拼凑**

scala> rdd2.reduce((x,y)=>(x._1 + y._1,x._2 + y._2))

res51: (String, Int) = (adca,12)

### 2.4.2 collect()案例

\1. 作用：在驱动程序中，以数组的形式返回数据集的所有元素。

\2. 需求：创建一个RDD，并将RDD内容收集到Driver端打印

（1）创建一个RDD

scala> val rdd = sc.parallelize(1 to 10)

rdd: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[0] at parallelize at <console>:24

（2）将结果收集到Driver端

scala> rdd.collect

res0: Array[Int] = Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)  

### 2.4.3 count()案例

\1. 作用：返回RDD中元素的个数

\2. 需求：创建一个RDD，统计该RDD的条数

（1）创建一个RDD

scala> val rdd = sc.parallelize(1 to 10)

rdd: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[0] at parallelize at <console>:24

（2）统计该RDD的条数

scala> rdd.count

res1: Long = 10

### 2.4.4 first()案例

\1. 作用：返回RDD中的第一个元素

\2. 需求：创建一个RDD，返回该RDD中的第一个元素

（1）创建一个RDD

scala> val rdd = sc.parallelize(1 to 10)

rdd: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[0] at parallelize at <console>:24

（2）统计该RDD的条数

scala> rdd.first

res2: Int = 1

### 2.4.5 take(n)案例

\1. 作用：返回一个由RDD的前n个元素组成的数组

\2. 需求：创建一个RDD，统计该RDD的条数

（1）创建一个RDD

scala> val rdd = sc.parallelize(Array(2,5,4,6,8,3))

rdd: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[2] at parallelize at <console>:24

（2）统计该RDD的条数

scala> rdd.take(3)

res10: Array[Int] = Array(2, 5, 4)

### 2.4.6 takeOrdered(n)案例

\1. 作用：返回该RDD排序后的前n个元素组成的数组

\2. 需求：创建一个RDD，统计该RDD的条数

（1）创建一个RDD

scala> val rdd = sc.parallelize(Array(2,5,4,6,8,3))

rdd: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[2] at parallelize at <console>:24

（2）统计该RDD的条数

scala> rdd.takeOrdered(3)

res18: Array[Int] = Array(2, 3, 4)

### 2.4.7 aggregate案例

\1. 参数：(zeroValue: U)(seqOp: (U, T) ⇒ U, combOp: (U, U) ⇒ U)

\2. 作用：aggregate函数将每个分区里面的元素通过seqOp和初始值进行聚合，然后用combine函数将每个分区的结果和初始值(zeroValue)进行combine操作。这个函数最终返回的类型不需要和RDD中元素类型一致。

\3. 需求：创建一个RDD，将所有元素相加得到结果

（1）创建一个RDD

scala> var rdd1 = sc.makeRDD(1 to 10,2)

rdd1: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[88] at makeRDD at <console>:24

（2）将该RDD所有元素相加得到结果

scala> rdd.aggregate(0)(_+_,_+_)

res22: Int = 55

### 2.4.8 fold(num)(func)案例

\1. 作用：折叠操作，aggregate的简化操作，seqop和combop一样。

\2. 需求：创建一个RDD，将所有元素相加得到结果

（1）创建一个RDD

scala> var rdd1 = sc.makeRDD(1 to 10,2)

rdd1: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[88] at makeRDD at <console>:24

（2）将该RDD所有元素相加得到结果

scala> rdd.fold(0)(_+_)

res24: Int = 55

### 2.4.9 saveAsTextFile(path)

作用：将数据集的元素以textfile的形式保存到HDFS文件系统或者其他支持的文件系统，对于每个元素，Spark将会调用toString方法，将它装换为文件中的文本

### 2.4.10 saveAsSequenceFile(path) 

作用：将数据集中的元素以Hadoop sequencefile的格式保存到指定的目录下，可以使HDFS或者其他Hadoop支持的文件系统。

### 2.4.11 saveAsObjectFile(path) 

作用：用于将RDD中的元素序列化成对象，存储到文件中。

### 2.4.12 countByKey()案例

\1. 作用：针对(K,V)类型的RDD，返回一个(K,Int)的map，表示每一个key对应的元素个数。

\2. 需求：创建一个PairRDD，统计每种key的个数

（1）创建一个PairRDD

scala> val rdd = sc.parallelize(List((1,3),(1,2),(1,4),(2,3),(3,6),(3,8)),3)

rdd: org.apache.spark.rdd.RDD[(Int, Int)] = ParallelCollectionRDD[95] at parallelize at <console>:24

（2）统计每种key的个数

scala> rdd.countByKey

res63: scala.collection.Map[Int,Long] = Map(3 -> 2, 1 -> 3, 2 -> 1)

### 2.4.13 foreach(func)案例

\1. 作用：在数据集的每一个元素上，运行函数func进行更新。

\2. 需求：创建一个RDD，对每个元素进行打印

（1）创建一个RDD

scala> var rdd = sc.makeRDD(1 to 5,2)

rdd: org.apache.spark.rdd.RDD[Int] = ParallelCollectionRDD[107] at makeRDD at <console>:24

（2）对该RDD每个元素进行打印

scala> rdd.foreach(println(_))

3

4

5

1

2











## RDD 的持久化

### RDD 的 cache(持久化)

Spark中最重要的功能之一是跨操作在内存中持久化（或缓存）数据集。当您持久保存RDD时，每个节点都会存储它在内存中计算的任何分区，并在该数据集（或从中派生的数据集）的其他操作中重用它们。这使得未来的行动更快（通常超过10倍）。缓存是迭代算法和快速交互使用的关键工具。

您可以使用persist()或cache()方法标记要保留的RDD 。第一次在动作中计算它，它将保留在节点的内存中。Spark的缓存是容错的 - 如果丢失了RDD的任何分区，它将使用最初创建它的转换自动重新计算。

### 持久化需求

1. 要求的计算速度快
2. 集群的资源要足够大
3. 重要: cache 的数据会多次触发Action
4. 建议先进行数据过滤,然后将缩小范围后的数据再cache 到内存中.


