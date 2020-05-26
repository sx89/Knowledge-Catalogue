



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


Scala
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


Scala
```

将上述程序保存在源文件：*Demo.scala*中，使用以下命令编译和执行此程序。

```shell
D:\>scalac Demo.scala
D:\>scala Demo
capitals.get( "France" ) : Some(Paris)
capitals.get( "India" ) : None


Shell
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

//原文出自【易百教程】，商业转载请联系作者获得授权，非商业转载请保留原文链接：https://www.yiibai.com/scala/scala_options.html 

































































