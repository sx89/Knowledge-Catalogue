
<!-- TOC -->

- [数据类型](#数据类型)
    - [基本数据类型](#基本数据类型)
    - [拆箱与装箱](#拆箱与装箱)
    - [常量池](#常量池)
        - [定义](#定义)
        - [好处](#好处)
    - [包装类与常量池](#包装类与常量池)
        - [定义](#定义-1)
        - [场景](#场景)
        - [拓展](#拓展)
- [二、String](#二string)
    - [概览](#概览)
    - [不可变的好处](#不可变的好处)
    - [String ,StringBuffer,StringBuilder的区别](#string-stringbufferstringbuilder的区别)
        - [StrinBuffer和StringBuilder的常用方法](#strinbuffer和stringbuilder的常用方法)
    - [引用类型](#引用类型)
- [== 与 equal](#-与-equal)
- [泛型](#泛型)

<!-- /TOC -->
# 数据类型

## 基本数据类型

short 16位  
int 32位 4字节    
long 64位  

float 32位  
double 64位  

char 16位  
byte 8位  
boolean 1位  

boolean 只有两个值：true、false，可以使用 1 bit 来存储，但是具体大小没有明确规定。JVM 会在编译时期将 boolean 类型的数据转换为 int，使用 1 来表示 true，0 表示 false。JVM 支持 boolean 数组，但是是通过读写 byte 数组来实现的。


例如byte类型是1byte也就是8位，可以表示的数字是-128到127，因为还有一个0，加起来一共是256，也就是2的八次方。

##  拆箱与装箱

基本数据类型与它的包装类之间的相互转换

在一个类里面,基本数据类型的属性尽量使用包装类.


```java
Integer x = 2;     // 装箱
int y = x;         // 拆箱
```

## 常量池
### 定义
常量池分为静态常量池和运行时常量池;  
静态常量池是.class文件的常量池,包含字符串和类方法信息;  
运行时常量池是jvm虚拟机在完成类装载操作后，将class文件中的常量池载入到内存中，并保存在**方法区**中，我们常说的常量池，就是指方法区中的运行时常量池。

### 好处
避免频繁的创建和销毁对象而影响系统性能，其实现了对象的共享。
例如字符串常量池，在编译阶段就把所有的字符串文字放到一个常量池中。
（1）节省内存空间：常量池中所有相同的字符串常量被合并，只占用一个空间。
（2）节省运行时间：比较字符串时，==比equals()快。对于两个引用变量，只用==判断引用是否相等，也就可以判断实际值是否相等。

## 包装类与常量池
### 定义
Byte,Short,Integer,Long,Character,Boolean的[-128,127]都实现了常量池技术,两种浮点类型float,double没有实现,因为在任何区间内都有无限个数值.

### 场景

new Integer(123) 与 Integer.valueOf(123) 的区别在于：

- new Integer(123) 每次都会新建一个对象；
- Integer.valueOf(123) 会使用缓存池中的对象，多次调用会取得同一个对象的引用。

```java
Integer x = new Integer(123);
Integer y = new Integer(123);
System.out.println(x == y);    // false
Integer z = Integer.valueOf(123);
Integer k = Integer.valueOf(123);
System.out.println(z == k);   // true
```

valueOf() 方法的实现比较简单，就是先判断值是否在缓存池中，如果在的话就直接返回缓存池的内容。

```java
public static Integer valueOf(int i) {
    if (i >= IntegerCache.low && i <= IntegerCache.high)
        return IntegerCache.cache[i + (-IntegerCache.low)];
    return new Integer(i);
}
```
### 拓展

在 jdk 1.8 所有的数值类缓冲池中，Integer 的缓冲池 IntegerCache 很特殊，这个缓冲池的下界是 - 128，上界默认是 127，但是这个上界是可调的，在启动 jvm 的时候，通过 -XX:AutoBoxCacheMax=&lt;size&gt; 来指定这个缓冲池的大小，该选项在 JVM 初始化的时候会设定一个名为 java.lang.IntegerCache.high 系统属性，然后 IntegerCache 初始化的时候就会读取该系统属性来决定上界。


# 二、String

## 概览

String 被声明为 final，因此它不可被继承。

在 Java 8 中，String 内部使用 char 数组存储数据。

```java
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence {
    /** The value is used for character storage. */
    private final char value[];
}
```

在 Java 9 之后，String 类的实现改用 byte 数组存储字符串，同时使用 `coder` 来标识使用了哪种编码。

```java
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence {
    /** The value is used for character storage. */
    private final byte[] value;

    /** The identifier of the encoding used to encode the bytes in {@code value}. */
    private final byte coder;
}
```

value 数组被 1. 声明为 final，这意味着 value 数组初始化之后就不能再引用其它数组。2. 并且 String 内部没有改变 value 数组的方法，3. 而且为了防止value内部被修改,还是用了private来防止value的内容被修改,因此可以保证 String 不可变。



## 不可变的好处

**1. 可以缓存 hash 值** 

因为 String 的 hash 值经常被使用，例如 String 用做 HashMap 的 key。不可变的特性可以使得 hash 值也不可变，因此只需要进行一次计算。

**2. String Pool 的需要** 

如果一个 String 对象已经被创建过了，那么就会从 String Pool 中取得引用。只有 String 是不可变的，才可能使用 String Pool。


<div align="center"> <img src="pics/9112288f-23f5-4e53-b222-a46fdbca1603.png" width="300px"> </div><br>

**3. 安全性** 

String 经常作为参数，String 不可变性可以保证参数不可变。例如在作为网络连接参数的情况下如果 String 是可变的，那么在网络连接过程中，String 被改变，改变 String 对象的那一方以为现在连接的是其它主机，而实际情况却不一定是。

**4. 线程安全** 

String 不可变性天生具备线程安全，可以在多个线程中安全地使用。

[Program Creek : Why String is immutable in Java?](https://www.programcreek.com/2013/04/why-string-is-immutable-in-java/)




## String ,StringBuffer,StringBuilder的区别

**1. 可变性** 

- String 不可变,任何对String的改变都 会引发新的String对象的生成
- StringBuffer 和 StringBuilder 可变;任何对它所指代的字符串的改变都不会产生新的对象。

**2. 线程安全** 

- String 不可变，因此是线程安全的
- StringBuilder 不是线程安全的,单线程效率更高
- StringBuffer 是线程安全的，内部使用 synchronized 进行同步


在字符串操作频繁的情况下,String的效率远低于其他两个
```
代码:StringTest.java
```

### StrinBuffer和StringBuilder的常用方法
都继承自AbstractStringBuilder,主要的存储是 chars[] value,和 capicity

StringBuffer s = new StringBuffer();  
capicity=16

StringBuffer sb2=new StringBuffer(“how are you?”)  
初始化的时候,capicity=传入字符串的长度+16 即为内部chars[] value的长度

扩容大小 尝试将新容量扩为大小变成2倍+2
## 引用类型
在java里面除去基本数据类型的其它类型都是引用数据类型，自己定义的class类都是引用类型，可以像基本类型一样使用。

基本数据类型在往方法传值的时候,传递的是值

引用类型在往方法传值的时候,传递的是引用地址(String类型,类类型都是引用类型)
# == 与 equal

==比较的是引用的内存地址

equal看类的equal实现情况

String,Integer,Double等基本类型的包装类实现了equal方法,比较的是内容(先比较地址,若地址相同则返回true,否则比较内容)

StringBuilder,StringBuffer没有实现equal方法,比较的仍然是地址

# 泛型

