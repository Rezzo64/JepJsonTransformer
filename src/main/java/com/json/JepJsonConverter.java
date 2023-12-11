package com.json;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.*;

public class JepJsonConverter {
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
        JSONObject pokedexJson = createPokedex(speciesList);
    }

    private static void createLearnset(Map<String, DecompSpeciesData> jepSpeciesList) throws IOException {
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
        HashMap<String, PsSpeciesData> speciesLearnsetMap = new HashMap<>();

        for (String species : psJson.keySet()) {
            PsSpeciesData speciesData = new PsSpeciesData(psJson.getJSONObject(species));
            speciesLearnsetMap.put(species, speciesData);
        }

        for (String species : jepSpeciesList.keySet()) {
            PsSpeciesData speciesData = new PsSpeciesData(jepSpeciesList.get(species));
            // check for already existing species in previous file
            speciesLearnsetMap.merge(species, speciesData,
                    (speciesData1, speciesData2) -> speciesLearnsetMap.get(species).combineData(speciesData));
        }
        JSONObject newJsonLearnsets = new JSONObject(speciesLearnsetMap);
        Files.write(Paths.get("./src/main/resources/output.json"), newJsonLearnsets.toString().getBytes());
        System.out.println("Done!");
    }

    private static JSONObject createPokedex(HashMap<String, DecompSpeciesData> speciesList) {
        JSONObject jsonBuilder = new JSONObject();
        return jsonBuilder;
    }

}
