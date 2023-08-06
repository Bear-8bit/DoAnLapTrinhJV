package Windows;

import Model.Image.UIJPanelBG;

import javax.swing.*;
import java.awt.*;

/*
    Class tạo Frame SplashScreen cho Game
 */
public class FrameSplashScreen extends JFrame {
    private static final long serialVersionUID = 1L;
    public FrameSplashScreen(){
        super("Tàu chiến");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setUndecorated(true);
        this.setSize(600, 350);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Resources/icon.png")));
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
        JPanel container = new JPanel(null);
        UIJPanelBG splashPanel = new UIJPanelBG(
                Toolkit.getDefaultToolkit().createImage(getClass().getResource("/Resources/splashimage.png"))
        );
        ImageIcon loadingImg = new ImageIcon(getClass().getResource("/Resources/loading.gif"));
        JLabel loadingLabel = new JLabel(loadingImg);
        container.add(splashPanel);
        splashPanel.setBounds(0, 0, 800 , 350);
        container.add(loadingLabel, 0);
        loadingLabel.setBounds(490, 250, 100, 100);
        this.add(container);
        this.setVisible(true);
    }
}
