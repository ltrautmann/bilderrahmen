package com.sabel.bilderrahmen.Admin.panels;

import com.sabel.bilderrahmen.Admin.Client;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by robin on 09.05.17.
 */
public class EditClient extends Edit_Times {
    private JPanel jPanelRow;
    private JTextField jTextFieldName;
    private JTextField jTextFieldMac;
    private JLabel jLabelName;
    private JLabel jLabelMac;

    public EditClient() {
        super(MOD_CLIENT);
        jPanelRow = new JPanel();
        jPanelRow.setLayout(new BoxLayout(jPanelRow, BoxLayout.X_AXIS));
        jLabelName = new JLabel("Name:");
        jPanelRow.add(jLabelName);
        jTextFieldName = new JTextField(20);
        jPanelRow.add(jTextFieldName);
        jLabelMac = new JLabel("MacAddress");
        jPanelRow.add(jLabelMac);
        jTextFieldMac = new JTextField(20);
        jPanelRow.add(jTextFieldMac);
        add(jPanelRow, 1);
        showName();
        jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showName();
            }
        });

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


    }

    private void showName() {
        jTextFieldName.setText(jComboBox.getSelectedItem().toString());
        if (jComboBox.getSelectedItem() instanceof Client)
            jTextFieldMac.setText(((Client) jComboBox.getSelectedItem()).getMac());
    }
}
