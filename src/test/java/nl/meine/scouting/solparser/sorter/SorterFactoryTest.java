/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        try{
            result = SorterFactory.createSorter("aapnootmies");
            fail();
        }catch(IllegalArgumentException e){
            assert(true);
        }


    }

}
