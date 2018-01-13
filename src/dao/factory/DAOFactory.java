package dao.factory;

import dao.interfaces.AbonnementDAO;
import dao.interfaces.ClientDAO;
import dao.interfaces.PeriodiciteDAO;
import dao.interfaces.RevueDAO;

public abstract class DAOFactory {

    public static DAOFactory getDAOFactory(DAOManager daoManager) {
        Persistance persistance = daoManager.getPersistance();
        DAOFactory daoFactory = null;
        switch (persistance) {
            case MYSQL:
                return daoFactory = new MySQLDAOFactory();
            case LISTE:
                return daoFactory = new ListeMemoireDAOFactory();
            default:
                System.exit(0);
        }
        return daoFactory;
    }

    public abstract ClientDAO getClientDAO();

    public abstract PeriodiciteDAO getPeriodiciteDAO();

    public abstract RevueDAO getRevueDAO();

    public abstract AbonnementDAO getAbonnementDAO();

}

