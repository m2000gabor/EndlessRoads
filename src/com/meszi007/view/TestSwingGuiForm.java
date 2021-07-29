package com.meszi007.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestSwingGuiForm {
    public JPanel base;
    private JPanel downsideMenu;
    private JButton infoMode;
    private JButton build1Button;
    private JButton demolish;
    private JPanel modeChangingButtons;
    private JPanel messageDisplay;
    private JButton a1LaneButton;
    private JButton a11RoadButton;
    private JButton a22RoadButton;
    private JPanel buildOptions;
    private JLabel sampleText;
    private JButton buildMoreButton;
    private JPanel upperMenu;
    private JPanel gameField;


    private final GameField field;

    public TestSwingGuiForm() {


        field=new GameField();
        gameField.add(field);

        //action listeners for ui buttons
        infoMode.addActionListener(e -> {field.userInput(MenuInput.info());});
        a1LaneButton.addActionListener(e -> field.userInput(MenuInput.basicLane()));
        a11RoadButton.addActionListener(e -> {field.userInput(MenuInput.oneOneRoad());});
        a22RoadButton.addActionListener(e -> {field.userInput(MenuInput.twoTwoRoad());});
        demolish.addActionListener(e -> {field.userInput(MenuInput.demolish());});

        build1Button.addActionListener( e -> buildOptions.setVisible(true));
        buildMoreButton.addActionListener( e -> buildOptions.setVisible(true));
        buildOptions.setVisible(false);


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
