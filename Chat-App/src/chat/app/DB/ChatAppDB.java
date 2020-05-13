
package chat.app.DB;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatAppDB {

    private Connection con;
    
    public ChatAppDB() {
        //Connect database
        try{  
                Class.forName("com.mysql.jdbc.Driver");  
                con = (Connection) DriverManager.getConnection(  
                "jdbc:mysql://localhost:3306/chatappdb","root","Mrrobot5444");  
                 System.out.println("Database connection success");
                } catch(ClassNotFoundException | SQLException e) { System.out.println(e);}  
         } 

    public List<String> GetUserList() throws SQLException{
        String sql = "SELECT * FROM userlist";
        Statement statement = (Statement) con.createStatement();
        ResultSet result = statement.executeQuery(sql);
        
        List<String> resultList = new ArrayList<String>();
        
        while(result.next()) {
            resultList.add(result.getString(1));
        }

        return resultList;
    }
    
     public List<MessageDB> GetMessageList() throws SQLException{
        String sql = "SELECT * FROM messageList";
        Statement statement = (Statement) con.createStatement();
        ResultSet result = statement.executeQuery(sql);
        
        List<MessageDB> resultList = new ArrayList<MessageDB>();
        
        while(result.next()) {
            resultList.add(new MessageDB(result.getString(1), result.getString(2), result.getString(3), result.getString(4)));
        }

        return resultList;
    }
    
    public void InsertToUserList(String object) throws SQLException{
        String sql = "INSERT INTO userlist (username) VALUES (?)";
        PreparedStatement statement = (PreparedStatement) con.prepareStatement(sql);
        statement.setString(1, object);
        statement.executeUpdate();
    }
    
    public void InsertToMessageList(MessageDB object) throws SQLException{
        String sql = "INSERT INTO messagelist (sender, message, time, receiver) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = (PreparedStatement) con.prepareStatement(sql);
        statement.setString(1, object.getSender());
        statement.setString(2, object.getMessage());
        statement.setString(3, object.getTime());
        statement.setString(4, object.getReceiver());
        statement.executeUpdate();
    }
    
    public static void main(String[] args) throws SQLException{
        ChatAppDB chat = new ChatAppDB();
        
        List<MessageDB> resultList = chat.GetMessageList();
        MessageDB sa = new MessageDB("ADSD","DSA","DASAS","DSA");
        chat.InsertToUserList("HasanD");
        chat.InsertToMessageList(sa);
        for(MessageDB x : resultList){
            System.out.println(x.ToString());
        }
    }
    }

