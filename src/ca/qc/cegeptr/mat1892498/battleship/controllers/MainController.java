package ca.qc.cegeptr.mat1892498.battleship.controllers;

import ca.qc.cegeptr.mat1892498.battleship.BattleShip;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public @FXML TextField usernameInput;
    private @FXML Pane main;
    private @FXML Pane menu;
    private @FXML Label label;
    public @FXML Button startButton;
    public static String username;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameInput.setStyle("-fx-background-color:transparent; -fx-border-width:2px; -fx-border-color:white; -fx-padding:6px 12px;");
    }

    public void changingScene(ActionEvent event) throws IOException {
        username = (usernameInput.getText().length() < 25)? usernameInput.getText() : "";
        if (!username.equals("")) {
            BattleShip.sendPacket("{'username': '" + username + "'}");
            Parent boat_selector = FXMLLoader.load(getClass().getResource("/ca/qc/cegeptr/mat1892498/battleship/layouts/boat_selector.fxml"));
            Scene boatSelector = new Scene(boat_selector);
            Stage boatSelectorStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            boatSelectorStage.setTitle("BattleShip - Boat selector");
            boatSelectorStage.setScene(boatSelector);
            boatSelectorStage.show();
        }
    }
}
