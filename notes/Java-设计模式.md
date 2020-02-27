<!-- TOC -->

- [动态代理](#动态代理)
    - [定义](#定义)
    - [继承与聚合来扩展某个方法的功能](#继承与聚合来扩展某个方法的功能)
        - [继承](#继承)
        - [聚合](#聚合)
    - [静态代理](#静态代理)
    - [动态代理原理解析](#动态代理原理解析)
        - [三个角色的好处](#三个角色的好处)
    - [简单归纳：](#简单归纳)
    - [动态代理的优点](#动态代理的优点)
- [简单工厂,普通工厂,抽象工厂,](#简单工厂普通工厂抽象工厂)

<!-- /TOC -->


# 动态代理

[参考博客](https://juejin.im/post/5a99048a6fb9a028d5668e62#heading-4)

## 定义

实现InvocationHandler的invoke方法,就可以用handler来代替传入的对象来实现一些方法.而且传入的对象是任意的,要实现的方法也是任意的.

比如,在invoke方法中,实现bird对象的fly()方法,让fly()方法可以打印日志.

## 继承与聚合来扩展某个方法的功能

### 继承

创建一个bird类实现fly方法:

```java
public interface Flyable {
    void fly();
}

public class Bird implements Flyable {

    @Override
    public void fly() {
        System.out.println("Bird is flying...");
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

如果我现在想让fly()不仅要飞,还要计算飞的时间,一种不太理想的做法是创建一个Bird2类继承Bird,重写Bird的fly()方法,在里面实现记录时间:

```java
public class Bird2 extends Bird {

    @Override
    public void fly() {
        long start = System.currentTimeMillis();
        
        super.fly();
        
        long end = System.currentTimeMillis();
        System.out.println("Fly time = " + (end - start));
    }
}

```

弊端:每次对fly()有新的需求,就要新创建一个继承自Bird的子类

### 聚合

还有一种解决方案叫做：聚合。 我们再次创建新类Bird3，在Bird3的构造方法中传入Bird实例。同时，让Bird3也实现Flyable接口，并在fly方法中调用传入的Bird实例的fly方法：

```java
public class Bird3 implements Flyable {
    private Bird bird;

    public Bird3(Bird bird) {
        this.bird = bird;
    }

    @Override
    public void fly() {
        long start = System.currentTimeMillis();

        bird.fly();

        long end = System.currentTimeMillis();
        System.out.println("Fly time = " + (end - start));
    }
}
```

使用继承将导致在对fly()有新的需求时,类无限制扩展，同时灵活性也无法获得保障。那么，使用 聚合 是否可以避免这个问题,但我们的类需要稍微改造一下。修改Bird3类，将聚合对象Bird类型修改为Flyable


```java
//把Bird3更名为BirdLogProxy,改聚合对象Bird为Flyable,然后可以修改内部的
//fly()方法
public class BirdLogProxy implements Flyable {
    private Flyable flyable;

    public BirdLogProxy(Flyable flyable) {
        this.flyable = flyable;
    }

    @Override
    public void fly() {
        System.out.println("Bird fly start...");

        flyable.fly();

        System.out.println("Bird fly end...");
    }
}

```

为什么聚合可以灵活的修改某个方法的功能而不用添加新的子类

<div align="center"> <img src=".\pictures\java-design-patterns\Snipaste_2019-09-06_15-13-20.jpg" width=""/> </div><br>


## 静态代理

接下来，观察上面的类BirdTimeProxy，在它的fly方法中我们直接调用了flyable->fly()方法。换而言之，BirdTimeProxy其实代理了传入的Flyable对象，这就是典型的静态代理实现。  
从表面上看，静态代理已经完美解决了我们的问题。可是，试想一下，如果我们需要计算SDK中100个方法的运行时间，同样的代码至少需要重复100次，并且创建至少100个代理类。往小了说，如果Bird类有多个方法，我们需要知道其他方法的运行时间，同样的代码也至少需要重复多次。因此，静态代理至少有以下两个局限性问题：

如果同时代理多个类，依然会导致类无限制扩展  
如果类中有多个方法，同样的逻辑需要反复实现  

那么，我们是否可以使用同一个代理类来代理任意对象呢？我们以获取方法运行时间为例，是否可以使用同一个类（例如：TimeProxy）来计算任意对象的任一方法的执行时间呢？甚至再大胆一点，代理的逻辑也可以自己指定。比如，获取方法的执行时间，打印日志，这类逻辑都可以自己指定。这就是本文重点探讨的问题，也是最难理解的部分：动态代理。  

## 动态代理原理解析

我们用**代理实例生成代码**+自定义实现InvocationHandler接口的类来生成**代理代码**,在使用的时候把代理代码编译加载进内存即可实现动态代理

图中就是**代理实例生成代码**,它的作用是用反射来生成**代理代码**  
传入的参数是Class inf,即我们要自己实现的InvocationHandler接口

<div align="center"> <img src=".\pictures\java-design-patterns\Snipaste_2019-09-06_15-21-52.jpg" width=""/> </div><br>

我们要实现的InvocationHandler接口的invoke方法:

```java
public class MyInvocationHandler implements InvocationHandler {
    private Bird bird;

    public MyInvocationHandler(Bird bird) {
        this.bird = bird;
    }

    @Override
    public void invoke(Object proxy, Method method, Object[] args) {
        long start = System.currentTimeMillis();

        try {
            method.invoke(bird, new Object[] {});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println("Fly time = " + (end - start));
    }
}

```

最终生成的动态代理代码:

```java
package com.youngfeng.proxy;

import java.lang.Override;
import java.lang.reflect.Method;

public class TimeProxy implements Flyable {
  private InvocationHandler handler;

  public TimeProxy(InvocationHandler handler) {
    this.handler = handler;
  }

  @Override
  public void fly() {
    try {
    	Method method = com.youngfeng.proxy.Flyable.class.getMethod("fly");
    	this.handler.invoke(this, method, null);
    } catch(Exception e) {
    	e.printStackTrace();
    }
  }
}

最后的方法栈的调用关系如图:

```

<div align="center"> <img src=".\pictures\java-design-patterns\Snipaste_2019-09-06_16-01-12.jpg" width=""/> </div><br>

在静态代理部分，我们在代理类中传入了被代理对象。可是，使用newProxyInstance生成动态代理对象的时候，我们居然不再需要传入被代理对象了。我们传入了的实际对象是InvocationHandler实现类的实例，这看起来有点像生成了InvocationHandler的代理对象，在动态生成的代理类的任意方法中都会间接调用InvocationHandler->invoke(proxy, method, args)方法。

其实的确是这样。TimeProxy真正代理的对象就是InvocationHandler，不过这里设计的巧妙之处在于，InvocationHandler是一个接口，真正的实现由用户指定。另外，在每一个方法执行的时候，invoke方法都会被调用 ，这个时候如果你需要对某个方法进行自定义逻辑处理，可以根据method的特征信息进行判断分别处理。

### 三个角色的好处

这样设计有什么好处呢？

如果我们还需要对其它任意对象进行代理，是否还需要改动newProxyInstance方法的源码，答案是：完全不需要！

只要你在newProxyInstance方法中指定代理需要实现的接口，指定用于自定义处理的InvocationHandler对象，整个代理的逻辑处理都在你自定义的InvocationHandler实现类中进行处理。至此，而我们终于可以从不断地写代理类用于实现自定义逻辑的重复工作中解放出来了，从此需要做什么，交给InvocationHandler。

事实上，我们之前给自己定下的目标“使用同一个类来计算任意对象的任一方法的执行时间”已经实现了。严格来说，是我们超额完成了任务，TimeProxy不仅可以计算方法执行的时间，也可以打印方法执行日志，这完全取决于你的InvocationHandler接口实现。因此，这里取名为TimeProxy其实已经不合适了。我们可以修改为和JDK命名一致，即$Proxy0.


## 简单归纳：

Proxy->newProxyInstance(infs, handler) 用于生成代理对象
InvocationHandler：这个接口主要用于自定义代理逻辑处理
为了完成对被代理对象的方法拦截，我们需要在InvocationHandler对象中传入被代理对象实例


## 动态代理的优点

动态代理我们居然可以在不改变源码的情况下，直接在方法中插入自定义逻辑。这有点不太符合我们的一条线走到底的编程逻辑，这种编程模型有一个专业名称叫 AOP。所谓的AOP，就像刀一样，抓住时机，趁机插入。


基于这样一种动态特性，我们可以用它做很多事情，例如：

事务提交或回退（Web开发中很常见）  
权限管理  
自定义缓存逻辑处理   
SDK Bug修复 ...  

# 简单工厂,普通工厂,抽象工厂,

https://www.zhihu.com/question/20367734







# JDK中的设计模式

## 工厂模式

创建对象而不将实例化逻辑暴露给客户端，并通过公共接口引用新创建的对象。

例如：

- java.lang.Object#toString() (在所有子类中重写)
- java.lang.Class#newInstance()
- java.lang.Integer#valueOf(String)
- java.lang.Class#forName()
- java.lang.reflect.Array#newInstance()
- java.lang.reflect.Constructor#newInstance()




## 抽象工厂

javax.xml.parsers.DocumentBuilderFactory抽象类
public static DocumentBuilderFactory newInstance()方法
类功能：使得应用程序可以通过XML文件，获得一个能生成DOM对象的解析器。
方法功能：获取一个DocumentBuilderFactory的新实例。这一静态方法会创建一个新的工厂实例。



## 单例模式

java.lang.RunTime类
public static Runtime getRuntime()
类功能：每一个运行的java应用都会有一个唯一的RunTime类的实例，这个实例使得应用程序在运行期间能够受到运行环境的影响。
方法功能：返回一个和当前java应用关联的RunTime对象。
实现方式：

```java
private static Runtime currentRuntime = new Runtime();  

public static Runtime getRuntime() {
    return currentRuntime;
}  
```

## 适配器模式

java.util.Arrays
public static List asList(T… a)方法
类功能：此类包含了大量对数组操作的方法。
方法功能：将一个引用类型的数组转为一个List。从而可以使用List类的操作来操作数组对象，但是有一点要注意：就是不能使用add(),remove()操作，因为返回的list底层是基于数组的，数组结构是不能更改的。 list类就是这里的适配器，通过这个适配器，对数组的直接操作变为间接操作。



## 装饰器模式

- Reader抽象类和Writer抽象类有相同的构造器函数。
- 构造器函数
- 类功能：Reader抽象类用于读一个字符集流；Writer抽象类用于写一个字符集流。
- 方法功能：就是构造函数的功能

## 享元模式

java.lang.Integer(其它基本类型包装类(除去Float,Double)也如此,还有BigDecimal)
Integer.valueOf()方法
byte，short，int，long，boolean，char的包装型在类加载到JVM时，已经缓存了制定范围的对象引用，因为值的设定使用的是static块或者常量。其中char的范围为：0～127；boolean的值为true和false；其它默认范围都是－127～128。其中int的上限127可以调整，这需要调整JVM的参数。
同时利用了享元模式的还有String这个类，因为生存的每个字符串都是不可变的。



## 代理模式

java.lang.reflect.Proxy类
原理：代理提供了一个static方法用于创建一个动态代理类和被代理类的实例。它是所有通过此方式创建动态代理类的父类。
使用方式：

```java
InvocationHandler handler = new MyInvocationHandler(...);
class proxyClass = Proxy.getProxyClass(Foo.class.getClassLoader(), Foo.class);
Foo f = (Foo) proxyClass.getConstructor(InvocationHandler.class).newInstance(handler);
```

也可以更简单的写为:

```java
Foo f = (Foo) Proxy.newProxyInstance(Foo.class.getClassLoader(),new Class[]Foo.class},handler); 
```

   


## 职责链模式

java.util.logging.Logger类
public void log(LogRecord record)
类功能：为系统or组件记录日志消息。如何体现了职责链模式：每个记录器都跟踪“父”记录器，所谓”父”记录器，就是Logger命名空间中最近的现有祖先。
方法功能：用于记录日志信息。这一类中所有其它的日志方法都是通过调用这一方法实现日志记录的。子类能够覆写这一方法从而获取所有的日志行为。



## 命令模式

java.lang.Runnable
所有对Runable接口的实现类
类功能：实现Runable接口的类，可以被线程执行。
如果体现了命令模式：通过实现Runable接口的类，将请求封装为一个对象，对请求排队或记录请求日志，以及支持可撤销操作。允许接受请求的一方决定是否要否决请求，最重要一点就是：命令模式把请求一个操作的对象和怎么执行一个操作的对象解耦。这就是Excutor框架执行实现Runable接口任务类的体现。

## 解释器模式

java.text.Format
类功能：此抽象类用于格式化一些格式敏感的信息，如：日期，信息，数字等。
实现方式：此类定义了一些方法，用于：将格式敏感的信息转为String。



## 迭代器模式

这个就不说了，很多集合已经使用了迭代器进行遍历

## 备忘录模式

java.io.Serializable接口
接口功能：只有实现了Serializable接口的类才能序列化，此接口中没有任何方法，只是为类标记实现了此接口的类可以进行序列化。而如果一个类想要序列化，除了实现这个接口外，还要自己写ReadObject()，WriteObject()方法，用于对流的读取和输出。

## 观察者模式

类观察到变量的变化,让类们自动改变

# Spring中的设计模式



## 工厂设计模式

定义一个创建对象的接口，让其子类自己决定实例化哪一个工厂类，工厂模式使其创建过程延迟到子类进行。

![](/Users/sunxu/mycode/Java-notes/notes/pictures/java-design-patterns/Snipaste_2020-02-23_16-13-52.png)

在工厂模式中，我们在创建对象时不会对客户端暴露创建逻辑，并且是通过使用一个共同的接口来指向新创建的对象。

Spring使用工厂模式可以通过 `BeanFactory` 或 `ApplicationContext` 创建 bean 对象。简单工厂模式的实质是由一个工厂类根据传入的参数，动态决定应该创建哪一个产品类。 spring中的BeanFactory就是简单工厂模式的体现，根据传入一个唯一的标识来获得bean对象，但是否是在传入参数后创建还是传入参数前创建这个要根据具体情况来定。

**两者对比：**

- `BeanFactory` ：延迟注入(使用到某个 bean 的时候才会注入),相比于`BeanFactory` 来说会占用更少的内存，程序启动速度更快。
- `ApplicationContext` ：容器启动的时候，不管你用没用到，一次性创建所有 bean 。`BeanFactory` 仅提供了最基本的依赖注入支持，`ApplicationContext` 扩展了 `BeanFactory` ,除了有`BeanFactory`的功能还有额外更多功能，所以一般开发人员使用`ApplicationContext`会更多。





## 单例模式（Singleton）

**Spring 中 bean 的默认作用域就是 singleton(单例)的。** 除了 singleton 作用域，Spring 中 bean 还有下面几种作用域：

- prototype : 每次请求都会创建一个新的 bean 实例。
- request : 每一次HTTP请求都会产生一个新的bean，该bean仅在当前HTTP request内有效。
- session : 每一次HTTP请求都会产生一个新的 bean，该bean仅在当前 HTTP session 内有效。
- global-session： 全局session作用域，仅仅在基于portlet的web应用中才有意义，Spring5已经没有了。Portlet是能够生成语义代码(例如：HTML)片段的小型Java Web插件。它们基于portlet容器，可以像servlet一样处理HTTP请求。但是，与 servlet 不同，每个 portlet 都有不同的会话

**Spring 实现单例的方式：**

- xml : `<bean id="userService" class="top.snailclimb.UserService" scope="singleton"/>`
- 注解：`@Scope(value = "singleton")`

**Spring 通过 `ConcurrentHashMap` 实现单例注册表的特殊方式实现单例模式。Spring 实现单例的核心代码如下**

```
// 通过 ConcurrentHashMap（线程安全） 实现单例注册表
private final Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>(64);

public Object getSingleton(String beanName, ObjectFactory<?> singletonFactory) {
        Assert.notNull(beanName, "'beanName' must not be null");
        synchronized (this.singletonObjects) {
            // 检查缓存中是否存在实例  
            Object singletonObject = this.singletonObjects.get(beanName);
            if (singletonObject == null) {
                //...省略了很多代码
                try {
                    singletonObject = singletonFactory.getObject();
                }
                //...省略了很多代码
                // 如果实例对象在不存在，我们注册到单例注册表中。
                addSingleton(beanName, singletonObject);
            }
            return (singletonObject != NULL_OBJECT ? singletonObject : null);
        }
    }
    //将对象添加到单例注册表
    protected void addSingleton(String beanName, Object singletonObject) {
            synchronized (this.singletonObjects) {
                this.singletonObjects.put(beanName, (singletonObject != null ? singletonObject : NULL_OBJECT));

            }
        }
}
```




## 代理设计模式

### 代理模式在 AOP 中的应用

AOP(Aspect-Oriented Programming:面向切面编程)能够将那些与业务无关，**却为业务模块所共同调用的逻辑或责任（例如事务处理、日志管理、权限控制等）封装起来**，便于**减少系统的重复代码**，**降低模块间的耦合度**，并**有利于未来的可拓展性和可维护性**。

**Spring AOP 就是基于动态代理的**，如果要代理的对象，实现了某个接口，那么Spring AOP会使用**JDK Proxy**，去创建代理对象，而对于没有实现接口的对象，就无法使用 JDK Proxy 去进行代理了，这时候Spring AOP会使用**Cglib** ，这时候Spring AOP会使用 **Cglib** 生成一个被代理对象的子类来作为代理





## 适配器模式

适配器模式(Adapter Pattern) 将一个接口转换成客户希望的另一个接口，适配器模式使接口不兼容的那些类可以一起工作，其别名为包装器(Wrapper)。

### spring AOP中的适配器模式

我们知道 Spring AOP 的实现是基于代理模式，但是 Spring AOP 的增强或通知(Advice)使用到了适配器模式，与之相关的接口是`AdvisorAdapter` 。Advice 常用的类型有：`BeforeAdvice`（目标方法调用前,前置通知）、`AfterAdvice`（目标方法调用后,后置通知）、`AfterReturningAdvice`(目标方法执行结束后，return之前)等等。每个类型Advice（通知）都有对应的拦截器:`MethodBeforeAdviceInterceptor`、`AfterReturningAdviceAdapter`、`AfterReturningAdviceInterceptor`。Spring预定义的通知要通过对应的适配器，适配成 `MethodInterceptor`接口(方法拦截器)类型的对象（如：`MethodBeforeAdviceInterceptor` 负责适配 `MethodBeforeAdvice`）。

### spring MVC中的适配器模式

在Spring MVC中，`DispatcherServlet` 根据请求信息调用 `HandlerMapping`，解析请求对应的 `Handler`。解析到对应的 `Handler`（也就是我们平常说的 `Controller` 控制器）后，开始由`HandlerAdapter` 适配器处理。`HandlerAdapter` 作为期望接口，具体的适配器实现类用于对目标类进行适配，`Controller` 作为需要适配的类。

**为什么要在 Spring MVC 中使用适配器模式？** Spring MVC 中的 `Controller` 种类众多，不同类型的 `Controller` 通过不同的方法来对请求进行处理。如果不利用适配器模式的话，`DispatcherServlet` 直接获取对应类型的 `Controller`，需要的自行来判断，像下面这段代码一样：

```
if(mappedHandler.getHandler() instanceof MultiActionController){  
   ((MultiActionController)mappedHandler.getHandler()).xxx  
}else if(mappedHandler.getHandler() instanceof XXX){  
    ...  
}else if(...){  
   ...  
}  
复制代码
```

假如我们再增加一个 `Controller`类型就要在上面代码中再加入一行 判断语句，这种形式就使得程序难以维护，也违反了设计模式中的开闭原则 – 对扩展开放，对修改关闭。

## 装饰者模式

装饰者模式可以动态地给对象添加一些额外的属性或行为。相比于使用继承，装饰者模式更加灵活。简单点儿说就是当我们需要修改原有的功能，但我们又不愿直接去修改原有的代码时，设计一个Decorator套在原有代码外面。其实在 JDK 中就有很多地方用到了装饰者模式，比如 `InputStream`家族，`InputStream` 类下有 `FileInputStream` (读取文件)、`BufferedInputStream` (增加缓存,使读取文件速度大大提升)等子类都在不修改`InputStream` 代码的情况下扩展了它的功能。

 

## 责任链模式



监听器 过滤器.

或者打印日志.