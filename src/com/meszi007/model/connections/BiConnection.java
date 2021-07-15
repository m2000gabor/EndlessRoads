package com.meszi007.model.connections;

import com.meszi007.model.road.Road;

public class BiConnection implements Connection{
    public final Road road1;
    public final Road road2;

    public BiConnection(Road road1, Road road2) {
        this.road1 = road1;
        this.road2 = road2;
    }
}
