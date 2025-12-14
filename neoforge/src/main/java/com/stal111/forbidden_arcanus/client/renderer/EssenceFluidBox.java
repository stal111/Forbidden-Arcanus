package com.stal111.forbidden_arcanus.client.renderer;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import net.minecraft.resources.Identifier;
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
        ECTOPLASM(EssenceType.SOULS, "ectoplasm_still", "ectoplasm_flow"),
        BLOOD(EssenceType.BLOOD, "blood_still", "blood_flow"),
        EXPERIENCE(EssenceType.EXPERIENCE, "experience_still", "experience_flow");

        private final EssenceType essenceType;
        private final Identifier stillTexture;
        private final Identifier flowingTexture;

        Type(EssenceType type, String still, String flowing) {
            this.essenceType = type;
            this.stillTexture = ForbiddenArcanus.identifier("block/liquid/" + still);
            this.flowingTexture = ForbiddenArcanus.identifier("block/liquid/" + flowing);
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
