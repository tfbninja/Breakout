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

    //DECLARE a static GameState object here (used in the timer)
    private static GameState gs;
    private RedrawTimer timer = new RedrawTimer();

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        //instantiate your GameState object
        //create walls, ball, paddle, and blocks
        //add the game elements (walls, ball, paddle, and blocks) to the GameState object

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setTitle("Breakout");
        primaryStage.setScene(scene);
        primaryStage.show();
        timer.start();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.RIGHT) {
                    //set the paddle to move to the right
                }
                if (event.getCode() == KeyCode.LEFT) {
                    //set the paddle to move to the left
                }
            }

        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.RIGHT) {
                    //set the paddle to not move
                }
                if (event.getCode() == KeyCode.LEFT) {
                    //set the paddle to not more
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
            //update, draw and collide all of the Game Elements in the GameState object 
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
