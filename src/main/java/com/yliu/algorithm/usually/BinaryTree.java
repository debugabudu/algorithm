package com.yliu.algorithm.usually;

import java.util.*;

/**
 * 二叉树的遍历和重建
 */
public class BinaryTree {
    static class TreeNode {
        int value;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { value = x; }
    }
    /**
     * 层序（队列实现）
     * 根节点入队，队列不为空，当前节点出队并打印，左右节点入队
     * 求二叉树深度：1.递归  2.队列size为上一层节点数，全部出队为一层
     */
    public static List<TreeNode> levelRecur(TreeNode root){
        List<TreeNode> res = new ArrayList<>();
        Queue<TreeNode> helper = new LinkedList<>();
        TreeNode current = root;
        helper.offer(current);
        while (!helper.isEmpty()){
            current = helper.poll();
            res.add(current);
            if (current.left != null){
                helper.offer(current.left);
            }
            if (current.right != null){
                helper.offer(current.right);
            }
        }
        return res;
    }

    /**
     * 前序非递归（堆栈实现）
     * 当前节点不为空或堆栈不为空，当前节点不为空，节点入栈并打印，左节点入栈；节点为空，出栈并找出栈节点的右节点
     */
    public static List<TreeNode> preRecur(TreeNode root){
        List<TreeNode> res = new ArrayList<>();
        Stack<TreeNode> helper = new Stack<>();
        TreeNode current = root;
        while (current != null || !helper.isEmpty()){
            if (current != null){
                helper.push(current);
                res.add(current);
                current = current.left;
            }else {
                current = helper.pop();
                current = current.right;
            }
        }
        return res;
    }

    /**
     * 中序遍历(二叉搜索树的中序遍历刚好是从小到大)
     * 当前节点不为空或堆栈不为空，当前节点不为空，节点入栈，左节点入栈；节点为空，出栈并并打印，找出栈节点的右节点
     */
    public static List<TreeNode> midRecur(TreeNode root){
        List<TreeNode> res = new ArrayList<>();
        Stack<TreeNode> helper = new Stack<>();
        TreeNode current = root;
        while (current != null || !helper.isEmpty()){
            if (current != null){
                helper.push(current);
                current = current.left;
            }else {
                current = helper.pop();
                res.add(current);
                current = current.right;
            }
        }
        return res;
    }

    /**
     * 后序非递归（堆栈实现）
     * 增加一个辅助输出堆栈；当前节点不为空或堆栈不为空，当前节点不为空，节点入栈与辅助栈，右节点入栈；
     * 节点为空，出栈并找出栈节点的左节点，打印辅助堆栈。
     */
    public static List<TreeNode> backRecur(TreeNode root){
        List<TreeNode> res = new ArrayList<>();
        Stack<TreeNode> helper1 = new Stack<>();
        Stack<TreeNode> helper2 = new Stack<>();
        TreeNode current = root;
        while (current != null || !helper1.isEmpty()){
            if (current != null){
                helper1.push(current);
                helper2.push(current);
                current = current.right;
            }else {
                current = helper1.pop();
                current = current.left;
            }
        }
        while (!helper2.isEmpty()){
            res.add(helper2.pop());
        }
        return res;
    }

    /**
     * 重建二叉树
     * 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
     */
    public TreeNode buildTree(int[] preOrder, int[] inOrder) {
        return helper(0,0,inOrder.length-1,preOrder,inOrder);
    }
    private TreeNode helper(int preStart, int inStart, int inEnd, int[] preOrder, int[] inOrder) {
        if (preStart>preOrder.length-1 || inStart>inEnd){
            return null;
        }
        TreeNode node = new TreeNode(preOrder[preStart]);
        int index = 0;
        for (int i=inStart; i<=inEnd; i++){
            if (inOrder[i] == node.value){
                index = i;
                break;
            }
        }
        node.left = helper(preStart+1,inStart,index-1,preOrder,inOrder);
        node.right = helper(preStart+index-inStart+1,index+1,inEnd,preOrder,inOrder);
        return node;
    }

    /**
     * 已知中序和后序，重建二叉树
     */
    public TreeNode buildTree1(int[] inOrder, int[] postOrder) {
        return helper1(postOrder.length-1,0,inOrder.length-1,inOrder,postOrder);
    }
    private TreeNode helper1(int endPost, int inStart, int inEnd, int[] inOrder, int[] postOrder){
        if (endPost < 0 || inStart > inEnd || inOrder.length-1 < endPost){
            return null;
        }
        TreeNode node = new TreeNode(postOrder[endPost]);
        int index = 0;
        for (int i=inStart; i<=inEnd; i++){
            if (inOrder[i] == node.value){
                index = i;
                break;
            }
        }
        node.left = helper1(endPost-inEnd+index-1,inStart,index-1,inOrder,postOrder);
        node.right = helper1(endPost-1,index+1,inEnd,inOrder,postOrder);
        return node;
    }

    /**
     * 给你二叉树的根节点 root 和一个整数目标和 targetSum ，找出所有 从根节点到叶子节点 路径总和等于给定目标和的路径。
     * 叶子节点 是指没有子节点的节点。
     */
    public List<List<Integer>> pathSum(TreeNode root, int target) {
        List<List<Integer>> ret = new LinkedList<>();
        Deque<Integer> path = new LinkedList<>();
        dfs(root, target, ret, path);
        return ret;
    }

    public void dfs(TreeNode root, int target, List<List<Integer>> ret, Deque<Integer> path) {
        if (root == null) {
            return;
        }
        path.offerLast(root.value);
        target -= root.value;
        if (root.left == null && root.right == null && target == 0) {
            ret.add(new LinkedList<>(path));
        }
        dfs(root.left, target, ret, path);
        dfs(root.right, target, ret, path);
        path.pollLast();
    }

    /**
     * 删除二叉搜索树中的节点
     */
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (root.value > key) {
            root.left = deleteNode(root.left, key);
            return root;
        }
        if (root.value < key) {
            root.right = deleteNode(root.right, key);
            return root;
        }
        if (root.left == null && root.right == null) {
            return null;
        }
        if (root.right == null) {
            return root.left;
        }
        if (root.left == null) {
            return root.right;
        }
        TreeNode successor = root.right;
        while (successor.left != null) {
            successor = successor.left;
        }
        root.right = deleteNode(root.right, successor.value);
        successor.right = root.right;
        successor.left = root.left;
        return successor;
    }
}
