package main.java.ex2;

public class Solution {

    public static void main(String[] args) throws InterruptedException {
        Thread firstThread = new Thread(new MyRunnable());
        Thread secondThread = new Thread(new MyRunnable());

        firstThread.start();
        Thread.sleep(500);
        secondThread.start();
    }
}
