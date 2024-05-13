package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Ritual Starter Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.RitualStarterItem
 *
 * @author stal111
 * @since 2021-09-18
 */
public interface RitualStarterItem {
    int getRitualUses(ItemStack stack);
    int getRemainingUses(ItemStack stack);
    void setRemainingUses(ItemStack stack, int remainingUses);

    default boolean canStartRitual(ItemStack stack) {
        return this.getRemainingUses(stack) >= 1;
    }

    default void tryStartRitual(HephaestusForgeBlockEntity blockEntity, Level level, ItemStack stack, Player player) {
        if (this.canStartRitual(stack) && level instanceof ServerLevel) {
            if (blockEntity.getRitualManager().startRitual(blockEntity.getEssenceManager().getStorage()) && !player.getAbilities().instabuild) {
                this.setRemainingUses(stack, this.getRemainingUses(stack) - 1);
            }
        }

        this.playAdditionalEffect(level, blockEntity.getBlockPos(), player);
    }

    default void playAdditionalEffect(Level level, BlockPos pos, Player player) {
    }
}
