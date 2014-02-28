package model.dao.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.business.Contact;
import model.business.Departement;
import model.business.Enseignant;
import model.business.Formation;
import model.business.Manifestation;
import model.dao.DAOInterface;
import model.dao.MySQLConnect;

public class SQLContactDAO implements DAOInterface<Contact> {

    private static SQLContactDAO instance = null;

    public static SQLContactDAO getInstance() {

        if (SQLContactDAO.instance == null) {
            SQLContactDAO.instance = new SQLContactDAO();
        }
        return SQLContactDAO.instance;
    }

    private MySQLConnect connect;

    private SQLContactDAO() {
    }

    @Override
    public ArrayList<Contact> readAll() {
        ArrayList<Contact> listeContacts = new ArrayList<Contact>();

        Connection connection = MySQLConnect.getInstance().getConnexion();
        String sql = "SELECT * FROM contact c, manifestation m, departement d, enseignant e "
                + "WHERE c.id_manifestation = m.id_manif "
                + "AND c.id_enseignant = e.id_ens "
                + "AND e.id_dpt = d.id_dpt";

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {

                Departement departement = new Departement(res.getInt("id_dpt"), res.getString("libelle_dpt"));
                Enseignant enseignant = new Enseignant(res.getInt("id_ens"), res.getString("nom_ens"), res.getString("prenom_ens"), departement);

                Manifestation manifestation = new Manifestation(res.getInt("id_manif"), res.getString("libelle_manif"));

                Contact contact = new Contact(res.getInt("id_contact"),
                        manifestation,
                        enseignant,
                        res.getString("nom_contact"),
                        res.getString("prenom_contact"),
                        res.getString("email_contact"),
                        res.getString("etudes1_contact"),
                        res.getString("etudes2_contact"),
                        res.getDate("date_contact"),
                        res.getTime("heure_contact"),
                        res.getString("description_contact")
                );

                this.remplitFormation(contact);

                listeContacts.add(contact);

            }

            res.close();
            st.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }

        return listeContacts;
    }

    public Contact readById(int id) {

        Connection connection = MySQLConnect.getInstance().getConnexion();
        String sql = "SELECT * FROM contact c, manifestation m, departement d, enseignant e "
                + "WHERE c.id_manifestation = m.id_manif "
                + "AND c.id_enseignant = e.id_ens "
                + "AND e.id_dpt = d.id_dpt "
                + "AND id_contact = " + id;
        Contact contact = null;

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            if (res.next()) {
                Departement departement = new Departement(res.getInt("id_dpt"), res.getString("libelle_dpt"));
                Enseignant enseignant = new Enseignant(res.getInt("id_ens"), res.getString("nom_ens"), res.getString("prenom_ens"), departement);

                Manifestation manifestation = new Manifestation(res.getInt("id_manif"), res.getString("libelle_manif"));

                contact = new Contact(res.getInt("id_contact"),
                        manifestation,
                        enseignant,
                        res.getString("nom_contact"),
                        res.getString("prenom_contact"),
                        res.getString("email_contact"),
                        res.getString("etudes1_contact"),
                        res.getString("etudes2_contact"),
                        res.getDate("date_contact"),
                        res.getTime("heure_contact"),
                        res.getString("description_contact")
                );

                this.remplitFormation(contact);
            }
            res.close();
            st.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }

        return contact;

    }

    private void remplitFormation(Contact contact) {

        Connection connection = MySQLConnect.getInstance().getConnexion();
        String sql = "SELECT id_form FROM contact c, formation f, renseignements r "
                + "WHERE c.id_contact = r.id_contact "
                + "AND r.id_formation = f.id_form "
                + "AND c.id_contact = " + contact.getIdContact();

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                Formation f = SQLFormationDAO.getInstance().readById(res.getInt("id_form"));
                contact.addFormation(f);

            }

            res.close();
            st.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }

    }

    public int nbContactsParManifestation(Manifestation m) {
        Connection connection = MySQLConnect.getInstance().getConnexion();
        String sql = "SELECT COUNT(*) FROM contact WHERE id_manifestation = " + m.getIdManif();

        int i = 0;

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            if (res.next()) {
                i = res.getInt(1);
            }

            res.close();
            st.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }
        return i;
    }

    public int nbContactsParEns(Enseignant e) {

        Connection connection = MySQLConnect.getInstance().getConnexion();
        String sql = "SELECT COUNT(*) FROM contact WHERE id_enseignant = " + e.getIdEnseignant();

        int i = 0;

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            if (res.next()) {
                i = res.getInt(1);
            }

            res.close();
            st.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }

        return i;
    }

    public int nbContactsParFormation(Formation f) {

        Connection connection = MySQLConnect.getInstance().getConnexion();
        String sql = "SELECT COUNT(*) FROM renseignements WHERE id_formation = " + f.getIdFormation();

        int i = 0;

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            if (res.next()) {
                i = res.getInt(1);
            }

            res.close();
            st.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }
        return i;
    }

    public int nbContactsParDptFormation(Departement d) {

        Connection connection = MySQLConnect.getInstance().getConnexion();
        String sql = "SELECT COUNT(*) FROM renseignements r, formation f"
                + " WHERE r.id_formation = f.id_form"
                + " AND id_dpt = " + d.getIdDepartement();

        int i = 0;

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            if (res.next()) {
                i = res.getInt(1);
            }

            res.close();
            st.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }
        return i;
    }

    public int nbContactsParDptEnseignants(Departement d) {

        Connection connection = MySQLConnect.getInstance().getConnexion();
        String sql = "SELECT COUNT(*) FROM contact c, enseignant e"
                + " WHERE e.id_ens = c.id_enseignant"
                + " AND id_dpt = " + d.getIdDepartement();

        int i = 0;

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            if (res.next()) {
                i = res.getInt(1);
            }

            res.close();
            st.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }
        return i;
    }

    @Override

    public int create(Contact objetAAjouter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Contact objetAModifier) {
        return false;
    }

    @Override
    public boolean delete(Contact objetASupprimer) {
        return false;
    }

    @Override
    public void deleteList(ArrayList<Contact> liste) {
    }

}
