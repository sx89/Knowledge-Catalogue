#### [617. 合并二叉树](https://leetcode-cn.com/problems/merge-two-binary-trees/)

```java
改进:
当其中一颗树为空了之后,要学会剪枝
    
public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        TreeNode root;
        if (t1 != null && t2 != null) {
            root = new TreeNode(t1.val + t2.val);
            root.left = mergeTrees(t1.left, t2.left);
            root.right = mergeTrees(t1.right, t2.right);
        } else if (t1 == null && t2 == null) {
            return null;
        } else if (t1 == null) {
            return t2;
        } else {
            return t1;
            //下面的可以剪枝
           // root = new TreeNode(t1.val);
            // root.left = mergeTrees(t1.left,null);
            // root.right = mergeTrees(t1.right,null);
        }
        return root;
    }

改进:非迭代的先序遍历 while(p!=null&&!s.isEmpty()){  在这里访问节点 while(p!=null)p = p.left; }
    中序遍历 while(p!=null&&!s.isEmpty()){   while(p!=null)p = p.left;   } 在后续pop出来的时候访问
	后序遍历比较麻烦.懒得记
    
     public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 != null && t2 != null) {
            stack.push(t2);
            stack.push(t1);
        } else {
            return t1 == null ? t2 : t1;
        }
        while (!stack.isEmpty()) {
            TreeNode s1 = stack.pop();
            TreeNode s2 = stack.pop();
            s1.val = s1.val + s2.val;
            if (s1.left != null && s2.left != null) {
                stack.push(s2.left);
                stack.push(s1.left);
            } else if (s2.left != null) {
                s1.left = s2.left;
            }
            if (s1.right != null && s2.right != null) {
                stack.push(s2.right);
                stack.push(s1.right);

            } else if (s2.right != null) {
                s1.right = s2.right;
            }
        }
        return t1;
    }

```

#### [538. 把二叉搜索树转换为累加树](https://leetcode-cn.com/problems/convert-bst-to-greater-tree/)



```java
改进:树的非递归中序遍历  
    树的非递归前序遍历与中序遍历的区别是,前序遍历在压栈的时候做节点数据访问;
	树的非递归前序遍历还可以用深度优先搜索的写法,详情看226题翻转二叉树.
//非递归中序遍历变式 (右,根,左)
    private Stack<TreeNode> s = new Stack<TreeNode>();
    private int sum = 0;

    public TreeNode convertBST(TreeNode root) {
        if (root == null)
            return null;
        TreeNode p = root;
        while (!s.isEmpty() || p != null) {
            while (p != null) {
                //前序遍历访问数据处理
                s.push(p);
                p = p.right;
            }
            if (!s.isEmpty()) {
                p = s.pop();
                //中序遍历访问数据处
                sum += p.val;
                p.val = sum;
            }
            p = p.left;
        }
        return root;
    }
```

#### [226. 翻转二叉树](https://leetcode-cn.com/problems/invert-binary-tree/)

```java
深度优先搜索相当于先序遍历,
广度优先搜索相当于层次遍历.


 //迭代,先序遍历,
    private Stack<TreeNode> s = new Stack<TreeNode>();

    public TreeNode invertTree(TreeNode root) {
        if (root == null)
            return null;
        TreeNode p = root;
        while (!s.isEmpty() || p != null) {
            while (p != null) {
                TreeNode temp = p.right;
                p.right = p.left;
                p.left = temp;

                s.push(p);
                p = p.left;
            }
            if (!s.isEmpty()) {
                p = s.pop();
            }
            p = p.right;
        }
        return root;
    }
```



#### [104. 二叉树的最大深度](https://leetcode-cn.com/problems/maximum-depth-of-binary-tree/)

```java
树的深度优先遍历,也是树的第二种迭代先序遍历 

private Stack<Pair> s = new Stack<Pair>();
    private int maxDeep = 0;

    public int maxDepth(TreeNode root) {
        if (root == null)
            return maxDeep;
        s.push(new Pair(1, root));
        maxDeep = 1;

        while (!s.isEmpty()) {
            Pair p = s.pop();
            int curDeep = p.deepth;
            maxDeep = Math.max(curDeep, maxDeep);
            TreeNode node = p.node;
            if (node.left != null)
                s.push(new Pair(curDeep + 1, node.left));
            if (node.right != null)
                s.push(new Pair(curDeep + 1, node.right));
        }
        return maxDeep;
    }

    class Pair {
        int deepth;
        TreeNode node;

        Pair(int deepth, TreeNode node) {
            this.deepth = deepth;
            this.node = node;
        }
    }
```

#### [136. 只出现一次的数字](https://leetcode-cn.com/problems/single-number/)

