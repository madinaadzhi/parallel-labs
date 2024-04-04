package org.madi.lab1.part1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BallCanvas extends JPanel {
    private final ArrayList<Ball> balls = new ArrayList<>();
    private final ArrayList<Pocket> pockets = new ArrayList<>();

    public void add(Ball b) {
        this.balls.add(b);
    }

    public void add(Pocket p) {
        this.pockets.add(p);
    }

    public void remove(Ball b) {
        Score.increase();
        this.balls.remove(b);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        try {
            for (Ball b : balls) {
                for (Pocket p : pockets) {
                    if (b.isInPocket(p)) {
                        b.isInPocket = true;
                        remove(b);
                        break;
                    }
                }
                if (!b.isInPocket) {
                    b.draw(g2);
                }
            }
        } catch (Exception ignored) {
        }
        for (Pocket p : pockets) {
            p.draw(g2);
        }
    }
}
