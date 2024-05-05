package org.madi.lab5;

import java.util.ArrayDeque;
import java.util.Queue;

public class Service {
    private int rejectCnt;
    private int approveCnt;
    private final Queue<Integer> queue;
    public boolean isQueueOpen;

    public Service() {
        this.rejectCnt = 0;
        this.approveCnt = 0;
        this.isQueueOpen = true;
        this.queue = new ArrayDeque<>();
    }

    public synchronized void push(int item) {
        int queueSize = 3;
        if (queue.size() >= queueSize) {
            rejectCnt++;
        }
        queue.add(item);
        notifyAll();
    }

    public synchronized void pop() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
        queue.poll();
    }

    public synchronized void incApprovedCnt() {
        approveCnt++;
    }

    public double calcRejectedPercentage() {
        return rejectCnt / (double) (rejectCnt + approveCnt);
    }

    public synchronized int getCurrQueueLength() {
        return queue.size();
    }
}