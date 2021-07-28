package com.meszi007.model.road;

import com.meszi007.model.geometry.Line;
import com.meszi007.model.geometry.LineIterator;
import com.meszi007.view.ArrowDrawer;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.util.Objects;

public class Lane{
    public static final Color color= Color.gray;
    public static final int LANE_WIDTH =5;

    @NotNull private Line baseLine;
    private Line leftEdgeLine;
    private Line rightEdgeLine;

    public Lane(@NotNull Line baseLine){
        this.baseLine=baseLine;
        setupEdges();
    }

    public int getDefaultWidth(){return LANE_WIDTH;}

    private void setupEdges(){
        double vec_x=baseLine.end.x-baseLine.start.x;
        double vec_y=baseLine.end.y-baseLine.start.y;
        double perp_x=-1*vec_y;
        double perp_y=vec_x;
        double ratio_to_norm_with = getDefaultWidth()/Math.sqrt(Math.pow(perp_x,2)+Math.pow(perp_y,2));
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

        drawArrows(g);
    }

    private void drawArrows(Graphics2D g){
        g.setColor(Color.ORANGE);
        LineIterator it = new LineIterator(getBaseLine());
        it.first();
        while(!it.end()){
            g.fill(ArrowDrawer.createArrowShape(new Point(it.current().start.x,it.current().start.y),new Point(it.current().end.x,it.current().end.y)));
            it.next();
        }
    }


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
        Lane lane = (Lane) o;
        return baseLine.equals(lane.baseLine) && Objects.equals(leftEdgeLine, lane.leftEdgeLine) && Objects.equals(rightEdgeLine, lane.rightEdgeLine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseLine, leftEdgeLine, rightEdgeLine);
    }

    @Override
        public String toString() {
            return "Lane{" +
                    ", leftEdgeLine=" + leftEdgeLine +
                    ", rightEdgeLine=" + rightEdgeLine +
                    '}';
        }
}
