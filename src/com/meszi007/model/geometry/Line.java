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
        this.start = start;
        this.end = end;
    }

    public Line getTransformBy(double x, double y){
        return new Line(start.getTransformBy(x,y),end.getTransformBy(x,y));
    }

    public Vector getAsVector(){
        return new Vector(getSlope(),getBalance());
    }

    public Line2D getAsLine2d(){return new Line2D.Float(this.start.x,this.start.y,this.end.x,this.end.y);}

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
                ", x2=" + start.y +
                ", y1=" + end.x +
                ", y2=" + end.y +
                '}';
    }
}
