package model.dao.factory;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import model.dao.sql.SQLManifestationDAO;
import model.dao.interfaces.DAOInterface;

public class MySQLDAOFactory extends DAOFactory {

    private Properties proprietes;

    private Connection connexion;

    private String base;

    private String utilisateur;

    private String motDePasse;

    public MySQLDAOFactory() {

        this.chargeDonneesConnexion();
        this.connexion = null;
        try {
            this.demandePilote();
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Pb de driver : .jar dans le classpath ??? "
                    + cnfe.getMessage());
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

    public DAOInterface getEnseignantDAO() {
        return SQLManifestationDAO.getInstance();
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
