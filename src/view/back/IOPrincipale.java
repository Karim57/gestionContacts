package view.back;

import view.IObservable;
import model.business.Contact;

public interface IOPrincipale extends IObservable {

    public String getLibelle();

    public int getActivePane();

    public void construitProfil(Contact c);

    public void filtrer(String[] search);

}