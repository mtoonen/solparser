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
package nl.meine.scouting.solparser.entities;

/**
 *
 * @author Meine Toonen <toonen.meine@gmail.com>
 */
public class Person {
 
    private String lidnummer;
    private String lid_voornaam;
    private String lid_initialen;
    private String lid_tussenvoegsel;
    private String lid_achternaam;
    private String lid_straat;
    private String lid_huisnummer;
    private String lid_toevoegsel_huisnr;
    private String lid_postcode;
    private String lid_plaats;
    private String lid_land;
    private String lid_mailadres;
    private String lid_mailadres_ouder_verzorger;
    private String lid_naam_ouder_verzorger;
    private String lid_telefoonnummer_ouder_verzorger;
    private String lid_geslacht;
    private String lid_geboortedatum;
    private String lid_mobiel;
    private String lid_telefoon;
    private String functie;
    private String functie_startdatum;
    private String functie_status;
    private String functienummer;
    private String functietype;
    private String speleenheid_soort;
    private String speleenheid;
    private String speleenheidnummer;
    private String organisatienummer;
    private String organisatie_categorie;
    private String organisatie;
    private String organisatie_plaats;
    private String lid_naam_ouder_verzorger_2;
    private String lid_mailadres_ouder_verzorger_2;
    private String lid_telefoonnummer_ouder_verzorger_2;

    private String overige_informatie;

    public Person() {
    }

    public String getLidnummer() {
        return lidnummer;
    }

    public void setLidnummer(String lidnummer) {
        this.lidnummer = lidnummer;
    }

    public String getLid_voornaam() {
        return lid_voornaam;
    }

    public void setLid_voornaam(String lid_voornaam) {
        this.lid_voornaam = lid_voornaam;
    }

    public String getLid_initialen() {
        return lid_initialen;
    }

    public void setLid_initialen(String lid_initialen) {
        this.lid_initialen = lid_initialen;
    }

    public String getLid_tussenvoegsel() {
        return lid_tussenvoegsel;
    }

    public void setLid_tussenvoegsel(String lid_tussenvoegsel) {
        this.lid_tussenvoegsel = lid_tussenvoegsel;
    }

    public String getLid_achternaam() {
        return lid_achternaam;
    }

    public void setLid_achternaam(String lid_achternaam) {
        this.lid_achternaam = lid_achternaam;
    }

    public String getLid_straat() {
        return lid_straat;
    }

    public void setLid_straat(String lid_straat) {
        this.lid_straat = lid_straat;
    }

    public String getLid_huisnummer() {
        return lid_huisnummer;
    }

    public void setLid_huisnummer(String lid_huisnummer) {
        this.lid_huisnummer = lid_huisnummer;
    }

    public String getLid_toevoegsel_huisnr() {
        return lid_toevoegsel_huisnr;
    }

    public void setLid_toevoegsel_huisnr(String lid_toevoegsel_huisnr) {
        this.lid_toevoegsel_huisnr = lid_toevoegsel_huisnr;
    }

    public String getLid_postcode() {
        return lid_postcode;
    }

    public void setLid_postcode(String lid_postcode) {
        this.lid_postcode = lid_postcode;
    }

    public String getLid_plaats() {
        return lid_plaats;
    }

    public void setLid_plaats(String lid_plaats) {
        this.lid_plaats = lid_plaats;
    }

    public String getLid_land() {
        return lid_land;
    }

    public void setLid_land(String lid_land) {
        this.lid_land = lid_land;
    }

    public String getLid_mailadres() {
        return lid_mailadres;
    }

    public void setLid_mailadres(String lid_mailadres) {
        this.lid_mailadres = lid_mailadres;
    }

    public String getLid_mailadres_ouder_verzorger() {
        return lid_mailadres_ouder_verzorger;
    }

    public void setLid_mailadres_ouder_verzorger(String lid_mailadres_ouder_verzorger) {
        this.lid_mailadres_ouder_verzorger = lid_mailadres_ouder_verzorger;
    }

    public String getLid_geslacht() {
        return lid_geslacht;
    }

    public void setLid_geslacht(String lid_geslacht) {
        this.lid_geslacht = lid_geslacht;
    }

    public String getLid_geboortedatum() {
        return lid_geboortedatum;
    }

    public void setLid_geboortedatum(String lid_geboortedatum) {
        this.lid_geboortedatum = lid_geboortedatum;
    }

