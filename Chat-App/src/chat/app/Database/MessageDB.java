package chat.app.Database;

import chat.app.Server.Server;

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
        
        public String ToString() {
            return sender + " " + message + " " + time + " " + receiver;
        }
        
        public String getSender(){
            return sender;
        }
        
        public String getMessage(){
            return message;
        }
        
        public String getTime(){
            return time;
        }
        
        public String getReceiver(){
            return receiver;
        }
    }
    
