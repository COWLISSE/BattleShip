package ca.qc.cegeptr.mat1892498.battleship.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    private @FXML GridPane grid;
    public static GameController instance = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
    }

    public static GameController getInstance() {
        return instance;
    }

    public GridPane getGrid() {
        return grid;
    }
}
