package view;

import java.awt.Color;
import javax.swing.*;
import model.tables.ModeleGenerique;

public class JTableDonnees extends JTable {

    protected ModeleGenerique<?> modele;

    public JTableDonnees(int[] tailles, ModeleGenerique<?> modele) {
        
        this.modele = modele;
        this.setModel(this.modele);
        this.setGridColor(Color.GRAY);
        this.setColumnSelectionAllowed(false);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.getTableHeader().setResizingAllowed(false);
        this.getTableHeader().setReorderingAllowed(false);
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

    public void vide() {
        while (this.modele.getRowCount() != 0) {
            this.modele.supprimerElement(0);
        }
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
