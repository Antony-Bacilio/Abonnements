package dao.normalisation;

import dao.interfaces.RevueDAO;
import javafx.scene.control.Alert;
import metier.EntityBase;
import metier.MappingProperty;
import metier.Revue;

import java.util.HashMap;
import java.util.List;

import static dao.normalisation.NormaRevue.normalizDescrip;
import static dao.normalisation.NormaRevue.normalizTitre;

public class ValidationRevue extends Validation {

    private RevueDAO revueDAO;
    private Revue revue;

    public ValidationRevue(RevueDAO revueDAO) {
        super();
        this.revueDAO = revueDAO;
    }

    @Override
    public boolean estValide(EntityBase entityBase) {
        revue = (Revue) entityBase;
        if (!NormaliserEntity()) {
            return false;
        }
        return !existeDeja();
    }


    protected boolean NormaliserEntity() {
        StringBuilder sb = new StringBuilder();

        String titre = normalizTitre(revue.getTitre());
        if (isEmpty(titre)) {
            sb.append("le titre n'est pas valide").append("\n");
        } else {
            revue.setTitre(titre);
        }

        String description = normalizDescrip(revue.getDscp());
        if (isEmpty(description)) {
            sb.append("la description n'est pas valide").append("\n");
        } else {
            revue.setDscp(description);
        }

        if (sb.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Le revue n'est pas valide");
            alert.setContentText(sb.toString() + "L'opération ne sera pas finalisée");
            alert.showAndWait();
            return false;
        }
        return true;
    }


    protected boolean existeDeja() {


        HashMap<MappingProperty, Object> criteres = new HashMap<MappingProperty, Object>();
        criteres.put(revueDAO.getMappingProperty(Revue.TITRE), revue.getTitre());
        List<Revue> revueList = revueDAO.getList(criteres);

        if (revueList.size() == 1) {
            if (revueList.get(0).equals(revue)) return false;
        }
        if (revueList.size() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Le revue : " + revue.getTitre() + " existe déja");
            alert.setContentText("L'opération ne sera pas finalisée");
            alert.showAndWait();
            return true;
        } else {
            return false;
        }
    }


}
