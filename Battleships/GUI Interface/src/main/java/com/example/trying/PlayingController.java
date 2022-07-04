package com.example.trying;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.module.ModuleDescriptor;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PlayingController implements Initializable  {
    //All allied ships
    /*Ships ship01= new Ships(5,true);
    Ships ship02 = new Ships(4,true);
    Ships ship03 = new Ships(3,true);
    Ships ship04 = new Ships(2,true);
    Ships ship05 = new Ships(2,true);*/

    @FXML
    public ImageView Background;
    public ImageView ship1;
    public ImageView ship2;
    public ImageView ship3;
    public ImageView ship4;
    public ImageView ship5;
    private Stage stage;
    private Scene scene;

    private Parent root;
    public void switchtoLost(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Lost.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private TextField Column;
    @FXML
    private TextField Row;

@FXML
private Pane pane;

   @FXML Pane pane1;

//Everything for the geometry coding and grid building.

    private int size = 200;
    private int spots=10;
    private int squareSize = size/spots;
    //An Arraylist of the Cursors and NOT the ships. these Cursors are placed in an Arraylist to keep track of them.
    private ArrayList<Ships> Ships;
    //All 2 2 dimensional arrays to keep track of the coordinates when we need them
    private  Rectangle[][] grid;
    private Rectangle[][] gridenemy;
    /*
    A check box used to check whether a ship will be placed vertically or horizontally
    if its selected, ship will be placed horizontally on the grid going from the cursor to the right
    if not it will be placed vertically from the cursor down.
    */
    @FXML
    private CheckBox Horizontal;


//Initialize method where all the magic happens
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //making the text are for the chat read only.
      Chat.setEditable(false);
      //Initializing the grids
      grid = new Rectangle[spots][spots];
      gridenemy = new Rectangle[spots][spots];
      //Drawing allied battlefield.
      for(int i = 0; i<size;i+=squareSize){
          for(int j =0;j<size;j+=squareSize){
              Rectangle r = new Rectangle(i,j,squareSize,squareSize);
              grid[i/squareSize][j/squareSize] = r;
              r.setFill(Color.LIGHTGRAY);
              r.setStroke(Color.BLACK);
              pane.getChildren().add(r);


          }
      }
      //Drawing enemy battlefield.
      for(int i = 0; i<size;i+=squareSize){
            for(int j =0;j<size;j+=squareSize){
                Rectangle r = new Rectangle(i,j,squareSize,squareSize);
                gridenemy[i/squareSize][j/squareSize] = r;
                r.setFill(Color.LIGHTGRAY);
                r.setStroke(Color.BLACK);
                pane1.getChildren().add(r);


            }
        }
      Ships = new ArrayList<Ships>();
      int size = 5;
      //Drawing the cursors which are Called ships by mistake.
      for (int i = 0; i < 1;i++){
          Circle c = new Circle();
          c.setFill(Color.GREEN);
          c.setStroke(Color.BLACK);
          double radius =squareSize / 3.0;
          int x = squareSize/2 + squareSize * (int)(Math.random() * spots);
          int y = squareSize/2 + squareSize * (int)(Math.random() * spots);
          Ships ship = new Ships(size,x,y,radius,c,true);
          Ships.add(ship);
          c.setOnMousePressed(event -> pressed(event,ship));
          c.setOnMouseDragged(event -> dragged(event,ship));
          c.setOnMouseReleased(event -> released(event,ship));
          pane.getChildren().add(c);
          ship.draw();
      }
      //all Drawing the cursor for hitting the enemy. namly a piece on the enemy grid.
        for (int i = 0; i < 1;i++){
            Circle c2 = new Circle();
            c2.setFill(Color.GREEN);
            c2.setStroke(Color.BLACK);
            double radius =squareSize / 3.0;
            int x = squareSize/2 + squareSize * (int)(Math.random() * spots);
            int y = squareSize/2 + squareSize * (int)(Math.random() * spots);
            Ships ship = new Ships(size,x,y,radius,c2,true);
            Ships.add(ship);
            c2.setOnMousePressed(event -> pressed(event,ship));
            c2.setOnMouseDragged(event -> dragged(event,ship));
            c2.setOnMouseReleased(event -> hit(event,ship));
            pane1.getChildren().add(c2);
            ship.draw();
        }
    }
    //When the enemy cursor is dropped, the cell will turn red marking it as HIT.
    public void hit(MouseEvent event,Ships ship){
        int gridx =(int)ship.getX() / squareSize;
        int gridy =(int)ship.getY() / squareSize;
        gridenemy [gridx][gridy].setFill(Color.RED);
        ship.setX(squareSize/2 + squareSize * gridx);
        ship.setY(squareSize/2 + squareSize * gridy);
        ship.draw();

    }
    //Nothing important, trying the press event handler.
    public void pressed(MouseEvent event,Ships ship){
        ship.setColor(Color.DARKBLUE);

    }
    //function making the pieces draggable
    public void dragged(MouseEvent event,Ships ship){
        ship.setX(ship.getX()+event.getX());
        ship.setY(ship.getY()+event.getY());
        ship.draw();
    }
    //biggness is the size of the ship, it is used to keep track of what ships we placed and what ships to place after
private int biggness = 5;
    //Used as to not surpass 5 ships placed
    private int shipCounter = 0;
    /*placing ships, the ships are represented by green tiles marking them as SHIPS.

    * this method took the most time

    * as I had to reset everything in the try and catch block to its original state if there is no space for the ship
    *
    *
    * */
    public void released(MouseEvent event , Ships ship){

        int gridx =(int)ship.getX() / squareSize;
        int gridy =(int)ship.getY() / squareSize;
        try {
        if(Horizontal.isSelected() && shipCounter != 5){
            grid [gridx][gridy].setFill(Color.GREEN);
            for(int i=0;i < biggness; i++){

                grid[gridx + i][gridy].setFill(Color.GREEN);
            }
            this.biggness--;
            shipCounter++;
        }} catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Unable to place ship");
            try{
            if(Horizontal.isSelected() && shipCounter != 5){
                grid [gridx][gridy].setFill(Color.LIGHTGRAY);
                for(int i=0;i < biggness+1; i++){

                    grid[gridx + i][gridy].setFill(Color.LIGHTGRAY);
                }
                this.biggness++;
                shipCounter--;
            }}catch(ArrayIndexOutOfBoundsException exception){
                System.out.println("Placement Canceled");
            }

        }
       try{
        if (!Horizontal.isSelected() && shipCounter != 5){
            grid [gridx][gridy].setFill(Color.GREEN);
            for(int i=0;i < biggness; i++){
                grid[gridx][gridy+ i].setFill(Color.GREEN);
            }
            this.biggness--;
            shipCounter++;
        }}catch(ArrayIndexOutOfBoundsException e){
           System.out.println("Unable to place ship");
           try{
           if(!Horizontal.isSelected() && shipCounter != 5){
               grid [gridx][gridy].setFill(Color.LIGHTGRAY);
               for(int i=0;i < biggness+1; i++){

                   grid[gridx][gridy+i].setFill(Color.LIGHTGRAY);
               }
               this.biggness++;
               shipCounter--;
           }}catch(ArrayIndexOutOfBoundsException exception){
           System.out.println("Placement Canceled");
       }
       }
        ship.setX(squareSize/2 + squareSize * gridx);
        ship.setY(squareSize/2 + squareSize * gridy);
        ship.draw();
    }
    /*
     * The Chat Variables and the Chat Methods GUI sided. (Namly writing, storing and outputting.).
     *
     * the chat is crudely implemented it's basically the person talking by himself right now.
     *
     * the Input is always stored in the Message variable.
     *
     * the Send function sends the input from the text-field to the textarea and stores whatever came before as an Input
     *
     * the Chat TextArea is non Editable as it should.
     *
     * this comment is for the Network Part to understand the Implementation and deploy a better one with their knowledge of Network and having the Server on disposal
     * */
    @FXML
    private Button button_send;
    String PreviousMessage = "";
    @FXML
    private TextField tf_message;
    @FXML
    private TextArea Chat;

    String Message = "";
    //This method is used to send the information from the textfield to the read only chat text area,pretty simple implementation requested by the network for further development
    public void Send(ActionEvent e){
        Message = tf_message.getText();
        System.out.println(Message);
        String CurrentChatMessage = PreviousMessage + "\n"+"Player: "+Message;
        PreviousMessage = CurrentChatMessage;
        tf_message.setText("");
        Chat.setText(CurrentChatMessage);


    }
}
