package Windows;

import Model.Image.UIJPanelBG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.jar.JarEntry;

/*
    Class chứa Panel của Frame Menu và background của Menu
    bao gồm nút Chơi với AI và Thoát game
    thêm ActionListener cho hai nút và MouseListener
 */

public class MenuWindowPanel extends JPanel implements ActionListener, KeyListener {
    private Image img;
    Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    JButton btnPVE;
    JButton btnPVP;
    JButton btnStat;
    JButton btnQuit;
    public MenuWindowPanel(JFrame parent){
        setOpaque(false);
        setLayout(null);

        //Background
        img = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/Resources/GUI/menuBG.gif"));

        //Nút đấu với người
        ImageIcon btnPVPImg = new ImageIcon(getClass().getResource("/Resources/GUI/ButtonPVP.png"));
        ImageIcon btnPVPImgOver = new ImageIcon(getClass().getResource("/Resources/GUI/ButtonPVPOver.png"));
        btnPVP = new JButton(btnPVPImg);
        btnPVP.setRolloverIcon(btnPVPImgOver);
        btnPVP.setBorder(null);
        btnPVP.setOpaque(false);
        btnPVP.setBorderPainted(false);
        btnPVP.setContentAreaFilled(false);
        btnPVP.setBounds(600, 250, 290, 90);
        btnPVP.setCursor(cursor);
        btnPVP.setText("pvp");

        //Nút đấu với máy
        ImageIcon btnPVEImg = new ImageIcon(getClass().getResource("/Resources/GUI/ButtonAI.png"));
        ImageIcon btnPVEImgOver = new ImageIcon(getClass().getResource("/Resources/GUI/ButtonAIOver.png"));
        btnPVE = new JButton(btnPVEImg);
        btnPVE.setRolloverIcon(btnPVEImgOver);
        btnPVE.setBorder(null);
        btnPVE.setOpaque(false);
        btnPVE.setBorderPainted(false);
        btnPVE.setContentAreaFilled(false);
        btnPVE.setBounds(600, 350, 290, 90);
        btnPVE.setCursor(cursor);
        btnPVE.setText("pve");

        //Nút lịch sử đấu
        ImageIcon btnStatImg = new ImageIcon(getClass().getResource("/Resources/GUI/ButtonStat.png"));
        ImageIcon btnStatImgOver = new ImageIcon(getClass().getResource("/Resources/GUI/ButtonStatOver.png"));
        btnStat = new JButton(btnStatImg);
        btnStat.setRolloverIcon(btnStatImgOver);
        btnStat.setBorder(null);
        btnStat.setOpaque(false);
        btnStat.setBorderPainted(false);
        btnStat.setContentAreaFilled(false);
        btnStat.setBounds(600, 450, 290, 90);
        btnStat.setCursor(cursor);
        btnStat.setText("stat");

        //Nút thoát game
        ImageIcon btnQuitImg = new ImageIcon(getClass().getResource("/Resources/GUI/ButtonQuit.png"));
        ImageIcon btnQuitImgOver = new ImageIcon(getClass().getResource("/Resources/GUI/ButtonQuitOver.png"));
        btnQuit = new JButton(btnQuitImg);
        btnQuit.setRolloverIcon(btnQuitImgOver);
        btnQuit.setBorder(null);
        btnQuit.setOpaque(false);
        btnQuit.setBorderPainted(false);
        btnQuit.setContentAreaFilled(false);
        btnQuit.setBounds(600, 550, 290, 90);
        btnQuit.setCursor(cursor);
        btnQuit.setText("quit");

        this.add(btnPVP);
        this.add(btnPVE);
        this.add(btnStat);
        this.add(btnQuit);

        btnPVP.addActionListener(this);
        btnPVE.addActionListener(this);
        btnStat.addActionListener(this);
        btnQuit.addActionListener(this);
    }

    public void paintComponent(Graphics g) {

        g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);

    }

    //Action cho hai nút được lấy từ hai method quitGame() và aiGame() bên Class MainMenuFrame
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String text = source.getText();

        if(text.equals("pvp"))
        {
            MainMenuFrame.getInst().pvpGame();
        }
        else if(text.equals("pve"))
        {
            MainMenuFrame.getInst().aiGame();
        }
        else if(text.equals("stat"))
        {
            MainMenuFrame.getInst().gameStat();
        }
        else if(text.equals("quit"))
        {
            MainMenuFrame.getInst().quitGame();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
