package com.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.constant.Constable;
import java.util.ArrayList;
import java.util.HashMap;

public class KepSpeciesData {

    private HashMap<String, ArrayList<String>> learnset;
    private HashMap<String, ArrayList<KepSpeciesEncountersData>>encounters;
    private ArrayList<String> learnMethods;
    private ArrayList<HashMap<String, Integer>> encountersList;
    private KepSpeciesEventData eventData;

    public KepSpeciesData(JSONObject speciesData) {
        learnset = new HashMap<>();
        encounters = new HashMap<>();
        encountersList = new ArrayList<>();
        learnMethods = new ArrayList<>();

        if (speciesData.has("learnset")) {
            JSONObject jsonSpeciesMoves = (JSONObject) speciesData.get("learnset");
            for (String move : jsonSpeciesMoves.keySet()) {
                JSONArray jsonLearnMethods = (JSONArray) jsonSpeciesMoves.get(move);
                for (int i = 0; i < jsonLearnMethods.length(); i++) {
                    learnMethods.add((String) jsonLearnMethods.get(i));
                }
                learnset.put(move, learnMethods);
            }
        }
        if (speciesData.has("encounters")) {
            JSONArray jsonEncounters = (JSONArray) speciesData.get("encounters");
            for (int i = 0; i < jsonEncounters.length(); i++) {
                JSONObject encounterData = (JSONObject) jsonEncounters.get(i);
                HashMap<String, Integer> encounter = new HashMap<>();
                KepSpeciesEncountersData e = new KepSpeciesEncountersData(encounterData);
                encounter.put("generation", e.getGeneration());
                encounter.put("level", e.getLevel());
//                if (encounterData.has("japan")) {
//                }
//                encountersList.add(encounter);
            }
        }
        if (speciesData.has("eventData")) {
            JSONArray eventDataJson = (JSONArray) speciesData.get("eventData");
        }


    }
}
