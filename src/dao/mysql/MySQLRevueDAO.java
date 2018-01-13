package dao.mysql;

import dao.factory.DAOFactory;
import dao.factory.DAOManager;
import dao.interfaces.RevueDAO;
import dao.mysql.connexion.Connexion;
import dao.normalisation.ValidationRevue;
import metier.EntityBase;
import metier.MappingProperty;
import metier.Periodicite;
import metier.Revue;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLRevueDAO extends MySQLDAO<Revue> implements RevueDAO {
    private static MySQLRevueDAO ourInstance;

    private MySQLRevueDAO(Connexion connexion) {
        super(Revue.class);
        this.validation = new ValidationRevue(this);
        this.connexion = connexion;
        tableMap = "Revue";
        setMappingPropertyList();
    }

    public static MySQLRevueDAO getInstance() {
        if (ourInstance == null) {
            ourInstance = new MySQLRevueDAO(Connexion.getInstance());
        }
        return ourInstance;
    }

    public void setMappingPropertyList() {
        MappingProperty mappingProperty;
        mappingProperty = new MappingProperty(Revue.ID_REVUE, "id_revue", int.class);
        mappingProperty.setIdProperty(true);
        mappingPropertyList.add(mappingProperty);
        mappingProperty = new MappingProperty(Revue.TITRE, "titre", String.class);
        mappingPropertyList.add(mappingProperty);
        mappingProperty = new MappingProperty(Revue.DESCRIPTION, "description", String.class);
        mappingPropertyList.add(mappingProperty);
        mappingProperty = new MappingProperty(Revue.TARIF, "tarif_numero", float.class);
        mappingPropertyList.add(mappingProperty);
        mappingProperty = new MappingProperty(Revue.VISUEL, "visuel", String.class);
        mappingPropertyList.add(mappingProperty);
        mappingProperty = new MappingProperty(Revue.ID_PERIODICITE, "id_periodicite", int.class);
        mappingPropertyList.add(mappingProperty);
    }

    @Override
    public Revue cast(Class<Revue> clazz, ResultSet resultSet) throws SQLException {
        Revue revue = super.cast(clazz, resultSet);
        Periodicite periodicite = DAOFactory.getDAOFactory(DAOManager.getInstance()).getPeriodiciteDAO().getById(revue.getIdPeriodicite());
        revue.setPeriodicite(periodicite);
        return revue;
    }

    @Override
    public void create(Revue object) {
        super.create(object);
        if (validation.estValide((EntityBase) object)) {
            //on ajoute le libelle de periodicite pour qu'il soit accessible d'une revue (mauvaise conception de classe Revue)
            Periodicite periodicite = DAOFactory.getDAOFactory(DAOManager.getInstance()).getPeriodiciteDAO().getById(object.getIdPeriodicite());
            object.setPeriodicite(periodicite);
        }
    }
}
