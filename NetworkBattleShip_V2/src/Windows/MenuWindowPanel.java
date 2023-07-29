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
    private JPanel bgPanel;
    Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    JButton btnPVE;
    JButton btnQuit;
    public MenuWindowPanel(JFrame parent){
        setOpaque(false);
        setLayout(null);

        //Background
        img = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/Resources/GUI/menuBG.gif"));

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

        //Nút thoát game
        ImageIcon btnQuitImg = new ImageIcon(getClass().getResource("/Resources/GUI/ButtonQuit.png"));
        ImageIcon btnQuitImgOver = new ImageIcon(getClass().getResource("/Resources/GUI/ButtonQuitOver.png"));
        btnQuit = new JButton(btnQuitImg);
        btnQuit.setRolloverIcon(btnQuitImgOver);
        btnQuit.setBorder(null);
        btnQuit.setOpaque(false);
        btnQuit.setBorderPainted(false);
        btnQuit.setContentAreaFilled(false);
        btnQuit.setBounds(600, 450, 290, 90);
        btnQuit.setCursor(cursor);
        btnQuit.setText("quit");

        this.add(btnPVE);
        this.add(btnQuit);

        btnQuit.addActionListener(this);
        btnPVE.addActionListener(this);
    }

    public void paintComponent(Graphics g) {

        g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);

    }

    //Action cho hai nút được lấy từ hai method quitGame() và aiGame() bên Class MainMenuFrame
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String text = source.getText();

        if(text.equals("quit"))
        {
            MainMenuFrame.getInst().quitGame();
        }
        else if(text.equals("pve"))
        {
            MainMenuFrame.getInst().aiGame();
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
