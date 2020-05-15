package chat.app.Server;

import chat.app.Database.DBManager;
import chat.app.Database.MessageDB;
import chat.app.Database.UserDB;
import chat.app.Models.User;
import java.sql.SQLException;

public class CommandManager {
    
    private User user;

    public CommandManager(User user) {
        this.user = user;
    }
    
    public void command_group(String command) throws SQLException {
        if (user.getGroup().getGroupUsers().size() == 0) {
             MessageManager.messageSenderAsServer(user.getWriter(), "Create a group for sending message to group");
        }
        else{
            while(true) {
                if (command.substring(6).isBlank()){
                    MessageManager.messageSenderAsServer(user.getWriter(), "Message can not be empty. Try again");
                    break;
                }
                else if (command.substring(7).isBlank()){
                    MessageManager.messageSenderAsServer(user.getWriter(), "Message can not be empty. Try again");
                    break;
                }
                else {
                    // Send group message to group members
                    MessageManager.sendGroupMessage(command.substring(7), user);
                    //Store message to db
                    System.out.println("(Group" + user.getGroup().getGroupName() + ") " + user.getName() + " : " + command.substring(7));
                    DBManager.InsertGroupMessageList(command.substring(7), "(Group " + user.getGroup().getGroupName() + ") " + user.getName(), user.getGroup());
                    break;
                }
            }
        }
    }
    
    public void command_createGroup() throws SQLException {
        if (user.getGroup().getGroupUsers().size() == 0) {
            MessageManager.messageSenderAsServer(user.getWriter(), "Type group name");
            
            while(true) {
                String groupName = user.getReader().nextLine();
                
                if (groupName.isBlank()) {
                    MessageManager.messageSenderAsServer(user.getWriter(), "Group name can not be empty. Try again.");
                }
                else if (Server.isGroupExist(groupName) == null) {
                        // User group initializition
                        user.getGroup().createGroup(groupName);
                        
                        MessageManager.messageSenderAsServer(user.getWriter(), "Add group to member. Type user names. To complete, write /done.");
                        
                        while(true) {
                            String member = user.getReader().nextLine();
                            
                            if (member.startsWith("/done")) {
                                if (user.getGroup().getGroupUsers().size() > 0){
                                    user.getGroup().addUserToGroup(user);
                                    //Add new group to serverList
                                    Server.addServerGroupList(user.getGroup());
                                    
                                    MessageManager.messageSenderAsServer(user.getWriter(), "Group created succesfully..");
                                    break;
                                }
                                else {
                                    MessageManager.messageSenderAsServer(user.getWriter(), "To done, at least you need to add a user.");
                                }
                            }
                            
                            else if (member.isBlank()) {
                                MessageManager.messageSenderAsServer(user.getWriter(), "User name can not be empty. Try again.");
                            }
                            //if the member is exist
                            else if (Server.isUserExist(member) != null) {
                                User memberUser = Server.isUserExist(member);
                                if (memberUser.equals(user) == false) {
                                    user.getGroup().addUserToGroup(memberUser);
                                    MessageManager.messageSenderAsServer(user.getWriter(), member +  " added to group.");
                                }
                                else {
                                    MessageManager.messageSenderAsServer(user.getWriter(), "You are already a member of the group.");
                                }
                            }  
                            else {
                                MessageManager.messageSenderAsServer(user.getWriter(), "User does not exist. Try again.");
                            }
                        }
                        break;
                } 
                else {
                    MessageManager.messageSenderAsServer(user.getWriter(), "Group name is using by others. Try again..");
                }
            }
        }
        else{
            MessageManager.messageSenderAsServer(user.getWriter(), "You are already a member of the group.");
        }
    }

    public void command_allUser(String command) throws SQLException {
       while(true) {
           if (command.substring(9).isBlank()) {
               MessageManager.messageSenderAsServer(user.getWriter(), "Message can not be empty. Try again");
           }
           else
               break;
        }
       // Broadcast message
       MessageManager.sendBroadcastMessage(command.substring(9), user);
       //Store message to db
       DBManager.InsertManyMessageList(command.substring(9), user.getName());
    }
    
