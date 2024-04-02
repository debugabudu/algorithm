package com.yliu.algorithm.usually;

import com.yliu.structure.advance.Trie;

import java.util.*;

/**
 * 字符串和数组相关问题
 */
public class ArrayAndString {
    /**
     * 给你一个整数数组 nums，返回 数组 answer ，其中 answer[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积 。
     * 题目数据 保证 数组 nums之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内。
     * 请 不要使用除法，且在 O(n) 时间复杂度内完成此题。
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
     * 给你一个字符数组 chars ，请使用下述算法压缩：
     * 从一个空字符串 s 开始。对于 chars 中的每组 连续重复字符 ：
     * 如果这一组长度为 1 ，则将字符追加到 s 中。
     * 否则，需要向 s 追加字符，后跟这一组的长度。
     * 压缩后得到的字符串 s 不应该直接返回 ，需要转储到字符数组 chars 中。
     * 需要注意的是，如果组长度为 10 或 10 以上，则在 chars 数组中会被拆分为多个字符。
     * 请在 修改完输入数组后 ，返回该数组的新长度。
     * 你必须设计并实现一个只使用常量额外空间的算法来解决此问题。
     */
    public int compress(char[] chars) {
        int n = chars.length;
        int write = 0, left = 0;
        for (int read = 0; read < n; read++) {
            if (read == n - 1 || chars[read] != chars[read + 1]) {
                chars[write++] = chars[read];
                int num = read - left + 1;
                if (num > 1) {
                    int anchor = write;
                    while (num > 0) {
                        chars[write++] = (char) (num % 10 + '0');
                        num /= 10;
                    }
                    reverse(chars, anchor, write - 1);
                }
                left = read + 1;
            }
        }
        return write;
    }

    public void reverse(char[] chars, int left, int right) {
        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
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
        Arrays.sort(ids, (i, j) -> nums2[j] - nums2[i]);

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
     * 哦，不！你不小心把一个长篇文章中的空格、标点都删掉了，并且大写也弄成了小写。
     * 像句子"I reset the computer. It still didn’t boot!"已经变成了"iresetthecomputeritstilldidntboot"。
     * 在处理标点符号和大小写之前，你得先把它断成词语。当然了，你有一本厚厚的词典dictionary，不过，有些词没在词典里。
     * 假设文章用sentence表示，设计一个算法，把文章断开，要求未识别的字符最少，返回未识别的字符数。
     */
    public int replace(String[] dictionary, String sentence) {
        int n = sentence.length();

        Trie root = new Trie();
        for (String word: dictionary) {
            root.insert(word);
        }

        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 1; i <= n; ++i) {
            dp[i] = dp[i - 1] + 1;

            Trie curPos = root;
            for (int j = i; j >= 1; --j) {
                int t = sentence.charAt(j - 1) - 'a';
                if (curPos.children[t] == null) {
                    break;
                } else if (curPos.children[t].isEnd) {
                    dp[i] = Math.min(dp[i], dp[j - 1]);
                }
                if (dp[i] == 0) {
                    break;
                }
                curPos = curPos.children[t];
            }
        }
        return dp[n];
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
     * 给定字典中的两个词，长度相等。写一个方法，把一个词转换成另一个词， 但是一次只能改变一个字符。每一步得到的新词都必须能在字典中找到。
     * 编写一个程序，返回一个可能的转换序列。如有多个可能的转换序列，你可以返回任何一个。
     */
    public List<String> findLadders(String beginWord, String endWord, List<String> wordList) {
        //定义BFS的队列
        Queue<String> queue = new LinkedList<>();
        //ans存放答案
        List<String> ans = new LinkedList<>();
        //标记是否被访问过
        boolean[] visited = new boolean[wordList.size()];
        //存放每个单词的前驱，比如hot的前驱可以是hit,lot等；
        HashMap<String,String> map = new HashMap<>();
        //初步判断
        if(!wordList.contains(endWord)){
            return ans;
        }
        //将第一个单词加入队列
        queue.add(beginWord);
        boolean flag = false;
        //BFS主要操作
        while(!queue.isEmpty()){
            //先将头取出
            String queueHead = queue.poll();
            //如果队列头元素等于end word，代表已经找到，break同时设置flag = true;
            if(queueHead.equals(endWord)){
                flag = true;
                break;
            }
            //寻找可能的元素加入队列，并且设置对应的前驱。
            for(int i = 0;i < wordList.size();i ++){
                //如果未被访问过并且可以直接转换，则加入队列，compare()函数用来判断是否可以转换。
                if(!visited[i] && compare(wordList.get(i), queueHead)){
                    queue.add(wordList.get(i));
                    visited[i] = true;
                    //存储前驱
                    map.put(wordList.get(i), queueHead);
                }
            }
        }
        if(!flag){
            return ans;
        }

        //遍历答案
        String key = endWord;
        while(!Objects.equals(map.get(key), beginWord)){
            ans.add(key);
            key = map.get(key);
        }
        ans.add(key);
        ans.add(map.get(key));
        Collections.reverse(ans);
        return ans;
    }
    public static boolean compare(String word1,String word2){
        int diff = 0;
        for(int i = 0;i < word1.length();i ++){
            if(word1.charAt(i) != word2.charAt(i)){
                diff ++;
                if(diff >= 2){
                    return false;
                }
            }
        }
        return true;
    }
}
