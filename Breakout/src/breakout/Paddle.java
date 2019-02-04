package breakout;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author Tim Barber
 */
public class Paddle extends GameElement implements Renderable, Updateable {

    private Color color = Color.WHITE;
    private double speed;
    private double velocity = 0.0;
    private double maxVel;

    public Paddle() {
        super();
    }

    public Paddle(double x, double y, double w, double h, Color color, double speed) {
        super(x, y, w, h);
        this.color = color;
        this.speed = speed;
    }

    public double getMaxSpeed() {
        return speed;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setSpeed(double amt) {
        speed = amt;
    }

    public void setVelocity(double amt) {
        velocity = amt;
    }

    @Override
    public void draw(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(color);
        gc.fillRect(super.getX(), super.getY(), super.getW(), super.getH());
    }

    @Override
    public void onCollision(GameElement obj) {
        return;
    }

    @Override
    public boolean checkCollision(GameElement obj) {
        return false;
    }

    @Override
    public void update() {
        super.changeX(velocity);
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
