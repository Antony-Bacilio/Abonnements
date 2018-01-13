package dao.factory;

import tools.PropertiesTools;

import java.util.Properties;

public class DAOManager {
    private static DAOManager ourInstance = new DAOManager();
    public Persistance persistance = null;

    private DAOManager() {
    }

    public static DAOManager getInstance() {
        return ourInstance;
    }

    private static String readFile() {
        Properties properties = PropertiesTools.lireFichierProperties("dao/factory/persistance.properties");
        return properties.getProperty("persistance");
    }

    public Persistance getPersistance() {
        if (persistance == null) {
            setPersistance(Persistance.valueOf(readFile()));
        }
        return persistance;
    }

    public void setPersistance(Persistance persistance) {
        this.persistance = persistance;
    }

}
