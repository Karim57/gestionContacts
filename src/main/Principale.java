package main;

import model.business.Departement;
import model.business.Manifestation;
import model.dao.xml.XMLDepartementDAO;
import model.dao.xml.XMLManifestationDAO;

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
        
        Departement d1 = new Departement(1, "Informatique");
        Departement d2 = new Departement(2, "GEA");
        Departement d3 = new Departement(3, "Droit");
        Departement d4 = new Departement(4, "Biologie");
        
        XMLDepartementDAO xmlDpt = new XMLDepartementDAO();
        xmlDpt.create(d1);
        xmlDpt.ajouter(d2);
        xmlDpt.ajouter(d3);
        xmlDpt.ajouter(d4);
        xmlDpt.supprimer(d2);
        xmlDpt.modifier(d3, "Physique");
       // xml.create(m);
                
        //new VueFenetrePrincipale();
    }
}
