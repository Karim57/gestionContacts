package controller.front;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.business.Manifestation;
import model.tables.ModeleContact;
import model.tables.ModeleDepartement;
import model.tables.ModeleFormation;
import view.front.IOPrincipale;

public class ControleurPrincipal implements ActionListener {

    private IOPrincipale vue;
    
    private ModeleContact donneesContact;
    private ModeleDepartement donneesDepartement;
    private ModeleFormation donneesFormation;

    public ControleurPrincipal(IOPrincipale v) {
        this.vue = v;
    }
    
    public ModeleFormation getDonneesFormation() {
        Manifestation m1 = new Manifestation("okokokok");
         Manifestation m2 = new Manifestation("lolilol");
        this.donneesFormation = 
    }

    public ModeleContact getDonneesContact() {
        String[] champsManif = {"Nom", "Prenom", "E-mail", "Description", "Formation 1", "Formation 2", "Formation 3", "Formation 4"};
        this.donneesContact = new ModeleContact(champsManif);
        return donneesContact;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if(s.equals("Nouveau")) {
            this.vue.construitAjoutModif();
        }
     }
}
