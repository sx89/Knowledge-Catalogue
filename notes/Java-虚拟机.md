<!-- TOC -->

- [JDK,JRE,JVM,HotSpot关系](#jdkjrejvmhotspot关系)
    - [JIT的两个模式](#jit的两个模式)
    - [JIT动态编译的好处](#jit动态编译的好处)
- [内存泄漏与内存溢出](#内存泄漏与内存溢出)
    - [内存泄漏](#内存泄漏)
        - [定义](#定义)
        - [解决办法](#解决办法)
    - [内存溢出](#内存溢出)
- [运行时数据区域](#运行时数据区域)
    - [程序计数器](#程序计数器)
    - [Java 虚拟机栈](#java-虚拟机栈)
    - [本地方法栈](#本地方法栈)
    - [堆](#堆)
        - [java对象实例结构](#java对象实例结构)
        - [配置解释](#配置解释)
    - [方法区](#方法区)
    - [运行时常量池](#运行时常量池)
    - [直接内存](#直接内存)
    - [jvm分区的作用再理解](#jvm分区的作用再理解)
- [垃圾收集](#垃圾收集)
    - [判断一个对象是否可被回收](#判断一个对象是否可被回收)
        - [1. 引用计数算法](#1-引用计数算法)
        - [2. 可达性分析算法](#2-可达性分析算法)
        - [3. 方法区的回收](#3-方法区的回收)
        - [4. finalize()](#4-finalize)
    - [引用类型](#引用类型)
        - [1. 强引用](#1-强引用)
        - [2. 软引用](#2-软引用)
        - [3. 弱引用](#3-弱引用)
        - [4. 虚引用](#4-虚引用)
    - [垃圾收集算法](#垃圾收集算法)
        - [1. 标记 - 清除](#1-标记---清除)
        - [2. 标记 - 整理](#2-标记---整理)
        - [3. 复制](#3-复制)
        - [4. 分代收集](#4-分代收集)
    - [垃圾收集器](#垃圾收集器)
        - [1. Serial 收集器](#1-serial-收集器)
        - [2. ParNew 收集器](#2-parnew-收集器)
        - [3. Parallel Scavenge 收集器(平行清除)](#3-parallel-scavenge-收集器平行清除)
        - [4. Serial Old 收集器(以下是老年代收集器)](#4-serial-old-收集器以下是老年代收集器)
        - [5. Parallel Old 收集器](#5-parallel-old-收集器)
        - [6. CMS 收集器](#6-cms-收集器)
        - [7. G1 收集器](#7-g1-收集器)
- [三、内存分配与回收策略](#三内存分配与回收策略)
    - [Minor GC 和 Full GC](#minor-gc-和-full-gc)
    - [内存分配策略](#内存分配策略)
        - [1. 对象优先在 Eden 分配](#1-对象优先在-eden-分配)
        - [2. 大对象直接进入老年代](#2-大对象直接进入老年代)
        - [3. 长期存活的对象进入老年代](#3-长期存活的对象进入老年代)
        - [4. 动态对象年龄判定](#4-动态对象年龄判定)
        - [5. 空间分配担保(先看最大空间,再看平均空间)](#5-空间分配担保先看最大空间再看平均空间)
    - [Full GC 的触发条件](#full-gc-的触发条件)
        - [1. 调用 System.gc()](#1-调用-systemgc)
        - [2. 老年代空间不足](#2-老年代空间不足)
        - [3. 空间分配担保失败](#3-空间分配担保失败)
        - [4. JDK 1.7 及以前的永久代空间不足](#4-jdk-17-及以前的永久代空间不足)
        - [5. Concurrent Mode Failure](#5-concurrent-mode-failure)
- [JVM类生命周期概述：加载时机与加载过程](#jvm类生命周期概述加载时机与加载过程)
    - [类加载机制定义](#类加载机制定义)
    - [类的生命周期](#类的生命周期)
    - [加载时机与初始化时机](#加载时机与初始化时机)
        - [类加载时机](#类加载时机)
        - [类初始化时机(五种主动引用)](#类初始化时机五种主动引用)
        - [类的被动引用(不触发初始化)](#类的被动引用不触发初始化)
        - [实例化小结: 实例化就是对象实例变量,代码,构造器的初始化,字节码中是init函数(要与静态变量,代码的初始化区分开,字节码中是clinit函数)](#实例化小结-实例化就是对象实例变量代码构造器的初始化字节码中是init函数要与静态变量代码的初始化区分开字节码中是clinit函数)
    - [区分 准备阶段,初始化阶段,实例化阶段](#区分-准备阶段初始化阶段实例化阶段)
        - [常见考题](#常见考题)

<!-- /TOC -->

# JDK,JRE,JVM,HotSpot关系
JDK是java开发环境,JRE是java运行环境,JDK包含JRE

JRE包含JVM,HotSpot是JVM的一种技术实现,用C和汇编语言编写,包含两个编译器(JIT 的server版本,client版本)和一个解释器

JVM包含解释器与编译器,用来运行字节码(.class文件,是特殊的二进制文件，二进制字节码文件),javac.exe可以简单看成是Java编译器。


## JIT的两个模式(*Just-in-time* compilation即时编译)
client模式是一种轻量级编译器，也叫C1编译器，占用内存小，启动快，但是执行效率没有server模式高，默认状态下不进行动态编译，适用于桌面应用程序。

server模式是一种重量级编译器，也叫C2编译器，启动慢，占用内存大，执行效率高，默认是开启动态编译的，适合服务器应用。

XX:RewriteFrequentPairs   用于开启动态编译。

-Xint:禁用JIT编译，即禁用两个编译器，纯解释执行。

-Xcomp:纯编译执行，如果方法无法编译，则回退到解释执行模式解释无法编译的代码。
## JIT动态编译的好处
编译分为静态编译与动态编译,静态编译是字节码在装入虚拟机之前就转换成本地代码.动态编译是装入虚拟机之后,根据运行热度,把热度高的字节码转换成本地代码

好处: 

1. 静态编译器通常很难准确预知程序运行过程中究竟什么部分最需要优化。函数调用都是很浪费系统时间的，因为有许多进栈出栈操作。因此有一种优化办法，就是把原来的函数调用，通过编译器的编译，改成非函数调用，把函数代码直接嵌到调用处，变成顺序执行。
2. 面向对象的语言支持多态，静态编译无效确定程序调用哪个方法，因为多态是在程序运行中确定调用哪个方法。


# 内存泄漏与内存溢出
## 内存泄漏
### 定义
内存泄漏就是对象被分配的内存没有被回收; 长生命周期的对象有一个短生命周期的内存,会导致内存泄漏  

例子有:容器的内存泄漏,各种连接的close忘记,单例模式引用了其他短声明周期对象
```java
1.	public class Simple {  
2.	   
3.	    Object object;  
4.	   
5.	    public void method1(){  
6.	        object = new Object();  
7.	    //...其他代码  
8.	    }  
9.	}  

```
这里的object实例，其实我们期望它只作用于method1()方法中，且其他地方不会再用到它，但是，当method1()方法执行完成后，object对象所分配的内存不会马上被认为是可以被释放的对象，只有在Simple类创建的对象被释放后才会被释放，严格的说，这就是一种内存泄露。

### 解决办法
减小对象的作用域

及时对对象赋值为null

各种连接需要close也是这个道理,长周期对象短周期引用

## 内存溢出



# 运行时数据区域

<div align="center"> <img src="pics/5778d113-8e13-4c53-b5bf-801e58080b97.png" width="400px"> </div><br>
## 程序计数器

当前线程所执行的字节码的行号指示器，为了线程切换后能够恢复到正确的执行位置，每条线程都需要有一个独立的程序计数器。

## Java 虚拟机栈(装着栈帧)

每个 Java 方法在执行的同时会创建一个栈帧用于存储局部变量表、操作数栈(存储着需要执行的指令)、常量池引用等信息。从方法调用直至执行完成的过程，对应着一个栈帧在 Java 虚拟机栈中入栈和出栈的过程。

<div align="center"> <img src="pics/8442519f-0b4d-48f4-8229-56f984363c69.png" width="400px"> </div><br>
可以通过 -Xss 这个虚拟机参数来指定每个线程的 Java 虚拟机栈内存大小，在 JDK 1.4 中默认为 256K，而在 JDK 1.5+ 默认为 1M：

```java
java -Xss 2M HackTheJava
```

该区域可能抛出以下异常：

- 当线程请求的栈深度超过最大值，会抛出 StackOverflowError 异常；
- 栈进行动态扩展时如果无法申请到足够内存，会抛出 OutOfMemoryError 异常。

## 本地方法栈

本地方法栈与 Java 虚拟机栈类似，它们之间的区别只不过是本地方法栈为本地方法服务。

本地方法一般是用其它语言（C、C++ 或汇编语言等）编写的，并且被编译为基于本机硬件和操作系统的程序，对待这些方法需要特别处理。

<div align="center"> <img src="pics/66a6899d-c6b0-4a47-8569-9d08f0baf86c.png" width="300px"> </div><br>
## 堆

**所有对象实例都在这里分配内存，是垃圾收集的主要区域（"GC 堆"）。**

现代的垃圾收集器基本都是采用分代收集算法，其主要的思想是针对不同类型的对象采取不同的垃圾回收算法。可以将堆分成两块：

- 新生代（Young Generation）
- 老年代（Old Generation）

堆不需要连续内存，并且可以动态增加其内存，增加失败会抛出 OutOfMemoryError 异常。

可以通过 -Xms 和 -Xmx 这两个虚拟机参数来指定一个程序的堆内存大小，第一个参数设置初始值，第二个参数设置最大值。

```java
java -Xms 1M -Xmx 2M HackTheJava
```
### java对象实例结构
在HotSpot虚拟机中，对象在内存中存储的布局可以分为3块区域：对象头（Header）、实例数据（Instance Data）和对齐填充（Padding）。下图是普通对象实例与数组对象实例的数据结构：

<div align="center"> <img src=".\pictures\java-vm\Snipaste_2019-09-06_17-47-53.jpg" width=""/> </div><br>

对象头

HotSpot虚拟机的对象头包括两部分信息：

markword 

第一部分markword,用于存储对象自身的运行时数据，如哈希码（HashCode）、GC分代年龄、锁状态标志、线程持有的锁、偏向线程ID、偏向时间戳等，这部分数据的长度在32位和64位的虚拟机（未开启压缩指针）中分别为32bit和64bit，官方称它为“MarkWord”。
klass 
对象头的另外一部分是klass类型指针，即对象指向它的类元数据的指针，虚拟机通过这个指针来确定这个对象是哪个类的实例.
数组长度（只有数组对象有） 
如果对象是一个数组, 那在对象头中还必须有一块数据用于记录数组长度.
实例数据
实例数据部分是对象真正存储的有效信息，也是在程序代码中所定义的各种类型的字段内容。无论是从父类继承下来的，还是在子类中定义的，都需要记录起来。

对齐填充

第三部分对齐填充并不是必然存在的，也没有特别的含义，它仅仅起着占位符的作用。由于HotSpot VM的自动内存管理系统要求对象起始地址必须是8字节的整数倍，换句话说，就是对象的大小必须是8字节的整数倍。而对象头部分正好是8字节的倍数（1倍或者2倍），因此，当对象实例数据部分没有对齐时，就需要通过对齐填充来补全。

### 配置解释
```
先说VM选项， 三种：
- : 标准VM选项，VM规范的选项
- -X: 非标准VM选项，不保证所有VM支持
- -XX: 高级选项，高级特性，但属于不稳定的选项参见 
  
  
其语义分别是：
-Xmx: 堆的最大内存数，等同于-XX:MaxHeapSize-Xms: 堆的初始化初始化大小 
-Xmn: 堆中新生代初始及最大大小，如果需要进一步细化，初始化大小用  
-XX:NewSize，最大大小用-XX:MaxNewSize -Xss: 线程栈大小，等同于
-XX:ThreadStackSize命名应该非简称，

助记的话： memory maximum, memory startup, memory nursery/new, stack size.

作者：Home3k
链接：https://www.zhihu.com/question/59957834/answer/170775050
来源：知乎
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
```
## 方法区

用于存放已被加载的类信息、常量、静态变量、即时编译器编译后的代码等数据。

**方法区是一个 JVM 规范，永久代与元空间都是其一种实现方式。在 JDK 1.8 之后，原来永久代的数据被分到了堆和元空间中。元空间存储类的元信息，静态变量和常量池等放入堆中。**



用于存放已被加载的类信息、常量、静态变量、即时编译器编译后的代码等数据。

和堆一样不需要连续的内存，并且可以动态扩展，动态扩展失败一样会抛出 OutOfMemoryError 异常。

对这块区域进行垃圾回收的主要目标是对常量池的回收和对类的卸载，但是一般比较难实现。

HotSpot 虚拟机把它当成永久代来进行垃圾回收。但很难确定永久代的大小，因为它受到很多因素影响，并且每次 Full GC 之后永久代的大小都会改变，所以经常会抛出 OutOfMemoryError 异常。**为了更容易管理方法区，从 JDK 1.8 开始，移除永久代，并把方法区移至元空间，它位于本地内存中，而不是虚拟机内存中。**

方法区是一个 JVM 规范，永久代与元空间都是其一种实现方式。在 JDK 1.8 之后，原来永久代的数据被分到了堆和元空间中。元空间存储类的元信息，静态变量和常量池等放入堆中。

## 运行时常量池

运行时常量池是方法区的一部分。

Class 文件中的常量池（编译器生成的字面量和符号引用）会在类加载后被放入这个区域。

除了在编译期生成的常量，还允许动态生成，例如 String 类的 intern()。

## 直接内存

在 JDK 1.4 中新引入了 NIO 类，它可以使用 Native 函数库直接分配堆外内存，然后通过 Java 堆里的 DirectByteBuffer 对象作为这块内存的引用进行操作。这样能在一些场景中显著提高性能，因为避免了在堆内存和堆外内存来回拷贝数据。

## jvm分区的作用再理解
方法区：类信息、类变量（静态变量和常量）、方法 (JDK1.8之后,静态变量和常量进入堆,类信息进入元空间)

堆：对象、成员变量 

栈：局部变量 程序计数器 虚拟机栈(操作数栈,局部变量表,常量引用) native方法栈

（1）当程序运行时，首先通过类装载器加载字节码文件，经过解析后装入方法区！在方法区中存了类的各种信息，包括类变量、常量及方法。对于同一个方法的调用，同一个类的不同实例调用的都是存在方法区的同一个方法。类变量的生命周期从程序开始运行时创建，到程序终止运行时结束！ 

（2）当程序中new一个对象时，这个对象存在堆中，对象的成员变量都存在堆中，当对象被回收时，对象的成员变量随之消失！ 

（3）当方法调用时，JVM会在栈中分配一个栈桢，存储方法的局部变量。当方法调用结束时，局部变量消失！

类变量：属于类的属性信息，与类的实例无关，多个实例共用同一个类变量，存在与方法区中。类变量用static修饰，包括静态变量和常量。静态变量有默认初始值，常量必须声明同时初始化。

成员变量：属于实例的变量，只与实例有关，写在类下面，方法外，非static修饰。成员变量会随着成员的创建而生存，随着成员的回收而销毁。

局部变量：声明在方法中，没有默认初始值，随着方法的调用而创建，存储于栈中，随着方法调用的结束而销毁。

类的成员变量在不同对象中各不相同，基本数据类型和引用数据类型都存储在这个对象中，作为一个整体存储在堆中。而类的方法是所有的对象共享的，方法是存在方法区的，只用当调用的时候才会被压栈，不用的时候是占内存的。


# 垃圾收集

垃圾收集主要是针对堆和方法区进行。程序计数器、虚拟机栈和本地方法栈这三个区域属于线程私有的，只存在于线程的生命周期内，线程结束之后就会消失，因此不需要对这三个区域进行垃圾回收。

## 判断一个对象是否可被回收

### 1. 引用计数算法

为对象添加一个引用计数器，当对象增加一个引用时计数器加 1，引用失效时计数器减 1。引用计数为 0 的对象可被回收。

在两个对象出现循环引用的情况下，此时引用计数器永远不为 0，导致无法对它们进行回收。正是因为循环引用的存在，因此 Java 虚拟机不使用引用计数算法。

```java
public class Test {

    public Object instance = null;

    public static void main(String[] args) {
        Test a = new Test();
        Test b = new Test();
        a.instance = b;
        b.instance = a;
        a = null;
        b = null;
        doSomething();
    }
}
```

在上述代码中，a 与 b 引用的对象实例互相持有了对象的引用，因此当我们把对 a 对象与 b 对象的引用去除之后，由于两个对象还存在互相之间的引用，导致两个 Test 对象无法被回收。

### 2. 可达性分析算法

以 GC Roots 为起始点进行搜索，可达的对象都是存活的，不可达的对象可被回收。

Java 虚拟机使用该算法来判断对象是否可被回收，GC Roots 一般包含以下内容：

- 虚拟机栈中局部变量表中引用的对象
- 本地方法栈中 JNI(Native方法) 中引用的对象
- 方法区中类静态属性引用的对象
- 方法区中的常量引用的对象

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/83d909d2-3858-4fe1-8ff4-16471db0b180.png" width="350px"> </div><br>

以下几种对象可以作为GC Root：

虚拟机栈中的引用对象

方法区中类静态属性引用的对象

方法区中常量引用对象

本地方法栈中JNI引用对象


### 3. 方法区的回收

因为方法区主要存放永久代对象，而永久代对象的回收率比新生代低很多，所以在方法区上进行回收性价比不高。

方法区的垃圾回收主要有两种，分别是对废弃常量的回收和对无用类的回收。

当一个常量对象不再任何地方被引用的时候，则被标记为废弃常量，这个常量可以被回收。

方法区中的类需要同时满足以下三个条件才能被标记为无用的类：

1.Java堆中不存在该类的任何实例对象；

2.加载该类的类加载器已经被回收；

3.该类对应的java.lang.Class对象不在任何地方被引用，且无法在任何地方通过反射访问该类的方法。


主要是对常量池的回收和对类的卸载。

为了避免内存溢出，在大量使用反射和动态代理的场景都需要虚拟机具备类卸载功能。

类的卸载条件很多，需要满足以下三个条件，并且满足了条件也不一定会被卸载：

- 该类所有的实例都已经被回收，此时堆中不存在该类的任何实例。
- 加载该类的 ClassLoader 已经被回收。
- 该类对应的 Class 对象没有在任何地方被引用，也就无法在任何地方通过反射访问该类方法。

### 4. finalize()

类似 C++ 的析构函数，用于关闭外部资源。但是 try-finally 等方式可以做得更好，并且该方法运行代价很高，不确定性大，无法保证各个对象的调用顺序，因此最好不要使用。

当一个对象可被回收时，如果需要执行该对象的 finalize() 方法，那么就有可能在该方法中让对象重新被引用，从而实现自救。自救只能进行一次，如果回收的对象之前调用了 finalize() 方法自救，后面回收时不会再调用该方法。

## 引用类型

无论是通过引用计数算法判断对象的引用数量，还是通过可达性分析算法判断对象是否可达，判定对象是否可被回收都与引用有关。

Java 提供了四种强度不同的引用类型。

### 1. 强引用

被强引用关联的对象不会被回收。

使用 new 一个新对象的方式来创建强引用。

```java
Object obj = new Object();
```

### 2. 软引用

被软引用关联的对象只有在内存不够的情况下才会被回收。

使用 SoftReference 类来创建软引用。

```java
Object obj = new Object();
SoftReference<Object> sf = new SoftReference<Object>(obj);
obj = null;  // 使对象只被软引用关联
```

### 3. 弱引用

被弱引用关联的对象一定会被回收，也就是说它只能存活到下一次垃圾回收发生之前。

使用 WeakReference 类来创建弱引用。

```java
Object obj = new Object();
WeakReference<Object> wf = new WeakReference<Object>(obj);
obj = null;
```

### 4. 虚引用

又称为幽灵引用或者幻影引用，一个对象是否有虚引用的存在，不会对其生存时间造成影响，也无法通过虚引用得到一个对象。

为一个对象设置虚引用的唯一目的是能在这个对象被回收时收到一个系统通知。

使用 PhantomReference 来创建虚引用。

```java
Object obj = new Object();
PhantomReference<Object> pf = new PhantomReference<Object>(obj, null);
obj = null;
```

## 垃圾收集算法

### 1. 标记 - 清除

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/005b481b-502b-4e3f-985d-d043c2b330aa.png" width="400px"> </div><br>
在标记阶段，程序会检查每个对象是否为活动对象，如果是活动对象，则程序会在对象头部打上标记。

在清除阶段，会进行对象回收并取消标志位，另外，还会判断回收后的分块与前一个空闲分块是否连续，若连续，会合并这两个分块。回收对象就是把对象作为分块，连接到被称为 “空闲链表” 的单向链表，之后进行分配时只需要遍历这个空闲链表，就可以找到分块。

在分配时，程序会搜索空闲链表寻找空间大于等于新对象大小 size 的块 block。如果它找到的块等于 size，会直接返回这个分块；如果找到的块大于 size，会将块分割成大小为 size 与 (block - size) 的两部分，返回大小为 size 的分块，并把大小为 (block - size) 的块返回给空闲链表。

不足：

- 标记和清除过程效率都不高；
- 会产生大量不连续的内存碎片，导致无法给大对象分配内存。

### 2. 标记 - 整理

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/ccd773a5-ad38-4022-895c-7ac318f31437.png" width="400px"> </div><br>
让所有存活的对象都向一端移动，然后直接清理掉端边界以外的内存。

优点:

- 不会产生内存碎片

不足:

- 需要移动大量对象，处理效率比较低。

### 3. 复制

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/b2b77b9e-958c-4016-8ae5-9c6edd83871e.png" width="400px"> </div><br>
将内存划分为大小相等的两块，每次只使用其中一块，当这一块内存用完了就将还存活的对象复制到另一块上面，然后再把使用过的内存空间进行一次清理。

主要不足是只使用了内存的一半。

现在的商业虚拟机都采用这种收集算法回收新生代，但是并不是划分为大小相等的两块，而是一块较大的 Eden 空间和两块较小的 Survivor 空间，每次使用 Eden 和其中一块 Survivor。在回收时，将 Eden 和 Survivor 中还存活着的对象全部复制到另一块 Survivor 上，最后清理 Eden 和使用过的那一块 Survivor。

HotSpot 虚拟机的 Eden 和 Survivor 大小比例默认为 8:1，保证了内存的利用率达到 90%。如果每次回收有多于 10% 的对象存活，那么一块 Survivor 就不够用了，此时需要依赖于老年代进行空间分配担保，也就是借用老年代的空间存储放不下的对象。

### 4. 分代收集

现在的商业虚拟机采用分代收集算法，它根据对象存活周期将内存划分为几块，不同块采用适当的收集算法。

一般将堆分为新生代和老年代。

- 新生代使用：复制算法
- 老年代使用：标记 - 清除 或者 标记 - 整理 算法

## 垃圾收集器

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/c625baa0-dde6-449e-93df-c3a67f2f430f.jpg" width=""/> </div><br>
以上是 HotSpot 虚拟机中的 7 个垃圾收集器，连线表示垃圾收集器可以配合使用。

- 单线程与多线程：单线程指的是垃圾收集器只使用一个线程，而多线程使用多个线程；
- 串行与并行：串行指的是垃圾收集器与用户程序交替执行，这意味着在执行垃圾收集的时候需要停顿用户程序；并行指的是垃圾收集器和用户程序同时执行。除了 CMS 和 G1 之外，其它垃圾收集器都是以串行的方式执行。

### 1. Serial 收集器

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/22fda4ae-4dd5-489d-ab10-9ebfdad22ae0.jpg" width=""/> </div><br>
Serial 翻译为串行，也就是说它以串行的方式执行。

它是单线程的收集器，只会使用一个线程进行垃圾收集工作。

它的优点是简单高效，在单个 CPU 环境下，由于没有线程交互的开销，因此拥有最高的单线程收集效率。

它是 Client 场景下的默认新生代收集器，因为在该场景下内存一般来说不会很大。它收集一两百兆垃圾的停顿时间可以控制在一百多毫秒以内，只要不是太频繁，这点停顿时间是可以接受的。

### 2. ParNew 收集器

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/81538cd5-1bcf-4e31-86e5-e198df1e013b.jpg" width=""/> </div><br>
它是 Serial 收集器的多线程版本。多cpu下效率比serial好,但单核不如,因为有进程切换开销

它是 Server 场景下默认的新生代收集器，除了性能原因外，主要是因为除了 Serial 收集器，只有它能与 CMS 收集器配合使用。

### 3. Parallel Scavenge 收集器(平行清除)

与 ParNew 一样是多线程收集器。(适合交互少,吞吐量大的场景)

其它收集器目标是尽可能缩短垃圾收集时用户线程的停顿时间，而它的目标是达到一个可控制的吞吐量，因此它被称为“吞吐量优先”收集器。这里的吞吐量指 CPU 用于运行用户程序的时间占总时间的比值。

停顿时间越短就越适合需要与用户交互的程序，良好的响应速度能提升用户体验。

而高吞吐量则可以高效率地利用 CPU 时间，尽快完成程序的运算任务，适合在后台运算而不需要太多交互的任务。缩短停顿时间是以牺牲吞吐量和新生代空间来换取的：**新生代空间变小，垃圾回收变得频繁，导致吞吐量下降。**

可以通过一个开关参数打开 GC 自适应的调节策略（GC Ergonomics），就不需要手工指定新生代的大小（-Xmn）、Eden 和 Survivor 区的比例、晋升老年代对象年龄等细节参数了。虚拟机会根据当前系统的运行情况收集性能监控信息，动态调整这些参数以提供最合适的停顿时间或者最大的吞吐量。

**新生代收集器分为(单线程serial,多线程ParNew,少交互多吞吐Parallel Scavenge)**

### 4. Serial Old 收集器(以下是老年代收集器)

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/08f32fd3-f736-4a67-81ca-295b2a7972f2.jpg" width=""/> </div><br>
是 Serial 收集器的老年代版本，也是给 Client 场景下的虚拟机使用。如果用在 Server 场景下，它有两大用途：

- 在 JDK 1.5 以及之前版本（Parallel Old 诞生以前）中与 Parallel Scavenge 收集器搭配使用。
- 作为 CMS 收集器的后备预案，在并发收集发生 Concurrent Mode Failure 时使用(并发失败,就用串行)

### 5. Parallel Old 收集器

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/278fe431-af88-4a95-a895-9c3b80117de3.jpg" width=""/> </div><br>
是 Parallel Scavenge 收集器的老年代版本。

在注重吞吐量以及 CPU 资源敏感的场合，都可以优先考虑 Parallel Scavenge 加 Parallel Old 收集器。

### 6. CMS 收集器

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/62e77997-6957-4b68-8d12-bfd609bb2c68.jpg" width=""/> </div><br>
CMS（Concurrent Mark Sweep），Mark Sweep 指的是标记 - 清除算法。

分为以下四个流程：

- 初始标记：仅仅只是标记一下 GC Roots 能直接关联到的对象，速度很快，需要停顿。
- 并发标记：进行 GC Roots Tracing 的过程，它在整个回收过程中耗时最长，不需要停顿。
- 重新标记：为了修正并发标记期间因用户程序继续运作而导致标记产生变动的那一部分对象的标记记录，需要停顿。
- 并发清除：不需要停顿,耗时较长

在整个过程中耗时最长的并发标记和并发清除过程中，收集器线程都可以与用户线程一起工作，不需要进行停顿。初始标记和重新标记耗时短,要停顿.

具有以下缺点：

- 吞吐量低：低停顿时间是以牺牲吞吐量为代价的，导致 CPU 利用率不够高。
- 无法处理浮动垃圾，可能出现 Concurrent Mode Failure。浮动垃圾是指并发清除阶段由于用户线程继续运行而产生的垃圾，这部分垃圾只能到下一次 GC 时才能进行回收。由于浮动垃圾的存在，因此需要预留出一部分内存，意味着 CMS 收集不能像其它收集器那样等待老年代快满的时候再回收。如果预留的内存不够存放浮动垃圾，就会出现 Concurrent Mode Failure，这时虚拟机将临时启用 Serial Old 来替代 CMS。
- 标记 - 清除算法导致的空间碎片，往往出现老年代空间剩余，但无法找到足够大连续空间来分配当前对象，不得不提前触发一次 Full GC。

### 7. G1 收集器

G1（Garbage-First），它是一款面向服务端应用的垃圾收集器，在多 CPU 和大内存的场景下有很好的性能。HotSpot 开发团队赋予它的使命是未来可以替换掉 CMS 收集器。

堆被分为新生代和老年代，其它收集器进行收集的范围都是整个新生代或者老年代，而 G1 可以直接对新生代和老年代一起回收。

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/4cf711a8-7ab2-4152-b85c-d5c226733807.png" width="600"/> </div><br>
G1 把堆划分成多个大小相等的独立区域（Region），新生代和老年代不再物理隔离。

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/9bbddeeb-e939-41f0-8e8e-2b1a0aa7e0a7.png" width="600"/> </div><br>
通过引入 Region 的概念，从而将原来的一整块内存空间划分成多个的小空间，使得每个小空间可以单独进行垃圾回收。这种划分方法带来了很大的灵活性，使得可预测的停顿时间模型成为可能。通过记录每个 Region 垃圾回收时间以及回收所获得的空间（这两个值是通过过去回收的经验获得），并维护一个优先列表，每次根据允许的收集时间，优先回收价值最大的 Region。

每个 Region 都有一个 Remembered Set，用来记录该 Region 对象的引用对象所在的 Region。通过使用 Remembered Set，在做可达性分析的时候就可以避免全堆扫描。

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/f99ee771-c56f-47fb-9148-c0036695b5fe.jpg" width=""/> </div><br>
如果不计算维护 Remembered Set 的操作，G1 收集器的运作大致可划分为以下几个步骤：

- 初始标记
- 并发标记
- 最终标记：为了修正在并发标记期间因用户程序继续运作而导致标记产生变动的那一部分标记记录，虚拟机将这段时间对象变化记录在线程的 Remembered Set Logs 里面，最终标记阶段需要把 Remembered Set Logs 的数据合并到 Remembered Set 中。这阶段需要停顿线程，但是可并行执行。
- 筛选回收：首先对各个 Region 中的回收价值和成本进行排序，根据用户所期望的 GC 停顿时间来制定回收计划。此阶段其实也可以做到与用户程序一起并发执行，但是因为只回收一部分 Region，时间是用户可控制的，而且停顿用户线程将大幅度提高收集效率。

具备如下特点：

- 空间整合：整体来看是基于“标记 - 整理”算法实现的收集器，从局部（两个 Region 之间）上来看是基于“复制”算法实现的，这意味着运行期间不会产生内存空间碎片。
- 可预测的停顿：能让使用者明确指定在一个长度为 M 毫秒的时间片段内，消耗在 GC 上的时间不得超过 N 毫秒。
- 并行与并发 G1 能充分利用多CPU、多核环境下的硬件优势，使用多个CPU来缩短“Stop The World”停顿时间，部分其他收集器原本需要停顿Java线程执行的GC动作，G1收集器仍然可以通过并发的方式让Java程序继续执行。
- 分代收集 与其他收集器一样，分代概念在G1中依然得以保留。虽然G1可以不需要其他收集器配合就能独立管理整个GC堆，但它能够采用不同方式去处理新创建的对象和已存活一段时间、熬过多次GC的旧对象来获取更好的收集效果。




# 三、内存分配与回收策略

## Minor GC 和 Full GC

- Minor GC：回收新生代，因为新生代对象存活时间很短，因此 Minor GC 会频繁执行，执行的速度一般也会比较快。

- Full GC：回收老年代和新生代，老年代对象其存活时间长，因此 Full GC 很少执行，执行速度会比 Minor GC 慢很多。

## 内存分配策略

### 1. 对象优先在 Eden 分配

大多数情况下，对象在新生代 Eden 上分配，当 Eden 空间不够时，发起 Minor GC。

### 2. 大对象直接进入老年代

大对象是指需要连续内存空间的对象，最典型的大对象是那种很长的字符串以及数组。

经常出现大对象会提前触发垃圾收集以获取足够的连续空间分配给大对象。

-XX:PretenureSizeThreshold，大于此值的对象直接在老年代分配，避免在 Eden 和 Survivor 之间的大量内存复制。

### 3. 长期存活的对象进入老年代

为对象定义年龄计数器，对象在 Eden 出生并经过 Minor GC 依然存活，将移动到 Survivor 中，年龄就增加 1 岁，增加到一定年龄则移动到老年代中。

-XX:MaxTenuringThreshold 用来定义年龄的阈值。

### 4. 动态对象年龄判定

虚拟机并不是永远要求对象的年龄必须达到 MaxTenuringThreshold 才能晋升老年代，如果在 Survivor 中相同年龄所有对象大小的总和大于 Survivor 空间的一半，则年龄大于或等于该年龄的对象可以直接进入老年代，无需等到 MaxTenuringThreshold 中要求的年龄。

### 5. 空间分配担保(先看最大空间,再看平均空间)

在发生 Minor GC 之前，虚拟机先检查老年代最大可用的连续空间是否大于新生代所有对象总空间，如果条件成立的话，那么 Minor GC 可以确认是安全的。

如果不成立的话虚拟机会查看 HandlePromotionFailure 的值是否允许担保失败，如果允许那么就会继续检查老年代最大可用的连续空间是否大于历次晋升到老年代对象的平均大小，如果大于，将尝试着进行一次 Minor GC；如果小于，或者 HandlePromotionFailure 的值不允许冒险，那么就要进行一次 Full GC。

## Full GC 的触发条件

对于 Minor GC，其触发条件非常简单，当 Eden 空间满时，就将触发一次 Minor GC。而 Full GC 则相对复杂，有以下条件：

### 1. 调用 System.gc()

只是建议虚拟机执行 Full GC，但是虚拟机不一定真正去执行。不建议使用这种方式，而是让虚拟机管理内存。

### 2. 老年代空间不足

老年代空间不足的常见场景为前文所讲的大对象直接进入老年代、长期存活的对象进入老年代等。

为了避免以上原因引起的 Full GC，应当尽量不要创建过大的对象以及数组。除此之外，可以通过 -Xmn 虚拟机参数调大新生代的大小，让对象尽量在新生代被回收掉，不进入老年代。还可以通过 -XX:MaxTenuringThreshold 调大对象进入老年代的年龄，让对象在新生代多存活一段时间。

### 3. 空间分配担保失败

使用复制算法的 Minor GC 需要老年代的内存空间作担保，如果担保失败会执行一次 Full GC。具体内容请参考上面的第 5 小节。

### 4. JDK 1.7 及以前的永久代空间不足

在 JDK 1.7 及以前，HotSpot 虚拟机中的方法区是用永久代实现的，永久代中存放的为一些 Class 的信息、常量、静态变量等数据。

当系统中要加载的类、反射的类和调用的方法较多时，永久代可能会被占满，在未配置为采用 CMS GC 的情况下也会执行 Full GC。如果经过 Full GC 仍然回收不了，那么虚拟机会抛出 java.lang.OutOfMemoryError。

为避免以上原因引起的 Full GC，可采用的方法为增大永久代空间或转为使用 CMS GC。

### 5. Concurrent Mode Failure

执行 CMS GC 的过程中同时有对象要放入老年代，而此时老年代空间不足（可能是 GC 过程中浮动垃圾过多导致暂时性的空间不足），便会报 Concurrent Mode Failure 错误，并触发 Full GC。


# JVM类生命周期概述：加载时机与加载过程
原文链接：https://blog.csdn.net/justloveyou_/article/details/72466105
## 类加载机制定义
一个.java文件在编译后会形成相应的一个或多个Class文件，这些Class文件中描述了类的各种信息，并且它们最终都需要被加载到虚拟机中才能被运行和使用。

虚拟机把描述类的数据从Class文件**加载到内存**，并对数据进行校验，转换解析和**初始化**，最终形成可以被虚拟机直接使用的Java类型的过程就是虚拟机的**类加载机制**。

JVM类加载机制主要包括两个问题：类加载的时机与步骤 和 类加载的方式。


## 类的生命周期

包括以下 7 个阶段：

-  **加载（Loading）** 
-  **验证（Verification）** 
-  **准备（Preparation）** 
-  **解析（Resolution）** 
-  **初始化（Initialization）** 
- 使用（Using）
- 卸载（Unloading）


Java类从被加载到虚拟机内存中开始，到卸载出内存为止，它的整个生命周期包括：加载（Loading）、验证（Verification）、准备(Preparation)、解析(Resolution)、初始化(Initialization)、使用(Using) 和 卸载(Unloading)七个阶段。其中准备、验证、解析3个部分统称为连接（Linking）
<div align="center"> <img src="pictures/java-vm/Snipaste_2019-08-13_09-58-03.jpg" width="430px"> </div><br>
加载、验证、准备、初始化和卸载这5个阶段的顺序是确定的，类的加载过程必须按照这种顺序按部就班地开始，而解析阶段则不一定：它在某些情况下可以在初始化阶段之后再开始，这是为了支持Java语言的运行时绑定（也称为动态绑定或晚期绑定）。

这些阶段通常都是相互交叉地混合式进行的，也就是说通常会在一个阶段执行的过程中调用或激活另外一个阶段。

## 加载时机与初始化时机
### 类加载时机

什么情况下虚拟机需要开始加载一个类呢？虚拟机规范中并没有对此进行强制约束，这点可以交给虚拟机的具体实现来自由把握。程序运行期间虚拟机自行加载.

### 类初始化时机(五种主动引用)
**虚拟机规范指明 有且只有 五种情况必须立即对类进行初始化（而这一过程自然发生在加载、验证、准备之后）：**

1) 遇到new、getstatic、putstatic或invokestatic这四条字节码指令时，如果类没有进行过初始化，则需要先对其进行初始化。生成这四条指令的最常见的Java代码场景是：

使用new关键字实例化对象的时候；

读取或设置一个类的静态字段（被final修饰，已在编译器把结果放入常量池的静态字段除外）的时候；

调用一个类的静态方法的时候。

（注意，newarray指令触发的只是数组类型本身的初始化，而不会导致其相关类型的初始化，比如，new String[]只会直接触发String[]类的初始化，也就是触发对类[Ljava.lang.String的初始化，而直接不会触发String类的初始化）

2) 使用java.lang.reflect包的方法对类进行反射调用的时候，如果类没有进行过初始化，则需要先触发其初始化。

3) 当初始化一个类的时候，如果发现其父类还没有进行过初始化，则需要先触发其父类的初始化。

4) 当虚拟机启动时，用户需要指定一个要执行的主类（包含main()方法的那个类），虚拟机会先初始化这个主类。

5) 当使用jdk1.7动态语言支持时，如果一个java.lang.invoke.MethodHandle实例最后的解析结果REF_getstatic,REF_putstatic,REF_invokeStatic的方法句柄，并且这个方法句柄所对应的类没有进行初始化，则需要先出触发其初始化。


注意，对于这五种会触发类进行初始化的场景，虚拟机规范中使用了一个很强烈的限定语：“有且只有”，这五种场景中的行为称为对一个类进行** 主动引用**。除此之外，所有引用类的方式，都不会触发初始化，称为 **被动引用**。


特别需要指出的是，类的实例化与类的初始化是两个完全不同的概念：  
类的实例化是指创建一个类的实例(对象)的过程；
类的初始化是指为类中各个类成员(被static修饰的成员变量)赋初始值的过程，是类生命周期中的一个阶段。
### 类的被动引用(不触发初始化)
1)、通过子类引用父类的静态字段，不会导致子类初始化
```java
public class SSClass{
    static{
        System.out.println("SSClass");
    }
}  

public class SClass extends SSClass{
    static{
        System.out.println("SClass init!");
    }

    public static int value = 123;

    public SClass(){
        System.out.println("init SClass");
    }
}

public class SubClass extends SClass{
    static{
        System.out.println("SubClass init");
    }

    static int a;

    public SubClass(){
        System.out.println("init SubClass");
    }
}

public class NotInitialization{
    public static void main(String[] args){
        System.out.println(SubClass.value);
    }
}/* Output: 
        SSClass
        SClass init!
        123     
 *///:~
```
子类引用父类的静态字段只会出发父类的初始化

在本例中，由于value字段是在类SClass中定义的，因此该类会被初始化；此外，在初始化类SClass时，虚拟机会发现其父类SSClass还未被初始化，因此虚拟机将先初始化父类SSClass，然后初始化子类SClass，而SubClass始终不会被初始化。


　2)、通过数组定义来引用类，不会触发此类的初始化

```java
public class NotInitialization{
    public static void main(String[] args){
        SClass[] sca = new SClass[10];
    }
}
```
　上述案例运行之后并没有任何输出

虚拟机没有初始化数组内的类,而是初始化了一个元素类型为SClass的一维数组,它是由虚拟机自动生成的，直接继承于Object的子类，创建动作由字节码指令newarray触发。

3)、常量在编译阶段会存入调用类的常量池中，本质上并没有直接引用到定义常量的类，因此不会触发定义常量的类的初始化
```java
public class ConstClass{

    static{
        System.out.println("ConstClass init!");
    }

    public static  final String CONSTANT = "hello world";
}

public class NotInitialization{
    public static void main(String[] args){
        System.out.println(ConstClass.CONSTANT);
    }
}/* Output: 
        hello world
 *///:~


```
　　上述代码运行之后，只输出 “hello world”，这是因为虽然在Java源码中引用了ConstClass类中的常量CONSTANT，但是编译阶段将此常量的值“hello world”存储到了NotInitialization常量池中，对常量ConstClass.CONSTANT的引用实际都被转化为NotInitialization类对自身常量池的引用了。也就是说，实际上NotInitialization的Class文件之中并没有ConstClass类的符号引用入口，这两个类在编译为Class文件之后就不存在关系了


## 类加载的过程(每一步做了啥)
<div align="center"> <img src="pictures/java-vm/Snipaste_2019-08-13_09-58-03.jpg" width="430px"> </div><br>
1、加载（Loading）

　　在加载阶段（可以参考java.lang.ClassLoader的loadClass()方法），虚拟机需要完成以下三件事情：

　　(1). 由加载器通过一个类的全限定名来获取定义此类的二进制字节流（并没有指明要从一个Class文件中获取，可以从其他渠道，譬如：网络、动态生成、数据库等）；

　　(2). 将这个字节流所代表的静态存储结构转化为方法区的运行时数据结构；

　　(3). 在内存中(对于HotSpot虚拟就而言就是方法区)生成一个代表这个类的java.lang.Class对象，作为方法区这个类的各种数据的访问入口；

　　加载阶段和连接阶段（Linking）的部分内容（如一部分字节码文件格式验证动作）是交叉进行的，加载阶段尚未完成，连接阶段可能已经开始，但这些夹在加载阶段之中进行的动作，仍然属于连接阶段的内容，这两个阶段的开始时间仍然保持着固定的先后顺序。

2、验证（Verification）

　　验证是连接阶段的第一步，这一阶段的目的是为了确保Class文件的字节流中包含的信息符合当前虚拟机的要求，并且不会危害虚拟机自身的安全。
文件格式验证：验证字节流是否符合Class文件格式的规范(例如，是否以魔术0xCAFEBABE开头、主次版本号是否在当前虚拟机的处理范围之内、常量池中的常量是否有不被支持的类型)

元数据验证：对字节码描述的信息进行语义分析，以保证其描述的信息符合Java语言规范的要求(例如：这个类是否有父类，除了java.lang.Object之外)；

字节码验证：通过数据流和控制流分析，确定程序语义是合法的、符合逻辑的;

符号引用验证：确保解析动作能正确执行。

　　验证阶段是非常重要的，但不是必须的，它对程序运行期没有影响。如果所引用的类经过反复验证，那么可以考虑采用-Xverifynone参数来关闭大部分的类验证措施，以缩短虚拟机类加载的时间

3、准备(Preparation)
为static变量在方法区分配内存,并设置类变量初始值(一般为0)

这时候内存分配的变量只有类变量,没有实例变量,实例变量会在对象实例化的时候跟随对象实例化一起分配进堆内存.

其次，这里所说的初始值“通常情况”下是数据类型的零值，假设一个类变量的定义为：
```java
 public static int value = 123;
```
 那么，变量value在准备阶段过后的值为0而不是123。因为这时候尚未开始执行任何java方法，而把value赋值为123的putstatic指令是程序被编译后，存放于类构造器方法`<clinit>()`之中，所以把value赋值为123的动作将在初始化阶段才会执行。

 至于“特殊情况”是指：当类字段的字段属性是ConstantValue时，会在准备阶段初始化为指定的值，所以标注为final之后，value的值在准备阶段初始化为123而非0。

 ```java
 　public static final int value = 123;
 ```

4、 解析(Resolution)

　　解析阶段是虚拟机将常量池内的符号引用替换为直接引用的过程。解析动作主要针对类或接口、字段、类方法、接口方法、方法类型、方法句柄和调用点限定符7类符号引用进行。

5、初始化(Initialization)

　　类初始化阶段是类加载过程的最后一步。在前面的类加载过程中，除了在加载阶段用户应用程序可以通过自定义类加载器参与之外，其余动作完全由虚拟机主导和控制。到了初始化阶段，才真正开始执行类中定义的java程序代码(字节码)。

在准备阶段，变量已经赋过一次系统要求的初始值(零值)；而在初始化阶段，则根据程序猿通过程序制定的主观计划去初始化类变量和其他资源，或者更直接地说：初始化阶段是执行类构造器`<clinit>()`方法的过程。`<clinit>()`方法是由编译器自动收集类中的所有类变量的赋值动作和静态语句块static{}中的语句合并产生的，编译器收集的顺序是由语句在源文件中出现的顺序所决定的，静态语句块只能访问到定义在静态语句块之前的变量，定义在它之后的变量，在前面的静态语句块可以赋值，但是不能访问。如下：

```java
public class Test{
    static{
        i=0;
        System.out.println(i);//Error：Cannot reference a field before it is defined（非法向前应用）
    }
    static int i=1;
}

```
修改为下面就可以正常运行了：
```java
public class Test{
    static{
        i=0;
        //System.out.println(i);
    }

    static int i=1;

    public static void main(String args[]){
        System.out.println(i);
    }
}/* Output: 
        1
 *///:~
```



 类构造器`<clinit>()`与实例构造器`<init>()`不同，它不需要程序员进行显式调用

 虚拟机会保证在子类类构造器`<clinit>()`执行之前，父类的类构造`<clinit>()`执行完毕。由于父类的构造器`<clinit>()`先执行，也就意味着父类中定义的静态语句块/静态变量的初始化要优先于子类的静态语句块/静态变量的初始化执行。

 特别地，类构造器`<clinit>()`对于类或者接口来说并不是必需的，如果一个类中没有静态语句块，也没有对类变量的赋值操作，那么编译器可以不为这个类生产类构造器`<clinit>()`。

　虚拟机会保证一个类的类构造器<clinit>()在多线程环境中被正确的加锁、同步，如果多个线程同时去初始化一个类，那么只会有一个线程去执行这个类的类构造器<clinit>()，其他线程都需要阻塞等待，直到活动线程执行<clinit>()方法完毕。

当执行的线程初始化完成后，其他阻塞线程不会再执行初始化，因为**在同一个类加载器下，一个类型只会被初始化一次**'
```java
public class DealLoopTest {
    static{
        System.out.println("DealLoopTest...");
    }
    static class DeadLoopClass {
        static {
            if (true) {
                System.out.println(Thread.currentThread()
                        + "init DeadLoopClass");
                while (true) {      // 模拟耗时很长的操作
                }
            }
        }
    }

    public static void main(String[] args) {
        Runnable script = new Runnable() {   // 匿名内部类
            public void run() {
                System.out.println(Thread.currentThread() + " start");
                DeadLoopClass dlc = new DeadLoopClass();
                System.out.println(Thread.currentThread() + " run over");
            }
        };

        Thread thread1 = new Thread(script);
        Thread thread2 = new Thread(script);
        thread1.start();
        thread2.start();
    }
}/* Output: 
        DealLoopTest...
        Thread[Thread-1,5,main] start
        Thread[Thread-0,5,main] start
        Thread[Thread-1,5,main]init DeadLoopClass
 *///:~
```
 如上述代码所示，在初始化DeadLoopClass类时，线程Thread-1得到执行并在执行这个类的类构造器`<clinit>()` 时，由于该方法包含一个死循环，因此久久不能退出。

**创建一个对象常常需要经历如下几个过程：父类的类构造器`<clinit>() `-> 子类的类构造器`<clinit>() `-> 父类的成员变量和实例代码块 -> 父类的构造函数 -> 子类的成员变量和实例代码块 -> 子类的构造函数。**

举例：
```java 
public class StaticTest {
    public static void main(String[] args) {
        staticFunction();
    }

    static StaticTest st = new StaticTest();

    static {   //静态代码块
        System.out.println("1");
    }

    {       // 实例代码块
        System.out.println("2");
    }

    StaticTest() {    // 实例构造器
        System.out.println("3");
        System.out.println("a=" + a + ",b=" + b);
    }

    public static void staticFunction() {   // 静态方法
        System.out.println("4");
    }

    int a = 110;    // 实例变量
    static int b = 112;     // 静态变量
    //static final int b=112
    //那么，在准备阶段b的值就是112，而不再是0了。
}/* Output: 
        2
        3
        a=110,b=0
        1
        4
 *///:~
```
解释：

一般思路是：  
准备阶段   
先常量直接赋值（准备阶段转移到常量类），再 静态变量，静态代码（赋默认值0）

初始化阶段  
父类的类构造器`<clinit>()` -> 子类的类构造器`<clinit>() `-> 父类的成员变量和实例代码块 -> 父类的构造函数 -> 子类的成员变量和实例代码块 -> 子类的构造函数。

`<clinit>()`方法是由编译器自动收集类中的所有类变量的赋值动作和静态语句块static{}中的语句合并产生的，编译器收集的顺序是由语句在源文件中出现的顺序所决定的，静态语句块只能访问到定义在静态语句块之前的变量，定义在它之后的变量，在前面的静态语句块可以赋值，但是不能访问。


此题需要注意：   
在初始化阶段，当JVM对类StaticTest进行初始化时，首先会执行下面的语句：
```java
static StaticTest st = new StaticTest();
```
也就是实例化StaticTest对象，但这个时候类都没有初始化完毕啊，能直接进行实例化吗？事实上，这涉及到一个根本问题就是：实例初始化不一定要在类初始化结束之后才开始初始化。 下面我们结合类的加载过程说明这个问题。


此时，就碰到了笔者上面的疑惑，即“在类都没有初始化完毕之前，能直接进行实例化相应的对象吗？”。事实上，从Java角度看，我们知道一个类初始化的基本常识，那就是：在同一个类加载器下，一个类型只会被初始化一次。所以，一旦开始初始化一个类型，无论是否完成，后续都不会再重新触发该类型的初始化阶段了(只考虑在同一个类加载器下的情形)。因此，在实例化上述程序中的st变量时，实际上是把实例初始化嵌入到了静态初始化流程中，并且在上面的程序中，嵌入到了静态初始化的起始位置。这就导致了实例初始化完全发生在静态初始化之前，当然，这也是导致a为110b为0的原因。

因此，上述程序的StaticTest类构造器`<clinit>()`的实现等价于：
```java
public class StaticTest {
    <clinit>(){
        a = 110;    // 实例变量
        System.out.println("2");        // 实例代码块
        System.out.println("3");     // 实例构造器中代码的执行
        System.out.println("a=" + a + ",b=" + b);  // 实例构造器中代码的执行
        类变量st被初始化
        System.out.println("1");        //静态代码块
        类变量b被初始化为112
    }
}
```
下面，我们对上述程序稍作改动，如下所示：

```java
public class StaticTest {
    public static void main(String[] args) {
        staticFunction();
    }

    static StaticTest st = new StaticTest();

    static {
        System.out.println("1");
    }

    {
        System.out.println("2");
    }

    StaticTest() {
        System.out.println("3");
        System.out.println("a=" + a + ",b=" + b);
    }

    public static void staticFunction() {
        System.out.println("4");
    }

    int a = 110;
    static int b = 112;
    static StaticTest st1 = new StaticTest();
}
```
在程序最后的一行，增加代码行：static StaticTest st1 = new StaticTest();

```
输出变成了
2
3
a=110,b=0
1
2
3
a=110,b=112
4
```

关于方法的重写：
```java

class Foo {
    int i = 1;

    Foo() {
        System.out.println(i);             
        int x = getValue();
        System.out.println(x);            
    }

    {
        i = 2;
    }

    protected int getValue() {
        return i;
    }
}

//子类
class Bar extends Foo {
    int j = 1;

    Bar() {
        j = 2;
    }

    {
        j = 3;
    }

    @Override
    protected int getValue() {
        return j;
    }
}

public class ConstructorExample {
    public static void main(String... args) {
        Bar bar = new Bar();
        System.out.println(bar.getValue());        
    }
}
输出结果是 

2
0
2
这样程序就好看多了，我们一眼就可以观察出程序的输出结果。在通过使用Bar类的构造方法new一个Bar类的实例时，首先会调用Foo类构造函数，因此(1)处输出是2，这从Foo类构造函数的等价变换中可以直接看出。

(2)处输出是0，为什么呢？因为在执行Foo的构造函数的过程中，由于Bar重载了Foo中的getValue方法，所以根据Java的多态特性可以知道，其调用的getValue方法是被Bar重载的那个getValue方法。但由于这时Bar的构造函数还没有被执行，因此此时j的值还是默认值0，因此(2)处输出是0。

最后，在执行(3)处的代码时，由于bar对象已经创建完成，所以此时再访问j的值时，就得到了其初始化后的值2，这一点可以从Bar类构造函数的等价变换中直接看出。
```

## 加载后 类的实例化与初始化
一个Java对象的创建过程往往包括 类初始化 和 类实例化 两个阶段。

实例化对象之前需要先加载类并通过类构造器进行初始化

### 创建对象的5种方式,两种不需要构造器
1). 使用new关键字创建对象

这是我们最常见的也是最简单的创建对象的方式，通过这种方式我们可以调用任意的构造函数（无参的和有参的）去创建对象。比如：

使用new关键字来调用一个类的构造函数显式地创建对象，这种方式在Java规范中被称为 : 由执行类实例创建表达式而引起的对象创建。

```java
　Student student = new Student();
```
2). 使用Class类的newInstance方法(反射机制)

其实newinstance调用的是无参构造器

```java
　　Student student2 = (Student)Class.forName("Student类全限定名").newInstance();　
或者：
　　Student stu = Student.class.newInstance();
```

3). 使用Constructor类的newInstance方法(反射机制)

　java.lang.relect.Constructor类里也有一个newInstance方法可以创建对象，该方法和Class类中的newInstance方法很像，但是相比之下，Constructor类的newInstance方法更加强大些，我们可以通过这个newInstance方法调用有参数的和私有的构造函数

```java
public class Student {

    private int id;

    public Student(Integer id) {
        this.id = id;
    }

    public static void main(String[] args) throws Exception {

        Constructor<Student> constructor = Student.class
                .getConstructor(Integer.class);
        Student stu3 = constructor.newInstance(123);
    }
}
```

使用newInstance方法的这两种方式创建对象使用的就是Java的反射机制，事实上Class的newInstance方法内部调用的也是Constructor的newInstance方法。
4). 使用Clone方法创建对象

　无论何时我们调用一个对象的clone方法，JVM都会帮我们创建一个新的、一样的对象，特别需要说明的是，用clone方法创建对象的过程中并不会调用任何构造函数。

简单而言，要想使用clone方法，我们就必须先实现Cloneable接口并实现其定义的clone方法，这也是原型模式的应用。比如：
```java
public class Student implements Cloneable{

    private int id;

    public Student(Integer id) {
        this.id = id;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return super.clone();
    }

    public static void main(String[] args) throws Exception {

        Constructor<Student> constructor = Student.class
                .getConstructor(Integer.class);
        Student stu3 = constructor.newInstance(123);
        Student stu4 = (Student) stu3.clone();
    }
}
```

5). 使用(反)序列化机制创建对象

VM并不会调用任何构造函数。为了反序列化一个对象，我们需要让我们的类实现Serializable接口，比如：
```java
public class Student implements Cloneable, Serializable {

    private int id;

    public Student(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Student [id=" + id + "]";
    }

    public static void main(String[] args) throws Exception {

        Constructor<Student> constructor = Student.class
                .getConstructor(Integer.class);
        Student stu3 = constructor.newInstance(123);

        // 写对象
        ObjectOutputStream output = new ObjectOutputStream(
                new FileOutputStream("student.bin"));
        output.writeObject(stu3);
        output.close();

        // 读对象
        ObjectInputStream input = new ObjectInputStream(new FileInputStream(
                "student.bin"));
        Student stu5 = (Student) input.readObject();
        System.out.println(stu5);
    }
}
```

综合上述

```java
public class Student implements Cloneable, Serializable {

    private int id;

    public Student() {

    }

    public Student(Integer id) {
        this.id = id;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return super.clone();
    }

    @Override
    public String toString() {
        return "Student [id=" + id + "]";
    }

    public static void main(String[] args) throws Exception {

        System.out.println("使用new关键字创建对象：");
        Student stu1 = new Student(123);
        System.out.println(stu1);
        System.out.println("\n---------------------------\n");


        System.out.println("使用Class类的newInstance方法创建对象：");
        Student stu2 = Student.class.newInstance();    //对应类必须具有无参构造方法，且只有这一种创建方式
        System.out.println(stu2);
        System.out.println("\n---------------------------\n");

        System.out.println("使用Constructor类的newInstance方法创建对象：");
        Constructor<Student> constructor = Student.class
                .getConstructor(Integer.class);   // 调用有参构造方法
        Student stu3 = constructor.newInstance(123);   
        System.out.println(stu3);
        System.out.println("\n---------------------------\n");

        System.out.println("使用Clone方法创建对象：");
        Student stu4 = (Student) stu3.clone();
        System.out.println(stu4);
        System.out.println("\n---------------------------\n");

        System.out.println("使用(反)序列化机制创建对象：");
        // 写对象
        ObjectOutputStream output = new ObjectOutputStream(
                new FileOutputStream("student.bin"));
        output.writeObject(stu4);
        output.close();

        // 读取对象
        ObjectInputStream input = new ObjectInputStream(new FileInputStream(
                "student.bin"));
        Student stu5 = (Student) input.readObject();
        System.out.println(stu5);

    }
}/* Output: 
        使用new关键字创建对象：
        Student [id=123]

        ---------------------------

        使用Class类的newInstance方法创建对象：
        Student [id=0]

        ---------------------------

        使用Constructor类的newInstance方法创建对象：
        Student [id=123]

        ---------------------------

        使用Clone方法创建对象：
        Student [id=123]

        ---------------------------

        使用(反)序列化机制创建对象：
        Student [id=123]
*///:~
```
　从Java虚拟机层面看，除了使用new关键字创建对象的方式外，其他方式全部都是通过转变为invokevirtual指令直接创建对象的。

### 创建对象的过程
分配内存  
对象被创建时,虚拟机就会为其分配内存来存放对象自己的实例变量及其从父类继承过来的实例变量(即使这些从超类继承过来的实例变量有可能被隐藏也会被分配空间)。在为这些实例变量分配内存的同时，这些实例变量也会被赋予默认值(零值)。

初始化   
在内存分配完成之后，Java虚拟机就会开始对新创建的对象按照程序猿的意志进行初始化。在Java对象初始化过程中，主要涉及三种执行对象初始化的结构，分别是实例变量初始化、实例代码块初始化 以及 构造函数初始化。

1、实例变量初始化与实例代码块初始化

我们在定义（声明）实例变量的同时，还可以直接对实例变量进行赋值或者使用实例代码块对其进行赋值。如果我们以这两种方式为实例变量进行初始化，那么它们将**在构造函数执行之前**完成这些初始化操作。

实际上，如果我们对实例变量直接赋值或者使用实例代码块赋值，那么编译器会将其中的代码放到类的构造函数中去，**并且这些代码会被放在对超类构造函数的调用语句之后(还记得吗？Java要求构造函数的第一条语句必须是超类构造函数的调用语句)，构造函数本身的代码之前**。

```java
public class InstanceVariableInitializer {  

    private int i = 1;  
    private int j = i + 1;  

    public InstanceVariableInitializer(int var){
        System.out.println(i);
        System.out.println(j);
        this.i = var;
        System.out.println(i);
        System.out.println(j);
    }

    {               // 实例代码块
        j += 3; 

    }

    public static void main(String[] args) {
        new InstanceVariableInitializer(8);
    }
}/* Output: 
            1
            5
            8
            5
 *///:~
```

 　上面的例子正好印证了上面的结论。特别需要注意的是，Java是按照编程顺序来执行实例变量初始化器和实例初始化器中的代码的，并且**不允许顺序靠前的实例代码块初始化在其后面定义的实例变量**，比如：

```java
public class InstanceInitializer {  
    {  
        j = i;  
    }  

    private int i = 1;  
    private int j;  
}  

public class InstanceInitializer {  
    private int j = i;  
    private int i = 1;  
}  
```
上面的这些代码都是无法通过编译的，编译器会抱怨说我们使用了一个未经定义的变量。之所以要这么做是为了保证一个变量在被使用之前已经被正确地初始化。

但是我们仍然有办法绕过这种检查，比如：

```java
public class InstanceInitializer {  
    private int j = getI();  
    private int i = 1;  

    public InstanceInitializer() {  
        i = 2;  
    }  

    private int getI() {  
        return i;  
    }  

    public static void main(String[] args) {  
        InstanceInitializer ii = new InstanceInitializer();  
        System.out.println(ii.j);  
    }  
}  
```
如果我们执行上面这段代码，那么会发现打印的结果是0。因此我们可以确信，变量j被赋予了i的默认值0，这一动作发生在实例变量i初始化之前和构造函数调用之前。


2、构造函数初始化

实例变量初始化与实例代码块初始化总是发生在构造函数初始化之前

众所周知，每一个Java中的对象都至少会有一个构造函数，如果我们没有显式定义构造函数，那么它将会有一个默认无参的构造函数。在编译生成的字节码中，这些**构造函数**会被命名成`<init>()`方法，参数列表与Java语言书写的构造函数的参数列表相同。 

 而**类构造器**的名字是`<clinit>()`;

Java要求在实例化类之前，必须先实例化其超类，以保证所创建实例的完整性。事实上，这一点是在构造函数中保证的：Java强制要求Object对象(Object是Java的顶层对象，没有超类)之外的所有对象构造函数的第一条语句必须是超类构造函数的调用语句或者是类中定义的其他的构造函数，如果我们既没有调用其他的构造函数，也没有显式调用超类的构造函数，那么编译器会为我们自动生成一个对超类构造函数的调用
```java
public class ConstructorExample {  

} 
```
对于上面代码中定义的类，我们观察编译之后的字节码，我们会发现编译器为我们生成一个构造函数，如下，

```java
aload_0  
invokespecial   #8; //Method java/lang/Object."<init>":()V  
return  
```
上面代码的第二行就是调用Object类的默认构造函数的指令。也就是说，如果我们显式调用超类的构造函数，那么该调用必须放在构造函数所有代码的最前面，也就是必须是构造函数的第一条指令。正因为如此，Java才可以使得一个对象在初始化之前其所有的超类都被初始化完成，并保证创建一个完整的对象出来。

如果我们在一个构造函数中调用另外一个构造函数，如下所示，

```java
public class ConstructorExample {  
    private int i;  

    ConstructorExample() {  
        this(1);  
        ....  
    }  

    ConstructorExample(int i) {  
        ....  
        this.i = i;  
        ....  
    }  
}  
```
对于这种情况，Java只允许在ConstructorExample(int i)内调用超类的构造函数，也就是说，下面两种情形的代码编译是无法通过的：

```java
public class ConstructorExample {  
    private int i;  

    ConstructorExample() {  
        super();  
        this(1);  // Error:Constructor call must be the first statement in a constructor
        ....  
    }  

    ConstructorExample(int i) {  
        ....  
        this.i = i;  
        ....  
    }  
}  


public class ConstructorExample {  
    private int i;  

    ConstructorExample() {  
        this(1);  
        super();  //Error: Constructor call must be the first statement in a constructor
        ....  
    }  

    ConstructorExample(int i) {  
        this.i = i;  
    }  
}   



```

Java通过对构造函数作出这种限制以便保证一个类的实例能够在被使用之前正确地初始化。

### 实例化小结: 实例化就是对象实例变量,代码,构造器的初始化,字节码中是init函数(要与静态变量,代码的初始化区分开,字节码中是clinit函数)

实例化一个类的对象的过程是一个典型的递归过程

在准备实例化一个类的对象前，首先准备实例化该类的父类，如果该类的父类还有父类，那么准备实例化该类的父类的父类，依次递归直到递归到Object类。此时，首先实例化Object类，再依次对以下各类进行实例化，直到完成对目标类的实例化。具体而言，在实例化每个类时，都遵循如下顺序：先依次执行实例变量初始化和实例代码块初始化，再执行构造函数初始化。也就是说，编译器会将实例变量初始化和实例代码块初始化相关代码放到类的构造函数中去，并且这些代码会被放在对超类构造函数的调用语句之后，构造函数本身的代码之前。

### this逃逸

this逃逸就是说**在构造函数返回之前其他线程就持有该对象的引用，调用尚未构造完全的对象的方法可能引发错误。**



## 区分 准备阶段,初始化阶段,实例化阶段

在类加载过程中，准备阶段是正式为类变量(static 成员变量)分配内存并设置类变量初始值（零值）的阶段，

而初始化阶段是真正开始执行类中定义的java程序代码(字节码)并按程序猿的意图去初始化类变量的过程。更直接地说，初始化阶段就是执行类构造器`<clinit>()`方法的过程。`<clinit>()`方法是由编译器自动收集类中的所有类变量的赋值动作和静态代码块static{}中的语句合并产生的，其中编译器收集的顺序是由语句在源文件中出现的顺序所决定。


初始化阶段完成后,当创建一个对象的时候(new,class.instance,反射的constructor.newinstance,clone,反序列化)会触发对象的实例化`<init>()`


类构造器`<clinit>()`与实例构造器`<init>()`不同，它不需要程序员进行显式调用，虚拟机会保证在子类类构造器`<clinit>`()执行之前，父类的类构造`<clinit>()`执行完毕。由于父类的构造器`<clinit>()`先执行，也就意味着父类中定义的静态代码块/静态变量的初始化要优先于子类的静态代码块/静态变量的初始化执行。

特别地，**类构造器`<clinit>()`对于类或者接口来说并不是必需的**，如果一个类中没有静态代码块，也没有对类变量的赋值操作，那么编译器可以不为这个类生产类构造器`<clinit>()`。此外，在同一个类加载器下，一个类只会被初始化一次，但是一个类可以任意地实例化对象。也就是说，**在一个类的生命周期中，类构造器`<clinit>()`最多会被虚拟机调用一次，而实例构造器`<init>()`则会被虚拟机调用多次，只要程序员还在创建对象。**

### 常见考题

 一个实例变量在对象初始化的过程中会被赋值几次？

　　我们知道，JVM在为一个对象分配完内存之后，会给每一个实例变量赋予默认值，这个时候实例变量被第一次赋值，这个赋值过程是没有办法避免的。如果我们在声明实例变量x的同时对其进行了赋值操作，那么这个时候，这个实例变量就被第二次赋值了。如果我们在实例代码块中，又对变量x做了初始化操作，那么这个时候，这个实例变量就被第三次赋值了。如果我们在构造函数中，也对变量x做了初始化操作，那么这个时候，变量x就被第四次赋值。也就是说，在Java的对象初始化过程中，一个实例变量最多可以被初始化4次。

　2、类的初始化过程与类的实例化过程的异同？

　　函数  clinit init  
    一次   多次  
    静态   实例  
    非必须  必须  

　　3、假如一个类还未加载到内存中，那么在创建一个该类的实例时，具体过程是怎样的？

　　我们知道，要想创建一个类的实例，必须先将该类加载到内存并进行初始化，也就是说，类初始化操作是在类实例化操作之前进行的，**但并不意味着**：**只有类初始化操作结束后才能进行类实例化操作。**

上文提到过的经典案例
```java

public class StaticTest {
    public static void main(String[] args) {
        staticFunction();
    }

    static StaticTest st = new StaticTest();

    static {   //静态代码块
        System.out.println("1");
    }

    {       // 实例代码块
        System.out.println("2");
    }

    StaticTest() {    // 实例构造器
        System.out.println("3");
        System.out.println("a=" + a + ",b=" + b);
    }

    public static void staticFunction() {   // 静态方法
        System.out.println("4");
    }

    int a = 110;    // 实例变量
    static int b = 112;     // 静态变量
}/* Output: 
        2
        3
        a=110,b=0
        1
        4
 *///:~
```
 参考博文: https://blog.csdn.net/justloveyou_/article/details/72466416

## 类加载器

[https://blog.csdn.net/justloveyou_/article/details/72217806](https://blog.csdn.net/justloveyou_/article/details/72217806)

启动（Bootstrap）类加载器：启动类加载器是用本地代码实现的类加载器，它负责将JAVA_HOME/lib下面的核心类库或-Xbootclasspath选项指定的jar包等虚拟机识别的类库加载到内存中。由于启动类加载器涉及到虚拟机本地实现细节，开发者无法直接获取到启动类加载器的引用。具体可由启动类加载器加载到的路径可通过System.getProperty(“sun.boot.class.path”)查看。

扩展（Extension）类加载器：扩展类加载器是由Sun的ExtClassLoader（sun.misc.Launcher$ExtClassLoader）实现的，它负责将JAVA_HOME /lib/ext或者由系统变量-Djava.ext.dir指定位置中的类库加载到内存中。开发者可以直接使用标准扩展类加载器，具体可由扩展类加载器加载到的路径可通过System.getProperty("java.ext.dirs")查看。

系统（System）类加载器：系统类加载器是由 Sun 的 AppClassLoader（sun.misc.Launcher$AppClassLoader）实现的，它负责将用户类路径(java -classpath或-Djava.class.path变量所指的目录，即当前类所在路径及其引用的第三方类库的路径，如第四节中的问题6所述)下的类库加载到内存中。开发者可以直接使用系统类加载器，具体可由系统类加载器加载到的路径可通过System.getProperty("java.class.path")查看。


<div align="center"> <img src=".\pictures\java-vm\Snipaste_2019-09-18_10-52-19.jpg" width="420px"/> </div><br>

### 双亲委派机制
JVM在加载类时默认采用的是双亲委派机制。通俗的讲，就是某个特定的类加载器在接到加载类的请求时，首先将加载任务委托给父类加载器，依次递归 (本质上就是loadClass函数的递归调用)，因此所有的加载请求最终都应该传送到顶层的启动类加载器中。如果父类加载器可以完成这个类加载请求，就成功返回；只有当父类加载器无法完成此加载请求时，子加载器才会尝试自己去加载。

<div align="center"> <img src=".\pictures\java-vm\Snipaste_2019-09-18_13-06-19.jpg" width="420px"/> </div><br>
上面两张图分别是扩展类加载器继承层次图和系统类加载器继承层次图。通过这两张图我们可以看出，扩展类加载器和系统类加载器均是继承自 java.lang.ClassLoader抽象类。我们下面我们就看简要介绍一下抽象类 java.lang.ClassLoader 中几个最重要的方法：

面试题:findclass loadclass defineclass的区别

[https://blog.csdn.net/mollen/article/details/100840940](https://blog.csdn.net/mollen/article/details/100840940)

loadClass()
findLoadedClass(String) 调用这个方法，查看这个Class是否已经别加载

如果没有被加载，继续往下走，查看父类加载器，递归调用loadClass()

如果父类加载器是null，说明是启动类加载器，查找对应的Class

如果都没有找到，就调用findClass(String)

findClass()
根据名称或位置加载.class字节码,然后使用defineClass


definclass()
把字节码转化为Class


```java
//加载指定名称（包括包名）的二进制类型，供用户调用的接口  
public Class<?> loadClass(String name) throws ClassNotFoundException{ … }  
  
//加载指定名称（包括包名）的二进制类型，同时指定是否解析（但是这里的resolve参数不一定真正能达到解析的效果），供继承用  
protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException{ … }  
  
//findClass方法一般被loadClass方法调用去加载指定名称类，供继承用  
protected Class<?> findClass(String name) throws ClassNotFoundException { … }  
  
//定义类型，一般在findClass方法中读取到对应字节码后调用，final的，不能被继承  
//这也从侧面说明：JVM已经实现了对应的具体功能，解析对应的字节码，产生对应的内部数据结构放置到方法区，所以无需覆写，直接调用就可以了）  
protected final Class<?> defineClass(String name, byte[] b, int off, int len) throws ClassFormatError{ … }  

```

标准扩展类加载器和系统类加载器的代码以及其公共父类（java.net.URLClassLoader和java.security.SecureClassLoader）的代码可以看出，都没有覆写java.lang.ClassLoader中默认的加载委派规则 — loadClass（…）方法。既然这样，我们就可以从java.lang.ClassLoader中的loadClass（String name）方法的代码中分析出虚拟机默认采用的双亲委派机制到底是什么模样：

```java
public Class<?> loadClass(String name) throws ClassNotFoundException {  
    return loadClass(name, false);  
}  
  
protected synchronized Class<?> loadClass(String name, boolean resolve)  
        throws ClassNotFoundException {  
  
    // 首先判断该类型是否已经被加载  
    Class c = findLoadedClass(name);  
    if (c == null) {  
        //如果没有被加载，就委托给父类加载或者委派给启动类加载器加载  
        try {  
            if (parent != null) {  
                //如果存在父类加载器，就委派给父类加载器加载  
                c = parent.loadClass(name, false);  
            } else {    // 递归终止条件
                // 由于启动类加载器无法被Java程序直接引用，因此默认用 null 替代
                // parent == null就意味着由启动类加载器尝试加载该类，  
                // 即通过调用 native方法 findBootstrapClass0(String name)加载  
                c = findBootstrapClass0(name);  
            }  
        } catch (ClassNotFoundException e) {  
            // 如果父类加载器不能完成加载请求时，再调用自身的findClass方法进行类加载，若加载成功，findClass方法返回的是defineClass方法的返回值
            // 注意，若自身也加载不了，会产生ClassNotFoundException异常并向上抛出
            c = findClass(name);  
        }  
    }  
    if (resolve) {  
        resolveClass(c);  
    }  
    return c;  
}  

```


### 运行时动态扩展
Java的连接模型允许用户运行时扩展引用程序，既可以通过当前虚拟机中预定义的加载器加载编译时已知的类或者接口，又允许用户自行定义类装载器，在运行时动态扩展用户的程序。通过用户自定义的类装载器，你的程序可以加载在编译时并不知道或者尚未存在的类或者接口，并动态连接它们并进行有选择的解析。运行时动态扩展java应用程序有如下两个途径：

#### 反射 (调用java.lang.Class.forName(…)加载类)

**Class.forName(String name)默认会使用调用类的类加载器来进行类加载。**

```java
//java.lang.Class.java  
publicstatic Class<?> forName(String className) throws ClassNotFoundException {  
    return forName0(className, true, ClassLoader.getCallerClassLoader());  
}  
  
//java.lang.ClassLoader.java  
// Returns the invoker's class loader, or null if none.  
static ClassLoader getCallerClassLoader() {  
    // 获取调用类（caller）的类型  
    Class caller = Reflection.getCallerClass(3);  
    // This can be null if the VM is requesting it  
    if (caller == null) {  
        return null;  
    }  
    // 调用java.lang.Class中本地方法获取加载该调用类（caller）的ClassLoader  
    return caller.getClassLoader0();  
}  
  
//java.lang.Class.java  
//虚拟机本地实现，获取当前类的类加载器，前面介绍的Class的getClassLoader()也使用此方法  
native ClassLoader getClassLoader0(); 

```


**这里需要说明的是多参数版本的forName(…)方法：**

public static Class<?> forName(String name, boolean initialize, ClassLoader loader) throws ClassNotFoundException  


这里的initialize参数是很重要的，它表示在加载同时是否完成初始化的工作（说明：单参数版本的forName方法默认是完成初始化的）。有些场景下需要将initialize设置为true来强制加载同时完成初始化，例如典型的就是加载数据库驱动问题。因为JDBC驱动程序只有被注册后才能被应用程序使用，这就要求驱动程序类必须被初始化，而不单单被加载。



#### 自定义类加载器
用户自定义类加载器
　　
　　通过前面的分析，我们可以看出，除了和本地实现密切相关的启动类加载器之外，包括标准扩展类加载器和系统类加载器在内的所有其他类加载器我们都可以当做自定义类加载器来对待，唯一区别是是否被虚拟机默认使用。前面的内容中已经对java.lang.ClassLoader抽象类中的几个重要的方法做了介绍，这里就简要叙述一下一般用户自定义类加载器的工作流程（可以结合后面问题解答一起看）：

1、首先检查请求的类型是否已经被这个类装载器装载到命名空间中了，如果已经装载，直接返回；否则转入步骤2；

2、委派类加载请求给父类加载器（更准确的说应该是双亲类加载器，真实虚拟机中各种类加载器最终会呈现树状结构），如果父类加载器能够完成，则返回父类加载器加载的Class实例；否则转入步骤3；

3、调用本类加载器的findClass（…）方法，试图获取对应的字节码。如果获取的到，则调用defineClass（…）导入类型到方法区；如果获取不到对应的字节码或者其他原因失败， 向上抛异常给loadClass（…）， loadClass（…）转而调用findClass（…）方法处理异常，直至完成递归调用。

必须指出的是，这里所说的自定义类加载器是指JDK1.2以后版本的写法，即不覆写改变java.lang.loadClass(…)已有委派逻辑情况下。整个加载类的过程如下图：


<div align="center"> <img src=".\pictures\java-vm\Snipaste_2019-09-18_12-47-36.jpg" width="420px"/> </div><br>

### 类加载器常见问题

#### 由不同的类加载器加载的指定类还是相同的类型吗？

在Java中，一个类用其完全匹配类名(fully qualified class name)作为标识，这里指的完全匹配类名包括包名和类名。但在JVM中，一个类用其全名 和 一个ClassLoader的实例作为唯一标识，不同类加载器加载的类将被置于不同的命名空间。我们可以用两个自定义类加载器去加载某自定义类型（注意不要将自定义类型的字节码放置到系统路径或者扩展路径中，否则会被系统类加载器或扩展类加载器抢先加载），然后用获取到的两个Class实例进行java.lang.Object.equals（…）判断，将会得到不相等的结果，如下所示：
```java
public class TestBean {

	public static void main(String[] args) throws Exception {
	    // 一个简单的类加载器，逆向双亲委派机制
	    // 可以加载与自己在同一路径下的Class文件
		ClassLoader myClassLoader = new ClassLoader() {
			@Override
			public Class<?> loadClass(String name)
					throws ClassNotFoundException {
				try {
					String filename = name.substring(name.lastIndexOf(".") + 1)
							+ ".class";
					InputStream is = getClass().getResourceAsStream(filename);
					if (is == null) {
						return super.loadClass(name);   // 递归调用父类加载器
					}
					byte[] b = new byte[is.available()];
					is.read(b);
					return defineClass(name, b, 0, b.length);
				} catch (Exception e) {
					throw new ClassNotFoundException(name);
				}
			}
		};

		Object obj = myClassLoader.loadClass("classloader.test.bean.TestBean")
				.newInstance();
		System.out.println(obj.getClass());
		System.out.println(obj instanceof classloader.test.bean.TestBean);
	}
}/* Output: 
        class classloader.test.bean.TestBean
        false  
 *///:~    
```

我们发现，obj 确实是类classloader.test.bean.TestBean实例化出来的对象，但当这个对象与类classloader.test.bean.TestBean做所属类型检查时却返回了false。这是因为虚拟机中存在了两个TestBean类，一个是由系统类加载器加载的，另一个则是由我们自定义的类加载器加载的，虽然它们来自同一个Class文件，但依然是两个独立的类，因此做所属类型检查时返回false。

#### 在编写自定义类加载器时，如果没有设定父加载器，那么父加载器是谁？


自定义类加载器没有指定父类加载器的情况下，默认的父类加载器即为系统类加载器。同时，我们可以得出如下结论：即使用户自定义类加载器不指定父类加载器，那么，同样可以加载如下三个地方的类：

<Java_Runtime_Home>/lib下的类；  
<Java_Runtime_Home>/lib/ext下或者由系统变量java.ext.dir指定位置中的类；  
当前工程类路径下或者由系统变量java.class.path指定位置中的类。  

#### 在编写自定义类加载器时，如果将父类加载器强制设置为null，那么会有什么影响？如果自定义的类加载器不能加载指定类，就肯定会加载失败吗？
因为启动类加载器的代表就是null;

JVM规范中规定如果用户自定义的类加载器将父类加载器强制设置为null，那么会自动将启动类加载器设置为当前用户自定义类加载器的父类加载器（这个问题前面已经分析过了）。同时，我们可以得出如下结论：即使用户自定义类加载器不指定父类加载器，那么，同样可以加载到<JAVA_HOME>/lib下的类，但此时就不能够加载<JAVA_HOME>/lib/ext目录下的类了。

#### 如何在运行时判断系统类加载器能加载哪些路径下的类？


一是可以直接调用ClassLoader.getSystemClassLoader()或者其他方式获取到系统类加载器（系统类加载器和扩展类加载器本身都派生自URLClassLoader），调用URLClassLoader中的getURLs()方法可以获取到。二是可以直接通过获取系统属性java.class.path来查看当前类路径上的条目信息 ：System.getProperty(“java.class.path”)。如下所示，

```java
public class Test {
	public static void main(String[] args) {
		System.out.println("Rico");
		Gson gson = new Gson();
		System.out.println(gson.getClass().getClassLoader());
		System.out.println(System.getProperty("java.class.path"));
	}
}/* Output: 
        Rico
		sun.misc.Launcher$AppClassLoader@6c68bcef
		I:\AlgorithmPractice\TestClassLoader\bin;I:\Java\jars\Gson\gson-2.3.1.jar
 *///:~ 

```
如上述程序所示，Test类和Gson类由系统类加载器加载，并且其加载路径就是用户类路径，包括当前类路径和引用的第三方类库的路径。


### 再理解双亲委派
在前面介绍类加载器的代理委派模型的时候，提到过类加载器会首先代理给其它类加载器来尝试加载某个类，这就意味着真正完成类的加载工作的类加载器和启动这个加载过程的类加载器，有可能不是同一个。真正完成类的加载工作是通过调用defineClass来实现的；而启动类的加载过程是通过调用loadClass来实现的。前者称为一个类的定义加载器（defining loader），后者称为初始加载器（initiating loader）。在Java虚拟机判断两个类是否相同的时候，使用的是类的定义加载器。也就是说，哪个类加载器启动类的加载过程并不重要，重要的是最终定义这个类的加载器。两种类加载器的关联之处在于：一个类的定义加载器是它引用的其它类的初始加载器。如类 com.example.Outer引用了类 com.example.Inner，则由类 com.example.Outer的定义加载器负责启动类 com.example.Inner的加载过程。

类加载器在成功加载某个类之后，会把得到的 java.lang.Class类的实例缓存起来。下次再请求加载该类的时候，类加载器会直接使用缓存的类的实例，而不会尝试再次加载。也就是说，对于一个类加载器实例来说，相同全名的类只加载一次，即 loadClass方法不会被重复调用。



在绝大多数情况下，系统默认提供的类加载器实现已经可以满足需求。但是在某些情况下，您还是需要为应用开发出自己的类加载器。比如您的应用通过网络来传输Java类的字节代码，为了保证安全性，这些字节代码经过了加密处理。这个时候您就需要自己的类加载器来从某个网络地址上读取加密后的字节代码，接着进行解密和验证，最后定义出要在Java虚拟机中运行的类来。下面将通过两个具体的实例来说明类加载器的开发。

[文件系统类加载器,网络系统类加载器见博客最底部](https://blog.csdn.net/justloveyou_/article/details/72217806)

<div align="center"> <img src="" width="420px"/> </div><br>
<div align="center"> <img src="" width="420px"/> </div><br>

 # 强引用,软引用,弱引用,虚引用

 <div align="center"> <img src=".\pictures\java-vm\Snipaste_2019-08-13_14-42-54.jpg" width="420px"/> </div><br>
 ## 强引用
当内存空间不足时，Java虚拟机宁愿抛出OutOfMemoryError错误，使程序异常终止，也不会靠随意回收具有强引用的对象来解决内存不足的问题。

如果强引用对象不使用时，需要弱化从而使GC能够回收，如下：
```java
    strongReference = null;
```
显式地设置**strongReference对象为null**，或**让其超出对象的生命周期范围**，则gc认为该对象不存在引用，这时就可以回收这个对象。具体什么时候收集这要取决于GC算法。

所谓超出对象生命周期范围:  
在test方法的内部有一个强引用，这个引用保存在Java栈中，而真正的引用内容(Object)保存在Java堆中。 当这个方法运行完成后，就会退出方法栈，则引用对象的引用数为0，这个对象会被回收。

```java
  public void test() {
        Object strongReference = new Object();
        // 省略其他操作
    }
```


但是如果这个strongReference是全局变量时，就需要在不用这个对象时赋值为null，因为强引用不会被垃圾回收。

### 数组的取消强引用

<div align="center"> <img src=".\pictures\java-vm\Snipaste_2019-08-13_14-51-40.jpg" width="420px"/> </div><br>
在ArrayList类中定义了一个elementData数组，在调用clear方法清空数组时，每个数组元素被赋值为null。
不同于elementData=null，强引用仍然存在，避免在后续调用add()等方法添加元素时进行内存的重新分配。
使用如clear()方法内存数组中存放的引用类型进行内存释放特别适用，这样就可以及时释放内存。


 ## 软引用
应用场景: 软引用可用来实现内存敏感的高速缓存。

被软引用关联的对象只有在内存不够的情况下才会被回收。

使用 SoftReference 类来创建软引用。

```java
Object obj = new Object();
SoftReference<Object> sf = new SoftReference<Object>(obj);
obj = null;  // 使对象只被软引用关联
```

应用场景：

浏览器的后退按钮。按后退时，这个后退时显示的网页内容是重新进行请求还是从缓存中取出呢？这就要看具体的实现策略了。

```java
    // 获取浏览器对象进行浏览
    Browser browser = new Browser();
    // 从后台程序加载浏览页面
    BrowserPage page = browser.getPage();
    // 将浏览完毕的页面置为软引用
    SoftReference softReference = new SoftReference(page);

    // 回退或者再次浏览此页面时
    if(softReference.get() != null) {
        // 内存充足，还没有被回收器回收，直接获取缓存
        page = softReference.get();
    } else {
        // 内存不足，软引用的对象已经回收
        page = browser.getPage();
        // 重新构建软引用
        softReference = new SoftReference(page);
    }

```


 ## 弱引用

 被弱引用关联的对象一定会被回收，也就是说它只能存活到下一次垃圾回收发生之前。由于垃圾回收器是一个优先级很低的线程，因此不一定会很快发现那些只具有弱引用的对象。


使用 WeakReference 类来创建弱引用。

```java
Object obj = new Object();
WeakReference<Object> wf = new WeakReference<Object>(obj);
obj = null;
```

下面的代码会让一个弱引用再次变为一个强引用：
通过用一个新的引用来指向 弱引用返回的对象
```java
    String str = new String("abc");
    WeakReference<String> weakReference = new WeakReference<>(str);
    // 弱引用转强引用
    String strongReference = weakReference.get();
```


 ## 虚引用



又称为幽灵引用或者幻影引用，一个对象是否有虚引用的存在，不会对其生存时间造成影响，也无法通过虚引用得到一个对象。

为一个对象设置虚引用的唯一目的是能在这个对象被回收时收到一个系统通知。

使用 PhantomReference 来创建虚引用。

```java
Object obj = new Object();
PhantomReference<Object> pf = new PhantomReference<Object>(obj, null);
obj = null;
```

 ## 引用队列

 当gc（垃圾回收线程）准备回收一个对象时，如果发现它只有软引用(或弱引用，或虚引用)指向它，就会在回收该对象之前，把这个软引用（或弱引用，或虚引用）加入到与之关联的引用队列（ReferenceQueue）中。


当软引用（或弱引用，或虚引用）对象所指向的对象被回收了，那么这个引用对象本身就没有价值了，如果程序中存在大量的这类对象（注意，我们创建的软引用、弱引用、虚引用对象本身是个强引用，不会自动被gc回收），就会浪费内存。因此我们这就可以手动回收位于引用队列中的引用对象本身。

 # java逃逸分析

## JIT:Just-In-Time Compiler，即时编译器
java编译体系中,java源代码转换成可执行的机器代码文件需要经过两步:  
第一步:  
源代码转换成.class文件(字节码)  
第二步  
.class文件被解释成机器码.经过解释执行，其执行速度必然会比可执行的二进制字节码程序慢很多。这就是传统的JVM的解释器（Interpreter）的功能。为了解决这种效率问题，引入了 JIT（即时编译） 技术。

引入了 JIT 技术后，Java程序还是通过解释器进行解释执行，当JVM发现某个方法或代码块运行特别频繁的时候，就会认为这是“热点代码”（Hot Spot Code)。然后JIT会把部分“热点代码”翻译成本地机器相关的机器码，并进行优化，然后再把翻译后的机器码缓存起来，以备下次使用。

JIT可以进行编译,热点检测和优化,其中优化最重要的一个就是逃逸分析。
## 逃逸分析定义

是一种可以有效减少Java 程序中同步负载和内存堆分配压力的跨函数全局数据流分析算法。通过逃逸分析，Java Hotspot编译器能够分析出一个新的对象的引用的使用范围从而决定是否进行同步省略,标量替换,栈上引用等优化方法

逃逸分析的基本行为就是分析对象动态作用域：当一个对象在方法中被定义后，它可能被外部方法所引用，例如作为调用参数传递到其他地方中，称为方法逃逸。

```java
public static StringBuffer craeteStringBuffer(String s1, String s2) {
    StringBuffer sb = new StringBuffer();
    sb.append(s1);
    sb.append(s2);
    return sb;
}

public static String createStringBuffer(String s1, String s2) {
    StringBuffer sb = new StringBuffer();
    sb.append(s1);
    sb.append(s2);
    return sb.toString();
}
```
第一段代码中的sb就逃逸了，而第二段代码中的sb就没有逃逸。因为第一段把sb传给了外面,导致外面也可以使用.

使用逃逸分析，编译器可以对代码做如下优化：

一、同步省略。如果一个对象被发现只能从一个线程被访问到，那么对于这个对象的操作可以不考虑同步。

二、将堆分配转化为栈分配。如果一个对象在子程序中被分配，要使指向该对象的指针永远不会逃逸，对象可能是栈分配的候选，而不是堆分配。

三、分离对象或标量替换。有的对象可能不需要作为一个连续的内存结构存在也可以被访问到，那么对象的部分（或全部）可以不存储在内存，而是存储在CPU寄存器中。

在Java代码运行时，通过JVM参数可指定是否开启逃逸分析，

-XX:+DoEscapeAnalysis ： 表示开启逃逸分析

-XX:-DoEscapeAnalysis ： 表示关闭逃逸分析 从jdk 1.7开始已经默认开始逃逸分析，如需关闭，需要指定-XX:-DoEscapeAnalysis


## 同步省略

在动态编译同步块的时候，JIT编译器可以借助逃逸分析来判断同步块所使用的锁对象是否只能够被一个线程访问而没有被发布到其他线程。

如果同步块所使用的锁对象通过这种分析被证实只能够被一个线程访问，那么JIT编译器在编译这个同步块的时候就会取消对这部分代码的同步。这个取消同步的过程就叫同步省略，也叫锁消除。

```java
public void f() {
    Object hollis = new Object();
    synchronized(hollis) {
        System.out.println(hollis);
    }
}
```
代码中对hollis这个对象进行加锁，但是hollis对象的生命周期只在f()方法中，并不会被其他线程所访问到，所以在JIT编译阶段就会被优化掉。优化成：

```java
public void f() {
    Object hollis = new Object();
    System.out.println(hollis);
}
```
## 标量替换
标量（Scalar）是指一个无法再分解成更小的数据的数据。Java中的原始数据类型就是标量。相对的，那些还可以分解的数据叫做聚合量（Aggregate），Java中的对象就是聚合量，因为他可以分解成其他聚合量和标量。

在JIT阶段，如果经过逃逸分析，发现一个对象不会被外界访问的话，那么经过JIT优化，就会把这个对象拆解成若干个其中包含的若干个成员变量来代替。这个过程就是标量替换。

```java
public static void main(String[] args) {
   alloc();
}

private static void alloc() {
   Point point = new Point（1,2）;
   System.out.println("point.x="+point.x+"; point.y="+point.y);
}
class Point{
    private int x;
    private int y;
}
```
以上代码中，point对象并没有逃逸出alloc方法，并且point对象是可以拆解成标量的。那么，JIT就会不会直接创建Point对象，而是直接使用两个标量int x ，int y来替代Point对象。

以上代码，经过标量替换后，就会变成
```java
private static void alloc() {
   int x = 1;
   int y = 2;
   System.out.println("point.x="+x+"; point.y="+y);
}
```
可以看到，Point这个聚合量经过逃逸分析后，发现他并没有逃逸，就被替换成两个聚合量了。那么标量替换有什么好处呢？就是可以大大减少堆内存的占用。因为一旦不需要创建对象了，那么就不再需要分配堆内存了。

标量替换为栈上分配提供了很好的基础。

## 栈上分配

在Java虚拟机中，对象是在Java堆中分配内存的，这是一个普遍的常识。但是，有一种特殊情况，那就是如果经过逃逸分析后发现，一个对象并没有逃逸出方法的话，那么就可能被优化成栈上分配。这样就无需在堆上分配内存，也无须进行垃圾回收了。



其实在现有的虚拟机中，并没有真正的实现栈上分配，对象没有在堆上分配其实是标量替换实现的。

[java逃逸分析参考博文](https://zhuanlan.zhihu.com/p/39604934)

[栈上分配实验博文](http://www.hollischuang.com/archives/2398)

# JVM调优  博文很好,有时间提炼

[参考博文](https://blog.csdn.net/RickyIT/article/details/53895060)

Xms 是指设定程序启动时占用内存大小。一般来讲，大点，程序会启动的快一点，但是也可能会导致机器暂时间变慢。

Xmx 是指设定程序运行期间最大可占用的内存大小。如果程序运行需要占用更多的内存，超出了这个设置值，就会抛出OutOfMemory异常。

Xss 是指设定每个线程的堆栈大小。这个就要依据你的程序，看一个线程大约需要占用多少内存，可能会有多少线程同时运行等。

-Xmn2g ：设置年轻代大小为2G。整个堆大小=年轻代大小 + 年老代大小 + 持久代大小 。持久代一般固定大小为64m，所以增大年轻代后，将会减小年老代大小。此值对系统性能影响较大，Sun官方推荐配置为整个堆的3/8。

# Tomcat调优