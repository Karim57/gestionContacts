package model.tables;

import model.business.Enseignant;

public class ModeleEnseignant extends ModeleGenerique<Enseignant> {

    public ModeleEnseignant(String[] cols) {
        super(cols);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Enseignant enseignant = this.donnees.get(rowIndex);
        Object o = null;

        switch (columnIndex) {
            case 0:
                o = enseignant.getNomEnseignant();
                break;
            case 1:
                o = enseignant.getPrenomEnseignant();
                break;
            case 2:
                o = enseignant.getDepartement().getLibelleDepartement();
                break;
            case 3:
                o = enseignant.getIdEnseignant();
                break;
        }
        return o;
    }

}
