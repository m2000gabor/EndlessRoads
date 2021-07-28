package com.meszi007.model.geometry;

import java.util.Objects;

public class Point {
    public final int x;
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(double x, double y) {
        this.x = (int) Math.round(x);
        this.y = (int) Math.round(y);
    }

    public Point getMoved(Vector v){
        return new Point(x+v.x,y+v.y);
    }

    public static double getDistance(Point a, Point b){
        return Math.sqrt(Math.pow((a.x-b.x),2)+Math.pow((a.y-b.y),2));
    }

    /**
     * Vectorral valo eltoltjanak megkapasa
     * @param x
     * @param y
     */
    public Point getTransformBy(double x,double y){
        return new Point(this.x+x,this.y+y);
    }

    @Override
    public String toString() {
        return "["+x+","+y+"]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
