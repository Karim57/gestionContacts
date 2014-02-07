/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.tables;

import java.util.Collections;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import model.business.Departement;

/**
 *
 * @author Karim
 */
public class ModeleDepartement extends AbstractTableModel{

    private final String[] COLONNES = {"Departements"};

    private Vector<Departement> donnees;
    private Vector<Departement> aSupprimer;

    public ModeleDepartement() {

        this.donnees = new Vector<Departement>();
        this.aSupprimer = new Vector<Departement>();
    }

    public void setDonnees(Vector<Departement> donnees) {

        this.donnees = donnees;
        this.tri();
        this.fireTableDataChanged();
    }

    public Vector<Departement> getDonnees() {
        return this.donnees;
    }

    public Vector<Departement> getElementsASupprimer() {
        return this.aSupprimer;
    }

    public void videElementsASupprimer() {
        this.aSupprimer.clear();
    }

    @Override
    public int getRowCount() {
        if (this.donnees == null) {
            return 0;
        }
        return this.donnees.size();
    }

    @Override
    public int getColumnCount() {
        return this.COLONNES.length;
    }

    @Override
    public String getColumnName(int colIndex) {
        return this.COLONNES[colIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Departement d = this.donnees.get(rowIndex);
        if (columnIndex == 0) {
            return d.getLibelleDepartement();
        }
        return false;
    }

    public Departement getValueAt(int row) {

        return this.donnees.get(row);
    }

    public void setRow(int l, Departement d) {

        this.donnees.set(l, d);

        this.tri();

        // Le composant JTable est prévenu des changements
        this.fireTableDataChanged();
    }

    public void addRow(Departement d) {

        this.donnees.add(d);

        this.tri();

        this.fireTableDataChanged();
    }

    public void removeRow(int row) {

        Departement d = this.getValueAt(row);
        this.aSupprimer.add(d);
        this.donnees.remove(row);

        this.fireTableRowsDeleted(row, row);
    }

    /**
     * Tri de la liste des étudiants
     */
    private void tri() {
        Collections.sort(this.donnees);
        this.fireTableDataChanged();
    }
}
