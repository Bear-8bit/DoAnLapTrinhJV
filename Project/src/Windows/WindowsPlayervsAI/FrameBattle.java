package Windows.WindowsPlayervsAI;

import Model.GameObject.*;
import Model.Image.UIJPanelBG;
import Windows.MainMenuFrame;
import Windows.MenuWindowPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class FrameBattle implements ActionListener, KeyListener {
    UIBoardPanel playerPanel = new UIBoardPanel("player");
    UIBoardPanel cpuPanel = new UIBoardPanel("cpu");
    public JFrame frame = new JFrame("Battleship - Steampunk Edition");
    JPanel comandPanel = new JPanel();
    Cursor cursorDefault = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    UIJPanelBG panel = new UIJPanelBG(
            Toolkit.getDefaultToolkit().createImage(getClass().getResource("/Resources/GUI/battleBG.jpg")));
    Computer cpu;
    Board cpuBoard;
    Board playerBoard;
    int numShipPlayer = 7;
    int numShipCPU = 7;
    int pointPlayer = 0;
    int pointCPU = 0;
    StringBuilder sb = new StringBuilder();
    boolean b = true;
    UIStatPanel statPlayer;
    UIStatPanel statCPU;
    JPanel targetPanel = new JPanel(null);
    JLabel label = new JLabel();
    UIJPanelBG target = new UIJPanelBG(
            Toolkit.getDefaultToolkit().createImage(getClass().getResource("/Resources/Game/Board/target.png")));
    ImageIcon wreck = new ImageIcon(getClass().getResource("/Resources/Game/Board/wreck.gif"));
    Cursor cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    Timer timer;
    boolean turnCPU;

    public FrameBattle(LinkedList<int[]> playerShips, Board board) {
        playerBoard = board;
        cpu = new Computer(board);
        cpuBoard = new Board();
        cpuBoard.fillBoardRandom();
        frame.setSize(1100, 750);
        frame.setTitle("BattleShip - Steampunk Edition");
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.addKeyListener(this);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Resources/icon.png")));
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        label = new JLabel("POINT: " + pointPlayer);
        label.setBounds(50,-35,500,120);
        label.setFont(new Font("PEAK", Font.BOLD, 25));
        label.setForeground(Color.WHITE);

        //Panel chứa các tàu có thể bị đánh chìm
        statPlayer = new UIStatPanel();
        statCPU = new UIStatPanel();
        statPlayer.setBounds(30, 595, 500, 120);
        statCPU.setBounds(570, 595, 500, 120);
        frame.add(statPlayer);
        frame.add(statCPU);
        // Target Panel
        targetPanel.setBounds(0, 0, 500, 500);
        targetPanel.setOpaque(false);
        playerPanel.sky.add(targetPanel);

        panel.add(playerPanel);
        playerPanel.add(label);
        playerPanel.setBounds(0, 0, UIBoardPanel.X, UIBoardPanel.Y);
        playerPanel.setOpaque(false);
        panel.add(cpuPanel);
        cpuPanel.setBounds(540, 0, UIBoardPanel.X, UIBoardPanel.Y);
        panel.add(comandPanel);
        frame.add(panel);
        frame.setResizable(false);
        timer = new Timer(1000, new TimeManager());
        turnCPU = false;

        for (int i = 0; i < cpuPanel.buttons.length; i++) {
            for (int j = 0; j < cpuPanel.buttons[i].length; j++) {
                cpuPanel.buttons[i][j].addActionListener(this);
                cpuPanel.buttons[i][j].setActionCommand(" " + i + " " + j);
            }
        }
        for (int[] v : playerShips) {
            playerPanel.drawShip(v);
        }


    }

    void setBox(Report rep, boolean player) {
        int x = rep.getC().getCoordX();
        int y = rep.getC().getCoordY();
        ImageIcon fire = new ImageIcon(getClass().getResource("/Resources/GUI/fireButton.gif"));
        ImageIcon water = new ImageIcon(getClass().getResource("/Resources/GUI/grayButton.gif"));
        String what;
        if (rep.isHits())
            what = "X";
        else
            what = "A";
        UIBoardPanel boardPanel;
        if (!player) {
            boardPanel = playerPanel;
        } else {
            boardPanel = cpuPanel;
        }
        if (what == "X" && boardPanel == cpuPanel ) {
            boardPanel.buttons[x][y].setIcon(fire);
            boardPanel.buttons[x][y].setEnabled(false);
            boardPanel.buttons[x][y].setDisabledIcon(fire);
            boardPanel.buttons[x][y].setCursor(cursorDefault);
            pointPlayer += 10;
            label.setText("POINT: " + pointPlayer);
        } else if (what == "X" && boardPanel == playerPanel) {
            boardPanel.buttons[x][y].setIcon(fire);
            boardPanel.buttons[x][y].setEnabled(false);
            boardPanel.buttons[x][y].setDisabledIcon(fire);
            boardPanel.buttons[x][y].setCursor(cursorDefault);
            pointCPU += 10;
            System.out.println(" CPU: " + pointCPU);
        } else if (what == "A" && boardPanel == cpuPanel){
            pointPlayer -= 1;
            boardPanel.buttons[x][y].setIcon(water);
            boardPanel.buttons[x][y].setEnabled(false);
            boardPanel.buttons[x][y].setDisabledIcon(water);
            boardPanel.buttons[x][y].setCursor(cursorDefault);
            label.setText("POINT: " + pointPlayer);

        } else if (what == "A" && boardPanel == playerPanel){
            boardPanel.buttons[x][y].setIcon(water);
            boardPanel.buttons[x][y].setEnabled(false);
            boardPanel.buttons[x][y].setDisabledIcon(water);
            boardPanel.buttons[x][y].setCursor(cursorDefault);
            pointCPU -= 1;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (turnCPU)
            return;
        File file = new File("SavePoint/point.txt");
        JButton source = (JButton) e.getSource();
        StringTokenizer st = new StringTokenizer(source.getActionCommand(), " ");
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        Coordinate newC = new Coordinate(x, y);
        boolean hit = cpuBoard.shoot(newC);
        Report rep = new Report(newC, hit, false);
        this.setBox(rep, true);
        if (hit) { // Nếu như đánh trúng phần tàu của đối phương thì người chơi sẽ được quyền thực hiện thêm
                   // 1 đòn đánh nữa
            Ship shipSink = cpuBoard.sunk(newC);
            if (shipSink != null) {
                numShipCPU--;
                setSunk(shipSink);
                // Người chơi được quyền lựa chọn tạo trận mới hoặc thoát game sau trận đã kết thúc
                if (numShipCPU == 0) {
                    Object[] options = { "New Match", "Quit" };
                    int n = JOptionPane.showOptionDialog(frame, (new JLabel("You Win!" + " Your Point: " + pointPlayer, JLabel.CENTER)),
                            "Win", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options,
                            options[1]);

                    try
                    {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
                        bw.write("-------------------------------------------------------------------");
                        bw.newLine();
                        bw.write("|||     "+  pointPlayer +"         |||      Victory     |||             PVE         |||" );
                        bw.newLine();

                        bw.close();
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }

                    if (n == 0) {
                        FrameMangeShip restart = new FrameMangeShip();
                        restart.setVisible(true);
                        this.frame.setVisible(false);
                    } else {
                        System.exit(0);
                    }
                }
            }
        } else { // tùy thuộc vào máy

            if (b) {
                timer.start();
                turnCPU = true;
            }
        }
        frame.requestFocusInWindow();
    }

    private void setSunk(Coordinate c) {
        LinkedList<String> possibility = new LinkedList<String>();
        if (c.getCoordX() != 0) {
            possibility.add("N");
        }
        if (c.getCoordX() != Board.SIZE_BOARD - 1) {
            possibility.add("S");
        }
        if (c.getCoordY() != 0) {
            possibility.add("W");
        }
        if (c.getCoordY() != Board.SIZE_BOARD - 1) {
            possibility.add("E");
        }
        String direction;
        boolean found = false;
        Coordinate currentPos;
        do {
            currentPos = new Coordinate(c);
            if (possibility.isEmpty()) {
                deleteShip(1, statPlayer);
                playerPanel.buttons[currentPos.getCoordX()][currentPos.getCoordY()].setIcon(wreck);
                playerPanel.buttons[currentPos.getCoordX()][currentPos.getCoordY()].setEnabled(false);
                playerPanel.buttons[currentPos.getCoordX()][currentPos.getCoordY()].setDisabledIcon(wreck);
                playerPanel.buttons[currentPos.getCoordX()][currentPos.getCoordY()].setCursor(cursorDefault);
                return;
            }
            direction = possibility.removeFirst();
            currentPos.move(direction.charAt(0));
            if (playerBoard.hit(currentPos)) {
                found = true;
            }
        } while (!found);
        int size = 0;
        currentPos = new Coordinate(c);
        do {

            playerPanel.buttons[currentPos.getCoordX()][currentPos.getCoordY()].setIcon(wreck);
            playerPanel.buttons[currentPos.getCoordX()][currentPos.getCoordY()].setEnabled(false);
            playerPanel.buttons[currentPos.getCoordX()][currentPos.getCoordY()].setDisabledIcon(wreck);
            playerPanel.buttons[currentPos.getCoordX()][currentPos.getCoordY()].setCursor(cursorDefault);
            currentPos.move(direction.charAt(0));

            size++;
        } while (currentPos.getCoordX() >= 0 && currentPos.getCoordX() <= 9 && currentPos.getCoordY() >= 0
                && currentPos.getCoordY() <= 9 && !playerBoard.aqua(currentPos));

        deleteShip(size, statPlayer);
    }

    private void setSunk(Ship shipSunk) {
        int size = 0;
        for (int i = shipSunk.getX1(); i <= shipSunk.getX2(); i++) {
            for (int j = shipSunk.getY1(); j <= shipSunk.getY2(); j++) {
                cpuPanel.buttons[i][j].setIcon(wreck);
                cpuPanel.buttons[i][j].setEnabled(false);
                cpuPanel.buttons[i][j].setDisabledIcon(wreck);
                cpuPanel.buttons[i][j].setCursor(cursorDefault);
                size++;
            }
        }
        deleteShip(size, statCPU);
    }

    private void deleteShip(int size, UIStatPanel panel) {
        switch (size) {
            case 5:
                panel.ships[0].setEnabled(false);
                break;
            case 4:
                panel.ships[1].setEnabled(false);
                break;
            case 3:
                if (!panel.ships[2].isEnabled())
                    panel.ships[3].setEnabled(false);
                else
                    panel.ships[2].setEnabled(false);
                break;
            case 2:
                if (!panel.ships[4].isEnabled())
                    if (!panel.ships[5].isEnabled())
                        panel.ships[6].setEnabled(false);
                    else
                        panel.ships[5].setEnabled(false);
                else
                    panel.ships[4].setEnabled(false);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        int keyCode = arg0.getKeyCode();
        if (keyCode == KeyEvent.VK_ESCAPE) {
            FrameMangeShip manage = new FrameMangeShip();
            manage.setVisible(true);
            frame.setVisible(false);
        }

        sb.append(arg0.getKeyChar());
        if (sb.length() == 4) {
            int z = sb.toString().hashCode();
            if (z == 3194657) {
                sb = new StringBuilder();
                b = !b;
            } else {
                String s = sb.substring(1, 4);
                sb = new StringBuilder(s);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }

    @Override
    public void keyTyped(KeyEvent arg0) {

    }

    public class TimeManager implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            timer.stop();
            boolean flag;
            File file = new File("SavePoint/point.txt");
            Report report = cpu.myTurn();
            drawTarget(report.getC().getCoordX() * 50, report.getC().getCoordY() * 50);
            flag = report.isHits();
            setBox(report, false);
            if (report.isSink()) {
                numShipPlayer--;
                setSunk(report.getC());
                if (numShipPlayer == 0) {
                    Object[] options = { "Rematch", "Quit" };
                    int n = JOptionPane.showOptionDialog(frame, (new JLabel("You Lose" + " Your Point: " + pointPlayer, JLabel.CENTER)),
                            "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options,
                            options[1]);


                    try
                    {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
                        bw.write("-------------------------------------------------------------------");
                        bw.newLine();
                        bw.write("|||     "+  pointPlayer +"         |||      Defeat     |||             PVE         |||" );
                        bw.newLine();

                        bw.close();
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }

                    if (n == 0) {
                        FrameMangeShip restart = new FrameMangeShip();
                        restart.setVisible(true);
                        frame.setVisible(false);
                    } else {
                        System.exit(0);
                    }
                }
            }

            turnCPU = false;
            if (flag) {
                timer.start();
                turnCPU = true;
            }
            frame.requestFocusInWindow();
        }

    }

    public void drawTarget(int i, int j) {
        target.setBounds(j, i, 50, 50);
        target.setVisible(true);
        targetPanel.add(target);
        targetPanel.repaint();
    }

}
