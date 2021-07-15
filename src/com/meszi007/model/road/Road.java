package com.meszi007.model.road;

import com.meszi007.model.Line;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.util.List;

public class Road {
    public List<RoadSlice> roadSlices;
    public Line leftEdgeLine;
    public Line rightEdgeLine;
    public static int BASIC_ROAD_WIDTH =5;
    public Color color= Color.black;

    public Road(Line leftEdgeLine, Line rightEdgeLine) {
        this.leftEdgeLine = leftEdgeLine;
        this.rightEdgeLine = rightEdgeLine;
    }
    public Road(Line leftEdgeLine, Line rightEdgeLine,Color c) {
        this.leftEdgeLine = leftEdgeLine;
        this.rightEdgeLine = rightEdgeLine;
        this.color=c;
    }

    /*
    public Road(Line baseLine){
        double a;
        double eltolasMerteke;
        if(baseLine.getSlope()==0){
            //a=1;
            eltolasMerteke=BASIC_ROAD_WIDTH;
            //System.out.println("Eltolas merteke: "+eltolasMerteke );
            leftEdgeLine = baseLine.getTransformBy(0,eltolasMerteke);
            rightEdgeLine= baseLine.getTransformBy(0,-1*eltolasMerteke);
        }else{
            a=-1/baseLine.getSlope();
            //double b=baseLine.end.y-a*baseLine.end.x;
            eltolasMerteke=Math.sqrt( (double) BASIC_ROAD_WIDTH / (1+Math.pow(a,2)) );
            //System.out.println("Eltolas merteke: "+eltolasMerteke );
            leftEdgeLine = baseLine.getTransformBy(eltolasMerteke,eltolasMerteke*a);
            rightEdgeLine= baseLine.getTransformBy(-1*eltolasMerteke,-1*eltolasMerteke*a);
        }
    }
    */
    public Road(Line baseLine){
        double vec_x=baseLine.end.x-baseLine.start.x;
        double vec_y=baseLine.end.y-baseLine.start.y;
        double perp_x=-1*vec_y;
        double perp_y=vec_x;
        double ratio_to_norm_with = BASIC_ROAD_WIDTH/Math.sqrt(Math.pow(perp_x,2)+Math.pow(perp_y,2));
        perp_x*=ratio_to_norm_with;
        perp_y*=ratio_to_norm_with;
        leftEdgeLine = baseLine.getTransformBy(perp_x,perp_y);
        rightEdgeLine= baseLine.getTransformBy(-1*perp_x,-1*perp_y);
    }

    /*
    public Dimension getPreferredSize() {
        return new Dimension((int) Point.getDistance(leftEdgeLine.start,leftEdgeLine.end),(int) Point.getDistance(rightEdgeLine.start,rightEdgeLine.end));
    }*/

    private boolean hasNoWidth(){
        return leftEdgeLine.start.x == rightEdgeLine.start.x && leftEdgeLine.end.x==rightEdgeLine.end.x &&
                leftEdgeLine.start.y == rightEdgeLine.start.y && leftEdgeLine.end.y==rightEdgeLine.end.y;
    }

    private void drawAsLine(Graphics2D g){
        g.setColor(Color.RED);
        g.drawLine(leftEdgeLine.start.x,leftEdgeLine.start.y,leftEdgeLine.end.x,leftEdgeLine.end.y);
    }

    public void paint(Graphics graphics) {

        Graphics2D g=(Graphics2D) graphics;
        g.setColor(color);

        // draw GeneralPath (polygon)
        int[] x1Points = {leftEdgeLine.start.x, leftEdgeLine.end.x, rightEdgeLine.end.x,rightEdgeLine.start.x};
        int[] y1Points = {leftEdgeLine.start.y, leftEdgeLine.end.y, rightEdgeLine.end.y,rightEdgeLine.start.y};

        if(hasNoWidth()){drawAsLine(g);return;}

        GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD,x1Points.length);
        polygon.moveTo(x1Points[0], y1Points[0]);

        for (int index = 1; index < x1Points.length; index++) {
            polygon.lineTo(x1Points[index], y1Points[index]);
        }

        polygon.closePath();
        g.fill(polygon);
    }

    @Override
    public String toString() {
        return "Road{" +
                "roadSlices=" + roadSlices +
                ", leftEdgeLine=" + leftEdgeLine +
                ", rightEdgeLine=" + rightEdgeLine +
                '}';
    }
}
