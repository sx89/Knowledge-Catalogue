<!-- TOC -->

- [88. 合并两个有序数组](https://leetcode-cn.com/problems/merge-sorted-array/)](#88-合并两个有序数组httpsleetcode-cncomproblemsmerge-sorted-array)
- [[@253. 会议室 II](https://leetcode-cn.com/problems/meeting-rooms-ii/)](#253-会议室-iihttpsleetcode-cncomproblemsmeeting-rooms-ii)
- [[@443. 压缩字符串](https://leetcode-cn.com/problems/string-compression/)](#443-压缩字符串httpsleetcode-cncomproblemsstring-compression)
- [[1239. 串联字符串的最大长度](https://leetcode-cn.com/problems/maximum-length-of-a-concatenated-string-with-unique-characters/)](#1239-串联字符串的最大长度httpsleetcode-cncomproblemsmaximum-length-of-a-concatenated-string-with-unique-characters)
- [[@@@297. 二叉树的序列化与反序列化](https://leetcode-cn.com/problems/serialize-and-deserialize-binary-tree/)](#297-二叉树的序列化与反序列化httpsleetcode-cncomproblemsserialize-and-deserialize-binary-tree)
- [[103. 二叉树的锯齿形层次遍历](https://leetcode-cn.com/problems/binary-tree-zigzag-level-order-traversal/)](#103-二叉树的锯齿形层次遍历httpsleetcode-cncomproblemsbinary-tree-zigzag-level-order-traversal)
- [[@@151. 翻转字符串里的单词](https://leetcode-cn.com/problems/reverse-words-in-a-string/)](#151-翻转字符串里的单词httpsleetcode-cncomproblemsreverse-words-in-a-string)
- [[24. 两两交换链表中的节点](https://leetcode-cn.com/problems/swap-nodes-in-pairs/)](#24-两两交换链表中的节点httpsleetcode-cncomproblemsswap-nodes-in-pairs)
- [[25. K 个一组翻转链表](https://leetcode-cn.com/problems/reverse-nodes-in-k-group/)](#25-k-个一组翻转链表httpsleetcode-cncomproblemsreverse-nodes-in-k-group)
- [[@@@@@10. 正则表达式匹配](https://leetcode-cn.com/problems/regular-expression-matching/)](#10-正则表达式匹配httpsleetcode-cncomproblemsregular-expression-matching)
- [[@8. 字符串转换整数 (atoi)](https://leetcode-cn.com/problems/string-to-integer-atoi/)](#8-字符串转换整数-atoihttpsleetcode-cncomproblemsstring-to-integer-atoi)
- [[@235. 二叉搜索树的最近公共祖先](https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-search-tree/)](#235-二叉搜索树的最近公共祖先httpsleetcode-cncomproblemslowest-common-ancestor-of-a-binary-search-tree)
- [[@236. 二叉树的最近公共祖先](https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree/)](#236-二叉树的最近公共祖先httpsleetcode-cncomproblemslowest-common-ancestor-of-a-binary-tree)
- [[@450. 删除二叉搜索树中的节点](https://leetcode-cn.com/problems/delete-node-in-a-bst/)](#450-删除二叉搜索树中的节点httpsleetcode-cncomproblemsdelete-node-in-a-bst)
- [循环节长度](#循环节长度)
- [两个数字字符串相加，区分正负](#两个数字字符串相加区分正负)
- [[419. 甲板上的战舰](https://leetcode-cn.com/problems/battleships-in-a-board/)](#419-甲板上的战舰httpsleetcode-cncomproblemsbattleships-in-a-board)
- [[398. 随机数索引](https://leetcode-cn.com/problems/random-pick-index/)](#398-随机数索引httpsleetcode-cncomproblemsrandom-pick-index)
- [[面试题 16.11. 跳水板](https://leetcode-cn.com/problems/diving-board-lcci/)](#面试题-1611-跳水板httpsleetcode-cncomproblemsdiving-board-lcci)
- [[581. 最短无序连续子数组](https://leetcode-cn.com/problems/shortest-unsorted-continuous-subarray/)](#581-最短无序连续子数组httpsleetcode-cncomproblemsshortest-unsorted-continuous-subarray)
- [[@@93. 复原IP地址](https://leetcode-cn.com/problems/restore-ip-addresses/)](#93-复原ip地址httpsleetcode-cncomproblemsrestore-ip-addresses)
- [[134. 加油站](https://leetcode-cn.com/problems/gas-station/)](#134-加油站httpsleetcode-cncomproblemsgas-station)
- [[@116. 填充每个节点的下一个右侧节点指针](https://leetcode-cn.com/problems/populating-next-right-pointers-in-each-node/)](#116-填充每个节点的下一个右侧节点指针httpsleetcode-cncomproblemspopulating-next-right-pointers-in-each-node)
- [[90. 子集 II](https://leetcode-cn.com/problems/subsets-ii/)](#90-子集-iihttpsleetcode-cncomproblemssubsets-ii)
- [[13. 罗马数字转整数](https://leetcode-cn.com/problems/roman-to-integer/)](#13-罗马数字转整数httpsleetcode-cncomproblemsroman-to-integer)
- [[@348. 判定井字棋胜负](https://leetcode-cn.com/problems/design-tic-tac-toe/)](#348-判定井字棋胜负httpsleetcode-cncomproblemsdesign-tic-tac-toe)
- [和为0的连续最长子序列](#和为0的连续最长子序列)
- [链表快排.](#链表快排)
- [[@1143. 最长公共子序列](https://leetcode-cn.com/problems/longest-common-subsequence/)](#1143-最长公共子序列httpsleetcode-cncomproblemslongest-common-subsequence)
- [删除最少字符变成回文串](#删除最少字符变成回文串)
- [[148. 排序链表](https://leetcode-cn.com/problems/sort-list/)](#148-排序链表httpsleetcode-cncomproblemssort-list)
- [[114. 二叉树展开为链表](https://leetcode-cn.com/problems/flatten-binary-tree-to-linked-list/)](#114-二叉树展开为链表httpsleetcode-cncomproblemsflatten-binary-tree-to-linked-list)

<!-- /TOC -->




#### 88. 合并两个有序数组](https://leetcode-cn.com/problems/merge-sorted-array/)

```
nums1因为后方有空余位置,所以让nums1从后往前填充,可以达到时间复杂度O(m+n)空间复杂度O(1)
```

#### [@253. 会议室 II](https://leetcode-cn.com/problems/meeting-rooms-ii/)



```java


思路:startTime排序,每到一个startTime,usedRoom就+1.如果在该时间之前有结束的会议室(endTime[endPointer]<=startTime[startPointer]).usedRoom就-1;
public int minMeetingRooms(int[][] intervals) {
        int len = intervals.length;

        int max = 0;
        int[] startTime = new int[len];
        int[] endTime = new int[len];
        for (int i = 0; i < len; i++) {
            startTime[i] = intervals[i][0];
            endTime[i] = intervals[i][1];
        }
        Arrays.sort(startTime);
        Arrays.sort(endTime);
        int usedRooms = 0;
        int endPointer = 0;
        for (int startPointer = 0; startPointer < len; ) {
            if (endTime[endPointer] <= startTime[startPointer]) {
                usedRooms--;
                endPointer++;
            }
            usedRooms++;
            startPointer++;
        }
        return usedRooms;
    }
```



#### [@443. 压缩字符串](https://leetcode-cn.com/problems/string-compression/)



write read,chBegin的妙用

if (read == chars.length - 1 || chars[read] != chars[read + 1])  这两个条件很重要

```java
 public int compress(char[] chars) {
        int read = 0;
        int write = 0;
        int chBegin = 0;
        for (read = 0; read < chars.length; read++) {
            if (read == chars.length - 1 || chars[read] != chars[read + 1]) {
                int count = read - chBegin + 1;
                chars[write++] = chars[read];
                if (count >= 2) {
                    String temp = count + "";
                    for (int i = 0; i < temp.length(); i++) {
                        chars[write++] = temp.charAt(i);
                    }
                }
                chBegin = read + 1;
            }
        }
        return write;
    }
```



#### [1239. 串联字符串的最大长度](https://leetcode-cn.com/problems/maximum-length-of-a-concatenated-string-with-unique-characters/)

回溯的题目,没啥好说的,熟练了

```java
int maxLen = 0;
    HashSet<Character> set = new HashSet<>();

    public int maxLength(List<String> arr) {
        bracktracing(arr, 0, 0);
        return maxLen;
    }

    private void bracktracing(List<String> arr, int index, int len) {
        if (index >= arr.size()) {
            return;
        }
        for (int i = index; i < arr.size(); i++) {
            String temp = arr.get(i);
            if (isUnique(temp)) {
                maxLen = Math.max(maxLen, len + temp.length());
                for (int j = 0; j < temp.length(); j++) {
                    set.add(temp.charAt(j));
                }

                bracktracing(arr, i + 1, len + temp.length());

                for (int j = 0; j < temp.length(); j++) {
                    set.remove(temp.charAt(j));
                }
            }
        }
    }

    private boolean isUnique(String temp) {
        for (int i = 0; i < temp.length(); i++) {
            if (set.contains(temp.charAt(i))) {
                int cutIndex = i;
                for (int j = 0; j < cutIndex; j++) {
                    set.remove(temp.charAt(j));
                }
                return false;
            } else {
                set.add(temp.charAt(i));
            }
        }
        return true;
    }
```



#### [@@@297. 二叉树的序列化与反序列化](https://leetcode-cn.com/problems/serialize-and-deserialize-binary-tree/)



@为空返回#,左右子树用" "分隔开

@思路2 是错误的,  原因是 data是局部变量,而非全局变量,因为data是string类型,data的改变会被传入新的函数root.left = deserialize(data);但是当执行完root.left之后.回到root.right = deserialize(data); data的值不会受root.left的影响.

@序列出来的效果像这样:"1 2 # # 3 4 # # 5 # #"



```java
 //return "#"很关键
    String data = "";
    
    public String serialize(TreeNode root) {
        if (root == null) {
            return "#";
        }
        return root.val + " " + serialize(root.left) + " " + serialize(root.right);
    }

    public TreeNode deserialize(String str) {
        data = str;
        return Deserialize();
    }

    private TreeNode Deserialize() {
        if (data == null || data.length() == 0) {
            return null;
        }
        int cutIndex = 0;
        for (cutIndex = 0; cutIndex < data.length(); cutIndex++) {
            if (data.charAt(cutIndex) == ' ') {
                break;
            }
        }
        String rootVal = data.substring(0, cutIndex);
        if (cutIndex < data.length()) {
            data = data.substring(cutIndex + 1);
        } else {
            data = "";
        }
        if (rootVal.equals("#")) {
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(rootVal));
        root.left = Deserialize();
        root.right = Deserialize();
        return root;
    }



错误的反序列化方式:
错在data不是全局变量.
 public TreeNode deserialize2(String data) {
        if (data == null || data.length() == 0) {
            return null;
        }
        int cutIndex = 0;
        for (cutIndex = 0; cutIndex < data.length(); cutIndex++) {
            if (data.charAt(cutIndex) == ' ') {
                break;
            }
        }
        String rootVal = data.substring(0, cutIndex);
        if (cutIndex < data.length()) {
            data = data.substring(cutIndex + 1);
        } else {
            data = "";
        }
        if (rootVal.equals("#")) {
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(rootVal));
        root.left = deserialize(data);
        root.right = deserialize(data);
        return root;
    }

```





#### [103. 二叉树的锯齿形层次遍历](https://leetcode-cn.com/problems/binary-tree-zigzag-level-order-traversal/)



```java
public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
    Queue<TreeNode> queue = new LinkedList<>();
    int level = 1;
    int count = 0;
    List<List<Integer>> ret = new ArrayList<>();
    if (root == null) {
        return ret;
    }
    count = 1;
    queue.add(root);
    while (!queue.isEmpty()) {
        List<Integer> list = new ArrayList<>();
        int tempCount = 0;
        for (int i = 0; i < count; i++) {
            TreeNode node = queue.poll();
            if (level % 2 == 0) {
                list.add(0, node.val);
            } else {
                list.add(node.val);
            }
            if (node.left != null) {
                queue.add(node.left);
                tempCount++;
            }
            if (node.right != null) {
                queue.add(node.right);
                tempCount++;
            }
        }
        level++;
        count = tempCount;
        ret.add(list);
    }
    return ret;
}
```



#### [@@151. 翻转字符串里的单词](https://leetcode-cn.com/problems/reverse-words-in-a-string/)

@trim

@从后往前

@StringBuilder.append()  .deleter()  .length()



```java
public String reverseWords(String s) {
    s = s.trim();
    int len = s.length();
    int index = len - 1;
    int end = s.length() - 1;
    int start = 0;
    StringBuilder ret = new StringBuilder();
    while (index > 0) {
        if (s.charAt(index) == ' ') {
            start = index + 1;
            ret.append(s.substring(start, end + 1));
            ret.append(" ");
            while (s.charAt(index) == ' ') {
                index--;
            }
            end = index;
        }
        index--;
    }
    return ret.append(s.substring(0, end + 1)).toString();
}
```





#### [24. 两两交换链表中的节点](https://leetcode-cn.com/problems/swap-nodes-in-pairs/)

```java
 public ListNode swapPairs(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode pHead = new ListNode(-1);
        pHead.next = head;
        ListNode prePre = pHead;
        ListNode pre = pHead.next;
        ListNode last = pre.next;
        while (prePre != null && pre != null && last != null) {
            ListNode temp = last.next;
            pre.next = last.next;
            last.next = pre;
            prePre.next = last;

            prePre = pre;
            pre = temp;
            if (temp != null) {
                last = temp.next;
            }
        }
        return pHead.next;
    }
```





#### [25. K 个一组翻转链表](https://leetcode-cn.com/problems/reverse-nodes-in-k-group/)

@ k个一组,头插法翻转

@不需要先遍历k个一组是否满足k个,可以先翻转着,如果不满足k个,再翻转回来.

```

```





#### [@@@@@10. 正则表达式匹配](https://leetcode-cn.com/problems/regular-expression-matching/)

```java
 public boolean isMatch(String s, String p) {
        if (s == null || p == null) {
            return false;
        }
        int len1 = s.length();
        int len2 = p.length();
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        dp[0][0] = true;
        //初始化"" 与p的匹配关系  "" 和 a*a*a*是可以匹配的
        for (int i = 0; i < len2; i++) {
            if (p.charAt(i) == '*' && dp[0][i - 1]) {
                dp[0][i + 1] = true;
            }
        }
        for (int i = 0; i < len1; i++) {
            for (int j = 0; j < len2; j++) {
                // ##s   ##p     与     ##s    ##.
                if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.') {
                    dp[i + 1][j + 1] = dp[i][j];
                }
                if (p.charAt(j) == '*') {
                    if (p.charAt(j - 1) == s.charAt(i) || p.charAt(j - 1) == '.') {
                        // ##cp    ##cpp*  i  和 j-2   p*匹配0个
                        //##p     ##p*    i-1  和  j-2   p*  匹配1个
                        //##ppp   ##p*    i-1 和  j   p* 匹配多个
                        dp[i + 1][j + 1] = dp[i + 1][j - 1] || dp[i ][j-1] || dp[i][j + 1];
                    } else {
                        // ##b  要和  ##c*匹配   i  和  j-2
                        dp[i + 1][j + 1] = dp[i + 1][j - 1];
                    }
                }
            }
        }
        return dp[len1][len2];
    }


再做一遍
    
     public boolean isMatch(String s, String p) {
        if (s == null || p == null) {
            return false;
        }
        int len1 = s.length();
        int len2 = p.length();
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        dp[0][0] = true;
        //初始化"" 与p的匹配关系  "" 和 a*a*a*是可以匹配的
        for (int i = 0; i < len2; i++) {
            if (p.charAt(i) == '*' && dp[0][i - 1]) {
                dp[0][i + 1] = true;
            }
        }
        for (int i = 0; i < len1; i++) {
            for (int j = 0; j < len2; j++) {
                // ##s   ##p     与     ##s    ##.
                if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.') {
                    dp[i + 1][j + 1] = dp[i][j];
                }
                if (p.charAt(j) == '*') {
                    if (p.charAt(j - 1) == s.charAt(i) || p.charAt(j - 1) == '.') {
                        // ##cp    ##cpp*  i  和 j-2   p*匹配0个
                        //##p     ##p*    i  和  j-1   p*  匹配1个
                        //##ppp   ##p*    i-1 和  j   p* 匹配多个
                        dp[i + 1][j + 1] = dp[i + 1][j - 1] || dp[i + 1][j] || dp[i][j + 1];
                    } else {
                        // ##b  要和  ##c*匹配   i  和  j-2
                        dp[i + 1][j + 1] = dp[i + 1][j - 1];
                    }
                }
            }
        }
        return dp[len1][len2];
    }
```





#### [@8. 字符串转换整数 (atoi)](https://leetcode-cn.com/problems/string-to-integer-atoi/)

```java

public int myAtoi(String str) {
        str = str.trim();
        if (str == null || str.length() == 0)
            return 0;
        int len = str.length();
        boolean isNegative = false;
        boolean firstSign = false;
        char[] chars = str.toCharArray();

        int limit = 0;//用正数最大值的相反数作限制
        if (chars[0] == '-') {
            isNegative = true;
            firstSign = true;
            limit = Integer.MIN_VALUE;
        } else if (chars[0] == '+') {
            isNegative = false;
            firstSign = true;
            limit = -Integer.MAX_VALUE;
        } else {
            isNegative = false;
            firstSign = false;
            limit = -Integer.MAX_VALUE;
        }

        int littleLimit = limit / 10;
        int digit = 0;
        int result = 0;
        for (int i = firstSign ? 1 : 0; i < len; i++) {
            if (!(chars[i] >= '0' && chars[i] <= '9'))
                break;
            digit = chars[i] - '0';
            if (result < littleLimit)
                return isNegative?Integer.MIN_VALUE:Integer.MAX_VALUE;
            if (result * 10 < limit + digit)
                //注意!!!!!!!改进!!!!!!
                //这里不可以写result*10-digit < limit  左边会越界,影响比较大小的真实逻辑
                return isNegative?Integer.MIN_VALUE:Integer.MAX_VALUE;
            result = result * 10 - digit;
        }
        return isNegative ? result : -result;
    }
    
```





#### [@235. 二叉搜索树的最近公共祖先](https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-search-tree/)

@二叉搜索树的特点是,找到第一个可以把p和q区分开的root.val,就是最近的root.val.

所以本题也不需要后序遍历.

```java
递归:起名有问题,其实不是后序遍历,从根往下走即可

public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        return lastOrder(root, p, q);
    }

    private TreeNode lastOrder(TreeNode root, TreeNode p, TreeNode q) {

        if (root.val > p.val && root.val > q.val) {
            return lastOrder(root.left, p, q);
        } else if (root.val < p.val && root.val < q.val) {
            return lastOrder(root.right, p, q);
        } else {
            return root;
        }
    }

非递归:也不是后序遍历,直接从根往下走即可

public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root==null)
            return null;
        int pVal = p.val;
        int qVal = q.val;
        TreeNode node = root;
        while(node!=null){
            if(node.val>pVal&&node.val>qVal)
                node = node.left;
            else if(node.val<pVal&&node.val<qVal)
                node = node.right;
            else 
                return node;
        }
        return node;
    }

```



#### [@236. 二叉树的最近公共祖先](https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree/)

```java
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return preOrder(root, p, q);
    }

    private TreeNode preOrder(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return root;
        }
        if (root == p || root == q) {
            return root;
        }
        TreeNode left = preOrder(root.left, p, q);
        TreeNode right = preOrder(root.right, p, q);

        if (left != null && right != null) {
            return root;
        } else if (left == null && right == null) {
            return null;
        } else {
            return left == null ? right : left;
        }
    }
```



#### [@450. 删除二叉搜索树中的节点](https://leetcode-cn.com/problems/delete-node-in-a-bst/)

@叶子节点删自身

@有右子树,root.val赋值为后驱节点值,删后驱节点,  

@没右子树,只有左子树,root.val赋值为前驱节点值,删前驱节点

```java
public TreeNode deleteNode(TreeNode root, int key) {
        return deleteNodeFunc(root, key);
    }

    private TreeNode deleteNodeFunc(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (key > root.val) {
            root.right = deleteNodeFunc(root.right, key);
        } else if (key < root.val) {
            root.left = deleteNodeFunc(root.left, key);
        } else {
            if (root.left == null && root.right == null) {
                root = null;
            } else if (root.right != null) {
                TreeNode laster = getLaster(root.right);
                root.val = laster.val;
                root.right = deleteNodeFunc(root.right, laster.val);
            } else if (root.left != null) {
                TreeNode pre = getPre(root.left);
                root.val = pre.val;
                root.left = deleteNodeFunc(root.left, pre.val);
            }
        }
        return root;
    }

    private TreeNode getLaster(TreeNode root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    private TreeNode getPre(TreeNode root) {
        while (root.right != null) {
            root = root.right;
        }
        return root;
    }
```



#### 循环节长度

两个整数做除法，有时会产生循环小数，其循环部分称为：循环节。 
比如，11/13=6=>0.846153846153….. 其循环节为[846153] 共有6位。 



https://blog.csdn.net/weixin_41514525/article/details/97121292

```java
private int getCyc(int n, int m) {
        int count = 0;
        int remain = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        n = n % m;
        while (true) {
            n = n * 10;
            remain = n / m;
            n = n % m;
            if (n == 0) {//可以除尽,不是无限不循环
                return -1;
            }
            if (map.containsKey(remain)) {
                return count - map.get(remain);
            }
            map.put(remain, count++);
        }
    }
```





#### 两个数字字符串相加，区分正负



```

1.正负号另算,用大正数减去小正数 
2. 比如9887 - 92
3.  首位-1,   末位借10 ,  其他位都借9.
也就是8887 加上   99(10) -92
4.最后8887+ 908

```





#### [419. 甲板上的战舰](https://leetcode-cn.com/problems/battleships-in-a-board/)

```java
如果题设没有特殊规则和要求的话，常规做法就是使用BFS或者DFS扫描，计算连通图的个数。
但是题目要求只扫描一次，且O(1)的额外空间，那么就只能从题目给出的特殊规则出发了。
特殊规则：连通图是1xN或者Nx1的长条形的，且不会相交（之间有.分隔）。
解法：扫描到X时，如果上方或者左方也是X，则不计数，否则计数加1。

class Solution {
public:
    int countBattleships(vector<vector<char>>& board) {
        int nCount = 0;
        for (int i = 0; i < board.size(); ++i) {
            for (int j = 0; j < board[i].size(); ++j) {
                if (board[i][j] == 'X') {
                    if (i > 0 && board[i - 1][j] == 'X' ||
                        j > 0 && board[i][j - 1] == 'X')
                        continue;
                    ++nCount;
                }
            }
        }
        return nCount;
    }
};

```



#### [398. 随机数索引](https://leetcode-cn.com/problems/random-pick-index/)

```java
 Random random = new Random();
int i = random.nextInt(100);


蓄水池抽样问题.

 private int[] nums = null;

    public Solution(int[] nums) {
        this.nums = nums;
    }

    public int pick(int target) {
        int ret = 0;
        int cnt = 0;
        Random r = new Random();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                if (cnt == 0) {
                    cnt++;
                    ret = i;
                } else {
                    cnt++;
                    if (r.nextInt() % cnt == 0) {
                        ret = i;
                    }
                }
            }
        }
        return ret;
    }
```





#### [面试题 16.11. 跳水板](https://leetcode-cn.com/problems/diving-board-lcci/)

```java
public int[] divingBoard(int shorter, int longer, int k) {
    int diff = longer - shorter;

    if (k == 0) {
        return new int[0];
    }
    if (shorter == longer) {
        return new int[]{k * shorter};
    }
    int[] ret = new int[k + 1];
    for (int i = 0; i <= k; i++) {
        ret[i] = shorter * k + diff * i;
    }
    return ret;
}
```



#### [581. 最短无序连续子数组](https://leetcode-cn.com/problems/shortest-unsorted-continuous-subarray/)



@思想是无序子数组中最小元素的正确位置可以决定左边界，最大元素的正确位置可以决定右边界。



```java
public int findUnsortedSubarray(int[] nums) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        boolean flag = false;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1]) {
                flag = true;
            }
            if (flag) {
                min = Math.min(min, nums[i]);
            }
        }
        flag = false;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] > nums[i + 1]) {
                flag = true;
            }
            if (flag) {
                max = Math.max(max, nums[i]);
            }
        }
        int start = 0;
        int end = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > min) {
                start = i;
                break;
            }
        }
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] < max) {
                end = i;
                break;
            }
        }
        return start == end ? 0 : end - start + 1;
    }
作者：LeetCode
链接：https://leetcode-cn.com/problems/shortest-unsorted-continuous-subarray/solution/zui-duan-wu-xu-lian-xu-zi-shu-zu-by-leetcode/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
```



#### [@@93. 复原IP地址](https://leetcode-cn.com/problems/restore-ip-addresses/)

@StringBuilder用于回溯

@ip的条件  cutCount = 4, index==len,Integer.parseInt(temp)<=255,若temp.charAt(0)=='0'只能为一位0不能是001,012,08这种类型.

```java
  List<String> ret = new ArrayList<>();
    int len = 0;

    public List<String> restoreIpAddresses(String s) {
        len = s.length();
        backtracing(s, 0, 0, new StringBuilder());
        return ret;
    }

    private void backtracing(String s, int cutCount, int index, StringBuilder path) {
        if (index > len || cutCount > 4) {
            return;
        }
        if (index == len && cutCount == 4) {
            path.delete(path.length() - 1, path.length());
            ret.add(path.toString());
            path.append(".");
            return;
        }
        for (int j = index; j <= index + 3; j++) {
            String temp = "";
            if (j < len) {
                temp = s.substring(index, j + 1);
                if (s.charAt(index) == '0' && j == index) {
                    path.append(temp).append(".");
                    backtracing(s, cutCount + 1, j + 1, path);
                    path.delete(path.length() - temp.length() - 1, path.length());
                } else if (s.charAt(index) != '0' && Integer.parseInt(temp) <= 255) {
                    path.append(temp).append(".");
                    backtracing(s, cutCount + 1, j + 1, path);
                    path.delete(path.length() - temp.length() - 1, path.length());
                }
            }
        }
    }
```



#### [134. 加油站](https://leetcode-cn.com/problems/gas-station/)

```java
 boolean finded = false;

    public int canCompleteCircuit(int[] gas, int[] cost) {
        for (int i = 0; i < gas.length; i++) {
            carRun(gas, cost, i);
            if (finded) {
                return i;
            }
        }
        return -1;
    }

    private void carRun(int[] gas, int[] cost, int begin) {
        int tank = 0;
        for (int i = begin; i < gas.length; i++) {
            tank += gas[i];
            tank -= cost[i];
            if (tank < 0) {
                return;
            }
        }
        for (int i = 0; i <= begin; i++) {
            tank += gas[i];
            tank -= cost[i];
            if (tank < 0) {
                return;
            }
        }
        finded = true;
    }
```



#### [@116. 填充每个节点的下一个右侧节点指针](https://leetcode-cn.com/problems/populating-next-right-pointers-in-each-node/)

@每个递归,负责连两条线.



```java
public Node connect(Node root) {
        if (root == null) {
            return null;
        }
        Node left = root.left;
        Node right = root.right;
        if (left != null) {
            left.next = right;
        }

        Node next = root.next;
        if (right != null && next != null) {
            right.next = next.left;
        }

        connect(root.left);
        connect(root.right);
        return root;
    }
```



#### [90. 子集 II](https://leetcode-cn.com/problems/subsets-ii/)

@排序+第二次碰到相同的数字就跳过(注意是第二次碰到再跳过)比如 

1  2   2  3  

第一次到2的时候有组合:  2,22,223,

当遇到第二个2的时候有组合 2,23   所以第二个2可以跳过

```java
public List<List<Integer>> subsetsWithDup(int[] nums) {
    List<List<Integer>> ans = new ArrayList<>();
    Arrays.sort(nums); //排序
    getAns(nums, 0, new ArrayList<>(), ans);
    return ans;
}

private void getAns(int[] nums, int start, ArrayList<Integer> temp, List<List<Integer>> ans) {
    ans.add(new ArrayList<>(temp));
    for (int i = start; i < nums.length; i++) {
        //和上个数字相等就跳过
        if (i > start && nums[i] == nums[i - 1]) {
            continue;
        }
        temp.add(nums[i]);
        getAns(nums, i + 1, temp, ans);
        temp.remove(temp.size() - 1);
    }
}

```



#### [13. 罗马数字转整数](https://leetcode-cn.com/problems/roman-to-integer/)



@因为是3999以内,最大位就是M(千).另外需要注意的就是如果preNum<nowNum,则pre代表的是要减去的值.



通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：

I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
给定一个罗马数字，将其转换成整数。输入确保在 1 到 3999 的范围内。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/roman-to-integer
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

```java
 public int romanToInt(String s) {
        int preNum = getValue(s.charAt(0));
        int num = 0;
        int sum = 0;
        for (int i = 1; i < s.length(); i++) {
            num = getValue(s.charAt(i));
            if (preNum < num) {
                sum -= preNum;
            } else {
                sum += preNum;
            }
            preNum = num;
        }
        sum += preNum;
        return sum;

    }

    private int getValue(char ch) {
        switch (ch) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }
    }
```



#### [@348. 判定井字棋胜负](https://leetcode-cn.com/problems/design-tic-tac-toe/)

@但是本题没有判断 同一个玩家在某个点处重复下棋的情况,当然题目中也没要求有这种判断.

@制造一个记录每行,每列的数组,和两个记录对角线棋子数的数字.

一旦某个计数满足len,就获胜了

```java
    int[] rowRem = null;
    int[] colRem = null;
    int diagnol = 0;
    int antiDiagnol = 0;
    int len = 0;

    public TicTacToe(int n) {
        len = n;
        rowRem = new int[n];
        colRem = new int[n];
    }

    public int move(int row, int col, int player) {
        int toAdd = player == 1 ? 1 : -1;
        rowRem[row] += toAdd;
        colRem[col] += toAdd;
        if (row == col) {
            diagnol += toAdd;
        }
        if (row + col == len - 1) {
            antiDiagnol += toAdd;
        }
        if (Math.abs(rowRem[row]) == len ||
                Math.abs(colRem[col]) == len ||
                Math.abs(diagnol) == len ||
                Math.abs(antiDiagnol) == len) {
            return player;
        }
        return 0;
    }
```





#### 和为0的连续最长子序列

@利用求和的梯度来做.

```  
{3,0,-1,-2,-3,1,1,1,2,3,1,-2,-1}   12

{1，-1，1，-1，1，-1，1，-1}     8
```

```java
 public int func(int[] nums) {
        int maxLen = 0;
        int sum = 0;

        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (map.containsKey(sum)) {
                int len = i+1 - map.get(sum);
                maxLen = Math.max(len, maxLen);
            } else {
                map.put(sum, i+1);
            }
        }
        return maxLen;
    }
```

#### 链表快排.



```java
  public void listQuickSort(Node pHead) {
        quickSort(pHead, null);
    }

    public void quickSort(Node pHead, Node pEnd) {
        if (pHead == null || pHead == pEnd) {
            return;
        }
        Node mid = partition(pHead, null);
        quickSort(pHead, mid);
        quickSort(mid.next, null);
    }

    private Node partition(Node start, Node end) {
        if (start == null) {
            return start;
        }
        int key = start.val;
        Node itsNextIsRightNum = start;
        Node node = start.next;
        while (node != end) {
            if (node.val < key) {
                itsNextIsRightNum = itsNextIsRightNum.next;
                swap(itsNextIsRightNum, node);
            }

            node = node.next;
        }
        swap(start, itsNextIsRightNum);
        return itsNextIsRightNum;
    }

    private void swap(Node p1, Node p2) {
        int temp = p1.val;
        p1.val = p2.val;
        p2.val = temp;
    }
```



#### [@1143. 最长公共子序列](https://leetcode-cn.com/problems/longest-common-subsequence/)

```java
 public int longestCommonSubsequence(String text1, String text2) {
        int len1 = text1.length();
        int len2 = text2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];
        for (int i = 0; i <= len1; i++) {
            for (int j = 0; j <= len2; j++) {
                if (j == 0 || i == 0) {
                    dp[i][j] = 0;
                    continue;
                }
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[len1][len2];
    }
```



#### 删除最少字符变成回文串

题目描述：给定一字符串s,求最少删除多少个字符可以使得s成为回文串。例如：s="abca",答案是1.

思路:把自身翻转, 自身和翻转的lcs,就是最大回文长度,str.length()-lcs 即为要删除的字符.

```java
public int minChange(String str) {
        int lcs = longestCommonSubsequence(str, null);
        return str.length() - lcs;
    }

    public int longestCommonSubsequence(String text1, String text2) {
        int len1 = text1.length();
        StringBuffer stringBuffer = new StringBuffer(text1);
        text2 = stringBuffer.reverse().toString();
        int len2 = text2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];
        for (int i = 0; i <= len1; i++) {
            for (int j = 0; j <= len2; j++) {
                if (j == 0 || i == 0) {
                    dp[i][j] = 0;
                    continue;
                }
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[len1][len2];
    }
```







#### [148. 排序链表](https://leetcode-cn.com/problems/sort-list/)

@归并排序,需要截断list

@快速排序

```java

归并排序:
public ListNode sortList(ListNode head) {
        int len = 0;
        ListNode temp = head;
        while (head != null) {
            head = head.next;
            len++;
        }
        return mergeSort(temp, null, len);
    }

    private ListNode mergeSort(ListNode begin, ListNode end, int len) {
        if (begin == null || begin == end) {
            return begin;
        }

        int mid = len / 2;
        ListNode midNode = begin;
        int cnt = 1;
        while (cnt < mid) {
            cnt++;
            midNode = midNode.next;
        }

        ListNode begin2 = midNode.next;
        //记得把midNode截断这样l1和l2才是两段独立的链表.
        midNode.next = null;
        if (end != null) {
            end.next = null;
        }
        ListNode l1 = mergeSort(begin, midNode, mid);
        ListNode l2 = mergeSort(begin2, end, mid);
        return merge(l1, l2);
    }

    private ListNode merge(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(-1);
        ListNode node = head;
        if (l1 != null && l2 != null) {
            while (l1 != null && l2 != null) {
                if (l1.val <= l2.val) {
                    node.next = l1;
                    l1 = l1.next;
                } else {
                    node.next = l2;
                    l2 = l2.next;
                }
                node = node.next;
            }
            while (l1 != null) {
                node.next = l1;
                l1 = l1.next;
                node = node.next;
            }
            while (l2 != null) {
                node.next = l2;
                l2 = l2.next;
                node = node.next;
            }
            return head.next;
        } else if (l2 != null) {
            return l2;
        } else {
            return l1;
        }
    }


快速排序:

public ListNode sortList(ListNode head) {
        quickSort(head, null);
        return head;
    }

    private void quickSort(ListNode begin, ListNode end) {
        if (begin == null || begin == end) {
            return;
        }
        ListNode midNode = partition(begin, end);
        quickSort(begin, midNode);
        quickSort(midNode.next, end);
    }

    private ListNode partition(ListNode begin, ListNode end) {
        if (begin == null || begin == end) {
            return begin;
        }
        ListNode itsNextIsRightNum = begin;
        int key = begin.val;
        ListNode node = begin.next;
        while (node != end) {
            if (node.val <= key) {
                itsNextIsRightNum = itsNextIsRightNum.next;
                swap(itsNextIsRightNum, node);
            }
            node = node.next;
        }
        swap(itsNextIsRightNum, begin);
        return itsNextIsRightNum;
    }

    private void swap(ListNode p1, ListNode p2) {
        int temp = p1.val;
        p1.val = p2.val;
        p2.val = temp;
    }
```







#### [114. 二叉树展开为链表](https://leetcode-cn.com/problems/flatten-binary-tree-to-linked-list/)

```java
 private TreeNode pre = null;

    public void flatten(TreeNode root) {
        preOrder(root);
    }

    private void preOrder(TreeNode root) {

        if (root == null) {
            return;
        }
        //需要提前把左右子树存储好
        TreeNode left = root.left;
        TreeNode right = root.right;
        if (pre != null) {
            pre.right = root;

        }
        pre = root;
        root.left = null;
        preOrder(left);
        preOrder(right);

    }
```



































