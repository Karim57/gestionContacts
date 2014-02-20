package view;

import model.business.Manifestation;

public interface IObservable {

    public String getLibelle();

    public void close();

    public void activeButton();

    public void construitAjoutModif();

    public int getLigneSelectionnee();

    public int getActivePane();

    public void remplitChamps(String libelle);

    public void preapreModif();

    public void autoriseAjoutModif();

}
