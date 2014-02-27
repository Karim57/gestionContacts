package model.dao.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import model.business.Contact;
import model.business.Formation;

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
    public int create(Contact contact) {

        racine = new Element("contacts");
        Element cont = new Element("contact");

        Attribute id = new Attribute("id", Integer.toString(contact.getIdContact()));
        cont.setAttribute(id);
        racine.addContent(cont);

        Element manifestation = new Element("manifestation");
        manifestation.setText(Integer.toString(contact.getManifestation().getIdManif()));
        cont.addContent(manifestation);

        Element enseignant = new Element("enseignant");
        enseignant.setText(Integer.toString(contact.getEnseignant().getIdEnseignant()));
        cont.addContent(enseignant);

        Element nom = new Element("nom");
        nom.setText(contact.getNomContact());
        cont.addContent(nom);

        Element prenom = new Element("prenom");
        prenom.setText(contact.getPrenomContact());
        cont.addContent(prenom);

        Element email = new Element("email");
        email.setText(contact.getEmailContact());
        cont.addContent(email);

        Element etude1 = new Element("etude1");
        etude1.setText(contact.getEtudes1Contact());
        cont.addContent(etude1);

        Element etude2 = new Element("etude2");
        etude2.setText(contact.getEtudes2Contact());
        cont.addContent(etude2);

        Element date = new Element("date");
        date.setText(contact.getDateContact().toString());
        cont.addContent(date);

        Element heure = new Element("heure");
        heure.setText(contact.getHeureContact().toString());
        cont.addContent(heure);

        Element description = new Element("description");
        description.setText(contact.getDescriptionContact().toString());
        cont.addContent(description);

        Element formations = new Element("formations");
        for (Formation form : contact.getListeFormations()) {
            Element formation = new Element("formation");
            formation.setText(Integer.toString(form.getIdFormation()));
            formations.addContent(formation);
        }
        cont.addContent(formations);

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

            Element manifestation = new Element("manifestation");
            manifestation.setText(Integer.toString(contact.getManifestation().getIdManif()));
            cont.addContent(manifestation);

            Element enseignant = new Element("enseignant");
            enseignant.setText(Integer.toString(contact.getEnseignant().getIdEnseignant()));
            cont.addContent(enseignant);

            Element nom = new Element("nom");
            nom.setText(contact.getNomContact());
            cont.addContent(nom);

            Element prenom = new Element("prenom");
            prenom.setText(contact.getPrenomContact());
            cont.addContent(prenom);

            Element email = new Element("email");
            email.setText(contact.getEmailContact());
            cont.addContent(email);

            Element etude1 = new Element("etude1");
            etude1.setText(contact.getEtudes1Contact());
            cont.addContent(etude1);

            Element etude2 = new Element("etude2");
            etude2.setText(contact.getEtudes2Contact());
            cont.addContent(etude2);

            Element date = new Element("date");
            date.setText(contact.getDateContact().toString());
            cont.addContent(date);

            Element heure = new Element("heure");
            heure.setText(contact.getHeureContact().toString());
            cont.addContent(heure);

            Element description = new Element("description");
            description.setText(contact.getDescriptionContact().toString());
            cont.addContent(description);

            Element formations = new Element("formations");
            for (Formation form : contact.getListeFormations()) {
                Element formation = new Element("formation");
                formation.setText(Integer.toString(form.getIdFormation()));
                formations.addContent(formation);
            }
            cont.addContent(formations);

            XMLOutputter outputter1 = new XMLOutputter(Format.getPrettyFormat());
            outputter1.output(doc, new FileWriter(this.chemin.getChemin() + "/" + this.getNomFicher()));

        } catch (IOException | JDOMException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean update(Contact contact) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(this.chemin.getChemin() + "/" + this.getNomFicher());

        try {
            Document doc = (Document) builder.build(xmlFile);
            Element root = doc.getRootElement();

            String numContact = Integer.toString(contact.getIdContact());

            for (Element element : root.getChildren()) {

                if (numContact.equals(element.getAttributeValue("id"))) {
                    element.getChild("manifestation").setText(Integer.toString(contact.getManifestation().getIdManif()));
                    element.getChild("enseignant").setText(Integer.toString(contact.getEnseignant().getIdEnseignant()));
                    element.getChild("nom").setText(contact.getNomContact());
                    element.getChild("prenom").setText(contact.getPrenomContact());
                    element.getChild("email").setText(contact.getEmailContact());
                    element.getChild("etude1").setText(contact.getEtudes1Contact());
                    element.getChild("etude2").setText(contact.getEtudes2Contact());
                    element.getChild("date").setText(contact.getDateContact().toString());
                    element.getChild("heure").setText(contact.getHeureContact().toString());
                    element.getChild("description").setText(contact.getDescriptionContact());
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

}
