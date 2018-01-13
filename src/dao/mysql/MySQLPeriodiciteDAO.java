package dao.mysql;

import dao.interfaces.DAOException;
import dao.interfaces.PeriodiciteDAO;
import dao.mysql.connexion.Connexion;
import dao.normalisation.ValidationPeriodicite;
import metier.MappingProperty;
import metier.Periodicite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MySQLPeriodiciteDAO extends MySQLDAO<Periodicite> implements PeriodiciteDAO {

    private static MySQLPeriodiciteDAO ourInstance;


    private MySQLPeriodiciteDAO(Connexion connexion) {
        super(Periodicite.class);
        this.validation = new ValidationPeriodicite(this);
        this.connexion = connexion;
        tableMap = "Periodicite";
        setMappingPropertyList();
    }

    public static MySQLPeriodiciteDAO getInstance() {
        if (ourInstance == null) {
            ourInstance = new MySQLPeriodiciteDAO(Connexion.getInstance());
        }
        return ourInstance;
    }

    public void setMappingPropertyList() {
        MappingProperty mappingProperty;
        mappingProperty = new MappingProperty(Periodicite.ID_PERIODICITE, "id_periodicite", int.class);
        mappingProperty.setIdProperty(true);
        mappingPropertyList.add(mappingProperty);
        mappingProperty = new MappingProperty(Periodicite.LIBELLE, "libelle", String.class);
        mappingPropertyList.add(mappingProperty);
    }



    public Periodicite getByLibelle(String lib) {
        Connection cx = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Periodicite periodicite = null;
        try {
            cx = connexion.getConnection();
            String sqlSelectWhere = getSQLSelectWhere("libelle");
            preparedStatement = initialisationRequetePreparee(cx, sqlSelectWhere, false, lib);
            resultSet = preparedStatement.executeQuery();
    /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
            if (resultSet.next()) {
                periodicite = cast(Periodicite.class, resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return periodicite;
    }

}
