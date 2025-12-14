package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.block.skull.ObsidianSkullType;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Util;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;

/**
 * @author stal111
 * @since 2021-02-11
 */
public class ObsidianSkullItem extends StandingAndWallBlockItem {

    public static final Map<ObsidianSkullType, Block> NEXT_SKULL_STAGE = Util.make(new EnumMap<>(ObsidianSkullType.class), map -> {
        map.put(ObsidianSkullType.DEFAULT, ModBlocks.CRACKED_OBSIDIAN_SKULL.getSkull());
        map.put(ObsidianSkullType.CRACKED, ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.getSkull());
        map.put(ObsidianSkullType.FRAGMENTED, ModBlocks.FADING_OBSIDIAN_SKULL.getSkull());
        map.put(ObsidianSkullType.FADING, Blocks.SKELETON_SKULL);
    });

    public ObsidianSkullItem(Block floorBlock, Block wallBlock, Properties properties) {
        super(floorBlock, wallBlock, Direction.DOWN, properties);
    }

    @Nullable
    @Override
    public EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.HEAD;
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, @org.jetbrains.annotations.Nullable EquipmentSlot slot) {
        //TODO
//        if (slotId != 39 || !(entity instanceof Player player)) {
//            return;
//        }
//
//        if (!player.isOnFire()) {
//            return;
//        }
//
//        this.getType(stack).tick(stack, player);
    }


    public ObsidianSkullType getType(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.OBSIDIAN_SKULL_TYPE, ObsidianSkullType.DEFAULT);
    }
}
