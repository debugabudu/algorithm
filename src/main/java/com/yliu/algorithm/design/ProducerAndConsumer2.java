package com.yliu.algorithm.design;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class ProducerAndConsumer2 {
    private BlockingDeque<Integer> blockingDeque = new LinkedBlockingDeque<>();
    class Producer extends Thread{
        @Override
        public void run() {
            while (true){
                try {
                    blockingDeque.put(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("生产一条消息，队列长度为"+blockingDeque.size());
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
                try {
                    blockingDeque.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("消费一条消息，队列长度为"+blockingDeque.size());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ProducerAndConsumer2 pac = new ProducerAndConsumer2();
        Producer producer = pac.new Producer();
        Consumer consumer = pac.new Consumer();
        producer.start();
        consumer.start();
    }
}
