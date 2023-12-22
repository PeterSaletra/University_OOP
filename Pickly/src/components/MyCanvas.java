package components;

import java.awt.*;
import javax.swing.JFrame;

public class MyCanvas extends Canvas{

    public void paint(Graphics g) {

        Toolkit t=Toolkit.getDefaultToolkit();
        Image i=t.getImage("src/img/linkedinIcon.png");
        g.drawImage(i, 120,100,this);
    }
}