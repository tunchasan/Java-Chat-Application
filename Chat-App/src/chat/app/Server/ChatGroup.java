
package chat.app.Server;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ChatGroup {
    // The user' group print writers set for the client, used for sending message user' group
    private List<PrintWriter> groupWriters;
    // The user' group name
    private String groupName;
    
    public ChatGroup (){
        this.groupName = "";
        this.groupWriters = new ArrayList<PrintWriter>();
    }
    
    public void CreateGroup(String groupName){
        this.groupName = groupName;
    }
    
    public void AddUserToGroup(PrintWriter writer){
        groupWriters.add(writer);
    }
    
    public void SendMessageToGroup(String message, String userName){
        ChatServer.SendMessageToGroup(message,groupWriters, userName);
    }
    
    public String GetGroupName(){
        return groupName;
    }
    
    public int GetGroupSize(){
        return groupWriters.size();
    }
}
