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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.meine.scouting.solparser.entities.Person;

/**
 *
 * @author Meine Toonen
 */
public abstract class SolWriter {
    protected List<Person> allPersons;
    protected Map<String, List<Person>> sortedPersons;
    protected File output;

    public SolWriter(String output){
        this.output = new File(output);
    }

    public abstract void init();
    public abstract void write();

    @Override
    public void finalize() throws Throwable{
        super.finalize();
    }

    public List<Person> getAllPersons() {
        return allPersons;
    }

    public void setAllPersons(List<Person> allPersons) {
        this.allPersons = allPersons;
    }

    public Map<String, List<Person>> getSortedPersons() {
        return sortedPersons;
    }

    public void setSortedPersons(Map<String, List<Person>> sortedPersons) {
        this.sortedPersons = sortedPersons;
    }
}
