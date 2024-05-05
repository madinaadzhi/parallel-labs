package org.madi.lab5;

import lombok.AllArgsConstructor;

import java.util.Random;

@AllArgsConstructor
public class Producer extends Thread {
    private final Service service;

    @Override
    public void run() {
        Random random = new Random();
        long startTime = System.currentTimeMillis();
        long endTime = 0;

        while (endTime < 10000) {
            this.service.push(random.nextInt(100));
            try {
                Thread.sleep(random.nextInt(15));
            } catch (InterruptedException ignored) {
            }
            endTime = System.currentTimeMillis() - startTime;
        }
        service.isQueueOpen = false;
    }
}