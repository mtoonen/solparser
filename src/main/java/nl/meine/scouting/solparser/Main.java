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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import nl.meine.scouting.solparser.entities.Person;
import nl.meine.scouting.solparser.mail.Mailer;
import nl.meine.scouting.solparser.sorter.LeadersAndBestuurSorter;
import nl.meine.scouting.solparser.sorter.Sorter;
import nl.meine.scouting.solparser.sorter.SorterFactory;
import nl.meine.scouting.solparser.writer.ExcelWriter;
import nl.meine.scouting.solparser.writer.SolWriter;

/**
 *
 * @author Meine Toonen
 */
public class Main {
    public static final String HELP_TEXT = "SolParser Library created by Meine Toonen. Version 0.9. \n"
            + "\t Use the SolParser library to convert a horrifying csv file to a more human readable excel file, with handy grouping options. The following parameters are permitted: \n"
            + "\t\t Usage: \n"
            + "\t\t\t-i \t Required: Name to input file. \n"
            + "\t\t\t-o \t Optional: Name of the file to output. (default is ledenlijst_<date>.xls) \n"
            + "\t\t\t-s \t Optional (default is unit): Sorting options: which tabs should be created. Available options: leaders, bestuur, leadersandbestuur, unit, onlyall \n"
            + "\t\t\t-ot \t Optional (default is excel): Which output should be generated. Available options: excel \n"
            + "\t\t\t-h \t Display this helptext. \n"
            + "\t\t\t-m \t Mail the results. \n"
            + "\t\t\t-ms \t Mail the results from this mailserver. \n"
            + "\t\t\t-mu \t Mail the results with this user on the mailserver. \n"
            + "\t\t\t-mp \t Mail the results with this password from the mailuser. \n"
            + "\t\t\t-mh \t Mail the results from this mailhost. \n"
            + "\t\t\t-ma \t Mail the results from this actual mailaddress. \n"
            + "\t\t\t-mt \t Mail the results from to this mailaddress (use comma separated list (no spaces) for multiple recipients). \n"
            + "\t\t\t-mat \t Retrieve the mailaddresses automatically from the generated persons list (leaders and bestuur are being e-mailed) \n"
            + "\t\t\t-sf \t Optional (default true) Skip the first line of the csv value.";
    public static void main(String[] args) throws Throwable {

        Properties prop = argsParser(args);
        if (prop.containsKey("action")) {

            System.out.println(HELP_TEXT);
        } else {
            Date now = new Date();
            SimpleDateFormat fullMonthName = new SimpleDateFormat("dd MMMMM yyyy", Locale.forLanguageTag("NL"));
            SimpleDateFormat shortVersionFormatter = new SimpleDateFormat("dd_MM_yyyy", Locale.forLanguageTag("NL"));
            String fullName = fullMonthName.format(now);
            String shortVersion = shortVersionFormatter.format(now);
            
            String output = "";
            if(!prop.contains("output")){
                output = "ledenlijst_" + shortVersion + ".xls";
            }else{
                 output = prop.getProperty("output");
            }
            Sorter sorter = SorterFactory.createSorter(prop.getProperty("sorter"));
            SolWriter writer = getWriter(prop.getProperty("outputtype"),output);
            boolean skipfirst = prop.getProperty("skipfirst") == null ? true : Boolean.parseBoolean(prop.getProperty("skipfirst"));
            String input = prop.getProperty("input");
            if (input == null || output == null || input.isEmpty() || output.isEmpty()){
                throw new IllegalArgumentException("Input and/or output are not properly defined.");
            }
            
            
            Parser p = new Parser(input, sorter);
            writer.init();
            p.read(skipfirst);
            
            
            if (writer != null && p.getAllPersons().size() > 0 && p.getSortedPersons().size() > 0) {
                writer.setAllPersons(p.getAllPersons());
                writer.setSortedPersons(p.getSortedPersons());
                writer.write();
                writer.postprocess(sorter.getOrder());
                writer.closeWriter();
            } else {
                System.err.println("Not entirely initialized. Did you read before writing?");
            }
            
            
            if(prop.containsKey("mail")){
                String to = "";
                if(prop.contains("-mat")){
                    to = getToAdresses(p.getAllPersons());
                }else{
                     to = prop.getProperty("-mt");
                }
                String host = prop.getProperty("-mh");
                String fromEmail = prop.getProperty("-ma");
                String from = "Ledenlijstbot";
                String user = prop.getProperty("-mu");
                String password = prop.getProperty("-mp");
                String message = "Test" ;
                Mailer.sendMail(from, fromEmail, to, "Ledenlijst " +fullName, message,writer.getOutput(), writer.getOutput().getName(), user, password, host);
            }

        }

    }

    //-s leaders -o excel -sf true
    public static Properties argsParser(String[] args) {
        Properties prop = new Properties();
        for (int i = 0; i < args.length; i += 2) {
            String key = args[i];
            if (key.equalsIgnoreCase("-h")) {
                prop.setProperty("action", "help");
            } else {
                String value = args[i + 1];
                if (key.equalsIgnoreCase("-s")) {
                    prop.setProperty("sorter", value);
                } else if (key.equalsIgnoreCase("-ot")) {
                    prop.setProperty("outputtype", value);
                } else if (key.equalsIgnoreCase("-sf")) {
                    prop.setProperty("skipfirst", value);
                } else if (key.equalsIgnoreCase("-i")) {
                    prop.setProperty("input", value);
                } else if (key.equalsIgnoreCase("-o")) {
                    prop.setProperty("output", value);
                }else if(key.equalsIgnoreCase("-m")){
                    prop.setProperty("mail", value);
                }else{
                    prop.setProperty(key, value);
                }
            }
        }
        return prop;
    }

    public static SolWriter getWriter(String value, String output) {
        SolWriter writer = null;
        if (value == null) {
            writer = new ExcelWriter(output);
        } else if (value.equalsIgnoreCase("excel")) {
            writer = new ExcelWriter(output);
        } else {
            throw new IllegalArgumentException("Invalid output argument given: " + value);
        }
        return writer;
    }
    
    public static String getToAdresses(List<Person> persons){
        LeadersAndBestuurSorter lab = new LeadersAndBestuurSorter();
        Map<String, List<Person>> sorted = lab.sort(persons, false);
        
        String addresses = "";
        List<Person> leaders = sorted.get(SorterFactory.GROUP_LEADERS);
        for (Person leader : leaders) {
            if(addresses.length() > 0){
                addresses += ",";
            }
            addresses += leader.getLid_mailadres();
        }
        
        List<Person> bestuursLeden = sorted.get(SorterFactory.GROUP_BESTUUR);
        for (Person bestuursLid : bestuursLeden) {
            if(addresses.length() > 0){
                addresses += ",";
            }
            addresses += bestuursLid.getLid_mailadres();
        }
        
        return addresses;
    }
}
