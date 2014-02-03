package controller.manifestation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class ManifestationControlleurPrincipale implements ActionListener {

    private JFrame vue;

    public ManifestationControlleurPrincipale(JFrame f) {

        this.vue = f;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String s = e.getActionCommand();
        if (s.equals("Nouveau")) {
            System.out.print("ok");
        }
    }
}
