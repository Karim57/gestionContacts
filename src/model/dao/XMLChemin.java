package model.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class XMLChemin {

    private Properties proprietes;
    private final String chemin;

    public String getChemin() {
        return chemin;
    }

    public XMLChemin() {
        try {
            File dir = new File(this.chargeChemin());
            dir.mkdirs();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        chemin = this.chargeChemin();
    }

    private String chargeChemin() {
        String source = "src/config/xml.properties";
        this.proprietes = new Properties();

        try {
            try (FileInputStream path = new FileInputStream(source)) {
                this.proprietes.loadFromXML(path);
            }
        } catch (IOException ioe) {
            System.out.println("Problème à la lecture du fichier de config xml");
            System.out.println(ioe.getMessage());
        }

        return this.proprietes.getProperty("chemin");
    }

}
