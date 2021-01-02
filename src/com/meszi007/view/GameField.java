package com.meszi007.view;

import com.meszi007.model.ModelCore;
import com.meszi007.model.Point;
import com.meszi007.model.road.Road;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;

public class GameField extends JPanel {
    private final ModelCore modelCore;

    public GameField() {
        modelCore=new ModelCore();

        setPreferredSize(new Dimension(500,500));
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g=(Graphics2D) graphics;
        g.setColor(Color.BLACK);
        //g.drawLine(50,50,60,60);
        for(Road r:modelCore.roads){
            double width=Point.getDistance(r.leftEdgeLine.start,r.rightEdgeLine.start);
            double height=Point.getDistance(r.leftEdgeLine.start,r.leftEdgeLine.end);
/*
            g.fillRect(r.leftEdgeLine.start.x,r.leftEdgeLine.start.y,
                    (int) Math.round(width),
                    (int) Math.round(height));*/

            // draw GeneralPath (polygon)
            int[] x1Points = {r.leftEdgeLine.start.x, r.leftEdgeLine.end.x, r.rightEdgeLine.end.x,r.rightEdgeLine.start.x};
            int[] y1Points = {r.leftEdgeLine.start.y, r.leftEdgeLine.end.y, r.rightEdgeLine.end.y,r.rightEdgeLine.start.y};
            GeneralPath polygon =
                    new GeneralPath(GeneralPath.WIND_EVEN_ODD,
                            x1Points.length);
            polygon.moveTo(x1Points[0], y1Points[0]);

            for (int index = 1; index < x1Points.length; index++) {
                polygon.lineTo(x1Points[index], y1Points[index]);
            }

            polygon.closePath();
            g.fill(polygon);
        }
        
        /*
        for(Road r:modelCore.roads){
            for(RoadSlice slice:r.roadSlices){
                //g.setColor(slice.getColor());
                //g.fillRect(r.baseLine.x1,,slice.width(),r.getHeight());

            }


            g.setColor(Color.RED);
            //g.drawLine(r.baseLine.x1,r.baseLine.y1,r.baseLine.x2,r.baseLine.y2);
        }
*/

    }
}
