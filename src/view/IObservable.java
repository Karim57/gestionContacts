package view;

import model.business.Manifestation;

public interface IObservable {

    public String getLibelle();

    public void close();

    public void activeButton();

    public void construitAjout(int ajoutOuModif);

    public int getLigneSelectionnee();

    public void remplitChamps(Manifestation manifestation);

    public int getActivePane();

}
