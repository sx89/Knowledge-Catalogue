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

#### [53. 最大子序和](https://leetcode-cn.com/problems/maximum-subarray/)

```java
    public int maxSubArray(int[] nums) {
        int len = nums.length;
        int[] dp = new int[len];
        dp[0] = nums[0];
        int max = nums[0];
        for (int i = 1; i < len; i++) {
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
            max = Math.max(dp[i], max);
        }
        return max;
    }
```

#### [155. 最小栈](https://leetcode-cn.com/problems/min-stack/)

```java
	Stack<Integer> realStack = null;
    Stack<Integer> minStack = null;
    private int min = Integer.MAX_VALUE;

    public MinStack() {
        realStack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int x) {
        realStack.push(x);
        if (minStack.isEmpty()) {
            minStack.push(x);
        } else {
            if (minStack.peek() < x) {
                minStack.push(minStack.peek());
            } else {
                minStack.push(x);
            }
        }

    }
```

#### [1. 两数之和](https://leetcode-cn.com/problems/two-sum/)

```java
这个题没有难度,重点:
1.你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。  只对应一个答案的意思是,只有一对满足要求.
2.如果[3,3] target =6; 判断第二个3跟第一个不同的办法是  map.containsKey(two) && map.get(two) != i
public int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length <= 1) {
            return new int[]{};
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; i++) {
            int two = target - nums[i];

            if (map.containsKey(two) && map.get(two) != i) {
                int twoIndex = map.get(two);
                return new int[]{i, twoIndex};
            }
        }
        return new int[]{};
    }

```

#### [141. 环形链表](https://leetcode-cn.com/problems/linked-list-cycle/)

```java
快慢指针法

public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null)
            return false;
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && slow != null) {
            fast = fast.next;
            if (fast == slow)
                return true;
            if (fast != null) {
                fast = fast.next;
            } else {
                return false;
            }
            if (fast == slow)
                return true;
            slow = slow.next;
        }
        return false;
    }

哈希map记录是否访问过
    
    private HashSet<ListNode> set = new HashSet<ListNode>();

    public boolean hasCycle(ListNode head) {
        while (head != null) {
            if (set.contains(head)) {
                return true;
            } else {
                set.add(head);
            }
            head = head.next;
        }
        return false;
    }

```

#### [234. 回文链表](https://leetcode-cn.com/problems/palindrome-linked-list/)

```java
改进:

快慢指针分两种情况
1 2 3 4 5 奇数个: 结束的标志是    fast fast.next=null;fast在5处 slow为3是唯一中点,slow的next为下一半的开始
1 2 3 4	   偶数个: 结束的标志是   fast  fast =null  slow在3处是唯二的靠右边中点,slow即为下一半的开始
public boolean isPalindrome(ListNode head) {
       /* if (head == null) {
            return true;
        }
        if (head.next == null) {
            return true;
        }
        if (head.next.next == null) {
            return head.val == head.next.val;
        }*/
        ListNode slow = head;
        ListNode pre = head;
        ListNode fast = head;
        ListNode leftHead = new ListNode(0);
        while (fast != null && fast.next != null) {
            pre = slow;
            slow = slow.next;

            fast = fast.next.next;

            pre.next = leftHead.next;
            leftHead.next = pre;


        }
        ListNode left = leftHead.next;
        ListNode right = null;
        if (fast == null) {
            right = slow;
        } else {
            right = slow.next;
        }

        while (left != null && right != null) {
            if (left.val != right.val) {
                return false;
            }
            left = left.next;
            right = right.next;
        }
        return true;
    }
```



#### [20. 有效的括号](https://leetcode-cn.com/problems/valid-parentheses/)

