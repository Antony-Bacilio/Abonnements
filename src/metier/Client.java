package metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Client implements EntityBase {
    public static String ID_CLIENT = "idClient";
    public static String NOM = "nom";
    public static String PRENOM = "prenom";
    public static String NO_VOIE = "noVoie";
    public static String VOIE = "voie";
    public static String CODE_POSTAL = "codePostal";
    public static String VILLE = "ville";
    public static String PAYS = "pays";

    private int idClient;
    private String nom;
    private String prenom;
    private String noVoie;
    private String voie;
    private String codePostal;
    private String ville;
    private String pays;
    private HashMap<Revue, DateAbonnement> abonnement = new HashMap<Revue, DateAbonnement>();

    private Client clientClone;

    public Client() {
    }

    public Client(String nom, String prenom, String noVoie, String voie, String codePostal, String ville, String pays) {
        this(-1, nom, prenom, noVoie, voie, codePostal, ville, pays);
    }


    public Client(int idClient, String nom, String prenom, String noVoie, String voie, String codePostal, String ville, String pays) {
        setIdClient(idClient);
        setNom(nom);
        setPrenom(prenom);
        setNoVoie(noVoie);
        setVoie(voie);
        setCodePostal(codePostal);
        setVille(ville);
        setPays(pays);
        //this.abonnement = new HashMap<Revue, DateAbonnement>();
    }

    @Override
    public Object getId() {
        return idClient;
    }

    public void setId(int id) {
        this.idClient = id;
    }

    @Override
    public void setId(Object o) {
        setIdClient((Integer) o);
    }

    @Override
    public void cloneEntity() {
        clientClone = new Client();
        clientClone.setIdClient(idClient);
        clientClone.setNom(nom);
        clientClone.setPrenom(prenom);
        clientClone.setNoVoie(noVoie);
        clientClone.setVoie(voie);
        clientClone.setCodePostal(codePostal);
        clientClone.setVille(ville);
        clientClone.setPays(pays);
    }

    @Override
    public void restoreEntity() {
        if (getIdClient() == clientClone.getIdClient()) {
            setNom(clientClone.getNom());
            setPrenom(clientClone.getPrenom());
            setNoVoie(clientClone.getNoVoie());
            setVoie(clientClone.getVoie());
            setCodePostal(clientClone.getCodePostal());
            setVille(clientClone.getVille());
            setPays(clientClone.getPays());
        }
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        if (nom.trim().length() == 0) {
            throw new IllegalArgumentException("La saisie d'un nom est obligatoire !");
        }
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        if (prenom.trim().length() == 0) {
            throw new IllegalArgumentException("La saisie d'un prénom est obligatoire !");
        }
        this.prenom = prenom;
    }

    public String getNoVoie() {
        return noVoie;
    }

    public void setNoVoie(String noVoie) {
        this.noVoie = noVoie;
    }

    public String getVoie() {
        return voie;
    }

    public void setVoie(String voie) {
        this.voie = voie;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {

        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {

        this.pays = pays;
    }

    public HashMap<Revue, DateAbonnement> getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(HashMap<Revue, DateAbonnement> abonnement) {
        this.abonnement = abonnement;
    }


    @Override
    public String toString() {
        String msg = "{" +
                "idClient=" + idClient +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", noVoie='" + noVoie + '\'' +
                ", voie='" + voie + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", ville='" + ville + '\'' +
                ", pays='" + pays + '\'' +
                "}\nListe des abonnements :";

        StringBuilder sb = new StringBuilder();
        sb.append(msg);
        sb.append("\n");
        if (abonnement != null) {
            for (Revue key : abonnement.keySet()) {
                sb.append(key.toString() + abonnement.get(key).toString());
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Client && ((Client) obj).getIdClient() == this.getIdClient());
    }

    public Revue getRevueById(Object id) {
        // nouvelle instance de revue apres relecture du client
        for (Revue key : abonnement.keySet()) {
            if (key.getId() == id) return key;
        }
        return null;
    }

    public String messageConfirmation() {
        return "Nom : " + nom + "\nPrénom : " + prenom + "\nNuméro voie : " + noVoie + "\nNom voie : " + voie + "\nVille : " + ville + "\nCode Postal : " + codePostal
                + "\nPays : " + pays;

    }

    public List<Abonnement> getAbonnementList() {

        ArrayList<Abonnement> abonnementList = new ArrayList<Abonnement>();
        if (this.getAbonnement() != null) {
            for (Map.Entry<Revue, DateAbonnement> entry : this.getAbonnement().entrySet()) {
                if (!entry.getValue().toString().isEmpty()) {
                    Abonnement abonnement = new Abonnement();
                    abonnement.setRevue(entry.getKey());
                    abonnement.setIdClient(this.idClient);
                    abonnement.setIdRevue(entry.getKey().getIdRevue());
                    abonnement.setDateDebut(entry.getValue().getDateDebut());
                    abonnement.setDateFin(entry.getValue().getDateFin());
                    abonnementList.add(abonnement);
                }
            }
        }
        return abonnementList;
    }
}
