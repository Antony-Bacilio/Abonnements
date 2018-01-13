package graphic.client;

import graphic.Controller;
import graphic.View;


public class NewClientView extends View {
    protected NewClientController controller;


    public NewClientView(boolean create) {
        super("NewClient.fxml", "Nouveau Client", 850, 325);
        if (!create) {
            this.setTitle("Modification Client");
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
        this.controller = (NewClientController) controller;
    }
}

