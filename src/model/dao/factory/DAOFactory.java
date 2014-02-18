package model.dao.factory;

public abstract class DAOFactory {

    public static DAOFactory getDAOFactory(Persistance bdd) {

        DAOFactory daoF = null;

        switch (bdd) {
            case MYSQL:
                daoF = new MySQLDAOFactory();
                break;
            case XML:
                daoF = new XMLDAOFactory();
                break;
        }

        return daoF;
    }

}
