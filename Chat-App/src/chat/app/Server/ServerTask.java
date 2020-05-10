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
                  // Now successful name has been chosen , we can start core process to manage client requests.
                  while (true) {
                        String receiver = in.nextLine();
                        // Client connection to end, if write and send "/quit" to server
                        if (receiver.toLowerCase().startsWith("/quit")) {
                           return;
                        }
                        // if client's username didn't assing.
                        if (receiver.equals("/singleUser")){ // Message to single user Action
                           out.println("Message to single user");
                        }
                        else if (receiver.equals("/group")){ // Message to a group Action
                           out.println("Message to group");
                        }
                        else if (receiver.equals("/createGroup")){ // Message to a group Action
                           out.println("Creating group...");
                        }
                        else if (receiver.startsWith("/allUser")){ // Message to all user Action
                           ChatServer.SendMessageToAll(receiver.substring(8), userName, out);
                           out.println("Message sended to all user");
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
    
    // Sets Client's Unique Username
    private synchronized void SetUniqueUserName(){
        out.println("Enter an user name:");
        while(true) {
            String name = in.nextLine();
            if (name.isBlank()){
               out.println("Username can not be empty");
            }
            else if (!(ChatServer.names.contains(name))){
                // Add name to names list
                ChatServer.names.add(name);
                // Add socket's writer to writers set
                ChatServer.writers.add(out);
                // Assing unique name to userName
                userName = name;
                // Sends return message to client
                out.println("Connected Server as " + userName);
                //Then finalize the process
                return;
            }
            else{
                out.println("The username is already using by another user");
            }
         }             
    } 
}