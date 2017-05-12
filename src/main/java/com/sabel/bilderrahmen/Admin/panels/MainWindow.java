package com.sabel.bilderrahmen.Admin.panels;

import com.sabel.bilderrahmen.Admin.Client;
import com.sabel.bilderrahmen.Admin.Group;
import com.sabel.bilderrahmen.Admin.resources.ClientPool;
import com.sabel.bilderrahmen.Admin.resources.GroupPool;
import com.sabel.bilderrahmen.Admin.resources.PicturePool;
import com.sabel.bilderrahmen.Admin.services.FileService;
import com.sabel.bilderrahmen.Admin.services.FtpService;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * Created by robin on 19.04.17.
 */
public class MainWindow extends JFrame {
    private Container c;
    private JPanel panel1;
    private JPanel jpNorth;
    private JPanel jpSouth;
    private JPanel jpWest;
    private JPanel jpEast;
    private JScrollPane jScrollPaneCenter;
    private JMenuBar jMenuBar;
    private JMenu jMenuDatei;
    private JMenu jMenuNeu;
    private JMenu jMenuRemove;
    private JMenuItem jmFileUpload;
    private JMenuItem jmSave;
    private JMenuItem jmReload;
    private JMenuItem jmNewClient;
    private JMenuItem jmAcceptClient;
    private JMenuItem jmNewGroup;
    private JMenuItem jmRemClient;
    private JMenuItem jmRemGroup;
    private JTabbedPane jTabbedPane;
    private AllocatePane allocatePaneGroupToClient;
    private AllocatePane allocatePaneClientPictures;
    private AllocatePane allocatePaneGroupPicture;


    public MainWindow() throws HeadlessException {

        initComponents();
        buildWindow();
        initEvents();
        pack();
        setVisible(true);
    }

    private void initComponents() {
        jScrollPaneCenter = new JScrollPane();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Admin-Bilderrahmen");
        setMinimumSize(new Dimension(600, 450));
        c = getContentPane();
        panel1 = new JPanel();
        jpEast = new JPanel();
        jpNorth = new JPanel();
        jpSouth = new JPanel();
        jpWest = new JPanel();
        jMenuBar = new JMenuBar();
        jMenuDatei = new JMenu("Datei");
        jMenuNeu = new JMenu("Neu");
        jMenuRemove = new JMenu("Löschen");
        jmNewClient = new JMenuItem("Client");
        jmAcceptClient = new JMenuItem("AcceptClient");
        jmRemClient = new JMenuItem("Client");
        jmNewGroup = new JMenuItem("Gruppe");
        jmRemGroup = new JMenuItem("Gruppe");
        jmSave = new JMenuItem("Speichern");
        jmReload = new JMenuItem("Reload");
        jmFileUpload = new JMenuItem("FileUpload");
        jTabbedPane = new JTabbedPane();

        allocatePaneGroupToClient = new AllocatePane(GroupPool.getInstance().getGroupArrayList(), ClientPool.getInstance().getClientArrayList());
        allocatePaneClientPictures = new AllocatePane(PicturePool.getInstance().getPictureList(), ClientPool.getInstance().getClientArrayList());
        allocatePaneGroupPicture = new AllocatePane(PicturePool.getInstance().getPictureList(), GroupPool.getInstance().getGroupArrayList());

    }

    private void buildWindow() {
        panel1.setLayout(new BorderLayout());

        c.add(jScrollPaneCenter, BorderLayout.CENTER);
        c.add(jpEast, BorderLayout.EAST);
        c.add(jpNorth, BorderLayout.NORTH);
        c.add(jpSouth, BorderLayout.SOUTH);
        c.add(jpWest, BorderLayout.WEST);
        setJMenuBar(jMenuBar);
        jMenuBar.add(jMenuDatei);
        jMenuDatei.add(jmSave);
        jMenuDatei.add(jmReload);
        jMenuBar.add(jMenuNeu);
        jMenuNeu.add(jmAcceptClient);
        jMenuNeu.add(jmNewClient);
        jMenuNeu.add(jmNewGroup);
        jMenuBar.add(jMenuRemove);
        jMenuRemove.add(jmRemClient);
        jMenuRemove.add(jmRemGroup);
        jMenuDatei.add(jmFileUpload);

        jmReload.setToolTipText("Alle Einstellungen werden verworfen");
        jmSave.setToolTipText("Config wird auf FTP-Server geschrieben und überschreibt alte Config");

        jScrollPaneCenter.setViewportView(jTabbedPane);
        reload();

    }

