package controller.front;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import model.business.Departement;
import model.business.Enseignant;
import model.business.Formation;
import model.business.Manifestation;
import model.dao.xml.XMLDepartementDAO;
import model.dao.xml.XMLEnseignantDAO;
import model.dao.xml.XMLFormationDAO;
import model.dao.xml.XMLManifestationDAO;
import model.tables.ModeleContact;
import view.front.IOPrincipale;

public class ControleurPrincipal implements ActionListener, ItemListener, DocumentListener {

    private IOPrincipale vue;

    String chemin = null;

    private ModeleContact donneesContact;
    private ArrayList<Departement> donneesDepartement;
    private ArrayList<Formation> donneesFormation1;
    private ArrayList<Formation> donneesFormation2;
    private ArrayList<Formation> donneesFormation3;
    private ArrayList<Formation> donneesFormation4;
    private ArrayList<Enseignant> donneesEnseignant;
    private Manifestation manifEnCours;

    public ControleurPrincipal(IOPrincipale v) {
        this.vue = v;
    }

    public void initDonnees() {
        this.donneesDepartement = new XMLDepartementDAO(this.vue.getCheminImport()).readAll();
        this.donneesEnseignant = new XMLEnseignantDAO(this.vue.getCheminImport()).readAll();
        this.donneesFormation1 = new XMLFormationDAO(this.vue.getCheminImport()).readAll();
        this.manifEnCours = new XMLManifestationDAO(this.vue.getCheminImport()).read();
    }

    public void remplitComboFormation1() {
        this.vue.setListeFormation1(this.donneesFormation1);
    }

    public void remplitComboFormation2() {
        this.vue.setListeFormation2(this.donneesFormation2);
    }

    public void remplitComboFormation3() {
        this.vue.setListeFormation3(this.donneesFormation3);
    }

    public void remplitComboFormation4() {
        this.vue.setListeFormation4(this.donneesFormation4);
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
            this.vue.construitAjout();
        }

        if (s.equals("Importer")) {
            this.vue.construitAjout();
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        e.getItemSelectable().
    }
}
