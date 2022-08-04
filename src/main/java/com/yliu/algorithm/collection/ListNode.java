package com.yliu.algorithm.collection;

import lombok.Data;

@Data
public class ListNode {
    int val;
    ListNode pre;
    ListNode next;
    public ListNode(int val){
        this.val = val;
    }
}
