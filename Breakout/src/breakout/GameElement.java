package breakout;

import javafx.scene.paint.Color;

/**
 *
 * @author Tim Barber
 */
public abstract class GameElement {

    private double x;
    private double y;
    private double w;
    private double h;
    private boolean exists;

    public GameElement() {
        x = y = w = h = 0;
        exists = true;
    }

    public GameElement(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        exists = true;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getW() {
        return w;
    }

    public double getH() {
        return h;
    }

    public boolean exists() {
        return exists;
    }

    public void setX(double amt) {
        x = amt;
    }

    public void setY(double amt) {
        y = amt;
    }

    public void setW(double amt) {
        w = amt;
    }

    public void setH(double amt) {
        h = amt;
    }

    public void changeX(double amt) {
        x += amt;
    }

    public void changeY(double amt) {
        y += amt;
    }

    public void destroy() {
        exists = false;
    }

    public void reincarnate() {
        exists = true;
    }

    public abstract void onCollision(GameElement obj);

    public boolean checkCollision(GameElement obj) {
        double x1 = obj.getX(), y1 = obj.getY(), w1 = obj.getW(), h1 = obj.getH();
        if (x + w >= x1 && x <= x1 + w1) {
            if (y + h >= y1 && y <= y1 + h1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return x + " " + y + " " + w + " " + h;
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
