package java.jichu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 *
 *@author sunxu93@163.com
 *@date 19/8/22/022 13:05
 */

public class StringTest {

    public static String BASEINFO = "Mr.Y";
    public static final int COUNT = 2000000;

    /**
     * 执行一项String赋值测试
     */
    public static void doStringTest() {

        String str = new String(BASEINFO);
        long starttime = System.currentTimeMillis();
        for (int i = 0; i < COUNT / 100; i++) {
            str = str + "miss";
        }
        long endtime = System.currentTimeMillis();
        System.out.println((endtime - starttime)
                + " millis has costed when used String.");
    }

    /**
     * 执行一项StringBuffer赋值测试
     */
    public static void doStringBufferTest() {

        StringBuffer sb = new StringBuffer(BASEINFO);
        long starttime = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            sb = sb.append("miss");
        }
        long endtime = System.currentTimeMillis();
        System.out.println((endtime - starttime)
                + " millis has costed when used StringBuffer.");
    }

    /**
     * 执行一项StringBuilder赋值测试
     */
    public static void doStringBuilderTest() {

        StringBuilder sb = new StringBuilder(BASEINFO);
        long starttime = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            sb = sb.append("miss");
        }
        long endtime = System.currentTimeMillis();
        System.out.println((endtime - starttime)
                + " millis has costed when used StringBuilder.");
    }

    /**
     * 测试StringBuffer遍历赋值结果
     *
     * @param mlist
     */
    public static void doStringBufferListTest(List<String> mlist) {
        StringBuffer sb = new StringBuffer();
        long starttime = System.currentTimeMillis();
        for (String string : mlist) {
            sb.append(string);
        }
        long endtime = System.currentTimeMillis();
        System.out.println(sb.toString() + "buffer cost:"
                + (endtime - starttime) + " millis");
    }

    /**
     * 测试StringBuilder迭代赋值结果
     *
     * @param mlist
     */
    public static void doStringBuilderListTest(List<String> mlist) {
        StringBuilder sb = new StringBuilder();
        long starttime = System.currentTimeMillis();
        for (Iterator<String> iterator = mlist.iterator(); iterator.hasNext();) {
            sb.append(iterator.next());
        }

        long endtime = System.currentTimeMillis();
        System.out.println(sb.toString() + "builder cost:"
                + (endtime - starttime) + " millis");
    }

    public static void main(String[] args) {
        doStringTest();
        doStringBufferTest();
        doStringBuilderTest();

        List<String> list = new ArrayList<String>();
        list.add(" I ");
        list.add(" like ");
        list.add(" BeiJing ");
        list.add(" tian ");
        list.add(" an ");
        list.add(" men ");
        list.add(" . ");

        doStringBufferListTest(list);
        doStringBuilderListTest(list);
    }

}


//输出结果
//
//2711 millis has costed when used String.
//211 millis has costed when used StringBuffer.
//141 millis has costed when used StringBuilder.
// I  like  BeiJing  tian  an  men  . buffer cost:1 millis
// I  like  BeiJing  tian  an  men  . builder cost:0 millis
//
//```