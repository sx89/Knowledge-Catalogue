package javacode.jichu;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author sunxu93@163.com
 * @date 19/9/16/016 15:06
 */
public class BFSTree {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    public void bfs(TreeNode root) {
        if (root == null) {
            return ;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode temp = queue.poll();
            System.out.println(temp.val);
            if (temp.left != null) {
                queue.offer(temp.left);
            }
            if (temp.right != null) {
                queue.offer(temp.right);
            }
        }
    }
}
