package javacode.designpattern;

/**
 * @author sunxu93@163.com
 * @date 19/9/15/015 21:58
 */
public class Singleton2 {
    private static Singleton2 singleton2Instance;

    private Singleton2() {

    }

    public static Singleton2 getUniqueInstance() {
        if (singleton2Instance == null) {
            synchronized (Singleton2.class) {
                if (singleton2Instance == null) {
                    singleton2Instance = new Singleton2();
                }
            }
        }
        return singleton2Instance;
    }
}
