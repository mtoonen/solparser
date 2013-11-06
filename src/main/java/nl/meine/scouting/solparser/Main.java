/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.meine.scouting.solparser;

import nl.meine.scouting.solparser.writer.ExcelWriter;

/**
 *
 * @author Meine Toonen
 */
public class Main {
    
    public static void main(String[] args) throws Throwable {
        Parser p = new Parser("selectie_2871.csv", "aap.xls",new ExcelWriter());
        p.init();
        p.read();
        p.write();
    }
    
}