    public String getLid_mobiel() {
        return lid_mobiel;
    }

    public void setLid_mobiel(String lid_mobiel) {
        this.lid_mobiel = lid_mobiel;
    }

    public String getLid_telefoon() {
        return lid_telefoon;
    }

    public void setLid_telefoon(String lid_telefoon) {
        this.lid_telefoon = lid_telefoon;
    }

    public String getFunctie() {
        return functie;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
    }

    public String getFunctie_startdatum() {
        return functie_startdatum;
    }

    public void setFunctie_startdatum(String functie_startdatum) {
        this.functie_startdatum = functie_startdatum;
    }

    public String getFunctie_status() {
        return functie_status;
    }

    public void setFunctie_status(String functie_status) {
        this.functie_status = functie_status;
    }

    public String getFunctienummer() {
        return functienummer;
    }

    public void setFunctienummer(String functienummer) {
        this.functienummer = functienummer;
    }

    public String getFunctietype() {
        return functietype;
    }

    public void setFunctietype(String functietype) {
        this.functietype = functietype;
    }

    public String getSpeleenheid_soort() {
        return speleenheid_soort;
    }

    public void setSpeleenheid_soort(String speleenheid_soort) {
        this.speleenheid_soort = speleenheid_soort;
    }

    public String getSpeleenheid() {
        return speleenheid;
    }

    public void setSpeleenheid(String speleenheid) {
        this.speleenheid = speleenheid;
    }

    public String getSpeleenheidnummer() {
        return speleenheidnummer;
    }

    public void setSpeleenheidnummer(String speleenheidnummer) {
        this.speleenheidnummer = speleenheidnummer;
    }

    public String getOrganisatienummer() {
        return organisatienummer;
    }

    public void setOrganisatienummer(String organisatienummer) {
        this.organisatienummer = organisatienummer;
    }

    public String getOrganisatie_categorie() {
        return organisatie_categorie;
    }

    public void setOrganisatie_categorie(String organisatie_categorie) {
        this.organisatie_categorie = organisatie_categorie;
    }

    public String getOrganisatie() {
        return organisatie;
    }

    public void setOrganisatie(String organisatie) {
        this.organisatie = organisatie;
    }

    public String getOrganisatie_plaats() {
        return organisatie_plaats;
    }

    public void setOrganisatie_plaats(String organisatie_plaats) {
        this.organisatie_plaats = organisatie_plaats;
    }

    public String getLid_naam_ouder_verzorger_2() {
        return lid_naam_ouder_verzorger_2;
    }

    public void setLid_naam_ouder_verzorger_2(String lid_naam_ouder_verzorger_2) {
        this.lid_naam_ouder_verzorger_2 = lid_naam_ouder_verzorger_2;
    }

    public String getLid_mailadres_ouder_verzorger_2() {
        return lid_mailadres_ouder_verzorger_2;
    }

    public void setLid_mailadres_ouder_verzorger_2(String lid_mailadres_ouder_verzorger_2) {
        this.lid_mailadres_ouder_verzorger_2 = lid_mailadres_ouder_verzorger_2;
    }

    public String getLid_telefoonnummer_ouder_verzorger_2() {
        return lid_telefoonnummer_ouder_verzorger_2;
    }

    public void setLid_telefoonnummer_ouder_verzorger_2(String lid_telefoonnummer_ouder_verzorger_2) {
        this.lid_telefoonnummer_ouder_verzorger_2 = lid_telefoonnummer_ouder_verzorger_2;
    }

    public String getLid_naam_ouder_verzorger() {
        return lid_naam_ouder_verzorger;
    }

    public void setLid_naam_ouder_verzorger(String lid_naam_ouder_verzorger) {
        this.lid_naam_ouder_verzorger = lid_naam_ouder_verzorger;
    }

    public String getLid_telefoonnummer_ouder_verzorger() {
        return lid_telefoonnummer_ouder_verzorger;
    }

    public void setLid_telefoonnummer_ouder_verzorger(String lid_telefoonummer_ouder_verzorger) {
        this.lid_telefoonnummer_ouder_verzorger = lid_telefoonummer_ouder_verzorger;
    }

    public String getOverige_informatie() {
        return overige_informatie;
    }

    public void setOverige_informatie(String overige_informatie) {
        this.overige_informatie = overige_informatie;
    }
    
}