```java


Stack<Character> stack = new Stack<>();
    HashMap<Character, Character> map = new HashMap<>();

    public boolean isValid(String s) {
        map.put(')', '(');
        map.put('}', '{');
        map.put(']', '[');
        if (s == null || s.length() == 0) {
            return true;
        }
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (stack.isEmpty()) {
                stack.push(chars[i]);
            } else {
                if (map.get(chars[i]) == stack.peek()) {
                    stack.pop();
                } else {
                    stack.push(chars[i]);
                }
            }
        }
        return stack.isEmpty();
    }
```

#### [39. 组合总和](https://leetcode-cn.com/problems/combination-sum/)

```java
private List<List<Integer>> ret = new ArrayList<>();
    private List<Integer> list = new ArrayList<>();
    int target = 0;
    int len = 0;

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        if (candidates == null || candidates.length == 0 || target < 0) {
            return ret;
        }
        len = candidates.length;
        this.target = target;
        backtracing(0, 0, candidates);
        return ret;
    }

    private void backtracing(int begin, int sum, int[] candidates) {
        if (sum == target) {
            ret.add(new ArrayList(list));
        } else if (sum > target) {
            return;
        }
        for (int i = begin; i < len; i++) {
            list.add(candidates[i]);
            backtracing(i, sum + candidates[i], candidates);
            list.remove(list.size() - 1);
        }
    }private List<List<Integer>> ret = new ArrayList<>();
    private List<Integer> list = new ArrayList<>();
    int target = 0;
    int len = 0;

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        if (candidates == null || candidates.length == 0 || target < 0) {
            return ret;
        }
        len = candidates.length;
        this.target = target;
        backtracing(0, 0, candidates);
        return ret;
    }

    private void backtracing(int begin, int sum, int[] candidates) {
        if (sum == target) {
            ret.add(new ArrayList(list));
        } else if (sum > target) {
            return;
        }
        for (int i = begin; i < len; i++) {
            list.add(candidates[i]);
            backtracing(i, sum + candidates[i], candidates);
            list.remove(list.size() - 1);
        }
    }
```

#### [22. 括号生成](https://leetcode-cn.com/problems/generate-parentheses/)

```java
改进:
String的回溯用String的拼接就比较好  temp+"sdf"; 因为String的删除比较麻烦,而StringBuilder的删除也很耗时,如果题目要求返回的是String而不是StringBuilder,那么不要用StringBuilder来做转化.


List<String> ret = new ArrayList<String>();
int max = 0;
public List<String> generateParenthesis(int n) {
    max = n;
    backtracing(0, 0, "");
    return ret;
}

private void backtracing(int left, int right, String temp) {
    if (temp.length() == 2 * max && left == right) {
        ret.add(temp);
        return;
    }
    if (left >= right && left <= max) { //只有左括号没有超出max;左括号大于等于左括号的个数的时候,才有必要尝试下去(剪枝)
        backtracing(left + 1, right, temp + "(");
        backtracing(left, right + 1, temp + ")");
    }
}
```



#### [105. 从前序与中序遍历序列构造二叉树](https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/)

```java
 public TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildTree(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    private TreeNode buildTree(int[] preorder, int preStart, int preEnd, int[] inorder, int inStart, int inEnd) {
        if (preStart > preEnd || inStart > inEnd || preStart < 0 || preEnd >= preorder.length || inStart < 0 || inEnd >= inorder.length) {
            return null;
        }
        int rootVal = preorder[preStart];
        int cutIndex = 0;
        for (int i = inStart; i <= inEnd; i++) {
            if (inorder[i] == rootVal) {
                cutIndex = i;
                break;
            }
        }
        int leftLen = cutIndex - inStart;
        TreeNode root = new TreeNode(rootVal);
        root.left = buildTree(preorder, preStart + 1, preStart + leftLen, inorder, inStart, cutIndex - 1);
        root.right = buildTree(preorder, preStart + leftLen + 1, preEnd, inorder, cutIndex + 1, inEnd);
        return root;
    }
```



















































# 要再刷一遍



#### [46. 全排列](https://leetcode-cn.com/problems/permutations/)

