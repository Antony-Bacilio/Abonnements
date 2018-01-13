package dao.mysql.connexion;

import tools.PropertiesTools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connexion {

    private static final String FICHIER_PROPERTIES = "dao/mysql/connexion/connexion.properties";
    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_DRIVER = "driver";
    private static final String PROPERTY_NOM_UTILISATEUR = "nomutilisateur";
    private static final String PROPERTY_MOT_DE_PASSE = "motdepasse";

    private static Connexion instance;
    private Connection connection = null;

    private String url;
    private String nomUtilisateur;
    private String motDePasse;

    private Connexion() throws ConnexionException {
        Properties properties = PropertiesTools.lireFichierProperties(FICHIER_PROPERTIES);
        url = properties.getProperty(PROPERTY_URL);
        String driver = properties.getProperty(PROPERTY_DRIVER);
        nomUtilisateur = properties.getProperty(PROPERTY_NOM_UTILISATEUR);
        motDePasse = properties.getProperty(PROPERTY_MOT_DE_PASSE);
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new ConnexionException("Le driver est introuvable dans le classpath.", e);
        }
    }

    public static Connexion getInstance() {
        if (instance == null) {
            instance = new Connexion();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(url, nomUtilisateur, motDePasse);
        }
        return connection;
    }
}