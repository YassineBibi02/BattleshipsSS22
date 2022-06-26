package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHanlder implements Runnable {

    private Socket client;
    private PrintWriter writer;
    private BufferedReader reader;
    private ArrayList<ClientHanlder> Clients ;



    public ClientHanlder (Socket clientSocket, ArrayList<ClientHanlder> C) throws IOException {
        this.client = clientSocket;
        this.reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        this.writer = new PrintWriter(client.getOutputStream(), true);
        this.Clients = C;
    }
     


    @Override
    public void run() {

       try {
//_________________________________________________ essentially server functionality _____________________________________
         String s = null ;
         new Name();
         String Naame = Name.Get_Name();
         writer.println("/ws2 "+Naame);
         while ( ( s = reader.readLine() ) != null ) {
             ToAll(s);
             System.out.println("recieved from client :" + s );
             if ( s.equals("Close")){break;}
     
         }   
//_________________________________________________________________________________________________________________________
        }
        catch( IOException e){
            System.out.println("[ClientHandler] Error Running the ClientHandler Thread!");
            e.printStackTrace();
        }
        finally{
            writer.close();
            try {
                reader.close();
            } catch (IOException e) {
                System.out.println("[ClientHandler] Error Closing Reader ");
                e.printStackTrace();
            }
        }
        
    }
    


    private void ToAll(String msg){
        String ClientName = "";
        int firstspace = msg.indexOf(" ");
         if ( firstspace != -1){
            ClientName = msg.substring(0, firstspace+2);
            msg = msg.substring(firstspace+3);}

       for ( ClientHanlder aClient : Clients ){
         if (aClient== this) {} else {
         

         aClient.writer.println("["+ ClientName+"]: "+msg);}
       }
         
    }
}
