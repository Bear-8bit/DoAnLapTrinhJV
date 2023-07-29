package Windows.WindowsPlayervsAI;

import Model.GameObject.Board;
import Model.Image.UIJPanelBG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Random;
import java.util.StringTokenizer;

public class FrameMangeShip extends JFrame implements ActionListener, KeyListener {
    private static final long serialVersionUID = 2923975805665801740L;
    private static final int NUM_SHIP = 7;
    LinkedList<int[]> playerShips; // Chứa các tàu đã được đặt
    boolean finished = false;
    int enterShips = 0;
    int[] counterShip = { 1, 1, 2, 3, 0};
    Board board;
    UIManagePanel choosePan;
    UIBoardPanel boardPanel;

    public FrameMangeShip() {
        super("BattleShip - Steampunk Edition");
        board = new Board();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(900, 700);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Resources/icon.png")));
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
        UIJPanelBG container = new UIJPanelBG(
                Toolkit.getDefaultToolkit().createImage(getClass().getResource("/Resources/GUI/readyBG.jpg")));
        boardPanel = new UIBoardPanel("manage");
        container.add(boardPanel);
        choosePan = new UIManagePanel();
        container.add(choosePan);
        boardPanel.setBounds(25, 25, 600, 620);
        choosePan.setBounds(580, 25, 280, 800);
        // Panel trong chứa thuyền để đặt
        this.add(container);
        for (int i = 0; i < boardPanel.buttons.length; i++) {
            for (int j = 0; j < boardPanel.buttons[i].length; j++) {
                boardPanel.buttons[i][j].addActionListener(this);
                boardPanel.buttons[i][j].setActionCommand(" " + i + " " + j);
            }
        }
        choosePan.random.addActionListener(this);
        choosePan.reset.addActionListener(this);
        choosePan.start.addActionListener(this);
        playerShips = new LinkedList<int[]>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String text = source.getText();
        // RESET
        if (text.equals("reset")) {
            reset();
        }
        // RANDOM
        else if (text.equals("random")) {
            random();
        }
        // START
        else if (text.equals("start")) {
            start();

        } else {
            if (finished) {
                return;
            }
            StringTokenizer st = new StringTokenizer(source.getActionCommand(), " ");
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int ship = -1;
            int size = 0;
            int dir;
            for (int i = 0; i < choosePan.ship.length; i++) {
                if (choosePan.ship[i].isSelected())
                    ship = i;
            }
            switch (ship) {
                case 0:
                    size = 5;
                    break;
                case 1:
                    size = 4;
                    break;
                case 2:
                    size = 3;
                    break;
                case 3:
                    size = 2;
                    break;
               // case 3:
                   // size = 1;
               //     break;
            }
            if (choosePan.direction[0].isSelected())// 0 = Chiều ngang / 1 = Chiều dọc
                dir = 0;
            else
                dir = 1;
            boolean insert = board.insertShip(x, y, size, dir);
            if (insert) {
                // Tăng số lượng tàu đã được đặt
                enterShips++;
                // Giảm đi số lượng tàu
                counterShip[ship]--;
                choosePan.counterLabel[ship].setText("" + counterShip[ship]);
                // vô hiệu quá việc chọn tàu nếu như toàn bộ số lượng của tàu đó đã được đặt hết
                // tự động sẽ chuyển sang con tàu tiếp theo để đặt
                if (choosePan.counterLabel[ship].getText().equals("0")) {
                    choosePan.ship[ship].setEnabled(false);
                    for (int i = 0; i < choosePan.ship.length; i++) {
                        if (choosePan.ship[i].isEnabled() && !choosePan.ship[i].isSelected()) {
                            choosePan.ship[i].setSelected(true);
                            break;
                        }
                    }
                }
                // Kiếm tra xem chúng ta đã đặt hết tàu hay chưa
                if (enterShips == NUM_SHIP) {
                    finished = true;
                    choosePan.direction[0].setEnabled(false);
                    choosePan.direction[1].setEnabled(false);
                    choosePan.start.setEnabled(true);
                }
                int[] data = { x, y, size, dir };
                playerShips.add(data);
                boardPanel.drawShip(data);
            }
        }
        this.requestFocusInWindow();
    }

    //Nút Random sẽ Reset lại các tàu và đặt tàu ngẫu nhiên và theo chiều ngẫu nhiên
    private void random() {
        if (enterShips == NUM_SHIP) {
            reset();
        }
        Random random = new Random();
        int[] data = new int[4];
        for (int i = 0; i < counterShip.length; i++) {
            for (int j = 0; j < counterShip[i]; j++) {
                data = board.insertShipRandom(random, counterShip.length - i);
                playerShips.add(data);
                boardPanel.drawShip(data);
            }
        }
        enterShips = NUM_SHIP;
        finished = true;
        choosePan.start.setEnabled(true);
        for (int i = 0; i < choosePan.ship.length; i++) {
            choosePan.ship[i].setEnabled(false);
            choosePan.counterLabel[i].setText("0");
        }
        choosePan.direction[0].setEnabled(false);
        choosePan.direction[1].setEnabled(false);
       // for (int i = 0; i < counterShip.length; i++) {
           // counterShip[i] = 0;
            //choosePan.counterLabel[i].setText("0");
       // }
        choosePan.ship[0].setSelected(true);

    }

    // Nút reset xóa toàn bộ các con tàu có trên và tạo sàn đấu mới và reset lại số lượng của các con tàu
    private void reset() {
        board = new Board();
        playerShips = new LinkedList<int[]>();
        for (int i = 0; i < Board.SIZE_BOARD; i++) {
            for (int j = 0; j < Board.SIZE_BOARD; j++) {
                boardPanel.buttons[i][j].setEnabled(true);
            }
        }
        finished = false;
        choosePan.start.setEnabled(false);
        for (int i = 0; i < choosePan.ship.length; i++) {
            choosePan.ship[i].setEnabled(true);
        }
        choosePan.direction[0].setEnabled(true);
        choosePan.direction[1].setEnabled(true);
      for (int i = 0; i < counterShip.length; i++) {
               counterShip[0] = 1;
               counterShip[i] = i;
               counterShip[4] = 0;
           choosePan.counterLabel[i].setText(" " + i);
           choosePan.counterLabel[0].setText("1");
       }
        choosePan.ship[0].setSelected(true);
        enterShips = 0;
    }

    //Nút bắt đầu nhận vào số lượng tàu và sàn đấu
    private void start() {
        FrameBattle battle = new FrameBattle(playerShips, board);
        battle.frame.setVisible(true);
        this.setVisible(false);
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        char s = Character.toLowerCase(arg0.getKeyChar());
        int keyCode = arg0.getKeyCode();
        if (s == 's') {

            random();
            start();
        } else {
            if (s == 'r') {
                random();
            } else {
                if (keyCode == KeyEvent.VK_DELETE || keyCode == KeyEvent.VK_BACK_SPACE) {
                    reset();
                } else {
                    if (keyCode == KeyEvent.VK_ESCAPE) {
                        System.exit(0);
                    }
                }
                if (keyCode == KeyEvent.VK_ENTER) {
                    if (finished) {
                        start();
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {

    }

    @Override
    public void keyTyped(KeyEvent arg0) {

    }
}
