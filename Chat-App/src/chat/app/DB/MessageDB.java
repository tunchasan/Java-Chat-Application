package chat.app.DB;

public class MessageDB{
        private String sender;
        private String message;
        private String time;
        private String receiver;
        
        public MessageDB(String Sender, String Message, String Time, String Receiver){
            this.sender = Sender;
            this.message = Message;
            this.time = Time;
            this.receiver = Receiver;
        }
    }
    
