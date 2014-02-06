package view.manifestation;

import controller.manifestation.ControleurManifestation;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import model.tables.ModeleManifestation;
import view.Observable;

public class ManifestationTable implements Observable {

    private JTable tableauManifestation;
    private ControleurManifestation monControleur;

    public ManifestationTable() {
        this.tableauManifestation = new JTable();
        this.monControleur = new ControleurManifestation(this);
        this.monControleur.remplitTableManifestation();
    }

    public JPanel creerPanelTableManifestation() {

        JPanel jp = new JPanel();
        jp.setLayout(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();

        gbc2.anchor = GridBagConstraints.LINE_END;
        gbc2.weightx = 1;
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        this.tableauManifestation.getTableHeader().setBackground(new Color(245, 246, 247));
        this.tableauManifestation.getTableHeader().setFont(new Font("Arial", 1, 12));
        jp.add(this.tableauManifestation.getTableHeader(), gbc2);

        gbc2.anchor = GridBagConstraints.LINE_END;
        gbc2.weightx = 1;
        gbc2.weighty = 1;
        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.gridx = 0;
        gbc2.gridy = 1;

        jp.add(this.tableauManifestation, gbc2);

        JScrollPane pane = new JScrollPane(this.tableauManifestation);

        jp.add(pane, gbc2);

        return jp;

    }

    @Override
    public void remplitTable(ModeleManifestation modele) {
        this.tableauManifestation.setModel(modele);
    }

}
