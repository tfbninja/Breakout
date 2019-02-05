package breakout;

import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 *
 * @author Tim Barber
 */
public class ColorBlock extends Block {

    private ArrayList<Renderable> objs = new ArrayList<>();

    public static final int MODE_STATIC = 0;
    public static final int MODE_PULSATING = 1;
    public static final int MODE_STEP = 2;
    private int mode = 0;
    private int[] rgbMin = {0, 0, 0};
    private int[] rgbMax = {255, 255, 255};
    private boolean gettingDarker = true;
    private ArrayList<Color> colors = new ArrayList<>();
    private int currentColorIndex = 0;

    public ColorBlock(double x, double y, double w, double h, int mode, Color initial) {
        super(x, y, w, h, initial);
        this.mode = mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setRGBBounds(int[] min, int[] max) {
        rgbMin = min;
        rgbMax = max;
    }

    public void setGettingDarker(boolean val) {
        gettingDarker = val;
    }

    public void setColor(ArrayList<Color> list) {
        colors = list;
    }

    public boolean hasLargerValue(Color color) {
        return color.getRed() > rgbMax[0] || color.getGreen() > rgbMax[1] || color.getBlue() > rgbMax[2];
    }

    public boolean hasSmallerValue(Color color) {
        return color.getRed() < rgbMin[0] || color.getGreen() < rgbMin[1] || color.getBlue() < rgbMin[2];
    }

    public Color incrementColor(Color color) {
        return Color.rgb((int) color.getRed() + 1, (int) color.getGreen() + 1, (int) color.getBlue() + 1, color.getOpacity());
    }

    public Color decrementColor(Color color) {
        return Color.rgb((int) color.getRed() - 1, (int) color.getGreen() - 1, (int) color.getBlue() - 1, color.getOpacity());
    }

    public void nextColorIndex() {
        if (currentColorIndex + 1 >= colors.size()) {
            currentColorIndex = 0;
        } else {
            currentColorIndex++;
        }
    }

    @Override
    public boolean checkCollision(GameElement obj) {
        if (super.exists()) {
            double x = super.getX(), y = super.getY(), w = super.getW(), h = super.getH();
            double x1 = obj.getX(), y1 = obj.getY(), w1 = obj.getW(), h1 = obj.getH();
            if (x + w > x1 && x < x1 + w1) {
                if (y + h > y1 && y < y1 + h1) {
                    //System.out.println((x + w) + " is more than " + x1 + " and " + x + " is less than " + (x1 + w1));
                    //System.out.println((y + h) + " is more than " + y1 + " and " + y + " is less than " + (y1 + h1));
                    changeY(-5);
                    return true;
                }
            }
        }
        return false;
    }

    public void updateColor() {
        switch (mode) {
            case 0:
                super.setColor(super.getColor());
            case 1:
                if (hasSmallerValue(super.getColor()) || !gettingDarker) {
                    super.setColor(incrementColor(super.getColor()));
                    if (hasSmallerValue(super.getColor())) {
                        gettingDarker = false;
                    }

                } else if (hasLargerValue(super.getColor()) || gettingDarker) {
                    super.setColor(decrementColor(super.getColor()));
                    if (hasLargerValue(super.getColor())) {
                        gettingDarker = true;
                    }
                }
            case 2:
                super.setColor(colors.get(currentColorIndex));
                nextColorIndex();
        }
    }
}
