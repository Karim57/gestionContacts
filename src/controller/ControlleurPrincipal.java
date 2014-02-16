package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import model.business.Manifestation;
import model.dao.sql.SQLDepartementDAO;
import model.dao.sql.SQLManifestationDAO;
import model.tables.ModeleGenerique;
import model.tables.ModeleManifestation;
import view.IObservable;

public class ControlleurPrincipal implements ActionListener, DocumentListener {

    IObservable vue;
    ModeleGenerique modeleDonneesTables;

    public ControlleurPrincipal(IObservable v) {
        this.vue = v;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("Nouveau")) {
            vue.construitAjout(1);
        }
    }

    public void remplitTableManifestation() {
        String[] test = {"ok"};
        this.modeleDonneesTables = new ModeleManifestation(test);
        this.modeleDonneesTables.setDonnees(SQLManifestationDAO.getInstance().readAll());
    }

    public ModeleGenerique getModele(int quelModel) {

        switch (quelModel) {
            case 0:
                String[] champsManif = {"Liste des manifestations"};
                this.modeleDonneesTables = new ModeleManifestation(champsManif);
                this.modeleDonneesTables.setDonnees(SQLManifestationDAO.getInstance().readAll());
                System.out.println(modeleDonneesTables.getDonnees());

                System.out.println(modeleDonneesTables.toString());
                break;

            case 1:
                String[] champsDepartement = {"Liste des departements"};
                this.modeleDonneesTables = new ModeleManifestation(champsDepartement);
                this.modeleDonneesTables.setDonnees(SQLDepartementDAO.getInstance().readAll());
                break;
        }
        return this.modeleDonneesTables;

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

}
