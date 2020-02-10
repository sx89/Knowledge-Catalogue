

#### 88. 合并两个有序数组](https://leetcode-cn.com/problems/merge-sorted-array/)

```
nums1因为后方有空余位置,所以让nums1从后往前填充,可以达到时间复杂度O(m+n)空间复杂度O(1)
```

#### [@@253. 会议室 II](https://leetcode-cn.com/problems/meeting-rooms-ii/)



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



#### [@@443. 压缩字符串](https://leetcode-cn.com/problems/string-compression/)



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



#### [@@297. 二叉树的序列化与反序列化](https://leetcode-cn.com/problems/serialize-and-deserialize-binary-tree/)



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
