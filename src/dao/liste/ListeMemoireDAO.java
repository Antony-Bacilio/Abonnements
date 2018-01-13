package dao.liste;

import dao.interfaces.DAO;
import dao.interfaces.DAOException;
import dao.normalisation.Validation;
import metier.EntityBase;
import metier.MappingProperty;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ListeMemoireDAO<T> implements DAO<T> {
    protected List<T> donnes;
    protected Validation validation;
    private Class clazz;
    List<MappingProperty> mappingPropertyList = new ArrayList<MappingProperty>();

    ListeMemoireDAO(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public void create(T object) {
        if (validation.estValide((EntityBase) object)) {


            int dernierId = 0;
            if (donnes.size() > 0) {
                dernierId = (int) ((EntityBase) donnes.get(donnes.size() - 1)).getId();
            }
            ((EntityBase) object).setId(dernierId + 1);
            this.donnes.add(object);
        }
    }

    @Override
    public void update(T object) {
        if (validation.estValide((EntityBase) object)) {
            int idx = this.donnes.indexOf(object);
            if (idx == -1) {
                throw new DAOException("Tentative de modification d'un objet inexistant");
            } else {
                this.donnes.set(idx, object);
            }
        } else {
            ((EntityBase) object).restoreEntity();
        }

    }


    @Override
    public void delete(T object) {
        int idx = this.donnes.indexOf(object);
        if (idx == -1) {
            throw new DAOException("Tentative de suppression d'un objet inexistant");
        } else {
            this.donnes.remove(idx);
        }
    }

    @Override
    public T getById(int id) {
        for (T object : donnes) {

            if (id == (int) ((EntityBase) object).getId()) {
                ((EntityBase) object).cloneEntity();
                return object;
            }
        }
        return null;
    }

    @Override
    public List<T> getAll() {
        return donnes;
    }

    public List<T> getList(HashMap<MappingProperty, Object> criteriaList) {

        List<T> objectSelected = new ArrayList<T>();
        objectSelected.addAll(getAll());
        for (Map.Entry<MappingProperty, Object> entry : criteriaList.entrySet()) {
            MappingProperty key = entry.getKey();
            Object value = entry.getValue();
            if (!value.toString().isEmpty()) {
                objectSelected = appliquerCritere(objectSelected, key, value);
            }
        }
        return objectSelected;


    }

    private List<T> appliquerCritere(List<T> donnes, MappingProperty key, Object value) {
        ArrayList<T> donneTest = new ArrayList<T>();
        donneTest.addAll(donnes);
        for (T entity : donneTest) {

            Method methodGetProperty = null;
            try {
                methodGetProperty = key.methodGetProperty(clazz);
                Object objectValue = methodGetProperty.invoke(entity, new Object[0]);
                if (!objectValue.toString().equalsIgnoreCase(value.toString())) {
                    donnes.remove(entity);
                }

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return donnes;
    }

    public MappingProperty getMappingProperty(String name) {
        for (MappingProperty mp : mappingPropertyList) {
            if (mp.getProperty() == name) {
                return mp;
            }
        }
        return null;
    }
}
