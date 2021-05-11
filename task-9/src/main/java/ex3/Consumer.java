package main.java.ex3;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

    private BlockingQueue queue;

    public Consumer(BlockingQueue queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                Thread.sleep((int) (Math.random() * 600));
                String productName = (String) queue.take();
                System.out.println(String.format("Consumer take %s. There are %d items in stock", productName, queue.size()));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

