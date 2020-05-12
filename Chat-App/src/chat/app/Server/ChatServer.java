/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.app.Server;

import chat.app.DB.UserDataHandler;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.time.LocalDateTime;

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
            System.out.println(ServerResponseFormatter("The chat server is running.."));
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
    
    public static void SendMesssageToPerson(String message, String userName, String senderName){
         for (int i = 0; i < userList.size(); i ++) {
            if (userList.get(i).GetName().equals(userName)){
               userList.get(i).GetWriter().println(ClientMessageFormatter(message, senderName));
               break;
            }
        }
    }
    
    public static void SendMessageToGroup(String message, List<PrintWriter> groupWriter, String senderName, String groupName){
        for(PrintWriter writer:groupWriter){
            writer.println(ClientMessageFormatter(message, "(Group " + groupName + ") "+ senderName));
        }
    }
    
    public static void SendMessageToAll(String message, String senderName){
         for (int i = 0; i < userList.size(); i ++) {
            if (userList.get(i).GetName().equals(senderName) ){
               continue; 
            }
            userList.get(i).GetWriter().println(senderName + ": " + message);
         }
    }
    
    public static String GetServerTime(){
        return LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute();
    }
    
    public static String ServerResponseFormatter(String message){
        return " [" + ChatServer.GetServerTime() + " Server]  " + message;
    }
    
    public static String ClientMessageFormatter(String message, String senderName) {
        return " [" + ChatServer.GetServerTime() + " " + senderName + "] " +  message;
    }
}