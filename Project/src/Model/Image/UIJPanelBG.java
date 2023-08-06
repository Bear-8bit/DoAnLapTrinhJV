package Model.Image;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/*
 * Class này dùng để tài hình ảnh và gif
 */
public class UIJPanelBG extends JPanel{
    private static final long serialVersionUID = 1L;
    Image img;
    public UIJPanelBG(String img){
        this(UIJPanelBG.createImageIcon(img).getImage());
    }
    public UIJPanelBG(Image img){
        this.img = img;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }
    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
    public static ImageIcon createImageIcon(final String path) {
        InputStream is = Image.class.getResourceAsStream(path);
        int length;
        try {
            length = is.available();
            byte[] data = new byte[length];
            is.read(data);
            is.close();
            ImageIcon ii = new ImageIcon(data);
            return ii;
        } catch (IOException e) {
        }
        return null;
    }

}
