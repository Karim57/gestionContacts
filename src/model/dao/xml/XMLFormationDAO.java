package model.dao.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.business.Formation;

import model.dao.DAOInterface;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.*;

public class XMLFormationDAO implements DAOInterface<Formation> {

    private final String chemin;
    private final String NOM_FICHIER = "formations.xml";
    private Element racine;

    public Element getRacine() {
        return racine;
    }

    public XMLFormationDAO(String chemin) {
        this.chemin = chemin;
        racine = new Element("formations");
    }

    @Override
    public ArrayList<Formation> readAll() {
        ArrayList<Formation> liste = new ArrayList<>();

        try {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(new FileInputStream(this.chemin + "/" + this.NOM_FICHIER));
            Element root = doc.getRootElement();

            List list = root.getChildren();

            for (int i = 0; i < list.size(); i++) {
                Element node = (Element) list.get(i);
                liste.add(new Formation(node.getAttribute("id").getIntValue(),
                        node.getChildText("libelle_form"),
                        new XMLDepartementDAO(this.chemin).readById(Integer.parseInt(node.getChildText("id_dpt")))));
            }

        } catch (IOException | JDOMException io) {
            System.out.println(io.getMessage());
        }
        return liste;
    }

    @Override
    public int create(Formation formation) {

        Element form = new Element("formation");
        Attribute id = new Attribute("id", Integer.toString(formation.getIdFormation()));
        form.setAttribute(id);
        racine.addContent(form);

        Element libelle_form = new Element("libelle_form");
        libelle_form.setText(formation.getLibelleFormation());
        form.addContent(libelle_form);

        Element id_dpt = new Element("id_dpt");
        id_dpt.setText(Integer.toString(formation.getDepartement().getIdDepartement()));
        form.addContent(id_dpt);
        this.sauvegarde();

        return 1;
    }

    private void sauvegarde() {
        try {
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(this.racine, new FileOutputStream((this.chemin) + "/" + this.NOM_FICHIER));
        } catch (java.io.IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void ajouter(Formation formation) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(this.chemin + "/" + this.NOM_FICHIER);

        try {
            Document doc = (Document) builder.build(xmlFile);
            Element root = doc.getRootElement();

            Element form = new Element("formation");
            Attribute id = new Attribute("id", Integer.toString(formation.getIdFormation()));
            form.setAttribute(id);
            root.addContent(form);

            Element libelle_form = new Element("libelle_form");
            libelle_form.setText(formation.getLibelleFormation());
            form.addContent(libelle_form);

            Element id_dpt = new Element("id_dpt");
            id_dpt.setText(Integer.toString(formation.getDepartement().getIdDepartement()));
            form.addContent(id_dpt);

            XMLOutputter outputter1 = new XMLOutputter(Format.getPrettyFormat());
            outputter1.output(doc, new FileWriter(this.chemin + "/" + this.NOM_FICHIER));

        } catch (IOException | JDOMException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean update(Formation formation) {
        return false;
    }

    @Override
    public boolean delete(Formation formation) {
        return false;
    }

    @Override
    public void deleteList(ArrayList<Formation> liste) {
    }

    public void creerListe(ArrayList<Formation> liste) {
        this.sauvegarde();
        for (Formation formation : liste) {
            this.ajouter(formation);
        }
    }
}
