/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.meine.scouting.solparser;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author meine
 */
public class ParserTest{
    Parser p;
    
    public ParserTest(){
       // super(null,null,null,null);
    }
    /*
    public ParserTest(String inputFile, String outputFile, SolWriter writer, Sorter sorter) {
        super(inputFile, outputFile, writer, sorter);
        p  = new Parser(inputFile, outputFile, writer, sorter);
    }*/
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testCreatePerson(){
        
    }
    
    @Test
    public void testRead() throws URISyntaxException{
        File withHeader = getResource("emptywithheader.csv");
        p = new Parser(withHeader);
        p.read(true);
        assertTrue(p.getAllPersons().isEmpty());
        
        File withoutHeader = getResource("emptywithoutheader.csv");
        p = new Parser(withoutHeader);
        p.read(false);
        assertTrue(p.getAllPersons().isEmpty());
        
        try{
            p = new Parser(withoutHeader);
            p.read(true);
            assertTrue(p.getAllPersons().isEmpty());
        }catch(Exception e ){
            assert(true);
        }
    }
    
    public File getResource(String name) throws URISyntaxException {
        File f = null;

        URL a = ParserTest.class.getResource(name);
        if (a == null) {
            a = ParserTest.class.getResource("/" + name);
            f = new File(a.toURI());

        }
        f = new File(a.toURI());
        int b = 0;

        return f;
    }
}
