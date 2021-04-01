package com.yliu.algorithm.questions;

import java.util.*;

public class RealQuestion1 {
    /**
     * 1.正则表达式匹配
     * 请实现一个函数用来匹配包括'.'和星号的正则表达式。模式中的字符'.'表示任意一个字符，而星号表示它前面的字符可以出现任意次（包含0次）。
     * 在本题中，匹配是指字符串的所有字符匹配整个模式。例如，字符串"aaa"与模式"a.a"和"ab*ac*a"匹配，但是与"aa.a"和"ab*a"均不匹配。
     * 解题思路
     * 当模式中的第二个字符不是“*”时：
     * ①如果字符串第一个字符和模式中的第一个字符相匹配，那么字符串和模式都后移一个字符，然后匹配剩余的。
     * ②如果 字符串第一个字符和模式中的第一个字符相不匹配，直接返回false。
     * 而当模式中的第二个字符是“*”时：
     * 如果字符串第一个字符跟模式第一个字符不匹配，则模式后移2个字符，继续匹配。如果字符串第一个字符跟模式第一个字符匹配，可以有3种匹配方式：
     * ①模式后移2字符，相当于x*被忽略；
     * ②字符串后移1字符，模式后移2字符；
     * ③字符串后移1字符，模式不变，即继续匹配字符下一位，因为*可以匹配多位
     */
    public boolean match(char[] str, char[] pattern) {
        if (str == null || pattern == null) {
            return false;
        }
        int strIndex = 0;
        int patternIndex = 0;
        return matchCore(str, strIndex, pattern, patternIndex);
    }
    private boolean matchCore(char[] str, int strIndex, char[] pattern, int patternIndex) {
        //有效性检验：str到尾，pattern到尾，匹配成功
        if (strIndex == str.length && patternIndex == pattern.length) {
            return true;
        }
        //pattern先到尾，匹配失败
        if (strIndex != str.length && patternIndex == pattern.length) {
            return false;
        }
        //模式第2个是*，且字符串第1个跟模式第1个匹配,分3种匹配模式；如不匹配，模式后移2位
        if (patternIndex + 1 < pattern.length && pattern[patternIndex + 1] == '*') {
            if ((strIndex != str.length && pattern[patternIndex] == str[strIndex]) || (pattern[patternIndex] == '.' && strIndex != str.length)) {
                return matchCore(str, strIndex, pattern, patternIndex + 2)//模式后移2，视为x*匹配0个字符
                        || matchCore(str, strIndex + 1, pattern, patternIndex + 2)//视为模式匹配1个字符
                        || matchCore(str, strIndex + 1, pattern, patternIndex);//*匹配1个，再匹配str中的下一个
            } else {
                return matchCore(str, strIndex, pattern, patternIndex + 2);
            }
        }
        //模式第2个不是*，且字符串第1个跟模式第1个匹配，则都后移1位，否则直接返回false
        if ((strIndex != str.length && pattern[patternIndex] == str[strIndex]) || (pattern[patternIndex] == '.' && strIndex != str.length)) {
            return matchCore(str, strIndex + 1, pattern, patternIndex + 1);
        }
        return false;
    }

    /**
     * 2.数组中的逆序对(归并排序)
     * 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。输入一个数组,求出这个数组中的逆序对的总数P。并将P对1000000007取模的结果输出。 即输出P%1000000007。
     */
    public static int InversePairs(int [] array) {
        if(array==null||array.length==0){
            return 0;
        }
        int[] copy = new int[array.length];
        System.arraycopy(array, 0, copy, 0, array.length);
        return InversePairsCore(array,copy,0,array.length-1);
    }
    private  static int InversePairsCore(int[] array,int[] copy,int low,int high){
        if(low==high){
            return 0;
        }
        int mid = (low+high)>>1;
        int leftCount = InversePairsCore(array,copy,low,mid)%1000000007;
        int rightCount = InversePairsCore(array,copy,mid+1,high)%1000000007;
        int count = 0;
        int i=mid;
        int j=high;
        int locCopy = high;
        while(i>=low&&j>mid){
            if(array[i]>array[j]){
                count += j-mid;
                copy[locCopy--] = array[i--];
                if(count>=1000000007){//数值过大求余
                    count%=1000000007;
                }
            }else{
                copy[locCopy--] = array[j--];
            }
        }
        for(;i>=low;i--){
            copy[locCopy--]=array[i];
        }
        for(;j>mid;j--){
            copy[locCopy--]=array[j];
        }
        if (high + 1 - low >= 0) System.arraycopy(copy, low, array, low, high + 1 - low);
        return (leftCount+rightCount+count)%1000000007;
    }

    /**
     * 3.字符串排序
     * 输入一个字符串,按字典序打印出该字符串中字符的所有排列。例如输入字符串abc,则打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab和cba。
     */
    //回溯法
    public ArrayList<String> Permutation(String str) {
        ArrayList<String> res = new ArrayList<>();
        if (str != null && str.length() > 0) {
            PermutationHelper(str.toCharArray(), 0, res);
            Collections.sort(res);
        }
        return res;
    }
    private void PermutationHelper(char[] cs, int i, ArrayList<String> list) {
        if(i == cs.length - 1) {
            String val = String.valueOf(cs);
            if (!list.contains(val))
                list.add(val);
        } else {
            for(int j = i; j < cs.length; ++j) {
                swap(cs, i, j);
                PermutationHelper(cs, i + 1, list);
                swap(cs, i, j);
            }
        }
    }
    private void swap(char[] x, int a, int b) {
        char t = x[a];
        x[a] = x[b];
        x[b] = t;
    }

    //字典生成算法
    public ArrayList<String> Permutation1(String str) {
        ArrayList<String> res = new ArrayList<>();

        if (str != null && str.length() > 0) {
            char[] seq = str.toCharArray();
            Arrays.sort(seq); //排列
            res.add(String.valueOf(seq)); //先输出一个解

            int len = seq.length;
            while (true) {
                int p = len - 1, q;
                //从后向前找一个seq[p - 1] < seq[p]
                while (p >= 1 && seq[p - 1] >= seq[p]) --p;
                if (p == 0) break; //已经是“最小”的排列，退出
                //从p向后找最后一个比seq[p]大的数
                q = p; --p;
                while (q < len && seq[q] > seq[p]) q++;
                --q;
                //交换这两个位置上的值
                swap(seq, q, p);
                //将p之后的序列倒序排列
                reverse(seq, p + 1);
                res.add(String.valueOf(seq));
            }
        }

        return res;
    }
    private void reverse(char[] seq, int start) {
        int len;
        if(seq == null || (len = seq.length) <= start)
            return;
        for (int i = 0; i < ((len - start) >> 1); i++) {
            int p = start + i, q = len - 1 - i;
            if (p != q)
                swap(seq, p, q);
        }
    }

    public static void main(String[] args) {
        int[] array = {6,5,2,7,3,9,8,4,1,10};
        System.out.println(InversePairs(array));
    }
}
