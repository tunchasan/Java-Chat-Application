package chat.app.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClientReadHandler implements Runnable {
    
    private Socket socket;
    private Scanner in;
    private PrintWriter out;
            
    public ChatClientReadHandler(Socket socket) throws IOException{
        this.socket = socket;
        in = new Scanner(this.socket.getInputStream());
        out = new PrintWriter(this.socket.getOutputStream(), true);
    }
    
    @Override
    public void run() {
        String serverResponse = null;
        while (true){
                serverResponse = in.nextLine();
                System.out.println(" > " + serverResponse);
        }
    }
}
