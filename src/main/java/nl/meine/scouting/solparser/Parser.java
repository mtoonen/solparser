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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author Meine Toonen
 */
public class Parser {

    public static void main(String[] args) {
        Parser p = new Parser("selectie_2871.csv", "aap.xls");
        p.start();
    }
    private File input;
    private File output;
    private Map<String, List<Person>> sortedPersonsPerSpelenheid = new HashMap();
    private List<Person> allPersons = new ArrayList();
    private Workbook workbook;
    private FileOutputStream out = null;
    
    private static final int NUM_ATTRIBUTES_PER_PERSON = 18;

    public Parser(String inputFile, String outputFile) {
        input = new File(inputFile);
        output = new File(outputFile);
        init();
    }

    private void init() {
        try {
            out = new FileOutputStream(output);
            // create a new workbook
            workbook = new HSSFWorkbook();
           
        } catch (FileNotFoundException ex) {
            System.err.println("File Read Error" + ex.getLocalizedMessage());
        } 
    }

    public void start() {
        read();
        write();
    }

    public void read() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(input));
            String str;
            str = in.readLine();
            while ((str = in.readLine()) != null) {
                if(str.isEmpty()){
                    continue;
                }
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
            createHeading(sheet);
            for (int i = 0; i < personsPerEenheid.size(); i++) {
                Person person = personsPerEenheid.get(i);
                Row r = createRow(person,sheet,i+1);
                
            }
        }
        try {
            workbook.write(out);
        } catch (IOException ex) {
            System.err.println("File write Error" + ex.getLocalizedMessage());
        }finally{
            if(out != null){
                try {
                    out.close();
                } catch (IOException ex) {
            System.err.println("File close Error" + ex.getLocalizedMessage());
                }
            }
        }
    }
    
    private Row createRow(Person p, Sheet sheet, int index){
        Row r = sheet.createRow(index);
        Cell[] cells = new Cell[NUM_ATTRIBUTES_PER_PERSON];
        for (int i = 0; i < NUM_ATTRIBUTES_PER_PERSON; i++) {
            Cell c = r.createCell(i);
            cells[i] = c;
        }
//        Lidnummer	Achternaam	tussenvoegsel	Roepnaam	Voorletters	geslacht	Adres	Huisnummer	Postcode	Woonplaats	
        cells[0].setCellValue(p.getLidnummer());
        cells[1].setCellValue(p.getLid_achternaam());
        cells[2].setCellValue(p.getLid_tussenvoegsel());
        cells[3].setCellValue(p.getLid_voornaam());
        cells[4].setCellValue(p.getLid_initialen());
        cells[5].setCellValue(p.getLid_geslacht());
        cells[6].setCellValue(p.getLid_straat());
        cells[7].setCellValue(p.getLid_huisnummer() + " " + p.getLid_toevoegsel_huisnr());
        cells[8].setCellValue(p.getLid_postcode());
        cells[9].setCellValue(p.getLid_plaats());
        //telefoon	mobiel lid	mobiel ouders 	
        //mobiel vader	mobiel moeder	// niet aanwezig!
        //e-mail lid	e-mail ouders	(Jeugd)lid bij Speltak	
        //Leiding bij Speltak	// Volgt uit tabbladen verdeling
        //Functie	Geboortedatum	Lid sinds
        cells[10].setCellValue(p.getLid_telefoon());
        cells[11].setCellValue(p.getLid_mobiel());
        cells[12].setCellValue(p.getLid_mailadres());
        cells[13].setCellValue(p.getLid_mailadres_ouder_verzorger());
        cells[14].setCellValue(p.getSpeleenheid());
        cells[15].setCellValue(p.getFunctie());
        cells[16].setCellValue(p.getLid_geboortedatum());
        cells[17].setCellValue(p.getFunctie_startdatum());
        
        return r;
    }
    
    private void createHeading (Sheet sheet){
        Row r = sheet.createRow(0);
        r.createCell(0).setCellValue("Lidnummer");
        r.createCell(1).setCellValue("Achternaam");
        r.createCell(2).setCellValue("Tussenvoegsel");
        r.createCell(3).setCellValue("Voornaam");
        r.createCell(4).setCellValue("Initialen");
        r.createCell(5).setCellValue("Geslacht");
        r.createCell(6).setCellValue("Straat");
        r.createCell(7).setCellValue("Adres");
        r.createCell(8).setCellValue("Postcode");
        r.createCell(9).setCellValue("Plaats");
        r.createCell(10).setCellValue("Telefoonnummer");
        r.createCell(11).setCellValue("Mobiel");
        r.createCell(12).setCellValue("Mail lid");
        r.createCell(13).setCellValue("Mail ouder/verzorger");
        r.createCell(14).setCellValue("Speltak");
        r.createCell(15).setCellValue("Functie");
        r.createCell(16).setCellValue("Geboortedatum");
        r.createCell(17).setCellValue("Functie startdatum");
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
        String speleenheid = row[23];
        speleenheid = speleenheid.replaceAll("/", "-");
        p.setSpeleenheid(speleenheid);
        p.setSpeleenheidnummer(row[24]);
//;"organisatienummer";"organisatie categorie";"organisatie";"organisatie plaats"
        p.setOrganisatienummer(row[25]);
        p.setOrganisatie_categorie(row[26]);
        p.setOrganisatie(row[27]);
        p.setOrganisatie_plaats(row[28]);
        return p;
    }

}
