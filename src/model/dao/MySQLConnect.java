package model.dao;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLConnect {

    private static MySQLConnect instance = null;

    private Connection connexion;

    private Properties proprietes;

    /**
     * Constructeur : création de la connexion grâce au chargement des données
     * dans un fichier Properties
     *
     */
    private MySQLConnect() {

        this.chargeDonneesConnexion();
        this.connexion = null;
        try {
            this.demandePilote();
            this.demandeConnexion();
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Pb de driver : .jar dans le classpath ??? "
                    + cnfe.getMessage());
        } catch (SQLException se) {
            System.out.println("Connexion impossible : pb de droits ??? "
                    + se.getMessage());
        }
    }

    /**
     * Accesseur
     *
     * @return cette instance
     */
    public static MySQLConnect getInstance() {

        if (instance == null) {
            instance = new MySQLConnect();
        }
        return instance;
    }

    /**
     * Accesseur
     *
     * @return l'objet de connexion
     */
    public Connection getConnexion() {

        try {
            if (this.connexion.isClosed()) {
                this.demandeConnexion();
            }
        } catch (SQLException se) {
            System.out.println("getConnexion " + se.getMessage());
        }
        return this.connexion;
    }

    /**
     * Fermeture de la connexion
     *
     */
    public void ferme() {

        try {
            this.connexion.close();
        } catch (SQLException e) {
            System.out.println("Pb fermeture bdd " + e.getMessage());
        }
    }

    /**
     * Chargement du driver MySQL
     *
     * @throws ClassNotFoundException
     */
    private void demandePilote() throws ClassNotFoundException {

        Class.forName(this.proprietes.getProperty("pilote_bdd"));
    }

    /**
     * Connexion à MySQL
     *
     * @throws SQLException
     */
    private void demandeConnexion() throws SQLException {

        String url = this.proprietes.getProperty("type_bdd") + "://";
        url += this.proprietes.getProperty("adresse_ip") + ":" + this.proprietes.getProperty("port") + "/"
                + this.proprietes.getProperty("bdd");

        String login = this.proprietes.getProperty("login");
        String mdp = this.proprietes.getProperty("pass");

        this.connexion = DriverManager.getConnection(url, login, mdp);
    }

    /**
     * Création d'un objet properties et remplissage à partir du fichier contenu
     * dans le chemin donné
     *
     */
    private void chargeDonneesConnexion() {

        ClassLoader cl = this.getClass().getClassLoader();
        URL url = cl.getResource("config/bdd.properties");

        this.proprietes = new Properties();

        try {
            this.proprietes.loadFromXML(url.openStream());
        } catch (IOException ioe) {
            System.out
                    .println("Problème à la lecture du fichier de config bdd");
            System.out.println(ioe.getMessage());
        }
    }

}
