package model.dao.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.business.Manifestation;
import model.dao.DAOInterface;
import model.dao.XMLChemin;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.*;

public class XMLManifestationDAO implements DAOInterface<Manifestation> {

    private final XMLChemin chemin;
    private final String NOM_FICHIER = "manifestations.xml";
    private Element racine;
    static XMLManifestationDAO instance = null;

    public static XMLManifestationDAO getInstance() {
        if (XMLManifestationDAO.instance == null) {
            XMLManifestationDAO.instance = new XMLManifestationDAO();
        }
        return XMLManifestationDAO.instance;
    }

    public String getNomFicher() {
        return NOM_FICHIER;
    }

    public Element getRacine() {
        return racine;
    }

    public XMLManifestationDAO() {
        this.chemin = new XMLChemin();
        racine = new Element("manifestations");
    }

    @Override
    public ArrayList<Manifestation> readAll() {
        ArrayList<Manifestation> liste = new ArrayList<>();

        try {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(new FileInputStream(this.chemin.getChemin() + "/" + this.NOM_FICHIER));
            Element root = doc.getRootElement();

            List list = root.getChildren();

            for (int i = 0; i < list.size(); i++) {
                Element node = (Element) list.get(i);

                liste.add(new Manifestation(node.getAttribute("id").getIntValue(), node.getChildText("libelle_manif")));
            }

        } catch (IOException | JDOMException io) {
            System.out.println(io.getMessage());
        }
        return liste;
    }

    @Override
    public int create(Manifestation manifestation) {

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
            sortie.output(this.racine, new FileOutputStream(this.chemin.getChemin() + "/" + this.NOM_FICHIER));
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
        for (Manifestation manifestation : liste) {
            this.delete(manifestation);
        }

    }

    public void creerListe(ArrayList<Manifestation> liste) {
        this.sauvegarde();
        for (Manifestation manifestation : liste) {
            this.ajouter(manifestation);
        }
    }

}
