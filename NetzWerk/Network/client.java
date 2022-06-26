package Network;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class client {
    


    public static void main(String[] Args){

        
      try {
        Socket Client = new Socket(server.IP, server.PORT);
        Client_Listener Ears = new Client_Listener(Client);
        System.out.println("Client Started");

        new Thread(Ears).start();
       
        PrintWriter writer = new PrintWriter(Client.getOutputStream());
//_________________________________________________________________________________________________________________________

        Scanner In = new Scanner(System.in);
        String Txt = null;

        do{
          
          Txt = In.nextLine();
          

          writer.write(Txt+"\n");
          writer.flush();
          
        } while (!Txt.equals("Close"));

//_________________________________________________________________________________________________________________________

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
