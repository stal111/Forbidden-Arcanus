package com.stal111.forbidden_arcanus.util;

import com.stal111.forbidden_arcanus.aureal.capability.AurealProvider;
import com.stal111.forbidden_arcanus.aureal.consequence.Consequences;
import com.stal111.forbidden_arcanus.aureal.consequence.IConsequence;
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

    public static void increaseAureal(PlayerEntity player, int amount) {
        player.getCapability(AurealProvider.CAPABILITY).ifPresent(aureal -> aureal.increaseAureal(amount));
    }

    public static void increaseCorruption(PlayerEntity player, int amount) {
        int finalAmount = amount;
        player.getCapability(AurealProvider.CAPABILITY).ifPresent(aureal -> aureal.increaseCorruption(finalAmount));
        int corruption = player.getCapability(AurealProvider.CAPABILITY).resolve().get().getCorruption();

        while (amount > 0) {
            amount--;
            if (player.getRNG().nextInt(100) <= Math.toIntExact(Math.round(1.5 + (corruption / 1.75)))) {
                IConsequence consequence = Consequences.getRandomConsequence(player.getRNG()).createConsequence();
                consequence.tick(player);

                if (consequence instanceof ISavedData) {
                    player.getCapability(AurealProvider.CAPABILITY).ifPresent(aureal -> aureal.addActiveConsequence(consequence));
                }
            }
        }
    }
}
