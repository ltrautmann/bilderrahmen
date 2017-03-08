package utils;

import utils.interfaces.I_GotGroups;

import javax.swing.AbstractListModel;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;

/**
 * Created by Robin on 07.03.2017.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GroupPool extends AbstractListModel implements I_GotGroups {
    @XmlElementWrapper(name = "Groups")
    @XmlElement(name = "Group")
    private ArrayList<Gruppe> gruppeArrayList;

    public GroupPool() {
        this.gruppeArrayList = new ArrayList<>();
    }

    public ArrayList<Gruppe> getGruppeArrayList() {
        return gruppeArrayList;
    }

    public void setGruppeArrayList(ArrayList<Gruppe> gruppeArrayList) {
        this.gruppeArrayList = gruppeArrayList;
    }

    @Override

    public int getSize() {
        return gruppeArrayList.size();
    }

    @Override
    public Object getElementAt(int index) {
        return gruppeArrayList.get(index);
    }

    @Override
    public void addElement(Gruppe gruppe) {
        if (!gruppeArrayList.contains(gruppe)) {
            gruppeArrayList.add(gruppe);
        }
    }

    @Override
    public ArrayList<Gruppe> getGruppen() {
        return gruppeArrayList;
    }

    public Gruppe getGroupByName(String groupname) {
        for (Gruppe g : gruppeArrayList) {
            if (g.getGruppenName().equals(groupname)) {
                return g;
            }
        }
        return null;
    }
}
