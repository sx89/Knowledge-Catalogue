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

第一遍,没有思路就直接看解法

<img src="./pictures/jianzhi-offer/Snipaste_2019-12-15_12-55-12.png" style="zoom:50%;" />



<img src="./pictures/jianzhi-offer/Snipaste_2019-12-15_12-58-34.png" style="zoom:50%;" />





第三遍一天后再刷

第四遍一周后再刷

第五遍考前适应性训练



# 常见的数据结构的用法



```
map:
HashMap<Integer,Integer> hm = new HashMap<Integer,Integer>();

遍历:
Set<Map.Entry<Integer,Integer>> entrySet = hm.entrySet();
Iterator<Map.Entry<Integer,Integer>> it = entrySet.iterator();
while(it.hasNext()){
	Map.Entry<Integer,Integer> entry =it.next();
	entry.getKey();
	entry.getValue();
}
遍历2:
for(Map.Entry<Integer,Integer> temp:hm.entrySet()){
	temp.getKey();
	temp.getValue();
}

队列和链表

LinkedList<Integer> list = new LinkedList<Integer>();

list.add(1111);
list.add(0,111);//头插
list.peek();//返回头结点,不删除
list.poll(); //返回头结点,删除
list.get(0);//获取0处的节点

遍历1:
Iterator<Integer> it = list.iterator();
while(it.hasNext()){
	int i = (int)it.next();
}
遍历2:
for(Integer temp:list){
	int a  =temp;
}
```



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


