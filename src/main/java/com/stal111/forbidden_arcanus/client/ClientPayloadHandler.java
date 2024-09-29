package com.stal111.forbidden_arcanus.client;

import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.inventory.clibano.ClibanoMenu;
import com.stal111.forbidden_arcanus.common.network.clientbound.*;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
import com.stal111.forbidden_arcanus.core.init.ModSounds;
import com.stal111.forbidden_arcanus.core.mixin.LevelRendererAccessor;
import net.minecraft.client.Minecraft;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * @author stal111
 * @since 02.03.2024
 */
public final class ClientPayloadHandler {

    private static final ClientPayloadHandler INSTANCE = new ClientPayloadHandler();

    private final Minecraft minecraft = Minecraft.getInstance();

    private ClientPayloadHandler() {
    }

    public static ClientPayloadHandler getInstance() {
        return INSTANCE;
    }

    public void handle(SetClibanoResiduesPayload payload, IPayloadContext context) {
        Player player = context.player();

        if (player.hasContainerOpen() && player.containerMenu instanceof ClibanoMenu menu) {
            menu.setResidueData(payload.residueAmounts().getResidueTypeAmountMap());
        }
    }

    public void handle(TransformPedestalPayload payload, IPayloadContext context) {
        ParticleUtils.spawnParticlesOnBlockFaces(this.minecraft.level, payload.pos(), ModParticles.MAGNETIC_GLOW.get(), UniformInt.of(3, 5));

        this.minecraft.level.playLocalSound(payload.pos(), ModSounds.FERROGNETIC_MIXTURE_APPLY.get(), SoundSource.PLAYERS, 0.75F, 0.9F + this.minecraft.level.getRandom().nextFloat() * 0.1F, false);
    }

    public void handle(SpawnParticlePayload payload, IPayloadContext context) {
        RandomSource random = this.minecraft.level.getRandom();
        LevelRenderer levelRenderer = this.minecraft.levelRenderer;

        double x = payload.x();
        double y = payload.y();
        double z = payload.z();

        for (int l = 0; l < 8; ++l) {
            this.minecraft.level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.SPLASH_POTION)), x, y, z, random.nextGaussian() * 0.15D, random.nextDouble() * 0.2D, random.nextGaussian() * 0.15D);
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

        this.minecraft.level.playLocalSound(x, y, z, SoundEvents.SPLASH_POTION_BREAK, SoundSource.NEUTRAL, 1.0F, random.nextFloat() * 0.1F + 0.9F, false);
    }

    public void handle(UpdateEssencePayload payload, IPayloadContext context) {
        EssenceHelper.getEssenceProvider(context.player()).ifPresent(provider -> {
            provider.setStorage(payload.storage());
        });
    }

    public void handle(AdvancedBlockEventPayload payload, IPayloadContext context) {
        this.minecraft.level.blockEvent(payload.pos(), payload.block(), payload.b1(), payload.b2());
    }
}
