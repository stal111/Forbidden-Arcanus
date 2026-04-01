package com.stal111.forbidden_arcanus.client.renderer;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.AABB;

/**
 * @author stal111
 * @since 29.04.2024
 */
public class EssenceFluidBox extends FluidBox {

    public EssenceFluidBox(TextureAtlasSprite stillTexture, TextureAtlasSprite flowingTexture, AABB boundingBox) {
        super(stillTexture, flowingTexture, new int[] {255, 255, 255, 255}, boundingBox);
    }

    public static EssenceFluidBox create(Type type, AABB boundingBox) {
        var stillTexture = Minecraft.getInstance().getAtlasManager().get(Sheets.BLOCKS_MAPPER.apply(type.stillTexture));
        var flowingTexture = Minecraft.getInstance().getAtlasManager().get(Sheets.BLOCKS_MAPPER.apply(type.flowingTexture));

        return new EssenceFluidBox(stillTexture, flowingTexture, boundingBox);
    }

    public enum Type {
        AUREAL(EssenceType.AUREAL, "aureal_still", "aureal_flow"),
        ECTOPLASM(EssenceType.SOULS, "ectoplasm_still", "ectoplasm_flow"),
        BLOOD(EssenceType.BLOOD, "blood_still", "blood_flow"),
        EXPERIENCE(EssenceType.EXPERIENCE, "experience_still", "experience_flow");

        private final EssenceType essenceType;
        private final Identifier stillTexture;
        private final Identifier flowingTexture;

        Type(EssenceType type, String still, String flowing) {
            this.essenceType = type;
            this.stillTexture = ForbiddenArcanus.identifier("liquid/" + still);
            this.flowingTexture = ForbiddenArcanus.identifier("liquid/" + flowing);
        }

        public static Type byEssenceType(EssenceType type) {
            for (Type value : values()) {
                if (value.essenceType == type) {
                    return value;
                }
            }
            return null;
        }
    }
}
