package org.madi.lab1.part2;

public class CharThread extends Thread {
    private char ch;

    public CharThread(char ch) {
        this.ch = ch;
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                System.out.print(this.ch);
            }
            System.out.println();
        }
    }
}
