package view.front;

import java.util.ArrayList;
import model.business.Formation;
import view.IObservable;

public interface IOPrincipale {

    public void setListeFormation(ArrayList<Formation> liste);

    public void autoriseAjout();

    public int confirmation(String message, String titre, int typeMessage, int icone);

    public void afficheMessage(String message, String titre, int typeMessage);

    public void close();

    public void construitAjout();

    public String getCheminImport();

    public Object[] getInfosAjout();

}
