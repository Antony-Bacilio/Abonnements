package dao.liste;

import dao.interfaces.PeriodiciteDAO;
import dao.normalisation.ValidationPeriodicite;
import metier.MappingProperty;
import metier.Periodicite;

import java.util.ArrayList;

public class ListeMemoirePeriodiciteDAO extends ListeMemoireDAO<Periodicite> implements PeriodiciteDAO {
    private static ListeMemoirePeriodiciteDAO instance;

    private ListeMemoirePeriodiciteDAO() {
        super(Periodicite.class);
        this.validation = new ValidationPeriodicite(this);
        this.donnes = new ArrayList<Periodicite>();
        this.donnes.add(new Periodicite(1, "Mensuel"));
        this.donnes.add(new Periodicite(2, "Quotidien"));
        setMappingPropertyList();
    }

    public static ListeMemoirePeriodiciteDAO getInstance() {
        if (instance == null) {
            instance = new ListeMemoirePeriodiciteDAO();
        }
        return instance;
    }

    public void setMappingPropertyList() {
        MappingProperty mappingProperty;
        mappingProperty = new MappingProperty(Periodicite.ID_PERIODICITE, Periodicite.ID_PERIODICITE, int.class);
        mappingProperty.setIdProperty(true);
        mappingPropertyList.add(mappingProperty);
        mappingProperty = new MappingProperty(Periodicite.LIBELLE, Periodicite.LIBELLE, String.class);
        mappingPropertyList.add(mappingProperty);
    }

}