```java
改进  典型的回溯法问题,一定要熟练掌握.

private List<List<Integer>> ret = new ArrayList<>();
boolean[] visited;
private List<Integer> path = new ArrayList<>();

public List<List<Integer>> permute(int[] nums) {
    int len = nums.length;
    visited = new boolean[len];
    backtracing(nums);

    return ret;
}

private void backtracing(int[] nums) {
    if (path.size() == nums.length) {
        ret.add(new ArrayList(path));
    }
    for (int i = 0; i < nums.length; i++) {
        if (visited[i])
            continue;

        path.add(nums[i]);
        visited[i] = true;
        backtracing(nums);
        visited[i] = false;
        path.remove(path.size() - 1);
    }

}
```

#### [78. 子集](https://leetcode-cn.com/problems/subsets/)

```java
   List<List<Integer>> ret = new ArrayList<>();
    ArrayList<Integer> list = new ArrayList<>();
    int len = 0;

    public List<List<Integer>> subsets(int[] nums) {
        len = nums.length;
        backtracing(0, nums);
        return ret;
    }

    private void backtracing(int begin, int[] nums) {
        ret.add(new ArrayList(list));
        for (int i = begin; i < len; i++) {
            list.add(nums[i]);
            backtracing(i + 1,nums);
            list.remove(list.size()-1);
        }
    }
```

#### [338. 比特位计数](https://leetcode-cn.com/problems/counting-bits/)

```java

改进 动态规划的方法
    0 可以得出 1               2^0
    0 和 1 能得出  2 和 3       2^1
    0 1 2 3 能得出  4 5 6 7    2^2
public int[] countBits(int num) {
        int[] ans = new int[num + 1];
        ans[0] = 0;
        int b = 1;
        int i = 0;
        while (b <= num) {
            while (i < b && b + i <= num) {
                ans[b + i] = ans[i] + 1;
                i++;
            }
            b <<= 1;

            i = 0;
        }
        return ans;
    }
```





#### 543. [二叉树的直径](https://leetcode-cn.com/problems/diameter-of-binary-tree/)



```java
改进: 先根遍历/也是深度优先搜索 ,每个root求左右子树长度之和,注意最终的直径也许不会经过根节点,比如
      2
    3   1
  6  4
  9
  8
private int maxLength = Integer.MIN_VALUE;

    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null)
            return 0;
        preOrder(root);
        return maxLength;
    }

    private int preOrder(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int lenL = preOrder(root.left);
        int lenR = preOrder(root.right);
        int length = lenL + lenR; //root不算在总长度里面
        maxLength = Math.max(maxLength, length);
        return Math.max(lenL, lenR) + 1;
    }
```



#### [96. 不同的二叉搜索树](https://leetcode-cn.com/problems/unique-binary-search-trees/)

```java
改进: 从1~n,每个节点都可以当根来组成二叉搜索树.
    如果1是根,则左右两边的节点数分别是0,n-1;能组成的搜索树个数为:dp[0]*dp[n-1]
    如果2是根,则左右两边的节点数分别是1,n-2;能组成的搜索树个数为:dp[1]*dp[n-2]
    所以第一个循环是从i=2求到i=n的dp[n]有多少种搜索树的组成
    第二个循环是在求dp[i]的1~分别当根节点,组成的搜索树个数

public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }
        return dp[n];
    }
```



#### [64. 最小路径和](https://leetcode-cn.com/problems/minimum-path-sum/)

