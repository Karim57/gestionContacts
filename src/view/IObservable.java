package view;

public interface IObservable {

    public String getLibelle();

    public String getSearch();

    public void close();

    public void gereButtonsActifs();

    public void construitAjoutModif();

    public void afficheAjoutModif();

    public int getLigneSelectionnee();

    public int[] getLesLignesSelectionnee();

    public int getActivePane();

    public void remplitChamps(String libelle);

    public void prepareModif();

    public void autoriseAjoutModif();

    public void filtrer(String[] search);

    public int confirmation(String message, String titre, int typeMessage, int icone);

    public void afficheErreur(String message, String titre, int typeMessage);

}
