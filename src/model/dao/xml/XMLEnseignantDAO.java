package model.dao.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.business.Departement;
import model.business.Enseignant;
import model.business.Manifestation;

import model.dao.DAOInterface;
import model.dao.XMLChemin;
import model.dao.sql.SQLDepartementDAO;
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
        ArrayList<Enseignant> liste = new ArrayList<>();

        try {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(new FileInputStream(this.chemin.getChemin() + "/" + this.nomFichier));
            Element root = doc.getRootElement();

            List list = root.getChildren();

            for (int i = 0; i < list.size(); i++) {
                Element node = (Element) list.get(i);

                liste.add(new Enseignant(node.getAttribute("id").getIntValue(),
                        node.getChildText("nom_ens"),
                        node.getChildText("prenom_ens"),
                        SQLDepartementDAO.getInstance().readById(Integer.parseInt(node.getChildText("id_dpt")))));
            }

        } catch (IOException | JDOMException io) {
            System.out.println(io.getMessage());
        }
        return liste;
    }

    @Override
    public int create(Enseignant enseignant) {

        racine = new Element("enseignants");
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

        Element id_dpt = new Element("id_dpt");
        id_dpt.setText(Integer.toString(enseignant.getDepartement().getIdDepartement()));
        ens.addContent(id_dpt);

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

            Element id_dpt = new Element("id_dpt");
            id_dpt.setText(Integer.toString(enseignant.getDepartement().getIdDepartement()));
            ens.addContent(id_dpt);

            XMLOutputter outputter1 = new XMLOutputter(Format.getPrettyFormat());
            outputter1.output(doc, new FileWriter(this.chemin.getChemin() + "/" + this.getNomFicher()));

        } catch (IOException | JDOMException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean update(Enseignant enseignant) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(this.chemin.getChemin() + "/" + this.getNomFicher());

        try {
            Document doc = (Document) builder.build(xmlFile);
            Element root = doc.getRootElement();

            String numEns = Integer.toString(enseignant.getIdEnseignant());

            for (Element element : root.getChildren()) {

                if (numEns.equals(element.getAttributeValue("id"))) {
                    element.getChild("nom_ens").setText(enseignant.getNomEnseignant());
                    element.getChild("prenom_ens").setText(enseignant.getPrenomEnseignant());
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
    public boolean delete(Enseignant enseignant) {
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
            return false;
        }
        return true;
    }

    @Override
    public void deleteList(ArrayList<Enseignant> liste) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
