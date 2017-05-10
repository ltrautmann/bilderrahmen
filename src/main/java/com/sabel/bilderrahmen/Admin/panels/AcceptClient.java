package com.sabel.bilderrahmen.Admin.panels;

import com.sabel.bilderrahmen.Admin.models.CreateClientTableModel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by robin on 10.05.17.
 */
public class AcceptClient extends JFrame {
    private JTable table;
    private CreateClientTableModel createClientTableModel;
    private JScrollPane jScrollPane;


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

    }

    private void initEvents() {
    }

    private void buildWindow() {
        getContentPane().add(jScrollPane);
        pack();
        setVisible(true);


    }

    private void readNewClients() {

    }
}
