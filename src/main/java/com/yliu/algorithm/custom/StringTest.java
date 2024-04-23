package com.yliu.algorithm.custom;

import com.yliu.structure.advance.Trie;

import java.util.*;

public class StringTest {
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

    /**
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
     */
    public int lengthOfLongestSubstring(String s) {
        Set<Character> occ = new HashSet<>();
        // 右指针，初始值为 0，相当于我们在字符串的左边界的左侧，开始移动
        int rk = 0, ans = 0;
        for (int i=0; i<s.length(); i++){
            if (i != 0){
                occ.remove(s.charAt(i-1));
            }
            while (rk<s.length() && !occ.contains(s.charAt(rk))){
                occ.add(s.charAt(rk));
                rk++;
            }
            ans = Math.max(ans,rk-i);
        }
        return ans;
    }

    /**
     * 给出一个字符串数组words 组成的一本英语词典。返回words 中最长的一个单词，该单词是由words词典中其他单词逐步添加一个字母组成。
     * 若其中有多个可行的答案，则返回答案中字典序最小的单词。若无答案，则返回空字符串。
     */
    public String longestWord(String[] words) {
        Trie trie = new Trie();
        for (String word : words) {
            trie.insert(word);
        }
        String longest = "";
        for (String word : words) {
            if (trie.search(word)) {
                if (word.length() > longest.length() ||
                        (word.length() == longest.length() && word.compareTo(longest) < 0)) {
                    longest = word;
                }
            }
        }
        return longest;
    }

    /**
     * 给定两个字符串s和 p，找到s中所有p的异位词的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
     * 异位词 指由相同字母重排列形成的字符串（包括相同的字符串）。
     */
    public List<Integer> findAnagrams(String s, String p) {
        int sLen = s.length(), pLen = p.length();
        if (sLen < pLen) {
            return new ArrayList<>();
        }
        List<Integer> ans = new ArrayList<>();
        int[] sCount = new int[26];
        int[] pCount = new int[26];
        for (int i = 0; i < pLen; ++i) {
            ++sCount[s.charAt(i) - 'a'];
            ++pCount[p.charAt(i) - 'a'];
        }
        if (Arrays.equals(sCount, pCount)) {
            ans.add(0);
        }
        for (int i = 0; i < sLen - pLen; ++i) {
            --sCount[s.charAt(i) - 'a'];
            ++sCount[s.charAt(i + pLen) - 'a'];

            if (Arrays.equals(sCount, pCount)) {
                ans.add(i + 1);
            }
        }
        return ans;
    }

    /**
     * 给你一个字符串 s，找到 s 中最长的回文子串。
     */
    public String longestPalindrome(String s) {
        if (s.length() < 2){
            return s;
        }
        int start = 0, end = 0;
        for (int i=0; i<s.length(); i++){
            int len1 = expend(s,i,i);
            int len2 = expend(s,i,i+1);
            int len = Math.max(len1,len2);
            if (len > (end-start)){
                start = i - (len-1)/2;
                end = i + len/2;
            }
        }
        return s.substring(start,end+1);
    }

    public int expend(String s, int left, int right){
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)){
            left--;
            right++;
        }
        return right-left-1;
    }

    /**
     * 每年，政府都会公布一万个最常见的婴儿名字和它们出现的频率，也就是同名婴儿的数量。
     * 有些名字有多种拼法，例如，John 和 Jon 本质上是相同的名字，但被当成了两个名字公布出来。
     * 给定两个列表，一个是名字及对应的频率，另一个是本质相同的名字对。设计一个算法打印出每个真实名字的实际频率。
     * 注意，如果 John 和 Jon 是相同的，并且 Jon 和 Johnny 相同，则 John 与 Johnny 也相同，即它们有传递和对称性。
     * 在结果列表中，选择 字典序最小 的名字作为真实名字。
     */
    public String[] trulyMostPopular(String[] names, String[] synonyms) {
        Map<String, Integer> map = new HashMap<>();
        Map<String, String> unionMap = new HashMap<>();     //并查集， key(子孙)->value(祖宗)
        for (String name : names) {     //统计频率
            int idx1 = name.indexOf('(');
            int idx2 = name.indexOf(')');
            int frequency = Integer.parseInt(name.substring(idx1 + 1, idx2));
            map.put(name.substring(0, idx1), frequency);
        }
        for (String pair : synonyms) {  //union同义词
            int idx = pair.indexOf(',');
            String name1 = pair.substring(1, idx);
            String name2 = pair.substring(idx + 1, pair.length() - 1);
            while (unionMap.containsKey(name1)) {   //找name1祖宗
                name1 = unionMap.get(name1);
            }
            while (unionMap.containsKey(name2)) {   //找name2祖宗
                name2 = unionMap.get(name2);
            }
            if(!name1.equals(name2)){   //祖宗不同，要合并
                //出现次数是两者之和
                int frequency = map.getOrDefault(name1, 0) + map.getOrDefault(name2, 0);
                String trulyName = name1.compareTo(name2) < 0 ? name1 : name2;
                String nickName = name1.compareTo(name2) < 0 ? name2 : name1;
                unionMap.put(nickName, trulyName);      //小名作为大名的分支，即大名是小名的祖宗
                map.remove(nickName);       //更新一下数据
                map.put(trulyName, frequency);
            }
        }
        String[] res = new String[map.size()];
        int index = 0;
        for (String name : map.keySet()) {
            String sb = name + '(' +
                    map.get(name) +
                    ')';
            res[index++] = sb;
        }
        return res;
    }
}
