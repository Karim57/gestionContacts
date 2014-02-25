package model.dao.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import model.business.Enseignant;

import model.dao.DAOInterface;
import model.dao.XMLChemin;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.*;

public class XMLEnseignantDAO implements DAOInterface<Enseignant> {

    private static XMLEnseignantDAO instance = null;

    public static XMLEnseignantDAO getInstance() {
        if (XMLEnseignantDAO.instance == null) {
            XMLEnseignantDAO.instance = new XMLEnseignantDAO();
        }
        return XMLEnseignantDAO.instance;
    }

    private final XMLChemin chemin;
    private final String nomFichier;
    private Element racine;
    
    /*
    public XMLChemin getChemin() {
    return chemin;
    }*/
    
    public String getNomFicher() {
        return nomFichier;
    }

    public Element getRacine() {
        return racine;
    }

    public XMLEnseignantDAO() {
        this.chemin = new XMLChemin();
        this.nomFichier = "enseignants.xml";
    }

    @Override
    public ArrayList<Enseignant> readAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int create(Enseignant enseignant) {

        racine = new Element("contact");
        Element ens = new Element("enseignant");

        Attribute id = new Attribute("id", Integer.toString(enseignant.getIdEnseignant()));
        ens.setAttribute(id);
        racine.addContent(ens);

        Element nom_ens = new Element("nom_ens");
        nom_ens.setText(enseignant.getNomEnseignant());
        ens.addContent(nom_ens);
        
        Element prenom_ens = new Element("prenom_ens");
        prenom_ens.setText(enseignant.getPrenomEnseignant());
        ens.addContent(prenom_ens);
        /*
        Element dpt_ens = new Element("dpt_ens");
        dpt_ens.setText(enseignant.geti());
        ens.addContent(nom_ens);
        */
        
        
        this.sauvegarde();

        return 1;
    }

    private void sauvegarde() {
        try {
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(this.racine, new FileOutputStream(this.chemin.getChemin() + "/" + this.nomFichier));
        } catch (java.io.IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void ajouter(Enseignant enseignant) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(this.chemin.getChemin() + "/" + this.getNomFicher());

        try {
            Document doc = (Document) builder.build(xmlFile);
            Element root = doc.getRootElement();

            Element ens = new Element("enseignant");
            Attribute id = new Attribute("id", Integer.toString(enseignant.getIdEnseignant()));
            ens.setAttribute(id);
            root.addContent(ens);

            Element nom_ens = new Element("nom_ens");
            nom_ens.setText(enseignant.getNomEnseignant());
            ens.addContent(nom_ens);
            
            Element prenom_ens = new Element("prenom_ens");
            prenom_ens.setText(enseignant.getPrenomEnseignant());
            ens.addContent(prenom_ens);

            XMLOutputter outputter1 = new XMLOutputter(Format.getPrettyFormat());
            outputter1.output(doc, new FileWriter(this.chemin.getChemin() + "/" + this.getNomFicher()));
            
        } catch (IOException | JDOMException e) {
            System.out.println(e.getMessage());
        }
    }

    public void supprimer(Enseignant enseignant) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(this.chemin.getChemin() + "/" + this.getNomFicher());

        try {
            Document doc = (Document) builder.build(xmlFile);
            Element root = doc.getRootElement();

            String numEns = Integer.toString(enseignant.getIdEnseignant());

            for (Element element : root.getChildren()) {

                if (numEns.equals(element.getAttributeValue("id"))) {
                    root.removeContent(element);
                    break;
                }

            }

            XMLOutputter outputter1 = new XMLOutputter(Format.getPrettyFormat());
            outputter1.output(doc, new FileWriter(this.chemin.getChemin() + "/" + this.getNomFicher()));

        } catch (IOException | JDOMException e) {
            System.out.println(e.getMessage());
        }
    }
    
        public void modifier(Enseignant enseignant, String nom, String prenom) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(this.chemin.getChemin() + "/" + this.getNomFicher());

        try {
            Document doc = (Document) builder.build(xmlFile);
            Element root = doc.getRootElement();

            String numEns = Integer.toString(enseignant.getIdEnseignant());

            for (Element element : root.getChildren()) {

                if (numEns.equals(element.getAttributeValue("id"))) {
                     element.getChild("nom_ens").setText(nom);
                     element.getChild("prenom_ens").setText(prenom);
                    break;
                }

            }

            XMLOutputter outputter1 = new XMLOutputter(Format.getPrettyFormat());
            outputter1.output(doc, new FileWriter(this.chemin.getChemin() + "/" + this.getNomFicher()));
            // this.sauvegarde(this.nomFicher);

        } catch (IOException | JDOMException e) {
            System.out.println(e.getMessage());
        }
    }
    /*
     @Override
     public int createList(ArrayList<Manifestation> liste) {
     for (Manifestation manifestation : liste) {
     this.create(manifestation);
     }
     return 0;
     }
     */

    @Override
    public boolean update(Enseignant objetAModifier) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Enseignant objetASupprimer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteList(ArrayList<Enseignant> liste) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Enseignant readById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
