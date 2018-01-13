package graphic;

import dao.factory.DAOFactory;
import dao.factory.DAOManager;
import dao.interfaces.AbonnementDAO;
import dao.interfaces.ClientDAO;
import dao.interfaces.PeriodiciteDAO;
import dao.interfaces.RevueDAO;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class Controller implements Initializable {
    protected RevueDAO revueDAO;
    protected PeriodiciteDAO periodiciteDAO;
    protected ClientDAO clientDAO;
    protected AbonnementDAO abonnementDAO;
    protected View view;
    public boolean create;   //true si create, false si update

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Controller() {
        periodiciteDAO = DAOFactory.getDAOFactory(DAOManager.getInstance()).getPeriodiciteDAO();
        revueDAO = DAOFactory.getDAOFactory(DAOManager.getInstance()).getRevueDAO();
        clientDAO = DAOFactory.getDAOFactory(DAOManager.getInstance()).getClientDAO();
        abonnementDAO = DAOFactory.getDAOFactory(DAOManager.getInstance()).getAbonnementDAO();
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public boolean isCreate() {
        return create;
    }

    public void setCreate(boolean create) {
        this.create = create;
    }

    protected void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(this.view);
        alert.setTitle("Erreur lors de la saisie");
        alert.setHeaderText("Un ou plusieurs champs sont mal remplis.");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
