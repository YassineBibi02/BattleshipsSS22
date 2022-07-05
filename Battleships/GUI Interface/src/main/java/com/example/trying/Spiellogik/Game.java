package com.example.trying.Spiellogik;

import com.example.trying.*;
import java.util.ArrayList;
import java.util.List;

public class Game {
     private List<Ship> shipPlayer1=new ArrayList<>();
     private List<Ship> shipPlayer2=new ArrayList<>();
     List<Board> boards;

     public void GameLogik(){
          Input board1=new Input();
          boards=board1.getBoards();
          Board boardPlayer1 =boards.get(0);
          Board boardPlayer2=boards.get(1);
          //-------------------------------------------------- für Client Version müsste das einfach verwechselt werden ! ---------------
          //lehni chtestamlha fazet il plus 1
          // il thnin hia adad il Ship eli chenhothom
          


          for (int i=0;i<5;i++){
               Ship one =board1.createShip(0);
               while (one.IsplacementOk(one,shipPlayer1,boardPlayer1)==false){
                    one=board1.createShip(0);
               }
               shipPlayer1.add(one);
          }
          for ( ClientHanlder aClient : ServerThread.Clients ){
               aClient.writer.println("/txtc "+"You can now Place your Ships");
               aClient.writer.flush();
           }

          // PlayingController2.ServerNotif.setText("Opponent's Turn");
          Input.ShipNum1=1; // Temporare ! 
          for (int i=0;i<5;i++){                // Adversaire filling server side
               Ship one =board1.ServercreateShip(1);
               while (one.IsplacementOk(one,shipPlayer2,boardPlayer2)==false){
                    one=board1.ServercreateShip(0);
               }
               shipPlayer2.add(one);
          }
          //--------------------------------------------------- Bis hier ----------------------------------------------------------
          Player3 player1=new Player3(shipPlayer1,boardPlayer2);
          Player2 player2=new Player2(shipPlayer2,boardPlayer1);
          boolean GameOn=true;
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
     }
}
