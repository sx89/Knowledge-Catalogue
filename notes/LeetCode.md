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