改进点
    命名的规范清晰,为逻辑的编写 和 debug提供了极大的方便,继续保持
    要利用函数重载的技巧,缩减重复写的代码
    利用map记录下了每个点的位置,parentLoc的定位会减少计算量
    
    
    public class Solution {
    public TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        //如果前序数组长度为0,则返回
        //递归
        //前序确定根节点
        //中序拆分左右子树
        //将返回的结果放入左右子树

        //重复上述步骤
        int parentLoc = 0;
        int leftChildLength = 0;
        int rightChildLength = 0;
        int inL = 0;
        int inR = in.length - 1;
        int preL = 0;
        int preR = pre.length - 1;


        if (pre.length <= 0) {
            return null;
        }
        TreeNode parent = new TreeNode(pre[preL]);
        for (int i = inL; i <= inR; i++) {
            if (in[i] == parent.val) {
                parentLoc = i;
                break;
            }
        }
        leftChildLength = parentLoc - inL;
        rightChildLength = inR - parentLoc;

        parent.left = reConstructBinaryTree(pre, preL + 1, preL + leftChildLength, in, inL, inL + leftChildLength);
        parent.right = reConstructBinaryTree(pre, preL + leftChildLength + 1, preR, in, parentLoc + 1, inR);

        parent = reConstructBinaryTree(pre, preL, preR, in, inL, inR);
        return parent;

    }

    private TreeNode reConstructBinaryTree(int[] pre, int preL, int preR, int[] in, int inL, int inR) {
        int parentLoc = 0;
        int leftChildLength = 0;
        int rightChildLength = 0;

        if (preR < preL || inR < inL) {
            return null;
        }
        TreeNode parent = new TreeNode(pre[preL]);
        for (int i = inL; i <= inR; i++) {
            if (in[i] == parent.val) {
                parentLoc = i;
                break;
            }
        }
        leftChildLength = parentLoc - inL;
        rightChildLength = inR - parentLoc;

        parent.left = reConstructBinaryTree(pre, preL + 1, preL + leftChildLength, in, inL, inL + leftChildLength);
        parent.right = reConstructBinaryTree(pre, preL + leftChildLength + 1, preR, in, parentLoc + 1, inR);
        return parent;
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

改进:
思路很重要,如果没有右子树,就找该node作为父亲节点左子节点的父亲节点
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


改进:
整体有序要考虑二分查找
针对此题的边界的解决办法:

			为什么要返回array[l];因为int m = l + (h - l) / 2;使得m偏向于l
 			if (array[l] == array[m] && array[m] == array[h]) {
                return orderFind(array, l, h);
            } else if (array[m] <= array[h]) {
                h = m;
            } else {
                l = m + 1;
            }
			 return array[l];


import java.util.ArrayList;
public class Solution {
    public int minNumberInRotateArray(int [] array) {
        if(array.length==0){
            return 0;
        }
        
        for(int i = 0;i<array.length-1;i++){
            if(array[i]>array[i+1]){
                return array[i+1];
            }
        }
        return array[0];
    }
}
```





# 12. 矩阵中的路径

[NowCoder](https://www.nowcoder.com/practice/c61c6999eecb4b8f88a98f66b273a3cc?tpId=13&tqId=11218&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking&from=cyc_github)

## 题目描述

判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。路径可以从矩阵中的任意一个格子开始，每一步可以在矩阵中向上下左右移动一个格子。如果一条路径经过了矩阵中的某一个格子，则该路径不能再进入该格子。

例如下面的矩阵包含了一条 bfce 路径。

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/1db1c7ea-0443-478b-8df9-7e33b1336cc4.png" width="200px"> </div><br>
## 解题思路

使用回溯法（backtracking）进行求解，它是一种暴力搜索方法，通过搜索所有可能的结果来求解问题。回溯法在一次搜索结束时需要进行回溯（回退），将这一次搜索过程中设置的状态进行清除，从而开始一次新的搜索过程。例如下图示例中，从 f 开始，下一步有 4 种搜索可能，如果先搜索 b，需要将 b 标记为已经使用，防止重复使用。在这一次搜索结束之后，需要将 b 的已经使用状态清除，并搜索 c。

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/dc964b86-7a08-4bde-a3d9-e6ddceb29f98.png" width="200px"> </div><br>
本题的输入是数组而不是矩阵（二维数组），因此需要先将数组转换成矩阵。

```java
private final static int[][] next = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
private int rows;
private int cols;

public boolean hasPath(char[] array, int rows, int cols, char[] str) {
    if (rows == 0 || cols == 0) return false;
    this.rows = rows;
    this.cols = cols;
    boolean[][] marked = new boolean[rows][cols];
    char[][] matrix = buildMatrix(array);
    for (int i = 0; i < rows; i++)
        for (int j = 0; j < cols; j++)
            if (backtracking(matrix, str, marked, 0, i, j))
                return true;

    return false;
}

private boolean backtracking(char[][] matrix, char[] str,
                             boolean[][] marked, int pathLen, int r, int c) {

    if (pathLen == str.length) return true;
    if (r < 0 || r >= rows || c < 0 || c >= cols
            || matrix[r][c] != str[pathLen] || marked[r][c]) {

        return false;
    }
    marked[r][c] = true;
    for (int[] n : next)
        if (backtracking(matrix, str, marked, pathLen + 1, r + n[0], c + n[1]))
            return true;
    marked[r][c] = false;
    return false;
}

private char[][] buildMatrix(char[] array) {
    char[][] matrix = new char[rows][cols];
    for (int r = 0, idx = 0; r < rows; r++)
        for (int c = 0; c < cols; c++)
            matrix[r][c] = array[idx++];
    return matrix;
}


改进:
backTracing里面,返回的条件是str.length==pathLen;
终止的条件有:row<0,row>rows,col<0,col>cols,matrix[i][j]!=str[pathLen]和marked[i][j]==false
marked记得设置成true和false;

public class Solution {
    private int[][] next = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    private int rows, cols;

    public boolean hasPath(char[] array, int rows, int cols, char[] str) {
        this.rows = rows;
        this.cols = cols;
        //制作矩阵
        boolean[][] marked = new boolean[rows][cols];
        char[][] matrix = buildMatrix(array, rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (backTracing(matrix, marked, i, j, str, 0))
                    return true;
            }
        }
        return false;
        //回溯
    }

    private boolean backTracing(char[][] matrix, boolean[][] marked, int row,
                                int col, char[] str, int pathLen) {
        if (pathLen == str.length) {
            return true;
        }
        if (row < 0 || row >= rows || col < 0 || col >= cols || matrix[row][col] != str[pathLen] || marked[row][col]) {
            return false;
        }
        marked[row][col] = true;
        for (int[] temp : next) {
            if (backTracing(matrix, marked, row + temp[0], col + temp[1], str, pathLen + 1)) {
                return true;
            }
        }
        marked[row][col] = false;
        return false;

    }

    private char[][] buildMatrix(char[] array, int rows, int cols) {
        char[][] matrix = new char[rows][cols];
        for (int i = 0, idx = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = array[idx++];
            }
        }
        return matrix;
    }


}
```

# 13. 机器人的运动范围

[NowCoder](https://www.nowcoder.com/practice/6e5207314b5241fb83f2329e89fdecc8?tpId=13&tqId=11219&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking&from=cyc_github)

## 题目描述

地上有一个 m 行和 n 列的方格。一个机器人从坐标 (0, 0) 的格子开始移动，每一次只能向左右上下四个方向移动一格，但是不能进入行坐标和列坐标的数位之和大于 k 的格子。

例如，当 k 为 18 时，机器人能够进入方格 (35,37)，因为 3+5+3+7=18。但是，它不能进入方格 (35,38)，因为 3+5+3+8=19。请问该机器人能够达到多少个格子？

## 解题思路

使用深度优先搜索（Depth First Search，DFS）方法进行求解。回溯是深度优先搜索的一种特例，它在一次搜索过程中需要设置一些本次搜索过程的局部状态，并在本次搜索结束之后清除状态。而普通的深度优先搜索并不需要使用这些局部状态，虽然还是有可能设置一些全局状态。

```java
private static final int[][] next = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
private int cnt = 0;
private int rows;
private int cols;
private int threshold;
private int[][] digitSum;

