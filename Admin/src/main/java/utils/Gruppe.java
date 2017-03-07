package utils;

import utils.interfaces.I_GotPictures;

import javax.swing.AbstractListModel;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;

/**
 * Created by robin on 22.01.17.
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Gruppe extends AbstractListModel implements I_GotPictures {
    private String gruppenName;
    @XmlElementWrapper(name = "GroupPictures")
    @XmlElement(name = "Picture")
    private ArrayList<BildSettings> gruppenBildArrayList;

    public Gruppe() {
    }

    public Gruppe(String gruppenName) {
        this.gruppenName = gruppenName;
        gruppenBildArrayList = new ArrayList<>();
    }

    public Gruppe(String gruppenName, ArrayList<BildSettings> gruppenBilderListe) {
        this.gruppenName = gruppenName;
        gruppenBildArrayList = gruppenBilderListe;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gruppe gruppe = (Gruppe) o;

        if (gruppenBildArrayList != null ? !gruppenBildArrayList.equals(gruppe.gruppenBildArrayList) : gruppe.gruppenBildArrayList != null)
            return false;
        return gruppenName != null ? gruppenName.equals(gruppe.gruppenName) : gruppe.gruppenName == null;
    }

    @Override
    public int hashCode() {
        int result = gruppenBildArrayList != null ? gruppenBildArrayList.hashCode() : 0;
        result = 31 * result + (gruppenName != null ? gruppenName.hashCode() : 0);
        return result;
    }

    public ArrayList<BildSettings> getGruppenBildArrayList() {
        return gruppenBildArrayList;
    }

    public void setGruppenBildArrayList(ArrayList<BildSettings> gruppenBildArrayList) {
        this.gruppenBildArrayList = gruppenBildArrayList;
    }

    public String getGruppenName() {
        return gruppenName;
    }

    public void setGruppenName(String gruppenName) {
        gruppenName = gruppenName;
    }

    @Override
    public String toString() {
        return gruppenName;
    }

    @Override
    public int getSize() {
        return gruppenBildArrayList.size();
    }

    @Override
    public BildSettings getElementAt(int index) {
        return gruppenBildArrayList.get(index);
    }


    @Override
    public void addElement(BildSettings bildSettings) {
        if (!gruppenBildArrayList.contains(bildSettings))
            gruppenBildArrayList.add(bildSettings);
        fireIntervalAdded(this, gruppenBildArrayList.size(), gruppenBildArrayList.size());
    }

    @Override
    public ArrayList<BildSettings> getPictures() {
        return gruppenBildArrayList;
    }
}
