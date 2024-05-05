package org.madi.lab5;

public class Statistic extends Thread {
    private final Service service;
    private int sumQueuesLengths;
    private int i;

    public Statistic(Service service) {
        this.service = service;
        this.sumQueuesLengths = 0;
        this.i = 0;
    }

    @Override
    public void run() {
        while (service.isQueueOpen) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
            sumQueuesLengths += service.getCurrQueueLength();
            i++;
        }
    }

    public double getAvgQueueLength() {
        return sumQueuesLengths / (double) i;
    }
}
