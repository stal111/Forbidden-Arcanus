package com.stal111.forbidden_arcanus.common.network;

import com.stal111.forbidden_arcanus.common.network.clientbound.AddThrownAurealBottleParticle;
import com.stal111.forbidden_arcanus.common.network.clientbound.UpdateItemInSlotPacket;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
import com.stal111.forbidden_arcanus.core.mixin.LevelRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.valhelsia.valhelsia_core.api.common.block.entity.neoforge.ValhelsiaContainerBlockEntity;

/**
 * @author stal111
 * @since 2021-12-25
 */
public class ClientPacketHandler {

    public static void handleUpdateItemInSlot(UpdateItemInSlotPacket packet) {
        Level level = getLevel();

        if (level != null && level.getBlockEntity(packet.pos()) instanceof ValhelsiaContainerBlockEntity blockEntity) {
            blockEntity.setStack(packet.slot(), packet.stack());
        }
    }

    public static void handleAddThrownAurealBottleParticle(AddThrownAurealBottleParticle packet) {
        Level level = getLevel();
        RandomSource random = level.getRandom();
        LevelRenderer levelRenderer = getLevelRenderer();

        double x = packet.x();
        double y = packet.y();
        double z = packet.z();

        for (int l = 0; l < 8; ++l) {
            level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.SPLASH_POTION)), x, y, z, random.nextGaussian() * 0.15D, random.nextDouble() * 0.2D, random.nextGaussian() * 0.15D);
        }

        ParticleOptions particleoptions = ModParticles.AUREAL_MOTE.get();

        for (int j3 = 0; j3 < 100; ++j3) {
            double xPos = random.nextDouble() * 4.0D;
            double zPos = random.nextDouble() * Math.PI * 2.0D;
            double xSpeed = Math.cos(zPos) * xPos;
            double ySpeed = ((double) random.nextFloat() - 0.4D) * 0.125D;
            double zSpeed = Math.sin(zPos) * xPos;

            Particle particle = ((LevelRendererAccessor) levelRenderer).callAddParticleInternal(particleoptions, particleoptions.getType().getOverrideLimiter(), x + xSpeed * 0.1D, y + 0.3D, z + zSpeed * 0.1D, xSpeed, ySpeed, zSpeed);

            particle.setLifetime(25 + random.nextInt(10));
        }

        level.playLocalSound(x, y, z, SoundEvents.SPLASH_POTION_BREAK, SoundSource.NEUTRAL, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
    }

    private static ClientLevel getLevel() {
        return Minecraft.getInstance().level;
    }

    private static LevelRenderer getLevelRenderer() {
        return Minecraft.getInstance().levelRenderer;
    }
}
