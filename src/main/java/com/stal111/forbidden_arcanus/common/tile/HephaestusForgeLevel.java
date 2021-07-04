package com.stal111.forbidden_arcanus.common.tile;

/**
 * Hephaestus Forge Level
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.HephaestusForgeLevel
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-06-29
 */
public enum HephaestusForgeLevel {
    ONE("1", 0, 200, 200, 1, 5000, 200, 1),
    TWO("2", 1, 500, 500, 5, 10000, 300, 2),
    THREE("3", 2, 800, 800, 10, 30000, 400, 3),
    FOUR("4", 3, 1000, 1000, 100, 50000, 1000, 4),
    X("X", 4, 3000, 3000, 666, 100000, 2000, 4);

    private final String name;

    private final int index;

    private final int maxAureal;
    private final int maxCorruption;
    private final int maxSouls;
    private final int maxBlood;
    private final int maxExperience;

    private final int enhancerSlots;

    HephaestusForgeLevel(String name, int index, int maxAureal, int maxCorruption, int maxSouls, int maxBlood, int maxExperience, int enhancerSlots) {
        this.name = name;
        this.index = index;
        this.maxAureal = maxAureal;
        this.maxCorruption = maxCorruption;
        this.maxSouls = maxSouls;
        this.maxBlood = maxBlood;
        this.maxExperience = maxExperience;
        this.enhancerSlots = enhancerSlots;
    }

    public String getName() {
        return name;
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

    public int getEnhancerSlots() {
        return enhancerSlots;
    }

    public static HephaestusForgeLevel getRequiredLevelForSlot(int slot) {
        for (HephaestusForgeLevel level : HephaestusForgeLevel.values()) {
            if (level.getEnhancerSlots() == slot) {
                return level;
            }
        }
        return null;
    }

    public static HephaestusForgeLevel getFromName(String name) {
        switch (name) {
            case "2":   return HephaestusForgeLevel.TWO;
            case "3":   return HephaestusForgeLevel.THREE;
            case "4":   return HephaestusForgeLevel.FOUR;
            case "x":   return HephaestusForgeLevel.X;
            default:    return HephaestusForgeLevel.ONE;
        }
    }

    public static HephaestusForgeLevel getFromIndex(int index) {
        switch (index) {
            case 1:   return HephaestusForgeLevel.TWO;
            case 2:   return HephaestusForgeLevel.THREE;
            case 3:   return HephaestusForgeLevel.FOUR;
            case 4:   return HephaestusForgeLevel.X;
            default:    return HephaestusForgeLevel.ONE;
        }
    }
}
