package dao.mysql;

import dao.interfaces.ClientDAO;
import dao.mysql.connexion.Connexion;
import dao.normalisation.ValidationClient;
import metier.Client;
import metier.DateAbonnement;
import metier.MappingProperty;
import metier.Revue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


public class MySQLClientDAO extends MySQLDAO<Client> implements ClientDAO {
    private static MySQLClientDAO ourInstance;

    private MySQLClientDAO(Connexion connexion) {
        super(Client.class);
        this.validation = new ValidationClient(this );
        this.connexion = connexion;
        tableMap = "Client";
        setMappingPropertyList();
    }

    public static MySQLClientDAO getInstance() {
        if (ourInstance == null) {
            ourInstance = new MySQLClientDAO(Connexion.getInstance());
        }
        return ourInstance;
    }

    public void setMappingPropertyList() {
        MappingProperty mappingProperty;
        mappingProperty = new MappingProperty(Client.ID_CLIENT, "id_client", int.class);
        mappingProperty.setIdProperty(true);
        mappingPropertyList.add(mappingProperty);

        mappingProperty = new MappingProperty(Client.NOM, "nom", String.class);
        mappingPropertyList.add(mappingProperty);

        mappingProperty = new MappingProperty(Client.PRENOM, "prenom", String.class);
        mappingPropertyList.add(mappingProperty);

        mappingProperty = new MappingProperty(Client.NO_VOIE, "no_rue", String.class);
        mappingPropertyList.add(mappingProperty);
        mappingProperty = new MappingProperty(Client.VOIE, "voie", String.class);
        mappingPropertyList.add(mappingProperty);
        mappingProperty = new MappingProperty(Client.CODE_POSTAL, "code_postal", String.class);
        mappingPropertyList.add(mappingProperty);
        mappingProperty = new MappingProperty(Client.VILLE, "ville", String.class);
        mappingPropertyList.add(mappingProperty);
        mappingProperty = new MappingProperty(Client.PAYS, "pays", String.class);
        mappingPropertyList.add(mappingProperty);
    }

    @Override
    public Client cast(Class<Client> clazz, ResultSet resultSet) throws SQLException {
        Client client = super.cast(clazz, resultSet);
        MySQLAbonnementDAO mySQLAbonnementDAO = MySQLAbonnementDAO.getInstance();
        client.setAbonnement(mySQLAbonnementDAO.getByClient(client));
        return client;
    }

    //-- Abonnement
    public Client createAbonnement(Client client, Revue revue, DateAbonnement dateAbonnement) {
        HashMap<Revue, DateAbonnement> abonnement = client.getAbonnement();
        if (!abonnement.containsKey(revue)) {
            abonnement.put(revue, dateAbonnement);
            MySQLAbonnementDAO mySQLAbonnementDAO = MySQLAbonnementDAO.getInstance();
            mySQLAbonnementDAO.create(client, revue, dateAbonnement);
            MySQLClientDAO mySQLClientDAO = MySQLClientDAO.getInstance();
            return mySQLClientDAO.getById(client.getIdClient());
        }
        return client;
    }

    public Client updateAbonnement(Client client, Revue revue, DateAbonnement dateAbonnement) {
        HashMap<Revue, DateAbonnement> abonnement = client.getAbonnement();
        if (abonnement.containsKey(revue)) {
            MySQLAbonnementDAO mySQLAbonnementDAO = MySQLAbonnementDAO.getInstance();
            mySQLAbonnementDAO.update(client, revue, dateAbonnement);
            MySQLClientDAO mySQLClientDAO = MySQLClientDAO.getInstance();
            return mySQLClientDAO.getById(client.getIdClient());
        }
        return client;
    }

    public Client deleteAbonnement(Client client, Revue revue) {
        HashMap<Revue, DateAbonnement> abonnement = client.getAbonnement();
        if (abonnement.containsKey(revue)) {
            MySQLAbonnementDAO mySQLAbonnementDAO = MySQLAbonnementDAO.getInstance();
            mySQLAbonnementDAO.delete(client, revue);
            MySQLClientDAO mySQLClientDAO = MySQLClientDAO.getInstance();
            return mySQLClientDAO.getById(client.getIdClient());
        }
        return client;
    }

}

