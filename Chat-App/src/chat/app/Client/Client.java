package chat.app.Client;

import chat.app.Views.ChatUI;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    
    private static int SERVER_PORT_NO = 4999;
    
    public static void main(String[] args) throws Exception {
        
         System.out.println( "*******************************Server Command List******************************\n"
                                 + "/createGroup -----------> Create Group\n"
                                 + "/singleUser  -----------> Send message to a user\n"
                                 + "/group + {Message} -----> Send message to a group\n"
                                 + "/allUser + {Message} ---> Send message to all user\n"
                                 + "/quit ------------------> Close the connection to the server\n"
                                 + "********************************************************************************");
        
         try (var socket = new Socket("localhost", SERVER_PORT_NO)) {
             
            var scanner = new Scanner(System.in);
            
            var out = new PrintWriter(socket.getOutputStream(), true);
            
            // Create new UI for new user
            ChatUI chatUI = new ChatUI(out);
            // Create an object for handling client read actions in thread
            ClientListener conn = new ClientListener(socket, chatUI);
            // Start the thread
            new Thread(conn).start();
            
            while (true) {
                out.println(scanner.nextLine());
            }
        }
    }   
}