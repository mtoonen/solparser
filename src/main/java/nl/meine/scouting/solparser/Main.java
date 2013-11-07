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

import java.util.Properties;
import nl.meine.scouting.solparser.sorter.BestuurSorter;
import nl.meine.scouting.solparser.sorter.LeadersAndBestuurSorter;
import nl.meine.scouting.solparser.sorter.LeadersSorter;
import nl.meine.scouting.solparser.sorter.Sorter;
import nl.meine.scouting.solparser.sorter.UnitSorter;
import nl.meine.scouting.solparser.writer.ExcelWriter;
import nl.meine.scouting.solparser.writer.SolWriter;

/**
 *
 * @author Meine Toonen
 */
public class Main {

    public static void main(String[] args) throws Throwable {

        Properties prop = argsParser(args);
        if (prop.contains("action")) {
        } else {
            Sorter sorter = getSorter(prop.getProperty("sorter"));
            SolWriter writer = getWriter(prop.getProperty("outputtype"));
            boolean skipfirst = prop.getProperty("skipfirst") == null ? true : Boolean.parseBoolean(prop.getProperty("skipfirst"));
            String input = prop.getProperty("input");
            String output = prop.getProperty("output");
            if (input == null || output == null || input.isEmpty() || output.isEmpty()){
                throw new IllegalArgumentException("Input and/or output are not properly defined.");
            }
            Parser p = new Parser(input, output, writer, sorter);
            p.init();
            p.read();
            p.write();
        }

    }

    //-s leaders -o excel -sf true
    public static Properties argsParser(String[] args) {
        Properties prop = new Properties();
        for (int i = 0; i < args.length; i += 2) {
            String key = args[i];
            String value = args[i + 1];
            if (key.equalsIgnoreCase("-s")) {
                prop.setProperty("sorter", value);
            } else if (key.equalsIgnoreCase("-ot")) {
                prop.setProperty("outputtype", value);
            } else if (key.equalsIgnoreCase("-sf")) {
                prop.setProperty("skipfirst", value);
            } else if (key.equalsIgnoreCase("-h")) {
                prop.setProperty("action", "help");
            } else if (key.equalsIgnoreCase("-i")) {
                prop.setProperty("input", value);
            } else if (key.equalsIgnoreCase("-o")) {
                prop.setProperty("output", value);
            }
        }
        return prop;
    }

    public static Sorter getSorter(String value) {
        Sorter sorter = null;
        if (value == null) {
            sorter = new UnitSorter();
        } else if (value.equalsIgnoreCase("leaders")) {
            sorter = new LeadersSorter();
        } else if (value.equalsIgnoreCase("leadersandbestuur")) {
            sorter = new LeadersAndBestuurSorter();
        } else if (value.equalsIgnoreCase("unit")) {
            sorter = new UnitSorter();
        } else if (value.equalsIgnoreCase("bestuur")) {
            sorter = new BestuurSorter();
        } else {

            throw new IllegalArgumentException("Invalid sorter argument given: " + value);
        }
        return sorter;
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
