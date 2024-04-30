package org.madi.lab3.part3;

import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Journal journal = new Journal();

        Runnable r = new Runnable() {
            @Override
            public void run() {
                (new Thread(new Lecturer("Lecturer 1", Arrays.asList("ІP-13", "ІP-14", "ІP-15"), 3, journal))).start();
                (new Thread(new Lecturer("Assistant 1", Arrays.asList("ІP-13", "ІP-14", "ІP-15"), 3, journal))).start();
                (new Thread(new Lecturer("Assistant 2", Arrays.asList("ІP-13", "ІP-14", "ІP-15"), 3, journal))).start();
                (new Thread(new Lecturer("Assistant 3", Arrays.asList("ІP-13", "ІP-14", "ІP-15"), 3, journal))).start();
            }
        };
        Thread t = new Thread(r);
        t.start();
        t.join();
        journal.print();
    }
}