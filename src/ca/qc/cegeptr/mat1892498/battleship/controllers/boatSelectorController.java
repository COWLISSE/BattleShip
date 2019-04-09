package ca.qc.cegeptr.mat1892498.battleship.controllers;

import ca.qc.cegeptr.mat1892498.battleship.boats.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.*;

public class boatSelectorController implements Initializable {

    private @FXML GridPane grid;
    private @FXML RadioButton carrier, battleship, cruiser, submarine, destroyer;
    private @FXML Button rotation;
    private BoatManager manager = new BoatManager();
    private int[] freq = new int[5];
    private ArrayList<Rectangle> boat = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manager.addBoat(new Boat(Boats.CARRIER, carrier));
        manager.addBoat(new Boat(Boats.BATTLESHIP, battleship));
        manager.addBoat(new Boat(Boats.CRUISER, cruiser));
        manager.addBoat(new Boat(Boats.SUBMARINE, submarine));
        manager.addBoat(new Boat(Boats.DESTROYER, destroyer));
        placingBoats();
    }

    private void placingBoats() {
        grid.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            int j = 0, index = 0, x = (int) (event.getX() * 10 / grid.getWidth()), y = (int) (event.getY() * 10 / grid.getHeight());

            for (int i = 0; i < manager.getBoats().size(); i++)
                if (manager.getBoats().get(i).getBtn().isSelected()) {
                    j = manager.getBoats().get(i).getType().getWidth();
                    if (x + j <= 10)
                    freq[index = i]++;
                }

            Color color = new Color(Math.random(), Math.random(), Math.random(), Math.random());

            if (freq[index] == 1 && (x + j <= 10)) {
                for (int i = 0; i < j; i++) {
                    boat.add(i, new Rectangle(grid.getWidth() / 10, grid.getHeight() / 10));
                    boat.get(i).setFill(color);
                    boat.get(i).setEffect(new DropShadow(10,Color.GREY));
                    Boats.getBoatPos().add(i, x + i + "," + y);
                    grid.add(boat.get(i), x + i, y);
                }
            }
        });
    }
}
