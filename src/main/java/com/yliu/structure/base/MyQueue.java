package com.yliu.structure.base;

import java.util.Stack;

/**
 * 两个堆栈实现一个队列
 */
public class MyQueue {
    Stack<Integer> stack1 = new Stack<>();
    Stack<Integer> stack2 = new Stack<>();

    public void offer(int i){
        stack1.push(i);
    }

    public int poll(){
        if (stack2.isEmpty()){
            if (stack1.isEmpty()){
                return -1;
            }
            while (!stack1.isEmpty()){
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }

    public boolean isEmpty(){
        return stack1.isEmpty() && stack2.isEmpty();
    }
}
