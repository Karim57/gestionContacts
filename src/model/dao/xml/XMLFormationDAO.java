package model.dao.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import model.business.Formation;

import model.dao.DAOInterface;
import model.dao.XMLChemin;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.*;

public class XMLFormationDAO implements DAOInterface<Formation> {

    private static XMLFormationDAO instance = null;

    public static XMLFormationDAO getInstance() {
        if (XMLFormationDAO.instance == null) {
            XMLFormationDAO.instance = new XMLFormationDAO();
        }
        return XMLFormationDAO.instance;
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

    public XMLFormationDAO() {
        this.chemin = new XMLChemin();
        this.nomFichier = "formations.xml";
    }

    @Override
    public ArrayList<Formation> readAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int create(Formation formation) {

        racine = new Element("formations");
        Element form = new Element("formation");

        Attribute id = new Attribute("id", Integer.toString(formation.getIdFormation()));
        form.setAttribute(id);
        racine.addContent(form);

        Element libelle_form = new Element("libelle_form");
        libelle_form.setText(formation.getLibelleFormation());
        form.addContent(libelle_form);
        this.sauvegarde();

        return 1;
    }
    /*
     public void afficher(Element racine, String nomFichier) {
     //On crée une instance de SAXBuilder
     SAXBuilder sxb = new SAXBuilder();

     try {
     //On crée un nouveau document JDOM avec en argument le fichier XML
     //Le parsing est terminé ;)
     Document document = sxb.build(new File(this.chemin + "/" + nomFichier));
     } catch (IOException | JDOMException e) {
     System.out.println(e.getMessage());
     }

     //On crée une List contenant tous les noeuds "manifestation" de l'Element racine
     List liste = racine.getChildren("manifestation");

     //On crée un Iterator sur notre liste
     Iterator i = liste.iterator();
     while (i.hasNext()) {
     Element courant = (Element) i.next();
     //On affiche id_manif de l’élément courant
     System.out.println(courant.getChild("libelle_manif").getText());
     System.out.println("hdgyuehhen");
     }

     }*/

    private void sauvegarde() {
        try {
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(this.racine, new FileOutputStream(this.chemin.getChemin() + "/" + this.nomFichier));
        } catch (java.io.IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void ajouter(Formation formation) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(this.chemin.getChemin() + "/" + this.getNomFicher());

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

            XMLOutputter outputter1 = new XMLOutputter(Format.getPrettyFormat());
            outputter1.output(doc, new FileWriter(this.chemin.getChemin() + "/" + this.getNomFicher()));
            
        } catch (IOException | JDOMException e) {
            System.out.println(e.getMessage());
        }
    }

    public void supprimer(Formation formation) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(this.chemin.getChemin() + "/" + this.getNomFicher());

        try {
            Document doc = (Document) builder.build(xmlFile);
            Element root = doc.getRootElement();

            String numForm = Integer.toString(formation.getIdFormation());

            for (Element element : root.getChildren()) {

                if (numForm.equals(element.getAttributeValue("id"))) {
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
    
        public void modifier(Formation formation, String libelle) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(this.chemin.getChemin() + "/" + this.getNomFicher());

        try {
            Document doc = (Document) builder.build(xmlFile);
            Element root = doc.getRootElement();

            String numForm = Integer.toString(formation.getIdFormation());

            for (Element element : root.getChildren()) {

                if (numForm.equals(element.getAttributeValue("id"))) {
                     element.getChild("libelle_form").setText(libelle);
                    break;
                }

            }

            XMLOutputter outputter1 = new XMLOutputter(Format.getPrettyFormat());
            outputter1.output(doc, new FileWriter(this.chemin.getChemin() + "/" + this.getNomFicher()));

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
    public boolean update(Formation objetAModifier) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Formation objetASupprimer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteList(ArrayList<Formation> liste) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Formation readById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
