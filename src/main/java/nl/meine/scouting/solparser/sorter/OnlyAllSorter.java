/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.meine.scouting.solparser.sorter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.meine.scouting.solparser.entities.Person;

/**
 *
 * @author Meine Toonen <toonen.meine@gmail.com>
 */
public class OnlyAllSorter implements Sorter{

    public Map<String, List<Person>> sort(List<Person> allPersons) {
        Map<String, List<Person>> all = new HashMap();
        all.put("Allemaal", allPersons);
        return all;
    }
    
}
