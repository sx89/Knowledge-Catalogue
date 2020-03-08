[@@@@1143. 最长公共子序列](https://leetcode-cn.com/problems/longest-common-subsequence/)

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



#### @@@删除最少字符变成回文串

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



#### [@647. 回文子串](https://leetcode-cn.com/problems/palindromic-substrings/)(数量)

本题可以看成是字符串类的动态规划



中心扩展法比动态规划效率高.

因为动态规划是从短串到长串都要判断,不好剪枝

中心扩展从短串到长串的过程中,如果短串不成立,则长串终止.

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



#### [5. 最长回文子串](https://leetcode-cn.com/problems/longest-palindromic-substring/)(长度)

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

#### 用少来表示多



给一串只包含0~9的数字串，每个数字出现的概率相同（比如32978417506），现在告诉你（1,3,5,7）这四个数字不可用，即只能用（0,2,4,6,8,9）这6个数，如何表示原数字串？

* **00表示0**, 02表示1, 04表示3， 06表示5， 08表示7

* 15位表示以前的10位，所以存储多出来0.5倍

    

#### @253. 会议室 II](https://leetcode-cn.com/problems/meeting-rooms-ii/)



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



#### @@@[435. 无重叠区间](https://leetcode-cn.com/problems/non-overlapping-intervals/)

求无重叠区间cnt,然后用len-ret就是需要移除的最少区间数

把区间按照endTime从小到大排序. 然后往后找,如果后面的区间的startTime<该endTime,则有重叠.

如果endTIme>=startTime无重叠,此时endTime更新到下个区间,cnt++



```java
public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        Comparator<int[]> c = new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return a[1] - b[1];
            }
        };
        int len = intervals.length;
        Arrays.sort(intervals, c);
        int cnt = 1;
        int endTime = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            int startTime = intervals[i][0];
            if (endTime > startTime) {
                continue;
            } else if (endTime <= startTime) {
                cnt++;
                endTime = intervals[i][1];
            }
        }
        return len - cnt;
    }
```





#### @@[43. 字符串相乘](https://leetcode-cn.com/problems/multiply-strings/)

```java
 public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        int[] ret = new int[num1.length() + num2.length()];
        for (int i = num1.length() - 1; i >= 0; i--) {
            int a = num1.charAt(i) - '0';
            for (int j = num2.length() - 1; j >= 0; j--) {
                
                int b = num2.charAt(j) - '0';
                int sum = a * b + ret[i + j + 1];
                
                ret[i + j + 1] = sum % 10;
                ret[i + j] += sum / 10;
            }
        }
        StringBuilder retStr = new StringBuilder();
        for (int i = 0; i < ret.length; i++) {
            if (i == 0 && ret[i] == 0) {
                while (ret[i] == 0) {
                    i++;
                }
            }
            retStr.append(ret[i] + "");
        }
        return retStr.toString();
    }
```





#### @@@字符移位

链接：https://www.nowcoder.com/questionTerminal/7e8aa3f9873046d08899e0b44dac5e43
来源：牛客网

小Q最近遇到了一个难题：把一个字符串的大写字母放到字符串的后面，各个字符的相对位置不变，且不能申请额外的空间。
  你能帮帮小Q吗？ 

##### **输入描述:**

```
输入数据有多组，每组包含一个字符串s，且保证:1<=s.length<=1000.
  
```

##### **输出描述:**

```
对于每组数据，输出移位后的字符串。
```

```java
思路1
冒泡:
@@@@@@@@@@@@@@@
@要交换 i 0~len-1次
@每次  j从 len-1  到  i进行扫描;逐渐冒泡,把一个小写字母冒到i的位置上.

 public String func(String str) {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            for (int j = chars.length - 2; j >= i; j--) {
                if (chars[j] >= 'A' && chars[j] <= 'Z' && chars[j + 1] >= 'a' && chars[j + 1] <= 'z') {
                    char temp = chars[j];
                    chars[j] = chars[j + 1];
                    chars[j + 1] = temp;
                }
            }
        }
        return new String(chars);
    }



思路2:
选择排序:
@@@@@@@@@@@@@@@@@@@
@littleIndex代表下一个要插入小写字母的位置
@i往后找,找到一个小写字母. 然后从littleIndex 到 i之间的字母往后移一位
@然后把i处的字母插入到littleIndex处.

 public String func(String str) {
        char[] chars = str.toCharArray();
        int littleIndex = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= 'a' && chars[i] <= 'z') {
                char temp = chars[i];
                for (int j = i; j >= littleIndex + 1; j--) {
                    chars[j] = chars[j - 1];
                }
                chars[littleIndex] = temp;
                littleIndex++;
            }
        }
        return new String(chars);
    }

思路3:

public static String getResult(String str) {
        return str.replaceAll("[A-Z]", "") + str.replaceAll("[a-z]", "");
    }
```





#### [295. 数据流的中位数](https://leetcode-cn.com/problems/find-median-from-data-stream/)

@优先队列是log(n)的复杂度.

```java

class MedianFinder {
    PriorityQueue<Integer> leftNums = null;
    PriorityQueue<Integer> rightNums = null;

    public MedianFinder() {
        leftNums = new PriorityQueue<Integer>(10, (o2, o1) -> (o1 - o2));
        rightNums = new PriorityQueue<Integer>(10, (o1, o2) -> (o1 - o2));

    }

    public void addNum(int num) {
        if (leftNums.size() > rightNums.size()) {
            rightNums.add(num);
        } else {
            leftNums.add(num);
        }
        if (!rightNums.isEmpty() && rightNums.peek() < leftNums.peek()) {
            int leftNum = leftNums.poll();
            int rightNum = rightNums.poll();
            leftNums.add(rightNum);
            rightNums.add(leftNum);
        }
    }

    public double findMedian() {
        if (leftNums.size() == rightNums.size()) {
            return (double) (leftNums.peek() + rightNums.peek()) / 2;
        }
        return leftNums.peek();
    }
}
```



#### 图的m着色问题(dfs)

[问题描述]

给定无向连通图G和m种不同的颜色，用这些颜色给图的各个顶点着一种颜色，若某种方案使得图中每条边的2个顶点的颜色都不相同，则是一个满足的方案，找出所有的方案。

输入格式
 第一行有3个正整数n，k和m，分别表示n个顶点，k条边，m种颜色
接下来k行，每行2个正整数，保送一条边的两个顶点

所有不同的着色方案数
输入样例】
5 8 4 
1 2
1 3 
1 4
2 3
2 4
2 5
3 4
4 5
输出样例
48

```java
  public class Main {
    static int[][] matrix = null;
    int[] pointColor = null;
    int len = 0;
    int numPoint = 0;
    int numEdge = 0;
    int ret = 0;
    int numColor = 0;

    public int getResult(int numPoint, int numEdge, int numColor) {
        len = numPoint;
        this.numColor = numColor;
        this.numEdge = numEdge;
        this.numPoint = numPoint;
//        this.matrix = new int[numPoint + 1][numPoint + 1];
        pointColor = new int[numPoint + 1];
        backtracing(1, 1);
        return ret;
    }

    private boolean uniqueColor(int index) {
        for (int i = 1; i <= numPoint; i++) {
            if (matrix[index][i] == 1 && pointColor[index] == pointColor[i]) {
                return false;
            }
        }
        return true;
    }

    private void backtracing(int index, int cnt) {
        if (cnt >= numPoint + 1) {
            return;
        }
        //先染色
        for (int j = 1; j <= numColor; j++) {
            pointColor[index] = j;
            //如果颜色符合要求
            if (uniqueColor(index)) {
                if (cnt == numPoint) {
                    ret++;
                }
                //从与index相连的  第一个没有被访问过的 顶点开始染色
                //为什么是第一个,假设1与2,3相连,我给1染颜色1,2染颜色2,3染颜色3.
                //1->2->3  和  1->3->2  其实是同一种颜色组合.
                for (int k = 1; k <= numPoint; k++) {
                    if (matrix[index][k] == 1 && pointColor[k] == 0) {
                        backtracing(k, cnt + 1);
                        break;
                    }
                }
            }
            pointColor[index] = 0;
        }
    }

    private void initialMatrix(String str) {
        for (int i = 0; i < str.length(); i = i + 2) {
            int x = str.charAt(i) - '0';
            int y = str.charAt(i + 1) - '0';
            matrix[x][y] = 1;
            matrix[y][x] = 1;
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        String str = "1213142324253445";
        matrix = new int[6][6];
        main.initialMatrix(str);

        int func = main.getResult(5, 8, 4);
        System.out.println(func);

    }
}
```



```java
 下面的做法错误的原因是  
 1->2->3和
 1->3->2 假设1涂红,2涂蓝,3涂绿,这被认为是两种情况了.
 
 static int[][] matrix = null;
    int[] pointColor = null;
    int len = 0;
    int numPoint = 0;
    int numEdge = 0;
    int ret = 0;
    int numColor = 0;

    public int func(int numPoint, int numEdge, int numColor) {
        len = numPoint;
        this.numColor = numColor;
        this.numEdge = numEdge;
        this.numPoint = numPoint;
//        this.matrix = new int[numPoint + 1][numPoint + 1];
        pointColor = new int[numPoint + 1];
        backtracing(1, 1);
        return ret;
    }

    private boolean uniqueColor(int index) {
        for (int i = 1; i <= numPoint; i++) {
            if (matrix[index][i] == 1 && pointColor[index] == pointColor[i]) {
                return false;
            }
        }
        return true;
    }

    private void backtracing(int index, int cnt) {
        if (cnt >= numPoint + 1) {
            return;
        }
        for (int j = 1; j <= numColor; j++) {
            pointColor[index] = j;
            if (uniqueColor(index)) {
                if (cnt == numPoint) {
                    ret++;
                }
                for (int k = 1; k <= numPoint; k++) {
                    if (matrix[index][k] == 1 && pointColor[k] == 0) {
                        backtracing(k, cnt + 1);
                    }
                }
            }
            pointColor[index] = 0;
        }
    }
```



#### 

#### 100亿数字排序,求中位数,平均数,求和

今天要给100亿个数字排序，100亿个 int 型数字放在文件里面大概有 37.2GB，非常大，内存一次装不下了。那么肯定是要拆分成小的文件一个一个来处理，最终在合并成一个排好序的大文件。

##### 实现思路

1.把这个37GB的大文件，用哈希分成1000个小文件，每个小文件平均38MB左右（理想情况），把100亿个数字对1000取模，模出来的结果在0到999之间，每个结果对应一个文件，所以我这里取的哈希函数是 h = x % 1000，哈希函数取得"好"，能使冲突减小，结果分布均匀。

2.拆分完了之后，得到一些几十MB的小文件，那么就可以放进内存里排序了，可以用快速排序，归并排序，堆排序等等。

3.1000个小文件内部排好序之后，就要把这些内部有序的小文件，合并成一个大的文件，可以用**二叉堆**来做1000路合并的操作，每个小文件是一路，合并后的大文件仍然有序。

- 首先遍历1000个文件，每个文件里面取第一个数字，组成 (数字, 文件号) 这样的组合加入到堆里（假设是从小到大排序，用小顶堆），遍历完后堆里有1000个 (数字，文件号) 这样的元素
- 然后不断从堆顶拿元素出来，每拿出一个元素，把它的文件号读取出来，然后去对应的文件里，加一个元素进入堆，直到那个文件被读取完。拿出来的元素当然追加到最终结果的文件里。
- 按照上面的操作，直到堆被取空了，此时最终结果文件里的全部数字就是有序的了。

最后我用c++写了个实验程序，具体代码在[这里](https://link.jianshu.com?t=https://github.com/hehe520/Data-structure-and-algorithm/blob/master/海量数据处理/外部归并排序 - 分治.cpp)可以看到。

##### 思维拓展

类似的100亿个数字求和，求中位数，求平均数，套路就是一样的了。
 求和：统计每个小文件的和，返回给master再求和就可以了。
 求平均数：上面能求和了，再除以100亿就是平均数了
 求中位数：在排序的基础上，遍历到中间的那个数就是中位数了

#### [113. 路径总和 II](https://leetcode-cn.com/problems/path-sum-ii/)

```java
List<List<Integer>> ret = new ArrayList<>();

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        preOrder(root, sum, 0, new ArrayList<Integer>());
        return ret;
    }

    public void preOrder(TreeNode root, int sum, int pathSum, List<Integer> path) {
        if (root == null) {
            return;
        }
        path.add(root.val);
        pathSum += root.val;
        if (root.left == null && root.right == null && pathSum == sum) {
            ret.add(new ArrayList<Integer>(path));
            path.remove(path.size() - 1);
            return;
        }
        preOrder(root.left, sum, pathSum, path);
        preOrder(root.right, sum, pathSum, path);
        path.remove(path.size() - 1);
    }
```





#### [@@@547. 朋友圈](https://leetcode-cn.com/problems/friend-circles/)

```java

public class Solution {
    public int findCircleNum(int[][] M) {
        if (M == null || M.length == 0) {
            return 0;
        }
        int len = M.length;
        UF u = new UF(len);
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (M[i][j] == 1) {
                    u.union(i, j);
                }
            }
        }
        return u.getCount();
    }
}

class UF {
    private int count;
    private int[] parent;
    private int[] size;

    public UF(int len) {
        this.count = len;
        this.parent = new int[len];
        this.size = new int[len];
        for (int i = 0; i < len; i++) {
            size[i] = 1;
            parent[i] = i;
        }
    }
    public void union(int p, int q) {
        int rootP = findParent(p);
        int rootQ = findParent(q);
        if (rootP == rootQ)
            return;
        if (size[rootP] > size[rootQ]) {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        } else {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }
        count--;
    }
    //路径压缩+寻找根节点
    public int findParent(int p) {
        while (parent[p] != p) {
            //把 p的爷爷 替换成  p的爸爸
            parent[p] = parent[parent[p]];
            //把p变成p的爷爷
            p = parent[p];
        }
        return p;
    }

    public int getCount() {
        return count;
    }
     public boolean connected(int p, int q) {
        int rootP = findParent(p);
        int rootQ = findParent(q);
        return rootP == rootQ;
    }

}
```





#### [@@@200. 岛屿数量](https://leetcode-cn.com/problems/number-of-islands/)



@@查并集做法

```java
class Solution {
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int row = grid.length;
        int col = grid[0].length;
        UF uf = new UF(grid, grid.length, grid[0].length);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                //如果某个坐标是一座岛,就把这座岛与右边或者下边的连接岛 合并
                //为什么没有左和上的岛,i,j是按照从左上往右下的顺序移动
                // 合并前会把grid[i][j]置为0,所以左上的岛永远是空的
                if (grid[i][j] == '1') {
                    grid[i][j] = '0';
                    if (j + 1 < col && grid[i][j + 1] == '1') {
                        uf.union(i * col + j, i * col + j + 1);
                    }
                    if (i + 1 < row && grid[i + 1][j] == '1') {
                        uf.union(i * col + j, (i + 1) * col + j);
                    }
                }
            }
        }
        return uf.getCount();
    }
}

class UF {
    int count;
    int[] parent;
    int[] size;

    public UF(char[][] grid, int row, int col) {
        this.parent = new int[row * col];
        this.size = new int[row * col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                //如果某个坐标是导,就count++, parent初始化成自己,size初始化为1.
                if (grid[i][j] == '1') {
                    parent[i * col + j] = i * col + j;
                    ++count;
                    size[i * col + j] = 1;
                }
            }
        }
    }

    public int getCount() {
        return this.count;
    }

    public boolean connected(int p, int q) {
        int rootP = findParent(p);
        int rootQ = findParent(q);
        if (rootP == rootQ) {
            return true;
        }
        return false;
    }

    public int findParent(int p) {
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }

    public void union(int p, int q) {
        int rootP = findParent(p);
        int rootQ = findParent(q);
        if (rootQ == rootP) {
            return;
        }
        if (size[rootP] > size[rootQ]) {
            size[rootP] += size[rootQ];
            parent[rootQ] = rootP;
        } else {
            size[rootQ] += size[rootP];
            parent[rootP] = rootQ;
        }
        count--;
    }

}
```



#### 找到二叉树中与target之差的绝对值最小的node

@如果node处的值小于target,则node左边的值跟target的差距只会越来越大

比如target是10,如果node=9,则node左边的值,跟10的差距只会越来越大

<img src="pictures/LeetCode03/image-20200303225244927.png" alt="image-20200303225244927" style="zoom:50%;" />

在二叉查找树中，[查找与目标值最接近的节点并返回](https://www.geeksforgeeks.org/find-closest-element-binary-search-tree/)。如果有多个节点都符合要求，返回其中一个即可

```java
 public void maxDiffUtil(Node  ptr, int k)
    {
        if (ptr == null)
            return ;

        // If k itself is present 
        if (ptr.key == k)
        {
            min_diff_key = k;
            return;
        }

        // update min_diff and min_diff_key by checking 
        // current node value 
        if (min_diff > Math.abs(ptr.key - k))
        {
            min_diff = Math.abs(ptr.key - k);
            min_diff_key = ptr.key;
        }

        // if k is less than ptr.key then move in 
        // left subtree else in right subtree 
        if (k < ptr.key)
            maxDiffUtil(ptr.left, k);
        else
            maxDiffUtil(ptr.right, k);
    }
```





#### @@@@二叉树的非递归后序遍历

```java
private void lastOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode lastVisited = null;
        TreeNode nodeCur = root;
        Stack<TreeNode> stack = new Stack<>();
        while (nodeCur != null) {
            stack.add(nodeCur);
            nodeCur = nodeCur.left;
        }
        while (!stack.isEmpty()) {
            nodeCur = stack.pop();
            if (nodeCur.right == null || lastVisited == nodeCur.right) {
                System.out.println(nodeCur.val);
                lastVisited = nodeCur;
            } else if (lastVisited == nodeCur.left) {
                stack.add(nodeCur);
                nodeCur = nodeCur.right;
                while (nodeCur != null) {
                    stack.add(nodeCur);
                    nodeCur = nodeCur.left;
                }
            }
        }
    }
```



#### 有序数组重建平衡二叉树

```java
public TreeNode rebuildBST(int[] nums, int start, int end) {
        if (start > end || start < 0 || end > nums.length - 1) {
            return null;
        }
        int mid = start + (end - start) / 2;
        TreeNode treeNode = new TreeNode(nums[mid]);
        treeNode.left = rebuildBST(nums, start, mid - 1);
        treeNode.right = rebuildBST(nums, mid + 1, end);
        return treeNode;
    }
```

#### 一个数组里找任意两个数之和的绝对值最小值

```
排序,left= 0,right = len-1, sum>0 right--,sum<0,left++.
```

#### 一个整数数组找任意两数的最小之差

```
排序,相邻的数的差最小
```

#### [@@@208. 实现 Trie (前缀树)](https://leetcode-cn.com/problems/implement-trie-prefix-tree/)

```java

class Trie {
    private TreeNode root = null;

    /**
     * Initialize your data structure here.
     */
    public Trie() {
        root = new TreeNode();
    }

    public TreeNode getPrefixEnd(String word) {
        TreeNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (node.containsKey(ch)) {
                node = node.get(ch);
            } else {
                return null;
            }
        }
        return node;
    }

    /**
     * Inserts a word into the trie.
     */
    public void insert(String word) {
        TreeNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (!node.containsKey(ch)) {
                node.put(ch, new TreeNode());
            }
            node = node.get(ch);
        }
        node.setEnd();
    }


    /**
     * Returns if the word is in the trie.
     */
    public boolean search(String word) {
        TreeNode node = getPrefixEnd(word);
        return node != null && node.isEnd();
    }

    /**
     * Returns if there is any word in the trie that starts with the given prefix.
     */
    public boolean startsWith(String prefix) {
        TreeNode node = getPrefixEnd(prefix);
        return node != null;
    }
}

class TreeNode {
    private int len = 26;
    private TreeNode[] links = null;
    private boolean isEnd = false;

    public TreeNode() {
        links = new TreeNode[len];
    }

    public void put(char ch, TreeNode node) {
        links[ch - 'a'] = node;
    }

    public TreeNode get(char ch) {
        return links[ch - 'a'];
    }

    public boolean containsKey(char ch) {
        return links[ch - 'a'] != null;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd() {
        isEnd = true;
    }

}
```



#### [209. 长度最小的子数组](https://leetcode-cn.com/problems/minimum-size-subarray-sum/)



思路一:滑动窗口  复杂度 O(n)

思路二:二分长度法   len = 8 先搜len=4,如果sum>s,搜len=2,如果sum<s,搜len=6.  在长度上用二分法.



#### [60. 第k个排列](https://leetcode-cn.com/problems/permutation-sequence/)

permutation在全排列中的index

```
思路1.回溯+剪枝,到了k个的时候,返回
    
思路二,利用每一位的权重. @@@@用于没有重复数字的情况.如果有重复数字,比如  112.  一共只有3种组合,就比较难想.


```



#### [@@@@60. 第k个排列](https://leetcode-cn.com/problems/permutation-sequence/)



@@特别注意使用  

k-1来除以factor        而不是   k/factor[index]    因为 1/1  =  1. 而我这时候想要的是0位上的数

k =k - numIndex*factor  而不是  k = k%factor[index]   因为   1%1 = 0   而我这时候想要的是 k=1.方便下一步 (k-1)/1 =0.

```java
String getPermutation(int n, int k) {
        if (n == 0) {
            return "";
        }
        StringBuffer strRet = new StringBuffer();
        int[] factor = new int[n + 1];
        factor[0] = 1;
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            factor[i] = factor[i - 1] * i;
            list.add(i);
        }
        int factorIndex = n - 1;
        for (int strIndex = 0; strIndex < n; strIndex++) {

            int numIndex = (k - 1) / factor[factorIndex];
            k = k - numIndex * factor[factorIndex];

            int num = list.get(numIndex);
            strRet.append(num);

            list.remove(numIndex);
            factorIndex--;

        }
        return strRet.toString();
    }
```



#### 一个数组的值先从小到大递增后从大到小递减，找出最大的值

@@@如果数组中有平峰,比如  1,4,3,3,3,,3,3,3,3,1;可以考虑先去重,再调用下面的代码.

思路：最简单的办法就是从第二个值开始，判断是否满足 A[i] > A[i-1] && A[i] > A[i+1]. 如果满足，i 就是那个最大值的下标。该算法复杂度为O(n).

我们可以改进这种算法，因为这个数组是排好序的，所以我们可以利用二分查找的思想，更快速的找到最大值，时间复杂度为O(lg n)。

```java
 public int findPeak(int[] nums) {
        int len = nums.length;
        return findPeakFunc(nums, 0, len - 1);
    }

    private int findPeakFunc(int[] nums, int left, int right) {
        int mid = left + (right - left) / 2;
        if (mid == 0 || mid == nums.length - 1 || (nums[mid] > nums[mid + 1] && nums[mid] > nums[mid - 1])) {
            return nums[mid];
        } else if (nums[mid] < nums[mid - 1]) {
            return findPeakFunc(nums, left, mid - 1);
        } else {
            return findPeakFunc(nums, mid + 1, right);
        }
    }
```



#### [@@@@127. 单词接龙](https://leetcode-cn.com/problems/word-ladder/)

这个题不能用dfs来做,假设dict是100.那么dfs要遍历所有的路径,复杂度是100的100次方,大概就是1*10的200次方,复杂度是指数级别的



用BFS来做,就可以找到最少的转换次数,因为有level层来控制.复杂度变成了  dict表长*字符串的len



```java
   boolean[] visited = null;
    String target = null;
    List<String> wordList = null;

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        target = endWord;
        this.wordList = wordList;
        visited = new boolean[wordList.size()];

        if (!wordList.contains(endWord)) {
            return 0;
        }

        for (int i = 0; i < wordList.size(); i++) {
            if (beginWord.equals(wordList.get(i))) {
                visited[i] = true;
            }
        }

        LinkedList<Pair<String, Integer>> queue = new LinkedList<>();
        queue.add(new Pair(beginWord, 1));
        while (!queue.isEmpty()) {
            //每一层
            for (int queueIndex = 0; queueIndex < queue.size(); queueIndex++) {
                String curStr = queue.peek().getKey();
                int level = queue.poll().getValue();
                //每一层的某个string,能否往下转换
                for (int i = 0; i < wordList.size(); i++) {
                    String nextStr = wordList.get(i);
                    if (visited[i] == false && canTrans(curStr, nextStr)) {
                        if (nextStr.equals(endWord)) {
                            return level + 1;
                        }
                        queue.add(new Pair<>(nextStr, level + 1));
                        visited[i] = true;
                    }
                }
            }

        }

        return 0;
    }


    private boolean canTrans(String curStr, String nextStr) {
        int diffCount = 0;
        if (curStr.equals(nextStr)) {
            return false;
        }
        for (int i = 0; i < curStr.length(); i++) {
            if (curStr.charAt(i) != nextStr.charAt(i)) {
                diffCount++;
            }
            if (diffCount > 1) {
                return false;
            }
        }
        return diffCount == 1;
    }
```





#### [@@@@162. 寻找峰值](https://leetcode-cn.com/problems/find-peak-element/)

迭代法:

@@@mid和mid+1的比较是很有水平的.因为while里面是left<right.所以mid求出来之后,mid+1不会越界,但mid-1会越界

所以可以

```java
 if (nums[mid] > nums[mid + 1]) {
            right = mid;
        } else if (nums[mid] <= nums[mid + 1]) {
            left = mid + 1;
        }
```

但是不能用

```java
if(nums[mid-1]<nums[mid]){
    left = mid;
}else if(nums[mid-1]>=nums[mid]){
    right = mid-1;
}
```

```java
public int findPeakElement(int[] nums) {
    int left = 0;
    int right = nums.length - 1;
    while (left < right) {
        int mid = left + (right - left) / 2;
        if (nums[mid] > nums[mid + 1]) {
            right = mid;
        } else if (nums[mid] <= nums[mid + 1]) {
            left = mid + 1;
        }
    }
    return left;
}
```

递归法:边界的判断.

```java
 public int findPeakElement(int[] nums) {
        return func(nums, 0, nums.length - 1);
    }

    private int func(int[] nums, int left, int right) {
        if (left == right) {
            return left;
        }
        int mid = left + (right - left) / 2;
        if (nums[mid] > nums[mid + 1]) {
            right = mid;
            return func(nums, left, right);
        } else {
            left = mid + 1;
            return func(nums, left, right);
        }
    }
```





#### 实现一个数据结构，判断某个IP是否在1秒内请求了>=100次，用来在服务器上防止拒绝服务攻击

```
用循环数组，只需要长度为100，后来的请求覆盖掉之前的请求，然后判断最新的一个请求和队列中最早的请求时间差是否>=1。
```





#### ab替换成c,b替换成ef

输入一个char\[\] str，要求把str中所有的"ab"都替换成"c"，把所有单个的"b"都替换成"ef"。要求in-place，并且保证str中"ab"的个数 &gt;"b"的个数（也就是str的长度足够放下替换后的结果）

```java
//ab替换成c
//b替换成ef


private String handleStr(char[] chars) {
    int fillIndex = 0;
    int countB = 0;
    for (int i = 0; i < chars.length; ) {
        char ch = chars[i];
        if (ch == 'a') {
            if (i + 1 < chars.length && chars[i + 1] == 'b') {
                i++;
                chars[fillIndex++] = 'c';
            }
            i++;
        } else {
            if (ch == 'b') {
                countB++;
            }
            chars[fillIndex++] = chars[i++];
        }
    }
    for (int j = fillIndex; j < chars.length; j++) {
        chars[j] = 0;

    }
    int rightIndex = fillIndex + countB - 1;
    for (int i = fillIndex - 1; i >= 0; ) {
        char ch = chars[i];
        if (ch == 'b') {
            chars[rightIndex--] = 'f';
            chars[rightIndex--] = 'e';
            i--;
        } else {
            chars[rightIndex--] = chars[i--];
        }
    }
    return new String(chars);
}
```





#### [@@@4. 寻找两个有序数组的中位数](https://leetcode-cn.com/problems/median-of-two-sorted-arrays/)

@@@各种边界 非常麻烦

```java
public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    int len1 = nums1.length;
    int len2 = nums2.length;
    int sum = len1 + len2;
    if (sum % 2 == 1) {
        return findKth(nums1, 0, len1 - 1, nums2, 0, len2 - 1, sum / 2 + 1);
    } else {
        return (findKth(nums1, 0, len1 - 1, nums2, 0, len2 - 1, sum / 2) + findKth(nums1, 0, len1 - 1, nums2, 0, len2 - 1, sum / 2 + 1)) * 0.5;
    }
}

public int findKth(int[] nums1, int left1, int right1, int[] nums2, int left2, int right2, int k) {
    int len1 = right1 - left1 + 1;
    int len2 = right2 - left2 + 1;
    if (len1 > len2) {
        return findKth(nums2, left2, right2, nums1, left1, right1, k);
    }
    if (len1 == 0) {
        return nums2[left2 + k - 1];
    }
    if (k == 1) {
        return Math.min(nums1[left1], nums2[left2]);
    }
    int index1 = left1 + Math.min(len1, k / 2) - 1;
    int index2 = left2 + Math.min(len2, k / 2) - 1;
    if (nums1[index1] < nums2[index2]) {
        return findKth(nums1, index1 + 1, right1, nums2, left2, right2, k - (index1 - left1 + 1));
    } else {
        return findKth(nums1, left1, right1, nums2, index2 + 1, right2, k - (index2 - left2 + 1));
    }
}
```

#### [@@@701. 二叉搜索树中的插入操作](https://leetcode-cn.com/problems/insert-into-a-binary-search-tree/)

```java
public TreeNode insertIntoBST(TreeNode root, int val) {
    if (root == null) {
        return new TreeNode(val);
    }
    if (root.val < val) {
        root.right = insertIntoBST(root.right, val);
    } else {
        root.left = insertIntoBST(root.left, val);
    }
    return root;
}
```



#### [@@@@@@@@@@450. 删除二叉搜索树中的节点](https://leetcode-cn.com/problems/delete-node-in-a-bst/)

```java
public TreeNode deleteNode(TreeNode root, int key) {
    if (root == null) {
        return null;
    }

    if (root.val > key) {
        root.left = deleteNode(root.left, key);
        return root;
    } else if (root.val < key) {
        root.right = deleteNode(root.right, key);
        return root;
    }
    //else if(key==root.val)
    if (root.left == null) {
        TreeNode right = root.right;
        root.right = null;
        return right;
    } else if (root.right == null) {
        TreeNode left = root.left;
        root.left = null;
        return left;
    }
    //如果左右子树都不为空
    TreeNode leftMaxChild = getLeftMaxChild(root.left);
    TreeNode leftMaxChildCopy = new TreeNode(leftMaxChild.val);
    leftMaxChildCopy.left = delLeftMaxChild(root.left);
    leftMaxChildCopy.right = root.right;
    return leftMaxChildCopy;
}

private TreeNode delLeftMaxChild(TreeNode node) {
    if (node.right == null) {
        TreeNode left = node.left;
        node.left = null;
        return left;
    }
    node.right = delLeftMaxChild(node.right);
    return node;
}

private TreeNode getLeftMaxChild(TreeNode node) {
    while (node.right != null) {
        node = node.right;
    }
    return node;
}
```





#### [@@654. 最大二叉树](https://leetcode-cn.com/problems/maximum-binary-tree/)

```java
public TreeNode constructMaximumBinaryTree(int[] nums) {
    if (nums == null || nums.length == 0) {
        return null;
    }
    return construct(nums, 0, nums.length - 1);
}

private TreeNode construct(int[] nums, int left, int right) {
    if (left > right) {
        return null;
    }
    int[] ret = findMax(nums, left, right);
    TreeNode node = new TreeNode(ret[0]);
    node.left = construct(nums, left, ret[1] - 1);
    node.right = construct(nums, ret[1] + 1, right);
    return node;
}

private int[] findMax(int[] nums, int left, int right) {
    if (left > right) {
        return new int[]{};
    }
    int max = Integer.MIN_VALUE;
    int maxIndex = -1;
    for (int i = left; i <= right; i++) {
        if (nums[i] > max) {
            max = nums[i];
            maxIndex = i;
        }
    }
    return new int[]{max, maxIndex};
}
```







#### [@@@273. 整数转换英文表示](https://leetcode-cn.com/problems/integer-to-english-words/)

```java
class Solution {
    public String numberToWords(int num) {
        if (num == 0) {
            return "Zero";
        }
        int billion = num / 1000000000;
        int million = (num % 1000000000) / 1000000;
        int thousand = (num % 1000000) / 1000;
        int rest = num % 1000;

        String ret = "";
        if (billion != 0) {
            ret = three(billion) + " " + "Billion";
        }
        if (million != 0) {
            if (!ret.isEmpty()) {
                ret += " ";
            }
            ret += three(million) + " " + "Million";
        }
        if (thousand != 0) {
            if (!ret.isEmpty()) {
                ret += " ";
            }
            ret += three(thousand) + " " + "Thousand";
        }
        if (rest != 0) {
            if (!ret.isEmpty()) {
                ret += " ";
            }
            ret += three(rest);
        }
        return ret;
    }

    public String one(int num) {
        switch (num) {
            case 1:
                return "One";
            case 2:
                return "Two";
            case 3:
                return "Three";
            case 4:
                return "Four";
            case 5:
                return "Five";
            case 6:
                return "Six";
            case 7:
                return "Seven";
            case 8:
                return "Eight";
            case 9:
                return "Nine";
        }
        return "";
    }

    public String twoLessThan20(int num) {
        switch (num) {
            case 10:
                return "Ten";
            case 11:
                return "Eleven";
            case 12:
                return "Twelve";
            case 13:
                return "Thirteen";
            case 14:
                return "Fourteen";
            case 15:
                return "Fifteen";
            case 16:
                return "Sixteen";
            case 17:
                return "Seventeen";
            case 18:
                return "Eighteen";
            case 19:
                return "Nineteen";
        }
        return "";
    }

    public String ten(int num) {
        switch (num) {
            case 2:
                return "Twenty";
            case 3:
                return "Thirty";
            case 4:
                return "Forty";
            case 5:
                return "Fifty";
            case 6:
                return "Sixty";
            case 7:
                return "Seventy";
            case 8:
                return "Eighty";
            case 9:
                return "Ninety";
        }
        return "";
    }

    public String two(int num) {
        if (num == 0) {
            return "";
        }
        int two = num / 10;
        int rest = num % 10;
        if (num < 10) {
            return one(num);
        } else if (num < 20) {
            return twoLessThan20(num);
        } else {
            if (two != 0 && rest != 0) {
                return ten(two) + " " + one(rest);
            } else if (two != 0 && rest == 0) {
                return ten(two);
            } 
        }
        return "";
    }

    public String three(int num) {
        if (num == 0) {
            return "";
        }
        int hundred = num / 100;
        int rest = num % 100;
        String ret = "";
        if (rest != 0 && hundred != 0) {
            return one(hundred) + " " + "Hundred" + " " + two(rest);
        } else if (hundred != 0) {
            return one(hundred) + " " + "Hundred";
        } else if (rest != 0) {
            return two(rest);
        }
        return "";
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        String s = solution.numberToWords(10);
        System.out.println(s);
    }
}
```















































