package com.meszi007.model.connections;

import com.meszi007.model.geometry.GravityPoint;
import com.meszi007.model.geometry.Point;
import com.meszi007.model.road.Road;

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
