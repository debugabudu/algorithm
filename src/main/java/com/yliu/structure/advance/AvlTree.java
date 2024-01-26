package com.yliu.structure.advance;

public class AvlTree {
    static class AvlTreeNode {
        int val;
        int height;
        AvlTreeNode left;
        AvlTreeNode right;
        AvlTreeNode(int x) { val = x; }
    }

    int height(AvlTreeNode node){
        return node == null ? -1 : node.height;
    }

    void updateHeight(AvlTreeNode node){
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    //获取平衡因子
    int balanceFactor(AvlTreeNode node){
        if (node == null){
            return 0;
        }
        return height(node.left) - height(node.right);
    }

    //右旋
    AvlTreeNode rightRotate(AvlTreeNode node){
        AvlTreeNode child = node.left;
        AvlTreeNode grandChild = node.right;
        //以child为原点，将node向右旋转
        child.right = node;
        node.left = grandChild;
        updateHeight(node);
        updateHeight(child);
        return child;
    }

    //左旋
    AvlTreeNode leftRotate(AvlTreeNode node){
        AvlTreeNode child = node.right;
        AvlTreeNode grandChild = node.left;
        //以child为原点，将node向左旋转
        child.left = node;
        node.right = grandChild;
        updateHeight(node);
        updateHeight(child);
        return child;
    }

    /**
     * AVL树重新平衡：
     * 失衡节点平衡因子>1，子节点平衡因子>=0，右旋
     * 失衡节点平衡因子>1，子节点平衡因子<0，先左再右
     * 失衡节点平衡因子<-1，子节点平衡因子<=0，左旋
     * 失衡节点平衡因子<-1，子节点平衡因子>0，先右再左
    */
    AvlTreeNode rotate(AvlTreeNode node){
        int balance = balanceFactor(node);
        if (balance > 1){
            if (balanceFactor(node.left) < 0) {
                node.left = leftRotate(node);
            }
            return rightRotate(node);
        }
        if (balance < -1){
            if (balanceFactor(node.right) > 0){
                node.right = rightRotate(node);
            }
            return leftRotate(node);
        }
        return node;
    }

    /* 插入节点 */
    AvlTreeNode insert(AvlTreeNode root, int val) {
        return insertHelper(root, val);
    }

    /* 递归插入节点（辅助方法） */
    AvlTreeNode insertHelper(AvlTreeNode node, int val) {
        if (node == null)
            return new AvlTreeNode(val);
        /* 1. 查找插入位置并插入节点 */
        if (val < node.val)
            node.left = insertHelper(node.left, val);
        else if (val > node.val)
            node.right = insertHelper(node.right, val);
        else
            return node; // 重复节点不插入，直接返回
        updateHeight(node); // 更新节点高度
        /* 2. 执行旋转操作，使该子树重新恢复平衡 */
        node = rotate(node);
        // 返回子树的根节点
        return node;
    }

    /* 删除节点 */
    AvlTreeNode remove(AvlTreeNode root, int val) {
        return removeHelper(root, val);
    }

    /* 递归删除节点（辅助方法） */
    AvlTreeNode removeHelper(AvlTreeNode node, int val) {
        if (node == null)
            return null;
        /* 1. 查找节点并删除 */
        if (val < node.val)
            node.left = removeHelper(node.left, val);
        else if (val > node.val)
            node.right = removeHelper(node.right, val);
        else {
            if (node.left == null || node.right == null) {
                AvlTreeNode child = node.left != null ? node.left : node.right;
                // 子节点数量 = 0 ，直接删除 node 并返回
                if (child == null)
                    return null;
                    // 子节点数量 = 1 ，直接删除 node
                else
                    node = child;
            } else {
                // 子节点数量 = 2 ，则将中序遍历的下个节点删除，并用该节点替换当前节点
                AvlTreeNode temp = node.right;
                while (temp.left != null) {
                    temp = temp.left;
                }
                node.right = removeHelper(node.right, temp.val);
                node.val = temp.val;
            }
        }
        updateHeight(node); // 更新节点高度
        /* 2. 执行旋转操作，使该子树重新恢复平衡 */
        node = rotate(node);
        // 返回子树的根节点
        return node;
    }
}
