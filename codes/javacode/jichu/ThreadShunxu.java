//package javacode.jichu;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
///**
// * @author sunxu93@163.com
// * @date 19/9/17/017 0:11
// */
//public class ThreadShunxu {
//
//
//    public static void main(String[] args) {
////        Thread t1 = new Thread(new runFuc());
////        Thread t2 = new Thread(new runFuc());
//
//        Thread t1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("1");
//            }
//        });
//
//        Thread t2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("2");
//            }
//        });
//        Thread t3 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("3");
//            }
//        });
//        ExecutorService executorService = Executors.newFixedThreadPool();
//        executorService.submit(t1);
//        executorService.submit(t2);  executorService.submit(t3);
//        executorService.shutdown();
//    }
//}
//
//
//class runFuc implements Runnable {
//
//    @Override
//    public void run() {
//        System.out.println("hello");
//    }
//}