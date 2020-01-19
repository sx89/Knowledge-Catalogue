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



#### [102. 二叉树的层次遍历](https://leetcode-cn.com/problems/binary-tree-level-order-traversal/)



```java
改进:
思路有 1. 层次遍历(每层入队列的时候记录下一层有多少个子节点)for (int i = 0; i < levelCount; i++)
      2.深度优先遍历(先根遍历)

//层次遍历源码
Queue<TreeNode> queue = new LinkedList<TreeNode>();

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<>();
        if (root == null) {
            return ret;
        }
        queue.add(root);
        int levelCount = 1;

        while (!queue.isEmpty()) {
            List<Integer> list = new ArrayList<Integer>();
            int temp = 0;
            for (int i = 0; i < levelCount; i++) {
                TreeNode node = queue.poll();
                list.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                    temp++;
                }
                if (node.right != null) {
                    queue.add(node.right);
                    temp++;
                }
            }
            levelCount = temp;
            ret.add(list);
        }
        return ret;
    }


//深度优先源码

List<List<Integer>> ret = new ArrayList<>();

    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return ret;
        }
        preOrder(root, 0);

        return ret;

    }

    private void preOrder(TreeNode root, int level) {
        if (root == null) {
            return;
        }
        List<Integer> temp = null;
        if (ret.size() <= level) {
            temp = new ArrayList<Integer>();
            ret.add(temp);
        }
        temp = ret.get(level);
        temp.add(root.val);
        preOrder(root.left, level + 1);
        preOrder(root.right, level + 1);
    }
```



#### [347. 前 K 个高频元素](https://leetcode-cn.com/problems/top-k-frequent-elements/)



```java
改进:
map来记录出现次数
小顶堆来记录topk

注意比较器的写法:return map.get(i1) - map.get(i2);
注意k不能决定堆得最大容量,堆会自动扩容,所以要在add元素进堆得时候进行大小判断,如果size()>k,则add之后poll一个出来


    HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    public List<Integer> topKFrequent(int[] nums, int k) {
        Comparator<Integer> c = new Comparator<Integer>() {
            public int compare(Integer i1, Integer i2) {
                return map.get(i1) - map.get(i2);
            }
        };
        PriorityQueue<Integer> heap = new PriorityQueue<Integer>(k, c);
        for (int temp : nums) {
            if (map.get(temp) == null) {
                map.put(temp, 1);
            } else {
                map.put(temp, map.get(temp) + 1);
            }
        }
        Set<Map.Entry<Integer, Integer>> s = map.entrySet();
        Iterator<Map.Entry<Integer, Integer>> i = s.iterator();
        while (i.hasNext()) {
            if (heap.size() < k) {
                heap.add(i.next().getKey());
            } else {
                heap.add(i.next().getKey());
                heap.poll();
            }
        }
        List<Integer> list = new ArrayList<Integer>();
        while (!heap.isEmpty()) {
            list.add(heap.poll());
        }
        return list;
    }
```



#### [17. 电话号码的字母组合](https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/)

```java
回溯,没啥好说的

Map<String, String> numsAndLetters = new HashMap<String, String>() {{
    put("2", "abc");
    put("3", "def");
    put("4", "ghi");
    put("5", "jkl");
    put("6", "mno");
    put("7", "pqrs");
    put("8", "tuv");
    put("9", "wxyz");
}};
List<String> ret = new ArrayList<String>();
int len = 0;
public List<String> letterCombinations(String digits) {
    if (digits == null || digits.length() == 0) {
        return ret;
    }
    len = digits.length();
    backtracing(0, "", digits);
    return ret;
}

private void backtracing(Integer index, String path, String digits) {
    if (index == len) {
        ret.add(path);
        return;
    }
    String numLetters = numsAndLetters.get(digits.substring(index, index + 1));
    for (int i = 0; i < numLetters.length(); i++) {
        String temp = numLetters.substring(i, i + 1);
        backtracing(index + 1, path + temp, digits);
    }
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

#### [49. 字母异位词分组](https://leetcode-cn.com/problems/group-anagrams/)

```java
改进:
每个字符串"cba" 用排序之后的"abc"作为key存储在HashMap<String, ArrayList<String>>;
key相同的字符串放在map的value即ArrayList里面

public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        for (int i = 0; i < strs.length; i++) {
            String str = strs[i];
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String strSort = new String(chars);

            if (map.get(strSort) == null) {
                ArrayList<String> list = new ArrayList<String>();
                list.add(str);
                map.put(strSort, list);
            } else {
                ArrayList<String> list = map.get(strSort);
                list.add(str);
                map.put(strSort, list);
            }
        }
        List<List<String>> ret = new ArrayList<>();
        Set<Map.Entry<String, ArrayList<String>>> s = map.entrySet();
        Iterator<Map.Entry<String, ArrayList<String>>> it = s.iterator();
        while (it.hasNext()) {
            ret.add(it.next().getValue());
        }
        return ret;
    }

```



#### [739. 每日温度](https://leetcode-cn.com/problems/daily-temperatures/)

```java
改进:逆序遍历,记录以往的温度的最大值,因为如果 倒数第二天温度是75, 最后一天温度是70,则倒数第三天的时候如果温度是76比75大,则继续往后找也找不到比76大的天气了,因为75是未来几天最大的温度.

Stack<Integer> stack = new Stack<Integer>();

    public int[] dailyTemperatures(int[] T) {

        int len = T.length;
        int[] ans = new int[len];
        for (int i = len - 1; i >= 0; i--) {
            int temp = T[i];
            while (!stack.isEmpty()) {
                if (T[stack.peek()] <= temp) {
                    stack.pop();
                } else {
                    break;
                }
            }
            if (stack.isEmpty()) {
                ans[i] = 0;
            } else {
                ans[i] = stack.peek() - i;
            }
            stack.push(i);
        }
        return ans;
    }

改进:获取比当前温度temp高的所有温度的日期,找距离当前日期最近的那个

public int[] dailyTemperatures(int[] T) {
        int len = T.length;
        int[] ans = new int[len];
        int[] next = new int[101];
        for (int i = len - 1; i >= 0; i--) {
            int temp = T[i];
            next[temp] = i;
            int warmerIndex = Integer.MAX_VALUE;
            ans[i] = 0;
            for (int j = temp + 1; j <= 100; j++) {
                if (next[j] != 0) {
                    warmerIndex = Math.min(next[j], warmerIndex);
                    ans[i] = warmerIndex - i;
                }
            }

        }
        return ans;
    }
