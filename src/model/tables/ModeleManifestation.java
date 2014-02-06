/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tables;

import java.util.Vector;
import java.util.Collections;
import javax.swing.table.AbstractTableModel;
import model.business.Manifestation;

/**
 *
 * @author Aymane
 */
public class ModeleManifestation extends AbstractTableModel {

    private final String[] COLONNES = {"Manifestations"};

    private Vector<Manifestation> donnees;
    private Vector<Manifestation> aSupprimer;

    public ModeleManifestation() {

        this.donnees = new Vector<Manifestation>();
        this.aSupprimer = new Vector<Manifestation>();
    }

    public void setDonnees(Vector<Manifestation> donnees) {

        this.donnees = donnees;
        this.fireTableDataChanged();
    }

    public Vector<Manifestation> getDonnees() {
        return this.donnees;
    }

    public Vector<Manifestation> getElementsASupprimer() {
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

        Manifestation m = this.donnees.get(rowIndex);
        if (columnIndex == 0) {
            return m.getLibelleManif();
        }
        return false;
    }

    public Manifestation getValueAt(int row) {

        return this.donnees.get(row);
    }

    public void setRow(int l, Manifestation m) {

        this.donnees.set(l, m);

        this.tri();

        // Le composant JTable est prévenu des changements
        this.fireTableDataChanged();
    }

    public void addRow(Manifestation m) {

        this.donnees.add(m);

        this.tri();

        this.fireTableDataChanged();
    }

    public void removeRow(int row) {

        Manifestation m = this.getValueAt(row);
        this.aSupprimer.add(m);
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
