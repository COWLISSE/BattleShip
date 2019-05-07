package ca.qc.cegeptr.mat1892498.battleship.controllers;

import ca.qc.cegeptr.mat1892498.battleship.BattleShip;
import ca.qc.cegeptr.mat1892498.battleship.game.GameManager;
import ca.qc.cegeptr.mat1892498.battleship.game.boards.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    private @FXML GridPane grid;
    private @FXML Button exit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(()->{
            GameManager.addGrid(grid);
            ((Player) GameManager.getBoards().get("player")).drawBoats();
        });

        grid.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            int x = (int) (event.getX() * 10 / grid.getWidth());
            int y = (int) (event.getY() * 10 / grid.getHeight());
            BattleShip.sendPacket("{'hit': '" + x + "," + y + "'}");
        });

        exit.setOnAction(e -> BattleShip.primary.close());
    }

}
