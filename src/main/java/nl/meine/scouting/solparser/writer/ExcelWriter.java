/*
 *  Copyright (C) 2013 Meine Toonen
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.meine.scouting.solparser.entities.Person;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PatternFormatting;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author Meine Toonen
 */
public class ExcelWriter extends SolWriter{

    private CellStyle headingStyle;
    private CellStyle normalStyle;
    private CellStyle zebraStyle;
    private FileOutputStream out = null;
    private Workbook workbook;
    private static final int NUM_ATTRIBUTES_PER_PERSON = 18;
    
    public ExcelWriter( ){
    }

    @Override
    public void init() {
             try {
            out = new FileOutputStream(output);
            // create a new workbook
            workbook = new HSSFWorkbook();
            createStyles();

        } catch (FileNotFoundException ex) {
            System.err.println("File Read Error" + ex.getLocalizedMessage());
        }
    }

    @Override
    public void write() {
        
        // Make sheet for all the persons
        Sheet all = workbook.createSheet("Allemaal");
        createHeading(all);
        for (int i = 0; i < allPersons.size(); i++) {
            Person person = allPersons.get(i);
            createRow(person, all, i);
        }
        postProcessSheet(all);
        // create a new sheet
        for (String sortKey : sortedPersons.keySet()) {
            Sheet sheet = workbook.createSheet(sortKey);
            List<Person> personsPerEenheid = sortedPersons.get(sortKey);
            createHeading(sheet);
            for (int i = 0; i < personsPerEenheid.size(); i++) {
                Person person = personsPerEenheid.get(i);
                Row r = createRow(person, sheet, i);
            }

            postProcessSheet(sheet);
        }
    }

    private void postProcessSheet(Sheet sheet) {
        // Set the with to auto
        int numcells = sheet.getRow(0).getLastCellNum();
        for (int i = 0; i < numcells; i++) {
            sheet.autoSizeColumn(i);
        }
    }
    
     private Row createRow(Person p, Sheet sheet, int index) {
        // Skip the heading
        index++;
        Row r = sheet.createRow(index);
        Cell[] cells = new Cell[NUM_ATTRIBUTES_PER_PERSON];
        for (int i = 0; i < NUM_ATTRIBUTES_PER_PERSON; i++) {
            Cell c = r.createCell(i);
            cells[i] = c;
            if (index % 2 == 0) {
                c.setCellStyle(zebraStyle);
            } else {
                c.setCellStyle(normalStyle);
            }
        }
//        Lidnummer	Achternaam	tussenvoegsel	Roepnaam	Voorletters	geslacht	Adres	Huisnummer	Postcode	Woonplaats	
        cells[0].setCellValue(p.getLidnummer());
        cells[1].setCellValue(p.getLid_achternaam());
        cells[2].setCellValue(p.getLid_tussenvoegsel());
        cells[3].setCellValue(p.getLid_voornaam());
        cells[4].setCellValue(p.getLid_initialen());
        cells[5].setCellValue(p.getLid_geslacht());
        cells[6].setCellValue(p.getLid_straat());
        cells[7].setCellValue(p.getLid_huisnummer() + " " + p.getLid_toevoegsel_huisnr());
        cells[8].setCellValue(p.getLid_postcode());
        cells[9].setCellValue(p.getLid_plaats());
        //telefoon	mobiel lid	mobiel ouders 	
        //mobiel vader	mobiel moeder	// niet aanwezig!
        //e-mail lid	e-mail ouders	(Jeugd)lid bij Speltak	
        //Leiding bij Speltak	// Volgt uit tabbladen verdeling
        //Functie	Geboortedatum	Lid sinds
        cells[10].setCellValue(p.getLid_telefoon());
        cells[11].setCellValue(p.getLid_mobiel());
        cells[12].setCellValue(p.getLid_mailadres());
        cells[13].setCellValue(p.getLid_mailadres_ouder_verzorger());
        cells[14].setCellValue(p.getSpeleenheid());
        cells[15].setCellValue(p.getFunctie());
        cells[16].setCellValue(p.getLid_geboortedatum());
        cells[17].setCellValue(p.getFunctie_startdatum());

        return r;
    }

