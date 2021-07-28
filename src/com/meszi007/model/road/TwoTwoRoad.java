package com.meszi007.model.road;

import com.meszi007.model.geometry.Line;
import com.meszi007.model.geometry.Vector;
import org.jetbrains.annotations.NotNull;

public class TwoTwoRoad extends Road{
    public TwoTwoRoad(@NotNull Line baseLine) {
        super(baseLine);
    }

    @Override
    public int getDefaultWidth() {
        return Lane.LANE_WIDTH*4;
    }

    @Override
    public void setupLanes() {
        Vector v =new Vector(getBaseLine().getAsVector().y*-1,getBaseLine().getAsVector().x);
        v=v.normVectorToLength(Lane.LANE_WIDTH);
        lanes.add(new Lane(getBaseLine().getTransformBy(v.x,v.y)));


        //v=v.normVectorToLength(Lane.LANE_WIDTH);
        v=new Vector(v.x*-1,v.y*-1);
        lanes.add(new Lane(getBaseLine().getTransformBy(v.x,v.y)));


        v=v.normVectorToLength(Lane.LANE_WIDTH*3);
        lanes.add(new Lane(getBaseLine().getTransformBy(v.x,v.y)));

        v=new Vector(v.x*-1,v.y*-1);
        //v=v.normVectorToLength(Lane.LANE_WIDTH*3);
        lanes.add(new Lane(getBaseLine().getTransformBy(v.x,v.y)));
    }
}
