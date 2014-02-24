package view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.TableRowSorter;
import model.tables.ModeleGenerique;

public class JTableDonnees extends JTable {

    protected ModeleGenerique<?> modele;

    public JTableDonnees(int[] tailles, ModeleGenerique<?> modele) {

        this.modele = modele;
        this.setModel(this.modele);
        this.setGridColor(Color.GRAY);
        this.setColumnSelectionAllowed(false);
        this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        this.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        if (tailles != null) {
            this.setTailles(tailles);
        }
    }

    private void setTailles(int[] tailles) {
        for (int i = 0; i < tailles.length; i++) {
            this.getColumnModel().getColumn(i).setPreferredWidth(tailles[i]);
        }
    }

    protected void filtrer(String... lesFiltres) {
        TableRowSorter sorter = new TableRowSorter(this.modele);
        List<RowFilter<Object, Object>> listeFilters = new ArrayList<RowFilter<Object, Object>>();

        for (String s : lesFiltres) {
            s = s.trim();
            List<RowFilter<Object, Object>> filtreUnMot = new ArrayList<RowFilter<Object, Object>>();
            for (int i = 0; i < this.modele.getColumnCount(); i++) {
                filtreUnMot.add(RowFilter.regexFilter("(?i)" + s, i));
            }
            listeFilters.add(RowFilter.orFilter(filtreUnMot));
        }

        sorter.setRowFilter(RowFilter.andFilter(listeFilters));
        this.setRowSorter(sorter);
    }

    @Override
    public Class<?> getColumnClass(int c) {

        boolean trouve = false;
        int l = 0;
        while (!trouve && l < this.getRowCount()) {
            trouve = this.getValueAt(l, c) != null;
            l++;
        }
        if (!trouve) {
            return String.class;
        }

        return this.getValueAt(l - 1, c).getClass();
    }

}
