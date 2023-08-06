
import Model.GameObject.Board;
import Windows.FrameSplashScreen;
import Windows.MainMenuFrame;
import Windows.WindowsPlayervsAI.FrameMangeShip;
import jdk.jshell.spi.ExecutionControl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Main {


    public static void main(String[] args)
    {
        FrameSplashScreen intro = new FrameSplashScreen();
        try {
           Thread.sleep(4000);
        }
        catch (InterruptedException e) {

        }

       intro.setVisible(false);
       MainMenuFrame mainMenuFrame = new MainMenuFrame(1500, 800);

    }
}