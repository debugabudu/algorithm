package com.yliu.algorithm.custom;

import java.util.*;

/**
 * 字符串和数组相关问题
 */
public class ArrayTest {

    /**
     * 给你一个整数数组 nums，返回 数组 answer ，其中 answer[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积 。
     * 题目数据保证数组 nums 之中任意元素的全部前缀元素和后缀的乘积都在 32 位 整数范围内。
     * 请不要使用除法，且在 O(n) 时间复杂度内完成此题。
     */
    public int[] productExceptSelf(int[] nums) {
        int length = nums.length;
        int[] answer = new int[length];

        // answer[i] 表示索引 i 左侧所有元素的乘积
        // 因为索引为 '0' 的元素左侧没有元素， 所以 answer[0] = 1
        answer[0] = 1;
        for (int i = 1; i < length; i++) {
            answer[i] = nums[i - 1] * answer[i - 1];
        }

        // R 为右侧所有元素的乘积
        // 刚开始右边没有元素，所以 R = 1
        int R = 1;
        for (int i = length - 1; i >= 0; i--) {
            // 对于索引 i，左边的乘积为 answer[i]，右边的乘积为 R
            answer[i] = answer[i] * R;
            // R 需要包含右边所有的乘积，所以计算下一个结果时需要将当前值乘到 R 上
            R *= nums[i];
        }
        return answer;
    }

