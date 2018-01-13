package dao.normalisation;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NormaClient {

    //revoie un chaine sans espace en miniscule
    private static String formatString(String s) {
        return s.toLowerCase().trim();
    }

    public static String NormalizNom(String s) {
        String nom = formatString(s);
        String lettre = nom.substring(0,1);
        return lettre.toUpperCase() + nom.substring(1);
    }

    public static String normalizPays(String pays) {
        if (pays == null || pays.isEmpty()) return "";
        pays = formatString(pays);
        ArrayList<String> listReplace = new ArrayList<String>();

        listReplace.add("letzebuerg");
        listReplace.add("luxembourg");
        for (String test : listReplace) {
            if (pays.equalsIgnoreCase(test)) return "Luxembourg";
        }

        listReplace.add("belgium");
        listReplace.add("belgique");
        for (String test : listReplace) {
            if (pays.equalsIgnoreCase(test)) return "Belgique";
        }

        listReplace.add("switzerland");
        listReplace.add("schweiz");
        listReplace.add("suisse");
        for (String test : listReplace) {
            if (pays.equalsIgnoreCase(test)) return "Suisse";
        }

        if (pays.equalsIgnoreCase("france")) return "France";

        return null;
    }

    public static String normalizVille(String ville) {
        if (ville == null || ville.isEmpty()) return "";
        ville = formatString(ville);
        String maj;
        boolean needMaj = true;
        ArrayList<String> tab = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(ville, "-_ ");
        while (st.hasMoreTokens()) {
            tab.add(st.nextToken());
        }
        ville = "";
        for (String token : tab) {
            if (token.equalsIgnoreCase("st") || token.equalsIgnoreCase("saint")) token = "saint-";
            if (token.equalsIgnoreCase("ste") || token.equalsIgnoreCase("sainte")) token = "sainte-";
            if (token.equalsIgnoreCase("les") || token.equalsIgnoreCase("lès")) {
                token = "-lès-";
                needMaj = false;
            }

            if (token.equalsIgnoreCase("a") || token.equalsIgnoreCase("à")) {
                token = "-à-";
                needMaj = false;
            }

            if (token.equalsIgnoreCase("la")) {
                token = "-la-";
                needMaj = false;
            }
            if (token.equalsIgnoreCase("le")) {
                token = "-le-";
                needMaj = false;
            }

            if (token.equalsIgnoreCase("sous")) {
                token = "-sous-";
                needMaj = false;
            }

            if (token.equalsIgnoreCase("sur")) {
                token = "-sur-";
                needMaj = false;
            }

            if (token.equalsIgnoreCase("aux")) {
                token = "-aux-";
                needMaj = false;
            }

            if (needMaj) {
                maj = token.substring(0, 1);
                token = token.substring(1, token.length());
                maj = maj.toUpperCase();
                token = maj + token;
            }
            ville += token;
            needMaj = true;
        }

        return ville;
    }

    public static String normalizCodePostal(String cod, String pays) {
        if (cod == null || pays == null || cod.isEmpty() || pays.isEmpty()) return "";
        switch (pays) {
            case "Luxembourg":
                Pattern pat1 = Pattern.compile("[L-].*");
                Matcher mat1 = pat1.matcher(cod);
                if (mat1.matches()) {
                    String l = cod.substring(0, 2);//cod.charAt(0) + cod.charAt(1);
                    return cod.replaceAll(l, "");
                }
                break;

            case "Belgique":
                Pattern pat2 = Pattern.compile("[B-].*");
                Matcher mat2 = pat2.matcher(cod);
                if (mat2.matches()) {
                    String l = cod.substring(0, 2);
                    return cod.replaceAll(l, "");
                }
                break;

            case "Suisse":
                Pattern pat3 = Pattern.compile("[S-].*");
                Matcher mat3 = pat3.matcher(cod);
                if (mat3.matches()) {
                    String l = cod.substring(0, 2);
                    return cod.replaceAll(l, "");
                }
                break;

            case "France":
                Pattern pat = Pattern.compile("[0-9]{5}");
                Matcher mat = pat.matcher(cod);
                boolean valid = false;
                if (mat.matches()) return cod;
                else {
                    Pattern p = Pattern.compile("\\d+"); // Ici ton regex => ta chaine de caractere a trouver
                    Matcher m = p.matcher(cod);
                    while (m.find()) { // tant qu'il arrive a matcher ton regex ds la chaine de caractere s
                        cod = m.group();
                        valid = true;// contient au moins 1 chiffre
                    }
                    if (!valid) return null;
                    while (cod.length() != 5) {
                        if (cod.length() < 5) cod = 0 + cod;
                        else cod = cod.substring(1, cod.length() - 1);
                    }
                    return cod;
                }
        }
        return null;
    }

    public static String nomralizNomVoie(String nom) {
        if (nom == null || nom.isEmpty()) return "";
        nom = formatString(nom);
        ArrayList<String> listReplace = new ArrayList<String>();

        listReplace.add("boul ");
        listReplace.add("boul. ");
        listReplace.add("bd ");
        listReplace.add("bd. ");
        for (String testReplace : listReplace) {
            nom = nom.replace(testReplace, "boulevard ");
        }

        listReplace.clear();
        listReplace.add("av ");
        listReplace.add("av. ");
        for (String testReplace : listReplace) {
            nom = nom.replace(testReplace, "avenue ");
        }

        listReplace.clear();
        listReplace.add("faub ");
        listReplace.add("faub. ");
        listReplace.add("fg ");
        listReplace.add("fg. ");
        for (String testReplace : listReplace) {
            nom = nom.replace(testReplace, "faubourg ");
        }

        listReplace.clear();
        listReplace.add("pl ");
        listReplace.add("pl. ");
        for (String testReplace : listReplace) {
            nom = nom.replace(testReplace, "place ");
        }
        return nom;
    }

    public static String normalizNumRue(String numero) {
        if (numero == null || numero.isEmpty()) return "";
        numero = formatString(numero);
        boolean valid = false;
        String complement = ",";
        if (numero.indexOf("bis") > 0) complement = " bis,";
        if (numero.indexOf("ter") > 0) complement = " ter,";
        Pattern pat = Pattern.compile("\\d+"); // Ici ton regex => ta chaine de caractere a trouver
        Matcher mat = pat.matcher(numero);
        while (mat.find()) {// tant qu'il arrive a matcher ton regex ds la chaine de caractere
            numero = mat.group();
            valid = true;
        }
        if (!valid) return null;
        else return numero + complement;
    }


}
