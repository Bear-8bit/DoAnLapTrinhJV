package Windows.WindowsPlayervsAI;

import Model.Image.UIJPanelBG;

import javax.swing.*;
import java.awt.*;
/*
    Class này dùng để quản lý các thông tin của Sàn chơi
 */
public class UIBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    static int X = 570;
    static int Y = 630;
    int numC = 10;
    int sizeC = 48;
    int horoff = 1;
    int veroff = 1;
    public JButton[][] buttons;
    JLabel[] CHor;
    JLabel[] CVer;
    UIJPanelBG sky;
    Cursor cursorHand = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    Cursor cursorDefault = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);

    public UIBoardPanel(String labels) {

        this.setSize(X, Y);
        this.setLayout(null);
        this.setOpaque(false);

        // Code này dùng để thêm các hình ảnh của sản đấu bao gồm
        // Background, sàn đấu, các tọa độ của sàn đấu
        // Còn những ô mà cho người chơi và máy có thể đánh lên sàn
        // Sẽ được quy định là các button và sử dụng hình grayButtonOpaque
        // và để có thể xếp các nút này sử dụng vòng lặp để mà cài đặt từng thuộc tính của button
        // lần lượt thực hiện vòng lặp trong cho tới khi hết hàng ngang đó, sau đó thì thực hiện vòng lặp ngoài
        // để chuyến xuống hàng dọc và tiếp tục vòng lặp cho tới khi hoàn thành sàn chơi
        sky = new UIJPanelBG(
                Toolkit.getDefaultToolkit().createImage(FrameMangeShip.class.getResource("/Resources/Game/Board/sky.jpg")));
        sky.setBounds(34, 45, 550, 550);
        buttons = new JButton[numC][numC];
        ImageIcon gray = new ImageIcon(getClass().getResource("/Resources/Game/Board/grayButtonOpaque.png"));
        for (int i = 0; i < numC; i++) {
            for (int j = 0; j < numC; j++) {
                buttons[i][j] = new JButton(gray);
                buttons[i][j].setSize(sizeC, sizeC);
                sky.add(buttons[i][j]);
                buttons[i][j].setCursor(cursorHand);
                buttons[i][j].setBorder(null);
                buttons[i][j].setOpaque(false);
                buttons[i][j].setBorderPainted(false);
                buttons[i][j].setContentAreaFilled(false);
                buttons[i][j].setBounds(horoff, veroff, sizeC, sizeC);
                if (labels.equals("player")) {
                    buttons[i][j].setCursor(cursorDefault);
                    buttons[i][j].setDisabledIcon(gray);
                    buttons[i][j].setEnabled(false);
                } else {
                    buttons[i][j].setCursor(cursorHand);
                }
                horoff += sizeC + 2;
            }
            veroff += sizeC + 2;
            horoff = 1;
        }

        horoff = 40;
        veroff = 0;
        JPanel grid = new JPanel(null);
        grid.setOpaque(false);
        grid.add(sky);
        CHor = new JLabel[10];
        CVer = new JLabel[10];
        // Dùng để load các tọa độ của sàn chơi
        for (int i = 0; i < 10; i++) {
            CHor[i] = new JLabel();
            CVer[i] = new JLabel();
            grid.add(CHor[i]);
            grid.add(CVer[i]);
            CVer[i].setIcon(new ImageIcon(getClass().getResource((("/Resources/Game/Board/coord/" + (i + 1) + ".png")))));
            CVer[i].setBounds(veroff, horoff, sizeC, sizeC);
            CHor[i].setIcon(new ImageIcon(getClass().getResource((("/Resources/Game/Board/coord/" + (i + 11) + ".png")))));
            CHor[i].setBounds(horoff, veroff, sizeC, sizeC);
            horoff += 50;
        }

        this.add(grid);
        grid.setBounds(0, 45, 550, 660);

    }

    public void drawShip(int[] data) {
        int x = data[0];
        int y = data[1];
        int size = data[2];
        int dir = data[3];

        // Gán hình cho tàu
        ImageIcon shipHeadLeft = new ImageIcon(
                getClass().getResource("/Resources/Game/Ship/shipHeadLeft.png"));
        ImageIcon shipHeadTop = new ImageIcon(
                getClass().getResource("/Resources/Game/Ship/shipHeadTop.png"));
        ImageIcon shipBodyLeft = new ImageIcon(
                getClass().getResource("/Resources/Game/Ship/shipBodyLeft.png"));
        ImageIcon shipBodyTop = new ImageIcon(
                getClass().getResource("/Resources/Game/Ship/shipBodyTop.png"));
        ImageIcon shipFootLeft = new ImageIcon(
                getClass().getResource("/Resources/Game/Ship/shipFootLeft.png"));
        ImageIcon shipFootTop = new ImageIcon(
                getClass().getResource("/Resources/Game/Ship/shipFootTop.png"));

        //Khi đặt tàu lên sàn đấu thì từng đoạn của sẽ được đặt lên sàn đấu
        // Tùy thuộc vào Kích cỡ của tàu
        if (dir == 0) {// chọn hướng ngang
            // Ship Head
            buttons[x][y].setDisabledIcon(shipHeadLeft);
            buttons[x][y].setEnabled(false);
            // Ship Body
            // Vòng lặp dùng để đặt thân của các tàu theo hướng ngang
            for (int i = 1; i < size - 1; i++) {
                buttons[x][y + i].setDisabledIcon(shipBodyLeft);
                buttons[x][y + i].setEnabled(false);
            }
            // Ship Foot
            buttons[x][y + size - 1].setDisabledIcon(shipFootLeft);
            buttons[x][y + size - 1].setEnabled(false);
        }
        else
        {   // chọn hướng dọc
            // Ship Head
            buttons[x][y].setDisabledIcon(shipHeadTop);
            buttons[x][y].setEnabled(false);
            // Ship Body
            // Vòng lặp dùng để đặt thân của các tàu theo hướng dọc
            for (int i = 1; i < size - 1; i++) {
                buttons[x + i][y].setDisabledIcon(shipBodyTop);
                buttons[x + i][y].setEnabled(false);
            }
            // Ship Foot
            buttons[x + size - 1][y].setDisabledIcon(shipFootTop);
            buttons[x + size - 1][y].setEnabled(false);
        }
    }
}
