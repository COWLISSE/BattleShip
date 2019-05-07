package ca.qc.cegeptr.mat1892498.battleship.components;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicBoolean;

public class ConfirmBox {

    public static boolean display(String title, String message){
        AtomicBoolean close = new AtomicBoolean(false);
        Stage window = AlertBox.createStage();
            window.setTitle(title);

            Button yes = new Button("Yes");
            yes.setStyle("-fx-background-color:#689F38; -fx-padding: 6px 12px; -fx-text-fill:white; -fx-background-radius:0;");
            yes.setOnAction(e -> {
                window.close();
                close.set(true);
            });

            Button no = new Button("No");
            no.setStyle("-fx-background-color:#689F38; -fx-padding: 6px 12px; -fx-text-fill:white; -fx-background-radius:0;");
            no.setOnAction(e -> window.close());

            HBox btnLayout = new HBox(10);
            btnLayout.setAlignment(Pos.CENTER);
            btnLayout.getChildren().addAll(yes, no);

            VBox layout = new VBox(10);
            layout.setStyle("-fx-padding: 12px; -fx-background-color: #212121;");

            Label confirm = new Label(message);
            confirm.setStyle("-fx-text-fill:white; -fx-font-weight: bold;");

            layout.getChildren().addAll(confirm, btnLayout);
            layout.setAlignment(Pos.CENTER);
            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
        return close.get();
    }

}
