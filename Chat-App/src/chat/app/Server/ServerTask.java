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
                    // if client's username didn't assing.
                    if( userName ==  "" ) {
                        // Sends client's input to set username
                        SetUserName(in, out);
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
    
    // Sets Client's Username
    private void SetUserName(Scanner in, PrintWriter out){
            userName = in.nextLine();                          
            out.println("Connected Server as " + userName);                         
         }             
    } 