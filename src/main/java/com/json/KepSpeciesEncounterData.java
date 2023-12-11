package com.json;

import org.json.JSONObject;

public class KepSpeciesEncounterData {
    private final Integer generation, level;

    private boolean japan;
    public KepSpeciesEncounterData(JSONObject encountersData) {
        this.generation = encountersData.getInt("generation");
        this.level = encountersData.getInt("level");
        if (encountersData.has("japan")) {
            this.japan = encountersData.getBoolean("japan");
        }
    }
    public Integer getGeneration() {
        return generation;
    }
    public Integer getLevel() {
        return level;
    }
    public Boolean isJapan() {
        return japan;
    }

}
