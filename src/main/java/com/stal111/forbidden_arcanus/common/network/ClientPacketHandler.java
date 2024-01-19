package com.stal111.forbidden_arcanus.common.network;

import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.MagicCircle;
import com.stal111.forbidden_arcanus.common.network.clientbound.*;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
import com.stal111.forbidden_arcanus.core.init.ModSounds;
import com.stal111.forbidden_arcanus.core.mixin.LevelRendererAccessor;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import com.stal111.forbidden_arcanus.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.valhelsia.valhelsia_core.api.common.block.entity.neoforge.ValhelsiaContainerBlockEntity;

/**
 * @author stal111
 * @since 2021-12-25
 */
public class ClientPacketHandler {
    public static void handleTransformPedestal(TransformPedestalPacket packet) {
        ParticleUtils.spawnParticlesOnBlockFaces(getLevel(), packet.pos(), ModParticles.MAGNETIC_GLOW.get(), UniformInt.of(3, 5));

        getLevel().playLocalSound(packet.pos(), ModSounds.FERROGNETIC_MIXTURE_APPLY.get(), SoundSource.PLAYERS, 0.75F, 0.9F + getLevel().random.nextFloat() * 0.1F, false);
    }

    public static void handleCreateMagicCircle(CreateMagicCirclePacket packet) {
        Level level = getLevel();

        if (level == null || !(level.getBlockEntity(packet.pos()) instanceof HephaestusForgeBlockEntity blockEntity)) {
            return;
        }

        blockEntity.setMagicCircle(new MagicCircle(level, packet.pos(), packet.innerTexture(), packet.outerTexture()));
    }

    public static void handleRemoveMagicCircle(RemoveMagicCirclePacket packet) {
        Level level = getLevel();

        if (level == null || !(level.getBlockEntity(packet.pos()) instanceof HephaestusForgeBlockEntity blockEntity)) {
            return;
        }

        blockEntity.removeMagicCircle();
    }

    public static void handleCreateValidRitualIndicator(CreateValidRitualIndicatorPacket packet) {
        Level level = getLevel();

        if (level == null || !(level.getBlockEntity(packet.pos()) instanceof HephaestusForgeBlockEntity blockEntity)) {
            return;
        }

        blockEntity.createValidRitualIndicator(true);
    }

    public static void handleRemoveValidRitualIndicator(RemoveValidRitualIndicatorPacket packet) {
        Level level = getLevel();

        if (level == null || !(level.getBlockEntity(packet.pos()) instanceof HephaestusForgeBlockEntity blockEntity)) {
            return;
        }

        blockEntity.removeValidRitualIndicator();
    }

    public static void handleAddItemParticle(AddItemParticlePacket packet) {
        Level level = getLevel();

        if (level != null) {
            RenderUtils.addItemParticles(level, packet.stack(), packet.pos(), 16);
        }
    }

    public static void handleUpdateRitual(UpdateForgeRitualPacket packet) {
        Level level = getLevel();

        if (level == null || !(level.getBlockEntity(packet.pos()) instanceof HephaestusForgeBlockEntity blockEntity)) {
            return;
        }

        blockEntity.getRitualManager().setActiveRitual(level.registryAccess().registryOrThrow(FARegistries.RITUAL).get(packet.ritual()));
    }

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
