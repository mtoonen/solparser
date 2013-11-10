/*
 *  Copyright (C) 2013 Meine Toonen
 *  This file is part of the SolParser library.
 *
 *  The SolParser librar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  The SolParser librar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with the SolParser librar. If not, see <http://www.gnu.org/licenses/>.
 */
package nl.meine.scouting.solparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.meine.scouting.solparser.entities.Person;
import nl.meine.scouting.solparser.sorter.Sorter;
import nl.meine.scouting.solparser.writer.SolWriter;

/**
 *
 * @author Meine Toonen
 */
public class Parser {

    private File input;
    private File output;
    private Map<String, List<Person>> sortedPersons = new HashMap();
    private List<Person> allPersons = new ArrayList();
    
    private SolWriter writer;
    private Sorter sorter;
    
    public Parser(String inputFile, String outputFile, SolWriter writer, Sorter sorter) {
        input = new File(inputFile);
        output = new File(outputFile);
        this.writer = writer;
        this.writer.setOutput(output);
        this.sorter = sorter;
    }

    public void init() {
        writer.init();
    }

    public void read(boolean skipfirst) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(input));
            String str;

            // Skip first line
            if(skipfirst){
                str = in.readLine();
            }
            while ((str = in.readLine()) != null) {
                if (str.isEmpty()) {
                    continue;
                }
                str = str.replaceAll("\"", "");
                String[] ar = str.split(";");
                Person p = createPerson(ar);

                allPersons.add(p);
            }
            in.close();
        } catch (IOException e) {
            System.err.println("File Read Error" + e.getLocalizedMessage());
        }
        postProcessPersons();

    }

    public void write() throws Throwable {
        if(writer != null && allPersons.size() > 0  && sortedPersons.size() > 0){
            writer.setAllPersons(allPersons);
            writer.setSortedPersons(sortedPersons);
            writer.write();
            writer.finalize();
        }else{
            System.err.println("Not entirely initialized. Did you read before writing?");
        }
    }

    private void postProcessPersons() {
        groupPersons();
        sortedPersons = sorter.sort(allPersons,true);
    }

    private void groupPersons(){
        Map<String, List<Person>> groupedPersons = new HashMap();
        for (Person person : allPersons) {
            if(!groupedPersons.containsKey(person.getLidnummer())){
                groupedPersons.put(person.getLidnummer(), new ArrayList());
            }
            groupedPersons.get(person.getLidnummer()).add(person);
        }
        List<Person> newList = new ArrayList();
        for (String lidnummer : groupedPersons.keySet()) {
            List<Person> same = groupedPersons.get(lidnummer);
            Person p = same.get(0);
            if(same.size() >1){
                String functie = p.getFunctie() + " (" + p.getSpeleenheid() + ")";
                for(int i = 1 ; i< same.size();i++){
                    Person per = same.get(i);
                    functie += " / " +per.getFunctie() + " (" + per.getSpeleenheid() + ")";
                    p.setSpeleenheid(concatIfNotExisting(p.getSpeleenheid(),per.getSpeleenheid()));
                }
                p.setFunctie(functie);
            }
            newList.add(p);
        }
        allPersons = newList;
    }
    
    private String concatIfNotExisting(String value1, String value2){
        if(value1.indexOf(value2) > 0 ){
            value1 += " / " + value2;
        }
        return value1;
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
        p.setLid_land(row[10]);
        p.setLid_mailadres(row[11]);
        p.setLid_mailadres_ouder_verzorger(row[12]);
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
