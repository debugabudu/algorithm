package com.yliu.algorithm.usually;

/**
 * 回溯
 */
public class Backtracking {
    /**
     * ①基本思想：回溯法是一种选优搜索法，按选优条件向前搜索，以达到目标。但当探索到某一步时，发现原先选择并不优或达不到目标，就退回一步重新选择。
     * ②一般步骤：
     * （1）针对所给问题，确定问题的解空间：首先应明确定义问题的解空间，问题的解空间应至少包含问题的一个（最优）解。
     * （2）确定结点的扩展搜索规则
     * （3）以深度优先方式搜索解空间，并在搜索过程中用剪枝函数避免无效搜索。
     */

    /**
     * 矩阵中的路径
     * 请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。路径可以从矩阵中的任意一个格子开始，每一步可以在矩阵中向左，向右，向上，向下移动一个格子。
     * 如果一条路径经过了矩阵中的某一个格子，则该路径不能再进入该格子。 例如 a b c e s f c s a d e e 矩阵中包含一条字符串”bcced”的路径，但是矩阵中不包含"abcb"路径，
     * 回溯-因为不能重复进入同一个格子，相当于对使用过的格子进行标记。
     */
    public boolean hasPath(char[] matrix, int rows, int cols, char[] str){
        int []flag=new int[matrix.length];
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                //以每一个点作为起点进行遍历
                if(helper(matrix,rows,cols,i,j,0,str,flag)){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean helper(char[] matrix, int rows, int cols, int i, int j, int k, char[] str, int[] flag){
        int index=i*cols+j;
        if(i<0||i>=rows||j<0||j>=cols||flag[index]==1||matrix[index]!=str[k]){
            return false;
        }
        if(k==str.length-1){
            return true;
        }
        //先将点标记，继续往下递归遍历，如果最后找到路径返回true，否则取消标记，返回false
        flag[index]=1;
        if(helper(matrix,rows,cols,i+1,j,k+1,str,flag)
                ||helper(matrix,rows,cols,i-1,j,k+1,str,flag)
                ||helper(matrix,rows,cols,i,j+1,k+1,str,flag)
                ||helper(matrix,rows,cols,i,j-1,k+1,str,flag)){
            return true;
        }
        flag[index]=0;
        return false;
    }
}