```java
思路
1.求差  2(a+b+c) - (a+a+b+b+c)   第一步要求不重复,应该想到用set,Iterator<Integer> i = s.iterator();
2.异或
3.hashMap第一次出现存到map,第二次出现从map里面删除掉
```



#### [169. 多数元素](https://leetcode-cn.com/problems/majority-element/)

```java
public int majorityElement(int[] nums) {
        if (nums == null || nums.length == 0)
            return -1;
        int maxNum = nums[0];
        int count = 1;
        for (int temp : nums) {
            if (temp == maxNum) {
                count++;
            } else {
                count--;
                if (count == 0) {
                    maxNum = temp;
                    count = 1;
                }
            }
        }
        count = 0;
        for (int temp : nums) {
            if (maxNum == temp)
                count++;
        }
        if (count > nums.length / 2)
            return maxNum;
        else
            return -1;
    }
```



#### [21. 合并两个有序链表](https://leetcode-cn.com/problems/merge-two-sorted-lists/)

```java
public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode last = new ListNode(-1);
        ListNode pHead = last;
        while (l1 != null || l2 != null) {

            if (l1 == null && l2 != null) {
                l1 = l2;
                l2 = null;
            }
            while (l2 == null && l1 != null) {
                ListNode next = l1.next;
                l1.next = last.next;
                last.next = l1;
                l1 = next;
                last = last.next;
            }
            while (l1 != null && l2 != null) {
                ListNode min = null;
                if (l1.val <= l2.val) {
                    min = l1;
                    l1 = l1.next;
                } else {
                    min = l2;
                    l2 = l2.next;
                }
                min.next = last.next;
                last.next = min;
                last = last.next;
            }
        }
        return pHead.next;
    }
```

#### [283. 移动零](https://leetcode-cn.com/problems/move-zeroes/)

```java
改进:把前面index个位置填满正确的数,最后把index后面的位用0填充
public void moveZeroes(int[] nums) {
        int index = 0;
        for(int temp:nums){
            if(temp!=0){
                nums[index++] = temp;
            }
        }
        for(int i =index;i<nums.length;i++){
            nums[i] = 0;
        }
    }
```

#### [448. 找到所有数组中消失的数字](https://leetcode-cn.com/problems/find-all-numbers-disappeared-in-an-array/)

```java
改进:
一种意外情况需要考虑   数列: 3,2,3  i=0的时候的3应该放在下标为2的地方,也就是第二个3所在的地方,
但如果一直交换的话,会陷入死循环,所以判断条件里加了一句   nums[i] != nums[index]  ,意思是i所
在位置的值num[i],在index(num[i]-1]处已经存在值num[i]-1了.这个时候不做交换,直接跳过.
                                                                                                                          
  具体来说就是  i=0处 的3应该放在index=2处,但index=2处已经有3了,所以i++,继续往后计算.

public List<Integer> findDisappearedNumbers(int[] nums) {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        for (int i = 0; i < nums.length; ) {
            int index = nums[i] - 1;
            if (index != i && nums[i] != nums[index]) {
                int temp = nums[index];
                nums[index] = nums[i];
                nums[i] = temp;
            } else {
                i++;
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] - 1 != i) {
                ret.add(i + 1);
            }
        }
        return ret;
    }
                                                                                                                                         
方法二:                                                                                                                                         HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    public List<Integer> findDisappearedNumbers(int[] nums) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], 1);
        }
        for (int j = 1; j <= nums.length; j++) {
            if (map.get(j) == null) {
                list.add(j);
            }
        }
        return list;
    }
                                                                                                                                         
                                                                                                                                         改进                                                                                                                               关于map的遍历                                                                                                                         Set<Map.Entry<Integer,Integer>> set = map.entrySet();                                                                                    Iterator<Map.Entry<Integer,Integer>> it = set.iterator();
                                                                                                                                         
```

#### [160. 相交链表](https://leetcode-cn.com/problems/intersection-of-two-linked-lists/)

```java
 public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int lenA = 0;
        int lenB = 0;
        ListNode pA = headA;
        ListNode pB = headB;
        while (pA != null) {
            lenA++;
            pA = pA.next;
        }
        while (pB != null) {
            lenB++;
            pB = pB.next;
        }
        if (lenB > lenA) {
            int temp = lenA;
            lenA = lenB;
            lenB = temp;
            pA = headB;
            pB = headA;
        } else {
            pA = headA;
            pB = headB;
        }
        for (int i = 1; i <= lenA - lenB; i++) {
            pA = pA.next;
        }
        while (pA != null) {
            if (pA == pB) {
                return pA;
            }
            pA = pA.next;
            pB = pB.next;
        }
        return null;
    }
```



```

```



```

```



```



```



```

```

















