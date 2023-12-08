package com.json;

import org.json.JSONObject;

import java.util.HashMap;

public class KepSpeciesEncountersData {
    private final Integer generation, level;
//    public final Boolean japan;
    public Integer getGeneration() {
        return generation;
    }
    public Integer getLevel() {
        return level;
    }

//    public Boolean getJapan() {
//        return japan;
//    }

    public KepSpeciesEncountersData(JSONObject encountersData) {
        this.generation = encountersData.getInt("generation");
        this.level = encountersData.getInt("level");
//        this.japan = encountersData.getBoolean("japan");
    }
}
