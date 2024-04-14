package com.stal111.forbidden_arcanus.client;

import com.stal111.forbidden_arcanus.common.inventory.clibano.ClibanoMenu;
import com.stal111.forbidden_arcanus.common.network.clientbound.SetClibanoResiduesPayload;
import com.stal111.forbidden_arcanus.common.network.clientbound.TransformPedestalPayload;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
import com.stal111.forbidden_arcanus.core.init.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

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

    public void handle(SetClibanoResiduesPayload payload, PlayPayloadContext context) {
        context.player().ifPresent(player -> {
            if (player.hasContainerOpen() && player.containerMenu instanceof ClibanoMenu menu) {
                menu.setResidueData(payload.residueAmounts());
            }
        });
    }

    public void handle(TransformPedestalPayload payload, PlayPayloadContext context) {
        context.workHandler().execute(() -> {
            context.level().ifPresent(level -> {
                ParticleUtils.spawnParticlesOnBlockFaces(level, payload.pos(), ModParticles.MAGNETIC_GLOW.get(), UniformInt.of(3, 5));

                level.playLocalSound(payload.pos(), ModSounds.FERROGNETIC_MIXTURE_APPLY.get(), SoundSource.PLAYERS, 0.75F, 0.9F + level.getRandom().nextFloat() * 0.1F, false);
            });
        });
    }
}
