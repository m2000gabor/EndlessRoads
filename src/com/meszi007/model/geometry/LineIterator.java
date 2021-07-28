package com.meszi007.model.geometry;

public class LineIterator {
    private final Line toIterate;
    private static final int SCALE=10;
    private Line currentLine;
    private final Vector direction;

    public LineIterator(Line toIterate) {
        this.toIterate = toIterate;
        direction = toIterate.getAsVector().normVectorToLength(SCALE);
    }

    public void first(){currentLine=new Line(0,0,toIterate.start.x,toIterate.start.y);next();}
    public void next(){
        //currentLine=new Line(currentLine.end,currentLine.end.getMoved(direction));
        currentLine=new Line(toIterate.start, toIterate.end);
    }
    public Line current(){return currentLine;}
    public boolean end(){
        return currentLine.end.equals(toIterate.end) || currentLine.getAsLine2d().contains(toIterate.end.x,toIterate.end.y);
    }


}
