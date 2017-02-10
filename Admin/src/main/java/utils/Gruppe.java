package utils;

import javax.swing.AbstractListModel;
import java.util.ArrayList;

/**
 * Created by robin on 22.01.17.
 */
public class Gruppe extends AbstractListModel<BildSettings> {
    private ArrayList<BildSettings> gruppenBildArrayList;
    private String gruppenName;

    public Gruppe(String gruppenName) {
        this.gruppenName = gruppenName;
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

    public Gruppe(String gruppenName, ArrayList<BildSettings> gruppenBilderListe) {
        this.gruppenName = gruppenName;
        gruppenBildArrayList = gruppenBilderListe;

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
}
