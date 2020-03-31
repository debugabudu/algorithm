package com.yliu.algorithm.usually;

import java.util.ArrayList;

/**
 * 动态规划
 */
public class Dynamic {
    /**
     * ①基本思想：基本思想与分治法类似，将待求解的问题分解为若干个子问题（阶段），按顺序求解子阶段，前一子问题的解，为后一子问题的求解提供了有用的信息。
     * 在求解任一子问题时，列出各种可能的局部解，通过决策保留那些有可能达到最优的局部解，丢弃其他局部解。依次解决各子问题，最后一个子问题就是初始问题的解。
     * ②适用情况：
     * 能采用动态规划求解的问题的一般要具有3个性质：
     * (1) 最优化原理：如果问题的最优解所包含的子问题的解也是最优的，就称该问题具有最优子结构，即满足最优化原理。
     * (2) 无后效性：即某阶段状态一旦确定，就不受这个状态以后决策的影响。也就是说，某状态以后的过程不会影响以前的状态，只与当前状态有关。
     * (3)有重叠子问题：即子问题之间是不独立的，一个子问题在下一阶段决策中可能被多次使用到。（该性质并不是动态规划适用的必要条件，但是如果没有这条性质，动态规划算法同其他算法相比就不具备优势）
     * ③基本求解步骤：
     * 初始状态→│决策１│→│决策２│→…→│决策ｎ│→结束状态
     * (1)划分阶段：按照问题的时间或空间特征，把问题分为若干个阶段。在划分阶段时，注意划分后的阶段一定要是有序的或者是可排序的，否则问题就无法求解。
     * (2)确定状态和状态变量：将问题发展到各个阶段时所处于的各种客观情况用不同的状态表示出来。当然，状态的选择要满足无后效性。
     * (3)确定决策并写出状态转移方程：因为决策和状态转移有着天然的联系，状态转移就是根据上一阶段的状态和决策来导出本阶段的状态。
     * 所以如果确定了决策，状态转移方程也就可写出。但事实上常常是反过来做，根据相邻两个阶段的状态之间的关系来确定决策方法和状态转移方程。
     * (4)寻找边界条件：给出的状态转移方程是一个递推式，需要一个递推的终止条件或边界条件。
     * 一般，只要解决问题的阶段、状态和状态转移决策确定了，就可以写出状态转移方程（包括边界条件）。
     * *实际应用中可以按以下几个简化的步骤进行设计：*
     * （1）分析最优解的性质，并刻画其结构特征。
     * （2）递归的定义最优解。
     * （3）以自底向上或自顶向下的记忆化方式（备忘录法）计算出最优值
     * （4）根据计算最优值时得到的信息，构造问题的最优解
     * ④算法实现：
     * 三要素：（1）问题的阶段（2）每个阶段的状态（3）从前一个阶段转化到后一个阶段之间的递推关系。
     */

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