```



#### [406. 根据身高重建队列](https://leetcode-cn.com/problems/queue-reconstruction-by-height/)

```java
改进:先根据身高从大到小,k从小到大来排数组,然后将排好序的数组往另一个数组里插入,插完就是想要的结果
public int[][] reconstructQueue(int[][] people) {
    Comparator c = new Comparator<int[]>() {
        public int compare(int[] o1, int[] o2) {
            return o1[0] == o2[0] ? o1[1] - o2[1] : o2[0] - o1[0];
        }
    };
    Arrays.sort(people, c);
    ArrayList<int[]> list = new ArrayList<>();
    for (int[] temp : people) {
        list.add(temp[1], temp);
    }
    return list.toArray(new int[list.size()][2]);
}
```





#### [11. 盛最多水的容器](https://leetcode-cn.com/problems/container-with-most-water/)

```java
改进:
思路是双指针法,左右两边的指针往中间移动
public int maxArea(int[] height) {
    int left = 0;
    int right = height.length - 1;
    int maxArea = 0;
    while (left < right) {
        maxArea = Math.max(maxArea, Math.min(height[left], height[right]) * (right - left));
        if (height[left] > height[right]) {
            right--;
        } else {
            left++;
        }
    }
    return maxArea;
}
```

#### [647. 回文子串](https://leetcode-cn.com/problems/palindromic-substrings/)

```java
改进:动态规划的做法
     i-j<2  在i-j为0的时候,i和j为同一个值;i-j为1的时候由于前面的
     s.charAt(i)==s.charAt(j)也决定了它们是相等的
public int countSubstrings(String s) {
    int len = s.length();
    boolean[][] dp = new boolean[len][len];
    int ret = 0;
    for (int i = 0; i < len; i++) {
        for (int j = i; j >= 0; j--) {
            //i-j<2  在i-j为0的时候,i和j为同一个值;i-j为1的时候由于前面的
            //s.charAt(i)==s.charAt(j)也决定了它们是相等的
            if (s.charAt(i) == s.charAt(j) && ((i - j < 2) || dp[i - 1][j + 1])) {
                dp[i][j] = true;
                ret++;
            }
        }
    }
    return ret;
}
改进: 从中间往两边扩展,注意拓展方式有两种,  aba 以b为对称线  和 aa以两个a之间为对称线
    对应的代码是  int left = i;
           		 int right = left;
				left = i;
           		 right = left + 1;
	
     public int countSubstrings(String s) {
        int len = s.length();
        int res = 0;
        for (int i = 0; i < len; i++) {
            int left = i;
            int right = left;
            while (left >= 0 && right < len && s.charAt(left) == s.charAt(right)) {
                left--;
                right++;
                res++;
            }
            left = i;
            right = left + 1;
            while (left >= 0 && right < len && s.charAt(left) == s.charAt(right)) {
                left--;
                right++;
                res++;
            }

        }
        return res;
    }
```



#### [198. 打家劫舍](https://leetcode-cn.com/problems/house-robber/)

```java
改进:动态规划类的题目,找好状态转义方程+定好不满足方程的边界条件就可以解出来


dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]); 第i天的收获 = Max(昨天,前天+今天);
关于边界处的思考: dp[0] = nums[0];dp[1] = Math.max(nums[0], nums[1]);也就是说dp[1]可以是nums[0]也可以是nums[1],我会有疑惑: 如果dp[1]选的是nums[0],那dp[2]在选dp[1]=nums[0]的时候还有必要隔一天吗?直接dp[2]=dp[1]+nums[2]不就好了
    
事实上,dp[1] =  nums[0]  或  nums[1]
     dp[2] =  max(dp[1],dp[0]+nums[2])分下面两种情况
    当dp[1] = nums[1]时
		max(dp[1],dp[0]+nums[2])其实是  nums[1] 和 nums[0]+nums[2]比较大小
     当dp[1] = nums[0]时
    	 max(dp[1],dp[0]+nums[2])其实是  nums[0] 和 nums[0]+nums[2]比较大小;该情况下必选dp[0]+nums[2],所以上面疑惑的那种情况出现的时候,选dp[1]+nums[2](也就是dp[0]+nums[2])而非dp[1]是必然的,而且能满足隔一个房间.

public int rob(int[] nums) {
    int len = nums.length;
    if (len == 0)
        return 0;
    if (len == 1) {
        return nums[0];
    }
    int[] dp = new int[len];
    dp[0] = nums[0];
    dp[1] = Math.max(nums[0], nums[1]);
    for (int i = 2; i < len; i++) {
        dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
    }
    return dp[len - 1];
}
```

#### [279. 完全平方数](https://leetcode-cn.com/problems/perfect-squares/)

```java
改进:状态转移方程 dp[i] = Math.min(dp[i],dp[i-1],dp[i-4],dp[i-16],dp[i-j*j])

public int numSquares(int n) {
    int[] dp = new int[n + 1];
    for (int i = 1; i <= n; i++) {
        dp[i] = i;
        for (int j = 1; i - j * j >= 0; j++) {
            dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
        }
    }
    return dp[n];
}


```

#### [75. 颜色分类](https://leetcode-cn.com/problems/sort-colors/)

```java
改进:统计每个颜色有几个,然后填到数组中

//借助常数空间意味着在原来的数组上改动,并且用temp之类的变量来记录信息
    public void sortColors(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int temp : nums) {
            if (map.get(temp) == null) {
                map.put(temp, 1);
            } else {
                map.put(temp, map.get(temp) + 1);
            }
        }
        int color = -1;
        int colorCount = 0;
        for (int i = 0; i < nums.length; ) {
            if (colorCount > 0) {
                nums[i++] = color;
                colorCount--;
            } else {
                color++;
                if (map.get(color) == null) {
                    colorCount = 0;
                } else {
                    colorCount = map.get(color);
                }
            }
        }
    }

改进: 思路  left->   cur->   <-right
 //常数空间(用一些小数据记录信息,temp交换数据))+一趟遍历(修改原数组)
    public void sortColors(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        int cur = 0;
        while (cur <= right) {
            if (nums[cur] == 0) {
                int temp = nums[cur];
                nums[cur] = nums[left];
                nums[left] = temp;
                left++;
                cur++;//left++的时候cur++是因为cur是从左边开始往右走,
                //如果left++,代表cur的左边界往右走去了.这个时候cur++就又回到了左边界最开始的地方
            } else if (nums[cur] == 2) {
                int temp = nums[cur];
                nums[cur] = nums[right];
                nums[right] = temp;
                right--;
            } else {
                cur++;
            }
        }
    }