    private void createHeading(Sheet sheet) {
        Row r = sheet.createRow(0);
        r.createCell(0).setCellValue("Lidnummer");
        r.createCell(1).setCellValue("Achternaam");
        r.createCell(2).setCellValue("Tussenvoegsel");
        r.createCell(3).setCellValue("Voornaam");
        r.createCell(4).setCellValue("Initialen");
        r.createCell(5).setCellValue("Geslacht");
        r.createCell(6).setCellValue("Straat");
        r.createCell(7).setCellValue("Adres");
        r.createCell(8).setCellValue("Postcode");
        r.createCell(9).setCellValue("Plaats");
        r.createCell(10).setCellValue("Telefoonnummer");
        r.createCell(11).setCellValue("Mobiel");
        r.createCell(12).setCellValue("Mail lid");
        r.createCell(13).setCellValue("Mail ouder/verzorger");
        r.createCell(14).setCellValue("Speltak");
        r.createCell(15).setCellValue("Functie");
        r.createCell(16).setCellValue("Geboortedatum");
        r.createCell(17).setCellValue("Functie startdatm");

        Iterator<Cell> it = r.cellIterator();
        while (it.hasNext()) {
            Cell c = it.next();
            c.setCellStyle(headingStyle);
        }
        
        sheet.setAutoFilter(new CellRangeAddress(0, 0, 0, 17));
    }
    
    private void createStyles() {

        headingStyle = workbook.createCellStyle();
        Font f = workbook.createFont();

        //set font 1 to 12 point type
        f.setFontHeightInPoints((short) 12);
        // make it bold
        //arial is the default font
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headingStyle.setFont(f);
        //set a thin border
        headingStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        headingStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        headingStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        headingStyle.setBorderRight(CellStyle.BORDER_MEDIUM);

        normalStyle = workbook.createCellStyle();
        normalStyle.setBorderBottom(CellStyle.BORDER_THIN);
        normalStyle.setBorderLeft(CellStyle.BORDER_THIN);
        normalStyle.setBorderTop(CellStyle.BORDER_THIN);
        normalStyle.setBorderRight(CellStyle.BORDER_THIN);
        Font f2 = workbook.createFont();
        normalStyle.setFont(f2);

        zebraStyle = workbook.createCellStyle();
        zebraStyle.cloneStyleFrom(normalStyle);
        zebraStyle.setFillForegroundColor(IndexedColors.DARK_YELLOW.index);
        zebraStyle.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

    }
    
    @Override
    public void finalize() throws Throwable {
        super.finalize();
        try {
            workbook.write(out);
        } catch (IOException ex) {
            System.err.println("File write Error" + ex.getLocalizedMessage());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    System.err.println("File close Error" + ex.getLocalizedMessage());
                }
            }
        }
        replacePrevious(output);
    }
    private void replacePrevious(File source){
        InputStream in = null;
        try {
            File dataDir = new File("data");
            if(!dataDir.exists()){
                dataDir.mkdir();
            }
            File target = new File("data" + File.separator + "previous.xls");
            in = new FileInputStream(source);
            OutputStream out = new FileOutputStream(target);
            // Copy the bits from instream to outstream
            byte[] buf = new byte[4096];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException ex) {
            System.err.println("Could not locate file: "+ ex.getLocalizedMessage());
        } catch (IOException ex) {
            System.err.println("Problems writing file: "+ ex.getLocalizedMessage());
        } finally {
            try {
                if(in != null){
                    in.close();
                }
            } catch (IOException ex) {
                System.err.println("Problems locating file: "+ ex.getLocalizedMessage());
            }
        }
    }
    
}
