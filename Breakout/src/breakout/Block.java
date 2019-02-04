package breakout;

import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author Tim Barber
 */
public class Block extends GameElement implements Renderable {

    private boolean exists = true;
    private Color color = Color.WHITE;

    public Block() {
        super();
    }

    public Block(double x, double y, double w, double h, Color color) {
        super(x, y, w, h);
        this.color = color;
    }

    public void destroy() {
        exists = false;
        super.setH(0);
        super.setW(0);
        super.setY(0);
        super.setX(0);
    }

    @Override
    public void draw(Canvas canvas) {
        if (exists) {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setFill(color);
            gc.fillRect(super.getX(), super.getY(), super.getW(), super.getH());
        }
    }

    @Override
    public void onCollision(GameElement obj) {
        if (!obj.getClass().equals(Ball.class.getClass())) {

        } else {
            destroy();
        }
    }

    @Override
    public boolean checkCollision(GameElement obj) {
        return false;
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