```



#### [142. 环形链表 II](https://leetcode-cn.com/problems/linked-list-cycle-ii/)

```java
改进:公式  x + y+ z + y = 2(x+y)推出  x = z 所以这个时候把快指针放到phead处,走x;慢指针走z;两个指针就会在
    环入口处相遇
    
    注意代码的逻辑上有几个要注意的问题
     while (fast != null && fast.next != null) {//走两步必备的判空
         
     if (fast == null || fast.next == null) {  //如果fast是因为空指针跳出while循环则不存在环
        return null;
    }

public ListNode detectCycle(ListNode head) {
    if (head == null) {
        return null;
    }
    if (head.next == null) {
        return null;
    }
    ListNode fast = head;
    ListNode slow = head;
    while (fast != null && fast.next != null) {//走两步必备的判空
        fast = fast.next.next;
        slow = slow.next;
        if (fast == slow) {
            break;
        }
    }
    if (fast == null || fast.next == null) {  //如果fast是因为空指针跳出while循环则不存在环
        return null;
    }
    fast = head;
    while (fast != slow) {                  //如果fast不是因为空指针跳出while循环,fast和slow分别在
        									//走了x 和 z步后相遇(x=z)
        fast = fast.next;
        slow = slow.next;
    }
    return slow;
}
```

#### [394. 字符串解码](https://leetcode-cn.com/problems/decode-string/)

```java
改进:代码太牛,记住就好
public String decodeString(String s) {
    if (s == null || s.length() == 0) {
        return "";
    }
    StringBuilder res = new StringBuilder();
    int num = 0;
    Stack<Integer> stack_num = new Stack<Integer>();
    Stack<String> stack_res = new Stack<String>();
    for (char c : s.toCharArray()) {
        if (c == '[') {
            stack_res.push(res.toString());
            stack_num.push(num);
            res = new StringBuilder();
            num = 0;
        } else if (c == ']') {
            StringBuilder temp = new StringBuilder();
            int repeat = stack_num.pop();
            for (int i = 0; i < repeat; i++) {
                temp = temp.append(res);

            }
            res = new StringBuilder(stack_res.pop() + temp);
        } else if (c >= '0' && c <= '9') {
            num = num * 10 + Integer.parseInt(c + "");
        } else {
            res.append(c);
        }
    }
    return res.toString();
}
```





#### [337. 打家劫舍 III](https://leetcode-cn.com/problems/house-robber-iii/)



改进:树的后续遍历+动态规划

```java


HashMap<TreeNode, Integer> memo = new HashMap<TreeNode, Integer>();

public int rob(TreeNode root) {
    if (root == null) {
        return 0;
    }
    lastOrder(root);
    return memo.get(root);
}

private int lastOrder(TreeNode root) {
    if (root == null) {
        return 0;
    }

    if (memo.containsKey(root)) return memo.get(root);
    int money1 = root.val;
    int money2 = 0;
    int maxMoney = 0;
    if (root.left != null) {
        money2 += lastOrder(root.left);
        money1 += lastOrder(root.left.left);
        money1 += lastOrder(root.left.right);

    }
    if (root.right != null) {
        money2 += lastOrder(root.right);
        money1 += lastOrder(root.right.left);
        money1 += lastOrder(root.right.right);

    }
    maxMoney = Math.max(money1, money2);
    memo.put(root, maxMoney);
    return maxMoney;
}
```

#### [121. 买卖股票的最佳时机](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/)

```java
public int maxProfit(int[] prices) {
    if (prices == null || prices.length == 0)
        return 0;

    int len = prices.length;
    int[][] dp = new int[len][2];

    for (int i = 0; i < len; i++) {
        if (i == 0) {
            dp[0][0] = 0;
            dp[0][1] = -prices[0];
        } else {

            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(0 - prices[i], dp[i - 1][1]);
            //0-prices[i]而非dp[i-1][1]-prices[i]是为了防止多次买卖,

        }
    }
    return dp[len - 1][0];
}
```



#### [309. 最佳买卖股票时机含冷冻期](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/)

改进:动态规划类的题目:1.找状态和选择2.定好base_case 就这两点而已,详情看下面大神题解
    https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/solution/yi-ge-fang-fa-tuan-mie-6-dao-gu-piao-wen-ti-by-lab/

```java


public int maxProfit(int[] prices) {
    if (prices == null || prices.length == 0) {
        return 0;
    }
    int len = prices.length;
    int[][] dp = new int[len][2];

    for (int i = 0; i < len; i++) {
        if (i == 0) {
            dp[i][0] = 0;
            dp[i][1] = -prices[0];
        } else if (i == 1) {
            dp[1][0] = Math.max(dp[0][0], dp[0][1] + prices[i]);
            dp[1][1] = Math.max(dp[0][1], -prices[i]);
        } else {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 2][0] - prices[i]);
        }
    }
    return dp[len - 1][0];
}
```



#### [123. 买卖股票的最佳时机 III](https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iii/)

改进:k表示交易了多少次

```java
public int maxProfit(int[] prices) {
    if (prices == null || prices.length == 0) {
        return 0;
    }
    int len = prices.length;
    int max_k = 2;
    int[][][] dp = new int[len][max_k + 1][2];
    for (int i = 0; i < len; i++) {
        //k=1是交易了一次,k=2 是交易了两次,而不是剩余几次机会
        for (int k = 1; k <= max_k; k++) { //改成k++
            if (i == 0) {
                dp[0][k][0] = 0;
                dp[0][k][1] = -prices[0];
            } else {
                dp[i][k][0] = Math.max(dp[i - 1][k][0], dp[i - 1][k][1] + prices[i]);
                dp[i][k][1] = Math.max(dp[i - 1][k][1], dp[i - 1][k - 1][0] - prices[i]);
            }
        }

    }
    return dp[len - 1][2][0];
}
```

#### [621. 任务调度器](https://leetcode-cn.com/problems/task-scheduler/)



```java
改进:
容易出错的地方:
1.当queue是空并且list里面也没有候选,要提前退出,cpu不需要走完完整的一个轮回(n+1)个任务
    if (frequencyOfChar.isEmpty() && frequencyDcreByOneList.size() == 0)
                    break;


