package ca.qc.cegeptr.mat1892498.battleship.actions;

import ca.qc.cegeptr.mat1892498.battleship.BattleShip;
import ca.qc.cegeptr.mat1892498.battleship.boats.BoatManager;
import ca.qc.cegeptr.mat1892498.battleship.components.AlertBox;
import ca.qc.cegeptr.mat1892498.battleship.components.GameState;
import ca.qc.cegeptr.mat1892498.battleship.controllers.GameController;
import ca.qc.cegeptr.mat1892498.battleship.game.GameManager;
import ca.qc.cegeptr.mat1892498.battleship.socket.Client;
import ca.qc.cegeptr.mat1892498.battleship.socket.Response;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;
import java.util.Set;

public class ActionHandler {

    public ActionHandler(Response json){
        if(!json.getJson().isEmpty()){
//            System.out.println(json);
            JsonElement element = Client.GSON.fromJson(json.getJson(), JsonElement.class);
            JsonObject obj = element.getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();
            for (Map.Entry<String, JsonElement> entry: entries) {
                // Detection of keys sent to the server by the clients
                switch (entry.getKey()){
                    case "board":
                        BoatManager.valid = !entry.getValue().getAsString().equals("failed");
                        break;
                    case "error":
                        AlertBox.display("Error!", entry.getValue().getAsString());
                        break;
                    case "game":
                        if(entry.getValue().getAsString().equals("starting")) {
                            GameManager.startGame();
                            BattleShip.sendPacket("{'version': 1}");
                        }
                        break;
                    case "username":
                        GameManager.opponentName = entry.getValue().getAsString();
                        break;
                    case "chat":
                        GameManager.chat(GameManager.opponentName, entry.getValue().getAsString());
                        break;
                    case "turn":
                        GameManager.changeTurn(entry.getValue().getAsString());
                        break;
                    case "hit":
                        if(entry.getValue().getAsString().equals("already"))
                            AlertBox.display("Error!", "You have already hit that position...");
                        break;
                    case "got_hit":
                        GameManager.receive(entry.getValue().getAsString());
                        break;
                    case "hit_success":
                        GameManager.hitResponse(entry.getValue().getAsString(), true);
                        break;
                    case "hit_failed":
                        GameManager.hitResponse(entry.getValue().getAsString(), false);
                        break;
                    case "game_state":
                        GameState.display("End of game...", "You have " + entry.getValue().getAsString() + " !");
                        break;
                    default:
                        System.out.println("Unknown key received");
                        break;
                }
            }
        }
    }

}