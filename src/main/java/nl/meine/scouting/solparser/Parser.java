/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.meine.scouting.solparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.meine.scouting.solparser.entities.Person;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author Meine Toonen
 */
public class Parser {

    public static void main(String[] args) {
        Parser p = new Parser("test.csv", "aap.xls");
        p.start();
    }
    private File input;
    private File output;
    private Map<String, List<Person>> sortedPersonsPerSpelenheid = new HashMap();
    private List<Person> allPersons = new ArrayList();
    private Workbook workbook;

    public Parser(String inputFile, String outputFile) {
        input = new File(inputFile);
        output = new File(outputFile);
        init();

    }

    private void init() {
         FileOutputStream out = null;
        try {
            out = new FileOutputStream(output);
            // create a new workbook
            workbook = new HSSFWorkbook();
           
        } catch (FileNotFoundException ex) {
            System.err.println("File Read Error" + ex.getLocalizedMessage());
        } finally {
            try {
                if(out != null){
                    out.close();
                }
            } catch (IOException ex) {
                System.err.println("File Read Error" + ex.getLocalizedMessage());
            }
        }
    }

    public void start() {
        read();
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
                String spelEenheid = p.getSpeleenheid();
                if(!sortedPersonsPerSpelenheid.containsKey(spelEenheid)){
                    sortedPersonsPerSpelenheid.put(spelEenheid, new ArrayList());
                }
                sortedPersonsPerSpelenheid.get(p.getSpeleenheid()).add( p);
                allPersons.add(p);
            }
            in.close();
        } catch (IOException e) {
            System.err.println("File Read Error" + e.getLocalizedMessage());
        }

    }
    
    
    public void write() {
        // create a new sheet
        for (String eenheid : sortedPersonsPerSpelenheid.keySet()) {
            Sheet sheet = workbook.createSheet(eenheid);
            List<Person> personsPerEenheid = sortedPersonsPerSpelenheid.get(eenheid);
            for (int i = 0; i < personsPerEenheid.size(); i++) {
                Person person = personsPerEenheid.get(i);
                Row r = createRow(person,sheet,i);
                
            }
            
        }
    }
    
    private Row createRow(Person p, Sheet sheet, int index){
        Row r = sheet.createRow(index);
        
        return r;
    }
    

    private Person createPerson(String[] row) {
//"lidnummer";"lid voornaam";"lid initialen";"lid tussenvoegsel";"lid achternaam";"lid straat";"lid huisnummer";"lid toevoegsel huisnr";
        Person p = new Person();
        p.setLidnummer(row[0]);
        p.setLid_voornaam(row[1]);
        p.setLid_initialen(row[2]);
        p.setLid_tussenvoegsel(row[3]);
        p.setLid_achternaam(row[4]);
        p.setLid_straat(row[5]);
        p.setLid_huisnummer(row[6]);
        p.setLid_toevoegsel_huisnr(row[7]);
        p.setLid_postcode(row[8]);
//"lid postcode";"lid plaats";"lid land";"lid mailadres";"Lid mailadres ouder/verzorger";"Lid geslacht";"lid geboortedatum";"lid mobiel";
        p.setLid_plaats(row[9]);
        p.setLid_land (row[10]);
        p.setLid_mailadres(row[11]);
        p.setLid_mailadres_ouder_verzorger (row[12]);
        p.setLid_geslacht(row[13]);
        p.setLid_geboortedatum(row[14]);
        p.setLid_mobiel(row[15]);
//"lid telefoon";"functie";"functie startdatum";"Functie status";"Functienummer";"Functietype";"speleenheid soort";"speleenheid";"Speleenheidnummer"
        p.setLid_telefoon(row[16]);
        p.setFunctie(row[17]);
        p.setFunctie_startdatum(row[18]);
        p.setFunctie_status(row[19]);
        p.setFunctienummer(row[20]);
        p.setFunctietype(row[21]);
        p.setSpeleenheid_soort(row[22]);
        p.setSpeleenheid(row[23]);
        p.setSpeleenheidnummer(row[24]);
//;"organisatienummer";"organisatie categorie";"organisatie";"organisatie plaats"
        p.setOrganisatienummer(row[25]);
        p.setOrganisatie_categorie(row[26]);
        p.setOrganisatie(row[27]);
        p.setOrganisatie_plaats(row[28]);
        return p;
    }

}