public int leastInterval(char[] tasks, int n) {
        HashMap<Character, Integer> map = new HashMap<>();
        Comparator<Integer> com = new Comparator<Integer>() {
            public int compare(Integer i1, Integer i2) {
                return i2 - i1;
            }
        };
        PriorityQueue<Integer> frequencyOfChar = new PriorityQueue<Integer>(26, com);
        for (char c : tasks) {
            if (map.get(c) == null) {
                map.put(c, 1);
            } else {
                map.put(c, map.get(c) + 1);
            }
        }
        for (Map.Entry<Character, Integer> temp : map.entrySet()) {
            frequencyOfChar.add(temp.getValue());
        }
        int taskTimeSum = 0;

        while (!frequencyOfChar.isEmpty()) {
            int i = 0;
            LinkedList<Integer> frequencyDcreByOneList = new LinkedList<Integer>();
            while (i <= n) {//小于等于是因为 每个任务的后续冷却是n,所以加上自身的时间,一个轮回是n+1次
                if (!frequencyOfChar.isEmpty()) {
                    if (frequencyOfChar.peek() > 1) {
                        frequencyDcreByOneList.add(frequencyOfChar.poll() - 1);
                    } else {
                        frequencyOfChar.poll();
                    }
                }
                taskTimeSum++;
                i++;
                if (frequencyOfChar.isEmpty() && frequencyDcreByOneList.size() == 0)
                    break;

            }
            for (int temp : frequencyDcreByOneList) {
                frequencyOfChar.add(temp);
            }

        }
        return taskTimeSum;
    }

```

#### [146. LRU缓存机制](https://leetcode-cn.com/problems/lru-cache/)

```java
改进:LinkedHashMap 可以在构造的时候指定,是否按照访问顺序来存储节点(key)
    访问的时候,从链表中拿到key,在用key从map中拿到value

    class LRUCache {
        private LinkedHashMap<Integer, Integer> map;

        public LRUCache(int capacity) {
            map = new LinkedHashMap<Integer, Integer>(capacity, 0.75F, true) {
                public boolean removeEldestEntry(Map.Entry<Integer, Integer> entry) {
                    return size() > capacity;
                }
            };
        }

        public int get(int key) {
            return map.getOrDefault(key, -1);
        }

        public void put(int key, int value) {
            map.put(key, value);
        }
    }
```

#### [416. 分割等和子集](https://leetcode-cn.com/problems/partition-equal-subset-sum/)

```java
改进:01背包问题
public boolean canPartition(int[] nums) {
    int sum = 0;
    for (int temp : nums) {
        sum += temp;
    }
    int target = sum / 2;
    if (sum % 2 == 1) {
        return false;
    }
    boolean[][] dp = new boolean[nums.length][target + 1];
    if (nums[0] <= target) {
        dp[0][nums[0]] = true;
    }
    for (int i = 1; i < nums.length; i++) {
        for (int j = 0; j <= target; j++) {
            //如果j为0,记得把选择(要还是不要)第i个物品后,恰好总和为0的情况设置为true
            //只要一个都不选,则dp[i][0]永远为true
            //设置成true是为了 nums[i]==j的时候用
            if (j == 0) {
                dp[i][j] = true;
                continue;
            }
            //只有nums[i]<=j的时候,背包的总和才有可能刚好等于j
            if (nums[i] <= j) {
                //状态转移方程
                dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i]];
            }
        }
    }
    return dp[nums.length - 1][target];
}
```



#### [494. 目标和](https://leetcode-cn.com/problems/target-sum/)

```java
改进:背包问题
    对于sum为负数的dp[i][sum],用dp[i][sum+1000]来解决负数
public int findTargetSumWays(int[] nums, int S) {
    int len = nums.length;
    if (nums == null || nums.length == 0) {
        return 0;
    }
    int[][] dp = new int[len][2001];
    for (int i = 0; i < len; i++) {
        for (int j = -1000; j < 1001; j++) {
            if (i == 0) {
                //原题为非负整数,不然要添加j==-nums[i]条件
                if (j == nums[i]) {
                    dp[i][1000 - nums[i]] = 1;
                    //注意!!!!!!!!!!!!!! 下面之所以要用+= 是为了应对开头是0的case,
                    //+0  和 -0 算两种情况
                    dp[i][1000 + nums[i]] += 1;
                } else {
                    dp[i][j + 1000] = 0;
                }
                continue;
            }
            
            if (j - nums[i] + 1000 >= 0) {
                dp[i][j + 1000] += dp[i - 1][j - nums[i] + 1000];
            }
            if (j + nums[i] + 1000 < 2001) {
                dp[i][j + 1000] += dp[i - 1][j + nums[i] + 1000];
            }
            
            //上面的状态转移可以用下面来代替
           /* if (dp[i - 1][j + 1000] > 0) {
                    dp[i][j + 1000 - nums[i]] += dp[i - 1][j + 1000];
                    dp[i][j + 1000 + nums[i]] += dp[i - 1][j + 1000];
                }
            */
        }
    }
    //题中说数组和不会超过1000所以要判断S>1000
    return S > 1000 ? 0 : dp[len - 1][S + 1000];
}
```

#### [300. 最长上升子序列](https://leetcode-cn.com/problems/longest-increasing-subsequence/)

```java
public int lengthOfLIS(int[] nums) {
    if (nums == null || nums.length == 0) {
        return 0;
    }
    int len = nums.length;
    int[] dp = new int[len];
    int max = 0;
    for (int i = 0; i < len; i++) {
        dp[i] = 1;
        for (int j = 0; j < i; j++) {
            if (nums[i] > nums[j])
                dp[i] = Math.max(dp[i], dp[j] + 1);

        }
        max = Math.max(dp[i], max);
    }
    return max;
}
```



#### [560. 和为K的子数组](https://leetcode-cn.com/problems/subarray-sum-equals-k/)

```java
改进: map.put(0,1)放入,是为了sum本身就等于 k,即 sum-k = 0;
		map中先搜索sum-k再放入sum   是为了防止k=0的情况,此时先放入sum-0,再搜索sum,是一种混淆. 本身sum放入map是给下一次累加的sum使用的,而非本次累加.

HashMap<Integer, Integer> map = new HashMap<>();
    public int subarraySum(int[] nums, int k) {
        int count = 0;
        int sum = 0;
        map.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (map.containsKey(sum - k)) {
                count += map.get(sum - k);
            }
            if (map.containsKey(sum)) {
                map.put(sum, map.get(sum) + 1);
            } else {
                map.put(sum, 1);
            }
        }
        return count;
    }

