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
import nl.meine.scouting.solparser.entities.Functie;
import nl.meine.scouting.solparser.entities.Person;

/**
 *
 * @author Meine Toonen
 */
public class LeadersSorter extends OnlyAllSorter {

    @Override
    public Map<String, List<Person>> sort(List<Person> allPersons, boolean includeall) {
        Map<String, List<Person>> sorted = new HashMap();
        if (includeall) {
            sorted.putAll(super.sort(allPersons, true));
        }
        List<Person> leaders = new ArrayList();
        for (Person p : allPersons) {
            for (Functie functieObj : p.getFuncties()) {
                String functie = functieObj.getFunctie();
                if (functie.equalsIgnoreCase("leid(st)er") || functie.equalsIgnoreCase("teamleid(st)er")) {
                    leaders.add(p);
                }
            }
        }
        sorted.put(SorterFactory.GROUP_LEADERS, leaders);
        return sorted;
    }

    @Override
    public List<String> getOrder() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
