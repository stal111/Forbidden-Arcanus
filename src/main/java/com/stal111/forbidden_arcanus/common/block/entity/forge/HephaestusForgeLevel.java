package com.stal111.forbidden_arcanus.common.block.entity.forge;

import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.EssenceType;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.EssencesDefinition;

import java.util.function.IntSupplier;

/**
 * Hephaestus Forge Level
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeLevel
 *
 * @author stal111
 * @since 2021-06-29
 */
public enum HephaestusForgeLevel implements IntSupplier {
    ONE(1, 500, 10, 5000, 500),
    TWO(2, 1000, 100, 10000, 1500),
    THREE(3, 2000, 500, 30000, 5000),
    FOUR(4, 5000, 1000, 50000, 15000),
    FIVE(5, 10000, 2500, 100000, 30000);

    private final int index;
    private final EssencesDefinition maxEssences;

    HephaestusForgeLevel(int index, int maxAureal, int maxSouls, int maxBlood, int maxExperience) {
        this.index = index;
        this.maxEssences = new EssencesDefinition(maxAureal, maxSouls, maxBlood, maxExperience);
    }

    public int getIndex() {
        return index;
    }

    public int getMaxAmount(EssenceType type) {
        return this.maxEssences.get(type);
    }

    public int getMaxAureal() {
        return this.maxEssences.get(EssenceType.AUREAL);
    }

    public int getMaxSouls() {
        return this.maxEssences.get(EssenceType.SOULS);
    }

    public int getMaxBlood() {
        return this.maxEssences.get(EssenceType.BLOOD);
    }

    public int getMaxExperience() {
        return this.maxEssences.get(EssenceType.EXPERIENCE);
    }

    public EssencesDefinition getMaxEssences() {
        return this.maxEssences;
    }

    public static HephaestusForgeLevel getFromIndex(int index) {
        return switch (index) {
            case 1 -> HephaestusForgeLevel.ONE;
            case 2 -> HephaestusForgeLevel.TWO;
            case 3 -> HephaestusForgeLevel.THREE;
            case 4 -> HephaestusForgeLevel.FOUR;
            default -> HephaestusForgeLevel.FIVE;
        };
    }

    @Override
    public int getAsInt() {
        return this.index;
    }
}
