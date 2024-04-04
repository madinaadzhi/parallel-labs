package org.madi.lab1.part1;

import lombok.Setter;

@Setter
public class BallThread extends Thread {
    private final Ball ball;
    private int TIME = 10000;

    public BallThread(Ball ball) {
        this.ball = ball;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i < TIME; i++) {
                ball.move();
                if (ball.isInPocket) {
                    // Thread.currentThread().interrupt();
                    break;
                }
                System.out.println("Thread name = " + Thread.currentThread().getName());
                Thread.sleep(5);
            }
        } catch (InterruptedException ignored) {
            System.out.println("Thread " + Thread.currentThread().getName() + " was interrupted!");
        }
    }
}
