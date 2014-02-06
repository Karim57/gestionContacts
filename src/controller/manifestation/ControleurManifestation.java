package controller.manifestation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import model.business.Manifestation;
import model.dao.interfaces.ManifestationDAO;
import model.dao.sql.SQLManifestationDAO;
import model.tables.ModeleManifestation;
import view.Observable;

public class ControleurManifestation implements ActionListener {

    private Observable vue;

    private ModeleManifestation modeleDonneesTable;

    public ControleurManifestation(Observable v) {

        this.vue = v;
    }

    public void remplitTableManifestation() {

        this.modeleDonneesTable = new ModeleManifestation();
        this.vue.remplitTable(this.modeleDonneesTable);
        this.modeleDonneesTable.setDonnees(SQLManifestationDAO.getInstance().readAll());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String s = e.getActionCommand();
        if (s.equals("Nouveau")) {
            System.out.print("ok");
        }
    }
}
