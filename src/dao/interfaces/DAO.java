package dao.interfaces;

import metier.MappingProperty;

import java.util.HashMap;
import java.util.List;

public interface DAO<T> {

    void create(T object);

    void update(T object);

    void delete(T object);

    T getById(int id);

    List<T> getAll();

    List<T> getList(HashMap<MappingProperty, Object> criteriaList);

    void setMappingPropertyList();

    MappingProperty getMappingProperty(String name);
}
