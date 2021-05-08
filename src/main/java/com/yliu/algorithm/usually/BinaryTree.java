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
     * 求二叉树深度：增加count和nextcount记录出队的节点数和该层进入的节点数，每次出队数等于一层进入数时depth++
     */
    public static void levelRecur(TreeNode root){
        Queue<TreeNode> helper = new LinkedList<>();
        TreeNode current = root;
        helper.offer(current);
        while (!helper.isEmpty()){
            current = helper.poll();
            System.out.println(current.value);
            if (current.left != null){
                helper.offer(current.left);
            }
            if (current.right != null){
                helper.offer(current.right);
            }
        }
    }

    /**
     * 前序非递归（堆栈实现）
     * 当前节点不为空或堆栈不为空，当前节点不为空，节点入栈并打印，左节点入栈；节点为空，出栈并找出栈节点的右节点
     */
    public static void preRecur(TreeNode root){
        Stack<TreeNode> helper = new Stack<>();
        TreeNode current = root;
        while (current != null || !helper.isEmpty()){
            if (current != null){
                helper.push(current);
                System.out.println(current.value);
                current = current.left;
            }else {
                current = helper.pop();
                current = current.right;
            }
        }
    }

    /**
     * 中序遍历
     */
    public static void midRecur(TreeNode root){
        Stack<TreeNode> helper = new Stack<>();
        TreeNode current = root;
        while (current != null || !helper.isEmpty()){
            if (current != null){
                helper.push(current);
                current = current.left;
            }else {
                current = helper.pop();
                System.out.println(current.value);
                current = current.right;
            }
        }
    }

    /**
     * 后序非递归（堆栈实现）
     * 增加一个辅助输出堆栈；当前节点不为空或堆栈不为空，当前节点不为空，节点入栈与辅助栈，右节点入栈；节点为空，出栈并找出栈节点的左节点，打印辅助堆栈。
     */
    public static void backRecur(TreeNode root){
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
            System.out.println(helper2.pop().value);
        }
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
     * 之字形打印二叉树
     * 思路：利用Java中的LinkedList的底层实现是双向链表的特点。
     * 1)可用做队列,实现树的层次遍历
     * 2)可双向遍历,奇数层时从前向后遍历，偶数层时从后向前遍历
     */
    public static void print(TreeNode root) {
        LinkedList<TreeNode> helper = new LinkedList<>();
        helper.addLast(null);
        helper.addLast(root);
        boolean leftToRight = true;
        while (helper.size() != 1){
            TreeNode node = helper.removeFirst();
            Iterator<TreeNode> iterator;
            if (node == null){
                if (leftToRight){
                    iterator = helper.iterator();
                }else {
                    iterator = helper.descendingIterator();
                }
                leftToRight = !leftToRight;
                while (iterator.hasNext()){
                    System.out.println(iterator.next().value);
                }
                helper.addLast(null);
                continue;
            }
            if (node.left != null){
                helper.addLast(node.left);
            }
            if (node.right != null){
                helper.addLast(node.right);
            }
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(2);
        TreeNode t1 = new TreeNode(4);
        TreeNode t2 = new TreeNode(6);
        TreeNode t3 = new TreeNode(8);
        TreeNode t4 = new TreeNode(1);
        TreeNode t5 = new TreeNode(3);
        TreeNode t6 = new TreeNode(5);
        TreeNode t7 = new TreeNode(7);
        root.left = t1;
        root.right = t2;
        t1.left = t3;
        t1.right = t4;
        t2.left = t5;
        t2.right = t6;
        t3.left = t7;
        levelRecur(root);
        System.out.println();
        preRecur(root);
        System.out.println();
        midRecur(root);
        System.out.println();
        backRecur(root);
        System.out.println();
        print(root);
    }
}
