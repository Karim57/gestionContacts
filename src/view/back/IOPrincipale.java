package view.back;

import java.util.List;
import view.IObservable;
import model.business.Contact;
import model.business.Departement;
import model.business.Manifestation;

public interface IOPrincipale extends IObservable {

    public String getLibelle();

    public int getActivePane();

    public void remplitChamps(String libelle);

    public void construitProfil(Contact c);

    public void filtrer(String[] search);

    public void annulerSelectionTables();

    public String fileChooser();

    public String fileChooserImport();

    public void creerPanelRecap(Manifestation manifestation, String date_deb, String date_fin, List<Departement> listeDpt);
}
