/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.app.Server;

import chat.app.DB.UserDataHandler;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
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
            System.out.println("The chat server is running...");
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
            if (userList.get(i).GetName() == userName){
               userList.get(i).GetWriter().println(senderName + ": " + message);
               return Messages.Results.Message_successfully_sended + " to " + userName;
            }
        }
        return Messages.Results.User_not_exist.toString();
    }
    
    public static void SendMessageToGroup(String message, String groupName){
        
    }
    
    public static String SendMessageToAll(String message, String senderName){
         for (int i = 0; i < userList.size(); i ++) {
            if (userList.get(i).GetName() == senderName){
               continue; 
            }
            userList.get(i).GetWriter().println(senderName + ": " + message);
         }
         return Messages.Results.Message_successfully_sended.toString();
    }
}
