package utils.interfaces;

import utils.BildSettings;

import java.util.ArrayList;

/**
 * Created by Robin on 17.02.2017.
 */
public interface I_GotPictures {
    void addElement(BildSettings bildSettings);

    ArrayList<BildSettings> getPictures();

}
