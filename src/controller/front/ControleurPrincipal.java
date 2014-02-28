package controller.front;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import model.business.Departement;
import model.business.Formation;
import model.dao.xml.XMLFormationDAO;
import model.tables.ModeleContact;
import view.front.IOPrincipale;

public class ControleurPrincipal implements ActionListener {

    private IOPrincipale vue;

    String chemin = null;

    private ModeleContact donneesContact;
    private ArrayList<Departement> donneesDepartement;
    private ArrayList<Formation> donneesFormation;

    public ControleurPrincipal(IOPrincipale v) {
        this.vue = v;
    }

    private ArrayList<Formation> getDonneesFormation() {
        this.donneesFormation = new XMLFormationDAO("").readAll();
        return this.donneesFormation;
    }

    public void remplitComboFormation() {
        this.vue.setListeFormation(this.getDonneesFormation());
    }

    public ModeleContact getDonneesContact() {
        String[] champsManif = {"Nom", "Prenom", "E-mail", "Description", "Formation 1", "Formation 2", "Formation 3", "Formation 4"};
        this.donneesContact = new ModeleContact(champsManif);
        return donneesContact;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("Nouveau")) {
            this.vue.construitAjoutModif();
        }

        if (s.equals("Importer")) {
            this.vue.construitAjoutModif();
        }
    }
}
