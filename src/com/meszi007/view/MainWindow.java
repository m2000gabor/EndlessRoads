package com.meszi007.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    public enum MenuInput{NEW_BASIC_LANE,NEW_WIDE_LINE,DEMOLISH};
    GameField field;

    public MainWindow() throws HeadlessException {
        field=new GameField();

        JMenuBar menuBar=new JMenuBar();

        JButton roadEdgeMenuItem=new JButton("Wide line");
        roadEdgeMenuItem.addActionListener(e -> {
            field.userInput(MenuInput.NEW_WIDE_LINE); //todo implement
        });
        menuBar.add(roadEdgeMenuItem);

        JButton basicRoadLaneMenuItem=new JButton("Basic Lane");
        basicRoadLaneMenuItem.addActionListener(e -> field.userInput(MenuInput.NEW_BASIC_LANE));
        menuBar.add(basicRoadLaneMenuItem);

        JButton demolishMenuItem=new JButton("Demolish");
        demolishMenuItem.addActionListener(e -> {System.out.println("Button clicked");field.userInput(MenuInput.DEMOLISH);});
        menuBar.add(demolishMenuItem);

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
