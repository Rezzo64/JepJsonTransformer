package com.json;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;

public class PsSpeciesMoveLearnData {
    private final ArrayList<String> learnMethods;
    PsSpeciesMoveLearnData(JSONArray methods) {
        learnMethods = new ArrayList<>();
        for (int i = 0; i < methods.length(); i++) {
            learnMethods.add(methods.getString(i));
        }
    }
    PsSpeciesMoveLearnData(String[] methods) {
        this.learnMethods = new ArrayList<>();
        this.learnMethods.addAll(Arrays.asList(methods));
    }

    public ArrayList<String> getLearnMethods() {
        return learnMethods;
    }
    public void add(ArrayList<String> learnMethodList) {
        this.learnMethods.addAll(learnMethodList);
    }
}
