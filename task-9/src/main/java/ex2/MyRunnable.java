package main.java.ex2;

public class MyRunnable implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(500);
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
