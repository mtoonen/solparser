/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.meine.scouting.solparser.sorter;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import nl.meine.scouting.solparser.Parser;
import nl.meine.scouting.solparser.ParserTest;
import nl.meine.scouting.solparser.entities.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author meine
 */
public class LeadersAndBestuurSorterTest {


    public LeadersAndBestuurSorterTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of sort method, of class LeadersAndBestuurSorter.
     */
    @Test
    public void testSort() throws URISyntaxException {
        File twopersons = ParserTest.getResource("twopersons.csv");
        Parser parser = new Parser(twopersons, SorterFactory.createSorter(SorterFactory.SORTER_LEADERS_AND_BESTUUR));
        parser.read(true);
        assertEquals(2, parser.getAllPersons().size());

        Map<String,List<Person>> sorted = parser.getSortedPersons();
        assertEquals(3, sorted.keySet().size());
        assertTrue(sorted.containsKey(SorterFactory.GROUP_NAME_ALL));
        assertTrue(sorted.containsKey(SorterFactory.GROUP_BESTUUR));
        assertTrue(sorted.containsKey(SorterFactory.GROUP_LEADERS));
        assertEquals(1, sorted.get(SorterFactory.GROUP_BESTUUR).size());
        assertEquals(1, sorted.get(SorterFactory.GROUP_LEADERS).size());
    }

}