public int movingCount(int threshold, int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    this.threshold = threshold;
    initDigitSum();
    boolean[][] marked = new boolean[rows][cols];
    dfs(marked, 0, 0);
    return cnt;
}

private void dfs(boolean[][] marked, int r, int c) {
    if (r < 0 || r >= rows || c < 0 || c >= cols || marked[r][c])
        return;
    marked[r][c] = true;
    if (this.digitSum[r][c] > this.threshold)
        return;
    cnt++;
    for (int[] n : next)
        dfs(marked, r + n[0], c + n[1]);
}

private void initDigitSum() {
    int[] digitSumOne = new int[Math.max(rows, cols)];
    for (int i = 0; i < digitSumOne.length; i++) {
        int n = i;
        while (n > 0) {
            digitSumOne[i] += n % 10;
            n /= 10;
        }
    }
    this.digitSum = new int[rows][cols];
    for (int i = 0; i < this.rows; i++)
        for (int j = 0; j < this.cols; j++)
            this.digitSum[i][j] = digitSumOne[i] + digitSumOne[j];
}


改进:
制作一个每个坐标的求和矩阵,方便和threshold做比较
marked要传入bfs里面
    
public class Solution {
    private int[][] locationSum;
    private int rows, cols;
    private int threshold;
    private boolean[][] marked;
    private int count = 0;
    private int[][] next = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

    public int movingCount(int threshold, int rows, int cols) {
        this.threshold = threshold;
        this.cols = cols;
        this.rows = rows;
        marked = new boolean[rows][cols];
        locationSum = getLocationSum(rows, cols);
         //进入bfs
        bfs(marked, 0, 0);
        return count;
    }

    //bfs  不符合条件就退出,符合就继续往下走,走过的就不能走了(不走回头路)
    private void bfs(boolean[][] marked, int row, int col) {
        if (row < 0 || col < 0 || row >= rows || col >= cols || marked[row][col] || locationSum[row][col] > threshold) {
            return;
        }
        count++;
        marked[row][col] = true;
        for (int[] temp : next) {
            bfs(marked, row + temp[0], col + temp[1]);
        }
        return;
    }
    //制作每个方格的和
    private int[][] getLocationSum(int rows, int cols) {
        int maxLen = Math.max(rows, cols);
        int[] sumOne = new int[maxLen];
        for (int i = 0; i < maxLen; i++) {
            int sum = 0;
            int loc = i;
            while (loc > 0) {
                sum += loc % 10;
                loc = loc / 10;
            }
            sumOne[i] = sum;
        }
        int[][] sumTwo = new int[maxLen][maxLen];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sumTwo[i][j] = sumOne[i] + sumOne[j];
            }
        }
        return sumTwo;
    }
}
```

# 14. 剪绳子

[Leetcode](https://leetcode.com/problems/integer-break/description/)

## 题目描述

把一根绳子剪成多段，并且使得每段的长度乘积最大。

```html
n = 2
return 1 (2 = 1 + 1)

