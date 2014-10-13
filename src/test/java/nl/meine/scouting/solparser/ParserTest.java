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
package nl.meine.scouting.solparser;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import nl.meine.scouting.solparser.entities.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author meine
 */
public class ParserTest{
    Parser parser;
    
    public ParserTest(){
    }
    
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
        parser = new Parser(withHeader);
        parser.read(true);
        assertTrue(parser.getAllPersons().isEmpty());
        
        File withoutHeader = getResource("emptywithoutheader.csv");
        parser = new Parser(withoutHeader);
        parser.read(false);
        assertTrue(parser.getAllPersons().isEmpty());
        
        try{
            parser = new Parser(withoutHeader);
            parser.read(true);
            assertTrue(parser.getAllPersons().isEmpty());
        }catch(Exception e ){
            assert(true);
        }
        
        File onePerson = getResource("onelinewithheader.csv");
        parser = new Parser(onePerson);
        parser.read(true);
        assertTrue(parser.getAllPersons().size() == 1);

        Person p = parser.getAllPersons().get(0);
        
        assertEquals("Smit", p.getLid_achternaam());
        assertEquals("1985-12-31", p.getLid_geboortedatum());
        assertEquals("Groepsbestuur", p.getAggregatedSpeleenheid());
        assertEquals("Groepsbestuur (Groepsbestuur)", p.getFunctie());
        assertEquals("2011-07-17", p.getFunctie_startdatum());
        assertEquals("Actief", p.getFunctie_status());
        assertEquals(1, p.getFuncties().size());
        assertEquals("Man", p.getLid_geslacht());
        assertEquals("1", p.getLid_huisnummer());
        assertEquals("J", p.getLid_initialen());
        assertEquals("Nederland", p.getLid_land());
        assertEquals("jvsmit@gmail.com", p.getLid_mailadres());
        assertEquals("ovsmit@gmail.com", p.getLid_mailadres_ouder_verzorger());
        assertEquals("o2vsmit@gmail.com", p.getLid_mailadres_ouder_verzorger_2());
        assertEquals("06-15637494", p.getLid_mobiel());
        assertEquals("Oen Verwekker 1", p.getLid_naam_ouder_verzorger());
        assertEquals("Oen Verwekker 2", p.getLid_naam_ouder_verzorger_2());
        assertEquals("Laurastad", p.getLid_plaats());
        assertEquals("1234AB", p.getLid_postcode());
        assertEquals("Omaweg", p.getLid_straat());
        assertEquals("13456798", p.getLid_telefoon());
        assertEquals("69876543", p.getLid_telefoonnummer_ouder_verzorger());
        assertEquals("63456789", p.getLid_telefoonnummer_ouder_verzorger_2());
        assertEquals("A", p.getLid_toevoegsel_huisnr());
        assertEquals("Jan", p.getLid_voornaam());
        assertEquals("16", p.getLidnummer());
        assertEquals("Jan van Hoof-groep", p.getOrganisatie());
        assertEquals("Gouda", p.getOrganisatie_plaats());
        assertEquals("Waanideeen over zangkunsten", p.getOverige_informatie());       
        
        
        File onePersonTwoFunctions = getResource("onepersontwofunctionswithheader.csv");
        parser = new Parser(onePersonTwoFunctions);
        parser.read(true);
        assertTrue(parser.getAllPersons().size() == 1);
        p = parser.getAllPersons().get(0);
        assertEquals("Groepsbestuur, Groepsbestuur", p.getAggregatedSpeleenheid());
        assertEquals(1, p.getSpeleenheid().size());
        assertEquals(2, p.getFuncties().size());
        
        
        File onePersonTwoUnits = getResource("onepersontwounitswithheader.csv");
        parser = new Parser(onePersonTwoUnits);
        parser.read(true);
        assertTrue(parser.getAllPersons().size() == 1);
        p = parser.getAllPersons().get(0);
        assertEquals("Groepsbestuur, Groepsbestuur2", p.getAggregatedSpeleenheid() );
    }
    
    @Test
    public void testParserMultiPersons() throws URISyntaxException{
        File twopersons = getResource("twopersons.csv");
        parser = new Parser(twopersons);
        parser.read(true);
        assertEquals(2, parser.getAllPersons().size());
    }
    
    public File getResource(String name) throws URISyntaxException {
        File file = null;

        URL url = ParserTest.class.getResource(name);
        if (url == null) {
            url = ParserTest.class.getResource("/" + name);
            file = new File(url.toURI());
        }
        file = new File(url.toURI());

        return file;
    }
}
