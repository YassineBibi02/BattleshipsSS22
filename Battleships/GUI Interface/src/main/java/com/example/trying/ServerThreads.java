package com.example.trying;






public class ServerThreads {
    

  static class Zählen implements Runnable {
   private final String Name = "Client ";
   private int NameV = 1;
   private String NName;

    @Override
    public void run() {
        for ( int i = 0 ; i < 10 ; i++){
            NName = Name + NameV;
            NameV++;
            System.out.printf("Zahlen : %s, %d\n",NName,i);
        }
        
    }
    
  }

  public static void main(String[] arStrings) {

   Thread T1=   new Thread(new Zählen());
   
   T1.start();
   

  }




}
