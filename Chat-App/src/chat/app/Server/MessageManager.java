package chat.app.Server;

import chat.app.Models.User;
import java.io.PrintWriter;
import java.util.List;

public class MessageManager {
            
   public static String serverResponseFormatter(String message){
         return " [" + Server.getServerTime() + " Server]  " + message;
    }
    
    public static String clientMessageFormatter(String message, String senderName) {
         return " [" + Server.getServerTime() + " " + senderName + "]  " +  message;
     }
    
    public static void messageSenderAsServer(PrintWriter writer, String message) {
        writer.println(serverResponseFormatter(message));
    }
    
    public static void messageSenderAsClient(PrintWriter writer, String message) {
        //TODO
    }
    
    //Group message
    public static void sendGroupMessage(String message, User user) {
        for(User person : user.getGroup().getGroupUsers()) {
            
            String finalMessage = clientMessageFormatter(message, "(Group " + user.getGroup().getGroupName() + ") "+ user.getName());
            
            person.getWriter().println(finalMessage);
        }
    }
    // Broadcast message
    public static void sendBroadcastMessage(String message, User user){
         for (User person : Server.getServerUserList()) {
             
            String finalMessage = MessageManager.clientMessageFormatter(message, user.getName());
            
            person.getWriter().println(finalMessage);
         }
    }
    //Private message
    public static void sendPrivateMessage(String message, User receiver, User sender){
         String finalMessage = clientMessageFormatter(message, sender.getName());
         
         receiver.getWriter().println(finalMessage);
    }
    
        //Private message
    public static void sendLoadedMessages(String message, User receiver, String sender, String messageTime){
         String finalMessage = " [" + messageTime + " " + sender + "]  " +  message;
         
         receiver.getWriter().println(finalMessage);
    }
}
        
    