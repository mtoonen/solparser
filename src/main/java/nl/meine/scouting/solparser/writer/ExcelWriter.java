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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import nl.meine.scouting.solparser.entities.Person;
import nl.meine.scouting.solparser.sorter.SorterFactory;
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

    protected CellStyle headingStyle;
    protected CellStyle normalStyle;
    protected FileOutputStream out = null;
    protected Workbook workbook;
    private static final int NUM_ATTRIBUTES_PER_PERSON = 24;
    private final short COLOR_UPDATED = IndexedColors.YELLOW.index;
    private final short COLOR_NEW = IndexedColors.LIGHT_BLUE.index;
    private final short COLOR_OVERVLIEGER = IndexedColors.BRIGHT_GREEN.index;

    protected File previous;

    private final static int NUM_LIDNUMMER_CELL = 0;
    private final static int NUM_SPELTAK_CELL = 19;

    public ExcelWriter( String output ){
        super(output);
    }

    @Override
    public void init() {
        try {
            out = new FileOutputStream(output);
            // create a new workbook
            workbook = new HSSFWorkbook();
            createStyles();

            previous = new File("data" + File.separator + "previous.xls");
        } catch (FileNotFoundException ex) {
            System.err.println("File Read Error" + ex.getLocalizedMessage());
        }
    }

    @Override
    public void write() {
        for (String sortKey : sortedPersons.keySet()) {
            Sheet sheet = workbook.createSheet(sortKey);
            List<Person> personList = sortedPersons.get(sortKey);
            createHeading(sheet);
            for (int i = 0; i < personList.size(); i++) {
                Person person = personList.get(i);
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
        processUpdates(sheet);
        if(sheet.getSheetName().equals(SorterFactory.GROUP_NAME_ALL)){
            workbook.setSheetOrder(SorterFactory.GROUP_NAME_ALL, 0);
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
            c.setCellStyle(normalStyle);

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
        cells[13].setCellValue(p.getLid_naam_ouder_verzorger());
        cells[14].setCellValue(p.getLid_mailadres_ouder_verzorger());
        cells[15].setCellValue(p.getLid_telefoonnummer_ouder_verzorger());
        cells[16].setCellValue(p.getLid_naam_ouder_verzorger_2());
        cells[17].setCellValue(p.getLid_mailadres_ouder_verzorger_2());
        cells[18].setCellValue(p.getLid_telefoonnummer_ouder_verzorger_2());
        cells[19].setCellValue(p.getAggregatedSpeleenheid());
        cells[20].setCellValue(p.getFunctie());
        cells[21].setCellValue(p.getLid_geboortedatum());
        cells[22].setCellValue(p.getFunctie_startdatum());
        cells[23].setCellValue(p.getOverige_informatie());

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
        r.createCell(13).setCellValue("Naam ouder/verzorger 1");
        r.createCell(14).setCellValue("Mail ouder/verzorger 1");
        r.createCell(15).setCellValue("Telefoonnummer ouder/verzorger 1");
        r.createCell(16).setCellValue("Naam ouder/verzorger 2");
        r.createCell(17).setCellValue("Mail ouder/verzorger 2");
        r.createCell(18).setCellValue("Telefoonnummer ouder/verzorger 2");
        r.createCell(19).setCellValue("Speltak");
        r.createCell(20).setCellValue("Functie");
        r.createCell(21).setCellValue("Geboortedatum");
        r.createCell(22).setCellValue("Functie startdatum");
        r.createCell(23).setCellValue("Overige informatie");

        Iterator<Cell> it = r.cellIterator();
        while (it.hasNext()) {
            Cell c = it.next();
            c.setCellStyle(headingStyle);
        }

        sheet.setAutoFilter(new CellRangeAddress(0, 0, 0, 23));
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

    private boolean hasPrevious(){
        return previous.exists();
    }

    private void processUpdates(Sheet sheet){
        if(hasPrevious()){
            FileInputStream previousStream = null;
            try {
                previousStream = new FileInputStream(previous);
                //Get the workbook instance for XLS file
                HSSFWorkbook prevWorkbook = new HSSFWorkbook(previousStream);
                Sheet prevSheet = prevWorkbook.getSheet(SorterFactory.GROUP_NAME_ALL);
                if(prevSheet != null){
                    for (Iterator<Row> it = sheet.rowIterator(); it.hasNext();) {
                        Row row = it.next();
                        if( row.getRowNum() > 0){
                            String lidnummer = row.getCell(NUM_LIDNUMMER_CELL).getStringCellValue();
                            Row previousRow = getPreviousLidRow(lidnummer, prevSheet);
                            processPersonUpdates(row, previousRow);
                        }

                    }
                }
            } catch (FileNotFoundException ex) {
                System.err.println("Could not locate file: "+ ex.getLocalizedMessage());
            } catch (IOException ex) {
                System.err.println("Problems reading file: "+ ex.getLocalizedMessage());
            } finally {
                try {
                    if(previousStream != null){
                        previousStream.close();
                    }
                } catch (IOException ex) {
                    System.err.println("Problems closing file: "+ ex.getLocalizedMessage());
                }
            }
        }
    }

    private Row getPreviousLidRow(String lidnummer, Sheet sheet){
        for (Iterator<Row> it = sheet.rowIterator(); it.hasNext();) {
            Row row = it.next();
            String oldLidnummer = row.getCell(NUM_LIDNUMMER_CELL).getStringCellValue();
            if(lidnummer.equals(oldLidnummer)){
                return row;
            }
        }
        return null;
    }

    private void processPersonUpdates( Row newRow, Row oldRow){
        String newSpeltak = newRow.getCell(NUM_SPELTAK_CELL).getStringCellValue();
        boolean isNew = oldRow == null;
        boolean isOvervlieger = false;
         if (!isNew) {
            String previousSpeltak = oldRow.getCell(NUM_SPELTAK_CELL).getStringCellValue();;
            if(!previousSpeltak.equals(newSpeltak)){
                isOvervlieger = true;
            }
        }

        for (Iterator<Cell> it = newRow.cellIterator(); it.hasNext();) {
            Cell newCell = it.next();
            if(isNew){
                updateCellColor(newCell, COLOR_NEW);
            }else{
                int colIndex = newCell.getColumnIndex();
                Cell oldCell = oldRow.getCell(colIndex);
                String newValue = newCell.getStringCellValue();
                String oldValue = oldCell.getStringCellValue();
                if(isOvervlieger){
                    updateCellColor(newCell, COLOR_OVERVLIEGER);
                }
                if(!newValue.equals(oldValue) && colIndex != NUM_SPELTAK_CELL){
                    updateCellColor(newCell, COLOR_UPDATED);
                }

            }
        }

    }

    private void updateCellColor(Cell cell, short color){
        CellStyle style = workbook.createCellStyle();;
        style.cloneStyleFrom(cell.getCellStyle());
        style.setFillForegroundColor(color);
        style.setFillPattern(PatternFormatting.SOLID_FOREGROUND);
        cell.setCellStyle(style);
    }

    private void replacePrevious(File source){
        InputStream in = null;
        try {
            File dataDir = new File("data");
            if(!dataDir.exists()){
                dataDir.mkdir();
            }
            in = new FileInputStream(source);
            OutputStream prevOut = new FileOutputStream(previous);
            // Copy the bits from instream to outstream
            byte[] buf = new byte[4096];
            int len;
            while ((len = in.read(buf)) > 0) {
                prevOut.write(buf, 0, len);
            }
            in.close();
            prevOut.close();
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
                System.err.println("Problems closing file: "+ ex.getLocalizedMessage());
            }
        }
    }

}
