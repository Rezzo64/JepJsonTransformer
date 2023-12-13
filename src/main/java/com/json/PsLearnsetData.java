package com.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PsLearnsetData {

    private final HashMap<String, ArrayList<String>> learnset;
    private ArrayList<PsLearnsetEncounterData> encounters;
    private ArrayList<PsLearnsetEventData> eventData;
    public PsLearnsetData(JSONObject speciesData) {
        this.learnset = new HashMap<>();
        this.encounters = new ArrayList<>();
        this.eventData = new ArrayList<>();
        if (speciesData.has("learnset")) {
            JSONObject learnsetJson = (JSONObject) speciesData.get("learnset");
            for (String move : learnsetJson.keySet()) {
                JSONArray learnMethodsJson = (JSONArray) learnsetJson.get(move);
                ArrayList<String> learnMethods = new PsSpeciesMoveLearnData(learnMethodsJson).getLearnMethods();
                learnset.put(move, learnMethods);
            }
        }
        if (speciesData.has("encounters")) {
            JSONArray encountersJson = (JSONArray) speciesData.get("encounters");
            for (int i = 0; i < encountersJson.length(); i++) {
                JSONObject encounterJson = (JSONObject) encountersJson.get(i);
                encounters.add(new PsLearnsetEncounterData(encounterJson));
            }
        }
        if (speciesData.has("eventData")) {
            JSONArray eventDataJson = (JSONArray) speciesData.get("eventData");
            for (int i = 0; i < eventDataJson.length(); i++) {
                JSONObject eventJson = (JSONObject) eventDataJson.get(i);
                eventData.add(new PsLearnsetEventData(eventJson));
            }
        }
    }
    public PsLearnsetData(DecompSpeciesData speciesData) {

        this.learnset = new HashMap<>();


        for (String move : speciesData.getTmHmList()) {
            String psMove = handleMove(move);
            if (!this.learnset.containsKey(psMove)) {
                this.learnset.put(psMove, new ArrayList<>());
            }
            this.learnset.get(psMove).add("2M");
        }
        for (String move : speciesData.getEvosList()) {
            String psMove = handleMove(move);
            if (!this.learnset.containsKey(psMove)) {
                this.learnset.put(psMove, new ArrayList<>());
            }
            this.learnset.get(psMove).add("2L1");
        }
        for (String move : speciesData.getEggsList()) {
            String psMove = handleMove(move);
            if (!this.learnset.containsKey(psMove)) {
                this.learnset.put(psMove, new ArrayList<>());
            }
            this.learnset.get(psMove).add("2E");
        }
    }
    private static String handleMove(String move) {
        move = move.replace("_", "").toLowerCase();
        if (move.equals("psychicm")) {
            return "psychic";
        }
        return move;
    }

    // Smashes the decomp map and the learnsets map together using Facts & Logic.
    public PsLearnsetData combineData(PsLearnsetData incomingSpeciesData) {
        for (String move : incomingSpeciesData.learnset.keySet()) {
            if (this.learnset.containsKey(move)) {
                this.learnset.get(move).addAll(incomingSpeciesData.learnset.get(move));
            } else {
                this.learnset.put(move, incomingSpeciesData.learnset.get(move));
            }
        }
        return this;
    }
    public HashMap<String, ArrayList<String>> getLearnset() {
        return learnset;
    }

    public ArrayList<PsLearnsetEncounterData> getEncounters() {
        return encounters;
    }

    public ArrayList<PsLearnsetEventData> getEventData() {
        return eventData;
    }

}
