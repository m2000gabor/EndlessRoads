package com.meszi007.model.connections;

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
}
