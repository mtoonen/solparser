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
package nl.meine.scouting.solparser.writer;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import nl.meine.scouting.solparser.Parser;
import nl.meine.scouting.solparser.ParserTest;
import nl.meine.scouting.solparser.entities.Person;
import nl.meine.scouting.solparser.sorter.Sorter;
import nl.meine.scouting.solparser.sorter.SorterFactory;
import nl.meine.scouting.solparser.sorter.UnitSorter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
    private static Sorter sorter;

    private ExcelWriter instance = new ExcelWriter("dummy.xls");

    public ExcelWriterTest() {
        super("dummy.xls");
    }


    @BeforeClass
    public static void setUpClass() throws URISyntaxException {

    }

    @AfterClass
    public static void tearDownClass() {

    }

    @Before
    public void setUp() throws URISyntaxException {

        File twopersons = ParserTest.getResource("twopersons.csv");
        sorter =  SorterFactory.createSorter(SorterFactory.SORTER_ONLYALL);
        Parser p = new Parser(twopersons ,sorter);

        p.read(true);
        persons = p.getAllPersons();
        sortedPersons = p.getSortedPersons();

        instance.setAllPersons(persons);
        instance.setSortedPersons(sortedPersons);
        instance.init();
    }

    @After
    public void tearDown() throws IOException {
        if(instance.output.exists()){
            instance.out.close();
            instance.output.delete();
        }
        if(instance.previous.exists()){
            instance.previous.delete();
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
    public void testWrite() throws Throwable {
        assertNotNull ("Persons must be initialized before callinig init", instance.allPersons);
        assertNotNull ("Persons must be initialized before callinig init", instance.sortedPersons);
        Date begin = new Date();
        instance.write();
        instance.postprocess(sorter.getOrder());
        instance.closeWriter();
        Date end = new Date();
        Long duration = end.getTime() - begin.getTime();
        assertTrue(instance.previous.lastModified() - instance.output.lastModified() < duration);
        assertTrue(instance.output.exists());

        assertEquals(1,instance.workbook.getNumberOfSheets());
        Sheet s = instance.workbook.getSheetAt(0);
        assertEquals(3, s.getPhysicalNumberOfRows());
        Row r1 = s.getRow(1);
        Cell c1 = r1.getCell(0);
        assertEquals ( true,c1.getStringCellValue().equalsIgnoreCase("16") || c1.getStringCellValue().equalsIgnoreCase("1616"));
        Row r2 = s.getRow(2);
        Cell c2 = r2.getCell(0);
        assertEquals ( true,c2.getStringCellValue().equalsIgnoreCase("16") || c2.getStringCellValue().equalsIgnoreCase("1616"));
        int a = 0;
    }

    /**
     * Test of closeWriter method, of class ExcelWriter.
     */
    @Test
    public void testcloseWriter() throws Exception, Throwable {
        System.out.println("closeWriter");
        instance.closeWriter();
      //  assertArrayEquals(IOUtils.toByteArray(new FileInputStream(instance.output)), IOUtils.toByteArray(new FileInputStream(instance.previous)));
        assertTrue(instance.output.exists());
        assertTrue(instance.previous.exists());
    }

    @Test
    public void testUpdates() throws Throwable{
        instance.write();
        instance.postprocess(sorter.getOrder());
        instance.closeWriter();

        File twopersons = ParserTest.getResource("twopersons_update.csv");
        Parser p = new Parser(twopersons, SorterFactory.createSorter(SorterFactory.SORTER_ONLYALL));

        p.read(true);
        persons = p.getAllPersons();
        sortedPersons = p.getSortedPersons();


        instance = new ExcelWriter("dummy.xls");
        instance.setAllPersons(allPersons);
        instance.setSortedPersons(sortedPersons);
        instance.init();
        instance.write();
        instance.postprocess(sorter.getOrder());
        instance.closeWriter();

        Workbook wb = instance.workbook;
        Sheet s = wb.getSheetAt(0);
        assertEquals(4, s.getPhysicalNumberOfRows());
        // lidnummer: 161616 is nieuw
        // lidnummer 16: straat geupdatet
        // lidnummer 1616 alles gelijk
        for(int i = 1 ; i < s.getPhysicalNumberOfRows() ;i++){
            Row r = s.getRow(i);
            Cell lidnummer = r.getCell(0);
            if(lidnummer.getStringCellValue().equals("16")){
                Cell straat = r.getCell(6);
                Cell huisnummer = r.getCell(7);
                assertEquals ( ExcelWriter.COLOR_UPDATED, straat.getCellStyle().getFillForegroundColor());
                assertEquals ( ExcelWriter.COLOR_UPDATED, huisnummer.getCellStyle().getFillForegroundColor());
            }else  if(lidnummer.getStringCellValue().equals("1616")){
                for(int j = 0 ; j < r.getPhysicalNumberOfCells() ;j++){
                    Cell c = r.getCell(j);
                    assertEquals(IndexedColors.AUTOMATIC.index, c.getCellStyle().getFillForegroundColor());
                }
            }else  if(lidnummer.getStringCellValue().equals("161616")){
                for(int j = 0 ; j < r.getPhysicalNumberOfCells() ;j++){
                    Cell c = r.getCell(j);
                    assertEquals(ExcelWriter.COLOR_NEW, c.getCellStyle().getFillForegroundColor());
                }
            }
        }
    }

    @Test
    public void testRemovedPerson() throws Throwable{
        instance.write();
        instance.postprocess(sorter.getOrder());
        instance.closeWriter();

        // lidnummer 16: weg
        // lidnummer 1616 alles gelijk
        File twopersons = ParserTest.getResource("twopersons_removed.csv");
        Parser p = new Parser(twopersons, SorterFactory.createSorter(SorterFactory.SORTER_ONLYALL));

        p.read(true);
        persons = p.getAllPersons();
        sortedPersons = p.getSortedPersons();


        instance = new ExcelWriter("dummy.xls");
        instance.setAllPersons(allPersons);
        instance.setSortedPersons(sortedPersons);
        instance.init();
        instance.write();
        instance.postprocess(sorter.getOrder());
        instance.closeWriter();

        Workbook wb = instance.workbook;
        Sheet s = wb.getSheetAt(0);
        assertEquals(2, s.getPhysicalNumberOfRows());

        Sheet removedSheet = wb.getSheet(ExcelWriter.SHEET_REMOVED_PERSONS);
        assertNotNull(removedSheet);
        assertEquals(2,removedSheet.getPhysicalNumberOfRows());
        Row r = removedSheet.getRow(1);
        Cell lidnummer = r.getCell(0);
        assertEquals("16", lidnummer.getStringCellValue());

    }

    @Test
    public void testOrderOfSheets() throws CloneNotSupportedException, Throwable{
        instance.write();
        instance.postprocess(sorter.getOrder());
        instance.closeWriter();

        // lidnummer 16: weg
        // lidnummer 1616 alles gelijk
        File twopersons = ParserTest.getResource("multipersons_multispeltak_removed.csv");
        Parser p = new Parser(twopersons, SorterFactory.createSorter(SorterFactory.SORTER_UNIT));

        p.read(true);
        persons = p.getAllPersons();
        sortedPersons = p.getSortedPersons();

        instance = new ExcelWriter("dummy.xls");
        instance.setAllPersons(persons);
        instance.setSortedPersons(sortedPersons);
        instance.init();
        instance.write();
        instance.postprocess(p.getSorter().getOrder());
        instance.closeWriter();
        assertEquals(13,instance.workbook.getNumberOfSheets());
        assertEquals(SorterFactory.GROUP_NAME_ALL, instance.workbook.getSheetAt(0).getSheetName());
        assertEquals(UnitSorter.SHEET_NAME_UNIT_BEAVERS, instance.workbook.getSheetAt(1).getSheetName());
        assertEquals(UnitSorter.SHEET_NAME_UNIT_CUBS_BOYS, instance.workbook.getSheetAt(2).getSheetName());
        assertEquals(UnitSorter.SHEET_NAME_UNIT_CUBS_GIRLS, instance.workbook.getSheetAt(3).getSheetName());
        assertEquals(UnitSorter.SHEET_NAME_UNIT_SCOUTS_BOYS, instance.workbook.getSheetAt(4).getSheetName());
        assertEquals(UnitSorter.SHEET_NAME_UNIT_SCOUTS_GIRLS, instance.workbook.getSheetAt(5).getSheetName());
        assertEquals(UnitSorter.SHEET_NAME_UNIT_EXPLORERS, instance.workbook.getSheetAt(6).getSheetName());
        assertEquals(UnitSorter.SHEET_NAME_UNIT_STAM, instance.workbook.getSheetAt(7).getSheetName());
        assertEquals(UnitSorter.SHEET_NAME_UNIT_BOARD_GROUP, instance.workbook.getSheetAt(8).getSheetName());
        assertEquals(UnitSorter.SHEET_NAME_UNIT_BOARD_OTHER, instance.workbook.getSheetAt(9).getSheetName());
        assertEquals(UnitSorter.SHEET_NAME_UNIT_STAMPLUS, instance.workbook.getSheetAt(10).getSheetName());
        assertEquals(UnitSorter.SHEET_NAME_UNIT_EXTRAORDINARY, instance.workbook.getSheetAt(11).getSheetName());
        assertEquals(ExcelWriter.SHEET_REMOVED_PERSONS, instance.workbook.getSheetAt(12).getSheetName());
    }
}
