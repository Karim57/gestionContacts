package model.dao.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
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
        String sql = "SELECT id_contact FROM contact";

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                Contact contact = this.readById(res.getInt("id_contact"));
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
        String sql = "SELECT * FROM contact WHERE id_contact = " + id;
        Contact contact = null;

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                contact = new Contact();

                contact.setNomContact(res.getString("nom_contact"));
                contact.setPrenomContact(res.getString("prenom_contact"));
                contact.setEmailContact(res.getString("email_contact"));
                contact.setEtudes1Contact(res.getString("etudes1_contact"));
                contact.setEtudes2Contact(res.getString("etudes2_contact"));
                contact.setDescriptionContact(res.getString("description_contact"));
                contact.setDateContact(res.getDate("date_contact"));
                contact.setHeureContact(res.getTime("heure_contact"));

                Manifestation m = SQLManifestationDAO.getInstance().readById(res.getInt("id_manif"));
                contact.setManifestation(m);

                Enseignant e = SQLEnseignantDAO.getInstance().readById(res.getInt("id_enseignant"));
                contact.setEnseignant(e);

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
        String sql = "SELECT * FROM contact c, formation f, rensignements r "
                + "WHERE c.id_contact = r.id_contact "
                + "AND r.id_formation = f.id_formation "
                + "AND id_contact = " + contact.getIdContact();

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
