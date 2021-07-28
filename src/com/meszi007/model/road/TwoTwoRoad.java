package com.meszi007.model.road;

import com.meszi007.model.geometry.Line;
import org.jetbrains.annotations.NotNull;

public class TwoTwoRoad extends Road{
    public TwoTwoRoad(@NotNull Line baseLine) {
        super(baseLine);
    }

    @Override
    public int getDefaultWidth() {
        return 20;
    }
}
