package graphic.client;

import graphic.Controller;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import metier.Client;

import java.util.Optional;

public class NewClientController extends Controller {

    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField voie;
    @FXML
    private TextField noVoie;
    @FXML
    private TextField ville;
    @FXML
    private TextField codePostal;
    @FXML
    private TextField pays;


    @FXML
    private Button btnCreer;

    private Client client = null;

    private ObservableList<Client> listClient;


    //------------------------- GETTERS / SETTERS -----------------------------------
    public ObservableList<Client> getListClient() {
        return listClient;
    }

    public void setListClient(ObservableList<Client> listClient) {
        this.listClient = listClient;
    }

    public Button getBtnCreer() {
        return btnCreer;
    }

    public void setBtnCreer(Button btnCreer) {
        this.btnCreer = btnCreer;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
        this.nom.setText(client.getNom());
        this.prenom.setText(client.getPrenom());
        this.noVoie.setText(client.getNoVoie());
        this.voie.setText(client.getVoie());
        this.ville.setText(client.getVille());
        this.codePostal.setText(client.getCodePostal());
        this.pays.setText(client.getPays());
    }

    //----------------------------------- METHODES -------------------------------------------
    public void creerClient() {
        if (create) {
            try {
                client = new Client(nom.getText(), prenom.getText(), noVoie.getText(), voie.getText(), codePostal.getText(), ville.getText(), pays.getText());
            } catch (IllegalArgumentException e) {
                afficherErreur(e.getMessage());
            }
            if (client != null) afficherConfirmationCreate(client);
        } else {
            try {
                client.setNom(nom.getText());
                client.setPrenom(prenom.getText());
                client.setNoVoie(noVoie.getText());
                client.setVoie(voie.getText());
                client.setCodePostal(codePostal.getText());
                client.setVille(ville.getText());
                client.setPays(pays.getText());
            } catch (IllegalArgumentException e) {
                afficherErreur(e.getMessage());
            }
            if (client != null) afficherConfirmationUpdate();
        }

    }

    private void afficherConfirmationCreate(Client client) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(this.view);
        alert.setTitle("Confirmation ajout client");
        alert.setHeaderText("Validez l'ajout du client");
        alert.setContentText(client.messageConfirmation());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            clientDAO.create(client);
            listClient.clear();
            listClient.setAll(clientDAO.getAll());
            getView().close();
        } else {
            alert.close();
        }
    }

    private void afficherConfirmationUpdate() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(this.view);
        alert.setTitle("Confirmation la modification du Client");
        alert.setHeaderText("Validez la modification du Client");
        alert.setContentText(client.messageConfirmation());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            clientDAO.update(client);
            listClient.clear();
            listClient.setAll(clientDAO.getAll());
            getView().close();
        } else {
            alert.close();
        }
    }
}
