package src.Client;

import src.Client.GUIcode.components.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import java.awt.geom.RoundRectangle2D;
import java.io.*;

public class Login extends RoundedPanel{
    private final int PADDING = 10;
    private final int MARGIN = 10;
    private final int ARC = 20;
    private static final int LINE_SIZE = 30;
    private  RoundedPanel nicknameInput;
    private Placeholder nicknameField;
    private RoundedPanel passwordInput;
    private RoundedButton sendButton;
    private RoundedButton restoreLastSessionButton;

    public Login(CardLayout crd, RoundedPanel leftPanel, App app) {
        createLoginInput();
        createSendButton();
        createRestoreSessionButton();
        createLoginCard();
        setOpaque(false);
        sendButton.addActionListener(e ->{
            if (nicknameField.getText().isEmpty()){
                App.createPopUpWindow("Provide nickname");
            }else{
                crd.next(leftPanel);
            }
        });

        restoreLastSessionButton.addActionListener(e -> {
            Client deserializedClient;
            try {
                deserializedClient = Client.deserialize("lastSession.txt");
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

            nicknameField.setText(deserializedClient.getNickname());
            app.getInputIP().setText(deserializedClient.getHost() + ":" + deserializedClient.getPort());
            crd.next(leftPanel);
            app.getBtnIP().doClick();
        });

    }
    private void createLoginInput(){
        nicknameInput = new RoundedPanel();
        nicknameInput.setPreferredSize(new Dimension(200, 50));
        nicknameInput.setBackground(new Color(166,217,116));
        nicknameInput.setRoundCorners(50);
        nicknameField = new Placeholder("Nickname");
        nicknameField.setRoundBottomLeft(45);
        nicknameField.setRoundBottomRight(45);
        nicknameField.setRoundTopLeft(45);
        nicknameField.setRoundTopRight(45);
        nicknameField.setPreferredSize(new Dimension(190,40));
        nicknameField.setHorizontalAlignment(JTextField.CENTER);
        nicknameField.setBorder(BorderFactory.createEmptyBorder());
        nicknameField.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        nicknameField.getCaret().setBlinkRate(0);
        nicknameField.setCaretColor(nicknameField.getBackground());
        nicknameInput.add(nicknameField);
    }
    private void createSendButton(){
        sendButton = new RoundedButton();
        sendButton.setRoundBottomLeft(20);
        sendButton.setRoundTopLeft(20);
        sendButton.setRoundTopRight(20);
        sendButton.setRoundBottomRight(20);
        sendButton.setText("Sign in");
    }
    private void createRestoreSessionButton(){
        restoreLastSessionButton = new RoundedButton();
        restoreLastSessionButton.setRoundBottomLeft(20);
        restoreLastSessionButton.setRoundTopLeft(20);
        restoreLastSessionButton.setRoundTopRight(20);
        restoreLastSessionButton.setRoundBottomRight(20);
        restoreLastSessionButton.setText("Restore last session");
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

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        this.add(sendButton, c);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        this.add(restoreLastSessionButton, c);

        RoundedPanel finalLoginComponent = this;
    }

    public Placeholder getNicknameField() {
        return nicknameField;
    }
}