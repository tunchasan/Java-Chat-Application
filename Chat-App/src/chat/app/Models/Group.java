package chat.app.Models;

import chat.app.Server.Server;
import java.util.ArrayList;
import java.util.List;

public class Group {
    // The user' group print writers set for the client, used for sending message user' group
    private List<User> groupUsers;
    // The user' group name
    private String groupName;
    
    public Group () {
        this.groupName = "";
        this.groupUsers = new ArrayList<User>();
    }
    
    public void createGroup(String groupName){
        this.groupName = groupName;
    }
    
    public void addUserToGroup(User user){
        groupUsers.add(user);
        //Update user's group data
        user.updateGroup(this);
    }
    
    public void removeUserFromGroup(User user) {
        groupUsers.remove(user);
    }
    
    public String getGroupName(){
        return groupName;
    }
    
    public List<User> getGroupUsers(){
        return groupUsers;
    }
    
    public void setGroupList(List<User> group) {
        this.groupUsers = group;
    }
    
    public void setGroupName(String name) {
        this.groupName = name;
    }
}
