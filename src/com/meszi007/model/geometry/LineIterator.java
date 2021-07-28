package com.meszi007.model.geometry;

public class LineIterator {
    private final Line toIterate;
    private static final int SCALE=10;
    private Line currentLine;
    private final Vector direction;

    private int terminate;

    public LineIterator(Line toIterate) {
        this.toIterate = toIterate;
        direction = toIterate.getAsVector().normVectorToLength(SCALE);
        terminate=0;
    }

    public void first(){
        currentLine=new Line(0,0,toIterate.start.x,toIterate.start.y);next();
    }
    public void next(){
        //currentLine=new Line(currentLine.end,currentLine.end.getMoved(direction));
        //if(end()){return;}
        currentLine=new Line(toIterate.start.getMoved(getCurrentVector(direction,terminate)),toIterate.start.getMoved(getCurrentVector(direction,++terminate)));
        //currentLine=new Line(toIterate.start, toIterate.end);
        //terminate++;
    }
    public Line current(){return currentLine;}
    public boolean end(){
        return isBorder() || currentLine.contains(toIterate.end) || terminate>100;
        //return currentLine.end.equals(toIterate.end) || currentLine.getAsLine2d().contains(toIterate.end.x,toIterate.end.y) || terminate>100;
    }

    private boolean isBorder(){
        return (currentLine.end.x==toIterate.end.x &&  Point.getDistance(currentLine.end, toIterate.end)<=SCALE ) ||
                (currentLine.end.y==toIterate.end.y &&  Point.getDistance(currentLine.end, toIterate.end)<=SCALE ) ;
    }

    private Vector getCurrentVector(Vector direction, int length){
        return new Vector(direction.x*length,direction.y*length);
    }


}
