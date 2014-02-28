package model.dao;

import java.io.IOException;
import static java.lang.System.exit;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import view.utils.GereErreurs;

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
            new GereErreurs("Le driver JDBC n'a pas pu être chargé, le programme va être fermé",
                    "Problème driver");
            exit(0);
        } catch (SQLException se) {
            new GereErreurs("Impossible de se connecter a la base de données, le programme va être fermé",
                    "Problème de connexion à la base de données");
            exit(0);
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
        } catch (IOException | NullPointerException e) {
            new GereErreurs("Erreur lors de la lecture de fichier de propriétés de la base de données",
                    "Une erreur s'est produite");
            exit(0);
        }
    }

}
