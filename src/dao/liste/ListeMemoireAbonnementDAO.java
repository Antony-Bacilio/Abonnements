package dao.liste;

import dao.factory.DAOFactory;
import dao.factory.DAOManager;
import dao.interfaces.AbonnementDAO;
import dao.interfaces.ClientDAO;
import metier.Abonnement;
import metier.Client;
import metier.MappingProperty;
import metier.Revue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ListeMemoireAbonnementDAO extends ListeMemoireDAO<Abonnement> implements AbonnementDAO {

    private static ListeMemoireAbonnementDAO instance;

    public ListeMemoireAbonnementDAO() {
        super(Abonnement.class);
        this.donnes = new ArrayList<Abonnement>();

        setMappingPropertyList();
    }

    public static ListeMemoireAbonnementDAO getInstance() {
        if (instance == null) {
            instance = new ListeMemoireAbonnementDAO();
        }
        return instance;
    }

    public void setMappingPropertyList() {
        MappingProperty mappingProperty;
        mappingProperty = new MappingProperty(Abonnement.ID_CLIENT, Abonnement.ID_CLIENT, int.class);
        mappingPropertyList.add(mappingProperty);
        mappingProperty = new MappingProperty(Abonnement.ID_REVUE, Abonnement.ID_REVUE, int.class);
        mappingPropertyList.add(mappingProperty);
        mappingProperty = new MappingProperty(Abonnement.DATE_DEBUT, Abonnement.DATE_DEBUT, LocalDate.class);
        mappingPropertyList.add(mappingProperty);
        mappingProperty = new MappingProperty(Abonnement.DATE_FIN, Abonnement.DATE_FIN, LocalDate.class);
        mappingPropertyList.add(mappingProperty);
    }


    @Override
    public List<Abonnement> getListAbonnementClients(HashMap<MappingProperty, Object> criteres) {

        ArrayList<Abonnement> abonnementList = new ArrayList<Abonnement>();
        Integer revueId = (Integer) criteres.values().toArray()[0];
        ClientDAO clientDAO = DAOFactory.getDAOFactory(DAOManager.getInstance()).getClientDAO();
        List<Client> clients = clientDAO.getAll();
        for(Client client : clients) {
            for(Abonnement abonnement :client.getAbonnementList()) {
                if(abonnement.getIdRevue() == revueId) {
                    abonnementList.add(abonnement);
                    break;
                }
            }
        }
        return abonnementList;
    }
}


