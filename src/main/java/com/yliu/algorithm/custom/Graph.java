package com.yliu.algorithm.custom;

import java.util.*;

/**
 * 深度优先搜索：二叉树遍历、图的连通分量、岛屿问题
 * // node：当前节点；visited：访问标记；res：结果集
 * void dfs(Node node, boolean[] visited, List<State> res) {
 *     // 终止条件：到达目标或无效节点
 *     if (node == null || visited[node]) return;
 *     // 标记访问并处理当前节点
 *     visited[node] = true;
 *     processNode(node, res);
 *     // 遍历邻居节点
 *     for (Node neighbor : node.neighbors) {
 *         // 递归深入未访问节点
 *         if (!visited[neighbor]) {
 *             dfs(neighbor, visited, res);
 *         }
 *     }
 *     // 回溯：某些问题需撤销访问标记（如回溯法）
 *     // visited[node] = false;
 * }
 *
 * 广度优先搜索：最短路径（无权图）、层序遍历、迷宫最短步数
 * // start：起始节点；dist：距离记录；queue：辅助队列
 * void bfs(Node start, int[] dist) {
 *     Queue<Node> queue = new LinkedList<>();
 *     queue.offer(start);
 *     dist[start] = 0; // 初始化距离
 *     while (!queue.isEmpty()) {
 *         Node cur = queue.poll();
 *         // 处理当前节点（如到达终点）
 *         if (isTarget(cur)) return;
 *         // 遍历邻居节点
 *         for (Node neighbor : cur.neighbors) {
 *             // 剪枝：未访问过的节点入队
 *             if (dist[neighbor] == -1) {
 *                 dist[neighbor] = dist[cur] + 1;
 *                 queue.offer(neighbor);
 *             }
 *         }
 *     }
 * }
 * 拓扑排序（针对有向无环图，在有依赖关系的情况下，进行排序）：课程表、任务调度、依赖解析
 * // graph：邻接表；indegree：入度数组；queue：零入度节点队列
 * List<Integer> topologicalSort(List<Integer>[] graph, int n) {
 *     int[] indegree = new int[n];
 *     // 统计入度
 *     for (List<Integer> neighbors : graph) {
 *         for (int neighbor : neighbors) indegree[neighbor]++;
 *     }
 *     Queue<Integer> queue = new LinkedList<>();
 *     for (int i = 0; i < n; i++) {
 *         if (indegree[i] == 0) queue.offer(i);
 *     }
 *     List<Integer> order = new ArrayList<>();
 *     while (!queue.isEmpty()) {
 *         int cur = queue.poll();
 *         order.add(cur);
 *         // 删除当前节点，更新邻居入度
 *         for (int neighbor : graph[cur]) {
 *             if (--indegree[neighbor] == 0) {
 *                 queue.offer(neighbor);
 *             }
 *         }
 *     }
 *     return order.size() == n ? order : Collections.emptyList(); // 判断是否有环
 * }
 */
public class Graph {
    /**
     * 给你一个大小为 m x n 的二进制矩阵 grid 。
     * 岛屿是由一些相邻的1(代表土地) 构成的组合，这里的「相邻」要求两个 1 必须在 水平或者竖直的四个方向上 相邻。
     * 你可以假设grid 的四个边缘都被 0（代表水）包围着。
     * 岛屿的面积是岛上值为 1 的单元格的数目。
     * 计算并返回 grid 中最大的岛屿面积。如果没有岛屿，则返回面积为 0
     */
    public int dfs(int[][] grid) {
        int ans = 0;
        for (int i = 0; i != grid.length; ++i) {
            for (int j = 0; j != grid[0].length; ++j) {
                int cur = 0;
                Deque<Integer> stacki = new LinkedList<>();
                Deque<Integer> stackj = new LinkedList<>();
                stacki.push(i);
                stackj.push(j);
                while (!stacki.isEmpty()) {
                    int cur_i = stacki.pop(), cur_j = stackj.pop();
                    if (cur_i < 0 || cur_j < 0 || cur_i == grid.length
                            || cur_j == grid[0].length || grid[cur_i][cur_j] != 1) {
                        continue;
                    }
                    ++cur;
                    grid[cur_i][cur_j] = 0;
                    int[] di = {0, 0, 1, -1};
                    int[] dj = {1, -1, 0, 0};
                    for (int index = 0; index < 4; ++index) {
                        int next_i = cur_i + di[index], next_j = cur_j + dj[index];
                        stacki.push(next_i);
                        stackj.push(next_j);
                    }
                }
                ans = Math.max(ans, cur);
            }
        }
        return ans;
    }

