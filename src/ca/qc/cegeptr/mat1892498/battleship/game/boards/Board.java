package ca.qc.cegeptr.mat1892498.battleship.game.boards;

import ca.qc.cegeptr.mat1892498.battleship.BattleShip;
import ca.qc.cegeptr.mat1892498.battleship.game.GameManager;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.io.IOException;

public abstract class Board {

    private Scene scene;

    public Board(){
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

    public void show(){
        Platform.runLater(() -> {
            BattleShip.primary.setScene(scene);
            BattleShip.primary.show();
        });
    }

    public Scene getScene() {
        return scene;
    }

    public void hit(String gridName, String pos, boolean hit){
        String[] coords = pos.split(",");
        int x = Integer.parseInt(coords[0]);
        int y = Integer.parseInt(coords[1]);
        double sWidth = GameManager.getGrids().get(gridName).getWidth() / 10;
        double sHeight = GameManager.getGrids().get(gridName).getHeight() / 10;
        Platform.runLater(()-> {
            Line line1 = new Line(sWidth * x, sHeight * y, (sWidth * x) + sWidth, (sHeight * y) + sHeight);
            Line line2 = new Line(sWidth * x, (sHeight * y) + sHeight, (sWidth * x) + sWidth, sHeight * y);
            line1.setStrokeWidth(4);
            line2.setStrokeWidth(4);
            if(hit){
                line1.setStroke(Color.RED);
                line2.setStroke(Color.RED);
            } else{
                line1.setStroke(Color.WHITE);
                line2.setStroke(Color.WHITE);
            }
            GameManager.getGrids().get(gridName).add(line1, x, y);
            GameManager.getGrids().get(gridName).add(line2, x, y);
        });
    }

}
