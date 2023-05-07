package com.meszi007.model.road;

import com.meszi007.model.geometry.Line;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.Objects;

public abstract class RoadSurface {
    protected Line baseLine;
    protected Line leftEdgeLine;
    protected Line rightEdgeLine;

    public Line getLeftEdgeLine() {
        return leftEdgeLine;
    }
    public Line getRightEdgeLine() {
        return rightEdgeLine;
    }

    public Line getBaseLine() {
        return baseLine;
    }

    public abstract int getDefaultWidth();

    protected void setupEdges(){
        double vec_x=baseLine.end.x-baseLine.start.x;
        double vec_y=baseLine.end.y-baseLine.start.y;
        double perp_x=-1*vec_y;
        double perp_y=vec_x;
        double ratio_to_norm_with = (getDefaultWidth()/2.0)/Math.sqrt(Math.pow(perp_x,2)+Math.pow(perp_y,2));
        perp_x*=ratio_to_norm_with;
        perp_y*=ratio_to_norm_with;
        leftEdgeLine = baseLine.getTransformBy(perp_x,perp_y);
        rightEdgeLine= baseLine.getTransformBy(-1*perp_x,-1*perp_y);
    }

    public Color getColor(){return Color.gray;}

    protected Path2D getAsPath(){
        Path2D.Float p = new Path2D.Float();
        p.moveTo(leftEdgeLine.start.x,leftEdgeLine.start.y);
        p.lineTo(leftEdgeLine.end.x, leftEdgeLine.end.y);
        p.lineTo(rightEdgeLine.end.x,rightEdgeLine.end.y);
        p.lineTo(rightEdgeLine.start.x,rightEdgeLine.start.y);
        p.closePath();
        return p;
    }

    public boolean includePoint(int x, int y){
        return getAsPath().contains(new Point(x,y));
    }

    protected boolean hasNoWidth(){
        return leftEdgeLine.start.x == rightEdgeLine.start.x && leftEdgeLine.end.x==rightEdgeLine.end.x &&
                leftEdgeLine.start.y == rightEdgeLine.start.y && leftEdgeLine.end.y==rightEdgeLine.end.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoadSurface road = (RoadSurface) o;
        return baseLine.equals(road.baseLine) && Objects.equals(leftEdgeLine, road.leftEdgeLine) && Objects.equals(rightEdgeLine, road.rightEdgeLine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseLine, leftEdgeLine, rightEdgeLine);
    }

}
