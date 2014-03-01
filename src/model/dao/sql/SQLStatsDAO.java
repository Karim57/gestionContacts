package model.dao.sql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.jar.Manifest;
import model.business.Manifestation;
import model.dao.MySQLConnect;
import org.jfree.data.category.DefaultCategoryDataset;

public class SQLStatsDAO {

    private static SQLStatsDAO instance = null;

    public static SQLStatsDAO getInstance() {

        if (SQLStatsDAO.instance == null) {
            SQLStatsDAO.instance = new SQLStatsDAO();
        }
        return SQLStatsDAO.instance;
    }

    private SQLStatsDAO() {
    }

    public DefaultCategoryDataset getNbContactsParFormation(Manifestation manifestation) {

        DefaultCategoryDataset dcd = new DefaultCategoryDataset();
        Connection connection = MySQLConnect.getInstance().getConnexion();

        String sql = "SELECT id_formation, libelle_form, id_manifestation, COUNT(c.id_contact) as nb "
                + "FROM renseignements r, formation f, contact c "
                + "WHERE f.id_form = r.id_formation "
                + "AND c.id_contact = r.id_contact "
                + "AND id_manifestation = " + manifestation.getIdManif() + " "
                + "GROUP BY f.id_form";

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                String libelle = res.getString("libelle_form");
                int nb = res.getInt("nb");
                dcd.addValue(nb, "Nombre de contacts", libelle);
            }
            st.close();
            res.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }

        return dcd;
    }

    public DefaultCategoryDataset getNbContactsParDpt(Manifestation manifestation) {

        DefaultCategoryDataset dcd = new DefaultCategoryDataset();
        Connection connection = MySQLConnect.getInstance().getConnexion();

        String sql = "SELECT id_manifestation, d.id_dpt, libelle_dpt, COUNT(c.id_contact) as nb "
                + "FROM contact c, departement d, formation f, renseignements r "
                + "WHERE c.id_contact = r.id_contact "
                + "AND r.id_formation = f.id_form "
                + "AND f.id_dpt = d.id_dpt "
                + "AND id_manifestation = " + manifestation.getIdManif() + " "
                + "GROUP BY d.id_dpt";

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                String libelle = res.getString("libelle_dpt");
                int nb = res.getInt("nb");
                dcd.addValue(nb, "Nombre de contacts", libelle);
            }
            st.close();
            res.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }

        return dcd;
    }

    public ArrayList<Date> getJours(Manifestation manifestation) {
        ArrayList<Date> listeDate = new ArrayList<Date>();

        Connection connection = MySQLConnect.getInstance().getConnexion();
        String sql = "SELECT DISTINCT date_contact "
                + "FROM contact WHERE id_manifestation = " + manifestation.getIdManif();

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                listeDate.add(res.getDate("date_contact"));
            }
            res.close();
            st.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }

        return listeDate;

    }

    public DefaultCategoryDataset getNbContactsParDateMatin(Manifestation manifestation, Date date) {

        DefaultCategoryDataset dcd = new DefaultCategoryDataset();
        Connection connection = MySQLConnect.getInstance().getConnexion();

        String sql = "SELECT id_manifestation, d.id_dpt, libelle_dpt, COUNT(c.id_contact) as nb "
                + "FROM contact c, departement d, formation f, renseignements r "
                + "WHERE c.id_contact = r.id_contact "
                + "AND r.id_formation = f.id_form "
                + "AND f.id_dpt = d.id_dpt "
                + "AND id_manifestation = " + manifestation.getIdManif() + " "
                + "AND date_contact = '" + date + "' "
                + "AND heure_contact < '12:00:00' "
                + "GROUP BY d.id_dpt";

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                String libelle = res.getString("libelle_dpt");
                int nb = res.getInt("nb");
                dcd.addValue(nb, "Nombre de contacts", libelle);
            }
            st.close();
            res.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }

        return dcd;
    }

    public DefaultCategoryDataset getNbContactsParDateAprem(Manifestation manifestation, Date date) {

        DefaultCategoryDataset dcd = new DefaultCategoryDataset();
        Connection connection = MySQLConnect.getInstance().getConnexion();

        String sql = "SELECT id_manifestation, d.id_dpt, libelle_dpt, COUNT(c.id_contact) as nb "
                + "FROM contact c, departement d, formation f, renseignements r "
                + "WHERE c.id_contact = r.id_contact "
                + "AND r.id_formation = f.id_form "
                + "AND f.id_dpt = d.id_dpt "
                + "AND id_manifestation = " + manifestation.getIdManif() + " "
                + "AND date_contact = '" + date + "' "
                + "AND heure_contact >= '12:00:00' "
                + "GROUP BY d.id_dpt";

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                String libelle = res.getString("libelle_dpt");
                int nb = res.getInt("nb");
                dcd.addValue(nb, "Nombre de contacts", libelle);
            }
            st.close();
            res.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }

        return dcd;
    }

}
