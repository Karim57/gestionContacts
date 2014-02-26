package model.dao.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import model.business.Departement;

import model.dao.DAOInterface;
import model.dao.XMLChemin;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.*;

public class XMLDepartementDAO implements DAOInterface<Departement> {

    private static XMLDepartementDAO instance = null;

    public static XMLDepartementDAO getInstance() {
        if (XMLDepartementDAO.instance == null) {
            XMLDepartementDAO.instance = new XMLDepartementDAO();
        }
        return XMLDepartementDAO.instance;
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

    public XMLDepartementDAO() {
        this.chemin = new XMLChemin();
        this.nomFichier = "departements.xml";
    }

    @Override
    public ArrayList<Departement> readAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int create(Departement departement) {

        racine = new Element("departements");
        Element dpt = new Element("departement");

        Attribute id = new Attribute("id", Integer.toString(departement.getIdDepartement()));
        dpt.setAttribute(id);
        racine.addContent(dpt);

        Element libelle_dpt = new Element("libelle_dpt");
        libelle_dpt.setText(departement.getLibelleDepartement());
        dpt.addContent(libelle_dpt);
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

    public void ajouter(Departement departement) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(this.chemin.getChemin() + "/" + this.getNomFicher());

        try {
            Document doc = (Document) builder.build(xmlFile);
            Element root = doc.getRootElement();

            Element dpt = new Element("departement");
            Attribute id = new Attribute("id", Integer.toString(departement.getIdDepartement()));
            dpt.setAttribute(id);
            root.addContent(dpt);

            Element libelle_dpt = new Element("libelle_dpt");
            libelle_dpt.setText(departement.getLibelleDepartement());
            dpt.addContent(libelle_dpt);

            XMLOutputter outputter1 = new XMLOutputter(Format.getPrettyFormat());
            outputter1.output(doc, new FileWriter(this.chemin.getChemin() + "/" + this.getNomFicher()));
            
        } catch (IOException | JDOMException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean update(Departement departement) {
         SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(this.chemin.getChemin() + "/" + this.getNomFicher());

        try {
            Document doc = (Document) builder.build(xmlFile);
            Element root = doc.getRootElement();

            String numDpt = Integer.toString(departement.getIdDepartement());

            for (Element element : root.getChildren()) {

                if (numDpt.equals(element.getAttributeValue("id"))) {
                     element.getChild("libelle_dpt").setText(departement.getLibelleDepartement());
                    break;
                }
            }
            
            XMLOutputter outputter1 = new XMLOutputter(Format.getPrettyFormat());
            outputter1.output(doc, new FileWriter(this.chemin.getChemin() + "/" + this.getNomFicher()));
            // this.sauvegarde(this.nomFicher);

        } catch (IOException | JDOMException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Departement departement) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(this.chemin.getChemin() + "/" + this.getNomFicher());

        try {
            Document doc = (Document) builder.build(xmlFile);
            Element root = doc.getRootElement();

            String numDpt = Integer.toString(departement.getIdDepartement());

            for (Element element : root.getChildren()) {

                if (numDpt.equals(element.getAttributeValue("id"))) {
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
    public void deleteList(ArrayList<Departement> liste) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
