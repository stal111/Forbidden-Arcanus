package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.block.StellaArcanumBlock;
import com.stal111.forbidden_arcanus.core.config.BlockConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

/**
 * @author stal111
 * @since 2021-11-17
 */
@EventBusSubscriber
public class BlockEvents {

    @SubscribeEvent
    public static void onBlockBroken(BlockEvent.BreakEvent event) {
        LevelAccessor level = event.getLevel();

        if (!level.isClientSide()) {
            Player player = event.getPlayer();
            BlockState state = event.getState();
            ItemStack stack = player.getMainHandItem();

            if (state.getBlock() instanceof StellaArcanumBlock && BlockConfig.STELLA_ARCANUM_EXPLODE.get() && stack.getEnchantmentLevel(Enchantments.SILK_TOUCH) == 0) {
                StellaArcanumBlock.explode = true;
                StellaArcanumBlock.world = (Level) level;
            }
        }
    }
}
