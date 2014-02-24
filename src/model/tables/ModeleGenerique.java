package model.tables;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.table.AbstractTableModel;

public abstract class ModeleGenerique<T extends Comparable<? super T>> extends AbstractTableModel {

    private final String[] COLONNES;

    protected ArrayList<T> donnees;
    protected ArrayList<T> aSupprimer;

    public ModeleGenerique(String[] colonnes) {
        this.COLONNES = colonnes;
        this.aSupprimer = new ArrayList<T>();
    }

    public void setDonnees(ArrayList<T> donnees) {
        this.donnees = donnees;
        this.tri();
        this.fireTableDataChanged();
    }

    public ArrayList<T> getDonnees() {
        return this.donnees;
    }

    public ArrayList<T> getElementsASupprimer() {
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

    public T getValueAt(int row) {
        return this.donnees.get(row);
    }

    private void tri() {
        Collections.sort(this.donnees);
        this.fireTableDataChanged();
    }

    public void ajouterElement(T element) {
        this.donnees.add(element);
        this.fireTableDataChanged();
        this.tri();
    }

    public void setRow(int l, T e) {

        this.donnees.set(l, e);
        this.tri();
        this.fireTableDataChanged();
    }

    public void supprimerElement(T elementASupprimer) {
        this.aSupprimer.add(elementASupprimer);
    }

    public void confirmerSuppression() {
        for (T obj : this.aSupprimer) {
            this.donnees.remove(obj);
        }
        this.fireTableDataChanged();
    }

}
