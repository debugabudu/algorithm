package com.yliu.algorithm.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树的遍历和重建
 */
public class TreeAlgorithm {
    /**
     * 层序（队列实现）
     * 根节点入队，队列不为空，当前节点出队并打印，左右节点入队
     * 求二叉树深度：增加count和nextcount记录出队的节点数和该层进入的节点数，每次出队数等于一层进入数时depth++
     */
    public static void cengRecur(TreeNode root){
        Queue<TreeNode> tmp = new LinkedList<>();
        TreeNode current = root;
        tmp.offer(current);
        while (!tmp.isEmpty()){
            current = tmp.poll();
            System.out.print(current.value);
            if (current.left != null){
                tmp.offer(current.left);
            }
            if (current.right != null){
                tmp.offer(current.right);
            }
        }
    }

    /**
     * 前序非递归（堆栈实现）
     * 当前节点不为空且堆栈不为空，当前节点不为空，节点入栈并打印，左节点入栈；节点为空，出栈并找出栈节点的右节点
     */
    public static void preRecur2(TreeNode root){
        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode current = root;
        while (current != null || !stack.isEmpty()){
            if (current != null){
                stack.push(current);
                System.out.print(current.value);
                current = current.left;
            }else {
                current = stack.pop();
                current = current.right;
            }
        }
    }

    /**
     * 后序非递归（堆栈实现）
     * 增加一个辅助输出堆栈；当前节点不为空且堆栈不为空，当前节点不为空，节点入栈与辅助栈，右节点入栈；节点为空，出栈并找出栈节点的左节点，打印辅助堆栈。
     */
    public static void bacRecur2(TreeNode root){
        LinkedList<TreeNode> stack = new LinkedList<>();
        LinkedList<TreeNode> output = new LinkedList<>();
        TreeNode current = root;
        while(current != null || !stack.isEmpty()){
            if(current != null){
                output.push(current);
                stack.push(current);
                current = current.right;
            }else{
                current = stack.pop();
                current = current.left;
            }
        }
        while(output.size()>0){
            System.out.print(output.pop().value);
        }
    }

    /**
     * 重建二叉树
     * 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return helper(0, 0, inorder.length - 1, preorder, inorder);
    }

    public TreeNode helper(int preStart, int inStart, int inEnd, int[] preorder, int[] inorder) {
        if (preStart > preorder.length - 1 || inStart > inEnd) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[preStart]);
        int inIndex = 0; // Index of current root in inorder
        for (int i = inStart; i <= inEnd; i++) {
            if (inorder[i] == root.value) {
                inIndex = i;
            }
        }
        root.left = helper(preStart + 1, inStart, inIndex - 1, preorder, inorder);
        root.right = helper(preStart + inIndex - inStart + 1, inIndex + 1, inEnd, preorder, inorder);
        return root;
    }

    /**
     * 已知中序和后序，重建二叉树
     */
    public TreeNode buildTree1(int[] inorder, int[] postorder) {
        return  dfs(postorder.length-1,0,inorder.length-1,inorder,postorder);
    }
    public TreeNode dfs(int endpost,int instart,int inend,int [] inorder,int [] postorder){
        if(inorder.length-1<endpost||inend<instart||endpost<0) return null;
        TreeNode root=new TreeNode(postorder[endpost]);
        int index=0;
        for(int i=instart;i<=inend;i++){
            if(inorder[i]==postorder[endpost])
                index=i;
        }
        root.left=dfs(endpost-(inend-index)-1,instart,index-1,inorder,postorder);
        root.right=dfs(endpost-1,index+1,inend,inorder,postorder);
        return root;
    }

    /**
     * 之字形打印二叉树
     * 思路：利用Java中的LinkedList的底层实现是双向链表的特点。
     * 1)可用做队列,实现树的层次遍历
     * 2)可双向遍历,奇数层时从前向后遍历，偶数层时从后向前遍历
     */
    public ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> ret = new ArrayList<>();
        if (pRoot == null) {
            return ret;
        }
        ArrayList<Integer> list = new ArrayList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.addLast(null);//层分隔符
        queue.addLast(pRoot);
        boolean leftToRight = true;
        while (queue.size() != 1) {
            TreeNode node = queue.removeFirst();
            if (node == null) {//到达层分隔符
                Iterator<TreeNode> iter = null;
                if (leftToRight) {
                    iter = queue.iterator();//从前往后遍历
                } else {
                    iter = queue.descendingIterator();//从后往前遍历
                }
                leftToRight = !leftToRight;
                while (iter.hasNext()) {
                    TreeNode temp = (TreeNode)iter.next();
                    list.add(temp.value);
                }
                ret.add(new ArrayList<Integer>(list));
                list.clear();
                queue.addLast(null);//添加层分隔符
                continue;//一定要continue
            }
            if (node.left != null) {
                queue.addLast(node.left);
            }
            if (node.right != null) {
                queue.addLast(node.right);
            }
        }
        return ret;
    }
}
