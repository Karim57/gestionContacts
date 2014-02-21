package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import model.business.Departement;
import model.business.Manifestation;
import model.dao.sql.SQLContactDAO;
import model.dao.sql.SQLDepartementDAO;
import model.dao.sql.SQLManifestationDAO;
import model.tables.ModeleContact;
import model.tables.ModeleDepartement;
import model.tables.ModeleManifestation;
import view.IObservable;

public class ControleurPrincipal implements ActionListener, DocumentListener, ChangeListener {

    private IObservable vue;
    private ModeleManifestation donneesManifestation;
    private ModeleDepartement donneesDepartement;
    private ModeleContact donneesContact;

    private static int activePane = 0;
    private static int activePaneAjout = 0;

    public ControleurPrincipal(IObservable v) {
        this.vue = v;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if (s.equals("Nouveau")) {
            this.vue.construitAjoutModif();
            activePaneAjout = this.vue.getActivePane();
        }

        if (s.equals("Supprimer")) {
            System.out.println(this.donneesManifestation);
            this.donneesManifestation.supprimerElement(vue.getLigneSelectionnee());
        }

        if (s.equals("Modifier")) {
            this.vue.construitAjoutModif();
            this.vue.preapreModif();
            if (activePane == 0) {
                Manifestation manifestaion = this.donneesManifestation.getValueAt(this.vue.getLigneSelectionnee());
                this.vue.remplitChamps(manifestaion.getLibelleManif());
                activePaneAjout = 0;
            } else if (activePane == 1) {
                Departement departement = this.donneesDepartement.getValueAt(this.vue.getLigneSelectionnee());
                this.vue.remplitChamps(departement.getLibelleDepartement());
                activePaneAjout = 1;
            }
        }

        if (s.equals("submitAjout")) {
            String libelle = this.vue.getLibelle();
            if (activePaneAjout == 0) {
                Manifestation manifestaion = new Manifestation(libelle);
                this.donneesManifestation.ajouterElement(manifestaion);
                SQLManifestationDAO.getInstance().create(manifestaion);

            } else if (activePaneAjout == 1) {
                Departement departement = new Departement(libelle);
                this.donneesDepartement.ajouterElement(departement);
                SQLDepartementDAO.getInstance().create(departement);
            }
            this.vue.close();
        }

        if (s.equals("submitModif")) {
            String libelle = this.vue.getLibelle();
            if (activePaneAjout == 0) {
                Manifestation manifestaion = this.donneesManifestation.getValueAt(this.vue.getLigneSelectionnee());
                manifestaion.setLibelleManif(libelle);
                this.donneesManifestation.setRow(this.vue.getLigneSelectionnee(), manifestaion);
                SQLManifestationDAO.getInstance().update(manifestaion);

            } else if (activePaneAjout == 1) {
                Departement departement = this.donneesDepartement.getValueAt(this.vue.getLigneSelectionnee());
                departement.setLibelleDepartement(libelle);
                this.donneesDepartement.setRow(this.vue.getLigneSelectionnee(), departement);
                SQLDepartementDAO.getInstance().update(departement);
            }
            this.vue.close();
        }

        if (s.equals("Annuler")) {
            this.vue.close();
        }
    }

    public ModeleManifestation getDonneesManifestation() {
        String[] champsManif = {"Liste des manifestations"};
        this.donneesManifestation = new ModeleManifestation(champsManif);
        this.donneesManifestation.setDonnees(SQLManifestationDAO.getInstance().readAll());
        return donneesManifestation;
    }

    public ModeleDepartement getDonneesDepartement() {
        String[] champsDpt = {"Liste des départements"};
        this.donneesDepartement = new ModeleDepartement(champsDpt);
        this.donneesDepartement.setDonnees(SQLDepartementDAO.getInstance().readAll());
        return donneesDepartement;
    }

    public ModeleContact getDonneesContact() {
        String[] champsForm = {"Nom", "Prenom", "Description", "Lieu de la rencontre"};
        this.donneesContact = new ModeleContact(champsForm);
        this.donneesContact.setDonnees(SQLContactDAO.getInstance().readAll());
        return donneesContact;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        this.changedUpdate(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        this.changedUpdate(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        this.vue.autoriseAjoutModif();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        activePane = vue.getActivePane();
    }

}
