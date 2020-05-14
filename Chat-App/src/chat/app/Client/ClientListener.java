package chat.app.Client;

import chat.app.Views.ChatUI;
import java.io.IOException;
import static java.lang.System.exit;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientListener implements Runnable {
    
    private Socket socket;
    private Scanner in;
    private ChatUI chatUI;
            
    public ClientListener(Socket socket, ChatUI chatUI) throws IOException{
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
                    chatUI.AddMessageToUI(serverResponse);
                    System.out.println(serverResponse);
            }
        } catch (NoSuchElementException e) { }
            finally {
                chatUI.RemoveUI();
                exit(0);
         }         
    }
}
