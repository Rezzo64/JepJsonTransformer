package com.json;

import javax.json.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
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
        
        LinkedHashMap<String, JepSpeciesData> speciesList = new LinkedHashMap<>();
        System.out.println(jsonInput.keySet());
        for (String species : jsonInput.keySet()) {
            JepSpeciesData speciesData = new JepSpeciesData(jsonInput.getJsonObject(species));
            speciesList.put(species, speciesData);
        }
        System.out.println("Species Data obtained...");
        ConvertJepData(speciesList);
    }

    private static void ConvertJepData(LinkedHashMap<String, JepSpeciesData> speciesList) {
        JSONObject learnsetJson = createLearnset(speciesList);
        JSONObject pokedexJson = createPokedex(speciesList);
    }

    private static JSONObject createLearnset(Map<String, JepSpeciesData> speciesList) {
        String kepLearnsets = "src/main/resources/keplearnsets.json";
        JSONObject jsonBuilder;
        String kepJsonTxt;

        try {
            kepJsonTxt = new String(Files.readAllBytes(Paths.get(kepLearnsets)));
            jsonBuilder = new JSONObject(kepJsonTxt);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HashMap<String, KepSpeciesData> kepLearnsetMap = new HashMap<>();

        for (String species : jsonBuilder.keySet()) {
            KepSpeciesData speciesData = new KepSpeciesData(jsonBuilder.getJSONObject(species));
            kepLearnsetMap.put(species, speciesData);
        }

        for (String species : speciesList.keySet()) {
            for (String move : speciesList.get(species).tmHmList) {

            }
//            jsonBuilder.put(species,
//                    new JSONObject().put("learnset", ))
            jsonBuilder.put(species, speciesList.get(species).getTmHmList());
        }
        return jsonBuilder;
    }

    private static JSONObject createPokedex(Map<String, JepSpeciesData> speciesList) {
        JSONObject jsonBuilder = new JSONObject();
        return jsonBuilder;
    }

}
