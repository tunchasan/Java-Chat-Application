package chat.app.Views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
    private Font font;
    private PrintWriter out;
    private Color color;
    
    public ChatUI(PrintWriter writer){
        frame = new JFrame("JavaChats");
        textField = new JTextField(50);
        messageArea = new JTextArea(16, 50);
        font = new Font("Merriweather", Font.BOLD,12);
        color = new Color (102,204,0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        this.out = writer;
        
        // Configurate Chat UI
        ConfigureUI();
    }

    public void ConfigureUI(){
        textField.setEditable(true);
        textField.setFont(font);
        messageArea.setEditable(false);
        messageArea.setFont(font);
        messageArea.setBackground(color);
        frame.getContentPane().add(textField, BorderLayout.SOUTH);
        frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
        frame.pack();
        
        // Send on enter then clear to prepare for next message
        textField.addActionListener((ActionEvent e) -> {
            out.println(textField.getText());
            textField.setText("");

        });
    }
    
    public void RemoveUI(){
        frame.setVisible(false);
        frame.dispose();
    }
    
    public void AddMessageToUI(String message){
        messageArea.append(message + "\n");
    }
}
