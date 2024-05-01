package com.stal111.forbidden_arcanus.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;

/**
 * @author stal111
 * @since 29.04.2024
 */
public class FluidBox {

    private final TextureAtlasSprite stillTexture;
    private final TextureAtlasSprite flowingTexture;
    private final int[] color;
    private AABB boundingBox;

    public FluidBox(ResourceLocation stillTexture, ResourceLocation flowingTexture, int[] color, AABB boundingBox) {
        this.stillTexture = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(stillTexture);
        this.flowingTexture = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(flowingTexture);
        this.color = color;
        this.boundingBox = boundingBox;
    }

    public static FluidBox create(FluidStack fluid, AABB boundingBox) {
        IClientFluidTypeExtensions extensions = IClientFluidTypeExtensions.of(fluid.getFluid());

        ResourceLocation stillTexture = extensions.getStillTexture();
        ResourceLocation flowingTexture = extensions.getFlowingTexture();
        int color = extensions.getTintColor(fluid);

        int a = color >> 24 & 0xFF;
        int r = color >> 16 & 0xFF;
        int g = color >> 8 & 0xFF;
        int b = color & 0xFF;

        return new FluidBox(stillTexture, flowingTexture, new int[] {r, g, b, a}, boundingBox);
    }

    public static FluidBox create(ResourceLocation stillTexture, ResourceLocation flowingTexture, AABB boundingBox) {
        return new FluidBox(stillTexture, flowingTexture, new int[] {255, 255, 255, 255}, boundingBox);
    }

    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        poseStack.pushPose();

        VertexConsumer builder = bufferSource.getBuffer(Sheets.translucentCullBlockSheet());
        PoseStack.Pose pose = poseStack.last();

        float x1 = (float) boundingBox.minX;
        float x2 = (float) boundingBox.maxX;
        float y1 = (float) boundingBox.minY;
        float y2 = (float) boundingBox.maxY;
        float z1 = (float) boundingBox.minZ;
        float z2 = (float) boundingBox.maxZ;
        float bx1 = (float) (boundingBox.minX);
        float bx2 = (float) (boundingBox.maxX);
        float by1 = (float) (boundingBox.minY);
        float by2 = (float) (boundingBox.maxY);
        float bz1 = (float) (boundingBox.minZ);
        float bz2 = (float) (boundingBox.maxZ);

        for (Direction direction : Direction.values()) {
            TextureAtlasSprite texture = direction.getAxis() == Direction.Axis.Y ? this.stillTexture : this.flowingTexture;

            float scale = direction.getAxis() == Direction.Axis.Y ? 1.0F : 0.5F;
            
            float u1 = texture.getU((direction.getAxis() == Direction.Axis.X ? by1 : bx1) * scale);
            float u2 = texture.getU((direction.getAxis() == Direction.Axis.X ? by2 : bx2) * scale);
            float v1 = texture.getV((direction.getAxis() == Direction.Axis.Z ? by1 : bz1) * scale);
            float v2 = texture.getV((direction.getAxis() == Direction.Axis.Z ? by2 : bz2) * scale);

            if (direction == Direction.DOWN) {
                this.renderVertex(builder, pose, x1, y1, z2, u1, v2, combinedLight, combinedOverlay, direction);
                this.renderVertex(builder, pose, x1, y1, z1, u1, v1, combinedLight, combinedOverlay, direction);
                this.renderVertex(builder, pose, x2, y1, z1, u2, v1, combinedLight, combinedOverlay, direction);
                this.renderVertex(builder, pose, x2, y1, z2, u2, v2, combinedLight, combinedOverlay, direction);
            }

            if (direction == Direction.UP) {
                this.renderVertex(builder, pose, x1, y2, z2, u1, v2, combinedLight, combinedOverlay, direction);
                this.renderVertex(builder, pose, x2, y2, z2, u1, v1, combinedLight, combinedOverlay, direction);
                this.renderVertex(builder, pose, x2, y2, z1, u2, v1, combinedLight, combinedOverlay, direction);
                this.renderVertex(builder, pose, x1, y2, z1, u2, v2, combinedLight, combinedOverlay, direction);
            }

            if (direction == Direction.NORTH) {
                this.renderVertex(builder, pose, x1, y1, z1, u1, v2, combinedLight, combinedOverlay, direction);
                this.renderVertex(builder, pose, x1, y2, z1, u1, v1, combinedLight, combinedOverlay, direction);
                this.renderVertex(builder, pose, x2, y2, z1, u2, v1, combinedLight, combinedOverlay, direction);
                this.renderVertex(builder, pose, x2, y1, z1, u2, v2, combinedLight, combinedOverlay, direction);
            }

            if (direction == Direction.SOUTH) {
                this.renderVertex(builder, pose, x2, y1, z2, u1, v2, combinedLight, combinedOverlay, direction);
                this.renderVertex(builder, pose, x2, y2, z2, u1, v1, combinedLight, combinedOverlay, direction);
                this.renderVertex(builder, pose, x1, y2, z2, u2, v1, combinedLight, combinedOverlay, direction);
                this.renderVertex(builder, pose, x1, y1, z2, u2, v2, combinedLight, combinedOverlay, direction);
            }

            if (direction == Direction.WEST) {
                this.renderVertex(builder, pose, x1, y1, z2, u1, v2, combinedLight, combinedOverlay, direction);
                this.renderVertex(builder, pose, x1, y2, z2, u1, v1, combinedLight, combinedOverlay, direction);
                this.renderVertex(builder, pose, x1, y2, z1, u2, v1, combinedLight, combinedOverlay, direction);
                this.renderVertex(builder, pose, x1, y1, z1, u2, v2, combinedLight, combinedOverlay, direction);
            }

            if (direction == Direction.EAST) {
                this.renderVertex(builder, pose, x2, y1, z1, u1, v2, combinedLight, combinedOverlay, direction);
                this.renderVertex(builder, pose, x2, y2, z1, u1, v1, combinedLight, combinedOverlay, direction);
                this.renderVertex(builder, pose, x2, y2, z2, u2, v1, combinedLight, combinedOverlay, direction);
                this.renderVertex(builder, pose, x2, y1, z2, u2, v2, combinedLight, combinedOverlay, direction);
            }
        }

        poseStack.popPose();
    }

    private void renderVertex(VertexConsumer vertexConsumer, PoseStack.Pose pose, float x, float y, float z, float u, float v, int packedLight, int packedOverlay, Direction direction) {
        vertexConsumer.vertex(pose, x, y, z).color(color[0], color[1], color[2], color[3]).uv(u, v).overlayCoords(packedOverlay).uv2(packedLight).normal(pose, direction.getStepX(), direction.getStepY(), direction.getStepZ()).endVertex();
    }
}
