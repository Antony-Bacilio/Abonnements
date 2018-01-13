package dao.normalisation;

import dao.interfaces.ClientDAO;
import javafx.scene.control.Alert;
import metier.Client;
import metier.EntityBase;
import metier.MappingProperty;

import java.util.HashMap;
import java.util.List;

import static dao.normalisation.NormaClient.*;

public class ValidationClient extends Validation {

    private ClientDAO clientDAO;
    private Client client;

    public ValidationClient(ClientDAO clientDAO) {
        super();
        this.clientDAO = clientDAO;
    }

    @Override
    public boolean estValide(EntityBase entityBase) {
        client = (Client) entityBase;
        if (!NormaliserEntity()) {
            return false;
        }
        return !existeDeja();
    }


    protected boolean NormaliserEntity() {
        StringBuilder sb = new StringBuilder();

        String nom = NormalizNom(client.getNom());
        if (isEmpty(nom)) {
            sb.append("le nom n'est pas valide").append("\n");
        } else {
            client.setNom(nom);
        }

        String prenom = NormalizNom(client.getPrenom());
        if (isEmpty(prenom)) {
            sb.append("le prenom n'est pas valide").append("\n");
        } else {
            client.setPrenom(prenom);
        }


        String noVoie = normalizNumRue(client.getNoVoie());
        if (noVoie == null) {
            sb.append("le n° de voie n'est pas valide").append("\n");
        } else {
            client.setNoVoie(noVoie);
        }

        String voie = nomralizNomVoie(client.getVoie());
        if (voie == null) {
            sb.append("la voie n'est pas valide").append("\n");
        } else {
            client.setVoie(voie);
        }

        String ville = normalizVille(client.getVille());
        if (ville == null) {
            sb.append("la ville n'est pas valide").append("\n");
        } else {
            client.setVille(ville);
        }

        String pays = normalizPays(client.getPays());
        if (pays == null) {
            sb.append("le pays n'est pas valide").append("\n");
        } else {
            client.setPays(pays);
        }

        String codePostal = normalizCodePostal(client.getCodePostal(), client.getPays());
        if (codePostal == null) {
            sb.append("le code postal n'est pas valide").append("\n");
        } else {
            client.setCodePostal(codePostal);
        }
        if (sb.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Le client n'est pas valide");
            alert.setContentText(sb.toString() + "L'opération ne sera pas finalisée");
            alert.showAndWait();
            return false;
        }
        return true;
    }


    protected boolean existeDeja() {
        HashMap<MappingProperty, Object> criteres = new HashMap<MappingProperty, Object>();
        criteres.put(clientDAO.getMappingProperty(Client.NOM), client.getNom());
        criteres.put(clientDAO.getMappingProperty(Client.PRENOM), client.getPrenom());
        criteres.put(clientDAO.getMappingProperty(Client.NO_VOIE), client.getNoVoie());
        criteres.put(clientDAO.getMappingProperty(Client.VOIE), client.getVoie());
        criteres.put(clientDAO.getMappingProperty(Client.VILLE), client.getVille());
        criteres.put(clientDAO.getMappingProperty(Client.PAYS), client.getPays());
        criteres.put(clientDAO.getMappingProperty(Client.CODE_POSTAL), client.getCodePostal());
        List<Client> clientList = clientDAO.getList(criteres);

        if (clientList.size() == 1) {
            if (clientList.get(0).equals(client)) return false;
        }
        if (clientList.size() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Le client : " + client.getNom() + " " + client.getPrenom() + " existe déja");
            alert.setContentText("L'opération ne sera pas finalisée");
            alert.showAndWait();
            return true;
        } else {
            return false;
        }
    }


}
