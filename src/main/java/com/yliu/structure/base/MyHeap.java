package com.yliu.structure.base;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 堆，实际直接用PriorityQueue
 */
public class MyHeap {
    List<Integer> heap;

    public MyHeap(List<Integer> nums){
        heap = new ArrayList<>(nums);
        for (int i=parent(size()-1); i>=0; i--){
            siftDown(i);
        }
    }

    /* 获取左子节点的索引 */
    int left(int i) {
        return 2 * i + 1;
    }

    /* 获取右子节点的索引 */
    int right(int i) {
        return 2 * i + 2;
    }

    /* 获取父节点的索引 */
    int parent(int i) {
        return (i - 1) / 2; // 向下整除
    }

    /* 访问堆顶元素 */
    int peek() {
        return heap.get(0);
    }

    void swap(int i, int p){
        int temp = heap.get(i);
        heap.set(i, heap.get(p));
        heap.set(p, temp);
    }

    int size(){
        return heap.size();
    }

    boolean isEmpty(){
        return heap.isEmpty();
    }

    /* 元素入堆 */
    void push(int val) {
        // 添加节点
        heap.add(val);
        // 从底至顶堆化
        siftUp(size() - 1);
    }

    /* 从节点 i 开始，从底至顶堆化 */
    void siftUp(int i) {
        while (true) {
            // 获取节点 i 的父节点
            int p = parent(i);
            // 当“越过根节点”或“节点无须修复”时，结束堆化
            if (p < 0 || heap.get(i) <= heap.get(p))
                break;
            // 交换两节点
            swap(i, p);
            // 循环向上堆化
            i = p;
        }
    }

    /* 元素出堆 */
    int pop() {
        // 判空处理
        if (isEmpty())
            throw new IndexOutOfBoundsException();
        // 交换根节点与最右叶节点（交换首元素与尾元素）
        swap(0, size() - 1);
        // 删除节点
        int val = heap.remove(size() - 1);
        // 从顶至底堆化
        siftDown(0);
        // 返回堆顶元素
        return val;
    }

    /* 从节点 i 开始，从顶至底堆化 */
    void siftDown(int i) {
        while (true) {
            // 判断节点 i, l, r 中值最大的节点，记为 ma
            int l = left(i), r = right(i), ma = i;
            if (l < size() && heap.get(l) > heap.get(ma))
                ma = l;
            if (r < size() && heap.get(r) > heap.get(ma))
                ma = r;
            // 若节点 i 最大或索引 l, r 越界，则无须继续堆化，跳出
            if (ma == i)
                break;
            // 交换两节点
            swap(i, ma);
            // 循环向下堆化
            i = ma;
        }
    }

    /**
     * topk问题，找出数组中前k大的元素，直接排序-O(nlogn)，使用堆-O(nlogk)
     */
    Queue<Integer> topK(int[] nums, int k){
        Queue<Integer> pq = new PriorityQueue<>();
        for (int i=0; i<k; i++){
            pq.offer(nums[i]);
        }
        for (int i=k; i<nums.length; i++){
            if (!pq.isEmpty() && nums[i] > pq.peek()){
                pq.poll();
                pq.offer(nums[i]);
            }
        }
        return pq;
    }
}
