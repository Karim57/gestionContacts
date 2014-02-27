package model.dao.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.dao.MySQLConnect;
import org.jfree.data.category.DefaultCategoryDataset;

public class SQLStatsDAO {

    private static SQLStatsDAO instance = null;

    public static SQLStatsDAO getInstance() {
        if (SQLStatsDAO.instance == null) {
            SQLStatsDAO.instance = new SQLStatsDAO();
        }
        return SQLStatsDAO.instance;
    }

    private MySQLConnect connect;

    private SQLStatsDAO() {
        this.connect = new MySQLConnect();
    }

    public DefaultCategoryDataset getNbContactsParDpt() {

        DefaultCategoryDataset dcd = new DefaultCategoryDataset();
        Connection connection = this.connect.getConnexion();
        String sql = "SELECT id_formation, libelle_form, COUNT(id_contact) as nb "
                + "FROM renseignements r, formation f "
                + "WHERE f.id_form = r.id_formation "
                + "GROUP BY f.id_form";

        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                String libelle = res.getString("libelle_form");
                int nb = res.getInt("nb");
                dcd.addValue(nb, "Quelque chose", libelle);
            }
        } catch (SQLException se) {
            System.out.println("Erreur rq sql : " + se.getMessage());
        } finally {
            this.connect.fermeConnexion();
        }

        return dcd;
    }

}
