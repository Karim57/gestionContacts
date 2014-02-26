package model.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.business.Departement;
import model.business.Enseignant;
import model.dao.DAOInterface;
import model.dao.MySQLConnect;

public class SQLEnseignantDAO implements DAOInterface<Enseignant> {

    private static SQLEnseignantDAO instance = null;

    public static SQLEnseignantDAO getInstance() {
        if (SQLEnseignantDAO.instance == null) {
            SQLEnseignantDAO.instance = new SQLEnseignantDAO();
        }
        return SQLEnseignantDAO.instance;
    }

    private MySQLConnect connect;

    private SQLEnseignantDAO() {
        this.connect = new MySQLConnect();
    }

    @Override
    public ArrayList<Enseignant> readAll() {

        ArrayList<Enseignant> listeEnseignants = new ArrayList<Enseignant>();

        Connection connection = this.connect.getConnexion();
        String sql = "SELECT id_ens FROM enseignant";

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                Enseignant enseignant = this.readById(res.getInt("id_ens"));
                listeEnseignants.add(enseignant);
            }
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        } finally {
            this.connect.fermeConnexion();
        }

        return listeEnseignants;
    }

    @Override
    public Enseignant readById(int id) {

        Connection connection = this.connect.getConnexion();
        String sql = "SELECT * FROM enseignant e, departement d WHERE e.id_dpt = d.id_dpt AND id_enseignant = " + id;
        Enseignant enseignant = null;

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                Departement departement = SQLDepartementDAO.getInstance().readById(res.getInt("id_dpt"));
                enseignant = new Enseignant(res.getInt("id_ens"), res.getString("nom_ens"), res.getString("prenom_ens"), departement);
            }
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        } finally {
            this.connect.fermeConnexion();
        }

        return enseignant;
    }

    @Override
    public int create(Enseignant enseignant) {
        Connection connection = this.connect.getConnexion();
        String insert = "INSERT INTO enseignant SET nom_ens = ?, prenom_ens = ?, id_dpt = ?";

        int id = -1;

        try {
            PreparedStatement stI = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stI.setString(1, enseignant.getNomEnseignant());
            stI.setString(2, enseignant.getPrenomEnseignant());
            stI.setInt(3, enseignant.getDepartement().getIdDepartement());
            stI.executeUpdate();

            ResultSet cle = stI.getGeneratedKeys();
            if (cle.next()) {
                id = cle.getInt(1);
                enseignant.setIdEnseignant(id);
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
    public boolean update(Enseignant enseignant) {
        Connection connection = this.connect.getConnexion();
        String insert = "UPDATE enseignant SET nom_ens = ?, prenom_ens = ?, id_dpt = ? WHERE id_ens = ?";

        boolean updated = false;

        try {
            PreparedStatement stU = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stU.setString(1, enseignant.getNomEnseignant());
            stU.setString(2, enseignant.getPrenomEnseignant());
            stU.setInt(3, enseignant.getDepartement().getIdDepartement());
            stU.setInt(4, enseignant.getIdEnseignant());
            stU.execute();

            updated = stU.getUpdateCount() > 0;
            stU.close();
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        } finally {
            this.connect.fermeConnexion();
        }

        return updated;
    }

    @Override
    public boolean delete(Enseignant enseignant) {
        Connection connection = this.connect.getConnexion();
        String delete = "DELETE FROM enseignant WHERE id_ens = ?";

        boolean deleted = false;

        try {
            PreparedStatement stD = connection.prepareStatement(delete);

            stD.setInt(1, enseignant.getIdEnseignant());
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
    public void deleteList(ArrayList<Enseignant> liste) {
        for (Enseignant enseignant : liste) {
            this.delete(enseignant);
        }
    }

    @Override
    public boolean update(Enseignant objetAModifier, String libelle) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
