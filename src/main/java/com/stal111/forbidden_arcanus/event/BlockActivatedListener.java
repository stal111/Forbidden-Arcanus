package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.block.ModBlocks;
import com.stal111.forbidden_arcanus.item.ModItems;
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
        if (state.getBlock() == Blocks.FARMLAND) {
            if (stack.getItem() == ModItems.arcane_crystal_dust) {
                if (!event.getPlayer().abilities.isCreativeMode) {
                    stack.shrink(1);
                }
                if (state.get(BlockStateProperties.MOISTURE_0_7) >= 1) {
                    world.setBlockState(event.getPos(), ModBlocks.magical_farmland.getDefaultState().with(BlockStateProperties.MOISTURE_0_7, Integer.valueOf(7)), 2);
                } else {
                    world.setBlockState(event.getPos(), ModBlocks.magical_farmland.getDefaultState(), 2);
                }
            }
        }
    }
}
