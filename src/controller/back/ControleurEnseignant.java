package controller.back;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.business.Departement;
import model.dao.sql.SQLDepartementDAO;
import model.dao.sql.SQLEnseignantDAO;
import model.tables.ModeleEnseignant;
import view.back.IOEnseignant;

public class ControleurEnseignant implements ActionListener, DocumentListener, ListSelectionListener, ItemListener {

    private IOEnseignant vue;
    private ModeleEnseignant donneesEnseignant;

    private String[] searchNomPrenom = {""};
    private String searchDpt = "";

    public ControleurEnseignant(IOEnseignant v) {
        this.vue = v;
    }

    public ModeleEnseignant getDonneesEnseignant() {
        String[] champsForm = {"Nom", "Prenom", "Département"};
        this.donneesEnseignant = new ModeleEnseignant(champsForm);
        this.donneesEnseignant.setDonnees(SQLEnseignantDAO.getInstance().readAll());

        return donneesEnseignant;
    }

    public void remplitComboDpt() {
        this.vue.setListeDepartement(SQLDepartementDAO.getInstance().readAll());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        changedUpdate(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        changedUpdate(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {

        searchNomPrenom = this.vue.getSearch().split(" ");
        this.vue.filtrer(this.searchNomPrenom, this.searchDpt);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        if (e.getStateChange() == ItemEvent.SELECTED) {
            this.searchDpt = this.vue.getDptSelected().toString();
            this.vue.filtrer(this.searchNomPrenom, this.searchDpt);
        }
    }

}
