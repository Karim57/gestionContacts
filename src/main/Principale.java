package main;

import javax.swing.JFrame;
import model.business.Contact;
import model.business.Manifestation;
import model.dao.xml.XMLManifestationDAO;
import view.VueFenetrePrincipale;

public class Principale {

    public static void main(String[] args) {
        
        Manifestation m = new Manifestation(5, "Porte ouverte");
        XMLManifestationDAO test=new XMLManifestationDAO();
        test.create(m);
       //test.afficher(, null);
        
        //new VueFenetrePrincipale();
    }
}
