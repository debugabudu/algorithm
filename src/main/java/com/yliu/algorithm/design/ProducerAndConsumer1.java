package com.yliu.algorithm.design;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerAndConsumer1 {
    private final int SIZE = 10;
    private Queue<Integer> queue = new LinkedList<>();
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    class Producer extends Thread{
        @Override
        public void run() {
            while (true){
                lock.lock();
                while (queue.size() == SIZE){
                    System.out.println("队列已满");
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                queue.offer(1);
                condition.signal();
                System.out.println("生产一条消息，队列长度为"+queue.size());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    class Consumer extends Thread{
        @Override
        public void run() {
            while (true){
                lock.lock();
                while (queue.size() == 0){
                    System.out.println("队列为空");
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                queue.poll();
                condition.signal();
                System.out.println("消费一条消息，队列长度为"+queue.size());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ProducerAndConsumer1 pac = new ProducerAndConsumer1();
        Producer producer = pac.new Producer();
        Consumer consumer = pac.new Consumer();
        producer.start();
        consumer.start();
    }
}
