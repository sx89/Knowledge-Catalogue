>借鉴CyC大佬复习框架,**代码原创,思路更新**

<!-- TOC -->

- [3. 数组中重复的数字](#3-数组中重复的数字)
    - [题目描述](#题目描述)
    - [解题思路](#解题思路)
- [4. 二维数组中的查找](#4-二维数组中的查找)
    - [题目描述](#题目描述-1)
    - [解题思路](#解题思路-1)
- [5. 替换空格](#5-替换空格)
    - [题目描述](#题目描述-2)
    - [解题思路](#解题思路-2)
- [6. 从尾到头打印链表](#6-从尾到头打印链表)
    - [题目描述](#题目描述-3)
    - [解题思路](#解题思路-3)
        - [使用递归](#使用递归)
        - [使用头插法](#使用头插法)
        - [使用栈](#使用栈)
- [7. 重建二叉树](#7-重建二叉树)
    - [题目描述](#题目描述-4)
    - [解题思路](#解题思路-4)
- [8. 二叉树的下一个结点](#8-二叉树的下一个结点)
    - [题目描述](#题目描述-5)
    - [解题思路](#解题思路-5)
- [9. 用两个栈实现队列](#9-用两个栈实现队列)
    - [题目描述](#题目描述-6)
    - [解题思路](#解题思路-6)
- [10.1 斐波那契数列](#101-斐波那契数列)
    - [题目描述](#题目描述-7)
    - [解题思路](#解题思路-7)
- [10.2 矩形覆盖](#102-矩形覆盖)
    - [题目描述](#题目描述-8)
    - [解题思路](#解题思路-8)
- [10.3 跳台阶](#103-跳台阶)
    - [题目描述](#题目描述-9)
    - [解题思路](#解题思路-9)

<!-- /TOC -->



# 如何写算法

## 职业选手的培养方法

1.知识体系(链表数组栈队列,树图,bitmap,动态规划,排序...)

2.刻意练习(那里不熟练哪里,每道题五遍以上)

3.注意反馈(主动反馈:算法选手直播,代码范例   被动反馈:codereview)

## 面试五步

1.搞清楚问题是什么,跟面试官复述一遍,确定一些边界(空,格式错误,百万以上的大数)

2.可能的解法有多种,都跟面试官说明一下,分析他们的时间空间复杂度.

3.确定整体思路

4.着手写代码

5.自己写testCase来做测试

## 五遍刷题

<img src="./pictures/jianzhi-offer/Snipaste_2019-12-15_12-55-12.png" style="zoom:50%;" />



<img src="./pictures/jianzhi-offer/Snipaste_2019-12-15_12-58-34.png" style="zoom:50%;" />





第三遍一天后再刷

第四遍一周后再刷

第五遍考前适应性训练





























# 3. 数组中重复的数字

[NowCoder](https://www.nowcoder.com/practice/623a5ac0ea5b4e5f95552655361ae0a8?tpId=13&tqId=11203&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

## 题目描述

在一个长度为 n 的数组里的所有数字都在 0 到 n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字是重复的，也不知道每个数字重复几次。请找出数组中任意一个重复的数字。

```html
Input:
{2, 3, 1, 0, 2, 5}

Output:
2
```

## 解题思路

要求时间复杂度 O(N)，空间复杂度 O(1)。因此不能使用排序的方法，也不能使用额外的标记数组。

对于这种数组元素在 [0, n-1] 范围内的问题，可以将值为 i 的元素调整到第 i 个位置上进行求解。

以 (2, 3, 1, 0, 2, 5) 为例，遍历到位置 4 时，该位置上的数为 2，但是第 2 个位置上已经有一个 2 的值了，因此可以知道 2 重复：


```java
  public class Solution {
            // Parameters:
            //    numbers:     an array of integers
            //    length:      the length of array numbers
            //    duplication: (Output) the duplicated number in the array number,length of duplication array is 1,so using duplication[0] = ? in implementation;
            //                  Here duplication like pointor in C/C++, duplication[0] equal *duplication in C/C++
            //    这里要特别注意~返回任意重复的一个，赋值duplication[0]
            // Return value:       true if the input is valid, and there are some duplications in the array number
            //                     otherwise false
            public boolean duplicate(int numbers[], int length, int[] duplication) {
                if (numbers == null || length < 0) {
                    return false;
                }
                for (int i = 0; i < length; i++) {
                    while (i != numbers[i]) {
                        if (numbers[i] == numbers[numbers[i]]) {
                            duplication[0] = numbers[i];
                            return true;
                        }
                        swap(i, numbers[i], numbers);
                    }

                }
                return false;
            }

            private void swap(int index1, int index2, int[] numbers) {
                int c = numbers[index1];
                numbers[index1] = numbers[index2];
                numbers[index2] = c;
            }
        }
```


改进

1.数组判空

2.for循环改成while更符合逻辑

```java
    public boolean duplicate(int numbers[], int length, int[] duplication) {
         if (numbers == null || length < 0) {
                    return false;
         }
        //遍历数组
        //数字放到对应下标处
        //做交换
        //如果该出已有与下标相同,则返回
        //如果下标不同,则放入后,重复此过程
        int idx;
        int temp;
        for (int i = 0; i < length; ) {
            if (numbers[i] != i) {
                idx = numbers[i];
                if (numbers[idx] == idx) {
                    duplication[0] = idx;
                    return true;
                }
                temp = numbers[idx];
                numbers[idx] = numbers[i];
                numbers[i] = temp;
                //再循环,不后移
            } else {
                i++;
            }
        }
        return false;
    }
```



# 4. 二维数组中的查找

[NowCoder](https://www.nowcoder.com/practice/abc3fe2ce8e146608e868a70efebf62e?tpId=13&tqId=11154&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

## 题目描述

给定一个二维数组，其每一行从左到右递增排序，从上到下也是递增排序。给定一个数，判断这个数是否在该二维数组中。

```html
Consider the following matrix:
[
  [1,   4,  7, 11, 15],
  [2,   5,  8, 12, 19],
  [3,   6,  9, 16, 22],
  [10, 13, 14, 17, 24],
  [18, 21, 23, 26, 30]
]

Given target = 5, return true.
Given target = 20, return false.
```

## 解题思路

要求时间复杂度 O(M + N)，空间复杂度 O(1)。其中 M 为行数，N 为 列数。
从左下角开始查找.

如果只是每一层使用快排,那么其实只利用了每一行有序的条件,没有使用每一列也有序,复杂度为O(nlgn)

```java 
  public boolean Find(int target, int[][] array) {
        //rows和cols的命名方式
        int rows = array.length;
        int cols = array[0].length;
        //从左下角开始
        int i = rows - 1;
        int j = 0;
        //此处使用while比使用for要简洁
        while (i >= 0 && j < cols) {
            if (target == array[i][j])
                return true;
            else if (target > array[i][j])
                j++;
            else
                i--;
        }
        return  false;
    }
```


改进

1.row--后,col不用从零开始,因为`target>array[row][col]>array[row--][col]>array[row--][0到col-1]`

2.注意判断数组越界

```java

public class Solution {
    public boolean Find(int target, int[][] array) {
        //先画图

        //判空
        //从左下角开始
        //比上面大  行--
        //比左边大  列++
        //到头了没找到就退出
        int row = array.length - 1, col = 0;
        int rowSize = array.length;
        int colSize = array[0].length;
        while (row >= 0 && col < colSize) {
            while (row >= 0 && col < colSize && target < array[row][0]) {
                row--;
            }
            while (row >= 0 && col < colSize && target > array[row][col]) {
                col++;
            }
            if (row >= 0 && col < colSize && target == array[row][col]) {
                return true;
            }
            row--;
        }
        return false;
    }
}
```



# 5. 替换空格

[NowCoder](https://www.nowcoder.com/practice/4060ac7e3e404ad1a894ef3e17650423?tpId=13&tqId=11155&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

## 题目描述


将一个字符串中的空格替换成 "%20"。

```text
Input:
"A B"

Output:
"A%20B"
```

## 解题思路
时间O(n) 空间O(n)
新创建一个StringBuffer来存储结果
```java
public class Solution {
    public String replaceSpace(StringBuffer str) {
    	StringBuilder  sb  = new StringBuilder();
        for(int i =0;i<str.length();i++){
            char c = str.charAt(i);
            if(c==' '){
                sb.append("%20");
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }
}

```



改进:

时间O(n) 空间O(1)

**提前遍历一遍字符串,可以确定里面空格的个数,复杂度依然保持O(n)**

```java
public class Solution {
    public String replaceSpace(StringBuffer str) {
        //遍历,寻找空格个数,设置长度是空格数+2
        //倒序遍历,空格处给str设置为%20
        int oldPointer = str.length() - 1;
        int newPointer;
        for (int i = 0; i <= oldPointer; i++) {
            if (str.charAt(i) == ' ') {
                str.append("  ");
            }
        }
        newPointer = str.length() - 1;
        for (; oldPointer >= 0; oldPointer--) {
            if (str.charAt(oldPointer) == ' ') {
                str.setCharAt(newPointer--, '0');
                str.setCharAt(newPointer--, '2');
                str.setCharAt(newPointer--, '%');
            } else {
                str.setCharAt(newPointer--, str.charAt(oldPointer));
            }
        }
        return str.toString();
    }
}
```

# 6. 从尾到头打印链表

[NowCoder](https://www.nowcoder.com/practice/d0267f7f55b3412ba93bd35cfa8e8035?tpId=13&tqId=11156&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

## 题目描述

从尾到头反过来打印出每个结点的值。

时间复杂度一般是O(n),空间复杂度可以是O(1)或者O(n),另外还要注意是否可以改变输入的数据结构


## 解题思路

### 使用递归

要逆序打印链表 1->2->3（3,2,1)，可以先逆序打印链表 2->3(3,2)，最后再打印第一个节点 1。而链表 2->3 可以看成一个新的链表，要逆序打印该链表可以继续使用求解函数，也就是在求解函数中调用自己，这就是递归函数。

```java
public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
    ArrayList<Integer> ret = new ArrayList<>();
    if (listNode != null) {
        ret.addAll(printListFromTailToHead(listNode.next));
        ret.add(listNode.val);
    }
    return ret;
}


改进:
从头到尾的逆序 考虑用递归或者栈
2N的复杂度依然是O(N)
ArrayList的addAll函数
前面写了接收类型,后面可不写:
ArrayList<Integer> reverseList = new ArrayList<>();
在本层判断本层的listNode!=null;而不是判断下一层 if (listNode.next != null) 
    

public class Solution {
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        //创建一个链表
        //如果还有后续节点,就调用后续,并把结果放到当前链表上
        //返回当前链表
        if (listNode == null) {
            return new ArrayList<Integer>();
        }
        ArrayList<Integer> reverseList = new ArrayList<>();

        if (listNode.next != null) {
            ArrayList<Integer> ret = printListFromTailToHead(listNode.next);
            reverseList.addAll(ret);
        }
        reverseList.add(listNode.val);
        return reverseList;
    }
}
```

### 使用头插法

使用头插法可以得到一个逆序的链表。

list.add(0,value);//实现头插

头结点和第一个节点的区别：

- 头结点是在头插法中使用的一个额外节点，这个节点不存储值；
- 第一个节点就是链表的第一个真正存储值的节点。


```java

/**
*    public class ListNode {
*        int val;
*        ListNode next = null;
*
*        ListNode(int val) {
*            this.val = val;
*        }
*    }
*
*/
import java.util.ArrayList;
public class Solution {
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList arrayList = new ArrayList();
        while(listNode!=null){
            //头插法的可用函数
            arrayList.add(0,listNode.val);
            listNode = listNode.next;
        }
        return arrayList;
    }
}


改进:
arrayList有add(0,value)的函数,可以实现头插
```
或者
```java
public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
    ListNode head = new ListNode(-1);
    //头插法构建逆序链表
    while (listNode != null) {
        ListNode memo = listNode.next;
        listNode.next = head.next;
        head.next = listNode;
        listNode = memo;
    }
    //逆序链表放入 ArrayList
    ArrayList<Integer> ret = new ArrayList<>();
    head = head.next;
    while (head != null) {
        ret.add(head.val);
        head = head.next;
    }
    return ret;
}

改进
    头插的链表放到list里面的时候,记得的第一个head节点给跳过	head = head.next;
```

### 使用栈

栈具有后进先出的特点，在遍历链表时将值按顺序放入栈中，最后出栈的顺序即为逆序。

```java
//注意导入stack包
import java.util.Stack;
import java.util.ArrayList;
public class Solution {
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
         Stack<Integer> stack  = new Stack<>();
        ArrayList<Integer> arrayList = new ArrayList<>();
        while (listNode != null) {
            //stack的add方法来自它继承的List父类,效果与push相同
            stack.push(listNode.val);
            listNode = listNode.next;
        }
        while (!stack.isEmpty()) {
            arrayList.add((Integer) stack.pop());
        }
        return arrayList;
    }
}
```



# 7. 重建二叉树

[NowCoder](https://www.nowcoder.com/practice/8a19cbe657394eeaac2f6ea9b0f6fcf6?tpId=13&tqId=11157&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

## 题目描述

根据二叉树的前序遍历和中序遍历的结果，重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。


## 解题思路
数组的长度用length而不是size  

本题treenode的构造器没有无参构造器,注意报错  

HashMap<integer,integer> = new HashMap<integer,integer>();等号两遍泛型都填好,防止歧义;注意要导入包`import java.util.HashMap`


前序遍历的第一个值为根节点的值，使用这个值将中序遍历结果分成两部分，左部分为树的左子树中序遍历结果，右部分为树的右子树中序遍历的结果。
```java
/**
 * Definition for binary tree
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
import java.util.HashMap;
public class Solution {
    HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
    public TreeNode reConstructBinaryTree(int [] pre,int [] in) {
        if(pre==null||in==null){
            return null;
        }
        for(int i = 0;i<in.length;i++){
            map.put(in[i],i);
        }
        return reConstructBinaryTree(pre,0,pre.length-1,0);
    }
    public TreeNode reConstructBinaryTree(int [] pre,int preL,int preR,int inL){
        if(preL>preR){
            return null;
        }
        int rootVal = pre[preL];
        TreeNode tree = new TreeNode(rootVal);
        int inIndex = map.get(rootVal);
        int leftSize = inIndex-inL;
        tree.left = reConstructBinaryTree(pre,preL+1,preL+leftSize,inL);
        tree.right = reConstructBinaryTree(pre,preL+leftSize+1,preR,inIndex+1);
        return tree;
    }
}
```

# 8. 二叉树的下一个结点

[NowCoder](https://www.nowcoder.com/practice/9023a0c988684a53960365b889ceaf5e?tpId=13&tqId=11210&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

## 题目描述

给定一个二叉树和其中的一个结点，请找出中序遍历顺序的下一个结点并且返回。注意，树中的结点不仅包含左右子结点，同时包含指向父结点的指针。

## 解题思路

① 如果一个节点的右子树不为空，那么该节点的下一个节点是右子树的最左节点；

<div align="center"> <img src="pics/b0611f89-1e5f-4494-a795-3544bf65042a.gif" width="220px"/> </div><br>
② 否则，向上找第一个左链接指向的树包含该节点的祖先节点。

```java
public class Solution {
    public TreeLinkNode GetNext(TreeLinkNode pNode) {
        if (pNode == null) {
            return null;
        }
        if (pNode.right != null) {
            TreeLinkNode node = pNode.right;
            while (node.left != null) {
                node = node.left;
            }
            return node;
        } else {
            while (pNode.next != null) {
                TreeLinkNode parent = pNode.next;
                if (parent.left == pNode) {
                    return parent;
                }
                pNode = pNode.next;

            }
        }
        return null;
    }
}

class TreeLinkNode {
    int val;
    TreeLinkNode left = null;
    TreeLinkNode right = null;
    TreeLinkNode next = null;

    TreeLinkNode(int val) {
        this.val = val;
    }
}
```


# 9. 用两个栈实现队列

[NowCoder](https://www.nowcoder.com/practice/54275ddae22f475981afa2244dd448c6?tpId=13&tqId=11158&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

## 题目描述

用两个栈来实现一个队列，完成队列的 Push 和 Pop 操作。

## 解题思路

in 栈用来处理入栈（push）操作，out 栈用来处理出栈（pop）操作。一个元素进入 in 栈之后，出栈的顺序被反转。当元素要出栈时，需要先进入 out 栈，此时元素出栈顺序再一次被反转，因此出栈顺序就和最开始入栈顺序是相同的，先进入的元素先退出，这就是队列的顺序。


```java
Stack<Integer> in = new Stack<Integer>();
Stack<Integer> out = new Stack<Integer>();

public void push(int node) {
    in.push(node);
}

public int pop() throws Exception {
    //要等stack2空了在往里面添加元素,stack1里面的元素会压在
    //stack2内部的元素上面,破坏了先进先出规则
    if (out.isEmpty())
        while (!in.isEmpty())
            out.push(in.pop());

    if (out.isEmpty())
        throw new Exception("queue is empty");

    return out.pop();
}
```


# 10.1 斐波那契数列

[NowCoder](https://www.nowcoder.com/practice/c6c7742f5ba7442aada113136ddea0c3?tpId=13&tqId=11160&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

## 题目描述

求斐波那契数列的第 n 项，n <= 39。

<div align="center"><img src="https://latex.codecogs.com/gif.latex?f(n)=\left\{\begin{array}{rcl}0&&{n=0}\\1&&{n=1}\\f(n-1)+f(n-2)&&{n>1}\end{array}\right." class="mathjax-pic"/></div> <br>

## 解题思路

如果使用递归求解，会重复计算一些子问题。例如，计算 f(4) 需要计算 f(3) 和 f(2)，计算 f(3) 需要计算 f(2) 和 f(1)，可以看到 f(2) 被重复计算了。
<div align="center"> <img src="pics/c13e2a3d-b01c-4a08-a69b-db2c4e821e09.png" width="350px"/> </div><br>

递归是将一个问题划分成多个子问题求解，动态规划也是如此，但是动态规划会把子问题的解缓存起来，从而避免重复求解子问题。

```java
 public int Fibonacci(int n) {
        int[] fibs = calFib();
        return fibs[n];
    }

    //把之前计算的fib数列存储起来,既可以防止重复计算,
    //又可以在取值的时候迅速拿到值
    private int[] calFib() {
        int[] fibs = new int[40];
        fibs[1] = 1;
        for (int i = 2; i < 40; i++) {
            fibs[i] = fibs[i - 1] + fibs[i - 2];
        }
        return fibs;
    }
```

# 10.2 矩形覆盖

[NowCoder](https://www.nowcoder.com/practice/72a5a919508a4251859fb2cfb987a0e6?tpId=13&tqId=11163&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

## 题目描述

我们可以用 2\*1 的小矩形横着或者竖着去覆盖更大的矩形。请问用 n 个 2\*1 的小矩形无重叠地覆盖一个 2\*n 的大矩形，总共有多少种方法？

<div align="center"> <img src="pics/b903fda8-07d0-46a7-91a7-e803892895cf.gif" width="100px"> </div><br>
## 解题思路

当 n 为 1 时，只有一种覆盖方法：

<div align="center"> <img src="pics/f6e146f1-57ad-411b-beb3-770a142164ef.png" width="100px"> </div><br>
当 n 为 2 时，有两种覆盖方法：

<div align="center"> <img src="pics/fb3b8f7a-4293-4a38-aae1-62284db979a3.png" width="200px"> </div><br>
要覆盖 2\*n 的大矩形，可以先覆盖 2\*1 的矩形，再覆盖 2\*(n-1) 的矩形；或者先覆盖 2\*2 的矩形，再覆盖 2\*(n-2) 的矩形。

**2\*2有两种覆盖方法,但其中一种和2\*1重合**

而覆盖 2\*(n-1) 和 2\*(n-2) 的矩形可以看成子问题。该问题的递推公式如下：

<!-- <div align="center"><img src="https://latex.codecogs.com/gif.latex?f(n)=\left\{\begin{array}{rcl}1&&{n=1}\\2&&{n=2}\\f(n-1)+f(n-2)&&{n>1}\end{array}\right." class="mathjax-pic"/></div> <br> -->

<div align="center"> <img src="pics/508c6e52-9f93-44ed-b6b9-e69050e14807.jpg" width="350px"> </div><br>
非递归写法:
```java
  public int RectCover(int target) {
        int pre1 = 1;
        int pre2 = 2;
        if (target <= 2) {
            return target;
        }
        int result = -1;
        for (int i = 3; i <= target; i++) {
            result = pre1 + pre2;
            pre1 = pre2;
            pre2 = result;
        }
        return result;
    }
```
递归写法:
```java
public int RectCover2(int target) {
        if (target < 1) {
            return 0;
        } else if (target == 1 || target == 2) {
            return target;
        } else {
            return RectCover(target-1) + RectCover(target-2);
        }
    }
```

# 10.3 跳台阶

[NowCoder](https://www.nowcoder.com/practice/8c82a5b80378478f9484d87d1c5f12a4?tpId=13&tqId=11161&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

## 题目描述

一只青蛙一次可以跳上 1 级台阶，也可以跳上 2 级。求该青蛙跳上一个 n 级的台阶总共有多少种跳法。

<div align="center"> <img src="pics/9dae7475-934f-42e5-b3b3-12724337170a.png" width="380px"> </div><br>
## 解题思路

当 n = 1 时，只有一种跳法：

<div align="center"> <img src="pics/72aac98a-d5df-4bfa-a71a-4bb16a87474c.png" width="250px"> </div><br>
当 n = 2 时，有两种跳法：

<div align="center"> <img src="pics/1b80288d-1b35-4cd3-aa17-7e27ab9a2389.png" width="300px"> </div><br>
跳 n 阶台阶，可以先跳 1 阶台阶，再跳 n-1 阶台阶；或者先跳 2 阶台阶，再跳 n-2 阶台阶。而 n-1 和 n-2 阶台阶的跳法可以看成子问题，该问题的递推公式为：

<div align="center"> <img src="pics/508c6e52-9f93-44ed-b6b9-e69050e14807.jpg" width="350px"> </div><br>

递归写法:
```java
 public int JumpFloor(int target) {
        if (target == 0) {
            return 0;
        } else if (target == 1 || target == 2) {
            return target;
        } else {
            return JumpFloor(target - 1) + JumpFloor(target - 2);
        }
    }
```
非递归写法
```java
public int JumpFloor(int target) {
        if (target <= 2) {
            return target;
        } else {
            int pre1 = 1;
            int pre2 = 2;
            int result = 0;
            for (int i = 3; i <= target; i++) {
                result = pre1 + pre2;
                pre1 = pre2;
                pre2 = result;
            }
            return result;
        }
    }
```


# 10.4 变态跳台阶

[NowCoder](https://www.nowcoder.com/practice/22243d016f6b47f2a6928b4313c85387?tpId=13&tqId=11162&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

## 题目描述

一只青蛙一次可以跳上 1 级台阶，也可以跳上 2 级... 它也可以跳上 n 级。求该青蛙跳上一个 n 级的台阶总共有多少种跳法。

<div align="center"> <img src="pics/cd411a94-3786-4c94-9e08-f28320e010d5.png" width="380px"> </div><br>
## 解题思路

### 动态规划

```java
public int JumpFloorII(int target) {
        if (target <= 0) {
            return 0;
        }
        if (target <= 2) {
            return target;
        }
        int[] array = new int[target + 1];
        array[1] = 1;
        array[2] = 2;

        for (int i = 3; i <= target; i++) {
            for (int j = 1; j < i; j++) {
                array[i] = array[i] + array[j];
            }
            array[i] = array[i] + 1;
        }
        return array[target];
    }
```
### 数学推导

跳上 n-1 级台阶，可以从 n-2 级跳 1 级上去，也可以从 n-3 级跳 2 级上去...，那么

```
f(n-1) = f(n-2) + f(n-3) + ... + f(0)
```

同样，跳上 n 级台阶，可以从 n-1 级跳 1 级上去，也可以从 n-2 级跳 2 级上去... ，那么

```
f(n) = f(n-1) + f(n-2) + ... + f(0)
```

综上可得

```
f(n) - f(n-1) = f(n-1)
```

即

```
f(n) = 2*f(n-1)
```

所以 f(n) 是一个等比数列

```java
public int JumpFloorII(int target) {
    //返回值强转,底数与指数,不用导包
    return (int) Math.pow(2, target - 1);
}
```



# 11. 旋转数组的最小数字

[NowCoder](https://www.nowcoder.com/practice/9f3231a991af4f55b95579b44b7a01ba?tpId=13&tqId=11159&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

## 题目描述

把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。输入一个非递减排序的数组的一个旋转，输出旋转数组的最小元素。


## 解题思路

将旋转数组对半分可以得到一个包含最小元素的新旋转数组，以及一个非递减排序的数组。新的旋转数组的数组元素是原数组的一半，从而将问题规模减少了一半，这种折半性质的算法的时间复杂度为 O(logN)（为了方便，这里将 log<sub>2</sub>N 写为 logN）。


此时问题的关键在于确定对半分得到的两个数组哪一个是旋转数组，哪一个是非递减数组。我们很容易知道非递减数组的第一个元素一定小于等于最后一个元素。

通过修改二分查找算法进行求解（l 代表 low，m 代表 mid，h 代表 high）：

- 当 nums[m] <= nums[h] 时，表示 [m, h] 区间内的数组是非递减数组，[l, m] 区间内的数组是旋转数组，此时令 h = m；
- 否则 [m + 1, h] 区间内的数组是旋转数组，令 l = m + 1。

```java
    public int minNumberInRotateArray(int[] array) {

        if (array.length <= 0) {
            return 0;
        }
        int l = 0;
        int h = array.length - 1;
        while (l < h) {
            int m = l + (h - l) / 2;
            if (array[l] == array[m] && array[m] == array[h]) {
                return orderFind(array, l, h);
            } else if (array[m] <= array[h]) {
                h = m;
            } else {
                l = m + 1;
            }
        }
        return array[l];
    }

    private int orderFind(int[] array, int l, int h) {
        if (array.length <= 0) {
            return 0;
        }
        for (int i = l; i < h; i++) {
            if (array[i] > array[i + 1]) {
                return array[i + 1];
            }
        }
        return array[l];
    }
}
```







