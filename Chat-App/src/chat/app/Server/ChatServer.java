/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.app.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class ChatServer {

    public static int SERVET_LIMIT = 20;
    
    private static int SERVER_PORT_NO = 4999;
    
    public static void main(String[] args) throws Exception{
        
        try (var listener = new ServerSocket(SERVER_PORT_NO)) {
            
            System.out.println("The chat server is running...");
            
            var pool = Executors.newFixedThreadPool(SERVET_LIMIT);

            while (true) {
                ServerTask task = new ServerTask(listener.accept()); 
                pool.execute(task);
            }
        }
    }       
}
