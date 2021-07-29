package com.meszi007.model.road;

import com.meszi007.model.connections.Connection;
import com.meszi007.model.geometry.Line;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.Objects;

public abstract class Road extends RoadSurface{

    private Connection startConnection;
    private Connection endConnection;
    protected final ArrayList<Lane> lanes;

    public Road(@NotNull Line baseLine){
        this.baseLine=baseLine;
        this.lanes=new ArrayList<Lane>();
        setupEdges();
        setupLanes();
    }

    public abstract void setupLanes();

    @Override
    public Color getColor() {
        return Color.black;
    }

    public void changeBaseline(@NotNull Line baseLine){
        this.baseLine=baseLine;
        lanes.clear();
        setupEdges();
        setupLanes();
    }


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
        //g.fill(polygon);
        g.draw(polygon);
        Lane middle= findNoDashingLane(lanes);
        for(Lane l:lanes){ l.paint(g);
            if(Objects.nonNull(middle) && !l.equals(middle)){l.drawDashedLine(g);}
        }
        //drawArrows(g);
    }

    private static Lane findNoDashingLane(ArrayList<Lane> lanes){
        if(Objects.isNull(lanes) || lanes.isEmpty() || lanes.size()==1){return null;}

        /*
        One-way road: last one dont need.
        Two-way: when direction changes, that one doesnt need.
         */
        int i= 1;
        while(! lanes.get(i).getRightEdgeLine().start.equals(lanes.get(i-1).getRightEdgeLine().end)){
            if(i==lanes.size()-1){break;}
            i++;
        }

        return lanes.get(i);
    }


    public void setConnection(Connection c){
        if(c.getFocusPoint().includesPoint(this.baseLine.start)){
            startConnection=c;
        }else if(c.getFocusPoint().includesPoint(this.baseLine.end)){
            endConnection=c;
        }else{
            throw new IllegalStateException("This connection doesnt contains this road.");
        }
    }

    public Connection getStartConnection(){return startConnection;}
    public Connection getEndConnection(){return endConnection;}



    @Override
    public String toString() {
        return "Road{" +
                ", leftEdgeLine=" + leftEdgeLine +
                ", rightEdgeLine=" + rightEdgeLine +
                '}';
    }
}
