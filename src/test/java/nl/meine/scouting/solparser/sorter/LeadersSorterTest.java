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

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import nl.meine.scouting.solparser.Parser;
import nl.meine.scouting.solparser.ParserTest;
import nl.meine.scouting.solparser.entities.Person;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author meine
 */
public class LeadersSorterTest {

    public LeadersSorterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of sort method, of class LeadersSorter.
     */
    @Test
    public void testSort() throws URISyntaxException {
        File twopersons = ParserTest.getResource("twopersons.csv");
        Parser parser = new Parser(twopersons, SorterFactory.createSorter(SorterFactory.SORTER_LEADERS));
        parser.read(true);
        assertEquals(2, parser.getAllPersons().size());

        Map<String,List<Person>> sorted = parser.getSortedPersons();
        assertEquals(2, sorted.keySet().size());
        assertTrue(sorted.containsKey(SorterFactory.GROUP_NAME_ALL));
        assertTrue(!sorted.containsKey(SorterFactory.GROUP_BESTUUR));
        assertTrue(sorted.containsKey(SorterFactory.GROUP_LEADERS));
        assertEquals(1, sorted.get(SorterFactory.GROUP_LEADERS).size());
    }

}
