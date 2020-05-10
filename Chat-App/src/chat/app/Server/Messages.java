package chat.app.Server;

// The class handles all client-server request result messages
public class Messages {
    
    enum Results {
        Message_successfully_sended,
        Message_send_failure,
        User_not_exist,
    }
}