n = 10
return 36 (10 = 3 + 3 + 4)
```

## 解题思路

### 贪心

尽可能多剪长度为 3 的绳子，并且不允许有长度为 1 的绳子出现。如果出现了，就从已经切好长度为 3 的绳子中拿出一段与长度为 1 的绳子重新组合，把它们切成两段长度为 2 的绳子。

证明：当 n >= 5 时，3(n - 3) - n = 2n - 9 > 0，且 2(n - 2) - n = n - 4 > 0。因此在 n >= 5 的情况下，将绳子剪成一段为 2 或者 3，得到的乘积会更大。又因为 3(n - 3) - 2(n - 2) = n - 5 >= 0，所以剪成一段长度为 3 比长度为 2 得到的乘积更大。

```java
public int integerBreak(int n) {
    if (n < 2)
        return 0;
    if (n == 2)
        return 1;
    if (n == 3)
        return 2;
    int timesOf3 = n / 3;
    if (n - timesOf3 * 3 == 1)
        timesOf3--;
    int timesOf2 = (n - timesOf3 * 3) / 2;
    return (int) (Math.pow(3, timesOf3)) * (int) (Math.pow(2, timesOf2));
}
```

### 动态规划

```java
public int integerBreak(int n) {
    int[] dp = new int[n + 1];
    dp[1] = 1;
    for (int i = 2; i <= n; i++)
        for (int j = 1; j < i; j++)
            dp[i] = Math.max(dp[i], Math.max(j * (i - j), dp[j] * (i - j)));
    return dp[n];
}

思考:
1.从i=2开始,我要求 dp[i]和dp[j]*(i-j)的乘积哪个更大
  2.  另外dp[j]不包括 j不用分割,当前元素乘积就是最大的情况,因此也需要判断.
    3.因为是从i之前的元素开始找最大值,所以无论j是 j=i-1;j>=0;j--  还是 j=1;j<i;j++都是没有区别的
    
```

# 15. 二进制中 1 的个数

[NowCoder](https://www.nowcoder.com/practice/8ee967e43c2c4ec193b040ea7fbb10b8?tpId=13&tqId=11164&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking&from=cyc_github)

## 题目描述

输入一个整数，输出该数二进制表示中 1 的个数。

### n&(n-1)

该位运算去除 n 的位级表示中最低的那一位。

```
n       : 10110100
n-1     : 10110011
n&(n-1) : 10110000
```

时间复杂度：O(M)，其中 M 表示 1 的个数。


```java
public int NumberOf1(int n) {
    int cnt = 0;
    while (n != 0) {
        cnt++;
        n &= (n - 1);
    }
    return cnt;
}

思考:
n&(n-1)该位运算去除 n 的位级表示中最低的那一位。
    而且题目中的整数包含负数,所while(里面是n!=0 而不是n>0)
```


### Integer.bitCount()

```java
public int NumberOf1(int n) {
    return Integer.bitCount(n);
}
```

# 16. 数值的整数次方

[NowCoder](https://www.nowcoder.com/practice/1a834e5e3e1a4b7ba251417554e07c00?tpId=13&tqId=11165&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking&from=cyc_github)

## 题目描述

给定一个 double 类型的浮点数 base 和 int 类型的整数 exponent，求 base 的 exponent 次方。

## 解题思路

下面的讨论中 x 代表 base，n 代表 exponent。

<!--<div align="center"><img src="https://latex.codecogs.com/gif.latex?x^n=\left\{\begin{array}{rcl}(x*x)^{n/2}&&{n\%2=0}\\x*(x*x)^{n/2}&&{n\%2=1}\end{array}\right." class="mathjax-pic"/></div> <br>-->

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/48b1d459-8832-4e92-938a-728aae730739.jpg" width="330px"> </div><br>
因为 (x\*x)<sup>n/2</sup> 可以通过递归求解，并且每次递归 n 都减小一半，因此整个算法的时间复杂度为 O(logN)。

```java
public double Power(double base, int exponent) {
    if (exponent == 0)
        return 1;
    if (exponent == 1)
        return base;
    boolean isNegative = false;
    if (exponent < 0) {
        exponent = -exponent;
        isNegative = true;
    }
    double pow = Power(base * base, exponent / 2);
    if (exponent % 2 != 0)
        pow = pow * base;
    return isNegative ? 1 / pow : pow;
}

