package com.meszi007.model.connections;

import com.meszi007.model.geometry.GravityPoint;
import com.meszi007.model.road.Road;

import java.awt.*;

public interface Connection {
    boolean contains(Road road);
    GravityPoint getFocusPoint();

    Connection upgrade(Road road);
    void paint(Graphics2D gr);
}