public int subarraySum(int[] nums, int k) {
    int left = 0;
    int right = 0;
    int sum = 0;
    int count = 0;
    for (left = 0; left < nums.length; left++) {
        sum = 0;
        for (right = left; right < nums.length; right++) {
            sum += nums[right];
            if (sum == k) {
                count++;
            }
        }
    }
    return count;
}
```



#### [221. 最大正方形](https://leetcode-cn.com/problems/maximal-square/)

改进:关键是这种题的状态转移方程记住.

```java
public int maximalSquare(char[][] matrix) {
    int rowBound = matrix.length;
    int colBound = rowBound > 0 ? matrix[0].length : 0;
    int[][] dp = new int[rowBound + 1][colBound + 1];
    int maxSquare = 0;
    for (int i = 1; i <= rowBound; i++) {
        for (int j = 1; j <= colBound; j++) {
            if (matrix[i - 1][j - 1] == '1') {
                dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i - 1][j - 1]), dp[i][j - 1]) + 1;
                maxSquare = Math.max(dp[i][j], maxSquare);
            }

        }
    }
    return maxSquare * maxSquare;
}

//空间优化的写法
 public int maximalSquare(char[][] matrix) {
        int rowBound = matrix.length;
        int colBound = rowBound > 0 ? matrix[0].length : 0;
        int[] dp = new int[colBound + 1];
        int maxSquare = 0;
        int temp = 0, pre = 0;
        for (int i = 1; i <= rowBound; i++) {
            for (int j = 1; j <= colBound; j++) {
                temp = dp[j];
                if (matrix[i - 1][j - 1] == '1') {
                    dp[j] = Math.min(Math.min(dp[j], dp[j - 1]), pre) + 1;
                    maxSquare = Math.max(dp[j], maxSquare);
                } else {
                    // dp[j]在用过多次后,后续不为零,要重新清理
                    dp[j] = 0;
                }
                空间优化的写法,temp,pre的使用
                pre = temp;
            }
        }
        return maxSquare * maxSquare;
    }
```

#### [34. 在排序数组中查找元素的第一个和最后一个位置](https://leetcode-cn.com/problems/find-first-and-last-position-of-element-in-sorted-array/)

```java
改进:find不到就返回-1
    getLower 和 getUpper的学习

public int[] searchRange(int[] nums, int target) {
        int left = getLower(nums, target);
        int right = getUpper(nums, target);
        return new int[]{left, right};
    }

    private int getUpper(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        boolean found = false;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] < target) {
                low = mid + 1;
            } else if (nums[mid] == target) {
                low = mid + 1;
                found = true;
            } else if (nums[mid] > target) {
                high = mid - 1;
            }
        }
        return found ? high : -1;
    }

    private int getLower(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        boolean found = false;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] > target) {
                high = mid - 1;
            } else if (nums[mid] == target) {
                high = mid - 1;
                found = true;
            } else if (nums[mid] < target) {
                low = mid + 1;
            }
        }
        return found ? low : -1;
    }
```

#### [240. 搜索二维矩阵 II](https://leetcode-cn.com/problems/search-a-2d-matrix-ii/)

```java
改进:还有一个方法二.

public boolean searchMatrix(int[][] matrix, int target) {
    int rowBound = matrix.length;
    if (rowBound == 0 || matrix[0].length == 0) {
        return false;
    }
    int colBound = matrix[0].length;
    for (int i = rowBound - 1; i >= 0; i--) {
        if (matrix[i][0] == target) {
            return true;
        } else if (matrix[i][0] < target && matrix[i][colBound - 1] >= target) {
            int foundIndex = binarySearch(matrix[i], target);
            if (foundIndex != -1) {
                return true;
            }
        }
    }
    return false;
}

private int binarySearch(int[] nums, int target) {
    int low = 0, high = nums.length - 1;
    while (low <= high) {
        int mid = low + (high - low) / 2;
        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] < target) {
            low = mid + 1;
        } else {
            high = mid - 1;
        }
    }
    return -1;
}


方法二
public boolean searchMatrix(int[][] matrix, int target) {
        // start our "pointer" in the bottom-left
        int row = matrix.length-1;
        int col = 0;

        while (row >= 0 && col < matrix[0].length) {
            if (matrix[row][col] > target) {
                row--;
            } else if (matrix[row][col] < target) {
                col++;
            } else { // found it
                return true;
            }
        }

        return false;
    }
```







#### [79. 单词搜索](https://leetcode-cn.com/problems/word-search/)



```java
改进:回溯,没啥好说的,继续熟练
boolean[][] visited;
    boolean found;
    int rowBound;
    int colBound;
    int[][] loc = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return false;
        }
        rowBound = board.length;
        colBound = board[0].length;
        visited = new boolean[rowBound][colBound];

        for (int i = 0; i < rowBound; i++) {
            for (int j = 0; j < colBound; j++) {
                backtracing(board, i, j, 0, word);
            }
        }
        return found;
    }

    private void backtracing(char[][] board, int row, int col, int path, String word) {
        if (found) {
            return;
        }
        if (row < 0 || col < 0 || row >= rowBound || col >= colBound) {
            return;
        }
        if (board[row][col] != word.charAt(path) || visited[row][col]) {
            return;
        }
        if (board[row][col] == word.charAt(path) && path == word.length() - 1) {
            found = true;
            return;
        }
        visited[row][col] = true;
        for (int i = 0; i < loc.length; i++) {
            int rowTemp = row + loc[i][0];
            int colTemp = col + loc[i][1];
            backtracing(board, rowTemp, colTemp, path + 1, word);
        }
        visited[row][col] = false;
    }
```



#### [19. 删除链表的倒数第N个节点](https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/)

```java

改进:方法很巧妙,有四点值得学
//1.删除节点的题,可以添加一个头结点来用
//2.pre前进N+1步
//3.删除倒数第n个节点,但second到了倒数第n+1就停下,为了使用 second.next = second.next.next;
//4.链表类题目的画图方式  [pHead] [head]  []  []  []  null

public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode pHead = new ListNode(1);
    pHead.next = head;
    ListNode pre = pHead;
    ListNode second = pHead;
    for (int i = 1; i <= n + 1; i++) {
        pre = pre.next;//其实应该判空
    }
    while (pre != null) {
        second = second.next;
        pre = pre.next;
    }
    second.next = second.next.next;
    return pHead.next;
}
```



#### [322. 零钱兑换](https://leetcode-cn.com/problems/coin-change/)

```java
改进:
//动态规划三部演化:  递归(自顶向下)->带备忘录的递归(自顶向下,也就是把递归树剪枝,已经算过的不再重复计算)->动态规划(自底向上)
//背包问题dp[i][j] i是选到第i个物品,j是当前的和,里面的值是
//斐波那契,跳台阶,零钱兑换  dp[i] i是和,是台阶总高,是当前拼凑的总钱数;里面的值是
//里面的值取决于题目,有的是方案可行,有的是方案次数,有的是最优子结构(即最少的拼凑次数,
//最少的跳台阶数,最少的背包物品数)

