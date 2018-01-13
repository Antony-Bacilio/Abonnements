package test;

import dao.normalisation.NormaClient;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NomalClientTest {

    // ---------------------- PAYS --------------------------------
    @Test
    public void testNormalizPaysNull() {
        assertEquals(null, NormaClient.normalizPays(null));
    }

    @Test
    public void testNormalizPaysEmpty() {
        assertEquals(null, NormaClient.normalizPays(""));
    }

    @Test
    public void testNormalizPaysSpace() {
        assertEquals("Luxembourg", NormaClient.normalizPays("  Luxembourg  "));
    }

    @Test
    public void testNormalizPaysUpperCase() {
        assertEquals("Luxembourg", NormaClient.normalizPays("lUXEMbouRG"));
    }

    @Test
    public void testNormalizPaysLuxembourg() {
        assertEquals("Luxembourg", NormaClient.normalizPays("letzebuerg"));
    }

    @Test
    public void testNormalizPaysBelgique() {
        assertEquals("Belgique", NormaClient.normalizPays("Belgium"));
    }

    @Test
    public void testNormalizPaysSwitzerland() {
        assertEquals("Suisse", NormaClient.normalizPays("Switzerland"));
    }

    @Test
    public void testNormalizPaysSchweitz() {
        assertEquals("Suisse", NormaClient.normalizPays("Schweiz"));
    }

    @Test
    public void testNormalizPaysFrance() {
        assertEquals("France", NormaClient.normalizPays("France"));
    }

    @Test
    public void testNormalizPaysUnvalid() {
        assertEquals(null, NormaClient.normalizPays("Luxem-bourg"));
    }

    /*****************************************   VILLE   ************************************************/

    @Test
    public void testNormalizVilleNull() {
        assertEquals(null, NormaClient.normalizVille(null));
    }

    @Test
    public void testNormalizVilleEmpty() {
        assertEquals(null, NormaClient.normalizVille(""));
    }

    @Test
    public void testNormalizVilleUpperCase() {
        assertEquals("Metz", NormaClient.normalizVille("metz"));
    }


    //Preposition le
    @Test
    public void testNormalizVilleLeSpace() {
        assertEquals("Montigny-le-Metz", NormaClient.normalizVille("mOntiGny lE mEtz "));
    }

    @Test
    public void testNormalizVilleLeUnderscore() {
        assertEquals("Montigny-le-Metz", NormaClient.normalizVille("mOntiGny_lE_mEtz "));
    }

    @Test
    public void testNormalizVilleLe() {
        assertEquals("Montigny-le-Metz", NormaClient.normalizVille("mOntiGny-lE-mEtz "));
    }

    //Preposition la
    @Test
    public void testNormalizVilleLaSpace() {
        assertEquals("Montigny-la-Metz", NormaClient.normalizVille("mOntiGny la mEtz "));
    }

    @Test
    public void testNormalizVilleLaUnderscore() {
        assertEquals("Montigny-la-Metz", NormaClient.normalizVille("mOntiGny_la_mEtz "));
    }

    //Preposition les
    @Test
    public void testNormalizVilleLesSpace() {
        assertEquals("Montigny-lès-Metz", NormaClient.normalizVille(" mOntiGny lEs mEtz "));
    }

    @Test
    public void testNormalizVilleLesUnderscore() {
        assertEquals("Montigny-lès-Metz", NormaClient.normalizVille(" mOntiGny_lEs_mEtz "));
    }

    @Test
    public void testNormalizVilleLes() {
        assertEquals("Montigny-lès-Metz", NormaClient.normalizVille(" mOntiGny-lEs-mEtz "));
    }

    //Preposition sous
    @Test
    public void testNormalizVilleSousSpace() {
        assertEquals("Montigny-sous-Metz", NormaClient.normalizVille(" mOntiGny SOUs mEtz "));
    }

    @Test
    public void testNormalizVilleSousUnderscore() {
        assertEquals("Montigny-sous-Metz", NormaClient.normalizVille(" mOntiGny_SOUs_mEtz "));
    }

    @Test
    public void testNormalizVilleSous() {
        assertEquals("Montigny-sous-Metz", NormaClient.normalizVille(" mOntiGny-SOUs-mEtz "));
    }

    //Preposition a
    @Test
    public void testNormalizVilleA() {
        assertEquals("Montigny-à-Metz", NormaClient.normalizVille(" mOntiGny-a-mEtz"));
    }

    @Test
    public void testNormalizVilleASpace() {
        assertEquals("Montigny-à-Metz", NormaClient.normalizVille(" mOntiGny à mEtz"));
    }

    @Test
    public void testNormalizVilleAUnderscore() {
        assertEquals("Montigny-à-Metz", NormaClient.normalizVille(" mOntiGny_à_mEtz"));
    }

    @Test
    public void testNormalizVilleValid() {
        assertEquals("Montigny-à-Metz", NormaClient.normalizVille(" mOntiGny-à-mEtz"));
    }


    //Preposition aux
    @Test
    public void testNormalizVilleAuxSpace() {
        assertEquals("Montigny-aux-Metz", NormaClient.normalizVille(" mOntiGny AuX mEtz "));
    }

    @Test
    public void testNormalizVilleAuxUnderscore() {
        assertEquals("Montigny-aux-Metz", NormaClient.normalizVille(" mOntiGny_AuX_mEtz "));
    }

    @Test
    public void testNormalizVilleAux() {
        assertEquals("Montigny-aux-Metz", NormaClient.normalizVille(" mOntiGny-AuX-mEtz "));
    }


    //Saint
    @Test
    public void testNormalizVilleSt() {
        assertEquals("Saint-Julien", NormaClient.normalizVille(" sT JuLIEn "));
    }

    @Test
    public void testNormalizVilleStUnderscore() {
        assertEquals("Saint-Julien", NormaClient.normalizVille(" sT_JuLIEn "));
    }

    @Test
    public void testNormalizVilleStDash() {
        assertEquals("Saint-Julien", NormaClient.normalizVille(" sT-JuLIEn "));
    }

    @Test
    public void testNormalizVilleSaintUnderscore() {
        assertEquals("Saint-Julien", NormaClient.normalizVille(" saint_JuLIEn "));
    }

    @Test
    public void testNormalizVilleSaintSpace() {
        assertEquals("Saint-Julien", NormaClient.normalizVille(" saint JuLIEn "));
    }

    @Test
    public void testNormalizVilleSaint() {
        assertEquals("Saint-Julien", NormaClient.normalizVille(" saint-JuLIEn "));
    }

    //Sainte
    @Test
    public void testNormalizVilleSte() {
        assertEquals("Sainte-Julien", NormaClient.normalizVille(" sTe JuLIEn "));
    }

    @Test
    public void testNormalizVilleSteUnderscore() {
        assertEquals("Sainte-Julien", NormaClient.normalizVille(" sTe_JuLIEn "));
    }

    @Test
    public void testNormalizVilleSteDash() {
        assertEquals("Sainte-Julien", NormaClient.normalizVille(" sTe-JuLIEn "));
    }

    @Test
    public void testNormalizVilleSainteUnderscore() {
        assertEquals("Sainte-Julien", NormaClient.normalizVille(" sainte_JuLIEn "));
    }

    @Test
    public void testNormalizVilleSainteSpace() {
        assertEquals("Sainte-Julien", NormaClient.normalizVille(" sainte JuLIEn "));
    }

    @Test
    public void testNormalizVilleSainte() {
        assertEquals("Sainte-Julien", NormaClient.normalizVille(" sainte-JuLIEn "));
    }


    //-----------------------------CODE POSTAL --------------------------------

    @Test
    public void testNormalizodePostalNull() {
        assertEquals(null, NormaClient.normalizCodePostal(null, null));
    }

    @Test
    public void testNormaliCodePostalEmpty() {
        assertEquals(null, NormaClient.normalizCodePostal("", ""));
    }

    @Test
    public void testNormalizCodePostalIndicatifLux() {
        assertEquals("8227", NormaClient.normalizCodePostal("L-8227", "Luxembourg"));
    }


    @Test
    public void testNormalizCodePostalIndicatifBelg() {
        assertEquals("8270", NormaClient.normalizCodePostal("B-8270", "Belgique"));
    }

    @Test
    public void testNormalizCodePostalIndicatifSuisse() {
        assertEquals("8220", NormaClient.normalizCodePostal("S-8220", "Suisse"));
    }

    @Test
    public void testNormalizCodePostalFrance() {
        assertEquals("57000", NormaClient.normalizCodePostal("57000", "France"));
    }


    @Test
    public void testNormalizCodePostalIndicatifFrance() {
        assertEquals("92270", NormaClient.normalizCodePostal("fre92270", "France"));
    }

    @Test
    public void testNormalizCodePostal4Chiffre() {
        assertEquals("02270", NormaClient.normalizCodePostal("2270", "France"));
    }


    @Test
    public void testNormalizCodePostalUnvalid() {
        assertEquals(null, NormaClient.normalizCodePostal("Bonjour", "France"));
    }

    //--------------------------------NOM VOIE-----------------------------

    @Test
    public void testNormalizNomVoieNull() {
        assertEquals(null, NormaClient.nomralizNomVoie(null));
    }

    @Test
    public void testNormalizNomVoieEmpty() {
        assertEquals(null, NormaClient.nomralizNomVoie(""));
    }

    @Test
    public void testNormalizNomVoieBoul() {
        assertEquals("boulevard foch", NormaClient.nomralizNomVoie("boul foch"));
    }

    @Test
    public void testNormalizNomVoieBoulDot() {
        assertEquals("boulevard foch", NormaClient.nomralizNomVoie("boul. foch"));
    }

    @Test
    public void testNormalizNomVoieBd() {
        assertEquals("boulevard foch", NormaClient.nomralizNomVoie("bd foch"));
    }

    @Test
    public void testNormalizNomVoieBdDot() {
        assertEquals("boulevard foch", NormaClient.nomralizNomVoie("bd. foch"));
    }

    @Test
    public void testNormalizNomVoieAv() {
        assertEquals("avenue foch", NormaClient.nomralizNomVoie("av foch"));
    }

    @Test
    public void testNormalizNomVoieAvDot() {
        assertEquals("avenue foch", NormaClient.nomralizNomVoie("av. foch"));
    }

    @Test
    public void testNormalizNomVoieFaub() {
        assertEquals("faubourg foch", NormaClient.nomralizNomVoie("faub foch"));
    }

    @Test
    public void testNormalizNomVoieFaubDot() {
        assertEquals("faubourg foch", NormaClient.nomralizNomVoie("faub. foch"));
    }

    @Test
    public void testNormalizNomVoieFg() {
        assertEquals("faubourg foch", NormaClient.nomralizNomVoie("fg foch"));
    }

    @Test
    public void testNormalizNomVoieFgDot() {
        assertEquals("faubourg foch", NormaClient.nomralizNomVoie("fg. foch"));
    }

    @Test
    public void testNormalizNomVoiePl() {
        assertEquals("place foch", NormaClient.nomralizNomVoie("pl foch"));
    }

    @Test
    public void testNormalizNomVoiePlDot() {
        assertEquals("place foch", NormaClient.nomralizNomVoie("pl. foch"));
    }

    // ----------------- NUM VOIE ----------------------------


    @Test
    public void testNormalizNumVoieNull() {
        assertEquals(null, NormaClient.normalizNumRue(null));
    }

    @Test
    public void testNormalizNumVoieEmpty() {
        assertEquals(null, NormaClient.normalizNumRue(""));
    }

    @Test
    public void testNormalizNumRueValid() {
        assertEquals("45,", NormaClient.normalizNumRue("45,"));
    }

    @Test
    public void testNormalizNumRueBis() {
        assertEquals("45 bis,", NormaClient.normalizNumRue("45 bis,"));
    }

    @Test
    public void testNormalizNumRueTer() {
        assertEquals("45 ter,", NormaClient.normalizNumRue("45 ter,"));
    }

    @Test
    public void testNormalizNumRueWithoutComma() {
        assertEquals("45,", NormaClient.normalizNumRue("45"));
    }

    @Test
    public void testNormalizNumRueBisWithoutSpace() {
        assertEquals("45 bis,", NormaClient.normalizNumRue("45bis,"));
    }


        @Test
    public void testNormalizNumRueBisWithoutBisComma() {
            assertEquals("45 bis,", NormaClient.normalizNumRue("45 bis"));
    }


    @Test
    public void testNormalizNumRueUnvalid() {
        assertEquals(null, NormaClient.normalizNumRue("bis,"));
    }

}