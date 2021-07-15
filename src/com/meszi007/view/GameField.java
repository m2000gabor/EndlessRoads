package com.meszi007.view;

import com.meszi007.model.Line;
import com.meszi007.model.ModelCore;
import com.meszi007.model.Point;
import com.meszi007.model.road.Road;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Objects;

public class GameField extends JPanel {
    private final ModelCore modelCore;
    private boolean roadStarted=false;
    private Point startPoint=null;
    private Point endPoint=null;

    public GameField() {
        modelCore=new ModelCore();

        setPreferredSize(new Dimension(500,500));

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(e.getButton()==MouseEvent.BUTTON1){
                    if(!roadStarted){
                        startRoad(e.getX(),e.getY());
                    }else{
                        finishRoad(e.getX(),e.getY());
                        startPoint=null;
                        endPoint=null;
                        modelCore.temporaryRoad=null;
                    }
                    roadStarted=!roadStarted;
                }else if (e.getButton()==MouseEvent.BUTTON3){
                    roadStarted=false;
                    startPoint=null;
                    endPoint=null;
                    modelCore.temporaryRoad=null;
                }

            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                if(roadStarted){
                    changeTemporaryShadow(e.getX(), e.getY());
                }
            }
        });

        setVisible(true);
    }

    private void startRoad(int x, int y){
        startPoint= new Point(x,y);
        endPoint= new Point(x,y);
        System.out.println("Road started in point: " + startPoint);
    }

    private void changeTemporaryShadow(int x, int y){
        endPoint=new Point(x,y);
        Line baseLine=new Line(startPoint,endPoint);
        modelCore.temporaryRoad=new Road(baseLine);
    }

    private void finishRoad(int x, int y){
        endPoint=new Point(x,y);
        Line baseLine=new Line(startPoint,endPoint);
        modelCore.roads.add(new Road(baseLine));

        /*
        double a=-1/baseLine.getSlope();
        double b=endPoint.y-a*endPoint.x;
        int BASIC_ROAD_WIDTH=10;

        double eltolasMerteke=Math.sqrt(BASIC_ROAD_WIDTH / (1+Math.pow(a,2)) );

        System.err.println("Eltolas merteke: "+eltolasMerteke );
        Line leftEdge = baseLine.getTransformBy(eltolasMerteke,eltolasMerteke*a);
        Line rightEdge=baseLine;

        Line perpendicular= new Line(new Point(endPoint.x,a*endPoint.x+b),new Point(endPoint.x+eltolasMerteke,a*(endPoint.x+eltolasMerteke)+b));
        Road r2= new Road(perpendicular,perpendicular,Color.RED);
        modelCore.roads.add(r2);

        System.out.println("Baseline: " + baseLine);
        System.out.println("Perpendicular: " + perpendicular);
        System.out.println("Left edge: " + leftEdge);
        System.out.println("Right edge: " + rightEdge);

        Road r= new Road(leftEdge,rightEdge);
        modelCore.roads.add(r);*/

        System.out.println("Road ended in point: " + endPoint);
    }

    /*
    private void createRoad(int x, int y){
        System.out.println(x+","+y);
        if(roadStarted){
            Point endPoint=new Point(x,y);
            Line left=new Line(startPoint,endPoint);
            Road r= new Road(left,left.ifMove90(Road.DEFAULT_WIDTH));
            modelCore.roads.add(r);

            add(r);
            System.out.println(r);
            roadStarted=false;
            System.out.println("Road ended");

        }else{
            startPoint=new Point(x,y);
            roadStarted=true;
            System.out.println("Road started");
        }
        revalidate();
        repaint();
    }*/


    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        //graphics.drawLine(10,10,40,40);
        //System.out.println(modelCore.roads.size());
        for(Road r : modelCore.roads){
            r.paint(graphics);
        }
        if(Objects.nonNull(modelCore.temporaryRoad)){
            modelCore.temporaryRoad.paint(graphics);
        }
    }
}
