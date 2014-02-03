package model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.business.Manifestation;
import model.dao.factory.MySQLDAOFactory;
import model.dao.interfaces.ManifestationDAO;

public class MySQLManifestationDAO implements ManifestationDAO {

    private static MySQLManifestationDAO instance = null;

    public static MySQLManifestationDAO getInstance() {

        if (MySQLManifestationDAO.instance == null) {
            MySQLManifestationDAO.instance = new MySQLManifestationDAO();
        }

        return MySQLManifestationDAO.instance;
    }

    private MySQLDAOFactory daoFactory;

    @Override
    public ArrayList<Manifestation> readAll() {

        ArrayList<Manifestation> listeManifestation = new ArrayList<Manifestation>();

        Connection connection = this.daoFactory.getConnexion();
        String sql = "SELECT * FROM manifestation";

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                Manifestation manifestation = new Manifestation(res.getInt("id_manif"), res.getString("libelle_manif"));
                listeManifestation.add(manifestation);
            }
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        } finally {
            this.daoFactory.fermeConnexion();
        }

        return listeManifestation;
    }

    @Override
    public int create(Manifestation manifestation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Manifestation manifestation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Manifestation manifestation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
