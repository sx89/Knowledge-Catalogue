package javacode.jichu;

import java.util.Arrays;

/**
 * @author sunxu93@163.com
 * @date 19/9/16/016 23:41
 */
public class MaopaoSort {
    public void func(int[] nums){
        if (nums == null || nums.length <= 0) {
            return;
        }
        for (int i = 0; i <nums.length-1; i++) {
            for (int j = 0; j < nums.length -1- i; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j];
                    nums[j] = nums[j+1];
                    nums[j+1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {1, 7, 2, 4, 9, 0, 4};
        new MaopaoSort().func(array);
        System.out.println(Arrays.toString(array));
    }
}
