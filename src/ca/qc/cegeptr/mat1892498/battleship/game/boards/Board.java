package ca.qc.cegeptr.mat1892498.battleship.game.boards;

import ca.qc.cegeptr.mat1892498.battleship.BattleShip;
import ca.qc.cegeptr.mat1892498.battleship.controllers.GameController;
import ca.qc.cegeptr.mat1892498.battleship.game.GameManager;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Board {

    private Scene scene;

    public Board() {
        this.prepareScene();
    }

    public void prepareScene() {
        Platform.runLater(() -> {
            try {
                Parent layout = FXMLLoader.load(GameManager.class.getResource("/ca/qc/cegeptr/mat1892498/battleship/layouts/game.fxml"));
                scene = new Scene(layout);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void show() {
        Platform.runLater(() -> {
            BattleShip.primary.setScene(scene);
            BattleShip.primary.show();
        });
    }

    public Scene getScene() {
        return scene;
    }

    public void hit(String gridName, String pos, boolean hit) {
        String[] coords = pos.split(",");
        int x = Integer.parseInt(coords[0]);
        int y = Integer.parseInt(coords[1]);
        double sWidth = GameManager.getGrids().get(gridName).getWidth() / 10;
        double sHeight = GameManager.getGrids().get(gridName).getHeight() / 10;
        Image image = new Image("/ca/qc/cegeptr/mat1892498/battleship/layouts/images/explosion.gif");
        ImageView imageView = new ImageView(image);
        Platform.runLater(() -> {
            Line line1 = new Line(sWidth * x, sHeight * y, (sWidth * x) + sWidth, (sHeight * y) + sHeight);
            Line line2 = new Line(sWidth * x, (sHeight * y) + sHeight, (sWidth * x) + sWidth, sHeight * y);
            line1.setStrokeWidth(4);
            line2.setStrokeWidth(4);
            if (hit) {
                line1.setStroke(Color.RED);
                line2.setStroke(Color.RED);
                Platform.runLater(() -> GameManager.getGrids().get(gridName).add(imageView, x, y));
                new Timer().schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                Platform.runLater(() -> {
                                    GameManager.getGrids().get(gridName).getChildren().remove(imageView);

                                    GameManager.getGrids().get(gridName).add(line1, x, y);
                                    GameManager.getGrids().get(gridName).add(line2, x, y);
                                });
                            }
                        }, 2000
                );
            } else {
                line1.setStroke(Color.WHITE);
                line2.setStroke(Color.WHITE);

                GameManager.getGrids().get(gridName).add(line1, x, y);
                GameManager.getGrids().get(gridName).add(line2, x, y);
            }
        });
    }

    public static void displayMessage(GameController controller, String content, String name) {
        Platform.runLater(() -> {
            controller.scrollPane.vvalueProperty().bind(controller.vbox.heightProperty());

            if (!content.trim().equals("") && !content.equals("")) {

//                StringBuilder res = new StringBuilder(name).append(": ");
                StringBuilder res = new StringBuilder();
                res.append(name).append(": ");
                LinkedList<String> messageQueue = new LinkedList<>(Arrays.asList(content.split("\\s+")));

                while (!messageQueue.isEmpty()) {
                    if (messageQueue.getFirst().length() <= 26) {
                        if (messageQueue.getFirst().length() + res.length() <= 25) {
                            res.append(messageQueue.getFirst()).append(" ");
                            messageQueue.removeFirst();
                        } else {
                            controller.vbox.getChildren().add(new Label(res.toString()));
                            res.delete(0, res.length());
                        }
                    } else {
                        res.append(messageQueue.getFirst(), 0, 23).append("...");
                        messageQueue.removeFirst();
                    }
                }
                if (res.length() != 0) {
                    controller.vbox.getChildren().add(new Label(res.toString()));
                    res.delete(0, res.length());
                }

                controller.vbox.setSpacing(0);

                if (!content.trim().equals("") && !content.equals("")) {
                    controller.vbox.getChildren().add(new Label("\n"));
                }

                controller.scrollPane.setContent(controller.vbox);
                controller.message.clear();
                controller.message.setPromptText("Type a message...");
            }
        });
    }

}
