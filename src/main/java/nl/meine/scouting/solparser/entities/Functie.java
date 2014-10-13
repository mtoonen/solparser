/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.meine.scouting.solparser.entities;

/**
 *
 * @author meine
 */
public class Functie {
    private String speltak;
    private String functie;

    public Functie(String speltak, String functie) {
        this.speltak = speltak;
        this.functie = functie;
    }

    
    public String getSpeltak() {
        return speltak;
    }

    public void setSpeltak(String speltak) {
        this.speltak = speltak;
    }

    public String getFunctie() {
        return functie;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
    }

    @Override
    public String toString() {
        return functie + " (" + speltak + ")";
    }
    
    
}
