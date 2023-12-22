package components;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

public class Awatar extends ImageIcon {

    private ImageIcon scaledAwatar;
    private Image awatarImg;
    private File img;
    private String pathToAwatar;
    private int width;
    private int height;


    public Awatar(String path ,int width, int height) throws IOException {
        this.pathToAwatar = path;
        this.width = width;
        this.height = height;

        awatarImg = new ImageIcon(path).getImage();
        awatarImg = awatarImg.getScaledInstance(width,height,Image.SCALE_DEFAULT);
        scaledAwatar = this;
        this.setImage(awatarImg);
    }

}
