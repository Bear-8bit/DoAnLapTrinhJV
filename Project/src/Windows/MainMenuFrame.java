package Windows;

import Model.Image.UIJPanelBG;
import Windows.WindowsPlayervsAI.FrameMangeShip;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class MainMenuFrame extends JFrame implements Runnable {
    static MainMenuFrame inst;
    public JFrame frame = new JFrame("Battleship - Steampunk Edition");
    MenuWindowPanel menuWindowPanel;
    private int mX, mY;
    private int x, y;
    public int mouseX, mouseY;
    public static int xOffset = 8, yOffset = 31;
    Cursor defaultCursor = Cursor.getDefaultCursor();
    Cursor targetCursor;


    public static MainMenuFrame getInst(){
        if(inst == null)
        {
            inst = new MainMenuFrame(1024, 768);
            System.out.println("Lỗi");
        }
        return inst;
    }

    public MainMenuFrame(int width, int height){

        if(inst == null)
            inst = this;

        setSize(width, height);
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - getWidth() / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height) / 2 - getHeight() / 2);
        setTitle("Battleship");

        menuWindowPanel = new MenuWindowPanel(this);
        menuWindowPanel.setAlignmentX(0.5f);
        menuWindowPanel.setAlignmentX(0.5f);
        add(menuWindowPanel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        Thread t = new Thread(this);
        t.run();
    }

    public void pvpGame(){
        JOptionPane.showMessageDialog(frame, "Tính năng đang được cập nhật");
    }
    public void aiGame(){
        removePanels();
        FrameMangeShip manage = new FrameMangeShip();
        manage.setVisible(true);
    }
    public void gameStat(){
        File file = new File("SavePoint/point.txt");
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));


        try
        {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while((line = br.readLine()) != null)
            {
                JLabel label = new JLabel(line.toString());
                label.setText(line);
                container.add(label);

                frame.add(container);
                frame.setBounds(100, 100, 290, 450);
                frame.setResizable(true);
                frame.setVisible(true);

                System.out.println(line);
            }
            br.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

    }
    public void quitGame(){
        dispose();
        System.exit(0);
    }

    public void removePanels() {
        dispose();
        setVisible(false);
    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (this) {
                    mX = MouseInfo.getPointerInfo().getLocation().x;
                    mY = MouseInfo.getPointerInfo().getLocation().y;

                    x = getLocation().x;
                    y = getLocation().y;

                    mouseX = mX - x - xOffset;
                    mouseY = mY - y - yOffset;
                }
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void setDefaultCursor() {
        setCursor(defaultCursor);
    }

    public void setTargetCursor() {
        setCursor(targetCursor);
    }
}
