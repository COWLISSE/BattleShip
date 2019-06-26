package ca.qc.cegeptr.mat1892498.battleship.controllers;

import ca.qc.cegeptr.mat1892498.battleship.BattleShip;
import ca.qc.cegeptr.mat1892498.battleship.game.GameManager;
import ca.qc.cegeptr.mat1892498.battleship.game.boards.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    private @FXML GridPane grid;
    public ScrollPane scrollPane;
    public TextField message;
    public Button send;
    public Button endGame;
    public VBox vbox;
    public AnchorPane anchorPane;
    public Label count;
    private int time = 30;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            GameManager.addGrid(grid);
            ((Player) GameManager.getBoards().get("player")).drawBoats();
            GameManager.addController(this);
        });
        sendMessage();
        endGame();
        countdown();


        grid.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            int x = (int) (event.getX() * 10 / grid.getWidth());
            int y = (int) (event.getY() * 10 / grid.getHeight());
            BattleShip.sendPacket("{'hit': '" + x + "," + y + "'}");
        });

    }


    private void endGame() {
        endGame.setOnAction(event -> Platform.exit());
    }

    private void sendMessage() {
        message.setPromptText("Type a message...");
        send.setOnAction(event -> {
            BattleShip.sendPacket("{'chat': '" + message.getText() + "'}");
//            Board.displayMessage(this, message.getText(), MainController.username);
            GameManager.chat(MainController.username, message.getText());
        });

        message.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                BattleShip.sendPacket("{'chat': '" + message.getText() + "'}");
                GameManager.chat(MainController.username, message.getText());
//                Board.displayMessage(this, message.getText(), MainController.username);
            }
        });
    }

    private void countdown() {
        Timeline animation = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (time > 0)
                time--;
            count.setText(String.valueOf(time));
        }));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

}
