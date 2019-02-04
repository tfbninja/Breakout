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
    private double maxVel;
    
    public Ball() {
        super();
    }
    
    public Ball(double x, double y, double r, Color color, double xVel, double yVel) {
        super(x, y, r, r);
        this.color = color;
        this.xVel = xVel;
        this.yVel = yVel;
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
    
    @Override
    public void draw(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(color);
        gc.fillOval(super.getX(), super.getY(), super.getW(), super.getH());
    }
    
    @Override
    public void onCollision(GameElement obj) {
        
    }
    
    @Override
    public boolean checkCollision(GameElement obj) {
        return false;
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
