package com.yliu.algorithm.custom;

import java.util.Arrays;

/**
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
     * 给你两个 非递增 的整数数组 nums1 和 nums2 ，数组下标均 从 0 开始 计数。
     * 下标对 (i, j) 中 0 <= i < nums1.length 且 0 <= j < nums2.length 。
     * 如果该下标对同时满足 i <= j 且 nums1[i] <= nums2[j] ，则称之为 有效 下标对，该下标对的 距离 为 j - i.
     * 返回所有 有效 下标对 (i, j) 中的 最大距离 。如果不存在有效下标对，返回 0
     */
    public int maxDistance(int[] nums1, int[] nums2) {
        int max = 0;
        for (int i = 0; i < nums1.length; i++) {
            int left = i, right = nums2.length - 1, tmp1 = nums1[i];
            while (left <= right) {
                int mid = (left + right) >>> 1, tmp2 = nums2[mid];
                if (tmp2 < tmp1) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            max = Math.max((left - 1 - i), max);
        }
        return max;
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

    /**
     * 有个马戏团正在设计叠罗汉的表演节目，一个人要站在另一人的肩膀上。出于实际和美观的考虑，在上面的人要比下面的人矮一点且轻一点。
     * 已知马戏团每个人的身高和体重，请编写代码计算叠罗汉最多能叠几个人。
     */
    public int bestSeqAtIndex(int[] height, int[] weight) {
        int len = height.length;
        int[][] arr = new int[len][2];
        for(int i = 0; i < len; i++){
            arr[i][0] = height[i];
            arr[i][1] = weight[i];
        }

        Arrays.sort(arr, (o1, o2) -> {
            if(o1[0] == o2[0]){
                return Integer.compare(o1[1], o2[1]);
            }
            return Integer.compare(o2[0], o1[0]);
        });

        int[][] dp = new int[len + 1][2];
        dp[1] = arr[0];
        int index = 1;
        for(int i = 1; i < len; i++){
            if(arr[i][1] < dp[index][1]){
                dp[++index] = arr[i];
            } else {
                int pos = find(dp, arr[i], index);
                dp[pos] = arr[i];
            }
        }

        return index;
    }

    public int find(int[][] dp, int[] person, int index){
        int left = 1;
        int right = index;
        while(left < right){
            int mid = (left + right) / 2;
            if(dp[mid][1] <= person[1]){
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return right;
    }
}
