package graphic.abonnement;

import graphic.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import metier.Abonnement;
import metier.Client;
import metier.DateAbonnement;
import metier.Revue;

import java.util.Optional;

public class NewAbonnementController extends Controller {

    @FXML
    public ComboBox<Revue> revue;
    @FXML
    public DatePicker dateDebut;
    @FXML
    public DatePicker dateFin;


    @FXML
    private Button btnCreer;

    private Client client;

    private Abonnement abonnement = null;

    private ObservableList<Abonnement> listAbonnement;


    //------------------------- GETTERS / SETTERS -----------------------------------
    public ObservableList<Abonnement> getListAbonnement() {
        return listAbonnement;
    }

    public void setListAbonnement(ObservableList<Abonnement> listAbonnement) {
        this.listAbonnement = listAbonnement;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Button getBtnCreer() {
        return btnCreer;
    }

    public void setBtnCreer(Button btnCreer) {
        this.btnCreer = btnCreer;
    }

    public Abonnement getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(Abonnement abonnement) {
        this.abonnement = abonnement;
        revue.getSelectionModel().select(abonnement.getRevue());
        dateDebut.setValue(abonnement.getDateDebut());
        dateFin.setValue(abonnement.getDateFin());
    }


    //----------------------------------- METHODES -------------------------------------------

    public void creerAbonnement() {
        if (create) {
            try {
                if (!client.getAbonnement().containsKey(revue.getValue())) {
                    if (dateDebut.getValue().isAfter(dateFin.getValue())){
                        throw new IllegalArgumentException("La date de début doit être inférieure à la date de fin.");
                    }
                    abonnement = new Abonnement(client.getIdClient(), revue.getValue().getIdRevue(), dateDebut.getValue(), dateFin.getValue());
                    abonnement.setRevue(revueDAO.getById(revue.getValue().getIdRevue()));
                } else {
                    throw new IllegalArgumentException("Un abonnement à cette revue existe déjà !");
                }
            } catch (IllegalArgumentException e) {
                afficherErreur(e.getMessage());
            } catch (NullPointerException e) {
                afficherErreur("Une revue doit être sélectionnée !");
            }
            if (abonnement != null) afficherConfirmationCreate(abonnement);
        } else {
            try {
                if (dateDebut.getValue().isAfter(dateFin.getValue())){
                    throw new IllegalArgumentException("La date de début doit être inférieure à la date de fin.");
                }
                abonnement.setDateDebut(dateDebut.getValue());
                abonnement.setDateFin(dateFin.getValue());
                afficherConfirmationUpdate();
            } catch (IllegalArgumentException e) {
                afficherErreur(e.getMessage());
            }
        }
    }

    private void afficherConfirmationUpdate() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(this.view);
        alert.setTitle("Confirmation la modification de l'abonnement");
        alert.setHeaderText("Validez la modification de l'abonnement");
        alert.setContentText(abonnement.messageConfirmation());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            DateAbonnement dateAbonnement = new DateAbonnement(abonnement.getDateDebut(), abonnement.getDateFin());
            clientDAO.updateAbonnement(client, abonnement.getRevue(), dateAbonnement);
            listAbonnement.clear();
            listAbonnement.setAll(client.getAbonnementList());
            getView().close();
        } else {
            alert.close();
        }
    }

    private void afficherConfirmationCreate(Abonnement abonnement) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(this.view);
        alert.setTitle("Confirmation ajout de l'abonnement");
        alert.setHeaderText("Validez l'ajout de l'abonnement");
        alert.setContentText(abonnement.messageConfirmation());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            DateAbonnement dateAbonnement = new DateAbonnement(dateDebut.getValue(), dateFin.getValue());
            clientDAO.createAbonnement(client, revue.getValue(), dateAbonnement);
            listAbonnement.clear();
            listAbonnement.setAll(client.getAbonnementList());
            getView().close();
        } else {
            alert.close();
        }
    }

    protected void setComboBoxValues() {
        revue.setItems(FXCollections.observableArrayList(revueDAO.getAll()));
    }
}
