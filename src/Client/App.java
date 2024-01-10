package src.Client;

import src.Client.GUIcode.components.*;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class App extends JFrame implements ActionListener {
    private JPanel App;
    private RoundedPanel mainSection;
    private JLabel logo;
    private RoundedPanel rightBar;
    private RoundedPanel bottomBarMessagesContainer;
    private RoundedPanel rightBarBottomSection;
    private JPanel rightBarActiveUsersSection;
    private RoundedPanel chatSection;
    private JScrollPane scrollPane;
    private RoundedPanel messagesContainer;
    private RoundedPanel topBarMessagesContainer;
    private JButton btnIP;
    private JTextField inputIP;
    private static JButton sendBtn;
    private static JTextField messageField;
    private RoundedPanel rightBarUpperBar;


    boolean sessionExists = false;
    boolean toggleUserSide = true;

    private User[] users = new User[2];

    public App() throws IOException {


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
        setSize(new Dimension(905, 720));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        App = new JPanel();
        App.setPreferredSize(new Dimension(905, 680));

        btnIP = new JButton();
        btnIP = new JButton("Connect");
        btnIP.addActionListener(e -> {
        });

        inputIP = new JTextField();
        inputIP = new JTextField(10);

        sendBtn = new JButton();
        sendBtn.setText("Send");
        sendBtn.addActionListener(this::sendBtnActionPerformed);

        messageField = new JTextField();
        messageField = new JTextField(20);
        messageField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                messageFieldActionPerformed(evt);
            }
        });

        mainSection = new RoundedPanel();
        mainSection.setBackground(new Color(166, 217, 116));
        mainSection.setPreferredSize(new Dimension(905, 603));
        mainSection.setLayout(new BoxLayout(mainSection, BoxLayout.X_AXIS));
        mainSection.setBorder(new EmptyBorder(20,20,20,20));


        topBarMessagesContainer = new RoundedPanel();
        topBarMessagesContainer.setRoundTopLeft(20);
        topBarMessagesContainer.setRoundTopRight(20);
        topBarMessagesContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topBarMessagesContainer.add(inputIP);
        topBarMessagesContainer.add(btnIP);
        topBarMessagesContainer.setBackground(new Color(166, 217, 116));

        messagesContainer = new RoundedPanel();
        messagesContainer.setBackground(new Color(255, 255, 255));
        messagesContainer.setRoundTopLeft(20);
        messagesContainer.setRoundTopRight(20);
        messagesContainer.setRoundBottomLeft(20);
        messagesContainer.setRoundBottomRight(20);
        messagesContainer.setLayout(new BoxLayout(messagesContainer, BoxLayout.Y_AXIS));

        bottomBarMessagesContainer = new RoundedPanel();
        bottomBarMessagesContainer.setRoundBottomLeft(20);
        bottomBarMessagesContainer.setRoundBottomRight(20);
        bottomBarMessagesContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        bottomBarMessagesContainer.add(sendBtn);
        bottomBarMessagesContainer.add(messageField);
        bottomBarMessagesContainer.setPreferredSize(new Dimension(500, 50));


        chatSection = new RoundedPanel();
        chatSection.setBackground(new Color(255, 255, 255));
        chatSection.setRoundBottomLeft(20);
        chatSection.setRoundBottomRight(20);
        chatSection.setRoundTopLeft(20);
        chatSection.setRoundTopRight(20);
        chatSection.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(20,20,20,20),
                new RoundBorder(Color.black, 1, 20)                )
        );
        chatSection.setLayout(new BorderLayout());


        chatSection.add(topBarMessagesContainer, BorderLayout.NORTH);
        scrollPane = new JScrollPane(messagesContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        chatSection.add(scrollPane, BorderLayout.CENTER);
        chatSection.add(bottomBarMessagesContainer, BorderLayout.SOUTH);


        ImageIcon logoIcon = new ImageIcon("src/Client/img/pickleLogo.png");
        logo = new JLabel(logoIcon);
        logo.setOpaque(true);

        rightBar = new RoundedPanel();
        rightBar.setLayout(new BorderLayout());
        rightBar.setBackground(new Color(255, 255, 255));
        rightBar.setRoundBottomLeft(20);
        rightBar.setRoundBottomRight(20);
        rightBar.setRoundTopLeft(20);
        rightBar.setRoundTopRight(20);


        rightBarUpperBar = new RoundedPanel();
        rightBarUpperBar.setBackground(Color.white);
        rightBarUpperBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        rightBarUpperBar.add(logo, BorderLayout.CENTER);
        rightBarUpperBar.setRoundTopLeft(20);
        rightBarUpperBar.setRoundTopRight(20);
        rightBarUpperBar.setRoundBottomLeft(20);
        rightBarUpperBar.setRoundBottomRight(20);


        rightBarBottomSection = new RoundedPanel();
        rightBarBottomSection.setRoundBottomLeft(20);
        rightBarBottomSection.setRoundBottomRight(20);



        JTextArea activeUsersTextArea = new JTextArea(10, 20);
        activeUsersTextArea.setEditable(false);

        Awatar awatarIcon = new Awatar("src/Client/img/EmptyAwatar.png", 50, 50);
        JLabel awatarLabel = new JLabel(awatarIcon);
        awatarLabel.setOpaque(true);


        rightBarActiveUsersSection = new JPanel();
        rightBarActiveUsersSection.setBackground(new Color(255, 255, 255));
        rightBarActiveUsersSection.setLayout(new BoxLayout(rightBarActiveUsersSection, BoxLayout.Y_AXIS));
        rightBarActiveUsersSection.add(new UserTile(awatarIcon, users[0]));
        rightBarActiveUsersSection.add(new UserTile(awatarIcon, users[1]));
        rightBarActiveUsersSection.add(Box.createVerticalGlue());

        rightBar.add(rightBarUpperBar, BorderLayout.NORTH);
        rightBar.add(rightBarActiveUsersSection, BorderLayout.CENTER);
        rightBar.add(rightBarBottomSection, BorderLayout.SOUTH);

        RoundedPanel leftPanel = new RoundedPanel();
        leftPanel.setRoundTopLeft(20);
        leftPanel.setRoundTopRight(20);
        leftPanel.setRoundBottomLeft(20);
        leftPanel.setRoundBottomRight(20);
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(chatSection, BorderLayout.CENTER);

        RoundedPanel rightPanel = new RoundedPanel();
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


    private void sendMessage(String text, int option) {
        SwingUtilities.invokeLater(() -> {
            Message messageComponent = new Message(text, option);
            messagesContainer.add(messageComponent);
            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
            verticalScrollBar.setValue(verticalScrollBar.getMaximum());
            messagesContainer.revalidate();
            messagesContainer.repaint();
        });
    }

    private void showActiveUsers(){

    }



    private void messageFieldActionPerformed(ActionEvent evt) {

    }

    private void sendBtnActionPerformed(ActionEvent evt) {

    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String args[]) throws IOException {



        MyFrame myFrame = new MyFrame();
        myFrame.setVisible(true);
    }
}




