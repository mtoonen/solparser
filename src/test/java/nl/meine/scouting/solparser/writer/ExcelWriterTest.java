/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.meine.scouting.solparser.writer;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import nl.meine.scouting.solparser.Parser;
import nl.meine.scouting.solparser.ParserTest;
import nl.meine.scouting.solparser.entities.Person;
import nl.meine.scouting.solparser.sorter.SorterFactory;
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
public class ExcelWriterTest extends ExcelWriter{

    private static Map<String, List<Person>> sortedPersons;
    private static List<Person> persons;
    
    private ExcelWriter instance = new ExcelWriter("dummy.xls");

    public ExcelWriterTest() {
        super("dummy.xls");
    }


    @BeforeClass
    public static void setUpClass() throws URISyntaxException {

        File twopersons = ParserTest.getResource("twopersons.csv");
        Parser p = new Parser(twopersons , SorterFactory.createSorter(SorterFactory.SORTER_ONLYALL));

        p.read(true);
        persons = p.getAllPersons();
        sortedPersons = p.getSortedPersons();
    }

    @AfterClass
    public static void tearDownClass() {
        
    }

    @Before
    public void setUp() {
        instance.setAllPersons(persons);
        instance.setSortedPersons(sortedPersons);
        instance.init();
    }

    @After
    public void tearDown() {
        if(instance.output.exists()){
            instance.output.delete();
        }
    }

    /**
     * Test of init method, of class ExcelWriter.
     */
    @Test
    public void testInit() {
        assertNotNull ("Persons must be initialized before callinig init", instance.allPersons);
        assertNotNull ("Persons must be initialized before callinig init", instance.sortedPersons);
        assertNotNull( instance.out);
        assertNotNull( instance.output);
        assertNotNull(instance.previous);
        assertNotNull(instance.workbook);
        assertNotNull(instance.headingStyle);
        assertNotNull(instance.normalStyle);

    }

    /**
     * Test of write method, of class ExcelWriter.
     */
    @Test
    public void testWrite() {
        assertNotNull ("Persons must be initialized before callinig init", instance.allPersons);
        assertNotNull ("Persons must be initialized before callinig init", instance.sortedPersons);
        Date begin = new Date();
        instance.write();
        Date end = new Date();
        Long duration = end.getTime() - begin.getTime();
        assertTrue(instance.previous.lastModified() - instance.output.lastModified() < duration);
        assertTrue(instance.previous.exists());
        assertTrue(instance.output.exists());
        
        assertEquals(1,instance.workbook.getNumberOfSheets());

    }

    /**
     * Test of finalize method, of class ExcelWriter.
     */
    @Test
    public void testFinalize() throws Exception, Throwable {
        System.out.println("finalize");
        instance.finalize();
        fail("The test case is a prototype.");
    }

}
