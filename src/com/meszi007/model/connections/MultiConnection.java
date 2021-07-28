package com.meszi007.model.connections;

import com.meszi007.model.geometry.GravityPoint;
import com.meszi007.model.geometry.Point;
import com.meszi007.model.road.Road;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.Arrays;

public class MultiConnection implements Connection{
    private final ArrayList<Road> roadList;
    private final GravityPoint focusPoint;

    private MultiConnection(Point focusPoint, Road... roads) {
        this.focusPoint=new GravityPoint(focusPoint);
        roadList = new ArrayList<Road>(Arrays.asList(roads));
    }

    public static MultiConnection getInstance(Point focusPoint, Road... roads){
        MultiConnection multiConnection = new MultiConnection(focusPoint,roads);
        multiConnection.roadList.forEach(r -> r.setConnection(multiConnection));
        return multiConnection;
    }

    /*
    @Override
    public void paint(Graphics2D gr) {
        if(roadList.isEmpty()) return;
        gr.setColor(Color.PINK);

        // draw GeneralPath (polygon)
        //int[] x1Points = {leftEdgeLine.start.x, leftEdgeLine.end.x, rightEdgeLine.end.x,rightEdgeLine.start.x};
        //int[] y1Points = {leftEdgeLine.start.y, leftEdgeLine.end.y, rightEdgeLine.end.y,rightEdgeLine.start.y};

        GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD,roadList.size());

        if(roadList.get(0).getStartConnection()==this){
            polygon.moveTo(roadList.get(0).getLeftEdgeLine().start.x, roadList.get(0).getLeftEdgeLine().start.y);
            polygon.lineTo(roadList.get(0).getRightEdgeLine().start.x, roadList.get(0).getRightEdgeLine().start.y);
        }else if(roadList.get(0).getEndConnection()==this){
            polygon.moveTo(roadList.get(0).getLeftEdgeLine().end.x, roadList.get(0).getLeftEdgeLine().end.y);
            polygon.lineTo(roadList.get(0).getRightEdgeLine().end.x, roadList.get(0).getRightEdgeLine().end.y);
        }else{
            throw new RuntimeException("This connection include neither the end nor the start of this road. Why is this road added?");
        }

        for (int i = 1; i < roadList.size(); i++) {
            if(roadList.get(i).getStartConnection()==this){
                polygon.lineTo(roadList.get(i).getLeftEdgeLine().start.x, roadList.get(i).getLeftEdgeLine().start.y);
                polygon.lineTo(roadList.get(i).getRightEdgeLine().start.x, roadList.get(i).getRightEdgeLine().start.y);
            }else if(roadList.get(i).getEndConnection()==this){
                polygon.lineTo(roadList.get(i).getLeftEdgeLine().end.x, roadList.get(i).getLeftEdgeLine().end.y);
                polygon.lineTo(roadList.get(i).getRightEdgeLine().end.x, roadList.get(i).getRightEdgeLine().end.y);
            }else{
                throw new RuntimeException("This connection include neither the end nor the start of this road. Why is this road added?");
            }
        }

        polygon.closePath();
        gr.fill(polygon);
    }*/

    @Override
    public void paint(Graphics2D gr) {
        if(roadList.isEmpty()) return;
        gr.setColor(Color.PINK);

        // draw GeneralPath (polygon)
        ArrayList<Point> affectedPoints=new ArrayList<>();
        for (Road r: roadList){
            if(r.getStartConnection()==this){
                affectedPoints.add(r.getLeftEdgeLine().start);
                affectedPoints.add(r.getRightEdgeLine().start);
            }else if(r.getEndConnection()==this){
                affectedPoints.add(r.getLeftEdgeLine().end);
                affectedPoints.add(r.getRightEdgeLine().end);
            }else{
                throw new RuntimeException("This connection include neither the end nor the start of this road. Why is this road added?");
            }
        }

        //todo sort the points counterclockwise
        //include points where
        /*
        Iterator<Point> pointIterator = affectedPoints.stream().sorted(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                Point2D a= new Point2D.Float(o1.x,o1.y);
                Point2D b= new Point2D.Float(o2.x,o2.y);
                return o1.x-o2.x;
            }
        }).iterator();*/

        GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD,roadList.size()*2);

        polygon.moveTo(affectedPoints.get(0).x,affectedPoints.get(0).y);
        for (int i = 1; i < affectedPoints.size(); i++) {
             polygon.lineTo(affectedPoints.get(i).x,affectedPoints.get(i).y);
        }

        polygon.closePath();
        gr.fill(polygon);
    }

    @Override
    public boolean contains(Road road) {
        return roadList.contains(road);
    }

    @Override
    public GravityPoint getFocusPoint() {
        return focusPoint;
    }

    @Override
    public MultiConnection upgrade(Road road) {
        roadList.add(road);
        return getInstance(getFocusPoint(),roadList.toArray(new Road[]{}) );
    }
}
