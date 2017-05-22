package com.sabel.bilderrahmen.Admin.panels;

import com.sabel.bilderrahmen.Admin.Client;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.GridLayout;

/**
 * Created by robin on 09.05.17.
 */
public class EditClient extends Edit_Times {
    private JPanel jPanelGrid;
    private JPanel jPanels[];
    private JTextField jTextFieldName;
    private JTextField jTextFieldMac;
    private JLabel jLabelName;
    private JLabel jLabelMac;
    private JCheckBox randomCB;
    private JLabel defaultTimeLable;
    private JSpinner jSpinner;

    public EditClient() {
        super(MOD_CLIENT);
        jLabelClient.setText("Client auswählen: ");
        jPanels = new JPanel[4];
        for (int i = 0; i < jPanels.length; i++) {
            jPanels[i] = new JPanel();
            jPanels[i].setLayout((new BoxLayout(jPanels[i], BoxLayout.X_AXIS)));
        }

        jSpinner = new JSpinner();
        jPanelGrid = new JPanel();
        jPanelGrid.setLayout(new GridLayout(2, 2));
        defaultTimeLable = new JLabel("   USB Anzeigedauer ");
        jLabelName = new JLabel("Name   ");
        jTextFieldName = new JTextField(10);
        jLabelMac = new JLabel("   MacAddress           ");
        jTextFieldMac = new JTextField(10);
        randomCB = new JCheckBox("Zufällige wiedergabe");
        jPanels[0].add(jLabelName);
        jPanels[0].add(jTextFieldName);
        jPanels[1].add(jLabelMac);
        jPanels[1].add(jTextFieldMac);
        jPanels[2].add(randomCB);
        jPanels[3].add(defaultTimeLable);
        jPanels[3].add(jSpinner);
        jPanelGrid.add(jPanels[0]);
        jPanelGrid.add(jPanels[1]);
        jPanelGrid.add(jPanels[2]);
        jPanelGrid.add(jPanels[3]);
        add(jPanelGrid, 1);
        showName();
        jComboBox.addActionListener(e -> showName());

        jTextFieldMac.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                ((Client) jComboBox.getSelectedItem()).setMac(jTextFieldMac.getText());

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                ((Client) jComboBox.getSelectedItem()).setMac(jTextFieldMac.getText());

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                ((Client) jComboBox.getSelectedItem()).setMac(jTextFieldMac.getText());

            }
        });

        jTextFieldName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                ((Client) jComboBox.getSelectedItem()).setName(jTextFieldName.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                ((Client) jComboBox.getSelectedItem()).setName(jTextFieldName.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                ((Client) jComboBox.getSelectedItem()).setName(jTextFieldName.getText());
            }
        });
        randomCB.addActionListener(e -> ((Client) jComboBox.getSelectedItem()).setRandomImageOrder(randomCB.isSelected()));
        jSpinner.addChangeListener(e -> ((Client) jComboBox.getSelectedItem()).setDefaultanzeigedauer((Integer) jSpinner.getValue()));


    }

    private void showName() {
        jTextFieldName.setText(jComboBox.getSelectedItem().toString());
        jSpinner.setValue(((Client) jComboBox.getSelectedItem()).getDefaultanzeigedauer());
        if (jComboBox.getSelectedItem() instanceof Client) {
            jTextFieldMac.setText(((Client) jComboBox.getSelectedItem()).getMac());
            randomCB.setSelected(((Client) jComboBox.getSelectedItem()).isRandomImageOrder());
        }
    }
}