    private void reload() {

        jTabbedPane.removeAll();
        allocatePaneGroupToClient = new AllocatePane(GroupPool.getInstance().getGroupArrayList(), ClientPool.getInstance().getClientArrayList());
        allocatePaneClientPictures = new AllocatePane(PicturePool.getInstance().getPictureList(), ClientPool.getInstance().getClientArrayList());
        allocatePaneGroupPicture = new AllocatePane(PicturePool.getInstance().getPictureList(), GroupPool.getInstance().getGroupArrayList());
        jTabbedPane.add("Gruppe bekommt Bilder", allocatePaneGroupPicture);
        jTabbedPane.add("Gruppe Einstellungen", new Edit_Times(Edit_Times.MOD_Gruppe));
        jTabbedPane.add("Gruppen zuordnen", allocatePaneGroupToClient);
        jTabbedPane.add("Client Bilder", allocatePaneClientPictures);
        jTabbedPane.add("Client Einstellungen", new EditClient());
        jTabbedPane.add("BilderIgnorieren", new IgnorPicPane());

    }

    private void initEvents() {
        jmAcceptClient.addActionListener(e -> {
            new AcceptClient().addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    reload();
                }
            });
        });

        jmSave.addActionListener(e -> {
            FileService.writeClients();
            FileService.writeGroups();
        });
        jmReload.addActionListener(e -> {
            FileService.readClients();
            FileService.readGroups();
            FileService.readPictures();
            reload();
        });
        jTabbedPane.addChangeListener(e -> {
            if (jTabbedPane.getSelectedComponent() instanceof AllocatePane) {
                ((AllocatePane) jTabbedPane.getSelectedComponent()).refreshPane();
            }
        });
        jmNewClient.addActionListener(e -> {
            String name ,mac;
            do {
                name = JOptionPane.showInputDialog("name of New Client");
                mac = JOptionPane.showInputDialog("mac of New Client");
            } while (
                    !ClientPool.getInstance().addClient(new Client(name, mac)));
            reload();
        });
        jmNewGroup.addActionListener(e -> {
            while (!GroupPool.getInstance().addGroup(new Group(JOptionPane.showInputDialog("Groupname"))));
            reload();

        });
        jmRemGroup.addActionListener(e -> {
            GroupPool.getInstance().removeGroup((Group) JOptionPane.showInputDialog(null, "Welche Gruppe willst du löschen", "Löschdialog", JOptionPane.QUESTION_MESSAGE, null, GroupPool.getInstance().getGroupArrayList().toArray(), GroupPool.getInstance().getGroupArrayList().get(0)));
            reload();

        });
        jmRemClient.addActionListener(e -> {
            ClientPool.getInstance().removeClient((Client) JOptionPane.showInputDialog(null, "Welchen Client wills du löschen", "Löschdialog", JOptionPane.QUESTION_MESSAGE, null, ClientPool.getInstance().getClientArrayList().toArray(), ClientPool.getInstance().getClientArrayList().get(0)));
            reload();
        });
        jmFileUpload.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setMultiSelectionEnabled(true);
            chooser.showOpenDialog(null);
            File[] files = chooser.getSelectedFiles();
            for (File file : files) {
                FtpService.getInstance().upload("files/images", file);
            }
            JOptionPane.showMessageDialog(null, "Uploade done");
            FileService.readPictures();
            reload();

        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int speichern = JOptionPane.showConfirmDialog(null, "Änderungen speichern?", "Speichern?", JOptionPane.YES_NO_OPTION);
                if (speichern == JOptionPane.YES_OPTION) {
                    FileService.writeClients();
                    FileService.writeGroups();
                }
            }
        });
        reload();

    }


}