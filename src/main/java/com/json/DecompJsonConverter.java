package com.json;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.*;

public class DecompJsonConverter {
    public static void main(String[] args) throws IOException {

        String decompLoc = ("src/main/resources/decomp.json");
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

    private static void ConvertJepData(LinkedHashMap<String, DecompSpeciesData> speciesList) throws IOException {
        createLearnset(speciesList);
        createPokedex(speciesList);
        System.out.println("Done!");
    }

    private static void createLearnset(Map<String, DecompSpeciesData> decompSpeciesList) throws IOException {
        String learnsetsLoc = "src/main/resources/learnsets.json";
        JSONObject psJson;
        String psJsonTxt;

        try {
            psJsonTxt = new String(Files.readAllBytes(Paths.get(learnsetsLoc)));
            psJson = new JSONObject(psJsonTxt);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // add all entries here
        HashMap<String, PsLearnsetData> speciesDataMap = new HashMap<>();

        for (String species : psJson.keySet()) {
            PsLearnsetData speciesData = new PsLearnsetData(psJson.getJSONObject(species));
            speciesDataMap.put(species, speciesData);
        }

        for (String species : decompSpeciesList.keySet()) {
            PsLearnsetData speciesData = new PsLearnsetData(decompSpeciesList.get(species));
            // check for already existing species in previous file
            speciesDataMap.merge(species, speciesData,
                    (speciesData1, speciesData2) -> speciesDataMap.get(species).combineData(speciesData));
        }
        JSONObject newJsonLearnsets = new JSONObject(speciesDataMap);
        Files.write(Paths.get("./src/main/resources/output-learnsets.json"), newJsonLearnsets.toString().getBytes());

    }

    private static void createPokedex(HashMap<String, DecompSpeciesData> decompSpeciesList) throws IOException {
        HashMap<String, PsPokedexData> pokedexDataMap = new HashMap<>();

        for (String species : decompSpeciesList.keySet()) {
            PsPokedexData pokedexData = new PsPokedexData(decompSpeciesList.get(species), species);
            pokedexDataMap.put(species, pokedexData);
        }
        JSONObject newJsonPokedex = new JSONObject(pokedexDataMap);
        Files.write(Paths.get("./src/main/resources/output-pokedex.json"), newJsonPokedex.toString().getBytes());
    }
}
