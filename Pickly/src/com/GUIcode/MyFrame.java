package com.GUIcode;

import components.RoundedPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.IOException;

class User{
    private String nickname;
    private String status;
    public User(String nickaname, String status){
        this.nickname= nickaname;
        this.status = status;
    }
}
public class MyFrame extends javax.swing.JFrame implements ActionListener {
    private javax.swing.JPanel App;
    private components.RoundedPanel mainSection;
    private javax.swing.JLabel logo;
    private components.RoundedPanel rightBar;
    private components.RoundedPanel bottomBarMessagesContainer;
    private components.RoundedPanel rightBarBottomSection;
    private javax.swing.JPanel rightBarActiveUsersSection;
    private components.RoundedPanel chatSection;
    private JScrollPane scrollPane;
    private components.RoundedPanel messagesContainer;
    private components.RoundedPanel topBarMessagesContainer;
    private javax.swing.JButton btnIP;
    private javax.swing.JTextField inputIP;
    private static javax.swing.JButton sendBtn;
    private static javax.swing.JTextField messageField;
    private components.RoundedPanel rightBarUpperBar;


    boolean sessionExists = false;
    boolean toggleUserSide = true;

    private User[] users = new User[2];
    private final int LINE_SIZE = 30;

    public MyFrame() throws IOException {


        this.users[0] = new User("krysztalxd294", "online");
        this.users[1]= new User("jdabrowski", "AFK");

        initComponents();
        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        Thread.sleep(1);
                        return null;
                    }
                    @Override
                    protected void done() {
                        if (toggleUserSide){
                            sendMessage(messageField.getText(),1);
                            messageField.setText("");
                            toggleUserSide = false;
                        }else{
                            sendMessage(messageField.getText(),2);
                            messageField.setText("");
                            toggleUserSide = true;
                        }
                    }
                };
                worker.execute();
            }
        });

    }
    @SuppressWarnings("unchecked")

    private void initComponents() throws IOException {
        setTitle("Pickly");
        setResizable(false);
        setSize(new java.awt.Dimension(905, 720));
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        App = new javax.swing.JPanel();
        App.setPreferredSize(new java.awt.Dimension(905, 680));

        btnIP = new javax.swing.JButton();
        btnIP = new JButton("Connect");
        btnIP.addActionListener(e -> {
        });

        inputIP = new javax.swing.JTextField();
        inputIP = new JTextField(10);

        sendBtn = new javax.swing.JButton();
        sendBtn.setText("Send");
        sendBtn.addActionListener(this::sendBtnActionPerformed);

        messageField = new javax.swing.JTextField();
        messageField = new JTextField(20);
        messageField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messageFieldActionPerformed(evt);
            }
        });

        mainSection = new components.RoundedPanel();
        mainSection.setBackground(new java.awt.Color(166, 217, 116));
        mainSection.setPreferredSize(new java.awt.Dimension(905, 603));
        mainSection.setLayout(new BoxLayout(mainSection, BoxLayout.X_AXIS));
        mainSection.setBorder(new EmptyBorder(20,20,20,20));


        topBarMessagesContainer = new components.RoundedPanel();
        topBarMessagesContainer.setRoundTopLeft(20);
        topBarMessagesContainer.setRoundTopRight(20);
        topBarMessagesContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topBarMessagesContainer.add(inputIP);
        topBarMessagesContainer.add(btnIP);
        topBarMessagesContainer.setBackground(new java.awt.Color(166, 217, 116));

        messagesContainer = new components.RoundedPanel();
        messagesContainer.setBackground(new java.awt.Color(255, 255, 255));
        messagesContainer.setRoundTopLeft(20);
        messagesContainer.setRoundTopRight(20);
        messagesContainer.setRoundBottomLeft(20);
        messagesContainer.setRoundBottomRight(20);
        messagesContainer.setLayout(new BoxLayout(messagesContainer, BoxLayout.Y_AXIS));

        bottomBarMessagesContainer = new components.RoundedPanel();
        bottomBarMessagesContainer.setRoundBottomLeft(20);
        bottomBarMessagesContainer.setRoundBottomRight(20);
        bottomBarMessagesContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        bottomBarMessagesContainer.add(sendBtn);
        bottomBarMessagesContainer.add(messageField);
        bottomBarMessagesContainer.setPreferredSize(new Dimension(500, 50));


        chatSection = new components.RoundedPanel();
        chatSection.setBackground(new java.awt.Color(255, 255, 255));
        chatSection.setRoundBottomLeft(20);
        chatSection.setRoundBottomRight(20);
        chatSection.setRoundTopLeft(20);
        chatSection.setRoundTopRight(20);
        chatSection.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(20,20,20,20),
                new components.RoundBorder(Color.black, 1, 20)                )
        );
        chatSection.setLayout(new BorderLayout());


        chatSection.add(topBarMessagesContainer, BorderLayout.NORTH);
        scrollPane = new JScrollPane(messagesContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        chatSection.add(scrollPane, BorderLayout.CENTER);
        chatSection.add(bottomBarMessagesContainer, BorderLayout.SOUTH);


        ImageIcon logoIcon = new ImageIcon("Pickly/src/img/pickleLogo.png");
        logo = new JLabel(logoIcon);
        logo.setOpaque(true);

        rightBar = new components.RoundedPanel();
        rightBar.setLayout(new BorderLayout());
        rightBar.setBackground(new java.awt.Color(255, 255, 255));
        rightBar.setRoundBottomLeft(20);
        rightBar.setRoundBottomRight(20);
        rightBar.setRoundTopLeft(20);
        rightBar.setRoundTopRight(20);


        rightBarUpperBar = new components.RoundedPanel();
        rightBarUpperBar.setBackground(Color.white);
        rightBarUpperBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        rightBarUpperBar.add(logo, BorderLayout.CENTER);
        rightBarUpperBar.setRoundTopLeft(20);
        rightBarUpperBar.setRoundTopRight(20);
        rightBarUpperBar.setRoundBottomLeft(20);
        rightBarUpperBar.setRoundBottomRight(20);


        rightBarBottomSection = new components.RoundedPanel();
        rightBarBottomSection.setRoundBottomLeft(20);
        rightBarBottomSection.setRoundBottomRight(20);



        JTextArea activeUsersTextArea = new JTextArea(10, 20);
        activeUsersTextArea.setEditable(false);

        rightBarActiveUsersSection = new JPanel();
        rightBarActiveUsersSection.setBackground(new java.awt.Color(255, 255, 255));
        rightBarActiveUsersSection.setLayout(new javax.swing.BoxLayout(rightBarActiveUsersSection, javax.swing.BoxLayout.Y_AXIS));
        rightBarActiveUsersSection.add(Box.createVerticalGlue());

        rightBar.add(rightBarUpperBar, BorderLayout.NORTH);
        rightBar.add(rightBarActiveUsersSection, BorderLayout.CENTER);
        rightBar.add(rightBarBottomSection, BorderLayout.SOUTH);

        RoundedPanel leftPanel = new components.RoundedPanel();
        leftPanel.setRoundTopLeft(20);
        leftPanel.setRoundTopRight(20);
        leftPanel.setRoundBottomLeft(20);
        leftPanel.setRoundBottomRight(20);
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(chatSection, BorderLayout.CENTER);

        RoundedPanel rightPanel = new components.RoundedPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(rightBar, BorderLayout.CENTER);
        rightPanel.setRoundTopLeft(20);
        rightPanel.setRoundTopRight(20);
        rightPanel.setRoundBottomLeft(20);
        rightPanel.setRoundBottomRight(20);

        mainSection.add(leftPanel);
        mainSection.add(Box.createHorizontalStrut(40));
        mainSection.add(rightPanel);

        App.setLayout(new BorderLayout());
        App.add(mainSection, BorderLayout.CENTER);
        getContentPane().add(App);
        pack();
    }

    private String divideMessage(String message){
        int fullLines = message.length() / LINE_SIZE;
        String dividedMessage = "";
        for (int i = 0; i < fullLines * LINE_SIZE; i+=LINE_SIZE)
            dividedMessage = dividedMessage.concat(message.substring(i, i+LINE_SIZE) + "<br>");
        for (int i = fullLines * LINE_SIZE; i < message.length() ; i++)
            dividedMessage = dividedMessage.concat(String.valueOf(message.charAt(i)));
        return dividedMessage;
    }

    private JPanel createMessage(String text, int option){
        JLabel messageLabel = new JLabel();
        messageLabel.setText("<html>" + text + "</html>");
        messageLabel.setBorder(new EmptyBorder(10,10,10,10));

        RoundedPanel messageWrapper = new components.RoundedPanel();
        messageWrapper.setRoundTopLeft(20);
        messageWrapper.setRoundTopRight(20);
        messageWrapper.setRoundBottomLeft(20);
        messageWrapper.setRoundBottomRight(20);
        messageWrapper.setBackground(new Color(204, 255, 153));
        messageWrapper.setLayout(new BorderLayout());
        messageWrapper.add(messageLabel, BorderLayout.CENTER);

        JPanel panelLabel = new JPanel();
        panelLabel.setLayout(new BorderLayout());
        panelLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, messageLabel.getPreferredSize().height + 10));
        panelLabel.setBackground(Color.WHITE);
        panelLabel.setBorder(new EmptyBorder(10,10,10,10));

        switch (option) {
            case 1:
                panelLabel.add(messageWrapper, BorderLayout.EAST);
                break;
            case 2:
                panelLabel.add(messageWrapper, BorderLayout.WEST);
                break;
        }
        return panelLabel;
    }

    private void sendMessage(String message, int option) {
        if (message.length() > LINE_SIZE)
            message = divideMessage(message);
        String finalMessage1 = message;
        javax.swing.SwingUtilities.invokeLater(() -> {
            JPanel panelLabel = createMessage(finalMessage1, option);
            messagesContainer.add(panelLabel);
            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
            verticalScrollBar.setValue(verticalScrollBar.getMaximum());
            messagesContainer.revalidate();
            messagesContainer.repaint();

        });
    }

    private void showActiveUsers(){

    }



    private void messageFieldActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void sendBtnActionPerformed(java.awt.event.ActionEvent evt) {

    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String args[]) throws IOException {



        MyFrame myFrame = new MyFrame();
        myFrame.setVisible(true);
    }
}




