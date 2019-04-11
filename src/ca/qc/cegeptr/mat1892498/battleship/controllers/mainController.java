package ca.qc.cegeptr.mat1892498.battleship.controllers;

import ca.qc.cegeptr.mat1892498.battleship.BattleShip;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class    mainController implements Initializable {

    public @FXML CheckBox lang_fr;
    public @FXML CheckBox lang_en;
    private @FXML Pane main;
    private @FXML Pane menu;
    private @FXML Label label;
    public @FXML Button startButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lang_en.fire();
        lang_fr.selectedProperty().addListener((o, t, l) -> {
            if (o.getValue() && lang_en.isSelected()) lang_en.fire();
        });
        lang_en.selectedProperty().addListener((o, t, l) -> {
            if (o.getValue() && lang_fr.isSelected()) lang_fr.fire();
        });
    }

    public void changingScene(ActionEvent event) throws IOException {
        String path = System.getProperty("user.dir");
        System.out.println(path);
        Parent boat_selector = FXMLLoader.load(getClass().getResource("../layouts/boatSelector.fxml"));
        Scene boatSelector = new Scene(boat_selector);
        BattleShip.primary.setScene(boatSelector);
        BattleShip.primary.setTitle("BattleShip - Boat Selector");
        BattleShip.primary.setScene(boatSelector);
        BattleShip.primary.show();
//        Client.SERVER.writeAndFlush(new Response("{'message': 'changing scene'}"));
    }
}
