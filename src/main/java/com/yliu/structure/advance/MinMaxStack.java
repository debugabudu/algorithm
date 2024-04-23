package com.yliu.structure.advance;

import java.util.Stack;

public class MinMaxStack {
    Stack<Integer> stack;
    Stack<Integer> minStack;
    Stack<Integer> maxStack;

    public MinMaxStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
        minStack.push(Integer.MAX_VALUE);
        maxStack = new Stack<>();
        maxStack.push(Integer.MIN_VALUE);
    }

    public void push(int x) {
        stack.push(x);
        minStack.push(Math.min(minStack.peek(), x));
        maxStack.push(Math.max(maxStack.peek(), x));
    }

    public void pop() {
        stack.pop();
        minStack.pop();
        maxStack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }

    public int getMax(){
        return maxStack.peek();
    }
}
