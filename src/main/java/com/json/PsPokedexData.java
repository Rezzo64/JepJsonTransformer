package com.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PsPokedexData {
    private final Integer gen;
    private String name;
    private final ArrayList<String> types;
    private final HashMap<String, PsPokedexBaseStatsData> baseStats;
    private final ArrayList<String> evos;
    public PsPokedexData(DecompSpeciesData speciesData, String speciesName) {
        this.gen = 2;    // Manually correct this to 1 if necessary
        this.name = speciesName;
        if (this.name.endsWith("galar")) {
            this.name = this.name.substring(0, this.name.length() - 5) + "-Galar";
        }
        if (this.name.endsWith("alola")) {
            this.name = this.name.substring(0, this.name.length() - 5) + "-Alola";
        }
        if (this.name.endsWith("hisui")) {
            this.name = this.name.substring(0, this.name.length() - 5) + "-Hisui";
        }
        if (this.name.endsWith("paldea")) {
            this.name = this.name.substring(0, this.name.length() - 6) + "-Paldea";
        }
        this.baseStats = new HashMap<>();
        this.baseStats.put("baseStats", new PsPokedexBaseStatsData(speciesData));
        this.types = speciesData.getTypes();
        handleTypes(types);
        evos = new ArrayList<>();
        evos.add("");
    }
    private static void handleTypes(ArrayList<String> types) {
        if (Objects.equals(types.getFirst(), "PSYCHIC_TYPE")) {
            types.set(0, "Psychic");
        }
        types.set(0, types.get(0).replace("_", "").toLowerCase());
        types.set(0, (types.get(0).substring(0, 1).toUpperCase() + types.get(0).substring(1)));

        if (types.size() == 2) {
            if (Objects.equals(types.get(1), "PSYCHIC_TYPE")) {
                types.set(1, "Psychic");
            }
            types.set(1, types.get(1).replace("_", "").toLowerCase());
            types.set(1, (types.get(1).substring(0, 1).toUpperCase() + types.get(1).substring(1)));
            // Decomp stores monotypes twice - PS does not.
            if (types.get(0).equals(types.get(1))) {
                types.remove(1);
            }
        }
    }
    public Integer getGen() {
        return gen;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, PsPokedexBaseStatsData> getBaseStats() {
        return baseStats;
    }

    public ArrayList<String> getEvos() {
        return evos;
    }

    public ArrayList<String> getTypes() {
        return types;
    }
}
