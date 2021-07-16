package com.meszi007.model.road;

import com.meszi007.model.Line;
import com.meszi007.model.connections.Connection;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.util.Objects;

public class Road {
    public static final int BASIC_ROAD_WIDTH =5;
    public static final Color color= Color.black;

    @NotNull private Line baseLine;
    private Line leftEdgeLine;
    private Line rightEdgeLine;
    private Connection startConnection;
    private Connection endConnection;

    public Road(@NotNull Line baseLine){
        this.baseLine=baseLine;
        setupEdges();
    }

    private void setupEdges(){
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

    public void changeBaseline(@NotNull Line baseLine){
        this.baseLine=baseLine;
        setupEdges();
    }

    public boolean includePoint(int x, int y){
        return getAsPath().contains(new Point(x,y));
    };

    private Path2D getAsPath(){
        Path2D.Float p = new Path2D.Float();
        p.moveTo(leftEdgeLine.start.x,leftEdgeLine.start.y);
        p.lineTo(leftEdgeLine.end.x, leftEdgeLine.end.y);
        p.lineTo(rightEdgeLine.end.x,rightEdgeLine.end.y);
        p.lineTo(rightEdgeLine.start.x,rightEdgeLine.start.y);
        p.closePath();
        return p;
    }

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

    public Line getLeftEdgeLine() {
        return leftEdgeLine;
    }
    public Line getRightEdgeLine() {
        return rightEdgeLine;
    }

    public @NotNull Line getBaseLine() {
        return baseLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Road road = (Road) o;
        return baseLine.equals(road.baseLine) && Objects.equals(leftEdgeLine, road.leftEdgeLine) && Objects.equals(rightEdgeLine, road.rightEdgeLine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseLine, leftEdgeLine, rightEdgeLine);
    }

    @Override
    public String toString() {
        return "Road{" +
                ", leftEdgeLine=" + leftEdgeLine +
                ", rightEdgeLine=" + rightEdgeLine +
                '}';
    }
}
