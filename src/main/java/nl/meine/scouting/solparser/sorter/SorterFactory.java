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
package nl.meine.scouting.solparser.sorter;

/**
 *
 * @author Meine Toonen <toonen.meine@gmail.com>
 */
public class SorterFactory {


    public static Sorter createSorter(String value) {
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
        } else if (value.equalsIgnoreCase("onlyall")) {
            sorter = new OnlyAllSorter();
        } else {
            throw new IllegalArgumentException("Invalid sorter argument given: " + value);
        }
        return sorter;
    }

}