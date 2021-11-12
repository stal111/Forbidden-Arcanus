package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.entity.projectile.BoomArrowEntity;
import com.stal111.forbidden_arcanus.entity.projectile.DracoArcanusArrowEntity;
import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.Item.Properties;

public class ModArrowItem extends ArrowItem {

    public ModArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level world, ItemStack stack, LivingEntity entity) {
        if (this == ModItems.BOOM_ARROW.get()) {
            return  new BoomArrowEntity(entity, world);
        } else if (this == ModItems.DRACO_ARCANUS_ARROW.get()) {
            return new DracoArcanusArrowEntity(entity, world);
        } else {
            return null;
        }
    }
}
