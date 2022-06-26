package Network;



import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class client {
   static public String Own_Name ;
   static public int offset = 0 ; 
 
   public static int get_offset(){
    // System.out.printf("Code #%d\n", offset);
    return offset;
   }

  //  public static void set_offset(int neew){
  //   client.offset = neew;
  //  }

    public static void main(String[] Args){
     
    //  Scanner INside = new Scanner(System.in);
    //  String Own_Name;
    //  do {
    //   System.out.println("Enter your Name (no Spaces): ");

    //   Own_Name =  INside.nextLine();
    //  } while (Own_Name.contains(" "));
    //  System.out.printf("Welcome %s! Enjoy the Chat service! \n", Own_Name);

        
      try {
        Socket Client = new Socket(server.IP, server.PORT);
        Client_Listener Ears = new Client_Listener(Client);
        // System.out.println("Client Started");

        new Thread(Ears).start();
        
        PrintWriter writer = new PrintWriter(Client.getOutputStream());

//_________________________________________________________________________________________________________________________

        Scanner In = new Scanner(System.in);
        String Txt = null;
        try {
          Thread.sleep(20);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        while (Own_Name== null){
          System.out.println("[Client] Not Connected to server , Waiting 10 Seconds..");
          Thread.sleep(10000);
        }
        System.out.printf("Welcome %s! Enjoy the Chat service! \n", Own_Name);
        do{
          try {
            
          
          Txt = In.nextLine();
          if (Txt.startsWith("/ws3")){
            int Index =  Txt.indexOf("3");
            client.Own_Name = Txt.substring(Index+2);
            // set_offset(0);
          }
          else if (Txt.startsWith("/sus")){
            System.out.printf("[Client] Your name is '%s'\n", client.Own_Name);
          }else {
          writer.write(Own_Name+"#"+ Txt+"\n");
          writer.flush();
          }}catch (Exception e) {
            System.out.println("Fehler meldung ~1");
          }
          
        } while (!Txt.equals("/dis"));

//_________________________________________________________________________________________________________________________

        writer.close();
        Client.close();
        In.close();
        // INside.close();
        System.out.println("[Client] Process Closed");
    } catch (ConnectException e){
        System.out.println("[Client] No Server Online");
    } catch (UnknownHostException e) {
        System.out.println("[Client] Host is not reachable");
        e.printStackTrace();
    } catch (Exception e) {
        System.out.println("[Client] Error At Client");
        e.printStackTrace();
    }



    }
}
