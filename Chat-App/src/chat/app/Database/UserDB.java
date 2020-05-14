package chat.app.Database;

public class UserDB {
    
    private String userName;
    private String groupName;
    
    public UserDB(String userName, String groupName) {
        this.userName = userName;
        this.groupName = groupName;
    }
    
    public String getUserName() {
        return this.userName;
    }
    
    public String getGroupName(){
        return this.groupName;
    }
}
