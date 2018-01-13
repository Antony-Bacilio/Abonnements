package dao.normalisation;

public class NormaPeriodicite {
    //revoie un chaine sans espace en miniscule
    private static String formatString(String s) {
        return s.toLowerCase().trim();
    }

    public static String normalizLibelle(String s) {
        String nom = formatString(s);
        String lettre = nom.substring(0,1);
        return lettre.toUpperCase() + nom.substring(1);
    }
}
