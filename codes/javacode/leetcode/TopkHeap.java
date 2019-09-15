package javacode.leetcode;

import java.util.PriorityQueue;

/**
 * @author sunxu93@163.com
 * @date 19/9/15/015 11:43
 */
public class TopkHeap {
    public static void main(String[] args) {

    }

    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(); // 小顶堆
        for (int val : nums) {
            pq.add(val);
            if (pq.size() > k)  // 维护堆的大小为 K
                pq.poll();
        }
        return pq.peek();
    }
}
