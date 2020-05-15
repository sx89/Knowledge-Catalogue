



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