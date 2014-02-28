package view.front;

import java.util.ArrayList;
import model.business.Formation;
import view.IObservable;

public interface IOPrincipale extends IObservable {

    public void setListeFormation(ArrayList<Formation> liste);

    public String fileChooser();

}
