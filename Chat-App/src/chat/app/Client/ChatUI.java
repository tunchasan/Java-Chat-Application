package chat.app.Client;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatUI {
    
    private JFrame frame;
    private JTextField textField;
    private JTextArea messageArea;
    private String userName = "";
    
    public ChatUI(String name){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame = new JFrame("Chatter");
        textField = new JTextField(50);
        messageArea = new JTextArea(16, 50);
        userName = name;
        // Configurate Chat UI
        ConfigureUI();
    }
    
    public void ConfigureUI(){
        textField.setEditable(false);
        messageArea.setEditable(false);
        frame.getContentPane().add(textField, BorderLayout.SOUTH);
        frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
        frame.pack();
        //Set frame title
        this.frame.setTitle("Chatter - " + userName);
    }
    
    public void RemoveUI(){
        frame.setVisible(false);
        frame.dispose();
    }
    
    public void AddMessageToUI(String message){
        messageArea.append(message + "\n");
    }
}
