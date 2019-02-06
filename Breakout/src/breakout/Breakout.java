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
import java.util.ArrayList;
import java.util.Collections;
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
    private static final Color bg = Color.BLACK.brighter();

    private final double ballDiameter = 22.0;
    private final Color ballColor = Color.LIGHTGRAY.darker();
    private final double ballX = WIDTH / 3;
    private final double ballY = HEIGHT / 2;
    private final double paddleY = HEIGHT * 6 / 7;
    private final double paddleW = 200;
    private final double paddleH = 20;
    private final Color paddleColor = Color.PALEVIOLETRED;

    private final double ballXVel = 8;
    private final double ballYVel = 6.7;
    private final double topBallSpeed = Vector.magnitude(ballXVel * 2, ballYVel * 2);
    private final double paddleSpeed = topBallSpeed * 2 / 3;
    private final double ballSpeedIncreaseRatio = 1.001;

    private final double blockH = 20;
    private final double blockYMargin = 2.0;
    private final double blockXMargin = 3.0;
    private final int[] numBlocksPerRow = {5, 7, 13, 15, 19};
    private final double initialBlockY = 180;
    private final int rows = 5;
    private final ArrayList<ColorBlock> blocks;
    private Ball ball;
    private Wall bottom;

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

        ball = new Ball(ballX, ballY, ballDiameter, ballColor, ballXVel, ballYVel, ballSpeedIncreaseRatio, topBallSpeed);
        Paddle paddle = new Paddle(WIDTH / 2, paddleY, paddleW, paddleH, paddleColor, paddleSpeed);
        gs.addCollidable(ball);
        gs.addRenderable(ball);
        gs.addUpdateable(ball);
        gs.addCollidable(paddle);
        gs.addRenderable(paddle);
        gs.addUpdateable(paddle);

        for (int row = 0; row < rows; row++) {
            double yPos = initialBlockY + blockH * row + blockYMargin * row;
            double blockWidth = (WIDTH - (numBlocksPerRow[row % numBlocksPerRow.length] + 1) * blockXMargin) / numBlocksPerRow[row % numBlocksPerRow.length];
            for (double x = blockXMargin; x < WIDTH - blockWidth; x += blockWidth + blockXMargin) {
                ColorBlock temp = new ColorBlock(x, yPos, blockWidth, blockH, rows - row);
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
        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                paddle.setX(event.getX() - paddle.getW() / 2);
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
            if (ball.hitTopSide(bottom)) {
                ball.setX(ballX);
                ball.setY(ballY);
            }
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
