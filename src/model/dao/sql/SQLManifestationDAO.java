package model.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.business.Manifestation;
import model.dao.MySQLConnect;
import model.dao.DAOInterface;

public class SQLManifestationDAO implements DAOInterface<Manifestation> {

    private static SQLManifestationDAO instance = null;

    public static SQLManifestationDAO getInstance() {
        if (SQLManifestationDAO.instance == null) {
            SQLManifestationDAO.instance = new SQLManifestationDAO();
        }
        return SQLManifestationDAO.instance;
    }

    private MySQLConnect connect;

    private SQLManifestationDAO() {
    }

    @Override
    public ArrayList<Manifestation> readAll() {

        ArrayList<Manifestation> listeManifestation = new ArrayList<Manifestation>();

        Connection connection = MySQLConnect.getInstance().getConnexion();
        String sql = "SELECT * FROM manifestation";

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                Manifestation manifestation = new Manifestation(res.getInt("id_manif"), res.getString("libelle_manif"));
                listeManifestation.add(manifestation);
            }
            res.close();
            st.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }

        return listeManifestation;
    }

    public Manifestation readById(int id) {

        Connection connection = MySQLConnect.getInstance().getConnexion();
        String sql = "SELECT * FROM manifestation where id_manif = " + id;
        Manifestation manifestation = null;

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                manifestation = new Manifestation(res.getInt("id_manif"), res.getString("libelle_manif"));
            }
            res.close();
            st.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }

        return manifestation;
    }

    @Override
    public int create(Manifestation manifestation) {

        Connection connection = MySQLConnect.getInstance().getConnexion();
        String insert = "INSERT INTO manifestation SET libelle_manif = ?";

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
            cle.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }

        return id;
    }

    @Override
    public boolean update(Manifestation manifestation) {

        Connection connection = MySQLConnect.getInstance().getConnexion();
        String update = "UPDATE manifestation SET libelle_manif = ? WHERE id_manif = ?";

        boolean updated = false;

        try {
            PreparedStatement stU = connection.prepareStatement(update);

            stU.setString(1, manifestation.getLibelleManif());
            stU.setInt(2, manifestation.getIdManif());
            stU.execute();

            updated = stU.getUpdateCount() > 0;
            stU.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }
        return updated;
    }

    @Override
    public boolean delete(Manifestation manifestation) {

        Connection connection = MySQLConnect.getInstance().getConnexion();
        String delete = "DELETE FROM manifestation WHERE id_manif = " + manifestation.getIdManif();

        boolean deleted = false;

        try {
            PreparedStatement stD = connection.prepareStatement(delete);
            stD.execute();

            deleted = stD.executeUpdate(delete) > 0;
            stD.close();

        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }

        return deleted;
    }

    @Override
    public void deleteList(ArrayList<Manifestation> liste) {
        for (Manifestation manifestation : liste) {
            this.delete(manifestation);
        }
    }

}