改进:
因为 (x\*x)n/2 可以通过递归求解，并且每次递归 n 都减小一半，因此整个算法的时间复杂度为 O(logN)。
    如果for循环来求n遍的话,复杂度为O(N)
    
```

# 17. 打印从 1 到最大的 n 位数

## 题目描述

输入数字 n，按顺序打印出从 1 到最大的 n 位十进制数。比如输入 3，则打印出 1、2、3 一直到最大的 3 位数即 999。

## 解题思路

由于 n 可能会非常大，因此不能直接用 int 表示数字，而是用 char 数组进行存储。

使用回溯法得到所有的数。

```java
public void print1ToMaxOfNDigits(int n) {
    if (n <= 0)
        return;
    char[] number = new char[n];
    print1ToMaxOfNDigits(number, 0);
}

private void print1ToMaxOfNDigits(char[] number, int digit) {
    if (digit == number.length) {
        printNumber(number);
        return;
    }
    for (int i = 0; i < 10; i++) {
        number[digit] = (char) (i + '0');
        print1ToMaxOfNDigits(number, digit + 1);
    }
}

private void printNumber(char[] number) {
    int index = 0;
    while (index < number.length && number[index] == '0')
        index++;
    while (index < number.length)
        System.out.print(number[index++]);
    System.out.println();
}
```

# 18.1 在 O(1) 时间内删除链表节点

## 解题思路

① 如果该节点不是尾节点，那么可以直接将下一个节点的值赋给该节点，然后令该节点指向下下个节点，再删除下一个节点，时间复杂度为 O(1)。

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/1176f9e1-3442-4808-a47a-76fbaea1b806.png" width="600"/> </div><br>
② 否则，就需要先遍历链表，找到节点的前一个节点，然后让前一个节点指向 null，时间复杂度为 O(N)。

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/4bf8d0ba-36f0-459e-83a0-f15278a5a157.png" width="600"/> </div><br>
综上，如果进行 N 次操作，那么大约需要操作节点的次数为 N-1+N=2N-1，其中 N-1 表示 N-1 个不是尾节点的每个节点以 O(1) 的时间复杂度操作节点的总次数，N 表示 1 个尾节点以 O(N) 的时间复杂度操作节点的总次数。(2N-1)/N \~ 2，因此该算法的平均时间复杂度为 O(1)。

```java
public ListNode deleteNode(ListNode head, ListNode tobeDelete) {
    if (head == null || tobeDelete == null)
        return null;
    if (tobeDelete.next != null) {
        // 要删除的节点不是尾节点
        ListNode next = tobeDelete.next;
        tobeDelete.val = next.val;
        tobeDelete.next = next.next;
    } else {
        if (head == tobeDelete)
             // 只有一个节点
            head = null;
        else {
            ListNode cur = head;
            while (cur.next != tobeDelete)
                cur = cur.next;
            cur.next = null;
        }
    }
    return head;
}
```

# 18.2 删除链表中重复的结点

[NowCoder](https://www.nowcoder.com/practice/fc533c45b73a41b0b44ccba763f866ef?tpId=13&tqId=11209&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking&from=cyc_github)

## 题目描述

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/17e301df-52e8-4886-b593-841a16d13e44.png" width="450"/> </div><br>
## 解题描述

```java
public ListNode deleteDuplication(ListNode pHead) {
    if (pHead == null || pHead.next == null)
        return pHead;
    ListNode next = pHead.next;
    if (pHead.val == next.val) {
        while (next != null && pHead.val == next.val)
            next = next.next;
        return deleteDuplication(next); //返回的是从next开始的链表,所以pHead如果也是重复值,pHead也会被删掉
    } else {
        pHead.next = deleteDuplication(pHead.next);
        return pHead;
    }
}

改进:
上面递归的方法比较绕,建议用下面的办法,精髓在于创建了一个头结点Head,在pHead也是重复元素的情况下,可以删除Head下的子节点pHead.
    
    各种next比较绕,要重新做几遍
  public ListNode deleteDuplication(ListNode pHead) {
        ListNode Head = new ListNode(0);
        Head.next = pHead;
        ListNode pre = Head;
        ListNode next = pre.next;
        while (next != null) {
            if (next.next != null && next.val == next.next.val) {
                while (next.next != null && next.val == next.next.val) {
                    next = next.next;
                }
                pre.next = next.next;
                next = next.next;
            } else {
                pre = next;
                next = next.next;
            }
        }
        return Head.next;
    }
