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
    Food food;
    Poison poison;
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
        frame.setResizable(false); //don't allow user to resize window

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
        food = new Food();

        while (!gameOver) { //game continue...
            snake.move();
            if (food.isEaten()) {
                food.next();
            }
            canvasPanel.repaint();
            try {
                Thread.sleep(SHOW_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    class Snake {
        ArrayList<Point> snake = new ArrayList<Point>();
        int direction;

        public Snake(int x, int y, int length, int direction) { //snake constructor
            for (int i = 0; i < length; i++) {
                Point point = new Point(x - i, y);
                snake.add(point);
            }
            this.direction = direction;
        }

        boolean isInsideSnake(int x, int y) {
            for (Point point : snake) {
                if ((point.getX() == x) && (point.getY() == y)) {
                    return true;

                }
            }
            return false;
        }

        boolean isFood(Point food) {     //detect when snake eat food
            return ((snake.get(0).getX() == food.getX()) &&
                    snake.get(0).getY() == food.getY());
        }

        void move() {
            int x = snake.get(0).getX();
            int y = snake.get(0).getY();
            if (direction == LEFT) {
                x--;
            } //meh...
            if (direction == RIGHT) {
                x++;
            }
            if (direction == UP) {
                y--;
            }
            if (direction == DOWN) {
                y++;
            }
            if (x > FIELD_WIDTH - 1) {
                x = 0;
            }
            if (x < 0) {
                x = FIELD_WIDTH - 1;
            }
            if (y > FIELD_HEIGHT - 1) {
                y = 0;
            }
            if (y < 0) {
                y = FIELD_HEIGHT - 1;
            }
            gameOver = isInsideSnake(x, y); //check for cross snake itselves
            snake.add(0, new Point(x, y)); //add snake element
            if (isFood(food)) {
                food.eat();
                frame.setTitle(TITLE_OF_PROGRAMM + " : " + snake.size());
            } else {
                snake.remove(snake.size() - 1);
            }

        }

        void setDirection(int direction) {
            if ((direction >= LEFT && direction <= DOWN)) {
                if (Math.abs(this.direction - direction) != 2) { //dont allow snake move itself
                    this.direction = direction;

                }
            }
        }

        void paint(Graphics g) {                //Snake draw
            for (Point point : snake) {
                point.paint(g);
            }
        }

    }

    class Food extends Point {

        public Food() {
            super(-1, -1);
            this.color = FOOD_COLOR;
        }

        void eat() {
            this.setXY(-1, -1);
        }

        boolean isEaten() { //food eaten?
            return this.getX() == -1;
        }

        boolean isFood(int x, int y) { return (this.x == x) && (this.y == y); }


        void next() { //create FOOD
            int x, y;
            do {
                x = random.nextInt(FIELD_WIDTH-1); //don't create food on field border
                y = random.nextInt(FIELD_HEIGHT-1);
            } while (snake.isInsideSnake(x, y));
            this.setXY(x, y);
        }

    }

    class Poison {
        private ArrayList<Point> poison = new ArrayList<Point>();
//        int x, y;

        boolean isPoison(int x, int y) {
            for (Point point : poison) if ((point.getX() == x) && (point.getY() == y)) return true;
            return false;
        }

        void add() {
            int x, y;
            do {
                x = random.nextInt(FIELD_WIDTH-1);
                y = random.nextInt(FIELD_HEIGHT-1);
            } while (isPoison(x, y) || snake.isInsideSnake(x, y) || food.isFood(x, y));
            poison.add(new Point(x, y)); //, POISON_COLOR));
        }

//        void paint(Graphics g) {
//            g.setColor(POISON_COLOR);
//            g.fillOval( * POINT_RADIUS, Point.getY()* POINT_RADIUS, POINT_RADIUS, POINT_RADIUS);
//            for (Point point : poison) {
//                point.paint(g);
//        }


    }

    class Point {
        int x, y;
        Color color = DEFAULT_COLOR;

        public Point(int x, int y) { //constructor
            this.setXY(x, y);
        }

        void paint(Graphics g) {
            g.setColor(color);
            g.fillOval(x * POINT_RADIUS, y * POINT_RADIUS, POINT_RADIUS, POINT_RADIUS);
        }

        int getX() {
            return x;
        } //getters

        int getY() {
            return y;
        }

        void setXY(int x, int y) { //interesting...
            this.x = x;
            this.y = y;
        }
    }

    public class Canvas extends JPanel {

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            snake.paint(g);
            food.paint(g);
//            poison.paint(g);
            if (gameOver) {
                g.setColor(Color.red);
                g.setFont(new Font("Arial", Font.BOLD, 60));
                FontMetrics fm = g.getFontMetrics();
                g.drawString(GAME_OVER_MSG, (FIELD_WIDTH * POINT_RADIUS + FIELD_DX - fm.stringWidth(GAME_OVER_MSG)) / 2,
                        (FIELD_HEIGHT * POINT_RADIUS + FIELD_DY) / 2);
            }
        }
    }
}
