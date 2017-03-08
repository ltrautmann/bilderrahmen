package utils;

import utils.interfaces.I_GotPictures;

import javax.swing.AbstractListModel;
import java.util.ArrayList;

/**
 * Created by Robin on 10.02.2017.
 */
public class BilderPool extends AbstractListModel implements I_GotPictures {

    private ArrayList<BildSettings> bildArrayList;

    public BilderPool() {
        bildArrayList = new ArrayList<BildSettings>();

    }

    public void addBild(String pfad) {
        bildArrayList.add(new BildSettings(pfad, 10));
    }



    @Override
    public int getSize() {
        return bildArrayList.size();
    }

    @Override
    public Object getElementAt(int index) {
        return bildArrayList.get(index);
    }

    @Override
    public void addElement(BildSettings bildSettings) {
        //todo
    }

    @Override
    public ArrayList<BildSettings> getPictures() {
        //todo
        return null;
    }
}
