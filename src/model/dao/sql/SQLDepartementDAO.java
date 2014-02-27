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

    private MySQLConnect connect;

    private SQLDepartementDAO() {
        this.connect = new MySQLConnect();
    }

    @Override
    public ArrayList<Departement> readAll() {

        ArrayList<Departement> listeDepartement = new ArrayList<Departement>();

        Connection connection = this.connect.getConnexion();
        String sql = "SELECT * FROM departement";

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                Departement departement = new Departement(res.getInt("id_dpt"), res.getString("libelle_dpt"));
                listeDepartement.add(departement);
            }
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        } finally {
            this.connect.fermeConnexion();
        }

        return listeDepartement;
    }

    public Departement readById(int id) {

        Connection connection = this.connect.getConnexion();
        String sql = "SELECT * FROM departement WHERE id_dpt = " + id;
        Departement departement = null;

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            if (res.next()) {
                departement = new Departement(res.getInt("id_dpt"), res.getString("libelle_dpt"));
            }
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        } finally {
            this.connect.fermeConnexion();
        }

        return departement;
    }

    @Override
    public int create(Departement departement) {
        Connection connection = this.connect.getConnexion();
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
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        } finally {
            this.connect.fermeConnexion();
        }

        return id;
    }

    @Override
    public boolean update(Departement departement) {
        Connection connection = this.connect.getConnexion();
        String update = "UPDATE departement SET libelle_dpt = ? WHERE id_dpt = ?";

        boolean updated = false;

        try {
            System.err.println(departement.getIdDepartement());
            PreparedStatement stU = connection.prepareStatement(update);

            stU.setString(1, departement.getLibelleDepartement());
            stU.setInt(2, departement.getIdDepartement());
            stU.execute();

            updated = stU.getUpdateCount() > 0;
            stU.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        } finally {
            this.connect.fermeConnexion();
        }
        System.err.println(updated);
        return updated;
    }

    @Override
    public boolean delete(Departement departement) {
        Connection connection = this.connect.getConnexion();
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
        } finally {
            this.connect.fermeConnexion();
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
