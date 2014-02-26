package main;

import model.business.Contact;
import model.business.Departement;
import model.business.Enseignant;
import model.business.Formation;
import model.business.Manifestation;
import model.dao.xml.XMLContactDAO;
import model.dao.xml.XMLDepartementDAO;
import model.dao.xml.XMLEnseignantDAO;
import model.dao.xml.XMLFormationDAO;
import model.dao.xml.XMLManifestationDAO;

public class Principale {

    public static void main(String[] args) {
        
        Manifestation m1 = new Manifestation(1, "Porte ouverte");
        Manifestation m2 = new Manifestation(2, "Conférence sur internet");
        Manifestation m3 = new Manifestation(3, "Réunion de fin d'année");
        Manifestation m4 = new Manifestation(4, "Rentrée des classes");
        
        XMLManifestationDAO xmlManif = new XMLManifestationDAO();
        xmlManif.create(m1);
        xmlManif.ajouter(m2);
        xmlManif.ajouter(m3);
        xmlManif.ajouter(m4);
        xmlManif.delete(m2);
        xmlManif.modifier(m3, "Reunion annulée");
        
        Departement d1 = new Departement(1, "Informatique");
        Departement d2 = new Departement(2, "GEA");
        Departement d3 = new Departement(3, "Droit");
        Departement d4 = new Departement(4, "Biologie");
        
        XMLDepartementDAO xmlDpt = new XMLDepartementDAO();
        xmlDpt.create(d1);
        xmlDpt.ajouter(d2);
        xmlDpt.ajouter(d3);
        xmlDpt.ajouter(d4);
        xmlDpt.delete(d2);
        xmlDpt.modifier(d3, "Physique");
        
        Enseignant e1 = new Enseignant(1, "Laroche", "Pierre", d1);
        Enseignant e2 = new Enseignant(2, "Weber", "Pierre", d1);
        Enseignant e3 = new Enseignant(3, "Dosne", "Isabelle", d3);
        Enseignant e4 = new Enseignant(4, "Nagel", "Bernard", d1);
        
        XMLEnseignantDAO xmlEns = new XMLEnseignantDAO();
        xmlEns.create(e1);
        xmlEns.ajouter(e2);
        xmlEns.ajouter(e3);
        xmlEns.ajouter(e4);
        xmlEns.delete(e2);
        xmlEns.modifier(e4, "Nagel" , "Cyril");
        
        Formation f1 = new Formation(1, "LPSIL", d1);
        Formation f2 = new Formation(2, "1A", d1);
        Formation f3 = new Formation(3, "2A", d1);
        Formation f4 = new Formation(4, "LPGEA", d2);
        
        XMLFormationDAO xmlForm = new XMLFormationDAO();
        xmlForm.create(f1);
        xmlForm.ajouter(f2);
        xmlForm.ajouter(f3);
        xmlForm.ajouter(f4);
        xmlForm.delete(f2);
        xmlForm.modifier(f4, "LP Management");
        
        Contact contact = new Contact(1,m1,e1);
        XMLContactDAO xmlContact = new XMLContactDAO();
        xmlContact.create(contact,xmlManif, xmlEns);
                
        //new VueFenetrePrincipale();
    }
}
