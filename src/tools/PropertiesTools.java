package tools;

import dao.mysql.connexion.ConnexionException;
import metier.Abonnement;
import metier.Client;
import metier.MappingProperty;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class PropertiesTools {

    public static Properties lireFichierProperties(String propertiesPath) {
        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream fichierProperties = classLoader.getResourceAsStream(propertiesPath);


        if (fichierProperties == null) {
            throw new ConnexionException("Le fichier properties " + propertiesPath + " est introuvable.");
        }

        try {
            properties.load(fichierProperties);
            return properties;
        } catch (IOException e) {
            throw new ConnexionException("Impossible de charger le fichier properties " + propertiesPath, e);
        }
    }


}
