package com.meszi007.view;

import com.meszi007.model.ModelCore;
import com.meszi007.model.geometry.GravityPoint;
import com.meszi007.model.geometry.Line;
import com.meszi007.model.geometry.Point;
import com.meszi007.model.road.OneLaneRoad;
import com.meszi007.model.road.OneOneRoad;
import com.meszi007.model.road.Road;
import com.meszi007.model.road.TwoTwoRoad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Objects;
import java.util.Optional;

public class GameField extends JPanel {
    enum MouseMode{ROAD_PLACING_0,ROAD_PLACING_START_PLACED,DEMOLISHING,NONE}
    private final ModelCore modelCore;
    private MouseMode mode=MouseMode.NONE;
    private MenuInput roadType;

    public GameField() {
        setBackground(Color.white);
        modelCore=new ModelCore();

        setPreferredSize(new Dimension(500,500));

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(e.getButton()==MouseEvent.BUTTON1){
                    switch (mode) {
                        case ROAD_PLACING_0:
                            startRoad(e.getX(), e.getY());
                            mode = MouseMode.ROAD_PLACING_START_PLACED;
                            break;
                        case ROAD_PLACING_START_PLACED:
                            finishRoad(e.getX(), e.getY());
                            modelCore.temporaryRoad = null;
                            mode = MouseMode.ROAD_PLACING_0;
                            break;
                        case NONE:
                            if(findGPInLocation(e.getX(), e.getY()).isPresent()){
                                if(modelCore.connections.stream().anyMatch(c -> c.getFocusPoint().includesPoint(new Point(e.getX(),e.getY()))) ){
                                    System.out.println("Its a connection");
                                }else{
                                    System.out.println("Its a GravityPoint");
                                }

                            }else if(findRoadInLocation(e.getX(), e.getY()).isPresent()){
                                System.out.println("Its a road");
                            }else{
                                System.out.println("Nothing found in this point");
                            }

                            break;
                        case DEMOLISHING:
                            Optional<Road> opt= findRoadInLocation(e.getX(), e.getY());
                            if(opt.isPresent()){
                                modelCore.roads.remove(opt.get());
                                System.out.println("selected road deleted");

                            }else{
                                System.out.println("Nothing to delete");
                            }
                            mode=MouseMode.DEMOLISHING;
                            break;

                    }
                }else if (e.getButton()==MouseEvent.BUTTON3) {
                    if(mode==MouseMode.ROAD_PLACING_0 || mode==MouseMode.ROAD_PLACING_START_PLACED){
                        modelCore.temporaryRoad=null;
                        mode=MouseMode.NONE;
                    }

                }

            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                if(mode==MouseMode.ROAD_PLACING_START_PLACED){
                    changeTemporaryShadow(e.getX(), e.getY());
                }
            }
        });

        setVisible(true);
    }

    private Optional<Road> findRoadInLocation(final int x, final int y) {
        return modelCore.roads.stream().filter(r -> r.includePoint(x,y)).findFirst();
    }

    private Optional<Road> findGPInLocation(final int x, final int y) {
        return modelCore.roads.stream().filter(r -> new GravityPoint(r.getBaseLine().start).includesPoint(x,y) ||
                new GravityPoint(r.getBaseLine().end).includesPoint(x,y) ).findFirst();
    }

    private void startRoad(int x, int y){
        Point startPoint=null;
        Road r=null;
        for(int i=0;i<modelCore.roads.size();i++){
            r=modelCore.roads.get(i);
            if(new GravityPoint(r.getBaseLine().start).includesPoint(x,y)){
                startPoint= r.getBaseLine().start;break;
            }else if(new GravityPoint(r.getBaseLine().end).includesPoint(x,y)){
                startPoint= r.getBaseLine().end;break;
            }
        }

        if(Objects.isNull(startPoint)){
            startPoint=new Point(x,y);
            modelCore.temporaryRoad=createAppropriateRoadType(roadType,startPoint,startPoint);
        }else{
            modelCore.temporaryRoad=createAppropriateRoadType(roadType,startPoint,startPoint);
            modelCore.createJunction(modelCore.temporaryRoad,r);
            System.out.println("Road start connected");
        }
        //System.out.println("Road started in point: " + startPoint);
    }

    private static Road createAppropriateRoadType(MenuInput roadType,Point start, Point end){
        if(!roadType.isRoad()) {throw new IllegalStateException("Only roads can be built");}
        switch (roadType.getState()){
            case ONE_ONE_ROAD:
                return new OneOneRoad(new Line(start,end));
            case NEW_BASIC_LANE:
                return new OneLaneRoad(new Line(start,end));
            case TWO_TWO_ROAD:
                return new TwoTwoRoad(new Line(start,end));
            default:
                throw new IllegalStateException("Cannot build this type of road. Not implemented yet.");
        }
    }

    private void changeTemporaryShadow(int x, int y){
        Point endPoint=new Point(x,y);
        modelCore.temporaryRoad.changeBaseline(new Line(modelCore.temporaryRoad.getBaseLine().start,endPoint));
    }

    private void finishRoad(int x, int y){
        //search for junction
        Point endPoint=null;
        Road r=null;
        for(int i=0;i<modelCore.roads.size();i++){
            r=modelCore.roads.get(i);
            if(new GravityPoint(r.getBaseLine().start).includesPoint(x,y)){
                endPoint= r.getBaseLine().start;break;
            }else if(new GravityPoint(r.getBaseLine().end).includesPoint(x,y)){
                endPoint=r.getBaseLine().end;break;
            }
        }

        if(Objects.isNull(endPoint)){
            endPoint=new Point(x,y);
            modelCore.temporaryRoad.changeBaseline(new Line(modelCore.temporaryRoad.getBaseLine().start,endPoint));
        }else{
            modelCore.temporaryRoad.changeBaseline(new Line(modelCore.temporaryRoad.getBaseLine().start,endPoint));
            modelCore.createJunction(modelCore.temporaryRoad,r);
            System.out.println("Road end connected");
        }

        modelCore.roads.add(modelCore.temporaryRoad);

        //System.out.println("Road ended in point: " + endPoint);
    }

    public void userInput(MenuInput in){
        if (in.isRoad()) {
            if (mode == MouseMode.NONE || mode== MouseMode.ROAD_PLACING_0) {
                mode = MouseMode.ROAD_PLACING_0;
                roadType = in;
            } else {
                System.err.println("This mode cannot be changed!");
            }
        } else if (in.isDemolish()) {
            if (mode == MouseMode.NONE|| mode== MouseMode.ROAD_PLACING_0) {
                mode = MouseMode.DEMOLISHING;
            } else {
                System.err.println("This mode cannot be changed!");
            }
        }else{
            modelCore.temporaryRoad=null;
            mode = MouseMode.NONE;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        super.paintComponent(graphics);
        //graphics.drawLine(10,10,40,40);
        //System.out.println(modelCore.roads.size());
        for(Road r : modelCore.roads){
            r.paint(graphics);
            GravityPoint.paintPointAsGravityPoint(graphics,r.getBaseLine().start);
            GravityPoint.paintPointAsGravityPoint(graphics,r.getBaseLine().end);
        }
        if(Objects.nonNull(modelCore.temporaryRoad)){
            modelCore.temporaryRoad.paint(graphics);
        }

        //for (Connection c : modelCore.connections){c.paint(graphics);}
    }
}
