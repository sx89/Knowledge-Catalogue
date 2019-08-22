
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

<div align="center"> <img src="pictures\java-core\Snipaste_2019-08-11_19-55-46.jpg" width="600"/> </div><br>

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



# 泛型

