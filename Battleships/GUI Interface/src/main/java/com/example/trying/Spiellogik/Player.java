package com.example.trying.Spiellogik;



import java.util.List;

public class Player {
    private List<Ship> ships;
    private Board boardPlayer;
    private int remainingShips=0;

    public Player(List<Ship> shipss,Board bor){
        this.boardPlayer=bor;
        this.ships=shipss;
    }

    public Board Getboard(){
        return this.boardPlayer;
    }
// Die Zahl von von square , die Ships drin sind
    public int NumberOfSquareofShips(List<Ship> ships){
        int Summ=0;
        for (Ship ship:ships){
            Summ+=ship.GettypevonShip().Getlabel();
        }
        return Summ;
    }
            //the Funktion von Shoot
    public boolean Shot(int x ,int y){
        for (Ship ship: ships){
            for (Squere square: ship.GetNewShip()){
                if (square.Gety()==y && square.Getx()==x && square.Getsquarestat().equals(SquareStatur.SHIP)){
                    square.setSquarestat(SquareStatur.HIT);
                    boardPlayer.GetSquere(x,y).setSquarestat(SquareStatur.HIT);
                    System.out.println("Du hast Getroffen");
                    return true;
                }else if (square.Gety()==y && square.Getx()==x && square.Getsquarestat().equals(SquareStatur.HIT)){
                    square.setSquarestat(SquareStatur.HIT);
                    boardPlayer.GetSquere(x,y).setSquarestat(SquareStatur.HIT);
                    System.out.println("Schon Getroffen");
                    return false;
                }
            }
        }
        boardPlayer.GetSquere(x,y).setSquarestat(SquareStatur.MISSED);
        System.out.println("MISSED");
        return false;

    }


}
