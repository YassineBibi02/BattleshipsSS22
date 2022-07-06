package com.example.trying;
import com.example.trying.Spiellogik.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class IpController {
    static public PlayingController playControl;
    static public PlayingController2 playControl2;
    private Stage stage;
    private Scene scene;
    public Thread ClientGame;
    public Thread ServerGame;
    // private Parent root;
    public void switchtoMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchtoPlaying(ActionEvent event) throws IOException {
        Thread T1=   new Thread(new Client_Thread());
        T1.start();
        ClientGame=  new Thread(new MainThreadClient());
        ClientGame.start();
       
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Playing.fxml"));
        Parent root = loader.load();

        playControl = loader.getController();
        

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void switchtoHosting(ActionEvent event) throws IOException {
        Thread T1=   new Thread(new ServerThread());
        T1.start();
        ServerGame = new Thread ( new MainThread());
        ServerGame.start();
        

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Playing2.fxml"));
        Parent root = loader.load();

        playControl2 = loader.getController(); 

        
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    
}
