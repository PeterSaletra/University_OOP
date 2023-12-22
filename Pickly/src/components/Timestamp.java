package components;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;


public class Timestamp extends RoundedPanel{
    private JLabel timestamp;
    public JPanel finalTimestampComponent ;
    private static final int FONT_SIZE = 10;

    public Timestamp(){
        createTimestamp();
        finalTimestampComponent= new RoundedPanel();
        finalTimestampComponent = this;
        this.add(timestamp, BorderLayout.WEST);
    }

    private void createTimestamp(){
        timestamp = new javax.swing.JLabel(LocalDateTime.now().toLocalTime().toString().substring(0, 5));
        timestamp.setFont(new Font("Serif", Font.PLAIN, FONT_SIZE));
        timestamp.setBackground(Color.WHITE);


        Awatar awatarIcon = null;
        try {
            awatarIcon = new Awatar("Pickly/src/img/EmptyAwatar.png", 25, 25);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JLabel awatarLabel = new JLabel(awatarIcon);
        awatarLabel.setOpaque(true);
        timestamp.setIcon(awatarIcon);
        timestamp.setIconTextGap(10);
        timestamp.setBorder(new EmptyBorder(1,0,0,0));
    }


}
