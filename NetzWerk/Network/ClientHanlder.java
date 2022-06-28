package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHanlder implements Runnable {
    private String Naame;
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
         Naame = Name.Get_Name();      // Assign a Name to The Client in an Increasing fashion
         writer.println("/ws2 "+Naame);       // Pushes the Name Command to the Client
         EToAll("'"+ Naame +"' Connected");   // Custom Message send to Other Connected Users
         while ( ( s = reader.readLine() ) != null ) {
             if ( extract_text(s).startsWith("/dis")){ // if the Client wishes to disconnect Breaks the Listening Loop , thus ending the Connection and ending the thread
                EToAll("'"+ extract_name(s) +"' Disconnected"); // broadcasts that the client is disconnected 
                break;} else
             if ( extract_text(s).startsWith("/ale")){  
                System.out.printf("[Server] [%s] : Changed Name to [%s]\n",extract_Oldname(s) ,extract_name(s) ); // Feedback to Server System
                EToAll("'"+extract_Oldname(s)+"' Changed Name to ["+extract_name(s)+"]");
                Naame = extract_name(s);
             }  else
             if ( extract_text(s).startsWith("/list")){  
                writer.println("[Server] Connected Users : "+ Get_ALLnames());
               
             } else {
             System.out.printf("[Server] recieved from [%s] : %s\n", extract_name(s),extract_text(s) ); // Feedback to Server System
             ToAll(s); // Sends the Message ( incase it doesnt have any commands ) to the connected clients
             }
     
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
    

    
    // a function to decode the coded message sent by clients
    // it extracts the Client's Name
    private String extract_name(String msg){
        String ClientName = "";
        int firstspace = msg.indexOf("#");
         if ( firstspace != -1){
            ClientName = msg.substring(0, firstspace + client.get_offset());
        }
        return ClientName;
    }

    // a function to decode the coded message sent by clients
    // it extracts the Client's Message
    private String extract_text(String msg){
        String Message= "";
        int firstspace = msg.indexOf("#");
        if ( firstspace != -1){
         Message = msg.substring(firstspace+client.get_offset()+1);
        }
        return Message;
    }


    private String extract_Oldname(String msg){
        String Message = extract_text(msg);
        int firstspace = msg.indexOf("$");
        if ( firstspace != -1){
            Message = msg.substring(firstspace+1);
           }
        return Message;
    }


    // The Function that sends the Text to all other Connected Users
    private void ToAll(String msg){

       for ( ClientHanlder aClient : Clients ){
         if (aClient== this) {} else {
         

         aClient.writer.println("["+ extract_name(msg)+"]: "+extract_text(msg));}
       }
         
    }
    
    private String get_Name(){
        return this.Naame;
    }

    private String Get_ALLnames(){
        String txt ="| ";
        for ( ClientHanlder aClient : Clients ){
            if (!(aClient.get_Name() == null)){
            txt += aClient.get_Name() + " | ";}}
        return txt;
    }

    // A Function That is used by the Server to Send Custom Messages
    private void EToAll(String msg){
        for ( ClientHanlder aClient : Clients ){
            if (aClient== this) {} else {
            aClient.writer.println("[Server] "+ msg);}}
    }


}
