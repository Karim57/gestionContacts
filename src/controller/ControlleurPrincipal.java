package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import model.dao.sql.SQLDepartementDAO;
import model.dao.sql.SQLFormationDAO;
import model.dao.sql.SQLManifestationDAO;
import model.tables.ModeleDepartement;
import model.tables.ModeleFormation;
import model.tables.ModeleManifestation;
import view.IObservable;

public class ControlleurPrincipal implements ActionListener, DocumentListener, ChangeListener {

    private IObservable vue;
    private ModeleManifestation donneesManifestation;
    private ModeleDepartement donneesDepartement;
    private ModeleFormation donneesFormation;

    public ControlleurPrincipal(IObservable v) {
        this.vue = v;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("Nouveau")) {
            vue.construitAjout(0);
        }

        if (s.equals("Supprimer")) {
            System.out.println(this.donneesManifestation);
            this.donneesManifestation.supprimerElement(vue.getLigneSelectionnee());
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

    public ModeleFormation getDonneesFormation() {
        String[] champsForm = {"Formation", "Département"};
        this.donneesFormation = new ModeleFormation(champsForm);
        this.donneesFormation.setDonnees(SQLFormationDAO.getInstance().readAll());
        return donneesFormation;
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
    public void stateChanged(ChangeEvent e) {
        System.out.println(vue.getActivePane());
    }

}
