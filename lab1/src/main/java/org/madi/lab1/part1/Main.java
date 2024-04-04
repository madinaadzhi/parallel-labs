package org.madi.lab1.part1;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BounceFrame bounceFrame = new BounceFrame();
        bounceFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bounceFrame.setVisible(true);
        System.out.println("Thread name = " + Thread.currentThread().getName());
    }
}