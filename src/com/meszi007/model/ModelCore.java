package com.meszi007.model;

import com.meszi007.model.connections.Connection;
import com.meszi007.model.connections.MultiConnection;
import com.meszi007.model.geometry.Point;
import com.meszi007.model.road.Road;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        if(r1.getBaseLine().start.equals(r2.getBaseLine().start) || r1.getBaseLine().start.equals(r2.getBaseLine().end)){
            p=r1.getBaseLine().start;
        }else if(r1.getBaseLine().end.equals(r2.getBaseLine().start) || r1.getBaseLine().end.equals(r2.getBaseLine().end)){
            p=r1.getBaseLine().end;
        }
        if(Objects.isNull(p)){
            throw new IllegalArgumentException("These roads' baselines dont intersect. No common GravityPoint found!");
        }

        //find out, whether its already in connection
        Connection r1PrevConnection = findConnection(r1,p);
        Connection r2PrevConnection = findConnection(r2,p);
        if(Objects.isNull(r1PrevConnection) && Objects.isNull(r2PrevConnection)){
            Connection c = MultiConnection.getInstance(p,r1,r2);
            connections.add(c);
        }else if(Objects.nonNull(r1PrevConnection) && Objects.isNull(r2PrevConnection)){
            Connection c = r1PrevConnection.upgrade(r2);
            connections.remove(r1PrevConnection);
            connections.add(c);
        }else if(Objects.isNull(r1PrevConnection) && Objects.nonNull(r2PrevConnection)){
            Connection c = r2PrevConnection.upgrade(r1);
            connections.remove(r2PrevConnection);
            connections.add(c);
        }else{//egyik sem null
            throw new IllegalStateException("Cannot connect 2 intersections");
        }

    }

    private @Nullable Connection findConnection(@NotNull Road road,Point p) {
        if(Objects.nonNull(road.getStartConnection()) && road.getStartConnection().getFocusPoint().includesPoint(p)){
            return road.getStartConnection();
        }else if(Objects.nonNull(road.getEndConnection()) && road.getEndConnection().getFocusPoint().includesPoint(p)){
            return road.getEndConnection();
        }else{
            return null;
        }
    }
}
