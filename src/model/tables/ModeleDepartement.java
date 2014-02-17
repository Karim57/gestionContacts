package model.tables;

import model.business.Departement;

public class ModeleDepartement extends ModeleGenerique<Departement> {

    public ModeleDepartement(String[] cols) {
        super(cols);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Departement departement = this.donnees.get(rowIndex);
        Object o = null;

        switch (columnIndex) {
            case 0:
                o = departement.getLibelleDepartement();
                break;
            case 1:
                o = departement.getIdDepartement();
        }
        return o;
    }

}
