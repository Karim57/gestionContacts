package main;

import model.business.Manifestation;
import model.dao.xml.XMLManifestationDAO;
import view.VueFenetrePrincipale;

public class Principale {

    public static void main(String[] args) {
        
        Manifestation m1 = new Manifestation(1, "Porte ouverte");
        Manifestation m2 = new Manifestation(2, "Conférence sur internet");
        Manifestation m3 = new Manifestation(3, "Réunion de fin d'année");
        Manifestation m4 = new Manifestation(4, "Rentrée des classes");
        
        XMLManifestationDAO xml = new XMLManifestationDAO();
        xml.create(m1);
        xml.ajouter(m2);
        xml.ajouter(m3);
        xml.ajouter(m4);
        xml.supprimer(m2);
        xml.modifier(m3, "Reunion annulée");
       // xml.create(m);
                
        
       
 
        //new VueFenetrePrincipale();
    }
}
