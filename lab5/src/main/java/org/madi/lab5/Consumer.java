package org.madi.lab5;

import lombok.AllArgsConstructor;

import java.util.Random;

@AllArgsConstructor
public class Consumer extends Thread {
    private final Service service;

    @Override
    public void run() {
        Random random = new Random();
        while (service.isQueueOpen) {
            service.pop();
            try {
                Thread.sleep(random.nextInt(100));
            } catch (InterruptedException ignored) {
            }
            service.incApprovedCnt();
        }
    }
}