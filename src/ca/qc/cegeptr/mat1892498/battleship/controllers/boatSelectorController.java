package ca.qc.cegeptr.mat1892498.battleship.controllers;

import ca.qc.cegeptr.mat1892498.battleship.BattleShip;
import ca.qc.cegeptr.mat1892498.battleship.boats.*;
import ca.qc.cegeptr.mat1892498.battleship.socket.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.*;

import static ca.qc.cegeptr.mat1892498.battleship.boats.Dirs.*;

public class boatSelectorController implements Initializable {

    private @FXML GridPane grid;
    private @FXML RadioButton carrier, battleship, cruiser, submarine, destroyer;
    private @FXML Button rotation, left, right, start;
    private BoatManager manager = new BoatManager();
    private int[] freq = new int[5];
    private String[] boatPosTemp;
    private int size, index, x, y;
    private Rectangle[] rectangles;
    private ArrayList<Rectangle[]> boat = new ArrayList<>(Arrays.asList(
            new Rectangle[5],
            new Rectangle[4],
            new Rectangle[3],
            new Rectangle[3],
            new Rectangle[2]
    ));
    private List<BoatJson> boatJsons = new ArrayList<>();

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
        play();
    }

    private void play(){
        start.setOnAction(e -> {
            if(Client.SERVER != null){
                boolean placed = true;
                boatJsons.clear();
                for(int i = 0; i < 5; i++) {
                    for(int j = 0; j < Boat.getBoatPos().get(i).length; j++)
                        if(Boat.getBoatPos().get(i)[j] == null) placed = false;
                    if(placed)
                        boatJsons.add(new BoatJson(Boat.getBoatPos().get(i)));
                }
                if(boatJsons.size() == 5) {
                    String json = "{'boats': " + Client.GSON.toJson(boatJsons) + "}";
                    BattleShip.sendPacket(json);
                }else System.out.println("BOATS ARE NOT ALL PLACED");
                // TODO: Add error alert
            }else{
                System.out.println("SERVER OFF");
                //TODO: Add error alert
            }
        });
    }

    private void placingBoats() {
        grid.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            int i;
            x = (int) (event.getX() * 10 / grid.getWidth());
            y = (int) (event.getY() * 10 / grid.getHeight());

            for (i = 0; i < manager.getBoats().size(); i++)
                if (manager.getBoats().get(i).getBtn().isSelected()) {
                    size = manager.getBoats().get(i).getType().getLength();
                    if (x + size <= 10) {
                        freq[index = i]++;
                        manager.getBoats().get(index).getType().setDirection(EAST);
                    }
                }

            rectangles = new Rectangle[size];
            boatPosTemp = new String[size];

            if (freq[index] == 1 && (x + size <= 10)) {
                for (i = 0; i < rectangles.length; i++) {
                    createRectangle(i, rectangles);
                    boatPosTemp[i] = (x + i) + "," + y;
                    grid.add(rectangles[i], x + i, y);
                }
                boat.set(index, rectangles);
                Boat.getBoatPos().set(index, boatPosTemp);
            } else {
                for (i = 0; i < size; i++)
                    grid.getChildren().remove(boat.get(index)[i]);
                Boat.getBoatPos().set(index, null);
                freq[index] = 0;
            }
            if (freq[index] == 1)
                isSuperposed(Boat.getBoatPos().get(index));
        });
    }

    private void rotate() {
        rotation.setOnAction(event -> {
            if (isSelected()) {
                int i;
                setXY();
                switch (manager.getBoats().get(index).getType().getDirection()) {
                    case EAST:
                        if (y + size <= 10) {
                            for (i = 0; i < size; i++) {
                                grid.getChildren().remove(boat.get(index)[i]);
                                x = Integer.parseInt(Boat.getBoatPos().get(index)[i].split(",")[0]) - i;
                                increaseYCord(i, x, rectangles, boatPosTemp);
                            }
                            boat.set(index, rectangles);
                            Boat.getBoatPos().set(index, boatPosTemp);
                            manager.getBoats().get(index).getType().setDirection(SOUTH);
                        }
                        break;
                    case SOUTH:
                        if (x - size + 1 >= 0) {
                            for (i = 0; i < size; i++) {
                                grid.getChildren().remove(boat.get(index)[i]);
                                x = Integer.parseInt(Boat.getBoatPos().get(index)[i].split(",")[0]) - i;
                                decreaseYCord(i, x, rectangles, boatPosTemp);
                            }
                            boat.set(index, rectangles);
                            Boat.getBoatPos().set(index, boatPosTemp);
                            manager.getBoats().get(index).getType().setDirection(WEST);
                        }
                        break;
                    case WEST:
                        if (y - size + 1 >= 0) {
                            for (i = 0; i < size; i++) {
                                grid.getChildren().remove(boat.get(index)[i]);
                                x = Integer.parseInt(Boat.getBoatPos().get(index)[i].split(",")[0]) + i;
                                decreaseYCord(i, x, rectangles, boatPosTemp);
                            }
                            boat.set(index, rectangles);
                            Boat.getBoatPos().set(index, boatPosTemp);
                            manager.getBoats().get(index).getType().setDirection(NORTH);
                        }
                        break;
                    case NORTH:
                        if (x + size <= 10) {
                            for (i = 0; i < size; i++) {
                                grid.getChildren().remove(boat.get(index)[i]);
                                x = Integer.parseInt(Boat.getBoatPos().get(index)[i].split(",")[0]) + i;
                                increaseYCord(i, x, rectangles, boatPosTemp);
                            }
                            boat.set(index, rectangles);
                            Boat.getBoatPos().set(index, boatPosTemp);
                            manager.getBoats().get(index).getType().setDirection(EAST);
                        }
                }
                if (freq[index] == 1)
                    isSuperposed(Boat.getBoatPos().get(index));
            }
        });
    }

    private void translate() {
        right.setOnAction(event -> {
            if (isSelected()) {
                int i;
                setXY();
                if (validateTranslation(0, manager.getBoats().get(index).getType().getDirection())) {
                    for (i = 0; i < size; i++) {
                        grid.getChildren().remove(boat.get(index)[i]);
                        x = Integer.parseInt(Boat.getBoatPos().get(index)[i].split(",")[0]) + 1;
                        addRectangles2Grid(i, x, rectangles, boatPosTemp);
                    }
                    boat.set(index, rectangles);
                    Boat.getBoatPos().set(index, boatPosTemp);
                }
                if (freq[index] == 1)
                    isSuperposed(Boat.getBoatPos().get(index));
            }
        });

        left.setOnAction(event -> {
            if (isSelected()) {
                int i;
                setXY();
                if (validateTranslation(1, manager.getBoats().get(index).getType().getDirection())) {
                    for (i = 0; i < size; i++) {
                        grid.getChildren().remove(boat.get(index)[i]);
                        x = Integer.parseInt(Boat.getBoatPos().get(index)[i].split(",")[0]) - 1;
                        addRectangles2Grid(i, x, rectangles, boatPosTemp);
                    }
                    boat.set(index, rectangles);
                    Boat.getBoatPos().set(index, boatPosTemp);
                }
                if (freq[index] == 1)
                    isSuperposed(Boat.getBoatPos().get(index));
            }
        });
    }

    private void increaseYCord(int i, int x, Rectangle[] rectangles, String[] boatPos) {
        y = Integer.parseInt(Boat.getBoatPos().get(index)[i].split(",")[1]) + i;

        createRectangle(i, rectangles);
        boatPos[i] = x + "," + y;
        grid.add(rectangles[i], x, y);
    }

    private void decreaseYCord(int i, int x, Rectangle[] rectangles, String[] boatPos) {
        y = Integer.parseInt(Boat.getBoatPos().get(index)[i].split(",")[1]) - i;

        createRectangle(i, rectangles);
        boatPos[i] = x + "," + y;
        grid.add(rectangles[i], x, y);
    }

    private void createRectangle(int i, Rectangle[] rectangles) {
        Paint color = Color.color(0,0,0);
        for(int j = 0; j < manager.getBoats().size(); j++){
            if(manager.getBoats().get(j).getBtn().isSelected())
                color = manager.getBoats().get(j).getColor();
        }
        rectangles[i] = new Rectangle(grid.getWidth() / 10, grid.getHeight() / 10);
        rectangles[i].setFill(color);
        rectangles[i].setEffect(new DropShadow(10, Color.BLACK));
    }

    private void addRectangles2Grid(int i, int x, Rectangle[] rectangles, String[] boatPos) {
        y = Integer.parseInt(Boat.getBoatPos().get(index)[i].split(",")[1]);

        createRectangle(i, rectangles);
        boatPos[i] = x + "," + y;
        grid.add(rectangles[i], x, y);
    }

    private void setIndexSize() {
        for (int i = 0; i < manager.getBoats().size(); i++)
            if (manager.getBoats().get(i).getBtn().isSelected()) {
                this.size = manager.getBoats().get(i).getType().getLength();
                this.index = i;
            }
    }

    private void setXY() {
        setIndexSize();
        rectangles = new Rectangle[size];
        boatPosTemp = new String[size];

        x = Integer.parseInt(Boat.getBoatPos().get(index)[0].split(",")[0]);
        y = Integer.parseInt(Boat.getBoatPos().get(index)[size - 1].split(",")[1]);
    }

    private boolean validateTranslation(int choice, Dirs dirs) {
        switch (dirs) {
            case EAST:
                return (choice == 0) ? (x + size < 10) : (x - 1 >= 0);
            case WEST:
                return (choice == 0) ? (x + 1 < 10) : (x - size >= 0);
            case NORTH:
            case SOUTH:
                return (choice == 0) ? (x + 1 < 10) : (x - 1 >= 0);
        }
        return false;
    }

    private boolean isSelected() {
        for (int i = 0; i < manager.getBoats().size(); i++)
            if (manager.getBoats().get(i).getBtn().isSelected())
                index = i;
        return !Arrays.toString(Boat.getBoatPos().get(index)).contains("null");
    }

    private void isSuperposed(String[] xy) {
        boolean isSuperposed = false;
        for (int i = 0; i < Boat.getBoatPos().size(); i++)
            if (i != index)
                for (String s : xy)
                    if (Arrays.toString(Boat.getBoatPos().get(i)).contains(s)) {
                        System.out.println(Arrays.toString(Boat.getBoatPos().get(i)));
                        System.out.println(s);
                        isSuperposed = true;
                    }

        if (isSuperposed) {
            for (int i = 0; i < size; i++)
                grid.getChildren().remove(boat.get(index)[i]);
            Boat.getBoatPos().set(index, null);
            freq[index] = 0;
        }
    }
}