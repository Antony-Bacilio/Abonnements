package graphic.window;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;

public class Window {

    private static ClassLoader classLoader;
    private static URL fxmlURL;
    private static FXMLLoader fxmlLoader;
    private static TabPane masterPane;
    public static Stage primaryStage;

    public static void start(Stage stage) {
        try {
            primaryStage = stage;
            classLoader = Thread.currentThread().getContextClassLoader();
            fxmlURL = classLoader.getResource("graphic/window/Window.fxml");
            fxmlLoader = new FXMLLoader(fxmlURL);
            masterPane = fxmlLoader.load();


            ajoutTab("Client", "graphic/window/tabclient/TabClient.fxml");
            ajoutTab("Revue", "graphic/window/tabrevue/TabRevue.fxml");
            ajoutTab("Périodicité", "graphic/window/tabperiodicite/TabPeriodicite.fxml");
            ajoutTab("Options", "graphic/window/taboptions/TabOptions.fxml");


            Scene scene = new Scene((TabPane) masterPane, 1500, 900);
            stage.setScene(scene);
            stage.setTitle("Revues On Line");
            final URL imageURL = Window.class.getResource("/graphic/resources/logoApp.png");
            final Image image = new Image(imageURL.toExternalForm());

            stage.getIcons().add(image);
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void ajoutTab(String titre, String url) {
        try {
            fxmlURL = classLoader.getResource(url);
            fxmlLoader = new FXMLLoader(fxmlURL);
            GridPane clientPane = fxmlLoader.load();
            Tab tab = new Tab();
            tab.setText(titre);
            tab.setContent(clientPane);

            masterPane.getTabs().add(tab);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
