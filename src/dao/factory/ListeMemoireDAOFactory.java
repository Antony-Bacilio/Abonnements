package dao.factory;

import dao.interfaces.AbonnementDAO;
import dao.interfaces.ClientDAO;
import dao.interfaces.PeriodiciteDAO;
import dao.interfaces.RevueDAO;
import dao.liste.ListeMemoireAbonnementDAO;
import dao.liste.ListeMemoireClientDAO;
import dao.liste.ListeMemoirePeriodiciteDAO;
import dao.liste.ListeMemoireRevueDAO;

public class ListeMemoireDAOFactory extends DAOFactory {

    @Override
    public ClientDAO getClientDAO() {
        return ListeMemoireClientDAO.getInstance();
    }

    @Override
    public PeriodiciteDAO getPeriodiciteDAO() {
        return ListeMemoirePeriodiciteDAO.getInstance();
    }

    @Override
    public RevueDAO getRevueDAO() {
        return ListeMemoireRevueDAO.getInstance();
    }

    @Override
    public AbonnementDAO getAbonnementDAO() {
        return ListeMemoireAbonnementDAO.getInstance();
    }

}
