package com.json;

import org.json.JSONObject;

public class PsLearnsetEventData {
    private final Integer generation, level;
    private Object shiny = null;
    private Boolean japan = null;
    public PsLearnsetEventData(JSONObject eventData) {
        this.generation = eventData.getInt("generation");
        this.level = eventData.getInt("level");
        PsLearnsetEventMovesData moves = new PsLearnsetEventMovesData(eventData.getJSONArray("moves"));
        if (eventData.has("shiny")) {
            this.shiny = eventData.get("shiny");
        }
        if (eventData.has("japan")) {
            this.japan = eventData.getBoolean("japan");
        }
    }

    public int getGeneration() {
        return generation;
    }
    public int getLevel() {
        return level;
    }
    public Object getShiny() {
        return shiny;
    }
    public Boolean isJapan() {
        return japan;
    }
}

