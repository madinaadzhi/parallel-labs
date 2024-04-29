package org.madi.lab3.part1.version2;

import org.madi.lab3.part1.version1.Bank;
import org.madi.lab3.part1.version1.TransferThread;

public class AsynchBankTest {
    public static final int NACCOUNTS = 10;
    public static final int INITIAL_BALANCE = 10000;

    public static void main(String[] args) {
        org.madi.lab3.part1.version1.Bank b = new Bank(NACCOUNTS, INITIAL_BALANCE);
        int i;
        for (i = 0; i < NACCOUNTS; i++) {
            org.madi.lab3.part1.version1.TransferThread t = new TransferThread(b, i,
                    INITIAL_BALANCE);
            t.setPriority(Thread.NORM_PRIORITY + i % 2);
            t.start();
        }
    }
}
