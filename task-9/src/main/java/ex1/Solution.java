package main.java.ex1;

public class Solution {

    public static void main(String[] args) throws InterruptedException {
        Thread target = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    Thread.currentThread().join();
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
            }
        });

        MonitoringThread thread = new MonitoringThread(target);
        thread.start();
        // target - NEW
        System.out.println(target.getState());

        target.start();
        // target - RUNNABLE
        System.out.println(target.getState());

        Thread.sleep(100);
        // target - TIMED_WAITING
        System.out.println(target.getState());

        Thread.sleep(700);
        // target - WAITING
        System.out.println(target.getState());

        synchronized (target) {
            Thread.sleep(200);
            target.notify();
            // target - BLOCKED
            System.out.println(target.getState());
        }

        target.interrupt();
    }

}
