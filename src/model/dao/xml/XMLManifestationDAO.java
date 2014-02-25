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

    private static XMLManifestationDAO instance = null;

    public static XMLManifestationDAO getInstance() {
        if (XMLManifestationDAO.instance == null) {
            XMLManifestationDAO.instance = new XMLManifestationDAO();
        }
        return XMLManifestationDAO.instance;
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

    public XMLManifestationDAO() {
        this.chemin = new XMLChemin();
        this.nomFichier = "manifestation.xml";
    }

    @Override
    public ArrayList<Manifestation> readAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int create(Manifestation manifestation) {

        racine = new Element("contact");
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

    public void supprimer(Manifestation manifestation) {
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
        }
    }
    
        public void modifier(Manifestation manifestation, String libelle) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(this.chemin.getChemin() + "/" + this.getNomFicher());

        try {
            Document doc = (Document) builder.build(xmlFile);
            Element root = doc.getRootElement();

            String numManif = Integer.toString(manifestation.getIdManif());

            for (Element element : root.getChildren()) {

                if (numManif.equals(element.getAttributeValue("id"))) {
                    // root.removeContent(element);
                     element.getChild("libelle_manif").setText(libelle);
                    // root.getChild("manifestation").getAttribute("libelle").setValue(libelle);
      
                    break;
                }

            }

            XMLOutputter outputter1 = new XMLOutputter(Format.getPrettyFormat());
            outputter1.output(doc, new FileWriter(this.chemin.getChemin() + "/" + this.getNomFicher()));
            // this.sauvegarde(this.nomFicher);

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
    public boolean update(Manifestation objetAModifier) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Manifestation objetASupprimer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteList(ArrayList<Manifestation> liste) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Manifestation readById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
