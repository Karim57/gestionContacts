package model.tables;

import java.util.ArrayList;
import model.business.Departement;
import model.business.Formation;

public class ModeleFormation extends ModeleGenerique<Formation> {

    private ArrayList<Formation> sousListe;

    public ModeleFormation(String[] cols) {
        super(cols);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Formation formation = this.donnees.get(rowIndex);
        Object o = null;

        switch (columnIndex) {
            case 0:
                o = formation.getLibelleFormation();
                break;
            case 1:
                o = formation.getDepartement().getLibelleDepartement();
                break;
            case 2:
                o = formation.getIdFormation();
                break;
        }
        return o;
    }

    public ArrayList<Formation> getSousListe(Departement d) {

        this.sousListe = new ArrayList<Formation>();

        for (Formation f : super.getDonnees()) {
            if (f.getDepartement().equals(d)) {
                this.sousListe.add(f);
            }
        }
        return this.sousListe;
    }
}
