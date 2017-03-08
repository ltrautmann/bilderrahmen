package utils.panels;

import utils.ClientPool;
import utils.GroupPool;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Robin on 23.01.2017.
 */
public class P_Buttons extends JPanel {
    private JButton saveJButton;
    private JButton loadJButton;
    private ClientPool clients;
    private GroupPool groups;

    public P_Buttons() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        saveJButton = new JButton("Save");
        loadJButton = new JButton("Reload");
        add(saveJButton);
        add(loadJButton);
        saveJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                clients.update();
                try {
                    File file = new File("D:\\Schippan\\TEMPBitteLoeschen.xml");
                    JAXBContext jaxbContext = JAXBContext.newInstance(ClientPool.class);
                    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                    // output pretty printed
                    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                    jaxbMarshaller.marshal(clients, file);


                    file = new File("D:\\Schippan\\GroupsLOECHMICH.xml");
                    jaxbContext = JAXBContext.newInstance(GroupPool.class);
                    jaxbMarshaller = jaxbContext.createMarshaller();

                    // output pretty printed
                    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                    jaxbMarshaller.marshal(groups, file);
                    // jaxbMarshaller.marshal(c, System.out);

                } catch (JAXBException f) {
                    f.printStackTrace();
                }

            }

        });
    }

    public GroupPool getGroups() {
        return groups;
    }

    public void setGroups(GroupPool groups) {
        this.groups = groups;
    }

    public void setClients(ClientPool clients) {
        this.clients = clients;
    }


}
