package com.stal111.forbidden_arcanus.common.block.entity.forge;

import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.EssenceType;
import net.minecraft.Util;

import java.util.EnumMap;
import java.util.function.IntSupplier;

/**
 * Hephaestus Forge Level
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeLevel
 *
 * @author stal111
 * @since 2021-06-29
 */
public enum HephaestusForgeLevel implements IntSupplier {
    ONE(1, 200, 1, 5000, 200),
    TWO(2, 500, 5, 10000, 300),
    THREE(3, 800, 10, 30000, 400),
    FOUR(4, 1000, 100, 50000, 1000),
    X(5, 3000, 666, 100000, 2000);

    private final int index;
    private final EnumMap<EssenceType, Integer> maxEssences;

    HephaestusForgeLevel(int index, int maxAureal, int maxSouls, int maxBlood, int maxExperience) {
        this.index = index;
        this.maxEssences = Util.make(new EnumMap<>(EssenceType.class), map -> {
           map.put(EssenceType.AUREAL, maxAureal);
           map.put(EssenceType.SOULS, maxSouls);
           map.put(EssenceType.BLOOD, maxBlood);
           map.put(EssenceType.EXPERIENCE, maxExperience);
        });
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
