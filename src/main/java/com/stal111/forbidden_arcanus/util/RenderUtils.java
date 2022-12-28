package com.stal111.forbidden_arcanus.util;

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
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class RenderUtils {

    public static void renderFluid(IFluidTank fluidTank, FluidStack fluidStack, MultiBufferSource buffer, Matrix4f matrix, Matrix3f normal, AABB boundingBox, int color, int combinedLight, int combinedOverlay) {
        Fluid fluid = fluidStack.getFluid();

        VertexConsumer builder = buffer.getBuffer(Sheets.translucentCullBlockSheet());

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
        double bx1 = bounds.minX * 16;
        double bx2 = bounds.maxX * 16;
        double by1 = bounds.minY * 16;
        double by2 = bounds.maxY * 16;
        double bz1 = bounds.minZ * 16;
        double bz2 = bounds.maxZ * 16;

        for (Direction direction : Direction.values()) {
            if (direction == Direction.DOWN) {
                float u1 = texture.getU(bx1);
                float u2 = texture.getU(bx2);
                float v1 = texture.getV(bz1);
                float v2 = texture.getV(bz2);
                builder.vertex(matrix, x1, y1, z2).color(r, g, b, a).uv(u1, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x1, y1, z1).color(r, g, b, a).uv(u1, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x2, y1, z1).color(r, g, b, a).uv(u2, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x2, y1, z2).color(r, g, b, a).uv(u2, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
            }

            if (direction == Direction.UP) {
                float u1 = texture.getU(bx1);
                float u2 = texture.getU(bx2);
                float v1 = texture.getV(bz1);
                float v2 = texture.getV(bz2);
                builder.vertex(matrix, x1, y2, z2).color(r, g, b, a).uv(u1, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x2, y2, z2).color(r, g, b, a).uv(u2, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x2, y2, z1).color(r, g, b, a).uv(u2, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x1, y2, z1).color(r, g, b, a).uv(u1, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
            }

            if (direction == Direction.NORTH) {
                float u1 = texture.getU(bx1);
                float u2 = texture.getU(bx2);
                float v1 = texture.getV(by1);
                float v2 = texture.getV(by2);
                builder.vertex(matrix, x1, y1, z1).color(r, g, b, a).uv(u1, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x1, y2, z1).color(r, g, b, a).uv(u1, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x2, y2, z1).color(r, g, b, a).uv(u2, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x2, y1, z1).color(r, g, b, a).uv(u2, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
            }

            if (direction == Direction.SOUTH) {
                float u1 = texture.getU(bx1);
                float u2 = texture.getU(bx2);
                float v1 = texture.getV(by1);
                float v2 = texture.getV(by2);
                builder.vertex(matrix, x2, y1, z2).color(r, g, b, a).uv(u2, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x2, y2, z2).color(r, g, b, a).uv(u2, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x1, y2, z2).color(r, g, b, a).uv(u1, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x1, y1, z2).color(r, g, b, a).uv(u1, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
            }

            if (direction == Direction.WEST) {
                float u1 = texture.getU(by1);
                float u2 = texture.getU(by2);
                float v1 = texture.getV(bz1);
                float v2 = texture.getV(bz2);
                builder.vertex(matrix, x1, y1, z2).color(r, g, b, a).uv(u1, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x1, y2, z2).color(r, g, b, a).uv(u2, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x1, y2, z1).color(r, g, b, a).uv(u2, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x1, y1, z1).color(r, g, b, a).uv(u1, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
            }

            if (direction == Direction.EAST) {
                float u1 = texture.getU(by1);
                float u2 = texture.getU(by2);
                float v1 = texture.getV(bz1);
                float v2 = texture.getV(bz2);
                builder.vertex(matrix, x2, y1, z1).color(r, g, b, a).uv(u1, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x2, y2, z1).color(r, g, b, a).uv(u2, v1).overlayCoords(combinedOverlay).uv2(light1, light2).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x2, y2, z2).color(r, g, b, a).uv(u2, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
                builder.vertex(matrix, x2, y1, z2).color(r, g, b, a).uv(u1, v2).overlayCoords(combinedOverlay).uv2(light1, light2).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
            }
        }
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
