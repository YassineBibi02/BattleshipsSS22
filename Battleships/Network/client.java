package Battleships.Network;


import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class client {
   static public String Own_Name ;
   static public int offset = 0 ; 
   protected static boolean gestopped = false;
   public static int get_offset(){
    return offset;
   }



    public static void main(String[] Args){

        
      try {
        Socket Client = new Socket(server.IP, server.PORT); //Establiches connection to server
        Client_Listener Ears = new Client_Listener(Client); 

        Thread earsThread = new Thread(Ears); 
        earsThread.start(); // starts The Listener Thread
        
        PrintWriter writer = new PrintWriter(Client.getOutputStream()); // Defines the Writer / pusher

//_________________________________________________________________________________________________________________________

        Scanner In = new Scanner(System.in); // Scanner for user input
        String Txt = null;
        try {
          Thread.sleep(20);            // This tiny wait gives chances for the Listener Thread to actually recieve and Give a proper name to client. Not mendatory but recommanded
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      
        while (Own_Name== null){      // If the Client still hasn't got the initlization Command from the Server then it should wait
         if ( !gestopped ){
          System.out.println("[Client] Server Full , Waiting..");  // essentially decalring once that server is Full but waiting in a loop
          gestopped = true;}
          Thread.sleep(1000); // wait 1 second to see if server is empty
        }
        if ( gestopped ){gestopped = false;} // Very neccesary , this is what let's the Client listener operate Normally
        System.out.printf("[Client] Welcome %s! Enjoy the Chat service! \n", Own_Name); // welcome message
        do{
          try {
            
          Txt = In.nextLine();
          if (Txt.startsWith("/ws3")){           // /ws3 is the command to change names , Handeled Locally ( WIP : Sends notification to server)
            int Index =  Txt.indexOf("3");
            String OldName = client.Own_Name;
            client.Own_Name = Txt.substring(Index+2);
            writer.write(Own_Name+"#"+"/ale$"+OldName+"\n");  // notifies server
            writer.flush();
          }
          else if (Txt.startsWith("/sus")){      // /sus , Command to Check your Name , Handeled Locally
            System.out.printf("[Client] Your name is '%s'\n", client.Own_Name);
          }
          else if (Txt.startsWith("/list")){      // /sus , Command to Check your Name , Handeled Locally
            writer.write(Own_Name+"#"+"/list\n");  // notifies server
            writer.flush();
          } else if (Txt.startsWith("/txtb")){           // /ws3 is the command to change names , Handeled Locally ( WIP : Sends notification to server)
            int Index =  Txt.indexOf("b");
            
            String New = Txt.substring(Index+2);
            writer.write(New +"\n");  // notifies server
            writer.flush();}
          else {
          writer.write(Own_Name+"#"+ Txt+"\n"); // if there's no Local Commands then Send a Coded Message to Server for Server Side Handling
          writer.flush();
          }}catch (Exception e) {
            System.out.println("Fehler meldung ~1");
          }
          
        } while (!Txt.equals("/dis")); // /dis is handeled both locally and Server side. essentially terminating the Two threads responsable for the connection

//_________________________________________________________________________________________________________________________

        writer.close();
        Client.close();
        In.close();
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
