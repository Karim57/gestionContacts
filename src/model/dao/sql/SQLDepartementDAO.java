package model.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.business.Departement;
import model.dao.MySQLConnect;
import model.dao.DAOInterface;

public class SQLDepartementDAO implements DAOInterface<Departement> {

    private static SQLDepartementDAO instance = null;

    public static SQLDepartementDAO getInstance() {

        if (SQLDepartementDAO.instance == null) {
            SQLDepartementDAO.instance = new SQLDepartementDAO();
        }
        return SQLDepartementDAO.instance;
    }

    private SQLDepartementDAO() {
    }

    public Departement readByName(String lib) {
        Connection connection = MySQLConnect.getInstance().getConnexion();
        String sql = "SELECT * FROM departement WHERE libelle_dpt = '" + lib + "'";
        Departement departement = null;

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            if (res.next()) {
                departement = new Departement(res.getInt("id_dpt"), res.getString("libelle_dpt"));
            }
            res.close();
            st.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }

        return departement;
    }

    @Override
    public ArrayList<Departement> readAll() {

        ArrayList<Departement> listeDepartement = new ArrayList<Departement>();

        Connection connection = MySQLConnect.getInstance().getConnexion();
        String sql = "SELECT * FROM departement";

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                Departement departement = new Departement(res.getInt("id_dpt"), res.getString("libelle_dpt"));
                listeDepartement.add(departement);
            }
            res.close();
            st.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }
        return listeDepartement;
    }

    public Departement readById(int id) {

        Connection connection = MySQLConnect.getInstance().getConnexion();
        String sql = "SELECT * FROM departement WHERE id_dpt = " + id;
        Departement departement = null;

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            if (res.next()) {
                departement = new Departement(res.getInt("id_dpt"), res.getString("libelle_dpt"));
            }
            res.close();
            st.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }

        return departement;
    }

    @Override
    public int create(Departement departement) {

        Connection connection = MySQLConnect.getInstance().getConnexion();
        String insert = "INSERT INTO departement SET libelle_dpt = ?";

        int id = -1;

        try {
            PreparedStatement stI = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stI.setString(1, departement.getLibelleDepartement());
            stI.executeUpdate();

            ResultSet cle = stI.getGeneratedKeys();
            if (cle.next()) {
                id = cle.getInt(1);
                departement.setIdDepartement(id);
            }
            stI.close();
            cle.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }

        return id;
    }

    @Override
    public boolean update(Departement departement) {

        Connection connection = MySQLConnect.getInstance().getConnexion();
        String update = "UPDATE departement SET libelle_dpt = ? WHERE id_dpt = ?";

        boolean updated = false;

        try {
            PreparedStatement stU = connection.prepareStatement(update);

            stU.setString(1, departement.getLibelleDepartement());
            stU.setInt(2, departement.getIdDepartement());
            stU.execute();

            updated = stU.getUpdateCount() > 0;
            stU.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }
        return updated;
    }

    @Override
    public boolean delete(Departement departement) {

        Connection connection = MySQLConnect.getInstance().getConnexion();
        String delete = "DELETE FROM departement where id_dpt = " + departement.getIdDepartement();

        boolean deleted = false;

        try {
            SQLFormationDAO.getInstance().deleteParDpt(departement);
            SQLEnseignantDAO.getInstance().deleteParDpt(departement);
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
    public void deleteList(ArrayList<Departement> liste) {
        for (Departement departement : liste) {
            this.delete(departement);
        }
    }

}
