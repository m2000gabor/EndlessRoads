package com.meszi007.model.road;

import com.meszi007.model.Line;

import java.awt.*;

public class RoadEdge extends RoadSlice {
    public RoadEdge( Line line) {
        super(10, Color.BLUE, line);
    }
}
