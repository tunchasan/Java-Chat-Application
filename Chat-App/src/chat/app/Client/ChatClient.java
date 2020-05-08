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
        
        try (var socket = new Socket("localhost", SERVER_PORT_NO)) {
            
            System.out.println("Enter lines of text then Ctrl+D or Ctrl+C to quit");
            
            var scanner = new Scanner(System.in);
            
            var in = new Scanner(socket.getInputStream());
            
            var out = new PrintWriter(socket.getOutputStream(), true);
            
            while (scanner.hasNextLine()) {
                
                out.println(scanner.nextLine());
                
                System.out.println(in.nextLine());
            }
        }
    }   
}
