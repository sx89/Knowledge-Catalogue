



## Scala的“=>”符号简介

`Scala`中的`=>`符号可以看做是创建函数实例的语法糖。例如：`A => T`，`A,B => T`表示一个函数的输入参数类型是“`A`”，“`A,B`”，返回值类型是`T`。请看下面这个实例：

```
scala> val f: Int => String = myInt => "The value of myInt is: " + myInt.toString()
f: Int => String = <function1>

scala> println(f(3))
The value of myInt is: 3
```

上面例子定义函数`f`：输入参数是整数类型，返回值是字符串。

另外，`() => T`表示函数输入参数为空，而`A => Unit`则表示函数没有返回值。



# 关键字Lazy

Scala中使用关键字lazy来定义惰性变量，实现延迟加载(懒加载)。 
惰性变量只能是不可变变量，并且只有在调用惰性变量时，才会去实例化这个变量。

# 作用域保护

Scala中，访问修饰符可以通过使用限定词强调。格式为:

```
private[x] 

或 

protected[x]
```



这里的x指代某个所属的包、类或单例对象。如果写成private[x],读作"这个成员除了对[…]中的类或[…]中的包中的类及它们的伴生对像可见外，对其它所有类都是private。

这种技巧在横跨了若干包的大型项目中非常有用，它允许你定义一些在你项目的若干子包中可见但对于项目外部的客户却始终不可见的东西。

```
package bobsrockets{
    package navigation{
        private[bobsrockets] class Navigator{
         protected[navigation] def useStarChart(){}
         class LegOfJourney{
             private[Navigator] val distance = 100
             }
            private[this] var speed = 200
            }
        }
        package launch{
        import navigation._
        object Vehicle{
        private[launch] val guide = new Navigator
        }
    }
}
```

上述例子中，类Navigator被标记为private[bobsrockets]就是说这个类对包含在bobsrockets包里的所有的类和对象可见。

比如说，从Vehicle对象里对Navigator的访问是被允许的，因为对象Vehicle包含在包launch中，而launch包在bobsrockets中，相反，所有在包bobsrockets之外的代码都不能访问类Navigator。



# Case Class

Case Class一般被翻译成样例类，它是一种特殊的类，能够被优化以用于模式匹配，下面的代码定义了一个样例类：

```scala
//抽象类Person
abstract class Person

//case class Student
case class Student(name:String,age:Int,studentNo:Int) extends Person
//case class Teacher
case class Teacher(name:String,age:Int,teacherNo:Int) extends Person
//case class Nobody
case class Nobody(name:String) extends Person

object CaseClassDemo{
  def main(args: Array[String]): Unit = {
    //case class 会自动生成apply方法，从而省去new操作
    val p:Person=Student("john",18,1024)  
    //match case 匹配语法  
    p  match {
      case Student(name,age,studentNo)=>println(name+":"+age+":"+studentNo)
      case Teacher(name,age,teacherNo)=>println(name+":"+age+":"+teacherNo)
      case Nobody(name)=>println(name)
    }
  }
}


```

当一个类被声名为case class的时候，scala会帮助我们做下面几件事情：
1 构造器中的参数如果不被声明为var的话，它默认的话是val类型的，但一般不推荐将构造器中的参数声明为var
2 自动创建伴生对象，同时在里面给我们实现子apply方法，使得我们在使用的时候可以不直接显示地new对象
3 伴生对象中同样会帮我们实现unapply方法，从而可以将case class应用于模式匹配，关于unapply方法我们在后面的“提取器”那一节会重点讲解
4 实现自己的toString、hashCode、copy、equals方法
除此之此，case class与其它普通的scala类没有区别

## case class在实用应用中的其它用途

某个类一旦被定义为case class，则编译器会自动生成该类的伴生对象，伴生对象中包括了apply方法及unapply方法，apply方法使得我们可以不需要new关键字就可以创建对象，而unapply方法，则使得可以方便地应用在模式匹配当中，另外编译器还自动地帮我们实现对应的toString、equals、copy等方法





# Scala的Option



Scala `Option[T]`是由给定类型的零或一个元素的一种容器。`Option[T]`可以是 `Some [T]`或`None`对象，它代表缺少的值。 例如，如果已找到与给定键对应的值，则Scala的Map的`get`方法会生成`Some(value)`，如果在`Map`中未定义给定的键，则将返回`None`。

`Option`类型在Scala程序中经常使用，可以将其与Java中可用的`null`值进行比较，表示`null`值。 例如，`java.util.HashMap`的`get`方法返回存储在`HashMap`中的值，如果没有找到值，则返回`null`。

