package com.example.trying.Spiellogik;

import com.example.trying.*;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Game {
     public List<Ship> shipPlayer1=new ArrayList<>();
     public List<Ship> shipPlayer2=new ArrayList<>();
     public  boolean GameOn;
     public Board boardPlayer1;
     public Board boardPlayer2;
     List<Board> boards;
     public Ship one ;

     public void GameLogik(){
          Input board1=new Input();
          boards=board1.getBoards();
          boardPlayer1 =boards.get(0);
          boardPlayer2=boards.get(1);
          GameOn=true; //// Win condition SERVER
          //-------------------------------------------------- für Client Version müsste das einfach verwechselt werden ! ---------------
          //lehni chtestamlha fazet il plus 1
          // il thnin hia adad il Ship eli chenhothom



          for (int i=0;i<5;i++){
                one =board1.createShip(0);     
                System.out.printf("Run number %d", i );                // request here 
               if ( one.IsplacementOk(one, shipPlayer1, boardPlayer1)){
                    System.out.println("IF TRUE");
               }
               while (one.IsplacementOk(one,shipPlayer1,boardPlayer1)==false){ // IF FALSE
                    System.out.println("Inside Boucle");
                    IpController.playControl2.PreviousMessage += "\nInvalid Placement";
                    IpController.playControl2.Chat.setText(IpController.playControl2.PreviousMessage);
                    IpController.playControl2.shipCounter--;
                    IpController.playControl2.biggness++;
                    Input.ShipNum1--;


                    one=board1.createShip(0);
                    
               }
               shipPlayer1.add(one);
               for (int j=0;j<one.GetNewShip().size();j++) {
                    System.out.printf("Size at %d ,index : %d\n",one.GetNewShip().size(), j);
                    IpController.playControl2.grid[one.GetNewShip().get(j).Gety()][one.GetNewShip().get(j).Getx()].setFill(Color.GREEN);
               }

          }
          for ( ClientHanlder aClient : ServerThread.Clients ){
               aClient.writer.println("/txtc "+"You can now Place your Ships");
               aClient.writer.flush();
          }

          // PlayingController2.ServerNotif.setText("Opponent's Turn");
          Input.ShipNum1=1; // Temporare ! 
          for (int i=0;i<5;i++){                // Adversaire filling server side
               Ship one =board1.ServercreateShip(1);
               if ( one.IsplacementOk(one, shipPlayer2, boardPlayer2)){
                    System.out.println("IF TRUE");
               }
               while (one.IsplacementOk(one,shipPlayer2,boardPlayer2)==false){
                    System.out.println("Inside Boucle");
                    Input.ShipNum1--;
                    one=board1.ServercreateShip(0);
                   

               }
               shipPlayer2.add(one);
          }
          //--------------------------------------------------- Bis hier ----------------------------------------------------------
          Player3 player1=new Player3(shipPlayer1,boardPlayer2);
          Player2 player2=new Player2(shipPlayer2,boardPlayer1);
          Display display=new Display();
          System.out.println("-----Player1 Board -------");
          display.PrintBoard(boardPlayer1);
          System.out.println("--------------------------");
          System.out.println("-----Player2 Board -------");
          display.PrintBoard(boardPlayer2);
          int NumberofshipsPlayer1=player1.NumberOfSquareofShips(shipPlayer1);
          int NumberofshipsPlayer2=player2.NumberOfSquareofShips(shipPlayer2);
          // PlayingController2.ServerNotif.setText("Your Turn");

          while (GameOn){
               int[] ShootCoordination;
               ShootCoordination=board1.shoot(0);
               if (player2.Shot(ShootCoordination[0],ShootCoordination[1])){
                    display.PrintBoard(player2.Getboard());
                    NumberofshipsPlayer2--;
               }else {
                    display.PrintBoard(player2.Getboard());
               }
               if (NumberofshipsPlayer2==0){
                    display.PrintBoard(player2.Getboard());
                    System.out.println("player 1 hat gewonnen");
                    break;
               }
               ShootCoordination=board1.Servershoot(1);
               if (player1.Shot(ShootCoordination[0],ShootCoordination[1])){
                    display.PrintBoard(player1.Getboard());
                    NumberofshipsPlayer1--;
               }else {
                    display.PrintBoard(player1.Getboard());
               }
               if (NumberofshipsPlayer1==0){
                    display.PrintBoard(player1.Getboard());
                    System.out.println("player 2 hat gewonnen");
                    break;
               }


          }
           // winn outside of while loop
           System.out.println("TEST TEST TEST");
     } 
}
