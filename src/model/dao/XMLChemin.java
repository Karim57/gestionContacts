package model.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public static void setChemin(String chemin) {
        try {
            FileOutputStream out = null;
            Properties props = new Properties();

            try {
                out = new FileOutputStream("src/config/xml.properties");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(XMLChemin.class.getName()).log(Level.SEVERE, null, ex);
            }
            props.setProperty("chemin", chemin);
            props.storeToXML(out, null);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(XMLChemin.class.getName()).log(Level.SEVERE, null, ex);
        }
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
