package ca.qc.cegeptr.mat1892498.battleship;

import ca.qc.cegeptr.mat1892498.battleship.components.AlertBox;
import ca.qc.cegeptr.mat1892498.battleship.components.ConfirmBox;
import ca.qc.cegeptr.mat1892498.battleship.socket.Client;
import ca.qc.cegeptr.mat1892498.battleship.socket.Response;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class BattleShip extends Application {

    public static Stage primary;

    public static void sendPacket(String json){
        if(Client.SERVER != null)
            Client.SERVER.channel().writeAndFlush(new Response(json));
        else AlertBox.display("Error!", "Failed to send packet to the server (Server is offline)");
    }

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                new Client();
            } catch (InterruptedException e) {
                AlertBox.display("Error!", "Failed to connect to default server");
            }
        }).start();
        launch(args);
        System.exit(0);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primary = primaryStage;
        Parent main_menu = FXMLLoader.load(getClass().getResource("/ca/qc/cegeptr/mat1892498/battleship/layouts/battleship_main.fxml"));
        Scene mainMenu = new Scene(main_menu);
        primary.getIcons().add(new Image(getClass().getResourceAsStream("/ca/qc/cegeptr/mat1892498/battleship/layouts/images/battleship.png")));
        primary.setTitle("BattleShip - Main Menu");
        primary.setScene(mainMenu);
        primary.show();
        primary.setOnCloseRequest(e -> {
            boolean conf = ConfirmBox.display("Already...?", "Are you sure you want to leave ?");
            if(conf) {
                if(Client.SERVER != null)
                    Client.SERVER.close();
                primary.close();
            } else e.consume();
        });
    }

}
