package graphic.window.taboptions;

import dao.factory.DAOManager;
import dao.factory.Persistance;
import graphic.Controller;
import graphic.window.Window;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class TabOptionsController extends Controller {
    final ToggleGroup persistance = new ToggleGroup();
    @FXML
    RadioButton bdd;
    @FXML
    RadioButton liste;

    @FXML
    ImageView imgLogo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        final URL imageURL = getClass().getResource("../../resources/imgLogo.png");
        final Image image = new Image(imageURL.toExternalForm());
        imgLogo.setImage(image);


        bdd.setToggleGroup(persistance);
        bdd.setUserData(Persistance.MYSQL);
        liste.setToggleGroup(persistance);
        liste.setUserData(Persistance.LISTE);
        if (DAOManager.getInstance().getPersistance() == Persistance.MYSQL) {
            bdd.setSelected(true);
        } else {
            liste.setSelected(true);
        }

        persistance.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (persistance.getSelectedToggle() != null) {
                Persistance newPersistance = (Persistance) persistance.getSelectedToggle().getUserData();
                if (newPersistance == Persistance.MYSQL) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.initOwner(this.view);
                    alert.setTitle("Confirmation choix persistance");
                    alert.setHeaderText("Validez choix Base de données");
                    alert.setContentText("Veuillez vous assurer que la connexion à la base de données est possible.");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        DAOManager.getInstance().setPersistance(newPersistance);
                        Window.start(Window.primaryStage);
                    } else {
                        liste.setSelected(true);
                        alert.close();
                    }
                } else {
                    DAOManager.getInstance().setPersistance(newPersistance);
                    Window.start(Window.primaryStage);
                }

            }
        });

    }
}
