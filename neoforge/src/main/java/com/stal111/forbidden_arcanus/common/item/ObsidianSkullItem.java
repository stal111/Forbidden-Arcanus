package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.block.skull.ObsidianSkullType;
import com.stal111.forbidden_arcanus.common.item.component.FireDegradation;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Util;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jspecify.annotations.Nullable;

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
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, @Nullable EquipmentSlot slot) {
        if (slot != EquipmentSlot.HEAD || !(entity instanceof LivingEntity livingEntity) || !livingEntity.isOnFire() || livingEntity.hasInfiniteMaterials() || livingEntity.hasEffect(MobEffects.FIRE_RESISTANCE)) {
            return;
        }

        FireDegradation fireDegradation = stack.get(ModDataComponents.FIRE_DEGRADATION);

        if (fireDegradation != null) {
            fireDegradation.tick(stack, livingEntity, slot);
        }
    }
}
