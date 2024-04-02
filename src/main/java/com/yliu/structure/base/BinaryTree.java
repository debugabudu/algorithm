package com.yliu.structure.base;

import java.util.*;

/**
 * 二叉树的遍历和重建、二叉搜索树插入删除节点
 * 深度优先搜索-递归/栈、广度优先-队列
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
    List<TreeNode> levelRecur(TreeNode root){
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
    List<TreeNode> preRecur(TreeNode root){
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
    List<TreeNode> midRecur(TreeNode root){
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
    List<TreeNode> backRecur(TreeNode root){
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
    TreeNode buildTree(int[] preOrder, int[] inOrder) {
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
    TreeNode buildTree1(int[] inOrder, int[] postOrder) {
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
     * 删除二叉搜索树中的节点
     */
    TreeNode deleteNode(TreeNode root, int key) {
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

    /**
     * 二叉搜索树插入节点
     */
    TreeNode insert(TreeNode root, int num) {
        // 若树为空，则初始化根节点
        if (root == null) {
            return new TreeNode(num);
        }
        TreeNode cur = root, pre = null;
        // 循环查找，越过叶节点后跳出
        while (cur != null) {
            // 找到重复节点，直接返回
            if (cur.value == num)
                return root;
            pre = cur;
            // 插入位置在 cur 的右子树中
            if (cur.value < num)
                cur = cur.right;
                // 插入位置在 cur 的左子树中
            else
                cur = cur.left;
        }
        // 插入节点
        TreeNode node = new TreeNode(num);
        if (pre.value < num)
            pre.right = node;
        else
            pre.left = node;
        return root;
    }
}
