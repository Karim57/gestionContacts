package main;

import model.business.Manifestation;
import model.dao.xml.XMLManifestationDAO;
import view.VueFenetrePrincipale;

public class Principale {

    public static void main(String[] args) {
        
        Manifestation m = new Manifestation(1, "Porte ouverte");
        Manifestation n = new Manifestation(2, "Conférence sur internet");
        Manifestation o = new Manifestation(3, "Réunion de fin d'année");
        XMLManifestationDAO xml = new XMLManifestationDAO();
        xml.create(m);
        xml.ajouter(n);
        xml.ajouter(o);
       // xml.create(m);
                
        
       
 
        //new VueFenetrePrincipale();
    }
}
