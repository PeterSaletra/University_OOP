package src.Client.GUIcode.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;

public class Message extends RoundedPanel {
    private final int PADDING = 10;
    private final int MARGIN = 10;
    private final int ARC = 20;
    private static final int LINE_SIZE = 30;
    private JLabel messageLabel;
    private RoundedPanel messageWrapper;
    public RoundedPanel finalMessageComponent;

    public Message(String text, int option) {
        createLabel(text);
        createWrapper();
        createMessage();
        messageWrapper.add(messageLabel);
        pickMessageSide(option);
        setOpaque(false);
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

    public static String divideMessage(String message){
        int fullLines = message.length() / LINE_SIZE;
        String dividedMessage = "";
        for (int i = 0; i < fullLines * LINE_SIZE; i+=LINE_SIZE)
            dividedMessage = dividedMessage.concat(message.substring(i, i+LINE_SIZE) + "<br>");
        for (int i = fullLines * LINE_SIZE; i < message.length() ; i++)
            dividedMessage = dividedMessage.concat(String.valueOf(message.charAt(i)));
        return dividedMessage;
    }
    private void createLabel(String text) {
        text = divideMessage(text);
        JLabel messageLabel = new JLabel();
        messageLabel.setText("<html>" + text + "</html>");
        messageLabel.setBorder(new EmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));
        this.messageLabel = messageLabel;
    }
    private void createWrapper() {
        RoundedPanel messageWrapper = new RoundedPanel();
            messageWrapper.setBackground(new Color(204, 255, 153));
            messageWrapper.setLayout(new BorderLayout());
            messageWrapper.setRoundCorners(ARC);

            Awatar awatarIcon = null;
            try {
                awatarIcon = new Awatar("src/Client/img/EmptyAwatar.png", 25, 25);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        messageWrapper.add(new Timestamp(), BorderLayout.WEST);

        messageWrapper.add(messageLabel, BorderLayout.CENTER);
        this.messageWrapper = messageWrapper;
    }
    private void pickMessageSide(int option) {
        switch (option) {
            case 1:
                finalMessageComponent.add(messageWrapper, BorderLayout.EAST);
                break;
            case 2:
                finalMessageComponent.add(messageWrapper, BorderLayout.WEST);
                break;
        }
    }
    private void createMessage() {
        this.setLayout(new BorderLayout());
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, messageLabel.getPreferredSize().height + 20));
        this.setBackground(Color.WHITE);
        this.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
        this.finalMessageComponent = this;
    }
}
