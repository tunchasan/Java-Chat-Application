package chat.app.Server;

import chat.app.Models.User;
import java.sql.SQLException;

public class ServerRouter {    
    
    private CommandManager executer;
    
    public ServerRouter(User user) {
         executer = new CommandManager(user);
         System.out.println(user.getSocket().toString() + " fasfsa");
    }

    public boolean Route(String command) throws SQLException {
        if (command.replaceAll(" ", "").equals("/quit")) {
            executer.command_quit();
            return false;
        }
        else if (command.replaceAll(" ", "").equals("/singleUser")) {
            executer.command_singleUser();
            return true;
        }
        else if (command.startsWith("/group")) {
            executer.command_group(command);
            return true;
        }
        else if (command.replaceAll(" ", "").equals("/createGroup")) {
            executer.command_createGroup();
            return true;
        }
        else if (command.startsWith("/allUser")) {
            executer.command_allUser(command);
            return true;
        }
        else if (command.replaceAll(" ", "").equals("/assignName")) {
            executer.command_assingName();
            return true;
        }
        else {
            executer.command_unkown();
            return true;
        }
    }
}
