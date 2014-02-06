package view;

import model.tables.ModeleManifestation;

public interface ManifestationObservable {

    public void remplitTable(ModeleManifestation modele);

    public String getLibelle();

    public void close();

    public void activeButton();

    public void construitAjout();

}
