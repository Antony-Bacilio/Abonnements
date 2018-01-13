package graphic.periodicite;

import graphic.Controller;
import graphic.View;

public class NewPeriodiciteView extends View {
    protected NewPeriodiciteController controller;

    public NewPeriodiciteView(boolean create) {
        super("NewPeriodicite.fxml", "Nouvelle périodicité", 680, 150);
        if (!create) {
            this.setTitle("Modification périodicité");
            controller.getBtnCreer().setText("Modifier");
            controller.setCreate(false);
        } else {
            controller.getBtnCreer().setText("Créer");
            controller.setCreate(true);
        }
    }

    @Override
    public Controller getController() {
        return controller;
    }

    @Override
    protected void setController(Controller controller) {
        this.controller = (NewPeriodiciteController) controller;
    }
}
