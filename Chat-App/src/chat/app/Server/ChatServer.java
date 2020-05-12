/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.app.Server;

import chat.app.DB.UserDataHandler;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class ChatServer {
    // Server client capacity
    private static int SERVET_LIMIT = 20;
    // Server port number
    private static int SERVER_PORT_NO = 4999;
    // Stores all user information seperately
    private static List <UserDataHandler> userList;
    // All client names, so we can check for duplicates upon registration
    private static List<String> names;
    
    //Get server name list
    public static List<String> GetNameList(){
        return names;
    }
    
    //Get server userdata list
    public static List<UserDataHandler> GetUserList(){
        return userList;
    }
    
    public static void main(String[] args) throws Exception{
        
        try (var listener = new ServerSocket(SERVER_PORT_NO)) {
            // Server starts message
            System.out.println("The chat server is running..");
            // Initialize the user data list
            userList = new ArrayList<UserDataHandler>();
            // Initialize name list
            names = new ArrayList<String>();
            // Create new thread the pool
            var pool = Executors.newFixedThreadPool(SERVET_LIMIT);

            while (true) {
                ServerTask task = new ServerTask(listener.accept()); 
                pool.execute(task);
            }
        }
    }
    
    public static String SendMessageToPerson(String message, String userName, String senderName){
         for (int i = 0; i < userList.size(); i ++) {
            if (userList.get(i).GetName().equals(userName)){
               userList.get(i).GetWriter().println(senderName + ": " + message);
               System.out.println((userList.get(i).GetName()));
               break;
            }
        }
        return "Message successfuly sended to " + userName;
    }
    
    public static void SendMessageToGroup(String message, List<PrintWriter> groupWriter, String senderName){
        for(PrintWriter writer:groupWriter){
            writer.println(senderName + ": " + message);
        }
    }
    
    public static String SendMessageToAll(String message, String senderName){
         for (int i = 0; i < userList.size(); i ++) {
            if (userList.get(i).GetName().equals(senderName) ){
               continue; 
            }
            userList.get(i).GetWriter().println(senderName + ": " + message);
         }
         return "Message successfuly sended.";
    }
}
