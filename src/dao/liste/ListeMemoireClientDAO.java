package dao.liste;

import dao.factory.DAOFactory;
import dao.factory.DAOManager;
import dao.interfaces.ClientDAO;
import dao.normalisation.ValidationClient;
import metier.Client;
import metier.DateAbonnement;
import metier.MappingProperty;
import metier.Revue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class ListeMemoireClientDAO extends ListeMemoireDAO<Client> implements ClientDAO {

    private static ListeMemoireClientDAO instance;

    private ListeMemoireClientDAO() {
        super(Client.class);
        this.validation = new ValidationClient(this);
        this.donnes = new ArrayList<Client>();
        Client client = new Client(1, "Dupont", "Jean", "5", "rue de Paris", "93000", "Paris", "France");
        Revue revue = DAOFactory.getDAOFactory(DAOManager.getInstance()).getRevueDAO().getById(1);
        DateAbonnement dateAbonnement = new DateAbonnement(LocalDate.now(), LocalDate.now());
        createAbonnement(client, revue, dateAbonnement);
        this.donnes.add(client);

        client = new Client(2, "Walter", "Michel", "6", "rue de la Liberation", "57000", "Metz", "France");
        revue = DAOFactory.getDAOFactory(DAOManager.getInstance()).getRevueDAO().getById(1);
        dateAbonnement = new DateAbonnement(LocalDate.now(), LocalDate.now());
        createAbonnement(client, revue, dateAbonnement);
        this.donnes.add(client);
        setMappingPropertyList();
    }

    public static ListeMemoireClientDAO getInstance() {
        if (instance == null) {
            instance = new ListeMemoireClientDAO();
        }
        return instance;
    }

    public void setMappingPropertyList() {
        MappingProperty mappingProperty;
        mappingProperty = new MappingProperty(Client.ID_CLIENT, Client.ID_CLIENT, int.class);
        mappingProperty.setIdProperty(true);
        mappingPropertyList.add(mappingProperty);
        mappingProperty = new MappingProperty(Client.NOM, Client.NOM, String.class);
        mappingPropertyList.add(mappingProperty);
        mappingProperty = new MappingProperty(Client.PRENOM, Client.PRENOM, String.class);
        mappingPropertyList.add(mappingProperty);
        mappingProperty = new MappingProperty(Client.NO_VOIE, Client.NO_VOIE, String.class);
        mappingPropertyList.add(mappingProperty);
        mappingProperty = new MappingProperty(Client.VOIE, Client.VOIE, String.class);
        mappingPropertyList.add(mappingProperty);
        mappingProperty = new MappingProperty(Client.CODE_POSTAL, Client.CODE_POSTAL, String.class);
        mappingPropertyList.add(mappingProperty);
        mappingProperty = new MappingProperty(Client.VILLE, Client.VILLE, String.class);
        mappingPropertyList.add(mappingProperty);
        mappingProperty = new MappingProperty(Client.PAYS, Client.PAYS, String.class);
        mappingPropertyList.add(mappingProperty);
    }


    public Client deleteAbonnement(Client client, Revue revue) {
        HashMap<Revue, DateAbonnement> abonnement = client.getAbonnement();
        if (abonnement.containsKey(revue)) {
            abonnement.remove(revue);
        }
        return client;
    }

    public Client updateAbonnement(Client client, Revue revue, DateAbonnement dateAbonnement) {
        HashMap<Revue, DateAbonnement> abonnement = client.getAbonnement();
        if (abonnement.containsKey(revue)) {
            abonnement.remove(revue);

            abonnement.put(revue, dateAbonnement);
        }
        return client;
    }

    public Client createAbonnement(Client client, Revue revue, DateAbonnement dateAbonnement) {
        HashMap<Revue, DateAbonnement> abonnement = client.getAbonnement();
        if (!abonnement.containsKey(revue)) {
            abonnement.put(revue, dateAbonnement);
        }
        return client;
    }
}
