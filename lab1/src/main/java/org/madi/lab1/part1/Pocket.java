package org.madi.lab1.part1;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.Ellipse2D;
@Getter
@Setter
public class Pocket {
    private final Component canvas;
    @Getter
    private static final int RADIUS = 40;
    private static final int DIAMETER = RADIUS * 2;
    private int x = 0;
    private int y = 0;
    private final Color color;

    public Pocket(Component c, Color color) {
        this.canvas = c;
        this.color = color;
    }

    public Pocket(Component c, Color color, int x, int y) {
        this.canvas = c;
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.black);
        g2.fill(new Ellipse2D.Double(x - RADIUS, y - RADIUS, DIAMETER, DIAMETER));
    }
}
