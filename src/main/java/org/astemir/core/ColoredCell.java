package org.astemir.core;

import java.awt.*;

public class ColoredCell {

    private int color;
    private CellPoint position;

    public ColoredCell(int color, CellPoint position) {
        this.color = color;
        this.position = position;
    }

    public int getColor() {
        return color;
    }

    public CellPoint getPosition() {
        return position;
    }

    public int getDistance() {
        return distanceFromOrigin(position);
    }

    public void setColor(int color) {
        this.color = color;
    }

    private int distanceFromOrigin(CellPoint CellPoint) {
        return (int) Math.sqrt(Math.pow(CellPoint.getX(), 2) + Math.pow(CellPoint.getY(), 2));
    }
}
