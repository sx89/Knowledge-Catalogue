<!-- TOC -->

- [[@@@@523. 连续的子数组和](https://leetcode-cn.com/problems/continuous-subarray-sum/)](#523-连续的子数组和httpsleetcode-cncomproblemscontinuous-subarray-sum)
- [[@@@@974. 和可被 K 整除的子数组](https://leetcode-cn.com/problems/subarray-sums-divisible-by-k/)](#974-和可被-k-整除的子数组httpsleetcode-cncomproblemssubarray-sums-divisible-by-k)
- [剔除k个数求最长连续区间](#剔除k个数求最长连续区间)

<!-- /TOC -->







#### [@@@@523. 连续的子数组和](https://leetcode-cn.com/problems/continuous-subarray-sum/)

@@@map存前缀和(map.get(i) 拿到的是-1~i的sum,-1的sum为0)的思路,可以用来解决求区间和的问题.

另外还有如下性质, 举例  i=1处,对5取模为3   j= 4处对5取模为3.则 index在2~4处的和可以整除5

<img src="pictures/LeetCode04/image-20200411153530641.png" alt="image-20200411153530641" style="zoom:50%;" />



@@为了处理case  [0,0]  k=0.而设置的map.put(0,-1)  这个哨兵太厉害了!

@@ i-map.get(sum)>=2  当前sum的index  减去之前sum的index是区间的长度.而非长度-1.

@@为了处理k=0,而设置的  if(k!=0)sum = sum%k;   这样的话,在k为0的时候,sum就是求阶梯和,如果存在map.contains(sum).则这段区间和为0

@@在k!=0的情况下,map记录sum的取余之后的值,找到map里包含sum,而且index比当前i小0以上的坐标



```java
public boolean checkSubarraySum(int[] nums, int k) {
    HashMap<Integer, Integer> map = new HashMap<>();
    int sum = 0;
    map.put(0, -1);
    for (int i = 0; i < nums.length; i++) {
        sum += nums[i];
        if (k != 0) {
            sum = sum % k;
        }
        if (map.containsKey(sum)) {
            if (i - map.get(sum) > 1) {
                return true;
            }
        } else {
            map.put(sum, i);
        }
    }
    return false;
}
```



#### [@@@@974. 和可被 K 整除的子数组](https://leetcode-cn.com/problems/subarray-sums-divisible-by-k/)

```java
思路1 : 错误的思路: 因为n过大,二维数组dp[i][j]存i~j的和会内存超出,而且时间复杂度也很高.

思路2:前缀和
    @@ 和为0的哨兵
    @@ nums[i]可能是过于大的整数或者负数, 所以要 先取余k,再加k,再取余sum = (sum % k + k) % k;
	@@故若想(sum[j]−sum[i])%K=0，则必有sum[j]%K = sum[i]%K .所以中途记录之前的sum和sum出现的次数.
        (如果要求得是区间长度等信息,则记录sum和sum出现的最早index)
        

public int subarraysDivByK(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        int sum = 0;
        int ret = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            sum = (sum % k + k) % k;
            if (map.containsKey(sum)) {
                ret += map.get(sum);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return ret;
    }


```





#### @@@剔除k个数求最长连续区间

最大剔除K个数，使得连续相同数最长。这个同3也是记录每个数的出现位置，然后依次遍历，采用滑动窗口记录长度即可。







#### @@@抽奖

题目:有1000人参与抽奖,10个奖品,每个人的抽奖次数可能是1到80次,设计一个抽奖算法,最后要把奖品抽完

假设一共抽奖n次，当前还剩k个奖品，第i次调用时，中奖概率 k/(n+1-i)

1.可以保证每个人的概率相等(第一个人的中奖概率是 10/1000,第二个人的中奖概率分两部分,第一个人中奖和不中奖)

2.可以保证最后抽完(k过大,会让中奖概率大于1)



#### @@完全二叉树的节点个数

完全二叉树

O

|\
OO

|\ |\

OO OO

|\ |\ |\ |\

OO Ox xx xx

\----------

求总的节点数？



```java

public class Main {
    

    private int func(TreeNode root) {
        if (root == null) {
            return 0;
        }
        TreeNode node = root;
        int maxLevel = 0;
        int sum = 0;
        while (node != null) {
            node = node.left;
            maxLevel++;
        }

        Node rootNode = new Node(1, root, 1);
        Node tempNode = null;
        while (rootNode.level <= maxLevel - 1) {
            tempNode = findMid(rootNode);
            if (maxLevel > tempNode.level) {
                rootNode = (rootNode.position * 2 - 1, rootNode.node.left, rootNode.level + 1,)
            } else if (maxLevel == tempNode.level) {
                rootNode = (rootNode.position * 2, rootNode.node.right, rootNode.level + 1,)
            }
        }
        sum = (int) Math.pow(2, maxLevel - 1) - 1 + tempNode.position;
        return sum;
    }

    private Node findMid(Node root) {
        if (root == null) {
            return null;
        }
        TreeNode treeNode = root.node.left;
        int level = root.level;
        int position = root.position;

        if (treeNode == null) {
            return root;
        }
        while (treeNode.right != null) {
            treeNode = treeNode.right;
            level++;
            position = position * 2;
        }
        return new Node(position, treeNode, level);
    }
}

class Node {
    int position;
    TreeNode node;
    int level;

    public Node(int positon, TreeNode node, int level) {
        this.position = position;
        this.node = node;
        this.level = level;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
}
```



#### 寻找最左边的1

给出01矩阵，每一行左边是0，右边是1，找到起始1的位置在最左边的1的下标。



如题

0000111

0000011

0001111

0111111

000001

返回第四行，下标为1.



思路1：逐列遍历，

思路2：利用类似于跳表的性质

第一行，折半查找，找到最左边的1所在的位置（colIndex = 4），然后跳到下一行

第二行，如果第二行的colIndex=4的地方为0，则往第三行跳。如果为1，则利用折半查找，找到第二行最左边1的坐标，再往第三行跳











































































































































