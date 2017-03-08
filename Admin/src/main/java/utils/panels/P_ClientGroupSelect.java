package utils.panels;

import utils.Client;
import utils.Gruppe;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by robin on 22.01.17.
 */
public class P_ClientGroupSelect extends JPanel {


    private ArrayList<Client> arrayListClients;
    private ArrayList<Gruppe> gruppeArrayList;
    private JPanel center;
    private JPanel buttons;
    private JButton[] jButtons;
    private P_Groups p_Groups_Global;
    private P_Groups p_Groups_User;
    private P_ClientSelect p_ClientSelect;


    public P_ClientGroupSelect(ArrayList<Client> arrayListClients, ArrayList<Gruppe> gruppeArrayList) {
        this.arrayListClients = arrayListClients;
        this.gruppeArrayList = gruppeArrayList;
        initComponents();
        initEvents();


    }

    private void initEvents() {
        jButtons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p_Groups_User.addGruppe(p_Groups_Global.getSelected());
                System.out.println(p_Groups_User.getSize());
            }
        });
        jButtons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Gruppe g : p_Groups_User.getSelected())
                    p_Groups_User.rmGruppe(g);
            }
        });

        p_ClientSelect.setComboboxActionlistener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p_Groups_User .setClient((Client) p_ClientSelect.getSelectedItem());
            }
        });

    }

    public ArrayList getClientArraylist(){
        return arrayListClients;
    }
    public ArrayList getGruppen() {
        return gruppeArrayList;
    }
    private void initComponents() {
        setLayout(new BorderLayout());
        center = new JPanel();
        buttons = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));

        jButtons = new JButton[2];
        jButtons[0] = new JButton("Hinzufuegen >");
        jButtons[1] = new JButton(" Entfernen   ");
        buttons.add(jButtons[0]);
        buttons.add(jButtons[1]);

        p_Groups_User = new P_Groups(arrayListClients.get(0));
        p_ClientSelect = new P_ClientSelect(arrayListClients);
        p_Groups_Global = new P_Groups(gruppeArrayList);
        add(p_ClientSelect, BorderLayout.NORTH);
        center.add(p_Groups_Global);
        center.add(buttons);
        center.add(p_Groups_User);
        add(center);
    }

//    public void testdaten() {
//        this.arrayListClients = new ArrayList<Client>();
//        this.gruppeArrayList = new ArrayList<Gruppe>();
//        Client c[] = new Client[100];
//        Gruppe g[] = new Gruppe[100];
//
//        for (int i = 0; i < c.length; i++) {
//            c[i] = new Client();
//            g[i] = new Gruppe("Gruppe_" + i);
//            c[i].setName("Client_Nr:" + i);
//            arrayListClients.add(c[i]);
//            gruppeArrayList.add(g[i]);
//        }
//    }
}
