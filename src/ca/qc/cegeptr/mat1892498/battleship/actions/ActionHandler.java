package ca.qc.cegeptr.mat1892498.battleship.actions;

import ca.qc.cegeptr.mat1892498.battleship.boats.BoatManager;
import ca.qc.cegeptr.mat1892498.battleship.components.AlertBox;
import ca.qc.cegeptr.mat1892498.battleship.game.Game;
import ca.qc.cegeptr.mat1892498.battleship.socket.Client;
import ca.qc.cegeptr.mat1892498.battleship.socket.Response;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class ActionHandler {

    public ActionHandler(Response json) {
        if(!json.getJson().isEmpty()){
            System.out.println(json);
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
                        if(entry.getValue().getAsString().equals("starting"))
                            new Game();
                        break;
                    default:
                        System.out.println("Unknown key received");
                        break;
                }
            }
        }
    }

}