package metier;

public class Revue implements EntityBase {
    public static String ID_REVUE = "idRevue";
    public static String TITRE = "titre";
    public static String DESCRIPTION = "dscp";
    public static String TARIF = "tarifNumero";
    public static String VISUEL = "visuel";
    public static String ID_PERIODICITE = "idPeriodicite";


    private int idRevue;
    private String titre;
    private String dscp;
    private float tarifNumero;
    private String visuel;
    private int idPeriodicite;
    private Periodicite periodicite;

    private Revue revueClone;

    public Revue() {
    }


    public Revue(String titre, String dscp, float tarifNumero, int idPeriodicite) {
        this(-1, titre, dscp, tarifNumero, null, idPeriodicite);
    }

    public Revue(String titre, String dscp, float tarifNumero, String visuel, int idPeriodicite) {

        this(-1, titre, dscp, tarifNumero, visuel, idPeriodicite);
    }

    public Revue(int idRevue, String titre, String dscp, float tarifNumero, String visuel, int idPeriodicite) {
        super();
        setIdRevue(idRevue);
        setTitre(titre);
        setDscp(dscp);
        setTarifNumero(tarifNumero);
        setVisuel(visuel);
        setIdPeriodicite(idPeriodicite);
    }

    @Override
    public void cloneEntity() {
        revueClone = new Revue();
        revueClone.setIdRevue(idRevue);
        revueClone.setTitre(titre);
        revueClone.setDscp(dscp);
        revueClone.setTarifNumero(tarifNumero);
        revueClone.setVisuel(visuel);
        revueClone.setIdPeriodicite(idPeriodicite);
    }

    @Override
    public void restoreEntity() {
        if(getIdRevue() == revueClone.getIdRevue()) {
            setTitre(revueClone.getTitre());
            setDscp(revueClone.getDscp());
            setTarifNumero(revueClone.getTarifNumero());
            setVisuel(revueClone.getVisuel());
            setIdPeriodicite(revueClone.getIdPeriodicite());
        }
    }

    public String getPeriodiciteLibelle() {
        return periodicite.getLibelle();
    }

    public Periodicite getPeriodicite(){return periodicite;}

    public void setPeriodicite(Periodicite periodicite) {
        this.periodicite = periodicite;
    }

    public int getIdRevue() {
        return idRevue;
    }

    public void setIdRevue(int idRevue) {
        this.idRevue = idRevue;
    }

    public String getDscp() {
        return dscp;
    }

    public void setDscp(String dscp) {
        if (dscp == null || dscp.trim().length() == 0) {
            throw new IllegalArgumentException("La saisie d'une description est obligatoire !");
        }

        this.dscp = dscp;
    }

    public String getVisuel() {
        return visuel;
    }

    public void setVisuel(String visuel) {
        this.visuel = visuel;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        if (titre == null || titre.trim().length() == 0) {
            throw new IllegalArgumentException("La saisie d'un titre est obligatoire !");
        }

        this.titre = titre;
    }


    public float getTarifNumero() {
        return tarifNumero;
    }

    public void setTarifNumero(float tarifNumero) {
        this.tarifNumero = tarifNumero;
    }



    public int getIdPeriodicite() {
        return idPeriodicite;
    }

    public void setIdPeriodicite(int idPeriodicite) {
        this.idPeriodicite = idPeriodicite;
    }


    public String messageConfirmation(String periodicite){
        return "Titre : "+titre+"\nDescription : "+dscp+"\nTarif au numéro : "+tarifNumero+"\nPériodicité : "+periodicite;
    }

    @Override
    public String toString() {
        return titre + " (" + tarifNumero + " euros)";
    }


    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Revue && ((Revue) obj).getIdRevue() == this.getIdRevue());
    }

    @Override
    public Object getId() {
        return idRevue;
    }

    @Override
    public void setId(Object o) {
        setIdRevue((Integer) o);
    }
}
