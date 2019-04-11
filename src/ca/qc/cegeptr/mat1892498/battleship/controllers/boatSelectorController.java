package ca.qc.cegeptr.mat1892498.battleship.controllers;

import ca.qc.cegeptr.mat1892498.battleship.BattleShip;
import ca.qc.cegeptr.mat1892498.battleship.boats.*;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.*;

import static ca.qc.cegeptr.mat1892498.battleship.boats.Dirs.*;

public class boatSelectorController implements Initializable {

    private @FXML
    GridPane grid;
    private @FXML
    RadioButton carrier, battleship, cruiser, submarine, destroyer;
    private @FXML Button rotation, left, right, play;
    private BoatManager manager = new BoatManager();
    private int[] freq = new int[5];
    private ArrayList<Rectangle[]> boat = new ArrayList<>(Arrays.asList(
            new Rectangle[5],
            new Rectangle[4],
            new Rectangle[3],
            new Rectangle[3],
            new Rectangle[2]
    ));
    BoatJson[] boatJsons = new BoatJson[5];

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manager.addBoat(new Boat(Boats.CARRIER, carrier));
        manager.addBoat(new Boat(Boats.BATTLESHIP, battleship));
        manager.addBoat(new Boat(Boats.CRUISER, cruiser));
        manager.addBoat(new Boat(Boats.SUBMARINE, submarine));
        manager.addBoat(new Boat(Boats.DESTROYER, destroyer));
        placingBoats();
        rotate();
        translate();

