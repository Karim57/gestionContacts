package model.dao.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.business.Contact;
import model.business.Departement;
import model.business.Formation;
import model.business.Manifestation;

import model.dao.DAOInterface;
import model.dao.sql.SQLEnseignantDAO;
import model.dao.sql.SQLFormationDAO;
import model.dao.sql.SQLManifestationDAO;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.*;
import sun.misc.VM;
import view.utils.GereErreurs;

public class XMLContactDAO implements DAOInterface<Contact> {

    private String chemin;
    private final String NOM_FICHIER = "contacts.xml";
    private Element racine;

    public Element getRacine() {
        return racine;
    }

    public XMLContactDAO(String chemin) {
        this.chemin = chemin;
        racine = new Element("contacts");
    }

    @Override
    public ArrayList<Contact> readAll() {
        ArrayList<Contact> liste = new ArrayList<>();

        try {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(new FileInputStream(this.chemin));
            Element root = doc.getRootElement();

            List list = root.getChildren();

            for (int i = 0; i < list.size(); i++) {
                Element node = (Element) list.get(i);
                Contact contact = new Contact(
                        SQLManifestationDAO.getInstance().readById(Integer.parseInt(node.getChildText("manifestation"))),
                        SQLEnseignantDAO.getInstance().readById(Integer.parseInt(node.getChildText("enseignant"))),
                        node.getChildText("nom"), node.getChildText("prenom"), node.getChildText("email"),
                        node.getChildText("etude1"), node.getChildText("etude2"), Date.valueOf(node.getChildText("date")), Time.valueOf(node.getChildText("heure")),
                        node.getChildText("description"));

                Element formations = node.getChild("formations");
                List listform = formations.getChildren();
                for (int j = 0; j < listform.size(); j++) {
                    Element node2 = (Element) listform.get(j);
                    contact.addFormation(SQLFormationDAO.getInstance().readById(Integer.parseInt(node2.getValue())));
                }

                liste.add(contact);
            }

        } catch (IOException | JDOMException | NullPointerException | NumberFormatException e) {
            new GereErreurs("Un problème s'est produit lors de la lecture du fichier de données " + NOM_FICHIER,
                    "Une erreur s'est produite");
        }
        return liste;
    }

    @Override
    public int create(Contact contact) {

        Element cont = new Element("contact");

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
            sortie.output(this.racine, new FileOutputStream(this.chemin));
        } catch (java.io.IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void ajouter(Contact contact) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(this.chemin);

        try {
            Document doc = (Document) builder.build(xmlFile);
            Element root = doc.getRootElement();

            Element cont = new Element("contact");
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
            outputter1.output(doc, new FileWriter(this.chemin));

        } catch (IOException | JDOMException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean update(Contact contact) {
        return false;
    }

    @Override
    public boolean delete(Contact contact) {
        return false;
    }

    @Override
    public void deleteList(ArrayList<Contact> liste) {
    }

    public void creerListe(ArrayList<Contact> liste) {
        this.sauvegarde();
        for (Contact contact : liste) {
            this.ajouter(contact);
        }
    }

    public Manifestation getManifestation() {
        Manifestation m = null;
        try {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(new FileInputStream(this.chemin));
            Element root = doc.getRootElement();

            List list = root.getChildren();

            for (int i = 0; i < list.size(); i++) {
                Element node = (Element) list.get(i);
                m = SQLManifestationDAO.getInstance().readById(Integer.parseInt(node.getChildText("manifestation")));
            }

        } catch (IOException | JDOMException | NullPointerException | NumberFormatException e) {
            new GereErreurs("Un problème s'est produit lors de la lecture du fichier de données ",
                    "Une erreur s'est produite");
        }
        return m;
    }

    public String getMaxDate() {
        String sMaxDate = null;
        try {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(new FileInputStream(this.chemin));
            Element root = doc.getRootElement();

            List list = root.getChildren();
            Element node = (Element) list.get(0);
            Date maxDate = Date.valueOf(node.getChildText("date"));

            for (int i = 1; i < list.size(); i++) {
                node = (Element) list.get(i);
                if (maxDate.before(Date.valueOf(node.getChildText("date")))) {
                    maxDate = Date.valueOf(node.getChildText("date"));
                }
            }
            DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
            sMaxDate = df.format(maxDate);

        } catch (IOException | JDOMException | NullPointerException | NumberFormatException e) {
            new GereErreurs("Un problème s'est produit lors de la lecture du fichier de données ",
                    "Une erreur s'est produite");
        }
        return sMaxDate;
    }

    public String getMinDate() {
        String sMinDate = null;
        try {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(new FileInputStream(this.chemin));
            Element root = doc.getRootElement();

            List list = root.getChildren();
            Element node = (Element) list.get(0);
            Date minDate = Date.valueOf(node.getChildText("date"));
            for (int i = 1; i < list.size(); i++) {
                node = (Element) list.get(i);
                if (minDate.after(Date.valueOf(node.getChildText("date")))) {
                    minDate = Date.valueOf(node.getChildText("date"));
                }
            }
            DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
            sMinDate = df.format(minDate);

        } catch (IOException | JDOMException | NullPointerException | NumberFormatException e) {
            new GereErreurs("Un problème s'est produit lors de la lecture du fichier de données",
                    "Une erreur s'est produite");
        }
        return sMinDate;
    }

    public ArrayList<Departement> getListDpt() {
        ArrayList<Departement> listeDpt = new ArrayList<Departement>();
        try {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(new FileInputStream(this.chemin));
            Element root = doc.getRootElement();

            List list = root.getChildren();

            for (int i = 0; i < list.size(); i++) {
                Element node = (Element) list.get(i);

                Element formations = node.getChild("formations");
                List listform = formations.getChildren();
                for (int j = 0; j < listform.size(); j++) {
                    Element node2 = (Element) listform.get(j);
                    Formation formation = SQLFormationDAO.getInstance().readById(Integer.parseInt(node2.getValue()));
                    if (!listeDpt.contains(formation.getDepartement())) {
                        listeDpt.add(formation.getDepartement());
                    }
                }
            }

        } catch (IOException | JDOMException | NullPointerException | NumberFormatException e) {
            new GereErreurs("Un problème s'est produit lors de la lecture du fichier de données",
                    "Une erreur s'est produite");
        }
        return listeDpt;
    }

}
