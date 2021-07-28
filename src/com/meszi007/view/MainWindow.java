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

        JButton infoMenuItem=new JButton("Info");
        infoMenuItem.addActionListener(e -> {field.userInput(MenuInput.info());});
        menuBar.add(infoMenuItem);

        JButton road22EdgeMenuItem=new JButton("2-2 lane road");
        road22EdgeMenuItem.addActionListener(e -> {field.userInput(MenuInput.twoTwoRoad());});
        menuBar.add(road22EdgeMenuItem);

        JButton road11EdgeMenuItem=new JButton("1-1 lane road");
        road11EdgeMenuItem.addActionListener(e -> {field.userInput(MenuInput.oneOneRoad());});
        menuBar.add(road11EdgeMenuItem);

        JButton basicRoadLaneMenuItem=new JButton("Basic Lane");
        basicRoadLaneMenuItem.addActionListener(e -> field.userInput(MenuInput.basicLane()));
        menuBar.add(basicRoadLaneMenuItem);

        JButton demolishMenuItem=new JButton("Demolish");
        demolishMenuItem.addActionListener(e -> {System.out.println("Button clicked");field.userInput(MenuInput.demolish());});
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
