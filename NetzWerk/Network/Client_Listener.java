package Network;

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
        System.out.println(serverCommand);
        }
    } catch (IOException e) {
        System.out.println("[Client_Listener] Couldnt Recieve Server responce");
        e.printStackTrace();
    }finally{
        writer.close();
    }
       
        
    }
    
}
