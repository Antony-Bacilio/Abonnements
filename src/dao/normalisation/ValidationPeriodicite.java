package dao.normalisation;

import dao.interfaces.PeriodiciteDAO;
import javafx.scene.control.Alert;
import metier.EntityBase;
import metier.MappingProperty;
import metier.Periodicite;

import java.util.HashMap;
import java.util.List;



public class ValidationPeriodicite extends Validation {

    private PeriodiciteDAO periodiciteDAO;
    private Periodicite periodicite;


    public ValidationPeriodicite(PeriodiciteDAO periodiciteDAO) {
        super();
        this.periodiciteDAO = periodiciteDAO;
    }

    @Override
    public boolean estValide(EntityBase entityBase) {
        periodicite = (Periodicite) entityBase;
        if (!NormaliserEntity()) {
            return false;
        }
        return !existeDeja();
    }


    protected boolean NormaliserEntity() {
        StringBuilder sb = new StringBuilder();

        String titre = NormaPeriodicite.normalizLibelle(periodicite.getLibelle());
        if (isEmpty(titre)) {
            sb.append("le titre n'est pas valide").append("\n");
        } else {
            periodicite.setLibelle(titre);
        }

        if (sb.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Le periodicité n'est pas valide");
            alert.setContentText(sb.toString() + "L'opération ne sera pas finalisée");
            alert.showAndWait();
            return false;
        }
        return true;
    }


    protected boolean existeDeja() {


        HashMap<MappingProperty, Object> criteres = new HashMap<MappingProperty, Object>();
        criteres.put(periodiciteDAO.getMappingProperty(Periodicite.LIBELLE), periodicite.getLibelle());
        List<Periodicite> periodiciteList = periodiciteDAO.getList(criteres);

        if (periodiciteList.size() == 1) {
            if (periodiciteList.get(0).equals(periodicite)) return false;
        }
        if (periodiciteList.size() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Création impossible");
            alert.setHeaderText("La periodicite : " + periodicite.getLibelle() + " existe déja");
            alert.setContentText("L'opération ne sera pas finalisée");
            alert.showAndWait();
            return true;
        } else {
            return false;
        }
    }


}
