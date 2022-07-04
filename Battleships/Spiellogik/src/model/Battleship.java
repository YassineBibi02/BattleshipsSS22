package model;

public class Battleship {
    private Display display;
    private Game game=new Game();
    private Input input;


    public Battleship(){
        display=new Display();
    }

    public void Anfangen(){
        display.PrintMenu();
        MainMenu();
    }

    public void MainMenu(){
        int choice;
        boolean exit=false;
        input=new Input();

        while (!exit){
            display.PrintMainMenuOption();
            System.out.println("Gib dein Auswahl");
            System.out.println();
            choice=input.GetIntMenuOption();
            switch (choice){
                case 0:
                    display.PrintMessage("you choosed to play the Game");
                    game.GameLogik();
                    break;
                case 1:
                    System.out.flush();
                    display.gameRules();
                case 2:
                    display.PrintMessage("byyyy ");
                    ExitGame();
                    break;

            }
        }
    }

    public void ExitGame(){
        display.printExitMessage();
        System.exit(0);
    }
}
