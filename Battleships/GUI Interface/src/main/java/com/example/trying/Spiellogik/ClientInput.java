package com.example.trying.Spiellogik;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.example.trying.*;


public class ClientInput {
    private Scanner scanner=new Scanner(System.in);
    private List<Board> boards=new ArrayList<>();
    public static Integer ShipNum1 = 1;
    public static boolean Client_wait = true;
    public static Integer ClientRow = null ;
    public static Integer ClientCol = null ;
    int choice;
    List<Integer> CoordinatesAndShiptype =new ArrayList<>();

    public List<Board> getBoards(){
        GenerateBoard();
        return boards;
    }

    public void GenerateBoard(){
       
        Board board1=new Board(10,10);
        Board board2=new Board(10,10);
        boards.add(board1);
        boards.add(board2);
    }

    public ClientInput(){

    }

    public int GetIntMenuOption(){
        choice=scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    private List<Integer> AskCoordForShipAndTyp(){           /// implementation de serveur
        this.CoordinatesAndShiptype=new ArrayList<>();
        System.out.println("select row : ");
        int row=scanner.nextInt();
        scanner.nextLine();
        System.out.println("Select col :");
        int col=scanner.nextInt();
        scanner.nextLine();                             //  Dass Input muss was anders Sein 
        CoordinatesAndShiptype.add(row);
        CoordinatesAndShiptype.add(col);
        CoordinatesAndShiptype.add(ShipNum1);
        ShipNum1++; /// Max 5 wergen die Loop // Muss nach 1 wieder gestzt werden wenn enemy nutzt es

        // Sends Coordinates to Server
        String Message = String.valueOf(row)+ String.valueOf(col);

        Client_Thread.writer.println("Player2#/spl$"+Message);
        Client_Thread.writer.flush();
        
        return CoordinatesAndShiptype;
    }
    private List<Integer> ClientAskCoordForShipAndTyp(){           /// Client Version with Read  // Needs coding
        this.CoordinatesAndShiptype=new ArrayList<>();
        // MAN BRAUCHT 3 LIST von 2 ints von Client
        // Erstmal warten bis Client etwas geshickt hat 
        while ( Client_wait ){
           try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            System.out.println("Fehler Code 21");
            e.printStackTrace();
        } // waits till Client wait is false ( Set by Client )
        }

        Client_wait = true;
        if ( (ClientRow != null )&& (ClientCol != null)){
         System.out.printf("Passed %d as Row and %d as Col\n", ClientRow, ClientCol);
         CoordinatesAndShiptype.add(ClientRow);
         CoordinatesAndShiptype.add(ClientCol);
         CoordinatesAndShiptype.add(ShipNum1);
        } else { 
            System.out.println("Fehler Code 22");
        }
        ClientRow = null;
        ClientCol = null;       //reset
        ShipNum1++; /// Max 5 wergen die Loop // Muss nach 1 wieder gestzt werden wenn enemy nutzt es
        return CoordinatesAndShiptype;
    }
    
    public Ship ClientcreateShip(int player){
        int GamePlayer= player+1;
        Squere shipPart;
        Ship ship;
        System.out.println("Player"+GamePlayer+" place ship");
        CoordinatesAndShiptype=ClientAskCoordForShipAndTyp();
        int row=CoordinatesAndShiptype.get(0);
        int col=CoordinatesAndShiptype.get(1);
        int shiptype=CoordinatesAndShiptype.get(2);
        shipPart=new Squere(row,col,SquareStatur.SHIP);
        ship=new Ship(new ArrayList<>(),ShipType.values()[shiptype-1]);
        boards.get(player).BuiltShip(shipPart,ship);
        System.out.println(ship.GetNewShip());
        return ship;

    }

    
     //fazet il plus 1 chnestamlha fil Game
    public Ship createShip(int player){
        int GamePlayer= player+1;
        Squere shipPart;
        Ship ship;
        System.out.println("Player"+GamePlayer+" place ship");
        CoordinatesAndShiptype=AskCoordForShipAndTyp();
        int row=CoordinatesAndShiptype.get(0);
        int col=CoordinatesAndShiptype.get(1);
        int shiptype=CoordinatesAndShiptype.get(2);
        shipPart=new Squere(row,col,SquareStatur.SHIP);
        ship=new Ship(new ArrayList<>(),ShipType.values()[shiptype-1]);
        boards.get(player).BuiltShip(shipPart,ship);
        System.out.println(ship.GetNewShip());
        return ship;

    }

    public int[] shoot (int player){
        int GamePlayer=player+1;
        System.out.println("Player"+ GamePlayer+"shoot");
        System.out.println("Gib Row");
        int row=scanner.nextInt();
        System.out.println("select col");
        int col=scanner.nextInt();

        //send to Server 
        String Message = String.valueOf(row)+ String.valueOf(col);

        Client_Thread.writer.println("Player2#/spl$"+Message);
        Client_Thread.writer.flush();
        
        
        return new int[]{row,col};
    }

    public int[] Clientshoot (int player){
        int GamePlayer=player+1;
        int[] ClientReturn = {0,0};
        System.out.println("Player2"+ GamePlayer+"shoot");
        while ( Client_wait ){
            try {
             Thread.sleep(20);
         } catch (InterruptedException e) {
             System.out.println("Fehler Code 21");
             e.printStackTrace();
         } // waits till Client wait is false ( Set by Client )
         }
         Client_wait = true;
         if ( (ClientRow != null )&& (ClientCol != null)){
            System.out.printf("Passed %d as Row and %d as Col\n", ClientRow, ClientCol);
         ClientReturn[0] = ClientRow;
         ClientReturn[1]=  ClientCol;
         } else {
            System.out.println("Fehler Code 23");
         }
         ClientRow = null;
         ClientCol = null; 
        return ClientReturn;
    }


 

       
    


   static public void SetClientCoordinates(Integer Row , Integer Col){
     ClientRow = Row;
     ClientCol = Col;
     Client_wait = false ;
   }
}
