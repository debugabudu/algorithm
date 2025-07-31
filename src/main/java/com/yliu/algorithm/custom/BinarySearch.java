package com.yliu.algorithm.custom;

/**
 * 有序数组查找、搜索插入位置、旋转数组搜索
 * 区间表示：
 * [l,r],l<=r,l=m+1,r=m-1
 * [l,r),l<r,l=m+1,r=m
 */
public class BinarySearch {
    /**
     * 二分查找插入点（无重复元素）
     * 有重复直接else j=m-1即可
     * 变种：查找重复数组中目标元素的边界
     *   左边界：等同插入重复数组
     *   右边界：等同找target+1的左边界-1
     */
    int binarySearchInsertionSimple(int[] nums, int target) {
        int i = 0, j = nums.length - 1; // 初始化双闭区间 [0, n-1]
        while (i <= j) {
            int m = i + (j - i) / 2; // 计算中点索引 m
            if (nums[m] < target) {
                i = m + 1; // target 在区间 [m+1, j] 中
            } else if (nums[m] > target) {
                j = m - 1; // target 在区间 [i, m-1] 中
            } else {
                return m; // 找到 target ，返回插入点 m
            }
        }
        // 未找到 target ，返回插入点 i
        return i;
    }

    /**
     * 给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。
     * 如果数组中不存在目标值 target，返回[-1, -1]。
     */
    public int[] searchRange(int[] nums, int target) {
        int leftIndex = binarySearch(nums,target,true);
        int rightIndex = binarySearch(nums,target,false)-1;
        if (leftIndex<=rightIndex && rightIndex<nums.length && nums[leftIndex]==target && nums[rightIndex]==target){
            return new int[]{leftIndex,rightIndex};
        }
        return new int[]{-1,-1};
    }

    private int binarySearch(int[] nums, int target, boolean lower){
        int left = 0, right = nums.length-1, ans = nums.length;
        while (left <= right){
            int mid = (left+right)/2;
            if (nums[mid] > target || (lower && nums[mid]>=target)){
                right = mid-1;
                ans = mid;
            }else {
                left = mid+1;
            }
        }
        return ans;
    }

    /**
     * 编写一个高效的算法来判断m x n矩阵中，是否存在一个目标值。该矩阵具有如下特性：
     * 每行中的整数从左到右按升序排列。
     * 每行的第一个整数大于前一行的最后一个整数。
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;
        int low = 0, high = m * n - 1;
        while (low <= high) {
            int mid = (high - low) / 2 + low;
            int x = matrix[mid / n][mid % n];
            if (x < target) {
                low = mid + 1;
            } else if (x > target) {
                high = mid - 1;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 整数数组 nums 按升序排列，数组中的值 互不相同 。
     * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回-1。
     */
    public int search(int[] nums, int target) {
        int n = nums.length;
        if (n == 0) {
            return -1;
        }
        if (n == 1) {
            return nums[0] == target ? 0 : -1;
        }
        int l = 0, r = n - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[0] <= nums[mid]) {
                if (nums[0] <= target && target < nums[mid]) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            } else {
                if (nums[mid] < target && target <= nums[n - 1]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }
        return -1;
    }
}
