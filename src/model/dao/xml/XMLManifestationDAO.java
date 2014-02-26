package model.dao.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import model.business.Manifestation;
import model.dao.DAOInterface;
import model.dao.XMLChemin;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.*;

public class XMLManifestationDAO implements DAOInterface<Manifestation> {

    static XMLManifestationDAO instance = null;

    public static XMLManifestationDAO getInstance() {
        if (XMLManifestationDAO.instance == null) {
            XMLManifestationDAO.instance = new XMLManifestationDAO();
        }
        return XMLManifestationDAO.instance;
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

    public XMLManifestationDAO() {
        this.chemin = new XMLChemin();
        this.nomFichier = "manifestations.xml";
    }

    @Override
    public ArrayList<Manifestation> readAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int create(Manifestation manifestation) {

        racine = new Element("manifestations");
        Element manif = new Element("manifestation");

        Attribute id = new Attribute("id", Integer.toString(manifestation.getIdManif()));
        manif.setAttribute(id);
        racine.addContent(manif);

        Element libelle_manif = new Element("libelle_manif");
        libelle_manif.setText(manifestation.getLibelleManif());
        manif.addContent(libelle_manif);
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

    public void ajouter(Manifestation manifestation) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(this.chemin.getChemin() + "/" + this.getNomFicher());

        try {
            Document doc = (Document) builder.build(xmlFile);
            Element root = doc.getRootElement();

            Element manif = new Element("manifestation");
            Attribute id = new Attribute("id", Integer.toString(manifestation.getIdManif()));
            manif.setAttribute(id);
            root.addContent(manif);

            Element libelle_manif = new Element("libelle_manif");
            libelle_manif.setText(manifestation.getLibelleManif());
            manif.addContent(libelle_manif);

            XMLOutputter outputter1 = new XMLOutputter(Format.getPrettyFormat());
            outputter1.output(doc, new FileWriter(this.chemin.getChemin() + "/" + this.getNomFicher()));

        } catch (IOException | JDOMException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean update(Manifestation manifestation) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(this.chemin.getChemin() + "/" + this.getNomFicher());

        try {
            Document doc = (Document) builder.build(xmlFile);
            Element root = doc.getRootElement();

            String numManif = Integer.toString(manifestation.getIdManif());

            for (Element element : root.getChildren()) {

                if (numManif.equals(element.getAttributeValue("id"))) {
                    element.getChild("libelle_manif").setText(manifestation.getLibelleManif());
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
    public boolean delete(Manifestation manifestation) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(this.chemin.getChemin() + "/" + this.getNomFicher());

        try {
            Document doc = (Document) builder.build(xmlFile);
            Element root = doc.getRootElement();

            String numManif = Integer.toString(manifestation.getIdManif());

            for (Element element : root.getChildren()) {

                if (numManif.equals(element.getAttributeValue("id"))) {
                    root.removeContent(element);
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
    public void deleteList(ArrayList<Manifestation> liste) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
