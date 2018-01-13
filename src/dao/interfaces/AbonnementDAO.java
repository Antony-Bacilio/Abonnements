package dao.interfaces;

import metier.Abonnement;
import metier.MappingProperty;

import java.util.HashMap;
import java.util.List;

public interface AbonnementDAO extends DAO<Abonnement> {

    List<Abonnement> getListAbonnementClients(HashMap<MappingProperty, Object> criteres);
}
