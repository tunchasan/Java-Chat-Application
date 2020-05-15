package chat.app.Database;

import chat.app.Models.Group;
import chat.app.Models.User;
import chat.app.Server.Server;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private static Connection con;
    
    public static void ConnectDB() {
        //Connect database
        try{  
                Class.forName("com.mysql.jdbc.Driver");  
                con = (Connection) DriverManager.getConnection(  
                "jdbc:mysql://localhost:3306/chatappdb","root","Mrrobot5444");  
                 System.out.println("Database connection success");
                } catch(ClassNotFoundException | SQLException e) { System.out.println(e);}  
         } 

    public static List<UserDB> GetUserList() throws SQLException{
        String sql = "SELECT * FROM userlist";
        Statement statement = (Statement) con.createStatement();
        ResultSet result = statement.executeQuery(sql);
        
        List<UserDB> resultList = new ArrayList<UserDB>();
        
        while(result.next()) {
            resultList.add(new UserDB(result.getString(1), ""));
        }

        return resultList;
    }

    public static List<MessageDB> GetMessageList() throws SQLException{
        String sql = "SELECT * FROM messageList";
        Statement statement = (Statement) con.createStatement();
        ResultSet result = statement.executeQuery(sql);
        
        List<MessageDB> resultList = new ArrayList<MessageDB>();
        
        while(result.next()) {
            resultList.add(new MessageDB(result.getString(1), result.getString(2), result.getString(3), result.getString(4)));
        }

        return resultList;
    }
    
    public static void InsertToUserList(UserDB user) throws SQLException{
        String sql = "INSERT INTO userlist (username) VALUES (?)";
        PreparedStatement statement = (PreparedStatement) con.prepareStatement(sql);
        statement.setString(1, user.getUserName());
        statement.executeUpdate();
    }

    public static void InsertToMessageList(MessageDB object) throws SQLException{
        String sql = "INSERT INTO messagelist (sender, message, time, receiver) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = (PreparedStatement) con.prepareStatement(sql);
        statement.setString(1, object.getSender());
        statement.setString(2, object.getMessage());
        statement.setString(3, object.getTime());
        statement.setString(4, object.getReceiver());
        statement.executeUpdate();
    }
    
    public static void InsertManyMessageList(String message, String senderName) throws SQLException {
        for (User member : Server.getServerUserList()) {
            InsertToMessageList( 
                    new MessageDB(senderName, message, Server.getServerTime(), member.getName()));
        }
    }
    
    public static void InsertGroupMessageList(String message, String senderName, Group group) throws SQLException {
        for (User member : group.getGroupUsers()) {
            InsertToMessageList( 
                    new MessageDB(senderName, message, Server.getServerTime(), member.getName()));
        }
    }
    
    public static boolean isUserExist(String username) throws SQLException {
        List<UserDB> userList = GetUserList();
        
        for ( UserDB user : userList) {
            if (user.getUserName().equals(username)){
                return true;
                }
             }
        return false;
        }
    
    }

