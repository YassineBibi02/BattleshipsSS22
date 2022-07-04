package com.example.trying;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;



public class HelloController {
    @FXML
    private Button Exit;
    @FXML
    private AnchorPane scenePane;
    @FXML
    public ImageView Logo;
    private Stage stage;
    private Scene scene;

    private Parent root;
    public void switchtoIp(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Ip.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void Test(ActionEvent e){
        Stage stage = (Stage) Exit.getScene().getWindow();
        stage.close();

    }
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Aasba lik hh");
    }
}