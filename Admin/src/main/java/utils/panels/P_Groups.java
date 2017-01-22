package utils.panels;

import utils.Gruppe;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by robin on 22.01.17.
 */
public class P_Groups extends JPanel {
    private JCheckBox[] jCheckBoxes;
    private ArrayList<Gruppe> gruppeArrayList;

    public P_Groups(ArrayList<Gruppe> gruppen) {

        initComponents(gruppen);
    }

    private void initComponents(ArrayList<Gruppe> gruppen) {
        gruppeArrayList = gruppen;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        jCheckBoxes = new JCheckBox[gruppeArrayList.size()];
        for (int i = 0; i < gruppeArrayList.size(); i++) {
            jCheckBoxes[i] = new JCheckBox(gruppeArrayList.get(i).getGruppenName());
            add(jCheckBoxes[i]);
        }
    }

    private void initEvents() {
    }

    public ArrayList getSelectedGruppen() {
        ArrayList<String> gruppeArrayListSelected = new ArrayList();
        for (JCheckBox jc : jCheckBoxes) {
            if (jc.isSelected()) {
                gruppeArrayListSelected.add(jc.getText());
            }
        }
        return gruppeArrayListSelected;
    }

}
