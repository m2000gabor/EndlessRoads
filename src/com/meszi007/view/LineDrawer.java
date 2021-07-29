package com.meszi007.view;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class LineDrawer {
    public static Shape getShape(int x1,int y1,int x2, int y2){
        return getShape(new Point(x1,y1),new Point(x2,y2));
    }
    public static Shape getShape(Point fromPt, Point toPt) {
        Polygon arrowPolygon = new Polygon();

        arrowPolygon.addPoint(-6,2);
        arrowPolygon.addPoint(-6,0);
        arrowPolygon.addPoint(0,0);
        arrowPolygon.addPoint(0,2);


        Point midPoint = midpoint(fromPt, toPt);

        double rotate = Math.atan2(toPt.y - fromPt.y, toPt.x - fromPt.x);

        AffineTransform transform = new AffineTransform();
        transform.translate(midPoint.x, midPoint.y);
        double ptDistance = fromPt.distance(toPt);
        double scale = ptDistance / 12.0; // 12 because it's the length of the arrow polygon.
        transform.scale(scale, scale);
        transform.rotate(rotate);

        return transform.createTransformedShape(arrowPolygon);
    }

    private static Point midpoint(Point p1, Point p2) {
        return new Point((int)((p1.x + p2.x)/2.0),
                (int)((p1.y + p2.y)/2.0));
    }
}
