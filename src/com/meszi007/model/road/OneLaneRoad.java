package com.meszi007.model.road;

import com.meszi007.model.geometry.Line;
import org.jetbrains.annotations.NotNull;

public class OneLaneRoad extends Road{

    public OneLaneRoad(@NotNull Line baseLine) {
        super(baseLine);
    }

    @Override
    public int getDefaultWidth() {
        return Lane.LANE_WIDTH;
    }

    @Override
    public void setupLanes() {
        lanes.add(new Lane(getBaseLine()));
    }
}
