package utils.panels;

import utils.Client;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Robin on 23.01.2017.
 */
public class P_Buttons extends JPanel {
    private JButton saveJButton;
    private JButton loadJButton;

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }
    private ArrayList<Client> clients;

    public P_Buttons() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        saveJButton = new JButton("Save");
        loadJButton = new JButton("Reload");
        add(saveJButton);
        add(loadJButton);
        saveJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int i = 0;
                for(Client c : clients)
                try {

                    i++;
                    File file = new File("D:\\Schippan\\TEMPBitteLoeschen"+i+".xml");
                    JAXBContext jaxbContext = JAXBContext.newInstance(Client.class);
                    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                    // output pretty printed
                    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                    jaxbMarshaller.marshal(c, file);
                    jaxbMarshaller.marshal(c, System.out);

                } catch (JAXBException f) {
                    f.printStackTrace();
                }

            }

        });
    }


}
