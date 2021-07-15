package com.meszi007.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    GameField field;

    public MainWindow() throws HeadlessException {
        field=new GameField();

        JMenuBar menuBar=new JMenuBar();
        JMenu newItem=new JMenu("Add item");
        JMenuItem roadEdgeMenuItem=new JMenuItem("Edge");
        JMenuItem basicRoadLaneMenuItem=new JMenuItem("Basic Lane");
        menuBar.add(newItem);
        newItem.add(roadEdgeMenuItem);
        newItem.add(basicRoadLaneMenuItem);
        setJMenuBar(menuBar);

        setLayout(new BorderLayout());
        setTitle("Endless Roads");
        add(field,BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //setResizable(false);

        pack();

        setVisible(true);

        gameLoop();
    }

    void gameLoop(){
        Timer timer=new Timer(10,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                field.repaint();
            }
        });
        timer.start();
    }


}
