package chat.app.Server;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class UserDataHandler {
    // The client name
    private String name;
    // The print writers for the client, used for sending message server to client
    private PrintWriter writer;
    // The user chat group object
    private ChatGroup group;
    
    public UserDataHandler(String name, PrintWriter writer){
        this.name = name;
        this.writer = writer;
        group = new ChatGroup();
    }
    
    public String GetName(){
        return this.name;
    }
    
    public PrintWriter GetWriter(){
        return this.writer;
    }
    
    public ChatGroup GetGroup(){
        return this.group;
    }
}
