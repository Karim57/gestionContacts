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
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.*;

public class XMLManifestationDAO implements DAOInterface<Manifestation> {

    private String chemin;
    private final String NOM_FICHIER = "manifestations.xml";
    private Element racine;
    static XMLManifestationDAO instance = null;

    public Element getRacine() {
        return racine;
    }

    public XMLManifestationDAO(String chemin) {
        this.chemin = chemin;
        racine = new Element("manifestation");
    }

    @Override
    public ArrayList<Manifestation> readAll() {
        return null;
    }

    public Manifestation read() {
        Manifestation manifestation = null;
        try {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(new FileInputStream(this.chemin + "/" + this.NOM_FICHIER));
            Element root = doc.getRootElement();

            manifestation = new Manifestation(Integer.valueOf(root.getChildText("id_manif")),
                    root.getChildText("libelle_manif"));
        } catch (IOException | JDOMException io) {
            System.out.println(io.getMessage());
        }
        return manifestation;

    }

    @Override
    public int create(Manifestation manifestation) {

        Element id_manif = new Element("id_manif");
        id_manif.setText(Integer.toString(manifestation.getIdManif()));
        racine.addContent(id_manif);

        Element libelle_manif = new Element("libelle_manif");
        libelle_manif.setText(manifestation.getLibelleManif());
        racine.addContent(libelle_manif);
        this.sauvegarde();

        return 1;
    }

    private void sauvegarde() {
        try {
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(this.racine, new FileOutputStream(this.chemin + "/" + this.NOM_FICHIER));
        } catch (java.io.IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void ajouter(Manifestation manifestation) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(this.chemin + "/" + this.NOM_FICHIER);

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
            outputter1.output(doc, new FileWriter(this.chemin + "/" + this.NOM_FICHIER));

        } catch (IOException | JDOMException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean update(Manifestation manifestation) {
        return false;
    }

    @Override
    public boolean delete(Manifestation manifestation) {
        return false;
    }

    @Override
    public void deleteList(ArrayList<Manifestation> liste) {
    }

}
