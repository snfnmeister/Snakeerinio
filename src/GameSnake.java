/*
 * Classic Game Snake
 * August 01, 2019 lesson*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class GameSnake {

    //constants
    final String TITLE_OF_PROGRAMM = "Classic Game Snake";
    final String GAME_OVER_MSG = "GAME OVER";
    final int POINT_RADIUS = 20; // circle radius in px
    final int FIELD_WIDTH = 30; // field size in POINT_RADIUS
    final int FIELD_HEIGHT = 20; // field size in POINT_RADIUS
    final int FIELD_DX = 6; //delimeters?
    final int FIELD_DY = 28;
    final int START_LOCATION = 200; // game start positions
    final int START_SNAKE_SIZE = 6;
    final int START_SNAKE_X = 10;
    final int START_SNAKE_Y = 10;
    final int SHOW_DELAY = 150; // in ms
    final int LEFT = 37; //move left (in keyboards codes)
    final int RIGHT = 39; //move right
    final int UP = 38; // move UP
    final int DOWN = 40; // move DOWN
    final int START_DIRECTION = RIGHT; // snake start moving to right
    final Color DEFAULT_COLOR = Color.black;
    final Color FOOD_COLOR = Color.green;
    final Color POISON_COLOR = Color.red;
    //Snake snake;
    //Food food;
    //Poison poison;
    JFrame frame;
    Canvas canvasPanel;
    Random random = new Random();
    boolean gameOver = false;


    public static void main(String[] args) {
        new GameSnake().go();

    }

    void go() {
        frame = new JFrame(TITLE_OF_PROGRAMM); //" : " + START_SNAKE_SIZE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close work window
        frame.setSize(FIELD_WIDTH * POINT_RADIUS + FIELD_DX, FIELD_HEIGHT * POINT_RADIUS + FIELD_DY); //frame size
        frame.setLocation(START_LOCATION, START_LOCATION); //start position
        frame.setResizable(false); //dont allow user to resize window

        canvasPanel = new Canvas();
        canvasPanel.setBackground(Color.blue);


        frame.setVisible(true);

    }

    public class Canvas extends JPanel {

        @Override
        public void paint(Graphics g) {
            super.paint(g);
        }
    }
}
