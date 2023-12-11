package com.json;

import org.json.JSONArray;

import java.util.ArrayList;

public class KepSpeciesEventMovesData {
    private final ArrayList<String> moves;

    KepSpeciesEventMovesData(JSONArray movesArray) {
        this.moves = new ArrayList<>();
        for (Object move : movesArray) {
            this.moves.add((String) move);
        }
    }

    public ArrayList<String> getMoves() {
        return moves;
    }
}
