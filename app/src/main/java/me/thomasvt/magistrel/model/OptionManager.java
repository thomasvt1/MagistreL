package me.thomasvt.magistrel.model;

import java.util.ArrayList;
import java.util.List;


public class OptionManager {

    private static String[] countryArray = {"Agenda", "Huiswerk", "Cijfers", "Aanwezigheid", "Berichten", "ELO"};

    private static OptionManager mInstance;
    private List<Option> opties;

    public static OptionManager getInstance() {
        if (mInstance == null) {
            mInstance = new OptionManager();
        }

        return mInstance;
    }

    public List<Option> getOpties() {
        if (opties == null) {
            opties = new ArrayList<Option>();

            for (String countryName : countryArray) {
                Option country = new Option();
                country.name = countryName;
                country.imageName = countryName.replaceAll("\\s+","").toLowerCase();
                opties.add(country);
            }
        }

        return opties;
    }

}