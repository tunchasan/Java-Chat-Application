package chat.app.Models;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class User {
    // The client name
    private String name;
    // The print writers for the client, used for sending message server to client
    private PrintWriter out;
    // The scanner that manages server -> client messages
    private Scanner in;
    // The usersocket that manages to communication
    private Socket userSocket; 
    // Store group information
    private Group group;
    
    public User(Socket socket) throws IOException{
        this.name = "";
        this.userSocket = socket;
        this.group = new Group();
        this.in = new Scanner(userSocket.getInputStream());
        this.out = new PrintWriter(userSocket.getOutputStream(), true);
    }
    
    public void updateName(String userName) {
        this.name = userName;
    }
    
    public void updateGroup(Group group) {
        this.group = group;
    }
    
    public String getName(){
        return this.name;
    }
    
    public PrintWriter getWriter(){
        return this.out;
    }
    
    public Scanner getReader(){
        return this.in;
    }
    
    public Socket getSocket(){
        return this.userSocket;
    }
    
    public Group getGroup() {
        return this.group;
    }
    
    public boolean isUserOnline() {
        return this.userSocket.isClosed();
    }
}
