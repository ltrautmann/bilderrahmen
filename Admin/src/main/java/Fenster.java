import utils.BilderPool;
import utils.Client;
import utils.Gruppe;
import utils.panels.P_Buttons;
import utils.panels.P_MainWindow;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Container;
import java.util.ArrayList;

/**
 * Created by Robin on 20.01.2017.
 */
public class Fenster extends JFrame {
    private Container c;
    private P_MainWindow p_mainWindow;
    private P_Buttons p_buttons;
    private ArrayList<Client> clients;
    private ArrayList<Gruppe> gruppes;
    private BilderPool bilderPool;


    public Fenster() {

        initComponents();
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {

        new Fenster();
    }

    private void initComponents() {
        c = getContentPane();
        setLayout(new BorderLayout());

        p_buttons = new P_Buttons();
        clients = new ArrayList<>();
        gruppes = new ArrayList<>();
        bilderPool = new BilderPool();
        readData();
        p_mainWindow = new P_MainWindow(clients, gruppes, bilderPool);
        c.add(p_mainWindow, BorderLayout.CENTER);
        c.add(p_buttons, BorderLayout.SOUTH);
        p_buttons.setClients(clients);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void readData() {

        for (int i = 0; i < 10; i++) {
            clients.add(new Client("Client: " + i));
            bilderPool.addBild("ein/test/bild" + i);
            gruppes.add(new Gruppe("Gruppe: " + i));

        }
    }
}
