import utils.panels.P_ClientGroupSelect;

import javax.swing.*;
import java.awt.Container;

/**
 * Created by Robin on 20.01.2017.
 */
public class Fenster extends JFrame {
    private Container c;
    private JTabbedPane tabbedPane;
    private P_ClientGroupSelect p_ClientGroupSelect;

    public Fenster() {
        initComponents();
        pack();
        setVisible(true);
    }

    private void initComponents(){
        c = getContentPane();
        tabbedPane = new JTabbedPane();
        p_ClientGroupSelect = new P_ClientGroupSelect(true, null, null);
        tabbedPane.addTab("Clients", null, p_ClientGroupSelect);
        tabbedPane.addTab("Gruppen", null, new JPanel());
        c.add(tabbedPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        new Fenster();
    }
}
