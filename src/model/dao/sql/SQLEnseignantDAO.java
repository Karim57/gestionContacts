package model.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.business.Enseignant;
import model.dao.factory.MySQLDAOFactory;
import model.dao.interfaces.DAOInterface;


/******** Question sur l'arrayList générique***/
public class SQLEnseignantDAO implements DAOInterface<Enseignant> {

    private static SQLEnseignantDAO instance = null;

    public static SQLEnseignantDAO getInstance() {

        if (SQLEnseignantDAO.instance == null) {
            SQLEnseignantDAO.instance = new SQLEnseignantDAO();
        }

        return  SQLEnseignantDAO.instance;
    }

    private MySQLDAOFactory daoFactory;

    private  SQLEnseignantDAO() {

        this.daoFactory = new MySQLDAOFactory();
    }

    @Override
    public ArrayList<Enseignant> readAll() {

        ArrayList<Enseignant> listeEnseignant = new ArrayList<Enseignant>();

        Connection connection = this.daoFactory.getConnexion();
        String sql = "SELECT * FROM enseignant ORDER BY id_dpt ASC";

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                Enseignant enseignant = new Enseignant(res.getInt("id_ens"), res.getString("nom_ens"), res.getString("prenom_ens"));
                listeEnseignant.add(enseignant);
            }
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        } finally {
            this.daoFactory.fermeConnexion();
        }

        return listeEnseignant;
    }

    @Override
    public int create(Enseignant enseignant) {
        Connection connection = this.daoFactory.getConnexion();
        String insert = "INSERT INTO enseignant SET nom_ens = ? and prenom_ens = ?";

        int id = -1;
/*******************QUE SIGNIFIE LE STI ET A QUOI IL SERT*****************/
        try {
            PreparedStatement stI = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stI.setString(1, enseignant.getNomEnseignant());
            stI.executeUpdate();

            ResultSet cle = stI.getGeneratedKeys();
            if (cle.next()) {
                id = cle.getInt(1);
                enseignant.setIdEnseignant(id);
            }
            stI.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }

        return id;
    }

    @Override
    public boolean update(Enseignant enseignant) {
        Connection connection = this.daoFactory.getConnexion();
        String update = "UPDATE departement SET nom_ens = ?, prenom_ens WHERE id_ens= ?";

        boolean updated = false;

        try {
            PreparedStatement stU = connection.prepareStatement(update);

            stU.setString(1, enseignant.getNomEnseignant());
            stU.setString (2, enseignant.getPrenomEnseignant());
            stU.setInt(3, enseignant.getIdEnseignant());
            stU.execute();

            updated = stU.getUpdateCount() > 0;
            stU.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        }

        return updated;
    }
/*
    @Override
    public boolean delete(Enseignant enseignant) {
        Connection connection = this.daoFactory.getConnexion();
        String delete = "DELETE FROM enseignant where id_ens = ?";

        boolean deleted = false;

        try {
            PreparedStatement stD = connection.prepareStatement(delete);

            stD.setInt(1, departement.getIdDepartement());
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
*/
}
