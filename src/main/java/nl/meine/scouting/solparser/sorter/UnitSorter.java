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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.meine.scouting.solparser.entities.Person;

/**
 *
 * @author Meine Toonen
 */
public class UnitSorter extends OnlyAllSorter {

    public static final String SHEET_NAME_UNIT_BEAVERS = "Bevers";
    public static final String SHEET_NAME_UNIT_CUBS_BOYS = "Welpen-jongens";
    public static final String SHEET_NAME_UNIT_CUBS_GIRLS = "Welpen-meisjes";
    public static final String SHEET_NAME_UNIT_SCOUTS_GIRLS = "Gidsen";
    public static final String SHEET_NAME_UNIT_SCOUTS_BOYS = "Verkenners";
    public static final String SHEET_NAME_UNIT_EXPLORERS = "Explorers Die Impeesa's";
    public static final String SHEET_NAME_UNIT_STAM = "Stam Arendsoog";
    public static final String SHEET_NAME_UNIT_STAMPLUS = "Vrienden van Jan";
    public static final String SHEET_NAME_UNIT_BOARD_GROUP = "Groepsbestuur";
    public static final String SHEET_NAME_UNIT_BOARD_OTHER = "stich.-ver-bestuur";
    public static final String SHEET_NAME_UNIT_EXTRAORDINARY = "Buitengewoon lid Scouting Jan v";
    
    public Map<String, List<Person>> sort(List<Person> allPersons, boolean includeall) {
        Map<String, List<Person>> sortedPersons = new HashMap();
        if (includeall) {
            sortedPersons.putAll(super.sort(allPersons, true));
        }
        for (Person person : allPersons) {
            Set<String> speleenheden = person.getSpeleenheid();
            for (String spelEenheid : speleenheden) {

                if (!sortedPersons.containsKey(spelEenheid)) {
                    sortedPersons.put(spelEenheid, new ArrayList());
                }
                sortedPersons.get(spelEenheid).add(person);

            }
        }
        return sortedPersons;
    }

    @Override
    public List<String> getOrder() {
        List<String> order = new ArrayList<String>();
        order.add(SHEET_NAME_UNIT_BEAVERS);
        order.add(SHEET_NAME_UNIT_CUBS_BOYS);
        order.add(SHEET_NAME_UNIT_CUBS_GIRLS);
        order.add(SHEET_NAME_UNIT_SCOUTS_BOYS);
        order.add(SHEET_NAME_UNIT_SCOUTS_GIRLS);
        order.add(SHEET_NAME_UNIT_EXPLORERS);
        order.add(SHEET_NAME_UNIT_STAM);
        order.add(SHEET_NAME_UNIT_BOARD_GROUP);
        order.add(SHEET_NAME_UNIT_BOARD_OTHER);
        order.add(SHEET_NAME_UNIT_STAMPLUS);
        order.add(SHEET_NAME_UNIT_EXTRAORDINARY);
        
        return order;
    }
}
