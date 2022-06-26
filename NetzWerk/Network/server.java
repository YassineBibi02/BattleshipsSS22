package Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class server {

 protected static final String IP = "localhost";
 protected static final int PORT = 1225;    
 private static final int THREAD_COUNT = 2;

 private static ArrayList<ClientHanlder> Clients = new ArrayList<>();
 private static ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);
 
 public static void main(String[] aStrings){
    ServerSocket server;

    try{ 
      server = new ServerSocket(PORT);
     while ( true ){ 
       System.out.println("[Server] Waiting for Connections ..");
       Socket Client = server.accept();
       System.out.println("[Server] Connection established");
       ClientHanlder ClientThread = new ClientHanlder(Client, Clients);
       Clients.add(ClientThread);
       pool.execute(ClientThread);
      
     }
    
      
    }catch(IOException e) {
     System.out.print("[Server] Error at Server Side");
     e.printStackTrace();

    
   }
    
 }}
