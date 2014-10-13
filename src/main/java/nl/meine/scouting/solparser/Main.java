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

import java.util.Properties;
import nl.meine.scouting.solparser.sorter.Sorter;
import nl.meine.scouting.solparser.sorter.SorterFactory;
import nl.meine.scouting.solparser.writer.ExcelWriter;
import nl.meine.scouting.solparser.writer.SolWriter;

/**
 *
 * @author Meine Toonen
 */
public class Main {
    public static final String HELP_TEXT = "SolParser Library created by Meine Toonen. Version 0.5. \n"
            + "\t Use the SolParser library to convert a horrifying csv file to a more human readable excel file, with handy grouping options. The following parameters are permitted: \n"
            + "\t\t Usage: \n"
            + "\t\t\t-i \t Required: Name to input file. \n"
            + "\t\t\t-o \t Required: Name of the file to output. \n"
            + "\t\t\t-s \t Optional (default is unit): Sorting options: which tabs should be created. Available options: leaders, bestuur, leadersandbestuur, unit, onlyall \n"
            + "\t\t\t-ot \t Optional (default is excel): Which output should be generated. Available options: excel \n"
            + "\t\t\t-h \t Display this helptext. \n"
            + "\t\t\t-sf \t Optional (default true) Skip the first line of the csv value.";
    public static void main(String[] args) throws Throwable {

        Properties prop = argsParser(args);
        if (prop.containsKey("action")) {
            
            System.out.println(HELP_TEXT);
        } else {
            //-s onlyall -ot excel -sf true -i selectie_2871.csv -o aap.xls
            Sorter sorter = SorterFactory.createSorter(prop.getProperty("sorter"));
            SolWriter writer = getWriter(prop.getProperty("outputtype"));
            boolean skipfirst = prop.getProperty("skipfirst") == null ? true : Boolean.parseBoolean(prop.getProperty("skipfirst"));
            String input = prop.getProperty("input");
            String output = prop.getProperty("output");
            if (input == null || output == null || input.isEmpty() || output.isEmpty()){
                throw new IllegalArgumentException("Input and/or output are not properly defined.");
            }
            Parser p = new Parser(input, output, writer, sorter);
            p.init();
            p.read(skipfirst);
            p.write();
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
                }
            }
        }
        return prop;
    }

    public static SolWriter getWriter(String value) {
        SolWriter writer = null;
        if (value == null) {
            writer = new ExcelWriter();
        } else if (value.equalsIgnoreCase("excel")) {
            writer = new ExcelWriter();
        } else {
            throw new IllegalArgumentException("Invalid output argument given: " + value);
        }
        return writer;
    }
}
