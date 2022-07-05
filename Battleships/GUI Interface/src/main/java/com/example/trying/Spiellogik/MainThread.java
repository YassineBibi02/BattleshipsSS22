package com.example.trying.Spiellogik;

public class MainThread implements Runnable {

    @Override
    public void run() {
        Battleship battleship=new Battleship();
        battleship.Anfangen();
    }

    
}