    /**
     * 给定一个由 0 和 1 组成的矩阵 mat，请输出一个大小相同的矩阵，其中每一个格子是 mat 中对应位置元素到最近的 0 的距离。
     * 两个相邻元素间的距离为 1 。
     */
    public int[][] bfs(int[][] matrix) {
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int m = matrix.length, n = matrix[0].length;
        int[][] dist = new int[m][n];
        boolean[][] seen = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<>();
        // 将所有的 0 添加进初始队列中
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (matrix[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                    seen[i][j] = true;
                }
            }
        }
        // 广度优先搜索
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int i = cell[0], j = cell[1];
            for (int d = 0; d < 4; ++d) {
                int ni = i + dirs[d][0];
                int nj = j + dirs[d][1];
                if (ni >= 0 && ni < m && nj >= 0 && nj < n && !seen[ni][nj]) {
                    dist[ni][nj] = dist[i][j] + 1;
                    queue.offer(new int[]{ni, nj});
                    seen[ni][nj] = true;
                }
            }
        }
        return dist;
    }

    /**
     * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
     */
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int nr = grid.length;
        int nc = grid[0].length;
        int num_islands = 0;
        for (int r = 0; r < nr; ++r) {
            for (int c = 0; c < nc; ++c) {
                if (grid[r][c] == '1') {
                    ++num_islands;
                    grid[r][c] = '0';
                    Queue<Integer> neighbors = new LinkedList<>();
                    neighbors.add(r * nc + c);
                    while (!neighbors.isEmpty()) {
                        int id = neighbors.remove();
                        int row = id / nc;
                        int col = id % nc;
                        if (row - 1 >= 0 && grid[row-1][col] == '1') {
                            neighbors.add((row-1) * nc + col);
                            grid[row-1][col] = '0';
                        }
                        if (row + 1 < nr && grid[row+1][col] == '1') {
                            neighbors.add((row+1) * nc + col);
                            grid[row+1][col] = '0';
                        }
                        if (col - 1 >= 0 && grid[row][col-1] == '1') {
                            neighbors.add(row * nc + col-1);
                            grid[row][col-1] = '0';
                        }
                        if (col + 1 < nc && grid[row][col+1] == '1') {
                            neighbors.add(row * nc + col+1);
                            grid[row][col+1] = '0';
                        }
                    }
                }
            }
        }
        return num_islands;
    }

    /**
     * 用以太网线缆将n台计算机连接成一个网络，计算机的编号从0到n-1。
     * 线缆用connections表示，其中connections[i] = [a, b]连接了计算机a和b。
     * 网络中的任何一台计算机都可以通过网络直接或者间接访问同一个网络中其他任意一台计算机。
     * 给你这个计算机网络的初始布线connections，你可以拔开任意两台直连计算机之间的线缆，并用它连接一对未直连的计算机。
     * 请你计算并返回使所有计算机都连通所需的最少操作次数。如果不可能，则返回-1 。
     */
    public int makeConnected(int n, int[][] connections) {
        if (connections.length < n - 1) {
            return -1;
        }
        List<Integer>[] edges = new List[n];
        boolean[] used = new boolean[n];
        for (int i = 0; i < n; ++i) {
            edges[i] = new ArrayList<>();
        }
        for (int[] conn : connections) {
            edges[conn[0]].add(conn[1]);
            edges[conn[1]].add(conn[0]);
        }
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            if (!used[i]) {
                dfs(i,edges,used);
                ++ans;
            }
        }

        return ans - 1;
    }

    public void dfs(int u, List<Integer>[] edges, boolean[] used) {
        used[u] = true;
        for (int v : edges[u]) {
            if (!used[v]) {
                dfs(v,edges,used);
            }
        }
    }

    /**
     * 你有一个带有四个圆形拨轮的转盘锁。每个拨轮都有10个数字： '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' 。
     * 每个拨轮可以自由旋转：例如把 '9' 变为'0'，'0' 变为 '9' 。每次旋转都只能旋转一个拨轮的一位数字。
     * 锁的初始数字为 '0000' ，一个代表四个拨轮的数字的字符串。
     * 列表 deadends 包含了一组死亡数字，一旦拨轮的数字和列表里的任何一个元素相同，这个锁将会被永久锁定，无法再被旋转。
     * 字符串 target 代表可以解锁的数字，你需要给出解锁需要的最小旋转次数，如果无论如何不能解锁，返回 -1 。
     */
    public int openLock(String[] deadEnds, String target) {
        if ("0000".equals(target)) {
            return 0;
        }

        Set<String> dead = new HashSet<>(Arrays.asList(deadEnds));
        if (dead.contains("0000")) {
            return -1;
        }

        int step = 0;
        Queue<String> queue = new LinkedList<>();
        queue.offer("0000");
        Set<String> seen = new HashSet<>();
        seen.add("0000");

        while (!queue.isEmpty()) {
            ++step;
            int size = queue.size();
            for (int i = 0; i < size; ++i) {
                String status = queue.poll();
                for (String nextStatus : get(status)) {
                    if (!seen.contains(nextStatus) && !dead.contains(nextStatus)) {
                        if (nextStatus.equals(target)) {
                            return step;
                        }
                        queue.offer(nextStatus);
                        seen.add(nextStatus);
                    }
                }
            }
        }

        return -1;
    }

    public char numPrev(char x) {
        return x == '0' ? '9' : (char) (x - 1);
    }

    public char numSucc(char x) {
        return x == '9' ? '0' : (char) (x + 1);
    }

    // 枚举 status 通过一次旋转得到的数字
    public List<String> get(String status) {
        List<String> ret = new ArrayList<>();
        char[] array = status.toCharArray();
        for (int i = 0; i < 4; ++i) {
            char num = array[i];
            array[i] = numPrev(num);
            ret.add(new String(array));
            array[i] = numSucc(num);
            ret.add(new String(array));
            array[i] = num;
        }
        return ret;
    }

    /**
     * 拓扑排序
     * 给定一个长度为 n 的整数数组 nums ，其中 nums 是范围为 [1，n] 的整数的排列。
     * 还提供了一个 2D 整数数组sequences，其中sequences[i]是nums的子序列。
     * 检查 nums 是否是唯一的最短超序列 。最短 超序列 是 长度最短 的序列，并且所有序列sequences[i]都是它的子序列。
     * 对于给定的数组sequences，可能存在多个有效的 超序列 。
     * 例如，对于sequences = [[1,2],[1,3]]，有两个最短的 超序列 ，[1,2,3] 和 [1,3,2] 。
     * 而对于sequences = [[1,2],[1,3],[1,2,3]]，唯一可能的最短 超序列 是 [1,2,3] 。[1,2,3,4] 是可能的超序列，但不是最短的。
     * 如果 nums 是序列的唯一最短 超序列 ，则返回 true ，否则返回 false 。
     * 子序列 是一个可以通过从另一个序列中删除一些元素或不删除任何元素，而不改变其余元素的顺序的序列。
     */
    public boolean sequenceReconstruction(int[] nums, int[][] sequences) {
        int n = nums.length;
        int[] inDegrees = new int[n + 1];
        Set<Integer>[] graph = new Set[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new HashSet<>();
        }
        for (int[] sequence : sequences) {
            int size = sequence.length;
            for (int i = 1; i < size; i++) {
                int prev = sequence[i - 1], next = sequence[i];
                if (graph[prev].add(next)) {
                    inDegrees[next]++;
                }
            }
        }
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 1; i <= n; i++) {
            if (inDegrees[i] == 0) {
                queue.offer(i);
            }
        }
        while (!queue.isEmpty()) {
            if (queue.size() > 1) {
                return false;
            }
            int num = queue.poll();
            Set<Integer> set = graph[num];
            for (int next : set) {
                inDegrees[next]--;
                if (inDegrees[next] == 0) {
                    queue.offer(next);
                }
            }
        }
        return true;
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
}
