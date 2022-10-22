package com.yliu.algorithm.usually;

/**
 * 排序
 */
public class Sort {
    //选择排序
    private static void selectSort(int[] nums){
        if (nums == null || nums.length<=1){
            return;
        }
        for (int i=0; i<nums.length-1; i++){
            int min = i;
            for (int j=i+1; j<nums.length; j++){
                if (nums[j] < nums[min]){
                    min = j;
                }
            }
            if (min != i){
                int tmp = nums[i];
                nums[i] = nums[min];
                nums[min] = tmp;
            }
        }
    }

    //冒泡排序
    private static void bubbleSort(int[] nums){
        for (int i=0; i<nums.length-1; i++){
            for (int j=0; j<nums.length-i-1; j++){
                if (nums[j] > nums[j+1]){
                    int tmp = nums[j];
                    nums[j] = nums[j+1];
                    nums[j+1] = tmp;
                }
            }
        }
    }

    //插入排序
    private static void insertSort(int[] nums){
        for (int i=1; i<nums.length; i++){
            int tmp = nums[i];
            int j = i-1;
            while (j>=0 && nums[j]>tmp){
                nums[j+1] = nums[j];
                j--;
            }
            nums[j+1] = tmp;
        }
    }

    //希尔排序
    private static void shellSort(int[] nums){
        for (int gap=nums.length/2; gap>0; gap/=2){
            for (int i=gap; i<nums.length; i++){
                int tmp = nums[i];
                int j = i-gap;
                while (j>=0 && nums[j]>tmp){
                    nums[j+gap] = nums[j];
                    j-=gap;
                }
                nums[j+gap] = tmp;
            }
        }
    }

    //归并排序
    private static void mergeSort(int[] nums){
        sort(nums,0,nums.length-1);
    }

    private static void sort(int[] nums, int left, int right){
        if (right<=left){
            return;
        }
        int mid = (left+right)/2;
        sort(nums,left,mid);
        sort(nums,mid+1,right);
        merge(nums,left,mid,right);
    }

    private static void merge(int[] nums, int left, int mid, int right){
        int[] tmp = new int[right-left+1];
        int l = left;
        int r = mid+1;
        int t = 0;
        while (l<=mid && r<=right){
            if (nums[l]<nums[r]){
                tmp[t++] = nums[l++];
            }else {
                tmp[t++] = nums[r++];
            }
        }
        while (l<=mid){
            tmp[t++] = nums[l++];
        }
        while (r<=right){
            tmp[t++] = nums[r++];
        }
        if (t>=0)System.arraycopy(tmp,0,nums,left,t);
    }

    //快速排序
    private static void quickSort(int[] nums){
        quick(nums,0,nums.length-1);
    }

    private static void quick(int[] nums, int left, int right){
        if (right<=left){
            return;
        }
        int index = sortOneTime(nums,left,right);
        quick(nums,left,index-1);
        quick(nums,index+1,right);
    }

    private static int sortOneTime(int[] nums, int left, int right){
        int key = nums[left];
        while (left<right){
            while (nums[right]>key && right>left){
                right--;
            }
            nums[left] = nums[right];
            while (nums[left]<key && left<right){
                left++;
            }
            nums[right] = nums[left];
        }
        nums[left] = key;
        return left;
    }

    //堆排序
    private static void heapSort(int[] nums){
        for (int i=nums.length/2-1; i>=0; i--){
            adjustHeap(nums,i,nums.length-1);
        }
        for (int i=nums.length-1; i>=0; i--){
            int tmp = nums[0];
            nums[0] = nums[i];
            nums[i] = tmp;
            adjustHeap(nums,0,i-1);
        }
    }

    private static void adjustHeap(int[] nums, int index, int right){
        int tmp = nums[index];
        for (int j=2*index+1; j<=right; j=2*j+1){
            if (j+1<=right && nums[j]<nums[j+1]){
                j++;
            }
            if (tmp > nums[j]){
                break;
            }
            nums[index] = nums[j];
            index = j;
        }
        nums[index] = tmp;
    }
}