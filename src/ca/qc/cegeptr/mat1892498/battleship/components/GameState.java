package ca.qc.cegeptr.mat1892498.battleship.components;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameState {

    public static void display(String title, String message){
        Platform.runLater(() -> {
            Stage window = AlertBox.createStage();
            window.setTitle(title);
            window.setMinWidth(600);
            Button close = new Button("Go back to main menu");
            close.setStyle("-fx-background-color:#689F38; -fx-padding: 6px 12px; -fx-text-fill:white; -fx-background-radius:0;");
            close.setOnAction(e -> window.close());

            VBox layout = new VBox(10);
            layout.setStyle("-fx-padding:12px; -fx-background-color:#212121;");
            layout.setAlignment(Pos.CENTER);

            Label label = new Label(message);
            label.setStyle("-fx-font-size:48px; -fx-text-fill:white;");

            layout.getChildren().addAll(label, close);
            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
        });
    }

}