```

# 19. 正则表达式匹配

[NowCoder](https://www.nowcoder.com/practice/45327ae22b7b413ea21df13ee7d6429c?tpId=13&tqId=11205&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking&from=cyc_github)

## 题目描述

请实现一个函数用来匹配包括 '.' 和 '\*' 的正则表达式。模式中的字符 '.' 表示任意一个字符，而 '\*' 表示它前面的字符可以出现任意次（包含 0 次）。

在本题中，匹配是指字符串的所有字符匹配整个模式。例如，字符串 "aaa" 与模式 "a.a" 和 "ab\*ac\*a" 匹配，但是与 "aa.a" 和 "ab\*a" 均不匹配。

## 解题思路

应该注意到，'.' 是用来当做一个任意字符，而 '\*' 是用来重复前面的字符。这两个的作用不同，不能把 '.' 的作用和 '\*' 进行类比，从而把它当成重复前面字符一次。

```java
public boolean match(char[] str, char[] pattern) {

    int m = str.length, n = pattern.length;
    boolean[][] dp = new boolean[m + 1][n + 1];

    dp[0][0] = true;
    for (int i = 1; i <= n; i++)
        if (pattern[i - 1] == '*')
            dp[0][i] = dp[0][i - 2];

    for (int i = 1; i <= m; i++)
        for (int j = 1; j <= n; j++)
            if (str[i - 1] == pattern[j - 1] || pattern[j - 1] == '.')
                dp[i][j] = dp[i - 1][j - 1];
            else if (pattern[j - 1] == '*')
                if (pattern[j - 2] == str[i - 1] || pattern[j - 2] == '.') {
                    dp[i][j] |= dp[i][j - 1]; // a* counts as single a
                    dp[i][j] |= dp[i - 1][j]; // a* counts as multiple a
                    dp[i][j] |= dp[i][j - 2]; // a* counts as empty
                } else
                    dp[i][j] = dp[i][j - 2];   // a* only counts as empty

    return dp[m][n];
}

改进:
用下面的思路 比较清晰

当模式中的第二个字符不是“*”时：
1、如果字符串第一个字符和模式中的第一个字符相匹配，那么字符串和模式都后移一个字符，然后匹配剩余的。
2、如果 字符串第一个字符和模式中的第一个字符相不匹配，直接返回false。

而当模式中的第二个字符是“*”时：
如果字符串第一个字符跟模式第一个字符不匹配，则模式后移2个字符，继续匹配。如果字符串第一个字符跟模式第一个字符匹配，可以有3种匹配方式：
1、模式后移2字符，相当于x*被忽略；
2、字符串后移1字符，模式后移2字符；
3、字符串后移1字符，模式不变，即继续匹配字符下一位，因为*可以匹配多位；

这里需要注意的是：Java里，要时刻检验数组是否越界。


链接：https://www.nowcoder.com/questionTerminal/45327ae22b7b413ea21df13ee7d6429c?f=discussion
来源：牛客网

