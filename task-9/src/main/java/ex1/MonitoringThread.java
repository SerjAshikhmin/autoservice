package main.java.ex1;

public class MonitoringThread extends Thread {

    private Thread target;

    public MonitoringThread(Thread target) {
        this.target = target;
    }

    @Override
    public void run() {
        while (!target.isInterrupted()) {
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // target - TERMINATED
        System.out.println(target.getState());
    }

}
