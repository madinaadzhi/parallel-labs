package org.madi.lab1.part2;

public class Main {
    public static void main(String[] args) throws InterruptedException {
//        CharThread charThread1 = new CharThread('|');
//        CharThread charThread2 = new CharThread('-');
        OrderedCharThread charThread1 = new OrderedCharThread('|');
        OrderedCharThread charThread2 = new OrderedCharThread('-');
        charThread1.start();
        charThread2.start();
        charThread1.join(200000);
        charThread2.join(200000);
        System.out.println("Main");
        System.out.println("Main");
        System.out.println("Main");
        System.out.println("Main");
    }
}
