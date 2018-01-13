package graphic.window.tabrevue;

import graphic.Controller;
import graphic.revue.NewRevueController;
import graphic.revue.NewRevueView;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import metier.Abonnement;
import metier.MappingProperty;
import metier.Periodicite;
import metier.Revue;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class TabRevueController extends Controller {

    //Table Revue
    @FXML
    protected TableView<Revue> tabRevue;
    @FXML
    protected TableColumn<Revue, String> colTitre;
    @FXML
    protected TableColumn<Revue, Float> colTarif;
    @FXML
    protected TableColumn<Revue, Periodicite> colPeriodicite;

    //Recherche REvue
    @FXML
    protected TextField searchTitre;
    @FXML
    protected TextField searchTarif;
    @FXML
    protected ComboBox<Periodicite> searchPeriodicite;

    //Details Revue
    @FXML
    protected Label lblTitre;
    @FXML
    protected Label lblTarif;
    @FXML
    protected Label lblEuro;
    @FXML
    protected Label lblPeriodicite;
    @FXML
    protected Text lblDscp;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ajoutListenerChangementLigneRevue();
        ajoutListenerClicDroitRevue();
        ajoutListenerDoubleClickRevue();
        initTable();
        searchPeriodicite.setItems(FXCollections.observableArrayList(periodiciteDAO.getAll()));
    }

    private void initTable() {
        colTitre.setCellValueFactory(new PropertyValueFactory<Revue, String>("titre"));
        colTarif.setCellValueFactory(new PropertyValueFactory<Revue, Float>("tarifNumero"));
        colPeriodicite.setCellValueFactory(new PropertyValueFactory<Revue, Periodicite>("periodiciteLibelle"));
        tabRevue.getColumns().setAll(colTitre, colTarif, colPeriodicite);
        tabRevue.getItems().addAll(revueDAO.getAll());
    }

    //METHODES ASSOCIEES A UN BUTTON
    public void creerRevue() {
        NewRevueView vueCreateRevue = new NewRevueView(true);
        ((NewRevueController) vueCreateRevue.getController()).setList(tabRevue.getItems());
    }

    public void rechercherRevue() {
        Float trf = 0f;
        tabRevue.getItems().clear();
        HashMap<MappingProperty, Object> criteres = new HashMap<MappingProperty, Object>();
        String titre = searchTitre.getText();
        String tarif = searchTarif.getText();
        try {
            trf = Float.parseFloat(tarif);
        } catch (NumberFormatException e) {
            e.getMessage();
        }
        if (searchPeriodicite.getValue() != null) {
            int periodicite = searchPeriodicite.getValue().getIdPeriodicite();
            criteres.put(periodiciteDAO.getMappingProperty(Periodicite.ID_PERIODICITE), periodicite);
        }

        if (!tarif.isEmpty()) criteres.put(revueDAO.getMappingProperty(Revue.TARIF), trf);

        if (!titre.isEmpty()) criteres.put(revueDAO.getMappingProperty(Revue.TITRE), titre);

        tabRevue.getItems().addAll(revueDAO.getList(criteres));
        detailRevue();
    }

    public void reinitialiserRechercheRevue() {
        searchTitre.clear();
        searchTarif.clear();
        searchPeriodicite.getSelectionModel().clearSelection();
        refreshTabRevue();
        detailRevue();
    }
    // ----------------------------------------------

    private void detailRevue() {
        Revue selectedRevue = tabRevue.getSelectionModel().getSelectedItem();
        if (selectedRevue == null) {
            lblTitre.setText("");
            lblTarif.setText("");
            lblPeriodicite.setText("");
            lblDscp.setText("");
            lblEuro.setVisible(false);
        } else {
            //gérer si null ???
            lblTitre.setText(selectedRevue.getTitre());
            lblTarif.setText(String.valueOf(selectedRevue.getTarifNumero()));
            lblPeriodicite.setText(selectedRevue.getPeriodiciteLibelle());
            lblDscp.setText(selectedRevue.getDscp());
            lblEuro.setVisible(true);
        }
    }

    private void refreshTabRevue() {
        tabRevue.getItems().clear();
        tabRevue.getItems().addAll(revueDAO.getAll());
    }

    public void refreshComboPeriodicite() {
        searchPeriodicite.getItems().clear();
        searchPeriodicite.setItems(FXCollections.observableArrayList(periodiciteDAO.getAll()));
    }

    // LISTENERS
    private void ajoutListenerChangementLigneRevue() {
        tabRevue.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                detailRevue();
            }
        }));
    }

    private void ajoutListenerDoubleClickRevue() {
        tabRevue.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                Revue revue = tabRevue.getSelectionModel().getSelectedItem();
                if (revue != null) {
                    NewRevueView vueCreateRevue = new NewRevueView(false);
                    NewRevueController newRevueController = (NewRevueController) vueCreateRevue.getController();
                    newRevueController.setList(tabRevue.getItems());
                    newRevueController.setRevue(revue);
                }
            }
        });
    }

    private void ajoutListenerClicDroitRevue() {
        tabRevue.setRowFactory(
                tableView -> {
                    final TableRow<Revue> row = new TableRow<>();
                    final ContextMenu rowMenu = new ContextMenu();
                    MenuItem removeItem = new MenuItem("Delete");
                    removeItem.setId("rpt02");
                    removeItem.setOnAction((ActionEvent event) -> {
                        if (isDeletable(row.getItem())) {
                            revueDAO.delete(row.getItem());
                            refreshTabRevue();
                            detailRevue();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Suppression impossible");
                            alert.setHeaderText("Un client est abonné à la revue " + row.getItem().getTitre() + "!");
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

    private boolean isDeletable(Revue revue) {
        HashMap<MappingProperty, Object> criteres = new HashMap<MappingProperty, Object>();
        int idRevue = revue.getIdRevue();

        criteres.put(revueDAO.getMappingProperty(Revue.ID_REVUE), idRevue);
        //if (abonnementDAO.getAll());

        List<Abonnement> abonnementList = abonnementDAO.getListAbonnementClients(criteres);

        return abonnementDAO.getListAbonnementClients(criteres).isEmpty();
    }
}
