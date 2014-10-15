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
