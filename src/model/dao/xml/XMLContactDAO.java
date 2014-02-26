package model.dao.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import model.business.Contact;

import model.dao.DAOInterface;
import model.dao.XMLChemin;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.*;

public class XMLContactDAO implements DAOInterface<Contact> {

    private static XMLContactDAO instance = null;

    public static XMLContactDAO getInstance() {
        if (XMLContactDAO.instance == null) {
            XMLContactDAO.instance = new XMLContactDAO();
        }
        return XMLContactDAO.instance;
    }

    private final XMLChemin chemin;
    private final String nomFichier;
    private Element racine;
        
    public String getNomFicher() {
        return nomFichier;
    }

    public Element getRacine() {
        return racine;
    }

    public XMLContactDAO() {
        this.chemin = new XMLChemin();
        this.nomFichier = "contacts.xml";
    }

    @Override
    public ArrayList<Contact> readAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int create(Contact objetAAjouter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int create(Contact contact, XMLManifestationDAO manifDAO, XMLEnseignantDAO ensDAO ) {

        racine = new Element("contacts");
        Element cont = new Element("contact");

        Attribute id = new Attribute("id", Integer.toString(contact.getIdContact()));
        cont.setAttribute(id);
        racine.addContent(cont);
          
        manifDAO.create(contact.getManifestation());
        ensDAO.create(contact.getEnseignant());
        
        Element nom = new Element("nom");
        nom.setText(contact.getNomContact());
        cont.addContent(nom);

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

    public void ajouter(Contact contact) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(this.chemin.getChemin() + "/" + this.getNomFicher());

        try {
            Document doc = (Document) builder.build(xmlFile);
            Element root = doc.getRootElement();

            Element cont = new Element("contact");
            Attribute id = new Attribute("id", Integer.toString(contact.getIdContact()));
            cont.setAttribute(id);
            root.addContent(cont);

           

            XMLOutputter outputter1 = new XMLOutputter(Format.getPrettyFormat());
            outputter1.output(doc, new FileWriter(this.chemin.getChemin() + "/" + this.getNomFicher()));
            
        } catch (IOException | JDOMException e) {
            System.out.println(e.getMessage());
        }
    }
    /*
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
    }*/
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
    public boolean update(Contact objetAModifier) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Contact contact) {
         SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(this.chemin.getChemin() + "/" + this.getNomFicher());

        try {
            Document doc = (Document) builder.build(xmlFile);
            Element root = doc.getRootElement();

            String numContact = Integer.toString(contact.getIdContact());

            for (Element element : root.getChildren()) {

                if (numContact.equals(element.getAttributeValue("id"))) {
                    root.removeContent(element);
                    break;
                }

            }

            XMLOutputter outputter1 = new XMLOutputter(Format.getPrettyFormat());
            outputter1.output(doc, new FileWriter(this.chemin.getChemin() + "/" + this.getNomFicher()));

        } catch (IOException | JDOMException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void deleteList(ArrayList<Contact> liste) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Contact readById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Contact objetAModifier, String libelle) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

