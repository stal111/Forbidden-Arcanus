package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.block.StellaArcanumBlock;
import com.stal111.forbidden_arcanus.config.BlockConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Block Events <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.event.BlockEvents
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-11-17
 */
@Mod.EventBusSubscriber
public class BlockEvents {

    @SubscribeEvent
    public static void onBlockBroken(BlockEvent.BreakEvent event) {
        LevelAccessor level = event.getWorld();

        if (!level.isClientSide()) {
            Player player = event.getPlayer();
            BlockState state = event.getState();
            ItemStack stack = player.getMainHandItem();

            if (state.getBlock() instanceof StellaArcanumBlock && BlockConfig.STELLA_ARCANUM_EXPLODE.get() && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0) {
                StellaArcanumBlock.explode = true;
                StellaArcanumBlock.world = (Level) event.getWorld();
            }
        }
    }
}
