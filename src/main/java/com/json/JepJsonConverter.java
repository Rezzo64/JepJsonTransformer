package com.json;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.*;

public class JepJsonConverter {
    public static void main(String[] args) {

        String decompLoc = ("src/main/resources/jepingame.json");
        JSONObject decompJson;
        String decompJsonTxt;
        try {
            decompJsonTxt = new String(Files.readAllBytes(Paths.get(decompLoc)));
            decompJson = new JSONObject(decompJsonTxt);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        LinkedHashMap<String, DecompSpeciesData> speciesList = new LinkedHashMap<>();
        System.out.println(decompJson.keySet());
        for (String species : decompJson.keySet()) {
            DecompSpeciesData speciesData = new DecompSpeciesData(decompJson.getJSONObject(species));
            speciesList.put(species, speciesData);
        }
        System.out.println("Species Data obtained...");
        ConvertJepData(speciesList);
    }

    private static void ConvertJepData(LinkedHashMap<String, DecompSpeciesData> speciesList) {
        JSONObject learnsetJson = createLearnset(speciesList);
        JSONObject pokedexJson = createPokedex(speciesList);
    }

    private static JSONObject createLearnset(Map<String, DecompSpeciesData> jepSpeciesList) {
        String learnsetsLoc = "src/main/resources/keplearnsets.json";
        JSONObject psJson;
        String psJsonTxt;

        try {
            psJsonTxt = new String(Files.readAllBytes(Paths.get(learnsetsLoc)));
            psJson = new JSONObject(psJsonTxt);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // add all entries here
        HashMap<String, PsSpeciesData> speciesLearnsetMap = new HashMap<>();

        for (String species : psJson.keySet()) {
            PsSpeciesData speciesData = new PsSpeciesData(psJson.getJSONObject(species));
            speciesLearnsetMap.put(species, speciesData);
        }

        for (String species : jepSpeciesList.keySet()) {
            PsSpeciesData speciesData = new PsSpeciesData(jepSpeciesList.get(species));

            // check for already existing species in previous file
            if (!speciesLearnsetMap.containsKey(species)) {
                speciesLearnsetMap.put(species, speciesData);
            } else {

            }
        }
        return psJson;
    }

    private static JSONObject createPokedex(Map<String, DecompSpeciesData> speciesList) {
        JSONObject jsonBuilder = new JSONObject();
        return jsonBuilder;
    }

}
