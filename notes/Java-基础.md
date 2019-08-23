
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
- [String](#string)
    - [概览](#概览)
    - [不可变的好处](#不可变的好处)
    - [构造函数](#构造函数)
- [String ,StringBuffer,StringBuilder的区别](#string-stringbufferstringbuilder的区别)
    - [StrinBuffer和StringBuilder的常用方法](#strinbuffer和stringbuilder的常用方法)
    - [String Pool](#string-pool)
    - [new String("abc")创建两个对象](#new-stringabc创建两个对象)
- [引用类型与传值传址](#引用类型与传值传址)
- [面向对象三大特性](#面向对象三大特性)
    - [继承](#继承)
    - [封装](#封装)
    - [多态](#多态)
        - [三种实现方式](#三种实现方式)
        - [表现形式](#表现形式)
        - [多态的实现原理](#多态的实现原理)
    - [接口](#接口)
    - [抽象类](#抽象类)
        - [定义](#定义-2)
        - [与普通类,接口的区别](#与普通类接口的区别)
        - [与接口的区别](#与接口的区别)
        - [java只有单继承,接口却可以多实现的原因](#java只有单继承接口却可以多实现的原因)
- [父子类构造器的调用顺序](#父子类构造器的调用顺序)
    - [默认调无参](#默认调无参)
    - [有参用super](#有参用super)
    - [基类private属性](#基类private属性)
    - [派生类的覆盖](#派生类的覆盖)
- [重写与重载](#重写与重载)
    - [重写(Override)](#重写override)
        - [定义](#定义-3)
        - [场景](#场景-1)
        - [调用父类被重写的方法](#调用父类被重写的方法)
    - [重写的向上向下转型](#重写的向上向下转型)
        - [向上转型(子类向上转成父类来用)](#向上转型子类向上转成父类来用)
        - [向下转型](#向下转型)
    - [静态分派和动态分派](#静态分派和动态分派)
    - [重载(Overload)](#重载overload)
        - [定义](#定义-4)
        - [重载规则:](#重载规则)
        - [重载和重写的区别](#重载和重写的区别)
- [Object 通用方法](#object-通用方法)
    - [概览](#概览-1)
- [== 与 equal](#-与-equal)
    - [hashCode()](#hashcode)
    - [toString()](#tostring)
    - [clone()](#clone)
- [关键字](#关键字)
    - [final](#final)
    - [static](#static)
- [反射](#反射)
    - [定义](#定义-5)
    - [获取Class对象](#获取class对象)
    - [创建实例](#创建实例)
    - [使用反射获取类信息](#使用反射获取类信息)
        - [使用反射获取变量信息](#使用反射获取变量信息)
        - [使用反射获取方法信息](#使用反射获取方法信息)

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


# String

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

## 构造函数
以下是 String 构造函数的源码，可以看到，在将一个字符串对象作为另一个字符串对象的构造函数参数时，并不会完全复制 value 数组内容，而是都会指向同一个 value 数组。

```java
public String(String original) {
    this.value = original.value;
    this.hash = original.hash;
}
```


# String ,StringBuffer,StringBuilder的区别

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

## StrinBuffer和StringBuilder的常用方法
都继承自AbstractStringBuilder,主要的存储是 chars[] value,和 capicity

StringBuffer s = new StringBuffer();  
capicity=16

StringBuffer sb2=new StringBuffer(“how are you?”)  
初始化的时候,capicity=传入字符串的长度+16 即为内部chars[] value的长度

扩容大小 尝试将新容量扩为大小变成2倍+2

## String Pool

字符串常量池（String Pool）保存着所有字符串字面量（literal strings），这些字面量在编译时期就确定。不仅如此，还可以使用 String 的 intern() 方法在运行过程中将字符串添加到 String Pool 中。

当一个字符串调用 intern() 方法时，如果 String Pool 中已经存在一个字符串和该字符串值相等（使用 equals() 方法进行确定），那么就会返回 String Pool 中字符串的引用；否则，就会在 String Pool 中添加一个新的字符串，并返回这个新字符串的引用。

下面示例中，s1 和 s2 采用 new String() 的方式新建了两个不同字符串，而 s3 和 s4 是通过 s1.intern() 方法取得一个字符串引用。intern() 首先把 s1 引用的字符串放到 String Pool 中，然后返回这个字符串引用。因此 s3 和 s4 引用的是同一个字符串。

```java
String s1 = new String("aaa");
String s2 = new String("aaa");
System.out.println(s1 == s2);           // false
String s3 = s1.intern();
String s4 = s1.intern();
System.out.println(s3 == s4);           // true
```

如果是采用 "bbb" 这种字面量的形式创建字符串，会自动地将字符串放入 String Pool 中。

```java
String s5 = "bbb";
String s6 = "bbb";
System.out.println(s5 == s6);  // true
String str1 = "abcd";
String str2 = new String("abcd");
System.out.println(str1==str2);//false
```

在 Java 7 之前，String Pool 被放在运行时常量池中，它属于永久代。而在 Java 7，String Pool 被移到堆中。这是因为永久代的空间有限，在大量使用字符串的场景下会导致 OutOfMemoryError 错误。

- [StackOverflow : What is String interning?](https://stackoverflow.com/questions/10578984/what-is-string-interning)
- [深入解析 String#intern](https://tech.meituan.com/in_depth_understanding_string_intern.html)

## new String("abc")创建两个对象

使用这种方式一共会创建两个字符串对象（前提是 String Pool 中还没有 "abc" 字符串对象）。

- "abc" 属于字符串字面量，因此编译时期会在 String Pool 中创建一个字符串对象，指向这个 "abc" 字符串字面量；
- 而使用 new 的方式会在堆中创建一个字符串对象。

创建一个测试类，其 main 方法中使用这种方式来创建字符串对象。

```java
public class NewStringTest {
    public static void main(String[] args) {
        String s = new String("abc");
    }
}
```

使用 javap -verbose 进行反编译，得到以下内容：

```java
// ...
Constant pool:
// ...
   #2 = Class              #18            // java/lang/String
   #3 = String             #19            // abc
// ...
  #18 = Utf8               java/lang/String
  #19 = Utf8               abc
// ...

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=3, locals=2, args_size=1
         0: new           #2                  // class java/lang/String
         3: dup
         4: ldc           #3                  // String abc
         6: invokespecial #4                  // Method java/lang/String."<init>":(Ljava/lang/String;)V
         9: astore_1
// ...
```

在 Constant Pool 中，#19 存储这字符串字面量 "abc"，#3 是 String Pool 的字符串对象，它指向 #19 这个字符串字面量。在 main 方法中，0: 行使用 new #2 在堆中创建一个字符串对象，并且使用 ldc #3 将 String Pool 中的字符串对象作为 String 构造函数的参数。


# 引用类型与传值传址
在java里面除基本数据类型的其它类型都是引用数据类型，自己定义的class类都是引用类型

基本数据类型在往方法传值的时候,传递的是值

引用类型在往方法传值的时候,传递的是引用地址(String类型,类类型都是引用类型)


# 面向对象三大特性

继承,封装,多态

## 继承
Java中的继承只能单继承，但是可以通过内部类继承其他类来实现多继承。
```java
    public class Son extends Father {
        public void go() {
            System.out.println("son go");
        }

        public void eat() {
            System.out.println("son eat");
        }

        public void sleep() {
            System.out.println("zzzzzz");
        }

        public void cook() {
//匿名内部类实现的多继承
            new Mother().cook();
//内部类继承第二个父类来实现多继承
            Mom mom = new Mom();
            mom.cook();
        }

        private class Mom extends Mother {
            @Override
            public void cook() {
                System.out.println("mom cook");
            }
        }
    }
```


## 封装
用访问修饰符来保证类内部的数据不会被外部随意访问修改

```java
default (即默认，什么也不写）: 在同一包内可见，不使用任何修饰符。使用对象：类、接口、变量、方法。

private : 在同一类内可见。使用对象：变量、方法。 注意：不能修饰类（外部类）

public : 对所有类可见。使用对象：类、接口、变量、方法

protected : 对同一包内的类和所有子类可见。使用对象：变量、方法。 注意：不能修饰类（外部类）。
```


## 多态
同一个方法或者类具有不同的功能或者表现形式

### 三种实现方式
1. 重写与重载

从jvm实现的角度来看，重写又叫运行时多态，编译时看不出子类调用的是哪个方法，但是运行时操作数栈会先根据子类的引用去子类的类信息中查找方法，找不到的话再到父类的类信息中查找方法。

而重载则是编译时多态，因为编译期就可以确定传入的参数组合，决定调用的具体方法是哪一个了。

2. 接口


3. 抽象类与抽象方法

### 表现形式
对象上 向上转型与向下转型

方法上 重写与重载
### 多态的实现原理



## 接口
接口是抽象类的延伸，在 Java 8 之前，它可以看成是一个完全抽象的类，也就是说它不能有任何的方法实现。

从 Java 8 开始，接口也可以拥有默认的方法实现，这是因为不支持默认方法的接口的维护成本太高了。在 Java 8 之前，如果一个接口想要添加新的方法，那么要修改所有实现了该接口的类。

接口的成员（字段 + 方法）默认都是 public 的，并且不允许定义为 private 或者 protected。

接口的字段默认都是 static 和 final 的。



## 抽象类
### 定义
abstract class的核心在于，我知道一类物体的部分行为（和属性），但是不清楚另一部分的行为（和属性），所以我不能自己实例化。
### 与普通类,接口的区别
抽象类和普通类最大的区别是，抽象类不能被实例化，需要继承抽象类才能实例化其子类。

### 与接口的区别
- 抽象类与接口的区别是,抽象类更看重类之间的继承关系,父类某些方法要被子类继承,另外一些特定方法由子类具体实现;接口不是父子继承关系,而是满足某个标准所以要实现什么样的功能;抽象类是针对父子继承的抽象,接口是针对标准满足的抽象;
- 从使用上来看，一个类可以实现多个接口，但是不能继承多个抽象类。
- 接口的字段只能是 static 和 final 类型的，而抽象类的字段没有这种限制。
- 接口的成员只能是 public 的，而抽象类的成员可以有多种访问权限。

### java只有单继承,接口却可以多实现的原因

举个例子，在这里有个A类，我们又编写了两个类B类和C类，并且B类和C类分别继承了A类，并且对A类的同一个方法进行了覆盖。如果此时我们再次编写了一个D类，并且D类以多继承的方式同时集成了B类和C类，那么D类也会继承B类和C类从A类中重载的方法，那么问题来了，D类也开始犯迷糊了，我到底应该哪个继承哪个类中的方法呢，因为类是结构性的，这样就会造成结构上的混乱。这就是多继承的菱形继承问题。

因为接口只有抽象方法，具体方法只能由实现接口的类实现，在调用的时候始终只会调用实现类（也就是子类覆盖的方法）的方法（不存在歧义），因此即使继承的两个接口中的方法名是一样的，最终调用的时候也都是调用实现类中的那个方法，不会缠身歧义；而又因为接口只有静态的常量，但是由于静态变量是在编译期决定调用关系的，即使存在一定的冲突也会在编译时提示出错；而引用静态变量一般直接使用类名或接口名，从而避免产生歧义，因此从接口的变量是来看也是可以通过的。 

# 父子类构造器的调用顺序
## 默认调无参
派生类的构造器会必须调用基类的构造器,如果不指定,则默认调用无参构造器(所以基类中推荐要有无参构造器)

```java
public class test {

    public static void main(String[] args) {
        new Student();
    }
}

class Humans {
    public Humans() {
        System.out.println("我是人");
    }
}

class Student extends Humans{
    public Student() {
        System.out.println("我是学生");
    }
}
```
输出为

```
我是人
我是学生
```
## 有参用super
构造器有参数时，那就必须使用关键字super现实地编写调用基类构造器的代码，并且匹配适当的参数列表。(要注意的是，super必须在构造器的最前面，不然会报错。)

```java 


    public static void main(String[] args) {
        new Student("sx");
    }
}

class Humans {
    public Humans() {
        System.out.println("我是人");
    }

    public Humans(String name){
        System.out.println("我是叫"+name+"的人");
    }
}

class Student extends Humans{
    public Student() {
        System.out.println("我是学生");
    }

    Student(String name){
        super(name);
        System.out.println("我是叫"+name+"的学生!");
    }

```
输出结果为:

```
我是叫sx的人
我是叫sx的学生!
```
**如果注释掉上面的super(name);将会报错。原因是派生类必须调用基类构造器。因为实例化派生类时，基类也会被实例化，如果不调用基类的有参构造器，基类又没有可以给派生类使用的默认无参构造器,基类将不会被实例化，所以派生类没有调用基类构造器会报错。**

**原因是，如果基类有一个无参的构造器，就算派生类不用super显示调用基类的构造函数，编译器也会自动去调用基类的无参构造函数。**

## 基类private属性
**如果派生类使用了访问修饰符,则派生类要想访问基类里面被private和default修饰的属性,需要用getter和setter**

```java
public class Humans {
	
	public String sex;
	
	protected int age ;
	
	private String name;


	Humans(String sex,String name,int age){
		this.sex = sex;
		this.name = name;
		this.age = age;
	}

    public String getName() {
    	return name;
    }

    public void setName(String name) {
    	this.name = name;
    }

}


public class Student extends Humans{
	
	Student(String sex ,String name,int age){
		super(sex,name,age);
	}
}


public class test {
	public static void main(String args[]){
		Student s = new Student("男","zhangsan",10);
		System.out.println(s.sex);
		System.out.println(s.name);
		System.out.println(s.age);
	}
}


```
 上面的System.out.println(s.name);会报错，因为name是private属性，如需访问，采用get方法：

```java
System.out.println(s.getName());  


输出结果为：
男
zhangsan
10 
```
## 派生类的覆盖
如果派生类定义了和基类一样的属性或方法，将覆盖基类的属性和方法。

如将student改为如下代码：
```java
public class Student extends Humans{
	
	public String sex;
	
	protected int age ;
	
	private String name;
	
	Student(String sex ,String name,int age){
		super(sex,name,age);
	}
	
    public String getName() {
    	return name;
    }

    public void setName(String name) {
    	this.name = name;
    }
}
```
输出结果为:

```
输出结果为：

null

null

0
```
因为只有基类的属性在构造时赋值了，派生类的没有，当访问这些属性时，访问的是派生类的属性，所以全为null或者0。
# 重写与重载
## 重写(Override) 
### 定义 
重写是子类对父类的允许访问的方法的实现过程进行重新编写, 返回值和形参都不能改变

### 场景
使用 @Override 注解，可以让编译器帮忙检查是否满足上面的三个限制条件。

- 子类方法的访问权限必须大于等于父类方法；
- 子类方法的返回类型必须是父类方法返回类型或为其子类型。
- 子类方法抛出的异常类型必须是父类抛出异常类型或为其子类型。。

### 调用父类被重写的方法

当需要在子类中调用父类的被重写方法时，要使用 super 关键字。

```java
class Animal{
   public void move(){
      System.out.println("动物可以移动");
   }
}
 
class Dog extends Animal{
   public void move(){
      super.move(); // 应用super类的方法
      System.out.println("狗可以跑和走");
   }
}
 
public class TestDog{
   public static void main(String args[]){
 
      Animal b = new Dog(); // Dog 对象
      b.move(); //执行 Dog类的方法
 
   }
}

```


## 重写的向上向下转型
### 向上转型(子类向上转成父类来用)

子类以丢失掉父类中没有的方法为代价,但使用的是子类重写的方法,变成一个父类的实例来用

```java
package com.sheepmu;
 class Animal
 {
	public void eat()
	{
		System.out.println("父类的 eating...");
	}
}
class Bird extends Animal
{	
	@Override
	public void eat()
	{
		System.out.println("子类重写的父类的  eatting...");
	}	
	public void fly()
	{
		System.out.println("子类新方法  flying...");
	}
}

public class Sys
{
	public static void main(String[] args) 
	{
		Animal b=new Bird(); //向上转型
		b.eat(); 
		//  b.fly(); b虽指向子类对象，但此时子类作为向上的代价丢失和父类不同的fly()方法
	
	}
}

```

输出:
```
子类重写的父类的  eatting...


Animal b=new Bird(); //向上转型  
b.eat(); // 调用的是子类的eat()方法  
b.fly(); // 报错!!!!!-------b虽指向子类对象，但此时子类作为向上转型的代价丢失和父类不同的fly()方法------
```


向上转型的好处:为何不直接Bird b=new Bird();b.eat() 呢？

降低了代码的可扩展性.若是不用向上转型，那么有多少个子类就得在这儿写多少种不同的Bird,Dog,Cat...的加工方法

### 向下转型

```java
package com.sheepmu;
 class Fruit
  {
	public void myName()
	{
		System.out.println("我是父类  水果...");
	}
}
 
class Apple extends Fruit
{ 
	@Override
	public void myName() 
	{ 
		System.out.println("我是子类  苹果...");
	}
	public void myMore()
	{
		System.out.println("我是你的小呀小苹果~~~~~~");
	}
}
 
public class Sys{ 
	public static void main(String[] args) { 
		Fruit a=new Apple(); //向上转型
		a.myName();
		
		Apple aa=(Apple)a; //向下转型,编译和运行皆不会出错(正确的)
		aa.myName();//向下转型时调用的是子类的
		aa.myMore();;
		  
		Fruit f=new Fruit();
        Apple aaa=(Apple)f; //-不安全的---向下转型,编译无错但会运行会出错
  		aaa.myName();
  		aaa.myMore(); 
	}
}


输出:
我是子类  苹果...
我是子类  苹果...
我是你的小呀小苹果~~~~~~
Exception in thread "main" java.lang.ClassCastException: com.sheepmu.Fruit cannot be cast to com.sheepmu.Apple
at com.sheepmu.Sys.main(Sys.java:30)


详解：

1.正确的向下转型    

                    Fruit a=new Apple(); //向上转型
                    a.myName(); 
                    Apple aa=(Apple)a; //向下转型,编译和运行皆不会出错(正确的)
                    aa.myName();
                    aa.myMore();
a指向子类的对象，所以子类的实例aa也可以指向a啊~~

向下转型后因为都是指向子类对象，所以调用的当然全是子类的方法~~

2.不安全的向下转型

               Fruit f=new Fruit();
               Apple aaa=(Apple)f; //-不安全的---向下转型,编译无错但会运行会出错
               aaa.myName();
                aaa.myMore(); 

f是父类对象，子类的实例aaa指向强转的父类,在编译时不报错,但运行时因为父类没有子类的方法,会导致aaa调用子类的方法出错

3.Java为了解决不安全的向下转型问题，引入泛型的概念

4.为了安全的类型转换，最好先用 if(A instanceof  B) 判断一下下~~

``` 
## 静态分派和动态分派

TODO



## 重载(Overload)
### 定义

重载(overloading) 是在一个类里面，方法名字相同，而参数个数,顺序,类型至少一个不同不同。

返回值不同不算重载

### 重载规则:

被重载的方法必须改变参数列表(参数个数或类型或顺序不一样)；

被重载的方法可以改变返回类型；

被重载的方法可以改变访问修饰符；

被重载的方法可以声明新的或更广的检查异常；

方法能够在同一个类中或者在一个子类中被重载。

无法以返回值类型作为重载函数的区分标准。

### 重载和重写的区别
方法重载是一个类的多态性表现,而方法重写是子类与父类的一种多态性表现。

另外还有 返回值,修饰符,抛出异常,参数列表方面的不同:

<div align="center"> <img src="pictures\java-basic\Snipaste_2019-08-11_19-55-46.jpg" width="600"/> </div><br>

# Object 通用方法

## 概览

```java

public native int hashCode()

public boolean equals(Object obj)

protected native Object clone() throws CloneNotSupportedException

public String toString()

public final native Class<?> getClass()

protected void finalize() throws Throwable {}

public final native void notify()

public final native void notifyAll()

public final native void wait(long timeout) throws InterruptedException

public final void wait(long timeout, int nanos) throws InterruptedException

public final void wait() throws InterruptedException
```
# == 与 equal

==比较的是引用的内存地址

equal看类的equal实现情况

- 对于基本类型，== 判断两个值是否相等，基本类型没有 equals() 方法。

- Integer,Double等基本类型的包装类,==判断引用地址,equal方法比较的是先比较地址,若地址相同则返回true,否则比较内容

- String ==比较地址,equal方法比较的是先比较地址,若地址相同则返回true,否则比较内容

- StringBuilder,StringBuffer==比较地址,没有实现equal方法,继承自Object的equals,所以比较的仍然是地址
```java
  public boolean equals(Object obj) {
        return (this == obj);
    }
```

## hashCode()

hashCode() 返回散列值，而 equals() 是用来判断两个对象是否等价。等价的两个对象散列值一定相同，但是散列值相同的两个对象不一定等价。

在覆盖 equals() 方法时应当总是覆盖 hashCode() 方法，保证等价的两个对象散列值也相等。

下面的代码中，新建了两个等价的对象，并将它们添加到 HashSet 中。我们希望将这两个对象当成一样的，只在集合中添加一个对象，但是因为 EqualExample 没有实现 hashCode() 方法，因此这两个对象的散列值是不同的，最终导致集合添加了两个等价的对象。

```java
EqualExample e1 = new EqualExample(1, 1, 1);
EqualExample e2 = new EqualExample(1, 1, 1);
System.out.println(e1.equals(e2)); // true
HashSet<EqualExample> set = new HashSet<>();
set.add(e1);
set.add(e2);
System.out.println(set.size());   // 2
```

理想的散列函数应当具有均匀性，即不相等的对象应当均匀分布到所有可能的散列值上。这就要求了散列函数要把所有域的值都考虑进来。可以将每个域都当成 R 进制的某一位，然后组成一个 R 进制的整数。R 一般取 31，因为它是一个奇素数，如果是偶数的话，当出现乘法溢出，信息就会丢失，因为与 2 相乘相当于向左移一位。

一个数与 31 相乘可以转换成移位和减法：`31*x == (x<<5)-x`，编译器会自动进行这个优化。

```java
@Override
public int hashCode() {
    int result = 17;
    result = 31 * result + x;
    result = 31 * result + y;
    result = 31 * result + z;
    return result;
}
```


## toString()

默认返回 ToStringExample@4554617c 这种形式，其中 @ 后面的数值为散列码的无符号十六进制表示。

```java
public class ToStringExample {

    private int number;

    public ToStringExample(int number) {
        this.number = number;
    }
}
```

```java
ToStringExample example = new ToStringExample(123);
System.out.println(example.toString());
```

```html
ToStringExample@4554617c
```


## clone()

**1. cloneable** 

clone() 是 Object 的 protected 方法，它不是 public，一个类不显式去重写 clone()，其它类就不能直接去调用该类实例的 clone() 方法。

```java
public class CloneExample {
    private int a;
    private int b;
}
```

```java
CloneExample e1 = new CloneExample();
// CloneExample e2 = e1.clone(); // 'clone()' has protected access in 'java.lang.Object'
```

重写 clone() 得到以下实现：

```java
public class CloneExample {
    private int a;
    private int b;

    @Override
    public CloneExample clone() throws CloneNotSupportedException {
        return (CloneExample)super.clone();
    }
}
```

```java
CloneExample e1 = new CloneExample();
try {
    CloneExample e2 = e1.clone();
} catch (CloneNotSupportedException e) {
    e.printStackTrace();
}
```

```html
java.lang.CloneNotSupportedException: CloneExample
```

以上抛出了 CloneNotSupportedException，这是因为 CloneExample 没有实现 Cloneable 接口。

应该注意的是，clone() 方法并不是 Cloneable 接口的方法，而是 Object 的一个 protected 方法。Cloneable 接口只是规定，如果一个类没有实现 Cloneable 接口又调用了 clone() 方法，就会抛出 CloneNotSupportedException。

```java
public class CloneExample implements Cloneable {
    private int a;
    private int b;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
```

**2. 浅拷贝** 

拷贝对象和原始对象的引用类型引用同一个对象。

```java
public class ShallowCloneExample implements Cloneable {

    private int[] arr;

    public ShallowCloneExample() {
        arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
    }

    public void set(int index, int value) {
        arr[index] = value;
    }

    public int get(int index) {
        return arr[index];
    }

    @Override
    protected ShallowCloneExample clone() throws CloneNotSupportedException {
        return (ShallowCloneExample) super.clone();
    }
}
```

```java
ShallowCloneExample e1 = new ShallowCloneExample();
ShallowCloneExample e2 = null;
try {
    e2 = e1.clone();
} catch (CloneNotSupportedException e) {
    e.printStackTrace();
}
e1.set(2, 222);
System.out.println(e2.get(2)); // 222
```

**3. 深拷贝** 

拷贝对象和原始对象的引用类型引用不同对象。

```java
public class DeepCloneExample implements Cloneable {

    private int[] arr;

    public DeepCloneExample() {
        arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
    }

    public void set(int index, int value) {
        arr[index] = value;
    }

    public int get(int index) {
        return arr[index];
    }

    @Override
    protected DeepCloneExample clone() throws CloneNotSupportedException {
        DeepCloneExample result = (DeepCloneExample) super.clone();
        result.arr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result.arr[i] = arr[i];
        }
        return result;
    }
}
```

```java
DeepCloneExample e1 = new DeepCloneExample();
DeepCloneExample e2 = null;
try {
    e2 = e1.clone();
} catch (CloneNotSupportedException e) {
    e.printStackTrace();
}
e1.set(2, 222);
System.out.println(e2.get(2)); // 2
```

**4. clone() 的替代方案** 

使用 clone() 方法来拷贝一个对象即复杂又有风险，它会抛出异常，并且还需要类型转换。Effective Java 书上讲到，最好不要去使用 clone()，可以使用拷贝构造函数或者拷贝工厂来拷贝一个对象。

```java
public class CloneConstructorExample {

    private int[] arr;

    public CloneConstructorExample() {
        arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
    }

    public CloneConstructorExample(CloneConstructorExample original) {
        arr = new int[original.arr.length];
        for (int i = 0; i < original.arr.length; i++) {
            arr[i] = original.arr[i];
        }
    }

    public void set(int index, int value) {
        arr[index] = value;
    }

    public int get(int index) {
        return arr[index];
    }
}
```

```java
CloneConstructorExample e1 = new CloneConstructorExample();
CloneConstructorExample e2 = new CloneConstructorExample(e1);
e1.set(2, 222);
System.out.println(e2.get(2)); // 2
```

# 关键字

## final

**1. 数据** 

声明数据为常量，可以是编译时常量，也可以是在运行时被初始化后不能被改变的常量。

- 对于基本类型，final 使数值不变；
- 对于引用类型，final 使引用不变，也就不能引用其它对象，但是被引用的对象本身是可以修改的。

```java
final int x = 1;
// x = 2;  // cannot assign value to final variable 'x'
final A y = new A();
y.a = 1;
```

**2. 方法** 

private声明方法不能被子类重写。

private 方法隐式地被指定为 final，如果在子类中定义的方法和基类中的一个 private 方法签名相同，此时子类的方法不是重写基类方法，而是在子类中定义了一个新的方法。


**3. 类** 

声明类不允许被继承。

## static

**1. 静态变量** 

- 静态变量：又称为类变量，也就是说这个变量属于类的，类所有的实例都共享静态变量，可以直接通过类名来访问它。静态变量在内存中只存在一份。
- 实例变量：每创建一个实例就会产生一个实例变量，它与该实例同生共死。

```java
public class A {

    private int x;         // 实例变量
    private static int y;  // 静态变量

    public static void main(String[] args) {
        // int x = A.x;  // Non-static field 'x' cannot be referenced from a static context
        A a = new A();
        int x = a.x;
        int y = A.y;
    }
}
```

**2. 静态方法** 

静态方法在类加载的时候就存在了，它不依赖于任何实例。所以静态方法必须有实现，也就是说它不能是抽象方法。

```java
public abstract class A {
    public static void func1(){
    }
    // public abstract static void func2();  // Illegal combination of modifiers: 'abstract' and 'static'
}
```

只能访问所属类的静态字段和静态方法，方法中不能有 this 和 super 关键字。

```java
public class A {

    private static int x;
    private int y;

    public static void func1(){
        int a = x;
        // int b = y;  // Non-static field 'y' cannot be referenced from a static context
        // int b = this.y;     // 'A.this' cannot be referenced from a static context
    }
}
```


**3. 静态语句块** 

静态语句块在类初始化时运行一次。

```java
public class A {
    static {
        System.out.println("123");
    }

    public static void main(String[] args) {
        A a1 = new A();
        A a2 = new A();
    }
}
```

```html
123
```


**4. 静态内部类** 

非静态内部类依赖于外部类的实例，而静态内部类不需要。

```java
public class OuterClass {

    class InnerClass {
    }

    static class StaticInnerClass {
    }

    public static void main(String[] args) {
        // InnerClass innerClass = new InnerClass(); // 'OuterClass.this' cannot be referenced from a static context
        OuterClass outerClass = new OuterClass();
        InnerClass innerClass = outerClass.new InnerClass();
        StaticInnerClass staticInnerClass = new StaticInnerClass();
    }
}
```

静态内部类不能访问外部类的非静态的变量和方法。


**5. 静态导包** 

在使用静态变量和方法时不用再指明 ClassName，从而简化代码，但可读性大大降低。

```java
import static com.xxx.ClassName.*
```

**6. 初始化顺序** 

静态变量和静态语句块优先于实例变量和普通语句块，静态变量和静态语句块的初始化顺序取决于它们在代码中的顺序。

```java
public static String staticField = "静态变量";
```

```java
static {
    System.out.println("静态语句块");
}
```

```java
public String field = "实例变量";
```

```java
{
    System.out.println("普通语句块");
}
```

最后才是构造函数的初始化。

```java
public InitialOrderTest() {
    System.out.println("构造函数");
}
```

存在继承的情况下，初始化顺序为：

- 父类（静态变量、静态语句块）
- 子类（静态变量、静态语句块）
- 父类（实例变量、普通语句块）
- 父类（构造函数）
- 子类（实例变量、普通语句块）
- 子类（构造函数）

# 反射
## 定义
在程序运行时可以知道和调用某个类的任意方法和属性的功能叫反射机制

每个类都有一个  **Class**  对象，包含了与类有关的信息。当编译一个新类时，会产生一个同名的 .class 文件，该文件内容保存着 Class 对象。

类加载相当于 Class 对象的加载，类在第一次使用时才动态加载到 JVM 中。也可以使用 `Class.forName("com.mysql.jdbc.Driver")` 这种方式来控制类的加载，该方法会返回一个 Class 对象。

反射可以提供运行时的类信息，并且这个类可以在运行时才加载进来，甚至在编译时期该类的 .class 不存在也可以加载进来。

Class 和 java.lang.reflect 一起对反射提供了支持，java.lang.reflect 类库主要包含了以下三个类：

-  **Field** ：可以使用 get() 和 set() 方法读取和修改 Field 对象关联的字段；
-  **Method** ：可以使用 invoke() 方法调用与 Method 对象关联的方法；
-  **Constructor** ：可以用 Constructor 的 newInstance() 创建新的对象。


## 获取Class对象
1.  用Class的方法: Class.forName(driver);
2. 直接获取某一个对象的 class: Class<?> classInt = Integer.TYPE;
3. 调用某个对象的 getClass() 方法:Class<?> klass = str.getClass();



## 创建实例

使用Class对象的newInstance()方法来创建Class对象对应类的实例。

Class<?> c = String.class;  
Object str = c.newInstance();  

先通过Class对象获取指定的Constructor对象，再调用Constructor对象的newInstance()方法来创建实例。这种方法可以用指定的构造器构造类的实例。
```java
//获取String所对应的Class对象
Class<?> c = String.class;
//获取String类带一个String参数的构造器
Constructor constructor = c.getConstructor(String.class);
//根据构造器创建实例
Object obj = constructor.newInstance("23333");
System.out.println(obj);
```

## 使用反射获取类信息

### 使用反射获取变量信息

```java
/**
 * 通过反射获取类的所有变量
 */
private static void printFields(){
    //1.获取并输出类的名称
    Class mClass = SonClass.class;
    System.out.println("类的名称：" + mClass.getName());

    //2.1 获取所有 public 访问权限的变量
    // 包括本类声明的和从父类继承的
    Field[] fields = mClass.getFields();

    //2.2 获取所有本类声明的变量（不问访问权限）
    //Field[] fields = mClass.getDeclaredFields();

    //3. 遍历变量并输出变量信息
    for (Field field :
            fields) {
        //获取访问权限并输出
        int modifiers = field.getModifiers();
        System.out.print(Modifier.toString(modifiers) + " ");
        //输出变量的类型及变量名
        System.out.println(field.getType().getName()
                 + " " + field.getName());
    }
}
```
调用 getFields() 方法，输出 SonClass 类以及其所继承的父类( 包括 FatherClass 和 Object ) 的 public 方法。
```java
  类的名称：obj.SonClass
  public java.lang.String mSonBirthday
  public java.lang.String mFatherName
  public int mFatherAge
  ```

调用 getDeclaredFields() ， 输出 SonClass 类的所有成员变量，不问访问权限。
```java
  类的名称：obj.SonClass
  private java.lang.String mSonName
  protected int mSonAge
  public java.lang.String mSonBirthday
  ```

  
### 使用反射获取方法信息

```java
/**
 * 通过反射获取类的所有方法
 */
private static void printMethods(){
    //1.获取并输出类的名称
    Class mClass = SonClass.class;
    System.out.println("类的名称：" + mClass.getName());

    //2.1 获取所有 public 访问权限的方法
    //包括自己声明和从父类继承的
    Method[] mMethods = mClass.getMethods();

    //2.2 获取所有本类的的方法（不问访问权限）
    //Method[] mMethods = mClass.getDeclaredMethods();

    //3.遍历所有方法
    for (Method method :
            mMethods) {
        //获取并输出方法的访问权限（Modifiers：修饰符）
        int modifiers = method.getModifiers();
        System.out.print(Modifier.toString(modifiers) + " ");
        //获取并输出方法的返回值类型
        Class returnType = method.getReturnType();
        System.out.print(returnType.getName() + " "
                + method.getName() + "( ");
        //获取并输出方法的所有参数
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter:
             parameters) {
            System.out.print(parameter.getType().getName()
                    + " " + parameter.getName() + ",");
        }
        //获取并输出方法抛出的异常
        Class[] exceptionTypes = method.getExceptionTypes();
        if (exceptionTypes.length == 0){
            System.out.println(" )");
        }
        else {
            for (Class c : exceptionTypes) {
                System.out.println(" ) throws "
                        + c.getName());
            }
        }
    }
}
```

调用 getMethods() 方法  获取 SonClass 类所有 public 访问权限的方法，包括从父类继承的。打印信息中，printSonMsg() 方法来自 SonClass 类， printFatherMsg() 来自 FatherClass 类，其余方法来自 Object 类。

**Class类是一个类,里面有Filed,Method,Parameter等描述一个类的属性.**

```java
  类的名称：obj.SonClass
  public void printSonMsg(  )
  public void printFatherMsg(  )
  public final void wait(  ) throws java.lang.InterruptedException
  public final void wait( long arg0,int arg1, ) throws java.lang.InterruptedException
  public final native void wait( long arg0, ) throws java.lang.InterruptedException
  public boolean equals( java.lang.Object arg0, )
  public java.lang.String toString(  )
  public native int hashCode(  )
  public final native java.lang.Class getClass(  )
  public final native void notify(  )
  public final native void notifyAll(  )
  ```

调用 getDeclaredMethods() 方法


```java
  类的名称：obj.SonClass
  private int getSonAge(  )
  private void setSonAge( int arg0, )
  public void printSonMsg(  )
  private void setSonName( java.lang.String arg0, )
  private java.lang.String getSonName(  )
```

## 访问和操作私有变量与方法

### 访问私有方法
```java
/**
 * 访问对象的私有方法
 * 为简洁代码，在方法上抛出总的异常，实际开发别这样
 */
private static void getPrivateMethod() throws Exception{
    //1. 获取 Class 类实例
    TestClass testClass = new TestClass();
    Class mClass = testClass.getClass();

    //2. 获取私有方法
    //第一个参数为要获取的私有方法的名称
    //第二个为要获取方法的参数的类型，参数为 Class...，没有参数就是null
    //方法参数也可这么写 ：new Class[]{String.class , int.class}
    Method privateMethod =
            mClass.getDeclaredMethod("privateMethod", String.class, int.class);

    //3. 开始操作方法
    if (privateMethod != null) {
        //获取私有方法的访问权
        //只是获取访问权，并不是修改实际权限
        privateMethod.setAccessible(true);

        //使用 invoke 反射调用私有方法
        //privateMethod 是获取到的私有方法
        //testClass 要操作的对象
        //后面两个参数传实参
        privateMethod.invoke(testClass, "Java Reflect ", 666);
    }
}
```
### 修改私有变量
```java
/**
 * 修改对象私有变量的值
 * 为简洁代码，在方法上抛出总的异常
 */
private static void modifyPrivateFiled() throws Exception {
    //1. 获取 Class 类实例
    TestClass testClass = new TestClass();
    Class mClass = testClass.getClass();

    //2. 获取私有变量
    Field privateField = mClass.getDeclaredField("MSG");

    //3. 操作私有变量
    if (privateField != null) {
        //获取私有变量的访问权
        privateField.setAccessible(true);

        //修改私有变量，并输出以测试
        System.out.println("Before Modify：MSG = " + testClass.getMsg());

        //调用 set(object , value) 修改变量的值
        //privateField 是获取到的私有变量
        //testClass 要操作的对象
        //"Modified" 为要修改成的值
        privateField.set(testClass, "Modified");
        System.out.println("After Modify：MSG = " + testClass.getMsg());
    }
}
```
### 修改私有常量

对于基本类型,由于在编译的时候,已经把用到常量的地方 替换为具体常量值,所以无法修改;

但普通的引用类型常量,可以修改


编译前:
```java
//注意是 String  类型的值
private final String FINAL_VALUE = "hello";

if(FINAL_VALUE.equals("world")){
    //do something
}

```

编译后的.class文件

```java
private final String FINAL_VALUE = "hello";
//替换为"hello"
if("hello".equals("world")){
    //do something
}
```

## 应用场景

**反射的优点：** 

*    **可扩展性**  ：应用程序可以利用全限定名创建可扩展对象的实例，来使用来自外部的用户自定义类。
*    **类浏览器和可视化开发环境**  ：一个类浏览器需要可以枚举类的成员。可视化开发环境（如 IDE）可以从利用反射中可用的类型信息中受益，以帮助程序员编写正确的代码。
*    **调试器和测试工具**  ： 调试器需要能够检查一个类里的私有成员。测试工具可以利用反射来自动地调用类里定义的可被发现的 API 定义，以确保一组测试中有较高的代码覆盖率。

**反射的缺点：** 

尽管反射非常强大，但也不能滥用。如果一个功能可以不用反射完成，那么最好就不用。在我们使用反射技术时，下面几条内容应该牢记于心。

*    **性能开销**  ：反射涉及了动态类型的解析，所以 JVM 无法对这些代码进行优化。因此，反射操作的效率要比那些非反射操作低得多。我们应该避免在经常被执行的代码或对性能要求很高的程序中使用反射。

*    **安全限制**  ：使用反射技术要求程序必须在一个没有安全限制的环境中运行。如果一个程序必须在有安全限制的环境中运行，如 Applet，那么这就是个问题了。

*    **内部暴露**  ：由于反射允许代码执行一些在正常情况下不被允许的操作（比如访问私有的属性和方法），所以使用反射可能会导致意料之外的副作用，这可能导致代码功能失调并破坏可移植性。反射代码破坏了抽象性，因此当平台发生改变的时候，代码的行为就有可能也随着变化。


# 异常
## 分类

Throwable 可以用来表示任何可以作为异常抛出的类，分为两种： **Error**  和 **Exception**。

<div align="center"> <img src="pics/PPjwP.png" width="600"/> </div><br>

**注意：异常(exception)和错误(error)的区别：异常能被程序本身可以处理，错误是无法处理。**

**Error**  
用来表示 JVM 无法处理的错误，大多数错误与代码编写者执行的操作无关，而表示代码运行时 JVM（Java 虚拟机）出现的问题。例如，Java虚拟机运行错误（Virtual MachineError），当 JVM 不再有继续执行操作所需的内存资源时，将出现 OutOfMemoryError。这些异常发生时，Java虚拟机（JVM）一般会选择线程终止。

**Exception**  
分为两种：

-  **受检异常/可查异常/非运行时异常/编译异常** ：需要用 try...catch... 语句捕获并进行处理,不捕获编译无法通过，并且可以从异常中恢复；
-  **非受检异常/不可查异常/运行时异常** ：是程序运行时错误,是由逻辑错误引起的,无法在编译期发现错误，例如除 0 会引发 Arithmetic Exception，此时程序崩溃并且无法恢复。

## 异常处理机制
在 Java 应用程序中，异常处理机制为：**抛出异常，捕捉异常。**

Error，当运行方法不欲捕捉时，Java允许该方法不做任何抛出声明。因为，大多数Error异常属于永远不能被允许发生的状况，也属于合理的应用程序不该捕捉的异常。

对于Exception中的可查异常：要么捕获,要么抛出

对于Exception中的运行时异常: 由于运行时异常的不可查性，运行时异常将由Java运行时系统自动抛出，允许应用程序忽略运行时异常。

### 异常捕获语句 try-catch
catch捕获 

对应异常及其子类异常,

一经捕获不再经过其他catch,所以对于有多个catch子句的异常程序而言，应该尽量将捕获底层异常类的catch子 句放在前面，同时尽量将捕获相对高层的异常类的catch子句放在后面。否则，捕获底层异常类的catch子句将可能会被屏蔽。
### 异常捕获语句 try－catch-finally
try-catch语句还可以包括第三部分，就是finally子句。它表示无论是否出现异常，都会执行的语句. 


## 执行顺序
1. try、catch、finally语句块的执行顺序:


1)当try没有捕获到异常时：try语句块中的语句逐一被执行，程序将跳过catch语句块，执行finally语句块和其后的语句；

2)当try捕获到异常，catch语句块里没有处理此异常的情况：当try语句块里的某条语句出现异常时，而没有处理此异常的catch语句块时，此异常将会抛给JVM处理，finally语句块里的语句还是会被执行，但finally语句块后的语句不会被执行；

3)当try捕获到异常，catch语句块里有处理此异常的情况：在try语句块中是按照顺序来执行的，当执行到某一条语句出现异常时，程序将跳到catch语句块，并与catch语句块逐一匹配，找到与之对应的处理程序，其他的catch语句块将不会被执行，而try语句块中，出现异常之后的语句也不会被执行，catch语句块执行完后，执行finally语句块里的语句，最后执行finally语句块后的语句；
<div align="center"> <img src=".\pictures\java-basic\Snipaste_2019-08-23_13-58-16.jpg" width="600"/> </div><br>

## 异常链
Java方法抛出的可查异常将依据调用栈、沿着方法调用的层次结构一直传递到具备处理能力的调用方法，最高层次到main方法为止。

## 常见异常

**runtimeException子类:**  

java.lang.ArrayIndexOutOfBoundsException  
数组索引越界异常。当对数组的索引值为负数或大于等于数组大小时抛出。

java.lang.ArithmeticException  
算术条件异常。譬如：整数除零等。

java.lang.NullPointerException  
空指针异常。当应用试图在要求使用对象的地方使用了null时，抛出该异常。譬如：调用null对象的实例方法、访问null对象的属性、计算null对象的长度、使用throw语句抛出null等等

java.lang.ClassNotFoundException
找不到类异常。当应用试图根据字符串形式的类名构造类，而在遍历CLASSPAH之后找不到对应名称的class文件时，抛出该异常。

java.lang.NegativeArraySizeException  数组长度为负异常

java.lang.ArrayStoreException 数组中包含不兼容的值抛出的异常

java.lang.SecurityException 安全性异常

java.lang.IllegalArgumentException 非法参数异常

**IOException**

IOException：操作输入流和输出流时可能出现的异常。

EOFException   文件已结束异常

FileNotFoundException   文件未找到异常

**其他**


ClassCastException    类型转换异常类

ArrayStoreException  数组中包含不兼容的值抛出的异常

SQLException   操作数据库异常类

NoSuchFieldException   字段未找到异常

NoSuchMethodException   方法未找到抛出的异常

NumberFormatException    字符串转换为数字抛出的异常

StringIndexOutOfBoundsException 字符串索引超出范围抛出的异常

IllegalAccessException  不允许访问某类异常

InstantiationException  当应用程序试图使用Class类中的newInstance()方法创建一个类的实例，而指定的类对象无法被实例化时，抛出该异常


# 泛型
## 定义

泛型就是把传入的类型参数化
```java
public class Box<T> {
    // T stands for "Type"
    private T t;
    public void set(T t) { this.t = t; }
    public T get() { return t; }
}
```
泛型只在编译阶段有效。在编译之后程序会采取去泛型化的措施。

## 经典问题
　1. Java中的泛型是什么 ? 使用泛型的好处是什么?

　　这是在各种Java泛型面试中，一开场你就会被问到的问题中的一个，主要集中在初级和中级面试中。那些拥有Java1.4或更早版本的开发背景的人都知道，在集合中存储对象并在使用前进行类型转换是多么的不方便。泛型防止了那种情况的发生。它提供了编译期的类型安全，确保你只能把正确类型的对象放入集合中，避免了在运行时出现ClassCastException。

　　2. Java的泛型是如何工作的 ? 什么是类型擦除 ?

　　这是一道更好的泛型面试题。泛型是通过类型擦除来实现的，编译器在编译时擦除了所有类型相关的信息，所以在运行时不存在任何类型相关的信息。例如List<String>在运行时仅用一个List来表示。这样做的目的，是确保能和Java 5之前的版本开发二进制类库进行兼容。你无法在运行时访问到类型参数，因为编译器已经把泛型类型转换成了原始类型。根据你对这个泛型问题的回答情况，你会得到一些后续提问，比如为什么泛型是由类型擦除来实现的或者给你展示一些会导致编译器出错的错误泛型代码。请阅读我的Java中泛型是如何工作的来了解更多信息。

　　3. 什么是泛型中的限定通配符和非限定通配符 ?

　　这是另一个非常流行的Java泛型面试题。限定通配符对类型进行了限制。有两种限定通配符，一种是<? extends T>它通过确保类型必须是T的子类来设定类型的上界，另一种是<? super T>它通过确保类型必须是T的父类来设定类型的下界。泛型类型必须用限定内的类型来进行初始化，否则会导致编译错误。另一方面<?>表示了非限定通配符，因为<?>可以用任意类型来替代。更多信息请参阅我的文章泛型中限定通配符和非限定通配符之间的区别。

　　4. List<? extends T>和List <? super T>之间有什么区别 ?

　　这和上一个面试题有联系，有时面试官会用这个问题来评估你对泛型的理解，而不是直接问你什么是限定通配符和非限定通配符。这两个List的声明都是限定通配符的例子，List<? extends T>可以接受任何继承自T的类型的List，而List<? super T>可以接受任何T的父类构成的List。例如List<? extends Number>可以接受List<Integer>或List<Float>。在本段出现的连接中可以找到更多信息。

　　5. 如何编写一个泛型方法，让它能接受泛型参数并返回泛型类型?

　　编写泛型方法并不困难，你需要用泛型类型来替代原始类型，比如使用T, E or K,V等被广泛认可的类型占位符。泛型方法的例子请参阅Java集合类框架。最简单的情况下，一个泛型方法可能会像这样:

      public V put(K key, V value) {

              return cache.put(key, value);

      }

　  6. Java中如何使用泛型编写带有参数的类?

　　这是上一道面试题的延伸。面试官可能会要求你用泛型编写一个类型安全的类，而不是编写一个泛型方法。关键仍然是使用泛型类型来代替原始类型，而且要使用JDK中采用的标准占位符。

　　7. 编写一段泛型程序来实现LRU缓存?

　　对于喜欢Java编程的人来说这相当于是一次练习。给你个提示，LinkedHashMap可以用来实现固定大小的LRU缓存，当LRU缓存已经满了的时候，它会把最老的键值对移出缓存。LinkedHashMap提供了一个称为removeEldestEntry()的方法，该方法会被put()和putAll()调用来删除最老的键值对。当然，如果你已经编写了一个可运行的JUnit测试，你也可以随意编写你自己的实现代码。

　　8. 你可以把List<String>传递给一个接受List<Object>参数的方法吗？

　　对任何一个不太熟悉泛型的人来说，这个Java泛型题目看起来令人疑惑，因为乍看起来String是一种Object，所以List<String>应当可以用在需要List<Object>的地方，但是事实并非如此。真这样做的话会导致编译错误。如果你再深一步考虑，你会发现Java这样做是有意义的，因为List<Object>可以存储任何类型的对象包括String, Integer等等，而List<String>却只能用来存储Strings。　

       List<Object> objectList;
       List<String> stringList;
       objectList = stringList;  //compilation error incompatible types
 　9. Array中可以用泛型吗?

　　这可能是Java泛型面试题中最简单的一个了，当然前提是你要知道Array事实上并不支持泛型，这也是为什么Joshua Bloch在Effective Java一书中建议使用List来代替Array，因为List可以提供编译期的类型安全保证，而Array却不能。

　　10. 如何阻止Java中的类型未检查的警告?

　　如果你把泛型和原始类型混合起来使用，例如下列代码，Java 5的javac编译器会产生类型未检查的警告，例如　　

       List<String> rawList = new ArrayList()
       
# 特性
## Java 各版本的新特性

**New highlights in Java SE 8** 

1. Lambda Expressions
2. Pipelines and Streams
3. Date and Time API
4. Default Methods
5. Type Annotations
6. Nashhorn JavaScript Engine
7. Concurrent Accumulators
8. Parallel operations
9. PermGen Error Removed

**New highlights in Java SE 7** 

1. Strings in Switch Statement
2. Type Inference for Generic Instance Creation
3. Multiple Exception Handling
4. Support for Dynamic Languages
5. Try with Resources
6. Java nio Package
7. Binary Literals, Underscore in literals
8. Diamond Syntax

- [Difference between Java 1.8 and Java 1.7?](http://www.selfgrowth.com/articles/difference-between-java-18-and-java-17)
- [Java 8 特性](http://www.importnew.com/19345.html)
## Java 与 C++ 的区别

- Java 是纯粹的面向对象语言，所有的对象都继承自 java.lang.Object，C++ 为了兼容 C 即支持面向对象也支持面向过程。
- Java 通过虚拟机从而实现跨平台特性，但是 C++ 依赖于特定的平台。
- Java 没有指针，它的引用可以理解为安全指针，而 C++ 具有和 C 一样的指针。
- Java 支持自动垃圾回收，而 C++ 需要手动回收。
- Java 不支持多重继承，只能通过实现多个接口来达到相同目的，而 C++ 支持多重继承。
- Java 不支持操作符重载，虽然可以对两个 String 对象执行加法运算，但是这是语言内置支持的操作，不属于操作符重载，而 C++ 可以。
- Java 的 goto 是保留字，但是不可用，C++ 可以使用 goto。
- Java 不支持条件编译，C++ 通过 #ifdef #ifndef 等预处理命令从而实现条件编译。

[What are the main differences between Java and C++?](http://cs-fundamentals.com/tech-interview/java/differences-between-java-and-cpp.php)

## JRE or JDK

- JRE is the JVM program, Java application need to run on JRE.
- JDK is a superset of JRE, JRE + tools for developing java programs. e.g, it provides the compiler "javac"