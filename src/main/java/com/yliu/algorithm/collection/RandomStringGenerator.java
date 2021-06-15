package com.yliu.algorithm.collection;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 随机序列产生器
 */
public class RandomStringGenerator<T> implements Iterable<T> {
    private List<T> list;
    public RandomStringGenerator(List<T> list){
        this.list = list;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                return list.get((int) (list.size()*Math.random()));
            }
        };
    }

    public static void main(String[] args) {
        List<String> list = Arrays.asList("List","Tree","Array");
        RandomStringGenerator<String> generator = new RandomStringGenerator<>(list);
        for (String s : generator){
            System.out.println(s);
        }
    }
}
