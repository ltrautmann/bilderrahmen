import utils.panels.P_ClientGroupSelect;

import javax.swing.*;
import java.awt.Container;

/**
 * Created by Robin on 20.01.2017.
 */
public class Fenster extends JFrame {
    private Container c;


    private P_ClientGroupSelect P_ClientGroupSelect;

    public Fenster() {
        c = getContentPane();
        P_ClientGroupSelect = new P_ClientGroupSelect(true, null, null);

        add(P_ClientGroupSelect);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {

        new Fenster();
    }
}
