package chat.app.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerTask implements Runnable {
    
    private Socket userSocket;
    
    private String userName;
    
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
                        if( userName ==  "" ) { // Username Assing Action
                           // Sends client's input to set username
                          out.println(SetUserName(in));
                        }
                        else if (in.nextLine().equals("1")){ // Message to single user Action
                           out.println("Message to single user");
                        }
                        else if (in.nextLine().equals("2")){ // Message to a group Action
                           out.println("Message to group");
                        }
                        else if (in.nextLine().equals("3")){ // Message to all user Action
                           out.println("Message to all user");
                        }
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
    private String SetUserName(Scanner in){
            userName = in.nextLine();                                       
            return "Connected Server as " + userName;
         }             
    } 