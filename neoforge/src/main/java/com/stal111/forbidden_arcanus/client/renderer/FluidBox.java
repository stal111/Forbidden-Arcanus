package com.stal111.forbidden_arcanus.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;

//TODO: move into core

public record FluidBox(TextureAtlasSprite stillTexture, TextureAtlasSprite flowingTexture, int color) {

    public FluidBox(TextureAtlasSprite stillTexture, TextureAtlasSprite flowingTexture) {
        this(stillTexture, flowingTexture, 0xFFFFFFFF);
    }

    public static FluidBox create(FluidModel fluid, FluidState fluidState) {
        return new FluidBox(fluid.stillMaterial().sprite(), fluid.flowingMaterial().sprite(), fluid.fluidTintSource().color(fluidState));
    }

    public static FluidBox create(TextureAtlasSprite stillTexture, TextureAtlasSprite flowingTexture) {
        return new FluidBox(stillTexture, flowingTexture);
    }

    public void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int lightCoords, int packedOverlay, AABB bounds, float fillPercentage) {
        if (fillPercentage <= 0.0F) {
            return;
        }

        float clampedFill = Math.clamp(fillPercentage, 0.0F, 1.0F);
        float minX = (float) bounds.minX;
        float maxX = (float) bounds.maxX;
        float minY = (float) bounds.minY;
        float maxY = (float) (bounds.minY + (bounds.maxY - bounds.minY) * clampedFill);
        float minZ = (float) bounds.minZ;
        float maxZ = (float) bounds.maxZ;

        nodeCollector.submitCustomGeometry(poseStack, RenderTypes.translucentMovingBlock(), (pose, builder) -> {

            for (Direction direction : Direction.values()) {
                TextureAtlasSprite texture = direction.getAxis() == Direction.Axis.Y ? this.stillTexture : this.flowingTexture;

                float scale = direction.getAxis() == Direction.Axis.Y ? 1.0F : 0.5F;

                float u1, u2, v1, v2;
                switch (direction.getAxis()) {
                    case Y:
                        // top/bottom: U = X, V = Z
                        u1 = texture.getU(minX * scale);
                        u2 = texture.getU(maxX * scale);
                        v1 = texture.getV(minZ * scale);
                        v2 = texture.getV(maxZ * scale);
                        break;
                    case Z:
                        // north/south: U = X, V = Y
                        u1 = texture.getU(minX * scale);
                        u2 = texture.getU(maxX * scale);
                        v1 = texture.getV(minY * scale);
                        v2 = texture.getV(maxY * scale);
                        break;
                    case X:
                    default:
                        // east/west: U = Z, V = Y
                        u1 = texture.getU(minZ * scale);
                        u2 = texture.getU(maxZ * scale);
                        v1 = texture.getV(minY * scale);
                        v2 = texture.getV(maxY * scale);
                        break;
                }

                switch (direction) {
                    case DOWN -> {
                        this.renderVertex(builder, pose, minX, minY, maxZ, u1, v2, lightCoords, packedOverlay, direction);
                        this.renderVertex(builder, pose, minX, minY, minZ, u1, v1, lightCoords, packedOverlay, direction);
                        this.renderVertex(builder, pose, maxX, minY, minZ, u2, v1, lightCoords, packedOverlay, direction);
                        this.renderVertex(builder, pose, maxX, minY, maxZ, u2, v2, lightCoords, packedOverlay, direction);
                    }
                    case UP -> {
                        this.renderVertex(builder, pose, minX, maxY, maxZ, u1, v2, lightCoords, packedOverlay, direction);
                        this.renderVertex(builder, pose, maxX, maxY, maxZ, u1, v1, lightCoords, packedOverlay, direction);
                        this.renderVertex(builder, pose, maxX, maxY, minZ, u2, v1, lightCoords, packedOverlay, direction);
                        this.renderVertex(builder, pose, minX, maxY, minZ, u2, v2, lightCoords, packedOverlay, direction);
                    }
                    case NORTH -> {
                        this.renderVertex(builder, pose, minX, minY, minZ, u1, v2, lightCoords, packedOverlay, direction);
                        this.renderVertex(builder, pose, minX, maxY, minZ, u1, v1, lightCoords, packedOverlay, direction);
                        this.renderVertex(builder, pose, maxX, maxY, minZ, u2, v1, lightCoords, packedOverlay, direction);
                        this.renderVertex(builder, pose, maxX, minY, minZ, u2, v2, lightCoords, packedOverlay, direction);
                    }
                    case SOUTH -> {
                        this.renderVertex(builder, pose, maxX, minY, maxZ, u1, v2, lightCoords, packedOverlay, direction);
                        this.renderVertex(builder, pose, maxX, maxY, maxZ, u1, v1, lightCoords, packedOverlay, direction);
                        this.renderVertex(builder, pose, minX, maxY, maxZ, u2, v1, lightCoords, packedOverlay, direction);
                        this.renderVertex(builder, pose, minX, minY, maxZ, u2, v2, lightCoords, packedOverlay, direction);
                    }
                    case WEST -> {
                        this.renderVertex(builder, pose, minX, minY, maxZ, u1, v2, lightCoords, packedOverlay, direction);
                        this.renderVertex(builder, pose, minX, maxY, maxZ, u1, v1, lightCoords, packedOverlay, direction);
                        this.renderVertex(builder, pose, minX, maxY, minZ, u2, v1, lightCoords, packedOverlay, direction);
                        this.renderVertex(builder, pose, minX, minY, minZ, u2, v2, lightCoords, packedOverlay, direction);
                    }
                    case EAST -> {
                        this.renderVertex(builder, pose, maxX, minY, minZ, u1, v2, lightCoords, packedOverlay, direction);
                        this.renderVertex(builder, pose, maxX, maxY, minZ, u1, v1, lightCoords, packedOverlay, direction);
                        this.renderVertex(builder, pose, maxX, maxY, maxZ, u2, v1, lightCoords, packedOverlay, direction);
                        this.renderVertex(builder, pose, maxX, minY, maxZ, u2, v2, lightCoords, packedOverlay, direction);
                    }
                }
            }
        });
    }

    private void renderVertex(VertexConsumer vertexConsumer, PoseStack.Pose pose, float x, float y, float z, float u, float v, int lightCoords, int packedOverlay, Direction direction) {
        vertexConsumer.addVertex(pose, x, y, z).setColor(this.color).setUv(u, v).setOverlay(packedOverlay).setLight(lightCoords).setNormal(pose, direction.getStepX(), direction.getStepY(), direction.getStepZ());
    }
}
