package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.ManifestationObservable;

public class ControlleurPrincipal implements ActionListener {

    ManifestationObservable vue;

    public ControlleurPrincipal(ManifestationObservable v) {
        this.vue = v;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("Nouveau")) {
            vue.construitAjout();
        }
    }

}
