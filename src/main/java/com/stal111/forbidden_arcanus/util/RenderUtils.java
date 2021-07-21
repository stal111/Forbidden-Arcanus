package com.stal111.forbidden_arcanus.util;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.NewModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SSpawnParticlePacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class RenderUtils {

    public static final RenderType RENDER_TYPE = RenderType.makeType(
            ForbiddenArcanus.MOD_ID + ":block_render_type",
            DefaultVertexFormats.POSITION_COLOR_TEX_LIGHTMAP, 7, 256, true, false,
            RenderType.State.getBuilder().texture(new RenderState.TextureState(PlayerContainer.LOCATION_BLOCKS_TEXTURE, false, false))
                    .shadeModel(RenderType.SHADE_ENABLED)
                    .lightmap(RenderType.LIGHTMAP_ENABLED)
                    .texture(RenderType.BLOCK_SHEET_MIPPED)
                    .transparency(RenderType.TRANSLUCENT_TRANSPARENCY)
                    .build(false));

    private static final ClientWorld world = Minecraft.getInstance().world;

    public static void setRenderLayer(ModBlocks block, RenderType renderType) {
        RenderTypeLookup.setRenderLayer(block.getBlock(), renderType);
    }

    public static void renderFluid(IFluidTank fluidTank, FluidStack fluidStack, IRenderTypeBuffer buffer, Matrix4f matrix, AxisAlignedBB boundingBox, int color, int combinedLight) {
        Fluid fluid = fluidStack.getFluid();

        IVertexBuilder builder = buffer.getBuffer(RENDER_TYPE);

        ResourceLocation resourceLocation = fluid.getAttributes().getStillTexture();
        TextureAtlasSprite texture = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(resourceLocation);

        int light1 = combinedLight & 0xFFFF;
        int light2 = combinedLight >> 0x10 & 0xFFFF;

        int a = color >> 24 & 0xFF;
        int r = color >> 16 & 0xFF;
        int g = color >> 8 & 0xFF;
        int b = color & 0xFF;

        AxisAlignedBB bounds = getRenderBounds(fluidTank, boundingBox);
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
                float u1 = texture.getInterpolatedU(bx1);
                float u2 = texture.getInterpolatedU(bx2);
                float v1 = texture.getInterpolatedV(bz1);
                float v2 = texture.getInterpolatedV(bz2);
                builder.pos(matrix, x1, y1, z2).color(r, g, b, a).tex(u1, v2).lightmap(light1, light2).endVertex();
                builder.pos(matrix, x1, y1, z1).color(r, g, b, a).tex(u1, v1).lightmap(light1, light2).endVertex();
                builder.pos(matrix, x2, y1, z1).color(r, g, b, a).tex(u2, v1).lightmap(light1, light2).endVertex();
                builder.pos(matrix, x2, y1, z2).color(r, g, b, a).tex(u2, v2).lightmap(light1, light2).endVertex();
            }

            if (direction == Direction.UP) {
                float u1 = texture.getInterpolatedU(bx1);
                float u2 = texture.getInterpolatedU(bx2);
                float v1 = texture.getInterpolatedV(bz1);
                float v2 = texture.getInterpolatedV(bz2);
                builder.pos(matrix, x1, y2, z2).color(r, g, b, a).tex(u1, v2).lightmap(light1, light2).endVertex();
                builder.pos(matrix, x2, y2, z2).color(r, g, b, a).tex(u2, v2).lightmap(light1, light2).endVertex();
                builder.pos(matrix, x2, y2, z1).color(r, g, b, a).tex(u2, v1).lightmap(light1, light2).endVertex();
                builder.pos(matrix, x1, y2, z1).color(r, g, b, a).tex(u1, v1).lightmap(light1, light2).endVertex();
            }

            if (direction == Direction.NORTH) {
                float u1 = texture.getInterpolatedU(bx1);
                float u2 = texture.getInterpolatedU(bx2);
                float v1 = texture.getInterpolatedV(by1);
                float v2 = texture.getInterpolatedV(by2);
                builder.pos(matrix, x1, y1, z1).color(r, g, b, a).tex(u1, v1).lightmap(light1, light2).endVertex();
                builder.pos(matrix, x1, y2, z1).color(r, g, b, a).tex(u1, v2).lightmap(light1, light2).endVertex();
                builder.pos(matrix, x2, y2, z1).color(r, g, b, a).tex(u2, v2).lightmap(light1, light2).endVertex();
                builder.pos(matrix, x2, y1, z1).color(r, g, b, a).tex(u2, v1).lightmap(light1, light2).endVertex();
            }

            if (direction == Direction.SOUTH) {
                float u1 = texture.getInterpolatedU(bx1);
                float u2 = texture.getInterpolatedU(bx2);
                float v1 = texture.getInterpolatedV(by1);
                float v2 = texture.getInterpolatedV(by2);
                builder.pos(matrix, x2, y1, z2).color(r, g, b, a).tex(u2, v1).lightmap(light1, light2).endVertex();
                builder.pos(matrix, x2, y2, z2).color(r, g, b, a).tex(u2, v2).lightmap(light1, light2).endVertex();
                builder.pos(matrix, x1, y2, z2).color(r, g, b, a).tex(u1, v2).lightmap(light1, light2).endVertex();
                builder.pos(matrix, x1, y1, z2).color(r, g, b, a).tex(u1, v1).lightmap(light1, light2).endVertex();
            }

            if (direction == Direction.WEST) {
                float u1 = texture.getInterpolatedU(by1);
                float u2 = texture.getInterpolatedU(by2);
                float v1 = texture.getInterpolatedV(bz1);
                float v2 = texture.getInterpolatedV(bz2);
                builder.pos(matrix, x1, y1, z2).color(r, g, b, a).tex(u1, v2).lightmap(light1, light2).endVertex();
                builder.pos(matrix, x1, y2, z2).color(r, g, b, a).tex(u2, v2).lightmap(light1, light2).endVertex();
                builder.pos(matrix, x1, y2, z1).color(r, g, b, a).tex(u2, v1).lightmap(light1, light2).endVertex();
                builder.pos(matrix, x1, y1, z1).color(r, g, b, a).tex(u1, v1).lightmap(light1, light2).endVertex();
            }

            if (direction == Direction.EAST) {
                float u1 = texture.getInterpolatedU(by1);
                float u2 = texture.getInterpolatedU(by2);
                float v1 = texture.getInterpolatedV(bz1);
                float v2 = texture.getInterpolatedV(bz2);
                builder.pos(matrix, x2, y1, z1).color(r, g, b, a).tex(u1, v1).lightmap(light1, light2).endVertex();
                builder.pos(matrix, x2, y2, z1).color(r, g, b, a).tex(u2, v1).lightmap(light1, light2).endVertex();
                builder.pos(matrix, x2, y2, z2).color(r, g, b, a).tex(u2, v2).lightmap(light1, light2).endVertex();
                builder.pos(matrix, x2, y1, z2).color(r, g, b, a).tex(u1, v2).lightmap(light1, light2).endVertex();
            }
        }
    }

    private static AxisAlignedBB getRenderBounds(IFluidTank tank, AxisAlignedBB tankBounds) {
        float percent = (float) tank.getFluidAmount() / (float) tank.getCapacity();

        double tankHeight = tankBounds.maxY - tankBounds.minY;
        double y1 = tankBounds.minY, y2 = (tankBounds.minY + (tankHeight * percent));
        if (tank.getFluid().getFluid().getAttributes().isLighterThanAir()) {
            double yOff = tankBounds.maxY - y2;  // lighter than air fluids move to the top of the tank
            y1 += yOff; y2 += yOff;
        }
        return new AxisAlignedBB(tankBounds.minX, y1, tankBounds.minZ, tankBounds.maxX, y2, tankBounds.maxZ);
    }

    public static void addItemParticles(World world, ItemStack stack, BlockPos pos, int count) {
        Random random = world.getRandom();

        for(int i = 0; i < count; i++) {
            Vector3d offset = new Vector3d(((double) random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
            Vector3d vector = new Vector3d(((double) random.nextFloat() - 0.5D) * 0.3D, -random.nextFloat() * 0.6D - 0.3D, 0.6D);
            vector = vector.add(pos.getX(), pos.getY(), pos.getZ());

            if (world instanceof ServerWorld) {
                ((ServerWorld) world).spawnParticle(new ItemParticleData(ParticleTypes.ITEM, stack), vector.x, vector.y, vector.z, 1, offset.x, offset.y + 0.05D, offset.z, 0.0D);
            } else {
                world.addParticle(new ItemParticleData(ParticleTypes.ITEM, stack), vector.x, vector.y, vector.z, offset.x, offset.y + 0.05D, offset.z);
            }
        }
    }

    public static <T extends IParticleData> void spawnAurealMoteParticle(T type, ServerWorld world, double posX, double posY, double posZ, int particleCount, double xOffset, double yOffset, double zOffset, double speed) {
        SSpawnParticlePacket sspawnparticlepacket = new SSpawnParticlePacket(type, false, posX, posY, posZ, (float)xOffset, (float)yOffset, (float)zOffset, (float)speed, particleCount);

        for(int j = 0; j < world.getPlayers().size(); ++j) {
            ServerPlayerEntity player = world.getPlayers().get(j);
            if (player.inventory.hasItemStack(NewModItems.Stacks.LENS_OF_VERITATIS)) {
                world.sendPacketWithinDistance(player, false, posX, posY, posZ, sspawnparticlepacket);
            }
        }
    }
}
