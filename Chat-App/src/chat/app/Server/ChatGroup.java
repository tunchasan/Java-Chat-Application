
package chat.app.Server;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class ChatGroup {
    // The user' group print writers set for the client, used for sending message user' group
    private Set<PrintWriter> groupWriters;
    // The user' group name
    private String groupName;
    
    public String CreateGroup(String groupName){
        
        this.groupName = groupName;
        
        this.groupWriters = new HashSet<PrintWriter>();
        // Return result message to server
        return groupName + " has been created by";
    }
    
    public String AddUserToGroup(){
        
        return "";
    }
    
    public String SendMessageToGroup(){
        
        return "";
    }
}
