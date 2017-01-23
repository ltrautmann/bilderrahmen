package utils.panels;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Created by Robin on 23.01.2017.
 */
public class P_Buttons extends JPanel {
    private JButton saveJButton;
    private JButton loadJButton;

    public P_Buttons() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        saveJButton = new JButton("Save");
        loadJButton = new JButton("Reload");
        add(saveJButton);
        add(loadJButton);
    }


}