假设我们有一种基于主键从数据库中检索记录的方法。

```scala
def findPerson(key: Int): Option[Person]
```

如果找到记录，该方法将返回`Some [Person]`，如果没有找到该记录，则返回`None`。下面来看看看以下程序代码 - 

**示例**

```scala
object Demo {
   def main(args: Array[String]) {
      val capitals = Map("France" -> "Paris", "Japan" -> "Tokyo")

      println("capitals.get( \"France\" ) : " +  capitals.get( "France" ))
      println("capitals.get( \"India\" ) : " +  capitals.get( "India" ))
   }
}

```

将上述程序保存在源文件：*Demo.scala*中，使用以下命令编译和执行此程序。

```shell
D:\>scalac Demo.scala
D:\>scala Demo
capitals.get( "France" ) : Some(Paris)
capitals.get( "India" ) : None

```

将可选值分开的最常见方法是通过模式匹配。例如尝试以下程序 - 

```scala
object Demo {
   def main(args: Array[String]) {
      val capitals = Map("France" -> "Paris", "Japan" -> "Tokyo")

      println("show(capitals.get( \"Japan\")) : " + show(capitals.get( "Japan")) )
      println("show(capitals.get( \"India\")) : " + show(capitals.get( "India")) )
   }

   def show(x: Option[String]) = x match {
      case Some(s) => s
      case None => "?"
   }
}


Scala
```

将上述程序保存在源文件：*Demo.scala*中，使用以下命令编译和执行此程序。

```shell
D:\>scalac Demo.scala
D:\>scala Demo
show(capitals.get( "Japan")) : Tokyo
show(capitals.get( "India")) : ?


Shell
```

## 使用getOrElse()方法

以下是示例程序，显示如何在没有值的情况下使用`getOrElse()`方法来访问值或默认值。

```scala
object Demo {
   def main(args: Array[String]) {
      val a:Option[Int] = Some(5)
      val b:Option[Int] = None 

      println("a.getOrElse(0): " + a.getOrElse(0) )
      println("b.getOrElse(10): " + b.getOrElse(10) )
   }
}


Scala
```

将上述程序保存在源文件：*Demo.scala*中，使用以下命令编译和执行此程序。

```shell
D:\>scalac Demo.scala
D:\>scala Demo
a.getOrElse(0): 5
b.getOrElse(10): 10


Shell
```

## 使用isEmpty()方法

以下是显示如何使用`isEmpty()`方法检查该选项是否为`None`的示例程序。

```scala
object Demo {
   def main(args: Array[String]) {
      val a:Option[Int] = Some(5)
      val b:Option[Int] = None 

      println("a.isEmpty: " + a.isEmpty )
      println("b.isEmpty: " + b.isEmpty )
   }
}


Scala
```

将上述程序保存在源文件：*Demo.scala*中，使用以下命令编译和执行此程序。

```shell
D:\>scalac Demo.scala
D:\>scala Demo
a.isEmpty: false
b.isEmpty: true
```





# 对象与类

## object

在scala中没有静态方法和静态字段，所以在scala中可以用object来实现这些功能，直接用对象名调用的方法都是采用这种实现方式，例如Array.toString。对象的构造器在第一次使用的时候会被调用，如果一个对象从未被使用，那么他的构造器也不会被执行；对象本质上拥有类（scala中）的所有特性，除此之外，object还可以一扩展类以及一个或者多个特质：例如，

```
abstract class ClassName（val parameter）{}

object Test extends ClassName(val parameter){}



trait TraitA{}
trait TraitB{}
trait TraitC{}
object Test1 extends TraitA with TraitB with TraitC{}
注意：object不能提供构造器参数，也就是说object必须是无参的
```

我们通常会定义和使用object的apply方法，有如下情形，apply方法就会被调用，Object（参数1 ，参数2 ，……）这样是隐含的调用apply方法，当然也可以显示的调用，如下Array（"Mary","tong"）,当然也可以这样来写Array.apply（"Mary","tong"）,一般我们提倡隐式的写法。

所有的main方法都必须在object中被调用，来提供程序的主入口，十分简单，不举例说明，处理main方法以外，scala中还提供了扩展App特质，然后将程序代码放入都早起方法体内，如

```
object Hello extends App{

   println（"Hello World ！！！"）

}
```

这样的代码可以直接执行，输出

如果需要命令行参数，可以直接调用args属性来得到，例如

```
def main(args: Array[String]) {
  if (args.length > 0 )
    println("Hello " + args(0))
  else 
    println("Hello World")
}
```

## class

