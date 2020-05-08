/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.app.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerTask implements Runnable {
    
    private Socket socket;

    ServerTask(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
            //TODO
    }
    
}
