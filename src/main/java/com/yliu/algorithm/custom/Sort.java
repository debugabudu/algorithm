package com.yliu.algorithm.custom;

/**
 * 排序
 */
public class Sort {
    /**
     * 选择排序
     * 每次找到最小的元素，与未排序中的第一个元素交换
     * 时间-O(n2)，空间-O(1)，非稳定
     */
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

    /**
     * 冒泡排序
     * 每次从左开始向右遍历，依次比较相邻元素大小，如果“左元素 > 右元素”就交换二者。
     * 时间-O(n2)，空间-O(1)，稳定
     */
    private static void bubbleSort(int[] nums){
        for (int i=nums.length-1; i>0; i--){
            //优化，如果某层未进行交换，说明已经有序
            boolean flag = false;
            for (int j=0; j<i; j++){
                if (nums[j] > nums[j+1]){
                    int tmp = nums[j];
                    nums[j] = nums[j+1];
                    nums[j+1] = tmp;
                    flag = true;
                }
            }
            if (!flag){
                break;
            }
        }
    }

    /**
     * 插入排序-比冒泡操作单元更少，比选择效率高
     * 左侧元素认为已排序，将右边未排序的元素依次插入左侧已排序中
     * 时间-O(n2)，空间-O(1)，稳定
     * java内置排序函数-短数组用插入，长数组用快排
     */
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

    /**
     * 希尔排序-插入排序的变种
     * 设置步长，每间隔一个步长的元素为一组，每组内进行插入排序，逐渐缩短步长
     * 时间-O(n2)，空间-O(1)，稳定
     */
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

    /**
     * 归并排序
     * 将数组不断从中间划分，再将左右的子数组合并成一个有序数组
     * 时间-O(nlogn)，空间-O(n)，稳定
     */
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

    /**
     * 快速排序
     * 选取一个基准数，找到这个数在数组中的位置，再对左右分别快排
     * 时间-O(nlogn)，空间-O(n)，非稳定
     */
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

    /**
     * 堆排序
     * 构建大顶堆，将堆顶元素与最后一个元素交换，且堆长度减1
     * 时间-O(nlogn)，空间-O(1)，非稳定
     */
    private static void heapSort(int[] nums){
        //堆化除叶子节点外的所有节点
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