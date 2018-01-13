package dao.normalisation;

public class NormaRevue {

    public static String normalizTitre(String titre) {
        if (titre == null || titre.isEmpty()) return null;
        titre = titre.toLowerCase().trim();
        String lettre = titre.substring(0, 1);
        return lettre.toUpperCase() + titre.substring(1);
    }

    public static String normalizDescrip(String description) {
        if (description == null || description.isEmpty()) return null;
        return description.trim();
    }
}
