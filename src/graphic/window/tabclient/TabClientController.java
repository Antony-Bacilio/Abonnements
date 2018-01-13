package graphic.window.tabclient;

import graphic.Controller;
import graphic.abonnement.NewAbonnementController;
import graphic.abonnement.NewAbonnementView;
import graphic.client.NewClientController;
import graphic.client.NewClientView;
import tools.csv.ClientCSVParser;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import metier.Abonnement;
import metier.Client;
import metier.MappingProperty;
import metier.Revue;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import static graphic.window.Window.primaryStage;

public class TabClientController extends Controller {

    @FXML
    public TableView<Abonnement> tabAbonnement;
    @FXML
    public TableColumn<Abonnement, String> colRevue;
    @FXML
    public TableColumn<Abonnement, String> colDebut;
    @FXML
    public TableColumn<Abonnement, String> colFin;
    //Table des clients
    @FXML
    protected TableView<Client> tabClient;
    @FXML
    protected TableColumn<Client, String> colNom;
    @FXML
    protected TableColumn<Client, String> colPrenom;
    @FXML
    protected TableColumn<Client, String> colVille;
    @FXML
    protected TableColumn<Client, String> colPays;
    //Rechercher Client
    @FXML
    protected TextField searchNom;
    @FXML
    protected TextField searchPrenom;
    @FXML
    protected TextField searchVille;
    @FXML
    protected TextField searchPays;
    //Details Client
    @FXML
    protected Label lblIdentite;
    @FXML
    protected Label lblAdresse;
    @FXML
    protected Label lblVille;
    @FXML
    protected Label lblCodePostal;
    @FXML
    protected Label lblPays;
    //Abonnements
    @FXML
    protected TitledPane zoneAbonnement;
    private Client selectedClient;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        zoneAbonnement.setVisible(false);
        initTableClient();
        initTableAbonnement();
        ajoutListenerClient();
        ajoutListenerAbonnement();

    }


    //----------------------------------------------- CLIENT ------------------------------------------------
    private void initTableClient() {
        colNom.setCellValueFactory(new PropertyValueFactory<Client, String>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<Client, String>("prenom"));
        colVille.setCellValueFactory(new PropertyValueFactory<Client, String>("ville"));
        colPays.setCellValueFactory(new PropertyValueFactory<Client, String>("pays"));
        tabClient.getColumns().setAll(colNom, colPrenom, colVille, colPays);
        tabClient.getItems().addAll(clientDAO.getAll());
    }

    //METHODES ASSOCIEES A UN BUTTON

    public void importerClients() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importer Client");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv")
        );

        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            ClientCSVParser.importerClients(file.getAbsolutePath());
            refreshTabClient();
            detailClient();
        }
    }

    public void creerClient() {
        NewClientView vueCreateClient = new NewClientView(true);
        ((NewClientController) vueCreateClient.getController()).setListClient(tabClient.getItems());
    }

    public void rechercherClient() {
        HashMap<MappingProperty, Object> criteres = new HashMap<MappingProperty, Object>();
        String nom = searchNom.getText();
        String prenom = searchPrenom.getText();
        String ville = searchVille.getText();
        String pays = searchPays.getText();

        if (!nom.isEmpty()) criteres.put(clientDAO.getMappingProperty(Client.NOM), nom);
        if (!prenom.isEmpty()) criteres.put(clientDAO.getMappingProperty(Client.PRENOM), prenom);
        if (!ville.isEmpty()) criteres.put(clientDAO.getMappingProperty(Client.VILLE), ville);
        if (!pays.isEmpty()) criteres.put(clientDAO.getMappingProperty(Client.PAYS), pays);

        tabClient.getItems().clear();
        tabClient.getItems().addAll(clientDAO.getList(criteres));
        detailClient();

    }

    public void reinitialiserRechercheClient() {
        searchNom.clear();
        searchPrenom.clear();
        searchVille.clear();
        searchPays.clear();
        refreshTabClient();
        detailClient();
    }
    //-------------------------------

    private void detailClient() {
        Client selectedClient = tabClient.getSelectionModel().getSelectedItem();
        if (selectedClient == null) {
            lblIdentite.setText("");
            lblAdresse.setText("");
            lblCodePostal.setText("");
            lblPays.setText("");
            lblVille.setText("");
        } else {
            //gérer si null ???
            lblIdentite.setText(selectedClient.getPrenom() + " " + selectedClient.getNom());
            lblAdresse.setText(selectedClient.getNoVoie() + " " + selectedClient.getVoie());
            lblCodePostal.setText(selectedClient.getCodePostal());
            lblPays.setText(selectedClient.getPays());
            lblVille.setText(selectedClient.getVille());
        }
    }

    private void refreshTabClient() {
        tabClient.getItems().clear();
        tabClient.getItems().addAll(clientDAO.getAll());
        zoneAbonnement.setVisible(false);
    }


    //LISTENERS
    private void ajoutListenerClient() {
        ajoutListenerChangementLigneClient();
        ajoutListenerClicDroitClient();
        ajoutListenerDoubleClickClient();
    }

    private void ajoutListenerChangementLigneClient() {
        tabClient.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedClient = clientDAO.getById(newValue.getIdClient());
                tabAbonnement.getItems().clear();
                tabAbonnement.getItems().setAll(selectedClient.getAbonnementList());
                zoneAbonnement.setVisible(true);
                detailClient();
            }
        }));
    }

    private void ajoutListenerClicDroitClient() {
        tabClient.setRowFactory(
                tableView -> {
                    final TableRow<Client> row = new TableRow<>();
                    final ContextMenu rowMenu = new ContextMenu();
                    MenuItem removeItem = new MenuItem("Delete");
                    removeItem.setId("rpt02");
                    removeItem.setOnAction((ActionEvent event) -> {
                        clientDAO.delete(row.getItem());
                        reinitialiserRechercheClient();
                    });
                    rowMenu.getItems().addAll(removeItem);

                    row.contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty())).then(rowMenu)
                            .otherwise((ContextMenu) null));
                    return row;
                });
    }

    private void ajoutListenerDoubleClickClient() {
        tabClient.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                Client client = tabClient.getSelectionModel().getSelectedItem();
                if (client != null){
                    NewClientView vueCreateClient = new NewClientView(false);
                    NewClientController newClientController = (NewClientController) vueCreateClient.getController();
                    newClientController.setListClient(tabClient.getItems());
                    newClientController.setClient(client);
                }

            }
        });
    }

    //-------------------------------------------- ABONNEMENT -------------------------------------------------
    private void initTableAbonnement() {
        colRevue.setCellValueFactory(new PropertyValueFactory<Abonnement, String>("revue"));
        colDebut.setCellValueFactory(new PropertyValueFactory<Abonnement, String>("dateDebut"));
        colFin.setCellValueFactory(new PropertyValueFactory<Abonnement, String>("dateFin"));
        tabAbonnement.getColumns().setAll(colRevue, colDebut, colFin);
    }

    public void creerAbonnement() {
        NewAbonnementView vueCreateAbonnement = new NewAbonnementView(true);
        NewAbonnementController newAbonnementController = (NewAbonnementController) vueCreateAbonnement.getController();
        newAbonnementController.setClient(selectedClient);
        newAbonnementController.setListAbonnement(tabAbonnement.getItems());
    }

    //LISTENERS

    private void ajoutListenerAbonnement() {
        ajoutListenerClicDroitAbonnement();
        ajoutListenerDoubleClickAbonnement();
    }

    private void ajoutListenerClicDroitAbonnement() {
        tabAbonnement.setRowFactory(
                tableView -> {
                    final TableRow<Abonnement> row = new TableRow<>();
                    final ContextMenu rowMenu = new ContextMenu();
                    MenuItem removeItem = new MenuItem("Delete");
                    removeItem.setId("rp02");
                    removeItem.setOnAction((ActionEvent event) -> {
                        Abonnement abonnement = row.getItem();
                        Client client = clientDAO.getById(abonnement.getIdClient());
                        Revue revue = revueDAO.getById(abonnement.getIdRevue());
                        clientDAO.deleteAbonnement(client, revue);
                        tabAbonnement.getItems().clear();
                        tabAbonnement.getItems().addAll(client.getAbonnementList());
                    });
                    rowMenu.getItems().addAll(removeItem);

                    row.contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty())).then(rowMenu)
                            .otherwise((ContextMenu) null));
                    return row;
                });
    }

    private void ajoutListenerDoubleClickAbonnement() {
        tabAbonnement.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                Abonnement abonnement = tabAbonnement.getSelectionModel().getSelectedItem();
                if (abonnement != null) {
                    NewAbonnementView vueCreateAbonnement = new NewAbonnementView(false);
                    NewAbonnementController newAbonnementController = (NewAbonnementController) vueCreateAbonnement.getController();
                    newAbonnementController.setListAbonnement(tabAbonnement.getItems());
                    newAbonnementController.setClient(selectedClient);
                    newAbonnementController.setAbonnement(abonnement);
                }
            }
        });
    }
}
