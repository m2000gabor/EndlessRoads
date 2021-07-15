package com.meszi007.model;

import com.meszi007.model.connections.BiConnection;
import com.meszi007.model.connections.Connection;
import com.meszi007.model.road.Road;

import java.util.ArrayList;
import java.util.Objects;

public class ModelCore {
    public final ArrayList<Road> roads;
    public final ArrayList<Connection> connections;
    public Road temporaryRoad=null;

    public ModelCore() {
        roads=new ArrayList<>();
        connections=new ArrayList<>();
    }

    public void createJunction(Road r1,Road r2){
        Point p=null;
        if(r1.getBaseLine().start == r2.getBaseLine().start || r1.getBaseLine().start == r2.getBaseLine().end){
            p=r1.getBaseLine().start;
        }else if(r1.getBaseLine().end == r2.getBaseLine().start || r1.getBaseLine().end == r2.getBaseLine().end){
            p=r1.getBaseLine().end;
        }
        if(Objects.isNull(p)){
            throw new IllegalArgumentException("These roads' baselines dont intersect. No common GravityPoint found!");
        }
        Connection c=new BiConnection(r1,r2);
        connections.add(c);
    }
}
