package com.stal111.forbidden_arcanus.client.renderer;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;

/**
 * @author stal111
 * @since 29.04.2024
 */
public class EssenceFluidBox extends FluidBox {

    private final Type type;

    public EssenceFluidBox(Type type, AABB boundingBox) {
        super(type.stillTexture, type.flowingTexture, new int[] {255, 255, 255, 255}, boundingBox);
        this.type = type;
    }

    public static EssenceFluidBox create(Type type, AABB boundingBox) {
        return new EssenceFluidBox(type, boundingBox);
    }

    public Type getType() {
        return this.type;
    }

    public enum Type {
        AUREAL(EssenceType.AUREAL, "aureal_still", "aureal_flow"),
        BLOOD(EssenceType.BLOOD, "blood_still", "blood_flow"),
        EXPERIENCE(EssenceType.EXPERIENCE, "experience_still", "experience_flow");

        private final EssenceType essenceType;
        private final ResourceLocation stillTexture;
        private final ResourceLocation flowingTexture;

        Type(EssenceType type, String still, String flowing) {
            this.essenceType = type;
            this.stillTexture = new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/liquid/" + still);
            this.flowingTexture = new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/liquid/" + flowing);
        }

        public static Type byEssenceType(EssenceType type) {
            for (Type value : values()) {
                if (value.essenceType == type) {
                    return value;
                }
            }
            return null;
        }

        public EssenceType getEssenceType() {
            return this.essenceType;
        }
    }
}
