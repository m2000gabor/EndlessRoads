package com.meszi007.model;

import com.meszi007.model.road.Road;

import java.util.ArrayList;

public class ModelCore {
    public final ArrayList<Road> roads;

    public ModelCore() {
        roads=new ArrayList<>();

        //init
        /*
        RoadSlice leftEdge=new RoadEdge(new Line());
        RoadSlice rightEdge=new RoadEdge();
        RoadSlice leftLane=new BasicRoadLane();
        RoadSlice rightLane=new BasicRoadLane();
        Line bsLine=new Line(0,10,0,10);
        Road r=new Road(new RoadSlice[]{leftEdge,leftLane,rightLane,rightEdge},bsLine);
        roads.add(r);*/
        roads.add(new Road(new Line(10,10,20,50),new Line(20,10,30,50)));
    }
}
