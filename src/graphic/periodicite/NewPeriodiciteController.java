package graphic.periodicite;

import graphic.Controller;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import metier.Periodicite;

import java.util.Optional;

public class NewPeriodiciteController extends Controller {

    @FXML
    private TextField libelle;


    @FXML
    private Button btnCreer;

    private ObservableList<Periodicite> listPeriodicite;

    private Periodicite periodicite = null;

    //------------------------- GETTERS / SETTERS -----------------------------------
    public Button getBtnCreer() {
        return btnCreer;
    }

    public void setBtnCreer(Button btnCreer) {
        this.btnCreer = btnCreer;
    }

    public ObservableList<Periodicite> getListPeriodicite() {
        return listPeriodicite;
    }

    public void setListPeriodicite(ObservableList<Periodicite> listPeriodicite) {
        this.listPeriodicite = listPeriodicite;
    }

    public Periodicite getPeriodicite() {
        return periodicite;
    }

    public void setPeriodicite(Periodicite periodicite) {
        this.periodicite = periodicite;
        this.libelle.setText(periodicite.getLibelle());
    }

    //----------------------------------- METHODES -------------------------------------------
    public void creerPeriodicite() {
        if (create) {
            try {
                periodicite = new Periodicite(libelle.getText());
            } catch (IllegalArgumentException e) {
                afficherErreur(e.getMessage());
            }
            if (periodicite != null) afficherConfirmationCreate();
        } else {
            try {
                periodicite.setLibelle(libelle.getText());
            } catch (IllegalArgumentException e) {
                afficherErreur(e.getMessage());
            }
            if (periodicite != null) afficherConfirmationUpdate();
        }
    }

    private void afficherConfirmationCreate() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(this.view);
        alert.setTitle("Confirmation ajout périodicité");
        alert.setHeaderText("Validez l'ajout de la périodicité");
        alert.setContentText(periodicite.messageConfirmation());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            periodiciteDAO.create(periodicite);
            listPeriodicite.clear();
            listPeriodicite.setAll(periodiciteDAO.getAll());
            getView().close();
        } else {
            alert.close();
        }
    }

    private void afficherConfirmationUpdate() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(this.view);
        alert.setTitle("Confirmation la modification de la périodicité");
        alert.setHeaderText("Validez la modification de la périodicité");
        alert.setContentText(periodicite.messageConfirmation());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            periodiciteDAO.update(periodicite);
            listPeriodicite.clear();
            listPeriodicite.setAll(periodiciteDAO.getAll());
            getView().close();
        } else {
            alert.close();
        }
    }
}

