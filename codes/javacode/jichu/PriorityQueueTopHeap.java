package javacode.jichu;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author sunxu93@163.com
 * @date 19/9/16/016 15:46
 */
public class PriorityQueueTopHeap {

    public static void main(String[] args) {

//        PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(10, new Comparator<Integer>() {
//
//            //默认逻辑是o1-o2
//            //默认是小顶堆
//            //这个时候把默认比较逻辑取反,就得到了大顶堆
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return o2-o1;
//            }
//
//        });

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(10, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return 0;
            }
        });
        maxHeap.offer(1);
        maxHeap.offer(4);
        maxHeap.offer(9);
        maxHeap.offer(3);
        System.out.println(maxHeap.poll());
        //peek是只查不删  poll是查完删除
        System.out.println(maxHeap.peek());
        System.out.println(maxHeap.poll());
    }
}