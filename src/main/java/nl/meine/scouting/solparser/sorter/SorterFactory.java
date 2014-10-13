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
package nl.meine.scouting.solparser.sorter;

/**
 *
 * @author Meine Toonen <toonen.meine@gmail.com>
 */
public class SorterFactory {

    public final static String LEADER_SORTER = "leaders";
    public final static String LEADER_AND_BESTUUR_SORTER = "leadersandbestuur";
    public final static String BESTUUR_SORTER = "leadersandbestuur";
    public final static String UNIT_SORTER = "unit";
    public final static String ONLYALL = "onlyall";

    public static Sorter createSorter(String value) {
        Sorter sorter = null;
        if (value == null) {
            sorter = new UnitSorter();
        } else if (value.equalsIgnoreCase(LEADER_SORTER)) {
            sorter = new LeadersSorter();
        } else if (value.equalsIgnoreCase(LEADER_AND_BESTUUR_SORTER)) {
            sorter = new LeadersAndBestuurSorter();
        } else if (value.equalsIgnoreCase(UNIT_SORTER)) {
            sorter = new UnitSorter();
        } else if (value.equalsIgnoreCase(BESTUUR_SORTER)) {
            sorter = new BestuurSorter();
        } else if (value.equalsIgnoreCase(ONLYALL)) {
            sorter = new OnlyAllSorter();
        } else {
            throw new IllegalArgumentException("Invalid sorter argument given: " + value);
        }
        return sorter;
    }

}