/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.app.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class ChatClient {
    
    private static int SERVER_PORT_NO = 4999;
    
    public static void main(String[] args) throws Exception {
        
        System.out.println( "Server Command List\n"
                                 + "/singleUser + {Username} + {Message}  ---> Send message to a user\n"
                                 + "/group + {Message} ----------------------> Send message to a group\n"
                                 + "/allUser + {Message} --------------------> Send message to all user\n"
                                 + "/quit -----------------------------------> Close the connection to the server\n"
                                 + "********************************************************************************************");
        
         try (var socket = new Socket("localhost", SERVER_PORT_NO)) {
             
            ChatClientReadHandler conn = new ChatClientReadHandler(socket);
            
            new Thread(conn).start();
            
            System.out.println("Enter an user name:");
            
            var scanner = new Scanner(System.in);
            
            var out = new PrintWriter(socket.getOutputStream(), true);
            
            while (true) {
                
                out.println(scanner.nextLine());
            }
        }
    }   
}