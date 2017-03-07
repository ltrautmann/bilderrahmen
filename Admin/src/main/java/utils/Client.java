package utils;

import utils.interfaces.I_GotGroups;
import utils.interfaces.I_GotPictures;

import javax.swing.AbstractListModel;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by robin on 22.01.17.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Client extends AbstractListModel implements I_GotGroups, I_GotPictures {


    private static int number = 0;
    private String id;
    private String name;
    @XmlTransient
    private ArrayList<Gruppe> arrayListGroups;                      //Liste0
    @XmlElementWrapper(name = "Groups")
    @XmlElement(name = "Group")
    private ArrayList<String> arrayListGroupNames;
    @XmlElementWrapper(name = "IgnoredPictures")
    @XmlElement(name = "Pictures")
    private ArrayList<BildSettings> bildIgnoreArrayList;            //Liste1
    @XmlElementWrapper(name = "SpecialPictures")
    @XmlElement(name = "Pictures")
    private ArrayList<BildSettings> clientSpezielleBilderListe;     //Liste2
    @XmlTransient
    private ArrayList<BildSettings> alleBilderListe;                //Liste3
    @XmlTransient
    private int angezeigteListe = 0;

    public Client() {
        number++;
        id = "" + number;
        arrayListGroups = new ArrayList<Gruppe>();
        clientSpezielleBilderListe = new ArrayList<BildSettings>();
        bildIgnoreArrayList = new ArrayList<>();
        arrayListGroupNames = new ArrayList<>();
    }

    public Client(String name) {
        this();
        this.name = name;
    }

    public ArrayList<String> getArrayListGroupNames() {
        return arrayListGroupNames;
    }

    public void setArrayListGroupNames(ArrayList<String> arrayListGroupNames) {
        this.arrayListGroupNames = arrayListGroupNames;
    }

    public ArrayList<Gruppe> getArrayListGroups() {
        return arrayListGroups;
    }

    public void setArrayListGroups(ArrayList<Gruppe> arrayListGroups) {
        this.arrayListGroups = arrayListGroups;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public ArrayList<Gruppe> getGruppen() {
        return arrayListGroups;
    }

    public int getAngezeigteListe() {
        return angezeigteListe;
    }

    public void setAngezeigteListe(int angezeigteListe) {
        this.angezeigteListe = angezeigteListe;
    }

    public int getNumber() {
        return number;
    }

    public static void setNumber(int number) {
        Client.number = number;
    }

    public ArrayList<BildSettings> getBildIgnoreArrayList() {
        return bildIgnoreArrayList;
    }

    public void setBildIgnoreArrayList(ArrayList<BildSettings> bildIgnoreArrayList) {
        this.bildIgnoreArrayList = bildIgnoreArrayList;
    }

    public ArrayList<BildSettings> getClientSpezielleBilderListe() {
        return clientSpezielleBilderListe;
    }

    public void setClientSpezielleBilderListe(ArrayList<BildSettings> clientSpezielleBilderListe) {
        this.clientSpezielleBilderListe = clientSpezielleBilderListe;
    }

    public ArrayList<BildSettings> getAlleBilderListe() {
        return alleBilderListe;
    }

    public void setAlleBilderListe(ArrayList<BildSettings> alleBilderListe) {
        this.alleBilderListe = alleBilderListe;
    }

    public Gruppe removeGruppe(String grupprnName) {
        Gruppe v;
        Iterator i = arrayListGroups.iterator();
        while (i.hasNext()) {
            v = (Gruppe) i.next();
            if (v.getGruppenName().equals(grupprnName)) {
                i.remove();
                return v;
            }

        }
        System.out.println("Gruppe nicht gefunden");
        return null;


    }

    public void addGroup(Gruppe g) {
        if (!arrayListGroups.contains(g))
            arrayListGroups.add(g);
        // System.out.println(arrayListGroups.size());
    }

    public void updateList() {
        fireIntervalAdded(this, arrayListGroups.size(), arrayListGroups.size());
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int getSize() {
        switch (angezeigteListe) {
            case 0:
                return arrayListGroups.size();
            case 1:
                return bildIgnoreArrayList.size();
            case 2:
                return clientSpezielleBilderListe.size();
            case 3:
                listenMergen();
                return alleBilderListe.size();
        }
        return 0;
    }

    @Override
    public Object getElementAt(int index) {
        switch (angezeigteListe) {
            case 0:
                return arrayListGroups.get(index);
            case 1:
                return bildIgnoreArrayList.get(index);
            case 2:
                return clientSpezielleBilderListe.get(index);
            case 3:
                return alleBilderListe.get(index);

        }
        return null;
    }

    public void listenMergen() {
        alleBilderListe = new ArrayList<BildSettings>();
        for (Gruppe g : arrayListGroups) {
            for (BildSettings gb : g.getGruppenBildArrayList()) {
                if (!alleBilderListe.contains(gb))
                    alleBilderListe.add(gb);
            }
        }
        for (BildSettings cb : clientSpezielleBilderListe) {
            if (!alleBilderListe.contains(cb)) {
                alleBilderListe.add(cb);
            } else {
                alleBilderListe.remove(cb);
                alleBilderListe.add(cb);
            }
        }
        for (BildSettings ib : bildIgnoreArrayList) {
            alleBilderListe.remove(bildIgnoreArrayList);
        }
    }

    @Override
    public void addElement(Gruppe gruppe) {
        if (!arrayListGroups.contains(gruppe))
            arrayListGroups.add(gruppe);
        fireIntervalAdded(this, arrayListGroups.size(), arrayListGroups.size());
    }

    @Override
    public void addElement(BildSettings bildSettings) {

        if (angezeigteListe == 1) {
            if (!bildIgnoreArrayList.contains(bildSettings))
                bildIgnoreArrayList.add(bildSettings);
            fireIntervalAdded(this, bildIgnoreArrayList.size(), bildIgnoreArrayList.size());
        }
        if (angezeigteListe == 2) {
            if (!clientSpezielleBilderListe.contains(bildSettings))
                clientSpezielleBilderListe.add(bildSettings);
            fireIntervalAdded(this, clientSpezielleBilderListe.size(), clientSpezielleBilderListe.size());
        }
    }

    @Override
    public ArrayList<BildSettings> getPictures() {
        return null;
        //todo add method
    }

    public void update() {
        arrayListGroupNames.clear();
        for (Gruppe g : arrayListGroups) {
            arrayListGroupNames.add(g.getGruppenName());

        }
    }
}
