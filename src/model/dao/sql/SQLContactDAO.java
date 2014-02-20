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
        this.connect = new MySQLConnect();
    }

    @Override
    public ArrayList<Contact> readAll() {
        ArrayList<Contact> listeContacts = new ArrayList<Contact>();

        Connection connection = this.connect.getConnexion();
        String sql = "SELECT * FROM contact c, manifestation m, departement d, enseignant e "
                + "WHERE c.id_manifestation = m.id_manif "
                + "AND c.id_enseignant = e.id_ens "
                + "AND e.id_ens = d.id_dpt";

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {

                Departement departement = new Departement(res.getInt("id_dpt"), res.getString("libelle_dpt"));
                Enseignant enseignant = new Enseignant(res.getInt("id_ens"), res.getString("nom_ens"), res.getString("prenom_ens"), departement);

                Manifestation manifestation = new Manifestation(res.getInt("id_manif"), res.getString("libelle_manif"));

                Contact contact = new Contact(res.getInt("id_contact"),
                        res.getString("nom_contact"),
                        res.getString("prenom_contact"),
                        res.getString("email_contact"),
                        res.getString("etudes1_contact"),
                        res.getString("etudes2_contact"),
                        res.getString("description_contact"),
                        res.getDate("date_contact"),
                        res.getTime("heure_contact"),
                        manifestation,
                        enseignant,
                        null);

                this.remplitFormation(contact);

                listeContacts.add(contact);
            }
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        } finally {
            this.connect.fermeConnexion();
        }

        return listeContacts;
    }

    public Contact readById(int id) {

        Connection connection = this.connect.getConnexion();
        String sql = "SELECT * FROM contact c, manifestation m, departement d, enseignant e "
                + "WHERE c.id_manifestation = m.id_manif "
                + "AND c.id_enseignant = e.id_ens "
                + "AND e.id_ens = d.id_dpt "
                + "AND id_contact = " + id;
        Contact contact = null;

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                Departement departement = new Departement(res.getInt("id_dpt"), res.getString("libelle_dpt"));
                Enseignant enseignant = new Enseignant(res.getInt("id_ens"), res.getString("nom_ens"), res.getString("prenom_ens"), departement);

                Manifestation manifestation = new Manifestation(res.getInt("id_manif"), res.getString("libelle_manif"));

                contact = new Contact(res.getInt("id_contact"),
                        res.getString("nom_contact"),
                        res.getString("prenom_contact"),
                        res.getString("email_contact"),
                        res.getString("etudes1_contact"),
                        res.getString("etudes2_contact"),
                        res.getString("description_contact"),
                        res.getDate("date_contact"),
                        res.getTime("heure_contact"),
                        manifestation,
                        enseignant,
                        null);

                this.remplitFormation(contact);
            }
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        } finally {
            this.connect.fermeConnexion();
        }

        return contact;

    }

    private void remplitFormation(Contact contact) {

        Connection connection = this.connect.getConnexion();
        String sql = "SELECT * FROM contact c, formation f, renseignements r "
                + "WHERE c.id_contact = r.id_contact "
                + "AND r.id_formation = f.id_formation "
                + "AND c.id_contact = " + contact.getIdContact();

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                Formation f = SQLFormationDAO.getInstance().readById(res.getInt("id_form"));
                contact.addFormation(f);
            }
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }

    }

    @Override
    public int create(Contact objetAAjouter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Contact objetAModifier) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Contact objetASupprimer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteList(ArrayList<Contact> liste) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
