package com.json;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Objects;

public class DecompSpeciesData {
    ArrayList<String> tmHmList, evosList, eggsList, types;
    JSONArray tmHmJsonArray, evosJsonArray, eggsJsonArray = new JSONArray();
    final Integer hp, atk, def, spe, spa, spd;

    public DecompSpeciesData(JSONObject speciesObject) {

        tmHmList = new ArrayList<>();
        evosList = new ArrayList<>();
        eggsList = new ArrayList<>();
        types = new ArrayList<>();

        if (speciesObject.has("tmhm") && !speciesObject.isNull("tmhm")) {
            tmHmJsonArray = speciesObject.getJSONArray("tmhm");
            for (Object move : tmHmJsonArray) {
                tmHmList.add((String) move);
            }
        }
        if (speciesObject.has("evos") && !speciesObject.isNull("evos")) {
            evosJsonArray = speciesObject.getJSONArray("evos");
            for (Object move : evosJsonArray) {
                evosList.add((String) move);
            }
        }
        if (speciesObject.has("eggs") && !speciesObject.isNull("eggs")) {
            eggsJsonArray = speciesObject.getJSONArray("eggs");
            for (Object move : eggsJsonArray) {
                eggsList.add((String) move);
            }
        }

        hp = speciesObject.getInt("hp");
        atk = speciesObject.getInt("atk");
        def = speciesObject.getInt("def");
        spe = speciesObject.getInt("spd");
        spa = speciesObject.getInt("sat");
        spd = speciesObject.getInt("sdf");
        types.add(speciesObject.getJSONArray("types").getString(0));
        types.add(speciesObject.getJSONArray("types").getString(1));
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

    public JSONArray getTmHmJsonArray() {
        return tmHmJsonArray;
    }

    public JSONArray getEvosJsonArray() {
        return evosJsonArray;
    }

    public JSONArray getEggsJsonArray() {
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

    public ArrayList<String> getTypes() {
        return types;
    }
}
