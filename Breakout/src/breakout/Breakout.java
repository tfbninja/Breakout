package breakout;

import javafx.scene.canvas.Canvas;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
    private static final Color bg = Color.BLACK;

    private double ballDiameter = 22.0;
    private Color ballColor = Color.SPRINGGREEN;
    private double paddleY = HEIGHT * 6 / 7;
    private double paddleW = 100;
    private double paddleH = 20;
    private Color paddleColor = Color.PURPLE;
    private double paddleSpeed = 6.0;

    private double blockH = 20;
    private double blockYMargin = 2.0;
    private double blockXMargin = 3.0;
    private int[] numBlocksPerRow = {5, 7, 18};
    private double initialBlockY = 180;
    private double[] blockYPositions = {initialBlockY, initialBlockY + blockH + blockYMargin, initialBlockY + blockH * 2 + blockYMargin * 2};
    private ArrayList<ColorBlock> blocks = new ArrayList<>();
    private ArrayList<Color> blockColors = new ArrayList<Color>() {
        {
            add(Color.MAROON);
            add(Color.ORANGERED);
            add(Color.GOLD);
        }
    };

    //DECLARE a static GameState object here (used in the timer)
    private static GameState gs;
    RedrawTimer timer = new RedrawTimer();

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);

        gs = new GameState();
        Wall top = new Wall(0, -1, WIDTH, 1);
        Wall bottom = new Wall(0, HEIGHT, WIDTH, 1);
        Wall left = new Wall(-1, 0, 1, HEIGHT);
        Wall right = new Wall(WIDTH, 0, 1, HEIGHT);
        gs.addCollidable(top);
        gs.addCollidable(bottom);
        gs.addCollidable(left);
        gs.addCollidable(right);

        Ball ball = new Ball(WIDTH / 3, HEIGHT * 1 / 2, ballDiameter, ballColor, 3, 4);
        Paddle paddle = new Paddle(WIDTH / 2, paddleY, paddleW, paddleH, paddleColor, paddleSpeed);
        gs.addCollidable(ball);
        gs.addRenderable(ball);
        gs.addUpdateable(ball);
        gs.addCollidable(paddle);
        gs.addRenderable(paddle);
        gs.addUpdateable(paddle);

        for (int row = 0; row < blockYPositions.length; row++) {
            double yPos = blockYPositions[row];
            double blockWidth = (WIDTH - (numBlocksPerRow[row] + 1) * blockXMargin) / numBlocksPerRow[row];
            for (double x = blockXMargin; x < WIDTH - blockWidth; x += blockWidth + blockXMargin) {
                ColorBlock temp = new ColorBlock(x, yPos, blockWidth, blockH, ColorBlock.MODE_STATIC, blockColors.get(row));
                blocks.add(temp);
                gs.addCollidable(temp);
                gs.addRenderable(temp);
                gs.addUpdateable(temp);
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
                    paddle.goRight();
                }
                if (event.getCode() == KeyCode.LEFT) {
                    //set the paddle to move to the left
                    paddle.goLeft();
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
        });
    }

    public static void main(String[] args) {
        launch(args);

    }

    public class RedrawTimer extends AnimationTimer {

        public void handle(long now) {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setFill(bg);
            gc.fillRect(0, 0, WIDTH, HEIGHT);

            gs.collideAll();
            gs.updateAll();
            gs.drawAll(canvas);
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
