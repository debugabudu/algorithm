package com.yliu.algorithm.usually;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 排序
 */
public class Sort {
    /**
     * 稳定：冒泡、插入、归并、桶、基数
     * 不稳定：选择、希尔、堆、快速
     * 每种的时间复杂度、空间复杂度，不同情况如何选择排序算法
     */

    /**
     * 快排
     */
    public static void quickSort(int[] a, int left, int right){
        if (left >= right){
            return;
        }
        int k = sortOneTime(a,left,right);
        quickSort(a,left,k-1);
        quickSort(a,k+1,right);
    }
    private static int sortOneTime(int[] a, int left, int right){
        int key = a[left];
        while (left < right){
            while (a[right]>=key && left<right){
                right--;
            }
            a[left] = a[right];
            while (a[left]<=key && left<right){
                left++;
            }
            a[right] = a[left];
        }
        a[left] = key;
        return left;
    }

    /**
     * 堆排
     */
    public static void heapSort(int[] a){
        int i;
        // 构建一个大顶堆
        for (i = a.length / 2 - 1; i >= 0; i--) {
            adjustHeap(a, i, a.length - 1);
        }
        // 将堆顶记录和当前未经排序子序列的最后一个记录交换
        for (i = a.length - 1; i >= 0; i--) {
            int temp = a[0];
            a[0] = a[i];
            a[i] = temp;
            // 将a中前i-1个记录重新调整为大顶堆
            adjustHeap(a, 0, i - 1);
        }
    }
    private static void adjustHeap(int[] a, int i, int len){
        int temp, j;
        temp = a[i];
        // 沿关键字较大的孩子结点向下筛选
        for (j = 2 * i; j < len; j *= 2) {
            if (a[j] < a[j + 1]){
                ++j; // j为关键字中较大记录的下标
            }
            if (temp >= a[j]){
                break;
            }
            a[i] = a[j];
            i = j;
        }
        a[i] = temp;
    }

    /**
     * 希尔
     */
    public static void shellSort(int[] a){
        int j = 0;
        int temp = 0;
        for (int increment = a.length / 2; increment > 0; increment /= 2) {
            for (int i = increment; i < a.length; i++) {
                temp = a[i];
                for (j = i - increment; j >= 0; j -= increment) {
                    if (temp < a[j]) {
                        a[j + increment] = a[j];
                    } else {
                        break;
                    }
                }
                a[j + increment] = temp;
            }
        }
    }

    /**
     * 桶排序
     */
    public static void bucketSort(int[] a){
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int value : a) {
            max = Math.max(max, value);
            min = Math.min(min, value);
        }
        int num = (max - min)/a.length + 1;
        ArrayList<ArrayList<Integer>> bucket = new ArrayList<ArrayList<Integer>>(num);
        for (int i=0; i<num; i++){
            bucket.add(new ArrayList<Integer>());
        }
        for (int value : a) {
            int num1 = (value - min) / a.length;
            bucket.get(num1).add(value);
        }
        for (int i=0; i<num; i++){
            Collections.sort(bucket.get(i));
        }
        int count = 0;
        for (int i=0; i<num; i++){
            if (bucket.get(i) != null){
                for (Integer integer : bucket.get(i)) {
                    a[count] = integer;
                    count++;
                }
            }
        }
    }

    /**
     * 归并排序（递归）
     */
    public static void mergeSort(int[] a){
        sort(a, 0, a.length-1);
    }
    private static void sort(int[] a, int left, int right){
        if (left >= right){
            return;
        }
        int mid = (left + right)/2;
        sort(a, left, mid);
        sort(a, mid+1, right);
        merge(a, left, mid, right);
    }
    private static void merge(int[] a, int left, int mid, int right){
        int n = right - left + 1;
        int[] tmp = new int[n];
        int l = left;
        int r = mid + 1;
        int t = 0;
        while (l <= mid && r <= right){
            if (a[l] < a[r]){
                tmp[t++] = a[l++];
            }else {
                tmp[t++] = a[r++];
            }
        }
        while (l <= mid){
            tmp[t++] = a[l++];
        }
        while (r <= right){
            tmp[t++] = a[r++];
        }
        if (t >= 0) System.arraycopy(tmp, 0, a, left, t);
    }

    /**
     * 归并排序（非递归）
     * 分治
     */
    public static void sort(int[] a){
        for (int n=1; n<a.length; n = n*2){
            for (int i=0; i<a.length-n; i=i+2*n){
                int mid = i+n-1;
                int right = Math.min(i+2*n-1,a.length-1);
                merge(a, i,mid,right);
            }
        }
    }
}