public class Solution {
    public boolean match(char[] str, char[] pattern) {
    if (str == null || pattern == null) {
        return false;
    }
    int strIndex = 0;
    int patternIndex = 0;
    return matchCore(str, strIndex, pattern, patternIndex);
}
  
public boolean matchCore(char[] str, int strIndex, char[] pattern, int patternIndex) {
    //有效性检验：str到尾，pattern到尾，匹配成功
    if (strIndex == str.length && patternIndex == pattern.length) {
        return true;
    }
    //pattern先到尾，匹配失败
    if (strIndex != str.length && patternIndex == pattern.length) {
        return false;
    }
    //模式第2个是*，且字符串第1个跟模式第1个匹配,分3种匹配模式；如不匹配，模式后移2位
    if (patternIndex + 1 < pattern.length && pattern[patternIndex + 1] == '*') {
        if ((strIndex != str.length && pattern[patternIndex] == str[strIndex]) || (pattern[patternIndex] == '.' && strIndex != str.length)) {
            return matchCore(str, strIndex, pattern, patternIndex + 2)//模式后移2，视为x*匹配0个字符
                    || matchCore(str, strIndex + 1, pattern, patternIndex + 2)//视为模式匹配1个字符
                    || matchCore(str, strIndex + 1, pattern, patternIndex);//*匹配1个，再匹配str中的下一个
        } else {
            return matchCore(str, strIndex, pattern, patternIndex + 2);
        }
    }
    //模式第2个不是*，且字符串第1个跟模式第1个匹配，则都后移1位，否则直接返回false
    if ((strIndex != str.length && pattern[patternIndex] == str[strIndex]) || (pattern[patternIndex] == '.' && strIndex != str.length)) {
        return matchCore(str, strIndex + 1, pattern, patternIndex + 1);
    }
    return false;
    }
}
```



# 20. 表示数值的字符串

[NowCoder](https://www.nowcoder.com/practice/6f8c901d091949a5837e24bb82a731f2?tpId=13&tqId=11206&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking&from=cyc_github)

## 题目描述

```
true

"+100"
"5e2"
"-123"
"3.1416"
"-1E-16"
```

```
false

"12e"
"1a3.14"
"1.2.3"
"+-5"
"12e+4.3"
```


## 解题思路

使用正则表达式进行匹配。

```html
[]  ： 字符集合
()  ： 分组
?   ： 重复 0 ~ 1 次
+   ： 重复 1 ~ n 次
*   ： 重复 0 ~ n 次
.   ： 任意字符
\\. ： 转义后的 .
\\d ： 数字
```

```java
public boolean isNumeric(char[] str) {
    if (str == null || str.length == 0)
        return false;
    return new String(str).matches("[+-]?\\d*(\\.\\d+)?([eE][+-]?\\d+)?");
}
```

# 21. 调整数组顺序使奇数位于偶数前面

[NowCoder](https://www.nowcoder.com/practice/beb5aa231adc45b2a5dcc5b62c93f593?tpId=13&tqId=11166&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking&from=cyc_github)

## 题目描述

需要保证奇数和奇数，偶数和偶数之间的相对位置不变，这和书本不太一样。

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/d03a2efa-ef19-4c96-97e8-ff61df8061d3.png" width="200px"> </div><br>
## 解题思路

方法一：创建一个新数组，时间复杂度 O(N)，空间复杂度 O(N)。

```java
public void reOrderArray(int[] nums) {
    // 奇数个数
    int oddCnt = 0;
    for (int x : nums)
        if (!isEven(x))
            oddCnt++;
    int[] copy = nums.clone();
    int i = 0, j = oddCnt;
    for (int num : copy) {
        if (num % 2 == 1)
            nums[i++] = num;
        else
            nums[j++] = num;
    }
}

private boolean isEven(int x) {
    return x % 2 == 0;
}



改进 : 
没改进,熟悉了一下LinkedList而已
    
public void reOrderArray(int[] array) {
        LinkedList<Integer> oddList = new LinkedList<Integer>();
        LinkedList<Integer> evenList = new LinkedList<Integer>();
        for (int i = 0; i < array.length; i++) {
            if (isOdd(array[i])) {
                oddList.add(array[i]);
                continue;
            }
            evenList.add(array[i]);
        }
        int index = 0;
        for (Integer temp : oddList) {
            array[index++] = temp;
        }
        for (Integer temp : evenList) {
            array[index++] = temp;
        }
        return;
    }

    //奇数判断
    private boolean isOdd(int num) {
        if (num % 2 == 1) {
            return true;
        }
        return false;
    }
```

方法二：使用冒泡思想，每次都当前偶数上浮到当前最右边。时间复杂度 O(N<sup>2</sup>)，空间复杂度 O(1)，时间换空间。

```java
public void reOrderArray(int[] nums) {
    int N = nums.length;
    for (int i = N - 1; i > 0; i--) {
        for (int j = 0; j < i; j++) {
            if (isEven(nums[j]) && !isEven(nums[j + 1])) {
                swap(nums, j, j + 1);
            }
        }
    }
}

private boolean isEven(int x) {
    return x % 2 == 0;
}

