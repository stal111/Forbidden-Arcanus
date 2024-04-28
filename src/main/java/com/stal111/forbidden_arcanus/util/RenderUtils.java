package com.stal111.forbidden_arcanus.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.IFluidTank;
import org.joml.Matrix4f;

public class RenderUtils {

    public static void renderFluid(PoseStack poseStack, IFluidTank fluidTank, FluidStack fluidStack, MultiBufferSource buffer, Matrix4f matrix, AABB boundingBox, int color, int combinedLight, int combinedOverlay) {
        Fluid fluid = fluidStack.getFluid();

        VertexConsumer builder = buffer.getBuffer(Sheets.translucentCullBlockSheet());
        PoseStack.Pose pose = poseStack.last();

        ResourceLocation resourceLocation = IClientFluidTypeExtensions.of(fluid).getStillTexture();
        TextureAtlasSprite texture = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(resourceLocation);

        int light1 = combinedLight & 0xFFFF;
        int light2 = combinedLight >> 0x10 & 0xFFFF;

        int a = color >> 24 & 0xFF;
        int r = color >> 16 & 0xFF;
        int g = color >> 8 & 0xFF;
        int b = color & 0xFF;

        AABB bounds = getRenderBounds(fluidTank, boundingBox);
        float x1 = (float) bounds.minX;
        float x2 = (float) bounds.maxX;
        float y1 = (float) bounds.minY;
        float y2 = (float) bounds.maxY;
        float z1 = (float) bounds.minZ;
        float z2 = (float) bounds.maxZ;
        float bx1 = (float) (bounds.minX * 16);
        float bx2 = (float) (bounds.maxX * 16);
        float by1 = (float) (bounds.minY * 16);
        float by2 = (float) (bounds.maxY * 16);
        float bz1 = (float) (bounds.minZ * 16);
        float bz2 = (float) (bounds.maxZ * 16);

        for (Direction direction : Direction.values()) {
            if (direction == Direction.DOWN) {
                float u1 = texture.getU(bx1);
                float u2 = texture.getU(bx2);
                float v1 = texture.getV(bz1);
                float v2 = texture.getV(bz2);
                builder.vertex(matrix, x1, y1, z2).color(r, g, b, a).uv(u1, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x1, y1, z1).color(r, g, b, a).uv(u1, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x2, y1, z1).color(r, g, b, a).uv(u2, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x2, y1, z2).color(r, g, b, a).uv(u2, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
            }

            if (direction == Direction.UP) {
                float u1 = texture.getU(bx1);
                float u2 = texture.getU(bx2);
                float v1 = texture.getV(bz1);
                float v2 = texture.getV(bz2);
                builder.vertex(matrix, x1, y2, z2).color(r, g, b, a).uv(u1, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x2, y2, z2).color(r, g, b, a).uv(u2, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x2, y2, z1).color(r, g, b, a).uv(u2, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x1, y2, z1).color(r, g, b, a).uv(u1, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
            }

            if (direction == Direction.NORTH) {
                float u1 = texture.getU(bx1);
                float u2 = texture.getU(bx2);
                float v1 = texture.getV(by1);
                float v2 = texture.getV(by2);
                builder.vertex(matrix, x1, y1, z1).color(r, g, b, a).uv(u1, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x1, y2, z1).color(r, g, b, a).uv(u1, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x2, y2, z1).color(r, g, b, a).uv(u2, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x2, y1, z1).color(r, g, b, a).uv(u2, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
            }

            if (direction == Direction.SOUTH) {
                float u1 = texture.getU(bx1);
                float u2 = texture.getU(bx2);
                float v1 = texture.getV(by1);
                float v2 = texture.getV(by2);
                builder.vertex(matrix, x2, y1, z2).color(r, g, b, a).uv(u2, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x2, y2, z2).color(r, g, b, a).uv(u2, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x1, y2, z2).color(r, g, b, a).uv(u1, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x1, y1, z2).color(r, g, b, a).uv(u1, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
            }

            if (direction == Direction.WEST) {
                float u1 = texture.getU(by1);
                float u2 = texture.getU(by2);
                float v1 = texture.getV(bz1);
                float v2 = texture.getV(bz2);
                builder.vertex(matrix, x1, y1, z2).color(r, g, b, a).uv(u1, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x1, y2, z2).color(r, g, b, a).uv(u2, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x1, y2, z1).color(r, g, b, a).uv(u2, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x1, y1, z1).color(r, g, b, a).uv(u1, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
            }

            if (direction == Direction.EAST) {
                float u1 = texture.getU(by1);
                float u2 = texture.getU(by2);
                float v1 = texture.getV(bz1);
                float v2 = texture.getV(bz2);
                builder.vertex(matrix, x2, y1, z1).color(r, g, b, a).uv(u1, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x2, y2, z1).color(r, g, b, a).uv(u2, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x2, y2, z2).color(r, g, b, a).uv(u2, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x2, y1, z2).color(r, g, b, a).uv(u1, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
            }
        }
    }

    public static void renderFluid(PoseStack poseStack, ResourceLocation texture, MultiBufferSource buffer, AABB boundingBox, int color, int combinedLight, int combinedOverlay) {
        poseStack.pushPose();

        VertexConsumer builder = buffer.getBuffer(Sheets.translucentCullBlockSheet());
        PoseStack.Pose pose = poseStack.last();

        TextureAtlasSprite atlasSprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(texture);

        int light1 = combinedLight & 0xFFFF;
        int light2 = combinedLight >> 0x10 & 0xFFFF;

        int a = color >> 24 & 0xFF;
        int r = color >> 16 & 0xFF;
        int g = color >> 8 & 0xFF;
        int b = color & 0xFF;

        float x1 = (float) boundingBox.minX;
        float x2 = (float) boundingBox.maxX;
        float y1 = (float) boundingBox.minY;
        float y2 = (float) boundingBox.maxY;
        float z1 = (float) boundingBox.minZ;
        float z2 = (float) boundingBox.maxZ;
        float bx1 = (float) (boundingBox.minX * 16);
        float bx2 = (float) (boundingBox.maxX * 16);
        float by1 = (float) (boundingBox.minY * 16);
        float by2 = (float) (boundingBox.maxY * 16);
        float bz1 = (float) (boundingBox.minZ * 16);
        float bz2 = (float) (boundingBox.maxZ * 16);

        for (Direction direction : Direction.values()) {
            if (direction == Direction.DOWN) {
                float u1 = atlasSprite.getU(bx1);
                float u2 = atlasSprite.getU(bx2);
                float v1 = atlasSprite.getV(bz1);
                float v2 = atlasSprite.getV(bz2);
                builder.vertex(pose, x1, y1, z2).color(r, g, b, a).uv(u1, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(pose, x1, y1, z1).color(r, g, b, a).uv(u1, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(pose, x2, y1, z1).color(r, g, b, a).uv(u2, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(pose, x2, y1, z2).color(r, g, b, a).uv(u2, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
            }

            if (direction == Direction.UP) {
                float u1 = atlasSprite.getU(bx1);
                float u2 = atlasSprite.getU(bx2);
                float v1 = atlasSprite.getV(bz1);
                float v2 = atlasSprite.getV(bz2);
                builder.vertex(pose, x1, y2, z2).color(255, 255, 255, 255).uv(u1, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(pose, x2, y2, z2).color(255, 255, 255, 255).uv(u2, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(pose, x2, y2, z1).color(255, 255, 255, 255).uv(u2, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(pose, x1, y2, z1).color(255, 255, 255, 255).uv(u1, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
            }

            if (direction == Direction.NORTH) {
                float u1 = atlasSprite.getU(bx1);
                float u2 = atlasSprite.getU(bx2);
                float v1 = atlasSprite.getV(by1);
                float v2 = atlasSprite.getV(by2);
                builder.vertex(pose, x1, y1, z1).color(r, g, b, a).uv(u1, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(pose, x1, y2, z1).color(r, g, b, a).uv(u1, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(pose, x2, y2, z1).color(r, g, b, a).uv(u2, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(pose, x2, y1, z1).color(r, g, b, a).uv(u2, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
            }

            if (direction == Direction.SOUTH) {
                float u1 = atlasSprite.getU(bx1);
                float u2 = atlasSprite.getU(bx2);
                float v1 = atlasSprite.getV(by1);
                float v2 = atlasSprite.getV(by2);
                builder.vertex(pose, x2, y1, z2).color(r, g, b, a).uv(u2, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(pose, x2, y2, z2).color(r, g, b, a).uv(u2, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(pose, x1, y2, z2).color(r, g, b, a).uv(u1, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(pose, x1, y1, z2).color(r, g, b, a).uv(u1, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
            }

            if (direction == Direction.WEST) {
                float u1 = atlasSprite.getU(by1);
                float u2 = atlasSprite.getU(by2);
                float v1 = atlasSprite.getV(bz1);
                float v2 = atlasSprite.getV(bz2);
                builder.vertex(pose, x1, y1, z2).color(r, g, b, a).uv(u1, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(pose, x1, y2, z2).color(r, g, b, a).uv(u2, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(pose, x1, y2, z1).color(r, g, b, a).uv(u2, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(pose, x1, y1, z1).color(r, g, b, a).uv(u1, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
            }

            if (direction == Direction.EAST) {
                float u1 = atlasSprite.getU(by1);
                float u2 = atlasSprite.getU(by2);
                float v1 = atlasSprite.getV(bz1);
                float v2 = atlasSprite.getV(bz2);
                builder.vertex(pose, x2, y1, z1).color(r, g, b, a).uv(u1, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(pose, x2, y2, z1).color(r, g, b, a).uv(u2, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(pose, x2, y2, z2).color(r, g, b, a).uv(u2, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(pose, x2, y1, z2).color(r, g, b, a).uv(u1, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
            }
        }

        poseStack.popPose();
    }

    private static AABB getRenderBounds(IFluidTank tank, AABB tankBounds) {
        float percent = (float) tank.getFluidAmount() / (float) tank.getCapacity();

        double tankHeight = tankBounds.maxY - tankBounds.minY;
        double y1 = tankBounds.minY, y2 = (tankBounds.minY + (tankHeight * percent));
        if (tank.getFluid().getFluid().getFluidType().isLighterThanAir()) {
            double yOff = tankBounds.maxY - y2;  // lighter than air fluids move to the top of the tank
            y1 += yOff; y2 += yOff;
        }
        return new AABB(tankBounds.minX, y1, tankBounds.minZ, tankBounds.maxX, y2, tankBounds.maxZ);
    }

    public static void addItemParticles(Level level, ItemStack stack, BlockPos pos, int count) {
        RandomSource random = level.getRandom();

        for(int i = 0; i < count; i++) {
            Vec3 offset = new Vec3((random.nextFloat() - 0.5D) * 0.1D, random.nextFloat() * 0.1D + 0.1D, 0.0D);
            Vec3 vector = new Vec3((random.nextFloat() - 0.5D) * 0.3D, -random.nextFloat() * 0.6D - 0.3D, 0.6D).add(ModUtils.blockPosToVector(pos));

            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack), vector.x, vector.y, vector.z, 1, offset.x, offset.y + 0.05D, offset.z, 0.0D);
            } else {
                level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, stack), vector.x, vector.y, vector.z, offset.x, offset.y + 0.05D, offset.z);
            }
        }
    }
}
