package view.back;

import java.util.ArrayList;
import model.business.Departement;
import model.business.Formation;
import view.IObservable;

public interface IOFormation extends IObservable {

    public void filtrer(String[] search, String dpt);

    public void setListeDepartement(ArrayList<Departement> liste);

    public void remplitChamps(Formation f);

    public void setListeDepartementAM(ArrayList<Departement> liste);

    public String getLibelle();

    public int getSelectedDepartementIndex();

    public Departement getDptFiltre();

    public Departement getDptAjoutModif();
}
