package org.madi.lab5;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Spectator extends Thread {
    private Service service;

    @Override
    public void run() {
        while (service.isQueueOpen) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
            System.out.println("Queue size: " + service.getCurrQueueLength() + "; fail probability: " + Math.round(service.calcRejectedPercentage() * 100.0) / 100.0);
        }
    }
}
