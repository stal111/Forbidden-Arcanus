package com.stal111.forbidden_arcanus.util;

import com.stal111.forbidden_arcanus.aureal.capability.AurealImpl;
import com.stal111.forbidden_arcanus.aureal.capability.AurealProvider;
import com.stal111.forbidden_arcanus.aureal.capability.IAureal;
import com.stal111.forbidden_arcanus.aureal.consequence.Consequences;
import com.stal111.forbidden_arcanus.aureal.consequence.IConsequence;
import com.stal111.forbidden_arcanus.config.AurealConfig;
import com.stal111.forbidden_arcanus.network.AurealUpdatePacket;
import com.stal111.forbidden_arcanus.network.NetworkHandler;
import net.minecraft.entity.player.PlayerEntity;

/**
 * Aureal Helper <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.util.AurealHelper
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-01-27
 */
public class AurealHelper {

    public static IAureal getCapability(PlayerEntity player) {
        return player.getCapability(AurealProvider.CAPABILITY).orElse(new AurealImpl());
    }

    public static int getAureal(PlayerEntity player) {
        return getCapability(player).getAureal();
    }

    public static void increaseAureal(PlayerEntity player, int amount) {
        getCapability(player).increaseAureal(amount);
    }

    public static void increaseCorruption(PlayerEntity player, int amount) {
        IAureal aureal = getCapability(player);

        for (int i = 0; i < amount; i++) {
            aureal.increaseCorruption(1);

            int corruption = aureal.getCorruption();

            if (corruption <= 10 || AurealConfig.DISABLE_CONSEQUENCES.get()) {
                continue;
            }

            if (player.getRNG().nextDouble() < (corruption + 3) / 185.0F) {
                IConsequence consequence = Consequences.getRandomConsequence(player.getRNG()).createConsequence();
                consequence.tick(player);

                if (consequence instanceof ISavedData) {
                    aureal.addActiveConsequence(consequence);
                }
            }
        }
    }

    public static void playerTick(PlayerEntity player) {
        IAureal aureal = AurealHelper.getCapability(player);

        aureal.updateActiveConsequences(player);

        if (!AurealConfig.NATURAL_CORRUPTION_DECREASEMENT.get()) {
            return;
        }

        if (aureal.getCorruption() >= 1) {
            aureal.setCorruptionTimer(aureal.getCorruptionTimer() + 1);

            if (aureal.getCorruptionTimer() >= AurealConfig.NATURAL_CORRUPTION_DECREASEMENT_TIME.get()) {
                aureal.setCorruption(aureal.getCorruption() - 1);
                aureal.setCorruptionTimer(0);

                sendAurealUpdatePacket(player);
            }
        } else if (aureal.getCorruptionTimer() != 0) {
            aureal.setCorruptionTimer(0);
        }
    }

    /**
     * Sends an {@link AurealUpdatePacket} to the given player.
     * This method should only be called server-side.
     *
     * @param player the player the packet gets sent to.
     */
    public static void sendAurealUpdatePacket(PlayerEntity player) {
        NetworkHandler.sendTo(player, new AurealUpdatePacket(player));
    }
}