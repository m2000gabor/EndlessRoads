package com.meszi007.view;

public class MenuInput {
    public enum State{DEMOLISH, NEW_BASIC_LANE, ONE_ONE_ROAD,TWO_TWO_ROAD,INFO};

    private final State state;

    public MenuInput(State state) {
        this.state = state;
    }

    public boolean isRoad(){
        switch (state){
            case NEW_BASIC_LANE:
            case ONE_ONE_ROAD:
            case TWO_TWO_ROAD:
                return true;
            default:
                return false;
        }
    }

    public boolean isDemolish(){return state==State.DEMOLISH;}

    public static MenuInput basicLane(){return new MenuInput(State.NEW_BASIC_LANE);}
    public static MenuInput oneOneRoad(){return new MenuInput(State.ONE_ONE_ROAD);}
    public static MenuInput twoTwoRoad(){return new MenuInput(State.TWO_TWO_ROAD);}
    public static MenuInput demolish(){return new MenuInput(State.DEMOLISH);}
    public static MenuInput info(){return new MenuInput(State.INFO);}

    public State getState() {
        return state;
    }
}
