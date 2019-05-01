package ca.qc.cegeptr.mat1892498.battleship.game;

import ca.qc.cegeptr.mat1892498.battleship.BattleShip;
import ca.qc.cegeptr.mat1892498.battleship.boats.Boat;
import ca.qc.cegeptr.mat1892498.battleship.boats.BoatManager;
import ca.qc.cegeptr.mat1892498.battleship.controllers.GameController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {

    public Game() {
        this.prepareWindow();
    }

    public void prepareWindow() {
        Platform.runLater(() -> {
            try {
                Parent layout = FXMLLoader.load(getClass().getResource("/ca/qc/cegeptr/mat1892498/battleship/layouts/game.fxml"));
                BattleShip.primary.setScene(new Scene(layout));
                BattleShip.primary.setTitle("Game...");
                showBoard();
                clearGrid();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void showBoard(){
        AtomicInteger index = new AtomicInteger();
        GridPane grid = GameController.getInstance().getGrid();
        Boat.getBoatPos().forEach(boat -> {
            for(int i = 0; i < boat.length; i++){
                String[] pos = boat[i].split(",");
                Rectangle rect = new Rectangle(grid.getWidth() / 10, grid.getHeight() / 10);
                rect.setFill(BoatManager.getBoats().get(index.get()).getColor());
                rect.setEffect(new DropShadow(10, Color.BLACK));
                grid.add(rect, Integer.parseInt(pos[0]), Integer.parseInt(pos[1]));
            }
            index.getAndIncrement();
        });
    }

    public void clearGrid(){
        Node lines = GameController.getInstance().getGrid().getChildren().get(0);
        GameController.getInstance().getGrid().getChildren().clear();
        GameController.getInstance().getGrid().getChildren().add(lines);
    }

}