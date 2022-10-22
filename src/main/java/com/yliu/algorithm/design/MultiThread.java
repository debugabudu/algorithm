package com.yliu.algorithm.design;

import java.util.concurrent.atomic.AtomicInteger;

public class MultiThread {
    public static void main(String[] args) {
        int[] a = {1,3,5,7,8};
        int[] b = {2,4,6,9,10};
        AtomicInteger i = new AtomicInteger(0);
        AtomicInteger j = new AtomicInteger(0);
        Object object = new Object();
        new Thread(()->{
            synchronized (object){
                while (i.get()<=a.length-1){
                    while (i.get() <= a.length-1
                            && (j.get() >= b.length-1
                            || a[i.get()] <= b[j.get()])){
                        System.out.println(a[i.get()]);
                        i.addAndGet(1);
                    }
                    object.notifyAll();
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(()->{
            synchronized (object){
                while (j.get()<=b.length-1){
                    while (j.get()<=b.length-1
                            && (i.get() >= a.length-1
                            || b[j.get()] <= a[i.get()])){
                        System.out.println(b[j.get()]);
                        j.addAndGet(1);
                    }
                    object.notifyAll();
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
