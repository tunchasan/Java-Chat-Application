package chat.app.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerTask implements Runnable {
    
    private Socket userSocket; 
    private String userName;
    private Scanner in; //receiver
    private PrintWriter out; //sender
    
    ServerTask(Socket userSocket) {
        this.userSocket = userSocket;
        this.userName = "";
    }
    
    @Override
    public void run() {
         System.out.println("Connected: " + userSocket);
             try {
                  in = new Scanner(userSocket.getInputStream());
                  
                  out = new PrintWriter(userSocket.getOutputStream(), true);
                  // Set client name as unique
                  SetUniqueUserName();
                   
                  while (in.hasNextLine()) {
                        String receiver = in.nextLine();
                        // if client's username didn't assing.
                        if (receiver.equals("1")){ // Message to single user Action
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
    private synchronized void SetUniqueUserName(){
        while(true) {
            String name = in.nextLine();
            if (name.isBlank()){
               out.println("Username can not be empty");
            }
            else if (!(ChatServer.names.contains(name))){
                ChatServer.names.add(name);
                userName = name;
                out.println("Connected Server as " + userName);
                return;
            }
            else{
                out.println("The username is already using by another user");
            }
         }             
    } 
}