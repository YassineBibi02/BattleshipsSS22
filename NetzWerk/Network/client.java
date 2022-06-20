package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class client {
    


    public static void main(String[] Args){

        
      try {
        Socket Client = new Socket("localhost", 1225);
        System.out.println("Client Started");

        OutputStream out = Client.getOutputStream();
        PrintWriter writer = new PrintWriter(out);
        InputStream in = Client.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        Scanner In = new Scanner(System.in);

        writer.write(In.nextLine()+"\n");
        writer.flush();

        String s = null;
        while ( ( s = reader.readLine() ) != null ){

         System.out.println(s );
        }

        reader.close();
        writer.close();


    } catch (UnknownHostException e) {
        System.out.println("Host is not reachable");
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }



    }
}
