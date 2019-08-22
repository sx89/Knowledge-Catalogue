package javacode.jichu;

/**
 * @author sunxu93@163.com
 * @date 19/8/22/022 15:05
 */
public class EqualTest {
    public static void main(String[] args) {
        int a = 1;
        int b = 1;
        Integer integer = new Integer(3);
        Integer integer1 = new Integer(3);

        String c = "abc";
        String d = "abc";

        StringBuilder abc = new StringBuilder("abc");
        StringBuilder abc1 = new StringBuilder("abc");

        System.out.println(a == b);

        System.out.println(integer==integer1);
        System.out.println(integer.equals(integer1));

        System.out.println(c == d);
        System.out.println(c.equals(d));

        System.out.println(abc == abc1);
        System.out.println(abc.equals(abc1));

    }
}
