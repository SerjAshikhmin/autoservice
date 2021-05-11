package main.java.ex4;

import java.time.LocalTime;
import java.util.Date;

public class ServiceThread implements Runnable {

    private final long timeInSecInterval;

    public ServiceThread(long timeInSecInterval) {
        this.timeInSecInterval = timeInSecInterval;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(timeInSecInterval * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(LocalTime.now());
        }
    }
}
