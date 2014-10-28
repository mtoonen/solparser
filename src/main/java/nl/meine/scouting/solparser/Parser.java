/*
 *  Copyright (C) 2013-2014 Meine Toonen
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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.meine.scouting.solparser.entities.Person;
import nl.meine.scouting.solparser.sorter.Sorter;
import nl.meine.scouting.solparser.sorter.SorterFactory;

/**
 *
 * @author Meine Toonen
 */
public class Parser {

    private File input;
    private Map<String, List<Person>> sortedPersons = new HashMap();
    private List<Person> allPersons = new ArrayList();

    private Sorter sorter;

    private int type = 1216;

    public Parser(){

    }

    public Parser(File input){
        this(input, SorterFactory.createSorter(SorterFactory.SORTER_ONLYALL));
    }

    public Parser (File input, Sorter sorter){
        this.input = input;
        this.sorter = sorter;
    }

    public Parser(String inputFile, Sorter sorter) {
        input = new File(inputFile);
        this.sorter = sorter;
    }

    public void read(boolean skipfirst) {
        try {
            Reader reader = new InputStreamReader(new FileInputStream(input), "utf-8");
            BufferedReader br = new BufferedReader(reader);
            String str;

            // Skip first line
            if(skipfirst){
                str = br.readLine();
            }
            while ((str = br.readLine()) != null) {
                if (str.isEmpty()) {
                    continue;
                }
                str = str.replaceAll("\"", "");
                String[] ar = str.split("\t");
                Person p = createPerson(ar);

                allPersons.add(p);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("File Read Error" + e.getLocalizedMessage());
        }
        postProcessPersons();

    }

    private void postProcessPersons() {
        groupPersons();
        Collections.sort(allPersons);
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
            p.addFunctie(p.getSingleSpeleenheid(),p.getSingleFunctie());
            if(same.size() >1){
                for(int i = 1 ; i< same.size();i++){
                    Person per = same.get(i);
                    p.addFunctie(per.getSingleSpeleenheid(),per.getSingleFunctie());
                }
            }
            newList.add(p);
        }
        allPersons = newList;
    }

    public Person createPerson(String[] row) {
        Person p = new Person();

        //"Lidnummer";"Lid voornaam";"Lid initialen";"Lid tussenvoegsel";"Lid achternaam";"Lid volledige naam";"Lid straat";"Lid huisnummer"
        p.setLidnummer(row[0]);
        p.setLid_voornaam(row[1]);
        p.setLid_initialen(row[2]);
        p.setLid_tussenvoegsel(row[3]);
        p.setLid_achternaam(row[4]);
        p.setLid_straat(row[6]);
        p.setLid_huisnummer(row[7]);
        //;"Lid toevoegsel huisnr";"Lid postcode";"Lid plaats";"Lid land";"Lid geboortedatum";"Lid geslacht";"Lid telefoon";"Lid mobiel";"Lid mailadres";
        p.setLid_toevoegsel_huisnr(row[8]);
        p.setLid_postcode(row[9]);
        p.setLid_plaats(row[10]);
        p.setLid_land(row[11]);
        p.setLid_geboortedatum(row[12]);
        p.setLid_geslacht(row[13]);
        p.setLid_telefoon(row[14]);
        p.setLid_mobiel(row[15]);
        p.setLid_mailadres(row[16]);
        //"Lid naam ouder/verzorger 1";"Lid mailadres ouder/verzorger 1";"Lid telefoonnummer ouder/verzorger 1";"Lid naam ouder/verzorger 2";
        p.setLid_naam_ouder_verzorger(row[17]);
        p.setLid_mailadres_ouder_verzorger(row[18]);
        p.setLid_telefoonnummer_ouder_verzorger(row[19]);

        p.setLid_naam_ouder_verzorger_2(row[20]);
        //"Lid mailadres ouder/verzorger 2";"Lid telefoonnummer ouder/verzorger 2";"Overige informatie";"Functie";"Functie status";"Functie startdatum";
        p.setLid_mailadres_ouder_verzorger_2(row[21]);
        p.setLid_telefoonnummer_ouder_verzorger_2(row[22]);
        p.setOverige_informatie(row[23]);
        p.setFunctie(row[24]);
        p.setFunctie_status(row[25]);
        p.setFunctie_startdatum(row[26]);
        p.setOrganisatie(row[31]);
        p.setOrganisatie_plaats(row[32]);

        //"Speleenheid soort";"Speleenheid";"Organisatienummer";"Organisatie categorie";"Organisatie";"Organisatie plaats"

        p.setSpeleenheid_soort(row[27]);
        String speleenheid = row[28];
        speleenheid = speleenheid.replaceAll("/", "-");
        p.setSpeleenheid(speleenheid);

        return p;
    }

    public Map<String, List<Person>> getSortedPersons() {
        return sortedPersons;
    }

    public List<Person> getAllPersons() {
        return allPersons;
    }

    public Sorter getSorter(){
        return sorter;
    }
}
