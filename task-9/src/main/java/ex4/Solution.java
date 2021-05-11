package main.java.ex4;

public class Solution {

    public static void main(String[] args) {
        ServiceThread serviceThread = new ServiceThread(1);
        Thread thread = new Thread(serviceThread);
        thread.setDaemon(true);
        thread.start();
        while (true) {}
    }
}
