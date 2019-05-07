package ca.qc.cegeptr.mat1892498.battleship.components;

import ca.qc.cegeptr.mat1892498.battleship.BattleShip;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameType {

    public static void choiceSelector(){
        Stage window = new Stage();
//        window.getIcons().add(new Image(AlertBox.class.getResourceAsStream("../layouts/images/battleship.png")));
        window.getIcons().add(new Image(AlertBox.class.getResourceAsStream("/ca/qc/cegeptr/mat1892498/battleship/layouts/images/battleship.png")));
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Game mode selector");
        window.setMinWidth(250);

        Button random = new Button("Random");
        random.setStyle("-fx-background-color:#689F38; -fx-padding: 6px 12px; -fx-text-fill:white; -fx-background-radius:0;");
        random.setOnAction(e -> {
            BattleShip.sendPacket("{'game': 'random'}");
            window.close();
        });

        Button party = new Button("Party");
        party.setStyle("-fx-background-color:#689F38; -fx-padding: 6px 12px; -fx-text-fill:white; -fx-background-radius:0;");
        party.setOnAction(e -> {
            BattleShip.sendPacket("{'game': 'party'}");
            window.close();
        });

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 12px; -fx-background-color: #212121;");

        HBox hbox = new HBox(10);

        Label error = new Label("Please select a game mode");
        error.setStyle("-fx-text-fill:white; -fx-font-weight: bold;");

        layout.getChildren().addAll(error, hbox);
        hbox.getChildren().addAll(random, party);
        layout.setAlignment(Pos.CENTER);
        hbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

}
