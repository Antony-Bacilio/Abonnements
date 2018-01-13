package graphic.revue;

import graphic.Controller;
import graphic.View;

public class NewRevueView extends View {
    protected NewRevueController controller;

    public NewRevueView(boolean create) {
        super("NewRevue.fxml", "Nouvelle revue", 600, 350);
        if (!create) {
            this.setTitle("Modification revue");
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
        this.controller = (NewRevueController) controller;
    }
}
