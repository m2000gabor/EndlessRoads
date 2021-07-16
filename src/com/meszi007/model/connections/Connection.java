package com.meszi007.model.connections;

import com.meszi007.model.GravityPoint;
import com.meszi007.model.road.Road;

public interface Connection {
    boolean contains(Road road);
    GravityPoint getFocusPoint();

    Connection upgrade(Road road);
}
