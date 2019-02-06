package breakout;

import java.util.ArrayList;
import javafx.scene.canvas.Canvas;

/**
 *
 * @author Tim Barber
 */
public class GameState {

    /*
    Instance variables of a list of Renderables, a list of Updateables, and a list of GameElements that can collide.
Include an updateAll method that updates all the game elements.
Include a drawAll method that draws all the game elements
Include a collideAll method that invokes the collision detection.
     */
    private ArrayList<Renderable> renderables = new ArrayList<>();
    private ArrayList<Updateable> updateables = new ArrayList<>();
    private ArrayList<GameElement> gameElements = new ArrayList<>();
    private ArrayList<ColorBlock> breakables = new ArrayList<>();

    public GameState() {
    }

    public void addRenderable(Renderable r) {
        renderables.add(r);
    }

    public void addCollidable(GameElement g) {
        gameElements.add(g);
    }

    public void addUpdateable(Updateable u) {
        updateables.add(u);
    }

    public void addBreakable(ColorBlock c) {
        breakables.add(c);
    }

    public void updateAll() {
        updateables.forEach((u) -> {
            u.update();
        });
    }

    public void drawAll(Canvas canvas) {
        renderables.forEach((r) -> {
            r.draw(canvas);
        });
    }

    public void reset() {
        for (ColorBlock c : breakables) {
            c.reincarnate();
        }
    }

    public boolean blockStillExists() {
        boolean val = false;
        for (ColorBlock c : breakables) {
            val = val || c.exists();
        }
        return val;
    }

    public void collideAll() {
        int index = 0;
        for (GameElement obj : gameElements) {
            int index2 = 0;
            for (GameElement obj2 : gameElements) {
                if (index2 != index) { // ensures we are not double checking collisions
                    if (obj.checkCollision(obj2) && obj.exists() && obj2.exists()) {
                        obj.onCollision(obj2);
                    }
                }
                index2++;
            }
            index++;
        }
    }

}
