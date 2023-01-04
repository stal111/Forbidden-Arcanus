package com.stal111.forbidden_arcanus.common.block.entity.forge;

import java.util.function.IntSupplier;

/**
 * Hephaestus Forge Level
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeLevel
 *
 * @author stal111
 * @since 2021-06-29
 */
public enum HephaestusForgeLevel implements IntSupplier {
    ONE(1, 200, 200, 1, 5000, 200),
    TWO(2, 500, 500, 5, 10000, 300),
    THREE(3, 800, 800, 10, 30000, 400),
    FOUR(4, 1000, 1000, 100, 50000, 1000),
    X(5, 3000, 3000, 666, 100000, 2000);

    private final int index;

    private final int maxAureal;
    private final int maxCorruption;
    private final int maxSouls;
    private final int maxBlood;
    private final int maxExperience;

    HephaestusForgeLevel(int index, int maxAureal, int maxCorruption, int maxSouls, int maxBlood, int maxExperience) {
        this.index = index;
        this.maxAureal = maxAureal;
        this.maxCorruption = maxCorruption;
        this.maxSouls = maxSouls;
        this.maxBlood = maxBlood;
        this.maxExperience = maxExperience;
    }

    public int getIndex() {
        return index;
    }

    public int getMaxAureal() {
        return maxAureal;
    }

    public int getMaxCorruption() {
        return maxCorruption;
    }

    public int getMaxSouls() {
        return maxSouls;
    }

    public int getMaxBlood() {
        return maxBlood;
    }

    public int getMaxExperience() {
        return maxExperience;
    }

    public static HephaestusForgeLevel getFromIndex(int index) {
        return switch (index) {
            case 1 -> HephaestusForgeLevel.ONE;
            case 2 -> HephaestusForgeLevel.TWO;
            case 3 -> HephaestusForgeLevel.THREE;
            case 4 -> HephaestusForgeLevel.FOUR;
            default -> HephaestusForgeLevel.X;
        };
    }

    @Override
    public int getAsInt() {
        return this.index;
    }
}