//我定好base case,同时知道dp[n]是由下面的dp[n-1]等怎么得来的,就可以写出代码
public int coinChange(int[] coins, int amount) {
    int[] dp = new int[amount + 1];
    //初始化为amount+1是因为一个amount最差也可以由amount个1分硬币构成,所以amount+1是一个不可能的数
    //之所以用amount+1 而不用 -1是因为dp[i] = Math.min(dp[i], dp[i - coin] + 1);是要求最小值
    Arrays.fill(dp, amount + 1);
    dp[0] = 0;
    for (int i = 1; i <= amount; i++) {
        for (int coin : coins) {
            if (coin <= i) {
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }
    }
    return dp[amount] == amount + 1 ? -1 : dp[amount];
}
```

#### [152. 乘积最大子序列](https://leetcode-cn.com/problems/maximum-product-subarray/)

```java
改进:1.状态转移方程
     dp[i][0] = Math.max(imax * nums[i - 1], nums[i - 1]);
     dp[i][1] = Math.min(imin * nums[i - 1], nums[i - 1]);
	2.因为负负得正,所以维护最小值也是有必要的
	3.因为只与前一个dp数据有关,dp数组可以被优化空间
        
public int maxProduct(int[] nums) {
    int max = Integer.MIN_VALUE, imax = 1, imin = 1;
    int[][] dp = new int[nums.length + 1][2];
    dp[0][0] = 1;
    dp[0][1] = 1;
    for (int i = 1; i <= nums.length; i++) {
        if (nums[i - 1] < 0) {
            imin = dp[i - 1][0];
            imax = dp[i - 1][1];
        } else {
            imin = dp[i - 1][1];
            imax = dp[i - 1][0];
        }
        dp[i][0] = Math.max(imax * nums[i - 1], nums[i - 1]);
        dp[i][1] = Math.min(imin * nums[i - 1], nums[i - 1]);
        max = Math.max(max, dp[i][0]);
    }
    return max;
}

改进:优化空间
    public int maxProduct(int[] nums) {
        int max = Integer.MIN_VALUE, imax = 1, imin = 1;
        for (int i = 0; i < nums.length; i++) {
            int temp = nums[i];
            if (temp < 0) {
                int swap = imax;
                imax = imin;
                imin = swap;
            }
            imax = Math.max(imax * nums[i], nums[i]);
            imin = Math.min(imin * nums[i], nums[i]);
            max = Math.max(imax, max);
        }
        return max;
    }
```



#### [2. 两数相加](https://leetcode-cn.com/problems/add-two-numbers/)

```java
改进:1.巧法:如果l1或者l2为空,则它的值可以看做0,这样就不用后续再对剩下一条非空链做单独处理
    2.见备注的一些细节

public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    int num1 = 0;
    int num2 = 0;
    int sum = 0;
    int addOnePre = 0;
    int addOneNow = 0;
    ListNode pHead = new ListNode(1);
    ListNode pre = pHead;
    while (l1 != null || l2 != null) {
        num1 = l1 == null ? 0 : l1.val;
        num2 = l2 == null ? 0 : l2.val;
        sum = num1 + num2;
        if (addOneNow == 1) {
            //先sum++再判断是否超过10
            sum++;
        }
        if (sum >= 10) {
            sum = sum % 10;
            addOnePre = 1;
        } else {
            addOnePre = 0;
        }

        ListNode node = new ListNode(sum);
        pre.next = node;
        pre = pre.next;
        addOneNow = addOnePre;

        l1 = l1 == null ? null : l1.next;
        l2 = l2 == null ? null : l2.next;
    }
    //如果l1和l2都空了,但还有一个进位,也要把这个进位考虑到
    if (addOneNow == 1) {
        pre.next = new ListNode(1);
    }
    return pHead.next;
}
```

#### [98. 验证二叉搜索树](https://leetcode-cn.com/problems/validate-binary-search-tree/)

```java
改进:利用搜索树的性质

boolean ret = true;

public boolean isValidBST(TreeNode root) {
    ArrayList<Integer> list = new ArrayList<>();
    inOrder(root, list);
    return ret;
}

private void inOrder(TreeNode root, ArrayList<Integer> list) {
    if (ret == false) {
        return;
    }
    if (root == null) {
        return;
    }
    inOrder(root.left, list);
    if (list.size() >= 1 && root.val <= list.get(list.size() - 1)) {
        ret = false;
        return;
    } else {
        list.add(root.val);
    }
    inOrder(root.right, list);
}
```

#### [31. 下一个排列](https://leetcode-cn.com/problems/next-permutation/)



思路讲解:https://leetcode-cn.com/problems/next-permutation/solution/xia-yi-ge-pai-lie-by-leetcode/

```java
改进: 从后往前,找到第一个不满足降序的数字 nums[targetIndex]
    再从后往前,找到第一个降序的子数组中比nums[targetIndex]大的nums[i]
	交换nums[targetIndex] 和 nums[i]
    再把targetIndex后面的数变成升序.
    
    上面的思路有点像 一个数字,比如129,降序数组就是9,把2变成3,129就成了139,然后把降序数组9改成升序数组0
    最终变成了130.
    
    
public void nextPermutation(int[] nums) {
    int len = nums.length;
    int targetIndex = len - 2;
    while (targetIndex >= 0) {
        if (nums[targetIndex] < nums[targetIndex + 1]) {
            break;
        }
        targetIndex--;
    }
    if (targetIndex >= 0) {
        for (int i = len - 1; i >= 0; i--) {
            if (nums[i] > nums[targetIndex]) {
                swap(nums, targetIndex, i);
                break;
            }
        }
    }
    reverse(nums, targetIndex + 1, len - 1);
}

private void reverse(int[] nums, int start, int end) {
    while (start < end) {
        swap(nums, start++, end--);
    }
}

