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
    private JPanel jpCenter;
    private JScrollPane jScrollPaneCenter;
    private JMenuBar jMenuBar;
    private JMenu jMenuDatei;
    private JMenu jMenuNeu;
    private JMenu jMenuRemove;
    private JMenuItem jmFileUpload;
    private JMenuItem jmSave;
    private JMenuItem jmReload;
    private JMenuItem jmNewClient;
    private JMenuItem jmNewGroup;
    private JMenuItem jmRemClient;
    private JMenuItem jmRemGroup;
    private JTabbedPane jTabbedPane;

    private AllocatePane allocatePaneIgnore;
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
        setMinimumSize(new Dimension(600,450));
        c = getContentPane();
        panel1 = new JPanel();
        jpCenter = new JPanel();
        jpEast = new JPanel();
        jpNorth = new JPanel();
        jpSouth = new JPanel();
        jpWest = new JPanel();
        jMenuBar = new JMenuBar();
        jMenuDatei = new JMenu("Datei");
        jMenuNeu = new JMenu("Neu");
        jMenuRemove = new JMenu("Löschen");
        jmNewClient = new JMenuItem("Client");
        jmRemClient = new JMenuItem("Client");
        jmNewGroup = new JMenuItem("Gruppe");
        jmRemGroup = new JMenuItem("Gruppe");
        jmSave = new JMenuItem("Speichern");
        jmReload = new JMenuItem("Reload");
        jmFileUpload = new JMenuItem("FileUpload");
        jTabbedPane = new JTabbedPane();

        allocatePaneIgnore = new AllocatePane(ClientPool.getInstance().getClientArrayList());
        allocatePaneGroupToClient = new AllocatePane(GroupPool.getInstance().getGroupArrayList(), ClientPool.getInstance().getClientArrayList());
        allocatePaneClientPictures = new AllocatePane(PicturePool.getInstance().getPictureList(), ClientPool.getInstance().getClientArrayList());
        allocatePaneGroupPicture = new AllocatePane(PicturePool.getInstance().getPictureList(), GroupPool.getInstance().getGroupArrayList());

    }

    private void buildWindow() {
        panel1.setLayout(new BorderLayout());
        c.add(panel1);
        panel1.setLayout(new BorderLayout());
        panel1.add(jScrollPaneCenter, BorderLayout.CENTER);
        panel1.add(jpEast, BorderLayout.EAST);
        panel1.add(jpNorth, BorderLayout.NORTH);
        panel1.add(jpSouth, BorderLayout.SOUTH);
        panel1.add(jpWest, BorderLayout.WEST);
        setJMenuBar(jMenuBar);
        jMenuBar.add(jMenuDatei);
        jMenuDatei.add(jmSave);
        jMenuDatei.add(jmReload);
        jMenuBar.add(jMenuNeu);
        jMenuNeu.add(jmNewClient);
        jMenuNeu.add(jmNewGroup);
        jMenuBar.add(jMenuRemove);
        jMenuRemove.add(jmRemClient);
        jMenuRemove.add(jmRemGroup);
        jMenuDatei.add(jmFileUpload);

        jmReload.setToolTipText("Alle Einstellungen werden verworfen");
        jmSave.setToolTipText("Config wird auf FTP-Server geschrieben und überschreibt alte Config");

        jScrollPaneCenter.setViewportView(jpCenter);
        jpCenter.add(jTabbedPane);
        jTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        reload();

    }

    private void initEvents() {
        jmSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileService.writeClients();
                FileService.writeGroups();
            }
        });
        jmReload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileService.readClients();
                FileService.readGroups();
                reload();
            }
        });
        jTabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (jTabbedPane.getSelectedComponent() instanceof AllocatePane) {
                    ((AllocatePane) jTabbedPane.getSelectedComponent()).refreshPane();
                }
            }
        });
        jmNewClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = "";
                String mac = "";
                do {
                    name = JOptionPane.showInputDialog("name of New Client");
                    mac = JOptionPane.showInputDialog("mac of New Client");
                } while (
                        !ClientPool.getInstance().addClient(new Client(name, mac)));
                reload();
            }
        });
        jmNewGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while (!GroupPool.getInstance().addGroup(new Group(JOptionPane.showInputDialog("Groupname")))) ;
                reload();

            }
        });
        jmRemGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GroupPool.getInstance().removeGroup((Group) JOptionPane.showInputDialog(null, "Welche Gruppe willst du löschen", "Löschdialog", JOptionPane.QUESTION_MESSAGE, null, GroupPool.getInstance().getGroupArrayList().toArray(), GroupPool.getInstance().getGroupArrayList().get(0)));
                reload();

            }
        });
        jmRemClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientPool.getInstance().removeClient((Client) JOptionPane.showInputDialog(null, "Welchen Client wills du löschen", "Löschdialog", JOptionPane.QUESTION_MESSAGE, null, ClientPool.getInstance().getClientArrayList().toArray(), ClientPool.getInstance().getClientArrayList().get(0)));
                reload();
            }
        });
        jmFileUpload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setMultiSelectionEnabled(true);
                chooser.showOpenDialog(null);
                File[] files = chooser.getSelectedFiles();
                for (File file : files) {
                    FtpService.getInstance().upload("files/images",file);
                }
                JOptionPane.showMessageDialog(null,"Uploade done");

            }
        });
    }

    public void reload() {

        jTabbedPane.removeAll();
        allocatePaneIgnore = new AllocatePane(ClientPool.getInstance().getClientArrayList());
        allocatePaneGroupToClient = new AllocatePane(GroupPool.getInstance().getGroupArrayList(), ClientPool.getInstance().getClientArrayList());
        allocatePaneClientPictures = new AllocatePane(PicturePool.getInstance().getPictureList(), ClientPool.getInstance().getClientArrayList());
        allocatePaneGroupPicture = new AllocatePane(PicturePool.getInstance().getPictureList(), GroupPool.getInstance().getGroupArrayList());
        jTabbedPane.add("Gruppe bekommt Bilder", allocatePaneGroupPicture);
        jTabbedPane.add("Gruppe Einstellungen",new Edit_Times(Edit_Times.MOD_Gruppe));
        jTabbedPane.add("Gruppen zuordnen", allocatePaneGroupToClient);
        jTabbedPane.add("Client Bilder", allocatePaneClientPictures);
        jTabbedPane.add("Client Einstellungen",new Edit_Times(Edit_Times.MOD_CLIENT));

        jTabbedPane.add("BilderIgnorieren", allocatePaneIgnore);

    }


}