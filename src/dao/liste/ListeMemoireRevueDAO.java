package dao.liste;

import dao.factory.DAOFactory;
import dao.factory.DAOManager;
import dao.interfaces.RevueDAO;
import dao.normalisation.ValidationRevue;
import metier.*;

import java.util.ArrayList;

public class ListeMemoireRevueDAO extends ListeMemoireDAO<Revue> implements RevueDAO {
    private static ListeMemoireRevueDAO instance;

    private ListeMemoireRevueDAO() {
        super(Revue.class);
        this.validation = new ValidationRevue(this);
        this.donnes = new ArrayList<Revue>();
        Revue revue = new Revue(1, "Mickey", "Jeunesse", 1, null, 1);
        Periodicite periodicite = DAOFactory.getDAOFactory(DAOManager.getInstance()).getPeriodiciteDAO().getById(revue.getIdPeriodicite());
        revue.setPeriodicite(periodicite);

        this.donnes.add(revue);
        setMappingPropertyList();
    }

    public static ListeMemoireRevueDAO getInstance() {
        if (instance == null) {
            instance = new ListeMemoireRevueDAO();
        }
        return instance;
    }

    public void setMappingPropertyList() {
        MappingProperty mappingProperty;
        mappingProperty = new MappingProperty(Revue.ID_REVUE, Revue.ID_REVUE, int.class);
        mappingProperty.setIdProperty(true);
        mappingPropertyList.add(mappingProperty);
        mappingProperty = new MappingProperty(Revue.TITRE, Revue.TITRE, String.class);
        mappingPropertyList.add(mappingProperty);
        mappingProperty = new MappingProperty(Revue.DESCRIPTION, Revue.DESCRIPTION, String.class);
        mappingPropertyList.add(mappingProperty);
        mappingProperty = new MappingProperty(Revue.TARIF, Revue.TARIF, float.class);
        mappingPropertyList.add(mappingProperty);
        mappingProperty = new MappingProperty(Revue.VISUEL, Revue.VISUEL, String.class);
        mappingPropertyList.add(mappingProperty);
        mappingProperty = new MappingProperty(Revue.ID_PERIODICITE, Revue.ID_PERIODICITE, int.class);
        mappingPropertyList.add(mappingProperty);
    }

    @Override
    public void create(Revue object) {
        super.create(object);
        //on ajoute le libelle de periodicite pour qu'il soit accessible d'une revue (mauvaise conception de classe Revue)
        Periodicite periodicite = DAOFactory.getDAOFactory(DAOManager.getInstance()).getPeriodiciteDAO().getById(object.getIdPeriodicite());
        object.setPeriodicite(periodicite);

    }

    @Override
    public void update(Revue object) {
        super.update(object);
        //on ajoute le libelle de periodicite pour qu'il soit accessible d'une revue (mauvaise conception de classe Revue)
        Periodicite periodicite = DAOFactory.getDAOFactory(DAOManager.getInstance()).getPeriodiciteDAO().getById(object.getIdPeriodicite());
        object.setPeriodicite(periodicite);

    }
}
