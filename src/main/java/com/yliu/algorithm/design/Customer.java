package com.yliu.algorithm.design;

import lombok.Setter;

public class Customer extends Thread{
    @Setter
    private int num;
    private Storage storage;
    public Customer(Storage storage) {
        this.storage = storage;
    }

    public void run() {
        storage.consume(this.num);
    }
}
