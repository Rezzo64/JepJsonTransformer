package com.json;

import org.json.JSONObject;

public class PsLearnsetEncounterData {
    private final Integer generation, level;

    private boolean japan;
    public PsLearnsetEncounterData(JSONObject encountersData) {
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
