package model.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.business.Departement;
import model.business.Formation;
import model.dao.DAOInterface;
import model.dao.MySQLConnect;

public class SQLFormationDAO implements DAOInterface<Formation> {

    private static SQLFormationDAO instance = null;

    public static SQLFormationDAO getInstance() {
        if (SQLFormationDAO.instance == null) {
            SQLFormationDAO.instance = new SQLFormationDAO();
        }
        return SQLFormationDAO.instance;
    }

    private SQLFormationDAO() {
    }

    @Override
    public ArrayList<Formation> readAll() {

        ArrayList<Formation> listeFormations = new ArrayList<Formation>();

        Connection connection = MySQLConnect.getInstance().getConnexion();
        String sql = "SELECT * FROM formation";

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                Departement departement = SQLDepartementDAO.getInstance().readById(res.getInt("id_dpt"));
                Formation formation = new Formation(res.getInt("id_form"), res.getString("libelle_form"), departement);
                listeFormations.add(formation);
            }
            res.close();
            st.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }

        return listeFormations;
    }

    public Formation readByNameDpt(String lib, int id_dpt) {
        Connection connection = MySQLConnect.getInstance().getConnexion();
        String sql = "SELECT * FROM formation where libelle_form = '" + lib + "' AND id_dpt = " + id_dpt;
        Formation formation = null;
        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                Departement departement = SQLDepartementDAO.getInstance().readById(res.getInt("id_dpt"));
                formation = new Formation(res.getInt("id_form"), res.getString("libelle_form"), departement);
            }
            res.close();
            st.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }

        return formation;
    }

    public Formation readById(int id) {

        Connection connection = MySQLConnect.getInstance().getConnexion();
        String sql = "SELECT * FROM formation where id_form = " + id;
        Formation formation = null;
        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                Departement departement = SQLDepartementDAO.getInstance().readById(res.getInt("id_dpt"));
                formation = new Formation(res.getInt("id_form"), res.getString("libelle_form"), departement);
            }
            res.close();
            st.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }

        return formation;
    }

    public int nbFormationsParDpt(Departement d) {

        Connection connection = MySQLConnect.getInstance().getConnexion();
        String sql = "SELECT COUNT(*) FROM formation WHERE id_dpt = " + d.getIdDepartement();

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
    public int create(Formation formation) {
        Connection connection = MySQLConnect.getInstance().getConnexion();
        String insert = "INSERT INTO formation SET libelle_form = ?,  id_dpt = ?";

        int id = -1;

        try {
            PreparedStatement stI = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stI.setString(1, formation.getLibelleFormation());
            stI.setInt(2, formation.getDepartement().getIdDepartement());
            stI.executeUpdate();

            ResultSet cle = stI.getGeneratedKeys();
            if (cle.next()) {
                id = cle.getInt(1);
                formation.setIdFormation(id);
            }
            stI.close();
            cle.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }

        return id;
    }

    @Override
    public boolean update(Formation formation) {
        Connection connection = MySQLConnect.getInstance().getConnexion();
        String insert = "UPDATE formation SET libelle_form = ?,  id_dpt = ? WHERE id_form = ?";

        boolean updated = false;

        try {
            PreparedStatement stU = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stU.setString(1, formation.getLibelleFormation());
            stU.setInt(2, formation.getDepartement().getIdDepartement());
            stU.setInt(3, formation.getIdFormation());
            stU.execute();

            updated = stU.getUpdateCount() > 0;
            stU.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }
        return updated;
    }

    @Override
    public boolean delete(Formation formation) {
        Connection connection = MySQLConnect.getInstance().getConnexion();
        String delete = "DELETE FROM formation WHERE id_form = " + formation.getIdFormation();

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

    public boolean deleteParDpt(Departement d) {
        Connection connection = MySQLConnect.getInstance().getConnexion();
        String delete = "DELETE FROM formation WHERE id_dpt = " + d.getIdDepartement();

        boolean deleted = false;

        try {
            PreparedStatement stD = connection.prepareStatement(delete);
            stD.execute();

            deleted = stD.executeUpdate(delete) > 0;
            stD.close();

            deleted = true;

        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }

        return deleted;
    }

    @Override
    public void deleteList(ArrayList<Formation> liste) {
        for (Formation f : liste) {
            this.delete(f);
        }
    }

}
