package com.json;

public class PsPokedexBaseStatsData {
    private final Integer hp, atk, def, spa, spd, spe;

    public PsPokedexBaseStatsData(DecompSpeciesData speciesData) {
        this.hp = speciesData.getHp();
        this.atk = speciesData.getAtk();
        this.def = speciesData.getDef();
        this.spa = speciesData.getSpa();
        this.spd = speciesData.getSpd();
        this.spe = speciesData.getSpe();
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

    public Integer getSpa() {
        return spa;
    }

    public Integer getSpd() {
        return spd;
    }

    public Integer getSpe() {
        return spe;
    }
}
