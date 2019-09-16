package javacode.designpattern;

/**
 * @author sunxu93@163.com
 * @date 19/9/15/015 21:55
 */
public class SynchronizedSingleton {
    private static SynchronizedSingleton synchronizedSingletonInstance;

    private SynchronizedSingleton() {

    }

    public static synchronized SynchronizedSingleton getUniqueInstance() {
        if (synchronizedSingletonInstance == null) {
            synchronizedSingletonInstance = new SynchronizedSingleton();
        }
        return synchronizedSingletonInstance;
    }
}
