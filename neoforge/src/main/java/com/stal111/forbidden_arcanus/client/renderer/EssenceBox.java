package com.stal111.forbidden_arcanus.client.renderer;

import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Sheets;
import net.valhelsia.valhelsia_core.api.client.FluidBox;

import java.util.EnumMap;
import java.util.Map;

public final class EssenceBox {

    private static final Map<EssenceType, FluidBox> ESSENCE_CACHE = new EnumMap<>(EssenceType.class);

    private EssenceBox() {
    }

    public static FluidBox forEssence(EssenceType type) {
        return ESSENCE_CACHE.computeIfAbsent(type, EssenceBox::create);
    }

    public static void clearCache() {
        ESSENCE_CACHE.clear();
    }

    private static FluidBox create(EssenceType type) {
        var stillTexture = Minecraft.getInstance().getAtlasManager().get(Sheets.BLOCKS_MAPPER.apply(type.getStillTexture()));
        var flowingTexture = Minecraft.getInstance().getAtlasManager().get(Sheets.BLOCKS_MAPPER.apply(type.getFlowingTexture()));

        return new FluidBox(stillTexture, flowingTexture);
    }
}
