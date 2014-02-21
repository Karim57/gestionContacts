package main;

import javax.swing.JFrame;
import javax.swing.JLabel;
import model.business.Contact;
import view.VueEnseignants;
import view.VueFenetrePrincipale;

public class Principale {

    public static void main(String[] args) {
        VueFenetrePrincipale vuePrincipale = new VueFenetrePrincipale();
        VueEnseignants vueEnseignants = new VueEnseignants(1);

    }
}
