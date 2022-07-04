package model;

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
          //lehni chtestamlha fazet il plus 1
          // il thnin hia adad il Ship eli chenhothom
          for (int i=0;i<2;i++){
               Ship one =board1.createShip(0);
               while (one.IsplacementOk(one,shipPlayer1,boardPlayer1)==false){
                    one=board1.createShip(0);
               }
               shipPlayer1.add(one);
          }
          for (int i=0;i<2;i++){
               Ship one =board1.createShip(1);
               while (one.IsplacementOk(one,shipPlayer2,boardPlayer2)==false){
                    one=board1.createShip(0);
               }
               shipPlayer2.add(one);
          }

          Player player1=new Player(shipPlayer1,boardPlayer2);
          Player player2=new Player(shipPlayer2,boardPlayer1);
          boolean GameOn=true;
          Display display=new Display();
          System.out.println("-----Player1 Board -------");
          display.PrintBoard(boardPlayer1);
          System.out.println("--------------------------");
          System.out.println("-----Player2 Board -------");
          display.PrintBoard(boardPlayer2);
          int NumberofshipsPlayer1=player1.NumberOfSquareofShips(shipPlayer1);
          int NumberofshipsPlayer2=player2.NumberOfSquareofShips(shipPlayer2);
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
               ShootCoordination=board1.shoot(1);
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
