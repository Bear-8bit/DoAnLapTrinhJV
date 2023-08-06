package Windows.WindowsPlayervsAI;

import Model.Image.UIJPanelBG;

import javax.swing.*;
import java.awt.*;

/*
    Class kế thừa từ UIJPanelBG, class được sử dụng để gắn hình cho tàu
    khi sử dụng Class FrameBattle để hiển thị những tàu nào mà hai bên còn
 */
public class UIStatPanel extends UIJPanelBG {
    private static final long serialVersionUID = 1L;
    JLabel[] ships = new JLabel[7];

    public UIStatPanel() {
        super(Toolkit.getDefaultToolkit()
                .createImage(FrameMangeShip.class.getResource("/Resources/GUI/battlePaper.png")));

        for (int i = 0; i < ships.length; i++) {
            ships[i] = new JLabel();
            this.add(ships[i]);
        }

        ships[0].setIcon(new ImageIcon(getClass().getResource("/Resources/Game/Ship/ship5.png")));
        ships[1].setIcon(new ImageIcon(getClass().getResource("/Resources/Game/Ship/ship4.png")));
        ships[2].setIcon(new ImageIcon(getClass().getResource("/Resources/Game/Ship/ship3.png")));
        ships[3].setIcon(new ImageIcon(getClass().getResource("/Resources/Game/Ship/ship3.png")));
        ships[4].setIcon(new ImageIcon(getClass().getResource("/Resources/Game/Ship/ship2.png")));
        ships[5].setIcon(new ImageIcon(getClass().getResource("/Resources/Game/Ship/ship2.png")));
        ships[6].setIcon(new ImageIcon(getClass().getResource("/Resources/Game/Ship/ship2.png")));

        ships[0].setBounds(50, 25, 170, 35);
        ships[1].setBounds(300, 25, 135, 35);
        ships[2].setBounds(30, 60, 103, 35);
        ships[3].setBounds(130, 60, 103, 35);
        ships[4].setBounds(230, 60, 70, 35);
        ships[5].setBounds(310, 60, 70, 35);
        ships[6].setBounds(390, 60, 70, 35);
    }
}
