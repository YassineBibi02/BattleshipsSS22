package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class server {

public static void main(String[] aStrings){

    try{
     ServerSocket server = new ServerSocket(1225);
     System.out.println("Server Started");

     Socket Client = server.accept();

     OutputStream out = Client.getOutputStream();
     PrintWriter writer = new PrintWriter(out);

     InputStream in = Client.getInputStream();
     BufferedReader reader = new BufferedReader(new InputStreamReader(in));

     String s = null ;

     while ( ( s = reader.readLine() ) != null ) {
         writer.write("client:" + s + "\n");
         writer.flush();
         System.out.println("recieved from client :" + s );
         if ( s.equals("Close")){break;}

     }

     writer.close();
     reader.close();
     server.close();




     System.out.println("Server Closed");
    }catch(IOException e) {
     e.printStackTrace();

    }
}
    
}