```java
	如果是求和,用动规. 如果求path的具体内容比如匹配路径字符串(abcd)则用回溯

改进:用回溯法会超时;不过注意回溯法的用法:
	不要用xy   x和y在数组的申请和定位数据的时候是反直觉的grid[y][x] new int[yBound][xBound]
    用row col  row和col的含义是第几row和 第几col
    grid[row][col] new int [row][col]    
    
    
    boolean[][] visited = null;
    int min = Integer.MAX_VALUE;
    int[][] loc = new int[][]{{1, 0}, {-1, 0}, {0, -1}, {0, 1}};
    int path = 0;
    int targetRow = 0;
    int targetCol = 0;

    public int minPathSum(int[][] grid) {
        int rowCount = grid.length;
        int colCount = grid[0].length;
        targetRow = rowCount - 1;
        targetCol = colCount - 1;
        visited = new boolean[rowCount][colCount];
        visited[0][0] = true;
        path = grid[0][0];
        backtracing(grid, path, 0, 0);
        return min;
    }

    private void backtracing(int[][] grid, int path, int row, int col) {

        if (row == targetRow && col == targetCol) {
            min = Math.min(min, path);
        }
        for (int i = 0; i < loc.length; i++) {
            col = col + loc[i][1];
            row = row + loc[i][0];
            if (!(row < 0 || col < 0 || row > targetRow || col > targetCol || visited[row][col] == true)) {
                visited[row][col] = true;
                backtracing(grid, path + grid[row][col], row, col);
                visited[row][col] = false;
            }
            col = col - loc[i][1];
            row = row - loc[i][0];
        }
    }


改进:动态规划的做法
    状态转移方程: dp(i,j)=grid(i,j)+min(dp(i+1,j),dp(i,j+1))
        
    时间复杂度O(mn)已经无法优化,空间复杂度O(mn)可以优化为O(1),就是用grid本身来做dp.
    
    public int minPathSum(int[][] grid) {
        int rowCount = grid.length;
        int colCount = grid[0].length;
        int[][] dp = new int[rowCount][colCount];
        dp[0][0] = grid[0][0];
        for (int i = 1; i < rowCount; i++) {
            dp[i][0] = grid[i][0] + dp[i - 1][0];
        }
        for (int j = 1; j < colCount; j++) {
            dp[0][j] = grid[0][j] + dp[0][j - 1];
        }
        for (int i = 1; i < rowCount; i++) {
            for (int j = 1; j < colCount; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[rowCount - 1][colCount - 1];
    }
```



#### [437. 路径总和 III](https://leetcode-cn.com/problems/path-sum-iii/)

```java
改进:双递归的题,有点难度.
    pathSum的递归用于遍历所有节点,根节点是不断变化的
    pathFromRoot的递归用于找到一条root为根节点,root的子节点为终止节点的路径

public int pathSum(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }
        return pathFromRoot(root, sum) + pathSum(root.left, sum) + pathSum(root.right, sum);
    }

    private int pathFromRoot(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }
        int ret = 0;
        if (root.val == sum) {
            ret = 1;
        }
        ret += pathFromRoot(root.left, sum - root.val);
        ret += pathFromRoot(root.right, sum - root.val);
        return ret;
    }
```



#### [238. 除自身以外数组的乘积](https://leetcode-cn.com/problems/product-of-array-except-self/)

```java
改进:空间复杂度O(1) 要么是在ret上做累加,要么是原数组nums上做修改
	时间复杂度O(n)必然是之前计算的结果可以拿到后面用.
	ret[i] 等于nums的 i左边的数 乘以 i右边的数
public int[] productExceptSelf(int[] nums) {
        int len = nums.length;
        int[] ret = new int[len];
        ret[0] = 1;
        for (int i = 1; i < len; i++) {
            ret[i] = ret[i - 1] * nums[i - 1];
        }
        int temp = 1;
        for (int i = len - 1; i >= 0; i--) {
            ret[i] = ret[i] * temp;
            temp = temp * nums[i];
        }
        return ret;
    }
```

#### [48. 旋转图像](https://leetcode-cn.com/problems/rotate-image/)

```java
改进: 先转置,在镜像翻转  =  往右旋转九十度

public void rotate(int[][] matrix) {
        int len = matrix.length;
        //转置
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        //镜像翻转
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len / 2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][len - j - 1];
                matrix[i][len - j - 1] = temp;
            }
        }
    }
```











































































