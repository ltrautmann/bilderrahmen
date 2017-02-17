package utils;

import sun.swing.BakedArrayList;

import javax.swing.AbstractListModel;
import java.util.ArrayList;

/**
 * Created by Robin on 10.02.2017.
 */
public class BilderPool extends AbstractListModel {

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

}