private void swap(int[] nums, int i, int j) {
    int t = nums[i];
    nums[i] = nums[j];
    nums[j] = t;
}
改进 
    第一个循环是 n-1次循环即可
    第二个循环是  第一次:j为 0~n-2 的array[j]与array[j+1]作比较
        		第二次: j为 0~n-3 的array[j]与array[j+1]作比较


```

# 22. 链表中倒数第 K 个结点

[NowCoder](https://www.nowcoder.com/practice/529d3ae5a407492994ad2a246518148a?tpId=13&tqId=11167&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking&from=cyc_github)

输入一个链表，输出该链表中倒数第k个结点

## 解题思路

设链表的长度为 N。设置两个指针 P1 和 P2，先让 P1 移动 K 个节点，则还有 N - K 个节点可以移动。此时让 P1 和 P2 同时移动，可以知道当 P1 移动到链表结尾时，P2 移动到第 N - K 个节点处，该位置就是倒数第 K 个节点。

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/6b504f1f-bf76-4aab-a146-a9c7a58c2029.png" width="500"/> </div><br>

```java
public ListNode FindKthToTail(ListNode head, int k) {
    if (head == null)
        return null;
    ListNode P1 = head;
    while (P1 != null && k-- > 0)
        P1 = P1.next;
    if (k > 0)
        return null;
    ListNode P2 = head;
    while (P1 != null) {
        P1 = P1.next;
        P2 = P2.next;
    }
    return P2;
}

改进:没啥好改进的
public ListNode FindKthToTail(ListNode head, int k) {
        //p1 先走k
        //p2 和 p1 一起走
        //返回p2
        ListNode p1 = head;
        ListNode p2 = head;
        for (int i = 0; i < k; i++) {
            if (p1 == null) {
                return null;
            }
            p1 = p1.next;
        }
        while (p1 != null) {
            p1 = p1.next;
            p2 = p2.next;
        }
        return p2;
    }
```

# 23. 链表中环的入口结点

[NowCoder](https://www.nowcoder.com/practice/253d2c59ec3e4bc68da16833f79a38e4?tpId=13&tqId=11208&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking&from=cyc_github)

## 题目描述

一个链表中包含环，请找出该链表的环的入口结点。要求不能使用额外的空间。

## 解题思路

使用双指针，一个指针 fast 每次移动两个节点，一个指针 slow 每次移动一个节点。因为存在环，所以两个指针必定相遇在环中的某个节点上。假设相遇点在下图的 z1 位置，此时 fast 移动的节点数为 x+2y+z，slow 为 x+y，由于 fast 速度比 slow 快一倍，因此 x+2y+z=2(x+y)，得到 x=z。

在相遇点，slow 要到环的入口点还需要移动 z 个节点，如果让 fast 重新从头开始移动，并且速度变为每次移动一个节点，那么它到环入口点还需要移动 x 个节点。在上面已经推导出 x=z，因此 fast 和 slow 将在环入口点相遇。

<div align="center"> <img src="https://cs-notes-1256109796.cos.ap-guangzhou.myqcloud.com/bb7fc182-98c2-4860-8ea3-630e27a5f29f.png" width="500"/> </div><br>

```java
public ListNode EntryNodeOfLoop(ListNode pHead) {
    if (pHead == null || pHead.next == null)
        return null;
    ListNode slow = pHead, fast = pHead;
    do {
        fast = fast.next.next;
        slow = slow.next;
    } while (slow != fast);
    fast = pHead;
    while (slow != fast) {
        slow = slow.next;
        fast = fast.next;
    }
    return slow;
}
```



# 24. 反转链表

[NowCoder](https://www.nowcoder.com/practice/75e878df47f24fdc9dc3e400ec6058ca?tpId=13&tqId=11168&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking&from=cyc_github)

## 解题思路

### 递归

```java
public ListNode ReverseList(ListNode head) {
    if (head == null || head.next == null)
        return head;
    ListNode next = head.next;
    head.next = null;
    ListNode newHead = ReverseList(next);
    next.next = head;
    return newHead;
}
```

### 迭代

使用头插法。

```java
public ListNode ReverseList(ListNode head) {
    ListNode newList = new ListNode(-1);
    while (head != null) {
        ListNode next = head.next;
        head.next = newList.next;
        newList.next = head;
        head = next;
    }
    return newList.next;
}
```

# 