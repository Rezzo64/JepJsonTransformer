package com.json;

import javax.json.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.*;

public class JepJsonConverter {
    public static void main(String[] args) {

        File input = new File("src/main/resources/jepingame.json");
        JsonObject jsonInput;
        try {
            JsonReader reader = Json.createReader(new FileInputStream(input));
            jsonInput = reader.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        
        LinkedHashMap<String, SpeciesData> speciesList = new LinkedHashMap<>();
        System.out.println(jsonInput.keySet());
        for (String species : jsonInput.keySet()) {
            SpeciesData speciesData = new SpeciesData(jsonInput.getJsonObject(species));
            speciesList.put(species, speciesData);
        }
        System.out.println("Species Data obtained...");
//        ConvertJepData(speciesList);
    }

    private static void ConvertJepData(LinkedHashMap<String, SpeciesData> speciesList) {
        JSONObject learnsetJson = createLearnset(speciesList);
        JSONObject pokedexJson = createPokedex(speciesList);
    }

    private static JSONObject createLearnset(Map<String, SpeciesData> speciesList) {
        JSONObject jsonBuilder = new JSONObject();


        for (String species : speciesList.keySet()) {
            for (String move : speciesList.get(species).tmHmList) {

            }
//            jsonBuilder.put(species,
//                    new JSONObject().put("learnset", ))
            jsonBuilder.put(species, speciesList.get(species).getTmHmList());
        }
        return jsonBuilder;
    }

    private static JSONObject createPokedex(Map<String, SpeciesData> speciesList) {
        JSONObject jsonBuilder = new JSONObject();
        return jsonBuilder;
    }

}
