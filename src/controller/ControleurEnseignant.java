package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import model.dao.sql.SQLEnseignantDAO;
import model.tables.ModeleEnseignant;
import view.IOEnseignant;
import view.IObservable;

public class ControleurEnseignant implements ActionListener, DocumentListener {

    private IOEnseignant vue;
    private ModeleEnseignant donneesEnseignant;

    public ControleurEnseignant(IOEnseignant v) {
        this.vue = v;
    }

    public ModeleEnseignant getDonneesEnseignant() {
        String[] champsForm = {"Nom", "Prenom", "Département"};
        this.donneesEnseignant = new ModeleEnseignant(champsForm);
        if (this.vue.getIdDpt() == -1) {
            this.donneesEnseignant.setDonnees(SQLEnseignantDAO.getInstance().readAll());
        } else {
            this.donneesEnseignant.setDonnees(SQLEnseignantDAO.getInstance().readAllByDpt(this.vue.getIdDpt()));
        }

        return donneesEnseignant;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
