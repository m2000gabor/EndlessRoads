package com.meszi007.model;

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

    public Point ifMove90(int l){
        int d=(int) Math.round(1/(Math.sqrt(2)))*l;
        return new Point(x+d,y);
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
}
