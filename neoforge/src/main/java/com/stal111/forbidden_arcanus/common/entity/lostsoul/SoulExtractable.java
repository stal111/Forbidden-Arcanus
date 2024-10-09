package com.stal111.forbidden_arcanus.common.entity.lostsoul;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * @author stal111
 * @since 2022-10-01
 */
public interface SoulExtractable {

    ItemStack getSoulItem();
    void setExtracting();

    default void extractTick(Player player, LivingEntity entity) {
        if (!entity.isDeadOrDying()) {
            this.extractTick(player);
        }
    }

    void extractTick(Player player);
}
