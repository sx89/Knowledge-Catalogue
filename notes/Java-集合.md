# ArrayList和LinkedList
ArrayList是实现了基于动态数组的结构，而LinkedList则是基于实现链表的数据结构。而两种数据结构在程序上体现出来的优缺点在于增删和改查的速率;
## 区别
在数据的更新和查找方面,因为arraylist的数据在同一个地址上,所以可以直接定位数据,查找和更新很快;LinkedList的每一个数据都有一个不同的地址,需要顺序查询然后更新,速度会慢很多;

在增加和删除上面,arraylist需要移动其他数据,LinkList只需要修改对应数据处的指向,所以LinkList快很多;

**但其实还是要根据实际情况来分析,如果数据量很大并且插在中间,arrayList的速度会比LinkedList快,因为arrayList的连续转储会比linkedList的顺序指针遍历要快,但数据量很大并且插在头上,那还是LinkedList快一些**

## ArrayList

ArrayList 是一个数组队列，相当于 动态数组。与Java中的数组相比，它的容量能动态增长

ArrayList 继承了AbstractList，实现了List。它是一个数组队列，提供了相关的添加、删除、修改、遍历等功能。

ArrayList 实现了RandmoAccess接口，即提供了随机访问功能。

RandmoAccess是java中用来被List实现，为List提供快速访问功能的。在ArrayList中，我们即可以通过元素的序号快速获取元素对象；这就是快速随机访问。稍后，我们会比较List的“快速随机访问”和“通过Iterator迭代器访问”的效率。

ArrayList 实现了Cloneable接口，即覆盖了函数clone()，能被克隆。

ArrayList 实现java.io.Serializable接口，这意味着ArrayList支持序列化，能通过序列化去传输。

ArrayList中的操作不是线程安全的,所以，建议在单线程中才使用ArrayList，而在多线程中可以选择CopyOnWriteArrayList。
## ArrayList的初始容量与扩容
ArrayList的底层是一个动态数组，ArrayList首先会对传进来的初始化参数initalCapacity进行判断，如果参数等于0，则将数组初始化为一个空数组;`new ArrayList()`在没指定initialCapacity时就是会使用延迟分配对象数组空间，当第一次插入元素时才分配10（默认）个对象空间。

初始容量也可以自定义指定。随着不断添加元素，数组大小增加，当数组的大小大于初始容量的时候(比如初始为10，当添加第11个元素的时候)，就会进行扩容，新的容量为旧的容量的1.5倍。 

超出容量的时候才会扩容

扩容的时候，会以新的容量建一个原数组的拷贝，修改原数组，指向这个新数组，原数组被抛弃，会被GC回收。


# CopyOnWriteList