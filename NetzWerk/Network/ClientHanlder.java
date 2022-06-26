package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHanlder implements Runnable {

    private Socket client;
    private PrintWriter writer;
    private BufferedReader reader;


    public ClientHanlder (Socket clientSocket) throws IOException {
        this.client = clientSocket;
        this.reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        this.writer = new PrintWriter(client.getOutputStream());
    }
     


    @Override
    public void run() {

       try {
       
         String s = null ;
     
         while ( ( s = reader.readLine() ) != null ) {
             writer.write("client: " + s + "\n");
             writer.flush();
             System.out.println("recieved from client :" + s );
             if ( s.equals("Close")){break;}
     
         }
     
        
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
    
}
