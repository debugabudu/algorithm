package com.yliu.algorithm.collection;

public class MyCircularQueue {
    private ListNode head;
    private ListNode tail;
    private int capacity;
    private int size;

    public MyCircularQueue(int k){
        capacity = k;
        size = 0;
    }

    // 插入
    public boolean enQueue(int value) {
        if (isFull()) {
            return false;
        }
        ListNode node = new ListNode(value);
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
        return true;
    }

    // 删除
    public boolean deQueue() {
        if (isEmpty()) {
            return false;
        }
        ListNode node = head;
        head = head.next;
        size--;
        return true;
    }

    // 队首获取元素
    public int Front() {
        if (isEmpty()) {
            return -1;
        }
        return head.val;
    }

    // 队尾获取元素
    public int Rear() {
        if (isEmpty()) {
            return -1;
        }
        return tail.val;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }
}
