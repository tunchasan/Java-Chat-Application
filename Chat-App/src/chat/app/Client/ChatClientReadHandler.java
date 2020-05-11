package chat.app.Client;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.exit;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ChatClientReadHandler implements Runnable {
    
    private Socket socket;
    private Scanner in;
    private ChatUI chatUI;
            
    public ChatClientReadHandler(Socket socket, ChatUI chatUI) throws IOException{
        this.socket = socket;
        in = new Scanner(this.socket.getInputStream());
        this.chatUI = chatUI;
    }
    
    @Override
    public void run() {
        String serverResponse = null;
            try{
                while (true){
                    serverResponse = in.nextLine();
                    chatUI.AddMessageToUI(" > " + serverResponse);
                    System.out.println(" > " + serverResponse);
            }
        } catch (NoSuchElementException e) { }
            finally {
                chatUI.RemoveUI();
                exit(1);
         }         
    }
}
