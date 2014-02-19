package model.dao.sql;

import java.sql.Connection;
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

    private MySQLConnect connect;

    private SQLFormationDAO() {
        this.connect = new MySQLConnect();
    }

    @Override
    public ArrayList<Formation> readAll() {

        ArrayList<Formation> listeFormations = new ArrayList<Formation>();

        Connection connection = this.connect.getConnexion();
        String sql = "SELECT id_form FROM formation";

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                Formation formation = this.readById(res.getInt("id_form"));
                listeFormations.add(formation);
            }
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        } finally {
            this.connect.fermeConnexion();
        }

        return listeFormations;
    }

    @Override
    public Formation readById(int id) {

        Connection connection = this.connect.getConnexion();
        String sql = "SELECT * FROM formation where id_form = " + id;
        Formation formation = null;
        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                Departement departement = SQLDepartementDAO.getInstance().readById(res.getInt("id_dpt"));
                formation = new Formation(res.getInt("id_form"), res.getString("libelle_form"), departement);
            }
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        } finally {
            this.connect.fermeConnexion();
        }

        return formation;
    }

    @Override
    public int create(Formation objetAAjouter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Formation objetAModifier) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Formation objetASupprimer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteList(ArrayList<Formation> liste) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