在scala中，类名可以和对象名为同一个名字，该对象称为该类的伴生对象，类和伴生对象可以相互访问他们的私有属性，但是
他们必须在同一个源文件内。类只会被编译，不能直接被执行，类的申明和主构造器在一起被申明，在一个类中，主构造器只有一个，
所有必须在内部申明主构造器或者是其他申明主构造器的辅构造器，主构造器会执行类定义中的所有语句。scala对每个字段都会提供
getter和setter方法，同时也可以显示的申明，但是针对val类型，只提供getter方法，默认情况下，字段为公有类型，可以在setter
方法中增加限制条件来限定变量的变化范围，在scala中方法可以访问改类所有对象的私有字段

## var与val

在 Scala 中，使用关键词 **"var"** 声明变量，使用关键词 **"val"** 声明常量。

声明变量实例如下：

```
var myVar : String = "Foo"
var myVar : String = "Too"
```

以上定义了变量 myVar，我们可以修改它。

声明常量实例如下：

```
val myVal : String = "Foo"
```

以上定义了常量 myVal，它是不能修改的。如果程序尝试修改常量 myVal 的值，程序将会在编译时报错。

## 变量类型引用

在 Scala 中声明变量和常量不一定要指明数据类型，在没有指明数据类型的情况下，其数据类型是通过变量或常量的初始值推断出来的。

所以，如果在没有指明数据类型的情况下声明变量或常量必须要给出其初始值，否则将会报错。

```
var myVar = 10;
val myVal = "Hello, Scala!";
```



# Scala Trait(特征)

Scala Trait(特征) 相当于 Java 的接口，实际上它比接口还功能强大。

与接口不同的是，它还可以定义属性和方法的实现。

一般情况下Scala的类只能够继承单一父类，但是如果是 Trait(特征) 的话就可以继承多个，从结果来看就是实现了多重继承。

Trait(特征) 定义的方式与类类似，但它使用的关键字是 **trait**，如下所示：

```
trait Equal {
  def isEqual(x: Any): Boolean
  def isNotEqual(x: Any): Boolean = !isEqual(x)
}
```

以上Trait(特征)由两个方法组成：**isEqual** 和 **isNotEqual**。isEqual 方法没有定义方法的实现，isNotEqual定义了方法的实现。子类继承特征可以实现未被实现的方法。所以其实 Scala Trait(特征)更像 Java 的抽象类。

以下演示了特征的完整实例：

```
/* 文件名：Test.scala
 * author:菜鸟教程
 * url:www.runoob.com
 */
trait Equal {
  def isEqual(x: Any): Boolean
  def isNotEqual(x: Any): Boolean = !isEqual(x)
}

class Point(xc: Int, yc: Int) extends Equal {
  var x: Int = xc
  var y: Int = yc
  def isEqual(obj: Any) =
    obj.isInstanceOf[Point] &&
    obj.asInstanceOf[Point].x == x
}

object Test {
   def main(args: Array[String]) {
      val p1 = new Point(2, 3)
      val p2 = new Point(2, 4)
      val p3 = new Point(3, 3)

      println(p1.isNotEqual(p2))
      println(p1.isNotEqual(p3))
      println(p1.isNotEqual(2))
   }
}
```

执行以上代码，输出结果为：

```
$ scalac Test.scala 
$ scala Test
false
true
true
```



# with关键字

```
class A extends B with C with D with E 1
```

解读方式：

```
class A extends (B with C with D with E)1
```

*(B with C with D with E)*首先是一个整体，再由 A 去继承。
注：对于Java中的菱形继承问题；Scala的解决策略是：
\1. 继承*class*混入*trait*：(*extends SuperClass with subClass|subTrait with …* ) 必须是一个继承链(左->右)
\2. 继承无关的多*trait*：(*class extends T1 with T2 with …*), T1和T2中相同字段或方法时，会编译错误-inherits conflicting members…(继承成员冲突)。如：

```
trait T1 { val a = 1 }
trait T2 { val b = 2 }
class SubClass extends T1 with T2123
```

编译会报错：Error:(7, 7) class SubClass inherits conflicting members:

------

**复合类型**：

```
T1 with T2 with T3 ...1
```

这种形式的类型-**复合类型**(compound type)，或**交集类型**(intersection type)。

- 在方法声明***参数类型\*** 时使用复合类型：

```
def func (x: T1 with T2) = { println("hello") }

func(new T1 with T2)123
```

- ***type声明\*** 复合类型：

```
type T = X1 with X2

def func (x: T) = { println("hello") }123
```

- 复合类型中使用***结构类型\***：

```
def func (x: X1 with X2 { def hello(): Unit }) = { println("hello") }
```





















































