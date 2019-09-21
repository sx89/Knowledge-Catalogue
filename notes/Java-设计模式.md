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