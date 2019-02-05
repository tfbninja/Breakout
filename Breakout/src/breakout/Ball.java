package breakout;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author Tim Barber
 */
public class Ball extends GameElement implements Renderable, Updateable {

    private Color color = Color.WHITE;
    private double xVel;
    private double yVel;

    /*
     * If the ball hits the top of a block, the leftmost angle it can go is
     * 180 - this number, and the rightmost angle is this number. Simply rotate
     * your imaginary unit circle for the other sides
     */
    private static double angleMargin = 20;

    public Ball() {
        super();
    }

    public Ball(double x, double y, double d, Color color, double xVel, double yVel) {
        super(x, y, d, d);
        this.color = color;
        this.xVel = xVel;
        this.yVel = yVel;
    }

    @Override
    public Color getColor() {
        return color;
    }

    /**
     *
     * @param color The new color to display
     */
    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    public double getXVel() {
        return xVel;
    }

    public double getYVel() {
        return yVel;
    }

    public void setXVel(double amt) {
        xVel = amt;
    }

    public void setYVel(double amt) {
        yVel = amt;
    }

    public void toggleXVel() {
        xVel = -xVel;
    }

    public void toggleYVel() {
        yVel = -yVel;
    }

    public double getSpeed() {
        return new Vector(xVel, yVel).magnitude();
    }

    public boolean hitLeftSide(GameElement obj) {
        double x = super.getX(), y = super.getY(), w = super.getW(), h = super.getH();
        double x1 = obj.getX(), y1 = obj.getY(), w1 = obj.getW(), h1 = obj.getH();
        return x < x1 && x + w < x1 + w1;
    }

    public boolean hitRightSide(GameElement obj) {
        double x = super.getX(), y = super.getY(), w = super.getW(), h = super.getH();
        double x1 = obj.getX(), y1 = obj.getY(), w1 = obj.getW(), h1 = obj.getH();
        return x < x1 + w1 && x + w > x1 + w1;
    }

    public boolean hitTopSide(GameElement obj) {
        double x = super.getX(), y = super.getY(), w = super.getW(), h = super.getH();
        double x1 = obj.getX(), y1 = obj.getY(), w1 = obj.getW(), h1 = obj.getH();
        return y + h > y1 && y < y1;
    }

    public boolean hitBottomSide(GameElement obj) {
        double x = super.getX(), y = super.getY(), w = super.getW(), h = super.getH();
        double x1 = obj.getX(), y1 = obj.getY(), w1 = obj.getW(), h1 = obj.getH();
        return y < y1 + h1 && y > y1;
    }

    @Override
    public void draw(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(color);
        gc.fillOval(super.getX(), super.getY(), super.getW(), super.getH());
    }

    @Override
    public void onCollision(GameElement obj) {
        System.out.println(this.getClass().getSimpleName() + " has collided with " + obj.getClass().getSimpleName());
        double x = super.getX(), y = super.getY(), w = super.getW(), h = super.getH();
        double x1 = obj.getX(), y1 = obj.getY(), w1 = obj.getW(), h1 = obj.getH();
        if (obj.getClass().equals(Wall.class)) {
            if (obj.getW() == 1) {
                // if its a vertical wall
                toggleXVel();
            } else {
                // if it's not a vertical wall, it's a horizontal one
                toggleYVel();
            }
        } else if (obj.getClass().equals(Paddle.class)) {
            if (this.hitLeftSide(obj)) {
                //toggleXVel();
//                double ratio = 
            } else if (hitRightSide(obj)) {
                //toggleXVel();
            }
            if (this.hitTopSide(obj)) {
                //toggleYVel();
                double speed = new Vector(xVel, yVel).magnitude();
                double relativeX = x + w / 2 - x1;
                double ratio = relativeX / (w1 + w);
                double angle = (180 - angleMargin) - ((180 - angleMargin * 2) * ratio);
                double newXVel = Math.cos(angle) * speed;
                double newYVel = Math.sin(angle) * speed;

                xVel = newXVel;
                yVel = newYVel;
            } else if (hitBottomSide(obj)) {
                toggleYVel();
            }
        } else {
            if (this.hitLeftSide(obj) || hitRightSide(obj)) {
                toggleXVel();
            }
            if (this.hitTopSide(obj) || hitBottomSide(obj)) {
                toggleYVel();
            }
        }
    }

    @Override
    public void update() {
        super.changeX(xVel);
        super.changeY(yVel);
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
