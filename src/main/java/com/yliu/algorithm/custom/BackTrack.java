package com.yliu.algorithm.custom;

import java.util.*;

/**
 * 回溯-穷举（全排列、子集和、n皇后、数独）
 * void backtrack(State state, List<Choice> choices, List<State> res) {
 *     // 判断是否为解
 *     if (isSolution(state)) {
 *         // 记录解
 *         recordSolution(state, res);
 *         // 不再继续搜索
 *         return;
 *     }
 *     // 遍历所有选择
 *     for (Choice choice : choices) {
 *         // 剪枝：判断选择是否合法
 *         if (isValid(state, choice)) {
 *             // 尝试：做出选择，更新状态
 *             makeChoice(state, choice);
 *             // 进行下一轮选择
 *             backtrack(state, choices, res);
 *             // 回退：撤销选择，恢复到之前的状态
 *             undoChoice(state, choice);
 *         }
 *     }
 * }
 */
public class BackTrack {
    /**
     * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> output = new ArrayList<>();
        backtrack(output, nums, new boolean[nums.length], res);
        return res;
    }

    public void backtrack(List<Integer> output, int[] nums, boolean[] selected, List<List<Integer>> res) {
        // 所有数都填完了,记录一个解
        if (output.size() == nums.length) {
            res.add(new ArrayList<>(output));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (!selected[i]){
                // 添加数字
                selected[i] = true;
                output.add(num);
                // 继续递归填下一个数
                backtrack(output, nums, selected, res);
                // 撤销操作
                selected[i] = false;
                output.remove(output.size()-1);
            }
        }
    }

    /**
     * 给定一个可包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列。
     */
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> perm = new ArrayList<>();
        boolean[] vis = new boolean[nums.length];
        backtrack(nums, ans, perm, vis);
        return ans;
    }

    public void backtrack(int[] nums, List<List<Integer>> ans, List<Integer> perm, boolean[] vis) {
        if (perm.size() == nums.length) {
            ans.add(new ArrayList<>(perm));
            return;
        }
        Set<Integer> duplicated = new HashSet<>();
        for (int i = 0; i < nums.length; ++i) {
            int num = nums[i];
            if (!vis[i] && !duplicated.contains(num)){
                //添加数
                duplicated.add(num);
                vis[i] = true;
                perm.add(num);
                //下一轮选择
                backtrack(nums, ans, perm, vis);
                //回退
                vis[i] = false;
                perm.remove(perm.size()-1);
            }
        }
    }

    /**
     * 给定一个m x n 二维字符网格board 和一个字符串单词word 。如果word 存在于网格中，返回 true ；否则，返回 false 。
     * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。
     * 同一个单元格内的字母不允许被重复使用
     */
    public boolean exist(char[][] board, String word) {
        int h = board.length, w = board[0].length;
        boolean[][] visited = new boolean[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                boolean flag = check(board, visited, i, j, word, 0);
                if (flag) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean check(char[][] board, boolean[][] visited, int i, int j, String s, int k) {
        if (board[i][j] != s.charAt(k)) {
            return false;
        } else if (k == s.length() - 1) {
            return true;
        }
        visited[i][j] = true;
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        boolean result = false;
        for (int[] dir : directions) {
            int newi = i + dir[0], newj = j + dir[1];
            if (newi >= 0 && newi < board.length && newj >= 0 && newj < board[0].length) {
                if (!visited[newi][newj]) {
                    boolean flag = check(board, visited, newi, newj, s, k + 1);
                    if (flag) {
                        result = true;
                        break;
                    }
                }
            }
        }
        visited[i][j] = false;
        return result;
    }

    /**
     * 给你一个 无重复元素 的整数数组candidates 和一个目标整数target，找出candidates中可以使数字和为目标数target 的 所有不同组合 ，
     * 并以列表形式返回。你可以按 任意顺序 返回这些组合。
     * candidates 中的 同一个 数字可以 无限制重复被选取 。如果至少一个数字的被选数量不同，则两种组合是不同的。
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> combine = new ArrayList<>();
        dfs(candidates, target, ans, combine, 0);
        return ans;
    }

    public void dfs(int[] candidates, int target, List<List<Integer>> ans, List<Integer> combine, int idx) {
        if (idx == candidates.length) {
            return;
        }
        if (target == 0) {
            ans.add(new ArrayList<>(combine));
            return;
        }
        // 直接跳过
        dfs(candidates, target, ans, combine, idx + 1);
        // 选择当前数
        if (target - candidates[idx] >= 0) {
            combine.add(candidates[idx]);
            dfs(candidates, target - candidates[idx], ans, combine, idx);
            combine.remove(combine.size() - 1);
        }
    }

    /**
     * 给定一个候选人编号的集合candidates和一个目标数target，找出candidates中所有可以使数字和为target的组合。
     * candidates中的每个数字在每个组合中只能使用一次。
     * 解集不能包含重复的组合
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        // 为了将重复的数字都放到一起，所以先进行排序
        Arrays.sort(candidates);
        backTracking(candidates, target, 0, ans, path);
        return ans;
    }

    private void backTracking(int[] candidates, int target, int startIndex, List<List<Integer>> ans,
                              List<Integer> path) {
        if (target == 0) {
            ans.add(new ArrayList<>(path));
            return;
        }
        for (int i = startIndex; i < candidates.length; i++) {
            if (candidates[i] > target) {
                break;
            }
            // 出现重复节点，同层的第一个节点已经被访问过，所以直接跳过
            if (i > startIndex && candidates[i] == candidates[i - 1]) {
                continue;
            }
            path.add(candidates[i]);
            // 每个节点仅能选择一次，所以从下一位开始
            backTracking(candidates, target-candidates[i], i + 1, ans, path);
            path.remove(path.size()-1);
        }
    }

    /**
     * 给你一个字符串 s，请你将 s 分割成一些子串，使每个子串都是 回文串 。返回 s 所有可能的分割方案。
     */
    public List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();
        List<String> path = new ArrayList<>();
        backTracking(s, 0, res, path);
        return res;
    }

    private void backTracking(String s, int index, List<List<String>> res, List<String> path){
        if (index >= s.length()){
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i=index; i<s.length(); i++){
            if (isValid(s, index, i)){
                path.add(s.substring(index, i+1));
            }else {
                continue;
            }
            backTracking(s, i+1, res, path);
            path.remove(path.size()-1);
        }
    }

    private boolean isValid(String s, int l, int r){
        while (l < r){
            if (s.charAt(l) != s.charAt(r)){
                return false;
            }
            l++;
            r--;
        }
        return true;
    }

    /**
     * n皇后问题
     * 根据国际象棋的规则，皇后可以攻击与同处一行、一列或一条斜线上的棋子。
     * 给定n个皇后和一个nxn大小的棋盘，寻找使得所有皇后之间无法相互攻击的摆放方案。
     */
    void backtrack(int row, int n, List<List<String>> state, List<List<List<String>>> res,
                   boolean[] cols, boolean[] diags1, boolean[] diags2) {
        // 当放置完所有行时，记录解
        if (row == n) {
            List<List<String>> copyState = new ArrayList<>();
            for (List<String> sRow : state) {
                copyState.add(new ArrayList<>(sRow));
            }
            res.add(copyState);
            return;
        }
        // 遍历所有列
        for (int col = 0; col < n; col++) {
            // 计算该格子对应的主对角线和次对角线
            int diag1 = row - col + n - 1;
            int diag2 = row + col;
            // 剪枝：不允许该格子所在列、主对角线、次对角线上存在皇后
            if (!cols[col] && !diags1[diag1] && !diags2[diag2]) {
                // 尝试：将皇后放置在该格子
                state.get(row).set(col, "Q");
                cols[col] = diags1[diag1] = diags2[diag2] = true;
                // 放置下一行
                backtrack(row + 1, n, state, res, cols, diags1, diags2);
                // 回退：将该格子恢复为空位
                state.get(row).set(col, "#");
                cols[col] = diags1[diag1] = diags2[diag2] = false;
            }
        }
    }

    List<List<List<String>>> nQueens(int n) {
        // 初始化 n*n 大小的棋盘，其中 'Q' 代表皇后，'#' 代表空位
        List<List<String>> state = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<String> row = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                row.add("#");
            }
            state.add(row);
        }
        boolean[] cols = new boolean[n]; // 记录列是否有皇后
        boolean[] diags1 = new boolean[2 * n - 1]; // 记录主对角线上是否有皇后
        boolean[] diags2 = new boolean[2 * n - 1]; // 记录次对角线上是否有皇后
        List<List<List<String>>> res = new ArrayList<>();

        backtrack(0, n, state, res, cols, diags1, diags2);

        return res;
    }
}
