package Network;





public class ServerThreads {
    

  static class Zählen implements Runnable {

    @Override
    public void run() {
        for ( int i = 0 ; i < 55 ; i++){
            System.out.printf("Zahlen : %d\n",i);
        }
        
    }
    
  }

  public static void main(String[] arStrings) {

   Thread T1=   new Thread(new Zählen());
   T1.start();

  }




}
