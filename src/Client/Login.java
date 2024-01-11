package src.Client;


import src.Client.GUIcode.components.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;


public class Login extends RoundedPanel implements ActionListener {
    private final int PADDING = 10;
    private final int MARGIN = 10;
    private final int ARC = 20;
    private static final int LINE_SIZE = 30;
    public  RoundedPanel nicknameInput;
    public RoundedTextField nicknameField;

//    private RoundedPanel passwordInput;
    private RoundedButton sendButton;
    public RoundedPanel finalLoginComponent;

    public Login(CardLayout crd, RoundedPanel leftPanel) {
        createLoginInput();
//        createPasswordInput();
        createSendButton();
        createLoginCard();
        setOpaque(false);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crd.next(leftPanel);
            }
        });
    }
    private void createLoginInput(){
        nicknameInput = new RoundedPanel();
        nicknameInput.setPreferredSize(new Dimension(200, 50));
        nicknameInput.setBackground(new Color(166,217,116));
        nicknameInput.setRoundCorners(50);

        nicknameField = new RoundedTextField();
        nicknameField.setRoundBottomLeft(45);
        nicknameField.setRoundBottomRight(45);
        nicknameField.setRoundTopLeft(45);
        nicknameField.setRoundTopRight(45);
        nicknameField.setPreferredSize(new Dimension(190,40));
        nicknameField.setHorizontalAlignment(JTextField.CENTER);
        nicknameField.setBorder(BorderFactory.createEmptyBorder());
        nicknameField.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        nicknameInput.add(nicknameField);
    }

//    private void createPasswordInput(){
//        passwordInput = new RoundedPanel();
//        passwordInput.setPreferredSize(new Dimension(200, 50));
//        passwordInput.setBackground(new Color(166,217,116));
//        passwordInput.setRoundCorners(50);
//
//
//        RoundedTextField textField = new RoundedTextField();
//        textField.setRoundBottomLeft(45);
//        textField.setRoundBottomRight(45);
//        textField.setRoundTopLeft(45);
//        textField.setRoundTopRight(45);
//
//        textField.setPreferredSize(new Dimension(190,40));
//        textField.setHorizontalAlignment(JTextField.CENTER);
//        textField.setBorder(BorderFactory.createEmptyBorder());
//        textField.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
//        passwordInput.add(textField);
//    }

    private void createSendButton(){
        sendButton = new RoundedButton();
        sendButton.setRoundBottomLeft(20);
        sendButton.setRoundTopLeft(20);
        sendButton.setRoundTopRight(20);
        sendButton.setRoundBottomRight(20);
        sendButton.setLabel("Sign in");
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        RoundRectangle2D roundRect = new RoundRectangle2D.Double(
                0, 0, getWidth(), getHeight(), ARC, ARC);
        g2.setColor(getBackground());
        g2.fill(roundRect);
        g2.dispose();
    }

    private void createLoginCard() {

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        this.setLayout(layout);

        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.setBackground(Color.WHITE);
        this.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
        this.setRoundCorners(20);

        c.gridx = 0;
        c.gridy = 0;
        this.add(nicknameInput, c);

//        c.gridx = 0;
//        c.gridy = 1;
//        this.add(passwordInput, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        this.add(sendButton, c);


        this.finalLoginComponent= this;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}