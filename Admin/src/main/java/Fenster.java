import utils.BilderPool;
import utils.Client;
import utils.ClientPool;
import utils.GroupPool;
import utils.panels.P_Buttons;
import utils.panels.P_MainWindow;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;

/**
 * Created by Robin on 20.01.2017.
 */
public class Fenster extends JFrame {
    private Container c;
    private P_MainWindow p_mainWindow;
    private P_Buttons p_buttons;
    private ClientPool clients;
    private GroupPool gruppes;
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
        clients = new ClientPool();
        gruppes = new GroupPool();
        bilderPool = new BilderPool();
        readData();
        p_mainWindow = new P_MainWindow(clients.getClientArrayList(), gruppes.getGruppeArrayList(), bilderPool);
        c.add(p_mainWindow, BorderLayout.CENTER);
        c.add(p_buttons, BorderLayout.SOUTH);
        p_buttons.setClients(clients);
        p_buttons.setGroups(gruppes);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void readData() {

        for (int i = 0; i < 10; i++) {
            //  clients.add(new Client("Client: " + i));
            bilderPool.addBild("ein/test/bild" + i);
            // gruppes.add(new Gruppe("Gruppe: " + i));
        }
        JAXBContext jc = null;
        try {
            jc = JAXBContext.newInstance(ClientPool.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            File xml = new File("D:\\Schippan\\TEMPBitteLoeschen.xml");
            clients = (ClientPool) unmarshaller.unmarshal(xml);

            jc = JAXBContext.newInstance(GroupPool.class);
            unmarshaller = jc.createUnmarshaller();
            xml = new File("D:\\Schippan\\GroupsLOECHMICH.xml");
            gruppes = (GroupPool) unmarshaller.unmarshal(xml);

            for (Client c : clients.getClientArrayList()) {
                for (Object o : c.getArrayListGroupNames()) {
                    c.addGroup(gruppes.getGroupByName(o.toString()));
                    System.out.println(".");

                }
            }


        } catch (JAXBException e) {
            e.printStackTrace();
        }


    }
}
