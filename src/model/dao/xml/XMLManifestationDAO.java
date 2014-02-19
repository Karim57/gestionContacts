package model.dao.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.business.Manifestation;
import model.dao.DAOInterface;
import model.dao.XMLChemin;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.*;

public class XMLManifestationDAO implements DAOInterface<Manifestation> {
    
    private XMLChemin chemin;
    private String nomFicher;
    
    public  XMLManifestationDAO() {
        this.chemin = new XMLChemin();
        this.nomFicher = "manifestation.xml";
    }

    @Override
    public ArrayList<Manifestation> readAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int create(Manifestation manifestation) {
        Element racine = new Element("manifestation");
        
        Attribute id = new Attribute("id",Integer.toString(manifestation.getIdManif()));
        racine.setAttribute(id);

        Element libelle_manif = new Element("libelle_manif");
        libelle_manif.setText(manifestation.getLibelleManif());
        racine.addContent(libelle_manif);
        
        chemin.sauvegarde(racine, this.nomFicher);

        return 1;
    }
    
     public void afficher(Element racine, String nomFichier) {
        //On crée une instance de SAXBuilder
        SAXBuilder sxb = new SAXBuilder();

        try {
         //On crée un nouveau document JDOM avec en argument le fichier XML
            //Le parsing est terminé ;)
            Document document = sxb.build(new File(this.chemin + "/" + nomFichier));
        } catch (Exception e) {
            e.printStackTrace();
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
