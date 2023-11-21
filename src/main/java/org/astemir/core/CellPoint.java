package org.astemir.core;

import java.awt.*;
import java.util.Comparator;

public class CellPoint implements Comparable<CellPoint>{

    public int x = 0;
    public int y = 0;


    public CellPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getInvertedY(){return -getY();}

    public void setY(int y) {
        this.y = y;
    }



    @Override
    public int compareTo(CellPoint o) {
        return Comparator.comparingInt(CellPoint::getY).thenComparingInt(CellPoint::getX).compare(this,o);
    }

    public Point toPoint(){
        return new Point(x,y);
    }
}
