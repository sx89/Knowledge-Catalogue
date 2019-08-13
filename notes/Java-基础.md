
<!-- TOC -->

- [数据类型](#数据类型)
    - [基本数据类型](#基本数据类型)
        - [拆箱与装箱](#拆箱与装箱)
    - [String ,StringBuffer,StringBuilder的区别](#string-stringbufferstringbuilder的区别)
        - [StrinBuffer和StringBuilder的常用方法](#strinbuffer和stringbuilder的常用方法)
    - [引用类型](#引用类型)
- [== 与 equal](#-与-equal)
- [面向对象三大特性](#面向对象三大特性)
    - [继承](#继承)
    - [封装](#封装)
    - [多态](#多态)
        - [三种实现方式](#三种实现方式)
        - [表现形式](#表现形式)
        - [多态的实现原理](#多态的实现原理)
- [基类与派生类](#基类与派生类)
    - [定义](#定义)
    - [java只有单继承原因](#java只有单继承原因)
    - [接口可以多继承](#接口可以多继承)
    - [interface和abstract class](#interface和abstract-class)
    - [默认调无参](#默认调无参)
    - [有参用super](#有参用super)
    - [基类private属性](#基类private属性)
    - [派生类的覆盖](#派生类的覆盖)
- [重写与重载](#重写与重载)
    - [重写(Override)](#重写override)
        - [编译时与运行时细节](#编译时与运行时细节)
        - [重写规则](#重写规则)
        - [super关键字](#super关键字)
    - [重载(Overload)](#重载overload)
        - [重载规则:](#重载规则)
        - [重载和重写的区别](#重载和重写的区别)
    - [重写的向上向下转型](#重写的向上向下转型)
        - [向上转型(子类向上转成父类来用)](#向上转型子类向上转成父类来用)
        - [向下转型](#向下转型)
    - [静态分派和动态分派](#静态分派和动态分派)
    - [重载匹配优先级](#重载匹配优先级)
- [泛型](#泛型)

<!-- /TOC -->
## 数据类型

### 基本数据类型

int 32位 4字节    
short 16位  
float 32位  
double 64位  
long 64位  
char 16位  
byte 8位  
boolean 1位  
自动拆箱和装箱的意思就是，计算数值时，integer会自动转为int进行计算。
而当int传入类型为integer的引用时，int数值又会被包装为integer。在一个类里面,基本数据类型的属性尽量使用包装类.
####  拆箱与装箱

### String ,StringBuffer,StringBuilder的区别
StringBuffer、StringBuilder和String一样，也用来代表字符串。

String类是不可变类，任何对String的改变都 会引发新的String对象的生成；

StringBuffer和StringBuilder则是可变类，任何对它所指代的字符串的改变都不会产生新的对象。

StringBufferd支持并发操作，线性安全的，适 合多线程中使用。StringBuilder不支持并发操作，线性不安全的，不适合多线程中使用。新引入的StringBuilder类不是线程安全的，但其在单线程中的性能比StringBuffer高。

在字符串操作频繁的情况下,String的效率远低于其他两个:
```java
public class StringTest {
 
	public static String BASEINFO = "Mr.Y";
	public static final int COUNT = 2000000;
 
	/**
	 * 执行一项String赋值测试
	 */
	public static void doStringTest() {
 
		String str = new String(BASEINFO);
		long starttime = System.currentTimeMillis();
		for (int i = 0; i < COUNT / 100; i++) {
			str = str + "miss";
		}
		long endtime = System.currentTimeMillis();
		System.out.println((endtime - starttime)
				+ " millis has costed when used String.");
	}
 
	/**
	 * 执行一项StringBuffer赋值测试
	 */
	public static void doStringBufferTest() {
 
		StringBuffer sb = new StringBuffer(BASEINFO);
		long starttime = System.currentTimeMillis();
		for (int i = 0; i < COUNT; i++) {
			sb = sb.append("miss");
		}
		long endtime = System.currentTimeMillis();
		System.out.println((endtime - starttime)
				+ " millis has costed when used StringBuffer.");
	}
 
	/**
	 * 执行一项StringBuilder赋值测试
	 */
	public static void doStringBuilderTest() {
 
		StringBuilder sb = new StringBuilder(BASEINFO);
		long starttime = System.currentTimeMillis();
		for (int i = 0; i < COUNT; i++) {
			sb = sb.append("miss");
		}
		long endtime = System.currentTimeMillis();
		System.out.println((endtime - starttime)
				+ " millis has costed when used StringBuilder.");
	}
 
	/**
	 * 测试StringBuffer遍历赋值结果
	 * 
	 * @param mlist
	 */
	public static void doStringBufferListTest(List<String> mlist) {
		StringBuffer sb = new StringBuffer();
		long starttime = System.currentTimeMillis();
		for (String string : mlist) {
			sb.append(string);
		}
		long endtime = System.currentTimeMillis();
		System.out.println(sb.toString() + "buffer cost:"
				+ (endtime - starttime) + " millis");
	}
 
	/**
	 * 测试StringBuilder迭代赋值结果
	 * 
	 * @param mlist
	 */
	public static void doStringBuilderListTest(List<String> mlist) {
		StringBuilder sb = new StringBuilder();
		long starttime = System.currentTimeMillis();
		for (Iterator<String> iterator = mlist.iterator(); iterator.hasNext();) {
			sb.append(iterator.next());
		}
 
		long endtime = System.currentTimeMillis();
		System.out.println(sb.toString() + "builder cost:"
				+ (endtime - starttime) + " millis");
	}
 
	public static void main(String[] args) {
		doStringTest();
		doStringBufferTest();
		doStringBuilderTest();
 
		List<String> list = new ArrayList<String>();
		list.add(" I ");
		list.add(" like ");
		list.add(" BeiJing ");
		list.add(" tian ");
		list.add(" an ");
		list.add(" men ");
		list.add(" . ");
 
		doStringBufferListTest(list);
		doStringBuilderListTest(list);
	}
 
}


输出结果

2711 millis has costed when used String.
211 millis has costed when used StringBuffer.
141 millis has costed when used StringBuilder.
 I  like  BeiJing  tian  an  men  . buffer cost:1 millis
 I  like  BeiJing  tian  an  men  . builder cost:0 millis

```
####StrinBuffer和StringBuilder的常用方法
都继承自AbstractStringBuilder,主要的存储是 chars[] value,和 capicity

StringBuffer s = new StringBuffer();  
capicity=16

StringBuffer sb2=new StringBuffer(“how are you?”)  
初始化的时候,capicity=传入字符串的长度+16 即为内部chars[] value的长度

扩容大小 尝试将新容量扩为大小变成2倍+2
### 引用类型
在java里面除去基本数据类型的其它类型都是引用数据类型，自己定义的class类都是引用类型，可以像基本类型一样使用。

基本数据类型在往方法传值的时候,传递的是值

引用类型在往方法传值的时候,传递的是引用地址(String类型,类类型都是引用类型)
## == 与 equal

==比较的是引用的内存地址

equal看类的equal实现情况

String,Integer,Double等基本类型的包装类实现了equal方法,比较的是内容(先比较地址,若地址相同则返回true,否则比较内容)

StringBuilder,StringBuffer没有实现equal方法,比较的仍然是地址

## 面向对象三大特性
### 继承
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


### 封装
用访问修饰符来保证类内部的数据不会被外部随意访问修改

```java
default (即默认，什么也不写）: 在同一包内可见，不使用任何修饰符。使用对象：类、接口、变量、方法。

private : 在同一类内可见。使用对象：变量、方法。 注意：不能修饰类（外部类）

public : 对所有类可见。使用对象：类、接口、变量、方法

protected : 对同一包内的类和所有子类可见。使用对象：变量、方法。 注意：不能修饰类（外部类）。
```


### 多态
同一个方法或者类具有不同的功能或者表现形式

#### 三种实现方式
1. 重写与重载

从jvm实现的角度来看，重写又叫运行时多态，编译时看不出子类调用的是哪个方法，但是运行时操作数栈会先根据子类的引用去子类的类信息中查找方法，找不到的话再到父类的类信息中查找方法。

而重载则是编译时多态，因为编译期就可以确定传入的参数组合，决定调用的具体方法是哪一个了。


2. 接口
3. 抽象类与抽象方法

#### 表现形式
对象上 向上转型与向下转型

方法上 重写与重载
#### 多态的实现原理




## 基类与派生类
### 定义
派生类具有和基类相同的属性,方法;还可以自己增加一些额外的属性和方法

### java只有单继承原因

举个例子，在这里有个A类，我们又编写了两个类B类和C类，并且B类和C类分别继承了A类，并且对A类的同一个方法进行了覆盖。如果此时我们再次编写了一个D类，并且D类以多继承的方式同时集成了B类和C类，那么D类也会继承B类和C类从A类中重载的方法，那么问题来了，D类也开始犯迷糊了，我到底应该哪个继承哪个类中的方法呢，因为类是结构性的，这样就会造成结构上的混乱。这就是多继承的菱形继承问题。

### 接口可以多继承

因为接口只有抽象方法，具体方法只能由实现接口的类实现，在调用的时候始终只会调用实现类（也就是子类覆盖的方法）的方法（不存在歧义），因此即使继承的两个接口中的方法名是一样的，最终调用的时候也都是调用实现类中的那个方法，不会缠身歧义；而又因为接口只有静态的常量，但是由于静态变量是在编译期决定调用关系的，即使存在一定的冲突也会在编译时提示出错；而引用静态变量一般直接使用类名或接口名，从而避免产生歧义，因此从接口的变量是来看也是可以通过的。 

### interface和abstract class
abstract class的核心在于，我知道一类物体的部分行为（和属性），但是不清楚另一部分的行为（和属性），所以我不能自己实例化。

还是刚才那个例子，如果你有个abstract class叫哺乳动物，那么你可以定义他们胎生，恒定体温等共同的行为，但是具体“叫”这个行为时，你得留着让非abstract的狗和猫等等子类具体实现。

interface的核心在于，我只知道这个物体能干什么，具体是什么不需要遵从类的继承关系。比如上述的“玩耍”interface，狗有狗的玩法，猫有猫的玩法，妖魔鬼怪机器人都可以玩耍，只要你告诉我这个物体有玩耍接口，我就能让它玩起来(๑•̀ㅂ•́) ✧

所以abstract class和interface是不能互相替代的，interface不能定义（它只做了声明）共同的行为，事实上它也不能定义“非常量”的变量。而abstract class只是一种分类的抽象，它不能横跨类别来描述一类行为，它使得针对“别的分类方式”的抽象变得无法实现（所以需要接口来帮忙）。而多重继承不但会造成冲突，还让一个类变得不伦不类，看不出这个类的本质，所以java毅然舍弃掉了这个祸害。

### 默认调无参
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
### 有参用super
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

### 基类private属性
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
### 派生类的覆盖
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
## 重写与重载
### 重写(Override)  
重写是子类对父类的允许访问的方法的实现过程进行重新编写, 返回值和形参都不能改变

重写方法不能抛出新的检查异常或者比被重写方法申明更加宽泛的异常。例如： 父类的一个方法申明了一个检查异常 IOException，但是在重写这个方法的时候不能抛出 Exception 异常，因为 Exception 是 IOException 的父类，只能抛出 IOException 的子类异常。


#### 编译时与运行时细节
```java
class Animal{
   public void move(){
      System.out.println("动物可以移动");
   }
}
 
class Dog extends Animal{
   public void move(){
      System.out.println("狗可以跑和走");
   }
}
 
public class TestDog{
   public static void main(String args[]){
      Animal a = new Animal(); // Animal 对象
      Animal b = new Dog(); // Dog 对象
 
      a.move();// 执行 Animal 类的方法
 
      b.move();//执行 Dog 类的方法
   }
}
```
输出结果为:

```
动物可以移动
狗可以跑和走
```
尽管b属于Animal类型，但是它运行的是Dog类的move方法。
这是由于在编译阶段，只是检查参数的引用类型。( Animal引用类型 b参数 = new Dog() 实体类/对象 )

然而在运行时，Java虚拟机(JVM)指定对象的类型并且运行该对象的方法。

因此在上面的例子中，之所以能编译成功，是因为Animal类中存在move方法，然而运行时，运行的是特定对象的方法。

```java
class Animal{
   public void move(){
      System.out.println("动物可以移动");
   }
}
 
class Dog extends Animal{
   public void move(){
      System.out.println("狗可以跑和走");
   }
   public void bark(){
      System.out.println("狗可以吠叫");
   }
}
 
public class TestDog{
   public static void main(String args[]){
      Animal a = new Animal(); // Animal 对象
      Animal b = new Dog(); // Dog 对象
 
      a.move();// 执行 Animal 类的方法
      b.move();//执行 Dog 类的方法
      b.bark();
   }
}
```
编译结果:

```
TestDog.java:30: cannot find symbol
symbol  : method bark()
location: class Animal
                b.bark();
                 ^
```
该程序将抛出一个编译错误，因为b的引用类型Animal没有bark方法。

#### 重写规则
重写的方法能够抛出任何非强制异常，无论被重写的方法是否抛出异常。但是，重写的方法不能抛出新的强制性异常，或者比被重写方法声明的更广泛的强制性异常，反之则可以。

构造方法不能被重写。

参数列表必须完全与被重写方法的相同。

返回类型与被重写方法的返回类型可以不相同，但是必须是父类返回值的派生类（java5 及更早版本返回类型要一样，java7 及更高版本可以不同）。

访问权限不能比父类中被重写的方法的访问权限更低。例如：如果父类的一个方法被声明为 public，那么在子类中重写该方法就不能声明为 protected。

声明为 final 的方法不能被重写。

声明为 static 的方法不能被重写，但是能够被再次声明。

子类和父类在同一个包中，那么子类可以重写父类所有方法，除了声明为 private 和 final 的方法。

子类和父类不在同一个包中，那么子类只能够重写父类的声明为 public 和 protected 的非 final 方法。


如果不能继承一个方法，则不能重写这个方法。


#### super关键字

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


### 重载(Overload)

重载(overloading) 是在一个类里面，方法名字相同，而参数不同。返回类型可以相同也可以不同。

每个重载的方法（或者构造函数）都必须有一个独一无二的参数类型列表。

最常用的地方就是构造器的重载。

#### 重载规则:

被重载的方法必须改变参数列表(参数个数或类型不一样)；

被重载的方法可以改变返回类型；

被重载的方法可以改变访问修饰符；

被重载的方法可以声明新的或更广的检查异常；

方法能够在同一个类中或者在一个子类中被重载。

无法以返回值类型作为重载函数的区分标准。

#### 重载和重写的区别
方法重载是一个类的多态性表现,而方法重写是子类与父类的一种多态性表现。

<div align="center"> <img src="pictures\java-core\Snipaste_2019-08-11_19-55-46.jpg" width="600"/> </div><br>

### 重写的向上向下转型
#### 向上转型(子类向上转成父类来用)

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

#### 向下转型

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
### 静态分派和动态分派

TODO

### 重载匹配优先级
```java
public static void main(String[] args) {
        方法重载优先级匹配 a = new 方法重载优先级匹配();
        //普通的重载一般就是同名方法不同参数。
        //这里我们来讨论当同名方法只有一个参数时的情况。
        //此时会调用char参数的方法。
        //当没有char参数的方法。会调用int类型的方法，如果没有int就调用long
        //即存在一个调用顺序char -> int -> long ->double -> ..。
        //当没有基本类型对应的方法时，先自动装箱，调用包装类方法。
        //如果没有包装类方法，则调用包装类实现的接口的方法。
        //最后再调用持有多个参数的char...方法。
        a.eat('a');
        a.eat('a','c','b');
    }
    public void eat(short i) {
        System.out.println("short");
    }
    public void eat(int i) {
        System.out.println("int");
    }
    public void eat(double i) {
        System.out.println("double");
    }
    public void eat(long i) {
        System.out.println("long");
    }
    public void eat(Character c) {
        System.out.println("Character");
    }
    public void eat(Comparable c) {
        System.out.println("Comparable");
    }
    public void eat(char ... c) {
        System.out.println(Arrays.toString(c));
        System.out.println("...");
    }

//    public void eat(char i) {
//        System.out.println("char");
//    }

```
## 泛型

