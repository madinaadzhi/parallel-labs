package org.madi.lab1.part3;

import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Counter cntrIncDec = new Counter();
        Runnable runnableIncDec =
                () -> {
                    run(cntrIncDec);
                };
        Thread threadIncDec = new Thread(runnableIncDec);
        threadIncDec.start();
        threadIncDec.join();
        System.out.println("Counter Inc Dec: " + cntrIncDec.getCnt());

        Counter cntrIncDecSyncMthd = new Counter();
        Runnable runnableIncDecSyncMthd =
                () -> {
                    try {
                        runSyncMthd(cntrIncDecSyncMthd);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                };
        Thread threadIncDecSyncMthd = new Thread(runnableIncDecSyncMthd);
        threadIncDecSyncMthd.start();
        threadIncDecSyncMthd.join();
        System.out.println("Counter Inc Dec Sync Method: " + cntrIncDecSyncMthd.getCnt());

        Counter cntrIncDecSyncBlock = new Counter();
        Runnable runnableIncDecSyncBlock =
                () -> {
                    try {
                        runSyncBlock(cntrIncDecSyncBlock);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                };
        Thread threadIncDecSyncBlock = new Thread(runnableIncDecSyncBlock);
        threadIncDecSyncBlock.start();
        threadIncDecSyncBlock.join();
        System.out.println("Counter Inc Dec Sync Block: " + cntrIncDecSyncBlock.getCnt());

        Counter cntrIncDecObjectLock = new Counter();
        Runnable runnableIncDecObjectLock =
                () -> {
                    try {
                        runObjectLock(cntrIncDecObjectLock);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                };
        Thread threadIncDecObjectLock = new Thread(runnableIncDecObjectLock);
        threadIncDecObjectLock.start();
        threadIncDecObjectLock.join();
        System.out.println("Counter Inc Dec Object Lock: " + cntrIncDecObjectLock.getCnt());
    }

    private static void runObjectLock(Counter counter) throws InterruptedException {
        int n = 100000;
        ReentrantLock reentrantLock = new ReentrantLock();
        Runnable runnableInc =
                () -> {
                    for (int i = 0; i < n; i++) {
                        reentrantLock.lock();
                        counter.inc();
                        reentrantLock.unlock();
                    }
                };
        Thread threadInc = new Thread(runnableInc);

        Runnable runnableDec =
                () -> {
                    for (int i = 0; i < n; i++) {
                        reentrantLock.lock();
                        counter.dec();
                        reentrantLock.unlock();
                    }
                };
        Thread threadDec = new Thread(runnableDec);

        threadInc.start();
        threadDec.start();
        threadInc.join();
        threadDec.join();
    }

    private static void runSyncBlock(Counter counter) throws InterruptedException {
        int n = 100000;
        Runnable runnableInc =
                () -> {
                    for (int i = 0; i < n; i++) {
                        counter.incSyncBlock();
                    }
                };
        Thread threadInc = new Thread(runnableInc);

        Runnable runnableDec =
                () -> {
                    for (int i = 0; i < n; i++) {
                        counter.decSyncBlock();
                    }
                };
        Thread threadDec = new Thread(runnableDec);

        threadInc.start();
        threadDec.start();
        threadInc.join();
        threadDec.join();
    }

    private static void runSyncMthd(Counter counter) throws InterruptedException {
        int n = 100000;
        Runnable runnableInc =
                () -> {
                    for (int i = 0; i < n; i++) {
                        counter.incSyncMethod();
                    }
                };
        Thread threadInc = new Thread(runnableInc);

        Runnable runnableDec =
                () -> {
                    for (int i = 0; i < n; i++) {
                        counter.decSyncMethod();
                    }
                };
        Thread threadDec = new Thread(runnableDec);

        threadInc.start();
        threadDec.start();
        threadInc.join();
        threadDec.join();
    }

    private static void run(Counter counter) {
        int n = 100000;
        Runnable runnableInc =
                () -> {
                    for (int i = 0; i < n; i++) {
                        counter.inc();
                    }
                };
        Thread threadInc = new Thread(runnableInc);

        Runnable runnableDec =
                () -> {
                    for (int i = 0; i < n; i++) {
                        counter.dec();
                    }
                };
        Thread threadDec = new Thread(runnableDec);

        threadInc.start();
        threadDec.start();
    }
}