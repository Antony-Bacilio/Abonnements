package graphic.abonnement;

import graphic.Controller;
import graphic.View;

public class NewAbonnementView extends View {
    protected NewAbonnementController controller;

    public NewAbonnementView(boolean create) {
        super("NewAbonnement.fxml", "Nouveau Abonnement", 550, 200);
        if (!create) {
            this.setTitle("Modification Abonnement");
            controller.getBtnCreer().setText("Modifier");
            controller.setCreate(false);
        } else {
            controller.getBtnCreer().setText("Créer");
            controller.setCreate(true);
            controller.setComboBoxValues();
        }
    }


    @Override
    public Controller getController() {
        return controller;
    }

    @Override
    protected void setController(Controller controller) {
        this.controller = (NewAbonnementController) controller;
    }
}



