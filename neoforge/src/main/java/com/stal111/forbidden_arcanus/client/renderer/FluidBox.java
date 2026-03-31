package com.stal111.forbidden_arcanus.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;

//TODO: move into core

/**
 * @author stal111
 * @since 29.04.2024
 */
public class FluidBox {

    private final TextureAtlasSprite stillTexture = null;
    private final TextureAtlasSprite flowingTexture = null;
    private final int[] color;
    private final AABB fullBounds;
    private AABB boundingBox;
    private float fillPercentage = 1.0F;

    public FluidBox(Identifier stillTexture, Identifier flowingTexture, int[] color, AABB fullBounds) {
//        this.stillTexture = FluidSpriteCache.getSprite(stillTexture);
//        this.flowingTexture = FluidSpriteCache.getSprite(flowingTexture);
        this.color = color;
        this.fullBounds = fullBounds;
        this.boundingBox = fullBounds;
    }

    public static FluidBox create(FluidStack fluid, AABB boundingBox) {
        IClientFluidTypeExtensions extensions = IClientFluidTypeExtensions.of(fluid.getFluid());

        return null;
//        Identifier stillTexture = extensions.getStillTexture();
//        Identifier flowingTexture = extensions.getFlowingTexture();
//        int color = extensions.getTintColor(fluid);
//
//        int a = color >> 24 & 0xFF;
//        int r = color >> 16 & 0xFF;
//        int g = color >> 8 & 0xFF;
//        int b = color & 0xFF;
//
//        return new FluidBox(stillTexture, flowingTexture, new int[]{r, g, b, a}, boundingBox);
    }

    public static FluidBox create(Identifier stillTexture, Identifier flowingTexture, AABB boundingBox) {
        return new FluidBox(stillTexture, flowingTexture, new int[]{255, 255, 255, 255}, boundingBox);
    }

    public void setFillPercentage(float percentage) {
        if (this.fillPercentage == percentage) {
            return;
        }

        this.fillPercentage = percentage;
        this.boundingBox = this.boundingBox.setMaxY(this.fullBounds.minY + (this.fullBounds.maxY - this.fullBounds.minY) * percentage);
    }

    public void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int lightCoords, int packedOverlay) {
        if (this.fillPercentage == 0) {
            return;
        }

        nodeCollector.submitCustomGeometry(poseStack, RenderTypes.translucentMovingBlock(), (pose, builder) -> {
            float minX = (float) boundingBox.minX;
            float maxX = (float) boundingBox.maxX;
            float minY = (float) boundingBox.minY;
            float maxY = (float) boundingBox.maxY;
            float minZ = (float) boundingBox.minZ;
            float maxZ = (float) boundingBox.maxZ;

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

                if (direction == Direction.DOWN) {
                    this.renderVertex(builder, pose, minX, minY, maxZ, u1, v2, lightCoords, packedOverlay, direction);
                    this.renderVertex(builder, pose, minX, minY, minZ, u1, v1, lightCoords, packedOverlay, direction);
                    this.renderVertex(builder, pose, maxX, minY, minZ, u2, v1, lightCoords, packedOverlay, direction);
                    this.renderVertex(builder, pose, maxX, minY, maxZ, u2, v2, lightCoords, packedOverlay, direction);
                }

                if (direction == Direction.UP) {
                    this.renderVertex(builder, pose, minX, maxY, maxZ, u1, v2, lightCoords, packedOverlay, direction);
                    this.renderVertex(builder, pose, maxX, maxY, maxZ, u1, v1, lightCoords, packedOverlay, direction);
                    this.renderVertex(builder, pose, maxX, maxY, minZ, u2, v1, lightCoords, packedOverlay, direction);
                    this.renderVertex(builder, pose, minX, maxY, minZ, u2, v2, lightCoords, packedOverlay, direction);
                }

                if (direction == Direction.NORTH) {
                    this.renderVertex(builder, pose, minX, minY, minZ, u1, v2, lightCoords, packedOverlay, direction);
                    this.renderVertex(builder, pose, minX, maxY, minZ, u1, v1, lightCoords, packedOverlay, direction);
                    this.renderVertex(builder, pose, maxX, maxY, minZ, u2, v1, lightCoords, packedOverlay, direction);
                    this.renderVertex(builder, pose, maxX, minY, minZ, u2, v2, lightCoords, packedOverlay, direction);
                }

                if (direction == Direction.SOUTH) {
                    this.renderVertex(builder, pose, maxX, minY, maxZ, u1, v2, lightCoords, packedOverlay, direction);
                    this.renderVertex(builder, pose, maxX, maxY, maxZ, u1, v1, lightCoords, packedOverlay, direction);
                    this.renderVertex(builder, pose, minX, maxY, maxZ, u2, v1, lightCoords, packedOverlay, direction);
                    this.renderVertex(builder, pose, minX, minY, maxZ, u2, v2, lightCoords, packedOverlay, direction);
                }

                if (direction == Direction.WEST) {
                    this.renderVertex(builder, pose, minX, minY, maxZ, u1, v2, lightCoords, packedOverlay, direction);
                    this.renderVertex(builder, pose, minX, maxY, maxZ, u1, v1, lightCoords, packedOverlay, direction);
                    this.renderVertex(builder, pose, minX, maxY, minZ, u2, v1, lightCoords, packedOverlay, direction);
                    this.renderVertex(builder, pose, minX, minY, minZ, u2, v2, lightCoords, packedOverlay, direction);
                }

                if (direction == Direction.EAST) {
                    this.renderVertex(builder, pose, maxX, minY, minZ, u1, v2, lightCoords, packedOverlay, direction);
                    this.renderVertex(builder, pose, maxX, maxY, minZ, u1, v1, lightCoords, packedOverlay, direction);
                    this.renderVertex(builder, pose, maxX, maxY, maxZ, u2, v1, lightCoords, packedOverlay, direction);
                    this.renderVertex(builder, pose, maxX, minY, maxZ, u2, v2, lightCoords, packedOverlay, direction);
                }
            }
        });
    }

    private void renderVertex(VertexConsumer vertexConsumer, PoseStack.Pose pose, float x, float y, float z, float u, float v, int lightCoords, int packedOverlay, Direction direction) {
        vertexConsumer.addVertex(pose, x, y, z).setColor(color[0], color[1], color[2], color[3]).setUv(u, v).setOverlay(packedOverlay).setLight(lightCoords).setNormal(pose, direction.getStepX(), direction.getStepY(), direction.getStepZ());
    }
}
