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

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author meine
 */
public class SorterFactoryTest {

    public SorterFactoryTest() {
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
     * Test of createSorter method, of class SorterFactory.
     */
    @Test
    public void testCreateSorter() {

        Sorter result = SorterFactory.createSorter(SorterFactory.SORTER_LEADERS);
        assertTrue(result instanceof LeadersSorter);

        result = SorterFactory.createSorter(SorterFactory.SORTER_BESTUUR);
        assertTrue(result instanceof BestuurSorter);


        result = SorterFactory.createSorter(SorterFactory.SORTER_LEADERS_AND_BESTUUR);
        assertTrue(result instanceof LeadersAndBestuurSorter);


        result = SorterFactory.createSorter(SorterFactory.SORTER_ONLYALL);
        assertTrue(result instanceof OnlyAllSorter);


        result = SorterFactory.createSorter(SorterFactory.SORTER_UNIT);
        assertTrue(result instanceof UnitSorter);


        result = SorterFactory.createSorter(null);
        assertTrue(result instanceof UnitSorter);
        try{
            result = SorterFactory.createSorter("aapnootmies");
            fail();
        }catch(IllegalArgumentException e){
            assert(true);
        }


    }

}
