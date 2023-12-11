package com.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PsSpeciesData {

    private final HashMap<String, PsSpeciesMoveLearnData> moveLearnset;
    private ArrayList<JSONObject> encountersList;
    private ArrayList<KepSpeciesEncounterData> encounters;
    private ArrayList<JSONObject> eventDataList;
    private ArrayList<KepSpeciesEventData> eventData;
    private HashMap<String, Integer> hp, atk, def, spa, spd, spe;
    private String[] types;
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
                PsSpeciesMoveLearnData learnMethods = new PsSpeciesMoveLearnData(learnMethodsJson);
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

        for (String move : speciesData.getTmHmList()) {
            String psMove = handleMove(move);
            this.moveLearnset.put(psMove,
                    new PsSpeciesMoveLearnData(new String[]
                            {"2M"}
                    )
            );
        }
        for (String move : speciesData.getEvosList()) {
            String psMove = handleMove(move);
            this.moveLearnset.put(psMove,
                    new PsSpeciesMoveLearnData(new String[]
                            // Stadium 2 move re-learner makes this okay.
                            {"2L1"}
                    )
            );
        }
        for (String move : speciesData.getEggsList()) {
            String psMove = handleMove(move);
            this.moveLearnset.put(psMove,
                    new PsSpeciesMoveLearnData(new String[]
                            {"2E"}
                    )
            );
        }
    }
    private static String handleMove(String move) {
        move = move.replace("_", "").toLowerCase();
        if (move.equals("psychicm")) {
            return "psychic";
        }
        return move;
    }
    public HashMap<String, PsSpeciesMoveLearnData> getMoveLearnset() {
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

    public String[] getTypes() {
        return types;
    }
}
