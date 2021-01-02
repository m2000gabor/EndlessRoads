package com.meszi007.model;

public class Point {
    public final int x;
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static double getDistance(Point a, Point b){
        return Math.sqrt(Math.pow((a.x-b.x),2)+Math.pow((a.y-b.y),2));
    }

    @Override
    public String toString() {
        return "["+x+","+y+"]";
    }
}
