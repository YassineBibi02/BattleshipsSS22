package com.example.trying;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Client_Listener implements Runnable{

    private Socket server;
    private BufferedReader reader;
    private PrintWriter writer;

    public Client_Listener ( Socket S) throws IOException{
        this.server = S;
        this.reader = new BufferedReader(new InputStreamReader(server.getInputStream()));
        this.writer = new PrintWriter(server.getOutputStream());
    }




    @Override
    public void run() {
       String serverCommand;
    try {
        while (true){
       
        serverCommand = reader.readLine();
        if (serverCommand == null){throw new IOException();}    // No Server Connection
        else {
        if (serverCommand.startsWith("/ws2")){           // The Command given by the server at the start of the Connection , so to assign it a proper name
           int Index =  serverCommand.indexOf("2");
           client.Own_Name = serverCommand.substring(Index+2);
        //    System.out.println("Intiliazed Name");

        
        } else  if (serverCommand.startsWith("/txtb")){           // The Command given by the server at the start of the Connection , so to assign it a proper name
            int Index =  serverCommand.indexOf("b");
            IpController.playControl.Message = serverCommand.substring(Index+2);
            //    System.out.println(Message); // debug ?
            IpController.playControl.PreviousMessage += "\n"+"Player1: "+IpController.playControl.Message;
            IpController.playControl.Chat.setText(IpController.playControl.PreviousMessage);
 
         } else
        
        System.out.println(serverCommand);}
        }
    }catch ( IOException e ) {
        System.out.println("[Client] Disconnected");

    } catch (Exception e) {
        System.out.println("[Client_Listener] Couldnt Recieve Server responce");
        e.printStackTrace();
    }finally{
        writer.close();
    }
       
        
    }
    
}
