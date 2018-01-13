package dao.mysql;

import dao.interfaces.AbonnementDAO;
import dao.interfaces.DAOException;
import dao.mysql.connexion.Connexion;
import metier.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MySQLAbonnementDAO extends MySQLDAO<Abonnement> implements AbonnementDAO {
    private static MySQLAbonnementDAO ourInstance;

    private MySQLAbonnementDAO(Connexion connexion) {
        super(Abonnement.class);
        this.connexion = connexion;
        tableMap = "Abonnement";
        setMappingPropertyList();
    }

    public static MySQLAbonnementDAO getInstance() {
        if (ourInstance == null) {
            ourInstance = new MySQLAbonnementDAO(Connexion.getInstance());
        }
        return ourInstance;
    }

    public void setMappingPropertyList() {
        MappingProperty mappingProperty;
        mappingProperty = new MappingProperty(Abonnement.ID_CLIENT, "id_client", int.class);
        mappingPropertyList.add(mappingProperty);
        idCompositeList.add(mappingProperty);
        mappingProperty = new MappingProperty(Abonnement.ID_REVUE, "id_revue", int.class);
        idCompositeList.add(mappingProperty);
        mappingPropertyList.add(mappingProperty);
        mappingProperty = new MappingProperty(Abonnement.DATE_DEBUT, "date_debut", Date.class);
        mappingPropertyList.add(mappingProperty);
        mappingProperty = new MappingProperty(Abonnement.DATE_FIN, "date_fin", Date.class);
        mappingPropertyList.add(mappingProperty);
    }


    public void create(Client client, Revue revue, DateAbonnement dateAbonnement) {
        Connection c = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            c = connexion.getConnection();
            int idClient = client.getIdClient();
            int idRevue = revue.getIdRevue();
            java.sql.Date debut = java.sql.Date.valueOf(dateAbonnement.getDateDebut());
            java.sql.Date fin = java.sql.Date.valueOf(dateAbonnement.getDateFin());
            ArrayList<Object> values = new ArrayList<Object>();
            values.add(idClient);
            values.add(idRevue);
            values.add(debut);
            values.add(fin);
            String sqlInsert = getSQLInsert();
            preparedStatement = initialisationRequetePrepareeByList(c, sqlInsert, false, values);
            int statut = preparedStatement.executeUpdate();
            if (statut == 0) {
                throw new DAOException("Échec de la création du entity, aucune ligne ajoutée dans la table.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public void update(Client client, Revue revue, DateAbonnement dateAbonnement) {
        Connection cx = null;
        PreparedStatement preparedStatement = null;
        try {
            cx = connexion.getConnection();
            java.sql.Date debut = java.sql.Date.valueOf(dateAbonnement.getDateDebut());
            java.sql.Date fin = java.sql.Date.valueOf(dateAbonnement.getDateFin());
            int idClient = client.getIdClient();
            int idRevue = revue.getIdRevue();
            String sqlUpdate = getSQLUpdate();
            preparedStatement = initialisationRequetePreparee(cx, sqlUpdate, false,
                    debut, fin, idClient, idRevue);
            int statut = preparedStatement.executeUpdate();
            if (statut == 0) {
                throw new DAOException("Échec de la modification du revue, aucune ligne ajoutée dans la table.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }


    public void delete(Client client, Revue revue) {
        Connection cx = null;
        PreparedStatement preparedStatement = null;
        try {
            cx = connexion.getConnection();
            int idClient = client.getIdClient();
            int idRevue = revue.getIdRevue();
            preparedStatement = initialisationRequetePreparee(cx, getSQLdelete(idCompositeList), false, idClient, idRevue);
            int statut = preparedStatement.executeUpdate();
            if (statut == 0) {
                throw new DAOException("Échec de la suppression de l'abonnement");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

    }

    public HashMap<Revue, DateAbonnement> getByClient(Client client) {
        Connection cx = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        HashMap<Revue, DateAbonnement> abonnements = new HashMap<Revue, DateAbonnement>();
        try {
            cx = connexion.getConnection();
            int idClient = client.getIdClient();
            preparedStatement = initialisationRequetePreparee(cx, getSQLSelectWhere("id_client"),
                    false, idClient);
            resultSet = preparedStatement.executeQuery();
    /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
            while (resultSet.next()) {
                DateAbonnement dateAbonnement = new DateAbonnement(resultSet.getDate(3).toLocalDate(), resultSet.getDate(4).toLocalDate());
                Revue revue = MySQLRevueDAO.getInstance().getById(resultSet.getInt(2));
                abonnements.put(revue, dateAbonnement);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return abonnements;
    }


    private String getSQLSelectWhere(String clause) {
        StringBuilder sb = new StringBuilder();
        sb.append(getSQLSelect());
        sb.append(getSQLWhereClause(clause));
        return sb.toString();
    }


    private String getSQLdelete(List<MappingProperty> idCompositeList) {
        String idClient = idCompositeList.get(0).getColName();
        String idRevue = idCompositeList.get(1).getColName();
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(tableMap);
        sb.append(getSQLWhereClause(idClient, idRevue));
        return sb.toString();
    }


    private String getSQLUpdate() {
        String nameIdClient = mappingPropertyList.get(0).getColName();
        String nameIdRevue = mappingPropertyList.get(1).getColName();
        String nameDateDebut = mappingPropertyList.get(2).getColName();
        String nameDateFin = mappingPropertyList.get(3).getColName();
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(tableMap);
        sb.append(" SET ");
        sb.append(nameDateDebut + " = ? , " + nameDateFin + " = ? ");
        sb.append(getSQLWhereClause(nameIdClient, nameIdRevue));
        return sb.toString();
    }

    @Override
    public List<Abonnement> getListAbonnementClients(HashMap<MappingProperty, Object> criteres) {
        return getList(criteres);
    }
}
