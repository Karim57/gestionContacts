/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import model.business.Departement;
import model.dao.factory.MySQLDAOFactory;
import model.dao.interfaces.DepartementDAO;

/**
 *
 * @author Karim
 */


public class SQLDepartementDAO implements DepartementDAO {

    private static SQLDepartementDAO instance = null;

    public static SQLDepartementDAO getInstance() {

        if (SQLDepartementDAO.instance == null) {
            SQLDepartementDAO.instance = new SQLDepartementDAO();
        }

        return SQLDepartementDAO.instance;
    }

    private MySQLDAOFactory daoFactory;

    private SQLDepartementDAO() {

        this.daoFactory = new MySQLDAOFactory();
    }

    @Override
    public Vector<Departement> readAll() {

        Vector<Departement> listeDepartement = new Vector<Departement>();

        Connection connection = this.daoFactory.getConnexion();
        String sql = "SELECT * FROM manifestation";

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                Departement departement = new Departement(res.getInt("id_departement"), res.getString("libelle_departement"));
                listeDepartement.add(departement);
            }
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        } finally {
            this.daoFactory.fermeConnexion();
        }

        return listeDepartement;
    }

    public int create(Departement departement) {
        Connection connection = this.daoFactory.getConnexion();
        String insert = "insert into departement (libelle_departement) values (?)";

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
            se.printStackTrace();
        }

        return id;
    }

    public void update(Departement departement) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void delete(Departement departement) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
