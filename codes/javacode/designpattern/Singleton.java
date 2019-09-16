package javacode.designpattern;

/**
 * @author sunxu93@163.com
 * @date 19/9/15/015 21:45
 */
public class Singleton {

    //懒汉线程不安全
//    private static Singleton singletonInstance;
//
//    private Singleton() {
//
//    }
//
//    public static Singleton getUniqueInstance() {
//        if (singletonInstance == null) {
//            singletonInstance = new Singleton();
//        }
//        return singletonInstance;
//    }

    //饿汉式
//    private Singleton() {
//
//    }
//    private static Singleton singletonInstance = new Singleton();


}
