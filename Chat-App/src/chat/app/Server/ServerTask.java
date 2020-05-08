package chat.app.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerTask implements Runnable {
    
    public Socket userSocket;
    
    public String userName;

    ServerTask(Socket userSocket) {
        this.userSocket = userSocket;
    }
    
    @Override
    public void run() {
        System.out.println("Connected: " + userSocket);
             try {
                  var in = new Scanner(userSocket.getInputStream());
                  
                  var out = new PrintWriter(userSocket.getOutputStream(), true);
                  
                  while (in.hasNextLine()) {
                    out.println(in.nextLine().toUpperCase());
                  }
         } catch (Exception e) {
                  System.out.println("Error:" + userSocket); } 
             
             finally {
             try {
                    userSocket.close();
                } catch (IOException e) { }
                System.out.println("Closed: " + userSocket);
         }         
    }  
}