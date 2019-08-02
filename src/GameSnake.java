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
    Snake snake;
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
        canvasPanel.setBackground(Color.white);

        frame.getContentPane().add(BorderLayout.CENTER, canvasPanel);
        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                snake.setDirection(e.getKeyCode());
            }
        });


        frame.setVisible(true);

        snake = new Snake(START_SNAKE_X, START_SNAKE_Y, START_SNAKE_SIZE,
                START_DIRECTION);

        while (!gameOver) { //game continue...
            snake.move();
            canvasPanel.repaint();
            try {
                 Thread.sleep(SHOW_DELAY);
            } catch (InterruptedException e) { e.printStackTrace(); }
        }

    }

    class Snake {
        ArrayList<Point> snake = new ArrayList<Point>();
        int direction;

        public Snake(int x, int y, int length, int direction) { //snake constructor
            for (int i=0; i < length; i++) {
                Point point = new Point(x-i, y);
                snake.add(point);
            }
            this.direction = direction;
        }

        void move() {
            int x = snake.get(0).getX();
            int y = snake.get(0).getY();
            if (direction == LEFT) { x--;} //meh...
            if (direction == RIGHT) { x++;}
            if (direction == UP) { y--;}
            if (direction == DOWN) {y++;}
            if (x > FIELD_WIDTH - 1) { x = 0; }
            if (x < 0) { x = FIELD_WIDTH - 1; }
            if (y > FIELD_HEIGHT - 1) { y = 0; }
            if (y < 0) { y = FIELD_HEIGHT - 1; }
            snake.add(0,new Point(x,y));
            snake.remove (snake.size() - 1); //delete snake-end

        }

        void setDirection (int direction) {
            if (direction >= LEFT && direction <= DOWN) {
                this.direction = direction;

            }

        }

        void paint (Graphics g) {                //Snake draw
            for (Point point : snake) {
                point.paint(g);
            }
        }

        }

    class Point {
        int x, y;
        Color color = DEFAULT_COLOR;

        public Point (int x, int y) { //constructor
            this.setXY(x,y);
        }

        void paint(Graphics g) {
            g.setColor(color);
            g.fillOval(x*POINT_RADIUS, y*POINT_RADIUS, POINT_RADIUS, POINT_RADIUS);
        }

        int getX() { return x;} //getters
        int getY() { return y;}

        void setXY(int x, int y) { //interesting...
            this.x=x;
            this.y=y;
        }
    }

    public class Canvas extends JPanel {

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            snake.paint(g);
        }
    }
}
