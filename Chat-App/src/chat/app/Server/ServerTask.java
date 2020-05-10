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
                        String receiver = in.nextLine();
                        // if client's username didn't assing.
                        if (userName.equals("")) { // Username Assing Action
                           // Sends client's input to set username
                          out.println(SetUserName(receiver));
                        }
                        else if (receiver.equals("1")){ // Message to single user Action
                           out.println("Message to single user");
                        }
                        else if (receiver.equals("2")){ // Message to a group Action
                           out.println("Message to group");
                        }
                        else if (receiver.equals("3")){ // Message to all user Action
                           out.println("Message to all user");
                        }
                        else{
                            out.println("Wrong action!");
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
    private String SetUserName(String name){
            userName = name;       
            return "Connected Server as " + userName;
         }             
    } 