package ca.qc.cegeptr.mat1892498.battleship.components;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    public static void display(String title, String message){
        Stage window = createStage();
        Platform.runLater(() -> {
            window.setTitle(title);

            Button close = new Button("Close");
            close.setStyle("-fx-background-color:#689F38; -fx-padding: 6px 12px; -fx-text-fill:white; -fx-background-radius:0;");
            close.setOnAction(e -> window.close());

            VBox layout = new VBox(10);
            layout.setStyle("-fx-padding: 12px; -fx-background-color: #212121;");

            Label error = new Label(message);
            error.setStyle("-fx-text-fill:white; -fx-font-weight: bold;");

            layout.getChildren().addAll(error, close);
            layout.setAlignment(Pos.CENTER);
            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
        });
    }

    public static void displayText(String title, String message){
        Stage window = createStage();
        Platform.runLater(() -> {
            window.setTitle(title);

            Button close = new Button("Understood !");
            close.setStyle("-fx-background-color:#689F38; -fx-padding: 6px 12px; -fx-text-fill:white; -fx-background-radius:0;");
            close.setOnAction(e -> window.close());

            VBox layout = new VBox(10);
            layout.setStyle("-fx-padding:12px; -fx-background-color:#212121;");
            layout.setAlignment(Pos.CENTER);

            Label label = new Label(message);
            label.setStyle("-fx-font-size:18px; -fx-text-fill:white;");

            layout.getChildren().addAll(label, close);
            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
        });
    }

    private static Stage createStage(){
        Stage window = new Stage();
        window.getIcons().add(new Image(AlertBox.class.getResourceAsStream("/ca/qc/cegeptr/mat1892498/battleship/layouts/images/battleship.png")));
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(250);
        return window;
    }

}