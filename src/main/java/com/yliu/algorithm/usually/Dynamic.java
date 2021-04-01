package com.yliu.algorithm.usually;

import java.util.ArrayList;

/**
 * 动态规划
 */
public class Dynamic {
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
        int[] mark = new int[n + 1];//记录到达每一个位置的步数
        for(int i=m+1;i<=n;i++){   //初始化
            mark[i]=Integer.MAX_VALUE;
        }
        for(int i=m;i<n-1;i++){   //填mark[]
            if(mark[i]==Integer.MAX_VALUE)continue;//如果当前起始位置本身不可达 不作处理
            ArrayList<Integer> list=allFactor(i);//获得当前位置i的所有因子
            for (Integer integer : list) {//计算i能到达的每一个位置tmp
                int tmp = i + integer;
                int count = mark[i] + 1;
                if (tmp <= n && mark[tmp] > count) {//如果从I到达位置tmp的次数比以前记录的小 更新mark[tmp]
                    mark[tmp] = count;
                }
            }
        }
        return mark[n];
    }
    private static ArrayList<Integer>  allFactor(int n){//获得n的所有因子 除1 n外
        ArrayList<Integer> list=new ArrayList<>();
        for(int i=2;i<=Math.sqrt(n);i++){
            if(n%i==0){
                list.add(i);
                if(i!=n/i)list.add(n/i);
            }
        }
        return list;
    }

}
