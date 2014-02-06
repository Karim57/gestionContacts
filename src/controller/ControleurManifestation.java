package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import model.business.Manifestation;
import model.dao.sql.SQLManifestationDAO;
import model.tables.ModeleManifestation;
import view.ManifestationObservable;

public class ControleurManifestation implements ActionListener, DocumentListener {

    ManifestationObservable vue;
    private ModeleManifestation modeleDonneesTable;

    public ControleurManifestation(ManifestationObservable v) {
        this.vue = v;
        this.modeleDonneesTable = new ModeleManifestation();
    }

    public ControleurManifestation() {
    }

    public void remplitTableManifestation() {
        this.modeleDonneesTable = new ModeleManifestation();
        this.vue.remplitTable(this.modeleDonneesTable);
        this.modeleDonneesTable.setDonnees(SQLManifestationDAO.getInstance().readAll());
    }

    private void gereAjout() {
        Manifestation m = new Manifestation(this.vue.getLibelle());
        int id = SQLManifestationDAO.getInstance().create(m);
        m.setIdManif(id);
        this.modeleDonneesTable.addRow(m);
        this.vue.close();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String s = e.getActionCommand();
        if (s.equals("Ajouter")) {
            this.gereAjout();

        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        this.vue.activeButton();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        this.changedUpdate(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        this.changedUpdate(e);
    }

}
