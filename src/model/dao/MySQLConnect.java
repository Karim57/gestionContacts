package model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLConnect {

    private Properties proprietes;
    private Connection connexion;
    private String base;
    private String utilisateur;
    private String motDePasse;

    public MySQLConnect() {
        this.chargeDonneesConnexion();
        this.connexion = null;
        try {
            this.demandePilote();
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Problème de driver" + cnfe.getMessage());
        }
    }

    public Connection getConnexion() {
        this.connexion = null;
        try {
            this.connexion = DriverManager.getConnection(this.base, this.utilisateur, this.motDePasse);
        } catch (SQLException se) {
            System.out.println("Connexion refusée ! ");
        }
        return this.connexion;
    }

    public void fermeConnexion() {
        try {
            this.connexion.close();
        } catch (SQLException e) {
            System.out.println("Pb fermeture bdd " + e.getMessage());
        }
    }

    private void demandePilote() throws ClassNotFoundException {
        Class.forName(this.proprietes.getProperty("pilote_bdd"));
    }

    private void chargeDonneesConnexion() {

        String chemin = "src/config/bdd.properties";
        this.proprietes = new Properties();

        try {
            FileInputStream path = new FileInputStream(chemin);
            this.proprietes.loadFromXML(path);
            path.close();
        } catch (IOException ioe) {
            System.out.println("Problème à la lecture du fichier de config bdd");
            System.out.println(ioe.getMessage());
        }

        this.base = this.proprietes.getProperty("type_bdd") + "://"
                + this.proprietes.getProperty("adresse_ip") + ":"
                + this.proprietes.getProperty("port") + "/"
                + this.proprietes.getProperty("bdd");

        this.utilisateur = this.proprietes.getProperty("login");
        this.motDePasse = this.proprietes.getProperty("pass");

    }

}