private void swap(int[] nums, int index1, int index2) {
    int temp = nums[index1];
    nums[index1] = nums[index2];
    nums[index2] = temp;
}
```



#### [33. 搜索旋转排序数组](https://leetcode-cn.com/problems/search-in-rotated-sorted-array/)

```java
改进:做题的一般步骤,理解题意,确认题型,梳理给出的条件
    写代码:  先画出一个比较通用的情况,然后根据情况写代码; 然后再进行边界的处理(有的边界想不到会有致命bug,有的只是逻辑不健全,后者可以在代码通过一般情况后再考虑);然后空间,时间优化,逻辑结构优化
        
        
    本题思路,对半查找,有一半递增,有一半增减.检测target和递增的部分首尾的关系,进而决定low或者high接下来的变化

public int search(int[] nums, int target) {
    int len = nums.length;
    int low = 0, high = len - 1;
    while (low <= high) {
        int mid = low + (high - low) / 2;
        if (nums[mid] == target) {
            return mid;
        }
        if (nums[low] <= nums[mid]) {
            if (nums[low] > target || nums[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        } else {
            if (nums[mid] > target || nums[high] < target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
    }
    return -1;
}
```



### 背包问题的理解

#### 01背包

```
dp[i][j] = max(dp[i-1][j],dp[i-1][j-weight[i]]+value[i]);
```



#### 完全背包

```java
因为每种物品数量无限,所以到了选择第i件物品的时候,要么一件不放(dp[i][j] = dp[i-1][j])要么放1,2,3..k件
dp[i][j] = dp[i][j-weight[i]]+value[i];

所以代码为
for(int i = 1;i<n;i++){
	for(int j = 1;j<=W;j++){
		if(j<weight[i]){
			dp[i][j] = dp[i-1][j];
		}else{
			//要么一个也不放,要么在i物品上能放多少就放多少
			dp[i][j] = Math.max(dp[i-1][j],dp[i][j-weight[i]]+value[i]);
            //其实和多重背包是一样的?k是从0到无限大??  下面是自己的猜测,能不能用做题验证
  			dp[i][j] = Math.max(dp[i-1][j],dp[i][j-weight[i]*k]+value[i]*k);
            dp[i][j] = Math.max(dp[i-1][j],dp[i-1][j-weight[i]*k]+value[i]*k);
		}
	}
}
```



#### 多重背包

```java
每种物品都有数量限制,限制的数值是k,,要么一件不放(dp[i][j] = dp[i-1][j])要么放1,2,3..k件
dp[i][j] = dp[i][j-weight[i]]+value[i];

for(int i = 1;i<n;i++){
	for(int j = 1;j<=W;j++){
		if(j<weight[i]){
			dp[i][j] = dp[i-1][j];
		}else{
			int count = min(num[i],j/weight[i]);
			//要么一个也不放
			dp[i][j] = dp[i-1][j];
			for(int k = 0;k<count;k++){
			//要么1,2...k个都试一下
				dp[i][j] = Math.max(dp[i][j],dp[i-1][j-weight[i]*k]+value[i]*k);
			}
		}
	}
}
```

#### [3. 无重复字符的最长子串](https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/)

```java

改进:思路滑动窗口
public int lengthOfLongestSubstring(String s) {
    if (s == null || s.length() == 0) {
        return 0;
    }
    int len = s.length();
    int left = 0;
    int right = 0;
    int max = 0;
    HashMap<Character, Integer> charIndexMap = new HashMap<>();
    for (; right < len; right++) {
        char ch = s.charAt(right);
        if (charIndexMap.containsKey(ch)) {
            int charIndex = charIndexMap.get(ch);
            if (charIndex >= left) {
                for (; left <= charIndex; left++) {
                    charIndexMap.remove(s.charAt(left));
                }
            }
        }
        charIndexMap.put(ch, right);
        max = Math.max(max, right - left + 1);
    }
    return max;
}
```





#### [5. 最长回文子串](https://leetcode-cn.com/problems/longest-palindromic-substring/)

```java
改进:利用动规:dp[i][j] = dp[i-1][j+1] +1 (如果len>2的情况下,并且char[i] == char[j])
    dp[i][j]的含义是 以为i终点,j为起点的字符串中对称字符的长度.

public String longestPalindrome(String s) {
    if (s == null || s.length() == 0) {
        return "";
    }
    int maxLen = 0;
    int maxRow = 0;
    int maxCol = 0;
    int len = s.length();
    int[][] dp = new int[len][len];
    for (int i = 0; i < len; i++) {
        for (int j = i; j >= 0; j--) {

            if (s.charAt(i) == s.charAt(j)) {
                if (i == j) {//长度为1
                    dp[i][j] = 1;
                } else if (i == j + 1) {//长度为2
                    dp[i][j] = 2;
                } else { //长度大于2
                    if (dp[i - 1][j + 1] != 0) {
                        dp[i][j] = dp[i - 1][j + 1] + 2;

                    } else {
                        dp[i][j] = 0;
                    }
                }
                if (maxLen < dp[i][j]) {
                    maxLen = dp[i][j];
                    maxRow = i;
                    maxCol = j;
                }
            } else {
                //char i  与 char j 不相等
                dp[i][j] = 0;
            }
        }
    }
    return s.substring(maxCol, maxRow + 1);
}
```



#### [15. 三数之和](https://leetcode-cn.com/problems/3sum/)

```java
改进:1.数组排序
    2. 以i为左起点,nums[i]大于0则可以剪枝,否则L++R--来寻找组合
    3.num[i]可能会有重复,要用最左边的num[i],不然比如 -1  -1 0 1 2,用最右边的nums[i]会漏掉 -1 -1 2 这种情况
    4.找到 num[i] nums[L] nums[R]的组合之后  L要一直++ R要一直--,直到nums[L]!=nums[L],为的是去除重复解
    比如 -2 -1 -1 0 3 3

public List<List<Integer>> threeSum(int[] nums) {
    List<List<Integer>> ret = new ArrayList<>();
    if (nums == null || nums.length <= 2) {
        return ret;
    }
    Arrays.sort(nums);
    for (int i = 0; i < nums.length; i++) {
        if (nums[i] > 0) {
            break;
        }

        int L = i + 1;
        int R = nums.length - 1;
        while (L < R) {
            int sum = nums[i] + nums[L] + nums[R];
            if (sum > 0) {
                R--;
            } else if (sum < 0) {
                L++;
            } else {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(nums[i]);
                list.add(nums[L]);
                list.add(nums[R]);
                ret.add(list);
                while (L < R && nums[L] == nums[L + 1])
                    L++;
                while (L < R && nums[R] == nums[R - 1])
                    R--;
                L++;
                R--;
            }
        }
        while (i + 1 < nums.length && nums[i] == nums[i + 1]) {
            i++;
        }
    }
    return ret;
}
```



#### [56. 合并区间](https://leetcode-cn.com/problems/merge-intervals/)

```java
改进:思路 1.按照每个区间的左起点排序,
		2.根据i的right和 i+1的left能否相交决定是否合并区间(right = Math.max(right, intervals[i][1]);
        3.right = Math.max(right, intervals[i][1]);而非right = intervals[i][1],比如(1,9) (2,4)
        还是原来的right大
        4.创建ArrayList<int[]> ret; 转换的时候 ret.toArray(new int[0][]);

public int[][] merge(int[][] intervals) {
    Comparator<int[]> c = new Comparator<int[]>() {
        public int compare(int[] a, int[] b) {
            return a[0] - b[0];
        }
    };
    ArrayList<int[]> ret = new ArrayList<>();
    if (intervals == null || intervals.length == 0) {
        return ret.toArray(new int[0][]);
    }
    Arrays.sort(intervals, c);
    for (int i = 0; i < intervals.length; i++) {
        int right = intervals[i][1];
        int left = intervals[i][0];
        while (i + 1 < intervals.length && right >= intervals[i + 1][0]) {
            i++;
            right = Math.max(right, intervals[i][1]);
        }

        ret.add(new int[]{left, right});
    }
    return ret.toArray(new int[0][]);
}
```



#### [128. 最长连续序列](https://leetcode-cn.com/problems/longest-consecutive-sequence/)

```java
思路1:哈希表
     public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        HashSet<Integer> set = new HashSet<>();
        for (int temp : nums) {
            set.add(temp);
        }
        int cnt = 0;
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            int temp = nums[i];
            cnt = 1;
            while (set.contains(temp + 1)) {
                cnt++;
                temp++;
            }
            max = Math.max(max, cnt);
        }
        return max;
    }



思路2:排序,然后从左到右遍历
public int longestConsecutive(int[] nums) {
    if (nums.length == 0) {
        return 0;
    }
    Arrays.sort(nums);
    int max = 1;
    int cnt = 1;
    for (int i = 0; i < nums.length - 1; i++) {
        while (i < nums.length - 1 && nums[i] == nums[i + 1]) {
            i++;
        }
        if (i < nums.length - 1 && nums[i] + 1 == nums[i + 1]) {
            cnt++;
        } else if (i < nums.length - 1 && nums[i] + 1 != nums[i + 1]) {
            cnt = 1;
        }
        max = Math.max(cnt, max);
    }
    return max;
}
```



#### [23. 合并K个排序链表](https://leetcode-cn.com/problems/merge-k-sorted-lists/)

```java
思路1:分而治之法

 public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        return merge(lists, 0, lists.length - 1);
    }

    private ListNode merge(ListNode[] lists, int left, int right) {
        if (left == right) {
            return lists[left];
        }
        int mid = left + (right - left) / 2;
        ListNode l1 = merge(lists, left, mid);
        ListNode l2 = merge(lists, mid + 1, right);
        return mergeTwoList(l1, l2);
    }

    private ListNode mergeTwoList(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode pre = head;
        while (l1 != null || l2 != null) {
            if (l1 != null && l2 != null) {
                if (l1.val > l2.val) {
                    pre.next = l2;
                    pre = pre.next;
                    l2 = l2.next;
                } else {
                    pre.next = l1;
                    pre = pre.next;
                    l1 = l1.next;
                }
            } else if (l1 != null) {
                pre.next = l1;
                pre = pre.next;
                l1 = l1.next;
            } else if (l2 != null) {
                pre.next = l2;
                pre = pre.next;
                l2 = l2.next;
            }
        }
        return head.next;
    }




思路2:优先队列

public ListNode mergeKLists(ListNode[] lists) {
    if (lists == null || lists.length == 0) {
        return null;
    }
    ListNode head = new ListNode(1);
    ListNode pre = head;

    PriorityQueue<ListNode> queue = new PriorityQueue<ListNode>(1, (a, b) -> (a.val - b.val));
    for (int i = 0; i < lists.length; i++) {
        if (lists[i] != null) {
            queue.add(lists[i]);
        }
    }
    while (!queue.isEmpty()) {
        ListNode temp = queue.poll();
        pre.next = temp;
        pre = pre.next;
        if (temp.next != null) {
            queue.add(temp.next);
        }
    }
    return head.next;
}
```





#### [124. 二叉树中的最大路径和](https://leetcode-cn.com/problems/binary-tree-maximum-path-sum/)

```java
改进思路: 双递归:一层递归求该树的每个节点为root的路径最大值,一层递归求特定root根的左右子树路径的sum
    	注意:如果root.val或者 左右子树+root.val小于零,则return 0; 丢弃该子树


 int max = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        if (root.left != null)
            maxPathSum(root.left);
        if (root.right != null)
            maxPathSum(root.right);
        max = Math.max(max, pathSum(root.left) + pathSum(root.right) + root.val);
        return max;
    }

    private int pathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = 0;
        int right = 0;
        if (root.left == null && root.right == null) {
            return Math.max(root.val, 0);
        }
        if (root.left != null) {
            left = pathSum(root.left);
        }
        if (root.right != null) {
            right = pathSum(root.right);
        }
        int sum = Math.max(left, right) + root.val;
        return Math.max(sum, 0);
    }
```

#### [239. 滑动窗口最大值](https://leetcode-cn.com/problems/sliding-window-maximum/)

```java
改进思路  双端队列法:
		注意:ArrayList<Integer> 转int[] 不能用ret.toArray(new int[]);会报错无法将Integer转成int
        直接用循环赋值就好了
            
        不过像String这种,就可以用ArrayList<String> ret ;ret.toArray(new String[ret.size()])来
        转换成数组

public int[] maxSlidingWindow(int[] nums, int k) {
    ArrayList<Integer> ret = new ArrayList<Integer>();
    Deque<Integer> deque = new ArrayDeque<Integer>();
    int max = 0;
    for (int i = 0; i < nums.length; i++) {
        if (!deque.isEmpty() && deque.getFirst() <= i - k) {
            deque.removeFirst();
        }

        while (!deque.isEmpty() && nums[deque.getLast()] < nums[i]) {
            deque.removeLast();
        }
        deque.addLast(i);
        if (i >= k - 1) {
            ret.add(nums[deque.getFirst()]);
        }
    }
    int[] retArray = new int[ret.size()];
    int i = 0;
    for (int temp : ret) {
        retArray[i++] = temp;
    }
    return retArray;
}
```



















