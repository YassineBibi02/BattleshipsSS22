package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class client {
    


    public static void main(String[] Args){

        
      try {
        Socket Client = new Socket("localhost", 1225);
        System.out.println("Client Started");

       
        PrintWriter writer = new PrintWriter(Client.getOutputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(Client.getInputStream()));

        Scanner In = new Scanner(System.in);
        String Txt = null;

        do{
          System.out.printf("Message : ");
          Txt = In.nextLine();
          
          System.out.println();
          writer.write(Txt+"\n");
          writer.flush();
  
          String s = null;
          s = reader.readLine();
          while ( !s.equals("Close") && !Txt.equals("Close") ){
           
            System.out.println(s );
          
            break;
          }
          
        } while (!Txt.equals("Close"));


        reader.close();
        writer.close();
        Client.close();
        In.close();
        System.out.println("Client Process Closed");

    } catch (UnknownHostException e) {
        System.out.println("Host is not reachable");
        e.printStackTrace();
    } catch (IOException e) {
        System.out.println("[Client] Error At Client");
        e.printStackTrace();
    }



    }
}
