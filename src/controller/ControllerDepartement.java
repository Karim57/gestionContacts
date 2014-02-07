/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import model.business.Departement;
import model.dao.sql.SQLDepartementDAO;
import model.tables.ModeleDepartement;
import view.DepartementObservable;

/**
 *
 * @author Karim
 */
public class ControllerDepartement implements ActionListener, DocumentListener{
    
    DepartementObservable vue;
    private ModeleDepartement modeleDonneesTable;

    public ControllerDepartement(DepartementObservable v) {
        this.vue = v;
        this.modeleDonneesTable = new ModeleDepartement();
    }

    public ControllerDepartement() {
    }

    public void remplitTableDepartement() {
        this.modeleDonneesTable = new ModeleDepartement();
        this.vue.remplitTable(this.modeleDonneesTable);
        this.modeleDonneesTable.setDonnees(SQLDepartementDAO.getInstance().readAll());
    }

    private void gereAjout() {
        Departement d = new Departement(this.vue.getLibelle());
        int id = SQLDepartementDAO.getInstance().create(d);
        d.setIdDepartement(id);
        this.modeleDonneesTable.addRow(d);
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
