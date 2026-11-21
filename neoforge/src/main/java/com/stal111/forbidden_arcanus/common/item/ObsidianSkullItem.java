package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.essence.EssenceCost;
import com.stal111.forbidden_arcanus.common.item.component.FireDegradation;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;

/**
 * @author stal111
 * @since 2021-02-11
 */
public class ObsidianSkullItem extends StandingAndWallBlockItem {

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

        if (livingEntity.tickCount % 20 == 0) {
            EssenceCost essenceCost = stack.get(ModDataComponents.ESSENCE_COST);

            if (essenceCost != null && essenceCost.hasEnough(livingEntity)) {
                essenceCost.consume(livingEntity);
            }
        }
    }
}
