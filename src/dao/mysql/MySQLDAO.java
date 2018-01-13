package dao.mysql;

import dao.interfaces.DAO;
import dao.interfaces.DAOException;
import dao.mysql.connexion.Connexion;
import dao.normalisation.Validation;
import metier.EntityBase;
import metier.MappingProperty;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class MySQLDAO<T> implements DAO<T> {
    protected String tableMap;
    protected Connexion connexion;
    List<MappingProperty> idCompositeList = new ArrayList<MappingProperty>();
    List<MappingProperty> mappingPropertyList = new ArrayList<MappingProperty>();
    private Class clazz;
    protected Validation validation;

    public MySQLDAO(Class clazz) {
        this.clazz = clazz;
    }

    //retour la chaine (?,?,...) pour indiquer les valeurs
    private static String getSQLparamValues(List<MappingProperty> mappingPropertyList) {
        StringBuilder sb = new StringBuilder();
        for (MappingProperty mapentry : mappingPropertyList) {
            if (sb.length() == 0) {
                sb.append("");
            } else {
                sb.append(", ");
            }
            if (mapentry.getIdProperty()) {//id

                continue;
            }
            sb.append("?");
        }
        return sb.toString();
    }

    // retourne le nom des attributs de la classe sans l'id (nom, prenom ,...)
    private static String getSQLCol(List<MappingProperty> mappingPropertyList, boolean insertMode) {
        StringBuilder sb = new StringBuilder();
        for (MappingProperty mapentry : mappingPropertyList) {
            if (sb.length() == 0) {
                sb.append("");
            } else {
                sb.append(", ");
            }
            if ((insertMode && mapentry.getIdProperty())) {
                continue;
            }
            sb.append(mapentry.getColName());
        }
        return sb.toString();
    }

    private static String getSQLUpdateValue(List<MappingProperty> mappingPropertyList) {
        StringBuilder sb = new StringBuilder();
        for (MappingProperty mapentry : mappingPropertyList) {
            if (sb.length() == 0) {
                sb.append("");
            } else {
                sb.append(", ");
            }
            if (mapentry.getIdProperty()) {//id
                continue;
            }
            sb.append(mapentry.getColName());
            sb.append(" = ? ");

        }
        return sb.toString();
    }

    protected static PreparedStatement initialisationRequetePreparee(Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets) throws SQLException {
        PreparedStatement preparedStatement = connexion.prepareStatement(sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
        for (int i = 0; i < objets.length; i++) {
            preparedStatement.setObject(i + 1, objets[i]);
        }
        return preparedStatement;
    }

    protected static PreparedStatement initialisationRequetePrepareeByList(Connection connexion, String sql, boolean returnGeneratedKeys, ArrayList<Object> objets) throws SQLException {
        PreparedStatement preparedStatement = connexion.prepareStatement(sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
        for (int i = 0; i < objets.size(); i++) {
            preparedStatement.setObject(i + 1, objets.get(i));
        }
        return preparedStatement;
    }

    public List<MappingProperty> getMappingPropertyList() {
        return mappingPropertyList;
    }

    public void setMappingPropertyList(List<MappingProperty> mappingPropertyList) {
        this.mappingPropertyList = mappingPropertyList;
    }

    private String getSQLSelectById(String id) {
        StringBuilder sb = new StringBuilder();
        sb.append(getSQLSelect());
        sb.append(getSQLWhereClause(id));
        return sb.toString();
    }

    protected String getSQLInsert() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(tableMap);
        sb.append(" (");
        sb.append(getSQLCol(mappingPropertyList, true));
        sb.append(") VALUES ( ");
        sb.append(getSQLparamValues(mappingPropertyList));
        sb.append(")");
        return sb.toString();
    }

    protected String getSQLUpdate(String id) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(tableMap);
        sb.append(" SET ");
        sb.append(getSQLUpdateValue(mappingPropertyList));
        sb.append(getSQLWhereClause(id));
        return sb.toString();
    }

    protected String getSQLdelete(String id) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(tableMap);
        sb.append(getSQLWhereClause(id));
        return sb.toString();
    }

    protected String getSQLSelectWhere(Object... o) {
        StringBuilder sb = new StringBuilder();
        sb.append(getSQLSelect());
        sb.append(getSQLWhereClause(o));
        return sb.toString();
    }

    protected String getSQLSelectWhereByList(ArrayList<String> cols) {
        StringBuilder sb = new StringBuilder();
        sb.append(getSQLSelect());
        sb.append(getSQLWhereClauseByList(cols));
        return sb.toString();
    }


    protected String getSQLSelect() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(getSQLCol(mappingPropertyList, false));
        sb.append(" FROM ");
        sb.append(tableMap);
        return sb.toString();
    }

    protected String getSQLWhereClause(Object... o) {
        StringBuilder sb = new StringBuilder();
        for (Object clause : o) {
            if (sb.length() == 0) {
                sb.append(" WHERE ");
            } else {
                sb.append(" AND ");
            }
            sb.append(clause);
            sb.append(" = ?");
        }
        return sb.toString();
    }

    protected String getSQLWhereClauseByList(ArrayList<String> cols) {
        StringBuilder sb = new StringBuilder();
        for (Object clause : cols) {
            if (sb.length() == 0) {
                sb.append(" WHERE ");
            } else {
                sb.append(" AND ");
            }
            sb.append(clause);
            sb.append(" = ?");
        }
        return sb.toString();
    }

    //-----------CRUD-----------
    public void create(T entity) {
        if (validation.estValide((EntityBase) entity)) {
            Connection c = null;
            PreparedStatement preparedStatement = null;
            ResultSet valeursAutoGenerees = null;

            try {
                c = connexion.getConnection();
                String sqlInsert = getSQLInsert();
                preparedStatement = initialisationRequetePrepareeByList(c, sqlInsert, true, getInsertValues(entity));
                int statut = preparedStatement.executeUpdate();
                if (statut == 0) {
                    throw new DAOException("Échec de la création du entity, aucune ligne ajoutée dans la table.");
                }
                if (getIdColName() != null) {
                    valeursAutoGenerees = preparedStatement.getGeneratedKeys();
                    if (valeursAutoGenerees.next()) {
            /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
                        ((EntityBase) entity).setId(valeursAutoGenerees.getInt(1));
                    } else {
                        throw new DAOException("Échec de la création du entity en base, aucun ID auto-généré retourné.");
                    }
                }

            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

    public void update(T entity) {
        if (validation.estValide((EntityBase) entity)) {
            Connection cx = null;
            PreparedStatement preparedStatement = null;
            try {
                cx = connexion.getConnection();
                ArrayList<Object> values = getInsertValues(entity);
                values.add(((EntityBase) entity).getId());
                preparedStatement = initialisationRequetePrepareeByList(cx, getSQLUpdate(getIdColName()), false,
                        values);
                int statut = preparedStatement.executeUpdate();
                if (statut == 0) {
                    throw new DAOException("Échec de la modification du revue, aucune ligne ajoutée dans la table.");
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

    public void delete(T entity) {
        Connection cx = null;
        PreparedStatement preparedStatement = null;
        try {
            cx = connexion.getConnection();
            preparedStatement = initialisationRequetePreparee(cx, getSQLdelete(getIdColName()), false, ((EntityBase) entity).getId());
            int statut = preparedStatement.executeUpdate();
            if (statut == 0) {
                throw new DAOException("Échec de la suppression de l'entity: " + clazz.toString());
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

    }

    public T getById(int id) {
        Connection cx = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        T entity = null;
        try {

            cx = connexion.getConnection();
            String sqlSelectById = getSQLSelectById(getIdColName());
            preparedStatement = initialisationRequetePreparee(cx, sqlSelectById, false, id);
            resultSet = preparedStatement.executeQuery();
    /* Parcours de la ligne de donnees de l'éventuel ResulSet retourne */
            if (resultSet.next()) {
                entity = (T) cast(clazz, resultSet);
                ((EntityBase) entity).cloneEntity();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return entity;
    }

    public List<T> getAll() {
        Connection cx = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        T entity = null;
        List<T> entities = new ArrayList<T>();
        try {
            cx = connexion.getConnection();
            preparedStatement = initialisationRequetePreparee(cx, getSQLSelect(), false);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                entity = (T) cast(clazz, resultSet);
                entities.add((T) entity);
            }
            if (resultSet.next()) entity = (T) cast(clazz, resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return entities;
    }

    private List<T> findEntities(String sqlSelectWhere, ArrayList<Object> values) {
        Connection cx = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        T entity = null;
        List<T> entities = new ArrayList<T>();
        try {
            cx = connexion.getConnection();
            preparedStatement = initialisationRequetePrepareeByList(cx, sqlSelectWhere, false, values);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                entity = (T) cast(clazz, resultSet);
                entities.add((T) entity);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return entities;
    }

    public List<T> getList(HashMap<MappingProperty, Object> criteriaList) {

        ArrayList<String> columns = new ArrayList<String>();
        ArrayList<Object> values = new ArrayList<Object>();

        for (Map.Entry<MappingProperty, Object> entry : criteriaList.entrySet()) {
            if (!entry.getValue().toString().isEmpty()) {
                columns.add(entry.getKey().getColName());
                values.add(entry.getValue());
            }
        }


        String sqlSelectWhere = getSQLSelectWhereByList(columns);

        //--
        return findEntities(sqlSelectWhere, values);


    }
    //--------------------

    public T cast(Class<T> clazz, ResultSet resultSet) throws SQLException {
        T entity = null;
        try {
            entity = clazz.newInstance();
            for (MappingProperty mp : mappingPropertyList) {
                setValueProperty(entity, mp, resultSet);
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return entity;
    }

    private ArrayList<Object> getInsertValues(T entity) {
        ArrayList<Object> objects = new ArrayList<Object>();
        for (MappingProperty mp : mappingPropertyList) {
            if (mp.getIdProperty()) {
                continue;
            }
            try {
                objects.add(getValueProperty(entity, mp));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return objects;
    }

    private void setValueProperty(Object entity, MappingProperty mappingProperty, ResultSet resultSet) throws NoSuchMethodException, SQLException, InvocationTargetException, IllegalAccessException {
        Method methodSetProperty = mappingProperty.methodSetProperty(entity.getClass());
        methodSetProperty.invoke(entity, resultSet.getObject(mappingProperty.getColName()));
    }

    private Object getValueProperty(Object entity, MappingProperty mappingProperty) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method methodGetProperty = mappingProperty.methodGetProperty(entity.getClass());
        return methodGetProperty.invoke(entity, new Object[0]);
    }

    private String getIdColName() {
        for (MappingProperty mp : mappingPropertyList) {
            if (mp.getIdProperty()) {
                return mp.getColName();
            }
        }
        return null;
    }

    public MappingProperty getMappingProperty(String name) {
        for (MappingProperty mp : mappingPropertyList) {
            if (mp.getProperty().equals(name)) {
                return mp;
            }
        }
        return null;
    }

}
