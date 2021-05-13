package com.stal111.forbidden_arcanus.util;

import com.stal111.forbidden_arcanus.aureal.capability.AurealImpl;
import com.stal111.forbidden_arcanus.aureal.capability.AurealProvider;
import com.stal111.forbidden_arcanus.aureal.capability.IAureal;
import com.stal111.forbidden_arcanus.aureal.consequence.Consequences;
import com.stal111.forbidden_arcanus.aureal.consequence.IConsequence;
import com.stal111.forbidden_arcanus.config.AurealConfig;
import net.minecraft.entity.player.PlayerEntity;

/**
 * Aureal Helper
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.util.AurealHelper
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-01-27
 */
public class AurealHelper {

    public static IAureal getCapability(PlayerEntity player) {
        if (player.getCapability(AurealProvider.CAPABILITY).resolve().isPresent()) {
            return player.getCapability(AurealProvider.CAPABILITY).resolve().get();
        }
        return new AurealImpl();
    }

    public static int getAureal(PlayerEntity player) {
        return getCapability(player).getAureal();
    }

    public static void increaseAureal(PlayerEntity player, int amount) {
        getCapability(player).increaseAureal(amount);
    }

    public static void increaseCorruption(PlayerEntity player, int amount) {
        for (int i = 0; i < amount; i++) {
            getCapability(player).increaseCorruption(1);

            int corruption = getCapability(player).getCorruption();

            if (corruption <= 10 || AurealConfig.DISABLE_CONSEQUENCES.get()) {
                continue;
            }

            if (player.getRNG().nextInt(100) <= Math.toIntExact(Math.round(3 + (corruption / 1.85)))) {
                IConsequence consequence = Consequences.getRandomConsequence(player.getRNG()).createConsequence();
                consequence.tick(player);

                if (consequence instanceof ISavedData) {
                    getCapability(player).addActiveConsequence(consequence);
                }
            }
        }
    }
}