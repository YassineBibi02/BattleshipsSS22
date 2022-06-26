package Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class server {


 protected static final int PORT = 1225;    
 private static ArrayList<ClientHanlder> Clients = new ArrayList<>();
 private static ExecutorService pool = Executors.newFixedThreadPool(2);
 
 public static void main(String[] aStrings){
    ServerSocket server;

    try{ 
      server = new ServerSocket(PORT);
     while ( true ){ 
       System.out.println("[Server] Started , waiting for Connections ..");
       Socket Client = server.accept();
       System.out.println("[Server] Client Conntected !");
       ClientHanlder ClientThread = new ClientHanlder(Client);
       Clients.add(ClientThread);
       pool.execute(ClientThread);
      
     }
    
      
    }catch(IOException e) {
     System.out.print("[Server] Error at Server Side");
     e.printStackTrace();

    
   }
    
 }}
