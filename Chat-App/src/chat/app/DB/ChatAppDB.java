
package chat.app.DB;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatDB {
    
    private Connection con;
    
    public ChatDB() {
        //Connect database
        try{  
                Class.forName("com.mysql.jdbc.Driver");  
                con = (Connection) DriverManager.getConnection(  
                "jdbc:mysql://localhost:3306/chatappdb","root","Mrrobot5444");  
                 System.out.println("Database connection success");
                } catch(ClassNotFoundException | SQLException e) { System.out.println(e);}  
         } 
    
    public void Create(){
        
    }
    
    public List<String> GetUserList() throws SQLException{
        String sql = "SELECT * FROM userlist";
        Statement statement = (Statement) con.createStatement();
        ResultSet result = statement.executeQuery(sql);
        
        List<String> resultList = new ArrayList<String>();
        
        while(result.next()) {
            String username = result.getString(1);
            resultList.add(username);
        }

        return resultList;
    }
    
    public void Update(){
        
    }
    
    public void Delete(){
        
    }
    
    public static void main(String[] args) throws SQLException{
        ChatDB chat = new ChatDB();
        
        List<String> resultList = chat.GetUserList();

        for(String x : resultList){
            System.out.println(x);
        }
    }
    }

