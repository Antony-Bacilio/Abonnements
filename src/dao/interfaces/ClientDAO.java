package dao.interfaces;

import metier.Abonnement;
import metier.Client;
import metier.DateAbonnement;
import metier.Revue;

import java.util.List;

public interface ClientDAO extends DAO<Client> {

    public Client createAbonnement(Client client, Revue revue, DateAbonnement dateAbonnement);

    public Client updateAbonnement(Client client, Revue revue, DateAbonnement dateAbonnement);

    public Client deleteAbonnement(Client client, Revue revue);

}
