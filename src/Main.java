
import graphic.window.Window;
import tools.csv.ClientCSVParser;
import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Connexion.getInstance();

       // importerClient(primaryStage);
        Window.start(primaryStage);
    }

    public void importerClient(Stage primaryStage) {
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
        }
        //ClientCSVParser.importerClients("C:\\Project\\java\\schoirfer_cpoa\\src\\clients.csv");
    }
}


