package com.meszi007.model;

public class Line {
    final public Point start;
    final public Point end;

    public Line(int x1, int y1, int x2, int y2) {
        this.start=new Point(x1,y1);
        this.end=new Point(x2,y2);
    }

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }


    @Override
    public String toString() {
        return "Line{" +
                "x1=" + start.x +
                ", x2=" + start.y +
                ", y1=" + end.x +
                ", y2=" + end.y +
                '}';
    }
}
