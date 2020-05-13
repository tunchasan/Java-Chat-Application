
package chat.app.DB;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ChatDB {
    
    public ChatDB() {
        //Connect database
        try{  
                Class.forName("com.mysql.jdbc.Driver");  
                Connection con = (Connection) DriverManager.getConnection(  
                "jdbc:mysql://localhost:3306/chatappdb","root","Mrrobot5444");  
                 System.out.println("Database connection success");
                } catch(ClassNotFoundException | SQLException e) { System.out.println(e);}  
         } 
    
    }

