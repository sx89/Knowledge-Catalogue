package javacode.jichu;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author sunxu93@163.com
 * @date 19/9/15/015 20:46
 */
public class QuickSort {
    public void quickSort(int[] nums ,int low,int high) {
        if (low > high) {
            return;
        }
        int loc = partition(nums,low,high);
        quickSort(nums, low, loc - 1);
        quickSort(nums, loc + 1, high);
    }
    public int partition(int[] nums ,int low,int high){
        int temp = nums[low];
        while(low<high){
            while (low < high && nums[high] > temp) {
                high--;
            }
            nums[low] = nums[high];
            while (low < high && nums[low] <= temp) {
                low++;
            }
            nums[high] = nums[low];

        }
        nums[low] = temp;
        return  low;
    }

    public static void main(String[] args) {
        int[] array = {3, 46, 8, 1, 3, 7};
        new QuickSort().quickSort(array,0,array.length-1);
        System.out.println(Arrays.toString(array));

    }
}
