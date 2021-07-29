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
        /*
        @SuppressWarnings("SuspiciousNameCombination")
        Vector v =new Vector(getBaseLine().getAsVector().y*-1,getBaseLine().getAsVector().x);
        v=v.normVectorToLength(Lane.LANE_WIDTH);
        lanes.add(new Lane(getBaseLine().getTransformBy(v.x,v.y)));

        //v=v.normVectorToLength(Lane.LANE_WIDTH);
        v=new Vector(v.x*-1,v.y*-1);
        lanes.add(new Lane(getBaseLine().getTransformBy(v.x,v.y).getReversed()));


        v=v.normVectorToLength(Lane.LANE_WIDTH*3);
        lanes.add(new Lane(getBaseLine().getTransformBy(v.x,v.y).getReversed()));

        v=new Vector(v.x*-1,v.y*-1);
        //v=v.normVectorToLength(Lane.LANE_WIDTH*3);
        lanes.add(new Lane(getBaseLine().getTransformBy(v.x,v.y)));
         */

        Vector v = new Vector(getBaseLine().getAsVector().y*-1,getBaseLine().getAsVector().x);
        //Vector v0=v.normVectorToLength(Lane.LANE_WIDTH);
        //v0=new Vector(v0.x*-1,v0.y*-1);

        v=v.normVectorToLength(Lane.LANE_WIDTH/2.0);

        Lane l1=new Lane(getBaseLine().getTransformBy(v.x,v.y));
        v=v.normVectorToLength(Lane.LANE_WIDTH);
        Lane l2=new Lane(l1.getBaseLine().getTransformBy(v.x,v.y));
        //System.err.println("Before" + l1.getBaseLine() +"\neltol ennyivel: "+v.getLength() + "\n after: "+ l2.getBaseLine());
        v=new Vector(v.x*-1,v.y*-1);
        v=v.normVectorToLength(Lane.LANE_WIDTH/2.0);
        Lane l3=new Lane(getBaseLine().getTransformBy(v.x,v.y).getReversed());
        v=v.normVectorToLength(Lane.LANE_WIDTH);
        Lane l4=new Lane(l3.getBaseLine().getTransformBy(v.x,v.y));

        lanes.add(l4);
        lanes.add(l3);
        lanes.add(l1);
        lanes.add(l2);

        //v=new Vector(v.x*-1,v.y*-1);
    }
}
