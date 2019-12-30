package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BlockActivatedListener {

    @SubscribeEvent
    public static void onItemRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        ItemStack stack = event.getItemStack();
        BlockState state = world.getBlockState(event.getPos());
        if (stack.getItem() == ModItems.ARCANE_CRYSTAL_DUST.getItem()) {
            if (state.getBlock() == Blocks.FARMLAND) {
                if (!event.getPlayer().abilities.isCreativeMode) {
                    stack.shrink(1);
                }
                if (state.get(BlockStateProperties.MOISTURE_0_7) >= 1) {
                    world.setBlockState(event.getPos(), ModBlocks.MAGICAL_FARMLAND.getState().with(BlockStateProperties.MOISTURE_0_7, 7), 2);
                } else {
                    world.setBlockState(event.getPos(), ModBlocks.MAGICAL_FARMLAND.getState(), 2);
                }
            }
        }
    }
}