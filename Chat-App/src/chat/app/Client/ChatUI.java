package chat.app.Client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.PrintWriter;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatUI {
    
    private JFrame frame;
    private JTextField textField;
    private JTextArea messageArea;
    
    private PrintWriter out;
    
    public ChatUI(PrintWriter writer){
        frame = new JFrame("JavaChats");
        textField = new JTextField(50);
        messageArea = new JTextArea(16, 50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        this.out = writer;
        
        // Configurate Chat UI
        ConfigureUI();
        
        ConfigureInitialText();
    }

    public void ConfigureUI(){
        textField.setEditable(true);
        messageArea.setEditable(false);
        frame.getContentPane().add(textField, BorderLayout.SOUTH);
        frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
        frame.pack();
        
        // Send on enter then clear to prepare for next message
        textField.addActionListener((ActionEvent e) -> {
            out.println(textField.getText());
            textField.setText("");

        });
    }
    
    public void ConfigureInitialText (){
        messageArea.append("Server Command List\n"
                                    + "/createGroup -----> Create a new group\n"
                                    + "/singleUser -------> Send message to a user\n"
                                    + "/group --------------> Send message to a group\n"
                                    + "/allUser ------------> Send message to all user\n"
                                    + "/quit -----------------> Close the connection to the server\n"
                                    + "***************************************************************\n\n");
    }
    
    public void RemoveUI(){
        frame.setVisible(false);
        frame.dispose();
    }
    
    public void AddMessageToUI(String message){
        messageArea.append(message + "\n");
    }
}
