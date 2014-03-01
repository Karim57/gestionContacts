package view.front;

import java.util.ArrayList;
import model.business.Formation;
import view.IObservable;

public interface IOPrincipale {

    public void setListeFormation1(ArrayList<Formation> liste);

    public void setListeFormation2(ArrayList<Formation> liste);

    public void setListeFormation3(ArrayList<Formation> liste);

    public void setListeFormation4(ArrayList<Formation> liste);

    public void autoriseAjout();

    public void gereActivationComboAjout();

    public int confirmation(String message, String titre, int typeMessage, int icone);

    public void afficheMessage(String message, String titre, int typeMessage);

    public void close();

    public void construitAjout();

    public String getCheminImport();

    public Object[] getInfosAjout();

}
