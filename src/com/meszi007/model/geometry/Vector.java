package com.meszi007.model.geometry;

public class Vector {
    public final double x;
    public final double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector normVectorToLength(double length){
        double currentLength=Math.sqrt( Math.pow(x,2) + Math.pow(y,2));
        double multiplier=length/currentLength;
        return new Vector(x*multiplier, y*multiplier);
    }
}
