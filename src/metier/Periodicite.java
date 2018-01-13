package metier;

public class Periodicite implements EntityBase {

    public static String LIBELLE = "libelle";
    public static String ID_PERIODICITE = "idPeriodicite";

    private int idPeriodicite;
    private String libelle;
    private Periodicite periodiciteClone;

    public Periodicite() {
    }

    public Periodicite(String libelle) {
        this(-1, libelle);
    }

    public Periodicite(int idPeriodicite, String libelle) {
        setIdPeriodicite(idPeriodicite);
        setLibelle(libelle);
    }

    @Override
    public void cloneEntity() {
        periodiciteClone= new Periodicite();
        periodiciteClone.setIdPeriodicite(idPeriodicite);
        periodiciteClone.setLibelle(libelle);
    }

    @Override
    public void restoreEntity() {
        if(getIdPeriodicite() == periodiciteClone.getIdPeriodicite()) {
            setLibelle(periodiciteClone.getLibelle());
        }
    }

    public int getIdPeriodicite() {
        return idPeriodicite;
    }

    public void setIdPeriodicite(int idPeriodicite) {
        this.idPeriodicite = idPeriodicite;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        if (libelle == null || libelle.trim().length() == 0) {
            throw new IllegalArgumentException("La saisie d'un libelle est obligatoire !");
        }

        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return libelle;
    }


    public String messageConfirmation() {
        return "Libelle : " + libelle;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Periodicite && ((Periodicite) obj).getIdPeriodicite() == this.getIdPeriodicite());
    }

    @Override
    public Object getId() {
        return idPeriodicite;
    }

    @Override
    public void setId(Object o) {
        setIdPeriodicite((Integer) o);
    }
}
