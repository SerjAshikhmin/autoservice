package main.java.ex3;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

    private BlockingQueue queue;

    public Producer(BlockingQueue queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            int i = 0;
            while (!Thread.interrupted()) {
                String productName = String.valueOf(String.format("Product № %d", ++i));
                synchronized (queue) {
                    queue.put(productName);
                    System.out.println(String.format("Producer add %s. There are %d items in stock", productName, queue.size()));
                }
                Thread.sleep((int) (Math.random() * 600));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

