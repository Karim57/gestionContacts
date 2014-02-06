package model.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import model.business.Manifestation;
import model.dao.factory.MySQLDAOFactory;
import model.dao.interfaces.ManifestationDAO;

public class SQLManifestationDAO implements ManifestationDAO {

    private static SQLManifestationDAO instance = null;

    public static SQLManifestationDAO getInstance() {

        if (SQLManifestationDAO.instance == null) {
            SQLManifestationDAO.instance = new SQLManifestationDAO();
        }

        return SQLManifestationDAO.instance;
    }

    private MySQLDAOFactory daoFactory;

    private SQLManifestationDAO() {

        this.daoFactory = new MySQLDAOFactory();
    }

    @Override
    public Vector<Manifestation> readAll() {

        Vector<Manifestation> listeManifestation = new Vector<Manifestation>();

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

    public int create(Manifestation manifestation) {
        Connection connection = this.daoFactory.getConnexion();
        String insert = "insert into manifestation (libelle_manif) values (?)";

        int id = -1;

        try {
            PreparedStatement stI = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stI.setString(1, manifestation.getLibelleManif());
            stI.executeUpdate();

            ResultSet cle = stI.getGeneratedKeys();
            if (cle.next()) {
                id = cle.getInt(1);
                manifestation.setIdManif(id);
            }
            stI.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
            se.printStackTrace();
        }

        return id;
    }

    public void update(Manifestation manifestation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void delete(Manifestation manifestation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
