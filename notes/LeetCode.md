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

改进:非迭代的先序遍历
    中序遍历 while(p!=null&&!s.isEmpty()){   while(p!=null)p = p.left;   }
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

