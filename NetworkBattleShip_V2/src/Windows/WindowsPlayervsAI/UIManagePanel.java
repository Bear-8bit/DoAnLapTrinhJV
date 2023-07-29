package Windows.WindowsPlayervsAI;

import Model.Image.UIJPanelBG;

import javax.swing.*;
import java.awt.*;

/*
   Class này dùng quản lý UI cho người chơi đặt tàu
   Bao gồm có RadioButton cho tàu và hướng đặt tàu
   Label cho số lượng tàu đặt và Button cho nút reset
   Start và Random
 */

public class UIManagePanel extends UIJPanelBG {
    private static final long serialVersionUID = 1L;
    Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    public JRadioButtonMenuItem[] ship;
    public JLabel[] counterLabel = new JLabel[5];
    JLabel[] xLabel = new JLabel[4];
    ButtonGroup radioButtonShip;
    public JRadioButton[] direction;
    public JButton random;
    public JButton reset;
    public JButton start;

    public UIManagePanel() {

        super(Toolkit.getDefaultToolkit()
                .createImage(FrameMangeShip.class.getResource("/Resources/GUI/managePanel.png")));
        this.setLayout(null);
        this.setOpaque(false);

        // "Đặt thuyền"
        JLabel managePanelLabel = new JLabel();
        managePanelLabel.setIcon(new ImageIcon(getClass().getResource("")));
        managePanelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        managePanelLabel.setBounds(0, 30, 280, 35);

        // RadioButton cho việc lựa chọn tàu để đặt
        ship = new JRadioButtonMenuItem[4];
        radioButtonShip = new ButtonGroup();
        JPanel shipSelect = new JPanel(null);
        shipSelect.setOpaque(false);
        shipSelect.setBounds(30, 90, 200, 300);

        //Hình ảnh của các con tàu và RadioButton cho mỗi loại tàu có 4 loại ships[4]
        //ImageIcon ship1 = new ImageIcon(getClass().getResource("/Resources/Game/Ship/ship1.png"));
        ImageIcon ship2 = new ImageIcon(getClass().getResource("/Resources/Game/Ship/ship2.png"));
        ImageIcon ship3 = new ImageIcon(getClass().getResource("/Resources/Game/Ship/ship3.png"));
        ImageIcon ship4 = new ImageIcon(getClass().getResource("/Resources/Game/Ship/ship4.png"));
        ImageIcon ship5 = new ImageIcon(getClass().getResource("/Resources/Game/Ship/ship5.png"));
        ship[0] = new JRadioButtonMenuItem(ship5);
        ship[1] = new JRadioButtonMenuItem(ship4);
        ship[2] = new JRadioButtonMenuItem(ship3);
        ship[3] = new JRadioButtonMenuItem(ship2);

        //counterLabel là dùng để chỉ số lượng mà mỗi loại tàu có chỉ có 4 loại tàu
        //counterLabel[4] là dành cho một loại tàu khác không sử dụng trong game
        //ship[3] = new JRadioButtonMenuItem(ship1);
        counterLabel[0] = new JLabel("1");
        counterLabel[1] = new JLabel("1");
        counterLabel[2] = new JLabel("2");
        counterLabel[3] = new JLabel("3");
        counterLabel[4] = new JLabel("0");
        for (int i = 0; i < ship.length; i++) {
            ship[i].setBounds(0, 25 + (i * 60), 160, 40);
            radioButtonShip.add(ship[i]);
            shipSelect.add(ship[i]);
            ship[i].setOpaque(false);
            counterLabel[i].setBounds(220, 125 + (i * 60), 23, 19);
            counterLabel[i].setOpaque(false);
            this.add(counterLabel[i]);
            xLabel[i] = new JLabel("x");
            xLabel[i].setBounds(205, 125 + (i * 60), 23, 19);
            xLabel[i].setOpaque(false);
            this.add(xLabel[i]);
        }
        ship[0].setSelected(true);

        // RadioButton để lựa chọn giữa chiều ngang và chiều dọc
        // Chiều dọc/Chiều ngang
        direction = new JRadioButton[2];
        ButtonGroup radioButtonDirection = new ButtonGroup();
        direction[0] = new JRadioButton("Chiều ngang");
        direction[0].setBounds(0, 260, 105, 20);
        radioButtonDirection.add(direction[0]);
        direction[0].setSelected(true);
        direction[0].setOpaque(false);
        shipSelect.add(direction[0]);
        direction[1] = new JRadioButton("Chiều dọc");
        direction[1].setBounds(110, 260, 105, 20);
        direction[1].setOpaque(false);
        radioButtonDirection.add(direction[1]);
        shipSelect.add(direction[1]);

        // Nút ngẫu nhiên
        ImageIcon randomImg = new ImageIcon(getClass().getResource("/Resources/GUI/random.png"));
        ImageIcon randomImgOver = new ImageIcon(getClass().getResource("/Resources/GUI/randomOver.png"));
        random = new JButton(randomImg);
        random.setRolloverIcon(randomImgOver);
        random.setBorder(null);
        random.setOpaque(false);
        random.setBorderPainted(false);
        random.setContentAreaFilled(false);
        random.setBounds(20, 400, 250, 83);
        random.setCursor(cursor);
        random.setText("random");

        // Nút đặt lại
        ImageIcon resetImg = new ImageIcon(getClass().getResource("/Resources/GUI/reset.png"));
        ImageIcon resetImgOver = new ImageIcon(getClass().getResource("/Resources/GUI/resetOver.png"));
        reset = new JButton(resetImg);
        reset.setRolloverIcon(resetImgOver);
        reset.setBorder(null);
        reset.setOpaque(false);
        reset.setBorderPainted(false);
        reset.setContentAreaFilled(false);
        reset.setBounds(10, 500, 137, 102);
        reset.setCursor(cursor);
        reset.setText("reset");

        // Nút bắt đầu
        ImageIcon startImg = new ImageIcon(getClass().getResource("/Resources/GUI/start.png"));
        ImageIcon startImgOver = new ImageIcon(getClass().getResource("/Resources/GUI/startOver.png"));
        start = new JButton(startImg);
        start.setRolloverIcon(startImgOver);
        start.setBorder(null);
        start.setOpaque(false);
        start.setBorderPainted(false);
        start.setContentAreaFilled(false);
        start.setBounds(150, 500, 137, 102);
        start.setCursor(cursor);
        start.setText("start");
        start.setEnabled(false);

        //Thêm các component vào Panel
        this.add(managePanelLabel);
        this.add(shipSelect);
        this.add(random);
        this.add(start);
        this.add(reset);

    }
}
