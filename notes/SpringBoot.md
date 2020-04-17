<!-- TOC -->

- [切面编程，Aspect Oriented Programming](#%e5%88%87%e9%9d%a2%e7%bc%96%e7%a8%8baspect-oriented-programming)
  - [spring定义](#spring%e5%ae%9a%e4%b9%89)
  - [AOP例子](#aop%e4%be%8b%e5%ad%90)
- [依赖注入与控制反转](#%e4%be%9d%e8%b5%96%e6%b3%a8%e5%85%a5%e4%b8%8e%e6%8e%a7%e5%88%b6%e5%8f%8d%e8%bd%ac)
  - [控制反转介绍](#%e6%8e%a7%e5%88%b6%e5%8f%8d%e8%bd%ac%e4%bb%8b%e7%bb%8d)
  - [IoC能做什么](#ioc%e8%83%bd%e5%81%9a%e4%bb%80%e4%b9%88)
  - [依赖注入（DI，DI—Dependency Injection）](#%e4%be%9d%e8%b5%96%e6%b3%a8%e5%85%a5dididependency-injection)
  - [控制反转与依赖注入的理解](#%e6%8e%a7%e5%88%b6%e5%8f%8d%e8%bd%ac%e4%b8%8e%e4%be%9d%e8%b5%96%e6%b3%a8%e5%85%a5%e7%9a%84%e7%90%86%e8%a7%a3)
- [切面编程实现原理:发射与动态代理](#%e5%88%87%e9%9d%a2%e7%bc%96%e7%a8%8b%e5%ae%9e%e7%8e%b0%e5%8e%9f%e7%90%86%e5%8f%91%e5%b0%84%e4%b8%8e%e5%8a%a8%e6%80%81%e4%bb%a3%e7%90%86)
- [常用注解](#%e5%b8%b8%e7%94%a8%e6%b3%a8%e8%a7%a3)
  - [@AutoWired](#autowired)
- [springboot热部署的两种方式](#springboot%e7%83%ad%e9%83%a8%e7%bd%b2%e7%9a%84%e4%b8%a4%e7%a7%8d%e6%96%b9%e5%bc%8f)
- [spring mvc的执行流程](#spring-mvc%e7%9a%84%e6%89%a7%e8%a1%8c%e6%b5%81%e7%a8%8b)
  - [组件说明：](#%e7%bb%84%e4%bb%b6%e8%af%b4%e6%98%8e)
  - [SpringMVC执行流程:](#springmvc%e6%89%a7%e8%a1%8c%e6%b5%81%e7%a8%8b)

<!-- /TOC -->

# SpringMVC的流程

https://www.jianshu.com/p/8a20c547e245

**SpringMVC执行流程:**
 1.用户发送请求至前端控制器DispatcherServlet
 2.DispatcherServlet收到请求调用处理器映射器HandlerMapping。
 3.处理器映射器根据请求url找到具体的处理器，生成处理器执行链HandlerExecutionChain(包括处理器对象和处理器拦截器)一并返回给DispatcherServlet。
 4.DispatcherServlet根据处理器Handler获取处理器适配器HandlerAdapter执行HandlerAdapter处理一系列的操作，如：参数封装，数据格式转换，数据验证等操作
 5.执行处理器Handler(Controller，也叫页面控制器)。
 6.Handler执行完成返回ModelAndView
 7.HandlerAdapter将Handler执行结果ModelAndView返回到DispatcherServlet
 8.DispatcherServlet将ModelAndView传给ViewReslover视图解析器
 9.ViewReslover解析后返回具体View
 10.DispatcherServlet对View进行渲染视图（即将模型数据model填充至视图中）。
 11.DispatcherServlet响应用户。

## 组件说明：

1.DispatcherServlet：前端控制器。用户请求到达前端控制器，它就相当于mvc模式中的c，dispatcherServlet是整个流程控制的中心，由它调用其它组件处理用户的请求，dispatcherServlet的存在降低了组件之间的耦合性,系统扩展性提高。由框架实现
 2.HandlerMapping：处理器映射器。HandlerMapping负责根据用户请求的url找到Handler即处理器，springmvc提供了不同的映射器实现不同的映射方式，根据一定的规则去查找,例如：xml配置方式，实现接口方式，注解方式等。由框架实现
 3.Handler：处理器。Handler 是继DispatcherServlet前端控制器的后端控制器，在DispatcherServlet的控制下Handler对具体的用户请求进行处理。由于Handler涉及到具体的用户业务请求，所以一般情况需要程序员根据业务需求开发Handler。
 4.HandlAdapter：处理器适配器。通过HandlerAdapter对处理器进行执行，这是适配器模式的应用，通过扩展适配器可以对更多类型的处理器进行执行。由框架实现。
 5.ModelAndView是springmvc的封装对象，将model和view封装在一起。
 6.ViewResolver：视图解析器。ViewResolver负责将处理结果生成View视图，ViewResolver首先根据逻辑视图名解析成物理视图名即具体的页面地址，再生成View视图对象，最后对View进行渲染将处理结果通过页面展示给用户。
 7View:是springmvc的封装对象，是一个接口, springmvc框架提供了很多的View视图类型，包括：jspview，pdfview,jstlView、freemarkerView、pdfView等。一般情况下需要通过页面标签或页面模版技术将模型数据通过页面展示给用户，需要由程序员根据业务需求开发具体的页面。



# 切面编程，Aspect Oriented Programming

## spring定义

从功能的角度来定义的，
从本质意义上来讲，Spring是一个库，一个Java库，，它的功能是提供了一个软件框架，这个框架目的是使软件之间的逻辑更加清晰，配置更灵活，实现这个目的的手段使用AOP和IoC，而AOP和IoC是一种思想,AOP在Java里是利用反射机制实现（你也可以认为是动态代理，不过动态代理也是反射机制实现的，所以还是先不要管动态代理

如下图所示,去银行取款或者查询余额,都要经过一些类似的步骤,我们要把相同的步骤集中起来管理,当作一个个切面;把不同的部分当来进行灵活更换的组件.


<div align="center"> <img src=".\pictures\spring-boot\Snipaste_2019-09-12_00-02-25.jpg " width="250px"> </div><br>

没有想过可以把这个验证用户的代码是提取出来，不放到主流程里去呢，这就是AOP的作用了，有了AOP，你写代码时不要把这个验证用户步骤写进去，即完全不考虑验证用户，你写完之后，在另我一个地方，写好验证用户的代码，然后告诉Spring你要把这段代码加到哪几个地方，Spring就会帮你加过去，而不要你自己Copy过去，这里还是两个地方，如果你有多个控制流呢，这个写代码的方法可以大大减少你的时间

不过AOP的目的不是这样，这只是一个“副作用”，真正目的是，你写代码的时候，事先只需考虑主流程，而不用考虑那些不重要的流程.因为在主流程写好之后,可以用aop把不重要的代码加到主要流程的前后,不会影响源码,但会生成主流程前后都存在通用功能切面的机器码.



## 

## AOP例子
[参考博客](http://www.blogjava.net/javadragon/archive/2006/12/03/85115.html)

参考视频教程:https://www.bilibili.com/video/BV1KT4y1G7hs?from=search&seid=16047420821874007149

1.注解定义一个pointCut,

2.在pointCut那里实现一个方法,该方法的的触发时间由时间注解决定:

3.时间注解: @before @after @环绕  @return @Exception

<img src="pictures/SpringBoot/image-20200417190728179.png" alt="image-20200417190728179" style="zoom:50%;" />



# 依赖注入与控制反转
## 控制反转介绍

Ioc—Inversion of Control，即“控制反转”，不是什么技术，而是一种设计思想。在Java开发中，Ioc意味着将你设计好的对象交给容器控制，而不是传统的在你的对象内部直接控制。


谁控制谁，控制什么：传统Java SE程序设计，我们直接在对象内部通过new进行创建对象，是程序主动去创建依赖对象；而IoC是有专门一个容器来创建这些对象，即由Ioc容器来控制对 象的创建；谁控制谁？当然是IoC 容器控制了对象；控制什么？那就是主要控制了外部资源获取（不只是对象包括比如文件等）。

为何是反转，哪些方面反转了：有反转就有正转，传统应用程序是由我们自己在对象中主动控制去直接获取依赖对象，也就是正转；而反转则是由容器来帮忙创建及注入依赖对象；为何是反转？因为由容器帮我们查找及注入依赖对象，对象只是被动的接受依赖对象，所以是反转；哪些方面反转了？依赖对象的获取被反转了。


## IoC能做什么

IoC 不是一种技术，只是一种思想，一个重要的面向对象编程的法则，它能指导我们如何设计出松耦合、更优良的程序。传统应用程序都是由我们在类内部主动创建依赖对象，从而导致类与类之间高耦合，难于测试；有了IoC容器后，把创建和查找依赖对象的控制权交给了容器，由容器进行注入组合对象，所以对象与对象之间是 松散耦合，这样也方便测试，利于功能复用，更重要的是使得程序的整个体系结构变得非常灵活。

　　其实IoC对编程带来的最大改变不是从代码上，而是从思想上，发生了“主从换位”的变化。应用程序原本是老大，要获取什么资源都是主动出击，但是在IoC/DI思想中，应用程序就变成被动的了，被动的等待IoC容器来创建并注入它所需要的资源了。

　　IoC很好的体现了面向对象设计法则之一—— 好莱坞法则：“别找我们，我们找你”；即由IoC容器帮对象找相应的依赖对象并注入，而不是由对象主动去找。


## 依赖注入（DI，DI—Dependency Injection）

即“依赖注入”：组件之间依赖关系由容器在运行期决定，形象的说，即由容器动态的将某个依赖关系注入到组件之中。依赖注入的目的并非为软件系统带来更多功能，而是为了提升组件重用的频率，并为系统搭建一个灵活、可扩展的平台。通过依赖注入机制，我们只需要通过简单的配置，而无需任何代码就可指定目标需要的资源，完成自身的业务逻辑，而不需要关心具体的资源来自何处，由谁实现。

　　理解DI的关键是：“谁依赖谁，为什么需要依赖，谁注入谁，注入了什么”，那我们来深入分析一下： 


+ 谁依赖于谁：当然是应用程序依赖于IoC容器；

+ 为什么需要依赖：应用程序需要IoC容器来提供对象需要的外部资源；

+ 谁注入谁：很明显是IoC容器注入应用程序某个对象，应用程序依赖的对象；

+ 注入了什么：**就是注入某个对象所需要的外部资源（包括对象、资源、常量数据）**。

　　IoC和DI由什么关系呢？其实它们是同一个概念的不同角度描述，由于控制反转概念比较含糊（可能只是理解为容器控制对象这一个层面，很难让人想到谁来维护对象关系），所以2004年大师级人物Martin Fowler又给出了一个新的名字：“依赖注入”，相对IoC 而言，“依赖注入”明确描述了“被注入对象依赖IoC容器配置依赖对象”。

**依赖注入是由反射实现的**

## 控制反转与依赖注入的理解
在平时的java应用开发中，我们要实现某一个功能或者说是完成某个业务逻辑时至少需要两个或以上的对象来协作完成，在没有使用Spring的时候，每个对象在需要使用他的合作对象时，自己均要使用像new object() 这样的语法来将合作对象创建出来，这个合作对象是由自己主动创建出来的，创建合作对象的主动权在自己手上，自己需要哪个合作对象，就主动去创建，创建合作对象的主动权和创建时机是由自己把控的，而这样就会使得对象间的耦合度高了，A对象需要使用合作对象B来共同完成一件事，A要使用B，那么A就对B产生了依赖，也就是A和B之间存在一种耦合关系，并且是紧密耦合在一起，而使用了Spring之后就不一样了，创建合作对象B的工作是由Spring来做的，Spring创建好B对象，然后存储到一个容器里面，当A对象需要使用B对象时，Spring就从存放对象的那个容器里面取出A要使用的那个B对象，然后交给A对象使用，至于Spring是如何创建那个对象，以及什么时候创建好对象的，A对象不需要关心这些细节问题(你是什么时候生的，怎么生出来的我可不关心，能帮我干活就行)，A得到Spring给我们的对象之后，两个人一起协作完成要完成的工作即可。

　　**所以控制反转IoC(Inversion of Control)是说创建对象的控制权进行转移**，以前创建对象的主动权和创建时机是由自己把控的，而现在这种权力转移到第三方，比如转移交给了IoC容器，它就是一个专门用来创建对象的工厂，你要什么对象，它就给你什么对象，有了 IoC容器，依赖关系就变了，原先的依赖关系就没了，它们都依赖IoC容器了，通过IoC容器来建立它们之间的关系。

　　这是我对Spring的IoC(控制反转)的理解。DI(依赖注入)其实就是IOC的另外一种说法，DI是由Martin Fowler 在2004年初的一篇论文中首次提出的。他总结：控制的什么被反转了？就是：获得依赖对象的方式反转了


# 切面编程实现原理:发射与动态代理

proxy

# 常用注解

## @AutoWired
作用:@Autowired表示被修饰的类需要注入对象,spring会扫描所有被@Autowired标注的类,然后根据 类型 在ioc容器中找到匹配的类注入。


# springboot热部署的两种方式
https://blog.csdn.net/a656678879/article/details/80356323

# spring mvc的执行流程


## 组件说明：
DispatcherServlet：前端控制器。用户请求到达前端控制器，它就相当于MVC模式中的C，DispatcherServlet是整个流程控制的中心，由它调用其它组件处理用户的请求，DispatcherServlet的存在降低了组件之间的耦合性,系统扩展性提高。由框架实现

HandlerMapping：处理器映射器。HandlerMapping负责根据用户请求的url找到Handler即处理器，Spring MVC提供了不同的映射器实现不同的映射方式，根据一定的规则去查找,例如：xml配置方式，实现接口方式，注解方式等。由框架实现

Handler：处理器。Handler 是继DispatcherServlet前端控制器的后端控制器，在DispatcherServlet的控制下Handler对具体的用户请求进行处理。由于Handler涉及到具体的用户业务请求，所以一般情况需要程序员根据业务需求开发Handler。

HandlAdapter：处理器适配器。通过HandlerAdapter对处理器进行执行，这是适配器模式的应用，通过扩展适配器可以对更多类型的处理器进行执行。由框架实现。

ModelAndView是Spring MVC的封装对象，将Model和View封装在一起。

ViewResolver：视图解析器。ViewResolver负责将处理结果生成View视图，ViewResolver首先根据逻辑视图名解析成物理视图名即具体的页面地址，再生成View视图对象，最后对View进行渲染将处理结果通过页面展示给用户。

View:是Spring MVC的封装对象，是一个接口, Spring MVC框架提供了很多的View视图类型，包括：jspview，pdfview,jstlView、freemarkerView、pdfView等。一般情况下需要通过页面标签或页面模版技术将模型数据通过页面展示给用户，需要由程序员根据业务需求开发具体的页面。

## SpringMVC执行流程:

1.用户发送请求至前端控制器DispatcherServlet

2.DispatcherServlet收到请求调用处理器映射器HandlerMapping。

3.处理器映射器根据请求url找到具体的处理器，生成处理器执行链HandlerExecutionChain(包括处理器对象和处理器拦截器)一并返回给DispatcherServlet。

4.DispatcherServlet根据处理器Handler获取处理器适配器HandlerAdapter执行HandlerAdapter处理一系列的操作，如：参数封装，数据格式转换，数据验证等操作

5.执行处理器Handler(Controller，也叫页面控制器)。

6.Handler执行完成返回ModelAndView

7.HandlerAdapter将Handler执行结果ModelAndView返回到DispatcherServlet

8.DispatcherServlet将ModelAndView传给ViewReslover视图解析器

9.ViewReslover解析后返回具体View

10.DispatcherServlet对View进行渲染视图（即将模型数据model填充至视图中）。

11.DispatcherServlet响应用户。



# spring cloud使用Feign实现远程接口的调用	

在开发中，我们常用httpClient去远程调用其他系统的接口，一般情况下，需要我们指定调用的url，feign也实现了一套远程调用的方法，并且更为优雅。
1.添加依赖

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-feign</artifactId>
</dependency>
```


2.创建FeignClient接口（用于指定远程调用的服务）

2.创建FeignClient接口（用于指定远程调用的服务）

```
// 申明这是一个Feign客户端，并且指明服务id
@FeignClient(value = "com-spring-caclulate") 
public interface CacluFeignClient {

 // 这里定义了类似于SpringMVC用法的方法，就可以进行RESTful的调用了
@RequestMapping(value = "/caclu/{num}", method = RequestMethod.GET)
public Item caclulate(@PathVariable("num") Integer num);

}
```

注意：这里就是一个接口。
3.在需要进行远程调用的方法里注入该接口，并调用对应的api接口方法

注意：这里就是一个接口。
3.在需要进行远程调用的方法里注入该接口，并调用对应的api接口方法

```
@Autowired
private CacluFeignClient cacluFeignClient ;

@GetMapping(value = "query/result")
public Integer caclulate() {
    cacluFeignClient.caclulate(1);
}
```

4.在启动类上添加注解 @EnableFeignClients，表示支持Feign

FeignClient接口和spring mvc接口的格式一致，在调用方的方法中，我们只需要调用本系统中定义的接口即可。




# bean

## 产生机制



## 作用域

| Scope                                                        | Description                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [singleton](https://docs.spring.io/spring/docs/5.1.4.RELEASE/spring-framework-reference/core.html#beans-factory-scopes-singleton) | （默认的）在每个`Spring IoC`容器中，一个`bean`定义对应只会有唯一的一个`bean`实例。 |
| [prototype](https://docs.spring.io/spring/docs/5.1.4.RELEASE/spring-framework-reference/core.html#beans-factory-scopes-prototype) | 一个`bean`定义可以有多个`bean`实例。                         |
| [request](https://docs.spring.io/spring/docs/5.1.4.RELEASE/spring-framework-reference/core.html#beans-factory-scopes-request) | 一个`bean`定义对应于单个`HTTP` 请求的生命周期。也就是说，每个`HTTP` 请求都有一个`bean`实例，且该实例仅在这个`HTTP` 请求的生命周期里有效。该作用域仅适用于`WebApplicationContext`环境。 |
| [session](https://docs.spring.io/spring/docs/5.1.4.RELEASE/spring-framework-reference/core.html#beans-factory-scopes-session) | 一个`bean` 定义对应于单个`HTTP Session` 的生命周期，也就是说，每个`HTTP Session` 都有一个`bean`实例，且该实例仅在这个`HTTP Session` 的生命周期里有效。该作用域仅适用于`WebApplicationContext`环境。 |
| [application](https://docs.spring.io/spring/docs/5.1.4.RELEASE/spring-framework-reference/core.html#beans-factory-scopes-application) | 一个`bean` 定义对应于单个`ServletContext` 的生命周期。该作用域仅适用于`WebApplicationContext`环境。 |
| [websocket](https://docs.spring.io/spring/docs/5.1.4.RELEASE/spring-framework-reference/web.html#websocket-stomp-websocket-scope) | 一个`bean` 定义对应于单个`websocket` 的生命周期。该作用域仅适用于`WebApplicationContext`环境。 |



# 开箱即用,自动配置的原理

Spring Boot 内部提供了很多自动化配置的类这些自动化配置的类会判断 classpath 中是否存在自己需要的那个类，如果存在则会自动配置相关的配置，否则就不会自动配置，因此，开发者在 Maven 的 pom 文件中添加相关依赖后，这些依赖就会下载很多 jar 包到 classpath 中，有了这些 lib 就会触发自动化配置。



pring Boot提供了很多“开箱即用”的依赖模块，都是以spring-boot-starter-xx作为命名的。例如，之前提到的 spring-boot-starter-redis、spring-boot-starter-data-mongodb、spring-boot-starter-data-elasticsearch 等。

Spring Boot 的开箱即用是一个很棒的设计，给开发者带来很大的便利。开发者只要在 Maven 的 pom 文件中添加相关依赖后，Spring Boot 就会针对这个应用自动创建和注入需要的 Spring  Bean 到上下文中。

自动注入的核心在于 spring-boot-autoconfigure.jar 这个类库。在分析之前，我们先来看几个文件。

```
@Configuration
@ConditionalOnClass({ JedisConnection.class, RedisOperations.class, Jedis.class })
@EnableConfigurationProperties
public class RedisAutoConfiguration {}
```



```
@Configuration
@ConditionalOnClass({ Client.class, TransportClientFactoryBean.class,
    NodeClientFactoryBean.class })
@EnableConfigurationProperties(ElasticsearchProperties.class)
public class ElasticsearchAutoConfiguration implements DisposableBean {}
```



上面三个源码分别对应Redis、MongoDB、ElasticSearch。通过对比，我们会发现它们都有一个特点，都存在 @ConditionalOnClass 注解。这个注解就是问题的关键所在。