    public void command_singleUser() throws SQLException {
        MessageManager.messageSenderAsServer(user.getWriter(),"Type receiver's user name"); 
         
        while(true) {
            String receiverName = user.getReader().nextLine();
            
            if (receiverName.isBlank()) {
                MessageManager.messageSenderAsServer(user.getWriter(),"User name can not be empty. Try again."); 
            }
            else{
                // if user is exist in server user list
                if (Server.isUserExist(receiverName) != null || DBManager.isUserExist(receiverName)) {
                    MessageManager.messageSenderAsServer(user.getWriter(),"Type your message"); 
                    // get receiver's object
                    User receiverUser = Server.isUserExist(receiverName);
                    while(true) {
                        String message = user.getReader().nextLine();
                        
                        if (message.isBlank()) {
                             MessageManager.messageSenderAsServer(user.getWriter(),"Message can not be empty. Try again."); 
                        }
                        else {
                            if (Server.isUserExist(receiverName) != null) {
                                //Request to server to send message
                                MessageManager.sendPrivateMessage(message, receiverUser, user);
                                MessageManager.messageSenderAsServer(user.getWriter(),"Message sended to " + receiverUser.getName());
                            }
                            else
                                MessageManager.messageSenderAsServer(user.getWriter(),"User is offline. The message will be shown when the user is online.");
                            // Store message to db
                            DBManager.InsertToMessageList(
                                    new MessageDB(
                                            user.getName(), message, Server.getServerTime().toString(), receiverName));
                            break;
                        }
                    }
                    break;
                }
                // If the receiver does not exist nor database and server user list
                else{
                    MessageManager.messageSenderAsServer(user.getWriter(),"User not exist. Try again.."); 
                }
            }
        }
    }
    
    public void command_quit() {
        //Remove user informations from server
        Server.removeFromUserList(user);
        // If user has a groups
        if (user.getGroup().getGroupUsers().size() > 0) {
            // Just remove user information from group. We will add the user on next login
            user.getGroup().removeUserFromGroup(user);
            // if no one is available in the group, we remove the group information from server. But we already saved it in database.
            if (user.getGroup().getGroupUsers().size()  == 0) {
                Server.removeFromGroupList(user.getGroup().getGroupName());
            }
        }
        MessageManager.sendGroupMessage(user.getName() + " left to group.", user);
    }
    
    public void command_assingName() throws SQLException {
        MessageManager.messageSenderAsServer(user.getWriter(),  "Type a new username or type existing one");
        while(true) {
            String name = user.getReader().nextLine();
            if (name.isBlank()) {
               MessageManager.messageSenderAsServer(user.getWriter(), "Username can not be empty. Try again");
            }
            else if (Server.isUserExist(name) == null){
                // Assing unique name to user' name
                user.updateName(name);
                // Add new user to user list in server
                Server.addServerUserList(user);
                // If the username exist in database, load and print user informations
                if (DBManager.isUserExist(name)) {
                    //get user message data from database
                    // Sends return message to client
                    MessageManager.messageSenderAsServer(user.getWriter(), "Welcome back " + user.getName());
                    // Retrieve messages from database and print it
                    for (MessageDB message : DBManager.GetMessageList()) {
                        // if receiver is current user
                        if (message.getReceiver().equals(name)) {
                            MessageManager.sendLoadedMessages(message.getMessage(), user, message.getSender(), message.getTime());
                        }
                    }
                }
                else {
                    // Sends return message to client
                    MessageManager.messageSenderAsServer(user.getWriter(), "Connected Server as " + user.getName());
                    // // Store user to db
                    DBManager.InsertToUserList(new UserDB(name, ""));
                }
                //Then finalize the process
                return;
            }
            else{
                MessageManager.messageSenderAsServer(user.getWriter(), "The username is already using by another user");
            }
        }             
    }
    
    public void command_unkown() {
        MessageManager.messageSenderAsServer(user.getWriter(), "Wrong command. Try again!");
    }
}


                        
                        