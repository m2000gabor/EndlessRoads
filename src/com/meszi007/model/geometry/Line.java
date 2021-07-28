package com.meszi007.model.geometry;


import java.awt.geom.Line2D;

public class Line {
    final public Point start;
    final public Point end;

    public Line(int x1, int y1, int x2, int y2) {
        this.start=new Point(x1,y1);
        this.end=new Point(x2,y2);
    }

    public Line(Point start, Point end) {
        this.start = new Point(start);
        this.end = new Point(end);
    }

    public Line getTransformBy(double x, double y){
        return new Line(start.getTransformBy(x,y),end.getTransformBy(x,y));
    }

    public Line getReversed(){
        return new Line(end,start);
    }

    public Vector getAsVector(){
        return new Vector(end.x - start.x,end.y-start.y);
    }

    public Line2D getAsLine2d(){return new Line2D.Float(this.start.x,this.start.y,this.end.x,this.end.y);}

    public boolean contains(Point p){
        Line2D line2D = new Line2D.Float(this.start.x, this.start.y,this.end.x,this.end.y);
        return line2D.getBounds().contains(p.x, p.y);
    }

    /**
     * @return meredekség
     */
    public double getSlope(){
        if(end.y - start.y == 0){return 0;}
        return ((double)( end.y - start.y) )/ (end.x - start.x);
    }

    public double getBalance(){
        return start.y- (getSlope() * start.x);
    }

    @Override
    public String toString() {
        return "Line{" +
                "x1=" + start.x +
                ", y1=" + start.y +
                ", x2=" + end.x +
                ", y2=" + end.y +
                '}';
    }
}
