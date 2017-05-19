package com.sabel.bilderrahmen.Admin.panels;

import com.sabel.bilderrahmen.Admin.models.CreateClientTableModel;
import com.sabel.bilderrahmen.Admin.resources.ClientPool;
import com.sabel.bilderrahmen.Admin.services.Client_Create;
import com.sabel.bilderrahmen.Admin.services.FileService;
import com.sabel.bilderrahmen.Admin.services.FtpService;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.util.ArrayList;

/**
 * Created by robin on 10.05.17.
 */
public class AcceptClient extends JFrame {
    private JTable table;
    private CreateClientTableModel createClientTableModel;
    private JScrollPane jScrollPane;
    private JPanel jpCreate;
    private JButton jbtnCreate;


    public AcceptClient() throws HeadlessException {
        initComponents();
        initEvents();
        buildWindow();
        readNewClients();

    }

    private void initComponents() {
        createClientTableModel = new CreateClientTableModel();
        table = new JTable(createClientTableModel);
        jScrollPane = new JScrollPane(table);
        jbtnCreate = new JButton("Clients Registrieren");
        jpCreate = new JPanel();

    }

    private void initEvents() {
        jbtnCreate.addActionListener(e -> {
            String dir = "files/clients/";
            ArrayList<Client_Create> created = new ArrayList<>();

            for (Client_Create client_create : createClientTableModel.getNewClients()) { // Alle nicht registrierte Clients
                if (client_create.createMe) {                                               // soll er erstellt werden?
                    if (ClientPool.getInstance().addClient(client_create)) {                // versuche zu pool hinzu zu fügen
                        if (FtpService.getInstance().delFile(dir + client_create.getFilname())) {  // datei vom Server entfernen
                            created.add(client_create);                          // neuen Client persistieren
                        } else {
                            System.out.println(dir + client_create.getFilname());
                        }
                    }

                }
            }
            FileService.writeClients();
            createClientTableModel.getNewClients().remove(created);
            table.setModel(new CreateClientTableModel());

        });
    }

    private void buildWindow() {
        getContentPane().add(BorderLayout.CENTER, jScrollPane);
        jpCreate.add(jbtnCreate);
        getContentPane().add(BorderLayout.SOUTH, jpCreate);
        pack();
        setVisible(true);


    }

    private void readNewClients() {

    }
}
