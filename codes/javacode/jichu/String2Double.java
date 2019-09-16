package javacode.jichu;


import com.sun.deploy.util.StringUtils;

/**
 * @author sunxu93@163.com
 * @date 19/9/16/016 15:15
 */
public class String2Double {
    public static  Double func(String string) {
        //缺少了一种判定条件  "".equals()  直接用 isEmpty()代替
        // trim把头尾的空格去掉 然后返回值
        if (string==null||"".equals(string.trim())) {
            return null;
        }
        Double sum = 0.0;
        String[] array = string.split("\\.");
        //十位部分
        String str1 = array[0];
        Double wei = 1.0;
        for (int i = str1.length() - 1; i >= 0; i--) {
            int num = str1.charAt(i) - '0';
            sum = num * wei + sum;
            wei *= 10;
        }
        //小数部分
        String str2 = array[1];
        wei = 0.1;
        for (int i = 0; i < str2.length(); i++) {
            int num = str2.charAt(i) - '0';
            sum = num * wei + sum;
            wei *= 0.1;
        }
        return sum;
    }

    public static void main(String[] args) {
        Double sum = func("0.1");
        System.out.println(sum);
    }
}
