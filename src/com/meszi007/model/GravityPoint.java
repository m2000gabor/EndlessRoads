package com.meszi007.model;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class GravityPoint extends Point{
    private static final int radius=10;

    public GravityPoint(Point p){
        super(p.x,p.y);
    }

    public void paint(Graphics2D gr){
        paintPointAsGravityPoint(gr,this);
    }

    public static void paintPointAsGravityPoint(Graphics2D gr,Point p){
        gr.setColor(Color.BLUE);
        gr.fillOval(p.x-(radius/2),p.y-(radius/2),radius,radius);
    }

    public boolean includesPoint(int x, int y){
        java.awt.geom.Ellipse2D circle= new Ellipse2D.Float(this.x-radius,this.y-radius,radius*2,radius*2);
        return circle.contains(x,y);
    }
    public boolean includesPoint(Point p){return includesPoint(p.x,p.y);}

}
