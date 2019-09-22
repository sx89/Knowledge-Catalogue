package javacode.jichu;


/**
 * @author sunxu93@163.com
 * @date 19/9/22/022 14:32
 */
public class Trim {

    public static void main(String[] args) {
        String a = null;
        String b = myTrim(a);
        System.out.println(b);

        a.toLowerCase();
        a.compareTo("asd");
        a.isEmpty();
        new String();


    }

    public static String myTrim(String string) {
        if (string == null || string.length() < 1) {
            return "";
        }
        char[] chars = string.toCharArray();
        int start = 0;
        int end = chars.length-1;

        while (end >= 0 && chars[end] == ' ') {
            end--;
        }
        while (start <= chars.length-1 && chars[start] == ' ') {
            start++;
        }
        if (end < start) {
            return "";
        } else {
            //0,1,2,3,4,5
            //后面end+1 是因为substring 左闭右开,用的是数组下标
            //为什么是左闭右开  因为最后是new String (value,start,sublen);
            return string.substring(start, end+1);
        }
    }
}
