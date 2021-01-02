package com.meszi007.model.road;

import com.meszi007.model.Line;

import java.util.List;

public class Road {
    public List<RoadSlice> roadSlices;
    public Line leftEdgeLine;
    public Line rightEdgeLine;

    public Road(Line leftEdgeLine, Line rightEdgeLine) {
        this.leftEdgeLine = leftEdgeLine;
        this.rightEdgeLine = rightEdgeLine;
    }

    public void addRoadSlice(RoadSlice slice){

        //roadSlices.add();
    }

/*

    public int getWidth(){
        int sum=0;
        for(RoadSlice s: roadSlices){sum+=s.width();}
        return sum;
    }

    public int getHeight(){return (int)Math.round(Math.sqrt((baseLine.x2-baseLine.x1)^2 +(baseLine.y2-baseLine.y1)^2));}
*/
}
