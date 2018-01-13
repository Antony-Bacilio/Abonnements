package graphic.revue;

import graphic.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import metier.Periodicite;
import metier.Revue;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class NewRevueController extends Controller {

    @FXML
    private TextField titre;
    @FXML
    private TextField description;
    @FXML
    private TextField tarif;
    @FXML
    private ComboBox<Periodicite> periodicite;


    @FXML
    private Button btnCreer;

    private ObservableList<Revue> list;

    private Revue revue = null;

    //------------------------- GETTERS / SETTERS -----------------------------------
    public Button getBtnCreer() {
        return btnCreer;
    }

    public void setBtnCreer(Button btnCreer) {
        this.btnCreer = btnCreer;
    }

    public ObservableList<Revue> getList() {
        return list;
    }

    public void setList(ObservableList<Revue> list) {
        this.list = list;
    }

    public Revue getRevue() {
        return revue;
    }

    public void setRevue(Revue revue) {
        this.revue = revue;
        this.titre.setText(revue.getTitre());
        this.description.setText(revue.getDscp());
        this.tarif.setText(String.valueOf(revue.getTarifNumero()));
        this.periodicite.getSelectionModel().select(revue.getPeriodicite());


    }


    //----------------------------------- METHODES -------------------------------------------
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        this.periodicite.getItems().clear();
        this.periodicite.setItems(FXCollections.observableArrayList(periodiciteDAO.getAll()));
    }


    public void creerRevue() {
        if (create) {
            try {
                revue = new Revue(titre.getText(), description.getText(), Float.parseFloat(tarif.getText()), (periodicite.getValue()).getIdPeriodicite());
                revue.setPeriodicite(periodicite.getValue());
            } catch (NumberFormatException e) {
                afficherErreur("La saisie d'un tarif est obligatoire.");
            } catch (IllegalArgumentException e) {
                afficherErreur(e.getMessage());
            } catch (RuntimeException e) {
                afficherErreur("La sélection d'une périodicité est obligatoire.");
            }
            if (revue != null) afficherConfirmationCreate(revue);
        } else {
            try {
                revue.setTitre(titre.getText());
                revue.setDscp(description.getText());
                revue.setTarifNumero(Float.parseFloat(tarif.getText()));
                revue.setIdPeriodicite(periodicite.getValue().getIdPeriodicite());
                revue.setPeriodicite(periodicite.getValue());
            } catch (IllegalArgumentException e) {
                afficherErreur(e.getMessage());
            }
            if (revue != null) afficherConfirmationUpdate(revue);
        }
    }

    private void afficherConfirmationUpdate(Revue revue) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(this.view);
        alert.setTitle("Confirmation la modification de la revue");
        alert.setHeaderText("Validez la modification de la recue");
        alert.setContentText(revue.messageConfirmation(revue.getPeriodicite().getLibelle()));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            revueDAO.update(revue);
            list.clear();
            list.setAll(revueDAO.getAll());
            getView().close();
        } else {
            alert.close();
        }
    }

    private void afficherConfirmationCreate(Revue revue) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(this.view);
        alert.setTitle("Confirmation ajout revue");
        alert.setHeaderText("Validez l'ajout de la revue");
        alert.setContentText(revue.messageConfirmation(revue.getPeriodicite().getLibelle()));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            revueDAO.create(revue);
            list.clear();
            list.setAll(revueDAO.getAll());
            getView().close();
        } else {
            alert.close();
        }
    }


}
