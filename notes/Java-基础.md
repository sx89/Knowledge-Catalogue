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

## 基类与派生类
派生类具有和基类相同的属性,方法;还可以自己增加一些额外的属性和方法

派生类的构造器会必须调用基类的构造器,如果不指定,则默认调用无参构造器(所以基类中推荐要有无参构造器)

###   在无参构造器时， java会自动在派生类的构造器中插入对基类的构造器的调用

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
### 构造器有参数时，那就必须使用关键字super现实地编写调用基类构造器的代码，并且匹配适当的参数列表。(要注意的是，super必须在构造器的最前面，不然会报错。


)

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

### 如果派生类使用了访问修饰符,则派生类要想访问基类里面被private和default修饰的属性,需要用getter和setter

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
### 如果派生类定义了和基类一样的属性或方法，将覆盖基类的属性和方法。

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

 
