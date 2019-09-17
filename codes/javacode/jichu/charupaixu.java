package javacode.jichu;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author sunxu93@163.com
 * @date 19/9/17/017 8:52
 */
public class charupaixu {
    public static void func (int[] nums){
        for(int i =1;i<nums.length;i++){
            int insertNum = nums[i];
            for (int j = i; j >0; j--) {
                if(nums[j]<nums[j-1]){
                    int temp = nums[j-1];
                    nums[j-1] = nums[j];
                    nums[j] = temp;
                }

            }
        }
    }

    public static void xuanzepaixu(int[] nums){
        for(int i = 0;i<nums.length-1;i++){
            int min = i;
            for(int j = i+1;j<nums.length;j++){
                if (nums[j] < nums[min]) {
                    min = j;
                }
            }
            if (min != i) {
                int temp = nums[i];
                nums[i] = nums[min];
                nums[min ]=temp;
            }
        }

    }

    public static void main(String[] args) {
        int[] a = {4, 21, 67, 82, 1};
//        func(a);
        xuanzepaixu(a);
        System.out.println(Arrays.toString(a));

    }
}
