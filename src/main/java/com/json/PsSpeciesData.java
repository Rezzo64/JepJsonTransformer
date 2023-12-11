package com.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class PsSpeciesData {

    private final HashMap<String, ArrayList<String>> moveLearnset;
    private ArrayList<JSONObject> encountersList;
    private ArrayList<KepSpeciesEncounterData> encounters;
    private ArrayList<JSONObject> eventDataList;
    private ArrayList<KepSpeciesEventData> eventData;
    private HashMap<String, Integer> hp, atk, def, spa, spd, spe;
    private ArrayList<String> types;
    public PsSpeciesData(JSONObject speciesData) {
        this.moveLearnset = new HashMap<>();
        this.encountersList = new ArrayList<>();
        this.encounters = new ArrayList<>();
        this.eventDataList = new ArrayList<>();
        this.eventData = new ArrayList<>();
        if (speciesData.has("learnset")) {
            JSONObject learnsetJson = (JSONObject) speciesData.get("learnset");
            for (String move : learnsetJson.keySet()) {
                JSONArray learnMethodsJson = (JSONArray) learnsetJson.get(move);
                ArrayList<String> learnMethods = new PsSpeciesMoveLearnData(learnMethodsJson).getLearnMethods();
                moveLearnset.put(move, learnMethods);
            }
        }
        if (speciesData.has("encounters")) {
            JSONArray encountersJson = (JSONArray) speciesData.get("encounters");
            for (int i = 0; i < encountersJson.length(); i++) {
                JSONObject encounterJson = (JSONObject) encountersJson.get(i);
                encountersList.add(encounterJson);
                encounters.add(new KepSpeciesEncounterData(encounterJson));
            }
        }
        if (speciesData.has("eventData")) {
            JSONArray eventDataJson = (JSONArray) speciesData.get("eventData");
            for (int i = 0; i < eventDataJson.length(); i++) {
                JSONObject eventJson = (JSONObject) eventDataJson.get(i);
                eventDataList.add(eventJson);
                eventData.add(new KepSpeciesEventData(eventJson));
            }
        }
    }
    public PsSpeciesData(DecompSpeciesData speciesData) {
        this.hp = new HashMap<>();
        this.atk = new HashMap<>();
        this.def = new HashMap<>();
        this.spa = new HashMap<>();
        this.spd = new HashMap<>();
        this.spe = new HashMap<>();
        hp.put("hp", speciesData.getHp());
        atk.put("atk", speciesData.getAtk());
        def.put("def", speciesData.getDef());
        spa.put("spa", speciesData.getSpa());
        spd.put("spd", speciesData.getSpd());
        spe.put("spe", speciesData.getSpe());
        this.moveLearnset = new HashMap<>();
        this.types = speciesData.getTypes();
        handleTypes(types);

        for (String move : speciesData.getTmHmList()) {
            String psMove = handleMove(move);
            if (!this.moveLearnset.containsKey(psMove)) {
                this.moveLearnset.put(psMove, new ArrayList<>());
            }
            this.moveLearnset.get(psMove).add("2M");
        }
        for (String move : speciesData.getEvosList()) {
            String psMove = handleMove(move);
            if (!this.moveLearnset.containsKey(psMove)) {
                this.moveLearnset.put(psMove, new ArrayList<>());
            }
            this.moveLearnset.get(psMove).add("2L1");
        }
        for (String move : speciesData.getEggsList()) {
            String psMove = handleMove(move);
            if (!this.moveLearnset.containsKey(psMove)) {
                this.moveLearnset.put(psMove, new ArrayList<>());
            }
            this.moveLearnset.get(psMove).add("2E");
        }
    }
    private static String handleMove(String move) {
        move = move.replace("_", "").toLowerCase();
        if (move.equals("psychicm")) {
            return "psychic";
        }
        return move;
    }
    private static void handleTypes(ArrayList<String> types) {
        if (Objects.equals(types.getFirst(), "PSYCHIC_TYPE")) {
            types.set(0, "Psychic");
        }
        types.set(0, types.get(0).replace("_", "").toLowerCase());
        types.set(0, (types.get(0).substring(0, 1).toUpperCase() + types.get(0).substring(1)));

        if (types.size() == 2) {
            if (Objects.equals(types.get(1), "PSYCHIC_TYPE")) {
                types.set(1, "Psychic");
            }
            types.set(1, types.get(1).replace("_", "").toLowerCase());
            types.set(1, (types.get(1).substring(0, 1).toUpperCase() + types.get(1).substring(1)));
            // Decomp stores monotypes twice - PS does not.
            if (types.get(0).equals(types.get(1))) {
                types.remove(1);
            }
        }

    }
    // Smashes the decomp map and the learnsets map together using Facts & Logic.
    public PsSpeciesData combineData(PsSpeciesData incomingSpeciesData) {
        for (String move : this.moveLearnset.keySet()) {
            if (incomingSpeciesData.moveLearnset.containsKey(move)) {
                incomingSpeciesData.moveLearnset.get(move).addAll(this.moveLearnset.get(move));
            }
        }
        return incomingSpeciesData;
    }
    public HashMap<String, ArrayList<String>> getMoveLearnset() {
        return moveLearnset;
    }

    public ArrayList<JSONObject> getEncountersList() {
        return encountersList;
    }

    public ArrayList<KepSpeciesEncounterData> getEncounters() {
        return encounters;
    }

    public ArrayList<JSONObject> getEventDataList() {
        return eventDataList;
    }

    public ArrayList<KepSpeciesEventData> getEventData() {
        return eventData;
    }

    public HashMap<String, Integer> getHp() {
        return hp;
    }

    public HashMap<String, Integer> getAtk() {
        return atk;
    }

    public HashMap<String, Integer> getDef() {
        return def;
    }

    public HashMap<String, Integer> getSpa() {
        return spa;
    }

    public HashMap<String, Integer> getSpd() {
        return spd;
    }

    public HashMap<String, Integer> getSpe() {
        return spe;
    }

    public ArrayList<String> getTypes() {
        return types;
    }
}
