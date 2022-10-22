package com.yliu.algorithm.collection;

import lombok.Data;

@Data
public class ListNode {
    public int val;
    public ListNode pre;
    public ListNode next;
    public ListNode(int val){
        this.val = val;
    }
    public ListNode(int val, ListNode next){
        this.val = val;
        this.next = next;
    }
}
