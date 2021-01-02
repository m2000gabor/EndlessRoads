package com.meszi007.model.road;

import com.meszi007.model.Line;

import java.awt.*;

public abstract class RoadSlice {
    final int width;
    final Color color;
    public Line line;

    public RoadSlice(int width, Color color, Line line) {
        this.width = width;
        this.color = color;
        this.line = line;
    }
}
