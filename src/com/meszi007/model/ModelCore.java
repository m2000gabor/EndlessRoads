package com.meszi007.model;

import com.meszi007.model.road.Road;

import java.util.ArrayList;

public class ModelCore {
    public final ArrayList<Road> roads;
    public Road temporaryRoad=null;

    public ModelCore() {
        roads=new ArrayList<>();
        //roads.add(new Road(new Line(10,10,20,50),new Line(40,10,50,50)));
    }
}
