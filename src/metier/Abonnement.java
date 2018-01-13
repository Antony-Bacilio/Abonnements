package metier;


import java.sql.Date;
import java.time.LocalDate;

public class Abonnement {
    public static String ID_CLIENT = "idClient";
    public static String ID_REVUE = "idRevue";
    public static String DATE_DEBUT = "dateDebut";
    public static String DATE_FIN = "dateFin";

    private int idClient;
    private int idRevue;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Revue revue;
    private String revueTitre;

    public Abonnement() {
    }

    public Abonnement(int idClient, int idRevue, LocalDate dateDebut, LocalDate dateFin) {
        this.idClient = idClient;
        this.idRevue = idRevue;
        setDateDebut(dateDebut);
        setDateFin(dateFin);
    }

    public Revue getRevue() {
        return revue;
    }

    public void setRevue(Revue revue) {
        this.revue = revue;
        this.revueTitre = revue.getTitre();
    }

    public String getRevueTitre() {
        return revueTitre;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdRevue() {
        return idRevue;
    }

    public void setIdRevue(int idRevue) {
        this.idRevue = idRevue;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        if (dateDebut == null) {
            throw new IllegalArgumentException("La saisie d'une date de début est obligatoire !");
        }
        this.dateDebut = dateDebut;
    }

    // Les 2 methodes suivantes sont appelees par un invoke
    //Obligé de passer par Date (en non LocalDate) lorsqu'une recherche est effectuee sur la date
    public void setDateDebut(Date dateDebut) {
        setDateDebut(dateDebut.toLocalDate());
    }

    public void setDateFin(Date dateFin) {
        setDateFin(dateFin.toLocalDate());
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        if (dateFin == null) {
            throw new IllegalArgumentException("La saisie d'une date de fin est obligatoire !");
        }
        this.dateFin = dateFin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Abonnement)) return false;

        Abonnement that = (Abonnement) o;

        if (idClient != that.idClient) return false;
        if (idRevue != that.idRevue) return false;
        if (dateDebut != null ? !dateDebut.equals(that.dateDebut) : that.dateDebut != null) return false;
        return dateFin != null ? dateFin.equals(that.dateFin) : that.dateFin == null;
    }

    @Override
    public int hashCode() {
        int result = idClient;
        result = 31 * result + idRevue;
        result = 31 * result + (dateDebut != null ? dateDebut.hashCode() : 0);
        result = 31 * result + (dateFin != null ? dateFin.hashCode() : 0);
        return result;
    }

    public String messageConfirmation() {
        return "Revue : " + revue.getTitre() + "\nDate de début : " + dateDebut.toString() +
                "\nDate de fin : " + dateFin.toString();
    }
}
