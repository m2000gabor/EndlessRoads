package com.meszi007.model.road;

import com.meszi007.model.geometry.Line;
import com.meszi007.model.geometry.Vector;

public class OneOneRoad extends Road{

    public OneOneRoad(Line baseLine) {
        super(baseLine);
    }

    @Override
    public int getDefaultWidth() {
        return Lane.LANE_WIDTH*2;
    }

    @Override
    public void setupLanes() {
        @SuppressWarnings("SuspiciousNameCombination")
        Vector v =new Vector(getBaseLine().getAsVector().y*-1,getBaseLine().getAsVector().x);
        v=v.normVectorToLength(Lane.LANE_WIDTH/2.0);
        lanes.add(new Lane(getBaseLine().getTransformBy(v.x,v.y)));
        v=new Vector(v.x*-1,v.y*-1);
        lanes.add(new Lane(getBaseLine().getTransformBy(v.x,v.y).getReversed()));
    }

}
