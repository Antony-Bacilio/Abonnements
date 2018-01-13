package graphic.window.tabperiodicite;

import graphic.Controller;
import graphic.periodicite.NewPeriodiciteController;
import graphic.periodicite.NewPeriodiciteView;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import metier.MappingProperty;
import metier.Periodicite;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class TabPeriodiciteController extends Controller {

    @FXML
    protected TableView<Periodicite> tabPeriodicite;
    @FXML
    protected TableColumn<Periodicite, String> colLibelle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ajoutListenerDoubleClickPeriodicite();
        ajoutListenerClicDroitPeriodicite();
        initTablePeriodicite();
    }

    private void initTablePeriodicite() {
        colLibelle.setCellValueFactory(new PropertyValueFactory<Periodicite, String>("libelle"));
        tabPeriodicite.getColumns().setAll(colLibelle);
        tabPeriodicite.getItems().addAll(periodiciteDAO.getAll());
    }

    private void refreshTabPeriodicite() {
        tabPeriodicite.getItems().clear();
        tabPeriodicite.getItems().setAll(periodiciteDAO.getAll());
    }

    public void creerPeriodicite() {
        NewPeriodiciteView vueCreatePeriodicite = new NewPeriodiciteView(true);
        NewPeriodiciteController newPeriodiciteController = (NewPeriodiciteController) vueCreatePeriodicite.getController();
        ((NewPeriodiciteController) vueCreatePeriodicite.getController()).setListPeriodicite(tabPeriodicite.getItems());
    }

    private void ajoutListenerDoubleClickPeriodicite() {
        tabPeriodicite.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                Periodicite periodicite = tabPeriodicite.getSelectionModel().getSelectedItem();
                if (periodicite != null) {
                    NewPeriodiciteView vueCreatePeriodicite = new NewPeriodiciteView(false);
                    NewPeriodiciteController newPeriodiciteController = (NewPeriodiciteController) vueCreatePeriodicite.getController();
                    newPeriodiciteController.setListPeriodicite(tabPeriodicite.getItems());
                    newPeriodiciteController.setPeriodicite(periodicite);
                }

            }
        });
    }

    private void ajoutListenerClicDroitPeriodicite() {
        tabPeriodicite.setRowFactory(
                tableView -> {
                    final TableRow<Periodicite> row = new TableRow<>();
                    final ContextMenu rowMenu = new ContextMenu();
                    MenuItem removeItem = new MenuItem("Supprimer");
                    removeItem.setId("rpt02");
                    removeItem.setOnAction((ActionEvent event) -> {
                        if (isDeletable(row.getItem())) {
                            periodiciteDAO.delete(row.getItem());
                            refreshTabPeriodicite();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Suppression impossible");
                            alert.setHeaderText("La periodicite " + row.getItem().getLibelle() + " est utilisée dans pour une revue");
                            alert.setContentText("L'opération ne sera pas finalisée");
                            alert.showAndWait();
                        }

                    });
                    rowMenu.getItems().addAll(removeItem);

                    row.contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty())).then(rowMenu)
                            .otherwise((ContextMenu) null));
                    return row;
                });
    }

    private boolean isDeletable(Periodicite periodicite) {
        HashMap<MappingProperty, Object> criteres = new HashMap<MappingProperty, Object>();
        int idPeriodicite = periodicite.getIdPeriodicite();
        criteres.put(periodiciteDAO.getMappingProperty(Periodicite.ID_PERIODICITE), idPeriodicite);
        return revueDAO.getList(criteres).isEmpty();
    }

}
