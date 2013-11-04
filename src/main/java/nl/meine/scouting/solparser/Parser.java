/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.meine.scouting.solparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import nl.meine.scouting.solparser.entities.Person;
import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 * @author Meine Toonen
 */
public class Parser {

    public static void main(String[] args) {
        Parser p = new Parser("selectie_2871.csv", "aap.xls");
        p.read();
    }
    private File input;
    private File output;
    private Sheet sheet;
    private Map<String, Person> sortedPersonsPerSpelenheid = new HashMap();

    public Parser(String inputFile, String outputFile) {
        input = new File(inputFile);
        output = new File(outputFile);
        init();

    }

    private void init() {
    }

    public void start() {
    }

    public void read() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(input));
            String str;
            str = in.readLine();
            while ((str = in.readLine()) != null) {
                str = str.replaceAll("\"", "");
                String [] ar = str.split(";");
                Person p = createPerson(ar);
            }
            in.close();
        } catch (IOException e) {
            System.out.println("File Read Error");
        }

    }
    private Person createPerson(String[] row) {
//"lidnummer";"lid voornaam";"lid initialen";"lid tussenvoegsel";"lid achternaam";"lid straat";"lid huisnummer";"lid toevoegsel huisnr";
        Person p = new Person();
        p.setLidnummer(row[0]);
        p.setLid_voornaam(row[1]);
        p.setLid_initialen(row[2]);
        p.setLid_tussenvoegsel(row[3]);
        p.setLid_achternaam(row[4]);
        p.setLid_huisnummer(row[5]);
        p.setLid_toevoegsel_huisnr(row[6]);
        p.setLid_postcode(row[7]);
//"lid postcode";"lid plaats";"lid land";"lid mailadres";"Lid mailadres ouder/verzorger";"Lid geslacht";"lid geboortedatum";"lid mobiel";
        p.setLid_plaats(row[8]);
        p.setLid_land (row[9]);
        p.setLid_mailadres(row[10]);
        p.setLid_mailadres_ouder_verzorger (row[11]);
        p.setLid_geslacht(row[12]);
        p.setLid_geboortedatum(row[13]);
        p.setLid_mobiel(row[14]);
//"lid telefoon";"functie";"functie startdatum";"Functie status";"Functienummer";"Functietype";"speleenheid soort";"speleenheid";"Speleenheidnummer"
        p.setLid_telefoon(row[15]);
        p.setFunctie(row[16]);
        p.setFunctie_startdatum(row[17]);
        p.setFunctie_status(row[18]);
        p.setFunctienummer(row[19]);
        p.setFunctietype(row[20]);
        p.setSpeleenheid_soort(row[21]);
        p.setSpeleenheid(row[22]);
        p.setSpeleenheidnummer(row[23]);
//;"organisatienummer";"organisatie categorie";"organisatie";"organisatie plaats"
        p.setOrganisatienummer(row[24]);
        p.setOrganisatie_categorie(row[25]);
        p.setOrganisatie(row[26]);
        p.setOrganisatie_plaats(row[27]);
        return p;
    }

    public void write() {
    }
}
