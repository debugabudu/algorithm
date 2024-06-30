package com.yliu.algorithm.design;

import lombok.Setter;

public class Producer extends Thread {
    @Setter
    private int num;
    private Storage storage;
    public Producer(Storage storage) {
        this.storage = storage;
    }

    public void run() {
        storage.produce(this.num);
    }
}
