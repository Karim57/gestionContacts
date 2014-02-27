package view.back;

import view.IObservable;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import model.business.Departement;
import model.business.Enseignant;

public interface IOEnseignant extends IObservable {

    public void setListeDepartement(ArrayList<Departement> liste);

    public Departement getDptSelected();

    public void filtrer(String[] search, String dpt);

    public int getSelectedDepartement();

    public void remplitChamps(Enseignant e);

}
