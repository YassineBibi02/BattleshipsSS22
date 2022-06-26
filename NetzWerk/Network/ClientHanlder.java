package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHanlder implements Runnable {

    private Socket Client;
    private PrintWriter writer;
    private BufferedReader reader;
    private ArrayList<ClientHanlder> Clients ;



    public ClientHanlder (Socket clientSocket, ArrayList<ClientHanlder> C) throws IOException {
        this.Client = clientSocket;
        this.reader = new BufferedReader(new InputStreamReader(Client.getInputStream()));
        this.writer = new PrintWriter(Client.getOutputStream(), true);
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
         EToAll("'"+ Naame +"' Connected");
         while ( ( s = reader.readLine() ) != null ) {
             System.out.printf("recieved from %s : %s", extract_name(s),extract_text(s) );
             if ( extract_text(s).startsWith("/dis")){
                String eName = extract_name(s);
                EToAll("'"+ eName +"' Disconnected");
                break;}
             ToAll(s);
             
             
     
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
    

    private String extract_name(String msg){
        String ClientName = "";
        int firstspace = msg.indexOf("#");
         if ( firstspace != -1){
            ClientName = msg.substring(0, firstspace + client.get_offset());
        }
        return ClientName;
    }

    private String extract_text(String msg){
        String Message= "";
        int firstspace = msg.indexOf("#");
        if ( firstspace != -1){
         Message = msg.substring(firstspace+client.get_offset()+1);
        }
        return Message;
    }



    private void ToAll(String msg){

       for ( ClientHanlder aClient : Clients ){
         if (aClient== this) {} else {
         

         aClient.writer.println("["+ extract_name(msg)+"]: "+extract_text(msg));}
       }
         
    }

    private void EToAll(String msg){
        for ( ClientHanlder aClient : Clients ){
            if (aClient== this) {} else {
            aClient.writer.println("[Server] "+ msg);}}
    }
}
