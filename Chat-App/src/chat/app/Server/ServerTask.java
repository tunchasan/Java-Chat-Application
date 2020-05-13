package chat.app.Server;

import chat.app.DB.UserDataHandler;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerTask implements Runnable {
    
    private Socket userSocket; 
    private String userName;
    private Scanner in; //receiver
    private PrintWriter out; //sender
    private UserDataHandler userData;
    
    ServerTask(Socket userSocket) {
        this.userSocket = userSocket;
        this.userName = "";
    }
    
    @Override
    public void run() {
         System.out.println(ChatServer.ServerResponseFormatter("Connected: " + userSocket));
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
                           // Remove user informations from server
                           ChatServer.GetNameList().remove(userName);
                           ChatServer.GetUserList().remove(userData);
                           // Group warns message
                           if (userData.GetGroup().GetGroupSize() > 0) {
                               userData.GetGroup().SendMessageToGroup(userName + " has left the group.", "");
                           }
                           return;
                        }
                        // if client's username didn't assing.
                        if (receiver.equals("/singleUser")){ // Message to single user Action
                           out.println(ChatServer.ServerResponseFormatter("Type receiver's user name")); 
                           while(true){
                               receiver = in.nextLine();
                               
                               if(receiver.isBlank()){
                                   out.println(ChatServer.ServerResponseFormatter("User name can not be empty. Try again.")); 
                               }
                               else{
                                   if (ChatServer.GetNameList().contains(receiver)){
                                      String receiverName = receiver;
                                      
                                      out.println(ChatServer.ServerResponseFormatter("Type your message"));
                                      
                                      while(true){
                                          String message = in.nextLine();
                                          
                                          if(message.isBlank()){
                                            out.println(ChatServer.ServerResponseFormatter("User name can not be empty. Try again.")); 
                                          }
                                          else{
                                              //Request to server to send message
                                              ChatServer.SendMesssageToPerson(message, receiverName, userName);
                                              // Send request result message to client
                                              //out.println(ChatServer.ServerResponseFormatter("Message successfuly sended."));
                                              break;
                                          }
                                    }
                                      break;
                                      
                                    } else{
                                       out.println(ChatServer.ServerResponseFormatter("User not exist. Try again.")); 
                                   }
                               }
                           }
                        }
                        else if (receiver.startsWith("/group")){ // Message to a group Action
                           if (userData.GetGroup().GetGroupSize() == 0){
                                out.println(ChatServer.ServerResponseFormatter("Create a group for sending message to group"));
                           }
                           else{
                               while (true) {
                                    if (receiver.substring(7).isBlank()){
                                        out.println(ChatServer.ServerResponseFormatter("Message can not be empty. Try again")); 
                                    }  
                                    else{
                                        userData.GetGroup().SendMessageToGroup(receiver.substring(7), userName);
                                        //out.println(ChatServer.ServerResponseFormatter("Message sended successfuly.")); 
                                        break;
                                    }
                               }
                           }
                        }
                        else if (receiver.equals("/createGroup")){ // Message to a group Action
                           
                           if (userData.GetGroup().GetGroupSize() == 0){
                               out.println(ChatServer.ServerResponseFormatter("Type group name."));
                               while(true){
                                    // Get client request
                                    receiver = in.nextLine();

                                    if (receiver.isBlank()){
                                        out.println(ChatServer.ServerResponseFormatter("Group name can not be empty. Try again.")); 
                                    } 
                                    else{
                                        // User group initializition
                                        userData.GetGroup().CreateGroup(receiver);
                                        
                                       out.println(ChatServer.ServerResponseFormatter("Add group to member. Type user names. To complete, write /done.")); 
                                        while (true){
                                             receiver = in.nextLine();
                                             if (receiver.startsWith("/done")){
                                                 if (userData.GetGroup().GetGroupSize() > 0) {
                                                      userData.GetGroup().AddUserToGroup(out);
                                                      // Update group information for other members
                                                      for (UserDataHandler data:ChatServer.GetUserList()){
                                                          if (userData.GetGroup().GetGroupList().contains(data.GetWriter())){
                                                               // Update group member group data
                                                               data.GetGroup().SetGroupName(userData.GetGroup().GetGroupName());
                                                               
                                                               data.GetGroup().SetGroupList(userData.GetGroup().GetGroupList());
                                                          }
                                                      }
                                                      out.println(ChatServer.ServerResponseFormatter("Group created succesfully.")); 
                                                      break;
                                                 }
                                                 else{
                                                      out.println(ChatServer.ServerResponseFormatter("To done, at least you need to add a user.")); 
                                                 }
                                             }
                                             
                                             if (receiver.isBlank()){
                                                 out.println(ChatServer.ServerResponseFormatter("User name can not be empty. Try again.")); 
                                             }
                                             else if (ChatServer.GetNameList().contains(receiver)) {
                                                 for(UserDataHandler data:ChatServer.GetUserList()){
                                                     if (data.GetName().equals(receiver)){
                                                         if (data.GetName().equals(userName) == false) {
                                                             userData.GetGroup().AddUserToGroup(data.GetWriter());
                                                             out.println(receiver + " added to group"); 
                                                             break;
                                                         }
                                                         else{
                                                             out.println(ChatServer.ServerResponseFormatter("You are already a member of the group."));
                                                         }
                                                     }
                                                 }
                                             }
                                             else{
                                                 out.println(ChatServer.ServerResponseFormatter("User does not exist. Try again.")); 
                                             }
                                         }
                                        break;
                                    } 
                                }
                           }
                           else{
                               out.println(ChatServer.ServerResponseFormatter("You are already a member of a group..")); 
                           }
                        }
                        else if (receiver.startsWith("/allUser")){ // Message to all user Action
                           while(true){
                               if(receiver.substring(9).isBlank()){
                                    out.println(ChatServer.ServerResponseFormatter("Message can not be empty. Try again."));
                               } 
                               else{
                                   break;
                               }
                           }
                           ChatServer.SendMessageToAll(receiver.substring(9) , userName);
                           
                           //out.println(ChatServer.ServerResponseFormatter("Message successfuly sended."));
                        }
                        else{
                           out.println(ChatServer.ServerResponseFormatter("Wrong command. Try again!"));
                        }
                  }
                  
         } catch (IOException e) {
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
        out.println(ChatServer.ServerResponseFormatter("Type a username"));
        while(true) {
            String name = in.nextLine();
            if (name.isBlank()){
               out.println(ChatServer.ServerResponseFormatter("Username can not be empty"));
            }
            else if (!(ChatServer.GetNameList().contains(name))){
                // TODO
                // If the username exist in database, load and print user informations
                // Add name to names list in server
                ChatServer.GetNameList().add(name);
                //Create new data object to store user information
                userData = new UserDataHandler(name, out);
                //Add data to list
                ChatServer.GetUserList().add(userData);
                // Assing unique name to userName
                userName = name;
                // Sends return message to client
                out.println(ChatServer.ServerResponseFormatter("Connected Server as " + userName));
                //Then finalize the process
                return;
            }
            else{
                out.println(ChatServer.ServerResponseFormatter("The username is already using by another user"));
            }
        }             
    } 
}