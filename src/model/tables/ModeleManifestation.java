package model.tables;

import model.business.Manifestation;

public class ModeleManifestation extends ModeleGenerique<Manifestation> {

    public ModeleManifestation(String[] cols) {
        super(cols);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Manifestation manifestation = this.donnees.get(rowIndex);
        Object o = null;

        switch (columnIndex) {
            case 1:
                o = manifestation.getIdManif();
                break;
            case 0:
                o = manifestation.getLibelleManif();
        }
        return o;
    }

}
