package utils;

import utils.interfaces.I_GotGroups;
import utils.interfaces.I_GotPictures;

import javax.swing.AbstractListModel;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by robin on 22.01.17.
 */
public class Clients extends AbstractListModel implements I_GotGroups, I_GotPictures {

    private static int number = 0;
    private String id;
    private String name;
    private ArrayList<Gruppe> arrayListGroups;
    private ArrayList<BildSettings> bildIgnoreArrayList;
    private ArrayList<BildSettings> clientSpezielleBilderListe;
    private ArrayList<BildSettings> alleBilderListe;

    public Clients() {
        number++;
        id = "" + number;
        arrayListGroups = new ArrayList<Gruppe>();
        clientSpezielleBilderListe = new ArrayList<BildSettings>();
    }

    public Clients(String name) {
        this();
        this.name = name;
    }

    public int getNumber() {
        return number;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return arrayListGroups.size();
    }

    @Override
    public Gruppe getElementAt(int index) {
        return arrayListGroups.get(index);
    }

    public void listenMergen() {
        alleBilderListe = new ArrayList<BildSettings>();
        for (Gruppe g : arrayListGroups) {
            for (BildSettings gb : g.getGruppenBildArrayList()) {
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
        //todo add method
    }

    @Override
    public ArrayList<Gruppe> getGruppen() {
        return null;
        //todo add method
    }

    @Override
    public void addElement(BildSettings bildSettings) {
//todo add method
    }

    @Override
    public ArrayList<BildSettings> getPictures() {
        return null;
        //todo add method
    }
}
