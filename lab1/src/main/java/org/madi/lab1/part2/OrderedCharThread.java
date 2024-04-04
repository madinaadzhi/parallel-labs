package org.madi.lab1.part2;

public class OrderedCharThread extends Thread {
    private char ch;
    private static final Object lock = new Object();

    public OrderedCharThread(char ch) {
        this.ch = ch;
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                synchronized (lock) {
                    lock.notify();
                    System.out.print(this.ch);
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        System.exit(0);
    }
}
