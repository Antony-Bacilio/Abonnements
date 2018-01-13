package dao.mysql.connexion;

public class ConnexionException extends RuntimeException {

    public ConnexionException(String message) {
        super(message);
    }

    public ConnexionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnexionException(Throwable cause) {
        super(cause);
    }

}
