package com.stal111.forbidden_arcanus.client;

import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.inventory.clibano.ClibanoMenu;
import com.stal111.forbidden_arcanus.common.network.clientbound.SetClibanoResiduesPayload;
import com.stal111.forbidden_arcanus.common.network.clientbound.SpawnParticlePayload;
import com.stal111.forbidden_arcanus.common.network.clientbound.TransformPedestalPayload;
import com.stal111.forbidden_arcanus.common.network.clientbound.UpdateEssencePayload;
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
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * @author stal111
 * @since 02.03.2024
 */
public final class ClientPayloadHandler {

    private static final ClientPayloadHandler INSTANCE = new ClientPayloadHandler();

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
        Level level = Minecraft.getInstance().level;

        ParticleUtils.spawnParticlesOnBlockFaces(level, payload.pos(), ModParticles.MAGNETIC_GLOW.get(), UniformInt.of(3, 5));

        level.playLocalSound(payload.pos(), ModSounds.FERROGNETIC_MIXTURE_APPLY.get(), SoundSource.PLAYERS, 0.75F, 0.9F + level.getRandom().nextFloat() * 0.1F, false);
    }

    public void handle(SpawnParticlePayload payload, IPayloadContext context) {
        Level level = Minecraft.getInstance().level;
        RandomSource random = level.getRandom();
        LevelRenderer levelRenderer = Minecraft.getInstance().levelRenderer;

        double x = payload.x();
        double y = payload.y();
        double z = payload.z();

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

    public void handle(UpdateEssencePayload payload, IPayloadContext context) {
        EssenceHelper.getEssenceProvider(context.player()).ifPresent(provider -> {
            provider.setStorage(payload.storage());
        });
    }
}
