package view.front;

import java.util.ArrayList;
import model.business.Contact;
import model.business.Departement;
import model.business.Enseignant;
import model.business.Formation;
import view.IObservable;

public interface IOPrincipale {

    public void setListeDpt(ArrayList<Departement> liste);

    public void setListeEns(ArrayList<Enseignant> liste);

    public void setListeFormation1(ArrayList<Formation> liste);

    public void setListeFormation2(ArrayList<Formation> liste);

    public void setListeFormation3(ArrayList<Formation> liste);

    public void setListeFormation4(ArrayList<Formation> liste);

    public void gereComboAjout2();

    public void gereComboAjout3();

    public void gereComboAjout4();

    public int confirmation(String message, String titre, int typeMessage, int icone);

    public void afficheMessage(String message, String titre, int typeMessage);

    public void close(int i);

    public void construitAjout();

    public String getCheminImport();

    public String getNomContact();

    public String getPrenomContact();

    public String getEmailContact();

    public String getEtude1Contact();

    public String getEtude2Contact();

    public String getDescriptionContact();

    public Formation getForm1();

    public Formation getForm2();

    public Formation getForm3();

    public Formation getForm4();

    public void activeAjout();

    public Departement getDptSelected();

    public Enseignant getEnsSelected();

    public void construitProfil(Contact c);

    public int getSelectedContact();

    public void gereButtonsActifs();

    public void activeSauvegarde(boolean b);

    public void afficheConnexion(boolean obligatoire);

    public String fileChooserExport();

    public String fileChooserImport();

    public void viderChampsAjout();

}
