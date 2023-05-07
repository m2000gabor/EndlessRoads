package com.meszi007.model.road;

import com.meszi007.model.geometry.Line;
import com.meszi007.model.geometry.LineIterator;
import com.meszi007.view.ArrowDrawer;
import com.meszi007.view.LineDrawer;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class Lane extends RoadSurface{
    public static final int LANE_WIDTH =10;

    public Lane(Line baseLine){
        this.baseLine=baseLine;
        setupEdges();
    }

    @Override
    public int getDefaultWidth(){return LANE_WIDTH;}


    private void drawAsLine(Graphics2D g){
        g.setColor(Color.RED);
        g.drawLine(leftEdgeLine.start.x,leftEdgeLine.start.y,leftEdgeLine.end.x,leftEdgeLine.end.y);
    }

    public void paint(Graphics graphics) {

        Graphics2D g=(Graphics2D) graphics;
        g.setColor(getColor());

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

        drawArrows(g);
        //drawDashedLine(g);
    }

    private void drawArrows(Graphics2D g){
        g.setColor(Color.ORANGE);
        LineIterator it = new LineIterator(getBaseLine());
        it.first();
        boolean odd=true;
        while(!it.end()){
            if(odd){g.fill(ArrowDrawer.createArrowShape(new Point(it.current().start.x,it.current().start.y),new Point(it.current().end.x,it.current().end.y)));}
            odd=!odd;
            it.next();
        }
    }

    protected void drawDashedLine(Graphics2D g){
        g.setColor(Color.WHITE);
        LineIterator it = new LineIterator(getRightEdgeLine());
        it.first();
        boolean odd=true;
        while(!it.end()){
            if(odd){g.fill(LineDrawer.getShape(it.current().start.x,it.current().start.y,it.current().end.x,it.current().end.y));}
            odd=!odd;
            it.next();
        }
    }


    @Override
        public String toString() {
            return "Lane{" +
                    ", leftEdgeLine=" + leftEdgeLine +
                    ", rightEdgeLine=" + rightEdgeLine +
                    '}';
        }
}
