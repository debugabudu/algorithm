package com.yliu.structure.base;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 两个队列实现一个栈
 */
public class MyStack {
    Queue<Integer> queue1 = new ArrayDeque<>();
    Queue<Integer> queue2 = new ArrayDeque<>();

    public void push(int i){
        queue1.offer(i);
    }

    public int pop(){
        int result;
        if (queue1.isEmpty()){
            result = -1;
        }else {
            while (queue1.size() != 1){
                queue2.offer(queue1.poll());
            }
            result = queue1.poll();
            while (!queue2.isEmpty()){
                queue1.offer(queue2.poll());
            }
        }
        return result;
    }
}
