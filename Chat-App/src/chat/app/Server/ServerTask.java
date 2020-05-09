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
        
        this.userName = "";
    }
    
    @Override
    public void run() {
         System.out.println("Connected: " + userSocket);
             try {
                  var in = new Scanner(userSocket.getInputStream());
                  
                  var out = new PrintWriter(userSocket.getOutputStream(), true);

                  while (in.hasNextLine()) {
                      
                      // Username Assinging
                      if (userName == "") {
                          userName = in.nextLine();                          
                          out.println("Connected Server as " + userName);                         
                      }
                      
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