    /**
     * 假设有一个很长的花坛，一部分地块种植了花，另一部分却没有。可是，花不能种植在相邻的地块上，它们会争夺水源，两者都会死去。
     * 给你一个整数数组 flowerbed 表示花坛，由若干 0 和 1 组成，其中 0 表示没种植花，1 表示种植了花。
     * 另有一个数 n ，能否在不打破种植规则的情况下种入 n 朵花？能则返回 true ，不能则返回 false 。
     */
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        int count = 0;
        int m = flowerbed.length;
        int prev = -1;
        for (int i = 0; i < m; i++) {
            if (flowerbed[i] == 1) {
                if (prev < 0) {
                    count += i / 2;
                } else {
                    count += (i - prev - 2) / 2;
                }
                prev = i;
            }
        }
        if (prev < 0) {
            count += (m + 1) / 2;
        } else {
            count += (m - prev - 1) / 2;
        }
        return count >= n;
    }

    /**
     * 给你两个下标从 0 开始的整数数组 nums1 和 nums2 ，两者长度都是 n ，再给你一个正整数 k 。
     * 你必须从 nums1 中选一个长度为 k 的 子序列 对应的下标。
     * 对于选择的下标 i0 ，i1 ，...， ik - 1 ，你的 分数 定义如下：
     * nums1 中下标对应元素求和，乘以 nums2 中下标对应元素的 最小值 。
     * 用公式表示： (nums1[i0] + nums1[i1] +...+ nums1[ik - 1]) * min(nums2[i0] , nums2[i1], ... ,nums2[ik - 1]) 。
     * 请你返回 最大 可能的分数。
     * 一个数组的 子序列 下标是集合 {0, 1, ..., n-1} 中删除若干元素得到的剩余集合，也可以不删除任何元素
     */
    public long maxScore(int[] nums1, int[] nums2, int k) {
        int n = nums1.length;
        Integer[] ids = new Integer[n];
        for (int i = 0; i < n; i++) {
            ids[i] = i;
        }
        // 对下标排序，不影响原数组的顺序
        java.util.Arrays.sort(ids, (i, j) -> nums2[j] - nums2[i]);

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        long sum = 0;
        for (int i = 0; i < k; i++) {
            sum += nums1[ids[i]];
            pq.offer(nums1[ids[i]]);
        }

        long ans = sum * nums2[ids[k - 1]];
        for (int i = k; i < n; i++) {
            int x = nums1[ids[i]];
            if (x > pq.peek()) {
                sum += x - pq.poll();
                pq.offer(x);
                ans = Math.max(ans, sum * nums2[ids[i]]);
            }
        }
        return ans;
    }

    /**
     * 给定 N 个人的出生年份和死亡年份，第 i 个人的出生年份为 birth[i]，死亡年份为 death[i]，实现一个方法以计算生存人数最多的年份。
     * 你可以假设所有人都出生于 1900 年至 2000 年（含 1900 和 2000 ）之间。
     * 如果一个人在某一年的任意时期处于生存状态，那么他应该被纳入那一年的统计中。
     * 例如，生于 1908 年、死于 1909 年的人应当被列入 1908 年和 1909 年的计数。
     * 如果有多个年份生存人数相同且均为最大值，输出其中最小的年份。
     */
    public int maxAliveYear(int[] birth, int[] death) {
        int start = 1900, end = 2000;
        int[] num = new int[end - start + 2];
        for (int i = 0; i < birth.length; i++) {
            num[birth[i] - 1900]++;
            num[death[i] - 1900 + 1]--;
        }
        int max = 0, sum = 0, res = 0;
        for (int i = 0; i < end - start + 1; i++) {
            sum += num[i];
            if (sum > max) {
                max = sum;
                res = i;
            }
        }
        return start + res;
    }

    /**
     * 给定一个整数数组，编写一个函数，找出索引m和n，只要将索引区间[m,n]的元素排好序，整个数组就是有序的。
     * 注意：n-m尽量最小，也就是说，找出符合条件的最短序列。函数返回值为[m,n]，若不存在这样的m和n（例如整个数组是有序的），请返回[-1,-1]。
     */
    public int[] subSort(int[] array) {
        int n = array.length, left = 0, right = n - 1;
        while (left < right) {
            if (array[left] <= array[left + 1]) {
                ++left;
            }
            if (array[right] >= array[right - 1]) {
                --right;
            }
            if (array[left] > array[left + 1] && array[right] < array[right - 1]) {
                break;
            }
        }
        if (left >= right) {
            return new int[]{-1, -1};
        }
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        for (int i = left; i <= right; ++i) {
            max = Math.max(max, array[i]);
            min = Math.min(min, array[i]);
        }
        while (left > 0 && array[left - 1] > min) {
            --left;
        }
        while (right < n - 1 && array[right + 1] < max) {
            ++right;
        }
        return new int[]{left, right};
    }

    /**
     * 给你一个包含 n 个整数的数组nums，判断nums中是否存在三个元素 a，b，c ，使得a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。
     */
    public List<List<Integer>> threeSum(int[] nums) {
        int n = nums.length;
        java.util.Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        // 枚举 a
        for (int first = 0; first < n; ++first) {
            // 需要和上一次枚举的数不相同
            if (first > 0 && nums[first] == nums[first - 1]) {
                continue;
            }
            // c 对应的指针初始指向数组的最右端
            int third = n - 1;
            int target = -nums[first];
            // 枚举 b
            for (int second = first + 1; second < n; ++second) {
                // 需要和上一次枚举的数不相同
                if (second > first + 1 && nums[second] == nums[second - 1]) {
                    continue;
                }
                // 需要保证 b 的指针在 c 的指针的左侧
                while (second < third && nums[second] + nums[third] > target) {
                    --third;
                }
                // 如果指针重合，随着 b 后续的增加
                // 就不会有满足 a+b+c=0 并且 b<c 的 c 了，可以退出循环
                if (second == third) {
                    break;
                }
                if (nums[second] + nums[third] == target) {
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[first]);
                    list.add(nums[second]);
                    list.add(nums[third]);
                    ans.add(list);
                }
            }
        }
        return ans;
    }

    /**
     * 给你一个由 n 个整数组成的数组nums ，和一个目标值 target 。
     * 请你找出并返回满足下述全部条件且不重复的四元组[nums[a], nums[b], nums[c], nums[d]]
     * （若两个四元组元素一一对应，则认为两个四元组重复）：
     * 0 <= a, b, c, d< n
     * a、b、c 和 d 互不相同
     * nums[a] + nums[b] + nums[c] + nums[d] == target
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> quadruplets = new ArrayList<>();
        if (nums == null || nums.length < 4) {
            return quadruplets;
        }
        java.util.Arrays.sort(nums);
        int length = nums.length;
        for (int i = 0; i < length - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            if ((long) nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3] > target) {
                break;
            }
            if ((long) nums[i] + nums[length - 3] + nums[length - 2] + nums[length - 1] < target) {
                continue;
            }
            for (int j = i + 1; j < length - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                if ((long) nums[i] + nums[j] + nums[j + 1] + nums[j + 2] > target) {
                    break;
                }
                if ((long) nums[i] + nums[j] + nums[length - 2] + nums[length - 1] < target) {
                    continue;
                }
                int left = j + 1, right = length - 1;
                while (left < right) {
                    int sum = nums[i] + nums[j] + nums[left] + nums[right];
                    if (sum == target) {
                        quadruplets.add(java.util.Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        while (left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        left++;
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }
                        right--;
                    } else if (sum < target) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }
        return quadruplets;
    }

    /**
     * 假设你有两个数组，一个长一个短，短的元素均不相同。找到长数组中包含短数组所有的元素的最短子数组，其出现顺序无关紧要。
     * 返回最短子数组的左端点和右端点，如有多个满足条件的子数组，返回左端点最小的一个。若不存在，返回空数组。
     */
    public int[] shortestSeq(int[] big, int[] small) {
        int shortestStart = -1;
        int shortestLength = Integer.MAX_VALUE;
        Map<Integer, Integer> counts = new HashMap<>();
        int m = big.length, n = small.length;
        for (int num : small) {
            counts.put(num, -1);
        }
        int meets = 0;
        int start = 0, end = 0;
        while (end < m) {
            int curr = big[end];
            if (counts.containsKey(curr)) {
                counts.put(curr, counts.getOrDefault(curr, 0) + 1);
                if (counts.get(curr) == 0) {
                    meets++;
                }
            }
            while (meets == n) {
                if (end - start + 1 < shortestLength) {
                    shortestStart = start;
                    shortestLength = end - start + 1;
                }
                int prev = big[start];
                if (counts.containsKey(prev)) {
                    counts.put(prev, counts.get(prev) - 1);
                    if (counts.get(prev) < 0) {
                        meets--;
                    }
                }
                start++;
            }
            end++;
        }
        return shortestStart < 0 ? new int[0] : new int[]{shortestStart, shortestStart + shortestLength - 1};
    }

    /**
     * 给你一个非负整数数组nums ，你最初位于数组的第一个位置。
     * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
     * 你的目标是使用最少的跳跃次数到达数组的最后一个位置。
     * 假设你总是可以到达数组的最后一个位置。
     */
    public int jump(int[] nums) {
        int position = nums.length - 1;
        int steps = 0;
        while (position > 0) {
            for (int i = 0; i < position; i++) {
                if (i + nums[i] >= position) {
                    position = i;
                    steps++;
                    break;
                }
            }
        }
        return steps;
    }

    /**
     * 给定一个二进制数组 nums , 找到含有相同数量的 0 和 1 的最长连续子数组，并返回该子数组的长度。
     */
    public int findMaxLength(int[] nums) {
        int maxLength = 0;
        Map<Integer, Integer> map = new HashMap<>();
        int counter = 0;
        map.put(counter, -1);
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int num = nums[i];
            if (num == 1) {
                counter++;
            } else {
                counter--;
            }
            if (map.containsKey(counter)) {
                int prevIndex = map.get(counter);
                maxLength = Math.max(maxLength, i - prevIndex);
            } else {
                map.put(counter, i);
            }
        }
        return maxLength;
    }

    /**
     * 假设有打乱顺序的一群人站成一个队列，数组 people 表示队列中一些人的属性（不一定按顺序）。
     * 每个 people[i] = [hi, ki] 表示第 i 个人的身高为 hi ，前面 正好 有 ki 个身高大于或等于 hi 的人。
     * 请你重新构造并返回输入数组people 所表示的队列。
     * 返回的队列应该格式化为数组 queue ，其中 queue[j] = [hj, kj] 是队列中第 j 个人的属性（queue[0] 是排在队列前面的人）。
     */
    public int[][] reconstructQueue(int[][] people) {
        // 身高从大到小排（身高相同k小的站前面）
        java.util.Arrays.sort(people, (a, b) -> {
            if (a[0] == b[0]) return a[1] - b[1];
            return b[0] - a[0];
        });

        LinkedList<int[]> que = new LinkedList<>();

        for (int[] p : people) {
            que.add(p[1],p);
        }

        return que.toArray(new int[people.length][]);
    }
}
