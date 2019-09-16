package javacode.designpattern;

import java.util.Arrays;

/**
 * @author sunxu93@163.com
 * @date 19/9/16/016 8:25
 */
@SuppressWarnings("all")
public class Singleton4 {

    private void quickSort(int[] nums, int low, int high) {
        if (low > high) {
            return;
        }
        int loc = partition(nums, low, high);
        quickSort(nums, low, loc - 1);
        quickSort(nums, loc + 1, high);
    }

    private int partition(int[] nums, int low, int high) {
        int temp = nums[low];
        while (low < high) {
            while (low < high && nums[high] >= temp) {
                high--;
            }
            nums[low] = nums[high];
            while (low < high && nums[low] <= temp) {
                low++;
            }
            nums[high] = nums[low];

        }
        nums[low]=temp;
        return  low;
    }


    public static void main(String[] args) {
        int[] array = {1, 5, 23, 5, 8, 9, 34, 7, 5,0};
        new Singleton4().quickSort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));

    }
}
