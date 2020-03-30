package com.yliu.algorithm.questions;

import java.util.*;

public class RealQuestion {
    /**
     * 性代数-混合颜料
     * 你现在想绘制一幅画，但是你现在没有足够颜色的颜料。为了让问题简单，我们用正整数表示不同颜色的颜料。
     * 你知道这幅画需要的n种颜色的颜料，你现在可以去商店购买一些颜料，但是商店不能保证能供应所有颜色的颜料，所以你需要自己混合一些颜料。
     * 混合两种不一样的颜色A和颜色B颜料可以产生(A XOR B)这种颜色的颜料(新产生的颜料也可以用作继续混合产生新的颜色,XOR表示异或操作)。
     * 本着勤俭节约的精神，你想购买更少的颜料就满足要求，所以兼职程序员的你需要编程来计算出最少需要购买几种颜色的颜料？
     */
    public void colors() {
        Scanner scan = new Scanner(System.in);
        while(scan.hasNext()){
            int n = scan.nextInt();
            int[] col = new int[n];
            for(int i=0; i<n; i++){
                col[i] = scan.nextInt();
            }
            Arrays.sort(col);
            int count = 0;
            for(int i=n-1; i>=0; i--){
                for(int j=i-1; j>=0; j--){
                    if(highbit(col[i]) == highbit(col[j])){
                        col[j] = col[j] ^ col[i];
                    }
                }
                Arrays.sort(col);
            }
            for(int i=0 ;i<n;i++)
                if(col[i] !=0){
                    count ++;
                }
            System.out.println(count);
        }
    }
    public static int highbit(int x){
        for(int i=31; i>=0; i--){
            int m = (x>>i)&1;
            if(m != 0)
                return i+1;
        }
        return 0;
    }

