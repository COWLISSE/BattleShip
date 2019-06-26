package ca.qc.cegeptr.mat1892498.battleship.game;

import ca.qc.cegeptr.mat1892498.battleship.boats.Boat;
import ca.qc.cegeptr.mat1892498.battleship.boats.BoatManager;
import ca.qc.cegeptr.mat1892498.battleship.controllers.GameController;
import ca.qc.cegeptr.mat1892498.battleship.controllers.MainController;
import ca.qc.cegeptr.mat1892498.battleship.game.boards.Board;
import ca.qc.cegeptr.mat1892498.battleship.game.boards.Opponent;
import ca.qc.cegeptr.mat1892498.battleship.game.boards.Player;
import javafx.application.Platform;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameManager {

    private static boolean turn = false;
    private static Map<String, Board> boards = new HashMap<>();
    private static Map<String, GridPane> grids = new HashMap<>();
    private static Map<String, GameController> controllers = new HashMap<>();
    public static String opponentName = "Unknown";

    public static void startGame(){
        // Clears
        grids.clear();
        boards.clear();
        turn = false;
        // Start
        boards.put("player", new Player());
        boards.put("opponent", new Opponent(opponentName));
        boards.get("player").show(); // Start by showing player's screen (waiting for server's turn message)
    }

    public static void receive(String pos){
        AtomicBoolean hit = new AtomicBoolean(false);
        Boat.getBoatPos().forEach(boat -> {
            if(hit.get()) return;
            for (String s : boat) {
                if(hit.get()) continue;
                if(s.equals(pos)) {
                    hit.set(true);
                }
            }
        });
        boards.get("player").hit("player", pos, hit.get());
    }

    public static void chat(String name, String message){
        controllers.forEach((type, controller) -> {
//            if(type.equals("player"))
//                playerName = MainController.username;
//            else playerName = ((Opponent) boards.get("opponent")).getUsername();
            Board.displayMessage(controller, message, name);
        });
    }

    public static void hitResponse(String pos, boolean hit){
        boards.get("opponent").hit("opponent", pos, hit);
    }

    public static void changeTurn(String turn_){
        turn = turn_.equals("yours");
        new Timer().schedule(
            new TimerTask() {
                @Override
                public void run() {
                    if(turn) Platform.runLater(() -> boards.get("opponent").show());
                    else Platform.runLater(() -> boards.get("player").show());
                }
            },3000
        );
    }

    public static void addGrid(GridPane grid){
        if(grids.size() == 0)
            grids.put("player", grid);
        else grids.put("opponent", grid);
    }

    public static void addController(GameController controller){
        if(controllers.size() == 0)
            controllers.put("player", controller);
        else controllers.put("opponent", controller);
    }

    public static boolean getTurn(){
        return turn;
    }

    public static Map<String, GridPane> getGrids() {
        return grids;
    }

    public static Map<String, Board> getBoards() {
        return boards;
    }
}