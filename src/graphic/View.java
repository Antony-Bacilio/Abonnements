package graphic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;

public abstract class View extends Stage {


    protected abstract Controller getController();

    protected abstract void setController(Controller controller);

    protected View(boolean create, String viewXML, String viewTitle, int width, int height) {
        this(viewXML, viewTitle, width, height);
    }

    protected View(String viewXML, String viewTitle, int width, int height) {
        try {

            URL fxmlURL = getClass().getResource(viewXML);
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
            Node root = fxmlLoader.load();
            Scene scene = new Scene((VBox) root, width, height);
            this.setScene(scene);
            this.setTitle(viewTitle);
            this.setResizable(false);
            this.initModality(Modality.APPLICATION_MODAL);
            setController(fxmlLoader.getController());
            getController().setView(this);
            this.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
