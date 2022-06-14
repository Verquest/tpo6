import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

import static java.awt.GridBagConstraints.BOTH;

public class ChatWindow extends JFrame {
    private int W = 400, H = 800;
    private JScrollPane scrollPane;
    private JTextArea chatTextArea;
    private JTextArea inputTextArea;
    private JButton sendButton;
    private GridBagLayout layout;
    private GridBagConstraints constraints;
    public boolean hasUserBeenSet = false;
    public String user = "";

    public LinkedList<String> messagesToBeProcessed;
    public ChatWindow(){
        messagesToBeProcessed = new LinkedList<>();

        this.setPreferredSize(new Dimension(W, H));
        sendButton = new JButton("send message");
        sendButton.addActionListener(action -> getMessage());
        chatTextArea = new JTextArea();
        chatTextArea.setEditable(false);
        chatTextArea.setLineWrap(true);
        chatTextArea.setWrapStyleWord(true);
        scrollPane = new JScrollPane(chatTextArea);
        chatTextArea.setText("Enter your username\n");
        inputTextArea = new JTextArea();
        inputTextArea.setLineWrap(true);

        layout = new GridBagLayout();
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = BOTH;
        constraints.weightx = 1;
        constraints.weighty = 0.8;
        constraints.gridwidth = 4;
        this.setLayout(layout);
        this.add(scrollPane, constraints);
        constraints.weighty = 0.2;
        constraints.weightx = 1;
        constraints.gridwidth = 3;
        constraints.gridy = 1;
        this.add(inputTextArea, constraints);
        constraints.gridx = 3;
        constraints.gridwidth = 1;
        this.add(sendButton, constraints);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

    }
    public void appendMessage(String message){
        chatTextArea.append("[" + user + "]" + message + '\n');
    }
    public String getMessage(){
        String text = inputTextArea.getText();
        if(!hasUserBeenSet) {
            user = text;
            hasUserBeenSet = true;
            inputTextArea.setText("");
        }else {
            inputTextArea.setText("");
            appendMessage(text);
            messagesToBeProcessed.add(text);
        }
        return text;
    }
}
