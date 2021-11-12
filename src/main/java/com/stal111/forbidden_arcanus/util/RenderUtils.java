package com.stal111.forbidden_arcanus.util;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.NewModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import com.mojang.math.Matrix4f;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class RenderUtils {

    public static final RenderType RENDER_TYPE = RenderType.create(
            ForbiddenArcanus.MOD_ID + ":block_render_type",
            DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder().setTextureState(new RenderStateShard.TextureStateShard(InventoryMenu.BLOCK_ATLAS, false, false))
                    .setLightmapState(RenderType.LIGHTMAP)
                    .setTextureState(RenderType.BLOCK_SHEET_MIPPED)
                    .setTransparencyState(RenderType.TRANSLUCENT_TRANSPARENCY)
                    .createCompositeState(false));

    private static final ClientLevel world = Minecraft.getInstance().level;

    public static void setRenderLayer(ModBlocks block, RenderType renderType) {
        ItemBlockRenderTypes.setRenderLayer(block.getBlock(), renderType);
    }

    public static void renderFluid(IFluidTank fluidTank, FluidStack fluidStack, MultiBufferSource buffer, Matrix4f matrix, AABB boundingBox, int color, int combinedLight) {
        Fluid fluid = fluidStack.getFluid();

        VertexConsumer builder = buffer.getBuffer(RENDER_TYPE);

        ResourceLocation resourceLocation = fluid.getAttributes().getStillTexture();
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
                builder.vertex(matrix, x1, y1, z2).color(r, g, b, a).uv(u1, v2).uv2(light1, light2).endVertex();
                builder.vertex(matrix, x1, y1, z1).color(r, g, b, a).uv(u1, v1).uv2(light1, light2).endVertex();
                builder.vertex(matrix, x2, y1, z1).color(r, g, b, a).uv(u2, v1).uv2(light1, light2).endVertex();
                builder.vertex(matrix, x2, y1, z2).color(r, g, b, a).uv(u2, v2).uv2(light1, light2).endVertex();
            }

            if (direction == Direction.UP) {
                float u1 = texture.getU(bx1);
                float u2 = texture.getU(bx2);
                float v1 = texture.getV(bz1);
                float v2 = texture.getV(bz2);
                builder.vertex(matrix, x1, y2, z2).color(r, g, b, a).uv(u1, v2).uv2(light1, light2).endVertex();
                builder.vertex(matrix, x2, y2, z2).color(r, g, b, a).uv(u2, v2).uv2(light1, light2).endVertex();
                builder.vertex(matrix, x2, y2, z1).color(r, g, b, a).uv(u2, v1).uv2(light1, light2).endVertex();
                builder.vertex(matrix, x1, y2, z1).color(r, g, b, a).uv(u1, v1).uv2(light1, light2).endVertex();
            }

            if (direction == Direction.NORTH) {
                float u1 = texture.getU(bx1);
                float u2 = texture.getU(bx2);
                float v1 = texture.getV(by1);
                float v2 = texture.getV(by2);
                builder.vertex(matrix, x1, y1, z1).color(r, g, b, a).uv(u1, v1).uv2(light1, light2).endVertex();
                builder.vertex(matrix, x1, y2, z1).color(r, g, b, a).uv(u1, v2).uv2(light1, light2).endVertex();
                builder.vertex(matrix, x2, y2, z1).color(r, g, b, a).uv(u2, v2).uv2(light1, light2).endVertex();
                builder.vertex(matrix, x2, y1, z1).color(r, g, b, a).uv(u2, v1).uv2(light1, light2).endVertex();
            }

            if (direction == Direction.SOUTH) {
                float u1 = texture.getU(bx1);
                float u2 = texture.getU(bx2);
                float v1 = texture.getV(by1);
                float v2 = texture.getV(by2);
                builder.vertex(matrix, x2, y1, z2).color(r, g, b, a).uv(u2, v1).uv2(light1, light2).endVertex();
                builder.vertex(matrix, x2, y2, z2).color(r, g, b, a).uv(u2, v2).uv2(light1, light2).endVertex();
                builder.vertex(matrix, x1, y2, z2).color(r, g, b, a).uv(u1, v2).uv2(light1, light2).endVertex();
                builder.vertex(matrix, x1, y1, z2).color(r, g, b, a).uv(u1, v1).uv2(light1, light2).endVertex();
            }

            if (direction == Direction.WEST) {
                float u1 = texture.getU(by1);
                float u2 = texture.getU(by2);
                float v1 = texture.getV(bz1);
                float v2 = texture.getV(bz2);
                builder.vertex(matrix, x1, y1, z2).color(r, g, b, a).uv(u1, v2).uv2(light1, light2).endVertex();
                builder.vertex(matrix, x1, y2, z2).color(r, g, b, a).uv(u2, v2).uv2(light1, light2).endVertex();
                builder.vertex(matrix, x1, y2, z1).color(r, g, b, a).uv(u2, v1).uv2(light1, light2).endVertex();
                builder.vertex(matrix, x1, y1, z1).color(r, g, b, a).uv(u1, v1).uv2(light1, light2).endVertex();
            }

            if (direction == Direction.EAST) {
                float u1 = texture.getU(by1);
                float u2 = texture.getU(by2);
                float v1 = texture.getV(bz1);
                float v2 = texture.getV(bz2);
                builder.vertex(matrix, x2, y1, z1).color(r, g, b, a).uv(u1, v1).uv2(light1, light2).endVertex();
                builder.vertex(matrix, x2, y2, z1).color(r, g, b, a).uv(u2, v1).uv2(light1, light2).endVertex();
                builder.vertex(matrix, x2, y2, z2).color(r, g, b, a).uv(u2, v2).uv2(light1, light2).endVertex();
                builder.vertex(matrix, x2, y1, z2).color(r, g, b, a).uv(u1, v2).uv2(light1, light2).endVertex();
            }
        }
    }

    private static AABB getRenderBounds(IFluidTank tank, AABB tankBounds) {
        float percent = (float) tank.getFluidAmount() / (float) tank.getCapacity();

        double tankHeight = tankBounds.maxY - tankBounds.minY;
        double y1 = tankBounds.minY, y2 = (tankBounds.minY + (tankHeight * percent));
        if (tank.getFluid().getFluid().getAttributes().isLighterThanAir()) {
            double yOff = tankBounds.maxY - y2;  // lighter than air fluids move to the top of the tank
            y1 += yOff; y2 += yOff;
        }
        return new AABB(tankBounds.minX, y1, tankBounds.minZ, tankBounds.maxX, y2, tankBounds.maxZ);
    }

    public static void addItemParticles(Level world, ItemStack stack, BlockPos pos, int count) {
        Random random = world.getRandom();

        for(int i = 0; i < count; i++) {
            Vec3 offset = new Vec3(((double) random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
            Vec3 vector = new Vec3(((double) random.nextFloat() - 0.5D) * 0.3D, -random.nextFloat() * 0.6D - 0.3D, 0.6D);
            vector = vector.add(pos.getX(), pos.getY(), pos.getZ());

            if (world instanceof ServerLevel) {
                ((ServerLevel) world).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack), vector.x, vector.y, vector.z, 1, offset.x, offset.y + 0.05D, offset.z, 0.0D);
            } else {
                world.addParticle(new ItemParticleOption(ParticleTypes.ITEM, stack), vector.x, vector.y, vector.z, offset.x, offset.y + 0.05D, offset.z);
            }
        }
    }

    public static <T extends ParticleOptions> void spawnAurealMoteParticle(T type, ServerLevel world, double posX, double posY, double posZ, int particleCount, double xOffset, double yOffset, double zOffset, double speed) {
        ClientboundLevelParticlesPacket sspawnparticlepacket = new ClientboundLevelParticlesPacket(type, false, posX, posY, posZ, (float)xOffset, (float)yOffset, (float)zOffset, (float)speed, particleCount);

        for(int j = 0; j < world.players().size(); ++j) {
            ServerPlayer player = world.players().get(j);
            if (player.getInventory().contains(NewModItems.Stacks.LENS_OF_VERITATIS)) {
                world.sendParticles(player, false, posX, posY, posZ, sspawnparticlepacket);
            }
        }
    }
}
