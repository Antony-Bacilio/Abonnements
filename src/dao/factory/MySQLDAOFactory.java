package dao.factory;

import dao.interfaces.AbonnementDAO;
import dao.interfaces.ClientDAO;
import dao.interfaces.PeriodiciteDAO;
import dao.interfaces.RevueDAO;
import dao.mysql.MySQLAbonnementDAO;
import dao.mysql.MySQLClientDAO;
import dao.mysql.MySQLPeriodiciteDAO;
import dao.mysql.MySQLRevueDAO;

public class MySQLDAOFactory extends DAOFactory {

    @Override
    public ClientDAO getClientDAO() {
        return MySQLClientDAO.getInstance();
    }

    @Override
    public PeriodiciteDAO getPeriodiciteDAO() {
        return MySQLPeriodiciteDAO.getInstance();
    }

    @Override
    public RevueDAO getRevueDAO() {
        return MySQLRevueDAO.getInstance();
    }

    @Override
    public AbonnementDAO getAbonnementDAO() {
        return MySQLAbonnementDAO.getInstance();
    }

}
