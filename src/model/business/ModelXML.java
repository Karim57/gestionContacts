/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.business;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author Karim
 */
public class ModelXML {

    public static String genererXML() {
        XStream xstream = new XStream(new StaxDriver());
        xstream.alias("contact", Contact.class);
        Contact contact = new Contact(new Date ());
        contact.setManifestation(new Manifestation(""));
        contact.setEnseignant(new Enseignant("Pierre","LAROCHE"));
        contact.setFormations(new Formation("DUT"));
        
        String xml = xstream.toXML(contact);
        
        return xml;
    }

    public static void sauvegarderDansFichier(String xml) {
        try {
            File dossier = new File("src"+File.separator+"SauvegardeXML");
            File fichier;
            if (dossier.exists() && dossier.isDirectory()) {
                fichier = new File(dossier, "contact.xml");
            } else {
                dossier.mkdir();
                fichier = new File(dossier, "contact.xml");
            }

            FileWriter ecrire = new FileWriter(fichier);
            BufferedWriter writer = new BufferedWriter(ecrire);

            writer.write(xml);
            writer.close();

            System.out.println("-- Sauvegarde Terminée -- ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
