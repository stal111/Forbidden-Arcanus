package com.stal111.forbidden_arcanus.common.block.entity.forge;

import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceSet;
import net.minecraft.util.StringRepresentable;

import java.util.function.IntSupplier;

/**
 * Hephaestus Forge Level
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeLevel
 *
 * @author stal111
 * @since 2021-06-29
 */
public enum HephaestusForgeLevel implements IntSupplier, StringRepresentable {
    ONE(1000, 10, 10000, 900),
    TWO(3000, 50, 15000, 1350),
    THREE(5000, 100, 30000, 2500),
    FOUR(10000, 500, 50000, 5000),
    FIVE(20000, 1000, 100000, 7500);

    private final EssenceSet maxEssences;

    HephaestusForgeLevel(int maxAureal, int maxSouls, int maxBlood, int maxExperience) {
        this.maxEssences = new EssenceSet(maxAureal, maxSouls, maxBlood, maxExperience);
    }

    public int getMaxAmount(EssenceType type) {
        return this.maxEssences.get(type);
    }

    public EssenceSet getMaxEssences() {
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
        return this.ordinal() + 1;
    }

    @Override
    public String getSerializedName() {
        return String.valueOf(this.getAsInt());
    }
}
