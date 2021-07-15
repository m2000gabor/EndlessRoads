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
import java.util.Optional;

public class GameField extends JPanel {
    enum MouseMode{ROAD_PLACING_0,ROAD_PLACING_START_PLACED,DEMOLISHING,NONE};
    private final ModelCore modelCore;
    //private boolean roadStarted=false;
    private MouseMode mode=MouseMode.NONE;
    private Point startPoint=null;
    private Point endPoint=null;

    public GameField() {
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
                            startPoint = null;
                            endPoint = null;
                            modelCore.temporaryRoad = null;
                            mode = MouseMode.NONE;
                            break;
                        case NONE:
                            if(findObjectInLocation(e.getX(), e.getY()).isPresent()){
                                System.out.println("Van itt valami");
                            }else{
                                System.out.println("Nincs itt semmi");
                            }
                            break;
                        case DEMOLISHING:
                            Optional<Road> opt=findObjectInLocation(e.getX(), e.getY());
                            if(opt.isPresent()){
                                modelCore.roads.remove(opt.get());
                                System.out.println("A kivalasztott elem torolve");

                            }else{
                                System.out.println("Nincs mit torolni");
                            }
                            mode=MouseMode.NONE;
                            break;

                    }
                }else if (e.getButton()==MouseEvent.BUTTON3) {
                    if(mode==MouseMode.ROAD_PLACING_0 || mode==MouseMode.ROAD_PLACING_START_PLACED){
                        startPoint=null;
                        endPoint=null;
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

    private Optional<Road> findObjectInLocation(final int x, final int y) {
        return modelCore.roads.stream().filter(r -> r.includePoint(x,y)).findFirst();
    }

    private void startRoad(int x, int y){
        startPoint= new Point(x,y);
        endPoint= new Point(x,y);
        //System.out.println("Road started in point: " + startPoint);
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

        //System.out.println("Road ended in point: " + endPoint);
    }

    public void userInput(MainWindow.MenuInput in){
        switch (in){
            case NEW_BASIC_LANE:
                if(mode==MouseMode.NONE){mode=MouseMode.ROAD_PLACING_0;
                }else{
                    System.err.println("It's not in NONE mode, but only NONE mode can be changed!");
                }
                break;
            case DEMOLISH:
                if(mode==MouseMode.NONE){mode=MouseMode.DEMOLISHING;
                }else{
                    System.err.println("It's not in NONE mode, but only NONE mode can be changed!");
                }
                break;
            default: break;
        }
    }

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