    /**
     * 正则表达式匹配
     * 请实现一个函数用来匹配包括'.'和星号的正则表达式。模式中的字符'.'表示任意一个字符，而星号表示它前面的字符可以出现任意次（包含0次）。
     * 在本题中，匹配是指字符串的所有字符匹配整个模式。例如，字符串"aaa"与模式"a.a"和"ab*ac*a"匹配，但是与"aa.a"和"ab*a"均不匹配。
     * 解题思路
     * 当模式中的第二个字符不是“*”时：
     * 1、如果字符串第一个字符和模式中的第一个字符相匹配，那么字符串和模式都后移一个字符，然后匹配剩余的。
     * 2、如果 字符串第一个字符和模式中的第一个字符相不匹配，直接返回false。
     * 而当模式中的第二个字符是“*”时：
     * 如果字符串第一个字符跟模式第一个字符不匹配，则模式后移2个字符，继续匹配。如果字符串第一个字符跟模式第一个字符匹配，可以有3种匹配方式：
     * 1、模式后移2字符，相当于x*被忽略；
     * 2、字符串后移1字符，模式后移2字符；
     * 3、字符串后移1字符，模式不变，即继续匹配字符下一位，因为*可以匹配多位
     */
    public boolean match(char[] str, char[] pattern) {
        if (str == null || pattern == null) {
            return false;
        }
        int strIndex = 0;
        int patternIndex = 0;
        return matchCore(str, strIndex, pattern, patternIndex);
    }
    public boolean matchCore(char[] str, int strIndex, char[] pattern, int patternIndex) {
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
     * 数组中的逆序对
     * 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。输入一个数组,求出这个数组中的逆序对的总数P。并将P对1000000007取模的结果输出。 即输出P%1000000007。
     * 思路：先把数组分割成子数组，先统计出子数组内部的逆序对的数目，然后再统计出两个相邻子数组之间的逆序对的数目。在统计逆序对的过程中，还需要对数组进行排序。
     * 归并排序的改进，把数据分成前后两个数组(递归分到每个数组仅有一个数据项)，
     * 合并数组，合并时，出现前面的数组值array[i]大于后面数组值array[j]时；则前面
     * 数组array[I]~array[mid]都是大于array[j]的，count += mid+1 - i
     */
    public static int InversePairs(int [] array) {
        if(array==null||array.length==0){
            return 0;
        }
        int[] copy = new int[array.length];
        for(int i=0;i<array.length;i++){
            copy[i] = array[i];
        }
        int count = InversePairsCore(array,copy,0,array.length-1);//数值过大求余
        return count;
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
        for(int s=low;s<=high;s++){
            array[s] = copy[s];
        }
        return (leftCount+rightCount+count)%1000000007;
    }

    /**
     * 字符串排序
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
    public void PermutationHelper(char[] cs, int i, ArrayList<String> list) {
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
    public void swap(char[] x, int a, int b) {
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
    public void reverse(char[] seq, int start) {
        int len;
        if(seq == null || (len = seq.length) <= start)
            return;
        for (int i = 0; i < ((len - start) >> 1); i++) {
            int p = start + i, q = len - 1 - i;
            if (p != q)
                swap(seq, p, q);
        }
    }

    /**
     * 栈的压入弹出序列
     * 输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否为该栈的弹出顺序。假设压入栈的所有数字均不相等。
     * 例如序列1,2,3,4,5是某栈的压入顺序，序列4，5,3,2,1是该压栈序列对应的一个弹出序列，但4,3,5,1,2就不可能是该压栈序列的弹出序列。
     */
    public boolean IsPopOrder(int [] pushA,int [] popA) {
        if(pushA.length != popA.length || pushA == null || popA == null || pushA.length == 0 || popA.length == 0){
            return false;
        }
        Stack<Integer> s = new Stack<>();
        int i = 0;
        int j = 0;
        s.push(pushA[i++]);
        while(j <= popA.length-1){
            while(popA[j] != s.peek()){
                if(i == pushA.length) return false;
                s.push(pushA[i++]);
            }
            j++;
            s.pop();
        }
        return true;
    }

    /**
     * 跳石板
     * 小易来到了一条石板路前，每块石板上从1挨着编号为：1、2、3.......这条石板路要根据特殊的规则才能前进：对于小易当前所在的编号为K的 石板，小易单次只能往前跳K的一个约数(不含1和K)步，
     * 即跳到K+X(X为K的一个非1和本身的约数)的位置。 小易当前处在编号为N的石板，他想跳到编号恰好为M的石板去，小易想知道最少需要跳跃几次可以到达。
     * 思路 从m 到n 至少需要多少步
     * mark[i]记录到达位置i的最少步数。初始化mark[],起始位置mark[m]为0外，其它位置都为无穷大
     * I~[m,n-2]依次更新mark[]:
     * 判断，如果mark[i]为无穷大，则说明该位置不可由m到达，那么该位置也就到不了n。跳过，不作处理。减枝。
     * 如果mark[i]不是无穷大，计算i的因子，对每一个因子求出i的下一步的位置tmp，如果mark[tmp]>mark[i]+1，更新mark[tmp]=mark[i]+1;
     * 最终结果是mark[n],如果mark[n]是无穷大，则输出-1；否则返回mark[n]。
     * 比如以8开始 mark[8]=0,8的因子list={2,4},
     * factor=2,可到达10,mark[10]原本是无穷大,现在更新mark[10]=mark[8]+1;12同理。
     * 循环下一个i=9，mark[9]是无穷大，跳过，不用处理。
     * 也就是由8产生10和12，接下来就处理10,12及其产生的位置，而无需处理其他。
     */
    public static int deal(int m,int n){//m到n
        int mark[]=new int[n+1];//记录到达每一个位置的步数
        for(int i=m+1;i<=n;i++){   //初始化
            mark[i]=Integer.MAX_VALUE;
        }
        for(int i=m;i<n-1;i++){   //填mark[]
            if(mark[i]==Integer.MAX_VALUE)continue;//如果当前起始位置本身不可达 不作处理
            ArrayList<Integer> list=allFactor(i);//获得当前位置i的所有因子
            for(int j=0;j<list.size();j++){//计算i能到达的每一个位置tmp
                int tmp=i+list.get(j);
                int count=mark[i]+1;
                if(tmp<=n&&mark[tmp]>count){//如果从I到达位置tmp的次数比以前记录的小 更新mark[tmp]
                    mark[tmp]=count;
                }
            }
        }
        return mark[n];
    }
    public static ArrayList<Integer>  allFactor(int n){//获得n的所有因子 除1 n外
        ArrayList<Integer> list=new ArrayList<>();
        for(int i=2;i<=Math.sqrt(n);i++){
            if(n%i==0){
                list.add(i);
                if(i!=n/i)list.add(n/i);
            }
        }
        return list;
    }

    /**
     * BFS-推箱子
     * 在一个N*M的地图上，有1个玩家、1个箱子、1个目的地以及若干障碍，其余是空地。玩家可以往上下左右4个方向移动，但是不能移动出地图或者移动到障碍里去。
     * 如果往这个方向移动推到了箱子，箱子也会按这个方向移动一格，当然，箱子也不能被推出地图或推到障碍里。当箱子被推到目的地以后，游戏目标达成。
     * 现在告诉你游戏开始是初始的地图布局，请你求出玩家最少需要移动多少步才能够将游戏目标达成。
     * 输入描述:
     * 第一行输入两个数字N，M表示地图的大小。其中0<N，M<=8。
     * 接下来有N行，每行包含M个字符表示该行地图。其中 . 表示空地、X表示玩家、*表示箱子、#表示障碍、@表示目的地。
     * 每个地图必定包含1个玩家、1个箱子、1个目的地
     * 输出：最短要几步，无法达成返回-1
     */
    private static class State {
        int px, py, bx, by;
        State pre;

        public State(int px, int py, int bx, int by, State pre) {
            this.px = px;
            this.py = py;
            this.bx = bx;
            this.by = by;
            this.pre = pre;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String s = sc.nextLine();
            int n = Integer.parseInt(s.split(" ")[0]);
            int px = -1, py = -1, bx = -1, by = -1;
            char[][] maze = new char[n][];
            for (int i = 0; i < n; i++) {
                maze[i] = sc.nextLine().toCharArray();
                for (int j = 0; j < maze[i].length; j++) {
                    if (maze[i][j] == 'X') {
                        px = i;
                        py = j;
                    } else if (maze[i][j] == '*') {
                        bx = i;
                        by = j;
                    }
                }
            }
            State start = new State(px, py, bx, by, null);
            List<State> list = bfs(maze, start);
            System.out.println(list.size() - 1);
        }

    }

    private static List<State> bfs(char[][] maze, State start) {
        int n = maze.length;
        int m = maze[0].length;
        boolean[][][][] added = new boolean[n][m][n][m];
        Queue<State> queue = new LinkedList<>();
        LinkedList<State> list = new LinkedList<>();
        queue.add(start);
        added[start.px][start.py][start.bx][start.py] = true;
        int[][] move = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        State end = null;
        while (!queue.isEmpty()) {
            State cur = queue.poll();
            if (maze[cur.bx][cur.by] == '@') {
                end = cur;
                break;
            }
            for (int[] a : move) {
                State next = new State(cur.px + a[0], cur.py + a[1], cur.bx, cur.by, cur);
                if (next.px == next.bx && next.py == next.by) {
                    next.bx += a[0];
                    next.by += a[1];
                    if (next.bx < 0 || next.bx >= n || next.by < 0 || next.by >= m || maze[next.bx][next.by] == '#')
                        continue;
                } else if (next.px < 0 || next.px >= n || next.py < 0 || next.py >= m || maze[next.px][next.py] == '#') {
                    continue;
                }
                if (!added[next.px][next.py][next.bx][next.by]) {
                    queue.add(next);
                    added[next.px][next.py][next.bx][next.by] = true;
                }
            }
        }
        if (end != null) {
            while (end != null) {
                list.addFirst(end);
                end = end.pre;
            }
        }
        return list;
    }

}