        play.setOnAction(e -> {
            for(int i = 0; i < boatJsons.length; i++)
                boatJsons[i] = new BoatJson(Boat.getBoatPos().get(i));
            Gson gson = new Gson();
            String json = "{'boats': " + gson.toJson(boatJsons) + "}";
            BattleShip.sendPacket(json);
        });
    }

    private void placingBoats() {
        grid.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            int i, j = 0, index = 0, x = (int) (event.getX() * 10 / grid.getWidth()), y = (int) (event.getY() * 10 / grid.getHeight());

            for (i = 0; i < manager.getBoats().size(); i++)
                if (manager.getBoats().get(i).getBtn().isSelected()) {
                    j = manager.getBoats().get(i).getType().getLength();
                    if (x + j <= 10) {
                        freq[index = i]++;
                        manager.getBoats().get(index).getType().setDirection(EAST);
                    }
                }

            Rectangle[] rectangles = new Rectangle[j];
            String[] boatPos = new String[j];

            if (freq[index] == 1 && (x + j <= 10)) {
                for (i = 0; i < rectangles.length; i++) {
                    createRectangle(i, rectangles);
                    boatPos[i] = (x + i) + "," + y;
                    grid.add(rectangles[i], x + i, y);
                }
                boat.set(index, rectangles);
                Boat.getBoatPos().set(index, boatPos);
            } else {
                for (i = 0; i < j; i++)
                    grid.getChildren().remove(boat.get(index)[i]);
                Boat.getBoatPos().set(index, null);
                freq[index] = 0;
            }
        });
    }

    private void rotate() {
        rotation.setOnAction(event -> {
            int index = 0, i, j = 0, x , y;

            for (i = 0; i < manager.getBoats().size(); i++)
                if (manager.getBoats().get(i).getBtn().isSelected()) {
                    j = manager.getBoats().get(i).getType().getLength();
                    index = i;
                }

            x = Integer.parseInt(Boat.getBoatPos().get(index)[0].split(",")[0]);
            y = Integer.parseInt(Boat.getBoatPos().get(index)[0].split(",")[1]);
            Rectangle[] rectangles = new Rectangle[j];
            String[] boatPos = new String[j];

            switch (manager.getBoats().get(index).getType().getDirection()) {
                case EAST:
                    if (y + j <= 10) {
                        for (i = 0; i < j; i++) {
                            grid.getChildren().remove(boat.get(index)[i]);
                            x = Integer.parseInt(Boat.getBoatPos().get(index)[i].split(",")[0]) - i;
                            y = Integer.parseInt(Boat.getBoatPos().get(index)[i].split(",")[1]) + i;

                            createRectangle(i, rectangles);
                            boatPos[i] = x + "," + y;
                            grid.add(rectangles[i], x, y);
                        }
                        boat.set(index, rectangles);
                        Boat.getBoatPos().set(index, boatPos);
                        manager.getBoats().get(index).getType().setDirection(SOUTH);
                    }
                    break;
                case SOUTH:
                    if (x - j + 1 >= 0) {
                        for (i = 0; i < j; i++) {
                            grid.getChildren().remove(boat.get(index)[i]);
                            x = Integer.parseInt(Boat.getBoatPos().get(index)[i].split(",")[0]) - i;
                            y = Integer.parseInt(Boat.getBoatPos().get(index)[i].split(",")[1]) - i;

                            createRectangle(i, rectangles);
                            boatPos[i] = x + "," + y;
                            grid.add(rectangles[i], x, y);
                        }
                        boat.set(index, rectangles);
                        Boat.getBoatPos().set(index, boatPos);
                        manager.getBoats().get(index).getType().setDirection(WEST);
                    }
                    break;
                case WEST:
                    if (y - j + 1 >= 0) {
                        for (i = 0; i < j; i++) {
                            grid.getChildren().remove(boat.get(index)[i]);
                            x = Integer.parseInt(Boat.getBoatPos().get(index)[i].split(",")[0]) + i;
                            y = Integer.parseInt(Boat.getBoatPos().get(index)[i].split(",")[1]) - i;

                            createRectangle(i, rectangles);
                            boatPos[i] = x + "," + y;
                            grid.add(rectangles[i], x, y);
                        }
                        boat.set(index, rectangles);
                        Boat.getBoatPos().set(index, boatPos);
                        manager.getBoats().get(index).getType().setDirection(NORTH);
                    }
                    break;
                case NORTH:
                    if (x + j <= 10) {
                        for (i = 0; i < j; i++) {
                            grid.getChildren().remove(boat.get(index)[i]);
                            x = Integer.parseInt(Boat.getBoatPos().get(index)[i].split(",")[0]) + i;
                            y = Integer.parseInt(Boat.getBoatPos().get(index)[i].split(",")[1]) + i;

                            createRectangle(i, rectangles);
                            boatPos[i] = x + "," + y;
                            grid.add(rectangles[i], x, y);
                        }
                        boat.set(index, rectangles);
                        Boat.getBoatPos().set(index, boatPos);
                        manager.getBoats().get(index).getType().setDirection(EAST);
                    }
                    break;
            }

        });
    }

    private void createRectangle(int i, Rectangle[] rectangles) {
        rectangles[i] = new Rectangle(grid.getWidth() / 10, grid.getHeight() / 10);
        rectangles[i].setFill(Color.DARKCYAN);
        rectangles[i].setEffect(new DropShadow(10, Color.BLACK));
    }

    private void translate() {
        right.setOnAction(event -> {
            int index = 0, i, j = 0, x , y = 0;

            for (i = 0; i < manager.getBoats().size(); i++)
                if (manager.getBoats().get(i).getBtn().isSelected()) {
                    j = manager.getBoats().get(i).getType().getLength();
                    index = i;
                }

            Rectangle[] rectangles = new Rectangle[j];
            String[] boatPos = new String[j];

            x = Integer.parseInt(Boat.getBoatPos().get(index)[0].split(",")[0]);
            y = Integer.parseInt(Boat.getBoatPos().get(index)[j - 1].split(",")[0]);
            if (x + 1 < 10 && y + 1 < 10) {
                for (i = 0; i < j; i++) {
                    grid.getChildren().remove(boat.get(index)[i]);
                    x = Integer.parseInt(Boat.getBoatPos().get(index)[i].split(",")[0]) + 1;
                    y = Integer.parseInt(Boat.getBoatPos().get(index)[i].split(",")[1]);

                    createRectangle(i, rectangles);
                    boatPos[i] = x + "," + y;
                    grid.add(rectangles[i], x, y);
                }
                boat.set(index, rectangles);
                Boat.getBoatPos().set(index, boatPos);
            }
        });

        left.setOnAction(event -> {
            int index = 0, i, j = 0, x , y;

            for (i = 0; i < manager.getBoats().size(); i++)
                if (manager.getBoats().get(i).getBtn().isSelected()) {
                    j = manager.getBoats().get(i).getType().getLength();
                    index = i;
                }

            Rectangle[] rectangles = new Rectangle[j];
            String[] boatPos = new String[j];

            x = Integer.parseInt(Boat.getBoatPos().get(index)[0].split(",")[0]);
            y = Integer.parseInt(Boat.getBoatPos().get(index)[j - 1].split(",")[0]);
            if (x - 1 >= 0 && y - 1 >= 0) {
                for (i = 0; i < j; i++) {
                    grid.getChildren().remove(boat.get(index)[i]);
                    x = Integer.parseInt(Boat.getBoatPos().get(index)[i].split(",")[0]) - 1;
                    y = Integer.parseInt(Boat.getBoatPos().get(index)[i].split(",")[1]);

                    createRectangle(i, rectangles);
                    boatPos[i] = x + "," + y;
                    grid.add(rectangles[i], x, y);
                }
                boat.set(index, rectangles);
                Boat.getBoatPos().set(index, boatPos);
            }
        });
    }
}