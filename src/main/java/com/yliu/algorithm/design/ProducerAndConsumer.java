package com.yliu.algorithm.design;

import java.util.LinkedList;
import java.util.Queue;

public class ProducerAndConsumer {
    final int SIZE = 10;
    Queue<Integer> queue = new LinkedList<>();
    class Producer extends Thread{
        @Override
        public void run() {
            while (true){
                synchronized (queue){
                    while (queue.size() == SIZE){
                        queue.notify();
                        System.out.println("队列已满");
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.offer(1);
                    queue.notify();
                    System.out.println("生产一条消息，队列长度为"+queue.size());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    class Consumer extends Thread{
        @Override
        public void run() {
            while (true){
                synchronized (queue){
                    while (queue.size() == 0){
                        queue.notify();
                        System.out.println("队列为空");
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.poll();
                    queue.notify();
                    System.out.println("消费一条消息，队列长度为"+queue.size());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        ProducerAndConsumer pac = new ProducerAndConsumer();
        Producer producer = pac.new Producer();
        Consumer consumer = pac.new Consumer();
        producer.start();
        consumer.start();
    }
}
