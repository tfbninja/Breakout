package breakout;

import javafx.scene.canvas.Canvas;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.ArrayList;
import javafx.stage.Stage;

/**
 *
 * @author Tim Barber
 */
public class Breakout extends Application {

    /**
     * @param args the command line arguments
     */
    private static final int WIDTH = 900;
    private static final int HEIGHT = 600;
    private static Canvas canvas;
    private static final Color BG = Color.BLACK.brighter();

    private static final double BALLDIAMETER = 22.0;
    private static final Color BALLCOLOR = Color.LIGHTGRAY.darker();
    private static final double BALLX = WIDTH / 3;
    private static final double BALLY = HEIGHT / 2;
    private static final double PADDLEY = HEIGHT * 6 / 7;
    private static final double PADDLEW = 200;
    private static final double PADDLEH = 20;
    private static final Color PADDLECOLOR = Color.PALEVIOLETRED;
    private static final double BALLXVEL = 8;
    private static final double BALLYVEL = 6.7;
    private static final double TOPBALLSPEED = Vector.magnitude(BALLXVEL * 2, BALLYVEL * 2);
    private static final double PADDLESPEED = TOPBALLSPEED * 2 / 3;
    private static final double BALLSPEEDINCREASERATIO = 1.001;
    private static final double BLOCKH = 20;
    private static final double BLOCKYMARGIN = 2.0;
    private static final double BLOCKXMARGIN = 3.0;
    private static final int[] NUMBLOCKSPERROW = {5, 7, 13, 15, 19};
    private static final double INITIALBLOCKY = 180;
    private static final int ROWS = 5;
    private static int lives = 3;
    private static ArrayList<ColorBlock> blocks;
    private static Ball ball;
    private Wall bottom;
    private static int frame = 0;
    private static boolean win = false;

    private static GameState gs;
    RedrawTimer timer = new RedrawTimer();

    public Breakout() {
        this.blocks = new ArrayList<>();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);

        gs = new GameState();
        Wall top = new Wall(0, -100, WIDTH, 100);
        bottom = new Wall(0, HEIGHT, WIDTH, 100);
        Wall left = new Wall(-100, 0, 100, HEIGHT);
        Wall right = new Wall(WIDTH, 0, 100, HEIGHT);
        gs.addCollidable(top);
        gs.addCollidable(bottom);
        gs.addCollidable(left);
        gs.addCollidable(right);

        ball = new Ball(BALLX, BALLY, BALLDIAMETER, BALLCOLOR, BALLXVEL, BALLYVEL, BALLSPEEDINCREASERATIO, TOPBALLSPEED);
        Paddle paddle = new Paddle(WIDTH / 2, PADDLEY, PADDLEW, PADDLEH, PADDLECOLOR, PADDLESPEED);
        gs.addCollidable(ball);
        gs.addRenderable(ball);
        gs.addUpdateable(ball);
        gs.addCollidable(paddle);
        gs.addRenderable(paddle);
        gs.addUpdateable(paddle);

        for (int row = 0; row < ROWS; row++) {
            double yPos = INITIALBLOCKY + BLOCKH * row + BLOCKYMARGIN * row;
            double blockWidth = (WIDTH - (NUMBLOCKSPERROW[row % NUMBLOCKSPERROW.length] + 1) * BLOCKXMARGIN) / NUMBLOCKSPERROW[row % NUMBLOCKSPERROW.length];
            for (double x = BLOCKXMARGIN; x < WIDTH - blockWidth; x += blockWidth + BLOCKXMARGIN) {
                ColorBlock temp = new ColorBlock(x, yPos, blockWidth, BLOCKH, ROWS - row);
                blocks.add(temp);
                gs.addCollidable(temp);
                gs.addRenderable(temp);
                gs.addUpdateable(temp);
                gs.addBreakable(temp);
            }
        }

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setTitle("Breakout");
        primaryStage.setScene(scene);
        primaryStage.show();
        timer.start();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.RIGHT) {
                    //set the paddle to move to the right
                    if (paddle.getX() + paddle.getW() * 0.5 < WIDTH) {
                        paddle.goRight();
                    }
                }
                if (event.getCode() == KeyCode.LEFT) {
                    //set the paddle to move to the left
                    if (paddle.getX() + paddle.getW() * 0.5 > 0) {
                        paddle.goLeft();
                    }
                }
                if (event.getCode() == KeyCode.R) {
                    reset();
                }
            }

        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.RIGHT) {
                    //set the paddle to not move
                    paddle.stop();

                }
                if (event.getCode() == KeyCode.LEFT) {
                    //set the paddle to not more
                    paddle.stop();
                }
            }
        }
        );
        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                paddle.setX(event.getX() - paddle.getW() / 2);
            }
        });
    }

    public static void reset() {
        gs.reset();
        ball.setX(BALLX);
        ball.setY(BALLY);
        ball.setXVel(BALLXVEL);
        ball.setYVel(BALLYVEL);
        win = false;
        frame = 0;
    }

    public static void main(String[] args) {
        launch(args);

    }

    public class RedrawTimer extends AnimationTimer {

        public void handle(long now) {
            if (!win) {
                frame++;
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(BG);
                gc.fillRect(0, 0, WIDTH, HEIGHT);
                gc.setFont(new Font("VERDANA", 20));
                gc.setFill(Color.WHITE);
                gc.fillText("LIVES: " + lives, 10, 30);

                gs.collideAll();
                if (ball.hitTopSide(bottom)) {
                    ball.setX(BALLX);
                    ball.setY(BALLY);
                    lives--;
                }
                if (lives < 0) {
                    reset();
                    lives = 3;
                }
                if (!gs.blockStillExists()) {
                    gc.setFill(Color.RED);
                    gc.setFont(new Font("Impact", 50));
                    gc.fillText("YOU WIN", WIDTH / 2 - 100, HEIGHT / 2 + 25);
                    win = true;
                }
                gs.updateAll();
                gs.drawAll(canvas);
            }
        }
    }

}

/*
 * The MIT License
 *
 * Copyright (c) 2019 Tim Barber.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
