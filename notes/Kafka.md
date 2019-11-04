

# 基本概念

同一个topic的消息,有多个分区,会被平均分配(不是复制)到这几个分区,  
同一个组的多个消费者,会分别负责一个分区,来取他们中的消息消费;如果是多个消费者负责一个分区,会有offset来控制消费顺序.

<div align="center"> <img src="./pictures/kafka/Snipaste_2019-11-04_19-50-53.png" width="600"/> </div>


## Broker
物理概念，指服务于Kafka的一个node。


## Topic
是Kafka下消息的类别，类似于RabbitMQ中的Exchange的概念。这是逻辑上的概念，用来区分、隔离不同的消息数据，屏蔽了底层复杂的存储方式。对于大多数人来说，在开发的时候只需要关注数据写入到了哪个topic、从哪个topic取出数据。 

## Partition
是Kafka下数据存储的基本单元，这个是物理上的概念。**同一个topic的数据，会被分散的存储到多个partition中**，其优势在于：有利于水平扩展，避免单台机器在磁盘空间和性能上的限制，为了做到均匀分布，通常partition的数量通常是Broker Server数量的整数倍。

## Consumer Group 即 User Group
是Kafka实现单播和广播两种消息模型的手段。同一个topic的数据，会广播给不同的group；同一个group中的worker，只有一个worker能拿到这个数据。换句话说，对于同一个topic，每个group都可以拿到同样的所有数据，但是数据进入group后只能被其中的一个worker消费。

group内的worker可以使用多线程或多进程来实现，也可以将进程分散在多台机器上，**worker的数量通常不超过partition的数量，且二者最好保持整数倍关系**，因为Kafka在设计时假定了一个partition只能被一个worker消费（同一group内）。User group是为了便于实现MQ中的多播，重复消费等引入的概念。如果ConsumerA以及ConsumerB同在一个UserGroup，那么ConsumerA消费的数据ConsumerB就无法消费了。usergroup中的consumer使用一套offset。组内必然可以有多个消费者或消费者实例(consumer instance)，它们共享一个公共的ID，即group ID。

组内的所有消费者协调在一起来消费订阅主题(subscribed topics)的所有分区(partition)。当然，每个分区只能由同一个消费组内的一个consumer来消费。
1）consumer group下可以有一个或多个consumer instance，consumer instance可以是一个进程，也可以是一个线程
2）group.id是一个字符串，唯一标识一个consumer group
3）consumer group下订阅的topic下的每个分区只能分配给某个group下的一个consumer(当然该分区还可以被分配给其他group)

**一个topic有多个分区,每个group可以订阅多个topic,group中的 每个consumer instance都可以消费同一个topic下每个分区的数据,但是group存有每个分区的offset,所以保证一个topic 多个分区下的每个数据都只被 一个group 消费一次**

## Offset
Offset专指Partition以及User Group而言，记录某个user group在某个partiton中当前已经消费到达的位置。

# Kafka和RocketMq的不同

https://www.jianshu.com/p/c474ca9f9430