package main;

import model.business.Manifestation;
import model.dao.XMLChemin;
import model.dao.xml.XMLManifestationDAO;
import view.VueFenetrePrincipale;

public class Principale {

    public static void main(String[] args) {
        
       // XMLChemin n = new XMLChemin ();
        Manifestation m = new Manifestation(5, "Porte ouverte");
        Manifestation n = new Manifestation(2, "Grande soirée");
        XMLManifestationDAO xml = new XMLManifestationDAO();
        xml.create(m);
        xml.ajouter(n);
        //xml.create(m);
                
        
       
 
        //new VueFenetrePrincipale();
    }
}
