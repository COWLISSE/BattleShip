package ca.qc.cegeptr.mat1892498.battleship.game.boards;

import ca.qc.cegeptr.mat1892498.battleship.boats.Boat;
import ca.qc.cegeptr.mat1892498.battleship.boats.BoatManager;
import ca.qc.cegeptr.mat1892498.battleship.game.GameManager;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.concurrent.atomic.AtomicInteger;

public class Player extends Board {

    private boolean drawn = false;

    public Player() {
        super();
    }

    public void drawBoats(){
        if(!drawn) {
            if (GameManager.getGrids().get("player") != null) {
                AtomicInteger index = new AtomicInteger();
                Boat.getBoatPos().forEach(boat -> {
                    for (String s : boat) {
                        String[] pos = s.split(",");
                        Rectangle rect = new Rectangle(GameManager.getGrids().get("player").getWidth() / 10, GameManager.getGrids().get("player").getHeight() / 10);
                        rect.setFill(BoatManager.getBoats().get(index.get()).getColor().getValue());
                        rect.setEffect(new DropShadow(10, Color.BLACK));
                        GameManager.getGrids().get("player").add(rect, Integer.parseInt(pos[0]), Integer.parseInt(pos[1]));
                    }
                    index.getAndIncrement();
                });
                drawn = true;
            }
        }
    }

}
