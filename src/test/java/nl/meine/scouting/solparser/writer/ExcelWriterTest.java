/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.meine.scouting.solparser.writer;

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
public class ExcelWriterTest {

    public ExcelWriterTest() {
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
     * Test of init method, of class ExcelWriter.
     */
    @Test
    public void testInit() {
        System.out.println("init");
        ExcelWriter instance = new ExcelWriter("dummy.xls");
        instance.init();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of write method, of class ExcelWriter.
     */
    @Test
    public void testWrite() {
        System.out.println("write");
        ExcelWriter instance = new ExcelWriter("dummy.xls");
        instance.init();
        instance.write();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of finalize method, of class ExcelWriter.
     */
    @Test
    public void testFinalize() throws Exception, Throwable {
        System.out.println("finalize");
        ExcelWriter instance = new ExcelWriter("dummy.xls");
        instance.init();
        instance.finalize();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
