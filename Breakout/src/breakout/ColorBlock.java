package breakout;

import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 *
 * @author Tim Barber
 */
public class ColorBlock extends Block {

    public static final ArrayList<Color> colors = new ArrayList<Color>() {
        {
            add(Color.DARKMAGENTA);
            add(Color.DODGERBLUE);
            add(Color.GREENYELLOW);
            add(Color.GOLD);
            add(Color.ORANGERED);
            add(Color.MAROON);
        }
    };

    public ColorBlock(double x, double y, double w, double h, int maxHits) {
        super(x, y, w, h, colors.get(maxHits % colors.size()));
        super.setMaxHits(maxHits);
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
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void update() {
        if (super.getHitCount() >= super.getMaxHits()) {
            super.destroy();
        }
        super.setColor(colors.get(super.getMaxHits() >= colors.size() ? colors.size() - 1 : super.getMaxHits()));
    }
}
