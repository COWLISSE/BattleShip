package ca.qc.cegeptr.mat1892498.battleship.controllers;

import ca.qc.cegeptr.mat1892498.battleship.BattleShip;
import ca.qc.cegeptr.mat1892498.battleship.boats.*;
import ca.qc.cegeptr.mat1892498.battleship.components.AlertBox;
import ca.qc.cegeptr.mat1892498.battleship.components.GameType;
import ca.qc.cegeptr.mat1892498.battleship.socket.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static ca.qc.cegeptr.mat1892498.battleship.boats.Dirs.*;

public class BoatSelectorController implements Initializable {

    private @FXML GridPane grid;
    private @FXML RadioButton carrier, battleship, cruiser, submarine, destroyer;
    private @FXML Button rotation, left, right, start;
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
        BoatManager.addBoat(new Boat(Boats.CARRIER, carrier));
        BoatManager.addBoat(new Boat(Boats.BATTLESHIP, battleship));
        BoatManager.addBoat(new Boat(Boats.CRUISER, cruiser));
        BoatManager.addBoat(new Boat(Boats.SUBMARINE, submarine));
        BoatManager.addBoat(new Boat(Boats.DESTROYER, destroyer));
        placingBoats();
        rotate();
        translate();
        play();
    }

    private void play(){
        start.setOnAction(e -> {
            if(Client.SERVER != null){
                boatJsons.clear();
                for(int i = 0; i < 5; i++) {
                    if(!Arrays.toString(Boat.getBoatPos().get(i)).contains("null"))
                        boatJsons.add(new BoatJson(Boat.getBoatPos().get(i)));
                }
                if(boatJsons.size() == 5) {
                    String json = "{'boats': " + Client.GSON.toJson(boatJsons) + "}";
                    BattleShip.sendPacket(json);
                    GameType.choiceSelector();
                    AlertBox.displayText("Queue", "You're currently in queue, please wait !");
                }else AlertBox.display("Error!", "Boats are not all placed !");
            }else AlertBox.display("Error!", "The server is offline");
        });
    }

    private void changingScene(ActionEvent event) throws IOException {
        Parent game = FXMLLoader.load(getClass().getResource("/ca/qc/cegeptr/mat1892498/battleship/layouts/game.fxml"));
        Scene gameBoard = new Scene(game);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("BattleShip - Game");
        stage.setScene(gameBoard);
        stage.show();
    }

    private void placingBoats() {
        grid.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            BoatManager.valid = false;
            int i;
            x = (int) (event.getX() * 10 / grid.getWidth());
            y = (int) (event.getY() * 10 / grid.getHeight());

            for (i = 0; i < BoatManager.getBoats().size(); i++)
                if (BoatManager.getBoats().get(i).getBtn().isSelected()) {
                    size = BoatManager.getBoats().get(i).getType().getLength();
                    if (x + size <= 10) {
                        freq[index = i]++;
                        BoatManager.getBoats().get(index).getType().setDirection(EAST);
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
            BoatManager.valid = false;
            if (isSelected()) {
                int i;
                setXY();
                switch (BoatManager.getBoats().get(index).getType().getDirection()) {
                    case EAST:
                        if (y + size <= 10) {
                            for (i = 0; i < size; i++) {
                                grid.getChildren().remove(boat.get(index)[i]);
                                x = Integer.parseInt(Boat.getBoatPos().get(index)[i].split(",")[0]) - i;
                                increaseYCord(i, x, rectangles, boatPosTemp);
                            }
                            boat.set(index, rectangles);
                            Boat.getBoatPos().set(index, boatPosTemp);
                            BoatManager.getBoats().get(index).getType().setDirection(SOUTH);
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
                            BoatManager.getBoats().get(index).getType().setDirection(WEST);
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
                            BoatManager.getBoats().get(index).getType().setDirection(NORTH);
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
                            BoatManager.getBoats().get(index).getType().setDirection(EAST);
                        }
                }
                if (freq[index] == 1)
                    isSuperposed(Boat.getBoatPos().get(index));
            }
        });
    }

    private void translate() {
        right.setOnAction(event -> {
            BoatManager.valid = false;
            if (isSelected()) {
                int i;
                setXY();
                if (validateTranslation(0, BoatManager.getBoats().get(index).getType().getDirection())) {
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
                if (validateTranslation(1, BoatManager.getBoats().get(index).getType().getDirection())) {
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
        for(int j = 0; j < BoatManager.getBoats().size(); j++){
            if(BoatManager.getBoats().get(j).getBtn().isSelected())
                color = BoatManager.getBoats().get(j).getColor();
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
        for (int i = 0; i < BoatManager.getBoats().size(); i++)
            if (BoatManager.getBoats().get(i).getBtn().isSelected()) {
                this.size = BoatManager.getBoats().get(i).getType().getLength();
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
        for (int i = 0; i < BoatManager.getBoats().size(); i++)
            if (BoatManager.getBoats().get(i).getBtn().isSelected())
                index = i;
        return !Arrays.toString(Boat.getBoatPos().get(index)).contains("null");
    }

    private void isSuperposed(String[] xy) {
        boolean isSuperposed = false;
        for (int i = 0; i < Boat.getBoatPos().size(); i++)
            if (i != index)
                for (String s : xy)
                    if (Arrays.toString(Boat.getBoatPos().get(i)).contains(s))
                        isSuperposed = true;

        if (isSuperposed) {
            for (int i = 0; i < size; i++)
                grid.getChildren().remove(boat.get(index)[i]);
            Boat.getBoatPos().set(index, null);
            freq[index] = 0;
        }
    }
}