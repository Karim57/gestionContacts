package model.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XMLChemin {

    private Properties proprietes;

    private String chargeChemin() {
        String source = "src/config/xml.properties";
        this.proprietes = new Properties();

        try {
            FileInputStream path = new FileInputStream(source);
            this.proprietes.loadFromXML(path);
            path.close();
        } catch (IOException ioe) {
            System.out.println("Problème à la lecture du fichier de config xml");
            System.out.println(ioe.getMessage());
        }

        return this.proprietes.getProperty("chemin");
    }

    public void sauvegarde(Element racine, String nomFichier) {
        Document document = new Document(racine);
        try {
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(document, new FileOutputStream(this.chargeChemin() + "/" + nomFichier));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

}
