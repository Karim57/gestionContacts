package view.back;

import view.IObservable;
import java.util.ArrayList;
import model.business.Departement;
import model.business.Enseignant;

public interface IOEnseignant extends IObservable {

    public void setListeDepartement(ArrayList<Departement> liste);

    public void filtrer(String[] search, String dpt);

    public void remplitChamps(Enseignant e);

    public void setListeDepartementAM(ArrayList<Departement> liste);

    public String getNom();

    public String getPrenom();

    public int getSelectedDepartementIndex();

    public Departement getDptFiltre();

    public Departement getDptAjoutModif();

}
