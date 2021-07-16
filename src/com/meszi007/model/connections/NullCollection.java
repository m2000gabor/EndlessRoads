package com.meszi007.model.connections;

import com.meszi007.model.GravityPoint;
import com.meszi007.model.road.Road;

import java.util.Objects;

public class NullCollection implements Connection{
    private static NullCollection instance=null;

    private NullCollection(){}

    public static NullCollection getInstance(){
        if (Objects.isNull(instance)) {
            instance= new NullCollection();
        }
        return instance;
    }

    @Override
    public boolean contains(Road road) {
        return false;
    }

    @Override
    public GravityPoint getFocusPoint() {
        return null;
    }

    @Override
    public Connection upgrade(Road road) {
        return null;
    }
}
