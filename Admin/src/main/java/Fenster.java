import utils.panels.P_Buttons;
import utils.panels.P_ClientGroupSelect;
import utils.panels.P_MainWindow;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Container;

/**
 * Created by Robin on 20.01.2017.
 */
public class Fenster extends JFrame {
    private Container c;
    private P_MainWindow p_mainWindow;
    private P_Buttons p_buttons;


    public Fenster() {
        initComponents();
        pack();
        setVisible(true);
    }

    private void initComponents(){
        c = getContentPane();
        setLayout(new BorderLayout());

        p_buttons = new P_Buttons();
        p_mainWindow = new P_MainWindow(true, null, null);
        c.add(p_mainWindow, BorderLayout.CENTER);
        c.add(p_buttons, BorderLayout.SOUTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        new Fenster();
    }
}
