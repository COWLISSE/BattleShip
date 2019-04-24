package ca.qc.cegeptr.mat1892498.battleship;

import ca.qc.cegeptr.mat1892498.battleship.socket.Client;
import ca.qc.cegeptr.mat1892498.battleship.socket.Response;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class BattleShip extends Application {

    public static void sendPacket(String json){
        if(Client.SERVER != null){
            Client.SERVER.channel().writeAndFlush(new Response(json));
        }else{
            System.err.println("Failed to send packet to the server (Client is not connected)");
            //TODO: Add error message
        }
    }

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                new Client();
            } catch (InterruptedException e) {
                System.err.println("Unable to connect to default server");
            }
        }).start();
        launch(args);
        System.exit(0);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent main_menu = FXMLLoader.load(getClass().getResource("./layouts/battleship_main.fxml"));
        Scene mainMenu = new Scene(main_menu);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("./layouts/images/battleship.png")));
        primaryStage.setTitle("BattleShip - Main Menu");
        primaryStage.setScene(mainMenu);
        primaryStage.show();
    }

}
