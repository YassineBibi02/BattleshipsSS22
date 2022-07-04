package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Input {
    private Scanner scanner=new Scanner(System.in);
    private List<Board> boards=new ArrayList<>();
    int choice;
    List<Integer> CoordinatesAndShiptype =new ArrayList<>();

    public List<Board> getBoards(){
        GenerateBoard();
        return boards;
    }

    public void GenerateBoard(){
        System.out.println("Select height:");
        int x =scanner.nextInt();
        scanner.nextLine();
        System.out.println("Select Width");
        int y=scanner.nextInt();
        Board board1=new Board(x,y);
        Board board2=new Board(x,y);
        boards.add(board1);
        boards.add(board2);
    }

    public Input(){

    }

    public int GetIntMenuOption(){
        choice=scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    private List<Integer> AskCoordForShipAndTyp(){
        this.CoordinatesAndShiptype=new ArrayList<>();
        System.out.println("select row : ");
        int row=scanner.nextInt();
        scanner.nextLine();
        System.out.println("Select col :");
        int col=scanner.nextInt();
        scanner.nextLine();
        System.out.println("Select ship:\n"+"1.MOUDAMIR\n"+"2.EMLEK\n"+"3.KANAS\n"+"4.EDIA\n"+"5.KABIRA\n"+"6.GUEZ\n");
        int shiptype=scanner.nextInt();
        CoordinatesAndShiptype.add(row);
        CoordinatesAndShiptype.add(col);
        CoordinatesAndShiptype.add(shiptype);
        return CoordinatesAndShiptype;
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
        return new int[]{row,col};
    }



}
