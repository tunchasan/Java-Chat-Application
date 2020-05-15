package chat.app.Server;

import chat.app.Database.DBManager;
import chat.app.Models.Group;
import chat.app.Models.User;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.time.LocalDateTime;

public class Server {
    // Server client capacity
    private static int SERVET_LIMIT = 20;
    // Server port number
    private static int SERVER_PORT_NO = 4999;
    // Stores all user information seperately
    private static List <User> userList;
    // All client names, so we can check for duplicates upon registration
    private static List<Group> groupList;
    
    //Get server group list
    public static List<Group> getServerGroupList(){
        return groupList;
    }
    //Get server userdata list
    public static List<User> getServerUserList(){
        return userList;
    }
    //Add group to groupList
    public static void addServerGroupList(Group newGroup){
        groupList.add(newGroup);
    }
    //Add user to userList
    public static void addServerUserList(User newUser){
        userList.add(newUser);
    }
    //Remove from group list
    public static void removeFromGroupList(String groupName) {
        for (Group group : groupList) {
            if (group.getGroupName().equals(groupName)) {
                groupList.remove(group);
                return;
            }
        }
    }
    //Remove from user list
    public static void removeFromUserList(User user) {
        userList.remove(user);
    }
    //Controls whether user exist or not
    public static User isUserExist(String userName) {
        for (User user : userList) {
            if (userName.equals(user.getName())){
                return user;
            }
        }
        return null;
    }
    //Controls whether group exist or not
    public static Group isGroupExist(String groupName){
        for (Group group : groupList) {
            if (group.getGroupName().equals(groupName)){
                return group;
            }
        }
        return null;
    }
    //Return servertime as string
    public static String getServerTime(){
        return LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute();
    }
    
    public static void main(String[] args) throws Exception{
        try (var listener = new ServerSocket(SERVER_PORT_NO)) {
            // Server starts message
            String message = MessageManager.serverResponseFormatter("The chat server is running..");
            System.out.println(message);
            // Initialize the user data list
            userList = new ArrayList<User>();
            // Initialize name list
            groupList = new ArrayList<Group>();
            // Create new thread the pool
            var pool = Executors.newFixedThreadPool(SERVET_LIMIT);
            //Connect the DB
            DBManager.ConnectDB();

            while (true) {
                pool.execute(new ClientHandler(listener.accept()));
            }
        }
    }
}