package src.Client.GUIcode.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UserTile extends RoundedPanel{
    private Awatar awatar;
    private User user;
    private RoundedPanel finalTile;
    public UserTile(Awatar awatar, User user){
        this.awatar = awatar;
        this.user = user;
        JLabel nicknameTile = new JLabel();
        JLabel awatarTile = new JLabel();
        awatarTile.setIcon(awatar);
        awatarTile.setBorder(new EmptyBorder(5,10,5,0));
        nicknameTile.setText(user.nickname);
        nicknameTile.setBorder(new EmptyBorder(0,0,0,10));
        finalTile = new RoundedPanel();
        finalTile = this;
        this.setLayout(new BorderLayout());
        this.add(awatarTile, BorderLayout.WEST);
        this.add(nicknameTile, BorderLayout.EAST);
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, awatarTile.getPreferredSize().height + 20));

    }
}
