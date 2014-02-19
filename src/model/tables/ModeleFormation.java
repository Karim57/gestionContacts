package model.tables;

import model.business.Formation;

public class ModeleFormation extends ModeleGenerique<Formation> {

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
}
