package com.json;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.util.ArrayList;

public class JepSpeciesData {
    ArrayList<String> tmHmList, evosList, eggsList;
    JsonArray tmHmJsonArray, evosJsonArray, eggsJsonArray = JsonValue.EMPTY_JSON_ARRAY;
    final Integer hp, atk, def, spe, spa, spd;
    String[] types;

    public JepSpeciesData(JsonObject speciesObject) {

        tmHmList = new ArrayList<>();
        evosList = new ArrayList<>();
        eggsList = new ArrayList<>();
        types = new String[2];

        if (speciesObject.containsKey("tmhm") && !speciesObject.isNull("tmhm")) {
            tmHmJsonArray = speciesObject.getJsonArray("tmhm");
            for (JsonValue move : tmHmJsonArray) {
                tmHmList.add(move.toString());
            }
        }
        if (speciesObject.containsKey("evos") && !speciesObject.isNull("evos")) {
            evosJsonArray = speciesObject.getJsonArray("evos");
            for (JsonValue move : evosJsonArray) {
                evosList.add(move.toString());
            }
        }
        if (speciesObject.containsKey("eggs") && !speciesObject.isNull("eggs")) {
            eggsJsonArray = speciesObject.getJsonArray("eggs");
            for (JsonValue move : eggsJsonArray) {
                eggsList.add(move.toString());
            }
        }

        JsonArray eggsList = speciesObject.getJsonArray("eggs");

        hp = speciesObject.getJsonNumber("hp").intValue();
        atk = speciesObject.getJsonNumber("atk").intValue();
        def = speciesObject.getJsonNumber("def").intValue();
        spe = speciesObject.getJsonNumber("spd").intValue();
        spa = speciesObject.getJsonNumber("sat").intValue();
        spd = speciesObject.getJsonNumber("sdf").intValue();
    }
    public ArrayList<String> getTmHmList() {
        return tmHmList;
    }

    public ArrayList<String> getEvosList() {
        return evosList;
    }

    public ArrayList<String> getEggsList() {
        return eggsList;
    }

    public JsonArray getTmHmJsonArray() {
        return tmHmJsonArray;
    }

    public JsonArray getEvosJsonArray() {
        return evosJsonArray;
    }

    public JsonArray getEggsJsonArray() {
        return eggsJsonArray;
    }

    public Integer getHp() {
        return hp;
    }

    public Integer getAtk() {
        return atk;
    }

    public Integer getDef() {
        return def;
    }

    public Integer getSpe() {
        return spe;
    }

    public Integer getSpa() {
        return spa;
    }

    public Integer getSpd() {
        return spd;
    }

    public String[] getTypes() {
        return types;
    }
}
